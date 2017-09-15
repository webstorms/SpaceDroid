package com.skywalkerstudios.spacedroid.merchandise.spaceships;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.util.Log;

import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.merchandise.MerchandiseItem;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo;
import com.skywalkerstudios.spacedroid.merchandise.guns.Gun;
import com.webstorms.framework.Game;

public class Spaceship extends MerchandiseItem {
	
	private Gun[] gunSlots;
	private Ammo ammo;
	private int amountOfGunSlots;
	private int amountOfHealth;
	private int currentAmountOfAmmo;
	
	protected Spaceship(Game game) {
		super(game);
		
	}
	
	public void sortGunsAccordingToDamage() {
		List<Gun> copyOfGunSlotsArray = new ArrayList<Gun>(Arrays.asList(this.gunSlots));
		
		Collections.sort(copyOfGunSlotsArray, new Comparator<Gun>() {
			
			@Override 
			public int compare(Gun a, Gun b) {	
				if(a == null && b == null) {
					return 0;
					
				}
				else if(a == null && b != null) {
					return 1;
					
				}
				else if(a != null && b == null) {
					return -1;
					
				}
				else if(a.getRank() == b.getRank()){
					return 0;
					
				}
				else if(a.getRank() > b.getRank()) {
					return 1;
					
				}
				else {
					return -1;
					
				}
				
			}
			
		});
		
		this.gunSlots = copyOfGunSlotsArray.toArray(new Gun[copyOfGunSlotsArray.size()]);
				
	}
	
	public void setAmmo(Ammo ammo) {
		this.ammo = ammo;
		
	}
	
	public Ammo getAmmo() {
		return this.ammo;
		
	}
	
	public void setAmountOfHealth(int n) {
		this.amountOfHealth = n;
		
	}
	
	public int getAmountOfHealth() {
		return this.amountOfHealth;
		
	}
	
	public void setAmountOfGunSlots(int n) {
		this.amountOfGunSlots = n;
		gunSlots = new Gun[this.amountOfGunSlots];
		
	}
	
	public boolean hasSellingItems() {	
		boolean isSpaceshipFreeSpaceship = this.getClassTag().equals(Spaceship1.class.getSimpleName());
		boolean containsGuns = (this.getAmountOfGunSlotsInUse() != 0);
		
		return (isSpaceshipFreeSpaceship == false || containsGuns == true);
		
	}
	
	public int getAmountOfGunSlots() {
		return this.amountOfGunSlots;
		
	}
	
	public int getAmountOfGunSlotsInUse() {
		// Calculate the number of used slots
		int numberOfUsedSlots = 0;
		for(int i = 0; i < gunSlots.length; i++) {
			if(gunSlots[i] != null) {
				numberOfUsedSlots++;
			}
		}
		
		return numberOfUsedSlots;
		
	}
	
	public Gun[] getAllGunSlots() {
		return this.gunSlots;
		
	}
	
	public Gun[] getAllGunsPlacedInShip() {
		// Calculate the number of used slots
		int numberOfUsedSlots = 0;
		for(int i = 0; i < gunSlots.length; i++) {
			if(gunSlots[i] != null) {
				numberOfUsedSlots++;
			}
		}
		
		Gun[] onlyPlacedInShipGuns = new Gun[numberOfUsedSlots];
		
		// Initialze onlyPlacedInShipGuns with the guns of this spaceship
		for(int i = 0; i < gunSlots.length; i++) {
			if(gunSlots[i] != null) {
				onlyPlacedInShipGuns[i] = gunSlots[i];
			}
			
		}
		
		return onlyPlacedInShipGuns;
		
	}
	
	public boolean isSpaceForNewGun() {
		return this.amountOfGunSlots != getAllGunsPlacedInShip().length;
				
	}
	
	public boolean isSpaceForMoreAmmo(Game game) {
		if(((SpaceDroid) game).getWorld() != null) {
			return ((SpaceDroid) game).getWorld().getPlayerDroid().getGun().getCurrentAmmo() != ((SpaceDroid) game).getWorld().getPlayerDroid().getGun().getMagazineSize();
			
		}
		else {
			return false;
			
		}
		
	}
	
