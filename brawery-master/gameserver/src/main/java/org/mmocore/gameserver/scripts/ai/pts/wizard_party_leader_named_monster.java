package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.maker.default_maker;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard_party_leader_named_monster extends party_leader_wizard
{
	public int W_SelfRangeDDMagic = 458752001;
	public int W_LongRangeDDMagic = 458752001;
	public int W_ShortRangeDDMagic = 458752001;
	public String maker_name = "innadril22_2422_024m3";
	public String DoorName = "cyratal_cave_a_e";

	public wizard_party_leader_named_monster(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		BroadcastScriptEvent(ScriptEvent.SCE_NAMED_FOR_AL_SPAWN, 0, 4000);
		CreateOnePrivateEx(22291, "wizard_use_skill", 0, 0, AiUtils.FloatToInt(actor.getX() + 20), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
		CreateOnePrivateEx(22291, "wizard_use_skill", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY() + 20), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		if (DistFromMe(attacker) > 200)
		{
			if (AiUtils.Rand(100) < 33)
			{
				AddUseSkillDesire(attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		else if (AiUtils.Rand(100) < 33)
		{
			AddUseSkillDesire(attacker, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
		}
		else if (AiUtils.Rand(100) < 33)
		{
			AddUseSkillDesire(attacker, W_SelfRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		int i6 = 0;
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
			super.onEvtSeeCreature(creature);
			return;
		}
		if (GetHateInfoCount() == 0)
		{
			if (DistFromMe(creature) > 200)
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
			super.onEvtSeeCreature(creature);
		}
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (i_ai0 == 0)
		{
			h0 = GetMaxHateInfo(0);
			i0 = 0;
			if (!IsNullHateInfo(h0))
			{
				if (!IsNullCreature(h0.attacker))
				{
					i0 = 1;
				}
			}
			if (i0 == 1)
			{
				if (DistFromMe(h0.attacker) > 200)
				{
					if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp)
					{
						if (Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
						{
							AddUseSkillDesire(h0.attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						else
						{
							AddUseSkillDesire(h0.attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					else
					{
						i_ai0 = 1;
						AddAttackDesire(h0.attacker, DesireMove.MOVE_TO_TARGET, 1000);
					}
				}
				else if (Skill_GetConsumeMP(W_ShortRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_ShortRangeDDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_ShortRangeDDMagic) == 0)
					{
						AddUseSkillDesire(h0.attacker, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(h0.attacker, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(h0.attacker, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
		}
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		int i0 = 0;
		default_maker maker0 = null;

		i0 = InstantZone_GetId();
		maker0 = AiUtils.InstantZone_GetNpcMaker(i0, maker_name);
		if (!AiUtils.IsNull(maker0))
		{
			AiUtils.SendMakerScriptEvent(maker0, 12546, 0, 0);
		}
		super.onEvtDead(attacker);
	}

}