package org.mmocore.commons.net.xmlrpc.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mmocore.commons.database.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author iRock
 */
public abstract class Handler {

    protected final static Logger logDonate = LoggerFactory.getLogger("donate");

    private final ObjectMapper jsonObject;
    protected Connection conn;
    protected PreparedStatement statement;
    protected ResultSet resultSet;

    public Handler()
    {
        jsonObject = new ObjectMapper();
    }

    protected void databaseClose(boolean closeResultSet)
    {
        if(closeResultSet)
        {
            DbUtils.closeQuietly(conn, statement, resultSet);
        }
        else
        {
            DbUtils.closeQuietly(conn, statement);
        }
    }

    protected void statementClose(boolean closeResultSet)
    {
        if(closeResultSet)
        {
            DbUtils.closeQuietly(statement, resultSet);
        }
        else
        {
            DbUtils.closeQuietly(statement);
        }
    }

    public String json(Object data)
    {
        try {
            return jsonObject.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}