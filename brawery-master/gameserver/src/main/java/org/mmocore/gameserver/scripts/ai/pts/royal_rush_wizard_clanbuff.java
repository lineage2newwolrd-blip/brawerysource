package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;

public class royal_rush_wizard_clanbuff extends royal_rush_wizard_ddmagic2
{
	public int W_ClanBuff = 272039937;

	public royal_rush_wizard_clanbuff(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		int i6 = 0;
		final NpcInstance actor = getActor();

		RemoveAllHateInfoIF(1, 0);
		if ((GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))) && GetHateInfoCount() == 0)
		{
			if (Skill_GetConsumeMP(W_ClanBuff) < actor._currentMp && Skill_GetConsumeHP(W_ClanBuff) < actor.currentHp)
			{
				if (Skill_InReuseDelay(W_ClanBuff) == 0)
				{
					AddUseSkillDesire(attacker, W_ClanBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				else
				{
					AddUseSkillDesire(attacker, W_ClanBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else
			{
				i_ai0 = 1;
				AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

}