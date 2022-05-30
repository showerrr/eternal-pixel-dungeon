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

package com.eternalpixel.eternalpixeldungeon.items.food;

import com.eternalpixel.eternalpixeldungeon.Assets;
import com.eternalpixel.eternalpixeldungeon.Badges;
import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.Statistics;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Buff;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Hunger;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Thirsty;
import com.eternalpixel.eternalpixeldungeon.actors.hero.Hero;
import com.eternalpixel.eternalpixeldungeon.actors.hero.Talent;
import com.eternalpixel.eternalpixeldungeon.effects.SpellSprite;
import com.eternalpixel.eternalpixeldungeon.items.Heap;
import com.eternalpixel.eternalpixeldungeon.items.Item;
import com.eternalpixel.eternalpixeldungeon.items.drink.Drink;
import com.eternalpixel.eternalpixeldungeon.items.drink.Juice;
import com.eternalpixel.eternalpixeldungeon.items.food.vegetables.Vegetable;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.scenes.GameScene;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSprite;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;
import com.eternalpixel.eternalpixeldungeon.utils.GLog;
import com.eternalpixel.eternalpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

import java.util.ArrayList;

public class Food extends Item {

	public static final float TIME_TO_EAT = 3f;
	public static final String AC_EAT = "EAT";

	public float energy = 250f;
	public float waterenergy = -100f;
	public int rank = -1;

	{
		stackable = true;
		image = ItemSpriteSheet.RATION;
		defaultAction = AC_EAT;
		bones = true;
		weight = 3;
	}


	public String status() {
		if (rot > 0){
			return Messages.format("%d:%d",quantity,rot);
		} else {
			return Messages.format("%d",quantity);
		}

	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_EAT);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_EAT)) {
			if (hero.isOverFeed()) {
				onOverFeed();
			} else {
				detach(hero.belongings.backpack);

				eat(hero);
			}
		}
	}

	protected float eatingTime() {
		if (Dungeon.hero.hasTalent(Talent.IRON_STOMACH)
				|| Dungeon.hero.hasTalent(Talent.ENERGIZING_MEAL)
				|| Dungeon.hero.hasTalent(Talent.MYSTICAL_MEAL)
				|| Dungeon.hero.hasTalent(Talent.INVIGORATING_MEAL)) {
			return TIME_TO_EAT - 2;
		} else {
			return TIME_TO_EAT;
		}
	}

	protected void eat(Hero hero) {
		Buff.affect(hero, Hunger.class).satisfy(energy);
		Buff.affect(hero, Thirsty.class).satisfy(waterenergy);

		GLog.i(eatMessage());

		hero.sprite.operate(hero.pos);
		hero.busy();
		SpellSprite.show(hero, SpellSprite.FOOD);
		Sample.INSTANCE.play(Assets.Sounds.EAT);

		hero.spend(eatingTime());

		Talent.onFoodEaten(hero, energy, this);

		Statistics.foodEaten++;
		Badges.validateFoodEaten();
	}

	@Override
	public String name() {
		return super.name();
	}

	public void onOverFeed() {
		GLog.w(Messages.get(this, "onoverfeed"));
	}

	public String eatMessage() {
		return Messages.get(this, "eat_msg");
	}

	public boolean isCookable() {
			return this.rank == 0;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public int value() {
		return 10 * quantity;
	}

	@Override
	public boolean isSimilar( Item item ) {
		return level == item.level && getClass() == item.getClass() && rank == ((Food) item).rank;
	}

	public void cook(int levelcap) {

		Food newFood = null;
		
		///TODO the influence of cooking skill level and levelcap
		if (this instanceof FlourFood) {
			newFood = new FlourFood().Random(1,10);
		}

		if (this instanceof Noodles) {
			newFood = new Noodles().Random(1,10);
		}
		
		if (this instanceof Vegetable) {
			newFood = new VegetableFood().madeOf(this.name()).Random(1,10);
		}

		if (this instanceof Corpse) {
			newFood = new Corpse().madeOf(((Corpse) this).mob).Random(1,10);
		}

//		if (this.rot == -1)
		if (newFood != null) {
			if (newFood.rot > 0) {
				newFood.rot = 720;
			}

			Dungeon.hero.earnExp(newFood.rank);
			Dungeon.level.drop(newFood,curUser.pos);
		}
	}

	public void mix() {

		Drink newDrink = null;

		if (this instanceof Vegetable) {
			newDrink = new Juice().madeOf(this.name());
		}

		Dungeon.hero.earnExp(0);
		Dungeon.level.drop(newDrink,curUser.pos);
	}

	@Override
	public boolean interact() {

		Game.runOnRenderThread(new Callback() {
			@Override
			public void call() {
				GameScene.show(new WndOptions(new ItemSprite(Food.this),Food.this.name(),Food.this.info(),Messages.get(Food.class,"secretly_eat"),Messages.get(Food.class,"cancel")) {
					@Override
					protected void onSelect(int index) {
						if (index == 0) {
							if (Dungeon.hero.isOverFeed()) {
								Food.this.onOverFeed();
							} else {
								(Food.this).eat(Dungeon.hero);
								Heap food = Dungeon.level.heaps.get(Dungeon.hero.pos);
								food.destroy();
							}
						}
					}
				});
			}
		});
		return true;
	}

	private static final String ROT = "rot";
	private static final String RANK = "rank";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put(ROT,rot);
		bundle.put(RANK,rank);
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		rot = bundle.getInt(ROT);
		rank = bundle.getInt(RANK);
	}
}
