package com.skywalkerstudios.spacedroid.screens.playscreen;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.PlayerDroid;
import com.webstorms.framework.Game;
import com.webstorms.framework.WSObject;
import com.webstorms.framework.util.Util;
import com.webstorms.framework.gameObject.GameObject;
import com.webstorms.framework.gameObject.WorldObject;
import com.webstorms.framework.graphics.animation.SpriteSheetAnimation;
import com.webstorms.physics.Coordinator2D;

public class PowerUps extends WSObject {

	/* 
	 * 
	 * 
	 * 
	 */
	
	public static final Integer INSTANT_KILL = 0;
	public static final Integer NUKE = 1;
	public static final Integer MAX_AMMO = 2;
	public static final Integer TREASURE = 3;
	public static final Integer TIMES_TWO = 4;
	public static final Integer LASER = 5;
	public static final Integer MAX_LIFE = 6;
	
	World world;
	List<PowerUp> powerUps;
//	Integer currentPowerUp;
//	Timer lifeSpanOfActivePowerUp = new Timer(15*1000); // 10 Seconds
	List<ActivePowerUp> activePowerUps;
	AnimatedText animatedText;
	
	protected PowerUps(Game game, World world) {
		super(game);
		this.world = world;
		this.powerUps = new ArrayList<PowerUp>();
		this.activePowerUps = new ArrayList<ActivePowerUp>();
		this.animatedText = new AnimatedText(this.getGame(), this.world);
		
	}
	
	public class AnimatedText extends GameObject {
		
		protected AnimatedText(Game game, WorldObject world) {
			super(game, world);
			textColor.setColor(Color.rgb(215, 215, 205));
			textColor.setTypeface(Assets.standardScreen_Birdman);
			textColor.setTextSize(62);
			
			this.getMovementCoordinator().setAngle(Coordinator2D.DIRECTION_EAST);
			this.getMovementCoordinator().setVelocity(4);
			this.getMovementCoordinator().setLocation(40, 220);
			
		}
		
		private String text;
		private Paint textColor = new Paint();
		
		public void setText(String newText) {
			this.text = newText;
			this.getMovementCoordinator().setLocation(40, this.getMovementCoordinator().getY());
			
		}
		
		@Override
		public void updateGameObjectPosition() {
			if(this.getMovementCoordinator().getX() != 300) {
				super.updateGameObjectPosition();
				
			}
			
		}

		@Override
		public void render() {
			if(this.getMovementCoordinator().getX() != 300) {
				this.getGame().getGraphics().drawText(text, this.getMovementCoordinator().getX(), this.getMovementCoordinator().getY(), this.textColor);
			}
			
		}

		@Override
		public void checkCollisionDetection() {
			
			
		}
		
		
	}
	
	public List<ActivePowerUp> getActivePowerUps() {
		return this.activePowerUps;
		
	}
	
	public boolean shouldGenerateNewPowerUp() {
		
		// 1 in 20 chance
		if(1 == Util.getRandomNumberFrom(1, 7)) { // Util.getRandomNumberFrom(1, 7)
			return true;
			
		}
		else {
			return false;
			
		}
		
	}
	
	public void generateNewPowerUp(int x, int y) {
		PowerUp newPowerUp = new PowerUp(this.getGame(), world, x, y);
		this.powerUps.add(newPowerUp);
		this.world.attachGameObject(newPowerUp);
		this.world.getGame().getGameLog().d(this, "GeneratedNewPowerUp");
		
	}
	
