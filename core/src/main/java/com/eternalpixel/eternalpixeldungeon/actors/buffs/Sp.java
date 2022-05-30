package com.eternalpixel.eternalpixeldungeon.actors.buffs;

import com.eternalpixel.eternalpixeldungeon.actors.hero.Hero;
import com.watabou.utils.Bundle;

public class Sp extends Buff {

    private static final float STEP	= 1f;

    public int level = 100;
    public int maxLevel = 100;

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

            spend(STEP);
        } else {
            diactivate();
        }

        return true;
    }

    public void decreaseStamina(Integer sp) {
        level += sp;
    }
}
