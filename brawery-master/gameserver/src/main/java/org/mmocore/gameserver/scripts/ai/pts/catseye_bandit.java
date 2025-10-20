package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class catseye_bandit extends warrior_passive
{
	public catseye_bandit(final NpcInstance actor){super(actor);}

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
				SetCurrentQuestID(403);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					Say(AiUtils.MakeFString(40307, "", "", "", "", ""));
					if (AiUtils.HaveMemo(c_quest2, 403)!=0 && AiUtils.OwnItemCount(c_quest2, 1185) > 0 && i_quest2 == 1)
					{
						switch (AiUtils.Rand(4))
						{
							case 0:
							{
								if (AiUtils.OwnItemCount(c_quest2, 1186) == 0)
								{
									GiveItem1(c_quest2, 1186, 1);
									if (AiUtils.OwnItemCount(c_quest2, 1186) + AiUtils.OwnItemCount(c_quest2, 1187) + AiUtils.OwnItemCount(c_quest2, 1188) + AiUtils.OwnItemCount(c_quest2, 1189) >= 3)
									{
										SoundEffect(c_quest2, "ItemSound.quest_middle");
										SetFlagJournal(c_quest2, 403, 6);
										ShowQuestMark(c_quest2, 403);
									}
									else
									{
										SoundEffect(c_quest2, "ItemSound.quest_itemget");
									}
								}
								break;
							}
							case 1:
							{
								if (AiUtils.OwnItemCount(c_quest2, 1187) == 0)
								{
									GiveItem1(c_quest2, 1187, 1);
									if (AiUtils.OwnItemCount(c_quest2, 1186) + AiUtils.OwnItemCount(c_quest2, 1187) + AiUtils.OwnItemCount(c_quest2, 1188) + AiUtils.OwnItemCount(c_quest2, 1189) >= 3)
									{
										SoundEffect(c_quest2, "ItemSound.quest_middle");
										SetFlagJournal(c_quest2, 403, 6);
										ShowQuestMark(c_quest2, 403);
									}
									else
									{
										SoundEffect(c_quest2, "ItemSound.quest_itemget");
									}
								}
								break;
							}
							case 2:
							{
								if (AiUtils.OwnItemCount(c_quest2, 1188) == 0)
								{
									GiveItem1(c_quest2, 1188, 1);
									if (AiUtils.OwnItemCount(c_quest2, 1186) + AiUtils.OwnItemCount(c_quest2, 1187) + AiUtils.OwnItemCount(c_quest2, 1188) + AiUtils.OwnItemCount(c_quest2, 1189) >= 3)
									{
										SoundEffect(c_quest2, "ItemSound.quest_middle");
										SetFlagJournal(c_quest2, 403, 6);
										ShowQuestMark(c_quest2, 403);
									}
									else
									{
										SoundEffect(c_quest2, "ItemSound.quest_itemget");
									}
								}
								break;
							}
							case 3:
							{
								if (AiUtils.OwnItemCount(c_quest2, 1189) == 0)
								{
									GiveItem1(c_quest2, 1189, 1);
									if (AiUtils.OwnItemCount(c_quest2, 1186) + AiUtils.OwnItemCount(c_quest2, 1187) + AiUtils.OwnItemCount(c_quest2, 1188) + AiUtils.OwnItemCount(c_quest2, 1189) >= 3)
									{
										SoundEffect(c_quest2, "ItemSound.quest_middle");
										SetFlagJournal(c_quest2, 403, 6);
										ShowQuestMark(c_quest2, 403);
									}
									else
									{
										SoundEffect(c_quest2, "ItemSound.quest_itemget");
									}
								}
								break;
							}
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
		int weapon_class_id = 0;

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
						Say(AiUtils.MakeFString(40306, "", "", "", "", ""));
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