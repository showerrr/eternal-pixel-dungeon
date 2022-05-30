package com.eternalpixel.eternalpixeldungeon.items.bags;

import com.eternalpixel.eternalpixeldungeon.items.Item;
import com.eternalpixel.eternalpixeldungeon.items.food.Food;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;

public class CoolerBox extends Bag {
    {
        image = ItemSpriteSheet.COOLERBOX;
    }

    @Override
    public boolean canHold( Item item ) {
        if (item instanceof Food){
            return super.canHold(item);
        } else {
            return false;
        }
    }

    public int capacity(){
        return 50;
    }

    @Override
    public int value() {
        return 40;
    }

}
