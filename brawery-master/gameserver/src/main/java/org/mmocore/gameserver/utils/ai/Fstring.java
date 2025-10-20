package org.mmocore.gameserver.utils.ai;

/**
 * Created by iRock on 04.07.2018.
 */
public class Fstring {
	private final int npcStringId;
	private final String[] parameters = new String[5];

	public Fstring(final int id, final String... arg) {
		this.npcStringId = id;
		System.arraycopy(arg, 0, parameters, 0, arg.length);
	}

	public int getId(){return npcStringId;}

	public String[] getParams(){return parameters;}
}
