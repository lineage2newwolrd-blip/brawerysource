package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.SkillInfo;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class ai_tantaar_frog extends warrior_flee
{
	public int Max_Desire = 10000000;
	@SkillInfo(id = 7000, level = 1)
	public int Skill01_ID;
	public int babble_mode = 0;

	public ai_tantaar_frog(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai1 = 0;
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		final NpcInstance actor = getActor();

		if (!IsNullCreature(attacker))
		{
			c_ai0 = attacker;
		}
		if (i_ai1 == 0)
		{
			if (!IsNullCreature(attacker) && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()) || IsInCategory(Category.pet_group, attacker.getClassId())))
			{
				if (babble_mode !=0)
				{
					Say("buffer out");
				}
				CreateOnePrivateEx(18918, "ai_tantaar_vegetation_buffer", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, attacker.getObjectId(), 3, 0);
			}
			i_ai1 = 1;
			ChangeStatus(Status.TARGET_DISABLE);
			Suicide();
		}
	}

	@Override
	protected void onEvtSpelled(int skill_name_id, Creature speller)
	{
		final NpcInstance actor = getActor();

		super.onEvtSpelled(skill_name_id, speller);
		if (babble_mode !=0)
		{
			Say("SPELLED:" + (skill_name_id / (256 * 256)));
		}
		if (skill_name_id == 421199873)
		{
			if (babble_mode !=0)
			{
				Say("s_lizard_grasslands_fungus1 hit");
			}
			AddUseSkillDesire(actor, 433979393/*@skill_6622_1*/, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, Max_Desire);
		}
	}

}