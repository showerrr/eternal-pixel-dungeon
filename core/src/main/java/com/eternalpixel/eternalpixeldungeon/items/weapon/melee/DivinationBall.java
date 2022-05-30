package com.eternalpixel.eternalpixeldungeon.items.weapon.melee;

import com.eternalpixel.eternalpixeldungeon.Assets;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;

public class DivinationBall extends MeleeWeapon{

    {
        image = ItemSpriteSheet.DIVINATION;
        hitSound = Assets.Sounds.HIT;
        hitSoundPitch = 1.1f;

        tier = 1;
        DLY = 0.75f;

        bones = false;
    }

    @Override
    public int max(int lvl) {
        return  Math.round(3f*(tier+1)) +     //6 base, down from 10
                lvl*Math.round(0.5f*(tier+1));  //+2 per level
    }
}
