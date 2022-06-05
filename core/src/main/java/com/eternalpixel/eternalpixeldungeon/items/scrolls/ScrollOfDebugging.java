package com.eternalpixel.eternalpixeldungeon.items.scrolls;

import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.actors.mobs.YogDzewa;
import com.eternalpixel.eternalpixeldungeon.effects.newBeam;
import com.eternalpixel.eternalpixeldungeon.items.Item;
import com.eternalpixel.eternalpixeldungeon.items.food.Corpse;
import com.eternalpixel.eternalpixeldungeon.items.food.FlourFood;
import com.eternalpixel.eternalpixeldungeon.items.food.Noodles;
import com.eternalpixel.eternalpixeldungeon.items.food.vegetables.Tomato;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfExperience;
import com.eternalpixel.eternalpixeldungeon.mechanics.Ballistica;
import com.eternalpixel.eternalpixeldungeon.scenes.GameScene;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;
import com.eternalpixel.eternalpixeldungeon.tiles.DungeonTilemap;
import com.eternalpixel.eternalpixeldungeon.windows.WndWishing;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;

import java.util.ArrayList;
import java.util.Arrays;

public class ScrollOfDebugging extends Scroll {
    {
        icon = ItemSpriteSheet.Icons.SCROLL_LULLABY;
    }

    public static boolean a = true;

    @Override
    public void doRead() {
        Item i = new ScrollOfDebugging();
        i.collect();

        for (int j = 0;j < 20;j++) {
            i = new ScrollOfUpgrade();
            i.collect();
            i = new PotionOfExperience();
            i.collect();
            i = new Tomato();
            i.collect();
            i = new FlourFood();
            i.collect();
            i = new Noodles();
            i.collect();
            i = new Corpse().madeOf(curUser.name());
            i.collect();
        }

        Game.runOnRenderThread(new Callback() {
                @Override
                public void call() {
                    GameScene.show(new WndWishing());
                }
        });
    }

    @Override
    public boolean isIdentified() {
        return true;
    }
}
