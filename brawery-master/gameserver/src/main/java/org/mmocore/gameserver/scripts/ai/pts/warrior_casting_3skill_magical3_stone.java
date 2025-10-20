package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_casting_3skill_magical3_stone extends warrior_casting_3skill_magical3
{
	public int DeBuff1 = 295895041;

	public warrior_casting_3skill_magical3_stone(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		final NpcInstance actor = getActor();

		if (attacker.isPlayer())
		{
			if (AiUtils.OwnItemCount(attacker, FriendShip1) >= 1 || AiUtils.OwnItemCount(attacker, FriendShip2) >= 1 || AiUtils.OwnItemCount(attacker, FriendShip3) >= 1 || AiUtils.OwnItemCount(attacker, FriendShip4) >= 1 || AiUtils.OwnItemCount(attacker, FriendShip5) >= 1)
			{
				if (Skill_GetConsumeMP(DeBuff1) < actor._currentMp && Skill_GetConsumeHP(DeBuff1) < actor.currentHp && Skill_InReuseDelay(DeBuff1) == 0)
				{
					AddUseSkillDesire(attacker, DeBuff1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				RemoveAttackDesire(attacker.getObjectId());
			}
			else
			{
				super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
			}
		}
		else if (IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (!IsNullCreature(attacker.getPlayer()) && (AiUtils.OwnItemCount(attacker.getPlayer(), FriendShip1) >= 1 || AiUtils.OwnItemCount(attacker.getPlayer(), FriendShip2) >= 1 || AiUtils.OwnItemCount(attacker.getPlayer(), FriendShip3) >= 1 || AiUtils.OwnItemCount(attacker.getPlayer(), FriendShip4) >= 1 || AiUtils.OwnItemCount(attacker.getPlayer(), FriendShip5) >= 1))
			{
				if (Skill_GetConsumeMP(DeBuff1) < actor._currentMp && Skill_GetConsumeHP(DeBuff1) < actor.currentHp && Skill_InReuseDelay(DeBuff1) == 0)
				{
					AddUseSkillDesire(attacker, DeBuff1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				RemoveAttackDesire(attacker.getObjectId());
			}
			else
			{
				super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
			}
		}
		else
		{
			super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
		}
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance actor = getActor();

		if (attacker.isPlayer())
		{
			if (AiUtils.OwnItemCount(attacker, FriendShip1) >= 1 || AiUtils.OwnItemCount(attacker, FriendShip2) >= 1 || AiUtils.OwnItemCount(attacker, FriendShip3) >= 1 || AiUtils.OwnItemCount(attacker, FriendShip4) >= 1 || AiUtils.OwnItemCount(attacker, FriendShip5) >= 1)
			{
				if (Skill_GetConsumeMP(DeBuff1) < actor._currentMp && Skill_GetConsumeHP(DeBuff1) < actor.currentHp && Skill_InReuseDelay(DeBuff1) == 0)
				{
					AddUseSkillDesire(attacker, DeBuff1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				RemoveAttackDesire(attacker.getObjectId());
			}
			else
			{
				super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
			}
		}
		else if (IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (!IsNullCreature(attacker.getPlayer()) && (AiUtils.OwnItemCount(attacker.getPlayer(), FriendShip1) >= 1 || AiUtils.OwnItemCount(attacker.getPlayer(), FriendShip2) >= 1 || AiUtils.OwnItemCount(attacker.getPlayer(), FriendShip3) >= 1 || AiUtils.OwnItemCount(attacker.getPlayer(), FriendShip4) >= 1 || AiUtils.OwnItemCount(attacker.getPlayer(), FriendShip5) >= 1))
			{
				if (Skill_GetConsumeMP(DeBuff1) < actor._currentMp && Skill_GetConsumeHP(DeBuff1) < actor.currentHp && Skill_InReuseDelay(DeBuff1) == 0)
				{
					AddUseSkillDesire(attacker, DeBuff1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				RemoveAttackDesire(attacker.getObjectId());
			}
			else
			{
				super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
			}
		}
		else
		{
			super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
		}
	}

	@Override
	protected void onEvtSeeSpell(int skill_name_id, Creature speller, Creature target)
	{
		float f0 = 0;
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (Skill_GetEffectPoint(skill_name_id) > 0)
		{
			if (speller.isPlayer())
			{
				if (AiUtils.OwnItemCount(speller, FriendShip1) >= 1 || AiUtils.OwnItemCount(speller, FriendShip2) >= 1 || AiUtils.OwnItemCount(speller, FriendShip3) >= 1 || AiUtils.OwnItemCount(speller, FriendShip4) >= 1 || AiUtils.OwnItemCount(speller, FriendShip5) >= 1)
				{
					if (Skill_GetConsumeMP(DeBuff1) < actor._currentMp && Skill_GetConsumeHP(DeBuff1) < actor.currentHp && Skill_InReuseDelay(DeBuff1) == 0)
					{
						AddUseSkillDesire(speller, DeBuff1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					RemoveAttackDesire(speller.getObjectId());
				}
				else
				{
					super.onEvtSeeSpell(skill_name_id, speller, target);
				}
			}
			else if (IsInCategory(Category.summon_npc_group, speller.getClassId()))
			{
				if (!IsNullCreature(speller.getPlayer()) && (AiUtils.OwnItemCount(speller.getPlayer(), FriendShip1) >= 1 || AiUtils.OwnItemCount(speller.getPlayer(), FriendShip2) >= 1 || AiUtils.OwnItemCount(speller.getPlayer(), FriendShip3) >= 1 || AiUtils.OwnItemCount(speller.getPlayer(), FriendShip4) >= 1 || AiUtils.OwnItemCount(speller.getPlayer(), FriendShip5) >= 1))
				{
					if (Skill_GetConsumeMP(DeBuff1) < actor._currentMp && Skill_GetConsumeHP(DeBuff1) < actor.currentHp && Skill_InReuseDelay(DeBuff1) == 0)
					{
						AddUseSkillDesire(speller, DeBuff1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					RemoveAttackDesire(speller.getObjectId());
				}
				else
				{
					super.onEvtSeeSpell(skill_name_id, speller, target);
				}
			}
			else
			{
				super.onEvtSeeSpell(skill_name_id, speller, target);
			}
		}
	}

}