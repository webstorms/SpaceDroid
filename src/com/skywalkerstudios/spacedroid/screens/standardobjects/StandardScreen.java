package com.skywalkerstudios.spacedroid.screens.standardobjects;

import java.util.List;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.view.KeyEvent;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.FileNames;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.Profiles.UserProfile;
import com.skywalkerstudios.spacedroid.Profiles.UserProfile.ServerInteractionNotifier;
import com.skywalkerstudios.spacedroid.screens.MenuScreen;
import com.skywalkerstudios.spacedroid.screens.scoresscreen.UserBoard;
import com.webstorms.framework.Screen;
import com.webstorms.framework.graphics.animation.Animation;
import com.webstorms.framework.graphics.animation.buttons.Button;
import com.webstorms.framework.graphics.animation.buttons.ButtonListener;
import com.webstorms.framework.graphics.animation.buttons.PhysicalButton;
import com.webstorms.framework.graphics.animation.buttons.VirtualButton;
import com.webstorms.framework.graphics.animation.slideview.Board;
import com.webstorms.framework.graphics.animation.slideview.SlideViewOptions;
import com.webstorms.framework.input.OnKeyListener;
import com.webstorms.framework.input.OnTouchListener;
import com.webstorms.framework.util.Util;
import com.webstorms.physics.Shape;

public class StandardScreen extends Screen implements ButtonListener, OnTouchListener, OnKeyListener {
	
	/* Just have a getSlideView method instead of init method
	 * Change getGame to protecgted
	 * Change init of all General Add-on to init in the get methods
	 * 
	 * Don't like UserBoard import!
	 * 
	 * all 4 update methods should not be in the WSGameEngine, however in this class  // Or not? Fuu... I don't know...
	 * 
	 * User can't log out when some other server operation is in process!
	 * 
	 */
	
	protected final int STATE_VIEWING = 0;
	protected final int STATE_INFO = 1;
	protected final int STATE_ENQUIRY = 2;
	protected final int STATE_CONFIRMATION = 3;
	protected final int STATE_ERROR = 4;
	protected final int STATE_WARNING = 5;
	private final int STATE_LOGOUT = 6;
	protected final int STATE_WELCOME = 7;
	protected final int STATE_GO_SOCIAL_ENQUIRY = 8;
	protected final int STATE_CONNECTING_ONLINE_PROFILE = 9;
	
	protected final int ERROR_NO_NETWORK_CONNECTION = 0;
	protected final int ERROR_OUTSTANDING_NETWORD_CONNECTION = 1;
	protected final int ERROR_NETWORD_CONNECTION_PROBLEM = 2;
	
	private final String errorDialogMessageNoNetworkConnection = "Network connection fail. Please connect to the internet";
	private final String errorDialogMessageOutstandingNetworkConnection = "There is an outstanding Network Connection. Please wait.";
	private final String errorDialogMessageNetworkConnectionProblem = "Network connection fail. Try again later.";
	protected final String goSocialEnquiryMessage = "Go Social by connecting SpaceDroid to a Facebook account (recommended). This will enable you to have more fun by competing against your friends.";
	
	private int updateState = this.STATE_VIEWING;
	private int errorType;
	private int warningType;
	
	// Background
	private final VirtualButton logOutButton = new VirtualButton(this.getGame(), "LogOut", this, 348, 16, new Rect(0, 0, 119, 41), Assets.standardScreen_LogOutButton, Assets.standardScreen_LogOutButtonClicked);
	private final VirtualButton goSocialButton = new VirtualButton(this.getGame(), "GoSocial", this, 348, 16, new Rect(0, 0, 119, 41), Assets.standardScreen_LoginButton, Assets.standardScreen_LoginButtonClicked);
	private final Paint userName = new Paint();
	private final Paint level = new Paint();
	private final Paint experiencePoints = new Paint();
	private final Paint amountOfHe3 = new Paint();
	private final Paint amountOfCredits = new Paint();
	private int levelTextX;
	private int experiencePointsX;
	private final Shape progressionBarShape = new Shape(81, 90, new Rect(0, 0, 384, 37)); 
	
