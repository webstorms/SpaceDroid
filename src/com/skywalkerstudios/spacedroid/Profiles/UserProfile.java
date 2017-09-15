package com.skywalkerstudios.spacedroid.Profiles;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONException;
import org.json.JSONObject;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.FacebookUser;
import com.skywalkerstudios.spacedroid.FileNames;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo1;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo2;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo3;
import com.skywalkerstudios.spacedroid.merchandise.guns.Gun1;
import com.skywalkerstudios.spacedroid.merchandise.guns.Gun2;
import com.skywalkerstudios.spacedroid.merchandise.guns.Gun3;
import com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship;
import com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship1;
import com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship2;
import com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship3;
import com.webstorms.framework.Game;
import com.webstorms.framework.audio.Audio;
import com.webstorms.framework.audio.Music;
import com.webstorms.framework.audio.Sound;

public class UserProfile extends Profile {
	
	/*
	 * Performance: Don't always send id to server, only when profile is created
	 * Do not like the notifier system. Run it in parallel when data is being synched to/from server!
	 * Maybe give another thought about the superclass methods and how it gets implemented here
	 * 
	 */
	
	public static final int NUMBEROFPROFILE = 1;
	
	public static final Integer LOGIN_CANCELLED = 0;
	public static final Integer LOGIN_ERROR = 1;
	public static final Integer LOGIN_LOGGED_IN = 2;
	public static final Integer LOGIN_SIGNED_UP_AND_LOGGED_IN = 3;
	
	public static final Integer LOGOUT_ERROR = 0;
	
	Integer logOutType;
	String serverErrorMessage;
	Integer loginType;
	boolean loggedInLocally;
	// String currentlyLoading;
	
	boolean logOut;
	
	private boolean loadingScoreScreenData;
	private AtomicInteger isInteractingWithServer = new AtomicInteger();
	private List<ServerInteractionNotifier> notifier = new ArrayList<ServerInteractionNotifier>();
	
	// General info
	private int amountOfCredits;
	private int amountOfHe3;
	private List<Profile> friends = new ArrayList<Profile>(); // Friends of this player
	private List<Profile> sortedProfiles = new ArrayList<Profile>(); // Friends of this player and the player him self in sorted sequence according to XP // Init in loadUserProfile, making a copy of it
	
	// Space Ship info
	Spaceship spaceShip;
	
	// Settings
	private int tiltSensitivity;
	private int soundVolume;
	private int musicVolume;
	private boolean vibrationOn;
	
	public static final int LOAD_PROFILE_CREATE = 0;
	public static final int LOAD_PROFILE_SERVER = 1;
	// public static final int REFRESH_PROFILE_SERVER = 2;
	public static final int SAVE_PROFILE_SERVER = 3;
	
	public UserProfile(Game game) {
		super(game);
		
	//	this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  1 + "_" + FileNames.UserProfile.Ammo, "Ammo3");
		
	//	this.spaceShip = new Spaceship1();
	//	this.spaceShip.placeGunIntoShip(new Gun1());
	//	this.saveUserSpaceShip(Profile.TYPE_LOCAL, UserProfile.NUMBEROFPROFILE);
		
	//	this.setLoggedIn(false, this.TYPE_LOCAL);
	//	this.setLoggedIn(false, this.TYPE_SERVER);
	//	this.this.getGame().getIO().writePrimitiveInternalMemory(FileNames.UserProfile.AMOUNT_OF_FRIENDS, 0);
	//	this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  1 + "_" + FileNames.UserProfile.Ammo, Ammo1.CLASS_TAG);
		
	}
	
	public boolean isLoggedIn(int type) {
		boolean state = false;
		if(type == UserProfile.TYPE_LOCAL) {
			state = loggedInLocally;
		}
		else if(type == UserProfile.TYPE_SERVER) {
			
			if(this.getGame() == null) {
				this.getGame().getGameLog().d(this, "Game is null");
				
			}
			else if(this.getGame().getIO() == null) {
				this.getGame().getGameLog().d(this, "this.getGame().getIO() is null");
				
			}
			else if(this.getGame().getIO().readPrimitiveInternalMemoryBoolean(FileNames.UserProfile.LOG_IN)) {
				this.getGame().getGameLog().d(this, "this.getGame().getIO().readPrimitiveInternalMemoryBoolean(FileNames.UserProfile.LOG_IN) is null");
				
			}
			
			state = this.getGame().getIO().readPrimitiveInternalMemoryBoolean(FileNames.UserProfile.LOG_IN);
			
		}
		
		return state;
		
	}
	
	public void setLoggedIn(boolean loggedIn, int type) {
		if(type == UserProfile.TYPE_LOCAL) {
			this.loggedInLocally = loggedIn;
			
		}
		else if(type == UserProfile.TYPE_SERVER) {
			this.getGame().getIO().writePrimitiveInternalMemory(FileNames.UserProfile.LOG_IN, loggedIn);
			
		}	
		
	}
	
	public Integer getLoginType() {
		return loginType;
		
	}
	
	public Integer getLogOutType() {
		return logOutType;
		
	}
	
	public String getServerErrorMessage() {
		return this.serverErrorMessage;
		
	}
	
