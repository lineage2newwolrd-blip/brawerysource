package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard_healer extends wizard_ddmagic2
{
	public int Heal = 66519055;
	public int Buff = 263979009;
	public int DeBuff = 265748488;

	public wizard_healer(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		if (Skill_GetConsumeMP(Buff) < actor._currentMp && Skill_GetConsumeHP(Buff) < actor.currentHp && Skill_InReuseDelay(Buff) == 0)
		{
			AddUseSkillDesire(actor, Buff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
		}
		i_ai1 = 0;
		i_ai2 = 0;
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		i0 = AiUtils.Rand(100);
		if (i0 < 33 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) > 50)
		{
			if (i_ai2 == 0)
			{
				AddFleeDesireEx(attacker, 500, 1000000);
				if (GetPathfindFailCount() > 3 && attacker == top_desire_target && AiUtils.FloatToInt(actor.currentHp) != AiUtils.FloatToInt(actor.getMaxHp()))
				{
					i_ai2 = 1;
				}
			}
			else
			{
				super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
			}
		}
		else
		{
			super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
		}
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		i0 = AiUtils.Rand(100);
		if (victim.currentHp > victim.getMaxHp() / 2)
		{
			if (i0 < 33)
			{
				if (Skill_GetConsumeMP(Buff) < actor._currentMp && Skill_GetConsumeHP(Buff) < actor.currentHp && Skill_InReuseDelay(Buff) == 0)
				{
					AddUseSkillDesire(victim, Buff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				if (Skill_GetConsumeMP(DeBuff) < actor._currentMp && Skill_GetConsumeHP(DeBuff) < actor.currentHp && Skill_InReuseDelay(DeBuff) == 0)
				{
					AddUseSkillDesire(attacker, DeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		else if (i0 < 50)
		{
			if (Skill_GetConsumeMP(Heal) < actor._currentMp && Skill_GetConsumeHP(Heal) < actor.currentHp && Skill_InReuseDelay(Heal) == 0)
			{
				AddUseSkillDesire(victim, Heal, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtClanDead(Creature attacker, Creature victim)
	{
		AddFleeDesireEx(attacker, 500, 1000000);
	}

}