/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.eternalpixel.eternalpixeldungeon.ui;

import com.eternalpixel.eternalpixeldungeon.Assets;
import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.EPDAction;
import com.eternalpixel.eternalpixeldungeon.Statistics;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Hunger;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Thirsty;
import com.eternalpixel.eternalpixeldungeon.actors.buffs.Weight;
import com.eternalpixel.eternalpixeldungeon.effects.Speck;
import com.eternalpixel.eternalpixeldungeon.items.Item;
import com.eternalpixel.eternalpixeldungeon.journal.Document;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.scenes.GameScene;
import com.eternalpixel.eternalpixeldungeon.scenes.PixelScene;
import com.eternalpixel.eternalpixeldungeon.sprites.HeroSprite;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSprite;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;
import com.eternalpixel.eternalpixeldungeon.windows.WndGame;
import com.eternalpixel.eternalpixeldungeon.windows.WndHero;
import com.eternalpixel.eternalpixeldungeon.windows.WndJournal;
import com.eternalpixel.eternalpixeldungeon.windows.WndMessage;
import com.eternalpixel.eternalpixeldungeon.windows.WndStory;
import com.watabou.input.GameAction;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.ui.Button;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.ColorMath;

public class StatusPane extends Component {

	private NinePatch bg;
	private Image avatar;
	public static float talentBlink;
	private float warning;

	private static final float FLASH_RATE = (float)(Math.PI*1.5f); //1.5 blinks per second

	private int lastTier = 0;

	private Image rawShielding;
	private Image shieldedHP;
	private Image hp;
	private Image mp;
	private Image sp;
	private BitmapText hpText;
	private Image exp;

	private Image block1;
	private Image block2;
	private Image block3;
	private Image hunger;
	private Image thirsty;
	private Image weight;
	private Image ration;
	private Image milk;
	private Image backpack;
	Hunger hungerlvl = Dungeon.hero.buff(Hunger.class);
	Thirsty thirstylvl = Dungeon.hero.buff(Thirsty.class);
	Weight weightlvl = Dungeon.hero.buff(Weight.class);

	private BossHealthBar bossHP;

	private int lastLvl = -1;

	private BitmapText level;
	private BitmapText depth;

	private DangerIndicator danger;
	private BuffIndicator buffs;
	private Compass compass;

	private JournalButton btnJournal;
	private MenuButton btnMenu;

	private Toolbar.PickedUpItem pickedUp;

	private BitmapText version;

