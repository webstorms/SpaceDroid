package com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.templates;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.Lasers.AmmoBase;
import com.webstorms.framework.Game;
import com.webstorms.framework.util.Timer;
import com.webstorms.framework.util.Util;
import com.webstorms.framework.audio.Sound;
import com.webstorms.framework.gameObject.GameObject;
import com.webstorms.framework.gameObject.WorldObject;

public class Gun extends GameObject {
	
	// Variables that change when building a new gun
	// Gun information
	private int gunsCurrentAmountOfAmmo;
	private int gunsShootingFrequency;
	private int gunsLasersDamage;
	private float gunsLasersVelocity;
	private float gunsLasersAcceleration;
	
	// LaserHolder
	private AmmoBase[] magazine;
	
	// Routine variables
	private Timer laserGenerateDelay;
	private boolean shootLaser;
	
	private Sound shootSound;
	
	private boolean isShooting;

	private DroidObjectEntity droid;
	
	private String ammoType; // Determines strength: Red, Blue and Green
	
	public Gun(Game game, WorldObject world, DroidObjectEntity droid) {
		super(game, world);
		this.droid = droid;
		laserGenerateDelay = new Timer(this.gunsShootingFrequency);
		this.shootSound = Assets.playScreen_GunShoot1;
		
	}
	
	public void setAmmoType(String string) {
		this.ammoType = string;
		
	}
	
	public String getAmmoType() {
		return this.ammoType;
		
	}
	
	@Override
	public void checkCollisionDetection() {
		// TODO Auto-generated method stub
		
	}
	
	/*
	public int getAmountOfActiveLaserBullets() {
		int amountOfLaserBulletsActive = 0;
		
		for(int i = 0; i < ((World) this.getWorld()).getPlayerDroid().getGun().getMagazineSize(); i++) {
			if(((World) this.getWorld()).getPlayerDroid().getGun().getMagazine()[i].isDisposable() == false) {
				amountOfLaserBulletsActive++;
			}
			
		}
		
		return amountOfLaserBulletsActive;
	} */
	
	public int getCurrentAmmo() {
		return this.gunsCurrentAmountOfAmmo;
		
	}
	
	public int getMagazineSize() {
		return this.magazine.length;
		
	}
	
	public boolean isShooting() {
		return this.isShooting;
		
	}
	
	public void setShootFrequency(int n) {
		this.gunsShootingFrequency = n;
	}
	
	public void setShootDamage(int n) {
		this.gunsLasersDamage = n;
	}
	
	public void setShootVelocity(float v) {
		this.gunsLasersVelocity = v;
		
	}
	
	public void setShootAcceleration(float a) {
		this.gunsLasersAcceleration = a;
		
	}
	
	public void attachNewMagazine(AmmoBase[] copyOfNewMagazine) {
		this.magazine = copyOfNewMagazine;
		
		
		/*
		for(int i = 0; i < this.magazine.length; i++) {
			this.magazine[i].setDamage(this.gunsLasersDamage);
			this.magazine[i].setAmmoType(this.ammoType);
			this.magazine[i].setVelocity(this.gunsLasersVelocity);
			this.magazine[i].getMovementCoordinator().setAcceleration(this.gunsLasersAcceleration);
		} */
		
	//	gunsCurrentAmountOfAmmo = this.magazine.length;
		
	}
	
	public void increaseAmmoBy(int n) {
		this.gunsCurrentAmountOfAmmo += n;
		
	}
	
	public void decreaseAmmoBy(int n) {
		this.gunsCurrentAmountOfAmmo -= n;
		
	}
	
	public void setAmmo(int i) {
		this.gunsCurrentAmountOfAmmo = i;
		
	}
	
	public AmmoBase[] getMagazine() {
		return this.magazine;
	}
	
	public void setShootSound(Sound newShootSound) {
		this.shootSound = newShootSound;
	}
	
	public Sound getShootSound() {
		return this.shootSound;
		
	}
	
	public int getDamage() {
		return this.gunsLasersDamage;
	}
	
	public float getVelocity() {
		return this.gunsLasersVelocity;
		
	}
	
	public float getAcceleration() {
		return this.gunsLasersAcceleration;
		
	}
	
	public void shootLaser(boolean shootLaser) {
		this.shootLaser = shootLaser;
	}
	
	@Override
	public void update() {
		// Check if a new Laser should be created
		if(this.shootLaser) {
			if(this.laserGenerateDelay.delay() == true) {
				boolean createdNewLaser = false;
				for(int i = 0; i < this.magazine.length; i++) {
					if(createdNewLaser == false) {
						if(magazine[i].isDisposable()) {
							// Initialize default location
							if(this.gunsCurrentAmountOfAmmo != 0) {
								this.gunsCurrentAmountOfAmmo -= 1;

								magazine[i].reset();

								magazine[i].setLocation(
										Util.centerObject(this.magazine[i].getShape().getWidth(), this.droid.getX(), this.droid.getX() + this.droid.getShape().getWidth()), 
										Util.centerObject(this.magazine[i].getShape().getHeight(), this.droid.getY(), this.droid.getY() + this.droid.getShape().getHeight()));

								magazine[i].getMovementCoordinator().setAngle(this.getMovementCoordinator().getAngle());

								createdNewLaser = true;

								this.shootSound.play();


							}

						}
					}
				}

				// Refresh timer
				this.laserGenerateDelay.setDelayTime((int) this.gunsShootingFrequency);
				this.laserGenerateDelay.reset();

			}

		}	

		isShooting = false;

		// Update all the other Lasers
		for(int i = 0; i < this.magazine.length; i++) {
			if(!magazine[i].isDisposable()) {
				magazine[i].update();
				isShooting = true;

			}
		}

	}

	@Override
	public void render() {
		for(int i = 0; i < this.magazine.length; i++) {
			if(magazine[i].isDisposable() == false) {
				magazine[i].render();
			}

		}

	}

	public Timer getLaserGenerateTimer() {
		return this.laserGenerateDelay;

	}

	
}
