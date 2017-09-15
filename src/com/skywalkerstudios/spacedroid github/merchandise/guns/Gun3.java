package com.skywalkerstudios.spacedroid.merchandise.guns;

import com.skywalkerstudios.spacedroid.Assets;
import com.webstorms.framework.Game;

public class Gun3 extends Gun {
	
	public static final String CLASS_TAG = Gun3.class.getSimpleName();
	
	public Gun3(Game game) {
		super(game);
		this.setRank(1);
		this.setMerchandiseItemName("FL Laser Z");
		this.setMerchandiseItemPicture(Assets.merchandise_Gun3);
		this.setCostOfCredits(100000);
		this.setCostOfHe3(2000);
		this.setRequiredLevel(50);
		
		this.setAmountOfAmmo(75);
		this.setAmountOfDamage(110);
		this.setAmountOfFrequency((int) (1000/5));
		this.setAmountOfVelocity(15);
		
	}
	
}