	// General Compulsory
	//private Integer touchX;
	//private Integer touchY;
	//private Integer touchKey;
	private final VirtualButton leftNavigationButton = new VirtualButton(this.getGame(), "LeftNavigation", this, 13, 577, new Rect(0, 0, 69, 69), Assets.standardScreen_BackButton, Assets.standardScreen_BackButtonClicked);
	private final VirtualButton rightNavigationButton = new VirtualButton(this.getGame(), "RightNavigation", this, 398, 577, new Rect(0, 0, 69, 69), Assets.standardScreen_ForwardButton, Assets.standardScreen_ForwardButtonClicked);
	private final VirtualButton virtualBackButton = new VirtualButton(this.getGame(), "VirtualBack", this, 13, 713, new Rect(0, 0, 69, 69), Assets.standardScreen_BackButton, Assets.standardScreen_BackButtonClicked);
	private final PhysicalButton physicalBackButton = new PhysicalButton(this.getGame(), "PhysicalBack", this, KeyEvent.KEYCODE_BACK);
	private final PhysicalButton physicalMenuButton = new PhysicalButton(this.getGame(), "Menu", this, KeyEvent.KEYCODE_MENU);
	
	// General Add-on
	private DefaultSlideView slideView;
	private SlideViewOptions slideViewOptions;
	private List<Object> slideViewInfo; // Gets initialized in child class
	private List<DefaultBoard> slideViewBoards; // Gets initialized in child class
	private Dialog dialog;
	
	ProgressDialog progressDialog;
	
	private final Shape spinnerOfSmallRefreshIconShape = new Shape();
	
	private String welcomeScreenMessage;
	
	protected StandardScreen(SpaceDroid game) {
		super(game);
		
	}
	
	@Override
	public void load() {
		// Initialize Paints
		userName.setColor(Color.rgb(215, 215, 205));
		userName.setTypeface(Assets.standardScreen_Birdman);
		userName.setTextSize(31);

		level.setColor(Color.rgb(215, 215, 205));
		level.setTypeface(Assets.standardScreen_Birdman);
		level.setTextSize(27);

		experiencePoints.setColor(Color.rgb(215, 215, 205));
		experiencePoints.setTypeface(Assets.standardScreen_Birdman);
		experiencePoints.setTextSize(23);

		amountOfHe3.setColor(Color.rgb(215, 215, 205));
		amountOfHe3.setTypeface(Assets.standardScreen_Birdman);
		amountOfHe3.setTextSize(29);
		amountOfHe3.setTextAlign(Align.RIGHT);

		amountOfCredits.setColor(Color.rgb(215, 215, 205));
		amountOfCredits.setTypeface(Assets.standardScreen_Birdman);
		amountOfCredits.setTextSize(29);
		amountOfCredits.setTextAlign(Align.RIGHT);
		
		// Initialze Other

		// Get level text coordinates
		this.formatLayout();

		Rect bounds = new Rect();
		String level = String.valueOf((String.valueOf(((SpaceDroid) this.getGame()).getUserProfile().getUserXP()) + " / " + ((SpaceDroid) this.getGame()).getUserProfile().getExperiencePointsForNextLevel()));
		this.experiencePoints.getTextBounds(level, 0, level.length(), bounds);
		this.spinnerOfSmallRefreshIconShape.addRectangle(new Rect(0, 0, 33, 26));
		this.spinnerOfSmallRefreshIconShape.setLocation(302, 24);
		
		this.dialog = new Dialog(this);
		
	}

	protected int getValueOfLastState() {
		return this.STATE_CONNECTING_ONLINE_PROFILE;
		
	}
	
