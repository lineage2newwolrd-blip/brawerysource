package handler.bbs.custom;

import handler.bbs.ScriptBbsHandler;
import org.mmocore.gameserver.configuration.config.community.CBasicConfig;
import org.mmocore.gameserver.model.entity.events.fightclubmanager.FightClubEventManager;
import org.mmocore.gameserver.model.entity.events.fightclubmanager.FightClubLastPlayerStats;
import org.mmocore.gameserver.model.entity.events.fightclubmanager.FightClubLastStatsManager;
import org.mmocore.gameserver.model.entity.events.impl.AbstractFightClub;
import org.mmocore.gameserver.network.lineage.components.CustomMessage;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.utils.Language;
import org.mmocore.gameserver.utils.Util;

import java.util.List;

import static org.mmocore.gameserver.configuration.config.EventsConfig.ALLOW_FIGHT_CLUB;

/**
 * @author VISTALL
 * @date 15:16/06.09.2011
 */
public class CommunityEvents extends ScriptBbsHandler
{
	@Override
	public String[] getBypassCommands()
	{
		return new String[] { "_bbsevent" };
	}

	@Override
	public void onBypassCommand(Player player, String bypass)
	{
		if(!ALLOW_FIGHT_CLUB)
		{
			player.sendMessage(new CustomMessage("scripts.services.off"));
			useCommand(player, "_bbshome");
			return;
		}

		if(bypass.equals("_bbsevent"))
		{
			showEventPage(player);

		}
		else if(bypass.startsWith("_bbsevent:unreg"))
		{
			FightClubEventManager.getInstance().unsignFromEvent(player);
			useCommand(player, "_bbsevent");
		}
		else if(bypass.startsWith("_bbsevent:reg"))
		{
			AbstractFightClub event = FightClubEventManager.getInstance().getCurrentEvent();

			if(event == null)
				event = FightClubEventManager.getInstance().getNextEvent();

			FightClubEventManager.getInstance().trySignForEvent(player, event, true);
			useCommand(player, "_bbsevent");
		}
	}

	private void showEventPage(Player player) {
		String html = getCache().getHtml(CBasicConfig.BBS_PATH + "/events/index.htm", player);
		AbstractFightClub event = FightClubEventManager.getInstance().getCurrentEvent();

		if(event == null)
			event = FightClubEventManager.getInstance().getNextEvent();

		if(event != null)
		{
			html = html.replace("%eventIcon%", event.getIcon());
			html = html.replace("%eventName%", event.getName());
			html = html.replace("%eventDesc%", event.getDescription());
		}
		else
		{
			html = html.replace("%eventIcon%", "icon.NOIMAGE");
			html = html.replace("%eventName%", "...");
			html = html.replace("%eventDesc%", "Loading event engine...");
		}

		String register;
		if(event == null)
			register = player.getLanguage() == Language.RUSSIAN ? "<br><font color=\"FF0000\">Евент не загружен!</font>":"<br><font color=\"FF0000\">Event not loaded yet!</font>";
		else if(!FightClubEventManager.getInstance().isRegistrationOpened(event))
			register = player.getLanguage() == Language.RUSSIAN ? "<br><font color=\"FF0000\">Регистрация закрыта!</font>":"<br><font color=\"FF0000\">Registration Closed</font>";
		else if(FightClubEventManager.getInstance().isPlayerRegistered(player))
			register = "<button value=\""+(player.getLanguage() == Language.RUSSIAN ?"Отменить регистрацию":"Unregister from Event") + "\" action=\"bypass _bbsevent:unreg\" width=165 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\">";
		else
			register = "<button value=\""+(player.getLanguage() == Language.RUSSIAN ?"Регистрация":"Register to Event") +"\" action=\"bypass _bbsevent:reg\" width=165 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\">";

		html = html.replace("<?event_action?>", register);

		StringBuilder content = new StringBuilder();
		String template = getCache().getHtml(CBasicConfig.BBS_PATH + "/events/template.htm", player);
		List<FightClubLastPlayerStats> stats = FightClubLastStatsManager.getInstance().getStats(true);
		for(int i = 0; i < 9; i++)
		{
			String block = template;
			block = block.replace("<?color?>", i%2 == 0 ? "333333": "444444");
			block = block.replace("<?num?>", String.valueOf(i+1));
			if((i + 1) <= stats.size())
			{
				FightClubLastPlayerStats stat = stats.get(i);
				if(stat.isMyStat(player))
				{
					block = block.replace("<?name?>", "<fonr color=\"CC3333\">" + stat.getPlayerName() + "</font>");
					block = block.replace("<?count?>", "<fonr color=\"CC3333\">" + Util.formatAdena(stat.getScore()) + "</font>");
					block = block.replace("<?class?>", "<fonr color=\"CC3333\">" + stat.getClassName() + "</font>");
					block = block.replace("<?clan_?>", "<fonr color=\"CC3333\">" + stat.getClanName() + "</font>");
					block = block.replace("<?ally?>", "<fonr color=\"CC3333\">" + stat.getAllyName() + "</font>");
				}
				else
				{
					block = block.replace("<?name?>", stat.getPlayerName());
					block = block.replace("<?count?>", Util.formatAdena(stat.getScore()));
					block = block.replace("<?class?>", stat.getClassName());
					block = block.replace("<?clan?>", stat.getClanName());
					block = block.replace("<?ally?>", stat.getAllyName());
				}
			}
			else
			{
				block = block.replace("<?name?>", "...");
				block = block.replace("<?count?>", "...");
				block = block.replace("<?class?>", "...");
				block = block.replace("<?clan?>", "...");
				block = block.replace("<?ally?>", "...");
			}
			content.append(block).append("\n");
		}

		template = getCache().getHtml(CBasicConfig.BBS_PATH + "/events/template_me.htm", player);
		FightClubLastPlayerStats my = FightClubLastStatsManager.getInstance().getMyStat(player);
		if(my != null)
		{
			template = template.replace("<?name?>", "<fonr color=\"CC3333\">" + my.getPlayerName() + "</font>");
			template = template.replace("<?count?>", "<fonr color=\"CC3333\">" + Util.formatAdena(my.getScore()) + "</font>");
			template = template.replace("<?class?>", "<fonr color=\"CC3333\">" + my.getClassName() + "</font>");
			template = template.replace("<?clan?>", "<fonr color=\"CC3333\">" + my.getClanName() + "</font>");
			template = template.replace("<?ally?>", "<fonr color=\"CC3333\">" + my.getAllyName() + "</font>");
		}
		else
		{
			template = template.replace("<?name?>", "...");
			template = template.replace("<?count?>", "...");
			template = template.replace("<?class?>", "...");
			template = template.replace("<?clan?>", "...");
			template = template.replace("<?ally?>", "...");
		}
		content.append(template).append("\n");
		html = html.replace("<?stat?>", content);
		separateAndSend(html, player);
	}

	@Override
	public void onWriteCommand(Player player, String bypass, String arg1, String arg2, String arg3, String arg4, String arg5)
	{
		//
	}
}
