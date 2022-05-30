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

package com.eternalpixel.eternalpixeldungeon.scenes;

import com.eternalpixel.eternalpixeldungeon.Dungeon;
import com.eternalpixel.eternalpixeldungeon.EPDAction;
import com.eternalpixel.eternalpixeldungeon.EPDSettings;
import com.eternalpixel.eternalpixeldungeon.actors.Actor;
import com.eternalpixel.eternalpixeldungeon.actors.Char;
import com.eternalpixel.eternalpixeldungeon.actors.mobs.Mob;
import com.eternalpixel.eternalpixeldungeon.items.Heap;
import com.eternalpixel.eternalpixeldungeon.sprites.CharSprite;
import com.eternalpixel.eternalpixeldungeon.tiles.DungeonTilemap;
import com.watabou.input.GameAction;
import com.watabou.input.KeyBindings;
import com.watabou.input.KeyEvent;
import com.watabou.input.PointerEvent;
import com.watabou.input.ScrollEvent;
import com.watabou.noosa.Camera;
import com.watabou.noosa.ScrollArea;
import com.watabou.utils.GameMath;
import com.watabou.utils.PointF;
import com.watabou.utils.Signal;

public class CellSelector extends ScrollArea {

	public Listener listener = null;
	
	public boolean enabled;
	
	private float dragThreshold;
	
	public CellSelector( DungeonTilemap map ) {
		super( map );
		camera = map.camera();
		
		dragThreshold = PixelScene.defaultZoom * DungeonTilemap.SIZE / 2;
		
		mouseZoom = camera.zoom;
		KeyEvent.addKeyListener( keyListener );
	}
	
	private float mouseZoom;
	
	@Override
	protected void onScroll( ScrollEvent event ) {
		float diff = event.amount/10f;
		
		//scale zoom difference so zooming is consistent
		diff /= ((camera.zoom+1)/camera.zoom)-1;
		diff = Math.min(1, diff);
		mouseZoom = GameMath.gate( PixelScene.minZoom, mouseZoom - diff, PixelScene.maxZoom );
		
		zoom( Math.round(mouseZoom) );
	}
	
	@Override
	protected void onClick( PointerEvent event ) {
		if (dragging) {
			
			dragging = false;
			
		} else {
			
			PointF p = Camera.main.screenToCamera( (int) event.current.x, (int) event.current.y );

			//Prioritizes a sprite if it and a tile overlap, so long as that sprite isn't more than 4 pixels into another tile.
			//The extra check prevents large sprites from blocking the player from clicking adjacent tiles

			//hero first
			if (Dungeon.hero.sprite != null && Dungeon.hero.sprite.overlapsPoint( p.x, p.y )){
				PointF c = DungeonTilemap.tileCenterToWorld(Dungeon.hero.pos);
				if (Math.abs(p.x - c.x) <= 12 && Math.abs(p.y - c.y) <= 12) {
					select(Dungeon.hero.pos);
					return;
				}
			}

			//then mobs
			for (Char mob : Dungeon.level.mobs.toArray(new Mob[0])){
				if (mob.sprite != null && mob.sprite.overlapsPoint( p.x, p.y )){
					PointF c = DungeonTilemap.tileCenterToWorld(mob.pos);
					if (Math.abs(p.x - c.x) <= 12 && Math.abs(p.y - c.y) <= 12) {
						select(mob.pos);
						return;
					}
				}
			}

			//then heaps
			for (Heap heap : Dungeon.level.heaps.valueList()){
				if (heap.sprite != null && heap.sprite.overlapsPoint( p.x, p.y)){
					PointF c = DungeonTilemap.tileCenterToWorld(heap.pos);
					if (Math.abs(p.x - c.x) <= 12 && Math.abs(p.y - c.y) <= 12) {
						select(heap.pos);
						return;
					}
				}
			}
			
			select( ((DungeonTilemap)target).screenToTile(
				(int) event.current.x,
				(int) event.current.y,
					true ) );
		}
	}

	private float zoom( float value ) {

		value = GameMath.gate( PixelScene.minZoom, value, PixelScene.maxZoom );
		EPDSettings.zoom((int) (value - PixelScene.defaultZoom));
		camera.zoom( value );

		//Resets character sprite positions with the new camera zoom
		//This is important as characters are centered on a 16x16 tile, but may have any sprite size
		//This can lead to none-whole coordinate, which need to be aligned with the zoom
		for (Char c : Actor.chars()){
			if (c.sprite != null && !c.sprite.isMoving){
				c.sprite.point(c.sprite.worldToCamera(c.pos));
			}
		}

		return value;
	}
	
	public void select( int cell ) {
		if (enabled && Dungeon.hero.ready && !GameScene.isShowingWindow()
				&& listener != null && cell != -1) {
			
			listener.onSelect( cell );
			GameScene.ready();
			
		} else {
			
			GameScene.cancel();
			
		}
	}
	
