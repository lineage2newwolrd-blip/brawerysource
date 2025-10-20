package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class ragna_orc_seer extends warrior_passive
{
	public ragna_orc_seer(final NpcInstance actor){super(actor);}

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
					SetCurrentQuestID(220);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 220)!=0 && AiUtils.OwnItemCount(c1, 3235)!=0 && AiUtils.OwnItemCount(c1, 3236) == 0)
						{
							CreateOnePrivateEx(27086, "revenant_of_tantos_chief", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
							Say(AiUtils.MakeFString(22054, "", "", "", "", ""));
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(232);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 232) == 1 && AiUtils.OwnItemCount(c1, 3413) == 1)
						{
							if (AiUtils.OwnItemCount(c1, 3415) == 0)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 3415, 1);
								SoundEffect(c1, "ItemSound.quest_middle");
							}
							else if (AiUtils.OwnItemCount(c1, 3414) == 0)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 3414, 1);
								SoundEffect(c1, "ItemSound.quest_middle");
								SetFlagJournal(c1, 232, 5);
								ShowQuestMark(c1, 232);
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

		SetCurrentQuestID(220);
		switch (i_quest0)
		{
			case 0:
			{
				c_quest0 = attacker;
				if (AiUtils.HaveMemo(c_quest0, 220)!=0 && AiUtils.OwnItemCount(c_quest0, 3236) == 0)
				{
					Say(AiUtils.MakeFString(22053, "", "", "", "", ""));
					i_quest0 = 1;
				}
				break;
			}
			case 1:
			{
				i_quest0 = 2;
				break;
			}
			case 2:
			{
				break;
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}