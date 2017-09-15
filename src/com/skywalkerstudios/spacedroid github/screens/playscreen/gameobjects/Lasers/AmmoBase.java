package com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.Lasers;

import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo1;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo2;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo3;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.templates.DroidObjectEntity;
import com.webstorms.framework.Game;
import com.webstorms.framework.gameObject.GameObject;
import com.webstorms.framework.gameObject.WorldObject;

public class AmmoBase extends GameObject {
	
	// Remove gun and isDisposal variables!1
	
	private boolean isDisposable; // isDispose is equivalent to isDead from the DroidTemplate class
	private int damage;
	private String ammoType;
	
	public AmmoBase(Game game, WorldObject world, DroidObjectEntity droid) {
		super(game, world);
		
		// Set up properties of this bullet
		this.damage = droid.getGun().getDamage();
		this.ammoType = droid.getGun().getAmmoType();
		this.setVelocity(droid.getGun().getVelocity());
		this.getMovementCoordinator().setAcceleration(droid.getGun().getAcceleration());
		
	}	
	
	public String getAmmoType() {
		return this.ammoType;
		
	}
	
	public void setVelocity(float velocity) {
		this.getMovementCoordinator().setVelocity(velocity);
		
	}
	
	@Override
	public void checkCollisionDetection() {
		
	}
	
	public void setAmmoType(String ammoType) {
		this.ammoType = ammoType;
		
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
		
	}
	
	public int getDamage() {
		return this.damage;
		
	}
	
	public void reset() {
		isDisposable = false;
		this.setCollidable(true);
		this.setLocation(0, 0);
		
	}
	
	public boolean isDisposable() {
		return isDisposable;
		
	}
	
	public void dispose() {
		isDisposable = true;
		this.setCollidable(false);
		
	}

	@Override
	public void render() {
		super.render();
		if(this.ammoType.equals(Ammo1.CLASS_TAG)) {
		//	this.renderShapes(Color.RED);
			
		}
		else if(this.ammoType.equals(Ammo2.CLASS_TAG)) {
		//	this.renderShapes(Color.BLUE);
			
		}
		else if(this.ammoType.equals(Ammo3.CLASS_TAG)) {
		//	this.renderShapes(Color.GREEN);
			
		}
		
	}
	
	
}
