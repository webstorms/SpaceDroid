package com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo1;
import com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship1;
import com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship2;
import com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship3;
import com.skywalkerstudios.spacedroid.screens.playscreen.PlayScreen;
import com.skywalkerstudios.spacedroid.screens.playscreen.PowerUps;
import com.skywalkerstudios.spacedroid.screens.playscreen.World;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.Lasers.AmmoBase;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.Lasers.LaserBeam;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.Lasers.LaserBullet;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.templates.DroidObjectEntity;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.templates.Gun;
import com.webstorms.framework.Game;
import com.webstorms.framework.gameObject.WorldObject;
import com.webstorms.framework.util.Util;
import com.webstorms.physics.Coordinator2D;
import com.webstorms.physics.Physics;
import com.webstorms.physics.Shape;

import android.graphics.Bitmap;
import android.graphics.Rect;

//Last updated: 30/05/20128
// Improvements needed:
// Known Bugs: 
// back up.

public class PlayerDroid extends DroidObjectEntity {
	
	private static final float WEIGHT = 1;
	
	public static final int SPACESHIP_1_WIDTH = 100;
	public static final int SPACESHIP_1_HEIGHT = 97;
	public static final int SPACESHIP_2_WIDTH = 100;
	public static final int SPACESHIP_2_HEIGHT = 97;
	public static final int SPACESHIP_3_WIDTH = 110;
	public static final int SPACESHIP_3_HEIGHT = 80;
	
	// This magazine holds a reference to all alive lasers don't belong to the current gun
	// not to interfere with th current magazine 
	private LaserBullet[] tempMagazine;
	private Gun laserBeamGun;

	public PlayerDroid(Game game, WorldObject world, Bitmap bitmap) {
		super(game, world, bitmap);
		this.getWorld().getGame().getGameLog().d(this, "Creating new PlayerDroid");
		
		if(this.getGame().getUserProfile().getSpaceship() instanceof Spaceship1) {
			this.setBitmap(Assets.playScreen_Spaceship1);
			
		}
		else if(this.getGame().getUserProfile().getSpaceship() instanceof Spaceship2) {
			this.setBitmap(Assets.playScreen_Spaceship2);
			
		}
		if(this.getGame().getUserProfile().getSpaceship() instanceof Spaceship3) {
			this.setBitmap(Assets.playScreen_Spaceship3);
			
		}
		
		this.setLocation((this.getGame().getWSScreen().getGameScreenWidth()/2) - (this.getShape().getWidth()/2), this.getGame().getWSScreen().getGameScreenHeight() - this.getShape().getHeight());
		this.setHealth(this.getGame().getUserProfile().getSpaceship().getAmountOfHealth());
		this.setWeight(WEIGHT);
		this.setExplosionSound(Assets.playScreen_Explosion2);
		
	}
	
	public Gun getLaserBeamGun() {
		return this.laserBeamGun;
		
	}
	
	@Override
	public void initializeShape(Shape shape) {
		if(this.getGame().getUserProfile().getSpaceship() instanceof Spaceship1) {
			shape.addRectangle(new Rect(0, 0, SPACESHIP_1_WIDTH, SPACESHIP_1_HEIGHT));
			shape.addRectangle(new Rect(0, 38, 100, 48 + 38));
			shape.addRectangle(new Rect(32, 0, 36 + 32, 97));
			
		}
		else if(this.getGame().getUserProfile().getSpaceship() instanceof Spaceship2) {
			shape.addRectangle(new Rect(0, 0, SPACESHIP_2_WIDTH, SPACESHIP_2_HEIGHT));
			shape.addRectangle(new Rect(0, 34, 100, 43 + 34));
			shape.addRectangle(new Rect(36, 0, 28 + 36, 97));
			
		}
		if(this.getGame().getUserProfile().getSpaceship() instanceof Spaceship3) {
			shape.addRectangle(new Rect(0, 0, SPACESHIP_3_WIDTH, SPACESHIP_3_HEIGHT));
			shape.addRectangle(new Rect(0, 38, 110, 22 + 38));
			shape.addRectangle(new Rect(0 + 37, 0, 36 + 37, 80));
			
		}
		
	}
	
