package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class goblin_brigand_sub_ldr extends warrior_aggressive
{
	public goblin_brigand_sub_ldr(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i0 = 0;
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
		if (AiUtils.HaveMemo(target, 292)!=0)
		{
			random1_list.SetInfo(1, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(2, target);
		}
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(2, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 426) == 1)
				{
					random1_list.SetInfo(2, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(108);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					if (AiUtils.HaveMemo(c1, 108) == 1 && AiUtils.OwnItemCount(c1, 1563)!=0)
					{
						if (AiUtils.HaveMemo(c1, 108) == 1 && AiUtils.OwnItemCount(c1, 1563)!=0)
						{
							if (AiUtils.Rand(10) < 6)
							{
								if (AiUtils.OwnItemCount(c1, 1564) + AiUtils.OwnItemCount(c1, 1565) >= 19)
								{
									if (AiUtils.OwnItemCount(c1, 1564) < 10)
									{
										GiveItem1(c1, 1564, 1);
										SetFlagJournal(c1, 108, 6);
										ShowQuestMark(c1, 108);
										SoundEffect(c1, "ItemSound.quest_middle");
									}
								}
								else if (AiUtils.OwnItemCount(c1, 1564) < 10)
								{
									GiveItem1(c1, 1564, 1);
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
							}
							if (AiUtils.Rand(10) < 6)
							{
								if (AiUtils.OwnItemCount(c1, 1564) + AiUtils.OwnItemCount(c1, 1565) >= 19)
								{
									if (AiUtils.OwnItemCount(c1, 1565) < 10)
									{
										GiveItem1(c1, 1565, 1);
										SetFlagJournal(c1, 108, 6);
										ShowQuestMark(c1, 108);
										SoundEffect(c1, "ItemSound.quest_middle");
									}
								}
								else if (AiUtils.OwnItemCount(c1, 1564) + AiUtils.OwnItemCount(c1, 1565) < 20)
								{
									if (AiUtils.OwnItemCount(c1, 1565) < 10)
									{
										GiveItem1(c1, 1565, 1);
										SoundEffect(c1, "ItemSound.quest_itemget");
									}
								}
							}
						}
					}
				}
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			switch (code_info.getCode())
			{
				case 1:
				{
					SetCurrentQuestID(292);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i0 = AiUtils.Rand(10);
						if (i0 > 5)
						{
							GiveItem1(target, 1483, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
						else if (i0 > 4)
						{
							if (AiUtils.OwnItemCount(target, 1487) < 1 && AiUtils.OwnItemCount(target, 1486) < 3)
							{
								GiveItem1(target, 1486, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
							else if (AiUtils.OwnItemCount(target, 1487) < 1 && AiUtils.OwnItemCount(target, 1486) == 3)
							{
								GiveItem1(target, 1487, 1);
								SetFlagJournal(target, 292, 2);
								ShowQuestMark(target, 292);
								DeleteItem1(target, 1486, AiUtils.OwnItemCount(target, 1486));
								SoundEffect(target, "ItemSound.quest_middle");
							}
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(426);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(1000) < 85)
						{
							GiveItem1(target, 7586, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
			}
		}
		super.onEvtDead(attacker);
	}

}