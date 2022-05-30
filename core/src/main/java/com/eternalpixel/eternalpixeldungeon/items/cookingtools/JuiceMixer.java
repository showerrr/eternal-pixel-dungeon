package com.eternalpixel.eternalpixeldungeon.items.cookingtools;

import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.Statistics;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Buff;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Sp;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Stamina;
import com.eternalpixel.eternalpixeldungeon.actors.hero.Hero;
import com.eternalpixel.eternalpixeldungeon.items.Item;
import com.eternalpixel.eternalpixeldungeon.items.bags.Bag;
import com.eternalpixel.eternalpixeldungeon.items.food.Food;
import com.eternalpixel.eternalpixeldungeon.items.food.fruit.Fruit;
import com.eternalpixel.eternalpixeldungeon.items.food.vegetables.Vegetable;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.scenes.GameScene;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;
import com.eternalpixel.eternalpixeldungeon.windows.WndBag;

import java.util.ArrayList;

public class JuiceMixer extends Item {

    public static final float TIME_TO_MIX = 3f;
    public static final String AC_MIX = "MIX";
    public int levelcap;

    {
        image = ItemSpriteSheet.JUICEMIXER;
        defaultAction = AC_MIX;
        bones = true;
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_MIX );
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {
        super.execute( hero, action );

        if (action.equals( AC_MIX )) {

            GameScene.selectItem( itemSelector );
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
        return 50 * quantity;
    }

    private String mixTitle(){
        return Messages.get(this, "mix_title");
    }

    protected WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

        @Override
        public String textPrompt() {
            return mixTitle();
        }

        @Override
        public Class<? extends Bag> preferredBag() {
            return null;
        }

        @Override
        public boolean itemSelectable(Item item) {
            return item instanceof Vegetable || item instanceof Fruit;
        }

        @Override
        public void onSelect( Item item ) {
            if (!(curItem instanceof JuiceMixer)){
                return;
            }

            if (item instanceof Vegetable || item instanceof Fruit) {

                ((Food) item).mix();

                Hero hero = Dungeon.hero;
                hero.sprite.operate( hero.pos );
                hero.spendAndNext( TIME_TO_MIX );

                Buff.affect(hero, Sp.class).decreaseStamina(10);
                item.detach(hero.belongings.backpack);

                updateQuickslot();
                Statistics.juiceMix++;
            }
        }
    };
}
