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

package com.eternalpixel.eternalpixeldungeon.items;

import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.items.armor.Armor;
import com.eternalpixel.eternalpixeldungeon.items.armor.ClothArmor;
import com.eternalpixel.eternalpixeldungeon.items.armor.LeatherArmor;
import com.eternalpixel.eternalpixeldungeon.items.armor.MailArmor;
import com.eternalpixel.eternalpixeldungeon.items.armor.PlateArmor;
import com.eternalpixel.eternalpixeldungeon.items.armor.ScaleArmor;
import com.eternalpixel.eternalpixeldungeon.items.artifacts.AlchemistsToolkit;
import com.eternalpixel.eternalpixeldungeon.items.artifacts.Artifact;
import com.eternalpixel.eternalpixeldungeon.items.artifacts.CapeOfThorns;
import com.eternalpixel.eternalpixeldungeon.items.artifacts.ChaliceOfBlood;
import com.eternalpixel.eternalpixeldungeon.items.artifacts.CloakOfShadows;
import com.eternalpixel.eternalpixeldungeon.items.artifacts.DriedRose;
import com.eternalpixel.eternalpixeldungeon.items.artifacts.EtherealChains;
import com.eternalpixel.eternalpixeldungeon.items.artifacts.HornOfPlenty;
import com.eternalpixel.eternalpixeldungeon.items.artifacts.LloydsBeacon;
import com.eternalpixel.eternalpixeldungeon.items.artifacts.MasterThievesArmband;
import com.eternalpixel.eternalpixeldungeon.items.artifacts.SandalsOfNature;
import com.eternalpixel.eternalpixeldungeon.items.artifacts.TalismanOfForesight;
import com.eternalpixel.eternalpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.eternalpixel.eternalpixeldungeon.items.artifacts.UnstableSpellbook;
import com.eternalpixel.eternalpixeldungeon.items.cookingtools.BarbecueSet;
import com.eternalpixel.eternalpixeldungeon.items.cookingtools.Campfire;
import com.eternalpixel.eternalpixeldungeon.items.cookingtools.CookingTools;
import com.eternalpixel.eternalpixeldungeon.items.cookingtools.FoodMaker;
import com.eternalpixel.eternalpixeldungeon.items.cookingtools.FoodProcessor;
import com.eternalpixel.eternalpixeldungeon.items.cookingtools.Kitchen;
import com.eternalpixel.eternalpixeldungeon.items.cookingtools.KitchenOven;
import com.eternalpixel.eternalpixeldungeon.items.cookingtools.Oven;
import com.eternalpixel.eternalpixeldungeon.items.cookingtools.PortableCookingTool;
import com.eternalpixel.eternalpixeldungeon.items.cookingtools.Pot;
import com.eternalpixel.eternalpixeldungeon.items.cookingtools.Sink;
import com.eternalpixel.eternalpixeldungeon.items.food.Food;
import com.eternalpixel.eternalpixeldungeon.items.food.MysteryMeat;
import com.eternalpixel.eternalpixeldungeon.items.food.Pasty;
import com.eternalpixel.eternalpixeldungeon.items.food.FlourFood;
import com.eternalpixel.eternalpixeldungeon.items.food.Noodles;
import com.eternalpixel.eternalpixeldungeon.items.food.vegetables.EdibleWildPlant;
import com.eternalpixel.eternalpixeldungeon.items.food.vegetables.GreenPea;
import com.eternalpixel.eternalpixeldungeon.items.food.vegetables.HealthyLeaf;
import com.eternalpixel.eternalpixeldungeon.items.food.vegetables.Leccho;
import com.eternalpixel.eternalpixeldungeon.items.food.vegetables.Lettuce;
import com.eternalpixel.eternalpixeldungeon.items.food.vegetables.Melon;
import com.eternalpixel.eternalpixeldungeon.items.food.vegetables.Potato;
import com.eternalpixel.eternalpixeldungeon.items.food.vegetables.Squash;
import com.eternalpixel.eternalpixeldungeon.items.food.vegetables.SweetPotato;
import com.eternalpixel.eternalpixeldungeon.items.food.vegetables.Taro;
import com.eternalpixel.eternalpixeldungeon.items.food.vegetables.Tomato;
import com.eternalpixel.eternalpixeldungeon.items.food.vegetables.Vegetable;
import com.eternalpixel.eternalpixeldungeon.items.potions.Potion;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfExperience;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfFrost;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfHaste;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfHealing;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfInvisibility;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfLevitation;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfMindVision;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfParalyticGas;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfPurity;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfStrength;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfToxicGas;
import com.eternalpixel.eternalpixeldungeon.items.rings.Ring;
import com.eternalpixel.eternalpixeldungeon.items.rings.RingOfAccuracy;
import com.eternalpixel.eternalpixeldungeon.items.rings.RingOfElements;
import com.eternalpixel.eternalpixeldungeon.items.rings.RingOfEnergy;
import com.eternalpixel.eternalpixeldungeon.items.rings.RingOfEvasion;
import com.eternalpixel.eternalpixeldungeon.items.rings.RingOfForce;
import com.eternalpixel.eternalpixeldungeon.items.rings.RingOfFuror;
import com.eternalpixel.eternalpixeldungeon.items.rings.RingOfHaste;
import com.eternalpixel.eternalpixeldungeon.items.rings.RingOfMight;
import com.eternalpixel.eternalpixeldungeon.items.rings.RingOfSharpshooting;
import com.eternalpixel.eternalpixeldungeon.items.rings.RingOfTenacity;
import com.eternalpixel.eternalpixeldungeon.items.rings.RingOfWealth;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.Scroll;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfRage;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfTerror;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.eternalpixel.eternalpixeldungeon.items.stones.Runestone;
import com.eternalpixel.eternalpixeldungeon.items.stones.StoneOfFear;
import com.eternalpixel.eternalpixeldungeon.items.stones.StoneOfAggression;
import com.eternalpixel.eternalpixeldungeon.items.stones.StoneOfAugmentation;
import com.eternalpixel.eternalpixeldungeon.items.stones.StoneOfBlast;
import com.eternalpixel.eternalpixeldungeon.items.stones.StoneOfBlink;
import com.eternalpixel.eternalpixeldungeon.items.stones.StoneOfClairvoyance;
import com.eternalpixel.eternalpixeldungeon.items.stones.StoneOfDeepSleep;
import com.eternalpixel.eternalpixeldungeon.items.stones.StoneOfDisarming;
import com.eternalpixel.eternalpixeldungeon.items.stones.StoneOfEnchantment;
import com.eternalpixel.eternalpixeldungeon.items.stones.StoneOfFlock;
import com.eternalpixel.eternalpixeldungeon.items.stones.StoneOfIntuition;
import com.eternalpixel.eternalpixeldungeon.items.stones.StoneOfShock;
import com.eternalpixel.eternalpixeldungeon.items.wands.Wand;
import com.eternalpixel.eternalpixeldungeon.items.wands.WandOfBlastWave;
import com.eternalpixel.eternalpixeldungeon.items.wands.WandOfCorrosion;
import com.eternalpixel.eternalpixeldungeon.items.wands.WandOfCorruption;
import com.eternalpixel.eternalpixeldungeon.items.wands.WandOfDisintegration;
import com.eternalpixel.eternalpixeldungeon.items.wands.WandOfFireblast;
import com.eternalpixel.eternalpixeldungeon.items.wands.WandOfFrost;
import com.eternalpixel.eternalpixeldungeon.items.wands.WandOfLightning;
import com.eternalpixel.eternalpixeldungeon.items.wands.WandOfLivingEarth;
import com.eternalpixel.eternalpixeldungeon.items.wands.WandOfMagicMissile;
import com.eternalpixel.eternalpixeldungeon.items.wands.WandOfPrismaticLight;
import com.eternalpixel.eternalpixeldungeon.items.wands.WandOfRegrowth;
import com.eternalpixel.eternalpixeldungeon.items.wands.WandOfTransfusion;
import com.eternalpixel.eternalpixeldungeon.items.wands.WandOfWarding;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.AssassinsBlade;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.BattleAxe;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Crossbow;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Dagger;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Dirk;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Flail;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Gauntlet;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Glaive;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Gloves;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Greataxe;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Greatshield;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Greatsword;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.HandAxe;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Longsword;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Mace;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.MagesStaff;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Quarterstaff;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.RoundShield;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.RunicBlade;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Sai;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Scimitar;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Shortsword;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Spear;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Sword;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.WarHammer;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Whip;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.WornShortsword;
import com.eternalpixel.eternalpixeldungeon.items.weapon.missiles.Bolas;
import com.eternalpixel.eternalpixeldungeon.items.weapon.missiles.FishingSpear;
import com.eternalpixel.eternalpixeldungeon.items.weapon.missiles.ForceCube;
import com.eternalpixel.eternalpixeldungeon.items.weapon.missiles.HeavyBoomerang;
import com.eternalpixel.eternalpixeldungeon.items.weapon.missiles.Javelin;
import com.eternalpixel.eternalpixeldungeon.items.weapon.missiles.Kunai;
import com.eternalpixel.eternalpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.eternalpixel.eternalpixeldungeon.items.weapon.missiles.Shuriken;
import com.eternalpixel.eternalpixeldungeon.items.weapon.missiles.ThrowingClub;
import com.eternalpixel.eternalpixeldungeon.items.weapon.missiles.ThrowingHammer;
import com.eternalpixel.eternalpixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.eternalpixel.eternalpixeldungeon.items.weapon.missiles.ThrowingSpear;
import com.eternalpixel.eternalpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.eternalpixel.eternalpixeldungeon.items.weapon.missiles.Tomahawk;
import com.eternalpixel.eternalpixeldungeon.items.weapon.missiles.Trident;
import com.eternalpixel.eternalpixeldungeon.plants.Blindweed;
import com.eternalpixel.eternalpixeldungeon.plants.Dreamfoil;
import com.eternalpixel.eternalpixeldungeon.plants.Earthroot;
import com.eternalpixel.eternalpixeldungeon.plants.Fadeleaf;
import com.eternalpixel.eternalpixeldungeon.plants.Firebloom;
import com.eternalpixel.eternalpixeldungeon.plants.Icecap;
import com.eternalpixel.eternalpixeldungeon.plants.Plant;
import com.eternalpixel.eternalpixeldungeon.plants.Rotberry;
import com.eternalpixel.eternalpixeldungeon.plants.Sorrowmoss;
import com.eternalpixel.eternalpixeldungeon.plants.Starflower;
import com.eternalpixel.eternalpixeldungeon.plants.Stormvine;
import com.eternalpixel.eternalpixeldungeon.plants.Sungrass;
import com.eternalpixel.eternalpixeldungeon.plants.Swiftthistle;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Generator {

	public enum Category {
		WEAPON		( 4,    MeleeWeapon.class),
		WEP_T1		( 0,    MeleeWeapon.class),
		WEP_T2		( 0,    MeleeWeapon.class),
		WEP_T3		( 0,    MeleeWeapon.class),
		WEP_T4		( 0,    MeleeWeapon.class),
		WEP_T5		( 0,    MeleeWeapon.class),

		ARMOR		( 3,    Armor.class ),

		MISSILE		( 3,    MissileWeapon.class ),
		MIS_T1 		( 0,    MissileWeapon.class ),
		MIS_T2 		( 0,    MissileWeapon.class ),
		MIS_T3 		( 0,    MissileWeapon.class ),
		MIS_T4 		( 0,    MissileWeapon.class ),
		MIS_T5 		( 0,    MissileWeapon.class ),

		WAND		( 2,    Wand.class ),
		RING		( 1,    Ring.class ),
		ARTIFACT	( 1,    Artifact.class),

		FOOD		( 10,    Food.class ),
		COOKINGTOOLS( 2,CookingTools.class),

		POTION		( 16,   Potion.class ),
		SEED		( 2,    Plant.Seed.class ),

		SCROLL		( 16,   Scroll.class ),
		STONE  		( 2,    Runestone.class),

		GOLD		( 20,   Gold.class ),
		VEGETABLE(5,Vegetable.class);

		public Class<?>[] classes;

		//some item types use a deck-based system, where the probs decrement as items are picked
		// until they are all 0, and then they reset. Those generator classes should define
		// defaultProbs. If defaultProbs is null then a deck system isn't used.
		//Artifacts in particular don't reset, no duplicates!
		public float[] probs;
		public float[] defaultProbs = null;

		public float prob;
		public Class<? extends Item> superClass;

		private Category( float prob, Class<? extends Item> superClass ) {
			this.prob = prob;
			this.superClass = superClass;
		}

		public static int order( Item item ) {
			for (int i=0; i < values().length; i++) {
				if (values()[i].superClass.isInstance( item )) {
					return i;
				}
			}

			//items without a category-defined order are sorted based on the spritesheet
			return Short.MAX_VALUE+item.image();
		}

		static {
			GOLD.classes = new Class<?>[]{
					Gold.class };
			GOLD.probs = new float[]{ 1 };

			POTION.classes = new Class<?>[]{
					PotionOfStrength.class, //2 drop every chapter, see Dungeon.posNeeded()
					PotionOfHealing.class,
					PotionOfMindVision.class,
					PotionOfFrost.class,
					PotionOfLiquidFlame.class,
					PotionOfToxicGas.class,
					PotionOfHaste.class,
					PotionOfInvisibility.class,
					PotionOfLevitation.class,
					PotionOfParalyticGas.class,
					PotionOfPurity.class,
					PotionOfExperience.class};
			POTION.defaultProbs = new float[]{ 0, 6, 4, 3, 3, 3, 2, 2, 2, 2, 2, 1 };
			POTION.probs = POTION.defaultProbs.clone();

			SEED.classes = new Class<?>[]{
					Rotberry.Seed.class, //quest item
					Sungrass.Seed.class,
					Fadeleaf.Seed.class,
					Icecap.Seed.class,
					Firebloom.Seed.class,
					Sorrowmoss.Seed.class,
					Swiftthistle.Seed.class,
					Blindweed.Seed.class,
					Stormvine.Seed.class,
					Earthroot.Seed.class,
					Dreamfoil.Seed.class,
					Starflower.Seed.class};
			SEED.defaultProbs = new float[]{ 0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 2 };
			SEED.probs = SEED.defaultProbs.clone();

			SCROLL.classes = new Class<?>[]{
					ScrollOfUpgrade.class, //3 drop every chapter, see Dungeon.souNeeded()
					ScrollOfIdentify.class,
					ScrollOfRemoveCurse.class,
					ScrollOfMirrorImage.class,
					ScrollOfRecharging.class,
					ScrollOfTeleportation.class,
					ScrollOfLullaby.class,
					ScrollOfMagicMapping.class,
					ScrollOfRage.class,
					ScrollOfRetribution.class,
					ScrollOfTerror.class,
					ScrollOfTransmutation.class
			};
			SCROLL.defaultProbs = new float[]{ 0, 6, 4, 3, 3, 3, 2, 2, 2, 2, 2, 1 };
			SCROLL.probs = SCROLL.defaultProbs.clone();

			STONE.classes = new Class<?>[]{
					StoneOfEnchantment.class,   //1 is guaranteed to drop on floors 6-19
					StoneOfIntuition.class,     //1 additional stone is also dropped on floors 1-3
					StoneOfDisarming.class,
					StoneOfFlock.class,
					StoneOfShock.class,
					StoneOfBlink.class,
					StoneOfDeepSleep.class,
					StoneOfClairvoyance.class,
					StoneOfAggression.class,
					StoneOfBlast.class,
					StoneOfFear.class,
					StoneOfAugmentation.class  //1 is sold in each shop
			};
			STONE.defaultProbs = new float[]{ 0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 0 };
			STONE.probs = STONE.defaultProbs.clone();

			WAND.classes = new Class<?>[]{
					WandOfMagicMissile.class,
					WandOfLightning.class,
					WandOfDisintegration.class,
					WandOfFireblast.class,
					WandOfCorrosion.class,
					WandOfBlastWave.class,
					WandOfLivingEarth.class,
					WandOfFrost.class,
					WandOfPrismaticLight.class,
					WandOfWarding.class,
					WandOfTransfusion.class,
					WandOfCorruption.class,
					WandOfRegrowth.class };
			WAND.probs = new float[]{ 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3 };

			//see generator.randomWeapon
			WEAPON.classes = new Class<?>[]{};
			WEAPON.probs = new float[]{};

			WEP_T1.classes = new Class<?>[]{
					WornShortsword.class,
					Gloves.class,
					Dagger.class,
					MagesStaff.class
			};
			WEP_T1.probs = new float[]{ 1, 1, 1, 0 };

			WEP_T2.classes = new Class<?>[]{
					Shortsword.class,
					HandAxe.class,
					Spear.class,
					Quarterstaff.class,
					Dirk.class
			};
			WEP_T2.probs = new float[]{ 6, 5, 5, 4, 4 };

			WEP_T3.classes = new Class<?>[]{
					Sword.class,
					Mace.class,
					Scimitar.class,
					RoundShield.class,
					Sai.class,
					Whip.class
			};
			WEP_T3.probs = new float[]{ 6, 5, 5, 4, 4, 4 };

			WEP_T4.classes = new Class<?>[]{
					Longsword.class,
					BattleAxe.class,
					Flail.class,
					RunicBlade.class,
					AssassinsBlade.class,
					Crossbow.class
			};
			WEP_T4.probs = new float[]{ 6, 5, 5, 4, 4, 4 };

			WEP_T5.classes = new Class<?>[]{
					Greatsword.class,
					WarHammer.class,
					Glaive.class,
					Greataxe.class,
					Greatshield.class,
					Gauntlet.class
			};
			WEP_T5.probs = new float[]{ 6, 5, 5, 4, 4, 4 };

			//see Generator.randomArmor
			ARMOR.classes = new Class<?>[]{
					ClothArmor.class,
					LeatherArmor.class,
					MailArmor.class,
					ScaleArmor.class,
					PlateArmor.class };
			ARMOR.probs = new float[]{ 0, 0, 0, 0, 0 };

			//see Generator.randomMissile
			MISSILE.classes = new Class<?>[]{};
			MISSILE.probs = new float[]{};

			MIS_T1.classes = new Class<?>[]{
					ThrowingStone.class,
					ThrowingKnife.class
			};
			MIS_T1.probs = new float[]{ 6, 5 };

			MIS_T2.classes = new Class<?>[]{
					FishingSpear.class,
					ThrowingClub.class,
					Shuriken.class
			};
			MIS_T2.probs = new float[]{ 6, 5, 4 };

			MIS_T3.classes = new Class<?>[]{
					ThrowingSpear.class,
					Kunai.class,
					Bolas.class
			};
			MIS_T3.probs = new float[]{ 6, 5, 4 };

			MIS_T4.classes = new Class<?>[]{
					Javelin.class,
					Tomahawk.class,
					HeavyBoomerang.class
			};
			MIS_T4.probs = new float[]{ 6, 5, 4 };

			MIS_T5.classes = new Class<?>[]{
					Trident.class,
					ThrowingHammer.class,
					ForceCube.class,
			};
			MIS_T5.probs = new float[]{ 6, 5, 4 ,5};

			FOOD.classes = new Class<?>[]{
					Food.class,
					Pasty.class,
					MysteryMeat.class,
					FlourFood.class,
					Noodles.class
			};
			FOOD.probs = new float[]{ 1, 1, 0 ,2,2};

			COOKINGTOOLS.classes = new Class<?>[]{
					Campfire.class,
					Pot.class,
					PortableCookingTool.class,
					Sink.class,
					KitchenOven.class,
					Kitchen.class,
					Oven.class,
					FoodProcessor.class,
					FoodMaker.class,
					BarbecueSet.class
			};
			COOKINGTOOLS.probs = new float[]{ 7, 6, 5 , 4, 4, 4, 3, 2, 2, 1};

			RING.classes = new Class<?>[]{
					RingOfAccuracy.class,
					RingOfEvasion.class,
					RingOfElements.class,
					RingOfForce.class,
					RingOfFuror.class,
					RingOfHaste.class,
					RingOfEnergy.class,
					RingOfMight.class,
					RingOfSharpshooting.class,
					RingOfTenacity.class,
					RingOfWealth.class};
			RING.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

			ARTIFACT.classes = new Class<?>[]{
					CapeOfThorns.class,
					ChaliceOfBlood.class,
					CloakOfShadows.class,
					HornOfPlenty.class,
					MasterThievesArmband.class,
					SandalsOfNature.class,
					TalismanOfForesight.class,
					TimekeepersHourglass.class,
					UnstableSpellbook.class,
					AlchemistsToolkit.class,
					DriedRose.class,
					LloydsBeacon.class,
					EtherealChains.class
			};
			ARTIFACT.defaultProbs = new float[]{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			ARTIFACT.probs = ARTIFACT.defaultProbs.clone();

			VEGETABLE.classes = new Class<?>[]{
					EdibleWildPlant.class,
					GreenPea.class,
					HealthyLeaf.class,
					Leccho.class,
					Lettuce.class,
					Melon.class,
					Potato.class,
					Squash.class,
					SweetPotato.class,
					Taro.class,
					Tomato.class
			};
			VEGETABLE.probs = new float[]{1,1,1,1,1,1,1,1,1,1,1};

		}
	}

	private static final float[][] floorSetTierProbs = new float[][] {
			{0, 75, 20,  4,  1},
			{0, 25, 50, 20,  5},
			{0,  0, 40, 50, 10},
			{0,  0, 20, 40, 40},
			{0,  0,  0, 20, 80}
	};

	private static HashMap<Category,Float> categoryProbs = new LinkedHashMap<>();

	public static void fullReset() {
		generalReset();
		for (Category cat : Category.values()) {
			reset(cat);
		}
	}

	public static void generalReset(){
		for (Category cat : Category.values()) {
			categoryProbs.put( cat, cat.prob );
		}
	}

	public static void reset(Category cat){
		if (cat.defaultProbs != null) cat.probs = cat.defaultProbs.clone();
	}

	public static Item random() {
		Category cat = Random.chances( categoryProbs );
		if (cat == null){
			generalReset();
			cat = Random.chances( categoryProbs );
		}
		categoryProbs.put( cat, categoryProbs.get( cat ) - 1);
		return random( cat );
	}

	public static Item random( Category cat ) {
		switch (cat) {
			case ARMOR:
				return randomArmor();
			case WEAPON:
				return randomWeapon();
			case MISSILE:
				return randomMissile();
			case ARTIFACT:
				Item item = randomArtifact();
				//if we're out of artifacts, return a ring instead.
				return item != null ? item : random(Category.RING);
			default:
				int i = Random.chances(cat.probs);
				if (i == -1) {
					reset(cat);
					i = Random.chances(cat.probs);
				}
				if (cat.defaultProbs != null) cat.probs[i]--;
				return ((Item) Reflection.newInstance(cat.classes[i])).random();
		}
	}


	//overrides any deck systems and always uses default probs
	public static Item randomUsingDefaults( Category cat ){
		if (cat.defaultProbs == null) {
			return random(cat); //currently covers weapons/armor/missiles
		} else {
			return ((Item) Reflection.newInstance(cat.classes[Random.chances(cat.defaultProbs)])).random();
		}
	}

	public static Item random( Class<? extends Item> cl ) {
		return Reflection.newInstance(cl).random();
	}

	public static Armor randomArmor(){
		return randomArmor(Dungeon.depth / 5);
	}

	public static Armor randomArmor(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);

		Armor a = (Armor)Reflection.newInstance(Category.ARMOR.classes[Random.chances(floorSetTierProbs[floorSet])]);
		a.random();
		return a;
	}

	public static final Category[] wepTiers = new Category[]{
			Category.WEP_T1,
			Category.WEP_T2,
			Category.WEP_T3,
			Category.WEP_T4,
			Category.WEP_T5
	};

	public static MeleeWeapon randomWeapon(){
		return randomWeapon(Dungeon.depth / 5);
	}

	public static MeleeWeapon randomWeapon(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);

		Category c = wepTiers[Random.chances(floorSetTierProbs[floorSet])];
		MeleeWeapon w = (MeleeWeapon)Reflection.newInstance(c.classes[Random.chances(c.probs)]);
		w.random();
		return w;
	}

	public static final Category[] misTiers = new Category[]{
			Category.MIS_T1,
			Category.MIS_T2,
			Category.MIS_T3,
			Category.MIS_T4,
			Category.MIS_T5
	};

	public static MissileWeapon randomMissile(){
		return randomMissile(Dungeon.depth / 5);
	}

	public static MissileWeapon randomMissile(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);

		Category c = misTiers[Random.chances(floorSetTierProbs[floorSet])];
		MissileWeapon w = (MissileWeapon)Reflection.newInstance(c.classes[Random.chances(c.probs)]);
		w.random();
		return w;
	}

	//enforces uniqueness of artifacts throughout a run.
	public static Artifact randomArtifact() {

		Category cat = Category.ARTIFACT;
		int i = Random.chances( cat.probs );

		//if no artifacts are left, return null
		if (i == -1){
			return null;
		}

		cat.probs[i]--;
		return (Artifact) Reflection.newInstance((Class<? extends Artifact>) cat.classes[i]).random();

	}

	public static boolean removeArtifact(Class<?extends Artifact> artifact) {
		Category cat = Category.ARTIFACT;
		for (int i = 0; i < cat.classes.length; i++){
			if (cat.classes[i].equals(artifact) && cat.probs[i] > 0) {
				cat.probs[i] = 0;
				return true;
			}
		}
		return false;
	}

	private static final String GENERAL_PROBS = "general_probs";
	private static final String CATEGORY_PROBS = "_probs";

	public static void storeInBundle(Bundle bundle) {
		Float[] genProbs = categoryProbs.values().toArray(new Float[0]);
		float[] storeProbs = new float[genProbs.length];
		for (int i = 0; i < storeProbs.length; i++){
			storeProbs[i] = genProbs[i];
		}
		bundle.put( GENERAL_PROBS, storeProbs);

		for (Category cat : Category.values()){
			if (cat.defaultProbs == null) continue;
			boolean needsStore = false;
			for (int i = 0; i < cat.probs.length; i++){
				if (cat.probs[i] != cat.defaultProbs[i]){
					needsStore = true;
					break;
				}
			}

			if (needsStore){
				bundle.put(cat.name().toLowerCase() + CATEGORY_PROBS, cat.probs);
			}
		}
	}

	public static void restoreFromBundle(Bundle bundle) {
		fullReset();

		if (bundle.contains(GENERAL_PROBS)){
			float[] probs = bundle.getFloatArray(GENERAL_PROBS);
			for (int i = 0; i < probs.length; i++){
				categoryProbs.put(Category.values()[i], probs[i]);
			}
		}

		for (Category cat : Category.values()){
			if (bundle.contains(cat.name().toLowerCase() + CATEGORY_PROBS)){
				float[] probs = bundle.getFloatArray(cat.name().toLowerCase() + CATEGORY_PROBS);
				if (cat.defaultProbs != null && probs.length == cat.defaultProbs.length){
					cat.probs = probs;
				}
			}
		}

		//pre-0.8.1
		if (bundle.contains("spawned_artifacts")) {
			for (Class<? extends Artifact> artifact : bundle.getClassArray("spawned_artifacts")) {
				Category cat = Category.ARTIFACT;
				for (int i = 0; i < cat.classes.length; i++) {
					if (cat.classes[i].equals(artifact)) {
						cat.probs[i] = 0;
					}
				}
			}
		}

	}
}