	public void removeGunFromShip(String type) {
		
		// Remove the gun from the gunslots
		boolean removedGun = false;
		
		for(int i = (this.amountOfGunSlots - 1); i > (0 - 1); i--) {
			
			Log.d("SpaceDroid", "Iteration: " + i);
			
			if(this.gunSlots[i] != null && removedGun == false) {
				if(type.equals(this.gunSlots[i].getClassTag())) {
					this.gunSlots[i] = null;
					removedGun = true;
					
				}
				
			}
			
		}
		
	}
	
	public boolean placeGunIntoShip(Gun newGun) {
		// Calculate the number of used slots
		int numberOfUsedSlots = 0;
		for(int i = 0; i < gunSlots.length; i++) {
			if(gunSlots[i] != null) {
				numberOfUsedSlots++;
			}
		}
		
		if(numberOfUsedSlots != gunSlots.length) {
			// Look for the nearest free spot
			boolean foundNearestFreeSpot = false;
			int index = 0;
			while(foundNearestFreeSpot == false) {
				if(gunSlots[index] == null) {
					// Place gun into ship
					foundNearestFreeSpot = true;
					gunSlots[index] = newGun;
				}
				else {
					index++;
				}
			}
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public int getShipShootingFrequency() {
		int totalFrequency = 0;
		for(int i = 0; i < this.getAllGunsPlacedInShip().length; i++) {
			totalFrequency += this.getAllGunsPlacedInShip()[i].getAmountOfFrequency();
			
		}
		
		if(this.getAllGunsPlacedInShip().length != 0) {
			return totalFrequency/this.getAllGunsPlacedInShip().length; // Average frequency
			
		}
		else {
			return 0;
			
		}
		
	}
	
	public int getShipShootingDamage() {
		int totalDamage = this.getNetDamage();
		
		// Additional damage: Ammmo
		totalDamage += this.ammo.getAmountOfDamageHP();
		
		return totalDamage; // Total damage
		
	}
	
	public int getNetDamage() {
		int totalDamage = 0;
		// From all guns
		for(int i = 0; i < this.getAllGunsPlacedInShip().length; i++) {
			totalDamage += this.getAllGunsPlacedInShip()[i].getAmountOfDamage();
			
		}
		
		return totalDamage;
		
	}
	
	public int getShipShootingVelocity() {
		int totalVelocity = 0;
		for(int i = 0; i < this.getAllGunsPlacedInShip().length; i++) {
			totalVelocity += this.getAllGunsPlacedInShip()[i].getAmountOfVelocity();
		}
		
		if(this.getAllGunsPlacedInShip().length != 0) {
			return totalVelocity/this.getAllGunsPlacedInShip().length; // Average velocity
		}
		else {
			return 0;
		}
		
	}
	
	public int getShipMaxAmountOfAmmo() {
		int totalAmmo = 0;
		for(int i = 0; i < this.getAllGunsPlacedInShip().length; i++) {
			totalAmmo += this.getAllGunsPlacedInShip()[i].getAmountOfAmmo();
		}
		
		return totalAmmo; // Total ammo
		
	}
	
	public int getCurrentAmountOfAmmo() {
		return this.currentAmountOfAmmo;
		
	}
	
	public void setCurrentAmountOfAmmo(int currentAmountOfAmmo) {
		this.currentAmountOfAmmo = currentAmountOfAmmo;
		
	}
	
	// For Hangar Screen
	
	public String getHealth() {
		return this.amountOfHealth + " Hp";
		
	}
	
	public String getAmountOfAmmo() {
		return this.getShipMaxAmountOfAmmo() + " U";
		
	}
	
	public String getFrequency() {
		if(this.getShipShootingFrequency() != 0) {
			return ((int) 1000/this.getShipShootingFrequency()) + " Hz";
			
		}
		else {
			return 0 + " Hz";
			
		}
		
	}
	
	public String getDamage() {
		return String.valueOf(this.getShipShootingDamage()) + " P";
		
	}
	
	public String getVelocity() {
		return this.getShipShootingVelocity() + " V";
		
	}
	
	
}