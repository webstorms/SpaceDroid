package com.skywalkerstudios.spacedroid.merchandise.ammo;

import com.skywalkerstudios.spacedroid.Assets;
import com.webstorms.framework.Game;

public class Ammo2 extends Ammo {

	public static final String CLASS_TAG = Ammo2.class.getSimpleName();
	
	public Ammo2(Game game) {
		super(game);
		this.setRank(2);
		this.setMerchandiseItemName("Blue Ammo");
		this.setMerchandiseItemPicture(Assets.merchandise_Ammo2);
		this.setRequiredLevel(5);
		// Resultant: Twice the damage
		this.setAmountOfDamagePercentage(100);
		
	}
	
	
}
