package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class party_private_cannibali extends party_private
{
	public int PhysicalSpecial1 = 458752001;
	public int PhysicalSpecial2 = 458752001;

	public party_private_cannibali(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		NpcInstance privat = null;
		int i6 = 0;
		final NpcInstance boss = getActor().getLeader();
		final NpcInstance actor = getActor();

		if (IsMyBossAlive() && DistFromMe(boss) > 200)
		{
			AddMoveToDesire(AiUtils.FloatToInt(boss.getX()), AiUtils.FloatToInt(boss.getY()), AiUtils.FloatToInt(boss.getZ()), 1000);
		}
		if (AiUtils.Rand(100) < 33)
		{
			if (Skill_GetConsumeMP(PhysicalSpecial1) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial1) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial1) == 0)
			{
				AddUseSkillDesire(attacker, PhysicalSpecial1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		if (AiUtils.Rand(100) < 33)
		{
			if (Skill_GetConsumeMP(PhysicalSpecial2) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial2) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial2) == 0)
			{
				AddUseSkillDesire(attacker, PhysicalSpecial2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		int i0 = 0;
		Creature c0 = null;

		if (script_event_arg1 == ScriptEvent.SCE_KILL_PRIVATE)
		{
			Despawn();
		}
	}

}