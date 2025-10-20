package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_heretic extends party_leader_physicalspecial2
{
	public int HelpCastRange = 500;
	public int DistNoDesire = 500;

	public party_leader_heretic(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai3 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
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

		super.onEvtPartyAttacked(attacker, privat, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		int i0 = 0;
		float f0 = 0;

		if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
	}

	@Override
	protected void onEvtOutOfTerritory()
	{
		final NpcInstance actor = getActor();

		RemoveAllAttackDesire();
		AddMoveToDesire(start_x, start_y, start_z, 100);
		BroadcastScriptEventEx(ScriptEvent.SCE_STOP_ATTACK, 0, AiUtils.GetIndexFromCreature(actor), DistNoDesire);
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		final NpcInstance actor = getActor();

		BroadcastScriptEventEx(2, 0, AiUtils.GetIndexFromCreature(actor), 4000);
	}

}