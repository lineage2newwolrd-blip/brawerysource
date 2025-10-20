package org.mmocore.gameserver.network.xmlrpc.handler;

import org.mmocore.commons.net.xmlrpc.handler.Handler;
import org.mmocore.commons.net.xmlrpc.handler.Message;
import org.mmocore.gameserver.configuration.config.OtherConfig;
import org.mmocore.gameserver.configuration.config.ServicesConfig;
import org.mmocore.gameserver.configuration.config.custom.CustomConfig;
import org.mmocore.gameserver.data.xml.holder.ItemTemplateHolder;
import org.mmocore.gameserver.data.xml.holder.custom.PremiumHolder;
import org.mmocore.gameserver.database.DatabaseFactory;
import org.mmocore.gameserver.database.dao.impl.AccountBonusDAO;
import org.mmocore.gameserver.database.dao.impl.CharacterDAO;
import org.mmocore.gameserver.database.dao.impl.ItemsDAO;
import org.mmocore.gameserver.database.dao.impl.variables.PlayerVariablesDAO;
import org.mmocore.gameserver.model.zone.ZoneType;
import org.mmocore.gameserver.network.authcomm.AuthServerCommunication;
import org.mmocore.gameserver.network.authcomm.gs2as.BonusRequest;
import org.mmocore.gameserver.network.lineage.components.CustomMessage;
import org.mmocore.gameserver.network.lineage.serverpackets.ExBR_PremiumState;
import org.mmocore.gameserver.network.lineage.serverpackets.SystemMessage;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.components.items.ItemInstance;
import org.mmocore.gameserver.object.components.player.custom.CustomPlayerComponent;
import org.mmocore.gameserver.object.components.player.premium.PremiumBonus;
import org.mmocore.gameserver.object.components.variables.PlayerVariables;
import org.mmocore.gameserver.templates.custom.premium.PremiumPackage;
import org.mmocore.gameserver.templates.custom.premium.PremiumType;
import org.mmocore.gameserver.templates.custom.premium.component.PremiumTime;
import org.mmocore.gameserver.templates.item.ItemTemplate;
import org.mmocore.gameserver.utils.ItemFunctions;
import org.mmocore.gameserver.utils.Language;
import org.mmocore.gameserver.utils.Log;
import org.mmocore.gameserver.utils.TimeUtils;
import org.mmocore.gameserver.world.GameObjectsStorage;
import org.mmocore.gameserver.world.World;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mmocore.gameserver.configuration.config.community.CServiceConfig.rateBonusType;

public class PlayerHandler extends Handler
{
	/**
	 * Добавляет персонажу заданный итем
	 * @param playerId id игрока
	 * @param itemId ID предмета
	 * @param count количество предмета
	 * @return {@code OK} если добавление состоялось удачно, {@code FAIL} если по каким-то причинам добавление не состоялось
	 */
	public String xmlrpcAddItem(int playerId, int itemId, int count)
	{
		ItemTemplate temp = ItemTemplateHolder.getInstance().getTemplate(itemId);
		try
		{
			if(playerId < 0 || temp == null)
			{
				return json(Message.FAIL);
			}
			Player player = GameObjectsStorage.getPlayer(playerId);
			if(player != null)
			{
				if(temp.isStackable()) {
					ItemInstance item = ItemFunctions.createItem(itemId);
					item.setCount(count);
					player.getInventory().addItem(item);
					Log.items(player, Log.Create, item);
				}
				else{
					for(long i = 0; i < count; i++) {
						ItemInstance item = ItemFunctions.createItem(itemId);
						Log.items(player, Log.Create, item);
						player.getInventory().addItem(item);
					}
				}
				player.sendPacket(SystemMessage.obtainItems(itemId, count, 0));
				logDonate.info("XML RPC Donate: Player " + player.getName() + " donated ItemId: " + itemId + " Count: " + count + " [IP: " + player.getIP() + "]");
				return json(Message.OK);

			}
			else
			{
				String playerName = CharacterDAO.getInstance().getNameByObjectId(playerId);
				if(playerName == null || playerName.isEmpty())
					return json(Message.FAIL);

				if(temp.isStackable()) {
					ItemInstance item = ItemFunctions.createItem(itemId);
					item.setCount(count);
					addStackableOffline(playerId, itemId, count);
					Log.items(playerName, playerId, Log.Create, item, count);
				}
				else{
					for (long i = 0; i < count; i++) {
						ItemInstance item = ItemFunctions.createItem(itemId);
						item.setOwnerId(playerId);
						item.setLocation(ItemInstance.ItemLocation.INVENTORY);
						ItemsDAO.getInstance().save(item);
						Log.items(playerName, playerId, Log.Create, item, 1);
					}
				}

				logDonate.info("XML RPC Donate: Player " + playerName + " donated ItemId: " + itemId + " Count: " + count + " [OFFLINE PLAYER]");
				return json(Message.OK);
			}
		}
		catch(Exception e)
		{
			return json(Message.DATABASE_ERROR);
		}
	}

