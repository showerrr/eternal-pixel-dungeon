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

package com.eternalpixel.eternalpixeldungeon.items.armor.glyphs;

import com.eternalpixel.eternalpixeldungeon.Assets;
import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.actors.Char;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Buff;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Invisibility;
import com.eternalpixel.eternalpixeldungeon.items.armor.Armor;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSprite;
import com.watabou.noosa.audio.Sample;

public class Camouflage extends Armor.Glyph {

	private static ItemSprite.Glowing GREEN = new ItemSprite.Glowing( 0x448822 );

	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {
		//no proc effect, see HighGrass.trample
		return damage;
	}

	public static void activate(Char ch, int level){
		Buff.prolong(ch, Invisibility.class, 3 + level/2);
		if ( Dungeon.level.heroFOV[ch.pos] ) {
			Sample.INSTANCE.play( Assets.Sounds.MELD );
		}
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return GREEN;
	}

}

