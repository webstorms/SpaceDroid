package com.skywalkerstudios.spacedroid.screens.playscreen;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.PlayerDroid;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.enemies.Asteroid;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.enemies.Boss;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.enemies.FastWeakDroid;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.enemies.SlowStrongDroid;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.templates.DroidObjectEntity;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.templates.GameObjectEntity;
import com.skywalkerstudios.spacedroid.screens.playscreen.progressionmechanics.GameProgressionMechanics;
import com.webstorms.framework.gameObject.WorldObject;
import com.webstorms.framework.util.Timer;
import com.webstorms.framework.util.Util;

public class World extends WorldObject {
	
	private int amountOfVanishedSlowStrongDroids;
	private int amountOfVanishedFastWeakDroids;
	private int amountOfVanishedAsteroids;
	private int amountOfVanishedBosses;
	
	private int amountOfActiveSlowStrongDroids;
	private int amountOfActiveFastWeakDroids;
	private int amountOfActiveAsteroids;
	private int amountOfActiveBosses;
	
	public PlayerDroid playerDroid; // Only have one, hence no array
	public GameObjectEntity[] holderEnemies;
	
	private boolean gameOver;
	
	EnemyGeneratingMachine enemyGenerating;
	GameProgressionMechanics progressionMechanics;
	Stars stars;
	PowerUps powerUps;
	
	Timer newWaveDelay = new Timer(2000);
	Paint newWavePaint = new Paint();
	// DELTE THIS:
	Paint white = new Paint();
	
	// User collected values
	int amountOfXP;
	int amountOfCredits;
	int amountOfHe3;
	
	public World(SpaceDroid game) {
		super(game);
		
		// Initialize the game
		enemyGenerating = new EnemyGeneratingMachine();
		progressionMechanics = new GameProgressionMechanics(this);
		
		stars = new Stars(this.getGame(), this, 30);
		powerUps = new PowerUps(this.getGame(), this);
		
		this.progressionMechanics.setStartOfNewWave(true);
		this.amountOfVanishedSlowStrongDroids = 0;
		// this.amountOfActiveStupidBadDroids = 1;
		
		playerDroid = new PlayerDroid(this.getGame(), this, null);
		this.attachGameObject(playerDroid);
		
		holderEnemies = new GameObjectEntity[progressionMechanics.getAmountOfLevelVisibleEnemies()];
		
		newWavePaint.setColor(Color.rgb(215, 215, 205));
		newWavePaint.setTypeface(Assets.standardScreen_Birdman);
		newWavePaint.setTextSize(62);
		
		// DELETE THIS:
		white.setColor(Color.WHITE);
		
	}
	
	public void nukeWave() {
		amountOfVanishedSlowStrongDroids = this.progressionMechanics.getAmountOfLevelSlowStrongDroids();
		amountOfVanishedFastWeakDroids = this.progressionMechanics.getAmountOfLevelFastWeakDroids();
		amountOfVanishedAsteroids = this.progressionMechanics.getAmountOfLevelAsteroids();
		amountOfVanishedBosses = this.progressionMechanics.getAmountOfLevelBosses();
		
		for(int a = 0; a < this.getHolderEnemies().length; a++) {
			if(this.getHolderEnemies()[a] != null) {
				this.getHolderEnemies()[a].setHealth(0);

				if(this.getHolderEnemies()[a] instanceof SlowStrongDroid) {
					amountOfVanishedSlowStrongDroids--;

				}
				else if(this.getHolderEnemies()[a] instanceof FastWeakDroid) {
					amountOfVanishedFastWeakDroids--;

				}
				else if(this.getHolderEnemies()[a] instanceof Asteroid) {
					amountOfVanishedAsteroids--;

				}
				else if(this.getHolderEnemies()[a] instanceof Boss) {
					amountOfVanishedBosses--;

				}


			}

		}

		
	}
	
	@Override
	public SpaceDroid getGame() {
		return (SpaceDroid) super.getGame();
		
	}
	
	@Override
	public void resume() {
		super.resume();
		this.getGame().getGameLog().d(this, "Resuming world!");
		Timer.resumeAllTimers();
		this.resumeAllAudio();
		
	}
	
