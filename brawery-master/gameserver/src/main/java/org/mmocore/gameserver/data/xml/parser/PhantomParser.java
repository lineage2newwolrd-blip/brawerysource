package org.mmocore.gameserver.data.xml.parser;

import org.jdom2.Element;
import org.mmocore.commons.data.xml.AbstractFileParser;
import org.mmocore.gameserver.configuration.config.ServerConfig;
import org.mmocore.gameserver.data.client.holder.NpcNameLineHolder;
import org.mmocore.gameserver.data.xml.holder.PhantomHolder;
import org.mmocore.gameserver.database.dao.impl.CharacterDAO;
import org.mmocore.gameserver.utils.Util;

import java.io.File;

/**
 * Created by Hack
 * Date: 22.08.2016 6:32
 */
public class PhantomParser extends AbstractFileParser<PhantomHolder> {
    private static final PhantomParser instance = new PhantomParser();

    private PhantomParser() {
        super(PhantomHolder.getInstance());
    }

    public static PhantomParser getInstance() {
        return instance;
    }

    @Override
    public File getXMLFile() {
        return new File(ServerConfig.DATAPACK_ROOT, "data/xmlscript/phantoms/phantoms.xml");
    }

    @Override
    public String getDTDFileName() {
        return "phantoms.dtd";
    }

    @Override
    protected void readData(PhantomHolder holder, Element rootElement) throws Exception {
        for (Element phantom : rootElement.getChildren("phantom")) {
            String name = phantom.getAttributeValue("name");
            if (!Util.isMatchingRegexp(name, ServerConfig.CNAME_TEMPLATE)) {
                continue;
            } else if (NpcNameLineHolder.getInstance().isBlackListContainsName(name) || !Util.checkIsAllowedName(name)) {
                continue;
            } else if (CharacterDAO.getInstance().getObjectIdByName(name) > 0 || PhantomHolder.getInstance().isNameExists(name)) {
                continue;
            }
            holder.addPhantomName(name);
        }
    }
}
