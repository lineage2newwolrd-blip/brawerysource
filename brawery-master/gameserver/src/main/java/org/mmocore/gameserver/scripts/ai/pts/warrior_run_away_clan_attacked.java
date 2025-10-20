package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_run_away_clan_attacked extends warrior
{

	public warrior_run_away_clan_attacked(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		int i6 = 0;
		int i5 = 0;
		float f0 = 0;
		final NpcInstance actor = getActor();

		if ((p_state != State.ATTACK && actor.currentHp > 0))
		{
			i5 = AiUtils.Rand(100);
			if (i5 < 50)
			{
				Say(AiUtils.MakeFString(1000009, "", "", "", "", ""));
			}
			else if (i5 < 75)
			{
				Say(AiUtils.MakeFString(1000010, "", "", "", "", ""));
			}
			else
			{
				Say(AiUtils.MakeFString(1000011, "", "", "", "", ""));
			}
			AddFleeDesire(attacker, 30);
		}
		else if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			f0 = 0;
			if (SetHateGroup >= 0)
			{
				if (IsInCategory(SetHateGroup, attacker.getClassId()))
				{
					f0 = f0 + SetHateGroupRatio;
				}
			}
			if (attacker.getClassId() == SetHateOccupation)
			{
				f0 = f0 + SetHateOccupationRatio;
			}
			if (SetHateRace == attacker.getRaceId())
			{
				f0 = f0 + SetHateRaceRatio;
			}
			f0 = 1.000000f * damage / (actor.getLevel() + 7) + (f0 / 100 * (1.000000f * damage / (actor.getLevel() + 7)));
			AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, f0 * 30);
		}
	}

}