package com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.Lasers;

import android.graphics.Rect;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.templates.DroidObjectEntity;
import com.webstorms.framework.Game;
import com.webstorms.framework.gameObject.WorldObject;
import com.webstorms.framework.graphics.animation.SpriteSheetAnimation;

public class Missile extends AmmoBase {
	
	private SpriteSheetAnimation missleAnimation;
	
	// Next: Sort out dimensions of Missle
	private float initialVelocity;
	
	public Missile(Game game, WorldObject world, DroidObjectEntity droid) {
		super(game, world, droid);
		this.getShape().addRectangle(new Rect(0, 0, 30, 50)); // Define constraints
		this.missleAnimation = new SpriteSheetAnimation(this.getGame(), Assets.playScreen_MissleAnimation, this.getShape().getRect(), 1, 17);
		this.missleAnimation.repeatAnimation(true);
	//	this.getMovementCoordinator().setAcceleration(10);
		
	}
	
	@Override
	public void setVelocity(float velocity) {
		super.setVelocity(velocity);
		initialVelocity = velocity;
		
	}
	
	@Override
	public void setLocation(int newX, int newY) {
		super.setLocation(newX, newY);
		this.missleAnimation.setLocation(this.getX(), this.getY());
		
	}
	
	@Override
	public void update() {
		super.update();
		this.missleAnimation.setLocation(this.getX(), this.getY());
		this.missleAnimation.update();

	}

	@Override
	public void renderBitmap() {
		this.missleAnimation.renderBitmap();
		
	}

	@Override
	public void dispose() {
		super.dispose();
		this.missleAnimation.dispose();
		
	}

	@Override
	public void reset() {
		super.reset();
		this.getMovementCoordinator().setVelocity(initialVelocity);
		
	}
	
	
}
