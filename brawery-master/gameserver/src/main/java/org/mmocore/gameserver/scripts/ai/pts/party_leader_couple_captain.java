package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_couple_captain extends party_leader
{
	public int SummonMagic = 458752001;
	public int silhouette = 1020130;
	public String ai_type = "warrior";

	public party_leader_couple_captain(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i6 = 0;
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			c_ai0 = attacker;
			i6 = AiUtils.Rand(100);
			if (DistFromMe(attacker) > 300 && i6 < 20)
			{
				if (Skill_GetConsumeMP(SummonMagic) < actor._currentMp && Skill_GetConsumeHP(SummonMagic) < actor.currentHp && Skill_InReuseDelay(SummonMagic) == 0)
				{
					AddUseSkillDesire(attacker, SummonMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else if (DistFromMe(attacker) > 100 && i6 < 50)
			{
				if (Skill_GetConsumeMP(SummonMagic) < actor._currentMp && Skill_GetConsumeHP(SummonMagic) < actor.currentHp && Skill_InReuseDelay(SummonMagic) == 0)
				{
					AddUseSkillDesire(attacker, SummonMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		final NpcInstance actor = getActor();

		if ((skill_name_id == SummonMagic && success))
		{
			InstantTeleport(c_ai0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()));
			AddAttackDesire(c_ai0, DesireMove.MOVE_TO_TARGET, 100000);
		}
		super.onEvtUseSkillFinished(skill_name_id, target, success);
	}

	@Override
	protected void onEvtPartyDead(Creature attacker, NpcInstance privat)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (actor != privat)
		{
			if (actor.currentHp > actor.getMaxHp() * 0.700000f)
			{
				CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(top_desire_target), GetLifeTime());
				Despawn();
			}
		}
	}

}