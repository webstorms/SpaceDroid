package com.skywalkerstudios.spacedroid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.webstorms.framework.AssetsBase;
import com.webstorms.framework.Game;
import com.webstorms.framework.audio.Music;
import com.webstorms.framework.audio.Sound;
import com.webstorms.framework.graphics.animation.SpriteSheetAnimation;

public class Assets extends AssetsBase {
	
	/*
	 * Some assets are in the wrong directory
	 * 
	 */
	
	public Assets(Game game) {
		super(game);
		
	}
	
	// Splash screen
	public static Bitmap splashScreen_Background;
	public static Bitmap splashScreen_AppLogo;
	public static Bitmap splashScreen_Animation;
	public static Sound splashScreen_GlintEffect;
	
	// Standard screen
	public static Bitmap standardScreen_NotNowButton;
	public static Bitmap standardScreen_NotNowButtonClicked;
	public static Bitmap standardScreen_LogOutButton;
	public static Bitmap standardScreen_LogOutButtonClicked;
	public static Bitmap standardScreen_ProgressionBar;
	public static Bitmap standardScreen_LargeDialog;
	public static Bitmap standardScreen_InfoButton;
	public static Bitmap standardScreen_InfoButtonClicked;
	public static Bitmap standardScreen_InfoButtonDisabled;
	public static Bitmap standardScreen_YesButton;
	public static Bitmap standardScreen_YesButtonClicked;
	public static Bitmap standardScreen_NoButton;
	public static Bitmap standardScreen_NoButtonClicked;
	public static Bitmap standardScreen_SlideBar;
	public static Bitmap standardScreen_BigViewBoard;
	public static Bitmap standardScreen_SmallViewBoard;
	public static Bitmap standardScreen_OkayButton;
	public static Bitmap standardScreen_OkayButtonClicked;
	public static Bitmap standardScreen_Background;
	public static Bitmap standardScreen_BackButton;
	public static Bitmap standardScreen_BackButtonClicked;
	public static Bitmap standardScreen_ForwardButton;
	public static Bitmap standardScreen_ForwardButtonClicked;
	public static Bitmap standardScreen_LoginButton;
	public static Bitmap standardScreen_LoginButtonClicked;
	public static Typeface standardScreen_Birdman;
	public static Sound standardScreen_Click;
	public static Sound standardScreen_Notification;
	public static Music standardScreen_StandardScreenMusic;
	
	// Menu screen
	public static Bitmap menuScreen_HangarButton;
	public static Bitmap menuScreen_HangarButtonClicked;
	public static Bitmap menuScreen_PlayButton;
	public static Bitmap menuScreen_PlayButtonClicked;
	public static Bitmap menuScreen_ScoresButton;
	public static Bitmap menuScreen_ScoresButtonClicked;
	public static Bitmap menuScreen_SettingsButton;
	public static Bitmap menuScreen_SettingsButtonClicked;
	public static Bitmap menuScreen_StoreButton;
	public static Bitmap menuScreen_StoreButtonClicked;
	
	// Settings screen
	public static Bitmap settingsScreen_MusicButton;
	public static Bitmap settingsScreen_MusicButtonClicked;
	public static Bitmap settingsScreen_ProfileNameButton;
	public static Bitmap settingsScreen_ProfileNameButtonClicked;
	public static Bitmap settingsScreen_SoundButton;
	public static Bitmap settingsScreen_SoundButtonClicked;
	public static Bitmap settingsScreen_TiltSensitivityButton;
	public static Bitmap settingsScreen_TiltSensitivityButtonClicked;
	public static Bitmap settingsScreen_VibrationButton;
	public static Bitmap settingsScreen_VibrationButtonClicked;
	public static Bitmap settingsScreen_MuteButton;
	public static Bitmap settingsScreen_MuteButtonClicked;
	
