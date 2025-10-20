package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard_ddmagic2_solina extends wizard_ddmagic2_heal
{

	public wizard_ddmagic2_solina(final NpcInstance actor)
	{
		super(actor);
		W_ShortRangeDDMagic = 458752001;
		MagicHeal = 458752001;
		W_LongRangeDDMagic = 458752001;
	}

	@Override
	protected void onEvtSpawn()
	{

		i_ai3 = 0;
		i_ai4 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtNoDesire()
	{

		i_ai3 = 0;
		i_ai4 = 0;
		super.onEvtNoDesire();
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		int i6 = 0;
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (creature.isPlayer())
		{
			if (creature.getClassId() == 30 || creature.getClassId() == 16 || creature.getClassId() == 5 || creature.getClassId() == 90 || creature.getClassId() == 105 || creature.getClassId() == 97)
			{
				if (i_ai3 == 0)
				{
					if (AiUtils.Rand(100) < 50)
					{
						Say(AiUtils.MakeFString(10075, creature.getName(), "", "", "", ""));
					}
					else
					{
						Say(AiUtils.MakeFString(10076, creature.getName(), "", "", "", ""));
					}
					i_ai3 = 1;
				}
			}
			else if (DistFromMe(creature) > 100 && AiUtils.Rand(100) < 80)
			{
				if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
					{
						AddUseSkillDesire(creature, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(creature, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
			else if (Skill_GetConsumeMP(W_ShortRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_ShortRangeDDMagic) < actor.currentHp)
			{
				if (Skill_InReuseDelay(W_ShortRangeDDMagic) == 0)
				{
					AddUseSkillDesire(creature, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				else
				{
					AddUseSkillDesire(creature, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else
			{
				i_ai0 = 1;
				AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 1000);
			}
			if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
			{
				return;
			}
			i0 = 0;
			if (SeeCreatureAttackerTime == -1)
			{
				if (SetAggressiveTime == -1)
				{
					if (GetLifeTime() >= AiUtils.Rand(5) + 3 && InMyTerritory(actor))
					{
						i0 = 1;
					}
				}
				else if (SetAggressiveTime == 0)
				{
					if (InMyTerritory(actor))
					{
						i0 = 1;
					}
				}
				else
				{
					if (GetLifeTime() > SetAggressiveTime + AiUtils.Rand(4) && InMyTerritory(actor))
					{
						i0 = 1;
					}
				}
			}
			else
			{
				if (GetLifeTime() > SeeCreatureAttackerTime && InMyTerritory(actor))
				{
					i0 = 1;
				}
			}
			if (GetHateInfoCount() == 0 && i0 == 1)
			{
				AddHateInfo(creature, 300, 0, 1, 1);
			}
			else
			{
				AddHateInfo(creature, 100, 0, 1, 1);
			}
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		Creature c0 = null;
		int i6 = 0;
		int i0 = 0;

		if (attacker.isPlayer())
		{
			if (attacker.getClassId() == 30 || attacker.getClassId() == 16 || attacker.getClassId() == 5 || attacker.getClassId() == 90 || attacker.getClassId() == 105 || attacker.getClassId() == 97)
			{
				if (i_ai4 == 0)
				{
					if (AiUtils.Rand(100) < 50)
					{
						Say(AiUtils.MakeFString(10077, attacker.getName(), "", "", "", ""));
					}
					else
					{
						Say(AiUtils.MakeFString(10078, attacker.getName(), "", "", "", ""));
					}
					i_ai4 = 1;
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}