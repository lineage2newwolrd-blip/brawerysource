package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class kitanis_van_haken extends warrior_aggressive
{
	public kitanis_van_haken(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		Creature c0 = null;
		Creature c1 = null;
		int i0 = 0;
		int i1 = 0;
		NpcInstance npc0 = null;
		final NpcInstance actor = getActor();

		SetCurrentQuestID(91);
		c1 = attacker;
		if (c1.getPlayer() !=null)
		{
			c1 = c1.getPlayer();
		}
		i0 = actor.param3;
		if (i0 > 0)
		{
			c0 = Maker_FindNpcByKey(i0);
			if (c0 !=null)
			{
				AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 100000);
			}
		}
		if (AiUtils.HaveMemo(c1, 91) == 1 && c1.getClassId() == 14 && (AiUtils.GetMemoState(c1, 91) == 13 || AiUtils.GetMemoState(c1, 91) == 14))
		{
			if (c1.isPlayer())
			{
				if (c1.getObjectId() == actor.param2)
				{
					i_quest0 = i_quest0 + 1;
					if (i_quest0 == 1)
					{
						if (c1.isPlayer())
						{
							Say(AiUtils.MakeFString(9166, c1.getName(), "", "", "", ""));
						}
					}
					if (i_quest0 > 15)
					{
						SetMemoState(c1, 91, 14);
						if (c1.isPlayer())
						{
							Say(AiUtils.MakeFString(9167, c1.getName(), "", "", "", ""));
						}
						c0 = Maker_FindNpcByKey(actor.param3);
						npc0 = AiUtils.GetNPCFromID(c0.getObjectId());
						if (!AiUtils.IsNull(npc0))
						{
							npc0.i_quest0 = 1;
						}
						Despawn();
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		Creature c0 = null;
		int i0 = 0;
		NpcInstance npc0 = null;
		final NpcInstance actor = getActor();

		SetCurrentQuestID(91);
		if (timer_id == 99906)
		{
			i0 = actor.param3;
			if (i0 > 0)
			{
				c0 = Maker_FindNpcByKey(i0);
				if (c0 !=null)
				{
					AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 100000);
					Say(AiUtils.MakeFString(9164, "", "", "", "", ""));
				}
				else
				{
					AddTimerEx(99906, 500);
				}
			}
		}
		else if (timer_id == 99907)
		{
			Say(AiUtils.MakeFString(9165, "", "", "", "", ""));
			c0 = Maker_FindNpcByKey(actor.param3);
			npc0 = AiUtils.GetNPCFromID(c0.getObjectId());
			if (!AiUtils.IsNull(npc0))
			{
				npc0.i_quest0 = 2;
			}
			Despawn();
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtSpawn()
	{

		SetCurrentQuestID(91);
		AddTimerEx(99906, 500);
		AddTimerEx(99907, 1000 * (60 - 1));
		super.onEvtSpawn();
	}

}