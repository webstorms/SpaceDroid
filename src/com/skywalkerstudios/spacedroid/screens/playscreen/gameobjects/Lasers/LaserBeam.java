package com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.Lasers;

import android.graphics.Rect;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.templates.DroidObjectEntity;
import com.webstorms.framework.Game;
import com.webstorms.framework.gameObject.WorldObject;
import com.webstorms.framework.util.Util;

public class LaserBeam extends AmmoBase {
	
	// Make sure of disposals
	
	private DroidObjectEntity droid;
	
	public LaserBeam(Game game, WorldObject world, DroidObjectEntity droid) {
		super(game, world, droid);
		this.droid = droid;
		this.getShape().addRectangle(new Rect(0, 0, 250, this.getWorld().getGame().getWSScreen().getGameScreenHeight() - this.droid.getShape().getHeight()/2));
		this.getShape().addRectangle(new Rect(0 + 83, 0, 83 + 83, this.getWorld().getGame().getWSScreen().getGameScreenHeight() - this.droid.getShape().getHeight()/2));
		
		Assets.laserBeamAnimation.getShape().getHolderPolygons().clear(); // Remove the dst Rect
		Assets.laserBeamAnimation.getShape().addRectangle(this.getShape().getRect()); // Add dst Rect
		
		Rect src = new Rect(0, 0, 480, this.getShape().getRect().height());
		src.offsetTo(0, this.getWorld().getGame().getWSScreen().getGameScreenHeight() - this.getShape().getHeight());
		Assets.laserBeamAnimation.setSrcRect(src);
		
		Assets.laserBeamAnimation.repeatAnimation(true);
		
	}
	
	@Override
	public void setLocation(int newX, int newY) {
		super.setLocation(newX, newY);
		Assets.laserBeamAnimation.setLocation(this.getX(), this.getY());
		
	}
	
	@Override
	public void update() {
		super.update();
		Assets.laserBeamAnimation.update();
		
	}

	@Override
	public void updateGameObjectPosition() {
		super.updateGameObjectPosition();
		this.getShape().setLocation(this.droid.getX() + Util.centerObject(this.getShape().getWidth(), 0, this.droid.getShape().getWidth()), 0);
		Assets.laserBeamAnimation.setLocation(this.getX(), this.getY());
		
	}
	
	@Override
	public void renderBitmap() {
		Assets.laserBeamAnimation.renderBitmap();
		
	}
	
	
}
