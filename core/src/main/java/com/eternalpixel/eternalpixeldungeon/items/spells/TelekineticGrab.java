package com.eternalpixel.eternalpixeldungeon.items.spells;

import com.eternalpixel.eternalpixeldungeon.Assets;
import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.actors.Actor;
import com.eternalpixel.eternalpixeldungeon.actors.Char;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.PinCushion;
import com.eternalpixel.eternalpixeldungeon.actors.hero.Hero;
import com.eternalpixel.eternalpixeldungeon.actors.mobs.DwarfKing;
import com.eternalpixel.eternalpixeldungeon.effects.MagicMissile;
import com.eternalpixel.eternalpixeldungeon.items.Heap;
import com.eternalpixel.eternalpixeldungeon.items.Item;
import com.eternalpixel.eternalpixeldungeon.items.LiquidMetal;
import com.eternalpixel.eternalpixeldungeon.mechanics.Ballistica;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;
import com.eternalpixel.eternalpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class TelekineticGrab extends TargetedSpell {

	{
		image = ItemSpriteSheet.TELE_GRAB;
	}

	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.boltFromChar( curUser.sprite.parent,
				MagicMissile.BEACON,
				curUser.sprite,
				bolt.collisionPos,
				callback);
		Sample.INSTANCE.play( Assets.Sounds.ZAP );
	}

	@Override
	protected void affectTarget(Ballistica bolt, Hero hero) {
		Char ch = Actor.findChar(bolt.collisionPos);

		//special logic for DK when he is on his throne
		if (ch == null && bolt.path.size() > bolt.dist+1){
			ch = Actor.findChar(bolt.path.get(bolt.dist+1));
			if (!(ch instanceof DwarfKing && Dungeon.level.solid[ch.pos])){
				ch = null;
			}
		}

		if (ch != null && ch.buff(PinCushion.class) != null){

			Item item = ch.buff(PinCushion.class).grabOne();

			if (item.doPickUp(hero, ch.pos)){
				hero.spend(-Item.TIME_TO_PICK_UP); //casting the spell already takes a turn

			} else {
				GLog.w(Messages.get(this, "cant_grab"));
				Dungeon.level.drop(item, ch.pos).sprite.drop();
				return;
			}

		} else if (Dungeon.level.heaps.get(bolt.collisionPos) != null){

			Heap h = Dungeon.level.heaps.get(bolt.collisionPos);

			if (h.type != Heap.Type.HEAP){
				GLog.w(Messages.get(this, "cant_grab"));
				h.sprite.drop();
				return;
			}

			Item item = h.peek();

			if (item.doPickUp(hero, h.pos)){
				h.pickUp();
				hero.spend(-Item.TIME_TO_PICK_UP); //casting the spell already takes a turn

			} else {
				GLog.w(Messages.get(this, "cant_grab"));
				h.sprite.drop();
				return;
			}

		} else {
			GLog.w(Messages.get(this, "no_target"));
		}

	}

	@Override
	public int value() {
		//prices of ingredients, divided by output quantity (rounded up slightly)
		return Math.round(quantity * ((48) / 6f));
	}

	public static class Recipe extends com.eternalpixel.eternalpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{LiquidMetal.class, ArcaneCatalyst.class};
			inQuantity = new int[]{15, 1};

			cost = 4;

			output = TelekineticGrab.class;
			outQuantity = 6;
		}

	}

}
