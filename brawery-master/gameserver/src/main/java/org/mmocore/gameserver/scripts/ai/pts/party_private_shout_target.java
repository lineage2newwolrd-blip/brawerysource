package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class party_private_shout_target extends party_private
{
	public int MagicHeal = 458752001;

	public party_private_shout_target(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		Creature c0 = null;
		Creature c1 = null;
		top_desire_target = getTopHated();
		final NpcInstance boss = getActor().getLeader();
		final NpcInstance actor = getActor();

		if (!actor.isAlive())
		{
			return;
		}
		if (script_event_arg1 == ScriptEvent.SCE_VALAKAS_ATTACK)
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg2);
			c1 = AiUtils.GetCreatureFromIndex(boss.flag);
			if (!IsNullCreature(c0) && c0 == boss)
			{
				if (!IsNullCreature(top_desire_target))
				{
					if (!IsNullCreature(c1) && top_desire_target == c1)
					{
						return;
					}
				}
				switch (AiUtils.Rand(4))
				{
					case 0:
					{
						Say(AiUtils.MakeFString(1000292, "", "", "", "", ""));
						break;
					}
					case 1:
					{
						Say(AiUtils.MakeFString(1000400, "", "", "", "", ""));
						break;
					}
					case 2:
					{
						Say(AiUtils.MakeFString(1000401, "", "", "", "", ""));
						break;
					}
					case 3:
					{
						Say(AiUtils.MakeFString(1000402, "", "", "", "", ""));
						break;
					}
				}
				if (!IsNullCreature(c1))
				{
					RemoveAllAttackDesire();
					AddAttackDesire(AiUtils.GetCreatureFromIndex(boss.flag), DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		if (script_event_arg1 == ScriptEvent.SCE_PRIVATE_HEAL)
		{
			if (Skill_GetConsumeMP(MagicHeal) < actor._currentMp && Skill_GetConsumeHP(MagicHeal) < actor.currentHp && Skill_InReuseDelay(MagicHeal) == 0)
			{
				AddUseSkillDesire(boss, MagicHeal, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		if (script_event_arg1 == ScriptEvent.SCE_PRIVATE_DESPAWN)
		{
			Despawn();
		}
	}

}