	@Override
	protected void createChildren() {

		bg = new NinePatch( Assets.Interfaces.STATUS, 0, 0, 128, 36, 85, 0, 45, 0 );
		add( bg );

		add( new Button(){
			@Override
			protected void onClick () {
				Camera.main.panTo( Dungeon.hero.sprite.center(), 5f );
				GameScene.show( new WndHero() );
			}

			@Override
			public GameAction keyAction() {
				return EPDAction.HERO_INFO;
			}
		}.setRect( 0, 1, 30, 30 ));

		btnJournal = new JournalButton();
		add( btnJournal );

		btnMenu = new MenuButton();
		add( btnMenu );

		avatar = HeroSprite.avatar( Dungeon.hero.heroClass, lastTier );
		add( avatar );

		talentBlink = 0;

		compass = new Compass( Statistics.amuletObtained ? Dungeon.level.entrance : Dungeon.level.exit );
		add( compass );

		rawShielding = new Image( Assets.Interfaces.SHLD_BAR );
		rawShielding.alpha(0.5f);
		add(rawShielding);

		shieldedHP = new Image( Assets.Interfaces.SHLD_BAR );
		add(shieldedHP);

		hp = new Image( Assets.Interfaces.HP_BAR );
		add( hp );

		mp = new Image( Assets.Interfaces.MP_BAR );
		add( mp );

		sp = new Image( Assets.Interfaces.SP_BAR );
		add( sp );

		hpText = new BitmapText(PixelScene.pixelFont);
		hpText.alpha(0.6f);
		add(hpText);

		exp = new Image( Assets.Interfaces.XP_BAR );
		add( exp );

		bossHP = new BossHealthBar();
		add( bossHP );

		level = new BitmapText( PixelScene.pixelFont);
		level.hardlight( 0xFFFFAA );
		add( level );

		depth = new BitmapText( Integer.toString( Dungeon.depth ), PixelScene.pixelFont);
		depth.hardlight( 0xCACFC2 );
		depth.measure();
		add( depth );

		danger = new DangerIndicator();
		add( danger );

		buffs = new BuffIndicator( Dungeon.hero );
		add( buffs );

		add( pickedUp = new Toolbar.PickedUpItem());

		version = new BitmapText( "v" + Game.version, PixelScene.pixelFont);
		version.alpha( 0.5f );
		add(version);

		block1 = new Image(Assets.Interfaces.STATUSBLOCK);
		block1.frame(block1.texture.uvRect(0,0,22,16));
		add(block1);
		ration = new ItemSprite(ItemSpriteSheet.RATION);
		ration.scale.set(12 / 16f);
		add(ration);

		block2 = new Image(Assets.Interfaces.STATUSBLOCK);
		block2.frame(block2.texture.uvRect(0,0,22,16));
		add(block2);
		milk = new ItemSprite(ItemSpriteSheet.MILK);
		milk.scale.set(12 / 14f);
		add(milk);

		block3 = new Image(Assets.Interfaces.STATUSBLOCK);
		block3.frame(block3.texture.uvRect(0,0,22,16));
		add(block3);
		backpack = new ItemSprite(ItemSpriteSheet.BACKPACK);
		backpack.scale.set(12 / 16f);
		add(backpack);

		add( new Button(){
			@Override
			protected void onClick () {
				GameScene.show(new WndMessage(Messages.get(StatusPane.class,"info")));
			}
		}.setRect(0,31,16,48));

		hunger = new Image(Assets.Interfaces.STATUSBLOCK);
		hunger.frame(hunger.texture.uvRect(24,1,26,15));
		add(hunger);

		thirsty = new Image(Assets.Interfaces.STATUSBLOCK);
		thirsty.frame(thirsty.texture.uvRect(22,1,24,15));
		add(thirsty);

		weight = new Image(Assets.Interfaces.STATUSBLOCK);
		weight.frame(weight.texture.uvRect(26,1,28,15));
		add(weight);
	}

	@Override
	protected void layout() {

		height = 32;

		bg.size( width, bg.height );

		avatar.x = bg.x + 15 - avatar.width / 2f;
		avatar.y = bg.y + 16 - avatar.height / 2f;
		PixelScene.align(avatar);

		compass.x = avatar.x + avatar.width / 2f - compass.origin.x;
		compass.y = avatar.y + avatar.height / 2f - compass.origin.y;
		PixelScene.align(compass);

		hp.x = shieldedHP.x = rawShielding.x = 30;
		hp.y = shieldedHP.y = rawShielding.y = 3;

		mp.x = 30;
		mp.y = 8;
		sp.x = 30;
		sp.y = 13;
		sp.width = 1;

		hpText.scale.set(PixelScene.align(0.5f));
		hpText.x = hp.x + 1;
		hpText.y = hp.y + (hp.height - (hpText.baseLine()+hpText.scale.y))/2f;
		hpText.y -= 0.001f; //prefer to be slightly higher
		PixelScene.align(hpText);

		bossHP.setPos( 6 + (width - bossHP.width())/2, 20);

		depth.x = width - 35.5f - depth.width() / 2f;
		depth.y = 8f - depth.baseLine() / 2f;
		PixelScene.align(depth);

		danger.setPos( width - danger.width(), 20 );

		buffs.setPos( 33, 15 );

		btnJournal.setPos( width - 42, 1 );

		btnMenu.setPos( width - btnMenu.width(), 1 );

		version.scale.set(PixelScene.align(0.5f));
		version.measure();
		version.x = width - version.width();
		version.y = btnMenu.bottom() + (4 - version.baseLine());
		PixelScene.align(version);

		block1.x = block2.x = block3.x = 0;
		block1.y = 31;
		block2.y = 31 + block1.height;
		block3.y = 31 + block1.height + block2.height;

		ration.x = block1.x + 2;
		ration.y = block1.y + 4;

		milk.x = block2.x + 2;
		milk.y = block2.y + 2;

		backpack.x = block3.x + 2;
		backpack.y = block3.y + 2;

		hunger.x = thirsty.x = weight.x = 18;
		hunger.y = block1.y + 15 - hunger.height;
		thirsty.y = block2.y + 15 - thirsty.height;
		weight.y = block3.y + 15 - weight.height;
	}