	private void addStackableOffline(int playerId, int itemId, int count) throws SQLException
	{
		int objId = -1;

		try
		{
			conn = DatabaseFactory.getInstance().getConnection();
			statement = conn.prepareStatement("SELECT object_id FROM items WHERE item_id="+itemId+" AND owner_id="+playerId+" AND loc='INVENTORY'");
			resultSet = statement.executeQuery();
			if (resultSet.next())
				objId = resultSet.getInt("object_id");
		}
		catch (SQLException e)
		{
			throw new SQLException(e);
		}
		finally
		{
			databaseClose(true);
		}

		if (objId == -1)
		{
			ItemInstance item = ItemFunctions.createItem(itemId);
			item.setCount(count);
			item.setOwnerId(playerId);
			item.setLocation(ItemInstance.ItemLocation.INVENTORY);
			ItemsDAO.getInstance().save(item);
		}
		else
		{
			try
			{
				conn = DatabaseFactory.getInstance().getConnection();
				statement = conn.prepareStatement("UPDATE items SET count=count+"+count+" WHERE object_id="+objId);
				statement.execute();
			}
			catch (SQLException e)
			{
				throw new SQLException(e);
			}
			finally
			{
				databaseClose(false);
			}
		}
	}

	/**
	 * @param account имя аккаунта
	 * @param id_time идентификатор пакета премиума
	 * @return результат операции
	 */
	public String addPremiumDaysForPlayer(String account, int id_time, int id_pack)
	{
		if(rateBonusType == PremiumBonus.NO_BONUS)
		{
			return json(Message.FAIL);
		}
		// Все аккаунты в базе в нижнем регистре !!!!!!!!!!!!!!!!!!!!
		account = account.toLowerCase();
		final PremiumPackage premiumPackage = PremiumHolder.getInstance().getPremiumPackages().get(id_pack);
		if(premiumPackage == null)
		{
			return json(Message.FAIL);
		}
		final Optional<PremiumTime> premiumTime = premiumPackage.getTime(id_time);
		if(!premiumTime.isPresent())
		{
			return json(Message.FAIL);
		}
		try
		{
			final PremiumBonus premiumBonus = new PremiumBonus();
			premiumPackage.getPremiumRates().entrySet().stream().forEach(p -> {
				final PremiumType type = p.getKey();
				if(type == PremiumType.xp)
				{
					premiumBonus.setRateXp(p.getValue().getValue() > 1. ? p.getValue().getValue() : 1.);
				}
				else if(type == PremiumType.sp)
				{
					premiumBonus.setRateSp(p.getValue().getValue() > 1. ? p.getValue().getValue() : 1.);
				}
				else if(type == PremiumType.adena)
				{
					premiumBonus.setDropAdena(p.getValue().getValue() > 1. ? p.getValue().getValue() : 1.);
				}
				else if(type == PremiumType.drop)
				{
					premiumBonus.setDropItems(p.getValue().getValue() > 1. ? p.getValue().getValue() : 1.);
				}
				else if(type == PremiumType.spoil)
				{
					premiumBonus.setDropSpoil(p.getValue().getValue() > 1. ? p.getValue().getValue() : 1.);
				}
				else if(type == PremiumType.epaulette)
				{
					premiumBonus.setBonusEpaulette(p.getValue().getValue() > 1. ? p.getValue().getValue() : 1.);
				}
				else if (type == PremiumType.enchantChance) {
					premiumBonus.setEnchantChance(p.getValue().getValue() > 1. ? p.getValue().getValue() : 1.);
				}
				else if (type == PremiumType.attributeChance) {
					premiumBonus.setAttributeChance(p.getValue().getValue() > 1. ? p.getValue().getValue() : 1.);
				}
				else if (type == PremiumType.craftChance) {
					premiumBonus.setCraftChance(p.getValue().getValue() > 1. ? p.getValue().getValue() : 1.);
				}
			});
			final ZonedDateTime time = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).
					plusDays(premiumTime.get().getDays()).
					plusHours(premiumTime.get().getHour()).
					plusMinutes(premiumTime.get().getMinute());
			premiumBonus.setBonusExpire(time.toEpochSecond());
			switch(rateBonusType)
			{
				case PremiumBonus.BONUS_GLOBAL_ON_AUTHSERVER:
				{
					AuthServerCommunication.getInstance().sendPacket(new BonusRequest(account, 1, premiumBonus));
					break;
				}
				case PremiumBonus.BONUS_GLOBAL_ON_GAMESERVER:
				{
					AccountBonusDAO.getInstance().insert(account, premiumBonus);
					break;
				}
			}
			// Проверяем, есть ли персонаж онлайн
			boolean foundInWorld = false;
			for(Player player : GameObjectsStorage.getPlayers())
			{
				if(player.getAccountName().equals(account))
				{
					player.getNetConnection().setPremiumBonus(premiumBonus);
					player.getPremiumAccountComponent().stopBonusTask();
					player.getPremiumAccountComponent().startBonusTask();
					if(player.getParty() != null)
					{
						player.getParty().recalculatePartyData();
					}
					player.sendMessage(new CustomMessage("premium.time").addString(TimeUtils.dateTimeFormat(time)));
					player.sendPacket(new ExBR_PremiumState(player, true));
					if(CustomConfig.subscriptionAllow && !player.isGM() && CustomConfig.buyPremiumBuySubscription)
					{
						final long timeSub = Duration.between(ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()), time).toMillis();
						final CustomPlayerComponent playerComponent = player.getCustomPlayerComponent();
						playerComponent.saveSubscriptionTimeTask(false);
						playerComponent.stopSubscriptionTask();
						playerComponent.addTimeSubscription(timeSub);
						player.sendChanges();
						if(!player.isInZone(ZoneType.peace_zone))
						{
							playerComponent.startSubscription();
						}
					}
					logDonate.info("XML RPC Donate: Account " + account + " buyed premium for [DaysAdd: " + premiumTime.get().getDays() + "]   [IP: " + player.getIP() + "]");
					foundInWorld = true;
					break;
				}
			}

