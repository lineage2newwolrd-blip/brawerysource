package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_wizard_heretic extends party_leader_wizard_dd2_curse
{
	public int SpecialAttack = 458752001;
	public int SelfMagicHeal = 458752001;
	public int HelpCastRange = 500;
	public int DistNoDesire = 500;
	public int SpecialDDMagic = 0;
	public int SpecialDDMagicUse = 0;

	public party_leader_wizard_heretic(final NpcInstance actor){super(actor);}

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
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i6 = 0;
		int i0 = 0;
		final NpcInstance actor = getActor();

		if ((p_state != State.ATTACK && p_state != State.USE_SKILL))
		{
			BroadcastScriptEventEx(ScriptEvent.SCE_PROTECT_LEADER, AiUtils.GetIndexFromCreature(attacker), AiUtils.GetIndexFromCreature(actor), HelpCastRange);
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
	protected void onEvtSeeCreature(Creature creature)
	{
		int i0 = 0;
		float f0 = 0;
		final NpcInstance actor = getActor();

		if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
			if (creature == actor)
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
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		final NpcInstance actor = getActor();

		if (skill_name_id == SelfMagicHeal && actor.currentHp > actor.getMaxHp() * 0.400000f)
		{
			i_ai3 = 1;
		}
		super.onEvtUseSkillFinished(skill_name_id, target, success);
	}

	@Override
	protected void onEvtOutOfTerritory()
	{
		final NpcInstance actor = getActor();

		RemoveAllAttackDesire();
		AddMoveToDesire(start_x, start_y, start_z, 100);
		RemoveAllHateInfoIF(0, 0);
		BroadcastScriptEventEx(ScriptEvent.SCE_STOP_ATTACK, 0, AiUtils.GetIndexFromCreature(actor), DistNoDesire);
	}

}