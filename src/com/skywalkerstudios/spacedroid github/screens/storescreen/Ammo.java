package com.skywalkerstudios.spacedroid.screens.storescreen;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.merchandise.MerchandiseViewable;
import com.webstorms.framework.Game;

public class Ammo extends MerchandiseViewable {

	public static final String CLASS_TAG = Ammo.class.getSimpleName();
	
	public Ammo(Game game) {
		super(game);
		this.setMerchandiseItemName("Ammo");
		this.setMerchandiseItemPicture(Assets.merchandise_Cover1);
		
	}

}
