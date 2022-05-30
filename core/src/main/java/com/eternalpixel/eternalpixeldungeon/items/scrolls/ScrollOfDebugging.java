package com.eternalpixel.eternalpixeldungeon.items.scrolls;

import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.effects.newBeam;
import com.eternalpixel.eternalpixeldungeon.items.Item;
import com.eternalpixel.eternalpixeldungeon.mechanics.Ballistica;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;
import com.eternalpixel.eternalpixeldungeon.tiles.DungeonTilemap;

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
        }
    }

    @Override
    public boolean isIdentified() {
        return true;
    }
}
