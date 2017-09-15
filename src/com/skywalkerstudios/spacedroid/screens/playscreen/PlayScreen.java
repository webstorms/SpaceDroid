package com.skywalkerstudios.spacedroid.screens.playscreen;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.view.KeyEvent;

import com.flurry.android.FlurryAgent;
import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.FileNames;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo1;
import com.skywalkerstudios.spacedroid.screens.MenuScreen;
import com.skywalkerstudios.spacedroid.screens.storescreen.subscreens.AmmoScreen;
import com.webstorms.framework.Screen;
import com.webstorms.framework.graphics.animation.buttons.Button;
import com.webstorms.framework.graphics.animation.buttons.ButtonListener;
import com.webstorms.framework.graphics.animation.buttons.PhysicalButton;
import com.webstorms.framework.graphics.animation.buttons.VirtualButton;
import com.webstorms.framework.input.OnKeyListener;
import com.webstorms.framework.input.OnTouchListener;
import com.webstorms.framework.util.Executor;
import com.webstorms.framework.util.Timer;
import com.webstorms.physics.Shape;

public class PlayScreen extends Screen implements ButtonListener, OnTouchListener, OnKeyListener {
	
	/*
	 * When paused, make sure all active timers are paused. eg. The alser generating timer for the PlayerDroid!
	 * 
	 */
	
	// On Screen components
	public final VirtualButton pauseButton = new VirtualButton(this.getGame(), "Pause", this, 13, 18, new Rect(0, 0, 69, 69), Assets.playScreen_PauseButton, Assets.playScreen_PauseButtonClicked);
	public final VirtualButton getAmmoButton = new VirtualButton(this.getGame(), "GetAmmo", this, 101, 18, new Rect(0, 0, 151, 69), Assets.playScreen_GetAmmoButton, Assets.playScreen_GetAmmoButtonClicked);
	private final Shape lugerBulletShape = new Shape();
	private final Shape coinsShape = new Shape();
	private final Paint currentWave = new Paint();
	private final Paint currentAmmo = new Paint();
	private final Paint currentXP = new Paint();
	private final Paint currentCredits = new Paint();
	private final Paint currentHe3 = new Paint();
	private final Shape instantKillIconShape = new Shape();
	private final Shape timesTwoIconShape = new Shape();
	
	// Pause Dialog components
	private final VirtualButton resumeButton = new VirtualButton(this.getGame(), "Resume", this, 108, 320, new Rect(0, 0, 265, 72), Assets.playScreen_ResumeButton, Assets.playScreen_ResumeButtonClicked);
	private final VirtualButton restartButton = new VirtualButton(this.getGame(), "Restart", this, 108, 415, new Rect(0, 0, 265, 72), Assets.playScreen_RestartButton, Assets.playScreen_RestartButtonClicked);
	private final VirtualButton menuButton = new VirtualButton(this.getGame(), "Menu", this, 108, 511, new Rect(0, 0, 265, 72), Assets.playScreen_MenuButton, Assets.playScreen_MenuButtonClicked);
	private final PhysicalButton physicalBackButton = new PhysicalButton(this.getGame(), "Back", this, KeyEvent.KEYCODE_BACK);
	private final PhysicalButton physicalMenuButton = new PhysicalButton(this.getGame(), "Menu", this, KeyEvent.KEYCODE_MENU);
	
	// Pause Dialog components
	private final VirtualButton okayButton = new VirtualButton(this.getGame(), "Okay", this, 108, 511, new Rect(0, 0, 265, 72), Assets.standardScreen_OkayButton, Assets.standardScreen_OkayButtonClicked);
	
	// General Dialog components
	private final Shape dialogShape = new Shape();
	private final Paint dialogAlphaChanel = new Paint();
	private final Paint dialogTitle = new Paint();
	private final Paint dialogTextConstants = new Paint();
	private final Paint dialogTextValues = new Paint();
	
	private World world;
	private Executor onGameOver;
	
	private Timer instructionsTimer = new Timer(7 * 1000);
	private Shape instructionsShape = new Shape();
	
	public PlayScreen(SpaceDroid game, World world) {
		super(game);
		this.world = world;
		
	}
	
