package com.eternalpixel.eternalpixeldungeon.actors.buffs;

import com.eternalpixel.eternalpixeldungeon.Badges;
import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.actors.hero.Hero;
import com.eternalpixel.eternalpixeldungeon.items.Item;

import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.ui.BuffIndicator;
import com.eternalpixel.eternalpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Weight extends Buff implements Hero.Doom {

    private static final float STEP	= 1f;

    public static final int BURDEN      = 500;
    public static final int BURDEN2	    = 750;
    public static final int OVERWEIGHT  = 1000;
    public static final int OVERWEIGHT2	= 2000;

    public int level;
    public int carryLevel = 1000;
    private float partialDamage;

    private static final String LEVEL = "level";
    private static final String CARRYLEVEL = "carryLevel";
    private static final String PARTIALDAMAGE 	= "partialDamage";

    private static boolean needReset = true;

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put( LEVEL, level );
        bundle.put( CARRYLEVEL, carryLevel );
        bundle.put( PARTIALDAMAGE, partialDamage );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        level = bundle.getInt( LEVEL );
        carryLevel = bundle.getInt( CARRYLEVEL );
        partialDamage = bundle.getFloat(PARTIALDAMAGE);
    }

    @Override
    public boolean act() {

        if (needReset) {
            refresh();
            needReset = false;
        }

        if (target.isAlive() && target instanceof Hero) {

            if (level >= carryLevel) {
                if (Random.Int(20) == 0) {
                    partialDamage +=  ((float)level / carryLevel - 1) * target.HT/20f;
                }

                if (partialDamage > 1){
                    target.damage( (int)partialDamage, this);
                    partialDamage -= (int)partialDamage;
                }

            }
            spend( STEP );

        } else {
            diactivate();
        }
        return true;
    }

    public void increaseWeight( int weight ) {
        level += weight;
//        if (haveReset == false) {
//            reset();
//            haveReset = true;
//        }
        BuffIndicator.refreshHero();
    }
    public void decreaseWeight( int weight ) {
        level -= weight;
//        if (haveReset == false) {
//            reset();
//            haveReset = true;
//        }
        BuffIndicator.refreshHero();

    }

    public void refresh() {
        level = 0;
        for (Item i : Dungeon.hero.belongings) {
            level += (i.weight * i.quantity);
        }
    }

    public void reset() {
        refresh();
        needReset = true;
    }


    public boolean isBurden() { return level <= BURDEN2 && level > BURDEN; }
    public boolean isBurden2() { return level <= OVERWEIGHT && level > BURDEN2; }
    public boolean isOverweight() { return level <= OVERWEIGHT2 && level > OVERWEIGHT; }
    public boolean isOverweight2() { return level > OVERWEIGHT2; }

    @Override
    public int icon() {
        return BuffIndicator.NONE;
//        if (level <= BURDEN) {
//            return BuffIndicator.NORMALWEIGHT;
//        } else if (level <= BURDEN2) {
//            return BuffIndicator.BURDEN;
//        } else if (level <= OVERWEIGHT) {
//            return BuffIndicator.BURDEN2;
//        } else if (level <= OVERWEIGHT2) {
//            return BuffIndicator.OVERWEIGHT;
//        } else {
//            return BuffIndicator.OVERWEIGHT2;
//        }
    }

    @Override
    public String toString() {
        if (level <= BURDEN) {
            return Messages.get(this, "normal");
        } else if (level <= BURDEN2) {
            return Messages.get(this, "burden");
        } else if (level <= OVERWEIGHT) {
            return Messages.get(this, "burden2");
        } else if (level <= OVERWEIGHT2) {
            return Messages.get(this, "overweight");
        } else {
            return Messages.get(this, "overweight2");
        }
    }

    @Override
    public String desc() {
        String result;
        if (level <= BURDEN) {
            result = Messages.get(this, "desc_intro_normal");
        } else if (level <= BURDEN2) {
            result = Messages.get(this, "desc_intro_burden");
        } else if (level <= OVERWEIGHT) {
            result = Messages.get(this, "desc_intro_burden2");
        } else if (level <= OVERWEIGHT2) {
            result = Messages.get(this, "desc_intro_overweight");
        } else {
            result = Messages.get(this, "desc_intro_overweight2");
        }

        result += Messages.get(this, "desc");

        return result;
    }

    @Override
    public void onDeath() {
        Badges.validateDeathFromHunger();

        Dungeon.fail( getClass() );
        GLog.n( Messages.get(this, "ondeath") );
    }
}
