package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class silenos extends warrior_passive
{
	public silenos(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		NpcInstance npc0 = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 216) == 1 && AiUtils.OwnItemCount(target, 3122) == 1 && AiUtils.OwnItemCount(target, 3129) == 1 && AiUtils.OwnItemCount(target, 3133) < 70)
		{
			always_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 216) == 1 && AiUtils.OwnItemCount(target, 3122) == 1 && AiUtils.OwnItemCount(target, 3129) == 1 && AiUtils.OwnItemCount(target, 3133) < 70)
		{
			always_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 216) == 1 && AiUtils.OwnItemCount(target, 3122) == 1 && AiUtils.OwnItemCount(target, 3129) == 1 && AiUtils.OwnItemCount(target, 3133) < 70)
				{
					always_list.SetInfo(0, target);
				}
			}
		}
		target = attacker;
		always_list.SetInfo(1, target);
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(216);
					while (!AiUtils.IsNull(target = code_info.Next()))
					{
						if (DistFromMe(target) <= 1500)
						{
							SetCurrentQuestID(216);
							GiveItem1(target, 3133, 10);
							if (AiUtils.OwnItemCount(target, 3133) >= 60)
							{
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(334);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 334)!=0 && AiUtils.OwnItemCount(c1, 3680)!=0 && AiUtils.OwnItemCount(c1, 3681)!=0 && AiUtils.OwnItemCount(c1, 3688) == 0)
						{
							if (AiUtils.Rand(10) == 0)
							{
								if (GetCurrentTick() - c1.quest_last_reward_time > 1)
								{
									c1.quest_last_reward_time = GetCurrentTick();
									GiveItem1(c1, 3688, 1);
									SoundEffect(c1, "ItemSound.quest_itemget");
									if (AiUtils.OwnItemCount(c1, 3684) >= 1 && AiUtils.OwnItemCount(c1, 3685) >= 1 && AiUtils.OwnItemCount(c1, 3686) >= 1 && AiUtils.OwnItemCount(c1, 3687) >= 1 && AiUtils.OwnItemCount(c1, 3689) >= 1 && AiUtils.OwnItemCount(c1, 3689) >= 1 && AiUtils.OwnItemCount(c1, 3690) >= 1 && AiUtils.OwnItemCount(c1, 3691) >= 1)
									{
										SetJournal(c1, 334, 4);
										ShowQuestMark(c1, 334);
										SoundEffect(c1, "ItemSound.quest_middle");
									}
									else
									{
										SoundEffect(c1, "ItemSound.quest_itemget");
									}
								}
							}
						}
					}
					break;
				}
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			code_info.getCode();
		}
		super.onEvtDead(attacker);
	}

}