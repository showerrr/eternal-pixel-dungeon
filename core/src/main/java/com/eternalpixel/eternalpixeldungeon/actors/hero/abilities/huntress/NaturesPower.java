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

package com.eternalpixel.eternalpixeldungeon.actors.hero.abilities.huntress;

import com.eternalpixel.eternalpixeldungeon.Assets;
import com.eternalpixel.eternalpixeldungeon.actors.Actor;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Buff;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.FlavourBuff;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Invisibility;
import com.eternalpixel.eternalpixeldungeon.actors.hero.Hero;
import com.eternalpixel.eternalpixeldungeon.actors.hero.Talent;
import com.eternalpixel.eternalpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.eternalpixel.eternalpixeldungeon.effects.particles.LeafParticle;
import com.eternalpixel.eternalpixeldungeon.items.armor.ClassArmor;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.ui.BuffIndicator;
import com.eternalpixel.eternalpixeldungeon.ui.HeroIcon;
import com.watabou.noosa.audio.Sample;

public class NaturesPower extends ArmorAbility {

	{
		baseChargeUse = 35f;
	}

	@Override
	protected void activate(ClassArmor armor, Hero hero, Integer target) {

		Buff.prolong(hero, naturesPowerTracker.class, naturesPowerTracker.DURATION);
		hero.buff(naturesPowerTracker.class).extensionsLeft = 2;
		hero.sprite.operate(hero.pos);
		Sample.INSTANCE.play(Assets.Sounds.CHARGEUP);
		hero.sprite.emitter().burst(LeafParticle.GENERAL, 10);

		armor.charge -= chargeUse(hero);
		armor.updateQuickslot();
		Invisibility.dispel();
		hero.spendAndNext(Actor.TICK);

	}

	@Override
	public int icon() {
		return HeroIcon.NATURES_POWER;
	}

	@Override
	public Talent[] talents() {
		return new Talent[]{Talent.GROWING_POWER, Talent.NATURES_WRATH, Talent.WILD_MOMENTUM, Talent.HEROIC_ENERGY};
	}

	public static class naturesPowerTracker extends FlavourBuff{

		public static final float DURATION = 8f;

		public int extensionsLeft = 2;

		public void extend( int turns ){
			if (extensionsLeft > 0 && turns > 0) {
				spend(turns);
				extensionsLeft--;
			}
		}

		@Override
		public int icon() {
			return BuffIndicator.SHADOWS;
		}

		@Override
		public float iconFadePercent() {
			return Math.max(0, (DURATION - visualcooldown()) / DURATION);
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc", dispTurns(visualcooldown()));
		}

	}
}