	@Override
	public void pause() {
		super.pause();
		this.getGame().getGameLog().d(this, "Pausing world!");
		Timer.pauseAllTimers();
		this.pauseAllAudio();
		
	}

	public void resetAllAudio() {
		// Start/Restart Music
		Assets.playScreen_PlayMusic.restart();

		// Stop all Sounds
		Assets.playScreen_DamageTaken.stop();
		Assets.playScreen_Explosion1.stop();
		Assets.playScreen_Explosion2.stop();
		Assets.playScreen_GameOver.stop();
		Assets.playScreen_GunShoot1.stop();
		Assets.playScreen_GunShoot2.stop();
		Assets.playScreen_GunShoot3.stop();
		Assets.playScreen_LaserBeamSound.stop();
		Assets.playScreen_MissleLaunch.stop();
		Assets.playScreen_PowerUpSound.stop();

	}
	
	private void resumeAllAudio() {
		Assets.playScreen_PlayMusic.loop(true);
		Assets.playScreen_PlayMusic.play();

		Assets.playScreen_DamageTaken.resume();
		Assets.playScreen_Explosion1.resume();
		Assets.playScreen_Explosion2.resume();
		Assets.playScreen_GameOver.resume();
		Assets.playScreen_GunShoot1.resume();
		Assets.playScreen_GunShoot2.resume();
		Assets.playScreen_GunShoot3.resume();
		Assets.playScreen_LaserBeamSound.resume();
		Assets.playScreen_MissleLaunch.resume();
		Assets.playScreen_PowerUpSound.resume();
		
	}
	
	private void pauseAllAudio() {
		Assets.playScreen_PlayMusic.pause();
		
		Assets.playScreen_DamageTaken.pause();
		Assets.playScreen_Explosion1.pause();
		Assets.playScreen_Explosion2.pause();
		Assets.playScreen_GameOver.pause();
		Assets.playScreen_GunShoot1.pause();
		Assets.playScreen_GunShoot2.pause();
		Assets.playScreen_GunShoot3.pause();
		Assets.playScreen_LaserBeamSound.pause();
		Assets.playScreen_MissleLaunch.pause();
		Assets.playScreen_PowerUpSound.pause();
		
		// this.getGame().getAudio().pauseAllSounds();
		
	}

	public void increaseUserValues(int incrementXP, int incrementCredits, int incrementHe3) {
		
		int multiplier = 1;
		
	//	if(this.powerUps.getCurrentPowerUp() == PowerUps.TIMES_TWO) {
	//		multiplier = 2;
			
	//	}
		
		if(this.powerUps.isOneOfTheTypesActive(PowerUps.TIMES_TWO)) {
			multiplier = 2;
			
		}
		
		// Local copy; just as feedback for the user at end of game
		amountOfXP += incrementXP * multiplier;
		amountOfCredits += incrementCredits * multiplier;
		amountOfHe3 += incrementHe3 * multiplier;
		
		// Actually influence
		this.getGame().getUserProfile().setUserXP(this.getGame().getUserProfile().getUserXP() + incrementXP);
		this.getGame().getUserProfile().setAmountOfCredits(this.getGame().getUserProfile().getAmountOfCredits() + incrementCredits);
		this.getGame().getUserProfile().setAmountOfHe3(this.getGame().getUserProfile().getAmountOfHe3() +incrementHe3);
		
	}
	
