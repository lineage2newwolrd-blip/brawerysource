package org.mmocore.gameserver.model.entity.events.impl.fightclub;

import org.mmocore.commons.collections.MultiValueSet;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.model.entity.events.EventType;
import org.mmocore.gameserver.model.entity.events.fightclubmanager.FightClubPlayer;
import org.mmocore.gameserver.model.entity.events.impl.AbstractFightClub;
import org.mmocore.gameserver.utils.Language;

public class TeamVSTeamEvent extends AbstractFightClub
{
	public TeamVSTeamEvent(MultiValueSet<String> set)
	{
		super(set);
	}
	
	@Override
	public void onKilled(Creature actor, Creature victim)
	{
		if (actor != null && actor.isPlayable())
		{
			FightClubPlayer realActor = getFightClubPlayer(actor.getPlayer());
			if (victim.isPlayer() && realActor != null)
			{
				realActor.increaseKills(true);
				realActor.getTeam().incScore(1);
				updatePlayerScore(realActor);
				sendMessageToPlayer(realActor, MESSAGE_TYPES.GM, (realActor.getPlayer().getLanguage() == Language.RUSSIAN ?"Вы убили ":"You have killed ")+victim.getName());
			}
			else if (victim.isPet())
			{
				
			}
			actor.getPlayer().sendUserInfo();
		}
		
		if (victim.isPlayer())
		{
			FightClubPlayer realVictim = getFightClubPlayer(victim);
			if (realVictim != null)
			{
				realVictim.increaseDeaths();
				if (actor != null)
					sendMessageToPlayer(realVictim, MESSAGE_TYPES.GM, (realVictim.getPlayer().getLanguage() == Language.RUSSIAN ?"Вас убил ":"You have been killed by ")+actor.getName());
			}
			victim.broadcastCharInfo();
		}
		
		super.onKilled(actor, victim);
	}
	
	@Override
	public String getVisibleTitle(Player player, String currentTitle, boolean toMe)
	{
		FightClubPlayer fPlayer = getFightClubPlayer(player);
		
		if (fPlayer == null)
			return currentTitle;
		
		return "Kills: "+fPlayer.getKills(true)+" Deaths: "+fPlayer.getDeaths();
	}

    @Override
    public EventType getType()
    {
        return EventType.FIGHT_CLUB_EVENT;
    }

}
