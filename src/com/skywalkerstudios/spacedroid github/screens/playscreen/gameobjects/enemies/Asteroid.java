package com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.enemies;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.screens.playscreen.PowerUps;
import com.skywalkerstudios.spacedroid.screens.playscreen.World;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.templates.GameObjectEntity;
import com.skywalkerstudios.spacedroid.screens.playscreen.progressionmechanics.EnemyProgressionVariables;
import com.webstorms.framework.Game;
import com.webstorms.framework.util.Util;
import com.webstorms.framework.gameObject.WorldObject;
import com.webstorms.framework.graphics.animation.Spinner;
import com.webstorms.framework.graphics.animation.SpriteSheetAnimation;
import com.webstorms.physics.Circle;
import com.webstorms.physics.Coordinator2D;
import com.webstorms.physics.Shape;

public class Asteroid extends GameObjectEntity {
	
	/* Add two more types of Asteroids with different dimensions and images
	 * 
	 * 
	 */
	private static final int MIN_RADIUS = 35;
	private static final int MAX_RADIUS = 70;
	private static final int IDEAL_SPINNER_VALUE = 8;
	private static final int IDEAL_WEIGHT = 5;
	private static final float IDEAL_TIME_FOR_ONE_REVOLUTION = 1f;
	
	private static final int amountXP = 50;
	private static final int amountCredits = 50;
	private static final int amountHe3 = 5;
	
	private int RADIUS;
	Spinner spinner = new Spinner();
	
	SpriteSheetAnimation asteroidAnimation;
	
	public Asteroid(Game game, WorldObject world, Bitmap bitmap) {
		super(game, world, bitmap);
		
		// this.setBitmap(Assets.playScreen_Asteroid);
		
		float ratio = Util.round((float) RADIUS / Asteroid.MAX_RADIUS, 2);
		this.setHealth((int) (EnemyProgressionVariables.Asteroid.getHealth() * ratio));
		this.setWeight((int) (IDEAL_WEIGHT * ratio));
		this.spinner.setVelocity(IDEAL_SPINNER_VALUE * (1 - ratio));
		
		this.getMovementCoordinator().setVelocity(EnemyProgressionVariables.Asteroid.getVelocity());
		this.getMovementCoordinator().setAngle(Coordinator2D.DIRECTION_SOUTH + Util.getRandomNumberFrom(-20, 20));
		
		int randomNumber = Util.getRandomNumberFrom(1, 2);
		if(randomNumber == 1) {
			this.asteroidAnimation = new SpriteSheetAnimation(this.getGame(), Assets.playScreen_Asteroid1Animation, null, new Rect(0, 0, this.getShape().getWidth(), this.getShape().getHeight()), 1, 18, IDEAL_TIME_FOR_ONE_REVOLUTION * ratio, false);
			
		}
		else if(randomNumber == 2) {
			this.asteroidAnimation = new SpriteSheetAnimation(this.getGame(), Assets.playScreen_Asteroid2Animation, null, new Rect(0, 0, this.getShape().getWidth(), this.getShape().getHeight()), 1, 18, IDEAL_TIME_FOR_ONE_REVOLUTION * ratio, false);
			
		}
		this.asteroidAnimation.repeatAnimation(true);
		
		this.getGame().getGameLog().d(this, "Acceleration: " + this.getMovementCoordinator().getAcceleration());
		
		this.getGame().getGameLog().d(this, "Asteroid Size: " + ratio);
		this.getGame().getGameLog().d(this, "Asteroid Health: " + this.getHealth());
		
		this.setExplosionSound(Assets.playScreen_Explosion1);
		
	}
	
	@Override
	public void initializeShape(Shape shape) {
		RADIUS = Util.getRandomNumberFrom(Asteroid.MIN_RADIUS, Asteroid.MAX_RADIUS);
		shape.addRectangle(new Rect(0, 0, RADIUS * 2, RADIUS * 2));
		shape.addCircle(new Circle(RADIUS, RADIUS, RADIUS));
		
	}
	
	@Override
	public void updateActiveGameObject() {
		super.updateActiveGameObject();
		
		this.spinner.update();
		
		// Check if this StupidBadDroid has traveled off the visible screen area
		if(this.getMovementCoordinator().getY() > this.getGame().getWSScreen().getGameScreenHeight()) {
			// this.setY(0 - height);
			// this.setDead();
			this.setTravelledOffScreen(true);
			
		}
		
	}
	
	@Override
	public void checkCollisionDetection() {
		// Check if this Asteroid has been hit by a laser from the PlayeDroid
		Integer indexOfCollidedObject = this.checkCollisionDetection(this.getWorld().getPlayerDroid().getAllAttachedMagazines());
		Integer indexOfCollidedObjectTempMag = null;
		if(((World) this.getWorld()).getPlayerDroid().getTempMagazine() != null) {
			indexOfCollidedObjectTempMag = this.checkCollisionDetection(((World) this.getWorld()).getPlayerDroid().getTempMagazine());
			
		}
		
		// Decrease the health if this Asteroid has been hit
		if(indexOfCollidedObject != null || indexOfCollidedObjectTempMag != null) {
			if(!this.getWorld().getPowerUps().isOneOfTheTypesActive(PowerUps.INSTANT_KILL)) {
				if(indexOfCollidedObject != null) {
					this.setHealth((int) this.getHealth() - this.getWorld().getPlayerDroid().getAllAttachedMagazines()[indexOfCollidedObject].getDamage());
					
				}
				if(indexOfCollidedObjectTempMag != null) {
					this.setHealth((int) this.getHealth() - this.getWorld().getPlayerDroid().getTempMagazine()[indexOfCollidedObjectTempMag].getDamage());
					
				}
				
			}
			else {
				this.setHealth(0);
				
			}
			
			// Dispose the laser that belongs to the PlayerDroid which collided with this Asteroid
			if(indexOfCollidedObject != null) {
				// If collision is not with laser, then dispose
				if(!this.getWorld().getPowerUps().isOneOfTheTypesActive(PowerUps.LASER)) {
					this.getWorld().getPlayerDroid().getGun().getMagazine()[indexOfCollidedObject].dispose();
					
				}
				
			}
			if(indexOfCollidedObjectTempMag != null) {
				this.getWorld().getPlayerDroid().getTempMagazine()[indexOfCollidedObjectTempMag].dispose();
				
			}
			
		}
		
	}

	@Override
	public void onExplosion() {
		((World) this.getWorld()).increaseUserValues(amountXP, amountCredits, amountHe3);

		if(((World) this.getWorld()).getPowerUps().shouldGenerateNewPowerUp()) {
			((World) this.getWorld()).getPowerUps().generateNewPowerUp(this.getMovementCoordinator().getX(), this.getMovementCoordinator().getY());

		}

	}
	
	@Override
	public void updateGameObjectPosition() {
		super.updateGameObjectPosition();
		this.asteroidAnimation.update();
		this.asteroidAnimation.setLocation(this.getX(), this.getY());
		
		// this.increaseRotationOfEnemy(2); // Make this relevant to the speed of the Asteroid
		
	}
	
	@Override
	public void renderBitmap() {
	//	super.renderGameObjectBitmap();
		
		// this.getWorld().getGame().getGraphics().drawBitmap(this.getBitmap(), Animation.rotateMatrix(Assets.playScreen_Asteroid, this.getShape(), this.spinner.getValue()), null);
		
		this.asteroidAnimation.render();
		
		// Render using Matrix, for coolass spin effect
		
	}
	
	
}
