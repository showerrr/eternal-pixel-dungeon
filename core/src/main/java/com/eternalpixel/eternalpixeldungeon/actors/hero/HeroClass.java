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

package com.eternalpixel.eternalpixeldungeon.actors.hero;

import com.eternalpixel.eternalpixeldungeon.Assets;
import com.eternalpixel.eternalpixeldungeon.Badges;
import com.eternalpixel.eternalpixeldungeon.Challenges;
import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.QuickSlot;
import com.eternalpixel.eternalpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.eternalpixel.eternalpixeldungeon.actors.hero.abilities.huntress.NaturesPower;
import com.eternalpixel.eternalpixeldungeon.actors.hero.abilities.huntress.SpiritHawk;
import com.eternalpixel.eternalpixeldungeon.actors.hero.abilities.huntress.SpectralBlades;
import com.eternalpixel.eternalpixeldungeon.actors.hero.abilities.mage.WildMagic;
import com.eternalpixel.eternalpixeldungeon.actors.hero.abilities.mage.WarpBeacon;
import com.eternalpixel.eternalpixeldungeon.actors.hero.abilities.mage.ElementalBlast;
import com.eternalpixel.eternalpixeldungeon.actors.hero.abilities.rogue.DeathMark;
import com.eternalpixel.eternalpixeldungeon.actors.hero.abilities.rogue.ShadowClone;
import com.eternalpixel.eternalpixeldungeon.actors.hero.abilities.rogue.SmokeBomb;
import com.eternalpixel.eternalpixeldungeon.actors.hero.abilities.warrior.HeroicLeap;
import com.eternalpixel.eternalpixeldungeon.actors.hero.abilities.warrior.Shockwave;
import com.eternalpixel.eternalpixeldungeon.actors.hero.abilities.warrior.Endure;
import com.eternalpixel.eternalpixeldungeon.items.BrokenSeal;
import com.eternalpixel.eternalpixeldungeon.items.Item;
import com.eternalpixel.eternalpixeldungeon.items.Waterskin;
import com.eternalpixel.eternalpixeldungeon.items.armor.ClothArmor;
import com.eternalpixel.eternalpixeldungeon.items.artifacts.CloakOfShadows;
import com.eternalpixel.eternalpixeldungeon.items.artifacts.RiggedTarot;
import com.eternalpixel.eternalpixeldungeon.items.bags.CoolerBox;
import com.eternalpixel.eternalpixeldungeon.items.bags.MagicalHolster;
import com.eternalpixel.eternalpixeldungeon.items.bags.PotionBandolier;
import com.eternalpixel.eternalpixeldungeon.items.bags.ScrollHolder;
import com.eternalpixel.eternalpixeldungeon.items.bags.VelvetPouch;
import com.eternalpixel.eternalpixeldungeon.items.cookingtools.FoodProcessor;
import com.eternalpixel.eternalpixeldungeon.items.food.Food;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfHealing;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfInvisibility;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfMindVision;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfDebugging;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfRage;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.eternalpixel.eternalpixeldungeon.items.wands.WandOfMagicMissile;
import com.eternalpixel.eternalpixeldungeon.items.weapon.SpiritBow;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Dagger;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.DivinationBall;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.Gloves;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.MagesStaff;
import com.eternalpixel.eternalpixeldungeon.items.weapon.melee.WornShortsword;
import com.eternalpixel.eternalpixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.eternalpixel.eternalpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.ui.HeroIcon;
import com.watabou.utils.DeviceCompat;

public enum HeroClass {

	WARRIOR( HeroSubClass.BERSERKER, HeroSubClass.GLADIATOR ),
	MAGE( HeroSubClass.BATTLEMAGE, HeroSubClass.WARLOCK ),
	ROGUE( HeroSubClass.ASSASSIN, HeroSubClass.FREERUNNER ),
	HUNTRESS( HeroSubClass.SNIPER, HeroSubClass.WARDEN ),
	AUGUR(HeroSubClass.AUGUR1,HeroSubClass.AUGUR2);

	private HeroSubClass[] subClasses;

