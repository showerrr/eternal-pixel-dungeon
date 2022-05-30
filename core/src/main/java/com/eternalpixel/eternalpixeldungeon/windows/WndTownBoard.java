package com.eternalpixel.eternalpixeldungeon.windows;

import com.eternalpixel.eternalpixeldungeon.Assets;
import com.eternalpixel.eternalpixeldungeon.Chrome;
import com.eternalpixel.eternalpixeldungeon.EternalPixelDungeon;
import com.eternalpixel.eternalpixeldungeon.Statistics;
import com.eternalpixel.eternalpixeldungeon.items.Item;
import com.eternalpixel.eternalpixeldungeon.items.food.FlourFood;
import com.eternalpixel.eternalpixeldungeon.items.food.Noodles;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.scenes.PixelScene;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSprite;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;
import com.eternalpixel.eternalpixeldungeon.ui.ItemSlot;
import com.eternalpixel.eternalpixeldungeon.ui.RenderedTextBlock;
import com.eternalpixel.eternalpixeldungeon.ui.Window;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.Bundle;

public class WndTownBoard extends Window {

    public static Item jobitem = null;
    public static Item jobitem2 = null;
    private static final int type = 1;
    private static final int type2 = 2;

    private static int charge = 5;

//    public static boolean needReset = false;
    private static float lastDuration = 0f;

    private static final int WIDTH		= 120;
    private static final int HEIGHT		= 120;

    public WndTownBoard() {

        super();
        resize( WIDTH, HEIGHT );

        IconTitle titlebar = new IconTitle();
        titlebar.icon( new ItemSprite(ItemSpriteSheet.SIGH , null ) );
        titlebar.label( Messages.get(this,"title"));
        titlebar.setRect( 0, 0, WIDTH, 0 );
        add( titlebar );

//        String title = Messages.get(this,"title");
//        final RenderedTextBlock txtTitle = PixelScene.renderTextBlock(Messages.titleCase(title),9);
//        txtTitle.hardlight(Window.TITLE_COLOR);
//        txtTitle.setPos((WIDTH - txtTitle.width()) / 2, 2);
//        add(txtTitle);

        if ((Statistics.duration-lastDuration) > 100) {
            charge += (int)((Statistics.duration-lastDuration)/50);
//            needReset = true;
            lastDuration = Statistics.duration;
        }

//        if (jobitem == null || jobitem2 == null || needReset) {
//            reset();
//        }

        if (jobitem != null){
            JobButton jobButton1 = new JobButton(jobitem,type);
            jobButton1.setRect( 0,10 + titlebar.height(),WIDTH/2,(HEIGHT - titlebar.height())/4);
            add( jobButton1 );
        } else if (charge > 0){
            reset(1);
            charge--;
            JobButton jobButton1 = new JobButton(jobitem,type);
            jobButton1.setRect( 0,10 + titlebar.height(),WIDTH/2,(HEIGHT - titlebar.height())/4);
            add( jobButton1 );
        }

        if (jobitem2 != null){
            JobButton jobButton2 = new JobButton(jobitem2,type2);
            jobButton2.setRect( WIDTH/2,10 + titlebar.height(),WIDTH/2,(HEIGHT - titlebar.height())/4);
            add( jobButton2 );
        } else if (charge > 0){
            reset(2);
            charge--;
            JobButton jobButton2 = new JobButton(jobitem2,type2);
            jobButton2.setRect( WIDTH/2,10 + titlebar.height(),WIDTH/2,(HEIGHT - titlebar.height())/4);
            add( jobButton2 );
        }

        if (jobitem == null && jobitem2 == null) {
            RenderedTextBlock txtNoJobs = PixelScene.renderTextBlock(Messages.get(this,"no_jobs"), 8);
            txtNoJobs.maxWidth(WIDTH);
            txtNoJobs.setPos(titlebar.left(), titlebar.bottom() + 5);
            add( txtNoJobs );
        }

    }

    public class JobButton extends Component {

        protected NinePatch bg;
        protected ItemSlot slot;
        Item item ;

        public JobButton(Item jobitem,int t){
            bg = Chrome.get( Chrome.Type.RED_BUTTON);
            add( bg );


            slot = new ItemSlot( jobitem ){
                @Override
                protected void onPointerDown() {
                    bg.brightness( 1.2f );
                    Sample.INSTANCE.play( Assets.Sounds.CLICK );
                }
                @Override
                protected void onPointerUp() {
                    bg.resetColor();
                }
                @Override
                protected void onClick() {
                    hide();
                    EternalPixelDungeon.scene().addToFront(new WndJob(jobitem,t));
//                    GameScene.show(new WndJob(item));
                }
            };
            add(slot);
        }
        @Override
        protected void layout() {
            super.layout();

            bg.x = x;
            bg.y = y;
            bg.size( width, height );

            slot.setRect( x + 2, y + 2, width - 4, height - 4 );
        }

        public void item( Item item ) {
            slot.item( this.item = item );
        }

        public void clear(){
            slot.clear();
        }
    }

    public static void reset() {
        reset(1);
        reset(2);
    }

    public static void reset(int i) {
        if (i == 1) {
            jobitem = new FlourFood().Random(1,10).identify();
        } else if (i == 2) {
            jobitem2 = new Noodles().Random(1,10).identify();
        }
    }

    private static final String JOBITEM     = "jobitem";
    private static final String JOBITEM2     = "jobitem2";
    private static final String CHARGE     = "charge";

    public static void storeInBundle( Bundle bundle ) {
        bundle.put( JOBITEM, jobitem );
        bundle.put( JOBITEM2, jobitem2 );
        bundle.put( CHARGE, charge );
//        bundle.put( NEEDRESET, needReset );
    }

    public static void restoreFromBundle( Bundle bundle ) {
        if (!bundle.isNull()) {
            jobitem = (Item)bundle.get( JOBITEM );
            jobitem2 = (Item)bundle.get( JOBITEM2 );
            charge = bundle.getInt( CHARGE );
//            needReset = bundle.getBoolean( NEEDRESET) ;
        } else {
            reset();
        }
    }

}
