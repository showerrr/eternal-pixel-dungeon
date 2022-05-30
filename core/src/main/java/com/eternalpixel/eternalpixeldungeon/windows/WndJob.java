package com.eternalpixel.eternalpixeldungeon.windows;

import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.Statistics;
import com.eternalpixel.eternalpixeldungeon.items.Gold;
import com.eternalpixel.eternalpixeldungeon.items.Item;
import com.eternalpixel.eternalpixeldungeon.items.bags.Bag;
import com.eternalpixel.eternalpixeldungeon.items.food.FlourFood;
import com.eternalpixel.eternalpixeldungeon.items.food.Noodles;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.scenes.GameScene;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSprite;
import com.eternalpixel.eternalpixeldungeon.ui.RedButton;
import com.eternalpixel.eternalpixeldungeon.ui.Window;
import com.eternalpixel.eternalpixeldungeon.utils.GLog;

public class WndJob extends Window {

    private static final int WIDTH		= 120;
    private static final int HEIGHT		= 120;
    private static final int MARGIN = 2;
    private static final int BUTTON_HEIGHT = 16;
    private static int type;
    public static Item itemToHandIn;

    public static int reward;

    public WndJob(Item item, int t) {

        super();
        resize( WIDTH, HEIGHT );

        reward = item.value() * 10;
        itemToHandIn = item;
        type = t;
        boolean canHandIn = false;

        for (Item i : Dungeon.hero.belongings) {
            if (i.isSimilar(item)) {
                canHandIn = true;
            }
        }

        IconTitle titlebar = new IconTitle();
        titlebar.icon( new ItemSprite( item.image(), null ) );
        titlebar.label( Messages.get(this,"title") + Messages.titleCase(item.name()));
        titlebar.setRect( 0, 0, WIDTH, 0 );
        add( titlebar );

//        final RenderedTextBlock txtTitle = PixelScene.renderTextBlock(Messages.get(this,"title"), 9);
//        txtTitle.maxWidth(width);
//        txtTitle.hardlight(Window.TITLE_COLOR);
//        txtTitle.setPos((width - txtTitle.width()) / 2, 2);
//        add(txtTitle);

        final RedButton handInBtn;
        if (canHandIn) {
            handInBtn = new RedButton(Messages.get(this,"hand_in")) {
                @Override
                protected void onClick() {
                    hide();
                    GameScene.selectItem( itemSelector );
                }
            };
        } else {
            handInBtn = null;
        }

        final RedButton cancelBtn = new RedButton(Messages.get(this,"cancel")) {
            @Override
            protected void onClick() {
                GameScene.show(new WndTownBoard());
                hide();
            }

            @Override
            protected boolean onLongClick() {
                Item i = itemToHandIn;
                Dungeon.level.drop(i,Dungeon.hero.pos);
                return true;
            }
        };


        if (canHandIn) {
            handInBtn.setRect(MARGIN, HEIGHT-BUTTON_HEIGHT, (width - MARGIN * 3) / 2, BUTTON_HEIGHT);
            add(handInBtn);
            cancelBtn.setRect(handInBtn.right() + MARGIN, HEIGHT-BUTTON_HEIGHT, (width - MARGIN * 3) / 2, BUTTON_HEIGHT);
            add(cancelBtn);
        } else {
            cancelBtn.setRect(MARGIN, HEIGHT-BUTTON_HEIGHT, width - MARGIN * 2, BUTTON_HEIGHT);
            add(cancelBtn);
        }
    }

    private String jobTitle(){
        return Messages.get(this, "hand_in_title");
    }

    protected WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

        @Override
        public String textPrompt() {
            return jobTitle();
        }

        @Override
        public Class<? extends Bag> preferredBag() {
            return null;
        }

        @Override
        public boolean itemSelectable(Item item) {
            return item.isSimilar(itemToHandIn);
        }

        @Override
        public void onSelect( Item i ) {

            if (i != null) {

                Dungeon.hero.spendAndNext(1);
                i.detach(Dungeon.hero.belongings.backpack);

                Gold gold = new Gold(reward);
                Dungeon.level.drop( gold, Dungeon.hero.pos ).sprite.drop();

                Dungeon.hero.earnExp(5);

                Item reward = null;
                switch (type) {
                    case 1:
                        reward = new FlourFood();
                        break;
                    case 2:
                        reward = new Noodles();
                        break;
                }
                Dungeon.level.drop( reward, Dungeon.hero.pos ).sprite.drop();

                i.updateQuickslot();
                Statistics.jobFinished++;

                if (type == 1) {
                    WndTownBoard.jobitem = null;
                } else if (type == 2) {
                    WndTownBoard.jobitem2 = null;
                }

            } else {
                GLog.w(Messages.get(WndJob.class,"nothing"));
            }
        }
    };

}
