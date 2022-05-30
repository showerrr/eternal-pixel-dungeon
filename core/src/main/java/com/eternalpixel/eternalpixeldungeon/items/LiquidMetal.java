package com.eternalpixel.eternalpixeldungeon.items;

import com.eternalpixel.eternalpixeldungeon.Assets;
import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.actors.hero.Hero;
import com.eternalpixel.eternalpixeldungeon.effects.Speck;
import com.eternalpixel.eternalpixeldungeon.effects.Splash;
import com.eternalpixel.eternalpixeldungeon.items.bags.Bag;
import com.eternalpixel.eternalpixeldungeon.items.bags.MagicalHolster;
import com.eternalpixel.eternalpixeldungeon.items.potions.Potion;
import com.eternalpixel.eternalpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.eternalpixel.eternalpixeldungeon.items.weapon.missiles.darts.Dart;
import com.eternalpixel.eternalpixeldungeon.levels.Terrain;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.scenes.GameScene;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;
import com.eternalpixel.eternalpixeldungeon.utils.GLog;
import com.eternalpixel.eternalpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

//these aren't considered potions internally as most potion effects shouldn't apply to them
//mainly due to their high quantity
public class LiquidMetal extends Item {

	{
		image = ItemSpriteSheet.LIQUID_METAL;

		stackable = true;

		defaultAction = AC_APPLY;

		bones = true;
	}

	private static final String AC_APPLY = "APPLY";

	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_APPLY );
		return actions;
	}

	@Override
	public void execute( Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals(AC_APPLY)) {

			curUser = hero;
			GameScene.selectItem( itemSelector );

		}
	}

	@Override
	protected void onThrow( int cell ) {
		if (Dungeon.level.map[cell] == Terrain.WELL || Dungeon.level.pit[cell]) {

			super.onThrow( cell );

		} else  {

			Dungeon.level.pressCell( cell );
			if (Dungeon.level.heroFOV[cell]) {
				GLog.i( Messages.get(Potion.class, "shatter") );
				Sample.INSTANCE.play( Assets.Sounds.SHATTER );
				Splash.at( cell, 0xBFBFBF, 5 );
			}

		}
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
		return Math.max(1, quantity/2);
	}

	private final WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

		@Override
		public String textPrompt() {
			return Messages.get(LiquidMetal.class, "prompt");
		}

		@Override
		public Class<?extends Bag> preferredBag(){
			return MagicalHolster.class;
		}

		@Override
		public boolean itemSelectable(Item item) {
			return item instanceof MissileWeapon && !(item instanceof Dart);
		}

		@Override
		public void onSelect( Item item ) {
			if (item != null && item instanceof MissileWeapon) {
				MissileWeapon m = (MissileWeapon)item;

				int maxToUse = 5*(m.tier+1);
				maxToUse *= Math.pow(2, m.level());

				float durabilityPerMetal = 100 / (float)maxToUse;

				//we remove a tiny amount here to account for rounding errors
				float percentDurabilityLost = 0.999f - (m.durabilityLeft()/100f);
				maxToUse = (int)Math.ceil(maxToUse*percentDurabilityLost);
				float durPerUse = m.durabilityPerUse()/100f;
				if (maxToUse == 0 ||
						Math.ceil(m.durabilityLeft()/ m.durabilityPerUse()) >= Math.ceil(m.MAX_DURABILITY/ m.durabilityPerUse()) ){
					GLog.w(Messages.get(LiquidMetal.class, "already_fixed"));
					return;
				} else if (maxToUse < quantity()) {
					m.repair(maxToUse*durabilityPerMetal);
					quantity(quantity()-maxToUse);
					GLog.i(Messages.get(LiquidMetal.class, "apply", maxToUse));

				} else {
					m.repair(quantity()*durabilityPerMetal);
					GLog.i(Messages.get(LiquidMetal.class, "apply", quantity()));
					detachAll(Dungeon.hero.belongings.backpack);
				}

				curUser.sprite.operate(curUser.pos);
				Sample.INSTANCE.play(Assets.Sounds.DRINK);
				updateQuickslot();
				curUser.sprite.emitter().start(Speck.factory(Speck.LIGHT), 0.1f, 10);
			}
		}
	};

	public static class Recipe extends com.eternalpixel.eternalpixeldungeon.items.Recipe {

		@Override
		public boolean testIngredients(ArrayList<Item> ingredients) {
			for (Item i : ingredients){
				if (!(i instanceof MissileWeapon)){
					return false;
				}
			}

			return !ingredients.isEmpty();
		}

		@Override
		public int cost(ArrayList<Item> ingredients) {
			int cost = 1;
			for (Item i : ingredients){
				cost += i.quantity();
			}
			return cost;
		}

		@Override
		public Item brew(ArrayList<Item> ingredients) {
			Item result = sampleOutput(ingredients);

			for (Item i : ingredients){
				i.quantity(0);
			}

			return result;
		}

		@Override
		public Item sampleOutput(ArrayList<Item> ingredients) {
			int metalQuantity = 0;

			for (Item i : ingredients){
				MissileWeapon m = (MissileWeapon) i;
				float quantity = m.quantity()-1;
				quantity += 0.25f + 0.0075f*m.durabilityLeft();
				quantity *= Math.pow(2, Math.min(3, m.level()));
				metalQuantity += Math.round((5*(m.tier+1))*quantity);
			}

			return new LiquidMetal().quantity(metalQuantity);
		}
	}

}
