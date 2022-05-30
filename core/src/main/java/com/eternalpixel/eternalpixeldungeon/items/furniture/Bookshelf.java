package com.eternalpixel.eternalpixeldungeon.items.furniture;

import com.eternalpixel.eternalpixeldungeon.items.Item;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;

public class Bookshelf extends Item {

    {
        image = ItemSpriteSheet.BOOKSHELF;
    }

    @Override
    public boolean interact() {
        return true;
    }

    @Override
    public int value() {
        return 100 * quantity;
    }
}
