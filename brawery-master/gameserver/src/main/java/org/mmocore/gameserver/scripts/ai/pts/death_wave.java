package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class death_wave extends warrior_ag_casting_3skill_magical2
{
	public death_wave(final NpcInstance actor){super(actor);}

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

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		always_list.SetInfo(0, target);
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(374);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c0 = GetLastAttacker();
					if (c0.getPlayer() !=null)
					{
						c0 = c0.getPlayer();
					}
					i1 = AiUtils.Party_GetCount(c0);
					if (i1 != 0)
					{
						for (i0 = 0; i0 < i1; i0++)
						{
							c1 = AiUtils.Party_GetCreature(c0, i0);
							i2 = AiUtils.Rand(1000);
							if (AiUtils.Rand(1000) < 2 && AiUtils.HaveMemo(c1, 374)!=0 && AiUtils.GetMemoState(c1, 374) != 3 && AiUtils.OwnItemCount(c1, 5886) < 1 && DistFromMe(c1) <= 1500)
							{
								GiveItem1(c1, 5886, 1);
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
							if (i2 > i3 && AiUtils.HaveMemo(c1, 374)!=0)
							{
								i3 = i2;
								c2 = c1;
							}
						}
					}
					else
					{
						c2 = c0;
					}
					if (AiUtils.HaveMemo(c2, 374)!=0 && !IsNullCreature(c2) && DistFromMe(c2) <= 1500)
					{
						if (AiUtils.Rand(100) < 59 && AiUtils.OwnItemCount(c2, 5885) < 65)
						{
							if (AiUtils.OwnItemCount(c2, 5885) == 64)
							{
								GiveItem1(c2, 5885, 1);
								SoundEffect(c2, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(c2, 5885, 1);
								SoundEffect(c2, "ItemSound.quest_itemget");
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