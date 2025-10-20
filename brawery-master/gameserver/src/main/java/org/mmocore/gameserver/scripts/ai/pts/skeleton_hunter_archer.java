package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class skeleton_hunter_archer extends warrior_passive_use_bow
{
	public skeleton_hunter_archer(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i4 = 0;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.OwnItemCount(target, 970) == 1 && AiUtils.OwnItemCount(target, 1107) < 10)
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.OwnItemCount(target, 970) == 1 && AiUtils.OwnItemCount(target, 1107) < 10)
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.OwnItemCount(target, 970) == 1 && AiUtils.OwnItemCount(target, 1107) < 10)
				{
					random1_list.SetInfo(0, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 320) == 1 && AiUtils.OwnItemCount(target, 809) < 10)
		{
			random1_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 320) == 1 && AiUtils.OwnItemCount(target, 809) < 10)
		{
			random1_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 320) == 1 && AiUtils.OwnItemCount(target, 809) < 10)
				{
					random1_list.SetInfo(1, target);
				}
			}
		}
		target = attacker;
		always_list.SetInfo(2, target);
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 2)
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
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(103);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(10) < 10)
						{
							GiveItem1(target, 1107, 1);
							if (AiUtils.OwnItemCount(target, 1107) >= 9)
							{
								SoundEffect(target, "ItemSound.quest_middle");
								SetFlagJournal(target, 103, 4);
								ShowQuestMark(target, 103);
							}
							else
							{
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(320);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 18)
						{
							GiveItem1(target, 809, 1);
							if (AiUtils.OwnItemCount(target, 809) >= 9)
							{
								SoundEffect(target, "ItemSound.quest_middle");
								SetFlagJournal(target, 320, 2);
								ShowQuestMark(target, 320);
							}
							else
							{
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
			}
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