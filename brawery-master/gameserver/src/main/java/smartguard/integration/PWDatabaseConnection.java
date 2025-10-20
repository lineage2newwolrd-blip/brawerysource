package smartguard.integration;

import smartguard.api.integration.DatabaseConnection;

import java.sql.Connection;

public class PWDatabaseConnection extends DatabaseConnection {
	final Connection connect;

	public PWDatabaseConnection(Connection connection) {
		super(connection, Connection.class, "_con");
		this.connect = connection;
	}

	@Override
	public void close() throws Exception {
		connect.close();
	}
}
