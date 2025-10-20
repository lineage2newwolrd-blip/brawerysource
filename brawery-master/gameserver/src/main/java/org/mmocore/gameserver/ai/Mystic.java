package org.mmocore.gameserver.ai;

import org.mmocore.gameserver.model.instances.NpcInstance;

public class Mystic extends DefaultAI {
    public Mystic(final NpcInstance actor) {
        super(actor);
    }

    @Override
    protected boolean thinkActive()
    {
        return super.thinkActive() || _castReady > System.currentTimeMillis() && defaultThinkBuff(5);
    }

    @Override
    protected boolean createNewTask()
    {
        return defaultFightTask();
    }

    @Override
    public int getRatePHYS()
    {
        return _damSkills.length == 0 ? 25 : 0;
    }

    @Override
    public int getRateDOT()
    {
        return _dotSkills.length == 0 ? 0 : 10;
    }

    @Override
    public int getRateDEBUFF()
    {
        return _debuffSkills.length == 0 ? 0 : 10;
    }

    @Override
    public int getRateDAM()
    {
        return _damSkills.length == 0 ? 0 : 100;
    }

    @Override
    public int getRateSTUN()
    {
        return _stunSkills.length == 0 ? 0 : 5;
    }

    @Override
    public int getRateBUFF()
    {
        return _buffSkills.length == 0 ? 0 : 5;
    }

    @Override
    public int getRateHEAL()
    {
        return _healSkills.length == 0 ? 0 : 10;
    }
}