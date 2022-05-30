package com.eternalpixel.eternalpixeldungeon.items.food;

import com.eternalpixel.eternalpixeldungeon.items.Item;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;

public class RottenFood extends Food {

    {
        image = ItemSpriteSheet.ROTTENFOOD;
    }

    public String rotname;

    @Override
    public String name() {
        return  Messages.get(this,"name",rotname);
    }

    public RottenFood getName(Food food) {
        this.rotname = food.name();
        return this;
    }

    private static final String ROTNAME = "rotname";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put(ROTNAME,rotname);
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        rotname = bundle.getString(ROTNAME);
    }

    @Override
    public boolean isSimilar( Item item ) {
        return level == item.level && getClass() == item.getClass() && rank == ((Food) item).rank && rotname.equals(((RottenFood) item).rotname);
    }
}
