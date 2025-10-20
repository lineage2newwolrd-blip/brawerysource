package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class skeleton_infantry extends warrior_passive
{
	public skeleton_infantry(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c0 = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i1 = 0;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;
		final NpcInstance actor = getActor();

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		always_list.SetInfo(0, target);
		target = attacker;
		always_list.SetInfo(1, target);
		target = attacker;
		always_list.SetInfo(2, target);
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(413);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (AiUtils.HaveMemo(c_quest0, 413) == 1 && AiUtils.OwnItemCount(c_quest0, 1267) == 1 && i_quest0 == 1 && AiUtils.OwnItemCount(c_quest0, 1268) < 10)
						{
							GiveItem1(c_quest0, 1268, 1);
							if (AiUtils.OwnItemCount(c_quest0, 1268) >= 9)
							{
								SoundEffect(c_quest0, "ItemSound.quest_middle");
								SetFlagJournal(c_quest0, 413, 6);
								ShowQuestMark(c_quest0, 413);
							}
							else
							{
								SoundEffect(c_quest0, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(325);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.OwnItemCount(c1, 1349)!=0 && AiUtils.HaveMemo(c1, 325)!=0)
						{
							i0 = AiUtils.Rand(100);
							if (GetCurrentTick() - c1.quest_last_reward_time > 1)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								if (i0 < 5)
								{
									GiveItem1(c1, 1353, 1);
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
								else if (i0 < 20)
								{
									GiveItem1(c1, 1354, 1);
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
								else if (i0 < 31)
								{
									GiveItem1(c1, 1355, 1);
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
								else if (i0 < 33)
								{
									GiveItem1(c1, 1356, 1);
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
								else if (i0 < 69)
								{
									GiveItem1(c1, 1357, 1);
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(708);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c0 = Pledge_GetLeader(target);
						if (!IsNullCreature(c0))
						{
							if (AiUtils.HaveMemo(c0, 708) == 1 && AiUtils.GetMemoState(c0, 708) / 10 == 2)
							{
								i0 = AiUtils.GetMemoStateEx(c0, 708, 1);
								i1 = AiUtils.Rand(100);
								if (i1 < i0)
								{
									CreateOnePrivateEx(27393, "q_duahan_of_glodio", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), actor.getObjectId());
								}
								else
								{
									SetMemoStateEx(c0, 708, 1, i0 + 1);
								}
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

		SetCurrentQuestID(413);
		if (AiUtils.HaveMemo(attacker, 413)!=0)
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