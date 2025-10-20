package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class angel_killer extends warrior_pa_casting_3skill_magical2
{
	public angel_killer(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		Creature c1 = null;
		int i0 = 0;
		final NpcInstance actor = getActor();

		SetCurrentQuestID(348);
		if (attacker.getPlayer() !=null)
		{
			attacker = attacker.getPlayer();
		}
		if (AiUtils.HaveMemo(attacker, 348)!=0 && AiUtils.GetMemoStateEx(attacker, 348, 0) < 8 && AiUtils.GetMemoStateEx(attacker, 348, 1) % 100 / 10 == 1 && AiUtils.OwnItemCount(attacker, 4291) == 0 && AiUtils.OwnItemCount(attacker, 4398) == 0)
		{
			if (actor.currentHp < actor.getMaxHp() * 0.300000f)
			{
				i0 = AiUtils.GetMemoStateEx(attacker, 348, 1);
				i0 = i0 + 10;
				SetMemoStateEx(attacker, 348, 1, i0);
				if (i0 % 10 == 0)
				{
					DeleteRadar(attacker, -2908, 44128, -2712, 1);
					ShowRadar(attacker, -2908, 44128, -2712, 1);
				}
				else
				{
					SetFlagJournal(attacker, 348, 19);
					ShowQuestMark(attacker, 348);
					SoundEffect(attacker, "ItemSound.quest_middle");
				}
				Say(AiUtils.MakeFString(34830, "", "", "", "", ""));
				Despawn();
			}
		}
		else if (AiUtils.HaveMemo(attacker, 348)!=0 && AiUtils.GetMemoStateEx(attacker, 348, 0) < 8 && AiUtils.GetMemoStateEx(attacker, 348, 1) % 100 / 10 == 2 && AiUtils.OwnItemCount(attacker, 4291) == 0 && AiUtils.OwnItemCount(attacker, 4398) == 0)
		{
			Say(AiUtils.MakeFString(34839, "", "", "", "", ""));
			Despawn();
		}
		else if (AiUtils.HaveMemo(attacker, 348)!=0 && (AiUtils.OwnItemCount(attacker, 4291) >= 1 || AiUtils.OwnItemCount(attacker, 4398) >= 1))
		{
			Say(AiUtils.MakeFString(34839, "", "", "", "", ""));
			Despawn();
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		Creature c0 = null;

		SetCurrentQuestID(348);
		if (timer_id == 34801)
		{
			Despawn();
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtSpawn()
	{
		int i0 = 0;

		SetCurrentQuestID(348);
		AddTimerEx(34801, 1000 * 600);
		Say(AiUtils.MakeFString(34831, "", "", "", "", ""));
		super.onEvtSpawn();
	}

}