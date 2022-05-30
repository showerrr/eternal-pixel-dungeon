package com.eternalpixel.eternalpixeldungeon.items.drink;

import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.items.Heap;
import com.eternalpixel.eternalpixeldungeon.scenes.GameScene;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSprite;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;
import com.eternalpixel.eternalpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;

public class Milk extends Drink {

    {
        image = ItemSpriteSheet.MILK;
        waterenergy = 250;
        energy = 150;
    }

    @Override
    public boolean interact() {

        Game.runOnRenderThread(new Callback() {
            @Override
            public void call() {
                GameScene.show(new WndOptions(new ItemSprite(Milk.this),Milk.this.name(),Milk.this.info(),"偷吃","取消") {
                    @Override
                    protected void onSelect(int index) {
                        if (index == 0) {
                            (Milk.this).drink(Dungeon.hero);
                            Heap milk = Dungeon.level.heaps.get(Dungeon.hero.pos);
                            milk.destroy();
                        }
                    }
                });
            }
        });
        return true;
    }

}
