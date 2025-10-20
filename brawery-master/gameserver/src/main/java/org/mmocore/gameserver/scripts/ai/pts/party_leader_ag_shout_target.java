package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.maker.default_maker;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_ag_shout_target extends party_leader_aggressive
{
	public int selfbuff = 458752001;

	public party_leader_ag_shout_target(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		c_ai0 = AiUtils.GetCreatureFromIndex(0);
		c_ai1 = AiUtils.GetCreatureFromIndex(0);
		c_ai2 = AiUtils.GetCreatureFromIndex(0);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{

		if (creature.isPlayer() && IsInCategory(Category.cleric_group, creature.getClassId()))
		{
			if (c_ai0 == AiUtils.GetCreatureFromIndex(0))
			{
				c_ai0 = creature;
			}
			else if (c_ai1 == AiUtils.GetCreatureFromIndex(0))
			{
				c_ai1 = creature;
			}
			else if (c_ai2 == AiUtils.GetCreatureFromIndex(0))
			{
				c_ai2 = creature;
			}
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (AiUtils.Rand(100) < 5)
		{
			if (Skill_GetConsumeMP(selfbuff) < actor._currentMp && Skill_GetConsumeHP(selfbuff) < actor.currentHp && Skill_InReuseDelay(selfbuff) == 0)
			{
				AddUseSkillDesire(actor, selfbuff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		if (AiUtils.Rand(100) < 3)
		{
			if (top_desire_target == attacker)
			{
				Say(AiUtils.MakeFString(1000399, attacker.getName(), "", "", "", ""));
			}
			RemoveAllAttackDesire();
			AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000000);
			actor.flag = AiUtils.GetIndexFromCreature(attacker);
			BroadcastScriptEvent(ScriptEvent.SCE_VALAKAS_ATTACK, AiUtils.GetIndexFromCreature(actor), 2000);
		}
		if (actor.currentHp < actor.getMaxHp() * 0.800000f)
		{
			BroadcastScriptEvent(ScriptEvent.SCE_PRIVATE_HEAL, 0, 2000);
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtPartyDead(Creature attacker, NpcInstance privat)
	{
		final NpcInstance actor = getActor();

		if (actor != privat)
		{
			LookNeighbor(1000);
			if (!IsNullCreature(c_ai0))
			{
				if ((c_ai0.currentHp > 1 && c_ai0 != actor))
				{
					Say(AiUtils.MakeFString(1000399, c_ai0.getName(), "", "", "", ""));
					RemoveAllAttackDesire();
					AddAttackDesire(c_ai0, DesireMove.MOVE_TO_TARGET, 1000000);
					actor.flag = AiUtils.GetIndexFromCreature(c_ai0);
					BroadcastScriptEvent(ScriptEvent.SCE_VALAKAS_ATTACK, AiUtils.GetIndexFromCreature(actor), 500);
				}
			}
			if (!IsNullCreature(c_ai1))
			{
				if ((c_ai1.currentHp > 1 && c_ai1 != actor))
				{
					Say(AiUtils.MakeFString(1000399, c_ai1.getName(), "", "", "", ""));
					RemoveAllAttackDesire();
					AddAttackDesire(c_ai1, DesireMove.MOVE_TO_TARGET, 1000000);
					actor.flag = AiUtils.GetIndexFromCreature(c_ai1);
					BroadcastScriptEvent(ScriptEvent.SCE_VALAKAS_ATTACK, AiUtils.GetIndexFromCreature(actor), 500);
				}
			}
			if (!IsNullCreature(c_ai2))
			{
				if ((c_ai2.currentHp > 1 && c_ai2 != actor))
				{
					Say(AiUtils.MakeFString(1000399, c_ai2.getName(), "", "", "", ""));
					RemoveAllAttackDesire();
					AddAttackDesire(c_ai2, DesireMove.MOVE_TO_TARGET, 1000000);
					actor.flag = AiUtils.GetIndexFromCreature(c_ai2);
					BroadcastScriptEvent(ScriptEvent.SCE_VALAKAS_ATTACK, AiUtils.GetIndexFromCreature(actor), 500);
				}
			}
		}
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
		BroadcastScriptEvent(ScriptEvent.SCE_ICE_PRIVATE_DIED, 2, 7000);
	}

}