	@Override
	public void update() {
		if(newWaveDelay.delay() == true) {
			if(this.progressionMechanics.isStartOfNewWave()) {
				this.progressionMechanics.setStartOfNewWave(false);
				
			}
			
		}
		
		// First state: Game Over
		// Second state: Game Running
		if(!this.gameOver) {
			// First state: Wait for new wave
			// Second state: Fight new wave
			if(this.progressionMechanics.isStartOfNewWave() == false) {
				// Wave manager updates the wave: Takes care of registering deaths
				updateWave();
				// Update the StupidBadDroids
				updateStupidBadDroids();
				
				// Check if the game is over
				if(playerDroid.isDead()) {
					this.gameOver = true;
						
				}
				
				// Check if wave has been completed
				if((this.amountOfVanishedSlowStrongDroids + this.amountOfVanishedFastWeakDroids + this.amountOfVanishedAsteroids + this.amountOfVanishedBosses) == progressionMechanics.getAmountOfLevelEnemies()) {
					
					for(int i = 0; i < this.holderEnemies.length; i++) {
						if(holderEnemies[i] == null) {
							this.getGame().getGameLog().d(this, "Enemy: " + i + " is null");
						}
						else {
							this.getGame().getGameLog().d(this, "Enemy: " + i + " is not null");
						}
						
						
					}
					
					this.getGame().getGameLog().d(this, "=== Generating level: " + (this.progressionMechanics.getCurrentWave() + 1) + " ===");
					progressionMechanics.generateNewWave();
					this.progressionMechanics.setStartOfNewWave(true);
					newWaveDelay.reset();
					this.amountOfVanishedSlowStrongDroids = 0;
					this.amountOfVanishedFastWeakDroids = 0;
					this.amountOfVanishedAsteroids = 0;
					this.amountOfVanishedBosses = 0;
					
					// Increase Playerdroid ammo
				//	if(this.playerDroid.getGun().getCurrentAmmo() + 10 > this.game.getUserProfile().getSpaceship().getShipAmountOfAmmo()) {
				//		this.playerDroid.getGun().setAmmo(this.game.getUserProfile().getSpaceship().getShipAmountOfAmmo());
						
				//	}
				//	else {
				//		this.playerDroid.getGun().setAmmo(this.playerDroid.getGun().getCurrentAmmo() + 10);
						
				//	}
					
					this.getGame().getGameLog().d(this, "=== Generated level: " + (this.progressionMechanics.getCurrentWave()) + " ===");
					
					
				}
				
			}
			
		}
		
		// Things that should update throughout the whole update state
		updatePlayer();
		stars.update();
		powerUps.update();
		
	}
	
	public void updateWave() {
		for(int i = 0; i < this.progressionMechanics.getAmountOfLevelVisibleEnemies(); i++) {
			if(holderEnemies[i] != null && (holderEnemies[i].isDead() || holderEnemies[i].hasTravelledOffScreen())) {
				
				boolean disposeGameEntity = true;
				
				if(holderEnemies[i] instanceof DroidObjectEntity) {
					if(((DroidObjectEntity) holderEnemies[i]).getGun().isShooting() == true) {
						disposeGameEntity = false;
						
					}
					
				}
				
				if(disposeGameEntity && !holderEnemies[i].hasTheWorldRegisteredTheDeath()) {
					
				//	// Check if Enemy was disposed by means of PlayerDroid
				//	if(this.holderEnemies[i].getHealth() == 0) {
				//		// Try and create a new PowerUp
				//		if(this.getPowerUps().shouldGenerateNewPowerUp() == true) {
				//			this.getPowerUps().generateNewPowerUp(holderEnemies[i].getMovementCoordinator().getX(), holderEnemies[i].getMovementCoordinator().getY());
							
				//		}
						
				//	}
					
					// Do all the wave logic
					holderEnemies[i].setTheWorldHasRegisteredTheDeath();
					
					if(holderEnemies[i].getClass().getSimpleName().equals(SlowStrongDroid.class.getSimpleName())) {
						this.amountOfVanishedSlowStrongDroids += 1;
						this.amountOfActiveSlowStrongDroids -= 1;
						
					}
					else if(holderEnemies[i].getClass().getSimpleName().equals(FastWeakDroid.class.getSimpleName())) {
						this.amountOfVanishedFastWeakDroids += 1;
						this.amountOfActiveFastWeakDroids -= 1;
							
					}
					else if(holderEnemies[i].getClass().getSimpleName().equals(Asteroid.class.getSimpleName())) {
						this.amountOfVanishedAsteroids += 1;
						this.amountOfActiveAsteroids -= 1;
							
					}
					else if(holderEnemies[i].getClass().getSimpleName().equals(Boss.class.getSimpleName())) {
						this.amountOfVanishedBosses += 1;
						this.amountOfActiveBosses -= 1;
				//		this.getPowerUps().generateNewPowerUp(holderEnemies[i].getMovementCoordinator().getX(), holderEnemies[i].getMovementCoordinator().getY());
							
					}
					
					this.detachGameObject(holderEnemies[i]);
					holderEnemies[i] = null;
					
					this.getGame().getGameLog().d(this, "Vanished amount of " + SlowStrongDroid.class.getSimpleName() + ": " + this.amountOfVanishedSlowStrongDroids);
					this.getGame().getGameLog().d(this, "Vanished amount of " + FastWeakDroid.class.getSimpleName() + ": " + this.amountOfVanishedFastWeakDroids);
					this.getGame().getGameLog().d(this, "Vanished amount of " + Asteroid.class.getSimpleName() + ": " + this.amountOfVanishedAsteroids);
					// game.getGameLog().d(classTAG, "Death registered. Disposed enemy");
					// game.getGameLog().d(classTAG, "amountOfVanishedStupidBadDroids: " + amountOfVanishedSlowStrongDroids);
					// game.getGameLog().d(classTAG, "amountOfActiveStupidBadDroids: " + amountOfActiveSlowStrongDroids);
					
					}
				
				}
		
		}
		
		// Check if a new enemy should be spawned
		enemyGenerating.update();
		
	}
	
