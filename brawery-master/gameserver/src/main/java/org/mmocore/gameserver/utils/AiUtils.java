package org.mmocore.gameserver.utils;


import org.jts.dataparser.data.holder.SkillDataHolder;
import org.jts.dataparser.data.holder.skilldata.SkillData;
import org.jts.dataparser.data.holder.skilldata.abnormal.AbnormalType;
import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.ai.CtrlEvent;
import org.mmocore.gameserver.ai.maker.default_maker;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.GameObject;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.tables.SkillTable;
import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.utils.ai.Fstring;
import org.mmocore.gameserver.world.GameObjectsStorage;

/**
 * @author VISTALL
 * @date 11:02/24.05.2011
 */
public class AiUtils {
    public static int Rand(final int n) {
        return Rnd.get(n);
    }

    public static int GetAbnormalLevel(Creature actor, int abnormal_order){
        if(abnormal_order > AbnormalType.values().length-1) {
            SkillData skillInfo = SkillDataHolder.getInstance().getSkillInfo(abnormal_order);
            if (skillInfo != null && skillInfo.abnormal_type != null)
                abnormal_order = AbnormalType.valueFrom(skillInfo.abnormal_type).ordinal();
            else
                abnormal_order = 0;
        }
        SkillEntry se = actor.getEffectList().getSkillByAbnormalType(AbnormalType.values()[abnormal_order]);
        if(se==null)
            return -1;
        SkillData skillInfo = SkillDataHolder.getInstance().getSkillInfo(se.getId(), se.getLevel());
        if(skillInfo==null)
            return -1;
        return skillInfo.abnormal_lv;
    }

    public static int Skill_IsMagic(int pts_skill_id){
        SkillEntry sk = SkillTable.getInstance().getSkillEntry(pts_skill_id);
        if(sk == null)
            return 0;
        return sk.getTemplate().isMagic() ? 1 : 0;
    }
    public static Creature GetNullCreature(){
        return null;
    }
    public static Party GetParty(Creature attacker){
        return attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
    }
    public static int Party_GetCount(Creature object){
        return object.getPlayer() != null? object.getPlayer().getParty()!= null? object.getPlayer().getParty().getMemberCount() : 0 : 0;
    }
    public static Creature Party_GetCreature(Creature object, int index){
        if(object.getPlayer() == null)
            return null;
        if(object.getPlayer().getParty() == null)
            return null;

        return object.getPlayer().getParty().getPartyMembers().get(index);
    }

    /*public static Pair<Integer,String[]> MakeFString(int FString, String ...params){
        return new Pair<>(FString, params);
    }*/
    public static Fstring MakeFString(int FString, String... params){
        return new Fstring(FString, params);
    }

    public static int GetIndexFromCreature(Creature obj){ return obj == null? 0 : obj.getObjectId(); }
    public static Creature GetCreatureFromIndex(int objectId){
        final GameObject obj;
        if ((obj = GameObjectsStorage.findObject(objectId)) != null) {
            return (Creature) obj;
        }
        return null;
    }
    public static NpcInstance GetNPCFromID(int objectId){
        final GameObject obj;
        if ((obj = GameObjectsStorage.getNpc(objectId)) != null) {
            return (NpcInstance) obj;
        }
        return null;
    }
    public static boolean IsNull(Object obj){ return obj == null; }

    public static boolean IsSameString(String arg0, String arg1){
        return arg0.equalsIgnoreCase(arg1);
    }

    public static int FloatToInt(float value){
        return (int) value;
    }
    public static int FloatToInt(double value){
        return (int) value;
    }
    public static String IntToStr(int value){
        return String.valueOf(value);
    }

    /*TODO FieldCycle methods to Upgrade Hellbound*/
    public static int GetStep_FieldCycle(int arg){ return -2; }
    public static int GetPoint_FieldCycle(int arg){ return -2; }
    public static void AddPoint_FieldCycle(int arg0, int arg1, int arg2, Creature creature){}
    public static void SetStep_FieldCycle(int arg0, int arg1, int arg2, Creature creature){}
    public static void SetStepWithoutActor_FieldCycle(int arg0, int arg1, int arg2){}

    /*TODO SSQ methods*/
    public static int GetSSQPart(Creature arg){ return -1;}

    /*TODO Quest Reward methods*/
    public static CodeInfoList AllocCodeInfoList(){
        return new CodeInfoList();
    }
    public static int GetMemoStateEx(Creature player, int quest, int unk){ return 0;}
    public static int GetMemoState(Creature player, int quest){ return 0;}
    public static int HaveMemo(Creature player, int quest){ return 0;}
    public static int OwnItemCount(Creature player, int item_id){ return 0;}
    public static void AddLog(int unk, Creature player, int quest){}

    /*TODO AreaStatus methods*/
    public static void Area_SetOnOff(String areaname, boolean isOn){}
    public static void Area_SetOnOffEx(String areaname, boolean isOn, int unk){}
    public static void Castle_GateOpenClose2(String doorname, boolean open){}


    /*TODO Maker methods*/
    public static void SendMakerScriptEvent(default_maker maker, int script_event_id, Object ...args){
        //maker.onScriptEvent(script_event_id, args);
    }
    public static default_maker GetNpcMaker(String maker_name){
       return default_maker.EMPTY_MAKER;
    }
    public static default_maker InstantZone_GetNpcMaker(int reflection_id, String maker_name){
        return default_maker.EMPTY_MAKER;
    }
    public static void SendScriptEvent(NpcInstance npc, int script_event_id, int arg0){
        if(npc!=null)
            npc.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, script_event_id, arg0);
    }

}
