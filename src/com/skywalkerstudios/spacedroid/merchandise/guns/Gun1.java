package com.skywalkerstudios.spacedroid.merchandise.guns;

import com.skywalkerstudios.spacedroid.Assets;
import com.webstorms.framework.Game;

public class Gun1 extends Gun {
	
	public static final String CLASS_TAG = Gun1.class.getSimpleName();
	
	public Gun1(Game game) {
		super(game);
		this.setRank(3);
		this.setMerchandiseItemName("FL Laser X");
		this.setMerchandiseItemPicture(Assets.merchandise_Gun1);
		this.setCostOfCredits(3000);
		this.setCostOfHe3(100);
		this.setRequiredLevel(5);
		
		this.setAmountOfAmmo(30);
		this.setAmountOfDamage(40);
		this.setAmountOfFrequency((int) (1000/3));
		this.setAmountOfVelocity(10);
		
	}
	
	
}