	// Play screen
	public static Bitmap playScreen_Dialog;
	public static Bitmap playScreen_Background;
	public static Bitmap playScreen_MenuButton;
	public static Bitmap playScreen_MenuButtonClicked;
	public static Bitmap playScreen_PauseButton;
	public static Bitmap playScreen_PauseButtonClicked;
	public static Bitmap playScreen_RestartButton;
	public static Bitmap playScreen_RestartButtonClicked;
	public static Bitmap playScreen_ResumeButton;
	public static Bitmap playScreen_ResumeButtonClicked;
	public static Bitmap playScreen_9mmLugerBullet;
	public static Bitmap playScreen_Coins;
	public static Bitmap playScreen_GetAmmoButton;
	public static Bitmap playScreen_GetAmmoButtonClicked;
	public static Bitmap playScreen_LaserBeamAnimation;
	public static Bitmap playScreen_MissleAnimation;
	public static Bitmap playScreen_PowerUp;
	public static Bitmap playScreen_Star1;
	public static Bitmap playScreen_Star2;
	public static Bitmap playScreen_Star3;
	public static Bitmap playScreen_Spaceship1;
	public static Bitmap playScreen_Spaceship2;
	public static Bitmap playScreen_Spaceship3;
	public static Bitmap playScreen_SlowStrongDroid;
	public static Bitmap playScreen_FastWeakDroid;
	public static Bitmap playScreen_Boss;
	public static Bitmap playScreen_Explosion;
	public static Bitmap playScreen_InstantKillIcon;
	public static Bitmap playScreen_TimesTwoIcon;
	public static Bitmap playScreen_PowerUpAnimation;
	public static Bitmap playScreen_RedLaser;
	public static Bitmap playScreen_BlueLaser;
	public static Bitmap playScreen_GreenLaser;
	public static Bitmap playScreen_Asteroid1Animation;
	public static Bitmap playScreen_Asteroid2Animation;
	public static Bitmap playScreen_Instructions;
	public static Bitmap playScreen_InstructionsArrow;
	public static SpriteSheetAnimation laserBeamAnimation;
	public static Sound playScreen_DamageTaken;
	public static Sound playScreen_Explosion1;
	public static Sound playScreen_Explosion2;
	public static Sound playScreen_GameOver;
	public static Sound playScreen_GunShoot1;
	public static Sound playScreen_GunShoot2;
	public static Sound playScreen_GunShoot3;
	public static Sound playScreen_LaserBeamSound;
	public static Sound playScreen_MissleLaunch;
	public static Sound playScreen_PowerUpSound;
	public static Music playScreen_PlayMusic;
	
	// Scores screen	
	public static Bitmap scoresScreen_LevelStar;
	public static Bitmap scoresScreen_PositionStar;
	public static Bitmap scoresScreen_BestLevelBoard;
	public static Bitmap scoresScreen_RefreshButton;
	public static Bitmap scoresScreen_RefreshButtonClicked;
	public static Bitmap scoresScreen_RefreshIcon;
	
	// Stores Screen
	public static Bitmap storesScreen_BuyButton;
	public static Bitmap storesScreen_BuyButtonClicked;
	public static Bitmap storesScreen_BuyButtonDisabled;
	public static Bitmap storesScreen_GetHe3Button;
	public static Bitmap storesScreen_GetHe3ButtonClicked;
	public static Bitmap storesScreen_GetHe3ButtonDisabled;
	public static Bitmap storesScreen_GetHe3ButtonLarge;
	public static Bitmap storesScreen_GetHe3ButtonLargeClicked;
	
	// Hangar Screen
	public static Bitmap hangarScreen_SellButton;
	public static Bitmap hangarScreen_SellButtonClicked;
	public static Bitmap hangarScreen_SellButtonDisabled;
	public static Bitmap hangarScreen_Board;
	public static Bitmap hangarScreen_EmptyGunSlot;
	
	// Merchandise
	public static Bitmap merchandise_Ammo1;
	public static Bitmap merchandise_Ammo2;
	public static Bitmap merchandise_Ammo3;
	public static Bitmap merchandise_Gun1;
	public static Bitmap merchandise_Gun2;
	public static Bitmap merchandise_Gun3;
	public static Bitmap merchandise_Spaceship1;
	public static Bitmap merchandise_Spaceship2;
	public static Bitmap merchandise_Spaceship3;
	public static Bitmap merchandise_Cover1;
	public static Bitmap merchandise_Cover2;
	public static Bitmap merchandise_Cover3;
	
	public List<Sound> getAllSounds() {
		List<Sound> allSounds = new ArrayList<Sound>();
		allSounds.add(Assets.splashScreen_GlintEffect);
		allSounds.add(Assets.standardScreen_Click);
		allSounds.add(Assets.standardScreen_Notification);
		allSounds.add(Assets.playScreen_DamageTaken);
		allSounds.add(Assets.playScreen_Explosion1);
		allSounds.add(Assets.playScreen_Explosion2);
		allSounds.add(Assets.playScreen_GameOver);
		allSounds.add(Assets.playScreen_GunShoot1);
		allSounds.add(Assets.playScreen_GunShoot2);
		allSounds.add(Assets.playScreen_GunShoot3);
		allSounds.add(Assets.playScreen_LaserBeamSound);
		allSounds.add(Assets.playScreen_MissleLaunch);
		allSounds.add(Assets.playScreen_PowerUpSound);
		
		return allSounds;
		
	}
	
	public List<Music> getAllMusic() {
		List<Music> allMusic = new ArrayList<Music>();
		allMusic.add(Assets.standardScreen_StandardScreenMusic);
		allMusic.add(Assets.playScreen_PlayMusic);
		
		return allMusic;
		
	}
	
	@Override
	public SpaceDroid getGame() {
		return (SpaceDroid) super.getGame();
		
	}

