package org.mmocore.authserver.network.xmlrpc.handler;

import org.mmocore.authserver.accounts.Account;
import org.mmocore.authserver.configuration.config.LoginConfig;
import org.mmocore.commons.crypt.PBKDF2Hash;
import org.mmocore.commons.net.xmlrpc.handler.Handler;
import org.mmocore.commons.net.xmlrpc.handler.Message;

public class AccountHandler extends Handler
{
    /**
     * Checks if user typed correct information for existing account.
     */
    public String login(String user, String password)
    {
        user = user.toLowerCase();
        Account account = new Account(user);
        account.restore();
        if (account.getPasswordHash() == null)
            return json(Message.FAIL);
        boolean ok;
        try
        {
            ok = PBKDF2Hash.validatePassword(password, account.getPasswordHash());
        }
        catch(Exception e)
        {
            ok = false;
        }

        if(ok) {
            return json(Message.OK);
        } else {
            return json(Message.FAIL);
        }
    }

    /**
     * Checks if account with specified name already exists.
     */
    public String exists(String user)
    {
        user = user.toLowerCase();
        Account account = new Account(user);
        account.restore();
        if (account.getPasswordHash() == null)
            return json(Message.FAIL);

        return json(Message.OK);
    }

    /**
     * Registers account.
     */
    public String xmlrpcRegister(String user, String password)
    {
        user = user.toLowerCase();
        Account account = new Account(user);
        account.restore();
        try {
            if (account.getPasswordHash() != null)
                return json(Message.ACCOUNT_EXISTS);

            if(!user.matches(LoginConfig.ANAME_TEMPLATE))
                return json(Message.NOT_VALID_USERNAME);

            if (!password.matches(LoginConfig.APASSWD_TEMPLATE))
                return json(Message.NOT_VALID_PASSWORD);

            final String passwordHash = PBKDF2Hash.createHash(password);
            account.setPasswordHash(passwordHash);
            account.save();
        } catch (Exception e){
            return json(Message.DATABASE_ERROR);
        }
        return json(Message.OK);
    }

    /**
     * Смена пароля от аккаунта.
     */
    public String xmlrpcChangePassword(String user, String password, String newpass)
    {
        user = user.toLowerCase();
        Account account = new Account(user);
        account.restore();
        if (account.getPasswordHash() == null)
            return json(Message.ACCOUNT_NOT_EXISTS);
        boolean ok = false;
        try
        {
            ok = PBKDF2Hash.validatePassword(password, account.getPasswordHash());
        }
        catch(Exception e)
        {
            return json(Message.WRONG_PASSWORD);
        }

        if(ok)
        {
            if (!newpass.matches(LoginConfig.APASSWD_TEMPLATE))
                return json(Message.NOT_VALID_PASSWORD);
            try {
                final String passwordHash = PBKDF2Hash.createHash(newpass);
                account.setPasswordHash(passwordHash);
                account.save();
            } catch (Exception e){
                return json(Message.DATABASE_ERROR);
            }
        }
        else
        {
            return json(Message.WRONG_PASSWORD);
        }
        return json(Message.OK);
    }

    /**
     * Смена пароля от аккаунта.
     */
    public String xmlrpcSetPassword(String user, String password)
    {
        user = user.toLowerCase();
        Account account = new Account(user);
        account.restore();
        if (account.getPasswordHash() == null)
            return json(Message.ACCOUNT_NOT_EXISTS);

        if (!password.matches(LoginConfig.APASSWD_TEMPLATE))
            return json(Message.NOT_VALID_PASSWORD);
        try {
            final String passwordHash = PBKDF2Hash.createHash(password);
            account.setPasswordHash(passwordHash);
            account.save();
        } catch (Exception e){
            return json(Message.DATABASE_ERROR);
        }
        return json(Message.OK);
    }
}