	protected int getValueOfLastError() {
		return this.ERROR_NETWORD_CONNECTION_PROBLEM;
		
	}
	
	protected int getValueOfLastWarning() {
		return -1; // No warnings
		
	}
	
	@Override
	public void resume() {
		this.getGame().getInput().useTouchscreen(true);
		this.getGame().getInput().useKeyboard(true);
		this.getGame().getInput().setOnTouchListener(this);
		this.getGame().getInput().setOnKeyListener(this);
		Assets.standardScreen_StandardScreenMusic.loop(true);
		Assets.standardScreen_StandardScreenMusic.play();
		
	}
	
	@Override
	public void pause() {
		super.pause();
		Assets.standardScreen_StandardScreenMusic.pause();
		
	}

	@Override
	public void onTouch(Integer x, Integer y) {
		this.getGame().getGameLog().d(this, "onTouch has been called X: " + x + " Y: " + y);
		if(this.updateState == this.STATE_VIEWING) {
			if(this.getGame().getUserProfile().isSocialEnabled()) {
				this.logOutButton.update(x, y);
				
			}
			else {
				this.goSocialButton.update(x, y);
				
			}
			
		}
		else if(this.getUpdateState() == this.STATE_INFO) {
			this.dialog.getOkayButton().update(x, y);
			
		}
		else if(this.getUpdateState() == this.STATE_ENQUIRY) {
			this.dialog.getYesButton().update(x, y);
			this.dialog.getNotNowButton().update(x, y);
			
		}
		else if(this.getUpdateState() == this.STATE_CONFIRMATION) {
			this.dialog.getOkayButton().update(x, y);
			
		}
		else if(this.getUpdateState() == this.STATE_ERROR) {
			this.dialog.getOkayButton().update(x, y);
			
		}
		else if(this.getUpdateState() == this.STATE_WARNING) {
			this.dialog.getYesButton().update(x, y);
			this.dialog.getNoButton().update(x, y);
			
		}
		else if(this.getUpdateState() == this.STATE_WELCOME) {
			this.dialog.getOkayButton().update(x, y);

		}
		else if(this.getUpdateState() == this.STATE_GO_SOCIAL_ENQUIRY) {
			if(!this.getGame().getUserProfile().isSocialEnabled()) {
				this.dialog.getYesButton().update(x, y);
				this.dialog.getNoButton().update(x, y);
				
			}
			else {
				this.dialog.getOkayButton().update(x, y);
				
			}
			
		}

	}

	@Override
	public void onKey(Integer key) {
		this.physicalBackButton.update(key);
		this.physicalMenuButton.update(key);
		
	}
	
	public void showProgressDialog(final String title, final String message) {
		this.getGame().runOnUiThread(new Runnable() {
			public void run() {
				progressDialog = null;
				progressDialog = ProgressDialog.show(getGame(), title, message, true);
				progressDialog.setCancelable(true);
				progressDialog.setCanceledOnTouchOutside(false);
				
			}

		});
		
	}
	
	public void setProgressDialogMessage(final String message) {
		getGame().runOnUiThread(new Runnable() {
			public void run() {
				progressDialog.setMessage(message);
				
			}

		});
		
	}
	
	public void setProgressDialogCancellable(final boolean cancellable) {
		// this.cancellable = cancellable;
		this.getGame().runOnUiThread(new Runnable() {
			public void run() {
				progressDialog.setCancelable(cancellable);

			}	

		});
		
	}	
	
