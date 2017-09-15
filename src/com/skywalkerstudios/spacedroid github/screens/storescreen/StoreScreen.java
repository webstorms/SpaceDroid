package com.skywalkerstudios.spacedroid.screens.storescreen;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;

import com.flurry.android.FlurryAgent;
import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.FileNames;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.merchandise.MerchandiseBoard;
import com.skywalkerstudios.spacedroid.merchandise.MerchandiseViewable;
import com.skywalkerstudios.spacedroid.screens.MenuScreen;
import com.skywalkerstudios.spacedroid.screens.standardobjects.DefaultBoard;
import com.skywalkerstudios.spacedroid.screens.standardobjects.DefaultSlideView;
import com.skywalkerstudios.spacedroid.screens.standardobjects.SlideViewInitializer;
import com.skywalkerstudios.spacedroid.screens.standardobjects.StandardScreen;
import com.skywalkerstudios.spacedroid.screens.storescreen.subscreens.AmmoScreen;
import com.skywalkerstudios.spacedroid.screens.storescreen.subscreens.GunsScreen;
import com.skywalkerstudios.spacedroid.screens.storescreen.subscreens.SpaceshipsScreen;
import com.webstorms.framework.graphics.animation.buttons.Button;
import com.webstorms.framework.graphics.animation.buttons.VirtualButton;
import com.webstorms.framework.graphics.animation.slideview.SlideViewOptions;

public class StoreScreen extends StandardScreen implements SlideViewInitializer {

	public static final int START_POSITION_INITIAL = 1;
	public static final int START_POSITION_AMMO = 1;
	public static final int START_POSITION_GUNS = 2;
	public static final int START_POSITION_SPACESHIPS = 3;
	
	private final int STATE_GET_HE3_STATE_ENQUIRY = this.getValueOfLastState() + 1;
	
	private final VirtualButton getHe3Button = new VirtualButton(this.getGame(), "GetHe3", this, 109, 713, new Rect(0, 0, 122, 69), Assets.storesScreen_GetHe3Button, Assets.storesScreen_GetHe3ButtonClicked);
	private final VirtualButton buyButton = new VirtualButton(this.getGame(), "Buy", this, 375, 713, new Rect(0, 0, 93, 69), Assets.storesScreen_BuyButton, Assets.storesScreen_BuyButtonClicked);
	
	private int startPosition;
	private final String welcomeDialogMessage = "Aspire to get the greatest spaceship and equipt it with the best guns and ammo. Here you will be able to purchase new goods. Goods cost credits and He3 and require a special level for purchase.";
	private final String getHe3NotAvailableInBeta = "Sorry. Purchasing he3 with real cash is not available in the beta version of Spacedroid. We will get it working in the inaugaral version.";
	
	public StoreScreen(SpaceDroid game, int startPosition) {
		super(game);
		this.startPosition = startPosition;
		
	}
	
	@Override
	public void load() {
		super.load();
		FlurryAgent.onPageView();
		FlurryAgent.logEvent("StoreScreen has been launched");
		this.setWelcomeScreenMessage(this.welcomeDialogMessage);
		this.initializeSlideView(this);
		
		if(!this.getGame().getIO().readPrimitiveInternalMemoryBoolean(FileNames.Welcome.HAS_SHOWN_STORE_SCREEN_WELCOME)) {
			this.setUpdateState(this.STATE_WELCOME);
			
		}
		
	}
	
	@Override
	public void initializeSlideView(SlideViewOptions options) {
		options.setStartPosition(this.startPosition);
		
		this.initializeSlideViewBoards((List<DefaultBoard>)(List<?>) new ArrayList<MerchandiseBoard>());
		
		// Slideview Info 
		this.initializeSlideViewInfo((List<Object>)(List<?>) new ArrayList<MerchandiseViewable>());
		this.getSlideViewInfo().add(new Ammo(this.getGame()));
		this.getSlideViewInfo().add(new Guns(this.getGame()));
		this.getSlideViewInfo().add(new Spaceships(this.getGame()));
		
	}
	
	@Override
	public void initializeBoard(int index) {
		this.getSlideViewBoards().add(new MerchandiseBoard(this.getGame(), (MerchandiseViewable) this.getSlideViewInfo().get(index)));
		
	}
	
