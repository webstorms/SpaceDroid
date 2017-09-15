package com.skywalkerstudios.spacedroid.merchandise;

import com.webstorms.framework.Game;
import com.webstorms.framework.WSObject;

import android.graphics.Bitmap;

public class MerchandiseViewable extends WSObject {
	
	private String merchandiseItemName;
	private Bitmap merchandiseItemPicture;
	
	public MerchandiseViewable(Game game) {
		super(game);
		
	}
	
	// Setter
	
	public void setMerchandiseItemName(String merchandiseItemName) {
		this.merchandiseItemName = merchandiseItemName;
		
	}
	
	public void setMerchandiseItemPicture(Bitmap merchandiseItemPicture) {
		this.merchandiseItemPicture = merchandiseItemPicture;
		
	}
	
	// Getter
	
	public String getMerchandiseItemName() {
		return this.merchandiseItemName;
		
	}
	
	public Bitmap getMerchandiseItemPicture() {
		return this.merchandiseItemPicture;
		
	}
	
	
}
