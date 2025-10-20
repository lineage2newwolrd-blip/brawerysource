package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_run_away_to_clan extends warrior
{
	public int flee_x = 0;
	public int flee_y = 0;
	public int flee_z = 0;

	public warrior_run_away_to_clan(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
		i_ai4 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i6 = 0;
		int i5 = 0;
		final NpcInstance actor = getActor();

		i6 = AiUtils.Rand(100);
		if (i_ai4 == 0)
		{
			i_ai4 = 1;
		}
		else if (actor.currentHp < actor.getMaxHp() / 2.000000f && actor.currentHp > actor.getMaxHp() / 3.000000f && attacker.currentHp > attacker.getMaxHp() / 4.000000f && i6 < 10 && i_ai0 == 0 && flee_x != 0 && flee_y != 0 && flee_z != 0)
		{
			i5 = AiUtils.Rand(100);
			if (i5 < 7)
			{
				Say(AiUtils.MakeFString(1000007, "", "", "", "", ""));
			}
			else if (i5 < 14)
			{
				Say(AiUtils.MakeFString(1000008, "", "", "", "", ""));
			}
			else if (i5 < 21)
			{
				Say(AiUtils.MakeFString(1000009, "", "", "", "", ""));
			}
			else if (i5 < 28)
			{
				Say(AiUtils.MakeFString(1000010, "", "", "", "", ""));
			}
			else if (i5 < 35)
			{
				Say(AiUtils.MakeFString(1000011, "", "", "", "", ""));
			}
			else if (i5 < 42)
			{
				Say(AiUtils.MakeFString(1000012, "", "", "", "", ""));
			}
			else if (i5 < 49)
			{
				Say(AiUtils.MakeFString(1000013, "", "", "", "", ""));
			}
			else if (i5 < 56)
			{
				Say(AiUtils.MakeFString(1000014, "", "", "", "", ""));
			}
			else if (i5 < 63)
			{
				Say(AiUtils.MakeFString(1000015, "", "", "", "", ""));
			}
			else if (i5 < 70)
			{
				Say(AiUtils.MakeFString(1000016, "", "", "", "", ""));
			}
			else if (i5 < 77)
			{
				Say(AiUtils.MakeFString(1000017, "", "", "", "", ""));
			}
			else if (i5 < 79)
			{
				Say(AiUtils.MakeFString(1000018, "", "", "", "", ""));
			}
			else if (i5 < 81)
			{
				Say(AiUtils.MakeFString(1000019, "", "", "", "", ""));
			}
			else if (i5 < 83)
			{
				Say(AiUtils.MakeFString(1000020, "", "", "", "", ""));
			}
			else if (i5 < 85)
			{
				Say(AiUtils.MakeFString(1000021, "", "", "", "", ""));
			}
			else if (i5 < 87)
			{
				Say(AiUtils.MakeFString(1000022, "", "", "", "", ""));
			}
			else if (i5 < 89)
			{
				Say(AiUtils.MakeFString(1000023, "", "", "", "", ""));
			}
			else if (i5 < 91)
			{
				Say(AiUtils.MakeFString(1000024, "", "", "", "", ""));
			}
			else if (i5 < 93)
			{
				Say(AiUtils.MakeFString(1000025, "", "", "", "", ""));
			}
			else if (i5 < 95)
			{
				Say(AiUtils.MakeFString(1000026, "", "", "", "", ""));
			}
			else
			{
				Say(AiUtils.MakeFString(1000027, "", "", "", "", ""));
			}
			AddMoveToDesire(flee_x, flee_y, flee_z, 100000000);
			i_ai0 = 1;
			c_ai0 = attacker;
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{

	}

	@Override
	protected void onEvtMoveFinished(int x, int y, int z)
	{

		if (i_ai0 == 1)
		{
			if ((x == flee_x && y == flee_y && z == flee_z))
			{
				AddTimerEx(2001, 15000);
				AddMoveToDesire(flee_x, flee_y, flee_z, -100000000);
				AddMoveAroundDesire(100, 50);
				i_ai0 = 2;
				BroadcastScriptEvent(ScriptEvent.SCE_WATERING_QUIZ_NUMBER, AiUtils.GetIndexFromCreature(c_ai0), 400);
			}
			else
			{
				AddMoveToDesire(flee_x, flee_y, flee_z, 100000000);
			}
		}
		if (i_ai0 == 3)
		{
			if ((x == start_x && y == start_y && z == start_z))
			{
				AddMoveAroundDesire(100, 1000);
				i_ai0 = 0;
			}
		}
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		final NpcInstance actor = getActor();

		if (timer_id == 2001)
		{
			if (actor.currentHp / actor.getMaxHp() * 100.000000f > 70.000000f && i_ai0 == 2 && p_state != State.ATTACK)
			{
				AddMoveAroundDesire(100, -1000);
				AddMoveToDesire(start_x, start_y, start_z, 1000000);
				i_ai0 = 3;
			}
			else if (p_state != State.ATTACK)
			{
				RemoveAllAttackDesire();
				AddMoveToDesire(start_x, start_y, start_z, 50);
				i_ai0 = 0;
			}
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		Creature c0 = null;
		final NpcInstance actor = getActor();

		if (!actor.isAlive())
		{
			return;
		}
		if ((script_event_arg1 == ScriptEvent.SCE_WATERING_QUIZ_NUMBER && p_state != State.ATTACK))
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg2);
			if (!IsNullCreature(c0))
			{
				AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 1000000);
				i_ai0 = 3;
			}
		}
	}

}