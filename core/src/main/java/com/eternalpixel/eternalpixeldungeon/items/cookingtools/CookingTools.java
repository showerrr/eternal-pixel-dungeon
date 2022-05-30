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
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.scenes.GameScene;
import com.eternalpixel.eternalpixeldungeon.windows.WndBag;

import java.util.ArrayList;

public class CookingTools extends Item {

    public static final float TIME_TO_COOK = 3f;
    public static final String AC_COOK = "COOK";
    public int levelcap;

    {
        defaultAction = AC_COOK;
        bones = true;
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_COOK );
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals( AC_COOK )) {

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

    private String cookTitle(){
        return Messages.get(this, "cook_title");
    }

    protected WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

        @Override
        public String textPrompt() {
            return cookTitle();
        }

        @Override
        public Class<? extends Bag> preferredBag() {
            return null;
        }

        @Override
        public boolean itemSelectable(Item item) {
            return item instanceof Food && ((Food) item).isCookable();
        }

        @Override
        public void onSelect( Item item ) {
            if (!(curItem instanceof CookingTools)){
                return;
            }

            if (item instanceof Food) {

                ((Food) item).cook(9);///TODO The influence of cookingtools levelcap

                Hero hero = Dungeon.hero;
                hero.sprite.operate( hero.pos );
                hero.spendAndNext( TIME_TO_COOK );

                Buff.affect(hero, Sp.class).decreaseStamina(10);
                item.detach(hero.belongings.backpack);

                updateQuickslot();
                Statistics.foodCook++;
            }
        }
    };


}
