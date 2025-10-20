package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_heretic2 extends party_leader_physicalspecial2
{
	public int SpecialAttack = 0;
	public int SelfMagicHeal = 458752001;
	public int HelpCastRange = 500;
	public int DistNoDesire = 500;

	public party_leader_heretic2(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai3 = 0;
		super.onEvtSpawn();
		AddMoveToDesire(start_x, start_y, start_z, 30);
	}

	@Override
	protected void onEvtNoDesire()
	{

		AddMoveToDesire(start_x, start_y, start_z, 30);
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		int i0 = 0;
		float f0 = 0;
		final NpcInstance actor = getActor();

		if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
			if (creature != actor)
			{
				if (Skill_GetConsumeMP(SpecialAttack) < actor._currentMp && Skill_GetConsumeHP(SpecialAttack) < actor.currentHp && Skill_InReuseDelay(SpecialAttack) == 0)
				{
					AddUseSkillDesire(creature, SpecialAttack, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				if (Skill_GetConsumeMP(SelfMagicHeal) < actor._currentMp && Skill_GetConsumeHP(SelfMagicHeal) < actor.currentHp && Skill_InReuseDelay(SelfMagicHeal) == 0)
				{
					AddUseSkillDesire(actor, SelfMagicHeal, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			return;
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		final NpcInstance actor = getActor();

		if ((p_state != State.ATTACK && p_state != State.USE_SKILL))
		{
			BroadcastScriptEventEx(ScriptEvent.SCE_PROTECT_LEADER, AiUtils.GetIndexFromCreature(attacker), AiUtils.GetIndexFromCreature(actor), HelpCastRange);
		}
		if (actor.currentHp < actor.getMaxHp() * 0.400000f && AiUtils.Rand(100) < 33)
		{
			if (AiUtils.Rand(100) < 33 && (attacker.getClassId() == 5 || attacker.getClassId() == 90 || IsInCategory(Category.cleric_group, attacker.getClassId())))
			{
				BroadcastScriptEventEx(ScriptEvent.SCE_VALAKAS_ATTACK, AiUtils.GetIndexFromCreature(attacker), AiUtils.GetIndexFromCreature(actor), HelpCastRange);
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		float f0 = 0;
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (actor.currentHp < actor.getMaxHp() * 0.400000f && AiUtils.Rand(100) < 33)
		{
			if (i_ai3 == 0)
			{
				LookNeighbor(300);
			}
		}
		super.onEvtPartyAttacked(attacker, privat, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		if ((skill_name_id == SelfMagicHeal && success))
		{
			i_ai3 = 1;
		}
	}

	@Override
	protected void onEvtSeeSpell(int skill_name_id, Creature speller, Creature target)
	{
		float f0 = 0;
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (speller.isPlayer() && !IsInCategory(Category.summon_npc_group, speller.getClassId()))
		{
			if (actor.currentHp < actor.getMaxHp() * 0.400000f && AiUtils.Rand(100) < 33)
			{
				if (IsInCategory(Category.cleric_group, speller.getClassId()))
				{
					BroadcastScriptEventEx(ScriptEvent.SCE_VALAKAS_ATTACK, AiUtils.GetIndexFromCreature(speller), AiUtils.GetIndexFromCreature(actor), HelpCastRange);
				}
			}
		}
		super.onEvtSeeSpell(skill_name_id, speller, target);
	}

	@Override
	protected void onEvtOutOfTerritory()
	{
		final NpcInstance actor = getActor();

		RemoveAllAttackDesire();
		AddMoveToDesire(start_x, start_y, start_z, 100);
		BroadcastScriptEventEx(ScriptEvent.SCE_STOP_ATTACK, 0, AiUtils.GetIndexFromCreature(actor), DistNoDesire);
	}

}