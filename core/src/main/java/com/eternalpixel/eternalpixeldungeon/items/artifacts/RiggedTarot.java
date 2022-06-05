package com.eternalpixel.eternalpixeldungeon.items.artifacts;

import com.eternalpixel.eternalpixeldungeon.Assets;
import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.actors.Actor;
import com.eternalpixel.eternalpixeldungeon.actors.Char;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Barrier;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Blindness;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Buff;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Burning;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Cripple;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Invisibility;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Poison;
import com.eternalpixel.eternalpixeldungeon.actors.hero.Hero;
import com.eternalpixel.eternalpixeldungeon.actors.mobs.Mob;
import com.eternalpixel.eternalpixeldungeon.effects.MagicMissile;
import com.eternalpixel.eternalpixeldungeon.effects.newBeam;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.eternalpixel.eternalpixeldungeon.mechanics.Ballistica;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.scenes.CellSelector;
import com.eternalpixel.eternalpixeldungeon.scenes.GameScene;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;
import com.eternalpixel.eternalpixeldungeon.sprites.MissileSprite;
import com.eternalpixel.eternalpixeldungeon.tiles.DungeonTilemap;
import com.eternalpixel.eternalpixeldungeon.ui.BuffIndicator;
import com.eternalpixel.eternalpixeldungeon.utils.GLog;
import com.eternalpixel.eternalpixeldungeon.windows.WndWishing;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;

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
//                card = Random.Int(5);
                card = 9;
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
            card = Random.Int(5);
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
                GameScene.selectCell(cell);
                break;
            case 4:
                GameScene.selectCell(cell);
                break;
            case 5:
            case 6:
            case 7:
            case 8:
                int hp = curUser.HP;
                curUser.HP = Math.max(1,hp - curUser.HT * 3 / 4);
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show(new WndWishing());
                    }
                });
                break;
            case 9:
                timeCard timeCard = new timeCard();
                timeCard.set(curUser.HP,Dungeon.depth,curUser.pos);
                timeCard.attachTo(Dungeon.hero);
                break;
            default:
                break;
        }
    }

    public void changeCard() {
        image = ItemSpriteSheet.TAROT + card + 1;
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

            final Char enemy = Actor.findChar(cell);

            switch (lastCard) {
                case 1:
                    ArrayList<Integer> abc = new ArrayList<>(Arrays.asList(-1,0,1));
                    for(int j : abc) {
                        Ballistica b = new Ballistica(curUser.pos + j,cell,Ballistica.PROJECTILE);
                        curUser.sprite.parent.add(new newBeam(DungeonTilemap.raisedTileCenterToWorld(b.sourcePos), DungeonTilemap.raisedTileCenterToWorld(b.collisionPos)));
                    }


                    if (enemy != null) {
                        enemy.damage(Dungeon.hero.lvl * 4,Dungeon.hero);
                    }

                    curUser.spendAndNext(1f);
                    break;

                case 3:
                    ((MissileSprite) curUser.sprite.parent.recycle(MissileSprite.class)).reset(curUser.sprite, cell, RiggedTarot.this, new Callback() {
                        @Override
                        public void call() {
                            Ballistica ba = new Ballistica(curUser.pos,cell,Ballistica.STOP_TARGET);
                            for (int i : ba.path) {
                                Char enemy = Actor.findChar(i);
                                if (enemy != null && enemy != curUser) {
                                    Buff.affect(enemy, Cripple.class,3f);
                                    Buff.affect(enemy, Poison.class).set(2f);
                                    Buff.affect(enemy, Blindness.class,1f);
                                }
                            }
                        }
                    });
                    curUser.spendAndNext(1f);
                    break;

                case 4:
                    fireCard(curUser.pos,cell);
                    break;
            }

        }

        @Override
        public String prompt() {
            return Messages.get(this, "title");
        }
    };

    public class rogueCard extends Buff {

        protected float left = 10f;
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

    private static int nextFrom;
    private static int nextTo;

    public void fireCard(int from,int to) {
        MagicMissile mm = ((MagicMissile) Dungeon.hero.sprite.parent.recycle(MagicMissile.class));

        mm.reset(MagicMissile.FIRE, from, to, new Callback() {
            @Override
            public void call() {
                Char thisEnemy = Actor.findChar(to);
                if (thisEnemy instanceof Mob) {
                    if (thisEnemy.buff(Burning.class) != null){
                        Buff.affect(thisEnemy, Burning.class).reignite(thisEnemy, curUser.lvl * 4f);
                        int burnDamage = Random.NormalIntRange( 1, 3 + Dungeon.depth/4 );
                        thisEnemy.damage( Math.round(burnDamage * 0.67f), this );
                    } else {
                        Buff.affect(thisEnemy, Burning.class).reignite(thisEnemy, curUser.lvl * 4f);
                    }
                }

//                for (int i : PathFinder.NEIGHBOURS24) {
//                    Char nextEnemy = Actor.findChar(to + i);
//                    if (nextEnemy != null && nextEnemy instanceof Mob) {
//                        nextFrom = to;
//                        nextTo = nextEnemy.pos;
//                        fireCard(from,to);
//                    }
//                }
                curUser.spendAndNext(1f);
            }
        });
    }

    public class timeCard extends Buff {

        protected float left = 20f;
        public int hp = 0;
        public int depth = -1;
        public int pos = -1;

        {
            type = buffType.POSITIVE;
        }

        public void set(int hp,int depth,int pos) {
            this.hp = hp;
            this.depth = depth;
            this.pos = pos;
        }

        @Override
        public boolean act() {
            if (target.isAlive()) {

                spend( TICK );

                if (--left <= 0) {
                    int curHp = Dungeon.hero.HP;
                    Dungeon.hero.HP = Math.max(curHp,hp);
                    if (Dungeon.depth == depth) {
                        ScrollOfTeleportation.appear(curUser,pos);
                        Dungeon.hero.interrupt();
                        Dungeon.level.occupyCell(curUser);
                        Dungeon.observe();
                        GameScene.updateFog();
                    }
                    detach();
                    return true;
                }
            } else {

                detach();
            }

            return true;
        }

        @Override
        public int icon() {
            return BuffIndicator.TIME;
        }
    }
}
