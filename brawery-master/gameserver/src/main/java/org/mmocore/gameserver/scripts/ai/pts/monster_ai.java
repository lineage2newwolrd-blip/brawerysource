package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;

public class monster_ai extends default_npc
{
	public int Party_Type = 0;
	public int Party_Loyalty = 0;
	public String Privates = "";
	public int Party_OneShot = 0;
	public int ShoutMsg1 = 0;
	public int ShoutMsg1_FString = 0;
	public int ShoutMsg1_Probablity = 0;
	public int ShoutMsg2 = 0;
	public int ShoutMsg2_FString = 0;
	public int ShoutMsg2_Probablity = 0;
	public int ShoutMsg3 = 0;
	public int ShoutMsg3_FString = 0;
	public int ShoutMsg3_Probablity = 0;
	public int ShoutMsg4 = 0;
	public int ShoutMsg4_FString = 0;
	public int ShoutMsg4_Probablity = 0;
	public int Social0 = 0;
	public int MoveAroundSocial = 0;
	public int Social0_Probablity = 2000;
	public int Social0_Timer = 10000;
	public int Social1 = 1;
	public int MoveAroundSocial1 = 0;
	public int Social1_Probablity = 2000;
	public int Social1_Timer = 10000;
	public int Social2 = 2;
	public int MoveAroundSocial2 = 0;
	public int Social2_Probablity = 2000;
	public int Social2_Timer = 10000;
	public int OutOfTerritory = 0;

	public monster_ai(final NpcInstance actor){super(actor);}

}