	@Override
	public void onButtonClicked(Button button) {
		this.getGame().getGameLog().d(this, button.getName());
		this.getGame().getGameLog().d(this, "STATE: " + this.getUpdateState());
		
		if(this.dialog.isShowing()) {
			this.dialog.disposeDialog();
			
		}
		Assets.standardScreen_Click.play();

		if(this.updateState == this.STATE_VIEWING) {
			if(button.equals(this.logOutButton)) {
				if(this.getGame().isNetworkAvailable()) {
					if(!this.getGame().getUserProfile().isInteractingWithServer()) {
						//	this.getGame().setScreen(new LogOutScreen2((SpaceDroid) this.getGame()));
						((SpaceDroid) this.getGame()).getUserProfile().logOut();
						this.showProgressDialog("Log Out", "Connectig to the server...");

						getGame().getUserProfile().registerServerInteractionNotifier(new ServerInteractionNotifier() {
							@Override
							public void onChanged(final String message) {
								setProgressDialogMessage(message);

							}

							@Override
							public void onCompleted(int action) {
								if(action == UserProfile.SAVE_PROFILE_SERVER) {
									Assets.standardScreen_Notification.play();
									getGame().getUserProfile().deregisterServerInteractionNotifier(this);
									
								}

							}

						});

						this.setUpdateState(this.STATE_LOGOUT);
						
					}
					else {
						this.setUpdateState(this.STATE_ERROR);
						this.setErrorType(this.ERROR_OUTSTANDING_NETWORD_CONNECTION);
						
					}
					
				}
				else {
					this.setUpdateState(this.STATE_ERROR);
					this.setErrorType(this.ERROR_NO_NETWORK_CONNECTION);
					
				}

			}
			else if(button.equals(this.goSocialButton)) {
				this.updateState = this.STATE_GO_SOCIAL_ENQUIRY;
				
			}

		}
		else if(this.getUpdateState() == this.STATE_ERROR) {
			if(button.equals(this.getDialog().getOkayButton()) || button.equals(this.getPhysicalBackButton()) || button.equals(this.getMenuButton())) {
				if(this.getErrorType() == this.ERROR_NO_NETWORK_CONNECTION) {
					if(!this.getGame().getIO().readPrimitiveInternalMemoryBoolean(FileNames.Welcome.HAS_SHOWN_MENU_SCREEN_WELCOME) && !this.getGame().getUserProfile().isLoggedIn(UserProfile.TYPE_LOCAL)) {
						this.getGame().finishGame();
						
					}
					else {
						this.setUpdateState(this.STATE_VIEWING);
						
					}
					

				}
				else if(this.getErrorType() == this.ERROR_OUTSTANDING_NETWORD_CONNECTION) {
					this.setUpdateState(this.STATE_VIEWING);

				}
				else if(this.getErrorType() == this.ERROR_NETWORD_CONNECTION_PROBLEM) {
					this.setUpdateState(this.STATE_VIEWING);

				}

			}

		}
		else if(this.getUpdateState() == this.STATE_WELCOME) {
			if(button.equals(this.getDialog().getOkayButton()) || button.equals(this.getPhysicalBackButton())) {
				this.updateState = this.STATE_VIEWING;
				
			}
			else if(button.equals(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen(this.getGame()));
				
			}
			
		}
		else if(this.getUpdateState() == this.STATE_GO_SOCIAL_ENQUIRY) {
			if(button.equals(this.getDialog().getYesButton())) {
				if(this.getGame().isNetworkAvailable()) {
					this.updateState = this.STATE_CONNECTING_ONLINE_PROFILE;
					showProgressDialog("Go Social", "Creating Online Profile...");
					// this.setProgressDialogCancellable(false);
					this.getGame().getUserProfile().enableSocial();
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
								updateState = STATE_VIEWING;
								getProgressDialog().dismiss();
								
							}
							
						}
						
					});
					
				}
				else {
					this.setUpdateState(this.STATE_ERROR);
					this.setErrorType(this.ERROR_NO_NETWORK_CONNECTION);
					
				}
				
			}
			else if(button.equals(this.getDialog().getOkayButton())) {
				if(this.getGame().isNetworkAvailable()) {
					// Set facebook
					this.setUpdateState(this.STATE_CONNECTING_ONLINE_PROFILE);
					showProgressDialog("Go Social", "Connecting to Online Profile...");
					// this.setProgressDialogCancellable(false);
					this.getGame().getUserProfile().logIn();
					this.getGame().getUserProfile().registerServerInteractionNotifier(new ServerInteractionNotifier() {
						
						@Override
						public void onChanged(final String message) {
							setProgressDialogMessage(message);
							
						}

						@Override
						public void onCompleted(int action) {
							if(action == UserProfile.LOAD_PROFILE_SERVER) {
								Assets.standardScreen_Notification.play();
								getGame().getGameLog().d(this, "Reformat");
								formatLayout();
								getGame().getUserProfile().deregisterServerInteractionNotifier(this);
								updateState = STATE_VIEWING;
								getProgressDialog().dismiss();
								
							}
							
						}
						
					});
					
				}
				else {
					this.setUpdateState(this.STATE_ERROR);
					this.setErrorType(this.ERROR_NO_NETWORK_CONNECTION);
					
				}
				
			}
			else if(button.equals(this.dialog.getNoButton()) || button.equals(this.getPhysicalBackButton()) || button.equals(this.getMenuButton())) {
				this.updateState = this.STATE_VIEWING;
				
			}
			
		}
		
	}
	
	/*
	public void onNoNetworkConnection() {
		if(this.getGame().getUserProfile().isLoggedIn(UserProfile.TYPE_LOCAL)) {
			this.getGame().getUserProfile().saveProfile(Profile.TYPE_LOCAL, UserProfile.NUMBEROFPROFILE);
			
		}
		this.getGame().finishGame();
		
	} */
	
	@Override
	public void update() {
		if(this.getGame().getUserProfile().isInteractingWithServer()) {
			this.getGame().getSpinner().update();
			
		}
		if(this.getUpdateState() == this.STATE_LOGOUT) {
			// User aborded logout
			if(this.progressDialog != null && !this.progressDialog.isShowing()) {
				this.getGame().getGameLog().d(this, "User aborded logout");
				this.progressDialog.dismiss();
				this.getGame().finishGame();
				
			}
			// User succesfully loged out
			if(!this.getGame().getUserProfile().isLoggedIn(UserProfile.TYPE_SERVER) && !this.getGame().getUserProfile().isLoggedIn(UserProfile.TYPE_LOCAL)) {
				this.getGame().getGameLog().d(this, "User succesfully loged out");
				this.progressDialog.dismiss();
				this.getGame().finishGame();

			}
			// Some fuck up, log out process exited
			else if(this.getGame().getUserProfile().getLogOutType() == UserProfile.LOGOUT_ERROR && this.progressDialog.isShowing()) {
				this.getGame().getGameLog().d(this, "Some fuck up, log out process exited");
				this.progressDialog.dismiss();
				this.setUpdateState(this.STATE_ERROR);
				this.setErrorType(this.ERROR_NETWORD_CONNECTION_PROBLEM);

			}

		}
		else if(this.updateState == this.STATE_CONNECTING_ONLINE_PROFILE) {
			// Creating Profile
			if(this.getGame().getUserProfile().getLoginType() == UserProfile.LOGIN_SIGNED_UP_AND_LOGGED_IN) {
				this.getGame().getGameLog().d(this, "Cancelling the bitch!");
				this.setProgressDialogCancellable(false);

			}
			// Logged in
		//	if(this.getGame().getUserProfile().isLoggedIn(UserProfile.TYPE_SERVER) && this.getGame().getUserProfile().isLoggedIn(UserProfile.TYPE_LOCAL)) {
		//		this.getGame().getGameLog().d(this, "Logged in!");
		//		getProgressDialog().dismiss();
		//		this.updateState = this.STATE_VIEWING;

		//	}
			// Facebook Login cancelled 
			else if(this.getGame().getUserProfile().getLoginType() == UserProfile.LOGIN_CANCELLED && this.getProgressDialog() != null && this.getProgressDialog().isShowing()) {
				this.getGame().getGameLog().d(this, "Facebook Login cancelled");
				getProgressDialog().dismiss();
				this.getGame().finishGame();

			}
			// When the dialog is manually dismissed, close the app
			else if(this.getProgressDialog() != null && !this.getProgressDialog().isShowing() && this.getGame().getUserProfile().getLoginType() != UserProfile.LOGIN_ERROR) { // LOGIN_ERROR, because it might have been canceled due to network erre
				this.getGame().getGameLog().d(this, "dialog manually dismissed");
				this.getGame().finishGame();

			}
			// Error login in
			else if(this.getGame().getUserProfile().getLoginType() == UserProfile.LOGIN_ERROR && this.getProgressDialog() != null && this.getProgressDialog().isShowing()) {
				this.getGame().getGameLog().d(this, "Error login in");
				this.getProgressDialog().dismiss();
				this.setUpdateState(this.STATE_ERROR);
				this.setErrorType(this.ERROR_NETWORD_CONNECTION_PROBLEM);

			}
			
		}
		
	}
	
	protected void initializeSlideView(SlideViewInitializer initializer) {
		this.slideViewOptions = new SlideViewOptions();
		initializer.initializeSlideView(this.slideViewOptions);
		
		// Initialize boards with provided information
		for(int i = 0; i < this.getSlideViewInfo().size(); i++) {
			initializer.initializeBoard(i);
			
		}
		
		this.slideViewOptions.setDistanceOfGapBetweenBoards(20);
		this.slideViewOptions.setRestPosition(this.getGame().getWSScreen().getGameScreenWidth()/2 - UserBoard.BIG_USERBOARD_WIDTH/2);
		this.slideViewOptions.setSuppliedBoards((List<Board>)(List<?>) this.slideViewBoards);
		
		this.slideView = new DefaultSlideView(this.getGame(), this.slideViewOptions, new Rect(0, 0, 480, 45));
		
	}

	protected void refreshSlideViewData(SlideViewInitializer initializer) {
		 
		// Save locations of all the boards
		Point[] locations = new Point[this.slideViewBoards.size()];
		for(int i = 0; i < this.getSlideViewInfo().size(); i++) {
			int x = this.slideView.getSlideViewInfo().getSuppliedBoards().get(i).getX();
			int y = this.slideView.getSlideViewInfo().getSuppliedBoards().get(i).getY();
			locations[i] = new Point(x, y);
			
		}
		
		initializer.initializeSlideView(this.slideViewOptions);
		
		// Initialize boards with provided information and saved locations
		for(int i = 0; i < this.slideViewInfo.size(); i++) {
			
			initializer.initializeBoard(i);
			
			// Use saved locations
			if(i < locations.length) {
				this.slideViewBoards.get(i).setLocation(locations[i].x, locations[i].y);
				
			}
			else {
				this.slideViewBoards.get(i).setLocation(locations[locations.length - 1].x + (i + 1 - locations.length) * this.slideViewOptions.getDistanceOfGapBetweenBoards(), locations[locations.length - 1].y);
				
			}
			
			
		}
		
		// Apply the list
		this.getGame().getGameLog().d(this, "Size of slideViewBoards: " + this.slideViewBoards.size());
		this.slideViewOptions.setSuppliedBoards((List<Board>)(List<?>) this.slideViewBoards);
		this.getGame().getGameLog().d(this, "Size of slideViewBoards: " + this.slideViewOptions.getSuppliedBoards().size());
		// this.slideView.setSlideViewInfo(this.slideViewOptions);
		this.slideView = new DefaultSlideView(this.getGame(), this.slideViewOptions, new Rect(0, 0, 480, 45));
		
		this.getSlideView().update(DefaultSlideView.TYPE_UNACTIVE, null); // Make sure all boards are formated correctly
		
	}
	
	// Setter methods

	protected void setErrorType(int errorType) {
		this.errorType = errorType;
		
	}
		
	protected void setWarningType(int warningType) {
		this.warningType = warningType;
		
	}
	
	protected void setUpdateState(int updateState) {
		this.updateState = updateState;
		
	}
	
	public void initializeSlideViewInfo(List<Object> slideViewData) {
		this.slideViewInfo = slideViewData;
		
	}
	
	public void initializeSlideViewBoards(List<DefaultBoard> slideViewBoards) {
		this.slideViewBoards = slideViewBoards;
		
	}
	
	// Getter methods
	
	public ProgressDialog getProgressDialog() {
		return this.progressDialog;
		
	}
	
	protected int getErrorType() {
		return this.errorType;
		
	}
		
	protected int getWarningType() {
		return this.warningType;
		
	}
	
	protected Dialog getDialog() {
		return this.dialog;
		
	}
	
	@Override
	public SpaceDroid getGame() {
		return (SpaceDroid) super.getGame();
		
	}
	
	//protected boolean isScreenTouched() {
	//	return this.touchX != null && this.touchY != null;
		
	//}
	
