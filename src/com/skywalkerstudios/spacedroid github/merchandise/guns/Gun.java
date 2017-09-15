package com.skywalkerstudios.spacedroid.merchandise.guns;

import com.skywalkerstudios.spacedroid.merchandise.MerchandiseItem;
import com.webstorms.framework.Game;

public class Gun extends MerchandiseItem {

	/*
	 * Remove those weird methods at the end eg. public String getAmountOfVelocitySS()
	 */
	
	// private boolean isInShip;
	private int ammo; // Ship
	private int frequency; // Guns averaged
	private int damage; // Guns added up
	private int velocity; // Guns averaged
	private int rank;
	
	protected Gun(Game game) {
		super(game);
		
	}
	
	public void setRank(int n) {
		this.rank = n;
		
	}
	
	public int getRank() {
		return this.rank;
		
	}
	
	// Setter method
	
	//public void putInShip() {
	//	isInShip = true;
		
	//}
	
	public void setAmountOfAmmo(int n) {
		this.ammo = n;
		
	}
	
	public void setAmountOfFrequency(int f) {
		this.frequency = f;
		
	}
	
	public void setAmountOfDamage(int p) {
		this.damage = p;
		
	}
	
	public void setAmountOfVelocity(int v) {
		this.velocity = v;
		
	}
	
	// Getter method
	
	//public boolean isInShip() {
	//	return isInShip;
		
	//}
	
	public int getAmountOfAmmo() {
		return this.ammo;
		
	}
	
	public int getAmountOfFrequency() {
		return this.frequency;
		
	}
	
	public int getAmountOfDamage() {
		return this.damage;
		
	}
	
	public int getAmountOfVelocity() {
		return this.velocity;
		
	}
	
	// For StoreScreen (SS = Store Screen)
	
	public String getAmountOfAmmoSS() {
		return String.valueOf(this.ammo) + " U";
		
	}
	
	public String getAmountOfFrequencySS() {
		return String.valueOf(1000/this.frequency) + " Hz";
		
	}
	
	public String getAmountOfDamageSS() {
		return String.valueOf(this.damage) + " P";
		
	}
	
	public String getAmountOfVelocitySS() {
		return String.valueOf(this.velocity) + " V";
		
	}
	
	
	
}
