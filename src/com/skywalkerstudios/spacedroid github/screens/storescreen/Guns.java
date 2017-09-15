package com.skywalkerstudios.spacedroid.screens.storescreen;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.merchandise.MerchandiseViewable;
import com.webstorms.framework.Game;

public class Guns extends MerchandiseViewable {

	public static final String CLASS_TAG = Guns.class.getSimpleName();
	
	public Guns(Game game) {
		super(game);
		this.setMerchandiseItemName("Guns");
		this.setMerchandiseItemPicture(Assets.merchandise_Cover2);
		
	}
	

}
