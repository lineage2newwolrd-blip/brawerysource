package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class gremlin_filcher extends warrior_aggressive_immediate
{
	public gremlin_filcher(final NpcInstance actor){super(actor);}

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
				SetCurrentQuestID(335);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					if (GetCurrentTick() - c1.quest_last_reward_time > 1)
					{
						c1.quest_last_reward_time = GetCurrentTick();
						if (AiUtils.HaveMemo(c1, 335)!=0 && AiUtils.OwnItemCount(c1, 3754)!=0 && AiUtils.OwnItemCount(c1, 3796) < 40)
						{
							GiveItem1(c1, 3796, 5);
							if (AiUtils.OwnItemCount(c1, 3796) >= 40)
							{
								SoundEffect(c1, "ItemSound.quest_midddle");
							}
							else
							{
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
							Say(AiUtils.MakeFString(33513, "", "", "", "", ""));
						}
						if (AiUtils.HaveMemo(c1, 335)!=0 && AiUtils.OwnItemCount(c1, 3755)!=0 && AiUtils.OwnItemCount(c1, 3797) < 40)
						{
							GiveItem1(c1, 3797, 5);
							if (AiUtils.OwnItemCount(c1, 3797) >= 40)
							{
								SoundEffect(c1, "ItemSound.quest_midddle");
							}
							else
							{
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
							Say(AiUtils.MakeFString(33513, "", "", "", "", ""));
						}
						if (AiUtils.HaveMemo(c1, 335)!=0 && AiUtils.OwnItemCount(c1, 3757)!=0 && AiUtils.OwnItemCount(c1, 3799) < 40)
						{
							GiveItem1(c1, 3799, 5);
							if (AiUtils.OwnItemCount(c1, 3799) >= 40)
							{
								SoundEffect(c1, "ItemSound.quest_midddle");
							}
							else
							{
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
							Say(AiUtils.MakeFString(33513, "", "", "", "", ""));
						}
						if (AiUtils.HaveMemo(c1, 335)!=0 && AiUtils.OwnItemCount(c1, 3762)!=0 && AiUtils.OwnItemCount(c1, 3804) < 40)
						{
							GiveItem1(c1, 3804, 5);
							if (AiUtils.OwnItemCount(c1, 3804) >= 40)
							{
								SoundEffect(c1, "ItemSound.quest_midddle");
							}
							else
							{
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
							Say(AiUtils.MakeFString(33513, "", "", "", "", ""));
						}
						if (AiUtils.HaveMemo(c1, 335)!=0 && AiUtils.OwnItemCount(c1, 3752)!=0 && AiUtils.OwnItemCount(c1, 3794) < 20)
						{
							GiveItem1(c1, 3794, 3);
							if (AiUtils.OwnItemCount(c1, 3794) >= 20)
							{
								SoundEffect(c1, "ItemSound.quest_midddle");
							}
							else
							{
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
							Say(AiUtils.MakeFString(33513, "", "", "", "", ""));
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

}