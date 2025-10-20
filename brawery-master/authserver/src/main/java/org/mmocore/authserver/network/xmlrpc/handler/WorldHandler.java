package org.mmocore.authserver.network.xmlrpc.handler;

import org.mmocore.authserver.database.DatabaseFactory;
import org.mmocore.commons.net.xmlrpc.handler.Handler;
import org.mmocore.commons.net.xmlrpc.handler.Message;

import java.sql.SQLException;

public class WorldHandler extends Handler
{
	/**
	 * Пустой метод для запроса на сервер, чтобы понять, запущен он или нет.
	 * @return
	 */
	public String idle()
	{
		return json(Message.OK);
	}

	public String getAccountAmount()
	{
		int count = 0;
		try
		{
			conn = DatabaseFactory.getInstance().getConnection();
			statement = conn.prepareStatement("SELECT count(*) FROM `accounts`");
			resultSet = statement.executeQuery();

			if(resultSet.next())
			{
				count = resultSet.getInt(1);
			}

			resultSet.close();
			statement.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return json(Message.DATABASE_ERROR);
		}
		finally
		{
			databaseClose(true);
		}

		return json(String.valueOf(Math.max(0, count)));
	}
}