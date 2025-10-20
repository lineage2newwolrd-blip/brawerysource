package org.mmocore.gameserver.object.components.npc.superPoint;

public enum SuperPointRail {
    FollowRail, // От А до Б
    Random, // - Рандомные точки
    FollowRail_Restart, // - Путь с перезапуском
    FollowRail_Restart_Teleport, // - Путь с телепортом и перезапуском
}