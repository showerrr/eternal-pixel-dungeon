/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.eternalpixel.eternalpixeldungeon.levels.rooms.connection;

import com.eternalpixel.eternalpixeldungeon.levels.Level;
import com.eternalpixel.eternalpixeldungeon.levels.Terrain;
import com.eternalpixel.eternalpixeldungeon.levels.painters.Painter;
import com.eternalpixel.eternalpixeldungeon.levels.rooms.Room;
import com.watabou.utils.Point;
import com.watabou.utils.Rect;

public class WalkwayRoom extends PerimeterRoom {
	
	@Override
	public void paint(Level level) {
		
		if (Math.min(width(), height()) > 3) {
			Painter.fill(level, this, 1, Terrain.CHASM);
		}
		
		super.paint(level);
		
		for (Room r : neigbours){
			if (r instanceof BridgeRoom || r instanceof RingBridgeRoom || r instanceof WalkwayRoom){
				Rect i = intersect(r);
				if (i.width() != 0){
					i.left++;
					i.right--;
				} else {
					i.top++;
					i.bottom--;
				}
				Painter.fill(level, i.left, i.top, i.width()+1, i.height()+1, Terrain.CHASM);
			}
		}
	}

	@Override
	public boolean canMerge(Level l, Point p, int mergeTerrain) {
		return mergeTerrain == Terrain.CHASM;
	}
}
