package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_param_wizard extends wizard_parameter
{
	public String Privates = "orc:party_private:1:20sec";
	public int SummonPrivateRate = 0;

	public party_leader_param_wizard(final NpcInstance actor)
	{
		super(actor);
		Attack_BoostValue = 300.000000f;
		Attack_DecayRatio = 6.600000f;
		ShoutTarget = 0;
		UseSkill_BoostValue = 100000.000000f;
		AttackLowHP = 0;
		UseSkill_DecayRatio = 66000.000000f;
	}

	@Override
	protected void onEvtSpawn()
	{

		if (SummonPrivateRate == 0)
		{
			CreatePrivates(Privates);
			i_ai2 = 1;
		}
		else
		{
			i_ai2 = 0;
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		float f0 = 0;
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (i_ai2 == 0)
		{
			if (AiUtils.Rand(100) < SummonPrivateRate)
			{
				CreatePrivates(Privates);
				switch (AiUtils.Rand(4))
				{
					case 0:
					{
						Say(AiUtils.MakeFString(1000294, "", "", "", "", ""));
						break;
					}
					case 1:
					{
						Say(AiUtils.MakeFString(1000403, "", "", "", "", ""));
						break;
					}
					case 2:
					{
						Say(AiUtils.MakeFString(1000404, "", "", "", "", ""));
						break;
					}
					case 3:
					{
						Say(AiUtils.MakeFString(1000405, "", "", "", "", ""));
						break;
					}
				}
				i_ai2 = 1;
			}
		}
		if (ShoutTarget == 1)
		{
			if (AiUtils.Rand(100) < 50 && attacker.currentHp / attacker.getMaxHp() * 100 < 40)
			{
				h0 = GetMaxHateInfo(0);
				i0 = 0;
				if (!IsNullHateInfo(h0))
				{
					if (!IsNullCreature(h0.attacker))
					{
						i0 = 1;
					}
				}
				if (i0 == 1)
				{
					if (!IsNullCreature(h0.attacker) && attacker.isPlayer())
					{
						if (h0.attacker == attacker)
						{
							switch (AiUtils.Rand(3))
							{
								case 0:
								{
									Say(AiUtils.MakeFString(1000291, attacker.getName(), "", "", "", ""));
									break;
								}
								case 1:
								{
									Say(AiUtils.MakeFString(1000398, attacker.getName(), "", "", "", ""));
									break;
								}
								case 2:
								{
									Say(AiUtils.MakeFString(1000399, attacker.getName(), "", "", "", ""));
									break;
								}
							}
							RemoveAllHateInfoIF(0, 0);
							if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
							{
								if (GetHateInfoCount() == 0)
								{
									f0 = 0;
									if (SetHateGroup >= 0)
									{
										if (IsInCategory(SetHateGroup, attacker.getClassId()))
										{
											f0 = f0 + SetHateGroupRatio;
										}
									}
									if (attacker.getClassId() == SetHateOccupation)
									{
										f0 = f0 + SetHateOccupationRatio;
									}
									if (SetHateRace == attacker.getRaceId())
									{
										f0 = f0 + SetHateRaceRatio;
									}
									f0 = 1.000000f * damage / (actor.getLevel() + 7) + (f0 / 100 * (1.000000f * damage / (actor.getLevel() + 7)));
									AddHateInfo(attacker, AiUtils.FloatToInt(f0 * 1000 + 300), 0, 1, 1);
								}
								else
								{
									f0 = 0;
									if (SetHateGroup >= 0)
									{
										if (IsInCategory(SetHateGroup, attacker.getClassId()))
										{
											f0 = f0 + SetHateGroupRatio;
										}
									}
									if (attacker.getClassId() == SetHateOccupation)
									{
										f0 = f0 + SetHateOccupationRatio;
									}
									if (SetHateRace == attacker.getRaceId())
									{
										f0 = f0 + SetHateRaceRatio;
									}
									f0 = 1.000000f * damage / (actor.getLevel() + 7) + (f0 / 100 * (1.000000f * damage / (actor.getLevel() + 7)));
									AddHateInfo(attacker, AiUtils.FloatToInt(f0 * 1000), 0, 1, 1);
								}
							}
							actor.flag = AiUtils.GetIndexFromCreature(attacker);
							BroadcastScriptEvent(ScriptEvent.SCE_VALAKAS_ATTACK, AiUtils.GetIndexFromCreature(actor), 300);
						}
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}