package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_param_warrior extends warrior
{
	public String Privates = "";
	public int SummonPrivateRate = 0;

	public party_leader_param_warrior(final NpcInstance actor)
	{
		super(actor);
		ShoutTarget = 0;
		AttackLowHP = 0;
	}

	@Override
	protected void onEvtSpawn()
	{

		if (SummonPrivateRate == 0)
		{
			if (!IsNullString(Privates))
			{
				CreatePrivates(Privates);
			}
			i_ai2 = 1;
		}
		else
		{
			i_ai2 = 0;
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (i_ai2 == 0)
		{
			if (AiUtils.Rand(100) < SummonPrivateRate)
			{
				CreatePrivates(Privates);
				switch (AiUtils.Rand(4))
				{
					case 0:
					{
						Say(AiUtils.MakeFString(1000294, "", "", "", "", ""));
						break;
					}
					case 1:
					{
						Say(AiUtils.MakeFString(1000403, "", "", "", "", ""));
						break;
					}
					case 2:
					{
						Say(AiUtils.MakeFString(1000404, "", "", "", "", ""));
						break;
					}
					case 3:
					{
						Say(AiUtils.MakeFString(1000405, "", "", "", "", ""));
						break;
					}
				}
				i_ai2 = 1;
			}
		}
		if (ShoutTarget == 1)
		{
			if (AiUtils.Rand(100) < 50 && attacker.currentHp / attacker.getMaxHp() * 100 < 40)
			{
				if (!IsNullCreature(top_desire_target) && attacker.isPlayer())
				{
					if (top_desire_target == attacker)
					{
						switch (AiUtils.Rand(3))
						{
							case 0:
							{
								Say(AiUtils.MakeFString(1000291, attacker.getName(), "", "", "", ""));
								break;
							}
							case 1:
							{
								Say(AiUtils.MakeFString(1000398, attacker.getName(), "", "", "", ""));
								break;
							}
							case 2:
							{
								Say(AiUtils.MakeFString(1000399, attacker.getName(), "", "", "", ""));
								break;
							}
						}
						RemoveAllAttackDesire();
						AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
						actor.flag = AiUtils.GetIndexFromCreature(attacker);
						BroadcastScriptEvent(ScriptEvent.SCE_VALAKAS_ATTACK, AiUtils.GetIndexFromCreature(actor), 300);
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}