	@Override
	public void initializeGun(Gun gun) {
		// Initialize the underlying gun
		gun.getMovementCoordinator().setAngle(Coordinator2D.DIRECTION_NORTH);
		gun.setShootFrequency(this.getGame().getUserProfile().getSpaceship().getShipShootingFrequency());
		gun.setShootDamage(this.getGame().getUserProfile().getSpaceship().getShipShootingDamage());
		gun.setShootVelocity(this.getGame().getUserProfile().getSpaceship().getShipShootingVelocity());
		gun.setAmmoType(this.getGame().getUserProfile().getSpaceship().getAmmo().getType()); 
		gun.setShootSound(Assets.playScreen_GunShoot1);
		if(!gun.getAmmoType().equals(Ammo1.CLASS_TAG) && this.getWorld().getGame().getUserProfile().getSpaceship().getCurrentAmountOfAmmo() != 0) {
			gun.setAmmo(this.getWorld().getGame().getUserProfile().getSpaceship().getCurrentAmountOfAmmo());
			
		}
		else {
			gun.setAmmo(this.getWorld().getGame().getUserProfile().getSpaceship().getShipMaxAmountOfAmmo());
			
		}
		
		// Initialize the laserBeam gun
		this.laserBeamGun = new Gun(this.getGame(), this.getWorld(), this);
		this.laserBeamGun.setShootDamage(this.getGame().getUserProfile().getSpaceship().getShipShootingDamage());
		this.laserBeamGun.setShootVelocity(0);
		this.laserBeamGun.setAmmoType(this.getGame().getUserProfile().getSpaceship().getAmmo().getType());
		this.laserBeamGun.setAmmo(1);
		this.laserBeamGun.attachNewMagazine(new AmmoBase[] {(AmmoBase) new LaserBeam(this.getGame(), this.getWorld(), this)});
		this.laserBeamGun.getMagazine()[0].dispose();
		this.laserBeamGun.setShootSound(Assets.playScreen_LaserBeamSound);
		this.laserBeamGun.getShootSound().loop(true);
		
	}
	
	@Override
	public AmmoBase[] getMagazine() {
		AmmoBase[] newMagazine = new LaserBullet[this.getWorld().getGame().getUserProfile().getSpaceship().getShipMaxAmountOfAmmo()];
		for(int i = 0; i < this.getWorld().getGame().getUserProfile().getSpaceship().getShipMaxAmountOfAmmo(); i++) {
			newMagazine[i] = (AmmoBase) new LaserBullet(this.getGame(), (World) this.getWorld(), this);
			newMagazine[i].dispose();

		}
		return newMagazine;
		
	}
	
	public AmmoBase[] getAllAttachedMagazines() {
		int gunMagazineLength = this.getGun().getMagazine().length;
		int gunLaserBeamMagazineLength = this.laserBeamGun.getMagazine().length;
		
		AmmoBase[] allAttachedMagazines = new AmmoBase[gunMagazineLength + gunLaserBeamMagazineLength];
		System.arraycopy(this.getGun().getMagazine(), 0, allAttachedMagazines, 0, gunMagazineLength);
	    System.arraycopy(this.laserBeamGun.getMagazine(), 0, allAttachedMagazines, gunMagazineLength, gunLaserBeamMagazineLength);
	    
		return allAttachedMagazines;
		
	}
	
	public void enableLaserBeam(boolean use) {
		if(use) {
			this.getGame().getGameLog().d(this, "Starting LaserBeam");
			
			this.laserBeamGun.getShootSound().play();
			this.laserBeamGun.getMagazine()[0] = (AmmoBase) new LaserBeam(this.getGame(), this.getWorld(), this);
			this.laserBeamGun.getMagazine()[0].setLocation(
                    Util.centerObject(this.laserBeamGun.getMagazine()[0].getShape().getWidth(), this.getMovementCoordinator().getX(), this.getMovementCoordinator().getX() + this.getShape().getWidth()), 
                    0);
			
		}
		else {
			this.getGame().getGameLog().d(this, "Stopping LaserBeam");
			
			this.laserBeamGun.getShootSound().stop();
			this.laserBeamGun.getMagazine()[0].dispose();
			
		}
		
	}

	public void setTempMagazine(LaserBullet[] tempMagazine) {
		this.tempMagazine = tempMagazine;

	}

	public LaserBullet[] getTempMagazine() {
		return this.tempMagazine;
		
	}
	
	public boolean hasAmmo() {
		return this.getGun().getCurrentAmmo() != 0;
		
	}
	
	public boolean hasFullAmmo() {
		return this.getGun().getCurrentAmmo() == this.getGun().getMagazineSize();
		
	}
	
	public void replenishAmmo() {
		this.getGun().setAmmo(this.getWorld().getGame().getUserProfile().getSpaceship().getShipMaxAmountOfAmmo());
		
	}
	
