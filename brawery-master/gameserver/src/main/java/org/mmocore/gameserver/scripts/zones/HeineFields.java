package org.mmocore.gameserver.scripts.zones;

import org.mmocore.commons.geometry.CustomPolygon;
import org.mmocore.commons.threading.RunnableImpl;
import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.listener.script.OnInitScriptListener;
import org.mmocore.gameserver.model.Territory;
import org.mmocore.gameserver.object.components.items.ItemInstance;
import org.mmocore.gameserver.utils.ItemFunctions;
import org.mmocore.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class HeineFields implements OnInitScriptListener
{
    private static final Logger _log = LoggerFactory.getLogger(HeineFields.class);

    private static final int _herbs[] = {
            14824,//Slayer - 1 hit kill
            14825,//Immortal - Invul to Mobs
            14826,//Terminator - 150% Cast
            14827//Guide - 250 Speed
    };
    private static final Territory[] _heineTerritory = {
            new Territory().add(new CustomPolygon(4).add(84232, 227256).add(85864, 227896).add(87992, 225384).add(85784, 226136).setZmin(-3760).setZmax(-3700)),
            new Territory().add(new CustomPolygon(5).add(82904, 223848).add(83176, 225272).add(86744, 224120).add(86168, 223416).add(85000, 224040).setZmin(-3760).setZmax(-3700)),
            new Territory().add(new CustomPolygon(6).add(81880, 222840).add(83592, 221528).add(82328, 220808).add(80568, 220904).add(80744, 221624).add(82168, 221592).setZmin(-3760).setZmax(-3700)),
            new Territory().add(new CustomPolygon(4).add(91112, 221256).add(92808, 220600).add(93560, 219064).add(91768, 219416).setZmin(-3760).setZmax(-3700)),
            new Territory().add(new CustomPolygon(4).add(88888, 220312).add(89752, 218376).add(88392, 216792).add(87816, 216952).setZmin(-3760).setZmax(-3700)),
            new Territory().add(new CustomPolygon(7).add(85640, 215752).add(84664, 215304).add(85720, 212328).add(87528, 210312).add(88072, 210680).add(86904, 211960).add(86232, 213080).setZmin(-3760).setZmax(-3700)),
            new Territory().add(new CustomPolygon(4).add(92568, 210600).add(94248, 209944).add(94248, 208216).add(92984, 208936).setZmin(-3760).setZmax(-3700)),
            new Territory().add(new CustomPolygon(5).add(83896, 212616).add(82424, 214104).add(81992, 215960).add(81208, 213880).add(81864, 213224).setZmin(-3760).setZmax(-3700)),
            new Territory().add(new CustomPolygon(3).add(81992, 217720).add(81032, 219860).add(79512, 218440).setZmin(-3760).setZmax(-3700)),
            new Territory().add(new CustomPolygon(3).add(83000, 206936).add(85816, 206168).add(85016, 205240).setZmin(-3760).setZmax(-3700)),

            new Territory().add(new CustomPolygon(5).add(86072, 193848).add(85752, 192344).add(87576, 190792).add(87864, 192232).add(86904, 192648).setZmin(-3760).setZmax(-3700)),
            new Territory().add(new CustomPolygon(4).add(91528, 189416).add(92824, 189160).add(92168, 188104).add(90504, 187832).setZmin(-3760).setZmax(-3700)),
            new Territory().add(new CustomPolygon(4).add(87916, 187096).add(86040, 187096).add(84872, 185720).add(86456, 185224).setZmin(-3760).setZmax(-3700)),
            new Territory().add(new CustomPolygon(4).add(86936, 184200).add(88920, 184152).add(88824, 183064).add(87096, 182808).setZmin(-3760).setZmax(-3700)),
            new Territory().add(new CustomPolygon(6).add(81000, 181272).add(78872, 181064).add(79192, 183032).add(79912, 184408).add(80648, 183960).add(79880, 182040).setZmin(-3760).setZmax(-3700)),
            new Territory().add(new CustomPolygon(5).add(82472, 180792).add(84152, 179912).add(85368, 179752).add(84824, 181096).add(82920, 182120).setZmin(-3760).setZmax(-3700)),
            new Territory().add(new CustomPolygon(3).add(84264, 175480).add(85208, 176920).add(85512, 174616).setZmin(-3760).setZmax(-3700)),
            new Territory().add(new CustomPolygon(7).add(86440, 173576).add(86248, 174392).add(87592, 174744).add(88776, 173976).add(88680, 173368).add(87816, 174072).add(86920, 174056).setZmin(-3760).setZmax(-3700)),
            new Territory().add(new CustomPolygon(3).add(90296, 174120).add(92744, 172792).add(90600, 171064).setZmin(-3760).setZmax(-3700))
    };

    private static List<Territory> _spawnTerritory = new ArrayList<>();

    @Override
	public void onInit()
	{
        for (Territory territory : _heineTerritory) {
            CustomPolygon shape = (CustomPolygon)territory.getTerritories().get(0);
            if (!shape.validate())
                _log.error("HerbsAutoSpawned: invalid territory data : " + shape);
        }
        ThreadPoolManager.getInstance().scheduleAtFixedRate(new spawnHerbs(),5000L, 40 * 1000L);
	}

    private class spawnHerbs extends RunnableImpl {

        @Override
        public void runImpl() {
            shuffleSpawn(_heineTerritory);
            for (Territory territory : _spawnTerritory)
                for (int i = 0; i < Rnd.get(2, 4); i++) {
                    try {
                        ItemInstance item = ItemFunctions.createItem(Rnd.get(_herbs));
                        item.dropMe(null, Location.findAroundPosition(Territory.getRandomLoc(territory), 100, 0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

        }
    }

    private static void shuffleSpawn(Territory[] ar)
    {
        if (ar == null || ar.length == 0)
            return;
        _spawnTerritory.clear();
        int even = Rnd.get(2);
        for (int i=0; i <ar.length; i++)
        {
            if (i % 2 == even) {
                _spawnTerritory.add(ar[i]);
            }
        }
    }

}