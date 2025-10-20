package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;

public class party_private_couple extends party_private
{
	public int MagicHeal = 266403841;
	public int SelfBuff = 266403841;

	public party_private_couple(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtPartyDead(Creature attacker, NpcInstance privat)
	{
		Creature c0 = null;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (privat != actor)
		{
			if (DistFromMe(privat) < 100)
			{
				c0 = top_desire_target;
				if (Skill_GetConsumeMP(MagicHeal) < actor._currentMp && Skill_GetConsumeHP(MagicHeal) < actor.currentHp && Skill_InReuseDelay(MagicHeal) == 0)
				{
					AddUseSkillDesire(actor, MagicHeal, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				if (Skill_GetConsumeMP(SelfBuff) < actor._currentMp && Skill_GetConsumeHP(SelfBuff) < actor.currentHp && Skill_InReuseDelay(SelfBuff) == 0)
				{
					AddUseSkillDesire(actor, SelfBuff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				if (!IsNullCreature(c0))
				{
					RemoveAllAttackDesire();
					AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
		}
		super.onEvtPartyDead(attacker, privat);
	}

}