	HeroClass( HeroSubClass...subClasses ) {
		this.subClasses = subClasses;
	}

	public void initHero( Hero hero ) {

		hero.heroClass = this;
		Talent.initClassTalents(hero);

		Item i = new ClothArmor().identify();
		if (!Challenges.isItemBlocked(i)) hero.belongings.armor = (ClothArmor)i;

		i = new Food();
		if (!Challenges.isItemBlocked(i)) i.collect();

		new VelvetPouch().collect();
		Dungeon.LimitedDrops.VELVET_POUCH.drop();
		new CoolerBox().collect();
		Dungeon.LimitedDrops.COOLER_BOX.drop();
		new MagicalHolster().collect();
		Dungeon.LimitedDrops.MAGICAL_HOLSTER.drop();
		new PotionBandolier().collect();
		Dungeon.LimitedDrops.POTION_BANDOLIER.drop();
		new ScrollHolder().collect();
		Dungeon.LimitedDrops.SCROLL_HOLDER.drop();

		Waterskin waterskin = new Waterskin();
		waterskin.collect();

		FoodProcessor foodProcessor = new FoodProcessor();
		foodProcessor.identify().collect();

		new ScrollOfIdentify().identify();

		switch (this) {
			case WARRIOR:
				initWarrior( hero );
				break;

			case MAGE:
				initMage( hero );
				break;

			case ROGUE:
				initRogue( hero );
				break;

			case HUNTRESS:
				initHuntress( hero );
				break;

			case AUGUR:
				initAugur(hero);
				break;
		}

		for (int s = 0; s < QuickSlot.SIZE; s++){
			if (Dungeon.quickslot.getItem(s) == null){
				Dungeon.quickslot.setSlot(s, waterskin);
				break;
			}
		}

	}

	public Badges.Badge masteryBadge() {
		switch (this) {
			case WARRIOR:
				return Badges.Badge.MASTERY_WARRIOR;
			case MAGE:
				return Badges.Badge.MASTERY_MAGE;
			case ROGUE:
				return Badges.Badge.MASTERY_ROGUE;
			case HUNTRESS:
				return Badges.Badge.MASTERY_HUNTRESS;
		}
		return null;
	}

	private static void initWarrior( Hero hero ) {
		(hero.belongings.weapon = new WornShortsword()).identify();
		ThrowingStone stones = new ThrowingStone();
		stones.quantity(3).collect();
		Dungeon.quickslot.setSlot(0, stones);

		if (hero.belongings.armor != null){
			hero.belongings.armor.affixSeal(new BrokenSeal());
		}

		new PotionOfHealing().identify();
		new ScrollOfRage().identify();
	}

	private static void initMage( Hero hero ) {
		MagesStaff staff;

		staff = new MagesStaff(new WandOfMagicMissile());

		(hero.belongings.weapon = staff).identify();
		hero.belongings.weapon.activate(hero);

		Dungeon.quickslot.setSlot(0, staff);

		new ScrollOfUpgrade().identify();
		new PotionOfLiquidFlame().identify();
	}

	private static void initRogue( Hero hero ) {
		(hero.belongings.weapon = new Dagger()).identify();

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.artifact = cloak).identify();
		hero.belongings.artifact.activate( hero );

		ThrowingKnife knives = new ThrowingKnife();
		knives.quantity(3).collect();

		Dungeon.quickslot.setSlot(0, cloak);
		Dungeon.quickslot.setSlot(1, knives);