	private static final int[] warningColors = new int[]{0x660000, 0xCC0000, 0x660000};

	@Override
	public void update() {
		super.update();

		int health = Dungeon.hero.HP;
		int shield = Dungeon.hero.shielding();
		int maxhp = Dungeon.hero.HT;
		int magicpoint = Dungeon.hero.MP;
		int maxmp = Dungeon.hero.MT;
		int stamina = Dungeon.hero.sp();
		int maxsp = Dungeon.hero.maxsp();


		if (!Dungeon.hero.isAlive()) {
			avatar.tint(0x000000, 0.5f);
		} else if ((health/(float)maxhp) < 0.3f) {
			warning += Game.elapsed * 5f *(0.4f - (health/(float)maxhp));
			warning %= 1f;
			avatar.tint(ColorMath.interpolate(warning, warningColors), 0.5f );
		} else if (talentBlink > 0.33f){ //stops early so it doesn't end in the middle of a blink
			talentBlink -= Game.elapsed;
			avatar.tint(1, 1, 0, (float)Math.abs(Math.cos(talentBlink*FLASH_RATE))/2f);
		} else {
			avatar.resetColor();
		}

		hp.scale.x = Math.max( 0,(health-shield)/(float)maxhp);
		shieldedHP.scale.x = health/(float)maxhp;
		rawShielding.scale.x = shield/(float)maxhp;
		mp.scale.x = Math.max( 0,magicpoint/(float)maxmp);
		sp.scale.x = Math.max( 0,stamina/(float)maxsp) ;

		if (shield <= 0){
			hpText.text(health + "/" + maxhp);
		} else {
			hpText.text(health + "+" + shield +  "/" + maxhp);
		}

		exp.scale.x = (width / exp.width) * Dungeon.hero.exp / Dungeon.hero.maxExp();

		if (Dungeon.hero.lvl != lastLvl) {

			if (lastLvl != -1) {
				showStarParticles();
			}

			lastLvl = Dungeon.hero.lvl;
			level.text( Integer.toString( lastLvl ) );
			level.measure();
			level.x = 27.5f - level.width() / 2f;
			level.y = 28.0f - level.baseLine() / 2f;
			PixelScene.align(level);
		}

		int tier = Dungeon.hero.tier();
		if (tier != lastTier) {
			lastTier = tier;
			avatar.copy( HeroSprite.avatar( Dungeon.hero.heroClass, tier ) );
		}

		hunger.scale.y = Math.min(1,Math.max(0,hungerlvl.level/Hunger.SATISFIED));
		thirsty.scale.y = Math.min(1,Math.max(0,thirstylvl.level/Thirsty.WETTING));
		weight.scale.y = 1 - Math.min(1,Math.max(0, (float) weightlvl.level/Weight.OVERWEIGHT));
		hunger.y = block1.y + 15 - hunger.height();
		thirsty.y = block2.y + 15 - thirsty.height();
		weight.y = block3.y + 15 - weight.height();
	}

	public void showStarParticles(){
		Emitter emitter = (Emitter)recycle( Emitter.class );
		emitter.revive();
		emitter.pos( 27, 27 );
		emitter.burst( Speck.factory( Speck.STAR ), 12 );
	}

	public void pickup( Item item, int cell) {
		pickedUp.reset( item,
			cell,
			btnJournal.journalIcon.x + btnJournal.journalIcon.width()/2f,
			btnJournal.journalIcon.y + btnJournal.journalIcon.height()/2f);
	}

