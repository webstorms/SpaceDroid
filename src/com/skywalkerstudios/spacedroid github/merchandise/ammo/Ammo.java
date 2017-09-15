package com.skywalkerstudios.spacedroid.merchandise.ammo;

import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.merchandise.MerchandiseItem;
import com.webstorms.framework.Game;

public class Ammo extends MerchandiseItem {

	private int amountOfAmmo;
	private int amountOfDamagePercentage;
	private int amountOfDamageHP;
	private int rank;
	
	protected Ammo(Game game) {
		super(game);
		this.amountOfAmmo = ((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getShipMaxAmountOfAmmo();
		this.setCostOfCredits(8 * amountOfAmmo);
		this.setCostOfHe3(1 * amountOfAmmo);
		
	}
	
	@Override
	public int getCostOfCredits() {
		return super.getCostOfCredits() + super.getCostOfCredits() * (this.amountOfDamagePercentage/100);
		
	}
	
	@Override
	public int getCostOfHe3() {
		return super.getCostOfHe3() + super.getCostOfHe3() * (this.amountOfDamagePercentage/100);
		
	}
	
	public void setRank(int n) {
		this.rank = n;
		
	}
	
	public int getRank() {
		return this.rank;
		
	}
	
	// Setter
	
//	public void setAmountOfAmmo(int u) {
//		this.amountOfAmmo = u;
		
//	}
	
	public void setAmountOfDamagePercentage(int n) {
		this.amountOfDamagePercentage = n;
		this.amountOfDamageHP = ((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getNetDamage() * (this.amountOfDamagePercentage/100);
		
	}
	
	// Getter
	
	public String getType() {
		return this.getClassTag();
		
	}
	
	public int getAmountOfAmmo() {
		return this.amountOfAmmo;
		
	}
	
	public int getAmountOfDamagePercentage() {
		return this.amountOfDamagePercentage;

	}

	public int getAmountOfDamageHP() {
		return this.amountOfDamageHP;

	}
	
	
}