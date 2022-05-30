package com.eternalpixel.eternalpixeldungeon.windows;

import com.eternalpixel.eternalpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.eternalpixel.eternalpixeldungeon.levels.TownLevel;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.scenes.GameScene;
import com.eternalpixel.eternalpixeldungeon.scenes.PixelScene;
import com.eternalpixel.eternalpixeldungeon.sprites.ShopkeeperSprite;
import com.eternalpixel.eternalpixeldungeon.ui.RedButton;
import com.eternalpixel.eternalpixeldungeon.ui.RenderedTextBlock;
import com.eternalpixel.eternalpixeldungeon.ui.Window;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class WndTownShopOptions extends Window {

    private static final int WIDTH = 120;
    private static final int BTN_HEIGHT = 18;
    private static final int MARGIN = 2;
    public static int Talk;

    public WndTownShopOptions(int type){
        super();

        float pos = 0;

        IconTitle title = new IconTitle();
        title.icon( new ShopkeeperSprite());
        String kind = null;
        switch (type) {
            case 1:kind = "food";break;
            case 2:kind = "magic";break;
        }
        title.label( Messages.get(this,kind + "shopkeeper"));
        title.setRect( 0, pos, WIDTH, 0 );
        add(title);

        final RenderedTextBlock investment = PixelScene.renderTextBlock(Messages.get(this,"investment",TownLevel.investment.get(type)),5);
        investment.setPos(title.right(), 0);
        add(investment);

        final RenderedTextBlock talk = PixelScene.renderTextBlock(Messages.get(this,"talk"+Talk),7);
        talk.maxWidth(WIDTH);
        talk.hardlight(Window.WHITE);
        pos += title.bottom() + MARGIN;
        talk.setRect(0,pos,WIDTH,talk.height());
        add(talk);

        final RedButton btnTalk = new RedButton(Messages.get(this,"talkmore")) {
            @Override
            protected void onClick() {
                int newTalk;

                do {
                    newTalk = Random.Int(19);
                } while (WndTownShopOptions.Talk == newTalk);
                WndTownShopOptions.Talk = newTalk;
                ///TODO fix it //to show more new talk

                hide();
                GameScene.show(new WndTownShopOptions(type));
            }
        };
        pos += talk.height() + MARGIN;
        btnTalk.setRect(0, pos, WIDTH, 18);
        add(btnTalk);

        final RedButton btnBuy = new RedButton(Messages.get(this,"buy")) {
            @Override
            protected void onClick() {
                GameScene.show(new WndTownShop(type));
            }
        };
        pos += btnTalk.height() + MARGIN;
        btnBuy.setRect(0, pos, WIDTH,18);
        add(btnBuy);

        final RedButton btnSell = new RedButton(Messages.get(this,"sell")) {
            @Override
            protected void onClick() {
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        ///TODO fix it
                        Shopkeeper.sell();
                    }
                });
            }
        };
        pos += btnBuy.height() + MARGIN;
        btnSell.setRect(0, pos, WIDTH,18);
        add(btnSell);

//        title.setRect( 0, 0, WIDTH, 20 );
//        talk.setRect(0,title.bottom()+2,WIDTH,talk.height());
//        investment.setPos(title.right(), 0);
//        btnTalk.setRect(0, 46, WIDTH, 20);
//        btnBuy.setRect(0, 68, WIDTH,20);
//        btnSell.setRect(0, 90, WIDTH,20);
//
//        add(title);
//        add(talk);
//        add(btnTalk);
//        add(btnBuy);
//        add(btnSell);
//
//
        pos += btnSell.height();
        resize(WIDTH,(int)pos);
    }

    public static void getNewTalk() {
        int newTalk;
        do {
            newTalk = Random.Int(19);
        } while (WndTownShopOptions.Talk == newTalk);
        WndTownShopOptions.Talk = newTalk;
    }
}
