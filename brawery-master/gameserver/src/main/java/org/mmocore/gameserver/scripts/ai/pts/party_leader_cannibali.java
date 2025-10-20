package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_cannibali extends party_leader
{
	public int PhysicalSpecial1 = 458752001;
	public int EffectSkill2 = 458752001;
	public int EffectSkill = 458752001;
	public int SummonMagic = 458752001;
	public int buff = 458752001;

	public party_leader_cannibali(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		i_ai0 = 0;
		i_ai3 = 0;
		c_ai0 = actor;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		NpcInstance privat = null;
		int i6 = 0;
		final NpcInstance actor = getActor();

		c_ai0 = attacker;
		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (damage == 0)
			{
				damage = 1;
			}
			AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, (1.000000f * damage / (actor.getLevel() + 7)) * 1000);
		}
		if (actor.currentHp < actor.getMaxHp() * 0.500000f && i_ai1 == 0)
		{
			if (Skill_GetConsumeMP(buff) < actor._currentMp && Skill_GetConsumeHP(buff) < actor.currentHp && Skill_InReuseDelay(buff) == 0)
			{
				AddUseSkillDesire(actor, buff, DesireType.HEAL, DesireMove.STAND, 1000000);
			}
			i_ai1 = 1;
		}
		if (AiUtils.Rand(100) < 33)
		{
			if (Skill_GetConsumeMP(PhysicalSpecial1) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial1) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial1) == 0)
			{
				AddUseSkillDesire(attacker, PhysicalSpecial1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		if (DistFromMe(attacker) > 300 && DistFromMe(attacker) < 1000 && AiUtils.Rand(100) < 10)
		{
			if (Skill_GetConsumeMP(SummonMagic) < actor._currentMp && Skill_GetConsumeHP(SummonMagic) < actor.currentHp && Skill_InReuseDelay(SummonMagic) == 0)
			{
				AddUseSkillDesire(attacker, SummonMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (privat != actor)
		{
			if (privat.currentHp < privat.getMaxHp() / 3)
			{
				i_ai3 = 1;
				c_ai1 = privat;
			}
		}
		if (privat == actor)
		{
			if (actor.currentHp < actor.getMaxHp() * 0.700000f && i_ai3 == 1 && i_ai0 == 0)
			{
				i_ai0 = 1;
				if (Skill_GetConsumeMP(EffectSkill) < actor._currentMp && Skill_GetConsumeHP(EffectSkill) < actor.currentHp && Skill_InReuseDelay(EffectSkill) == 0)
				{
					AddUseSkillDesire(c_ai1, EffectSkill, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			if (actor.currentHp < actor.getMaxHp() / 3 && privat.isAlive() && i_ai0 == 0)
			{
				i_ai0 = 1;
				if (Skill_GetConsumeMP(EffectSkill2) < actor._currentMp && Skill_GetConsumeHP(EffectSkill2) < actor.currentHp && Skill_InReuseDelay(EffectSkill2) == 0)
				{
					AddUseSkillDesire(actor, EffectSkill2, DesireType.HEAL, DesireMove.STAND, 1000000);
				}
			}
		}
		super.onEvtPartyAttacked(attacker, privat, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		final NpcInstance actor = getActor();

		if (skill_name_id == SummonMagic && !IsNullCreature(c_ai0) && DistFromMe(c_ai0) < 1000 && AiUtils.Rand(100) < 10)
		{
			InstantTeleport(c_ai0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()));
			AddAttackDesire(c_ai0, DesireMove.MOVE_TO_TARGET, 100000);
		}
		if (skill_name_id == EffectSkill)
		{
			CreateOnePrivateEx(22626, "warrior_ag_casting_3skill_approach", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(c_ai0), GetLifeTime());
			Despawn();
			BroadcastScriptEvent(ScriptEvent.SCE_KILL_PRIVATE, AiUtils.GetIndexFromCreature(actor), 500);
		}
		if (skill_name_id == EffectSkill2)
		{
			BroadcastScriptEvent(ScriptEvent.SCE_KILL_PRIVATE, AiUtils.GetIndexFromCreature(actor), 500);
		}
	}

}