	public int getAmountOfGeneratedEnemies() {
		return ((amountOfVanishedSlowStrongDroids + amountOfActiveSlowStrongDroids) + (amountOfVanishedFastWeakDroids + amountOfActiveFastWeakDroids) + (amountOfVanishedAsteroids + amountOfActiveAsteroids) + (amountOfVanishedBosses + amountOfActiveBosses));
		
	}
	
	public Integer getAmountOfGeneratedEnemy(String enemyType) {
		
		if(enemyType.equals(Asteroid.class.getSimpleName())) {
			return (amountOfVanishedAsteroids + amountOfActiveAsteroids);
			
		}
		else if(enemyType.equals(SlowStrongDroid.class.getSimpleName())) {
			return (amountOfVanishedSlowStrongDroids + amountOfActiveSlowStrongDroids);
			
		}
		else if(enemyType.equals(FastWeakDroid.class.getSimpleName())) {
			return (amountOfVanishedFastWeakDroids + amountOfActiveFastWeakDroids);
			
		}
		else if(enemyType.equals(Boss.class.getSimpleName())) {
			return (amountOfVanishedBosses + amountOfActiveBosses);
			
		}
		else {
			return null; // Enemy Type doesn't exist
		}
		
	}
	
	public void render() {
		this.getGame().getGraphics().drawBitmap(Assets.playScreen_Background, null, this.getGame().getWSScreen().getGameScreenDimensions(), null);
		stars.render();
		powerUps.render();
		// DELETE THIS:
		// drawShapes();
		
		// First state: Game Over
		// Second state: Game Running
	//	if(progressionMechanics.isGameOver() != true) {
			// First state: Wait for new wave
			if(this.progressionMechanics.isStartOfNewWave() == true) {
				this.getGame().getGraphics().drawText("Wave " + progressionMechanics.getCurrentWave(), Util.centerObject((int) this.newWavePaint.measureText("Wave " + progressionMechanics.getCurrentWave()), 0, this.getGame().getWSScreen().getGameScreenWidth()), 305, this.newWavePaint);
				
			}
			// Second state: Fight new wave
			else {
				// Render the StupidBadDroids
				for(int i = 0; i < progressionMechanics.getAmountOfLevelVisibleEnemies(); i++) {
					if(holderEnemies[i] != null) { // use to compare like this != null // && holderEnemies[i].isDead() == false || holderEnemies[i].getGun().isShooting() == true
						holderEnemies[i].render();
					}
					
				//	holderEnemies[i].renderGameObjectBitmap();
					
				}
			}
					
	//	}
	
		// Things that should update throughout the whole game running state
		playerDroid.render();
	//	if(this.powerUps.getCurrentPowerUp() != null) {
	//		this.powerUps.renderAnimatedText();
			
	//	}
		
		if(this.powerUps.getActivePowerUps().size() != 0) {
			this.powerUps.renderAnimatedText();
			
		}
		
		// DELETE THIS:
		// drawGrid();
		
	}
	
