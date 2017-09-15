package com.skywalkerstudios.spacedroid.screens.storescreen;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.merchandise.MerchandiseViewable;
import com.webstorms.framework.Game;

public class Spaceships extends MerchandiseViewable {

	public static final String CLASS_TAG = Spaceships.class.getSimpleName();
	
	public Spaceships(Game game) {
		super(game);
		this.setMerchandiseItemName("Spacehships");
		this.setMerchandiseItemPicture(Assets.merchandise_Cover3);
		
	}

	
}
