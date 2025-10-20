package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class nameless_revenant extends warrior_pa_slow_type2
{
	public nameless_revenant(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;
		final NpcInstance actor = getActor();

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		always_list.SetInfo(0, target);
		target = attacker;
		always_list.SetInfo(1, target);
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(229);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 229) == 1 && AiUtils.OwnItemCount(c1, 3309) == 1 && AiUtils.OwnItemCount(c1, 3314) == 1 && AiUtils.OwnItemCount(c1, 3319) == 0)
						{
							GiveItem1(c1, 3319, 1);
							SoundEffect(c1, "ItemSound.quest_itemget");
							DeleteItem1(c1, 3314, AiUtils.OwnItemCount(c1, 3314));
							if (AiUtils.OwnItemCount(c1, 3317) >= 1 && AiUtils.OwnItemCount(c1, 3318) >= 1 && AiUtils.OwnItemCount(c1, 3320) >= 1 && AiUtils.OwnItemCount(c1, 3321) >= 1 && AiUtils.OwnItemCount(c1, 3322) >= 1)
							{
								SetFlagJournal(c1, 229, 3);
								ShowQuestMark(c1, 229);
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(227);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (AiUtils.HaveMemo(c1, 227) == 1 && AiUtils.GetMemoState(c1, 227) == 1 && AiUtils.OwnItemCount(c1, 2831) < 7 && AiUtils.OwnItemCount(c1, 2822) >= 1 && i_quest0 == 1 && AiUtils.OwnItemCount(c1, 2832) == 0)
						{
							if (AiUtils.OwnItemCount(c1, 2831) == 6)
							{
								CreateOnePrivateEx(27128, "aruraune", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
								DeleteItem1(c1, 2831, AiUtils.OwnItemCount(c1, 2831));
								SetFlagJournal(c1, 227, 2);
								ShowQuestMark(c1, 227);
							}
							else
							{
								GiveItem1(c1, 2831, 1);
								SoundEffect(c1, "ItemSound.quest_itemget");
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

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		SetCurrentQuestID(229);
		if (attacker.getPlayer() !=null)
		{
			attacker = attacker.getPlayer();
		}
		if (AiUtils.HaveMemo(attacker, 229) == 1 && AiUtils.OwnItemCount(attacker, 3309) == 1 && AiUtils.OwnItemCount(attacker, 3314) == 1 && AiUtils.OwnItemCount(attacker, 3319) == 0)
		{
			if (i_quest0 == 0)
			{
				i_quest0 = 1;
				Say(AiUtils.MakeFString(22933, "", "", "", "", ""));
			}
		}
		SetCurrentQuestID(227);
		if (skill_name_id / 65536 == 67567617 / 65536)
		{
			i_quest0 = 1;
		}
		else
		{
			i_quest0 = 2;
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}