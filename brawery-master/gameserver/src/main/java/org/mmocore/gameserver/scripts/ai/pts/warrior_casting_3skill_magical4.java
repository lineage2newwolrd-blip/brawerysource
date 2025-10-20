package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_casting_3skill_magical4 extends warrior
{
	public int MagicHeal = 264568833;
	public int Buff = 263979009;
	public int CancelMagic = 268304385;
	public int CheckMagic = 458752001;
	public int CheckMagic1 = 458752001;
	public int CheckMagic2 = 458752001;

	public warrior_casting_3skill_magical4(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
		i_ai1 = 0;
		i_ai2 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		i0 = AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100);
		if (AiUtils.Rand(100) < 33 && i0 < 70)
		{
			if (Skill_GetConsumeMP(MagicHeal) < actor._currentMp && Skill_GetConsumeHP(MagicHeal) < actor.currentHp && Skill_InReuseDelay(MagicHeal) == 0)
			{
				AddUseSkillDesire(actor, MagicHeal, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (i_ai1 == 0 && AiUtils.Rand(100) < 33 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) > 50)
			{
				if (Skill_GetConsumeMP(Buff) < actor._currentMp && Skill_GetConsumeHP(Buff) < actor.currentHp && Skill_InReuseDelay(Buff) == 0)
				{
					AddUseSkillDesire(actor, Buff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			if (AiUtils.Rand(100) < 1 && i_ai2 == 0 && actor.currentHp > actor.getMaxHp() * 0.400000f)
			{
				if (!IsNullCreature(top_desire_target))
				{
					i_ai2 = 1;
				}
			}
			i_ai1 = 1;
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (AiUtils.Rand(100) < 33 && p_state != State.ATTACK)
		{
			if (Skill_GetConsumeMP(MagicHeal) < actor._currentMp && Skill_GetConsumeHP(MagicHeal) < actor.currentHp && Skill_InReuseDelay(MagicHeal) == 0)
			{
				AddUseSkillDesire(victim, MagicHeal, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (AiUtils.Rand(100) < 1 && i_ai2 == 0 && actor.currentHp > actor.getMaxHp() * 0.400000f)
			{
				i_ai2 = 1;
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

}