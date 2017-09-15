package com.skywalkerstudios.spacedroid.merchandise.spaceships;

import com.skywalkerstudios.spacedroid.Assets;
import com.webstorms.framework.Game;

public class Spaceship2 extends Spaceship {
	
	public static final String CLASS_TAG = Spaceship2.class.getSimpleName();
	
	public Spaceship2(Game game) {
		super(game);
		this.setMerchandiseItemName("Shuttle Maria");
		this.setMerchandiseItemPicture(Assets.merchandise_Spaceship2);
		this.setCostOfCredits(30000);
		this.setCostOfHe3(800);
		this.setRequiredLevel(10);
		
		this.setAmountOfGunSlots(4);
		this.setAmountOfHealth(100);
		
	}
	
	
}
