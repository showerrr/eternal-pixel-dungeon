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

package com.eternalpixel.eternalpixeldungeon;

import com.eternalpixel.eternalpixeldungeon.items.scrolls.exotic.ScrollOfMetamorphosis;
import com.eternalpixel.eternalpixeldungeon.scenes.GameScene;
import com.eternalpixel.eternalpixeldungeon.scenes.PixelScene;
import com.eternalpixel.eternalpixeldungeon.scenes.TitleScene;
import com.eternalpixel.eternalpixeldungeon.scenes.WelcomeScene;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.PlatformSupport;

public class EternalPixelDungeon extends Game {

	//variable constants for specific older versions of shattered, used for data conversion
	//versions older than v0.9.0b are no longer supported, and data from them is ignored
	public static final int v0_9_0b  = 489;
	public static final int v0_9_1d  = 511;
	public static final int v0_9_2b  = 531;
	public static final int v0_9_3c  = 557; //557 on iOS, 554 on other platforms

	public static final int v1_0_3   = 574;
	public static final int v1_1_0   = 583;
	
	public EternalPixelDungeon(PlatformSupport platform ) {
		super( sceneClass == null ? WelcomeScene.class : sceneClass, platform );

		//v1.1.0
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.items.scrolls.exotic.ScrollOfDread.class,
				"com.eternalpixel.eternalpixeldungeon.items.scrolls.exotic.ScrollOfPetrification" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.items.scrolls.exotic.ScrollOfSirensSong.class,
				"com.eternalpixel.eternalpixeldungeon.items.scrolls.exotic.ScrollOfAffection" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.items.scrolls.exotic.ScrollOfChallenge.class,
				"com.eternalpixel.eternalpixeldungeon.items.scrolls.exotic.ScrollOfConfusion" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.items.potions.exotic.PotionOfDivineInspiration.class,
				"com.eternalpixel.eternalpixeldungeon.items.potions.exotic.PotionOfHolyFuror" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.items.potions.exotic.PotionOfMastery.class,
				"com.eternalpixel.eternalpixeldungeon.items.potions.exotic.PotionOfAdrenalineSurge" );
		com.watabou.utils.Bundle.addAlias(
				ScrollOfMetamorphosis.class,
				"com.eternalpixel.eternalpixeldungeon.items.scrolls.exotic.ScrollOfPolymorph" );

