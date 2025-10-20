package org.mmocore.gameserver.model.reward;

import org.apache.commons.lang3.ArrayUtils;
import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.configuration.config.ServerConfig;
import org.mmocore.gameserver.configuration.config.community.CServiceConfig;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.templates.item.EtcItemTemplate;
import org.mmocore.gameserver.templates.item.ItemTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mmocore.gameserver.model.reward.RewardList.MAX_CHANCE;

public class RewardGroup implements Cloneable {
	private final List<RewardData> _items = new ArrayList<>();
	private double _chance;
	private boolean _isAdena = false; // Шанс фиксирован, растет только количество
	private boolean _isHerb = false; // Шанс фиксирован, растет только количество
	private boolean _notRate = false; // Рейты вообще не применяются
	private boolean _isBossRatedItem = false;
	private double _chanceSum;

	public RewardGroup(final double chance) {
		setChance(Math.min(chance * ServerConfig.rateChance, MAX_CHANCE));
	}

	public boolean notRate() {
		return _notRate;
	}

	public void setNotRate(final boolean notRate) {
		_notRate = notRate;
	}

	public double getChance() {
		return _chance;
	}

	public void setChance(final double chance) {
		_chance = chance;
	}

	public boolean isAdena() {
		return _isAdena;
	}

	public boolean isHerb() {
		return _isHerb;
	}

	public boolean isBossRatedItem() {
		return _isBossRatedItem;
	}

	public void setIsAdena(final boolean isAdena) {
		_isAdena = isAdena;
	}

	public void addData(final RewardData item) {
		if (item.getItem().isAdena()) {
			_isAdena = true;
		} else if (item.getItem().isHerb()) {
			_isHerb = true;
		}

		//TODO[Hack]: переписать эту херню
		manageItemRate(item, EtcItemTemplate.EtcItemType.RECIPE, ServerConfig.recipeRate);
		manageItemRate(item, EtcItemTemplate.EtcItemType.MATERIAL, ServerConfig.materialRate);
		ItemTemplate template = item.getItem();
		if (template.isKeyMatherial()) {
			item.setMinDrop((long) Math.max(item.getMinDrop() * ServerConfig.pieceRate, 1));
			item.setMaxDrop((long) Math.max(item.getMaxDrop() * ServerConfig.pieceRate, 1));
		}
		if (ArrayUtils.contains(CServiceConfig.paBossRatedItems, item.getItem().getItemId()))
			_isBossRatedItem = true;
		_chanceSum += item.getChance();
		item.setChanceInGroup(_chanceSum);
		_items.add(item);
	}

	private RewardData manageItemRate(RewardData item, EtcItemTemplate.EtcItemType type, double cfgRate) {
		if (cfgRate == 0)
			return null;
		if (item.getItem().getItemType() == type) {
			item.setMinDrop((long) Math.max(item.getMinDrop() * cfgRate, 1));
			item.setMaxDrop((long) Math.max(item.getMaxDrop() * cfgRate, 1));
		}
		return item;
	}

	/**
	 * Возвращает список вещей
	 */
	public List<RewardData> getItems() {
		return _items;
	}

	/**
	 * Возвращает полностью независимую копию группы
	 */
	@Override
	public RewardGroup clone() {
		final RewardGroup ret = new RewardGroup(_chance);
		for (final RewardData i : _items) {
			ret.addData(i.clone());
		}
		return ret;
	}

	/**
	 * Функция используется в основном механизме расчета дропа, выбирает одну/несколько вещей из группы, в зависимости от рейтов
	 */
	public List<RewardItem> roll(final RewardType type, final Player player, final double mod, final boolean isRaid, final boolean isSiegeGuard) {
		if (player == null || !player.isConnected()) {
			return Collections.emptyList();
		}
		switch (type) {
			case NOT_RATED_GROUPED:
			case NOT_RATED_NOT_GROUPED:
			case EVENT:
				if (_isHerb && ServerConfig.RATE_DROP_HERB == 0)
					return Collections.emptyList();
				return rollItems(mod, 1.0, 1.0);
			case SWEEP:
				return rollSpoil(mod, ServerConfig.RATE_DROP_SPOIL, player.getRateSpoil());
			case RATED_GROUPED:
				if (_isAdena) {
					return rollAdena(mod, ServerConfig.RATE_DROP_ADENA, player.getRateAdena());
				}

				if (_isHerb) {
					return rollItems(mod, ServerConfig.RATE_DROP_HERB, player.getRateItems());
				}

				if (isRaid) {
					return rollItems(mod, ServerConfig.RATE_DROP_RAIDBOSS, _isBossRatedItem ? player.getRateItems() : 1.0);
				}

				if (isSiegeGuard) {
					return rollItems(mod, ServerConfig.RATE_DROP_SIEGE_GUARD, 1.0);
				}

				return rollItems(mod, ServerConfig.RATE_DROP_ITEMS, player.getRateItems());
			default:
				return Collections.emptyList();
		}
	}