	@Override
	public void load() {
		this.loadStandardScreen();
		this.loadMenuScreen();
		this.loadSettingsScreen();
		this.loadPlayScreen();
		this.loadScoresScreen();
		this.loadStoresScreen();
		this.loadHangarScreen();
		this.loadMerchandise();
		
	}
	
	private void loadMerchandise() {
		try {
			Assets.merchandise_Ammo1 = this.getGame().getIO().readAssetsBitmap("Merchandise/Ammo1.png");
			Assets.merchandise_Ammo2 = this.getGame().getIO().readAssetsBitmap("Merchandise/Ammo2.png");
			Assets.merchandise_Ammo3 = this.getGame().getIO().readAssetsBitmap("Merchandise/Ammo3.png");
			Assets.merchandise_Gun1 = this.getGame().getIO().readAssetsBitmap("Merchandise/Gun1.png");
			Assets.merchandise_Gun2 = this.getGame().getIO().readAssetsBitmap("Merchandise/Gun2.png");
			Assets.merchandise_Gun3 = this.getGame().getIO().readAssetsBitmap("Merchandise/Gun3.png");
			Assets.merchandise_Spaceship1 = this.getGame().getIO().readAssetsBitmap("Merchandise/Spaceship1.png");
			Assets.merchandise_Spaceship2 = this.getGame().getIO().readAssetsBitmap("Merchandise/Spaceship2.png");
			Assets.merchandise_Spaceship3 = this.getGame().getIO().readAssetsBitmap("Merchandise/Spaceship3.png");
			Assets.merchandise_Cover1 = this.getGame().getIO().readAssetsBitmap("Merchandise/Cover1.png");
			Assets.merchandise_Cover2 = this.getGame().getIO().readAssetsBitmap("Merchandise/Cover2.png");
			Assets.merchandise_Cover3 = this.getGame().getIO().readAssetsBitmap("Merchandise/Cover3.png");
			
		} 
		catch (IOException e) {
			e.printStackTrace();
			
		}
		
	}
	
