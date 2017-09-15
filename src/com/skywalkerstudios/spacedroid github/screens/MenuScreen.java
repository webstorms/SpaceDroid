package com.skywalkerstudios.spacedroid.screens;

import android.graphics.Rect;

import com.flurry.android.FlurryAgent;
import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.FileNames;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.Profiles.UserProfile;
import com.skywalkerstudios.spacedroid.Profiles.UserProfile.ServerInteractionNotifier;
import com.skywalkerstudios.spacedroid.screens.hangarscreen.HangarScreen;
import com.skywalkerstudios.spacedroid.screens.playscreen.PlayScreen;
import com.skywalkerstudios.spacedroid.screens.scoresscreen.ScoresScreen;
import com.skywalkerstudios.spacedroid.screens.standardobjects.StandardScreen;
import com.skywalkerstudios.spacedroid.screens.storescreen.StoreScreen;
import com.webstorms.framework.graphics.animation.buttons.Button;
import com.webstorms.framework.graphics.animation.buttons.VirtualButton;

public class MenuScreen extends StandardScreen {
	
	protected final int STATE_GO_SOCIAL_ENQUIRY_WELCOME = this.getValueOfLastState() + 1;
	protected final int STATE_CREATING_LOCAL_PROFILE = this.getValueOfLastState() + 2;
	private final int ERROR_APP_CRASHED_LAST_VISIT = this.getValueOfLastError() + 1;
	private final int WARNING_NO_GUNS_IN_SHIP = this.getValueOfLastWarning() + 1;
	
	private final VirtualButton playButton = new VirtualButton(this.getGame(), "Play", this, 128, 299, new Rect(0, 0, 226, 95), Assets.menuScreen_PlayButton, Assets.menuScreen_PlayButtonClicked);
	private final VirtualButton storeButton = new VirtualButton(this.getGame(), "Store", this, 128, 421, new Rect(0, 0, 226, 95), Assets.menuScreen_StoreButton, Assets.menuScreen_StoreButtonClicked);
	private final VirtualButton hangarButton = new VirtualButton(this.getGame(), "Hangar", this, 128, 538, new Rect(0, 0, 226, 95), Assets.menuScreen_HangarButton, Assets.menuScreen_HangarButtonClicked);
	private final VirtualButton scoresButton = new VirtualButton(this.getGame(), "Scores", this, 128, 652, new Rect(0, 0, 226, 95), Assets.menuScreen_ScoresButton, Assets.menuScreen_ScoresButtonClicked);
	private final VirtualButton settingsButton = new VirtualButton(this.getGame(), "Settings", this, 13, 713, new Rect(0, 0, 69, 69), Assets.menuScreen_SettingsButton, Assets.menuScreen_SettingsButtonClicked);
	
	private final String errorAppCrashedLastVisit = "We appolgize for crashing. We will work on fixing it. It might help us if you tell us what exactly you were doing when the app crashed.";
	private final String warningDialogMessageNoGunsInShip = "There are no guns placed in the Spaceship. Continue.";
	private String welcomeDialogMessage;
	
	public MenuScreen(SpaceDroid game) {
		super(game);
		
	}
	
	@Override
	public void load() {
		super.load();
		FlurryAgent.onPageView();
		FlurryAgent.logEvent("MenuScreen has been launched");
		if(this.getGame().getCrashState() && !this.getGame().hasShownAppCrashNotification()) {
			this.setUpdateState(this.STATE_ERROR);
			this.setErrorType(this.ERROR_APP_CRASHED_LAST_VISIT);
			this.getGame().setShownAppCrashNotification();
			
		}
		else {
			checkInitState();
			
		}
		
	}
	
	private void checkInitState() {
		if(!this.getGame().getUserProfile().isLoggedIn(UserProfile.TYPE_LOCAL) && this.getGame().getIO().readPrimitiveInternalMemoryBoolean(FileNames.Welcome.HAS_SHOWN_MENU_SCREEN_WELCOME)) {
			this.getGame().getUserProfile().loadProfile(UserProfile.TYPE_LOCAL, UserProfile.NUMBEROFPROFILE);
			this.formatLayout();
			this.getGame().getGameLog().d(this, "State 1");
			
		}
		else if(!this.getGame().getUserProfile().isLoggedIn(UserProfile.TYPE_SERVER) && this.getGame().getUserProfile().isSocialEnabled()) {
			this.setUpdateState(this.STATE_GO_SOCIAL_ENQUIRY);
			this.getGame().getGameLog().d(this, "State 2");	
			
		}
		else if(!this.getGame().getUserProfile().isLoggedIn(UserProfile.TYPE_LOCAL) && !this.getGame().getIO().readPrimitiveInternalMemoryBoolean(FileNames.Welcome.HAS_SHOWN_MENU_SCREEN_WELCOME)) {
			this.setUpdateState(this.STATE_GO_SOCIAL_ENQUIRY_WELCOME);
			this.getGame().getGameLog().d(this, "State 3");	
			
		}
		
	}
	
