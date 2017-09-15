package com.skywalkerstudios.spacedroid.screens.storescreen.subscreens;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.merchandise.MerchandiseBoard;
import com.skywalkerstudios.spacedroid.merchandise.MerchandiseItem;
import com.skywalkerstudios.spacedroid.merchandise.MerchandiseViewable;
import com.skywalkerstudios.spacedroid.screens.MenuScreen;
import com.skywalkerstudios.spacedroid.screens.playscreen.World;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.PlayerDroid;
import com.skywalkerstudios.spacedroid.screens.standardobjects.DefaultBoard;
import com.skywalkerstudios.spacedroid.screens.standardobjects.DefaultSlideView;
import com.skywalkerstudios.spacedroid.screens.standardobjects.SlideViewInitializer;
import com.skywalkerstudios.spacedroid.screens.standardobjects.StandardScreen;
import com.skywalkerstudios.spacedroid.screens.storescreen.StoreScreen;
import com.webstorms.framework.graphics.animation.buttons.Button;
import com.webstorms.framework.graphics.animation.buttons.VirtualButton;
import com.webstorms.framework.graphics.animation.slideview.SlideViewOptions;

public abstract class StandardStoreScreen extends StandardScreen implements SlideViewInitializer {
	
	/*
	 * else if(button.equals(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));
				
			} should not be called so often
			
			
		- Don't like that slideview (in general) doesn't update unactive when buttons are pressed!	
		
	 */
	
	private final int STATE_GET_HE3_STATE_ENQUIRY = this.getValueOfLastState() + 1;
	
	// Error States that could occure when purchasing
	private final int ERROR_NOT_ENOUGH_CREDITS = this.getValueOfLastError() + 1;
	private final int ERROR_NOT_ENOUGH_HE3 = this.getValueOfLastError() + 2;
	private final int ERROR_LEVEL_NOT_HIGH_ENOUGH = this.getValueOfLastError() + 3;
	private final int ERROR_GENERAL = this.getValueOfLastError() + 4;
	
	private final VirtualButton getHe3LargeButton = new VirtualButton(this.getGame(), "Get He3", this, 108, 487, new Rect(0, 0, 265, 72), Assets.storesScreen_GetHe3ButtonLarge, Assets.storesScreen_GetHe3ButtonLargeClicked);
	private final VirtualButton getHe3Button = new VirtualButton(this.getGame(), "GetHe3", this, 109, 713, new Rect(0, 0, 122, 69), Assets.storesScreen_GetHe3Button, Assets.storesScreen_GetHe3ButtonClicked);
	private final VirtualButton infoButton = new VirtualButton(this.getGame(), "Info", this, 256, 713, new Rect(0, 0, 93, 69), Assets.standardScreen_InfoButton, Assets.standardScreen_InfoButtonClicked);
	private final VirtualButton buyButton = new VirtualButton(this.getGame(), "Buy", this, 375, 713, new Rect(0, 0, 93, 69), Assets.storesScreen_BuyButton, Assets.storesScreen_BuyButtonClicked);
	
	private final String enquieryDialogMessage = "Are you sure you would like to purchase this item.";
	private final String confirmationDialogMessage = "The item has succesfully been purchased.";
	private final String errorDialogMessageGeneral = "Sorry, due to the requirements not being met the item cannot be purchased.";
	private final String errorDialogMessageNotEnoughCredits = "Sorry, you do not possess enough credits to purchase this item.";
	private final String errorDialogMessageNotEnoughHe3 = "Sorry, you do not possess enough He3 to purchase this item.";
	private final String errorDialogMessageNotHighEnoughLevel = "Sorry, your level is not high enough to purchase this item.";
	private final String getHe3NotAvailableInBeta = "Sorry. Purchasing he3 with real cash is not available in the beta version of Spacedroid. We will get it working in the inaugaral version.";
	
	protected StandardStoreScreen(SpaceDroid game) {
		super(game);
	
	}
	
	@Override
	public void load() {
		super.load();
		this.initializeSlideView(this);
		
	}
	
	@Override
	protected int getValueOfLastError() {
		return this.ERROR_GENERAL;
		
	}
	
