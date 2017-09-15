package com.skywalkerstudios.spacedroid.merchandise.ammo;

import com.skywalkerstudios.spacedroid.Assets;
import com.webstorms.framework.Game;

public class Ammo3 extends Ammo {

	public static final String CLASS_TAG = Ammo3.class.getSimpleName();
	
	public Ammo3(Game game) {
		super(game);
		this.setRank(1);
		this.setMerchandiseItemName("Green Ammo");
		this.setMerchandiseItemPicture(Assets.merchandise_Ammo3);
		this.setRequiredLevel(7);
		// Resultant: Thrice the damage
		this.setAmountOfDamagePercentage(200);
		
	}
	
	
}
