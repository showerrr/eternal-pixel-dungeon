package com.eternalpixel.eternalpixeldungeon.items.food;

import com.eternalpixel.eternalpixeldungeon.items.Item;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;
import com.eternalpixel.eternalpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class VegetableFood extends Food {

    {
        energy = 200f;
        weight = 3;
        waterenergy = 100;
        rot = 720;
    }

    public String vegetable;

    @Override
    public int image() {
        switch (rank) {
            case 1:
                return ItemSpriteSheet.KITCHENREFUSEVEGETABLE;
            case 2:
                return ItemSpriteSheet.SMELLYVEGETABLE;
            case 3:
                return ItemSpriteSheet.VEGETABLESALAD;
            case 4:
                return ItemSpriteSheet.FRIEDVEGETABLE;
            case 5:
                return ItemSpriteSheet.VEGETABLEROLL;
            case 6:
                return ItemSpriteSheet.VEGETABLETENPURA;
            case 7:
                return ItemSpriteSheet.VEGETABLEGRATIN;
            case 8:
                return ItemSpriteSheet.MEATANDVEGETABLESTEW;
            case 9:
                return ItemSpriteSheet.VEGETABLECURRY;
            default:
                return ItemSpriteSheet.RATION;
        }
    }

    @Override
    public String name() {
        if (vegetable != null) {
            return Messages.get(this,"name" + rank,vegetable);
        } else {
            return Messages.get(this,"name" + rank,"");
        }
    }

    @Override
    public String desc() {
        return  Messages.get(this,"desc" + rank);
    }

    @Override
    public void onOverFeed() {
        GLog.w(Messages.get(this, "onoverfeed"+rank));
    }

    @Override
    public String eatMessage() {
        return Messages.get(this,"eat_msg"+rank);
    }

    public VegetableFood Random(int min,int max) {
        this.rank = Random.Int(min,max);
        return this;
    }

    public VegetableFood madeOf(String vegetable) {
        this.vegetable = vegetable;
        return this;
    }

    private static final String VEGETABLE = "vegetable";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put(VEGETABLE,vegetable);
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        vegetable = bundle.getString(VEGETABLE);
    }

    @Override
    public boolean isSimilar( Item item ) {
        return level == item.level && getClass() == item.getClass() && rank == ((Food) item).rank && vegetable.equals(((VegetableFood) item).vegetable);
    }
}
