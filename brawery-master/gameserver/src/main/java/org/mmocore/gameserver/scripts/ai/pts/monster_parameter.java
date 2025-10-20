package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;

public class monster_parameter extends default_npc
{
	public int AttackLowLevel = 0;
	public int RunAway = 1;
	public int SetCurse = 0;
	public int AttackLowHP = 0;
	public int HelpHeroSilhouette = 0;
	public String HelpHeroAI = "warrior_hero";
	public int SetAggressiveTime = -1;
	public int HalfAggressive = 0;
	public int RandomAggressive = 0;
	public int SetHateGroup = -1;
	public int SetHateGroupRatio = 0;
	public int SetHateOccupation = -1;
	public int SetHateOccupationRatio = 0;
	public int SetHateRace = -1;
	public int SetHateRaceRatio = 0;
	public int IsTransform = 0;
	public int step1 = 1020130;
	public int step2 = 1020006;
	public int step3 = 1020853;
	public int DaggerBackAttack = 458752001;
	public int IsVs = 0;
	public int SpecialSkill = 458752001;
	public int MoveAroundSocial = 0;
	public int MoveAroundSocial1 = 0;
	public int MoveAroundSocial2 = 0;
	public int IsSay = 0;
	public int ShoutMsg1 = 0;
	public int ShoutMsg2 = 0;
	public int ShoutMsg3 = 0;
	public int ShoutMsg4 = 0;
	public int SSQLoserTeleport = 0;
	public int SSQTelPosX = 0;
	public int SSQTelPosY = 0;
	public int SSQTelPosZ = 0;
	public int SwapPosition = 0;
	public int FriendShip = 0;
	public int DungeonType = 0;
	public int DungeonTypeAI = 0;
	public String DungeonTypePrivate = "";
	public int ShoutTarget = 0;
	public int AcceptShoutTarget = 0;
	public int SelfExplosion = 0;
	public int FriendShip1 = 0;
	public int FriendShip2 = 0;
	public int FriendShip3 = 0;
	public int FriendShip4 = 0;
	public int FriendShip5 = 0;
	public int SoulShot = 0;
	public int SoulShotRate = 0;
	public int SpiritShot = 0;
	public int SpiritShotRate = 0;
	public float SpeedBonus = 0f;
	public float HealBonus = 0f;
	public int CreviceOfDiminsion = 0;
	public int LongRangeGuardRate = -1;
	public int SeeCreatureAttackerTime = -1;

	public monster_parameter(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAtkFinished(Creature target)
	{

		if (!target.isAlive() && !target.isPlayer())
		{
			if (IsInCategory(Category.summon_npc_group, target.getClassId()))
			{
				AddAttackDesire(target.getPlayer(), DesireMove.MOVE_TO_TARGET, 500);
			}
		}
	}

}