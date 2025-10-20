package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class krudel_lizardman extends warrior_aggressive
{
	public krudel_lizardman(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		always_list.SetInfo(0, target);
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(227);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					if (AiUtils.HaveMemo(c1, 227) == 1 && AiUtils.GetMemoState(c1, 227) == 13)
					{
						SetMemoState(c1, 227, 14);
						SetFlagJournal(c1, 227, 16);
						ShowQuestMark(c1, 227);
						SoundEffect(c1, "ItemSound.quest_middle");
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
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		SetCurrentQuestID(227);
		AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 2000);
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		Creature c0 = null;
		int i0 = 0;
		final NpcInstance actor = getActor();

		SetCurrentQuestID(227);
		if (timer_id == 7778)
		{
			if (i_quest0 < 60)
			{
				i_quest0 = i_quest0 + 1;
				AddTimerEx(7778, 1000 * 5);
			}
			else
			{
				Despawn();
			}
		}
		else if (timer_id == 7779)
		{
			i0 = actor.param1;
			if (i0 > 0)
			{
				c0 = Maker_FindNpcByKey(i0);
				if (c0 !=null)
				{
					AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 2000);
				}
				else
				{
					AddTimerEx(7779, 500);
				}
			}
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtSpawn()
	{

		SetCurrentQuestID(227);
		AddTimerEx(7778, 1000 * 5);
		AddTimerEx(7779, 500);
		i_quest0 = 0;
		super.onEvtSpawn();
	}

}