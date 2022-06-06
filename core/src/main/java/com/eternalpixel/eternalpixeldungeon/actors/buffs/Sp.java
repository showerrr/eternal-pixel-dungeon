package com.eternalpixel.eternalpixeldungeon.actors.buffs;

import com.eternalpixel.eternalpixeldungeon.actors.hero.Hero;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Sp extends Buff {

    private static final float STEP	= 1f;

    public static int level = 100;
    public static int maxLevel = 100;

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
    public boolean act() {
        if (target.isAlive() && target instanceof Hero) {

            if (Random.Int(10) == 0 && level < maxLevel) {
                level ++;
            }

            spend(STEP);
        } else {
            diactivate();
        }

        return true;
    }

    public void decreaseStamina(Integer sp) {
        level -= sp;
    }
}
