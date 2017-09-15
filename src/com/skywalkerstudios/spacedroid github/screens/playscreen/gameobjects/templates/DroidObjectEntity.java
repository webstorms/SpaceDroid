package com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.templates;

import android.graphics.Bitmap;

import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.Lasers.AmmoBase;
import com.webstorms.framework.Game;
import com.webstorms.framework.gameObject.WorldObject;

public abstract class DroidObjectEntity extends GameObjectEntity {

	private Gun droidGun;
	
	protected DroidObjectEntity(Game game, WorldObject world, Bitmap bitmap) {
		super(game, world, bitmap);
		this.droidGun = new Gun(this.getGame(), (WorldObject) this.getWorld(), this);
		this.initializeGun(this.droidGun);
		this.droidGun.attachNewMagazine(this.getMagazine());
		
	}
	
	public Gun getGun() {
		return this.droidGun;
		
	}

	public abstract void initializeGun(Gun gun);
	
	public abstract AmmoBase[] getMagazine();
	
	public abstract void updateGunShots();
	
	public abstract void updateGunDisposes();
	
	@Override
	public void updateActiveGameObject() {
		super.updateActiveGameObject();
		if(this.getHealth() != 0) {
			// Update the laser location
			this.updateGunShots();
			
		}
		
	}

	@Override
	public void update() {
		super.update();
		if(this.isExploding()) {
			if(this.droidGun.isShooting()) {
				this.droidGun.shootLaser(false);
				
			}
			
		}
		
		// No matter if dead or alive, the lasers of this entity will not die off once already travelling
		updateGunDisposes();
		this.droidGun.update();
		
	}

	@Override
	public void render() {
		super.render();
		// The lasers
		this.getGun().render();
				
	}
	
	
}
