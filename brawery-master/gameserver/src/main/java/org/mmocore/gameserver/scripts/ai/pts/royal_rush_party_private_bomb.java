package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class royal_rush_party_private_bomb extends party_private
{
	public int PhysicalSpecial1 = 262209537;
	public int MagicHeal = 266403841;
	public int Bomb = 266403841;

	public royal_rush_party_private_bomb(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		top_desire_target = getTopHated();
		final NpcInstance boss = getActor().getLeader();
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (!IsNullCreature(top_desire_target))
			{
				if (top_desire_target == attacker)
				{
					if (AiUtils.Rand(100) < 33)
					{
						if (Skill_GetConsumeMP(PhysicalSpecial1) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial1) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial1) == 0)
						{
							AddUseSkillDesire(attacker, PhysicalSpecial1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					if (AiUtils.Rand(100) < 33 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) < 40)
					{
						if (IsMyBossAlive())
						{
							if (Skill_GetConsumeMP(MagicHeal) < actor._currentMp && Skill_GetConsumeHP(MagicHeal) < actor.currentHp && Skill_InReuseDelay(MagicHeal) == 0)
							{
								AddUseSkillDesire(boss, MagicHeal, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
							}
							if (Skill_GetConsumeMP(Bomb) < actor._currentMp && Skill_GetConsumeHP(Bomb) < actor.currentHp && Skill_InReuseDelay(Bomb) == 0)
							{
								AddUseSkillDesire(actor, Bomb, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
							}
						}
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance boss = getActor().getLeader();
		final NpcInstance actor = getActor();

		if (AiUtils.Rand(100) < 33 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) < 40)
		{
			if (IsMyBossAlive())
			{
				if (Skill_GetConsumeMP(MagicHeal) < actor._currentMp && Skill_GetConsumeHP(MagicHeal) < actor.currentHp && Skill_InReuseDelay(MagicHeal) == 0)
				{
					AddUseSkillDesire(boss, MagicHeal, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				if (Skill_GetConsumeMP(Bomb) < actor._currentMp && Skill_GetConsumeHP(Bomb) < actor.currentHp && Skill_InReuseDelay(Bomb) == 0)
				{
					AddUseSkillDesire(actor, Bomb, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtPartyAttacked(attacker, privat, damage, skill_name_id, skill_id);
	}

}