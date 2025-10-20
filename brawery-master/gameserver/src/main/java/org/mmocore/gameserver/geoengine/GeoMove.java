package org.mmocore.gameserver.geoengine;

import org.mmocore.gameserver.configuration.config.GeodataConfig;
import org.mmocore.gameserver.geoengine.pathfind.PathFind;
import org.mmocore.gameserver.network.lineage.serverpackets.ExShowTrace;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.components.variables.PlayerVariables;
import org.mmocore.gameserver.utils.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Diamond
 * @Date: 27/04/2009
 */
public class GeoMove {
	public static List<List<Location>> findMovePath(int x, int y, int z, int destX, int destY, int destZ, Creature c, int geoIndex) {
		List<Location> path = PathFind.findPath(x, y, z, destX, destY, destZ, c != null && c.isPlayable(), geoIndex);
		if (path == null || c == null) {
			return Collections.emptyList();
		}
		if (GeodataConfig.PATH_CLEAN) {
			pathClean(path, geoIndex);
		}
		pathShift(path, geoIndex, c);

		//admin_geo_trace
		if (c.isPlayer() && ((Player) c).getPlayerVariables().get(PlayerVariables.GM_TRACE) != null) {
			Player player = (Player) c;
			ExShowTrace trace = new ExShowTrace();
			int i = 0;
			for (Location loc : path) {
				i++;
				if (i == 1 || i == path.size()) {
					continue;
				}
				trace.addTrace(loc.x, loc.y, loc.z, 30000);
			}
			player.sendPacket(trace);
		}

		return getNodePath(path, geoIndex);
	}

	private static List<List<Location>> getNodePath(List<Location> path, int geoIndex) {
		int size = path.size();
		if (size <= 1) {
			return Collections.emptyList();
		}
		List<List<Location>> result = new ArrayList<List<Location>>(size);
		for (int i = 1; i < size; i++) {
			Location p2 = path.get(i);
			Location p1 = path.get(i - 1);
			List<Location> moveList = GeoEngine.MoveList(p1.x, p1.y, p1.z, p2.x, p2.y, geoIndex, true); // onlyFullPath = true - проверяем весь путь до конца
			if (moveList == null) // если хотя-бы через один из участков нельзя пройти, забраковываем весь путь
			{
				return Collections.emptyList();
			}
			if (!moveList.isEmpty()) // это может случиться только если 2 одинаковых точки подряд
			{
				result.add(moveList);
			}
		}
		return result;
	}

	public static List<Location> constructMoveList(Location start, Location end) {
		Location geoFrom = start.clone().world2geo();
		Location geoTo = end.clone().world2geo();

		int diff_x = geoTo.x - geoFrom.x, diff_y = geoTo.y - geoFrom.y, diff_z = geoTo.z - geoFrom.z;
		int dx = Math.abs(diff_x), dy = Math.abs(diff_y), dz = Math.abs(diff_z) / 8;
		float steps = Math.max(Math.max(dx, dy), dz);
		if (steps == 0) // Никуда не идем
		{
			return Collections.emptyList();
		}

		float step_x = diff_x / steps, step_y = diff_y / steps, step_z = diff_z / steps;
		float next_x = geoFrom.x, next_y = geoFrom.y, next_z = geoFrom.z;

		List<Location> result = new ArrayList<Location>((int) steps + 1);
		result.add(new Location(geoFrom.x, geoFrom.y, geoFrom.z)); // Первая точка

		for (int i = 0; i < steps; i++) {
			next_x += step_x;
			next_y += step_y;
			next_z += step_z;

			result.add(new Location((int) (next_x + 0.5f), (int) (next_y + 0.5f), (int) (next_z + 0.5f)));
		}

		return result;
	}

	/**
	 * Очищает путь от ненужных точек.
	 *
	 * @param path путь который следует очистить
	 */
	public static void pathClean(List<Location> path, int geoIndex) {
		int size = path.size();
		if (size > 2)
			for (int i = 2; i < size; i++) {
				Location p1 = path.get(i - 2); // точка начала движения
				Location p2 = path.get(i - 1); // точка в середине, кандидат на вышибание
				Location p3 = path.get(i); // точка конца движения

				// если вторая точка совпадает с первой/третьей или на одной линии с ними - она не нужна
				if (p1.equals(p2) || p3.equals(p2) || isPointInLine(p1, p2, p3)) {
					path.remove(i - 1); // удаляем ее
					size--; // отмечаем это в размере массива
					i = Math.max(1, i - 1); // сдвигаемся назад
				}
			}

		int current = 0;
		int sub;
		while (current < path.size() - 2) {
			Location one = path.get(current);
			sub = current + 2;
			while (sub < path.size()) {
				Location two = path.get(sub);
				//canMoveWithCollision  /  canMoveToCoor
				if (one.equals(two) || GeoEngine.canMoveWithCollision(one.x, one.y, one.z, two.x, two.y, two.z, geoIndex)) {
					while (current + 1 < sub) {
						path.remove(current + 1);
						sub--;
					}
				}
				sub++;
			}
			current++;
		}
	}

	/**
	 * Сдвигает точки пути на величину коллизии актора
	 * для учета разницы между текущей координатой
	 * и центром модельки актора
	 */
	public static void pathShift(List<Location> path, int geoIndex, Creature creature) {
		// Сдвиг точек по пути движения на размер коллизии игрока для покрытия разницы между координатой позиции и середины модели актора
		if (creature.isPlayer()) {
			int current = 0;
			while (current < path.size() - 2) {
				Location from = path.get(current);
				Location temp = path.get(current + 1).clone().shiftLocFrom(from, creature.getColRadius());
				if (GeoEngine.canMoveWithCollision(from.x, from.y, from.z, temp.x, temp.y, temp.z, geoIndex)) {
					path.set(current + 1, temp);
				}
				current++;
			}
		}
	}

	private static boolean isPointInLine(Location p1, Location p2, Location p3) {
		// Все 3 точки на одной из осей X или Y.
		if (p1.x == p3.x && p3.x == p2.x || p1.y == p3.y && p3.y == p2.y)
			return true;

		// Условие ниже выполнится если все 3 точки выстроены по диагонали.
		// Это работает потому, что сравниваем мы соседние точки (расстояния между ними равны, важен только знак).
		// Для случая с произвольными точками работать не будет.
		if (p1.x != p2.x && p2.x != p3.x && p1.y != p2.y && p2.y != p3.y)
			return (p1.x - p2.x) * (p1.y - p2.y) == (p2.x - p3.x) * (p2.y - p3.y);
		return false;
	}
}