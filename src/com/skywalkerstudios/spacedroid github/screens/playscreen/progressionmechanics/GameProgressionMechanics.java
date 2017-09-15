package com.skywalkerstudios.spacedroid.screens.playscreen.progressionmechanics;

import com.skywalkerstudios.spacedroid.screens.playscreen.World;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.templates.GameObjectEntity;
import com.webstorms.framework.util.Timer;

public class GameProgressionMechanics {
	
	private World world;
	private final int minSpawnTime = 100;
	private final int initialMaxSpawnTime = 2000;
	private final int finalMaxSpawnTime = 500;
	
	private int amountOfLevelVisibleEnemies;
	private int amountOfLevelSlowStrongDroids;
	private int amountOfLevelFastWeakDroids;
	private int amountOfLevelAsteroids;
	private int amountOfLevelBosses;
	
	private boolean startOfNewWave;
	private int currentWave;
	
	private int lastUpdateWaveMedium;
	private int lastUpdateWaveHard;
	
	Timer newWaveDelay = new Timer(2000);
	
	public GameProgressionMechanics(World world) {
		this.world = world;
		
		// Initialize progression mechanics variables of the different game enemy entities 
		this.initializeAsteroidMechanics();
		this.initializeSlowStrongDroidMechanics();
		this.initializeFastWeakDroidMechanics();
		this.initializeBossMechanics();
		
		currentWave = 1;
		lastUpdateWaveMedium = 0;
		lastUpdateWaveHard = 0;
		this.amountOfLevelVisibleEnemies = 5; // Put back to 8
		this.amountOfLevelAsteroids = 10; // Put back to 10
		this.amountOfLevelSlowStrongDroids = 0; // 0
		this.amountOfLevelFastWeakDroids = 0; // 0
		this.amountOfLevelBosses = 1; // 0
		
		// Init Generater timer
		this.world.getEnemyGenerating().setMin(this.minSpawnTime);
		this.world.getEnemyGenerating().setMax(this.initialMaxSpawnTime);
		
	}
	
	public void initializeAsteroidMechanics() {
		EnemyProgressionVariables.Asteroid.setVelocity(4);
		EnemyProgressionVariables.Asteroid.setHealth(100);
		
	}
	
	public void initializeSlowStrongDroidMechanics() {
		EnemyProgressionVariables.SlowStrongDroid.setHealth(100);
		EnemyProgressionVariables.SlowStrongDroid.setVelocity(3);
		EnemyProgressionVariables.SlowStrongDroid.setLaserFrequency(0.5f); // = 1 every second (1000 miliseconds)
		EnemyProgressionVariables.SlowStrongDroid.setLaserDamage(20);
		EnemyProgressionVariables.SlowStrongDroid.setLaserVelocity(2);
		
	}
	
	public void initializeFastWeakDroidMechanics() {
		EnemyProgressionVariables.FastWeakDroid.setHealth(100);
		EnemyProgressionVariables.FastWeakDroid.setVelocity(4);
		EnemyProgressionVariables.FastWeakDroid.setLaserFrequency(1f); // = 1 every second (1000 miliseconds)
		EnemyProgressionVariables.FastWeakDroid.setLaserDamage(10);
		EnemyProgressionVariables.FastWeakDroid.setLaserVelocity(3);
		
	}
	
	public void initializeBossMechanics() {
		EnemyProgressionVariables.Boss.setHealth(500);
		EnemyProgressionVariables.Boss.setVelocity(3.5f);
		EnemyProgressionVariables.Boss.setLaserFrequency(0.7f); // = 1 every second (1000 miliseconds)
		EnemyProgressionVariables.Boss.setLaserDamage(15);
		EnemyProgressionVariables.Boss.setLaserVelocity(5);
		EnemyProgressionVariables.Boss.setLaserAcceleration(10);
		
	}
	
	public void increaseAsteroidMechanics() {
		EnemyProgressionVariables.Asteroid.increaseVelocity(0.3f);
		EnemyProgressionVariables.Asteroid.increaseHealth(50);
		
	}
	
	public void increaseSlowStrongDroidMechanics() {
		EnemyProgressionVariables.SlowStrongDroid.increaseVelocity(0.20f);
		EnemyProgressionVariables.SlowStrongDroid.increaseHealth(75);
		EnemyProgressionVariables.SlowStrongDroid.increaseLaserDamage(2);
		EnemyProgressionVariables.SlowStrongDroid.increaseLaserFrequency(0.1f);
		EnemyProgressionVariables.SlowStrongDroid.increaseLaserVelocity(0.2f);
		
	}
	
	public void increaseFastWeakDroidMechanics() {
		EnemyProgressionVariables.FastWeakDroid.increaseVelocity(0.20f);
		EnemyProgressionVariables.FastWeakDroid.increaseHealth(50);
		EnemyProgressionVariables.FastWeakDroid.increaseLaserDamage(1);
		EnemyProgressionVariables.FastWeakDroid.increaseLaserFrequency(0.2f);
		EnemyProgressionVariables.FastWeakDroid.increaseLaserVelocity(0.3f);
		
	}
	
