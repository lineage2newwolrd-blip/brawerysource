package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class drevanul_prince_zeruel extends warrior_passive
{
	public drevanul_prince_zeruel(final NpcInstance actor){super(actor);}

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
				SetCurrentQuestID(229);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					if (AiUtils.HaveMemo(c1, 229) == 1 && AiUtils.OwnItemCount(c1, 3324) == 1 && AiUtils.OwnItemCount(c1, 3335) == 1 && AiUtils.OwnItemCount(c1, 3029) == 1 && AiUtils.OwnItemCount(c1, 3332) == 1)
					{
						if (c1.getWeaponClass() == 3029)
						{
							Say(AiUtils.MakeFString(22936, "", "", "", "", ""));
							GiveItem1(c1, 3334, 1);
							GiveItem1(c1, 3333, 1);
							SoundEffect(c1, "ItemSound.quest_itemget");
							DeleteItem1(c1, 3335, AiUtils.OwnItemCount(c1, 3335));
							DeleteItem1(c1, 3332, AiUtils.OwnItemCount(c1, 3332));
							SetFlagJournal(c1, 229, 10);
							ShowQuestMark(c1, 229);
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
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		SetCurrentQuestID(229);
		if (attacker.getPlayer() !=null)
		{
			attacker = attacker.getPlayer();
		}
		if (AiUtils.HaveMemo(attacker, 229) == 1 && AiUtils.OwnItemCount(attacker, 3323) == 1)
		{
			Say(AiUtils.MakeFString(22934, "", "", "", "", ""));
			Despawn();
			SetFlagJournal(attacker, 229, 5);
			ShowQuestMark(attacker, 229);
			SoundEffect(attacker, "ItemSound.quest_middle");
		}
		else if (AiUtils.HaveMemo(attacker, 229) == 1 && AiUtils.OwnItemCount(attacker, 3324) == 1 && AiUtils.OwnItemCount(attacker, 3335) == 1 && AiUtils.OwnItemCount(attacker, 3029) == 1 && AiUtils.OwnItemCount(attacker, 3332) == 1)
		{
			if (i_quest0 == 0 && attacker.getWeaponClass() == 3029)
			{
				Say(AiUtils.MakeFString(22935, "", "", "", "", ""));
				i_quest0 = 1;
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}