	@Override
	public void load() {
		FlurryAgent.onPageView();
		FlurryAgent.logEvent("PlayScreen has been launched");
		currentAmmo.setColor(Color.rgb(215, 215, 205));
		currentAmmo.setTypeface(Assets.standardScreen_Birdman);
		currentAmmo.setTextSize(31);
		
		currentWave.setColor(Color.rgb(215, 215, 205));
		currentWave.setTypeface(Assets.standardScreen_Birdman);
		currentWave.setTextSize(31);
		
		currentXP.setColor(Color.rgb(215, 215, 205));
		currentXP.setTypeface(Assets.standardScreen_Birdman);
		currentXP.setTextSize(31);
		currentXP.setTextAlign(Align.RIGHT);
		
		currentCredits.setColor(Color.rgb(215, 215, 205));
		currentCredits.setTypeface(Assets.standardScreen_Birdman);
		currentCredits.setTextSize(31);
		currentCredits.setTextAlign(Align.RIGHT);
		
		currentHe3.setColor(Color.rgb(215, 215, 205));
		currentHe3.setTypeface(Assets.standardScreen_Birdman);
		currentHe3.setTextSize(31);
		currentHe3.setTextAlign(Align.RIGHT);
		
		lugerBulletShape.addRectangle(new Rect(0, 0, 40, 19));
		lugerBulletShape.setLocation(210, 27);
		
		coinsShape.addRectangle(new Rect(0, 0, 26, 23));
		coinsShape.setLocation(441, 66);
		
		// pauseDialogShape.addRectangle(new Rect(0, 0, 427, 475));
		dialogShape.addRectangle(new Rect(0, 0, 427, 475));
		dialogShape.setLocation(29, 165);
		
		dialogAlphaChanel.setAlpha(225);
		
		dialogTitle.setColor(Color.rgb(215, 215, 205));
		dialogTitle.setTypeface(Assets.standardScreen_Birdman);
		dialogTitle.setTextSize(31);
		
		dialogTextConstants.setColor(Color.rgb(215, 215, 205));
		dialogTextConstants.setTypeface(Assets.standardScreen_Birdman);
		dialogTextConstants.setTextSize(29);
		dialogTextConstants.setTextAlign(Align.LEFT);
		
		dialogTextValues.setColor(Color.rgb(215, 215, 205));
		dialogTextValues.setTypeface(Assets.standardScreen_Birdman);
		dialogTextValues.setTextSize(29);
		dialogTextValues.setTextAlign(Align.RIGHT);
		
		instantKillIconShape.addRectangle(new Rect(0, 0, 65, 65));
		instantKillIconShape.setLocation(15, 730);
		
		timesTwoIconShape.addRectangle(new Rect(0, 0, 65, 65));
		timesTwoIconShape.setLocation(95, 730);
		
		onGameOver = new Executor(new Runnable() {

			@Override
			public void run() {
				getGame().getGameLog().d(this, "GAME'S OVER BITCH");
				world.pause();
				Assets.playScreen_GameOver.play();
			//	getGame().getOutput().vibrate(1000);
				
			}
			
		});
		
		instructionsShape.addRectangle(new Rect(0, 0, 389, 470));
		instructionsShape.setLocation(45, 182);
		
	}
	
	@Override
	public void resume() {
		this.getGame().getInput().useKeyboard(true);
		this.getGame().getInput().useTouchscreen(true);
		this.getGame().getInput().useAccelerometer(true);
		this.getGame().getOutput().useVibrator(true);
		
	//	this.world.resume();
		this.getGame().getInput().setOnTouchListener(this);
		this.getGame().getInput().setOnKeyListener(this);
		
		/*
		Assets.playScreen_PlayMusic.loop(true);
		Assets.playScreen_PlayMusic.play();
		
		this.getGame().getAudio().resumeAllSounds(); */
		
	}
	
