package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class party_private_casting_enchant extends party_private
{
	public int Buff = 263979009;

	public party_private_casting_enchant(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
		i_ai1 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (i_ai1 == 0 && AiUtils.Rand(100) < 33 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) > 50)
			{
				if (Skill_GetConsumeMP(Buff) < actor._currentMp && Skill_GetConsumeHP(Buff) < actor.currentHp && Skill_InReuseDelay(Buff) == 0)
				{
					AddUseSkillDesire(actor, Buff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				i_ai1 = 1;
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{

		if (script_event_arg1 == ScriptEvent.SCE_PRIVATE_DESPAWN)
		{
			Despawn();
		}
	}

}