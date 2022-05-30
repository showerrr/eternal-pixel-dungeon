package com.eternalpixel.eternalpixeldungeon.items.drink;

import com.eternalpixel.eternalpixeldungeon.items.Item;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;

public class Juice extends Drink {

    {
        image = ItemSpriteSheet.JUICE;
    }

    public String material;

    public Juice madeOf(String material) {
        this.material = material;
        return this;
    }

    @Override
    public String name() {
        if (this.material != null) {
            return Messages.get(this,"name",material);
        } else {
            return  Messages.get(this,"name","");
        }
    }

    @Override
    public String desc() {
        if (this.material != null) {
            return Messages.get(this,"desc",material);
        } else {
            return  Messages.get(this,"desc","");
        }
    }

    private static final String MATERIAL = "material";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put(MATERIAL,material);
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        material = bundle.getString(MATERIAL);
    }

    @Override
    public boolean isSimilar( Item item ) {
        return level == item.level && getClass() == item.getClass() && material.equals(((Juice) item).material);
    }
}
