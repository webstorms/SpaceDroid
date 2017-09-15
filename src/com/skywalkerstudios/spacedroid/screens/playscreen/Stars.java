package com.skywalkerstudios.spacedroid.screens.playscreen;

import android.graphics.Rect;

import com.skywalkerstudios.spacedroid.Assets;
import com.webstorms.framework.Game;
import com.webstorms.framework.WSObject;
import com.webstorms.framework.util.Util;
import com.webstorms.framework.gameObject.GameObject;
import com.webstorms.framework.gameObject.WorldObject;
import com.webstorms.physics.Coordinator2D;

public class Stars extends WSObject {

	World world;
	Star[] stars;
	
	protected Stars(Game game, World world, int amount) {
		super(game);
		this.world = world;
		stars = new Star[amount];

		// Set up the first stars
		for(int i = 0; i < stars.length; i++) {
			// Set random location
			int x = Util.getRandomNumberBetween(0, this.world.getGame().getWSScreen().getGameScreenWidth());
			int y = Util.getRandomNumberBetween(0, this.world.getGame().getWSScreen().getGameScreenHeight());
			stars[i] = new Star(this.getGame(), this.world, x, y);

		}

	}
	
	public void update() {
		for(int i = 0; i < stars.length; i++) {
			// Update all the stars
			stars[i].update();
			
			// Check if a star has travelled of the visible viewing bounds and a new one should be spawned
			if(stars[i].getMovementCoordinator().getY() > this.world.getGame().getWSScreen().getGameScreenHeight()) {
				// Set random location
				int x = Util.getRandomNumberBetween(0, this.world.getGame().getWSScreen().getGameScreenWidth());
				int y = 40;
				stars[i] = new Star(this.getGame(), this.world, x, y);
			    
			}
			
		}
		
	}
	
	public void render() {
		// Render all the stars
		for(int i = 0; i < stars.length; i++) {
			stars[i].render();
			
		}
		
	}
	
	
	public class Star extends GameObject {

		protected Star(Game game, WorldObject world, int x, int y) {
			super(game, world);			
			starType = Util.getRandomNumberFrom(STAR_TYPE_1, STAR_TYPE_3);
			
			// Set movement
			this.getMovementCoordinator().setAngle(Coordinator2D.DIRECTION_SOUTH);
			
			int v = Util.getRandomNumberBetween(0, 5);
			this.getMovementCoordinator().setVelocity(v);
			
			// Set Bitmap
			if(starType == Star.STAR_TYPE_1) {
				this.setBitmap(Assets.playScreen_Star1);
				this.getShape().addRectangle(new Rect(0, 0, Star.STAR_TYPE_1_WIDTH, Star.STAR_TYPE_1_HEIGHT));
				
			}
			else if(starType == Star.STAR_TYPE_2) {
				this.setBitmap(Assets.playScreen_Star2);
				this.getShape().addRectangle(new Rect(0, 0, Star.STAR_TYPE_2_WIDTH, Star.STAR_TYPE_2_HEIGHT));
				
			}
			else if(starType == Star.STAR_TYPE_3) {
				this.setBitmap(Assets.playScreen_Star3);
				this.getShape().addRectangle(new Rect(0, 0, Star.STAR_TYPE_3_WIDTH, Star.STAR_TYPE_3_HEIGHT));
				
			}
			
			this.setLocation(x, y);
			
		}
		
		private static final int STAR_TYPE_1 = 0;
		private static final int STAR_TYPE_2 = 1;
		private static final int STAR_TYPE_3 = 2;
		
		private static final int STAR_TYPE_1_WIDTH = 9;
		private static final int STAR_TYPE_1_HEIGHT= 9;
		private static final int STAR_TYPE_2_WIDTH = 6;
		private static final int STAR_TYPE_2_HEIGHT= 6;
		private static final int STAR_TYPE_3_WIDTH = 4;
		private static final int STAR_TYPE_3_HEIGHT= 4;
		
		private int starType;


		@Override
		public void checkCollisionDetection() {
			
		}
		
		
	}
	
	
}