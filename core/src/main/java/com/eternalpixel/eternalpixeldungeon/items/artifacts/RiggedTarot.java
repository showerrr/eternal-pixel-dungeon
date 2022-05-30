package com.eternalpixel.eternalpixeldungeon.items.artifacts;

import com.eternalpixel.eternalpixeldungeon.Assets;
import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.actors.Actor;
import com.eternalpixel.eternalpixeldungeon.actors.Char;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Barrier;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Buff;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.FlavourBuff;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Invisibility;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.ShieldBuff;
import com.eternalpixel.eternalpixeldungeon.actors.hero.Hero;
import com.eternalpixel.eternalpixeldungeon.actors.hero.Talent;
import com.eternalpixel.eternalpixeldungeon.actors.mobs.Mob;
import com.eternalpixel.eternalpixeldungeon.effects.newBeam;
import com.eternalpixel.eternalpixeldungeon.mechanics.Ballistica;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.scenes.CellSelector;
import com.eternalpixel.eternalpixeldungeon.scenes.GameScene;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;
import com.eternalpixel.eternalpixeldungeon.tiles.DungeonTilemap;
import com.eternalpixel.eternalpixeldungeon.ui.BuffIndicator;
import com.eternalpixel.eternalpixeldungeon.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;

import sun.security.x509.CertAttrSet;

public class RiggedTarot extends Artifact {

    public static final String AC_DRAW = "DRAW";
    public static final String AC_REDRAW = "REDRAW";
    public static final String AC_PLAY = "PLAY";

    private int card = -1;
    private int lastCard = -1;

    {
        image = ItemSpriteSheet.TAROT;
        defaultAction = AC_DRAW;
        chargeCap = 1;
        charge = 1;
        unique = true;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = new ArrayList<>();
//        actions = super.actions(hero);
        if (isEquipped(hero)) {
            if (card == -1) {
                actions.add(AC_DRAW);
            } else {
                actions.add(AC_PLAY);
//                if (charge >= 1) {
//                    actions.add(AC_REDRAW);
//                }
            }
        }
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero,action);

        if (action.equals(AC_DRAW) && card == -1) {

            if (charge <= 0) {
                GLog.i(Messages.get(this,"no_charge"));
            } else if (cursed) {
                GLog.i(Messages.get(this,"cursed"));
            } else {
                defaultAction = AC_PLAY;
                charge--;
//                card = Random.Int(4);
                card = 1;
                changeCard();
                updateQuickslot();
                return;
            }
        }

        if ((action.equals(AC_PLAY) && card != -1) || (action.equals(AC_DRAW) && card != -1)) {
            defaultAction = AC_DRAW;
            Sample.INSTANCE.play(Assets.Sounds.ZAP);
            lastCard = card;
            selectEffect();
            card = -1;
            changeCard();
            updateQuickslot();
            return;
        }

        if (action.equals(AC_REDRAW) && card != -1) {
            card = Random.Int(22);
            changeCard();
            updateQuickslot();
            return;
        }
    }

    public void selectEffect() {
        switch (card) {
            case 0:
                Buff.affect(curUser,Barrier.class).setShield(curUser.HT / 5);
                break;
            case 1:
                GameScene.selectCell(cell);
                break;
            case 2:
                rogueCard rogueCard = new rogueCard();
                rogueCard.attachTo(Dungeon.hero);
                Buff.affect(Dungeon.hero,Invisibility.class,Invisibility.DURATION);
                curUser.spendAndNext(1f);
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            default:
                break;
        }
    }

    public void changeCard() {
        switch (card) {
            case -1:
                image = ItemSpriteSheet.TAROT;
                break;
            case 0:
                image = ItemSpriteSheet.TAROT0;
                break;
            case 1:
                image = ItemSpriteSheet.TAROT1;
                break;
            case 2:
                image = ItemSpriteSheet.TAROT2;
                break;
            case 3:
                image = ItemSpriteSheet.TAROT3;
                break;
        }
    }

    @Override
    protected ArtifactBuff passiveBuff() {
        return new tarotRecharge();
    }

    @Override
    public void charge(Hero target, float amount) {
        if (charge < chargeCap){
            partialCharge += 0.1f*amount;
            if (partialCharge >= 1){
                partialCharge--;
                charge++;
                updateQuickslot();
            }
        }
    }

    public class tarotRecharge extends ArtifactBuff {
        @Override
        public boolean act() {
            if (charge < chargeCap) {
                partialCharge += 0.05f;
                if (partialCharge >= 1) {
                    partialCharge--;
                    charge++;
                    if (charge == chargeCap) {
                        partialCharge = 0;
                    }
                }
            } else {
                partialCharge = 0;
            }

            updateQuickslot();
            spend(TICK);
            return true;
        }
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    private static final String CHARGE_CAP = "charge_cap";
    private static final String CARD = "card";
    private static final String IMAGE = "image";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(CHARGE_CAP,chargeCap);
        bundle.put(CARD,card);
        bundle.put(IMAGE,image);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        chargeCap = bundle.getInt(CHARGE_CAP);
        if (bundle.contains(CARD)) {
            card = bundle.getInt(CARD);
        }
        if (bundle.contains(IMAGE)) {
            image = bundle.getInt(IMAGE);
        }
    }

    public CellSelector.Listener cell = new CellSelector.Listener(){

        @Override
        public void onSelect(Integer cell) {
            if (cell == null) return;

            switch (lastCard) {
                case 1:
                    ArrayList<Integer> abc = new ArrayList<>(Arrays.asList(-1,0,1));
                    for(int j : abc) {
                        Ballistica b = new Ballistica(curUser.pos + j,cell,Ballistica.PROJECTILE);
                        curUser.sprite.parent.add(new newBeam(DungeonTilemap.raisedTileCenterToWorld(b.sourcePos), DungeonTilemap.raisedTileCenterToWorld(b.collisionPos)));
                    }

                    final Char enemy = Actor.findChar(cell);
                    if (enemy != null) {
                        enemy.damage(Dungeon.hero.lvl * 4,Dungeon.hero);
                    }

                    curUser.spendAndNext(1f);
            }

        }

        @Override
        public String prompt() {
            return Messages.get(this, "title");
        }
    };

    public class rogueCard extends Buff {

        protected float left = 20f;
        public int time = 0;

        {
            type = buffType.POSITIVE;
        }

        @Override
        public boolean act() {
            if (target.isAlive()) {

                if (time > 0) {
                    Buff.affect(Dungeon.hero,Invisibility.class,10);
                    time--;
                }

                spend( TICK );

                if (--left <= 0) {
                    detach();
                    return true;
                }
            } else {

                detach();

            }

            return true;
        }

        public void prolong() {
            left = 20f;
            time++;
        }

        @Override
        public int icon() {
            return BuffIndicator.NONE;
        }

        @Override
        public float iconFadePercent() {
            return 0;
        }

        private static final String LEFT	= "left";

        @Override
        public void storeInBundle( Bundle bundle ) {
            super.storeInBundle( bundle );
            bundle.put( LEFT, left );
        }

        @Override
        public void restoreFromBundle( Bundle bundle ) {
            super.restoreFromBundle( bundle );
            left = bundle.getFloat( LEFT );
        }
    }
}
