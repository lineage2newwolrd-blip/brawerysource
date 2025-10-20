package org.mmocore.gameserver.stats.conditions;

import org.jts.dataparser.data.holder.TransformHolder;
import org.jts.dataparser.data.holder.transform.TransformData;
import org.mmocore.gameserver.geoengine.GeoEngine;
import org.mmocore.gameserver.manager.ReflectionManager;
import org.mmocore.gameserver.model.Skill;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.SystemMessage;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.components.items.ItemInstance;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.world.World;

import java.util.Optional;
import java.util.OptionalDouble;

/**
 * Create by Mangol on 15.10.2015.
 * П.С. Спасибо PaInKiLlEr из j develop station за конд)
 */
public class Condition_can_transform extends Condition {
    private final int id;

    public Condition_can_transform(final int id) {
        this.id = id;
    }

    @Override
    protected boolean testImpl(final Creature creature, final Optional<Creature> optionalTarget, final Optional<SkillEntry> skill, final Optional<ItemInstance> item, final OptionalDouble initialValue) {
        final Player player = (Player) creature;
        if (player == null || player.getActiveWeaponFlagAttachment() != null) {
            //TODO: найти месагу.
            return false;
        }
        if (player.isTransformed()) {
            player.sendPacket(SystemMsg.YOU_ALREADY_POLYMORPHED_AND_CANNOT_POLYMORPH_AGAIN);
            return false;
        }
		/*		//TODO: надо ли?
		if(Math.abs(player.getZ() - player.getLoc().correctGeoZ().z) > 333)
		{
			player.sendPacket(SystemMsg.THE_NEARBY_AREA_IS_TOO_NARROW_FOR_YOU_TO_POLYMORPH);
			return false;
		}
		*/
        final Optional<TransformData> transform = TransformHolder.getInstance().getTransformId(id);
        if (!transform.isPresent()) {
            player.sendPacket(new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(skill.get()));
            return false;
        }
        if (!checkCollision(player)) {
            player.sendPacket(SystemMsg.THE_NEARBY_AREA_IS_TOO_NARROW_FOR_YOU_TO_POLYMORPH);
            return false;
        }
        if ((player.isInFlyingTransform()
                || skill.get().getId() == Skill.SKILL_FINAL_FLYING_FORM
                || skill.get().getId() == Skill.SKILL_AURA_BIRD_FALCON
                || skill.get().getId() == Skill.SKILL_AURA_BIRD_OWL)
                && (player.getX() > -166168 || player.getZ() <= 0 || player.getZ() >= 6000
                || !player.getReflection().equals(ReflectionManager.DEFAULT))) {
            player.sendPacket(new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(skill.get().getId(), skill.get().getLevel()));
            return false;
        }
        if (player.isInWater() && transform.get().can_swim == 0) {
            player.sendPacket(SystemMsg.YOU_CANNOT_POLYMORPH_INTO_THE_DESIRED_FORM_IN_WATER);
            return false;
        }
		/*
		if(player.isActionBlocked(Zone.ACTION_TRANSFORMABLE))
		{
			player.sendPacket(SystemMsg.THE_NEARBY_AREA_IS_TOO_NARROW_FOR_YOU_TO_POLYMORPH);
			return false;
		}
		*/
        if (player.isBlockTransform()) {
            player.sendPacket(SystemMsg.YOU_ARE_STILL_UNDER_TRANSFORM_PENALTY_AND_CANNOT_BE_POLYMORPHED);
            return false;
        }

/*		TODO[Hack]: найти условия, при которых нельзя входить в трансформу с петом.
		Проверка на рпг-клабе показала, что с петом можно входить в авангард, трансформы сабов и трансформы свитков.
		Так же вход работал и с призванной курицей

		if(player.getServitor() != null)
		{
			player.sendPacket(SystemMsg.YOU_CANNOT_POLYMORPH_WHEN_YOU_HAVE_SUMMONED_A_SERVITOR_PET);
			return false;
		}
*/

        if (player.isMounted() || player.isRiding() || player.getMountType() == 2) {
            player.sendPacket(SystemMsg.YOU_CANNOT_POLYMORPH_WHILE_RIDING_A_PET);
            return false;
        }
        if (player.isBuffImmune()) {
            player.sendPacket(SystemMsg.YOU_CANNOT_POLYMORPH_WHILE_UNDER_THE_EFFECT_OF_A_SPECIAL_SKILL);
            return false;
        }
        if (player.isInBoat()) {
            player.sendPacket(SystemMsg.YOU_CANNOT_POLYMORPH_WHILE_RIDING_A_BOAT);
            return false;
        }
        if (player.isSitting()) {
            player.sendPacket(SystemMsg.YOU_CANNOT_TRANSFORM_WHILE_SITTING);
            return false;
        }
        // Нельзя отменять летающую трансформу слишком высоко над землей
        if (player.isInFlyingTransform() && skill.get().getId() == Skill.SKILL_TRANSFORM_DISPEL
                && Math.abs(player.getZ() - player.getLoc().correctGeoZ().z) > 333) {
            player.sendPacket(new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(skill.get().getId(), skill.get().getLevel()));
            return false;
        }
        // Для трансформации у игрока не должно быть активировано умение Mystic Immunity.
        if (player.getEffectList().getEffectsBySkillId(Skill.SKILL_MYSTIC_IMMUNITY) != null) {
            player.sendPacket(SystemMsg.YOU_CANNOT_POLYMORPH_WHILE_UNDER_THE_EFFECT_OF_A_SPECIAL_SKILL);
            return false;
        }
        return transform.isPresent();
    }

    private static boolean checkCollision(Player player){
        byte NSWE = GeoEngine.getNSWE(player.getX(), player.getY(), player.getZ(), player.getGeoIndex());
        if (NSWE != GeoEngine.NSWE_ALL) {
            int factor = 0;
            if ((NSWE & GeoEngine.EAST) == 0) {
                factor += 1;
            }
            if ((NSWE & GeoEngine.WEST) == 0) {
                factor += 1;
            }
            if ((NSWE & GeoEngine.SOUTH) == 0) {
                factor += 1;
            }
            if ((NSWE & GeoEngine.NORTH) == 0) {
                factor += 1;
            }
            if(factor >= 2)
                return false;

            int geoX = player.getX() - World.MAP_MIN_X >> 4;
            int geoY = player.getY() - World.MAP_MIN_Y >> 4;
            if((NSWE & GeoEngine.EAST) == 0 && (GeoEngine.NgetNSWE(geoX - 1, geoY, player.getZ(), player.getGeoIndex()) & GeoEngine.WEST) == 0)
                return false;
            if((NSWE & GeoEngine.WEST) == 0 && (GeoEngine.NgetNSWE(geoX + 1, geoY, player.getZ(), player.getGeoIndex()) & GeoEngine.EAST) == 0)
                return false;
            if((NSWE & GeoEngine.SOUTH) == 0 && (GeoEngine.NgetNSWE(geoX, geoY - 1, player.getZ(), player.getGeoIndex()) & GeoEngine.NORTH) == 0)
                return false;
            if((NSWE & GeoEngine.NORTH) == 0 && (GeoEngine.NgetNSWE(geoX, geoY + 1, player.getZ(), player.getGeoIndex()) & GeoEngine.SOUTH) == 0)
                return false;
        }
        return true;
    }
}
