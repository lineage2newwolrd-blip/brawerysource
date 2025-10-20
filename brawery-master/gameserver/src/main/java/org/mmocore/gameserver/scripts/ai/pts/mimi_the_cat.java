package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class mimi_the_cat extends warrior_passive_hold
{
	public mimi_the_cat(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c0 = null;
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
		always_list.SetInfo(0, target);
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(230);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c0 = c1.getPlayer();
						if (AiUtils.HaveMemo(c0, 230)!=0 && AiUtils.OwnItemCount(c0, 3366)!=0)
						{
							GiveItem1(c0, 3369, 1);
							DeleteItem1(c0, 3366, AiUtils.OwnItemCount(c0, 3366));
							SoundEffect(c0, "Itemsound.quest_middle");
							Say(AiUtils.MakeFString(23068, "", "", "", "", ""));
						}
					}
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
		Creature c0 = null;
		Creature c1 = null;

		SetCurrentQuestID(230);
		switch (i_quest0)
		{
			case 0:
			{
				c_quest0 = attacker;
				i_quest0 = 1;
				AddTimerEx(23003, 1000 * 120);
				if (c_quest0.getPlayer() !=null)
				{
					c0 = c_quest0.getPlayer();
					AddTimerEx(23010, 1000 * 5);
					c_quest1 = c_quest0.getPlayer();
					if (AiUtils.HaveMemo(c0, 230)!=0 && AiUtils.OwnItemCount(c0, 3365)!=0)
					{
						Say(AiUtils.MakeFString(23066, "", "", "", "", ""));
						GiveItem1(c0, 3366, 1);
						DeleteItem1(c0, 3365, AiUtils.OwnItemCount(c0, 3365));
						AddAttackDesire(c_quest0, DesireMove.MOVE_TO_TARGET, 100000);
					}
				}
				break;
			}
			case 1:
			{
				if (c_quest0 != attacker)
				{
					if (AiUtils.OwnItemCount(c_quest1, 3365) == 0 && AiUtils.OwnItemCount(c_quest1, 3366)!=0)
					{
						i_quest0 = 2;
						Say(AiUtils.MakeFString(23067, "", "", "", "", ""));
						Despawn();
						GiveItem1(c_quest1, 3367, 1);
						DeleteItem1(c_quest1, 3366, AiUtils.OwnItemCount(c_quest1, 3366));
						DeleteItem1(c_quest1, 3365, AiUtils.OwnItemCount(c_quest1, 3365));
						c_quest2 = attacker;
						if (c_quest2.getPlayer() !=null)
						{
							c1 = c_quest2.getPlayer();
							if (AiUtils.HaveMemo(c1, 230)!=0 && AiUtils.OwnItemCount(c1, 3366)!=0)
							{
								GiveItem1(c1, 3367, 1);
								DeleteItem1(c1, 3366, AiUtils.OwnItemCount(c1, 3366));
								DeleteItem1(c1, 3365, AiUtils.OwnItemCount(c1, 3365));
							}
						}
						else if (c_quest2 != c_quest1 && AiUtils.HaveMemo(c_quest2, 230)!=0 && AiUtils.OwnItemCount(c_quest2, 3366)!=0)
						{
							GiveItem1(c_quest2, 3367, 1);
							DeleteItem1(c_quest2, 3366, AiUtils.OwnItemCount(c_quest2, 3366));
							DeleteItem1(c_quest2, 3365, AiUtils.OwnItemCount(c_quest2, 3365));
						}
					}
					else
					{
						Despawn();
					}
				}
			}
			case 2:
			{
				break;
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		SetCurrentQuestID(230);
		if (timer_id == 23003)
		{
			Despawn();
		}
		else if (timer_id == 23010)
		{
			if (!c_quest0.isAlive())
			{
				Despawn();
			}
			else
			{
				AddTimerEx(23010, 1000 * 5);
			}
		}
		super.onEvtTimerFiredEx(timer_id);
	}

}