package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class party_private_param extends warrior
{
	public int IsSayPrivate = 1;

	public party_private_param(final NpcInstance actor){super(actor);}

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
			if (!IsNullCreature(c0))
			{
				if (c0 == boss)
				{
					c1 = AiUtils.GetCreatureFromIndex(boss.flag);
					if (!IsNullCreature(c1))
					{
						if (!IsNullCreature(top_desire_target))
						{
							if (top_desire_target == c1)
							{
								return;
							}
						}
						if (IsSayPrivate == 1)
						{
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
						}
						RemoveAllAttackDesire();
						AddAttackDesire(c1, DesireMove.MOVE_TO_TARGET, 2000);
					}
				}
			}
		}
		else if (script_event_arg1 == ScriptEvent.SCE_PRIVATE_DESPAWN)
		{
			Despawn();
		}
	}

}