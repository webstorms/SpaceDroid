package com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.enemies;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo1;
import com.skywalkerstudios.spacedroid.screens.playscreen.PowerUps;
import com.skywalkerstudios.spacedroid.screens.playscreen.World;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.Lasers.AmmoBase;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.Lasers.Missile;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.templates.DroidObjectEntity;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.templates.Gun;
import com.skywalkerstudios.spacedroid.screens.playscreen.progressionmechanics.EnemyProgressionVariables;
import com.webstorms.framework.Game;
import com.webstorms.framework.gameObject.WorldObject;
import com.webstorms.framework.util.Timer;
import com.webstorms.framework.util.Util;
import com.webstorms.physics.Coordinator2D;
import com.webstorms.physics.Shape;

public class Boss extends DroidObjectEntity {

	private static final int WIDTH = 160;
	private static final int HEIGHT = 126;
	
	private static final int amountXP = 100;
	private static final int amountCredits = 75;
	private static final int amountHe3 = 10;
	
	Timer verticalMovementDelay = new Timer(10);
	
	private final int topBoundary = 0;
	private final int bottomBoundary = 500;
	private final int leftBoundary = 0;
	private final int rightBoundary = 480;
	
	public Boss(Game game, WorldObject world, Bitmap bitmap) {
		super(game, world, bitmap);
		this.setBitmap(Assets.playScreen_Boss);
		this.getMovementCoordinator().setVelocity(EnemyProgressionVariables.Boss.getVelocity());
		this.getMovementCoordinator().setAngle(this.getNewAngle(Coordinator2D.DIRECTION_SOUTH));
		this.setHealth((int) EnemyProgressionVariables.Boss.getHealth());
		
		this.getWorld().getGame().getGameLog().d(this, "Velocity: " + EnemyProgressionVariables.Boss.getVelocity());
		this.getWorld().getGame().getGameLog().d(this, "Laser Velocity: " + EnemyProgressionVariables.Boss.getLaserVelocity());
		
		this.setExplosionSound(Assets.playScreen_Explosion1);
		
	}
	
	@Override
	public void initializeShape(Shape shape) {
		shape.addRectangle(new Rect(0, 0, WIDTH, HEIGHT));
		
	}
	
	@Override
	public void initializeGun(Gun gun) {
		gun.getMovementCoordinator().setAngle(Coordinator2D.DIRECTION_SOUTH);
		gun.setShootFrequency(EnemyProgressionVariables.Boss.getLaserFrequency());
		gun.setShootDamage((int) EnemyProgressionVariables.Boss.getLaserDamage());
		gun.setShootVelocity(EnemyProgressionVariables.Boss.getLaserVelocity());
		gun.setAmmoType(Ammo1.CLASS_TAG); // Default
		gun.setShootAcceleration(EnemyProgressionVariables.Boss.getLaserAcceleration());
		gun.setAmmo(1); // Ammo is constantly 1. updateGunShots() sets Ammo to 1
		gun.setShootSound(Assets.playScreen_MissleLaunch);
		
	}

	@Override
	public AmmoBase[] getMagazine() {
		Missile[] newMagazine = new Missile[10];
		for(int i = 0; i < 10; i++) {
			newMagazine[i] = new Missile(this.getGame(), (World) this.getWorld(), this);
			newMagazine[i].dispose();
			
		}
		return newMagazine;
		
	}
	
	@Override
	public void updateGunShots() {
		// Check if the PlayerDroid in the view of this bad ass!
		int leftValue = ((World) this.getWorld()).getPlayerDroid().getMovementCoordinator().getX();
		int rightValue = ((World) this.getWorld()).getPlayerDroid().getMovementCoordinator().getX() + ((World) this.getWorld()).getPlayerDroid().getShape().getWidth();
		int start = this.getMovementCoordinator().getX();
		int end = this.getMovementCoordinator().getX() + this.getShape().getWidth();
		
		if(Util.inRangeFrom(start, end, leftValue) || Util.inRangeFrom(start, end, rightValue)) {
			this.getGun().setAmmo(1);
			this.getGun().shootLaser(true);
			
		}
		else {
			this.getGun().shootLaser(false);
			
		}
		
	}

	@Override
	public void updateGunDisposes() {
		// Check if the laser has traveled of the screen
		for(int i = 0; i < 10; i++) {
			if(this.getGun().getMagazine()[i].getMovementCoordinator().getY() > this.getWorld().getGame().getWSScreen().getGameScreenHeight()) {
				this.getGun().getMagazine()[i].dispose();
				
			}
		}
			
	}
	
