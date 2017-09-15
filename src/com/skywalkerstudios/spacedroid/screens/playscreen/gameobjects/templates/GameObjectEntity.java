package com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.templates;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.screens.playscreen.World;
import com.webstorms.framework.Game;
import com.webstorms.framework.util.Util;
import com.webstorms.framework.audio.Sound;
import com.webstorms.framework.gameObject.WorldObject;
import com.webstorms.framework.graphics.animation.SpriteSheetAnimation;
import com.webstorms.physics.Shape;

public abstract class GameObjectEntity extends com.webstorms.framework.gameObject.GameObject {
	
	private boolean hasTravelledOffScreen;
	private boolean isDead;
	private boolean isExploding;
	private int maxHealth;
	private int currentHealth;
	private boolean worldRegisteredTheDeath;
	
	private SpriteSheetAnimation explosionAnimation;
	private Sound explosionSound;
	
	protected Paint green;
	protected Paint red;
	
	protected GameObjectEntity(Game game, WorldObject world, Bitmap bitmap) {
		super(game, world);
		this.setBitmap(bitmap);
		this.initializeShape(this.getShape());
		this.explosionAnimation = new SpriteSheetAnimation(this.getGame(), Assets.playScreen_Explosion, new Rect(0, 0, 100, 100), 5, 5);
		
		green = new Paint();
		red = new Paint();
		green.setColor(Color.GREEN);
		red.setColor(Color.RED);
		
	}
	
	public abstract void initializeShape(Shape shape);
	
	public void setExplosionSound(Sound sound) {
		this.explosionSound = sound;
		
	}
	
	@Override
	public void checkCollisionDetection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SpaceDroid getGame() {
		return (SpaceDroid) super.getGame();
		
	}

	@Override
	public World getWorld() {
		return (World) super.getWorld();
		
	}

	public void setTravelledOffScreen(boolean state) {
		this.hasTravelledOffScreen = state;
		
	}
	
	public boolean hasTravelledOffScreen() {
		return this.hasTravelledOffScreen;
		
	}
	
	public void setDead() {
		this.isDead = true;
		
	}
	
	public boolean isDead() {
		return this.isDead;
		
	}
	
	public boolean isExploding() {
		return this.isExploding;
		
	}
	
	public void setTheWorldHasRegisteredTheDeath() {
		this.explosionAnimation.dispose();
		this.worldRegisteredTheDeath = true;
		
	}
	
	public boolean hasTheWorldRegisteredTheDeath() {
		return worldRegisteredTheDeath;
		
	}
	
	public float getHealth() {
		return this.currentHealth;
		
	}
	
	public void changeHealthBy(int delta) {
		Assets.playScreen_DamageTaken.play();
		this.setHealth(this.currentHealth + delta);
		
	}
	
	public void setHealth(int newHealth) {
		if(this.maxHealth == 0) {
			this.maxHealth = newHealth;
			
		}
		
		if(newHealth > 0) {
			this.currentHealth = newHealth;
			
		}
		else {
			this.currentHealth = 0;
			
		}

	}
	
	public void updateActiveGameObject() {
		if(this.currentHealth == 0) {
			this.isExploding = true;
			this.setCollidable(false);
			this.getMovementCoordinator().setVelocity(0);
			
		}
		
	}
	
	@Override
	public void update() {
		super.update();
		
		// First state: PlayerDroid is dead
		// Second state: PlayerDroid is alive
		if(this.isDead == false) {
			// First state: PlayerDroid is exploding yet isn't dead yet
			if(this.isExploding == true) {
				if(!this.explosionAnimation.hasBeenStarted()) {
					
					this.onExplosion();
					this.explosionSound.play();
					
					// Set location in this if statement, to avoid iteratingly setting the location
					
				}
				if(!this.explosionAnimation.isAnimationFinished()) {
					// Get middle point of StupidBadDroid sprite
					// Get the middle point of the explosion sprite
					int x = Util.centerObject(this.explosionAnimation.getShape().getWidth(), this.getMovementCoordinator().getX(), this.getMovementCoordinator().getX() + this.getShape().getWidth());
					int y = Util.centerObject(this.explosionAnimation.getShape().getHeight(), this.getMovementCoordinator().getY(), this.getMovementCoordinator().getY() + this.getShape().getHeight());
					this.explosionAnimation.setLocation(x, y);
					this.explosionAnimation.update();
					
				}
				else {
					this.isExploding = false;
					this.isDead = true;
					this.onDead();
					
				}
				
			}
			// Second state: PlayerDroid is actively moving about and doing its thing
			else {
				this.updateActiveGameObject();
					
			}
				
		}
		
	}

	public void onDead() {
		
	}
	
	public void onExplosion() {
		
	}
	
	@Override
	public void render() {
		// this.renderShapes(Color.YELLOW);
		// First state: StupidBadDroid is dead
		// Second state: StupidBadDroid is alive
		if(this.isDead == false) {
		//	this.renderGameObjectShapes();
			
			// First state: StupidBadDroid is exploding yet isn't dead yet
			if(this.isExploding == true) {
				if(this.explosionAnimation.hasBeenStarted() == true) {
					this.explosionAnimation.render();
					
				}
				
			}
			// Second state: StupidBadDroid is actively moving about and doing its thing
			else {
				
				this.renderBitmap();
				
				// Life of the game entity
				this.getGame().getGraphics().drawRect(this.getMovementCoordinator().getX(), this.getMovementCoordinator().getY() - 10, this.getMovementCoordinator().getX() + 50, this.getMovementCoordinator().getY() - 5, red);
				this.getGame().getGraphics().drawRect(this.getMovementCoordinator().getX(), this.getMovementCoordinator().getY() - 10, (this.getMovementCoordinator().getX() + (float) 50.0f * ((float) ((float)this.currentHealth/(float)this.maxHealth))), this.getMovementCoordinator().getY() - 5, green);
				
			}
							
		}
		
	}
	
	
}