		//v1.0.0
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.items.stones.StoneOfFear.class,
				"com.eternalpixel.eternalpixeldungeon.items.stones.StoneOfAffection" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.items.stones.StoneOfDeepSleep.class,
				"com.eternalpixel.eternalpixeldungeon.items.stones.StoneOfDeepenedSleep" );

		//v0.9.3
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.actors.mobs.Tengu.class,
				"com.eternalpixel.eternalpixeldungeon.actors.mobs.NewTengu" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.levels.PrisonBossLevel.class,
				"com.eternalpixel.eternalpixeldungeon.levels.NewPrisonBossLevel" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.levels.PrisonBossLevel.ExitVisual.class,
				"com.eternalpixel.eternalpixeldungeon.levels.NewPrisonBossLevel$exitVisual" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.levels.PrisonBossLevel.ExitVisualWalls.class,
				"com.eternalpixel.eternalpixeldungeon.levels.NewPrisonBossLevel$exitVisualWalls" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.actors.mobs.DM300.class,
				"com.eternalpixel.eternalpixeldungeon.actors.mobs.NewDM300" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.levels.CavesBossLevel.class,
				"com.eternalpixel.eternalpixeldungeon.levels.NewCavesBossLevel" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.levels.CavesBossLevel.PylonEnergy.class,
				"com.eternalpixel.eternalpixeldungeon.levels.NewCavesBossLevel$PylonEnergy" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.levels.CavesBossLevel.ArenaVisuals.class,
				"com.eternalpixel.eternalpixeldungeon.levels.NewCavesBossLevel$ArenaVisuals" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.levels.CavesBossLevel.CityEntrance.class,
				"com.eternalpixel.eternalpixeldungeon.levels.NewCavesBossLevel$CityEntrance" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.levels.CavesBossLevel.EntranceOverhang.class,
				"com.eternalpixel.eternalpixeldungeon.levels.NewCavesBossLevel$EntranceOverhang" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.levels.CityBossLevel.class,
				"com.eternalpixel.eternalpixeldungeon.levels.NewCityBossLevel" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.levels.CityBossLevel.CustomGroundVisuals.class,
				"com.eternalpixel.eternalpixeldungeon.levels.NewCityBossLevel$CustomGroundVisuals" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.levels.CityBossLevel.CustomWallVisuals.class,
				"com.eternalpixel.eternalpixeldungeon.levels.NewCityBossLevel$CustomWallVisuals" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.levels.HallsBossLevel.class,
				"com.eternalpixel.eternalpixeldungeon.levels.NewHallsBossLevel" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.levels.HallsBossLevel.CenterPieceVisuals.class,
				"com.eternalpixel.eternalpixeldungeon.levels.NewHallsBossLevel$CenterPieceWalls" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.levels.HallsBossLevel.CenterPieceWalls.class,
				"com.eternalpixel.eternalpixeldungeon.levels.NewHallsBossLevel$CenterPieceWalls" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.items.Waterskin.class,
				"com.eternalpixel.eternalpixeldungeon.items.DewVial" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.items.TengusMask.class,
				"com.eternalpixel.eternalpixeldungeon.items.TomeOfMastery" );
		com.watabou.utils.Bundle.addAlias(
				com.eternalpixel.eternalpixeldungeon.items.KingsCrown.class,
				"com.eternalpixel.eternalpixeldungeon.items.ArmorKit" );
		
	}
	
	@Override
	public void create() {
		super.create();

		updateSystemUI();
		EPDAction.loadBindings();
		
		Music.INSTANCE.enable( EPDSettings.music() );
		Music.INSTANCE.volume( EPDSettings.musicVol()* EPDSettings.musicVol()/100f );
		Sample.INSTANCE.enable( EPDSettings.soundFx() );
		Sample.INSTANCE.volume( EPDSettings.SFXVol()* EPDSettings.SFXVol()/100f );

		Sample.INSTANCE.load( Assets.Sounds.all );
		
	}

	@Override
	public void finish() {
		if (!DeviceCompat.isiOS()) {
			super.finish();
		} else {
			//can't exit on iOS (Apple guidelines), so just go to title screen
			switchScene(TitleScene.class);
		}
	}

	public static void switchNoFade(Class<? extends PixelScene> c){
		switchNoFade(c, null);
	}

	public static void switchNoFade(Class<? extends PixelScene> c, SceneChangeCallback callback) {
		PixelScene.noFade = true;
		switchScene( c, callback );
	}
	
	public static void seamlessResetScene(SceneChangeCallback callback) {
		if (scene() instanceof PixelScene){
			((PixelScene) scene()).saveWindows();
			switchNoFade((Class<? extends PixelScene>) sceneClass, callback );
		} else {
			resetScene();
		}
	}
	
	public static void seamlessResetScene(){
		seamlessResetScene(null);
	}
	
	@Override
	protected void switchScene() {
		super.switchScene();
		if (scene instanceof PixelScene){
			((PixelScene) scene).restoreWindows();
		}
	}
	
	@Override
	public void resize( int width, int height ) {
		if (width == 0 || height == 0){
			return;
		}

		if (scene instanceof PixelScene &&
				(height != Game.height || width != Game.width)) {
			PixelScene.noFade = true;
			((PixelScene) scene).saveWindows();
		}

		super.resize( width, height );

		updateDisplaySize();

	}
	
	@Override
	public void destroy(){
		super.destroy();
		GameScene.endActorThread();
	}
	
	public void updateDisplaySize(){
		platform.updateDisplaySize();
	}

	public static void updateSystemUI() {
		platform.updateSystemUI();
	}
}