package com.skywalkerstudios.spacedroid.merchandise.guns;

import com.skywalkerstudios.spacedroid.Assets;
import com.webstorms.framework.Game;

public class Gun2 extends Gun {
	
	public static final String CLASS_TAG = Gun2.class.getSimpleName();
	
	public Gun2(Game game) {
		super(game);
		this.setRank(2);
		this.setMerchandiseItemName("FL Laser ii");
		this.setMerchandiseItemPicture(Assets.merchandise_Gun2);
		this.setCostOfCredits(10000);
		this.setCostOfHe3(750);
		this.setRequiredLevel(25);
		
		this.setAmountOfAmmo(50);
		this.setAmountOfDamage(80);
		this.setAmountOfFrequency((int) (1000/4));
		this.setAmountOfVelocity(12);
		
	}
	
	
}
