package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class ol_mahum_patrol extends warrior_passive_use_bow
{
	public ol_mahum_patrol(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i4 = 0;
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
		target = attacker;
		if (AiUtils.HaveMemo(target, 63) == 1 && AiUtils.GetMemoState(target, 63) == 2 && AiUtils.OwnItemCount(target, 9763) < 5)
		{
			random1_list.SetInfo(2, target);
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(407);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (AiUtils.HaveMemo(c_quest0, 407)!=0 && i_quest0 == 1 && AiUtils.GetMemoState(c_quest0, 407) == 1)
						{
							if (AiUtils.OwnItemCount(c_quest0, 1208) + AiUtils.OwnItemCount(c_quest0, 1209) + AiUtils.OwnItemCount(c_quest0, 1210) + AiUtils.OwnItemCount(c_quest0, 1211) < 4)
							{
								if (AiUtils.OwnItemCount(c_quest0, 1208) < 1)
								{
									GiveItem1(c_quest0, 1208, 1);
									if (AiUtils.OwnItemCount(c_quest0, 1208) + AiUtils.OwnItemCount(c_quest0, 1209) + AiUtils.OwnItemCount(c_quest0, 1210) + AiUtils.OwnItemCount(c_quest0, 1211) >= 3)
									{
										SoundEffect(c_quest0, "ItemSound.quest_middle");
										SetFlagJournal(c_quest0, 407, 3);
										ShowQuestMark(c_quest0, 407);
									}
									else
									{
										SoundEffect(c_quest0, "ItemSound.quest_itemget");
									}
								}
								else if (AiUtils.OwnItemCount(c_quest0, 1209) < 1)
								{
									GiveItem1(c_quest0, 1209, 1);
									if (AiUtils.OwnItemCount(c_quest0, 1208) + AiUtils.OwnItemCount(c_quest0, 1209) + AiUtils.OwnItemCount(c_quest0, 1210) + AiUtils.OwnItemCount(c_quest0, 1211) >= 3)
									{
										SoundEffect(c_quest0, "ItemSound.quest_middle");
										SetFlagJournal(c_quest0, 407, 3);
										ShowQuestMark(c_quest0, 407);
									}
									else
									{
										SoundEffect(c_quest0, "ItemSound.quest_itemget");
									}
								}
								else if (AiUtils.OwnItemCount(c_quest0, 1210) < 1)
								{
									GiveItem1(c_quest0, 1210, 1);
									if (AiUtils.OwnItemCount(c_quest0, 1208) + AiUtils.OwnItemCount(c_quest0, 1209) + AiUtils.OwnItemCount(c_quest0, 1210) + AiUtils.OwnItemCount(c_quest0, 1211) >= 3)
									{
										SoundEffect(c_quest0, "ItemSound.quest_middle");
										SetFlagJournal(c_quest0, 407, 3);
										ShowQuestMark(c_quest0, 407);
									}
									else
									{
										SoundEffect(c_quest0, "ItemSound.quest_itemget");
									}
								}
								else if (AiUtils.OwnItemCount(c_quest0, 1211) < 1)
								{
									GiveItem1(c_quest0, 1211, 1);
									if (AiUtils.OwnItemCount(c_quest0, 1208) + AiUtils.OwnItemCount(c_quest0, 1209) + AiUtils.OwnItemCount(c_quest0, 1210) + AiUtils.OwnItemCount(c_quest0, 1211) >= 3)
									{
										SoundEffect(c_quest0, "ItemSound.quest_middle");
										SetFlagJournal(c_quest0, 407, 3);
										ShowQuestMark(c_quest0, 407);
									}
									else
									{
										SoundEffect(c_quest0, "ItemSound.quest_itemget");
									}
								}
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(326);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 326)!=0)
						{
							if (AiUtils.Rand(100) < 61)
							{
								GiveItem1(c1, 1359, 1);
								SoundEffect(c1, "ItemSound.quest_itemget");
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
			if (code_info.getCode() == 2)
			{
				SetCurrentQuestID(63);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					i4 = AiUtils.Rand(1000);
					if (AiUtils.OwnItemCount(target, 9763) < 5)
					{
						if (AiUtils.OwnItemCount(target, 9762) >= 10 && AiUtils.OwnItemCount(target, 9763) >= 4)
						{
							SetFlagJournal(target, 63, 3);
							ShowQuestMark(target, 63);
							SoundEffect(target, "ItemSound.quest_middle");
						}
						else
						{
							SoundEffect(target, "ItemSound.quest_itemget");
						}
						GiveItem1(target, 9763, 1);
					}
				}
			}
		}
		super.onEvtDead(attacker);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		SetCurrentQuestID(407);
		if (AiUtils.HaveMemo(attacker, 407)!=0)
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