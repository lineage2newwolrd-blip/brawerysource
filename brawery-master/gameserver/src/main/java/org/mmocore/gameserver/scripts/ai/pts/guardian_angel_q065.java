package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class guardian_angel_q065 extends warrior_aggressive
{
	public guardian_angel_q065(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c0 = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		NpcInstance npc0 = null;
		CodeInfoList random1_list = null;
		Creature target = null;
		final NpcInstance actor = getActor();

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		always_list.SetInfo(0, target);
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(65);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					c0 = AiUtils.GetCreatureFromIndex(actor.param1);
					npc0 = AiUtils.GetNPCFromID(actor.param3);
					if (!AiUtils.IsNull(c0) && !AiUtils.IsNull(npc0))
					{
						if (c1 == c0)
						{
							if (!AiUtils.IsNull(c1))
							{
								if (AiUtils.HaveMemo(c1, 65) == 1 && GetOneTimeQuestFlag(c1, 65) == 0 && AiUtils.GetMemoState(c1, 65) == 12)
								{
									CreateOnePrivateEx(32242, "Keit_nat_q0065", 0, 0, AiUtils.FloatToInt(actor.getX()) + 20, AiUtils.FloatToInt(actor.getY()) + 20, AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(c0), c0.getObjectId(), npc0.getObjectId());
									SetMemoState(c1, 65, 13);
									Say(AiUtils.MakeFString(6554, "", "", "", "", ""));
								}
							}
						}
						else if (!AiUtils.IsNull(npc0))
						{
							if (npc0.i_quest0 == 1)
							{
								npc0.i_quest0 = 0;
							}
						}
						Say(AiUtils.MakeFString(6555, "", "", "", "", ""));
					}
				}
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			code_info.getCode();
		}
		super.onEvtDead(attacker);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		Creature c0 = null;
		NpcInstance npc0 = null;
		final NpcInstance actor = getActor();

		SetCurrentQuestID(65);
		if (timer_id == 6511)
		{
			npc0 = AiUtils.GetNPCFromID(actor.param3);
			c0 = AiUtils.GetCreatureFromIndex(actor.param1);
			if (!AiUtils.IsNull(npc0))
			{
				if (npc0.i_quest0 == 1)
				{
					npc0.i_quest0 = 0;
					if (!AiUtils.IsNull(c0))
					{
						Say(AiUtils.MakeFString(6553, c0.getName(), "", "", "", ""));
					}
				}
			}
			Despawn();
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtSpawn()
	{
		Creature c0 = null;
		Creature c1 = null;
		int i0 = 0;
		final NpcInstance actor = getActor();

		SetCurrentQuestID(65);
		c0 = AiUtils.GetCreatureFromIndex(actor.param1);
		AddTimerEx(6511, 1000 * 70);
		if (!AiUtils.IsNull(c0))
		{
			Say(AiUtils.MakeFString(6552, c0.getName(), "", "", "", ""));
			AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 10000);
		}
		super.onEvtSpawn();
	}

}