		new ScrollOfMagicMapping().identify();
		new PotionOfInvisibility().identify();
	}

	private static void initHuntress( Hero hero ) {

		(hero.belongings.weapon = new Gloves()).identify();
		SpiritBow bow = new SpiritBow();
		bow.identify().collect();

		Dungeon.quickslot.setSlot(0, bow);

		new PotionOfMindVision().identify();
		new ScrollOfLullaby().identify();
	}

	private static void initAugur(Hero hero) {

		(hero.belongings.weapon = new DivinationBall()).identify();

		RiggedTarot riggedTarot = new RiggedTarot();
		(hero.belongings.artifact = riggedTarot).identify();
		Dungeon.quickslot.setSlot(3,riggedTarot);
		hero.belongings.artifact.activate(hero);

		ScrollOfDebugging scrollOfDebugging = new ScrollOfDebugging();
		scrollOfDebugging.collect();
	}

	public String title() {
		return Messages.get(HeroClass.class, name());
	}

	public String desc(){
		return Messages.get(HeroClass.class, name()+"_desc");
	}

	public HeroSubClass[] subClasses() {
		return subClasses;
	}

	public ArmorAbility[] armorAbilities(){
		switch (this) {
			case WARRIOR: default:
				return new ArmorAbility[]{new HeroicLeap(), new Shockwave(), new Endure()};
			case MAGE:
				return new ArmorAbility[]{new ElementalBlast(), new WildMagic(), new WarpBeacon()};
			case ROGUE:
				return new ArmorAbility[]{new SmokeBomb(), new DeathMark(), new ShadowClone()};
			case HUNTRESS:
				return new ArmorAbility[]{new SpectralBlades(), new NaturesPower(), new SpiritHawk()};
		}
	}

	public String spritesheet() {
		switch (this) {
			case WARRIOR: default:
				return Assets.Sprites.WARRIOR;
			case MAGE:
				return Assets.Sprites.MAGE;
			case ROGUE:
				return Assets.Sprites.ROGUE;
			case HUNTRESS:
				return Assets.Sprites.HUNTRESS;
			case AUGUR:
				return Assets.Sprites.AUGUR;
		}
	}

	public String splashArt(){
		switch (this) {
			case WARRIOR: default:
				return Assets.Splashes.WARRIOR;
			case MAGE:
				return Assets.Splashes.MAGE;
			case ROGUE:
				return Assets.Splashes.ROGUE;
			case HUNTRESS:
				return Assets.Splashes.HUNTRESS;
		}
	}
	
	public String[] perks() {
		switch (this) {
			case WARRIOR: default:
				return new String[]{
						Messages.get(HeroClass.class, "warrior_perk1"),
						Messages.get(HeroClass.class, "warrior_perk2"),
						Messages.get(HeroClass.class, "warrior_perk3"),
						Messages.get(HeroClass.class, "warrior_perk4"),
						Messages.get(HeroClass.class, "warrior_perk5"),
				};
			case MAGE:
				return new String[]{
						Messages.get(HeroClass.class, "mage_perk1"),
						Messages.get(HeroClass.class, "mage_perk2"),
						Messages.get(HeroClass.class, "mage_perk3"),
						Messages.get(HeroClass.class, "mage_perk4"),
						Messages.get(HeroClass.class, "mage_perk5"),
				};
			case ROGUE:
				return new String[]{
						Messages.get(HeroClass.class, "rogue_perk1"),
						Messages.get(HeroClass.class, "rogue_perk2"),
						Messages.get(HeroClass.class, "rogue_perk3"),
						Messages.get(HeroClass.class, "rogue_perk4"),
						Messages.get(HeroClass.class, "rogue_perk5"),
				};
			case HUNTRESS:
				return new String[]{
						Messages.get(HeroClass.class, "huntress_perk1"),
						Messages.get(HeroClass.class, "huntress_perk2"),
						Messages.get(HeroClass.class, "huntress_perk3"),
						Messages.get(HeroClass.class, "huntress_perk4"),
						Messages.get(HeroClass.class, "huntress_perk5"),
				};
		}
	}
	
	public boolean isUnlocked(){
		//always unlock on debug builds
		if (DeviceCompat.isDebug()) return true;
		
		switch (this){
			case WARRIOR: default:
				return true;
			case MAGE:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_MAGE);
			case ROGUE:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_ROGUE);
			case HUNTRESS:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_HUNTRESS);
		}
	}
	
	public String unlockMsg() {
		switch (this){
			case WARRIOR: default:
				return "";
			case MAGE:
				return Messages.get(HeroClass.class, "mage_unlock");
			case ROGUE:
				return Messages.get(HeroClass.class, "rogue_unlock");
			case HUNTRESS:
				return Messages.get(HeroClass.class, "huntress_unlock");
		}
	}

}
