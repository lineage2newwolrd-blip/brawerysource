package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_casting_enchant_physical_poison extends warrior
{
	public int PhysicalSpecial = 264241164;
	public int Buff1 = 273481729;
	public int Buff2 = 273612801;
	public int DeBuff1 = 298254337;
	public int DeBuff2 = 298319873;

	public warrior_casting_enchant_physical_poison(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		if (Skill_GetConsumeMP(Buff1) < actor._currentMp && Skill_GetConsumeHP(Buff1) < actor.currentHp && Skill_InReuseDelay(Buff1) == 0)
		{
			AddUseSkillDesire(actor, Buff1, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
		}
		if (Skill_GetConsumeMP(Buff2) < actor._currentMp && Skill_GetConsumeHP(Buff2) < actor.currentHp && Skill_InReuseDelay(Buff2) == 0)
		{
			AddUseSkillDesire(actor, Buff2, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i1 = 0;
		final NpcInstance actor = getActor();

		i1 = 0;
		if (AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(Buff1)) <= 0)
		{
			if (Skill_GetConsumeMP(Buff1) < actor._currentMp && Skill_GetConsumeHP(Buff1) < actor.currentHp && Skill_InReuseDelay(Buff1) == 0)
			{
				AddUseSkillDesire(actor, Buff1, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		if (AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(Buff2)) <= 0)
		{
			if (Skill_GetConsumeMP(Buff2) < actor._currentMp && Skill_GetConsumeHP(Buff2) < actor.currentHp && Skill_InReuseDelay(Buff2) == 0)
			{
				AddUseSkillDesire(actor, Buff2, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		if (AiUtils.Rand(100) < 33)
		{
			if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
			{
				AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		if (AiUtils.Rand(100) < 10)
		{
			i1 = AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(DeBuff1));
			if (i1 == -1)
			{
				if (Skill_GetConsumeMP(DeBuff1) < actor._currentMp && Skill_GetConsumeHP(DeBuff1) < actor.currentHp && Skill_InReuseDelay(DeBuff1) == 0)
				{
					AddUseSkillDesire(attacker, DeBuff1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else if (i1 == 10)
			{
			}
			else
			{
				i1 = DeBuff1 + i1;
				if (Skill_GetConsumeMP(i1) < actor._currentMp && Skill_GetConsumeHP(i1) < actor.currentHp && Skill_InReuseDelay(i1) == 0)
				{
					AddUseSkillDesire(attacker, i1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		if (AiUtils.Rand(100) < 10)
		{
			i1 = AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(DeBuff2));
			if (i1 == -1)
			{
				if (Skill_GetConsumeMP(DeBuff2) < actor._currentMp && Skill_GetConsumeHP(DeBuff2) < actor.currentHp && Skill_InReuseDelay(DeBuff2) == 0)
				{
					AddUseSkillDesire(attacker, DeBuff2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else if (i1 == 10)
			{
			}
			else
			{
				i1 = DeBuff2 + i1;
				if (Skill_GetConsumeMP(i1) < actor._currentMp && Skill_GetConsumeHP(i1) < actor.currentHp && Skill_InReuseDelay(i1) == 0)
				{
					AddUseSkillDesire(attacker, i1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		int i1 = 0;
		final NpcInstance actor = getActor();

		i1 = 0;
		if (AiUtils.Rand(100) < 10)
		{
			i1 = AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(DeBuff1));
			if (i1 == -1)
			{
				if (Skill_GetConsumeMP(DeBuff1) < actor._currentMp && Skill_GetConsumeHP(DeBuff1) < actor.currentHp && Skill_InReuseDelay(DeBuff1) == 0)
				{
					AddUseSkillDesire(attacker, DeBuff1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else if (i1 == 10)
			{
			}
			else
			{
				i1 = DeBuff1 + i1;
				if (Skill_GetConsumeMP(i1) < actor._currentMp && Skill_GetConsumeHP(i1) < actor.currentHp && Skill_InReuseDelay(i1) == 0)
				{
					AddUseSkillDesire(attacker, i1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		if (AiUtils.Rand(100) < 10)
		{
			i1 = AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(DeBuff2));
			if (i1 == -1)
			{
				if (Skill_GetConsumeMP(DeBuff2) < actor._currentMp && Skill_GetConsumeHP(DeBuff2) < actor.currentHp && Skill_InReuseDelay(DeBuff2) == 0)
				{
					AddUseSkillDesire(attacker, DeBuff2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else if (i1 == 10)
			{
			}
			else
			{
				i1 = DeBuff2 + i1;
				if (Skill_GetConsumeMP(i1) < actor._currentMp && Skill_GetConsumeHP(i1) < actor.currentHp && Skill_InReuseDelay(i1) == 0)
				{
					AddUseSkillDesire(attacker, i1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtSeeSpell(int skill_name_id, Creature speller, Creature target)
	{
		int i1 = 0;
		final NpcInstance actor = getActor();

		i1 = 0;
		if (AiUtils.Rand(100) < 10)
		{
			i1 = AiUtils.GetAbnormalLevel(speller, Skill_GetAbnormalType(DeBuff1));
			if (i1 == -1)
			{
				if (Skill_GetConsumeMP(DeBuff1) < actor._currentMp && Skill_GetConsumeHP(DeBuff1) < actor.currentHp && Skill_InReuseDelay(DeBuff1) == 0)
				{
					AddUseSkillDesire(speller, DeBuff1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else if (i1 == 10)
			{
			}
			else
			{
				i1 = DeBuff1 + i1;
				if (Skill_GetConsumeMP(i1) < actor._currentMp && Skill_GetConsumeHP(i1) < actor.currentHp && Skill_InReuseDelay(i1) == 0)
				{
					AddUseSkillDesire(speller, i1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		if (AiUtils.Rand(100) < 10)
		{
			i1 = AiUtils.GetAbnormalLevel(speller, Skill_GetAbnormalType(DeBuff2));
			if (i1 == -1)
			{
				if (Skill_GetConsumeMP(DeBuff2) < actor._currentMp && Skill_GetConsumeHP(DeBuff2) < actor.currentHp && Skill_InReuseDelay(DeBuff2) == 0)
				{
					AddUseSkillDesire(speller, DeBuff2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else if (i1 == 10)
			{
			}
			else
			{
				i1 = DeBuff2 + i1;
				if (Skill_GetConsumeMP(i1) < actor._currentMp && Skill_GetConsumeHP(i1) < actor.currentHp && Skill_InReuseDelay(i1) == 0)
				{
					AddUseSkillDesire(speller, i1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtSeeSpell(skill_name_id, speller, target);
	}

}