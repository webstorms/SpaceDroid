package com.skywalkerstudios.spacedroid.merchandise.spaceships;

import com.skywalkerstudios.spacedroid.Assets;
import com.webstorms.framework.Game;

public class Spaceship1 extends Spaceship {
	
	public static final String CLASS_TAG = Spaceship1.class.getSimpleName();
	
	public Spaceship1(Game game) {
		super(game);
		this.setMerchandiseItemName("Titan");
		this.setMerchandiseItemPicture(Assets.merchandise_Spaceship1);
		this.setCostOfCredits(5500);
		this.setCostOfHe3(400);
		this.setRequiredLevel(5);
		
		this.setAmountOfGunSlots(1);
		this.setAmountOfHealth(100);
		
	}
	
	
}