	public void increaseBossMechanics() {
		EnemyProgressionVariables.Boss.increaseVelocity(0.5f);
		EnemyProgressionVariables.Boss.increaseHealth(100);
		EnemyProgressionVariables.Boss.increaseLaserDamage(1);
	//	EnemyProgressionVariables.Boss.increaseLaserFrequency(0.4f);
	//	EnemyProgressionVariables.Boss.increaseLaserVelocity(0.7f);
		
	}
	
	public void generateNewWave() {
		currentWave = currentWave + 1;
		
		// Check if a highscore has been beaten
		if(this.currentWave > world.getGame().getUserProfile().getUserBestScore()) {
			world.getGame().getUserProfile().setUserBestScore(this.currentWave);
			
		}
		
		// Mild progression: Update individual bad guys abilities
		this.increaseAsteroidMechanics();
		this.increaseBossMechanics();
		
		if(currentWave == 4 || currentWave > 4) {
			this.increaseSlowStrongDroidMechanics();
			
		}
		if(currentWave == 8 || currentWave > 8) {
			this.increaseFastWeakDroidMechanics();
		}
		
		// Gradually reduce the generating time, increasing the generating frequency
	//	if(this.world.getEnemyGenerating().getMin() != this.world.getEnemyGenerating().getMax() && this.world.getEnemyGenerating().getMin() < this.world.getEnemyGenerating().getMax()) {
	//		this.world.getEnemyGenerating().decreaseGeneratingDelay(1000);
	//		if(this.world.getEnemyGenerating().getMin() == this.world.getEnemyGenerating().getMax() || this.world.getEnemyGenerating().getMin() > this.world.getEnemyGenerating().getMax()) {
	//			this.world.getEnemyGenerating().setMax(1000);
			
	//		}
			
	//	}
		
		if(this.world.getEnemyGenerating().getMax() > this.finalMaxSpawnTime) {
			this.world.getEnemyGenerating().decreaseGeneratingDelay(1000);
			
		}
		if(this.world.getEnemyGenerating().getMax() != this.finalMaxSpawnTime) {
			this.world.getEnemyGenerating().setMax(this.finalMaxSpawnTime);
			
		}
		
		
		this.world.getGame().getGameLog().d(this, "Min: " + world.getEnemyGenerating().getMin());
		this.world.getGame().getGameLog().d(this, "Max: " + world.getEnemyGenerating().getMax());
		
		// Medium proression: Update the amount of players
		if(currentWave == 2) {
			this.amountOfLevelSlowStrongDroids = 1;
			
		}
		if(currentWave == 3) {
			this.amountOfLevelFastWeakDroids = 1;
			
		}
		
		if(lastUpdateWaveMedium + 3 == currentWave) {
			lastUpdateWaveMedium = currentWave;
			
			this.amountOfLevelVisibleEnemies += 1;
			this.amountOfLevelAsteroids += 1;
			
			if(currentWave == 2 || currentWave > 2) {
				this.amountOfLevelSlowStrongDroids += 1;
				
			}
			
			if(currentWave == 3 || currentWave > 3) {
				this.amountOfLevelFastWeakDroids += 1;
				
			}
			
			world.holderEnemies = new GameObjectEntity[this.amountOfLevelVisibleEnemies];
			
			// world.setAmountOfActiveStupidBadDroids(1);
			
		}
		
		// Hard progression: Add new enemy type to game
		if(lastUpdateWaveHard + 10 == currentWave) {
			lastUpdateWaveHard = currentWave;
			
		}
		
		/*
		// Map out the amount of StupidBadDroids
		if(this.amountOfLevelVisibleStupidBadDroids != world.getAmountOfStupidBadDroidsNextToEachOther()) {
			this.amountOfLevelVisibleStupidBadDroids = this.amountOfLevelVisibleStupidBadDroids + 1;
		}
		
		world.setAmountOfActiveStupidBadDroids(this.amountOfLevelVisibleStupidBadDroids);
		*/
		
	//	for(int i = 0; i < this.amountOfLevelVisibleStupidBadDroids; i++) {
	//		world.generateNewStupidBadDroid(i);
	//	}
		
		// world.setAmountOfActiveStupidBadDroids(this.amountOfLevelVisibleStupidBadDroids);
		
	}
	
	// Setter methods
	
	public void setStartOfNewWave(boolean state) {
		this.startOfNewWave = state;
	}
	
	// Getter methods
	
	public int getAmountOfLevelEnemies() {
		return this.amountOfLevelSlowStrongDroids + this.amountOfLevelFastWeakDroids + this.amountOfLevelAsteroids + this.amountOfLevelBosses;
	}
	
	public int getAmountOfLevelSlowStrongDroids() {
		return this.amountOfLevelSlowStrongDroids;
	}
	
	public int getAmountOfLevelFastWeakDroids() {
		return this.amountOfLevelFastWeakDroids;
	}
	
	public int getAmountOfLevelAsteroids() {
		return this.amountOfLevelAsteroids;
	}
	
	public int getAmountOfLevelBosses() {
		return this.amountOfLevelBosses;
	}
	
	public int getAmountOfLevelVisibleEnemies() {
		return this.amountOfLevelVisibleEnemies;
	}
	
	public int getCurrentWave() {
		return this.currentWave;
	}
	
	public boolean isStartOfNewWave() {
		return this.startOfNewWave;
	}
	
	
}