	public List<RewardItem> rollItems(final double mod, final double baseRate, final double playerRate) {
		if (mod <= 0) {
			return Collections.emptyList();
		}

		final double rate;
		if (_notRate) {
			rate = Math.min(mod, 1.0);
		} else {
			rate = baseRate * playerRate * mod;
		}

		List<RewardItem> ret = new ArrayList<RewardItem>(_items.size() * 3 / 2);
		double gmult = _chance * Math.max(rate, 1.0) / MAX_CHANCE;

		if (gmult >= 1.0) {
			if (ServerConfig.ALT_MULTI_DROP) {
				for (long n = 0; n < Math.floor(gmult); n++) {
					rollFinal(_items, ret, 1.0);
				}
			} else {
				rollFinal(_items, ret, Math.max(gmult, 1.0));
				return ret;
			}

		}
		if (Rnd.get(1, MAX_CHANCE) <= _chance * Math.max(rate, 1.0) % MAX_CHANCE)
			rollFinal(_items, ret, 1.0);
		return ret;
	}

	private List<RewardItem> rollSpoil(final double mod, final double baseRate, final double playerRate) {
		if (mod <= 0) {
			return Collections.emptyList();
		}

		final double rate;
		if (_notRate) {
			rate = Math.min(mod, 1.0);
		} else {
			rate = baseRate * playerRate * mod;
		}

		final double mult = Math.ceil(rate);

		final List<RewardItem> ret = new ArrayList<>(_items.size() * 3 / 2);
		for (long n = 0; n < mult; n++) {
			if (Rnd.get(1, MAX_CHANCE) <= _chance * Math.min(rate - n, 1.0)) {
				rollFinal(_items, ret, 1.0);
			}
		}

		return ret;
	}

	private List<RewardItem> rollAdena(double mod, final double baseRate, final double playerRate) {
		double chance = _chance;
		if (mod > 10) {
			mod *= _chance / MAX_CHANCE;
			chance = MAX_CHANCE;
		}

		if (mod <= 0) {
			return Collections.emptyList();
		}

		if (Rnd.get(0, MAX_CHANCE) > chance) {
			return Collections.emptyList();
		}

		final double rate = baseRate * playerRate * mod;

		final List<RewardItem> ret = new ArrayList<>(_items.size());
		rollFinal(_items, ret, rate);
		for (final RewardItem i : ret) {
			i.isAdena = true;
		}

		return ret;
	}

	private void rollFinal(final List<RewardData> items, final List<RewardItem> ret, final double mult) {
		// перебираем все вещи в группе и проверяем шанс
		final int chance = Rnd.get(0, MAX_CHANCE);
		final long count;

		for (final RewardData i : items) {
			if (chance < i.getChanceInGroup() && chance > i.getChanceInGroup() - i.getChance()) {
				final double imult = i.notRate() ? 1.0 : mult;

				if (i.getMinDrop() == i.getMaxDrop()) {
					count = Math.round(i.getMaxDrop() * imult);
				} else {
					count = Rnd.get(Math.round(i.getMinDrop() * imult), Math.round(i.getMaxDrop() * imult));
				}

				RewardItem t = null;

				for (final RewardItem r : ret) {
					if (i.getItemId() == r.itemId) {
						t = r;
						break;
					}
				}

				if (t == null) {
					ret.add(t = new RewardItem(i.getItemId()));
					t.count = count;
				} else if (!i.notRate()) {
					t.count = Math.addExact(t.count, count);
				}

				break;
			}
		}
	}
}