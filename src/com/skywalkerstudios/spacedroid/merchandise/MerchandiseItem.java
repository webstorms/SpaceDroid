package com.skywalkerstudios.spacedroid.merchandise;

import com.webstorms.framework.Game;

public class MerchandiseItem extends MerchandiseViewable {
	
	private int costOfCredits;
	private int costOfHe3;
	private int requiredLevel;
	
	protected MerchandiseItem(Game game) {
		super(game);
		
		
	}
	
	// Setter methods
	
	public void setRequiredLevel(int requiredLevel) {
		this.requiredLevel = requiredLevel;
		
	}
	
	public void setCostOfCredits(int i) {
		this.costOfCredits = i;
		
	}
	
	public void setCostOfHe3(int i) {
		this.costOfHe3 = i;
		
	}
	
	// Getter methods
	
	public int getRequiredLevel() {
		return this.requiredLevel;
		
	}
	
	public int getCostOfCredits() {
		return this.costOfCredits;
		
	}
	
	public int getCostOfHe3() {
		return this.costOfHe3;
		
	}
	
	
}