	public void updatePlayer() {
		// Update the PlayerDroid, this method will iterate through all the ones we have. We have one, because it's normal mode.
		if(playerDroid.isDead() == false) {
			playerDroid.update();
			
		}
			
	}
	
	public void updateStupidBadDroids() {
		for(int i = 0; i < progressionMechanics.getAmountOfLevelVisibleEnemies(); i++) {
			if(holderEnemies[i] != null) { //  && holderEnemies[i].isDead() == false || holderEnemies[i].getGun().isShooting() == true
				holderEnemies[i].update(); 
				
			}
			
		}
	}
	
	// Setter methods
	
	public int getAmountOfXP() {
		return this.amountOfXP;
	}
	
	public int getAmountOfCredits() {
		return this.amountOfCredits;
	}
	
	public int getAmountOfHe3() {
		return this.amountOfHe3;
	}
	
	public int setAmountOfActiveStupidBadDroids(int n) { // Needed for GameProgressionMechanics class
		return this.amountOfActiveSlowStrongDroids = n;
	}
	
	// Getter methods
	
	public boolean isGameOver() {
		return this.gameOver;
	}
	
	public PowerUps getPowerUps() { // For all enemy classes
		return this.powerUps;
		
	}
	
	public GameObjectEntity[] getHolderEnemies() { // Needed for PlayerDroid class
		return this.holderEnemies;
	}
	
	public PlayerDroid getPlayerDroid() { // Needed for StuidBadDroid class
		return this.playerDroid;
	}
	
	public GameProgressionMechanics getProgressionMechanics() { // Needed for SpaceDroid class
		return this.progressionMechanics;
	}
	
	public EnemyGeneratingMachine getEnemyGenerating() {
		return this.enemyGenerating;
	}
	
	public class EnemyGeneratingMachine {
		
		private Timer generateDelayTime = new Timer();
		private int min;
		private int max;
		
		public void setMin(int i) {
			this.min = i;
		}
		
		public void setMax(int i) {
			this.max = i;
		}
		
		public void decreaseGeneratingDelay(int i) {
			this.max -= i;
		}
		
		public int getMin() {
			return this.min;
		}
		
		public int getMax() {
			return this.max;
		}
		
		public void pause() {
			this.generateDelayTime.pause();
			
		}
		
		public void resume() {
			this.generateDelayTime.reset();
			
		}
		
		public void update() {
			// Time based generation
			if(generateDelayTime.delay() == true) {
			//	game.getGameLog().d(classTAG, "Min: " + min);
			//	game.getGameLog().d(classTAG, "Max: " + max);
				generateDelayTime.setDelayTime(Util.getRandomNumberFrom(min, max)); // Reset the random generate time
				generateDelayTime.reset();
				
				for(int i = 0; i < getProgressionMechanics().getAmountOfLevelVisibleEnemies(); i ++) {
					if(getHolderEnemies()[i] == null ) { //|| getHolderEnemies()[i].isDead() == true
						if(getAmountOfGeneratedEnemies() < getProgressionMechanics().getAmountOfLevelEnemies()) {
							enemyGenerating.generateEnemy(i);
							break;
									
						}
						
					}
					
				}
				
			}
			
		}
		
