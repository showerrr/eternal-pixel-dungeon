package com.eternalpixel.eternalpixeldungeon.items.food;

import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;
import com.eternalpixel.eternalpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class FlourFood extends Food {

    {
        energy = 280f;
        weight = 4;
        waterenergy = -50;
        rank = 0;
    }

    @Override
    public int image() {
        switch (rank) {
            case 0:
                return ItemSpriteSheet.FLOUR;
            case 1:
                return ItemSpriteSheet.FEARSOMEBREAD;
            case 2:
                return ItemSpriteSheet.HARDBREAD;
            case 3:
                return ItemSpriteSheet.WALNUTBREAD;
            case 4:
                return ItemSpriteSheet.APPLEPIE;
            case 5:
                return ItemSpriteSheet.SANDWICH;
            case 6:
                return ItemSpriteSheet.CROISSANT;
            case 7:
                return ItemSpriteSheet.CROQUETTESANDWICH;
            case 8:
                return ItemSpriteSheet.CHOCOLATEBABKA;
            case 9:
                return ItemSpriteSheet.MELONFLAVOREDBREAD;
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

    public FlourFood Random(int min,int max) {
        this.rank = Random.Int(min,max);
        return this;
    }
}
