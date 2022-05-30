package com.eternalpixel.eternalpixeldungeon.items.food;

import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;

public class SoupOfWonderland extends Food {

    {
        image = ItemSpriteSheet.SOUPOFWONDERLAND;
        rot = 600;
        weight = 2;
    }


    @Override
    public int value() {
        return 10 * quantity;
    }
    ///TODO affect height and weight

}