	public void update() {
		
		// Move text
	//	if(this.currentPowerUp != null) {
	//		this.animatedText.update();
			
	//	}
		if(this.activePowerUps.size() != 0) {
			this.animatedText.update();
			
		}
		
		// Update PowerUps
		for(int i = 0; i < powerUps.size(); i++) {
			powerUps.get(i).update();
			
		}
		
		// Manage PowerUps collision and disapearance off the screen
		
		for(int i = 0; i < powerUps.size(); i++) {
			
			// Check if a PowerUp box has travelled of the visible viewing bounds or has exceeded its lifespan and a new one should be spawned
			if(powerUps.get(i).getMovementCoordinator().getY() > this.world.getGame().getWSScreen().getGameScreenHeight()) {
				this.world.detachGameObject(powerUps.get(i));
				this.powerUps.remove(i);
				this.world.getGame().getGameLog().d(this, "Travelled off screen!");
				
			}
			
			// Check if a PowerUp box has collided with the PlayerDroid and should be disposed
			else if(powerUps.get(i).hasCollidedWithPlayerDroid()) {
				
				setCurrentPowerUp(powerUps.get(i).getPowerUpType());
				// Set text of PowerUps class for visiul representation
				if(this.activePowerUps.get(this.activePowerUps.size() - 1).getActivePowerUp() == PowerUps.INSTANT_KILL) {
					this.animatedText.setText("Instant kill");
					
				}
				else if(this.activePowerUps.get(this.activePowerUps.size() - 1).getActivePowerUp() == PowerUps.NUKE) {
					this.animatedText.setText("Nuke");
					this.world.nukeWave();
					
				}
				else if(this.activePowerUps.get(this.activePowerUps.size() - 1).getActivePowerUp() == PowerUps.MAX_AMMO) {
					this.animatedText.setText("Max Ammo");
					world.getPlayerDroid().replenishAmmo();
					
				}
				else if(this.activePowerUps.get(this.activePowerUps.size() - 1).getActivePowerUp() == PowerUps.TREASURE) {
					this.animatedText.setText("Treasure");
					world.increaseUserValues(100, 100, 100);
					
				}
				else if(this.activePowerUps.get(this.activePowerUps.size() - 1).getActivePowerUp() == PowerUps.TIMES_TWO) {
					this.animatedText.setText("Times two");
					
				}  
				else if(this.activePowerUps.get(this.activePowerUps.size() - 1).getActivePowerUp() == PowerUps.LASER) {
					this.animatedText.setText("Laser");
					// Check if activePowerUps contain a LaserBeam
					boolean moreThanOneLaserBeamActive = false;
					for(int a = 0; a < this.activePowerUps.size() - 1; a++) {
						if(this.activePowerUps.get(a).getActivePowerUp() == PowerUps.LASER) {
							this.activePowerUps.remove(a);
							moreThanOneLaserBeamActive = true;
							
						}
						
					}
					
					if(!moreThanOneLaserBeamActive) {
						world.getPlayerDroid().enableLaserBeam(true);
					}

						
				}
				else if(this.activePowerUps.get(this.activePowerUps.size() - 1).getActivePowerUp() == PowerUps.MAX_LIFE) {
					this.animatedText.setText("Max Health");
					this.world.getPlayerDroid().replenishLife();
					
				}
				
				this.world.detachGameObject(powerUps.get(i));
				this.powerUps.remove(i);
				this.world.getGame().getGameLog().d(this, "Collided!");
				
			//	this.lifeSpanOfActivePowerUp.reset();
				
			}
			
		}
		
		for(int i = 0; i < this.activePowerUps.size(); i++) {
			if(this.activePowerUps.get(i).shouldBeDisposed()) {
				if(this.activePowerUps.get(i).getActivePowerUp() == PowerUps.LASER) {
					this.world.getPlayerDroid().enableLaserBeam(false);
					
				}
				this.activePowerUps.remove(i);
				
			}
				
		}
		
	}
	
	public boolean isOneOfTheTypesActive(Integer type) {
		boolean state = false;
		for(int i = 0; i < this.activePowerUps.size(); i++) {
			if(this.activePowerUps.get(i).getActivePowerUp() == type) {
				state =  true;
				
			}
			
		}
		
		return state;
		
	}
	
	public void renderAnimatedText() {
		// Move text
		this.animatedText.render();
		
	}
	
	public void render() {
		for(int i = 0; i < powerUps.size(); i++) {
			powerUps.get(i).render();
			
		}

	}
	
	private void setCurrentPowerUp(Integer type) {
		// End current PowerUp
	//	if(this.currentPowerUp == PowerUps.LASER) {
	//		this.world.getPlayerDroid().enableLaserBullets();
			
	//	}
	//	this.currentPowerUp = type;
	//	this.lifeSpanOfActivePowerUp.reset();
		this.activePowerUps.add(new ActivePowerUp(type));
		Assets.playScreen_PowerUpSound.play();
		
	}
	
//	public Integer getCurrentPowerUp() {
//		return currentPowerUp;
		
//	}
	
	public class PowerUp extends GameObject {
		
		public SpriteSheetAnimation powerUpAnimation;
		
		private int powerUpType;
		private boolean shouldBeDisposed;
		

		protected PowerUp(Game game, WorldObject world, int x, int y) {
			super(game, world);
			this.setLocation(x, y);
			
			// Set movement
			this.getMovementCoordinator().setAngle(Coordinator2D.DIRECTION_SOUTH);
			this.getMovementCoordinator().setVelocity(10); // 5
			
			// Set Bitmap
			// this.setBitmap(Assets.playScreen_PowerUp);
			this.getShape().addRectangle(new Rect(0, 0, 65, 64));
			powerUpAnimation = new SpriteSheetAnimation(this.getGame(), Assets.playScreen_PowerUpAnimation, new Rect(0, 0, this.getShape().getWidth(), this.getShape().getHeight()), 1, 18);
			powerUpAnimation.repeatAnimation(true);
				
			powerUpType = Util.getRandomNumberFrom(PowerUps.INSTANT_KILL, PowerUps.MAX_LIFE);
			// powerUpType = PowerUps.LASER;
			
			
		}
		
		@Override
		public void update() {
			super.update();
			this.powerUpAnimation.update();
			this.powerUpAnimation.setLocation(this.getX(), this.getY());
			
		}
		
		@Override
		public void render() {
			super.render();
			this.powerUpAnimation.render();
			
		}

		@Override
		public void checkCollisionDetection() {
			
			// Check if this PowerUp has collided with the PlayerDroid
			PlayerDroid[] playerDroid = {((World) this.getWorld()).getPlayerDroid()};
			Integer indexOfCollidedObject = this.checkCollisionDetection(playerDroid);
			
			if(indexOfCollidedObject != null) {
				shouldBeDisposed = true;
				
			}
			
		}
		
		public int getPowerUpType() {
			return this.powerUpType;
			
		}
		
		public boolean hasCollidedWithPlayerDroid() {
			return this.shouldBeDisposed;
			
		}
		
		
	}
	
	
}
