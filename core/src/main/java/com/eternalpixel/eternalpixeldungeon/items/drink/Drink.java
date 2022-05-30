package com.eternalpixel.eternalpixeldungeon.items.drink;

import com.eternalpixel.eternalpixeldungeon.Assets;
import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Buff;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Hunger;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Thirsty;
import com.eternalpixel.eternalpixeldungeon.actors.hero.Hero;
import com.eternalpixel.eternalpixeldungeon.items.Heap;
import com.eternalpixel.eternalpixeldungeon.items.Item;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.scenes.GameScene;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSprite;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;
import com.eternalpixel.eternalpixeldungeon.utils.GLog;
import com.eternalpixel.eternalpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

import java.util.ArrayList;

public class Drink extends Item {

    public static final float TIME_TO_DRINK = 1f;
    public static final String AC_DRINK = "DRINK";

    public int waterenergy = 400;
    public int energy = 50;

    {
        stackable = true;
        image = ItemSpriteSheet.RATION;
        defaultAction = AC_DRINK;
        bones = true;
        weight = 3;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_DRINK);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_DRINK)) {
            if (hero.isOverDrink()) {

                onOverDrink();

            } else {

                drink(hero);

//                Statistics.drink++;
//                Badges.validateDrinkEaten();
            }
        }
    }

    public void drink(Hero hero) {

        detach(hero.belongings.backpack);
        Buff.affect(hero, Thirsty.class).satisfy(waterenergy);
        Buff.affect(hero, Hunger.class).satisfy(energy);
        GLog.i(drinkMessage());
        hero.spend(TIME_TO_DRINK);
        hero.busy();

        Sample.INSTANCE.play(Assets.Sounds.DRINK);
        hero.sprite.operate(hero.pos);

    }

    @Override
    public String name() {
        return super.name();
    }

    public void onOverDrink() {
        GLog.w(Messages.get(this, "onoverdrink"));
    }

    public String drinkMessage() {
        return Messages.get(this, "drink_msg");
    }

    @Override
    public boolean interact() {

        Game.runOnRenderThread(new Callback() {
            @Override
            public void call() {
                GameScene.show(new WndOptions(new ItemSprite(Drink.this),Drink.this.name(),Drink.this.info(),Messages.get(Drink.class,"secretly_eat"),Messages.get(Drink.class,"cancel")) {
                    @Override
                    protected void onSelect(int index) {
                        if (index == 0) {
                            if (Dungeon.hero.isOverFeed()) {
                                Drink.this.onOverDrink();
                            } else {
                                (Drink.this).drink(Dungeon.hero);
                                Heap drink = Dungeon.level.heaps.get(Dungeon.hero.pos);
                                drink.destroy();
                            }
                        }
                    }
                });
            }
        });
        return true;
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

}
