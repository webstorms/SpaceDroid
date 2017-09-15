package com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.Lasers;

import android.graphics.Rect;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo1;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo2;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo3;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.templates.DroidObjectEntity;
import com.webstorms.framework.Game;
import com.webstorms.framework.gameObject.WorldObject;
import com.webstorms.physics.Circle;

public class LaserBullet extends AmmoBase {

	private static final int RADIUS = 10;

	public LaserBullet(Game game, WorldObject world, DroidObjectEntity droid) {
		super(game, world, droid);
		this.getShape().addRectangle(new Rect(0, 0, RADIUS * 2, RADIUS * 2));
		this.getShape().addCircle(new Circle(RADIUS, RADIUS, RADIUS));
		
		if(this.getAmmoType().equals(Ammo1.CLASS_TAG)) {
			this.getGame().getGameLog().d(this, "Ammo1.CLASS_TAG");
			this.setBitmap(Assets.playScreen_RedLaser);
			
		}
		else if(this.getAmmoType().equals(Ammo2.CLASS_TAG)) {
			this.getGame().getGameLog().d(this, "Ammo2.CLASS_TAG");
			this.setBitmap(Assets.playScreen_BlueLaser);
			
		}
		else if(this.getAmmoType().equals(Ammo3.CLASS_TAG)) {
			this.getGame().getGameLog().d(this, "Ammo3.CLASS_TAG");
			this.setBitmap(Assets.playScreen_GreenLaser);
			
		}
		
	}
	
	
	
	
}
