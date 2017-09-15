package com.skywalkerstudios.spacedroid.merchandise.ammo;

import com.skywalkerstudios.spacedroid.Assets;
import com.webstorms.framework.Game;

public class Ammo1 extends Ammo {

	public static final String CLASS_TAG = Ammo1.class.getSimpleName();
	
	public Ammo1(Game game) {
		super(game);
		this.setRank(3);
		this.setMerchandiseItemName("Red Ammo");
		this.setMerchandiseItemPicture(Assets.merchandise_Ammo1);
		this.setRequiredLevel(0);
		// Doesn't add any additional damage to the Spaceship
		this.setAmountOfDamagePercentage(0);
		
	}
	
	
}
