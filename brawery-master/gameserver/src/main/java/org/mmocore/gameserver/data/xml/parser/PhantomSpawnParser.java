package org.mmocore.gameserver.data.xml.parser;

import org.jdom2.Element;
import org.mmocore.commons.data.xml.AbstractFileParser;
import org.mmocore.commons.geometry.CustomPolygon;
import org.mmocore.gameserver.configuration.config.ServerConfig;
import org.mmocore.gameserver.data.xml.holder.PhantomSpawnHolder;
import org.mmocore.gameserver.model.Territory;
import org.mmocore.gameserver.object.components.npc.superPoint.SuperPoinCoordinate;
import org.mmocore.gameserver.object.components.npc.superPoint.SuperPoint;
import org.mmocore.gameserver.phantoms.ai.PhantomAiType;
import org.mmocore.gameserver.phantoms.template.PhantomSpawnTemplate;
import org.mmocore.gameserver.templates.item.ItemTemplate;
import org.mmocore.gameserver.utils.Location;

import java.io.File;
import java.util.List;

/**
 * Created by Hack
 * Date: 22.08.2016 3:55
 */
public class PhantomSpawnParser extends AbstractFileParser<PhantomSpawnHolder> {
    private static final PhantomSpawnParser instance = new PhantomSpawnParser();

    private PhantomSpawnParser() {
        super(PhantomSpawnHolder.getInstance());
    }

    public static PhantomSpawnParser getInstance() {
        return instance;
    }

    @Override
    public File getXMLFile() {
        return new File(ServerConfig.DATAPACK_ROOT, "data/xmlscript/phantoms/phspawn.xml");
    }

    @Override
    public String getDTDFileName() {
        return "phspawn.dtd";
    }

    @Override
    protected void readData(PhantomSpawnHolder holder, Element rootElement) throws Exception {
        for (Element spawn : rootElement.getChildren("spawn")) {
            PhantomSpawnTemplate template = new PhantomSpawnTemplate();
            template.setType(PhantomAiType.valueOf(spawn.getAttributeValue("type")));
            template.setCount(Integer.parseInt(spawn.getAttributeValue("count")));
            template.setGradeMin(ItemTemplate.Grade.valueOf(spawn.getAttributeValue("gradeMin")));
            template.setGradeMax(ItemTemplate.Grade.valueOf(spawn.getAttributeValue("gradeMax")));

            for (final Element subElement : spawn.getChildren()) {
                if ("point".equalsIgnoreCase(subElement.getName())) {
                    final int x = Integer.parseInt(subElement.getAttributeValue("x"));
                    final int y = Integer.parseInt(subElement.getAttributeValue("y"));
                    final int z = Integer.parseInt(subElement.getAttributeValue("z"));
                    template.addSpawnRange(new Location(x, y, z));
                } else if ("territory".equalsIgnoreCase(subElement.getName())) {
                    List<Element> coords = subElement.getChildren("add");
                    CustomPolygon poly = new CustomPolygon(coords.size());

                    // CustomPolygon can hold only 2d points. Z-coordinates contains only min and max value for whole shape.
                    int zmin = Integer.MAX_VALUE;
                    int zmax = Integer.MIN_VALUE;
                    for (Element add : coords) {
                        poly.add(Integer.parseInt(add.getAttributeValue("x")), Integer.parseInt(add.getAttributeValue("y")));
                        zmin = Math.min(zmin, Integer.parseInt(add.getAttributeValue("zmin")));
                        zmax = Math.max(zmax, Integer.parseInt(add.getAttributeValue("zmax")));
                    }
                    poly.setZmin(zmin);
                    poly.setZmax(zmax);
                    template.addSpawnRange(new Territory().add(poly));
                } else if ("superPoint".equalsIgnoreCase(subElement.getName())) {
                    final SuperPoint superPoint = new SuperPoint("phantom");
                    for (final Element pointElement : subElement.getChildren("step")) {
                        final int x = Integer.parseInt(pointElement.getAttributeValue("x"));
                        final int y = Integer.parseInt(pointElement.getAttributeValue("y"));
                        final int z = Integer.parseInt(pointElement.getAttributeValue("z"));
                        final SuperPoinCoordinate coords = new SuperPoinCoordinate(x, y, z);

                        final Element delayElement = pointElement.getChild("delay");
                        if (delayElement != null)
                            coords.setDelayInSec(Integer.parseInt(delayElement.getAttributeValue("sec", "0")));

                        superPoint.addMoveCoordinats(coords);
                    }
                    template.setSuperPoint(superPoint);
                }
            }
            holder.addSpawn(template);
        }
    }
}
