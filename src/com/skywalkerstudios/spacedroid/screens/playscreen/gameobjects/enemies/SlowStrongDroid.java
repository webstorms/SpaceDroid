package com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.enemies;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo1;
import com.skywalkerstudios.spacedroid.screens.playscreen.PowerUps;
import com.skywalkerstudios.spacedroid.screens.playscreen.World;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.Lasers.AmmoBase;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.Lasers.LaserBullet;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.templates.DroidObjectEntity;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.templates.Gun;
import com.skywalkerstudios.spacedroid.screens.playscreen.progressionmechanics.EnemyProgressionVariables;
import com.webstorms.framework.Game;
import com.webstorms.framework.gameObject.WorldObject;
import com.webstorms.framework.util.Util;
import com.webstorms.physics.Coordinator2D;
import com.webstorms.physics.Shape;

import android.graphics.Bitmap;
import android.graphics.Rect;

// Last updated: 12/05/2012
// Improvements needed: Make the explosion appear in the center of the StupidBadDroidSprite (minor, hardly noticeable)
// Known Bugs: 

public class SlowStrongDroid extends DroidObjectEntity {
	
	private static final float IDEAL_WEIGHT = 5;
	private static final int width = 100;
	private static final int height = 112;
	
	private static final int amountXP = 75;
	private static final int amountCredits = 50;
	private static final int amountHe3 = 5;
	
	public SlowStrongDroid(Game game, WorldObject world, Bitmap bitmap) {
		super(game, world, bitmap);
		this.setBitmap(Assets.playScreen_SlowStrongDroid);
		this.getMovementCoordinator().setVelocity(EnemyProgressionVariables.SlowStrongDroid.getVelocity());
		this.getMovementCoordinator().setAngle(Coordinator2D.DIRECTION_SOUTH + Util.getRandomNumberFrom(-10, 10));
		this.setHealth((int) EnemyProgressionVariables.SlowStrongDroid.getHealth());
		this.setWeight(IDEAL_WEIGHT);
		
		this.setExplosionSound(Assets.playScreen_Explosion1);
		
	}
	
	@Override
	public void initializeShape(Shape shape) {
		shape.addRectangle(new Rect(0, 0, width, height));
		
	}
	
	@Override
	public void initializeGun(Gun gun) {
		gun.getMovementCoordinator().setAngle(Coordinator2D.DIRECTION_SOUTH);
		gun.setShootFrequency(EnemyProgressionVariables.SlowStrongDroid.getLaserFrequency());
		gun.setShootDamage((int) EnemyProgressionVariables.SlowStrongDroid.getLaserDamage());
		gun.setShootVelocity(EnemyProgressionVariables.SlowStrongDroid.getLaserVelocity());
		gun.setAmmoType(Ammo1.CLASS_TAG); // Default
		gun.setAmmo(1);
		gun.setShootSound(Assets.playScreen_GunShoot2);
		
	}

	@Override
	public AmmoBase[] getMagazine() {
		LaserBullet[] newMagazine = new LaserBullet[10];
		for(int i = 0; i < 10; i++) {
			newMagazine[i] = new LaserBullet(this.getGame(), (World) this.getWorld(), this);
			newMagazine[i].setBitmap(Assets.playScreen_RedLaser);
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
	public void updateActiveGameObject() {
		super.updateActiveGameObject();
		
		// Check if this StupidBadDroid has traveled off the visible screen area
		if(this.getMovementCoordinator().getY() > this.getWorld().getGame().getWSScreen().getGameScreenHeight()) {
			// this.setY(0 - height);
		//	this.setDead();
			this.setTravelledOffScreen(true);
			
		}
		
	}

	@Override
	public void checkCollisionDetection() {
		// Check if this SlowStrongDroid has been hit by a laser from the PlayeDroid
		Integer indexOfCollidedObject = this.checkCollisionDetection(this.getWorld().getPlayerDroid().getAllAttachedMagazines());
		Integer indexOfCollidedObjectTempMag = null;
		if(((World) this.getWorld()).getPlayerDroid().getTempMagazine() != null) {
			indexOfCollidedObjectTempMag = this.checkCollisionDetection(((World) this.getWorld()).getPlayerDroid().getTempMagazine());

		}

		// Decrease the health if this SlowStrongDroid has been hit
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
	
	
}