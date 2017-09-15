package com.skywalkerstudios.spacedroid.screens.scoresscreen;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;

import com.flurry.android.FlurryAgent;
import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.FileNames;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.Profiles.Profile;
import com.skywalkerstudios.spacedroid.Profiles.UserProfile;
import com.skywalkerstudios.spacedroid.Profiles.UserProfile.ServerInteractionNotifier;
import com.skywalkerstudios.spacedroid.screens.MenuScreen;
import com.skywalkerstudios.spacedroid.screens.standardobjects.DefaultBoard;
import com.skywalkerstudios.spacedroid.screens.standardobjects.DefaultSlideView;
import com.skywalkerstudios.spacedroid.screens.standardobjects.SlideViewInitializer;
import com.skywalkerstudios.spacedroid.screens.standardobjects.StandardScreen;
import com.webstorms.framework.graphics.animation.buttons.Button;
import com.webstorms.framework.graphics.animation.buttons.VirtualButton;
import com.webstorms.framework.graphics.animation.slideview.SlideViewOptions;

public class ScoresScreen extends StandardScreen implements SlideViewInitializer {
	
	// 33x26
	// x: 480 - (480 - 348) - 33 - (480 - 348 - 119)
	// y: 16 + (41 - 26)/2
	
	private final VirtualButton refreshButton = new VirtualButton(this.getGame(), "RefreshButton", this, 398, 713, new Rect(0, 0, 69, 69), Assets.scoresScreen_RefreshButton, Assets.scoresScreen_RefreshButtonClicked);
	private final String welcomeDialogMessage = "Compete against your friends and show off what you are capable of doing.";
	
	public ScoresScreen(SpaceDroid game) {
		super(game);
		
	}
	
	@Override
	public void load() {
		super.load();
		FlurryAgent.onPageView();
		FlurryAgent.logEvent("ScoresScreen has been launched");
		this.setWelcomeScreenMessage(this.welcomeDialogMessage);
		this.initializeSlideView(this);
		// this.spinnerOfRefreshIconShape.addRectangle(new Rect(0, 0, 53, 42));
		// this.spinnerOfRefreshIconShape.setLocation(398 + 8, 713 + 13);
		
		if(!this.getGame().getIO().readPrimitiveInternalMemoryBoolean(FileNames.Welcome.HAS_SHOWN_SCORES_SCREEN_WELCOME)) {
			this.setUpdateState(this.STATE_WELCOME);
			
		}
		
	}
	
	@Override
	public void initializeSlideView(SlideViewOptions options) {
		this.getGame().getGameLog().d(this, "Position" + this.getGame().getUserProfile().getUserPosition());
		options.setStartPosition(this.getGame().getUserProfile().getUserPosition());
		
		// Slideview Boards
		this.initializeSlideViewBoards((List<DefaultBoard>)(List<?>) new ArrayList<UserBoard>());
		
		// Slideview Info 
		this.initializeSlideViewInfo((List<Object>)(List<?>) new ArrayList<Profile>(this.getGame().getUserProfile().getSortedProfiles()));
		
	}
	
	@Override
	public void initializeBoard(int index) {
		this.getGame().getGameLog().d(this, "New Board being added!");
		this.getSlideViewBoards().add(new UserBoard(this.getGame(), (Profile) this.getSlideViewInfo().get(index)));
		
	}
	
	@Override
	public void onTouch(Integer x, Integer y) {
		super.onTouch(x, y);
		this.getGame().getGameLog().d(this, "onTouch");
		if(this.getUpdateState() == this.STATE_VIEWING) {
			this.getGame().getGameLog().d(this, "onTouch state: viewing");
			// Check if left navigation button should be updated
			if(this.getSlideView().getPositionOfBoardClosestToRestPosition() != 1) {
				// Left button
				this.getLeftNavigationButton().update(x, y);
				
			}
			// Check if right navigation button should be updated
			if(this.getSlideView().getPositionOfBoardClosestToRestPosition() != this.getSlideViewBoards().size()) {
				// Right button
				this.getRightNavigationButton().update(x, y);
				
			}
			if(!this.getGame().getUserProfile().isLoadingScoreScreenData()) {
				this.refreshButton.update(x, y);
				
			}
			this.getVirtualBackButton().update(x, y);
			
			// Update Slideview
		//	if(this.getGame().getInput().isScreenTouchedDown() && !this.getLeftNavigationButton().isPressed() && !this.getRightNavigationButton().isPressed() && !this.getVirtualBackButton().isPressed()) {
		//		this.getSlideView().update(DefaultSlideView.TYPE_ACTIVE, x);
		//		this.getGame().getGameLog().d(this, "Updating slideobjects");
				
		//	}
			
		}
		
	}
	
