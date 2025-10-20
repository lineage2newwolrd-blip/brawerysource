package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class grave_sentinel extends warrior_aggressive_physicalspecial
{
	public grave_sentinel(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c0 = null;
		Creature c1 = null;
		Creature c2 = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i1 = 0;
		int i2 = 0;
		int i3 = 0;
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
		if (AiUtils.HaveMemo(target, 634) == 1)
		{
			always_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 634) == 1)
		{
			always_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 634) == 1)
				{
					always_list.SetInfo(1, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(385);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(1000) < 342)
						{
							c1 = GetLastAttacker();
							if (c1.getPlayer() !=null)
							{
								c1 = c1.getPlayer();
							}
							i1 = AiUtils.Party_GetCount(c1);
							i2 = 0;
							if (i1 == 0 && AiUtils.HaveMemo(c1, 385)!=0)
							{
								c2 = c1;
							}
							for (i0 = 0; i0 < i1; i0++)
							{
								c0 = AiUtils.Party_GetCreature(c1, i0);
								if (AiUtils.HaveMemo(c0, 385)!=0)
								{
									i3 = AiUtils.Rand(1000);
									if (i2 < i3)
									{
										i2 = i3;
										c2 = c0;
									}
								}
							}
							if (!IsNullCreature(c2) && DistFromMe(c2) <= 1500)
							{
								c2.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c2, 5902, 1);
								SoundEffect(c2, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(634);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i0 = AiUtils.FloatToInt(0.150000f * actor.getLevel() + 1.600000f);
						if (AiUtils.Rand(100) >= 10)
						{
							i0 = 0;
						}
						if (i0 > 0)
						{
							GiveItem1(target, 7079, i0);
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

}