//	protected Integer getTouchX() {
//		return this.touchX;
		
//	}
	
//	protected Integer getTouchY() {
//		return this.touchY;
//		
//	}
	
	protected int getUpdateState() {
		return this.updateState;
		
	}
	
//	protected int getTouchKey() {
//		return this.touchKey;
		
//	}
	
	protected VirtualButton getLeftNavigationButton() {
		return this.leftNavigationButton;
		
	}
	
	protected VirtualButton getRightNavigationButton() {
		return this.rightNavigationButton;
		
	}
	
	protected VirtualButton getVirtualBackButton() {
		return this.virtualBackButton;
		
	}
	
	protected PhysicalButton getPhysicalBackButton() {
		return this.physicalBackButton;
		
	}
	
	protected PhysicalButton getMenuButton() {
		return this.physicalMenuButton;
		
	}
	
	protected DefaultSlideView getSlideView() {
		return this.slideView;
		
	}
	
	protected List<Object> getSlideViewInfo() {
		return this.slideViewInfo;
		
	}
	
	protected List<DefaultBoard> getSlideViewBoards() {
		return this.slideViewBoards;
		
	}
	
	public void formatLayout() {
		this.levelTextX = Util.centerObject((int) this.level.measureText(String.valueOf(((SpaceDroid) this.getGame()).getUserProfile().getUserLevel())), 14, 65);
		this.experiencePointsX = Util.centerObject((int) this.experiencePoints.measureText((String.valueOf(((SpaceDroid) this.getGame()).getUserProfile().getUserXP()) + " / " + ((SpaceDroid) this.getGame()).getUserProfile().getExperiencePointsForNextLevel())), 77, 469);
		
	}
	
	@Override
	public void render() {
		this.renderBackground();
		this.renderViewing();
	//	if(this.updateState == StandardScreen.STATE_VIEWING) {
	//		this.renderViewing();
			
	//	}
		if(this.getUpdateState() == this.STATE_INFO) {
			this.renderInfoDialog();
			
		}
		else if(this.getUpdateState() == this.STATE_ENQUIRY) {
			this.renderEnquiryDialog();
			
		}
		else if(this.getUpdateState() == this.STATE_CONFIRMATION) {
			this.renderConfirmationDialog();
			
		}
		else if(this.getUpdateState() == this.STATE_ERROR) {
			this.renderErrorDialog();
			
		}
		else if(this.getUpdateState() == this.STATE_WARNING) {
			this.renderWarningDialog();
			
		}
		else if(this.getUpdateState() == this.STATE_WELCOME) {
			this.dialog.renderWelcomeDialog(this.welcomeScreenMessage);
			
		}
		else if(this.getUpdateState() == this.STATE_GO_SOCIAL_ENQUIRY) {
			if(!this.getGame().getUserProfile().isSocialEnabled()) {
				this.dialog.renderCustomDialog("Go Social", this.goSocialEnquiryMessage, 313 + 20);
				this.dialog.getYesButton().render();
				this.dialog.getNoButton().render();
				
			}
			else {
				this.dialog.renderCustomDialog("Go Social", this.goSocialEnquiryMessage, 218 + 20);
				this.dialog.getOkayButton().render();
				
			}
			
		}
		
	}
	
	public void setWelcomeScreenMessage(String welcomeScreenMessage) {
		this.welcomeScreenMessage = welcomeScreenMessage;
		
	}
	
	private void renderBackground() {
		// Background
		this.getGame().getGraphics().drawBitmap(Assets.standardScreen_Background, null, this.getGame().getWSScreen().getGameScreenDimensions(), null);
		
		// User name
		this.getGame().getGraphics().drawText(((SpaceDroid) this.getGame()).getUserProfile().getUserName(), 13, 54, this.userName);
		
		// Level
		this.getGame().getGraphics().drawText(String.valueOf(((SpaceDroid) this.getGame()).getUserProfile().getUserLevel()), this.levelTextX, 120, this.level);
		
		// Experience points
		this.getGame().getGraphics().drawBitmap(Assets.standardScreen_ProgressionBar, null, new Rect(this.progressionBarShape.getX(), this.progressionBarShape.getY(), this.progressionBarShape.getX() + this.progressionBarShape.getWidth() * ((SpaceDroid) this.getGame()).getUserProfile().getPercentOfLevelCompleted()/100, this.progressionBarShape.getY() + this.progressionBarShape.getHeight()), null);
		this.getGame().getGraphics().drawText(String.valueOf(((SpaceDroid) this.getGame()).getUserProfile().getUserXP() + " / " + ((SpaceDroid) this.getGame()).getUserProfile().getExperiencePointsForNextLevel()), this.experiencePointsX, 118, this.experiencePoints);
		
		// Credits
		this.getGame().getGraphics().drawText(String.valueOf(((SpaceDroid) this.getGame()).getUserProfile().getAmountOfCredits()), 469, 186, this.amountOfCredits);
		
		// He3
		this.getGame().getGraphics().drawText(String.valueOf(((SpaceDroid) this.getGame()).getUserProfile().getAmountOfHe3()), 469, 240, this.amountOfHe3);
				
	}
	
	public void renderViewing() {
		if(this.getGame().getUserProfile().isInteractingWithServer()) {
			this.getGame().getGraphics().drawBitmap(Assets.scoresScreen_RefreshIcon, Animation.rotateMatrix(Assets.scoresScreen_RefreshIcon, this.spinnerOfSmallRefreshIconShape, this.getGame().getSpinner().getValue()), null);
			
		}
		if(this.getGame().getUserProfile().isSocialEnabled()) {
			this.logOutButton.render();
			
		}
		else {
			this.goSocialButton.render();
			
		}
		
	}
	
	// Dialog overridable rendering methods
	
	public void renderInfoDialog() {
		

	}

	public void renderEnquiryDialog() {
		

	}

	public void renderConfirmationDialog() {
		

	}

	public void renderErrorDialog() {
		if(this.getErrorType() == this.ERROR_NO_NETWORK_CONNECTION) {
			this.dialog.renderErrorDialog(this.errorDialogMessageNoNetworkConnection);
			
		}
		else if(this.getErrorType() == this.ERROR_OUTSTANDING_NETWORD_CONNECTION) {
			this.dialog.renderErrorDialog(this.errorDialogMessageOutstandingNetworkConnection);
			
		}
		else if(this.getErrorType() == this.ERROR_NETWORD_CONNECTION_PROBLEM) {
			this.dialog.renderErrorDialog(this.errorDialogMessageNetworkConnectionProblem + this.getGame().getUserProfile().getServerErrorMessage());
			
		}
		
	}

	public void renderWarningDialog() {
		

	}
	
	
}