	public void logIn() {
	//	this.currentlyLoading = "Connectig to the server...";
		ParseFacebookUtils.logIn(this.getGame(), new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException err) {
				if(err != null) {
				//	currentlyLoading = "Network connection error.";
					getGame().getGameLog().d(getClassTag(), "Login aborded due to some fuck up");
					getGame().getGameLog().d(getClassTag(), err.getMessage());
					loginType = UserProfile.LOGIN_ERROR;
					serverErrorMessage = err.getMessage();
					
				}
				else if (user == null) {
					getGame().getGameLog().d(getClassTag(), "Uh oh. The user cancelled the Facebook login.");
					loginType = UserProfile.LOGIN_CANCELLED;
					
				} 
				else if (user.isNew()) {
					getGame().getGameLog().d(getClassTag(), "User signed up and logged in through Facebook!");
					loginType = UserProfile.LOGIN_SIGNED_UP_AND_LOGGED_IN;
					loadProfile(UserProfile.TYPE_CREATE_SERVER, null);
							
				} 
				else {
					getGame().getGameLog().d(getClassTag(), "User logged in through Facebook!");
					loginType = UserProfile.LOGIN_LOGGED_IN;
					loadProfile(UserProfile.TYPE_SERVER, null);
							
				}	
				
			}

			
		});
		
		
	}
	
	public void logOut() {
	//	this.currentlyLoading = "Connectig to the server...";
		this.logOut = true;
		this.saveProfile(UserProfile.TYPE_SERVER, null);
	
	}
	
	public void onLoadComplete() {
		this.sortedProfiles.add(this);
		this.sortProfilesAccordingToXP();
		this.spaceShip.sortGunsAccordingToDamage();
		loginType = UserProfile.LOGIN_SIGNED_UP_AND_LOGGED_IN;
		
	}
	
	@Override
	public void loadProfileCreate(final Integer numberOfProfile, int loadType) {
		super.loadProfileCreate(numberOfProfile, loadType);
		if(loadType == Profile.TYPE_CREATE_LOCAL) {
			// Generate User information
						new Thread() {		

							@Override
							public void run() {
								this.delayTime(1000);
								
								// enableSocialEnabled();
								
								setLoadTextOfProgressionDialog("Generating name...");
								this.delayTime(50);
								setUserName("Captain Hook");
								
								setLoadTextOfProgressionDialog("Generating Credits...");
								this.delayTime(50);
								setAmountOfCredits(5500);
								
								setLoadTextOfProgressionDialog("Generating He3...");
								this.delayTime(50);
								setAmountOfHe3(800);
								
								setLoadTextOfProgressionDialog("Generating XP...");
								this.delayTime(50);
								setUserXP(0);
								
								setLoadTextOfProgressionDialog("Generating Spaceship...");
								this.delayTime(50);
								setSpaceship(new Spaceship1(getGame()));
								
								// Fill Spaceship with guns
								for(int i = 0; i < getSpaceship().getAmountOfGunSlots(); i++) {
									getSpaceship().placeGunIntoShip(new Gun1(getGame()));
									
								}
								
								// Set initial Ammo
								getSpaceship().setAmmo(new Ammo1(getGame()));
								
								setLoadTextOfProgressionDialog("Init Best Score...");
								this.delayTime(50);
								setUserBestScore(0);
								
								setLoadTextOfProgressionDialog("Init Tilt...");
								this.delayTime(50);
								setTiltSensitivity(3);
								
								setLoadTextOfProgressionDialog("Init Sound...");
								this.delayTime(50);
								setSoundVolume(Audio.SOUND_MAX);
								
								setLoadTextOfProgressionDialog("Init Music...");
								this.delayTime(50);
								setMusicVolume(Audio.SOUND_MAX);
								
								setLoadTextOfProgressionDialog("Init Vibration...");
								this.delayTime(50);
								setViabrationOn(true);
								
								setLoadTextOfProgressionDialog("Generated user!");
								onLoadComplete();
								SINOnCompleted(UserProfile.LOAD_PROFILE_CREATE);
								this.delayTime(1000);
								
								// Successfully signed the user in
								saveProfileLocal(UserProfile.NUMBEROFPROFILE); // Safty first, incase the app crashes or something terible like that
								setLoggedIn(true, UserProfile.TYPE_LOCAL);
								
								getGame().getGameLog().d(getClassTag(), "Create and loaded profile: " + getUserName());
								
							}
							
							public void delayTime(int time) {
								try {
									Thread.sleep(time);
								} 
								catch (InterruptedException e) {
									e.printStackTrace();
								}
								
							}
							
							public void setLoadTextOfProgressionDialog(final String message) {
								if(SINRegistered()) {
									SINOnChanged(message);
									
								}
								
							}
							
						}.start();
			
		}
		else if(loadType == Profile.TYPE_CREATE_SERVER) {
			// Generate User information
						new Thread() {		

							@Override
							public void run() {
								
								enableSocialEnabled();
								
								setLoadTextOfProgressionDialog("Loading Social ID...");
								this.delayTime(50);
								setUserSocialID(FacebookUser.getFaceBookID());
								
								setLoadTextOfProgressionDialog("Loading Server ID...");
								this.delayTime(50);
								setUserServerID(ParseUser.getCurrentUser().getObjectId());
								
								setLoadTextOfProgressionDialog("Generating User...");
								loadUserFriends(UserProfile.TYPE_SERVER, numberOfProfile); // This takes some time so just say "Downloading data..." instead of "Loading friends..."
								setUserPicture(FacebookUser.getProfilePicture(getUserSocialID(), FacebookUser.TYPE_LARGE)); // This takes some time so just say "Downloading data..." instead of "Loading User picture..."
								
								setLoadTextOfProgressionDialog("Loading Username...");
								this.delayTime(50);
								setUserName(FacebookUser.getName());
								
								setLoadTextOfProgressionDialog("Generating Credits...");
								this.delayTime(50);
								setAmountOfCredits(5500);
								
								setLoadTextOfProgressionDialog("Generating He3...");
								this.delayTime(50);
								setAmountOfHe3(800);
								
								setLoadTextOfProgressionDialog("Generating XP...");
								this.delayTime(50);
								setUserXP(0);
								
								setLoadTextOfProgressionDialog("Generating Spaceship...");
								this.delayTime(50);
								setSpaceship(new Spaceship1(getGame()));
								
								// Fill Spaceship with guns
								for(int i = 0; i < getSpaceship().getAmountOfGunSlots(); i++) {
									getSpaceship().placeGunIntoShip(new Gun1(getGame()));
									
								}
								
								// Set initial Ammo
								getSpaceship().setAmmo(new Ammo1(getGame()));
								
								setLoadTextOfProgressionDialog("Init Best Score...");
								this.delayTime(50);
								setUserBestScore(0);
								
								setLoadTextOfProgressionDialog("Init Tilt...");
								this.delayTime(50);
								setTiltSensitivity(3);
								
								setLoadTextOfProgressionDialog("Init Sound...");
								this.delayTime(50);
								setSoundVolume(Audio.SOUND_MAX);
								
								setLoadTextOfProgressionDialog("Init Music...");
								this.delayTime(50);
								setMusicVolume(Audio.SOUND_MAX);
								
								setLoadTextOfProgressionDialog("Init Vibration...");
								this.delayTime(50);
								setViabrationOn(true);
								
								setLoadTextOfProgressionDialog("Finishing up...");
								saveProfileServer(UserProfile.NUMBEROFPROFILE);
								
								getGame().getUserProfile().registerServerInteractionNotifier(new ServerInteractionNotifier() {

									@Override
									public void onChanged(final String message) {
										

									}

									@Override
									public void onCompleted(int action) {
										if(action == UserProfile.SAVE_PROFILE_SERVER) {
											getGame().getUserProfile().deregisterServerInteractionNotifier(this);
											setLoadTextOfProgressionDialog("Generated user!");
											onLoadComplete();
											SINOnCompleted(UserProfile.LOAD_PROFILE_CREATE);
											delayTime(1000);
											
											// Successfully signed the user in
											saveProfileLocal(UserProfile.NUMBEROFPROFILE); // Safty first, incase the app crashes or something terible like that
											setLoggedIn(true, UserProfile.TYPE_LOCAL);
											setLoggedIn(true, UserProfile.TYPE_SERVER);
											
											getGame().getGameLog().d(getClassTag(), "Create and loaded profile: " + getUserName());
											
										}

									}

								});
								
							}
							
							public void delayTime(int time) {
								try {
									Thread.sleep(time);
								} 
								catch (InterruptedException e) {
									e.printStackTrace();
								}
								
							}
							
							public void setLoadTextOfProgressionDialog(final String message) {
								if(SINRegistered()) {
									SINOnChanged(message);
									
								}
								
							}
							
						}.start();
			
		}
		
	}
	
	@Override
	public void loadProfileLocal(Integer numberOfProfile) {
		super.loadProfileLocal(numberOfProfile);
		if(this.isSocialEnabled()) {
			this.loadUserFriends(UserProfile.TYPE_LOCAL, numberOfProfile);
			this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.SOCIAL_ID, this.getUserSocialID());
			this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.SERVER_ID, this.getUserServerID());
			this.getGame().getIO().writeBitmapToMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.PICTURE, this.getUserPicture());
			
		}
		this.setAmountOfCredits(this.getGame().getIO().readPrimitiveInternalMemoryInteger(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.CREDITS));
		this.setAmountOfHe3(this.getGame().getIO().readPrimitiveInternalMemoryInteger(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.HE3));
		this.setTiltSensitivity(this.getGame().getIO().readPrimitiveInternalMemoryInteger(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.TILT_SENSITIVITY));
		this.setSoundVolume(this.getGame().getIO().readPrimitiveInternalMemoryInteger(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.SOUND));
		this.setMusicVolume(this.getGame().getIO().readPrimitiveInternalMemoryInteger(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.MUSIC));
		this.setViabrationOn(this.getGame().getIO().readPrimitiveInternalMemoryBoolean(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.VIBRATION));
		this.loadUserSpaceShip(UserProfile.TYPE_LOCAL, numberOfProfile);
		
		// Successfully signed the user in
		setLoggedIn(true, UserProfile.TYPE_LOCAL);
		this.getGame().getGameLog().d(getClassTag(), "Loaded profile: " + this.getUserName());
		onLoadComplete();
		// this.cleanProfileLocal(1);
		// this.getGame().getGameLog().d(classTAG, "Cleaned profile: " + this.getUserName());
		
	}
	
	public void refreshScoreScreenData(final Integer numberOfProfile) {
		registerServerInteractionNotifier(new ServerInteractionNotifier() {

			@Override
			public void onChanged(String message) {
				
				
			}

			@Override
			public void onCompleted(int action) {
				if(action == UserProfile.SAVE_PROFILE_SERVER) {
					loadingScoreScreenData = false;
					deregisterServerInteractionNotifier(this);
					
				}
				
			}
			
		});

		// Perform Polling and Saving in a new Thread, not to block the UI Thread
		new Thread() {

			@Override
			public void run() {
				
				loadingScoreScreenData = true;
				isInteractingWithServer.getAndIncrement();
				
				// Download
				setUserPicture(FacebookUser.getProfilePicture(getUserSocialID(), FacebookUser.TYPE_LARGE));
				loadUserFriends(UserProfile.TYPE_SERVER, numberOfProfile);
				onLoadComplete();
				// SINOnCompleted(UserProfile.REFRESH_PROFILE_SERVER);
				saveProfileLocal(UserProfile.NUMBEROFPROFILE); // Safty first, incase the app crashes or something terible like that
				
				isInteractingWithServer.getAndDecrement();
				
				// Upload
				saveProfileServer(UserProfile.NUMBEROFPROFILE);
				
			}

		}.start();
		
	}
	
	@Override
	public void loadProfileServer(final Integer numberOfProfile) {
		super.loadProfileServer(numberOfProfile);
		this.isInteractingWithServer.getAndIncrement();
		ParseUser.getQuery().getInBackground(ParseUser.getCurrentUser().getObjectId(), new GetCallback() {
			public void done(ParseObject object, ParseException e) {
				if(e != null) {
					serverErrorMessage = e.getMessage();
					
				}
				else {
					// Load User information
					new Thread() {

						@Override
						public void run() {
							enableSocialEnabled();
							setLoadTextOfProgressionDialog("Loading Social ID...");
							this.delayTime(50);
							setUserSocialID(ParseUser.getCurrentUser().getString(FileNames.Profile.SOCIAL_ID));
							
							setLoadTextOfProgressionDialog("Loading Server ID...");
							this.delayTime(50);
							setUserServerID(ParseUser.getCurrentUser().getObjectId());
							
							setLoadTextOfProgressionDialog("Loading User...");
							loadUserFriends(UserProfile.TYPE_SERVER, numberOfProfile); // This takes some time so just say "Downloading data..." instead of "Loading friends..."
							setUserPicture(FacebookUser.getProfilePicture(getUserSocialID(), FacebookUser.TYPE_LARGE)); // This takes some time so just say "Downloading data..." instead of "Loading User picture..."
							
							setLoadTextOfProgressionDialog("Loading Username...");
							this.delayTime(50);
							setUserName(ParseUser.getCurrentUser().getUsername());
							
							setLoadTextOfProgressionDialog("Loading Credits...");
							this.delayTime(50);
							setAmountOfCredits(ParseUser.getCurrentUser().getInt(FileNames.UserProfile.CREDITS));
							
							setLoadTextOfProgressionDialog("Loading He3...");
							this.delayTime(50);
							setAmountOfHe3(ParseUser.getCurrentUser().getInt(FileNames.UserProfile.HE3));
							
							setLoadTextOfProgressionDialog("Loading XP...");
							this.delayTime(50);
							setUserXP(ParseUser.getCurrentUser().getInt(FileNames.Profile.XP));
							
							setLoadTextOfProgressionDialog("Loading Spaceship...");
							this.delayTime(50);
							loadUserSpaceShip(Profile.TYPE_SERVER, numberOfProfile);
							
							setLoadTextOfProgressionDialog("Loading Best Score...");
							this.delayTime(50);
							setUserBestScore(ParseUser.getCurrentUser().getInt(FileNames.Profile.BEST_SCORE));
							
							setLoadTextOfProgressionDialog("Loading Tilt...");
							this.delayTime(50);
							setTiltSensitivity(ParseUser.getCurrentUser().getInt(FileNames.UserProfile.TILT_SENSITIVITY));
							
							setLoadTextOfProgressionDialog("Loading Sound...");
							this.delayTime(50);
							setSoundVolume(ParseUser.getCurrentUser().getInt(FileNames.UserProfile.SOUND));
							
							setLoadTextOfProgressionDialog("Loading Music...");
							this.delayTime(50);
							setMusicVolume(ParseUser.getCurrentUser().getInt(FileNames.UserProfile.MUSIC));
							
							setLoadTextOfProgressionDialog("Loading Vibration...");
							this.delayTime(50);
							setViabrationOn(ParseUser.getCurrentUser().getBoolean(FileNames.UserProfile.VIBRATION));
							
							// ((StandardScreen) getGame().getCurrentScreen()).formatLayout();
							
							setLoadTextOfProgressionDialog("Loaded user!");
							onLoadComplete();
							SINOnCompleted(UserProfile.LOAD_PROFILE_SERVER);
						//	isInteractingWithServer.getAndDecrement();
							this.delayTime(1000);
							
							// Successfully signed the user in
							saveProfileLocal(UserProfile.NUMBEROFPROFILE); // Safty first, incase the app crashes or something terible like that
							setLoggedIn(true, UserProfile.TYPE_LOCAL);
							setLoggedIn(true, UserProfile.TYPE_SERVER);
							getGame().getGameLog().d(getClassTag(), "Loaded profile: " + getUserName());
							
							
							
						}
						
						public void delayTime(int time) {
							try {
								Thread.sleep(time);
							} 
							catch (InterruptedException e) {
								e.printStackTrace();
							}
							
						}
						
						public void setLoadTextOfProgressionDialog(final String message) {
							if(SINRegistered()) {
								SINOnChanged(message);
								
							}
							
						}
						
					}.start();
					
				}
				
			}
			
		});

	}
	
	private void loadUserSpaceShip(int loadType, Integer numberOfProfile) {
		if(loadType == UserProfile.TYPE_LOCAL) {
			String spaceshipType = this.getGame().getIO().readPrimitiveInternalMemoryString(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.SPACESHIP);
			
			if(spaceshipType.equals(Spaceship1.CLASS_TAG)) {
				this.getGame().getGameLog().d(this.getClassTag(), "Loaded Spaceship1!");
				this.setSpaceship(new Spaceship1(this.getGame()));
				
			}
			else if(spaceshipType.equals(Spaceship2.CLASS_TAG)) {
				this.getGame().getGameLog().d(this.getClassTag(), "Loaded Spaceship2!");
				this.setSpaceship(new Spaceship2(this.getGame()));
				
			}
			else if(spaceshipType.equals(Spaceship3.CLASS_TAG)) {
				this.getGame().getGameLog().d(this.getClassTag(), "Loaded Spaceship3!");
				this.setSpaceship(new Spaceship3(this.getGame()));
				
			}
			
		}
		else if(loadType == UserProfile.TYPE_SERVER) {
			String spaceshipType = ParseUser.getCurrentUser().getString(FileNames.UserProfile.SPACESHIP);
			
			if(spaceshipType.equals(Spaceship1.CLASS_TAG)) {
				this.setSpaceship(new Spaceship1(this.getGame()));
				
			}
			else if(spaceshipType.equals(Spaceship2.CLASS_TAG)) {
				this.setSpaceship(new Spaceship2(this.getGame()));
				
			}
			else if(spaceshipType.equals(Spaceship3.CLASS_TAG)) {
				this.setSpaceship(new Spaceship3(this.getGame()));
				
			}
			
		}
		
		this.loadUserGuns(loadType, numberOfProfile);
		this.loadUserAmmo(loadType, numberOfProfile);
		
	}
	
	// Remeber to save ammo as int
	
	private void loadUserAmmo(int loadType, Integer numberOfProfile) {
		String ammoType = "";
		int ammoAmmount = 0;
		
		if(loadType == Profile.TYPE_LOCAL) {
			ammoType = this.getGame().getIO().readPrimitiveInternalMemoryString(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.Ammo);
			ammoAmmount = this.getGame().getIO().readPrimitiveInternalMemoryInteger(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.AMOUNT_OF_AMMO);
			
		}
		else if(loadType == Profile.TYPE_SERVER) {
			ammoType = ParseUser.getCurrentUser().getString(FileNames.UserProfile.Ammo);
			ammoAmmount = ParseUser.getCurrentUser().getInt(FileNames.UserProfile.AMOUNT_OF_AMMO);
			
		}
		
		if(ammoType.equals(Ammo1.CLASS_TAG)) {
			this.spaceShip.setAmmo(new Ammo1(this.getGame()));
			
		}
		else if(ammoType.equals(Ammo2.CLASS_TAG)) {
			this.spaceShip.setAmmo(new Ammo2(this.getGame()));
			
		}
		else if(ammoType.equals(Ammo3.CLASS_TAG)) {
			this.spaceShip.setAmmo(new Ammo3(this.getGame()));
			
		}
		
		this.spaceShip.setCurrentAmountOfAmmo(ammoAmmount);
		
		this.getGame().getGameLog().d(this, "Ammo: Load: " + this.spaceShip.getAmmo().getType());
		
	}
	
	private void loadUserGuns(int loadType, Integer numberOfProfile) {
	//	guns = new ArrayList<Gun>();
		
		if(loadType == Profile.TYPE_LOCAL) {
			this.getGame().getGameLog().d(this.getClassTag(), "Loading guns locally...");
					
			int gunSize = this.getGame().getIO().readPrimitiveInternalMemoryInteger(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.AMOUNT_OF_GUNS);
			
			// Initialize objects
			for(int i = 0; i < gunSize; i++) {	
				String gunType = this.getGame().getIO().readPrimitiveInternalMemoryString(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.GUN + (i + 1));
				
				// this.this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.GUN + (i + 1), Gun1.CLASS_TAG);
				
				this.getGame().getGameLog().d(this.getClassTag(), gunType);
				this.getGame().getGameLog().d(this.getClassTag(), "Trying to add new gun...");
				
				if(gunType.equals(Gun1.CLASS_TAG)) {
					this.getGame().getGameLog().d(this.getClassTag(), "Adding new gun");
					this.spaceShip.placeGunIntoShip(new Gun1(this.getGame()));
					
				}
				else if(gunType.equals(Gun2.CLASS_TAG)) {
					this.getGame().getGameLog().d(this.getClassTag(), "Adding new gun");
					this.spaceShip.placeGunIntoShip(new Gun2(this.getGame()));
					
				}
				else if(gunType.equals(Gun3.CLASS_TAG)) {
					this.getGame().getGameLog().d(this.getClassTag(), "Adding new gun");
					this.spaceShip.placeGunIntoShip(new Gun3(this.getGame()));
					
				}
				
				
			}
			
		}
		else if(loadType == Profile.TYPE_SERVER) {
			this.getGame().getGameLog().d(this.getClassTag(), "Loading guns from server...");
			JSONObject downloadedEncodedGuns = ParseUser.getCurrentUser().getJSONObject(FileNames.UserProfile.GUNS);
			
			for(int i = 0; i < downloadedEncodedGuns.length(); i++) {
				try {
					if(downloadedEncodedGuns.getString(FileNames.UserProfile.GUN + (i + 1)).equals(Gun1.CLASS_TAG)) {
						this.spaceShip.placeGunIntoShip(new Gun1(this.getGame()));
						
					}
					else if(downloadedEncodedGuns.getString(FileNames.UserProfile.GUN + (i + 1)).equals(Gun2.CLASS_TAG)) {
						this.spaceShip.placeGunIntoShip(new Gun2(this.getGame()));
						
					}
					else if(downloadedEncodedGuns.getString(FileNames.UserProfile.GUN + (i + 1)).equals(Gun3.CLASS_TAG)) {
						this.spaceShip.placeGunIntoShip(new Gun3(this.getGame()));
						
					}
					
				} 
				catch (JSONException e) {
					e.printStackTrace();
				}
			}
				
		}
		
		this.getGame().getGameLog().d(this.getClassTag(), "Loaded guns: " + this.spaceShip.getAllGunsPlacedInShip().length);
		
	}
	
	private void loadUserFriends(int loadType, Integer numberOfProfile) {
		if(loadType == UserProfile.TYPE_LOCAL) {
			this.getGame().getGameLog().d(this.getClassTag(), "Loading friends locally...");
			// friends = (List<Profile>) this.getGame().getIO().readObjectsFromMemory(FileNames.UserProfile.FRIENDS);
			int friendsSize = this.getGame().getIO().readPrimitiveInternalMemoryInteger(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.AMOUNT_OF_FRIENDS);
			
			// Initialize objects
			for(int i = 0; i < friendsSize; i++) {	
				this.friends.add(new FriendProfile(this.getGame()));
				
			}
			
			// Load objects
			for(int i = 0; i < friendsSize; i++) {	
				this.friends.get(i).loadProfile(Profile.TYPE_LOCAL, i + 2);
					
			}
			
		}
		else if(loadType == UserProfile.TYPE_SERVER) {
			this.getGame().getGameLog().d(this.getClassTag(), "Loading friends from server...");
			// Get user friends from the server
			ParseQuery friendQuery = ParseUser.getQuery();
			
			if(ParseUser.getQuery() == null) {
				this.getGame().getGameLog().d(this, "ParseUser.getQuery() is null");
				
			}
			
			if(friendQuery == null) {
				this.getGame().getGameLog().d(this, "friendQuery is null");
				
			}
			
			friendQuery.whereContainedIn(FileNames.Profile.SOCIAL_ID, FacebookUser.getFaceBookFriendsIDs());
			List<ParseObject> friendUsers = null;
			
			try {
				// Add download users to friend list
				friendUsers = friendQuery.find();
				this.friends.clear(); // Clear list, not to add old items to the refreshed list
				for(int i = 0; i < friendUsers.size(); i++) {	
					this.friends.add(new FriendProfile(this.getGame()));
					this.friends.get(i).provideServerData(friendUsers.get(i));
					this.friends.get(i).loadProfile(UserProfile.TYPE_SERVER, null);
					
				}
				
			} 
			catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
		
		this.sortedProfiles = new ArrayList<Profile>(this.friends);
		this.getGame().getGameLog().d(this.getClassTag(), "Loaded friends: " + this.friends.size());
			
	}
	
	private void sortProfilesAccordingToXP() {
		// Sort users in friend list according to XP points
		Collections.sort(this.sortedProfiles, new Comparator<Profile>() {
			
			@Override 
			public int compare(Profile a, Profile b) {
				if(a.getUserXP() == b.getUserXP()){
					return 0;
					
				}
				else if(a.getUserXP() < b.getUserXP()){
					return 1;
					
				}
				return -1;
				
			}
			
		});
		
		// Give position to according user
		for(int i = 0; i < this.sortedProfiles.size(); i++) {	
			this.sortedProfiles.get(i).setUserPosition(i + 1);
			
		}
				
	}
	
	@Override
	public void cleanProfileLocal(Integer numberOfProfile) {
		super.cleanProfileLocal(numberOfProfile);
		this.getGame().getIO().removeFile(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.CREDITS);
		this.getGame().getIO().removeFile(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.HE3);
		this.getGame().getIO().removeFile(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.TILT_SENSITIVITY);
		this.getGame().getIO().removeFile(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.SOUND);
		this.getGame().getIO().removeFile(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.MUSIC);
		this.getGame().getIO().removeFile(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.VIBRATION);
		this.cleanUserFriendsLocal();
		this.cleanUserSpaceShip(numberOfProfile);
		
	}

	private void cleanUserFriendsLocal() {
		this.getGame().getIO().removeFile(FileNames.UserProfile.AMOUNT_OF_FRIENDS);
		for(int i = 0; i < this.friends.size(); i++) {	
			this.friends.get(i).cleanProfileLocal(i + 2); // Starting index is 2; 0 = nothing, 1 = the user playing the this.getGame(); 2 <= friend profiles
			
		}
		
	}
	
	private void cleanUserSpaceShip(Integer numberOfProfile) {
		this.getGame().getIO().removeFile(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.SPACESHIP);
		this.cleanGunsLocal(numberOfProfile);
		// Clean Ammo
		this.getGame().getIO().removeFile(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.Ammo);
		
	}
	
	private void cleanGunsLocal(Integer numberOfProfile) {
		this.getGame().getIO().removeFile(FileNames.UserProfile.AMOUNT_OF_GUNS);
		for(int i = 0; i < this.spaceShip.getAllGunsPlacedInShip().length; i++) {	
			this.getGame().getIO().removeFile(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.GUN + (i + 1));
				
		}
		
	}
	
	private void saveUserFriends(int saveType, Integer numberOfProfile) { 
		if(saveType == UserProfile.TYPE_LOCAL) {
			this.getGame().getGameLog().d(this.getClassTag(), "Saving friends locally...");
			this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.AMOUNT_OF_FRIENDS, this.friends.size());
			// friends = (List<Profile>) this.getGame().getIO().readObjectsFromMemory(FileNames.UserProfile.FRIENDS);
			for(int i = 0; i < friends.size(); i++) {	
				friends.get(i).saveProfile(Profile.TYPE_LOCAL, i + 2); // Starting index is 2; 0 = nothing, 1 = the user playing the this.getGame(); 2 <= friend profiles
					
			}
			
		}
			
	}
	
	private void saveUserSpaceShip(int saveType, Integer numberOfProfile) {
		if(saveType == Profile.TYPE_LOCAL) {
			
			if(this.spaceShip.getClassTag().equals(Spaceship1.CLASS_TAG)) {
				this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.SPACESHIP, Spaceship1.CLASS_TAG);
				
			}
			else if(this.spaceShip.getClassTag().equals(Spaceship2.CLASS_TAG)) {
				this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.SPACESHIP, Spaceship2.CLASS_TAG);
				
			}
			else if(this.spaceShip.getClassTag().equals(Spaceship3.CLASS_TAG)) {
				this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.SPACESHIP, Spaceship3.CLASS_TAG);
				
			}
			
		}
		else if(saveType == Profile.TYPE_SERVER) {
			
			if(this.spaceShip.getClassTag().equals(Spaceship1.CLASS_TAG)) {
				ParseUser.getCurrentUser().put(FileNames.UserProfile.SPACESHIP, Spaceship1.CLASS_TAG);
				
			}
			else if(this.spaceShip.getClassTag().equals(Spaceship2.CLASS_TAG)) {
				ParseUser.getCurrentUser().put(FileNames.UserProfile.SPACESHIP, Spaceship2.CLASS_TAG);
				
			}
			else if(this.spaceShip.getClassTag().equals(Spaceship3.CLASS_TAG)) {
				ParseUser.getCurrentUser().put(FileNames.UserProfile.SPACESHIP, Spaceship3.CLASS_TAG);
				
			}
			
		}
		
		this.saveGuns(saveType, numberOfProfile);
		this.saveAmmo(saveType, numberOfProfile);
		
	}
	
	private void saveAmmo(int saveType, Integer numberOfProfile) {
		if(saveType == Profile.TYPE_LOCAL) {
			if(this.spaceShip.getAmmo().getType().equals(Ammo1.CLASS_TAG)) {
				this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.Ammo, Ammo1.CLASS_TAG);
				
			}
			else if(this.spaceShip.getAmmo().getType().equals(Ammo2.CLASS_TAG)) {
				this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.Ammo, Ammo2.CLASS_TAG);
				
			}
			else if(this.spaceShip.getAmmo().getType().equals(Ammo3.CLASS_TAG)) {
				this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.Ammo, Ammo3.CLASS_TAG);
				
			}
			
			this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.AMOUNT_OF_AMMO, this.spaceShip.getCurrentAmountOfAmmo());
			
		}
		else if(saveType == Profile.TYPE_SERVER) {
			if(this.spaceShip.getAmmo().getType().equals(Ammo1.CLASS_TAG)) {
				ParseUser.getCurrentUser().put(FileNames.UserProfile.Ammo, Ammo1.CLASS_TAG);
				
			}
			else if(this.spaceShip.getAmmo().getType().equals(Ammo2.CLASS_TAG)) {
				ParseUser.getCurrentUser().put(FileNames.UserProfile.Ammo, Ammo2.CLASS_TAG);
				
			}
			else if(this.spaceShip.getAmmo().getType().equals(Ammo3.CLASS_TAG)) {
				ParseUser.getCurrentUser().put(FileNames.UserProfile.Ammo, Ammo3.CLASS_TAG);
				
			}
			
			ParseUser.getCurrentUser().put(FileNames.UserProfile.AMOUNT_OF_AMMO, this.spaceShip.getCurrentAmountOfAmmo());
			
		}
		
		this.getGame().getGameLog().d(this, "Ammo: Saved: " + this.spaceShip.getAmmo().getType());
		
	}
	
	private void saveGuns(int saveType, Integer numberOfProfile) { 
		if(saveType == UserProfile.TYPE_LOCAL) {
			this.getGame().getGameLog().d(this.getClassTag(), "Saving guns locally...");
			this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.AMOUNT_OF_GUNS, this.spaceShip.getAllGunsPlacedInShip().length);
			
			for(int i = 0; i < this.spaceShip.getAllGunsPlacedInShip().length; i++) {	
				
				if(this.spaceShip.getAllGunsPlacedInShip()[i].getClassTag().equals(Gun1.CLASS_TAG) == true) {
					this.getGame().getGameLog().d(this.getClassTag(), "Saving gun...");
					this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.GUN + (i + 1), Gun1.CLASS_TAG);
				}
				else if(this.spaceShip.getAllGunsPlacedInShip()[i].getClassTag().equals(Gun2.CLASS_TAG) == true) {
					this.getGame().getGameLog().d(this.getClassTag(), "Saving gun...");
					this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.GUN + (i + 1), Gun2.CLASS_TAG);
				}
				else if(this.spaceShip.getAllGunsPlacedInShip()[i].getClassTag().equals(Gun3.CLASS_TAG) == true) {
					this.getGame().getGameLog().d(this.getClassTag(), "Saving gun...");
					this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.GUN + (i + 1), Gun3.CLASS_TAG);
				}
				
					
			}
			
		}
		else if(saveType == UserProfile.TYPE_SERVER) {
			this.getGame().getGameLog().d(this.getClassTag(), "Saving guns to server...");
			JSONObject uploadEncodedGuns = new JSONObject();
			
			for(int i = 0; i < this.spaceShip.getAllGunsPlacedInShip().length; i++) {
				try {
					if(this.spaceShip.getAllGunsPlacedInShip()[i].getClassTag().equals(Gun1.CLASS_TAG) == true) {
					//	this.this.getGame().getIO().writePrimitiveInternalMemory(FileNames.UserProfile.GUN  + (i + 1), TestGun.classTAG);
						uploadEncodedGuns.put(FileNames.UserProfile.GUN  + (i + 1), Gun1.CLASS_TAG);
					}
					else if(this.spaceShip.getAllGunsPlacedInShip()[i].getClassTag().equals(Gun2.CLASS_TAG) == true) {
					//	this.this.getGame().getIO().writePrimitiveInternalMemory(FileNames.UserProfile.GUN  + (i + 1), TestGun.classTAG);
						uploadEncodedGuns.put(FileNames.UserProfile.GUN  + (i + 1), Gun2.CLASS_TAG);
					}
					else if(this.spaceShip.getAllGunsPlacedInShip()[i].getClassTag().equals(Gun3.CLASS_TAG) == true) {
					//	this.this.getGame().getIO().writePrimitiveInternalMemory(FileNames.UserProfile.GUN  + (i + 1), TestGun.classTAG);
						uploadEncodedGuns.put(FileNames.UserProfile.GUN  + (i + 1), Gun3.CLASS_TAG);
					}
					
						
				} 
				catch (JSONException e) {
					e.printStackTrace();
				}
				
			}			
		
			ParseUser.getCurrentUser().put(FileNames.UserProfile.GUNS, uploadEncodedGuns);
			
		}
			
	}
	
	@Override
	public void saveProfileLocal(Integer numberOfProfile) { 
		super.saveProfileLocal(numberOfProfile);
		if(this.isSocialEnabled()) {
			saveUserFriends(Profile.TYPE_LOCAL, numberOfProfile);
			this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.SOCIAL_ID, this.getUserSocialID());
	        this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.SERVER_ID, this.getUserServerID());
	        this.getGame().getIO().writeBitmapToMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.Profile.PICTURE, this.getUserPicture());
	        
		}
		
		this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.CREDITS, this.amountOfCredits);
		this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.HE3, this.amountOfHe3);
		this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.TILT_SENSITIVITY, this.tiltSensitivity);
		this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.SOUND, this.soundVolume);
		this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.MUSIC, this.musicVolume);
		this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  numberOfProfile + "_" + FileNames.UserProfile.VIBRATION, this.vibrationOn);
		saveUserSpaceShip(Profile.TYPE_LOCAL, numberOfProfile);
		
		// this.this.getGame().getIO().writeObjectToMemory(FileNames.UserProfile.FRIENDS, this.friends);
		setLoggedIn(false, UserProfile.TYPE_LOCAL);
		this.getGame().getGameLog().d(this.getClassTag(), "Saved profile: " + this.getUserName());
		
	}
	
	@Override
	public void saveProfileServer(Integer numberOfProfile) { 
		super.saveProfileServer(numberOfProfile);
		this.isInteractingWithServer.getAndIncrement();
		final ParseUser user = ParseUser.getCurrentUser();

		user.put(FileNames.Profile.SOCIAL_ID, this.getUserSocialID());
		// No need to upload the userServerID since it is static (only created once and does not change)
		user.setUsername(this.getUserName());
		// No need to upload the userPicture to the server, since it is stored on the FaceBook server
		user.put(FileNames.UserProfile.CREDITS, this.getAmountOfCredits());
		user.put(FileNames.UserProfile.HE3, this.getAmountOfHe3());
		user.put(FileNames.Profile.XP, this.getUserXP());
		user.put(FileNames.Profile.BEST_SCORE, this.getUserBestScore());
		user.put(FileNames.UserProfile.TILT_SENSITIVITY, this.getTiltSensitivity());
		user.put(FileNames.UserProfile.SOUND, this.getSoundVolume());
		user.put(FileNames.UserProfile.MUSIC, this.getMusicVolume());
		user.put(FileNames.UserProfile.VIBRATION, this.getViabrationOn());
		// No need to upload the userFriends, since they are search for every time the user logs in as opposed to reading the friends
		saveUserSpaceShip(Profile.TYPE_SERVER, numberOfProfile);

		new Thread() { 

			@Override
			public void run() { 
				try { 
					user.save();
					// Every thing went well
					// Save User information feedback

					setLoadTextOfProgressionDialog("Saving Social ID...");
					this.delayTime(50);

					setLoadTextOfProgressionDialog("Saving Server ID...");
					this.delayTime(50);

					setLoadTextOfProgressionDialog("Saving User...");
					this.delayTime(50);

					setLoadTextOfProgressionDialog("Saving Username...");
					this.delayTime(50);

					setLoadTextOfProgressionDialog("Saving Credits...");
					this.delayTime(50);

					setLoadTextOfProgressionDialog("Saving He3...");
					this.delayTime(50);

					setLoadTextOfProgressionDialog("Saving XP...");
					this.delayTime(50);

					setLoadTextOfProgressionDialog("Saving Guns...");
					this.delayTime(50);

					setLoadTextOfProgressionDialog("Saving Best Score...");
					this.delayTime(50);

					setLoadTextOfProgressionDialog("Saving Tilt...");
					this.delayTime(50);

					setLoadTextOfProgressionDialog("Saving Sound...");
					this.delayTime(50);

					setLoadTextOfProgressionDialog("Saving Music...");
					this.delayTime(50);

					setLoadTextOfProgressionDialog("Saving Vibration...");
					this.delayTime(50);

					setLoadTextOfProgressionDialog("Saved user!");
				//	isInteractingWithServer.getAndDecrement();
					this.delayTime(1000);

					// Successfully signed the user out
					try {
						if(logOut) {
							ParseFacebookUtils.getFacebook().logout(getGame());
							ParseUser.logOut();
							getGame().getUserProfile().cleanProfileLocal(UserProfile.NUMBEROFPROFILE);
							setLoggedIn(false, UserProfile.TYPE_LOCAL);
							setLoggedIn(false, UserProfile.TYPE_SERVER);
							getGame().getGameLog().d(getClassTag(), "Successfully logged out");

						}
						//      logOut = true;
						getGame().getGameLog().d(getClassTag(), "Successfully uploaded data to the server");
						getGame().getGameLog().d(getClassTag(), "Saved profile: " + getUserName());

					}
					catch (MalformedURLException e1) {
						e1.printStackTrace();

					} 
					catch (IOException e1) {
						e1.printStackTrace();

					}

				}
				catch (ParseException e) {
					e.printStackTrace();
					// Something fucked up... 
					logOutType = UserProfile.LOGOUT_ERROR;
					serverErrorMessage = e.getMessage();
					// Stop any network requests to the server

				}
				
				SINOnCompleted(UserProfile.SAVE_PROFILE_SERVER);

			}

			public void delayTime(int time) {
				try {
					Thread.sleep(time);
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

			public void setLoadTextOfProgressionDialog(final String message) {
				if(SINRegistered()) {
					SINOnChanged(message);
					
				}
				
			}

		}.start();

	}
	
	public boolean isInteractingWithServer() {
		return this.isInteractingWithServer.get() > 0;
		
	}
	
	public void SINOnChanged(String message) {
		List<ServerInteractionNotifier> notifierCopy = new ArrayList<ServerInteractionNotifier>(this.notifier);
		for(ServerInteractionNotifier notifier : notifierCopy) {
			notifier.onChanged(message);
			
		}
		
	}
	
	public void SINOnCompleted(int action) {
		isInteractingWithServer.getAndDecrement();
		List<ServerInteractionNotifier> notifierCopy = new ArrayList<ServerInteractionNotifier>(this.notifier);
		for(ServerInteractionNotifier notifier : notifierCopy) {
			notifier.onCompleted(action);
			
		}
		
	}
	
	public boolean SINRegistered() {
		return this.notifier.size() != 0;
		
	}
	
	public void registerServerInteractionNotifier(ServerInteractionNotifier notifier) {
		this.notifier.add(notifier);
		
	}
	
	public void deregisterServerInteractionNotifier(ServerInteractionNotifier notifier) {
		this.notifier.remove(notifier);
		
	}
	
	// General Info
	
	public void setAmountOfCredits(int newAmountOfCredits) {
		this.amountOfCredits = newAmountOfCredits;
		
	}
	
	public void setAmountOfHe3(int newAmountOfHe3) {
		this.amountOfHe3 = newAmountOfHe3;
		
	}
	
	// Space Ship Info
	
	public void setSpaceship(Spaceship newSpaceship) {
		this.spaceShip = newSpaceship;
		
	}
	
	// Settings
	
	public void setTiltSensitivity(int newTiltSensitivity) {
		this.tiltSensitivity = newTiltSensitivity;
		
	}
	
	public void setSoundVolume(int newVolume) {
		this.soundVolume = newVolume;
		for(Sound sound : ((Assets) this.getGame().getGameAssets()).getAllSounds()) {
			sound.setVolume(this.soundVolume);
			
		}
		
	}
	
	public void setMusicVolume(int newVolume) {
		this.musicVolume = newVolume;
		for(Music music : ((Assets) this.getGame().getGameAssets()).getAllMusic()) {
			music.setVolume(this.musicVolume);
			
		}
		
	}
	
	public void setViabrationOn(boolean state) {
		this.vibrationOn = state;
		
	}
	
	// Getter methods
	
	public boolean isLoadingScoreScreenData() {
		return this.loadingScoreScreenData;
		
	}
	
	// General Info
	
	//public String currentlyLoading() {
	//	return this.currentlyLoading;
		
	//}
	
	public int getExperiencePointsForNextLevel() {
		return levelToExp(this.getUserLevel() + 1);
		
	}
	
	public int getAmountOfCredits() {
		return this.amountOfCredits;
		
	}
	
	public int getAmountOfHe3() {
		return this.amountOfHe3;
		
	}
	
	public int getPercentOfLevelCompleted() {
		return (int) (100.0/(levelToExp(this.getUserLevel() + 1) - levelToExp(this.getUserLevel())) * (this.getUserXP() - levelToExp(this.getUserLevel())));
		
	}
	
	public List<Profile> getSortedProfiles() {
		return this.sortedProfiles;
		
	}
	
	// Space Ship Info
	
	public Spaceship getSpaceship() {
		return this.spaceShip;
		
	}
	
	// Settings
	
	public int getTiltSensitivity() {
		return this.tiltSensitivity;
		
	}
	
	public int getSoundVolume() {
		return this.soundVolume;
		
	}
	
	public int getMusicVolume() {
		return this.musicVolume;
		
	}
	
	public boolean getViabrationOn() {
		return this.vibrationOn;
		
	}
	
	public interface ServerInteractionNotifier {

		// Called when the individal load state has changed
		public void onChanged(String message);
		// Called when the overall load state has completed
		public void onCompleted(int action);
		
	}
	
	public void enableSocial() {
		ParseFacebookUtils.logIn(this.getGame(), new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException err) {
				if(err != null) {
				//	currentlyLoading = "Network connection error.";
					getGame().getGameLog().d(getClassTag(), "Login aborded due to some fuck up");
					getGame().getGameLog().d(getClassTag(), err.getMessage());
					loginType = UserProfile.LOGIN_ERROR;
					
				}
				else if(user == null) {
					loginType = UserProfile.LOGIN_CANCELLED;
					
				}
				else if(user.isNew()) {
					getGame().getGameLog().d(getClassTag(), "User signed up and logged in through Facebook!");
					loginType = UserProfile.LOGIN_SIGNED_UP_AND_LOGGED_IN;
					new Thread() {

						@Override
						public void run() {
							
							enableSocialEnabled();
							
							setLoadTextOfProgressionDialog("Loading Social ID...");
							this.delayTime(50);
							setUserSocialID(FacebookUser.getFaceBookID());
							
							setLoadTextOfProgressionDialog("Loading Server ID...");
							this.delayTime(50);
							setUserServerID(ParseUser.getCurrentUser().getObjectId());
							
							setLoadTextOfProgressionDialog("Generating User...");
							loadUserFriends(UserProfile.TYPE_SERVER, UserProfile.NUMBEROFPROFILE); // This takes some time so just say "Downloading data..." instead of "Loading friends..."
							setUserPicture(FacebookUser.getProfilePicture(getUserSocialID(), FacebookUser.TYPE_LARGE)); // This takes some time so just say "Downloading data..." instead of "Loading User picture..."
							
							setLoadTextOfProgressionDialog("Finishing up...");
							saveProfileServer(UserProfile.NUMBEROFPROFILE);
							
							setLoadTextOfProgressionDialog("Generated user!");
							onLoadComplete();
							SINOnCompleted(UserProfile.LOAD_PROFILE_CREATE);
							this.delayTime(1000);
							
							// Successfully signed the user in
							saveProfileLocal(UserProfile.NUMBEROFPROFILE); // Safty first, incase the app crashes or something terible like that
							setLoggedIn(true, UserProfile.TYPE_LOCAL);
							setLoggedIn(true, UserProfile.TYPE_SERVER);
							
							getGame().getGameLog().d(getClassTag(), "Create and loaded profile: " + getUserName());
							
						}
						
						public void delayTime(int time) {
							try {
								Thread.sleep(time);
							} 
							catch (InterruptedException e) {
								e.printStackTrace();
							}
							
						}
						
						public void setLoadTextOfProgressionDialog(final String message) {
							if(SINRegistered()) {
								SINOnChanged(message);
								
							}
							
						}
						
					}.start();
							
				}
				else {
					loginType = UserProfile.LOGIN_LOGGED_IN;
					loadProfile(UserProfile.TYPE_SERVER, null);
					
				}
				
			}

			
		});
		
	}
	
	public void enableSocialEnabled() {
		this.getGame().getGameLog().d(this, "SocialEnabled: " + this.isSocialEnabled());
		this.getGame().getIO().writePrimitiveInternalMemory(Profile.FILENAME_PREFIX +  UserProfile.NUMBEROFPROFILE + "_" + FileNames.UserProfile.PROFILE_SOCIAL, true);
		
	}
	
	public boolean isSocialEnabled() {
		return this.getGame().getIO().readPrimitiveInternalMemoryBoolean(Profile.FILENAME_PREFIX +  UserProfile.NUMBEROFPROFILE + "_" + FileNames.UserProfile.PROFILE_SOCIAL);
		
	}
	
	
}