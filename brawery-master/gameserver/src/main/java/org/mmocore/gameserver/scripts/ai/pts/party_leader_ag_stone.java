package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.maker.default_maker;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_ag_stone extends party_leader
{
	public int RangeHold_a = 458752001;
	public int DeBuff = 458752001;

	public party_leader_ag_stone(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai1 = 0;
		c_ai0 = AiUtils.GetNullCreature();
		c_ai1 = AiUtils.GetNullCreature();
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		int i1 = 0;
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (i_ai1 < 3 && AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(DeBuff)) == -1)
			{
				if (i_ai1 == 0)
				{
					c_ai0 = attacker;
					if (Skill_GetConsumeMP(DeBuff) < actor._currentMp && Skill_GetConsumeHP(DeBuff) < actor.currentHp && Skill_InReuseDelay(DeBuff) == 0)
					{
						AddUseSkillDesire(c_ai0, DeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					i_ai1 = i_ai1 + 1;
				}
				else if ((i_ai1 == 1 && c_ai0 != attacker))
				{
					c_ai1 = attacker;
					if (Skill_GetConsumeMP(DeBuff) < actor._currentMp && Skill_GetConsumeHP(DeBuff) < actor.currentHp && Skill_InReuseDelay(DeBuff) == 0)
					{
						AddUseSkillDesire(c_ai1, DeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					i_ai1 = i_ai1 + 1;
				}
				else if ((i_ai1 == 2 && c_ai0 != attacker && c_ai1 != attacker))
				{
					if (Skill_GetConsumeMP(DeBuff) < actor._currentMp && Skill_GetConsumeHP(DeBuff) < actor.currentHp && Skill_InReuseDelay(DeBuff) == 0)
					{
						AddUseSkillDesire(attacker, DeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					i_ai1 = i_ai1 + 1;
				}
			}
			else if (AiUtils.Rand(100) < 20)
			{
				if (Skill_GetConsumeMP(RangeHold_a) < actor._currentMp && Skill_GetConsumeHP(RangeHold_a) < actor.currentHp && Skill_InReuseDelay(RangeHold_a) == 0)
				{
					AddUseSkillDesire(actor, RangeHold_a, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		final NpcInstance actor = getActor();

		if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
			return;
		}
		if (SeeCreatureAttackerTime == -1)
		{
			if (SetAggressiveTime == -1)
			{
				if (GetLifeTime() >= AiUtils.Rand(5) + 3 && InMyTerritory(actor))
				{
					AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 200);
				}
			}
			else if (SetAggressiveTime == 0)
			{
				if (InMyTerritory(actor))
				{
					AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 200);
				}
			}
			else
			{
				if (GetLifeTime() > SetAggressiveTime + AiUtils.Rand(4) && InMyTerritory(actor))
				{
					AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 200);
				}
			}
		}
		else
		{
			if (GetLifeTime() > SeeCreatureAttackerTime && InMyTerritory(actor))
			{
				AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 200);
			}
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		if (skill_name_id == DeBuff)
		{
			RemoveAttackDesire(target.getObjectId());
		}
		super.onEvtUseSkillFinished(skill_name_id, target, success);
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		default_maker maker0 = null;

		maker0 = AiUtils.GetNpcMaker("schuttgart13_npc2314_2m1");
		if (!AiUtils.IsNull(maker0))
		{
			AiUtils.SendMakerScriptEvent(maker0, 10005, 0, 0);
		}
	}

}