	@Override
	public void checkCollisionDetection() {
		// Check if this Boss has been hit by a laser from the PlayeDroid
		Integer indexOfCollidedObject = this.checkCollisionDetection(this.getWorld().getPlayerDroid().getAllAttachedMagazines());
		Integer indexOfCollidedObjectTempMag = null;
		if(((World) this.getWorld()).getPlayerDroid().getTempMagazine() != null) {
			indexOfCollidedObjectTempMag = this.checkCollisionDetection(((World) this.getWorld()).getPlayerDroid().getTempMagazine());

		}

		// Decrease the health if this Boss has been hit
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

			// Dispose the laser that belongs to the PlayerDroid which collided with this Boss
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
	
	public Timer getMovementDelay() {
		return this.verticalMovementDelay;
		
	}
	
	public int getNewAngle(int towardsPole) {
		int newAngle = 0;
		
		int centerXOfPlayer = (((World) this.getWorld()).getPlayerDroid().getMovementCoordinator().getX() + ((World) this.getWorld()).getPlayerDroid().getShape().getWidth()/2);
		
		int centerXOfBoss = (this.getMovementCoordinator().getX() + this.getShape().getWidth()/2);
		int bottomYOfBoss = this.getMovementCoordinator().getY();
		int topYOfBoss = (this.getMovementCoordinator().getY() + this.getShape().getHeight());
		
		int adjacent;
		int opposite;
		
		if(towardsPole == Coordinator2D.DIRECTION_NORTH) {
			
			adjacent = (centerXOfPlayer - centerXOfBoss);
			opposite = (this.topBoundary - bottomYOfBoss);
			
			int calculatedAngle;
			if(adjacent != 0) {
				calculatedAngle = (int) Util.round((float) Math.toDegrees(Math.atan((float) opposite/adjacent)), 0);
				
			}
			else {
				// Boss is exactly in line with the player on the vertical axis, thus making the adjacent variable equal to 0
				calculatedAngle = 90;
				
			}
			int displacementAngle;
			if(calculatedAngle > 0) {
				displacementAngle = -(90 - calculatedAngle);
				
			}
			else {
				displacementAngle = 90 + calculatedAngle;
				
			}
			
			newAngle = Util.getAbsoluteAngle(displacementAngle);
			
		}
		else if(towardsPole == Coordinator2D.DIRECTION_SOUTH) {
		
			adjacent = (centerXOfPlayer - centerXOfBoss);
			opposite = (this.bottomBoundary - 	topYOfBoss);
			
			int calculatedAngle;
			if(adjacent != 0) {
				calculatedAngle = (int) Util.round((float) Math.toDegrees(Math.atan((float) opposite/adjacent)), 0);
				
			}
			else {
				// Boss is exactly in line with the player on the vertical axis, thus making the adjacent variable equal to 0
				calculatedAngle = 90;
				
			}
			int displacementAngle;
			if(calculatedAngle > 0) {
				displacementAngle = -(90 - calculatedAngle);
				
			}
			else {
				displacementAngle = 90 + calculatedAngle;
				
			}
			
			newAngle = 180 + displacementAngle;
			
		}
		
		return newAngle;
		
	}
	
	@Override
	public void updateActiveGameObject() {
		super.updateActiveGameObject();
		/*
		// Check if the boss is travelling off the horizontal viewing area
		// Reset the direction
		
		int leftXOfBoss = this.getMovementCoordinator().getX();
		int rightXOfBoss = (this.getMovementCoordinator().getX() + this.getShape().getWidth());
		
		if((leftXOfBoss < this.leftBoundary || leftXOfBoss == this.leftBoundary) || (rightXOfBoss > this.rightBoundary || rightXOfBoss == this.rightBoundary)) {
			this.getWorld().getGame().getGameLog().d(this, "Bumped into side wall");
			if(this.getMovementCoordinator().getVerticalVelocity() > 0) {
				this.getMovementCoordinator().setAngle(this.getNewAngle(Coordinator2D.DIRECTION_SOUTH));
				
			}
			else {
				this.getMovementCoordinator().setAngle(this.getNewAngle(Coordinator2D.DIRECTION_NORTH));
				
			}
			
			
		} */

		// Check if the boss is travelling off the vertical viewing area
			// Reset the direction with a new vertical velocity
		
		int bottomYOfBoss = this.getMovementCoordinator().getY();
		int topYOfBoss = (this.getMovementCoordinator().getY() + this.getShape().getHeight());
		
		if(bottomYOfBoss < this.topBoundary || bottomYOfBoss == this.topBoundary) {
			this.getWorld().getGame().getGameLog().d(this, "Bumped into top boundary");			
			this.getMovementCoordinator().setAngle(this.getNewAngle(Coordinator2D.DIRECTION_SOUTH));
			
		}
		else if(topYOfBoss > this.bottomBoundary || topYOfBoss == this.bottomBoundary) {
			this.getWorld().getGame().getGameLog().d(this, "Bumped into bottom boundary");	
			this.getMovementCoordinator().setAngle(this.getNewAngle(Coordinator2D.DIRECTION_NORTH));
			
		}
		
		// Check if the direction can be reupdated
		
		if(verticalMovementDelay.delay()) {
			verticalMovementDelay.reset();
			this.getWorld().getGame().getGameLog().d(this, "Reupdating position");
			if(this.getMovementCoordinator().getVerticalVelocity() > 0) {
				this.getMovementCoordinator().setAngle(this.getNewAngle(Coordinator2D.DIRECTION_SOUTH));
				
			}
			else {
				this.getMovementCoordinator().setAngle(this.getNewAngle(Coordinator2D.DIRECTION_NORTH));
				
			}
			
		}
		
		
	}
	
	@Override
	public void onExplosion() {
		((World) this.getWorld()).increaseUserValues(amountXP, amountCredits, amountHe3);
		
		((World) this.getWorld()).getPowerUps().generateNewPowerUp(this.getMovementCoordinator().getX(), this.getMovementCoordinator().getY());
		
	}
	
	
}
