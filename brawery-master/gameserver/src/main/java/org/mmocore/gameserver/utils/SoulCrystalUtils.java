package org.mmocore.gameserver.utils;

import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.configuration.config.AllSettingsConfig;
import org.mmocore.gameserver.data.xml.holder.SoulCrystalHolder;
import org.mmocore.gameserver.model.instances.MonsterInstance;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.SystemMessage;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.components.items.ItemInstance;
import org.mmocore.gameserver.templates.SoulCrystal;
import org.mmocore.gameserver.templates.npc.AbsorbInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by iRock
 */
public class SoulCrystalUtils {

	public static void calcAbsorb(Player player, NpcInstance npc, boolean withQuest){
		List<PlayerResult> list;
		Party party = player.getParty();
		if (party == null) {
			list = new ArrayList<>(1);
			list.add(new PlayerResult(player));
		} else {
			list = new ArrayList<>(party.getMemberCount() + 1);
			final PlayerResult pr = new PlayerResult(player);
			list.add(pr); // index 0
			list.add(pr); // DS: у убившего двойной шанс, из ai
			list.addAll(party.getPartyMembers().stream().filter(m -> m != player && m.isInRange(npc.getLoc(), AllSettingsConfig.ALT_PARTY_DISTRIBUTION_RANGE)).map(PlayerResult::new).collect(Collectors.toList()));
		}

		for (AbsorbInfo info : npc.getTemplate().getAbsorbInfo()) {
			calcAbsorb(list, (MonsterInstance) npc, info, withQuest);
		}

		list.forEach(PlayerResult::send);
	}

	private static void calcAbsorb(List<PlayerResult> players, MonsterInstance npc, AbsorbInfo info, boolean withQuest) {
		int memberSize;
		List<PlayerResult> targets;
		switch (info.getAbsorbType()) {
			case LAST_HIT:
				targets = Collections.singletonList(players.get(0));
				break;
			case PARTY_ALL:
				targets = players;
				break;
			case PARTY_RANDOM:
				memberSize = players.size();
				if (memberSize == 1) {
					targets = Collections.singletonList(players.get(0));
				} else {
					int size = Rnd.get(memberSize);
					targets = new ArrayList<>(size);
					List<PlayerResult> temp = new ArrayList<>(players);
					Collections.shuffle(temp);
					for (int i = 0; i < size; i++) {
						targets.add(temp.get(i));
					}
				}
				break;
			case PARTY_ONE:
				memberSize = players.size();
				if (memberSize == 1) {
					targets = Collections.singletonList(players.get(0));
				} else {
					int rnd = Rnd.get(memberSize);
					targets = Collections.singletonList(players.get(rnd));
				}
				break;
			default:
				return;
		}

		for (PlayerResult target : targets) {
			if (target == null || !(target.getMessage() == null || target.getMessage() == SystemMsg.THE_SOUL_CRYSTAL_IS_REFUSING_TO_ABSORB_THE_SOUL)) {
				continue;
			}
			Player targetPlayer = target.getPlayer();
			if (info.isSkill() && !npc.isAbsorbed(targetPlayer)) {
				continue;
			}
			if (withQuest && targetPlayer.getQuestState(350) == null) {
				continue;
			}

			boolean resonation = false;
			SoulCrystal soulCrystal = null;
			ItemInstance[] items = targetPlayer.getInventory().getItems();
			for (ItemInstance item : items) {
				SoulCrystal crystal = SoulCrystalHolder.getInstance().getCrystal(item.getItemId());
				if (crystal == null) {
					continue;
				}

				if (soulCrystal != null) {
					target.setMessage(SystemMsg.THE_SOUL_CRYSTAL_CAUSED_RESONATION_AND_FAILED_AT_ABSORBING_A_SOUL);
					resonation = true;
					break;
				}
				soulCrystal = crystal;
			}

			if (resonation) {
				continue;
			}

			if (soulCrystal == null) {
				continue;
			}

			if (!info.canAbsorb(soulCrystal.getLevel() + 1)) {
				target.setMessage(SystemMsg.THE_SOUL_CRYSTAL_IS_REFUSING_TO_ABSORB_THE_SOUL);
				continue;
			}

			int nextItemId = 0;
			if (info.getCursedChance() > 0 && soulCrystal.getCursedNextItemId() > 0) {
				nextItemId = Rnd.chance(info.getCursedChance()) ? soulCrystal.getCursedNextItemId() : 0;
			}

			if (nextItemId == 0) {
				nextItemId = Rnd.chance(info.getChance()) ? soulCrystal.getNextItemId() : 0;
			}

			if (nextItemId == 0) {
				target.setMessage(SystemMsg.THE_SOUL_CRYSTAL_WAS_NOT_ABLE_TO_ABSORB_THE_SOUL);
				continue;
			}

			if (targetPlayer.consumeItem(soulCrystal.getItemId(), 1)) {
				targetPlayer.getInventory().addItem(nextItemId, 1);
				targetPlayer.sendPacket(SystemMessage.obtainItems(nextItemId, 1, 0));

				target.setMessage(SystemMsg.THE_SOUL_CRYSTAL_SUCCEEDED_IN_ABSORBING_A_SOUL);
			}
		}
	}

	private static class PlayerResult {
		private final Player _player;
		private SystemMsg _message;

		public PlayerResult(Player player) {
			_player = player;
		}

		public Player getPlayer() {
			return _player;
		}

		public SystemMsg getMessage() {
			return _message;
		}

		public void setMessage(SystemMsg message) {
			_message = message;
		}

		public void send() {
			if (_message != null) {
				_player.sendPacket(_message);
				_message = null;
			}
		}
	}
}
