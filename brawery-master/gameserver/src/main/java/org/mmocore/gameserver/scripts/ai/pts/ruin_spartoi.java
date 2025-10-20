package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class ruin_spartoi extends warrior_passive
{
	public ruin_spartoi(final NpcInstance actor){super(actor);}

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
					SetCurrentQuestID(406);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (AiUtils.HaveMemo(c_quest0, 406) != 0 && AiUtils.OwnItemCount(c_quest0, 1203) == 0 && AiUtils.OwnItemCount(c_quest0, 1205) < 20 && i_quest0 == 1 && AiUtils.Rand(100) < 70)
						{
							GiveItem1(c_quest0, 1205, 1);
							if (AiUtils.OwnItemCount(c_quest0, 1205) >= 19)
							{
								SoundEffect(c_quest0, "ItemSound.quest_middle");
								SetFlagJournal(c_quest0, 406, 2);
								ShowQuestMark(c_quest0, 406);
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
					SetCurrentQuestID(403);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (AiUtils.HaveMemo(c_quest2, 403)!=0 && AiUtils.OwnItemCount(c_quest2, 1183) < 10 && i_quest2 == 1 && AiUtils.Rand(10) < 8)
						{
							GiveItem1(c_quest2, 1183, 1);
							if (AiUtils.OwnItemCount(c_quest2, 1183) >= 9)
							{
								SoundEffect(c_quest2, "ItemSound.quest_middle");
								SetFlagJournal(c_quest2, 403, 3);
								ShowQuestMark(c_quest2, 403);
							}
							else
							{
								SoundEffect(c_quest2, "ItemSound.quest_itemget");
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
		int weapon_class_id = 0;

		SetCurrentQuestID(406);
		if (AiUtils.HaveMemo(attacker, 406)!=0)
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
		SetCurrentQuestID(403);
		if (AiUtils.HaveMemo(attacker, 403)!=0)
		{
			switch (i_quest2)
			{
				case 0:
				{
					c_quest2 = attacker;
					if (c_quest2.getWeaponClass() != 1181 && c_quest2.getWeaponClass() != 1182)
					{
						i_quest2 = 2;
					}
					else
					{
						i_quest2 = 1;
					}
					break;
				}
				case 1:
				{
					if (c_quest2.getWeaponClass() != 1181 && c_quest2.getWeaponClass() != 1182)
					{
						i_quest2 = 2;
					}
					if (c_quest2 != attacker)
					{
						i_quest2 = 2;
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