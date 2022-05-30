package com.eternalpixel.eternalpixeldungeon.items.food;

import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;
import com.eternalpixel.eternalpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class Noodles extends Food {

    {
        energy = 350f;
        rot = 720;
        weight = 2;
        waterenergy = 250;
        rank = 0;
    }

    @Override
    public int image() {
        switch (rank) {
            case 0:
                return ItemSpriteSheet.RAWNOODLE;
            case 1:
                return ItemSpriteSheet.RISKYNOODLE;
            case 2:
                return ItemSpriteSheet.EXHAUSTEDNOODLE;
            case 3:
                return ItemSpriteSheet.SALADPASTA;
            case 4:
                return ItemSpriteSheet.UDON;
            case 5:
                return ItemSpriteSheet.SOBA;
            case 6:
                return ItemSpriteSheet.PEPERONCINO;
            case 7:
                return ItemSpriteSheet.CARBONARA;
            case 8:
                return ItemSpriteSheet.RAMEN;
            case 9:
                return ItemSpriteSheet.MEATSPAGHETTI;
            default:
                return ItemSpriteSheet.RATION;
        }
    }

    @Override
    public String name() {
        return  Messages.get(this,"name" + rank);
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

    public Noodles Random(int min, int max) {
        this.rank = Random.Int(min,max);
        return this;
    }

}