package org.mmocore.commons.net.xmlrpc.handler;

/**
 * @author iRock
 */
public class Message
{
	public static final Message OK = new Message(MessageType.OK);
	public static final Message FAIL = new Message(MessageType.FAIL);
	public static final Message NOT_VALID_USERNAME = new Message(MessageType.FAIL, "NOT_VALID_USERNAME");
	public static final Message NOT_VALID_PASSWORD = new Message(MessageType.FAIL, "NOT_VALID_PASSWORD");
	public static final Message ACCOUNT_EXISTS = new Message(MessageType.FAIL, "ACCOUNT_EXISTS");
	public static final Message ACCOUNT_NOT_EXISTS = new Message(MessageType.FAIL, "ACCOUNT_NOT_EXISTS");
	public static final Message DATABASE_ERROR = new Message(MessageType.FAIL, "DATABASE_ERROR");
	public static final Message WRONG_PASSWORD = new Message(MessageType.FAIL, "WRONG_PASSWORD");

	private final MessageType type;
	private final String message;

	public Message(MessageType type)
	{
		this.type = type;
		this.message = "";
	}

	public Message(MessageType type, String message)
	{
		this.type = type;
		this.message = message;
	}

	public enum MessageType
	{
		OK,
		FAIL
	}
}