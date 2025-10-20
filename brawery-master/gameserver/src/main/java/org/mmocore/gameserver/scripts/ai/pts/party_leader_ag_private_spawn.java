package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.maker.default_maker;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_ag_private_spawn extends party_leader_aggressive
{
	public party_leader_ag_private_spawn(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai1 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		float f0 = 0;
		final NpcInstance actor = getActor();

		f0 = 0.000000f;
		if (privat != actor && privat.currentHp < privat.getMaxHp() / 3 && actor.currentHp > 1 && i_ai1 < 15)
		{
			CreateOnePrivateEx(18327, "party_private_ag_private_spawn", 0, 0, AiUtils.FloatToInt(privat.getX()), AiUtils.FloatToInt(privat.getY()), AiUtils.FloatToInt(privat.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(attacker), 0);
			CreateOnePrivateEx(18327, "party_private_ag_private_spawn", 0, 0, AiUtils.FloatToInt(privat.getX()), AiUtils.FloatToInt(privat.getY()), AiUtils.FloatToInt(privat.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(attacker), 0);
			AiUtils.SendScriptEvent(privat, ScriptEvent.SCE_SUICIDE, 0);
			i_ai1 = i_ai1 + 1;
		}
		super.onEvtPartyAttacked(attacker, privat, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		final NpcInstance actor = getActor();

		if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
			return;
		}
		if (GetLifeTime() > SeeCreatureAttackerTime && InMyTerritory(actor))
		{
			AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 200);
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtPartyDead(Creature attacker, NpcInstance privat)
	{

	}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		default_maker maker0 = null;

		maker0 = AiUtils.GetNpcMaker("schuttgart13_mb2314_05m1");
		if (!AiUtils.IsNull(maker0))
		{
			AiUtils.SendMakerScriptEvent(maker0, 10005, 0, 0);
		}
		BroadcastScriptEvent(ScriptEvent.SCE_ICE_PRIVATE_DIED, 1, 8000);
	}

}