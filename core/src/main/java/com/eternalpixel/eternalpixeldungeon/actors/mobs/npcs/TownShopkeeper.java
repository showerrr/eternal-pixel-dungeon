package com.eternalpixel.eternalpixeldungeon.actors.mobs.npcs;

import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.actors.Char;
import com.eternalpixel.eternalpixeldungeon.scenes.GameScene;
import com.eternalpixel.eternalpixeldungeon.sprites.ShopkeeperSprite;
import com.eternalpixel.eternalpixeldungeon.windows.WndTownShopOptions;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;

public class TownShopkeeper extends NPC {

    private int TYPE = 0;///TODO fix it

    {
        spriteClass = ShopkeeperSprite.class;

        properties.add(Property.IMMOVABLE);
    }

    @Override
    public boolean interact(Char c) {

        if (c != Dungeon.hero) {
            return false;
        }

        //Is it too hard?
        if (this.pos == 8 + 18 * 32) {
            this.TYPE = 1;
        } else if (this.pos ==  20 + 4 * 32) {
            this.TYPE = 2;
        }

        Game.runOnRenderThread(new Callback() {
            @Override
            public void call() {
                GameScene.show(new WndTownShopOptions(TownShopkeeper.this.TYPE));
            }
        });

        return true;
    }
}
