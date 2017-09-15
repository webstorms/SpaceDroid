package com.skywalkerstudios.spacedroid.screens.playscreen;

import com.webstorms.framework.util.Timer;

public class ActivePowerUp {

	Integer activePowerUp;
	Timer lifeSpanOfActivePowerUp = new Timer(15*1000); // 10 Seconds
	
	public ActivePowerUp(Integer type) {
		this.activePowerUp = type;
		
	}
	
	public Integer getActivePowerUp() {
		return this.activePowerUp;
		
	}
	
	public void dispose() {
		this.lifeSpanOfActivePowerUp.dispose();
		
	}
	
	public boolean shouldBeDisposed() {
		return lifeSpanOfActivePowerUp.delay();
		
	}
	
	
}