	@Override
	public void onTouch(Integer x, Integer y) {
		super.onTouch(x, y);
		if(this.getUpdateState() == this.STATE_VIEWING) {
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
				
			// GetHe3 button
			this.getHe3Button.update(x, y);
			
			// Buy button
			this.buyButton.update(x, y);
				
			// Virtual Back button
			this.getVirtualBackButton().update(x, y);
			
			// Update Slideview
		//	if(this.getGame().getInput().isScreenTouchedDown() && !this.getLeftNavigationButton().isPressed() && !this.getRightNavigationButton().isPressed() && !this.getVirtualBackButton().isPressed()
		//			&& !this.getHe3Button.isPressed() && !this.buyButton.isPressed()) {
		//		this.getSlideView().update(DefaultSlideView.TYPE_ACTIVE, x);
				
		//	}
				
		}
		else if(this.getUpdateState() == this.STATE_GET_HE3_STATE_ENQUIRY) {
			this.getDialog().getOkayButton().update(x, y);
			
		}
		
	}
	
	@Override
	public void onButtonClicked(Button button) {
		if(this.getUpdateState() == this.STATE_WELCOME) {
			this.getGame().getIO().writePrimitiveInternalMemory(FileNames.Welcome.HAS_SHOWN_STORE_SCREEN_WELCOME, true);
			
		}
		super.onButtonClicked(button);
		if(this.getUpdateState() == this.STATE_VIEWING) {
			if(button.equals(this.getLeftNavigationButton())) {
				this.getSlideView().moveBoardToPosition(this.getSlideView().getPositionOfBoardClosestToRestPosition() - 1);
				
			}
			else if(button.equals(this.getRightNavigationButton())) {
				this.getSlideView().moveBoardToPosition(this.getSlideView().getPositionOfBoardClosestToRestPosition() + 1);
				
			}
			else if(button.equals(this.getHe3Button)) {
				this.setUpdateState(this.STATE_GET_HE3_STATE_ENQUIRY);
				
			}
			else if(button.equals(this.buyButton)) {
				if(((MerchandiseViewable) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getClassTag().equals(Ammo.CLASS_TAG)) {
					this.getGame().setScreen(new AmmoScreen(this.getGame()));
					
				}
				else if(((MerchandiseViewable) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getClassTag().equals(Guns.CLASS_TAG)) {
					this.getGame().setScreen(new GunsScreen(this.getGame()));
					
				}
				else if(((MerchandiseViewable) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getClassTag().equals(Spaceships.CLASS_TAG)) {
					this.getGame().setScreen(new SpaceshipsScreen(this.getGame()));
					
				}
				
			}
			else if(button.equals(this.getVirtualBackButton()) || button.equals(this.getPhysicalBackButton()) || button.equals(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));
				
			}
			
		}
		else if(this.getUpdateState() == this.STATE_GET_HE3_STATE_ENQUIRY) {
			if(button.equals(this.getDialog().getOkayButton())) {
				this.setUpdateState(this.STATE_VIEWING);
				
			}
			
		}
		
	}

	@Override
	public void update() {
		super.update();
		if(!this.getGame().getInput().isTouchDown() || this.getUpdateState() != this.STATE_VIEWING || this.getLeftNavigationButton().isPressed() || this.getRightNavigationButton().isPressed() 
				|| this.getHe3Button.isPressed() || this.buyButton.isPressed() || this.getVirtualBackButton().isPressed()) {
			this.getSlideView().update(DefaultSlideView.TYPE_UNACTIVE, null);
			
		}
		else {
			this.getSlideView().update(DefaultSlideView.TYPE_ACTIVE, this.getGame().getInput().getTouchX());
			
		}
		
	}
	
	@Override
	public void renderViewing() {
		super.renderViewing();
		this.getSlideView().render();
		// Check if left navigation button should be rendered
		if(this.getSlideView().isRestBoardFirst() == false) {
			// Left button
			this.getLeftNavigationButton().render();
			
		}
		// Check if right navigation button should be rendered
		if(this.getSlideView().isRestBoardLast() == false) {
			// Right button
			this.getRightNavigationButton().render();
			
		}
		this.getHe3Button.render();
		this.buyButton.render();
		this.getVirtualBackButton().render();
		
	}

	@Override
	public void render() {
		super.render();
		if(this.getUpdateState() == this.STATE_GET_HE3_STATE_ENQUIRY) {
			this.getDialog().renderCustomDialog("Get He3", this.getHe3NotAvailableInBeta, 218 + 20);
			this.getDialog().getOkayButton().render();
			
		}
		
	}
	
	
}
