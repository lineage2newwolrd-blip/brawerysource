package org.mmocore.gameserver.data.xml.parser;

import org.jdom2.Element;
import org.mmocore.commons.collections.MultiValueSet;
import org.mmocore.commons.data.xml.AbstractDirParser;
import org.mmocore.gameserver.configuration.config.ServerConfig;
import org.mmocore.gameserver.data.xml.holder.FightClubMapHolder;
import org.mmocore.gameserver.data.xml.holder.ZoneHolder;
import org.mmocore.gameserver.model.entity.events.fightclubmanager.FightClubMap;
import org.mmocore.gameserver.templates.ZoneTemplate;
import org.mmocore.gameserver.utils.Location;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author VISTALL
 * @date 12:56/10.12.2010
 */
public final class FightClubMapParser extends AbstractDirParser<FightClubMapHolder>
{
	private static final FightClubMapParser _instance = new FightClubMapParser();

	public static FightClubMapParser getInstance()
	{
		return _instance;
	}

	protected FightClubMapParser()
	{
		super(FightClubMapHolder.getInstance());
	}

	@Override
	public File getXMLDir()
	{
		return new File(ServerConfig.DATAPACK_ROOT, "data/xmlscript/fight_club_maps/");
	}

	@Override
	public boolean isIgnored(File f)
	{
		return false;
	}

	@Override
	public String getDTDFileName()
	{
		return "../dtd/maps.dtd";
	}

	@Override
	protected void readData(final FightClubMapHolder holder, Element rootElement) throws Exception
	{
		for (Element map : rootElement.getChildren("map"))
		{
			String name = map.getAttributeValue("name");

			MultiValueSet<String> set = new MultiValueSet<String>();
			set.set("name", name);

			for (Element parameter : map.getChildren("parameter"))
			{
				set.set(parameter.getAttributeValue("name"), parameter.getAttributeValue("value"));
			}

			Map<Integer, Location[]> teamSpawns = null;
			Map<Integer, Map<String, ZoneTemplate>> territories = null;
			Location[] keyLocations = null;
			
			for (Element object : map.getChildren("objects"))
			{
				String objectsName = object.getAttributeValue("name");

				int team = Integer.parseInt(object.getAttributeValue("team", "-1"));
				
				if (objectsName.equals("teamSpawns"))
				{
					if (teamSpawns == null)
						teamSpawns = new HashMap<>();
					teamSpawns.put(team, parseLocations(object));
				}
				else if (objectsName.equals("territory"))
				{
					if (territories == null)
						territories = new HashMap<>();
					territories.put(team, parseTerritory(object));
				}
				else if (objectsName.equals("keyLocations"))
				{
					keyLocations = parseLocations(object);
				}
			}

			holder.addMap(new FightClubMap(set, teamSpawns, territories, keyLocations));
		}
	}

	private Location[] parseLocations(Element element)
	{
		List<Location> locs = new ArrayList<Location>();
		for (Element point : element.getChildren("point"))
		{
			locs.add(Location.parse(point));
		}
		
		Location[] locArray = new Location[locs.size()];
		
		for (int i = 0;i<locs.size();i++)
			locArray[i] = locs.get(i);
		
		return locArray;
	}

	private Map<String, ZoneTemplate> parseTerritory(Element element)
	{
		Map<String, ZoneTemplate> territories = new HashMap<String, ZoneTemplate>();
		for (Element zone : element.getChildren("zone"))
		{
			ZoneTemplate template = ZoneHolder.getInstance().getTemplate(zone.getAttributeValue("name"));
			territories.put(template.getName(), template);
		}
		
		return territories;
	}
}
