package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;

public class quest_party_leader extends party_leader
{
	public quest_party_leader(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtNoDesire()
	{

	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{

	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{

	}

	@Override
	protected void onEvtSeeSpell(int skill_name_id, Creature speller, Creature target)
	{

	}

	@Override
	protected void onEvtDesireManipulation(Creature speller, int desire)
	{

	}

}