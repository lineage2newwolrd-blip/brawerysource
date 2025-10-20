package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class drake extends warrior_aggressive_casting_ddmagic
{
	public drake(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		Creature c2 = null;
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
				SetCurrentQuestID(503);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					c2 = Pledge_GetLeader(c1);
					if (!IsNullCreature(c2))
					{
						if (AiUtils.HaveMemo(c2, 503) == 1 && AiUtils.GetMemoState(c2, 503) < 3000 && AiUtils.GetMemoState(c2, 503) >= 2000 && DistFromMe(c2) <= 1500)
						{
							if (AiUtils.Rand(100) < 10)
							{
								if (AiUtils.OwnItemCount(c2, 3839) < 10)
								{
									GiveItem1(c2, 3839, 1);
									if (AiUtils.OwnItemCount(c2, 3839) >= 10)
									{
										SoundEffect(c2, "ItemSound.quest_middle");
									}
									else
									{
										SoundEffect(c2, "ItemSound.quest_itemget");
									}
								}
							}
							if (AiUtils.Rand(100) < 50)
							{
								if (AiUtils.OwnItemCount(c2, 3841) < 10)
								{
									GiveItem1(c2, 3841, 1);
									if (AiUtils.OwnItemCount(c2, 3841) >= 10)
									{
										SoundEffect(c2, "ItemSound.quest_middle");
									}
									else
									{
										SoundEffect(c2, "ItemSound.quest_itemget");
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
			code_info.getCode();
		}
		super.onEvtDead(attacker);
	}

}