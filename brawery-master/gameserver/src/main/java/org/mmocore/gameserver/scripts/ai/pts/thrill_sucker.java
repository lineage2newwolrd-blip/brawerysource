package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class thrill_sucker extends warrior_aggressive
{
	public thrill_sucker(final NpcInstance actor){super(actor);}

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
		target = attacker;
		always_list.SetInfo(1, target);
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(412);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (AiUtils.HaveMemo(c_quest0, 412) == 1 && AiUtils.OwnItemCount(c_quest0, 1278) == 1 && i_quest0 == 1 && AiUtils.OwnItemCount(c_quest0, 1259) < 2)
						{
							if (AiUtils.Rand(2) == 0)
							{
								GiveItem1(c_quest0, 1259, 1);
								if (AiUtils.OwnItemCount(c_quest0, 1259) == 2)
								{
									SoundEffect(c_quest0, "ItemSound.quest_middle");
								}
								else
								{
									SoundEffect(c_quest0, "ItemSound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 1:
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
						if (AiUtils.HaveMemo(c1, 227) == 1 && AiUtils.GetMemoState(c1, 227) == 16 && AiUtils.OwnItemCount(c1, 2837) == 0)
						{
							GiveItem1(c1, 2837, 1);
							SoundEffect(c1, "ItemSound.quest_itemget");
							if (AiUtils.OwnItemCount(c1, 2834) >= 1 && AiUtils.OwnItemCount(c1, 2835) >= 1 && AiUtils.OwnItemCount(c1, 2836) >= 1 && AiUtils.OwnItemCount(c1, 2838) >= 1)
							{
								SetMemoState(c1, 227, 17);
								SetFlagJournal(c1, 227, 19);
								ShowQuestMark(c1, 227);
							}
						}
					}
					break;
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

		SetCurrentQuestID(412);
		if (AiUtils.HaveMemo(attacker, 412)!=0)
		{
			switch (i_quest0)
			{
				case 0:
				{
					i_quest0 = 1;
					if (attacker.getPlayer() !=null)
					{
						c_quest0 = attacker.getPlayer();
					}
					else
					{
						c_quest0 = attacker;
					}
					break;
				}
				case 1:
				{
					if (attacker.getPlayer() !=null)
					{
						if (c_quest0 != attacker.getPlayer())
						{
							i_quest0 = 2;
						}
					}
					else if (c_quest0 != attacker)
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
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}