	private void loadStandardScreen() {
		try {
			Assets.standardScreen_ProgressionBar = this.getGame().getIO().readAssetsBitmap("StandardScreen/ProgressionBar.png");
			Assets.standardScreen_LogOutButtonClicked = this.getGame().getIO().readAssetsBitmap("StandardScreen/LogOutButtonClicked.png");
			Assets.standardScreen_LogOutButton = this.getGame().getIO().readAssetsBitmap("StandardScreen/LogOutButton.png");
			Assets.standardScreen_NotNowButtonClicked = this.getGame().getIO().readAssetsBitmap("StandardScreen/NotNowButtonClicked.png");
			Assets.standardScreen_NotNowButton = this.getGame().getIO().readAssetsBitmap("StandardScreen/NotNowButton.png");
			Assets.standardScreen_LargeDialog = this.getGame().getIO().readAssetsBitmap("StandardScreen/LargeDialog.png");
			Assets.standardScreen_InfoButton = this.getGame().getIO().readAssetsBitmap("StandardScreen/InfoButton.png");
			Assets.standardScreen_InfoButtonClicked = this.getGame().getIO().readAssetsBitmap("StandardScreen/InfoButtonClicked.png");
			Assets.standardScreen_InfoButtonDisabled = this.getGame().getIO().readAssetsBitmap("StandardScreen/InfoButtonDisabled.png");
			Assets.standardScreen_YesButton = this.getGame().getIO().readAssetsBitmap("StandardScreen/YesButton.png");
			Assets.standardScreen_YesButtonClicked = this.getGame().getIO().readAssetsBitmap("StandardScreen/YesButtonClicked.png");
			Assets.standardScreen_NoButton = this.getGame().getIO().readAssetsBitmap("StandardScreen/NoButton.png");
			Assets.standardScreen_NoButtonClicked = this.getGame().getIO().readAssetsBitmap("StandardScreen/NoButtonClicked.png");
			Assets.standardScreen_SlideBar = this.getGame().getIO().readAssetsBitmap("StandardScreen/SlideBar.png");
			Assets.standardScreen_BigViewBoard = this.getGame().getIO().readAssetsBitmap("StandardScreen/BigViewBoard.png");
			Assets.standardScreen_SmallViewBoard = this.getGame().getIO().readAssetsBitmap("StandardScreen/SmallViewBoard.png");
			Assets.standardScreen_OkayButton = this.getGame().getIO().readAssetsBitmap("StandardScreen/OkayButton.png");
			Assets.standardScreen_OkayButtonClicked = this.getGame().getIO().readAssetsBitmap("StandardScreen/OkayButtonClicked.png");
			Assets.standardScreen_Background = this.getGame().getIO().readAssetsBitmap("StandardScreen/Background.jpg");
			Assets.standardScreen_BackButton = this.getGame().getIO().readAssetsBitmap("StandardScreen/BackButton.png");
			Assets.standardScreen_ForwardButton = this.getGame().getIO().readAssetsBitmap("StandardScreen/ForwardButton.png");
			Assets.standardScreen_ForwardButtonClicked = this.getGame().getIO().readAssetsBitmap("StandardScreen/ForwardButtonClicked.png");
			Assets.standardScreen_BackButtonClicked = this.getGame().getIO().readAssetsBitmap("StandardScreen/BackButtonClicked.png");
			Assets.standardScreen_LoginButton = this.getGame().getIO().readAssetsBitmap("StandardScreen/LogInButton.png");
			Assets.standardScreen_LoginButtonClicked = this.getGame().getIO().readAssetsBitmap("StandardScreen/LogInButtonClicked.png");
			Assets.standardScreen_Birdman = this.getGame().getIO().readTypeface("StandardScreen/BIRDMAN_.TTF");
			Assets.standardScreen_Click = this.getGame().getAudio().newSound("StandardScreen/Click.ogg");
			Assets.standardScreen_Notification = this.getGame().getAudio().newSound("StandardScreen/Notification.ogg");
			Assets.standardScreen_StandardScreenMusic = this.getGame().getAudio().newMusic("StandardScreen/StandardScreenMusic.ogg");
			this.getGame().getGameLog().d(this, "Successfully read: StandardScreen");
			
		} 
		catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	private void loadMenuScreen() {
		try {
			Assets.menuScreen_HangarButton = this.getGame().getIO().readAssetsBitmap("MenuScreen/HangarButton.png");
			Assets.menuScreen_HangarButtonClicked = this.getGame().getIO().readAssetsBitmap("MenuScreen/HangarButtonClicked.png");
			Assets.menuScreen_PlayButton = this.getGame().getIO().readAssetsBitmap("MenuScreen/PlayButton.png");
			Assets.menuScreen_PlayButtonClicked = this.getGame().getIO().readAssetsBitmap("MenuScreen/PlayButtonClicked.png");
			Assets.menuScreen_ScoresButton = this.getGame().getIO().readAssetsBitmap("MenuScreen/ScoresButton.png");
			Assets.menuScreen_ScoresButtonClicked = this.getGame().getIO().readAssetsBitmap("MenuScreen/ScoresButtonClicked.png");
			Assets.menuScreen_SettingsButton = this.getGame().getIO().readAssetsBitmap("MenuScreen/SettingsButton.png");
			Assets.menuScreen_SettingsButtonClicked = this.getGame().getIO().readAssetsBitmap("MenuScreen/SettingsButtonClicked.png");
			Assets.menuScreen_StoreButton = this.getGame().getIO().readAssetsBitmap("MenuScreen/StoreButton.png");
			Assets.menuScreen_StoreButtonClicked = this.getGame().getIO().readAssetsBitmap("MenuScreen/StoreButtonClicked.png");
			this.getGame().getGameLog().d(this, "Successfully read: MenuScreen");
			
		} 
		catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	private void loadSettingsScreen() {
		try {
			Assets.settingsScreen_MusicButton = this.getGame().getIO().readAssetsBitmap("SettingsScreen/MusicButton.png");
			Assets.settingsScreen_MusicButtonClicked = this.getGame().getIO().readAssetsBitmap("SettingsScreen/MusicButtonClicked.png");
			Assets.settingsScreen_ProfileNameButton = this.getGame().getIO().readAssetsBitmap("SettingsScreen/ProfileNameButton.png");
			Assets.settingsScreen_ProfileNameButtonClicked = this.getGame().getIO().readAssetsBitmap("SettingsScreen/ProfileNameButtonClicked.png");
			Assets.settingsScreen_SoundButton = this.getGame().getIO().readAssetsBitmap("SettingsScreen/SoundButton.png");
			Assets.settingsScreen_SoundButtonClicked = this.getGame().getIO().readAssetsBitmap("SettingsScreen/SoundButtonClicked.png");
			Assets.settingsScreen_TiltSensitivityButton = this.getGame().getIO().readAssetsBitmap("SettingsScreen/TiltSensitivityButton.png");
			Assets.settingsScreen_TiltSensitivityButtonClicked = this.getGame().getIO().readAssetsBitmap("SettingsScreen/TiltSensitivityButtonClicked.png");
			Assets.settingsScreen_VibrationButton = this.getGame().getIO().readAssetsBitmap("SettingsScreen/VibrationButton.png");
			Assets.settingsScreen_VibrationButtonClicked = this.getGame().getIO().readAssetsBitmap("SettingsScreen/VibrationButtonClicked.png");
			Assets.settingsScreen_MuteButton = this.getGame().getIO().readAssetsBitmap("SettingsScreen/MuteButton.png");
			Assets.settingsScreen_MuteButtonClicked = this.getGame().getIO().readAssetsBitmap("SettingsScreen/MuteButtonClicked.png");
			this.getGame().getGameLog().d(this, "Successfully read: SettingsScreen");
			
		} 
		catch (IOException e) {
			e.printStackTrace();
			
		}
		
	}
		
	private void loadPlayScreen() {
		try {
			// Load Bitmaps
			Assets.playScreen_Dialog = this.getGame().getIO().readAssetsBitmap("PlayScreen/Dialog.png");
			Assets.playScreen_Background = this.getGame().getIO().readAssetsBitmap("PlayScreen/Background.jpg");
			Assets.playScreen_MenuButton = this.getGame().getIO().readAssetsBitmap("PlayScreen/MenuButton.png");
			Assets.playScreen_MenuButtonClicked = this.getGame().getIO().readAssetsBitmap("PlayScreen/MenuButtonClicked.png");
			Assets.playScreen_PauseButton = this.getGame().getIO().readAssetsBitmap("PlayScreen/PauseButton.png");
			Assets.playScreen_PauseButtonClicked = this.getGame().getIO().readAssetsBitmap("PlayScreen/PauseButtonClicked.png");
			Assets.playScreen_RestartButton = this.getGame().getIO().readAssetsBitmap("PlayScreen/RestartButton.png");
			Assets.playScreen_RestartButtonClicked = this.getGame().getIO().readAssetsBitmap("PlayScreen/RestartButtonClicked.png");
			Assets.playScreen_ResumeButton = this.getGame().getIO().readAssetsBitmap("PlayScreen/ResumeButton.png");
			Assets.playScreen_ResumeButtonClicked = this.getGame().getIO().readAssetsBitmap("PlayScreen/ResumeButtonClicked.png");
			Assets.playScreen_9mmLugerBullet = this.getGame().getIO().readAssetsBitmap("PlayScreen/9mmLugerBullet.png");
			Assets.playScreen_Coins = this.getGame().getIO().readAssetsBitmap("PlayScreen/Coins.png");
			Assets.playScreen_GetAmmoButton = this.getGame().getIO().readAssetsBitmap("PlayScreen/GetAmmoButton.png");
			Assets.playScreen_GetAmmoButtonClicked = this.getGame().getIO().readAssetsBitmap("PlayScreen/GetAmmoButtonClicked.png");
			Assets.playScreen_LaserBeamAnimation = this.getGame().getIO().readAssetsBitmap("PlayScreen/LaserBeamAnimation.png");
			Assets.playScreen_MissleAnimation = this.getGame().getIO().readAssetsBitmap("PlayScreen/MissleAnimation.png");
			Assets.playScreen_Boss = this.getGame().getIO().readAssetsBitmap("PlayScreen/Boss.png");
			Assets.playScreen_PowerUp = this.getGame().getIO().readAssetsBitmap("PlayScreen/PowerUp.jpeg");
			Assets.playScreen_Star1 = this.getGame().getIO().readAssetsBitmap("PlayScreen/Star1.png");
			Assets.playScreen_Star2 = this.getGame().getIO().readAssetsBitmap("PlayScreen/Star2.png");
			Assets.playScreen_Star3 = this.getGame().getIO().readAssetsBitmap("PlayScreen/Star3.png");
			Assets.playScreen_Explosion = this.getGame().getIO().readAssetsBitmap("PlayScreen/explosion.png");
			Assets.playScreen_Spaceship1 = this.getGame().getIO().readAssetsBitmap("PlayScreen/Spaceship1.png");
			Assets.playScreen_Spaceship2 = this.getGame().getIO().readAssetsBitmap("PlayScreen/Spaceship2.png");
			Assets.playScreen_Spaceship3 = this.getGame().getIO().readAssetsBitmap("PlayScreen/Spaceship3.png");
			Assets.playScreen_SlowStrongDroid = this.getGame().getIO().readAssetsBitmap("PlayScreen/SlowStrongDroid.png");
			Assets.playScreen_FastWeakDroid = this.getGame().getIO().readAssetsBitmap("PlayScreen/FastWeakDroid.png");
			Assets.playScreen_Explosion = this.getGame().getIO().readAssetsBitmap("PlayScreen/explosion.png");
			Assets.playScreen_InstantKillIcon = this.getGame().getIO().readAssetsBitmap("PlayScreen/InstantKillIcon.png");
			Assets.playScreen_TimesTwoIcon = this.getGame().getIO().readAssetsBitmap("PlayScreen/TimesTwoIcon.png");
			Assets.playScreen_PowerUpAnimation = this.getGame().getIO().readAssetsBitmap("PlayScreen/PowerUpAnimation.png");
			Assets.playScreen_RedLaser = this.getGame().getIO().readAssetsBitmap("PlayScreen/RedLaser.png");
			Assets.playScreen_BlueLaser = this.getGame().getIO().readAssetsBitmap("PlayScreen/BlueLaser.png");
			Assets.playScreen_GreenLaser = this.getGame().getIO().readAssetsBitmap("PlayScreen/GreenLaser.png");
			Assets.playScreen_Asteroid1Animation = this.getGame().getIO().readAssetsBitmap("PlayScreen/Asteroid1Animation.png");
			Assets.playScreen_Asteroid2Animation = this.getGame().getIO().readAssetsBitmap("PlayScreen/Asteroid2Animation.png");
			Assets.playScreen_Instructions = this.getGame().getIO().readAssetsBitmap("PlayScreen/Instructions.png");
			Assets.playScreen_InstructionsArrow = this.getGame().getIO().readAssetsBitmap("PlayScreen/arrow.png");
			Assets.laserBeamAnimation = new SpriteSheetAnimation(this.getGame(), Assets.playScreen_LaserBeamAnimation, null, new Rect(), 1, 12, 0, true);
			Assets.playScreen_DamageTaken = this.getGame().getAudio().newSound("PlayScreen/DamageTaken.ogg");
			Assets.playScreen_Explosion1 = this.getGame().getAudio().newSound("PlayScreen/Explosion1.ogg");
			Assets.playScreen_Explosion2 = this.getGame().getAudio().newSound("PlayScreen/Explosion2.ogg");
			Assets.playScreen_GameOver = this.getGame().getAudio().newSound("PlayScreen/GameOver.ogg");
			Assets.playScreen_GunShoot1 = this.getGame().getAudio().newSound("PlayScreen/GunShoot1.ogg");
			Assets.playScreen_GunShoot2 = this.getGame().getAudio().newSound("PlayScreen/GunShoot2.ogg");
			Assets.playScreen_GunShoot3 = this.getGame().getAudio().newSound("PlayScreen/GunShoot3.ogg");
			Assets.playScreen_LaserBeamSound = this.getGame().getAudio().newSound("PlayScreen/LaserBeamSound.ogg");
			Assets.playScreen_MissleLaunch = this.getGame().getAudio().newSound("PlayScreen/MissleLaunch.ogg");
			Assets.playScreen_PowerUpSound = this.getGame().getAudio().newSound("PlayScreen/PowerUpSound.ogg");
			Assets.playScreen_PlayMusic = this.getGame().getAudio().newMusic("PlayScreen/PlayMusic.ogg");
			
			this.getGame().getGameLog().d(this, "Successfully read: PlayScreen");
			
		} 
		catch (IOException e) {
			e.printStackTrace();
			
		}
		
	}
	
	private void loadScoresScreen() {
		try {
			Assets.scoresScreen_BestLevelBoard = this.getGame().getIO().readAssetsBitmap("ScoresScreen/BestLevelBoard.png");
			Assets.scoresScreen_LevelStar = this.getGame().getIO().readAssetsBitmap("ScoresScreen/LevelStar.png");
			Assets.scoresScreen_PositionStar = this.getGame().getIO().readAssetsBitmap("ScoresScreen/PositionStar.png");
			
			Assets.scoresScreen_RefreshButton = this.getGame().getIO().readAssetsBitmap("ScoresScreen/RefreshButton.png");
			Assets.scoresScreen_RefreshButtonClicked = this.getGame().getIO().readAssetsBitmap("ScoresScreen/RefreshButtonClicked.png");
			Assets.scoresScreen_RefreshIcon = this.getGame().getIO().readAssetsBitmap("ScoresScreen/RefreshIcon.png");
			
			this.getGame().getGameLog().d(this, "Successfully read: ScoresScreen");
			
		} 
		catch (IOException e) {
			e.printStackTrace();
			
		}
		
	}
	
	public void loadStoresScreen() {
		try {
			Assets.storesScreen_BuyButton = this.getGame().getIO().readAssetsBitmap("StoreScreen/BuyButton.png");
			Assets.storesScreen_BuyButtonClicked = this.getGame().getIO().readAssetsBitmap("StoreScreen/BuyButtonClicked.png");
			Assets.storesScreen_BuyButtonDisabled = this.getGame().getIO().readAssetsBitmap("StoreScreen/BuyButtonDisabled.png");
			Assets.storesScreen_GetHe3Button = this.getGame().getIO().readAssetsBitmap("StoreScreen/GetHe3Button.png");
			Assets.storesScreen_GetHe3ButtonClicked = this.getGame().getIO().readAssetsBitmap("StoreScreen/GetHe3ButtonClicked.png");
			Assets.storesScreen_GetHe3ButtonDisabled = this.getGame().getIO().readAssetsBitmap("StoreScreen/GetHe3ButtonDisabled.png");
			Assets.storesScreen_GetHe3ButtonLarge = this.getGame().getIO().readAssetsBitmap("StoreScreen/GetHe3ButtonLarge.png");
			Assets.storesScreen_GetHe3ButtonLargeClicked = this.getGame().getIO().readAssetsBitmap("StoreScreen/GetHe3ButtonLargeClicked.png");
			this.getGame().getGameLog().d(this, "Successfully read: StoresScreen");
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadHangarScreen() {
		try {
			Assets.hangarScreen_SellButton = this.getGame().getIO().readAssetsBitmap("HangarScreen/SellButton.png");
			Assets.hangarScreen_SellButtonClicked = this.getGame().getIO().readAssetsBitmap("HangarScreen/SellButtonClicked.png");
			Assets.hangarScreen_SellButtonDisabled = this.getGame().getIO().readAssetsBitmap("HangarScreen/SellButtonDisabled.png");
			Assets.hangarScreen_Board = this.getGame().getIO().readAssetsBitmap("HangarScreen/Board.png");
			Assets.hangarScreen_EmptyGunSlot = this.getGame().getIO().readAssetsBitmap("HangarScreen/EmptyGunSlot.png");
			this.getGame().getGameLog().d(this, "Successfully read: HangarScreen");
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void onDispose() {
		this.disposeStandardScreen();
		this.disposeMenuScreen();
		this.disposeSettingsScreen();
		this.disposePlayScreen();
		this.disposeScoresScreen();
		this.disposeStoresScreen();
		this.disposeHangarScreen();
		this.disposeMerchandise();
		
	}
	
	private void disposeMerchandise() {
		this.getGame().getIO().disposeBitmap(Assets.merchandise_Spaceship1);
		this.getGame().getIO().disposeBitmap(Assets.merchandise_Spaceship2);
		this.getGame().getIO().disposeBitmap(Assets.merchandise_Spaceship3);
		this.getGame().getIO().disposeBitmap(Assets.merchandise_Gun1);
		this.getGame().getIO().disposeBitmap(Assets.merchandise_Gun2);
		this.getGame().getIO().disposeBitmap(Assets.merchandise_Gun3);
		this.getGame().getIO().disposeBitmap(Assets.merchandise_Cover1);
		this.getGame().getIO().disposeBitmap(Assets.merchandise_Cover2);
		this.getGame().getIO().disposeBitmap(Assets.merchandise_Cover3);
		
	}
	
	private void disposeStandardScreen() {
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_ProgressionBar);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_LogOutButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_LogOutButton);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_NotNowButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_NotNowButton);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_LargeDialog);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_InfoButton);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_InfoButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_InfoButtonDisabled);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_YesButton);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_YesButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_NoButton);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_NoButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_SlideBar);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_BigViewBoard);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_SmallViewBoard);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_OkayButton);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_OkayButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_Background);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_BackButton);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_BackButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_ForwardButton);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_ForwardButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_LoginButton);
		this.getGame().getIO().disposeBitmap(Assets.standardScreen_LoginButtonClicked);
		this.getGame().getAudio().disposeSound(Assets.standardScreen_Click);
		this.getGame().getAudio().disposeSound(Assets.standardScreen_Notification);
		this.getGame().getAudio().disposeMusic(Assets.standardScreen_StandardScreenMusic);
		this.getGame().getGameLog().d(this, "Successfully disposed: StandardScreen");
		
	}
	
	private void disposeMenuScreen() {
		this.getGame().getIO().disposeBitmap(Assets.menuScreen_HangarButton);
		this.getGame().getIO().disposeBitmap(Assets.menuScreen_HangarButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.menuScreen_PlayButton);
		this.getGame().getIO().disposeBitmap(Assets.menuScreen_PlayButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.menuScreen_ScoresButton);
		this.getGame().getIO().disposeBitmap(Assets.menuScreen_ScoresButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.menuScreen_SettingsButton);
		this.getGame().getIO().disposeBitmap(Assets.menuScreen_SettingsButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.menuScreen_StoreButton);
		this.getGame().getIO().disposeBitmap(Assets.menuScreen_StoreButtonClicked);
		this.getGame().getGameLog().d(this, "Successfully disposed: MenuScreen");
		
	}
	
	private void disposeSettingsScreen() {		
		this.getGame().getIO().disposeBitmap(Assets.settingsScreen_MusicButton);
		this.getGame().getIO().disposeBitmap(Assets.settingsScreen_MusicButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.settingsScreen_ProfileNameButton);
		this.getGame().getIO().disposeBitmap(Assets.settingsScreen_ProfileNameButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.settingsScreen_SoundButton);
		this.getGame().getIO().disposeBitmap(Assets.settingsScreen_SoundButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.settingsScreen_TiltSensitivityButton);
		this.getGame().getIO().disposeBitmap(Assets.settingsScreen_TiltSensitivityButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.settingsScreen_VibrationButton);
		this.getGame().getIO().disposeBitmap(Assets.settingsScreen_VibrationButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.settingsScreen_MuteButton);
		this.getGame().getIO().disposeBitmap(Assets.settingsScreen_MuteButtonClicked);
		this.getGame().getGameLog().d(this, "Successfully disposed: SettingsScreen");
		
	}
	
	private void disposePlayScreen() {
		this.getGame().getIO().disposeBitmap(Assets.playScreen_Dialog);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_Background);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_MenuButton);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_MenuButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_PauseButton);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_PauseButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_RestartButton);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_RestartButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_ResumeButton);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_ResumeButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_9mmLugerBullet);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_Coins);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_GetAmmoButton);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_GetAmmoButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_LaserBeamAnimation);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_MissleAnimation);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_Boss);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_PowerUp);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_Star1);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_Star2);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_Star3);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_Explosion);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_Spaceship1);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_Spaceship2);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_Spaceship3);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_SlowStrongDroid);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_FastWeakDroid);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_InstantKillIcon);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_TimesTwoIcon);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_PowerUpAnimation);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_RedLaser);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_BlueLaser);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_GreenLaser);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_Asteroid1Animation);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_Asteroid2Animation);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_Instructions);
		this.getGame().getIO().disposeBitmap(Assets.playScreen_InstructionsArrow);
		Assets.laserBeamAnimation.dispose();
		this.getGame().getAudio().disposeSound(Assets.playScreen_DamageTaken);
		this.getGame().getAudio().disposeSound(Assets.playScreen_Explosion1);
		this.getGame().getAudio().disposeSound(Assets.playScreen_Explosion2);
		this.getGame().getAudio().disposeSound(Assets.playScreen_GameOver);
		this.getGame().getAudio().disposeSound(Assets.playScreen_GunShoot1);
		this.getGame().getAudio().disposeSound(Assets.playScreen_GunShoot2);
		this.getGame().getAudio().disposeSound(Assets.playScreen_GunShoot3);
		this.getGame().getAudio().disposeSound(Assets.playScreen_LaserBeamSound);
		this.getGame().getAudio().disposeSound(Assets.playScreen_MissleLaunch);
		this.getGame().getAudio().disposeSound(Assets.playScreen_PowerUpSound);
		this.getGame().getAudio().disposeMusic(Assets.playScreen_PlayMusic);
		
		this.getGame().getGameLog().d(this, "Successfully disposed: PlayScreen");
		
	}
	
	private void disposeScoresScreen() {
		this.getGame().getIO().disposeBitmap(Assets.scoresScreen_BestLevelBoard);
		this.getGame().getIO().disposeBitmap(Assets.scoresScreen_LevelStar);
		this.getGame().getIO().disposeBitmap(Assets.scoresScreen_PositionStar);
		this.getGame().getIO().disposeBitmap(Assets.scoresScreen_RefreshButton);
		this.getGame().getIO().disposeBitmap(Assets.scoresScreen_RefreshButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.scoresScreen_RefreshIcon);
		
		this.getGame().getGameLog().d(this, "Successfully disposed: ScoresScreen");
		
	}
	
	public void disposeStoresScreen() {
		this.getGame().getIO().disposeBitmap(Assets.storesScreen_BuyButton);
		this.getGame().getIO().disposeBitmap(Assets.storesScreen_BuyButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.storesScreen_BuyButtonDisabled);
		this.getGame().getIO().disposeBitmap(Assets.storesScreen_GetHe3Button);
		this.getGame().getIO().disposeBitmap(Assets.storesScreen_GetHe3ButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.storesScreen_GetHe3ButtonDisabled);
		this.getGame().getIO().disposeBitmap(Assets.storesScreen_GetHe3ButtonLarge);
		this.getGame().getIO().disposeBitmap(Assets.storesScreen_GetHe3ButtonLargeClicked);
		this.getGame().getGameLog().d(this, "Successfully disposed: StoresScreen");
		
	}
	
	public void disposeHangarScreen() {
		this.getGame().getIO().disposeBitmap(Assets.hangarScreen_SellButton);
		this.getGame().getIO().disposeBitmap(Assets.hangarScreen_SellButtonClicked);
		this.getGame().getIO().disposeBitmap(Assets.hangarScreen_SellButtonDisabled);
		this.getGame().getIO().disposeBitmap(Assets.hangarScreen_Board);
		this.getGame().getIO().disposeBitmap(Assets.hangarScreen_EmptyGunSlot);
		this.getGame().getGameLog().d(this, "Successfully disposed: HangarScreen");
		
	}
	
	
}
