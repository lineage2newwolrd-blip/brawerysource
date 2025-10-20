package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_casting_3skill_magical extends warrior
{
	public int SleepMagic = 265158657;
	public int DDMagic = 262209537;
	public int CancelMagic = 268304385;
	public int CheckMagic = 458752001;
	public int CheckMagic1 = 458752001;
	public int CheckMagic2 = 458752001;

	public warrior_casting_3skill_magical(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
		i_ai1 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i6 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (IsNullCreature(top_desire_target))
			{
			}
			else if (top_desire_target != attacker)
			{
				i6 = AiUtils.Rand(100);
				if (i_ai0 == 0)
				{
					i_ai0 = 1;
				}
				else if (i_ai0 == 1 && i6 < 30 && actor.currentHp > actor.getMaxHp() / 10.000000f)
				{
					if (Skill_GetConsumeMP(SleepMagic) < actor._currentMp && Skill_GetConsumeHP(SleepMagic) < actor.currentHp && Skill_InReuseDelay(SleepMagic) == 0)
					{
						AddUseSkillDesire(attacker, SleepMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
			else if (DistFromMe(attacker) > 100)
			{
				i6 = AiUtils.Rand(100);
				if ((top_desire_target == attacker && i6 < 33))
				{
					if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp && Skill_InReuseDelay(DDMagic) == 0)
					{
						AddUseSkillDesire(attacker, DDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
			if (AiUtils.Rand(100) < 1 && i_ai1 == 0 && actor.currentHp > actor.getMaxHp() * 0.400000f)
			{
				i_ai1 = 1;
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		int i6 = 0;
		final NpcInstance actor = getActor();

		if ((GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))) && p_state != State.ATTACK)
		{
			i6 = AiUtils.Rand(100);
			if (DistFromMe(attacker) > 100 && i6 < 33)
			{
				if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp && Skill_InReuseDelay(DDMagic) == 0)
				{
					AddUseSkillDesire(attacker, DDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			if (AiUtils.Rand(100) < 1 && i_ai1 == 0 && actor.currentHp > actor.getMaxHp() * 0.400000f)
			{
				i_ai1 = 1;
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

}