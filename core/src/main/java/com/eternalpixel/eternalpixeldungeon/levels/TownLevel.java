/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2018 Evan Debenham
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

package com.eternalpixel.eternalpixeldungeon.levels;

import com.eternalpixel.eternalpixeldungeon.Assets;
import com.eternalpixel.eternalpixeldungeon.actors.Actor;
import com.eternalpixel.eternalpixeldungeon.actors.mobs.Mob;
//import com.eternalpixel.eternalpixeldungeon.actors.mobs.npcs.TownGamer;
//import com.eternalpixel.eternalpixeldungeon.actors.mobs.npcs.TownShopkeeper;
import com.eternalpixel.eternalpixeldungeon.actors.mobs.npcs.TownGamer;
import com.eternalpixel.eternalpixeldungeon.actors.mobs.npcs.TownShopkeeper;
import com.eternalpixel.eternalpixeldungeon.items.Generator;
import com.eternalpixel.eternalpixeldungeon.items.Heap;
import com.eternalpixel.eternalpixeldungeon.items.Item;
import com.eternalpixel.eternalpixeldungeon.items.drink.Milk;
import com.eternalpixel.eternalpixeldungeon.items.food.Baguette;
import com.eternalpixel.eternalpixeldungeon.items.food.FlourFood;
import com.eternalpixel.eternalpixeldungeon.items.food.Noodles;
import com.eternalpixel.eternalpixeldungeon.items.furniture.BigBookshelf;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.watabou.noosa.Group;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TownLevel extends Level {

	{
		viewDistance = 15;

		color1 = 0x6a723d;
		color2 = 0x88924c;

	}

	public static ArrayList<Integer> investment = new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1, 1, 1));

	public static ArrayList<Item> shop1;
	public static ArrayList<Item> shop2;
	public ArrayList<Item> shop3;
	public ArrayList<Item> shop4;
	public ArrayList<Item> shop5;
	public ArrayList<Item> shop6;
	public ArrayList<Item> shop7;

	@Override
	public String tilesTex() {
		return Assets.Environment.TILES_TOWN;
	}

	@Override
	public String waterTex() {
		return Assets.Environment.WATER_TOWN;
	}

	@Override
	protected boolean build() {

		setSize(32, 32);

		map = MAP_START.clone();

		buildFlagMaps();
		cleanWalls();

		entrance = 15 + 2 * 32;
		exit = 15 + 2 * 32;

		placeItem();
		placeNPC();

		return true;
	}

	private void placeNPC() {

		Mob townShopkeeper1 = new TownShopkeeper();
		townShopkeeper1.pos = 8 + 18 * 32;
		mobs.add(townShopkeeper1);

		Mob townShopkeeper2 = new TownShopkeeper();
		townShopkeeper2.pos = 20 + 4 * 32;
		mobs.add(townShopkeeper2);

		Mob townGamer = new TownGamer();
		townGamer.pos = 6 + 23 * 32;
		mobs.add(townGamer);
	}

	private void placeItem() {

		Milk milk = new Milk();
		drop(milk,16 + 3 * 32).type = Heap.Type.INTERACTIVE;
//
		for (int i = 15;i < 20;i++) {
			drop(new BigBookshelf(),1 + i * 32).type = Heap.Type.INTERACTIVE;
			drop(new BigBookshelf(),4 + i * 32).type = Heap.Type.INTERACTIVE;
			drop(new Baguette(),10 + i * 32).type = Heap.Type.INTERACTIVE;
		}
	}

	@Override
	public Mob createMob() {
		return null;
	}

	@Override
	protected void createMobs() {
	}

	public Actor respawner() {
		return null;
	}

	@Override
	protected void createItems() {
//		Item item = Bones.get();
//		if (item != null) {
//			drop(item, exit - 1).type = Heap.Type.REMAINS;
//		}
	}

	@Override
	public String tileName(int tile) {
		if (tile == Terrain.WATER) {
			return Messages.get(SewerLevel.class, "water_name");
		}
		return super.tileName(tile);
	}

	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.EMPTY_DECO:
				return Messages.get(SewerLevel.class, "empty_deco_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(SewerLevel.class, "bookshelf_desc");
			default:
				return super.tileDesc(tile);
		}
	}

	@Override
	public Group addVisuals() {
		super.addVisuals();
		SewerLevel.addSewerVisuals(this, visuals);
		return visuals;
	}

	private static final int W = Terrain.WALL;
	private static final int D = Terrain.DOOR;
	private static final int L = Terrain.LOCKED_DOOR;
	private static final int e = Terrain.EMPTY;
	private static final int w = Terrain.WATER;
	private static final int m = Terrain.EMPTY_SP;
	private static final int g = Terrain.GRASS;


	private static final int S = Terrain.STATUE;

	private static final int E = Terrain.ENTRANCE;
	private static final int x = Terrain.EXIT;
	private static final int X = Terrain.TOWNEXIT;

	private static final int M = Terrain.WALL_DECO;
	private static final int P = Terrain.PEDESTAL;

	private static final int s = Terrain.SIGN;

	//TODO if I ever need to store more static maps I should externalize them instead of hard-coding
	//Especially as I means I won't be limited to legal identifiers
	private static final int[] MAP_START =
				   {W, W, W, W, W, W, W, W, W, W, W, W, X, X, X, X, X, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
					W, e, e, e, P, P, P, P, e, e, e, W, X, X, X, X, X, W, m, m, m, m, m, m, W, m, m, m, m, m, m, W,
					W, e, e, e, P, P, P, P, e, e, e, W, e, e, e, e, e, W, m, m, m, m, m, m, W, m, m, m, m, m, m, W,
					W, e, e, e, P, P, P, P, e, e, e, W, e, e, e, e, e, W, m, m, m, m, m, m, W, m, m, m, m, m, m, W,
					W, e, e, e, P, P, P, P, e, e, e, W, e, e, e, e, e, W, m, m, m, m, m, m, W, m, m, m, m, m, m, W,
					W, e, e, e, P, P, P, P, e, e, e, W, e, e, e, e, e, W, m, m, m, m, m, m, W, m, m, m, m, m, m, W,
					W, e, e, e, P, P, P, P, e, e, e, W, e, e, e, e, e, W, m, m, m, m, m, m, W, m, m, m, m, m, m, W,
					W, e, e, e, P, P, P, P, e, e, e, W, e, e, e, e, e, W, m, m, m, m, m, m, W, m, m, m, m, m, m, W,
					W, e, e, e, P, P, P, P, e, e, e, W, e, e, e, e, e, W, W, W, m, m, W, W, W, W, W, m, m, W, W, W,
					W, e, e, e, P, P, P, P, e, e, e, W, s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, W,
					W, e, e, e, P, P, P, P, e, e, e, W, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, W,
					W, W, W, W, P, P, P, P, W, W, W, W, e, e, e, e, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
					W, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, W, m, m, m, m, W, m, m, m, m, W, m, m, m, m, W,
					W, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, W, m, m, m, m, W, m, m, m, m, W, m, m, m, m, W,
					W, W, m, m, W, W, W, m, m, m, W, W, e, e, e, e, W, m, m, m, m, W, m, m, m, m, W, m, m, m, m, W,
					W, m, m, m, m, W, m, m, m, m, m, W, e, e, e, e, W, m, m, m, m, W, m, m, m, m, W, m, m, m, m, W,
					W, m, m, m, m, W, m, m, m, m, m, W, e, e, e, e, W, m, m, m, m, W, m, m, m, m, W, m, m, m, m, W,
					W, m, m, m, m, W, m, m, m, m, m, W, e, e, e, e, W, m, m, m, m, W, m, m, m, m, W, m, m, m, m, W,
					W, m, m, m, m, W, m, m, m, m, m, W, e, e, e, e, W, W, m, m, W, W, W, m, m, W, W, W, m, m, W, W,
					W, m, m, m, m, W, m, m, m, m, m, W, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, W,
					W, W, W, W, W, W, W, W, W, W, W, W, e, w, w, w, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, W,
					W, e, e, e, e, e, e, e, e, e, e, e, e, w, w, w, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, W,
					W, e, e, e, e, e, e, e, e, e, e, e, e, w, w, w, e, e, e, e, e, e, s, e, e, e, e, e, e, e, e, W,
					W, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, W, W, e, e, e, W, W, e, W, W, W, W, W, W, W,
					W, W, W, W, W, W, W, W, W, W, W, W, W, e, e, e, e, W, e, e, e, e, e, W, e, W, e, e, e, e, e, W,
					W, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, W, e, e, e, e, e, W, e, W, e, e, e, e, e, W,
					W, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, W,
					W, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, W,
					W, e, e, e, e, e, e, e, e, e, e, e, W, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, W,
					W, e, e, e, e, e, e, e, e, e, e, e, W, e, e, e, e, W, e, e, e, e, e, W, e, W, e, e, e, e, e, W,
					W, e, e, e, e, e, e, e, e, e, e, e, W, X, X, X, X, W, e, e, e, e, e, W, e, W, e, e, e, e, e, W,
					W, W, W, W, W, W, W, W, W, W, W, W, W, X, X, X, X, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W
			};

	protected static ArrayList<Item> generateItems(int i, int investment) {

		ArrayList<Item> itemsToSpawn = new ArrayList<>();
		int quantity = 10 + (int) Math.sqrt((double) investment / 5);

		switch (i) {
			case 1:
				itemsToSpawn.add(new FlourFood().Random(0,1).identify());
				itemsToSpawn.add(new FlourFood().Random(0,1).identify());
				itemsToSpawn.add(new Noodles().Random(0,1).identify());
				itemsToSpawn.add(new Noodles().Random(0,1).identify());
				for (int j = 0; j < (quantity - 4); j++) {
					if (j < (quantity - 4) / 2 ) {
						itemsToSpawn.add(new FlourFood().Random(1,10).identify());
					} else {
						itemsToSpawn.add(new Noodles().Random(1,10).identify());
					}
				}
				break;

			case 2:
				for (int j = 0; j < quantity; j++) {
					if (j < quantity * 2 / 5) {
						itemsToSpawn.add(Generator.random(Generator.Category.SCROLL));
					} else if (j < quantity * 4 / 5) {
						itemsToSpawn.add(Generator.random(Generator.Category.POTION));
					} else {
						itemsToSpawn.add(Generator.random(Generator.Category.WAND));
					}
				}
				break;

		}

		return itemsToSpawn;
	}

	private static final String SHOP1 = "shop1";
	private static final String SHOP2 = "shop2";

	public static void StoreInBundle(Bundle bundle){
		bundle.put(SHOP1,shop1);
		bundle.put(SHOP2,shop2);
	}

	public static void RestoreFromBundle(Bundle bundle){
		shop1 = new ArrayList<Item>();
		shop2 = new ArrayList<Item>();
		Item item;
		Collection<Bundlable> collection;
		collection = bundle.getCollection( SHOP1 );
		for (Bundlable s1 : collection) {
			item = (Item) s1;
			if (item != null) shop1.add(item);
		}
		collection = bundle.getCollection( SHOP2 );
		for (Bundlable s2 : collection) {
			item = (Item) s2;
			if (item != null) shop2.add(item);
		}
	}

	public static void Reset(){
		shop1 = TownLevel.generateItems(1, investment.get(1));
		shop2 = TownLevel.generateItems(2, investment.get(2));
	}
}