	@Override
	public void onTouch(Integer x, Integer y) {
		super.onTouch(x, y);
		if(this.getUpdateState() == this.STATE_VIEWING) {
			// Play button
			this.playButton.update(x, y);
			
			// Store button
			this.storeButton.update(x, y);
			
			// Hangar button
			this.hangarButton.update(x, y);
			
			// Scores button
			this.scoresButton.update(x, y);
			
			// Settings button
			this.settingsButton.update(x, y);
			
		}
		else if(this.getUpdateState() == this.STATE_GO_SOCIAL_ENQUIRY_WELCOME) {
			this.getDialog().getYesButton().update(x, y);
			this.getDialog().getNoButton().update(x, y);
			
		}
		
	}
	
	@Override
	public void onButtonClicked(Button button) {
		if(this.getUpdateState() == this.STATE_WELCOME) {
			this.getGame().getIO().writePrimitiveInternalMemory(FileNames.Welcome.HAS_SHOWN_MENU_SCREEN_WELCOME, true);
			
		}
		super.onButtonClicked(button);
		if(this.getUpdateState() == this.STATE_VIEWING) { 
			if(button.equals(this.playButton)) {
				// When starting a new game
				if(((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getAmountOfGunSlotsInUse() == 0) {
					// Display a warning that the Spaceship does not contain any guns
					this.setUpdateState(this.STATE_WARNING);
					this.setWarningType(this.WARNING_NO_GUNS_IN_SHIP);
					
				}
				else {
					// Start the game
					this.createNewGame();
					
				}
				
			}
			else if(button.equals(this.storeButton)) {
				this.getGame().setScreen(new StoreScreen((SpaceDroid) this.getGame(), StoreScreen.START_POSITION_INITIAL));
				
			}
			else if(button.equals(this.hangarButton)) {
				this.getGame().setScreen(new HangarScreen((SpaceDroid) this.getGame()));
				
			}
			else if(button.equals(this.scoresButton)) {
				if(this.getGame().getUserProfile().isSocialEnabled()) {
					this.getGame().setScreen(new ScoresScreen((SpaceDroid) this.getGame()));
					
				}
				else {
					this.setUpdateState(this.STATE_GO_SOCIAL_ENQUIRY);
					
				}
				
			}
			else if(button.equals(this.settingsButton) || button.equals(this.getMenuButton())) {
				this.getGame().setScreen(new SettingsScreen((SpaceDroid) this.getGame()));
				
			}
			else if(button.equals(this.getPhysicalBackButton())) {
				if(this.getGame().getUserProfile().isLoggedIn(UserProfile.TYPE_LOCAL)) {
					this.getGame().getUserProfile().saveProfile(UserProfile.TYPE_LOCAL, UserProfile.NUMBEROFPROFILE);
					
				}
				this.getGame().finishGame(); 
					
			}
			
		}
		else if(this.getUpdateState() == this.STATE_WARNING) {
			if(button.equals(this.getDialog().getYesButton())) {
				this.createNewGame();
				this.setUpdateState(this.STATE_VIEWING);
				
			}
			else if(button.equals(this.getDialog().getNoButton()) || button.equals(this.getPhysicalBackButton())) {
				this.setUpdateState(this.STATE_VIEWING);
				
			}
			else if(button.equals(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));
				
			}
			
		}
		else if(this.getUpdateState() == this.STATE_ERROR) {
			if(button.equals(this.getDialog().getOkayButton()) || button.equals(this.getPhysicalBackButton()) || button.equals(this.getMenuButton())) {
				if(this.getErrorType() == this.ERROR_APP_CRASHED_LAST_VISIT) {
					this.setUpdateState(this.STATE_VIEWING);
					this.checkInitState();

				}

			}
			
		}
		else if(this.getUpdateState() == this.STATE_GO_SOCIAL_ENQUIRY_WELCOME) {
			if(button.equals(this.getDialog().getNoButton()) || button.equals(this.getPhysicalBackButton()) || button.equals(this.getMenuButton())) {
				// Set no facebook
				showProgressDialog("Log In", "Creating Profile...");
				setProgressDialogCancellable(true);
				this.getGame().getUserProfile().loadProfileCreate(UserProfile.NUMBEROFPROFILE, UserProfile.TYPE_CREATE_LOCAL);
				this.getGame().getUserProfile().registerServerInteractionNotifier(new ServerInteractionNotifier() {

					@Override
					public void onChanged(final String message) {
						setProgressDialogMessage(message);

					}

					@Override
					public void onCompleted(int action) {
						if(action == UserProfile.LOAD_PROFILE_CREATE) {
							Assets.standardScreen_Notification.play();
							getGame().getGameLog().d(this, "Reformat");
							formatLayout();
							getGame().getUserProfile().deregisterServerInteractionNotifier(this);
							getGame().getGameLog().d(this, "Logged in locally!");
							getProgressDialog().dismiss();
							setUpdateState(STATE_WELCOME);
							welcomeDialogMessage = "Welcome to Spacedroid, " + getGame().getUserProfile().getUserName() + ".\n\nYour quest is to save the human race by fleeing deep into space from the aliens.";
							setWelcomeScreenMessage(welcomeDialogMessage);
							
						}

					}

				});
				
				this.setUpdateState(this.STATE_CREATING_LOCAL_PROFILE);

			}
			else if(button.equals(this.getDialog().getYesButton())) {
				if(this.getGame().isNetworkAvailable()) {
					// Set facebook
					this.setUpdateState(this.STATE_CONNECTING_ONLINE_PROFILE);
					showProgressDialog("Go Social", "Creating Online Profile...");
					// this.setProgressDialogCancellable(false);
					this.getGame().getUserProfile().logIn();
					this.getGame().getUserProfile().registerServerInteractionNotifier(new ServerInteractionNotifier() {
						
						@Override
						public void onChanged(final String message) {
							setProgressDialogMessage(message);
							
						}

						@Override
						public void onCompleted(int action) {
							if(action == UserProfile.LOAD_PROFILE_CREATE || action == UserProfile.LOAD_PROFILE_SERVER) {
								Assets.standardScreen_Notification.play();
								getGame().getGameLog().d(this, "Reformat");
								formatLayout();
								getGame().getUserProfile().deregisterServerInteractionNotifier(this);
								getProgressDialog().dismiss();
								setUpdateState(STATE_WELCOME);
								welcomeDialogMessage = "Welcome to Spacedroid, " + getGame().getUserProfile().getUserName() + ".\n\nYour quest is to save the human race by fleeing deep into space from the aliens.";
								setWelcomeScreenMessage(welcomeDialogMessage);
								
							}
							
						}
						
					});
					
				}
				else {
					this.setUpdateState(this.STATE_ERROR);
					this.setErrorType(this.ERROR_NO_NETWORK_CONNECTION);
					
				}

			}

		}

	}
	
	@Override
	public void update() {
		super.update();
		if(this.getUpdateState() == this.STATE_CREATING_LOCAL_PROFILE) {
			// When the dialog is manually dismissed, close the app
			if(this.getProgressDialog() != null && !this.getProgressDialog().isShowing()) {
				this.getGame().getGameLog().d(this, "dialog manually dismissed");
				this.getGame().finishGame();

			}
			// Logged in
			// if(this.getGame().getUserProfile().isLoggedIn(UserProfile.TYPE_LOCAL) && this.getProgressDialog() != null && this.getProgressDialog().isShowing()) {
			//	this.getGame().getGameLog().d(this, "Logged in locally!");
			//	getProgressDialog().dismiss();
			//	this.setUpdateState(STATE_WELCOME);

			// }

		}

	}

	private void createNewGame() {
		if(((SpaceDroid) this.getGame()).getWorld() == null) {
			this.getGame().setScreen(new PlayScreen((SpaceDroid) this.getGame(), ((SpaceDroid) this.getGame()).createNewWorld()));
			
		}
		else {
			this.getGame().setScreen(new PlayScreen((SpaceDroid) this.getGame(), ((SpaceDroid) this.getGame()).getWorld()));
			
		}
		
	}
	
	@Override
	public void render() {
		super.render();
		if(this.getUpdateState() == this.STATE_GO_SOCIAL_ENQUIRY_WELCOME) {
			this.getDialog().renderCustomDialog("Go Social", this.goSocialEnquiryMessage, 313 + 20);
			this.getDialog().getYesButton().render();
			this.getDialog().getNoButton().render();
			
		}
		
	}

	@Override
	public void renderViewing() {
		super.renderViewing();
		this.playButton.render();
		this.storeButton.render();
		this.hangarButton.render();
		this.scoresButton.render();
		this.settingsButton.render();
		
	}

	@Override
	public void renderWarningDialog() {
		super.renderWarningDialog();
		if(this.getWarningType() == this.WARNING_NO_GUNS_IN_SHIP) {
			this.getDialog().renderWarningDialog(this.warningDialogMessageNoGunsInShip);
			
		}
		
	}
	
	@Override
	public void renderErrorDialog() {
		super.renderErrorDialog();
		if(this.getErrorType() == this.ERROR_APP_CRASHED_LAST_VISIT) {
			this.getDialog().renderErrorDialog(this.errorAppCrashedLastVisit);
			
		}
		
	}
	
	
}
