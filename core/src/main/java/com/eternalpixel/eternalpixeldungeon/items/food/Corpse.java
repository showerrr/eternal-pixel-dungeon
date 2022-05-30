package com.eternalpixel.eternalpixeldungeon.items.food;

import com.eternalpixel.eternalpixeldungeon.items.Item;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;
import com.eternalpixel.eternalpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Corpse extends Food {

    {
        energy = 280f;
        rot = 120;
        weight = 4;
        waterenergy = 0;
        rank = 0;
    }

    public String mob;
    
    @Override
    public int image() {
        switch (rank) {
            case 0:
                return ItemSpriteSheet.CORPSE;
            case 1:
                return ItemSpriteSheet.GROTESQUEMEAT;
            case 2:
                return ItemSpriteSheet.CHARREDMEAT;
            case 3:
                return ItemSpriteSheet.ROASTMEAT;
            case 4:
                return ItemSpriteSheet.DEEPFRIEDMEAT;
            case 5:
                return ItemSpriteSheet.SKEWERGRILLEDMEAT;
            case 6:
                return ItemSpriteSheet.MEATCROQUETTE;
            case 7:
                return ItemSpriteSheet.MEATHAMBURGER;
            case 8:
                return ItemSpriteSheet.MEATCUTLET;
            case 9:
                return ItemSpriteSheet.MEATSTEAK;
            default:
                return ItemSpriteSheet.RATION;
        }
    }

    @Override
    public String name() {
        if (this.mob != null) {
            return Messages.get(this,"name" + rank,mob);
        } else {
            return  Messages.get(this,"name" + rank,"");
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

    public Corpse lootFrom(String mob) {
        this.mob = mob;
        return this;
    }

    public Corpse madeOf(String mob) {
        this.mob = mob;
        return this;
    }
    public Corpse Random(int min,int max) {
        this.rank = Random.Int(min,max);
        return this;
    }

    private static final String MOB = "mob";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put(MOB,mob);
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        mob = bundle.getString(MOB);
    }

    @Override
    public boolean isSimilar( Item item ) {
        return level == item.level && getClass() == item.getClass() && rank == ((Food) item).rank && mob.equals(((Corpse) item).mob);
    }
}
