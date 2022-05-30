package com.eternalpixel.eternalpixeldungeon.items.food;

import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;

public class Baguette extends Food {

    {
        image = ItemSpriteSheet.BAGUETTE;
        waterenergy = -40;
        energy = 250;
    }

    @Override
    public int value() {
        return 20 * quantity;
    }
}
