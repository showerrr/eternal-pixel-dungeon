package com.eternalpixel.eternalpixeldungeon.actors.buffs;

import com.eternalpixel.eternalpixeldungeon.actors.hero.Hero;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.ui.BuffIndicator;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Thirsty extends Buff {

    private static final float STEP	= 1f;

    public static final float DISTENDED	= 1500f;
    public static final float WETTING	= 1200f;
    public static final float THIRSTY	= 400f;
    public static final float DRYING	= 200f;
    public static final float DRYING2	= 100f;

    public float level;


    private static final String LEVEL = "level";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put( LEVEL, level );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        level = bundle.getInt( LEVEL );
    }


    @Override
    public boolean act(){

        if (target.isAlive() && target instanceof Hero) {

            if (level > 0){
                if (((Hero) target).resting){
                    if (Random.Int(2) == 0){
                        level --;
                    }
                } else {
                    level --;
                }
            }

            if (level <0) {
                level = 0;
            }

            spend(STEP);

        } else {

            diactivate();

        }

        return true;
    }

    public void satisfy( float energy ) {
        affectThirsty( energy );
    }

    public void affectThirsty( float energy ) {
        level += energy;
        if (level <0) {
            level = 0;
        }

        BuffIndicator.refreshHero();
    }

    public boolean isDrying2() { return level <= DRYING2; }
    public boolean isDrying() {
        return level <= DRYING && level > DRYING2;
    }
    public boolean isWetting() { return level < DISTENDED && level >= WETTING; }
    public boolean isDistended() {
        return level >= DISTENDED;
    }

    @Override
    public int icon() {
        return BuffIndicator.NONE;
//        if (level <= DRYING2) {
//            return BuffIndicator.DRYING2;
//        } else if (level <= DRYING && level > DRYING2) {
//            return BuffIndicator.DRYING;
//        } else if (level <= DRYING && level > THIRSTY) {
//            return BuffIndicator.THIRSTY;
//        } else if (level < DISTENDED && level >= WETTING) {
//            return BuffIndicator.WETTING;
//        } else if (level >= DISTENDED) {
//            return BuffIndicator.DISTENDED;
//        } else {
//            return BuffIndicator.NONE;
//        }
    }

    @Override
    public String toString() {
        if (level <= DRYING2) {
            return Messages.get(this, "drying2");
        } else if (level <= DRYING && level > DRYING2) {
            return Messages.get(this, "drying");
        } else if (level <= THIRSTY && level > DRYING) {
            return Messages.get(this, "thirsty");
        } else if (level < DISTENDED && level >= WETTING) {
            return Messages.get(this, "wetting");
        } else {
            return Messages.get(this, "distended");
        }
    }

    @Override
    public String desc() {
        String result;
        if (level <= DRYING2) {
            result = Messages.get(this, "desc_intro_drying2");
        } else if (level <= DRYING && level > DRYING2) {
            result = Messages.get(this, "desc_intro_drying");
        } else if (level <= THIRSTY && level > DRYING) {
            result = Messages.get(this, "desc_intro_thirsty");
        } else if (level < DISTENDED && level >= WETTING) {
            result = Messages.get(this, "desc_intro_wetting");
        } else {
            result = Messages.get(this, "desc_intro_distended");
        }

        result += Messages.get(this, "desc");

        return result;
    }
}