	public void flashForPage( String page ){
		btnJournal.flashingPage = page;
	}

	public void updateKeys(){
		btnJournal.updateKeyDisplay();
	}

	private static class JournalButton extends Button {

		private Image bg;
		private Image journalIcon;
		private KeyDisplay keyIcon;

		private String flashingPage = null;

		public JournalButton() {
			super();

			width = bg.width + 13; //includes the depth display to the left
			height = bg.height + 4;
		}

		@Override
		public GameAction keyAction() {
			return EPDAction.JOURNAL;
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			bg = new Image( Assets.Interfaces.MENU, 2, 2, 13, 11 );
			add( bg );

			journalIcon = new Image( Assets.Interfaces.MENU, 31, 0, 11, 7);
			add( journalIcon );

			keyIcon = new KeyDisplay();
			add(keyIcon);
			updateKeyDisplay();
		}

		@Override
		protected void layout() {
			super.layout();

			bg.x = x + 13;
			bg.y = y + 2;

			journalIcon.x = bg.x + (bg.width() - journalIcon.width())/2f;
			journalIcon.y = bg.y + (bg.height() - journalIcon.height())/2f;
			PixelScene.align(journalIcon);

			keyIcon.x = bg.x + 1;
			keyIcon.y = bg.y + 1;
			keyIcon.width = bg.width - 2;
			keyIcon.height = bg.height - 2;
			PixelScene.align(keyIcon);
		}

		private float time;

		@Override
		public void update() {
			super.update();

			if (flashingPage != null){
				journalIcon.am = (float)Math.abs(Math.cos( FLASH_RATE * (time += Game.elapsed) ));
				keyIcon.am = journalIcon.am;
				if (time >= Math.PI/FLASH_RATE) {
					time = 0;
				}
			}
		}

		public void updateKeyDisplay() {
			keyIcon.updateKeys();
			keyIcon.visible = keyIcon.keyCount() > 0;
			journalIcon.visible = !keyIcon.visible;
			if (keyIcon.keyCount() > 0) {
				bg.brightness(.8f - (Math.min(6, keyIcon.keyCount()) / 20f));
			} else {
				bg.resetColor();
			}
		}

		@Override
		protected void onPointerDown() {
			bg.brightness( 1.5f );
			Sample.INSTANCE.play( Assets.Sounds.CLICK );
		}

		@Override
		protected void onPointerUp() {
			if (keyIcon.keyCount() > 0) {
				bg.brightness(.8f - (Math.min(6, keyIcon.keyCount()) / 20f));
			} else {
				bg.resetColor();
			}
		}

		@Override
		protected void onClick() {
			time = 0;
			keyIcon.am = journalIcon.am = 1;
			if (flashingPage != null){
				if (Document.ADVENTURERS_GUIDE.pageNames().contains(flashingPage)){
					GameScene.show( new WndStory( WndJournal.GuideTab.iconForPage(flashingPage),
							Document.ADVENTURERS_GUIDE.pageTitle(flashingPage),
							Document.ADVENTURERS_GUIDE.pageBody(flashingPage) ));
					Document.ADVENTURERS_GUIDE.readPage(flashingPage);
				} else {
					GameScene.show( new WndJournal() );
				}
				flashingPage = null;
			} else {
				GameScene.show( new WndJournal() );
			}
		}

	}

	private static class MenuButton extends Button {

		private Image image;

		public MenuButton() {
			super();

			width = image.width + 4;
			height = image.height + 4;
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			image = new Image( Assets.Interfaces.MENU, 17, 2, 12, 11 );
			add( image );
		}

		@Override
		protected void layout() {
			super.layout();

			image.x = x + 2;
			image.y = y + 2;
		}

		@Override
		protected void onPointerDown() {
			image.brightness( 1.5f );
			Sample.INSTANCE.play( Assets.Sounds.CLICK );
		}

		@Override
		protected void onPointerUp() {
			image.resetColor();
		}

		@Override
		protected void onClick() {
			GameScene.show( new WndGame() );
		}
	}
}