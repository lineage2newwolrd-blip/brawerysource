package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.ai.maker.default_maker;
import org.mmocore.gameserver.utils.AiUtils;

public class swordman_party_leader_named_monster extends party_leader_aggressive
{
	public int PhysicalSpecial = 458752001;
	public int W_ShortRangeDDMagic = 458752001;
	public int W_LongRangeDDMagic = 458752001;
	public int debuff = 458752001;
	public String maker_name = "innadril22_2422_024m3";

	public swordman_party_leader_named_monster(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		CreateOnePrivateEx(22290, "warrior_use_skill", 0, 0, AiUtils.FloatToInt(actor.getX() + 20), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
		CreateOnePrivateEx(22290, "warrior_use_skill", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY() + 20), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		if (DistFromMe(attacker) > 200)
		{
			if (AiUtils.Rand(100) < 33)
			{
				AddUseSkillDesire(attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		else if (AiUtils.Rand(100) < 33)
		{
			AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
		}
		else if (AiUtils.Rand(100) < 33)
		{
			AddUseSkillDesire(attacker, debuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		int i0 = 0;
		default_maker maker0 = null;

		i0 = InstantZone_GetId();
		maker0 = AiUtils.InstantZone_GetNpcMaker(i0, maker_name);
		if (!AiUtils.IsNull(maker0))
		{
			AiUtils.SendMakerScriptEvent(maker0, 12548, 0, 0);
		}
		super.onEvtDead(attacker);
	}

}