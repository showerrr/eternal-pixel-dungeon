package com.eternalpixel.eternalpixeldungeon.actors.mobs.npcs;

import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.actors.Char;
import com.eternalpixel.eternalpixeldungeon.games.BlackJack;
import com.eternalpixel.eternalpixeldungeon.scenes.GameScene;
import com.eternalpixel.eternalpixeldungeon.sprites.ShopkeeperSprite;
import com.eternalpixel.eternalpixeldungeon.windows.WndBlackJack;
import com.eternalpixel.eternalpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;

public class TownGamer extends NPC{

    {
        spriteClass = ShopkeeperSprite.class;

        properties.add(Property.IMMOVABLE);
    }

    @Override
    public boolean interact(Char c) {

        if (c != Dungeon.hero) {
            return false;
        }

        Game.runOnRenderThread(new Callback() {
            @Override
            public void call() {

                GameScene.show(new WndOptions(new ShopkeeperSprite(),"游戏玩家","要来玩游戏吗？","好啊","算了") {
                    @Override
                    protected void onSelect(int index) {
                        if (index == 0) {
                            BlackJack.gameStart();
                            GameScene.show(new WndBlackJack(false));
                        }

                        if (index == 1) {
                            hide();
                        }
                    }
                });

            }
        });

        return true;
    }
}
