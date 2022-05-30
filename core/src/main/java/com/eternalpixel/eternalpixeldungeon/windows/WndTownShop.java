package com.eternalpixel.eternalpixeldungeon.windows;

import com.eternalpixel.eternalpixeldungeon.Assets;
import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.LostInventory;
import com.eternalpixel.eternalpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.eternalpixel.eternalpixeldungeon.items.EquipableItem;
import com.eternalpixel.eternalpixeldungeon.items.Gold;
import com.eternalpixel.eternalpixeldungeon.items.Item;
import com.eternalpixel.eternalpixeldungeon.items.bags.Bag;
import com.eternalpixel.eternalpixeldungeon.items.wands.Wand;
import com.eternalpixel.eternalpixeldungeon.levels.TownLevel;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.scenes.GameScene;
import com.eternalpixel.eternalpixeldungeon.scenes.PixelScene;
import com.eternalpixel.eternalpixeldungeon.ui.ItemSlot;
import com.eternalpixel.eternalpixeldungeon.ui.RedButton;
import com.eternalpixel.eternalpixeldungeon.ui.Window;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

import java.util.ArrayList;

public class WndTownShop extends Window {

    public static Window INSTANCE;

    public static int WIDTH;
    public static int HEIGHT;

    protected static final int SLOT_MARGIN	= 1;//1

    protected static final int TITLE_HEIGHT	= 20;//14

    private int nCols;
    private int nRows;

    private int slotWidth = 20;
    private int slotHeight = 20;

    protected int count;
    protected int col;
    protected int row;

    private static int TYPE;

    public WndTownShop(int type) {

        super();

        nCols = PixelScene.landscape() ? 14 : 7;
        nRows = (int)Math.ceil(62/(float)nCols); //we expect to lay out 25(63) slots in all cases

        WIDTH = slotWidth * nCols + SLOT_MARGIN * (nCols - 1);
        HEIGHT = TITLE_HEIGHT + slotHeight * nRows + SLOT_MARGIN * (nRows - 1);

        if (PixelScene.landscape()){
            while (slotHeight >= 24 && (HEIGHT + 20 + chrome.marginTop()) > PixelScene.uiCamera.height){
                slotHeight--;
                HEIGHT -= nRows;
            }
        } else {
            while (slotWidth >= 26 && (WIDTH + chrome.marginHor()) > PixelScene.uiCamera.width){
                slotWidth--;
                WIDTH -= nCols;
            }
        }


        if ( INSTANCE != null ){
            INSTANCE.hide();
        }
        INSTANCE = this;

        nCols = PixelScene.landscape() ? 14 : 7;
        nRows = (int)Math.ceil(62/(float)nCols); //we expect to lay out 25(63) slots in all cases

        resize(WIDTH, HEIGHT);

        ArrayList<Item> itemsToSpawn = new ArrayList<Item>();
        TYPE = type;
        int j;
        switch (TYPE) {
            case 1:
                for (j = 0;j < TownLevel.shop1.size();j++) itemsToSpawn.add(TownLevel.shop1.get(j));
                break;
            case 2:
                for (j = 0;j < TownLevel.shop2.size();j++) itemsToSpawn.add(TownLevel.shop2.get(j));
                break;
            default:
                itemsToSpawn = TownLevel.shop2;
        }

        for (Item item : itemsToSpawn) {
            if (item != null && item.quantity > 0) {
                placeItem(item);
            }
        }

    }

    private void placeItem( Item item ) {

        count++;

        int x = col * (slotWidth + SLOT_MARGIN);
        int y = TITLE_HEIGHT + row * (slotHeight + SLOT_MARGIN);

        add( new ItemButton(item).setPos( x, y ) );

        if (++col >= nCols) {
            col = 0;
            row++;
        }

    }

    private class ItemButton extends ItemSlot {

        private static final int NORMAL		= 0x9953564D;
        private static final int EQUIPPED	= 0x9991938C;

        private Item item;
        private ColorBlock bg;

        public ItemButton(Item item) {

            super( item );

            this.item = item;

            if (item instanceof Gold || item instanceof Bag) {
                bg.visible = false;
            }

            width = slotWidth;
            height = slotHeight;
        }

        @Override
        protected void createChildren() {
            bg = new ColorBlock( 1, 1, NORMAL );
            add( bg );

            super.createChildren();
        }

        @Override
        protected void layout() {
            bg.size(width, height);
            bg.x = x;
            bg.y = y;

            super.layout();
        }

        @Override
        public void item( Item item ) {

            super.item( item );
            if (item != null ) {

                bg.texture( TextureCache.createSolid(  NORMAL ) );
                if (item.cursed && item.cursedKnown) {
                    bg.ra = +0.3f;
                    bg.ga = -0.15f;
                } else if (!item.isIdentified()) {
                    if ((item instanceof EquipableItem || item instanceof Wand) && item.cursedKnown){
                        bg.ba = 0.3f;
                    } else {
                        bg.ra = 0.3f;
                        bg.ba = 0.3f;
                    }
                }

                if (item.name() == null) {
                    enable( false );
                } else if (Dungeon.hero.buff(LostInventory.class) != null
                        && !item.keptThoughLostInvent){
                    enable(false);
                }
            } else {
                bg.color( NORMAL );
            }
        }

        @Override
        protected void onPointerDown() {
            bg.brightness( 1.5f );
            Sample.INSTANCE.play( Assets.Sounds.CLICK, 0.7f, 0.7f, 1.2f );
        }

        protected void onPointerUp() {
            bg.brightness( 1.0f );
        }

        @Override
        protected void onClick() {
            Game.scene().addToFront(new WndConfirmBuy(item) );
        }
    }

    public class WndConfirmBuy extends WndInfoItem {

        private static final float GAP		= 2;
        private static final int BTN_HEIGHT	= 16;

        public WndConfirmBuy(Item item) {
            super(item);

            float pos = height;
            int price = item.value() * 5 * (Dungeon.depth / 5 + 1);

            RedButton btnBuy = new RedButton(price <= Dungeon.gold ? Messages.get(this,"not_enough",price) :Messages.get(this,"buy",price)) {
                @Override
                protected void onClick() {
                    hide();
                    buyComplete(item,TYPE);
                }
            };
            btnBuy.setRect(0,pos + GAP,width,BTN_HEIGHT);
            btnBuy.enable(price <= Dungeon.gold);
            add(btnBuy);

            resize(width, (int) (pos + GAP + BTN_HEIGHT));
        }

    }

    public void buyComplete(Item item,int type) {
        this.TYPE = type;

        switch (type) {
            case 1:
                for (int j = 0;j < TownLevel.shop1.size();j++) {
                    if (item.isSimilar(TownLevel.shop1.get(j))){
                        TownLevel.shop1.remove(TownLevel.shop1.get(j));
                        break;
                    }
                }
            case 2:
                for (int j = 0;j < TownLevel.shop2.size();j++) {
                    if (item.isSimilar(TownLevel.shop2.get(j))){
                        TownLevel.shop2.remove(TownLevel.shop2.get(j));
                        break;
                    }
                }
        }

        int price = Shopkeeper.sellPrice( item );
        Dungeon.gold -= price;
        item.doPickUp(Dungeon.hero);
//        Dungeon.level.drop(item,Dungeon.hero.pos).sprite.drop();
        Game.runOnRenderThread(new Callback() {
            @Override
            public void call() {
                GameScene.show(new WndTownShop(TYPE));
            }
        });
    }


}