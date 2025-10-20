package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class q_wendy_guardian extends warrior_casting_enchant_lab_clan1
{
	public q_wendy_guardian(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
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
				SetCurrentQuestID(114);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					if (target.isPlayer())
					{
						if (AiUtils.HaveMemo(target, 114) == 1 && AiUtils.GetMemoState(target, 114) == 6 && AiUtils.GetMemoStateEx(target, 114, 1) == 20111)
						{
							Say(AiUtils.MakeFString(11452, "", "", "", "", ""));
							SetMemoStateEx(target, 114, 1, 20211);
							SetFlagJournal(target, 114, 11);
							ShowQuestMark(target, 114);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					npc0 = AiUtils.GetNPCFromID(actor.param3);
					if (!AiUtils.IsNull(npc0))
					{
						npc0.i_quest0 = 0;
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

		SetCurrentQuestID(114);
		if (timer_id == 11401)
		{
			c0 = AiUtils.GetCreatureFromIndex(actor.param1);
			if (c0 !=null)
			{
				if (c0.isPlayer())
				{
					Say(AiUtils.MakeFString(11450, c0.getName(), "", "", "", ""));
					AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 2000);
				}
			}
		}
		else if (timer_id == 11402)
		{
			Say(AiUtils.MakeFString(11451, "", "", "", "", ""));
			npc0 = AiUtils.GetNPCFromID(actor.param3);
			if (!AiUtils.IsNull(npc0))
			{
				npc0.i_quest0 = 0;
			}
			Despawn();
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtSpawn()
	{

		SetCurrentQuestID(114);
		AddTimerEx(11401, 500);
		AddTimerEx(11402, 1000 * 300);
		super.onEvtSpawn();
	}

}