	@Override
	public void pause() {
		super.pause();
		this.world.pause();
		((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().setCurrentAmountOfAmmo(this.world.getPlayerDroid().getGun().getCurrentAmmo());
		
		/*
		Assets.playScreen_PlayMusic.pause();
		
		this.getGame().getAudio().pauseAllSounds(); */
		
	}
	
	@Override
	public void onButtonClicked(Button button) {
		Assets.standardScreen_Click.play();
		
		if(!this.getGame().getIO().readPrimitiveInternalMemoryBoolean(FileNames.Welcome.HAS_SHOWN_PLAY_SCREEN_WELCOME)) {
			this.getGame().getIO().writePrimitiveInternalMemory(FileNames.Welcome.HAS_SHOWN_PLAY_SCREEN_WELCOME, true);
			
		}
		
		// Game Over
		if(world.isGameOver()) {
			if(button.equals(this.okayButton) || button.equals(this.physicalBackButton) || button.equals(this.physicalMenuButton)) {
				((SpaceDroid) this.getGame()).disposeWorld();
				((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().setAmmo(new Ammo1(this.getGame()));
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));

			}

		}	

		// Game Running
		else if(this.world.getState() == World.STATE_RESUMED) {
			if(button.equals(this.physicalBackButton) || button.equals(this.pauseButton)) {	
				world.pause();

			}
			else if(button.equals(this.getAmmoButton)) {
				world.pause();
				this.getGame().setScreen(new AmmoScreen((SpaceDroid) this.getGame()));
				
			}
			else if(button.equals(this.physicalMenuButton)) {
				// world.pause(); Gets paused when new screen gets set due to to the pause() method being called
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));

			}

		}

		// Game Paused
		else if(this.world.getState() == World.STATE_PAUSED) {
			if(button.equals(this.resumeButton) || button.equals(this.physicalBackButton)) {
				world.resume();

			}
			else if(button.equals(this.restartButton)) {
				this.world.resetAllAudio();
				this.world = ((SpaceDroid) this.getGame()).createNewWorld();
				
			}
			else if(button.equals(this.menuButton) || button.equals(this.physicalMenuButton)) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));

			}

		}
		
	}
	
	@Override
	public void update() {
		// Game Over
		if(this.world.isGameOver()) {
			onGameOver.execute();
			
		}
		// Game Running
		if(this.world.getState() == World.STATE_RESUMED) {
			if(!this.pauseButton.isPressed() && !this.getAmmoButton.isPressed() && !this.physicalBackButton.isPressed() && !this.physicalMenuButton.isPressed()) {
				world.update();

			}

		}

	}

	@Override
	public void render() {
		// Background: No condition, because the world will allways render, not to lose the background
		world.render();
		this.pauseButton.render();
		if(!this.world.getPlayerDroid().hasAmmo() && ((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getAmountOfGunSlotsInUse() != 0) {
			this.getAmmoButton.render();
			this.getGame().getGraphics().drawText(String.valueOf(world.getProgressionMechanics().getCurrentWave()), 13, 125, this.currentWave);
			
			if(!this.getGame().getIO().readPrimitiveInternalMemoryBoolean(FileNames.Welcome.HAS_SHOWN_PLAY_SCREEN_WELCOME)) {
				this.getGame().getGraphics().drawBitmap(Assets.playScreen_InstructionsArrow, null, this.instructionsShape.getRect(), null);
				
			}
			
		}
		else {
			String ammo = this.world.playerDroid.getGun().getCurrentAmmo() + "/" + ((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getShipMaxAmountOfAmmo();
			this.lugerBulletShape.setLocation((int) (111 + this.currentAmmo.measureText(ammo)), this.lugerBulletShape.getY());
			this.getGame().getGraphics().drawText(ammo, 101, 47, this.currentAmmo);
			this.getGame().getGraphics().drawBitmap(Assets.playScreen_9mmLugerBullet, null, this.lugerBulletShape.getRect(), null);
			this.getGame().getGraphics().drawText(String.valueOf(world.getProgressionMechanics().getCurrentWave()), 101, 86, this.currentWave);
			
		}
		
		
		this.getGame().getGraphics().drawBitmap(Assets.playScreen_Coins, null, this.coinsShape.getRect(), null);
		this.getGame().getGraphics().drawText(this.world.amountOfXP + " XP", 468, 47, this.currentXP);
		this.getGame().getGraphics().drawText(String.valueOf(this.world.amountOfCredits), 432, 86, this.currentCredits);
		this.getGame().getGraphics().drawText(this.world.amountOfHe3 + " He3", 468, 125, this.currentHe3);
		
		// Game Over
		if(this.world.isGameOver()) {
			renderGameOverDialog();
			
		}
		
		// Game Running 
		else if(this.world.getState() == World.STATE_RESUMED) {
			// Render Active Powerups
			if(this.world.getPowerUps().isOneOfTheTypesActive(PowerUps.INSTANT_KILL)) {
				this.getGame().getGraphics().drawBitmap(Assets.playScreen_InstantKillIcon, null, this.instantKillIconShape.getRect(), null);
				
			}
			if(this.world.getPowerUps().isOneOfTheTypesActive(PowerUps.TIMES_TWO)) {
				this.getGame().getGraphics().drawBitmap(Assets.playScreen_TimesTwoIcon, null, this.timesTwoIconShape.getRect(), null);
				
			}
			
		}
		
		// Game Paused
		else if(this.world.getState() == World.STATE_PAUSED) {
			renderPauseDialog();
			
		}
		
		// Instructions
		if(!this.getGame().getIO().readPrimitiveInternalMemoryBoolean(FileNames.Welcome.HAS_SHOWN_PLAY_SCREEN_WELCOME) && !this.instructionsTimer.delay()) {
			this.getGame().getGraphics().drawBitmap(Assets.playScreen_Instructions, null, this.instructionsShape.getRect(), null);
			
		}
		
	}
	
	public void renderGameOverDialog() {	
		this.getGame().getGraphics().drawBitmap(Assets.playScreen_Dialog, null, this.dialogShape.getRect(), dialogAlphaChanel);
		this.getGame().getGraphics().drawText("Game Over", 137, 261, this.dialogTitle);
		// Render Constants
		// + 132
		this.getGame().getGraphics().drawText("Wave: ", 108, 330, this.dialogTextConstants);
		this.getGame().getGraphics().drawText("XP: ", 108, 372, this.dialogTextConstants);
		this.getGame().getGraphics().drawText("Credits: ", 108, 414, this.dialogTextConstants);
		this.getGame().getGraphics().drawText("He3: ", 108, 456, this.dialogTextConstants);
		// Render values
		this.getGame().getGraphics().drawText(String.valueOf(world.getProgressionMechanics().getCurrentWave()), 373, 330, this.dialogTextValues);
		this.getGame().getGraphics().drawText(String.valueOf(world.getAmountOfXP()), 373, 372, this.dialogTextValues);
		this.getGame().getGraphics().drawText(String.valueOf(world.getAmountOfCredits()), 373, 414, this.dialogTextValues);
		this.getGame().getGraphics().drawText(String.valueOf(world.getAmountOfHe3()), 373, 456, this.dialogTextValues);
		
		this.okayButton.render();
		
	}
	
	public void renderPauseDialog() {		
		this.getGame().getGraphics().drawBitmap(Assets.playScreen_Dialog, null, this.dialogShape.getRect(), dialogAlphaChanel);
		this.getGame().getGraphics().drawText("Paused", 171, 261, this.dialogTitle);
			
		// Resume button
		this.resumeButton.render();
		
		// Restart button
		this.restartButton.render();
		
		// Menu button
		this.menuButton.render();
		
	}
	
	@Override
	public void onKey(Integer key) {
		this.physicalBackButton.update(key);
		this.physicalMenuButton.update(key);
		
	}

	@Override
	public void onTouch(Integer x, Integer y) {
		
		// Game Over
		if(this.world.isGameOver()) {
			this.okayButton.update(x, y);

		}
		// Game Running
		else if(this.world.getState() == World.STATE_RESUMED) {
			if(!this.world.getPlayerDroid().hasAmmo() && ((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getAmountOfGunSlotsInUse() != 0) {
				this.getAmmoButton.update(x, y);
				
			}
			this.pauseButton.update(x, y);
			
		}
		// Game Paused
		else if(this.world.getState() == World.STATE_PAUSED) {
			this.resumeButton.update(x, y);
			this.restartButton.update(x, y);
			this.menuButton.update(x, y);

		}
		
	}
	
	
}