package com.skywalkerstudios.spacedroid.merchandise.spaceships;

import com.skywalkerstudios.spacedroid.Assets;
import com.webstorms.framework.Game;

public class Spaceship3 extends Spaceship {
	
	public static final String CLASS_TAG = Spaceship3.class.getSimpleName();
	
	public Spaceship3(Game game) {
		super(game);
		this.setMerchandiseItemName("Titanix");
		this.setMerchandiseItemPicture(Assets.merchandise_Spaceship3);
		this.setCostOfCredits(40000);
		this.setCostOfHe3(4000);
		this.setRequiredLevel(40);
		
		this.setAmountOfGunSlots(10);
		this.setAmountOfHealth(100);
		
	}
	
	
}