	private boolean pinching = false;
	private PointerEvent another;
	private float startZoom;
	private float startSpan;
	
	@Override
	protected void onPointerDown( PointerEvent event ) {

		if (event != curEvent && another == null) {
					
			if (!curEvent.down) {
				curEvent = event;
				onPointerDown( event );
				return;
			}
			
			pinching = true;
			
			another = event;
			startSpan = PointF.distance( curEvent.current, another.current );
			startZoom = camera.zoom;

			dragging = false;
		} else if (event != curEvent) {
			reset();
		}
	}
	
	@Override
	protected void onPointerUp( PointerEvent event ) {
		if (pinching && (event == curEvent || event == another)) {
			
			pinching = false;
			
			zoom(Math.round( camera.zoom ));
			
			dragging = true;
			if (event == curEvent) {
				curEvent = another;
			}
			another = null;
			lastPos.set( curEvent.current );
		}
	}
	
	private boolean dragging = false;
	private PointF lastPos = new PointF();
	
	@Override
	protected void onDrag( PointerEvent event ) {

		if (pinching) {

			float curSpan = PointF.distance( curEvent.current, another.current );
			float zoom = (startZoom * curSpan / startSpan);
			camera.zoom( GameMath.gate(
				PixelScene.minZoom,
					zoom - (zoom % 0.1f),
				PixelScene.maxZoom ) );

		} else {
		
			if (!dragging && PointF.distance( event.current, event.start ) > dragThreshold) {
				
				dragging = true;
				lastPos.set( event.current );
				
			} else if (dragging) {
				camera.shift( PointF.diff( lastPos, event.current ).invScale( camera.zoom ) );
				lastPos.set( event.current );
			}
		}
		
	}
	
	private GameAction heldAction = EPDAction.NONE;
	private int heldTurns = 0;
	
	private Signal.Listener<KeyEvent> keyListener = new Signal.Listener<KeyEvent>() {
		@Override
		public boolean onSignal(KeyEvent event) {
			GameAction action = KeyBindings.getActionForKey( event );
			if (!event.pressed){
				
				if (heldAction != EPDAction.NONE && heldAction == action) {
					resetKeyHold();
					return true;
				} else {
					if (action == EPDAction.ZOOM_IN){
						zoom( camera.zoom+1 );
						mouseZoom = camera.zoom;
						return true;

					} else if (action == EPDAction.ZOOM_OUT){
						zoom( camera.zoom-1 );
						mouseZoom = camera.zoom;
						return true;
					}
				}
			} else if (moveFromAction(action)) {
				heldAction = action;
				return true;
			}
			
			return false;
		}
	};
	
	private boolean moveFromAction(GameAction action){
		if (Dungeon.hero == null){
			return false;
		}

		int cell = Dungeon.hero.pos;

		if (action == EPDAction.N)  cell += -Dungeon.level.width();
		if (action == EPDAction.NE) cell += +1-Dungeon.level.width();
		if (action == EPDAction.E)  cell += +1;
		if (action == EPDAction.SE) cell += +1+Dungeon.level.width();
		if (action == EPDAction.S)  cell += +Dungeon.level.width();
		if (action == EPDAction.SW) cell += -1+Dungeon.level.width();
		if (action == EPDAction.W)  cell += -1;
		if (action == EPDAction.NW) cell += -1-Dungeon.level.width();
		
		if (cell != Dungeon.hero.pos){
			//each step when keyboard moving takes 0.15s, 0.125s, 0.1s, 0.1s, ...
			// this is to make it easier to move 1 or 2 steps without overshooting
			CharSprite.setMoveInterval( CharSprite.DEFAULT_MOVE_INTERVAL +
			                            Math.max(0, 0.05f - heldTurns *0.025f));
			select(cell);
			return true;

		} else {
			return false;
		}

	}
	
	public void processKeyHold(){
		if (heldAction != EPDAction.NONE){
			enabled = Dungeon.hero.ready = true;
			Dungeon.observe();
			heldTurns++;
			moveFromAction(heldAction);
		}
	}
	
	public void resetKeyHold(){
		heldAction = EPDAction.NONE;
		heldTurns = 0;
		CharSprite.setMoveInterval( CharSprite.DEFAULT_MOVE_INTERVAL );
	}
	
	public void cancel() {
		
		if (listener != null) {
			listener.onSelect( null );
		}
		
		GameScene.ready();
	}

	@Override
	public void reset() {
		super.reset();
		another = null;
		if (pinching){
			pinching = false;

			zoom( Math.round( camera.zoom ) );
		}
	}

	public void enable(boolean value){
		if (enabled != value){
			enabled = value;
		}
	}
	
	@Override
	public void destroy() {
		super.destroy();
		KeyEvent.removeKeyListener( keyListener );
	}
	
	public interface Listener {
		void onSelect( Integer cell );
		String prompt();
	}
}