			// Если персонажа с таким аккаунтом не найдено в игре
			if(!foundInWorld) {
				logDonate.info("XML RPC Donate: Account " + account + " buyed premium for " + premiumTime.get().getDays() + " days " + "[OFFLINE PLAYER]");
			}
			return json(Message.OK);
		}
		catch(Exception e)
		{
			return json(Message.FAIL);
		}
	}

	/**
	 * Устанавливает игроку указанный цвет ника
	 * @param charName имя игрока
	 * @param color цвет в RGB
	 * @return результат операции
	 */
	public String setNameColor(String charName, int color)
	{
		try
		{
			Player player = GameObjectsStorage.getPlayer(charName);
			if(player == null)
			{
				int objId = CharacterDAO.getInstance().getObjectIdByName(charName);
				if(objId == 0)
					return json(Message.FAIL);
				if(color != OtherConfig.NORMAL_NAME_COLOUR && color != OtherConfig.CLANLEADER_NAME_COLOUR && color != OtherConfig.GM_NAME_COLOUR && color != ServicesConfig.SERVICES_OFFLINE_TRADE_NAME_COLOR)
					PlayerVariablesDAO.getInstance().save(objId, PlayerVariables.NAME_COLOR.name(), Integer.toHexString(color), -1);
				else if(color == OtherConfig.NORMAL_NAME_COLOUR)
					PlayerVariablesDAO.getInstance().remove(objId,PlayerVariables.NAME_COLOR.name());
				logDonate.info("XML RPC Donate: Player " + charName + " changed name color to " + Integer.toHexString(color) + " RGB" + " [OFFLINE PLAYER]");
				return json(Message.OK);
			}
			else
			{
				player.getAppearanceComponent().setNameColor(color);
				player.broadcastUserInfo(true);
				logDonate.info("XML RPC Donate: Player " + charName + " changed name color to " + Integer.toHexString(color) + " RGB" + " [IP: " + player.getIP() + "]");
				return json(Message.OK);
			}
		}
		catch(Exception e)
		{
			return json(Message.FAIL);
		}
	}

	/**
	 * Устанавливает игроку указанный цвет титула
	 * @param charName имя игрока
	 * @param color цвет в RGB
	 * @return результат операции
	 */
	public String setTitleColor(String charName, int color)
	{
		try
		{
			Player player = GameObjectsStorage.getPlayer(charName);
			if(player == null)
			{
				int objId = CharacterDAO.getInstance().getObjectIdByName(charName);
				if(objId == 0)
					return json(Message.FAIL);
				if(color != 0xFFFF77)
					PlayerVariablesDAO.getInstance().save(objId, PlayerVariables.TITLE_COLOR.name(), Integer.toHexString(color), -1);
				else
					PlayerVariablesDAO.getInstance().remove(objId, PlayerVariables.TITLE_COLOR.name());
				logDonate.info("XML RPC Donate: Player " + charName + " title name color to " + Integer.toHexString(color) + " RGB" + " [OFFLINE PLAYER]");
			}
			else
			{
				player.getAppearanceComponent().setTitleColor(color);
				player.broadcastUserInfo(true);
				logDonate.info("XML RPC Donate: Player " + charName + " changed title color to " + Integer.toHexString(color) + " RGB" + " [IP: " + player.getIP() + "]");
			}
			return json(Message.OK);
		}
		catch(Exception e)
		{
			return json(Message.FAIL);
		}
	}

	/**
	 * Сброс кармы игрока, если она у него отрицательная
	 * @param charName имя игрока
	 * @return результат операции
	 */
	public String resetKarmaToZero(String charName)
	{
		try
		{
			Player player = GameObjectsStorage.getPlayer(charName);
			if(player == null)
			{
				int karma = 0;
				int objectId = CharacterDAO.getInstance().getObjectIdByName(charName);
				if(objectId == 0){
					databaseClose(true);
					return json(Message.FAIL);
				}

				conn = DatabaseFactory.getInstance().getConnection();
				statement = conn.prepareStatement("SELECT karma FROM characters WHERE obj_id=?");
				statement.setInt(1, objectId);
				resultSet = statement.executeQuery();
				while(resultSet.next())
				{
					karma = resultSet.getInt("karma");
				}

				if(karma > 0)
				{
					statement = conn.prepareStatement("UPDATE characters SET karma=0 WHERE obj_id=?");
					statement.setInt(1, objectId);
					statement.execute();
					databaseClose(true);
					return json(Message.OK);
				}
				else
				{
					databaseClose(true);
					return json(Message.FAIL);
				}
			}
			else
			{
				int karma = player.getKarma();
				if(karma > 0)
				{
					player.setKarma(0);
					return json(Message.OK);
				}
				else
				{
					return json(Message.FAIL);
				}
			}
		}
		catch(Exception e)
		{
			return json(Message.FAIL);
		}
		finally {
			databaseClose(true);
		}
	}

	/**
	 * Возвращает всех персонажей с заданным аккаунтом
	 * @param account имя аккаунта
	 * @return сериализованные инстансы игроков на аккаунте
	 */
	public String getAllCharsFromAccount(String account)
	{
		List<SitePlayer> result = new ArrayList<>();
		try
		{
			conn = DatabaseFactory.getInstance().getConnection();
			statement = conn.prepareStatement("SELECT c.obj_id, c.char_name, sub.class_id, sub.level, c.pvpkills, c.pkkills, c.karma, c.lastaccess, c.hwid_lock FROM characters c " +
					"join character_subclasses sub on sub.char_obj_id = c.obj_id and sub.isBase = 1 " +
					"WHERE account_name=? LIMIT 7");
			statement.setString(1, account);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				result.add(new SitePlayer(resultSet.getString(1), resultSet.getString(2), getClassNameById(Integer.parseInt(resultSet.getString(3))), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9)!=null? "true" : "false"));
			}
		}
		catch(Exception e)
		{
			return json(Message.FAIL);
		}
		finally
		{
			databaseClose(true);
		}
		return json(result);
	}

	/**
	 * Разблокировка аккаунта.
	 */
	public String resetLock(String account, int objId)
	{
		if(World.getPlayer(objId) == null)
		{
			try
			{
				conn = DatabaseFactory.getInstance().getConnection();
				statement = conn.prepareStatement("SELECT account_name FROM characters WHERE obj_Id=?");
				statement.setInt(1, objId);
				statement.execute();
				resultSet = statement.getResultSet();
				resultSet.next();
				String account_name = resultSet.getString("account_name");
				statementClose(true);

				if(!account_name.equalsIgnoreCase(account))
					return json(Message.FAIL);

				statement = conn.prepareStatement("UPDATE characters SET hwid_lock=NULL WHERE obj_Id=?");
				statement.setInt(1, objId);
				statement.execute();
				statementClose(false);
			}
			catch(Exception e)
			{
				Log.add(getClass().getSimpleName() + ": Error while ResetLock() : ", "Error");
				return json(Message.FAIL);
			}
			finally
			{
				databaseClose(true);
			}
		}
		else
		{
			return json(Message.FAIL);
		}

		return json(Message.OK);
	}

	/**
	 * Возвращает список имен персонажей аккаунта.
	 * @param account имя аккаунта
	 * @return игроков на аккаунте
	 */
	public String xmlrpcGetPlayerNamesByAccount(String account)
	{
		List<String> names = new ArrayList<>(7);
		try
		{
			conn = DatabaseFactory.getInstance().getConnection();
			statement = conn.prepareStatement("SELECT char_name FROM characters WHERE account_name=?");
			statement.setString(1, account);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				names.add(resultSet.getString(1));
			}
		}
		catch(Exception e)
		{
			return json(Message.FAIL);
		}
		finally
		{
			databaseClose(true);
		}

		return json(names);
	}

	/**
	 * Сериализует персонажа с указанным именем
	 * @param charName имя персонажа
	 * @param full режим сериализации
	 * @return сериализованный инстанс игрока
	 */
	/*public String getPlayer(String charName, String full)
	{
		L2PcInstance pc;
		String result = "";
		if(WorldManager.getInstance().getPlayer(charName) == null)
		{
			pc = Util.loadPlayer(charName, true);
		}
		else
		{
			pc = WorldManager.getInstance().getPlayer(charName);
		}

		try
		{
			if(pc != null)
			{
				result += XMLUtils.serializePlayer(pc, Boolean.parseBoolean(full));
			}
			else
			{
				result += "<char/>";
			}
		}
		catch(Exception e)
		{
			log.log(Level.ERROR, getClass().getSimpleName() + ": Error while getPlayer() : ", e);
		}
		finally
		{
			try
			{
				pc.getLocationController().delete();
			}
			catch(NullPointerException e)
			{
				log.log(Level.ERROR, getClass().getSimpleName() + ": NPE Error while getPlayer().deleteMe() : ", e);
			}
		}

		return json(result);
	}*/

	/**
	 * Восстановление игрока в ближайший город
	 * @param objId id игрока
	 * @return результат
	 */
	public String unstuckPlayer(String account, int objId)
	{
		if(World.getPlayer(objId) == null)
		{
			try
			{
				conn = DatabaseFactory.getInstance().getConnection();
				statement = conn.prepareStatement("SELECT account_name, karma FROM characters WHERE obj_Id=?");
				statement.setInt(1, objId);
				statement.execute();
				resultSet = statement.getResultSet();
				resultSet.next();

				String account_name = resultSet.getString("account_name");
				int karma = resultSet.getInt("karma");
				statementClose(true);

				if(!account_name.equalsIgnoreCase(account))
					return json(Message.FAIL);

				if(karma > 0)
				{
					statement = conn.prepareStatement("UPDATE characters SET x=17144, y=170156, z=-3502 WHERE obj_Id=?");
					statement.setInt(1, objId);
					statement.execute();
					statementClose(false);
				}
				else
				{
					statement = conn.prepareStatement("UPDATE characters SET x=0, y=0, z=0 WHERE obj_Id=?");
					statement.setInt(1, objId);
					statement.execute();
					statementClose(false);
				}

				statement = conn.prepareStatement("DELETE FROM character_variables WHERE obj_id=? AND type='user-var' AND name='reflection'");
				statement.setInt(1, objId);
				statement.execute();
				statementClose(false);
			}
			catch(Exception e)
			{
				Log.add(getClass().getSimpleName() + ": Error while unstuckPlayer() : ", "Error");
				return json(Message.FAIL);
			}
			finally
			{
				databaseClose(true);
			}
		}
		else
		{
			// Нельзя анстакать игрока, который онлайн
			return json(Message.FAIL);
		}
		return json(Message.OK);
	}

	private class SitePlayer{

		private String id, name, charclass, lastaccess, level, pvp, pk, karma, hwid;

		private SitePlayer(String id,String name, String charclass, String level, String pvp, String pk, String karma, String lastaccess, String hwid)
		{
			this.id = id;
			this.name = name;
			this.charclass = charclass;
			this.level = level;
			this.pvp = pvp;
			this.pk = pk;
			this.karma = karma;
			this.lastaccess = lastaccess;
			this.hwid = hwid;
		}
	}

	private static String getClassNameById(int classId)
	{
		if((classId < 0 || classId > 136) || (classId > 118 && classId < 123) || (classId > 57 && classId < 88))
		{
			return new CustomMessage("utils.classId.name.default").toString(Language.ENGLISH);
		}
		return new CustomMessage("utils.classId.name." + classId).toString(Language.ENGLISH);
	}

}