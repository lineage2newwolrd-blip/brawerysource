package org.mmocore.gameserver.configuration.parser.chatFilter;

import org.mmocore.gameserver.configuration.config.chatFilter.ChatFilterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * Create by iRock on 20.01.2019.
 */
@Deprecated
public class SpamWordsConfigParser {
    private static final Logger _log = LoggerFactory.getLogger(SpamWordsConfigParser.class);
    private static final SpamWordsConfigParser instance = new SpamWordsConfigParser();
    private final String configFile = "configuration/chatFilter/spamwords.txt";

    public static SpamWordsConfigParser getInstance() {
        return instance;
    }

    public void load() {
        final List<Pattern> tmp = new ArrayList<>();
        LineNumberReader lnr = null;
        try {
            String line;
            final File file = new File(configFile);
            lnr = new LineNumberReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            while ((line = lnr.readLine()) != null) {
                final StringTokenizer st = new StringTokenizer(line, "\n\r");
                if (st.hasMoreTokens()) {
                    tmp.add(Pattern.compile(".*" + st.nextToken() + ".*", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE));
                }
            }
            ChatFilterConfig.SPAMWORD_LIST = tmp.toArray(new Pattern[tmp.size()]);
            tmp.clear();
            _log.info(file.getName() + " loaded " + ChatFilterConfig.SPAMWORD_LIST.length + " spam words.");
        } catch (IOException e1) {
            _log.warn("Error reading abuse: " + e1);
        } finally {
            try {
                if (lnr != null) {
                    lnr.close();
                }
            } catch (Exception e2) {
                // nothing
            }
        }
    }

    public void reload() {
        load();
    }
}