	public void replenishLife() {
		this.setHealth(((SpaceDroid) this.getWorld().getGame()).getUserProfile().getSpaceship().getAmountOfHealth());
		
	}
	
	@Override
	public void update() {
		super.update();
		if(!this.isExploding() && !this.isDead()) {
			this.laserBeamGun.update();
			
		}
		
		// Update the temp Magazine
		if(this.tempMagazine != null) {
			this.getWorld().getGame().getGameLog().d(this, "Updating TempMagazine");
			int amountOfActiveLasers = 0;
			
			for(int i = 0; i < this.tempMagazine.length; i++) {
				if(!tempMagazine[i].isDisposable()) {
					tempMagazine[i].update();
					amountOfActiveLasers++;
					this.getWorld().getGame().getGameLog().d(this, "Updating Bullet: " + i);
					
				}
				
			}

			if(amountOfActiveLasers == 0) {
				this.tempMagazine = null;

			}

		}
				
	}
	
	@Override
	public void render() {
		if(!this.isExploding() && !this.isDead()) {
			this.laserBeamGun.render();
			
		}
		super.render();
		
		/*
		if(((SpaceDroid) this.getWorld().getGame()).getUserProfile().getSpaceship().getAmmo() instanceof Ammo1) {
			this.renderGameObjectShapes(Color.RED);
			
		}
		else if(((SpaceDroid) this.getWorld().getGame()).getUserProfile().getSpaceship().getAmmo() instanceof Ammo2) {
			this.renderGameObjectShapes(Color.BLUE);
			
		}
		else if(((SpaceDroid) this.getWorld().getGame()).getUserProfile().getSpaceship().getAmmo() instanceof Ammo3) {
			this.renderGameObjectShapes(Color.GREEN);
			
		} */
		
		// Render TempMagazine
		if(this.tempMagazine != null) {
			for(int i = 0; i < this.tempMagazine.length; i++) {
				if(!tempMagazine[i].isDisposable()) {
					this.getWorld().getGame().getGameLog().d(this, "Rendering Bullet " + i + " X: " + tempMagazine[i].getMovementCoordinator().getX() + " Y: " + tempMagazine[i].getMovementCoordinator().getY());
					tempMagazine[i].render();

				}
			}

		}
		
	}

	@Override
	public void updateGunShots() {
		this.getGun().shootLaser(this.getWorld().getGame().getInput().isTouchDown() && ((PlayScreen) this.getWorld().getGame().getCurrentScreen()).pauseButton.isPressed() != true && ((World) this.getWorld()).getPowerUps().isOneOfTheTypesActive(PowerUps.LASER) == false);

		if(this.getWorld().getGame().getInput().isTouchDown() && ((PlayScreen) this.getWorld().getGame().getCurrentScreen()).pauseButton.isPressed() != true && ((World) this.getWorld()).getPowerUps().isOneOfTheTypesActive(PowerUps.LASER) == false) {
			this.getGame().getGameLog().d(this, "Should shoot another laser");
			
		}
		
		if(this.getWorld().getGame().getInput().isTouchDown() && ((PlayScreen) this.getWorld().getGame().getCurrentScreen()).pauseButton.isPressed() != true) {
			this.getGame().getGameLog().d(this, "Should definitely shoot another laser");
			
		}
		
	}

	@Override
	public void updateGunDisposes() {
		// Check if the laser has traveled of the screen
		for(int i = 0; i < this.getGun().getMagazineSize(); i++) { // ((World) this.getWorld()).game.getUserProfile().getSpaceship().getShipAmountOfAmmo()
			if(this.getGun().getMagazine()[i].getMovementCoordinator().getY() < 0 - this.getGun().getMagazine()[i].getShape().getHeight()) {
				this.getGun().getMagazine()[i].dispose();
				
			}
			
		}
				
	}
	
