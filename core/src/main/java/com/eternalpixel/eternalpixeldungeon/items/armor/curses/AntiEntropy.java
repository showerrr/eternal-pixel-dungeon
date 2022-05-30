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

package com.eternalpixel.eternalpixeldungeon.items.armor.curses;

import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.actors.Char;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Buff;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Burning;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Frost;
import com.eternalpixel.eternalpixeldungeon.effects.CellEmitter;
import com.eternalpixel.eternalpixeldungeon.effects.particles.FlameParticle;
import com.eternalpixel.eternalpixeldungeon.effects.particles.SnowParticle;
import com.eternalpixel.eternalpixeldungeon.items.armor.Armor;
import com.eternalpixel.eternalpixeldungeon.items.armor.Armor.Glyph;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSprite;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class AntiEntropy extends Glyph {

	private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing( 0x000000 );
	
	@Override
	public int proc( Armor armor, Char attacker, Char defender, int damage) {

		if (Random.Int( 8 ) == 0) {

			if (Dungeon.level.adjacent( attacker.pos, defender.pos )) {
				Buff.prolong(attacker, Frost.class, Frost.DURATION);
				CellEmitter.get(attacker.pos).start(SnowParticle.FACTORY, 0.2f, 6);
			}
			
			Buff.affect( defender, Burning.class ).reignite( defender );
			defender.sprite.emitter().burst( FlameParticle.FACTORY, 5 );

		}
		
		return damage;
	}

	@Override
	public Glowing glowing() {
		return BLACK;
	}

	@Override
	public boolean curse() {
		return true;
	}
}
