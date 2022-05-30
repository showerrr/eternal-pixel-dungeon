package com.eternalpixel.eternalpixeldungeon.windows;

import static com.eternalpixel.eternalpixeldungeon.windows.WndTownShopOptions.Talk;

import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.actors.hero.Belongings;
import com.eternalpixel.eternalpixeldungeon.effects.Identification;
import com.eternalpixel.eternalpixeldungeon.items.Item;
import com.eternalpixel.eternalpixeldungeon.items.bags.Bag;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.scenes.GameScene;
import com.eternalpixel.eternalpixeldungeon.scenes.PixelScene;
import com.eternalpixel.eternalpixeldungeon.sprites.ShopkeeperSprite;
import com.eternalpixel.eternalpixeldungeon.ui.RedButton;
import com.eternalpixel.eternalpixeldungeon.ui.RenderedTextBlock;
import com.eternalpixel.eternalpixeldungeon.ui.Window;
import com.eternalpixel.eternalpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class WndTownIdentify extends Window {

    private static final int WIDTH = 120;
    private static final int BUTTON_HEIGHT	= 18;
    private static final int MARGIN 		= 2;

    public WndTownIdentify() {

        super();

        float pos = 0;

        IconTitle title = new IconTitle();
        title.icon( new ShopkeeperSprite());
        title.label( Messages.get(this,"title"));
        title.setRect( 0, pos, WIDTH, 0 );
        add(title);

        final RenderedTextBlock talk = PixelScene.renderTextBlock(Messages.get(WndTownShopOptions.class,"talk"+Talk),7);
        talk.maxWidth(WIDTH);
        talk.hardlight(Window.WHITE);
        pos += title.bottom() + MARGIN;
        talk.setRect(0,pos,WIDTH,talk.height());
        add(talk);

        final RedButton btnTalk = new RedButton(Messages.get(WndTownShopOptions.class,"talkmore")) {
            @Override
            protected void onClick() {
                int newTalk;

                do {
                    newTalk = Random.Int(19);
                } while (WndTownShopOptions.Talk == newTalk);
                WndTownShopOptions.Talk = newTalk;
                ///TODO fix it //to show more new talk

                hide();
                GameScene.show(new WndTownIdentify());
            }
        };
        pos += talk.height() + MARGIN;
        btnTalk.setRect(0, pos, WIDTH, BUTTON_HEIGHT);
        add(btnTalk);

        final RedButton btnIdentify = new RedButton(Messages.get("")) {
            @Override
            protected void onClick() {
                GameScene.selectItem(new WndBag.ItemSelector() {
                    @Override
                    public String textPrompt() { return Messages.get(this, "prompt"); }

                    @Override
                    public Class<?extends Bag> preferredBag(){
                        return Belongings.Backpack.class;
                    }

                    @Override
                    public boolean itemSelectable(Item item) {
                        return !item.isIdentified();
                    }

                    @Override
                    public void onSelect(Item item) {
                        if (item != null) {
                            if (Dungeon.gold >= 100) {
                                Dungeon.gold -= 100;
                                Dungeon.hero.sprite.parent.add( new Identification( Dungeon.hero.sprite.center().offset( 0, -16 ) ) );
                                item.identify();
                            } else {
                                hide();
                                GLog.w("嘿伙计鉴定物品是要给钱的");
                            }

                        }
                    }
                });
            }
        };

        pos += btnTalk.height() + MARGIN;
        btnIdentify.setRect(0,pos,WIDTH,BUTTON_HEIGHT);
        add(btnIdentify);

        
        pos += btnIdentify.height() + MARGIN;
        resize( WIDTH, (int)(pos - MARGIN) );
    }


}
