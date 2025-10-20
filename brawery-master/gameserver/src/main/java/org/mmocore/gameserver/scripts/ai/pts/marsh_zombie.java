package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class marsh_zombie extends warrior_pa_slow_type2
{
	public marsh_zombie(final NpcInstance actor){super(actor);}

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

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.OwnItemCount(target, 972) == 1)
		{
			random1_list.SetInfo(0, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 319) == 1 && AiUtils.OwnItemCount(target, 1045) < 5)
		{
			random1_list.SetInfo(1, target);
		}
		target = attacker;
		always_list.SetInfo(2, target);
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 2)
			{
				SetCurrentQuestID(412);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (AiUtils.HaveMemo(c_quest0, 412) == 1 && AiUtils.OwnItemCount(c_quest0, 1277) == 1 && i_quest0 == 1 && AiUtils.OwnItemCount(c_quest0, 1257) < 3)
					{
						if (AiUtils.Rand(2) == 0)
						{
							GiveItem1(c_quest0, 1257, 1);
							if (AiUtils.OwnItemCount(c_quest0, 1257) == 3)
							{
								SoundEffect(c_quest0, "ItemSound.quest_middle");
							}
							else
							{
								SoundEffect(c_quest0, "ItemSound.quest_itemget");
							}
						}
					}
				}
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(103);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(10) < 5)
						{
							GiveItem1(target, 973, 1);
							SoundEffect(target, "ItemSound.quest_middle");
							DeleteItem1(target, 972, 1);
							SetFlagJournal(target, 103, 7);
							ShowQuestMark(target, 103);
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(319);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(10) > 7)
						{
							GiveItem1(target, 1045, 1);
							if (AiUtils.OwnItemCount(target, 1045) >= 4)
							{
								SoundEffect(target, "ItemSound.quest_middle");
								SetFlagJournal(target, 319, 2);
								ShowQuestMark(target, 319);
							}
							else
							{
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
			}
		}
		super.onEvtDead(attacker);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		SetCurrentQuestID(412);
		if (AiUtils.HaveMemo(attacker, 412)!=0)
		{
			switch (i_quest0)
			{
				case 0:
				{
					i_quest0 = 1;
					if (attacker.getPlayer() !=null)
					{
						c_quest0 = attacker.getPlayer();
					}
					else
					{
						c_quest0 = attacker;
					}
					break;
				}
				case 1:
				{
					if (attacker.getPlayer() !=null)
					{
						if (c_quest0 != attacker.getPlayer())
						{
							i_quest0 = 2;
						}
					}
					else if (c_quest0 != attacker)
					{
						i_quest0 = 2;
					}
					break;
				}
				case 2:
				{
					break;
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}