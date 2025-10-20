package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class tutorial_gremlin extends warrior_passive
{
	public tutorial_gremlin(final NpcInstance actor){super(actor);}

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
				SetCurrentQuestID(201);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (AiUtils.GetMemoStateEx(c_quest0, 255, 1) == 1 || AiUtils.GetMemoStateEx(c_quest0, 255, 1) == 0)
					{
						VoiceEffect(c_quest0, "tutorial_voice_011", 1000);
						ShowQuestionMark(c_quest0, 3);
						SetMemoStateEx(c_quest0, 255, 1, 2);
					}
					if ((((AiUtils.GetMemoStateEx(c_quest0, 255, 1) == 1 || AiUtils.GetMemoStateEx(c_quest0, 255, 1) == 2 || AiUtils.GetMemoStateEx(c_quest0, 255, 1) == 0) && AiUtils.OwnItemCount(c_quest0, 6353) < 1) && i_quest0 == 1) && AiUtils.Rand(2) <= 1)
					{
						DropItem1(c_quest0, 6353, 1);
						SoundEffect(c_quest0, "ItemSound.quest_tutorial");
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

		SetCurrentQuestID(201);
		switch (i_quest0)
		{
			case 0:
			{
				i_quest0 = 1;
				c_quest0 = attacker;
			}
			case 1:
			{
				if (c_quest0 != attacker)
				{
					i_quest0 = 2;
				}
				break;
			}
			case 2:
			{
				break;
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}