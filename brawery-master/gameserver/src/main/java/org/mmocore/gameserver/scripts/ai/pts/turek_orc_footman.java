package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class turek_orc_footman extends warrior_passive_run_away_to_clan
{
	public turek_orc_footman(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i1 = 0;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;
		final NpcInstance actor = getActor();

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 171)!=0 && AiUtils.GetMemoState(target, 171) == 2 && AiUtils.OwnItemCount(target, 4239) < 20)
		{
			random1_list.SetInfo(0, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 327) == 1)
		{
			random1_list.SetInfo(1, target);
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			code_info.getCode();
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(171);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(2) == 1)
						{
							GiveItem1(target, 4239, 1);
							if (AiUtils.OwnItemCount(target, 4239) >= 19)
							{
								SoundEffect(target, "Itemsound.quest_middle");
							}
							else
							{
								SoundEffect(target, "Itemsound.quest_itemget");
							}
						}
						if (AiUtils.OwnItemCount(target, 4239) == 5)
						{
							CreateOnePrivateEx(27190, "ol_mahum_support_troop", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 1, AiUtils.GetIndexFromCreature(target), 0, 0);
						}
						if (AiUtils.OwnItemCount(target, 4239) >= 10)
						{
							if (AiUtils.Rand(100) <= 24)
							{
								CreateOnePrivateEx(27190, "ol_mahum_support_troop", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 1, AiUtils.GetIndexFromCreature(target), 0, 0);
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(327);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						GiveItem1(target, 1846, 1);
						SoundEffect(target, "ItemSound.quest_itemget");
						i0 = AiUtils.Rand(100);
						if (i0 < 19)
						{
							i1 = AiUtils.Rand(100);
							if (i1 < 25)
							{
								GiveItem1(target, 1848, 1);
							}
							else if (i1 < 50)
							{
								GiveItem1(target, 1849, 1);
							}
							else if (i1 < 75)
							{
								GiveItem1(target, 1850, 1);
							}
							else
							{
								GiveItem1(target, 1851, 1);
							}
						}
					}
					break;
				}
			}
		}
		super.onEvtDead(attacker);
	}

}