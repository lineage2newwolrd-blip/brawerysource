package org.mmocore.gameserver.scripts.npc.model.events.fightClub;

import org.mmocore.gameserver.ai.CtrlIntention;
import org.mmocore.gameserver.network.lineage.serverpackets.ActionFail;
import org.mmocore.gameserver.network.lineage.serverpackets.Moving.ValidateLocation;
import org.mmocore.gameserver.network.lineage.serverpackets.MyTargetSelected;
import org.mmocore.gameserver.network.lineage.serverpackets.StatusUpdate;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.model.entity.events.impl.fightclub.DominationEvent;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.tables.SkillTable;
import org.mmocore.gameserver.templates.npc.NpcTemplate;


public class BaseInstance extends NpcInstance
{
    private static final long serialVersionUID = -2427852972704727317L;
    private static final SkillEntry DOMINATION_SKILL = SkillTable.getInstance().getSkillEntry(427, 1);
    public BaseInstance(int objectId, NpcTemplate template)
    {
        super(objectId, template);
        startEffectImmunity();
        startHealBlocked();
        //startDamageBlocked();
    }

    @Override
    public void onAction(Player player, boolean shift)
    {
        if (!isTargetable())
        {
            player.sendActionFailed();
            return;
        }

        if (player.getTarget() != this)
        {
            player.setTarget(this);
            if (player.getTarget() == this)
                player.sendPacket(new MyTargetSelected(getObjectId(), player.getLevel() - getLevel()), makeStatusUpdate(StatusUpdate.CUR_HP, StatusUpdate.MAX_HP));

            player.sendPacket(new ValidateLocation(this), ActionFail.STATIC);
            return;
        }

        if (player.isSitting() || player.isAlikeDead())
            return;

        player.sendActionFailed();
        player.stopMove(false);

        if (player.isInFightClub())
        {
            if (player.getFightClubEvent() instanceof DominationEvent) {
                NpcInstance base = ((DominationEvent) player.getFightClubEvent()).getBaseInstance(player, this);
                if(base!=null){
                    player.getAI().Cast(DOMINATION_SKILL, base, true, false);
                    return;
                }
            }
        }

        if (!isInRange(player, DOMINATION_SKILL.getTemplate().getCastRange()))
        {
            if (player.getAI().getIntention() != CtrlIntention.AI_INTENTION_INTERACT)
                player.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, this, null);
        }
    }

    @Override
    public void onSeeSpell(SkillEntry skill, Creature caster) {

        if (isDead() || caster.getCastingTarget() != this || skill.getId()!=427)
            return;
        Player player = (Player)caster;
        if(player.isInFightClub() && player.getFightClubEvent() instanceof DominationEvent)
        {
            player.setLastActiveTime();
            ((DominationEvent)player.getFightClubEvent()).castingBase(player, this);
        }
    }
}