	@Override
	public void initializeSlideView(SlideViewOptions options) {
		options.setStartPosition(1);
		this.initializeSlideViewBoards((List<DefaultBoard>)(List<?>) new ArrayList<MerchandiseBoard>());
		
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
				
			this.infoButton.update(x, y);
			
			// Buy button
			this.buyButton.update(x, y);
				
			// Virtual Back button
			this.getVirtualBackButton().update(x, y);
			
			// Update Slideview
		//	if(this.getGame().getInput().isScreenTouchedDown() && !this.getLeftNavigationButton().isPressed() && !this.getRightNavigationButton().isPressed() && !this.getVirtualBackButton().isPressed()
		//			&& !this.getHe3Button.isPressed() && !this.infoButton.isPressed() && !this.buyButton.isPressed()) {
		//		this.getSlideView().update(DefaultSlideView.TYPE_ACTIVE, x);
				
		//	}
				
		}
		else if(this.getUpdateState() == this.STATE_ERROR && this.getErrorType() == this.ERROR_NOT_ENOUGH_HE3) {
			this.getHe3LargeButton.update(x, y);
			
		}
		else if(this.getUpdateState() == this.STATE_GET_HE3_STATE_ENQUIRY) {
			this.getDialog().getOkayButton().update(x, y);
			
		}
		
	}
	
	@Override
	public void onButtonClicked(Button button) {
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
			else if(button.equals(this.infoButton)) {
				this.setUpdateState(this.STATE_INFO);
				
			}
			else if(button.equals(this.buyButton)) {
				
				int amountOfErrors = 0;
				
				boolean hasEnoughCredits = ((SpaceDroid) this.getGame()).getUserProfile().getAmountOfCredits() >= ((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getCostOfCredits();
				boolean hasEnoughHe3 = ((SpaceDroid) this.getGame()).getUserProfile().getAmountOfHe3() >= ((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getCostOfHe3();
				boolean hasHighEnoughLevel = ((SpaceDroid) this.getGame()).getUserProfile().getUserLevel() >= ((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getRequiredLevel();
				
				if(!hasEnoughCredits) {
					amountOfErrors++;
					this.setErrorType(this.ERROR_NOT_ENOUGH_CREDITS);
					this.getGame().getGameLog().d(this, "Not enough credits");
					
				}
				
				if(!hasEnoughHe3) {
					amountOfErrors++;
					this.setErrorType(this.ERROR_NOT_ENOUGH_HE3);
					this.getGame().getGameLog().d(this, "Not enough He3");
					
				}
				
				if(!hasHighEnoughLevel) {
					amountOfErrors++;
					this.setErrorType(this.ERROR_LEVEL_NOT_HIGH_ENOUGH);
					this.getGame().getGameLog().d(this, "Not high enough level");
					
				}
				
				if(this.onCheckForErrors()) {
					amountOfErrors++;
					
				}
				
				if(amountOfErrors == 0) {
					this.setUpdateState(this.STATE_ENQUIRY);
					this.getGame().getGameLog().d(this, "All is fine");
					
				}
				else {
					this.setUpdateState(this.STATE_ERROR);
					this.getGame().getGameLog().d(this, "Atleast one fuck up");
					if(amountOfErrors > 1) {
						this.setErrorType(this.ERROR_GENERAL);
						this.getGame().getGameLog().d(this, "So many fuck ups..");
						
					}
					
				}
				
			}
			else if(button.equals(this.getVirtualBackButton()) || button.equals(this.getPhysicalBackButton())) {
				this.getGame().setScreen(new StoreScreen((SpaceDroid) this.getGame(), this.getStartPositionForStoreScreen()));
				
			}
			else if(button.equals(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));
				
			}
			
		}
		else if(this.getUpdateState() == this.STATE_INFO) {
			if(button.equals(this.getDialog().getOkayButton()) || button.equals(this.getPhysicalBackButton())) {
				this.setUpdateState(this.STATE_VIEWING);
				
			}
			else if(button.equals(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));
				
			}
			
		}
		else if(this.getUpdateState() == this.STATE_ENQUIRY) {
			if(button.equals(this.getDialog().getYesButton())) {
				if(this.onCheckForWarnings()) {
					this.setUpdateState(this.STATE_WARNING);
					
				}
				else {
					this.getGame().getGameLog().d(this, "Processing purchase...");
					
					// Process purchase
					this.transferFunds();
					this.onPurchase();
					if(this.getGame().getWorld() != null) {
						this.updatePlayerDroid(((World) this.getGame().getWorld()).getPlayerDroid());
						
					}
					this.setUpdateState(this.STATE_CONFIRMATION);
					
				}
				
			}
			else if(button.equals(this.getDialog().getNotNowButton()) || button.equals(this.getPhysicalBackButton())) {
				this.setUpdateState(this.STATE_VIEWING);
				
			}
			else if(button.equals(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));
				
			}
			
		}
		else if(this.getUpdateState() == this.STATE_CONFIRMATION) {
			if(button.equals(this.getDialog().getOkayButton()) || button.equals(this.getPhysicalBackButton())) {
				this.setUpdateState(this.STATE_VIEWING);
				
			}
			else if(button.equals(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));
				
			}
			
		}
		else if(this.getUpdateState() == this.STATE_ERROR) {
			if(button.equals(this.getDialog().getOkayButton()) || button.equals(this.getPhysicalBackButton())) {
				this.setUpdateState(this.STATE_VIEWING);
				
			}
			else if(button.equals(this.getHe3LargeButton)) {
				this.setUpdateState(this.STATE_GET_HE3_STATE_ENQUIRY);
				
			}
			else if(button.equals(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));
				
			}
			
		}
		else if(this.getUpdateState() == this.STATE_WARNING) {
			if(button.equals(this.getDialog().getYesButton())) {
				this.transferFunds();
				this.onPurchase();
				if(this.getGame().getWorld() != null) {
					this.updatePlayerDroid(((World) this.getGame().getWorld()).getPlayerDroid());
					
				}
				this.setUpdateState(this.STATE_CONFIRMATION);
				
			}
			else if(button.equals(this.getDialog().getNoButton()) || button.equals(this.getPhysicalBackButton())) {
				this.setUpdateState(this.STATE_VIEWING);
				
			}
			else if(button.equals(this.getMenuButton())) {
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
				|| this.getHe3Button.isPressed() || this.infoButton.isPressed() || this.buyButton.isPressed() 
				|| this.getVirtualBackButton().isPressed()) {
			this.getSlideView().update(DefaultSlideView.TYPE_UNACTIVE, null);
			
		}
		else {
			this.getSlideView().update(DefaultSlideView.TYPE_ACTIVE, this.getGame().getInput().getTouchX());
			
		}
		
	}
	
	@Override
	public void render() {
		super.render();
		if(this.getUpdateState() == this.STATE_GET_HE3_STATE_ENQUIRY) {
			this.getDialog().renderCustomDialog("Get He3", this.getHe3NotAvailableInBeta, 218 + 20);
			this.getDialog().getOkayButton().render();
			
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
		this.infoButton.render();
		this.buyButton.render();
		this.getVirtualBackButton().render();

	}
	
	@Override
	public void renderInfoDialog() {
		this.getDialog().renderInfoDialog("");
		
		// Render the requierments
		this.getGame().getGraphics().drawText("Credits: ", 108, 260, this.getDialog().getDialogTextConstants());
		this.getGame().getGraphics().drawText("He3: ", 108, 298, this.getDialog().getDialogTextConstants());
		this.getGame().getGraphics().drawText("Level: ", 108, 336, this.getDialog().getDialogTextConstants());
				
		// Render the values of the store item (Credits, He3, Level)
		this.getGame().getGraphics().drawText(String.valueOf(((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getCostOfCredits()), 373, 260, this.getDialog().getDialogTextValues());
		this.getGame().getGraphics().drawText(String.valueOf(((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getCostOfHe3()), 373, 298, this.getDialog().getDialogTextValues());
		this.getGame().getGraphics().drawText(String.valueOf(((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getRequiredLevel()), 373, 336, this.getDialog().getDialogTextValues());
		
	}
	
	@Override
	public void renderEnquiryDialog() {
		this.getDialog().renderEnquiryDialog(this.enquieryDialogMessage);
		
	}
	
	@Override
	public void renderConfirmationDialog() {
		this.getDialog().renderConfirmationDialog(this.confirmationDialogMessage);
		
	}
	
	@Override
	public void renderErrorDialog() {
		super.renderErrorDialog();
		if(this.getErrorType() == this.ERROR_NOT_ENOUGH_CREDITS) {
			this.getDialog().renderErrorDialog(this.errorDialogMessageNotEnoughCredits);
			
		}
		else if(this.getErrorType() == this.ERROR_NOT_ENOUGH_HE3) {
			this.getHe3LargeButton.render();
			this.getDialog().renderErrorDialog(this.errorDialogMessageNotEnoughHe3);
			
		}
		else if(this.getErrorType() == this.ERROR_LEVEL_NOT_HIGH_ENOUGH) {
			this.getDialog().renderErrorDialog(this.errorDialogMessageNotHighEnoughLevel);
			
		}
		else if(this.getErrorType() == this.ERROR_GENERAL) {
			this.getDialog().renderErrorDialog(this.errorDialogMessageGeneral);
			
		}
		
	}
	
	private void transferFunds() {
		// Pay for the new store item
		int creditsCapital = ((SpaceDroid) this.getGame()).getUserProfile().getAmountOfCredits();
		int creditsCost = ((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getCostOfCredits();
		int creditsBalance = creditsCapital - creditsCost;
		((SpaceDroid) this.getGame()).getUserProfile().setAmountOfCredits(creditsBalance);

		int he3Capital = ((SpaceDroid) this.getGame()).getUserProfile().getAmountOfHe3();
		int he3Cost = ((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getCostOfHe3();
		int he3Balance = he3Capital - he3Cost;
		((SpaceDroid) this.getGame()).getUserProfile().setAmountOfHe3(he3Balance);
		
	}
	
	protected VirtualButton getGetHe3Button() { 
		return this.getHe3Button;
		
	}
	
	protected VirtualButton getBuyButton() {
		return this.buyButton;
		
	}
	
	protected abstract void onPurchase();
	
	protected abstract void updatePlayerDroid(PlayerDroid playerDroid);
	
	protected abstract boolean onCheckForErrors();
	
	protected abstract boolean onCheckForWarnings();
	
	protected abstract int getStartPositionForStoreScreen();
	
	
}