	@Override
	public void update() {
		super.update();
		if(this.getUpdateState() == this.STATE_VIEWING) {
			if(!this.getGame().getInput().isTouchDown() || this.getLeftNavigationButton().isPressed() || this.getRightNavigationButton().isPressed() 
					|| this.getVirtualBackButton().isPressed() || this.refreshButton.isPressed()) {
				this.getSlideView().update(DefaultSlideView.TYPE_UNACTIVE, null);
				
			}
			else {
				this.getSlideView().update(DefaultSlideView.TYPE_ACTIVE, this.getGame().getInput().getTouchX());
				
			}
			
		}
		else {
			this.getSlideView().update(DefaultSlideView.TYPE_UNACTIVE, null);
			
		}
		
	}
	
	@Override
	public void onButtonClicked(Button button) {
		if(this.getUpdateState() == this.STATE_WELCOME) {
			this.getGame().getIO().writePrimitiveInternalMemory(FileNames.Welcome.HAS_SHOWN_SCORES_SCREEN_WELCOME, true);
			
		}
		super.onButtonClicked(button);
		if(button.equals(this.getLeftNavigationButton())) {
			this.getSlideView().moveBoardToPosition(this.getSlideView().getPositionOfBoardClosestToRestPosition() - 1);
				
		}
		else if(button.equals(this.getRightNavigationButton())) {
			this.getSlideView().moveBoardToPosition(this.getSlideView().getPositionOfBoardClosestToRestPosition() + 1);
			
		}
		else if(button.equals(this.refreshButton)) {
			if(this.getGame().isNetworkAvailable()) {
				this.getGame().getUserProfile().registerServerInteractionNotifier(new ServerInteractionNotifier() {

					@Override
					public void onChanged(String message) {
						
					}
					
					@Override
					public void onCompleted(int action) {
						if(action == UserProfile.SAVE_PROFILE_SERVER && getGame().getCurrentScreen() instanceof ScoresScreen) {
							Assets.standardScreen_Notification.play();
							getGame().getGameLog().d(this, "Refreshing Score Screen Layout");
							refreshSlideViewData((SlideViewInitializer) getGame().getCurrentScreen());
							getGame().getUserProfile().deregisterServerInteractionNotifier(this);
							
						}
						
						
					}
					
				});
				getGame().getUserProfile().refreshScoreScreenData(UserProfile.NUMBEROFPROFILE);
				
			}
			else {
				this.setUpdateState(this.STATE_ERROR);
				this.setErrorType(this.ERROR_NO_NETWORK_CONNECTION);
				
			}
			
		}
		else if(button.equals(this.getVirtualBackButton()) || button.equals(this.getPhysicalBackButton()) || button.equals(this.getMenuButton())) {
			this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));
			
		}
		
	}

	@Override
	public void renderViewing() {
		super.renderViewing();
		this.getSlideView().render();
		// Check if left navigation button should be rendered
		if(this.getSlideView().getPositionOfBoardClosestToRestPosition() != 1) {
			// Left button
			this.getLeftNavigationButton().render();
			
		}
		// Check if right navigation button should be rendered
		if(this.getSlideView().getPositionOfBoardClosestToRestPosition() != this.getSlideViewBoards().size()) {
			// Right button
			this.getRightNavigationButton().render();
			
		}
		this.getVirtualBackButton().render();
		if(!this.getGame().getUserProfile().isLoadingScoreScreenData()) {
			this.refreshButton.render();
			
		}
		// this.getGame().getGraphics().drawBitmap(Assets.scoresScreen_RefreshIcon, Animation.rotateMatrix(Assets.scoresScreen_RefreshIcon, this.spinnerOfRefreshIconShape, this.spinnerOfRefreshIcon.getValue()), null);
		
	}
	
	
}
