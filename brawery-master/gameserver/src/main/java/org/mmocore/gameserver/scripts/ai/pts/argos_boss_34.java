package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class argos_boss_34 extends warrior_aggressive
{
	public argos_boss_34(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		CodeInfo code_info = null;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;
		final NpcInstance actor = getActor();

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 69) == 1 && target.getClassId() == 130 && AiUtils.GetMemoState(target, 69) == 12 && AiUtils.OwnItemCount(target, 9736) == 0)
		{
			always_list.SetInfo(0, target);
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(69);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					if (target.isPlayer())
					{
						if (target.getObjectId() == actor.param2)
						{
							GiveItem1(target, 9736, 1);
							SetFlagJournal(target, 69, 16);
							ShowQuestMark(target, 69);
							SoundEffect(target, "ItemSound.quest_middle");
							DeleteItem1(target, 9742, AiUtils.OwnItemCount(target, 9742));
							Say(AiUtils.MakeFString(6954, "", "", "", "", ""));
						}
						else
						{
							Say(AiUtils.MakeFString(6955, "", "", "", "", ""));
						}
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
		int i0 = 0;

		SetCurrentQuestID(69);
		if (timer_id == 99908)
		{
			Say(AiUtils.MakeFString(6956, "", "", "", "", ""));
			Despawn();
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtSpawn()
	{
		Creature c0 = null;
		final NpcInstance actor = getActor();

		SetCurrentQuestID(69);
		AddTimerEx(99908, 1000 * 600);
		c0 = AiUtils.GetCreatureFromIndex(actor.param1);
		if (c0 !=null)
		{
			if (c0.isPlayer())
			{
				AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 10000);
			}
			if (c0.isPlayer())
			{
				Say(AiUtils.MakeFString(6953, c0.getName(), "", "", "", ""));
			}
		}
		super.onEvtSpawn();
	}

}