		public void generateEnemy(int index) {
				
			// Pick a random enemy form all the available ones
			final String randomFreeEnemy = generateRandomFreeEnemy();
			
			// Set a random location for that enemy 
			final int[] location;
					
			// Spawn the enemy
			if(randomFreeEnemy.equals(Asteroid.class.getSimpleName())) {
				getGame().getGameLog().d(this, "Generating new enemy Asteroid");
				amountOfActiveAsteroids += 1; // Update world variable
				//	holderEnemies[index] = new Boss(game.world, GameObjectTemplate.TYPE_DYNAMIC);
				holderEnemies[index] = new Asteroid(getGame(), (World) ((SpaceDroid) getGame()).getWorld(), null);
				
			}
			else if(randomFreeEnemy.equals(SlowStrongDroid.class.getSimpleName())) {
				getGame().getGameLog().d(this, "Generating new enemy SlowStrongDroid");
				amountOfActiveSlowStrongDroids += 1; // Update world variable
				holderEnemies[index] = new SlowStrongDroid(getGame(), (World) ((SpaceDroid) getGame()).getWorld(), null);
				
			}
			else if(randomFreeEnemy.equals(FastWeakDroid.class.getSimpleName())) {
				getGame().getGameLog().d(this, "Generating new enemy FastWeakDroid");
				amountOfActiveFastWeakDroids += 1; // Update world variable
				holderEnemies[index] = new FastWeakDroid(getGame(), (World) ((SpaceDroid) getGame()).getWorld(), null);
				
			}
			else if(randomFreeEnemy.equals(Boss.class.getSimpleName())) {
				getGame().getGameLog().d(this, "Generating new enemy Boss");
				amountOfActiveBosses += 1; // Update world variable
				holderEnemies[index] = new Boss(getGame(), (World) ((SpaceDroid) getGame()).getWorld(), null);
				
			}
			
			if(holderEnemies[index] != null) {
				location = generateRandomLocation(holderEnemies[index].getShape().getWidth(), holderEnemies[index].getShape().getWidth());
				holderEnemies[index].setLocation(location[0], location[1]);
				attachGameObject(holderEnemies[index]);
				
			}
			
		}
		
		private String generateRandomFreeEnemy() {
			
			List<String> availableEnemies = new ArrayList<String>();
			
		//	game.getGameLog().d(classTAG, "amountOfVanishedSlowStrongDroids: " + amountOfVanishedSlowStrongDroids);
		//	game.getGameLog().d(classTAG, "getAmountOfLevelSlowStrongDroids(): " + progressionMechanics.getAmountOfLevelSlowStrongDroids());
			
			// Add available enemies to the list
			if(getAmountOfGeneratedEnemy(SlowStrongDroid.class.getSimpleName()) != progressionMechanics.getAmountOfLevelSlowStrongDroids() && amountOfActiveSlowStrongDroids == 0) {
				availableEnemies.add(SlowStrongDroid.class.getSimpleName());
				
			}
			
		//	game.getGameLog().d(classTAG, "amountOfVanishedFastWeakDroids: " + amountOfVanishedFastWeakDroids);
		//	game.getGameLog().d(classTAG, "getAmountOfLevelFastWeakDroids(): " + progressionMechanics.getAmountOfLevelFastWeakDroids());
			
			if(getAmountOfGeneratedEnemy(FastWeakDroid.class.getSimpleName()) != progressionMechanics.getAmountOfLevelFastWeakDroids() && amountOfActiveFastWeakDroids == 0) {
				availableEnemies.add(FastWeakDroid.class.getSimpleName());
				
			}
			
		//	game.getGameLog().d(classTAG, "amountOfVanishedAsteroids: " + amountOfVanishedAsteroids);
		//	game.getGameLog().d(classTAG, "getAmountOfLevelAsteroids(): " + progressionMechanics.getAmountOfLevelAsteroids());
			
			if(getAmountOfGeneratedEnemy(Asteroid.class.getSimpleName()) != progressionMechanics.getAmountOfLevelAsteroids()) {
				availableEnemies.add(Asteroid.class.getSimpleName());
				
			}
			
			if(availableEnemies.size() == 0 && getAmountOfGeneratedEnemy(Boss.class.getSimpleName()) != progressionMechanics.getAmountOfLevelBosses()) {
				availableEnemies.add(Boss.class.getSimpleName());
				
			}
			
			// Pick one of the available to enemies to spawn
			if(availableEnemies.size() == 0) {
				return "";
				
			}
			else {
				return availableEnemies.get(Util.getRandomNumberFrom(0, (availableEnemies.size() - 1)));
				
			}
			
		}
		
		private int[] generateRandomLocation(int width, int height) {
			
			int[] location = new int[2];
			
			// Generate X
			location[0] = Util.getRandomNumberFrom(0, 480 - width);
			
			// Generate Y
			location[1] = - height;
			//location[1] = 50; // DELETE THIS!
			
			return location;
			
		}
		
		
	}
	
	
}