	@Override
	public void checkCollisionDetection() {
		
		Integer indexOfCollidedLaser = null;
		Integer indexOfCollidedEnemy = null;
		int collisionPower = 0;
		
		// Check if this PlayeDroid has been hit by an enemy laser
		for(int i = 0; i < ((World) this.getWorld()).getProgressionMechanics().getAmountOfLevelVisibleEnemies(); i++) {
			
			if(((World) this.getWorld()).getHolderEnemies()[i] instanceof DroidObjectEntity) {
				
				if(((World) this.getWorld()).getHolderEnemies()[i] != null) {
					indexOfCollidedLaser = this.checkCollisionDetection(((DroidObjectEntity) this.getWorld().getHolderEnemies()[i]).getGun().getMagazine());
					
				}
				
				if(indexOfCollidedLaser != null) {
					
					collisionPower = ((DroidObjectEntity) this.getWorld().getHolderEnemies()[i]).getGun().getMagazine()[indexOfCollidedLaser].getDamage();
					
					// this.changeHealthBy(- (((DroidObjectEntity) ((World) this.getWorld()).getHolderEnemies()[i]).getGun().getDamage()));
					this.changeHealthBy(- collisionPower);
					// Dispose the laser that belongs to the PlayerDroid which collided with this StupidBadDroid
					((DroidObjectEntity) ((World) this.getWorld()).getHolderEnemies()[i]).getGun().getMagazine()[indexOfCollidedLaser].dispose();
					
					if(this.getGame().getUserProfile().getViabrationOn()) {
						this.getGame().getOutput().vibrate(10 * collisionPower);
						
					}
					
				}
				
			}
			
		}
		
		// Check if this PlayerDroid has collided with the StupidBadDroid
		indexOfCollidedEnemy = this.checkCollisionDetection(((World) this.getWorld()).getHolderEnemies());
		
		if(indexOfCollidedEnemy != null) {
			
			// Velocities added and multiplied by 5
			collisionPower = Physics.getImpulseOfCollision(this, this.getWorld().getHolderEnemies()[indexOfCollidedEnemy]);
			// collisionPower = 2 * (this.getMovementCoordinator().getVelocity() + ((World) this.getWorld()).getHolderEnemies()[indexOfCollidedEnemy].getMovementCoordinator().getVelocity());
			
			this.getWorld().getGame().getGameLog().d(this, "PlayerDroid V: " + this.getMovementCoordinator().getVelocity());
			this.getWorld().getGame().getGameLog().d(this, "Enemy V: " + ((World) this.getWorld()).getHolderEnemies()[indexOfCollidedEnemy].getMovementCoordinator().getVelocity());
			
			this.getWorld().getGame().getGameLog().d(this, "Collision Power: " + collisionPower);
			
			this.changeHealthBy(- collisionPower);
			
			// Dispose the enemy
			((World) this.getWorld()).getHolderEnemies()[indexOfCollidedEnemy].setHealth(0);
			
			if(this.getGame().getUserProfile().getViabrationOn()) {
				this.getGame().getOutput().vibrate(10 * collisionPower);
				
			}
			
		}
	
		/*
		// Vibrate the phone
		if(indexOfCollidedLaser != null || indexOfCollidedEnemy != null) {
			this.getWorld().getGame().getGameLog().d(this, "Collision Power: " + collisionPower);
			if(this.getGame().getUserProfile().getViabrationOn()) {
				this.getGame().getOutput().vibrate(10 * collisionPower);
				
			}
			
		} */
		
	}

	@Override
	public void updateGameObjectPosition() {
		super.updateGameObjectPosition();
		
		if(this.isExploding() != true) {
			int rawData = (int) this.getWorld().getGame().getInput().getAccelX();;
			
			// Set orientation
			if(rawData > 0) {
				this.getMovementCoordinator().setAngle(Coordinator2D.DIRECTION_WEST);
				
			}
			else {
				this.getMovementCoordinator().setAngle(Coordinator2D.DIRECTION_EAST);
				
			}
			
			// Make raw data positive if it's negative
			if(rawData < 0) {
				rawData *= -1;
				
			}
			
			int v = (int) (((SpaceDroid) this.getWorld().getGame()).getUserProfile().getTiltSensitivity()) * rawData;
			
			this.getMovementCoordinator().setVelocity(v);
			
			// Check if the player is touching the left wall
			if(this.getMovementCoordinator().getX() < 0 || this.getMovementCoordinator().getX() == 0) {
				this.setLocation(0, this.getMovementCoordinator().getY());
				
			}
			// Check if the player is touching the right wall
			else if(this.getMovementCoordinator().getX() > this.getWorld().getGame().getWSScreen().getGameScreenWidth() - this.getShape().getWidth() || this.getMovementCoordinator().getX() == this.getWorld().getGame().getWSScreen().getGameScreenWidth() - this.getShape().getWidth()) {
				this.setLocation(this.getWorld().getGame().getWSScreen().getGameScreenWidth() - this.getShape().getWidth(), this.getMovementCoordinator().getY());
				
				
			}
			
		}
		
	}
	
	
}