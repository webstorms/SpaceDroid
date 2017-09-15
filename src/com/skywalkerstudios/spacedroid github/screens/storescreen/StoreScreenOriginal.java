package com.skywalkerstudios.spacedroid.screens.storescreen;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.merchandise.MerchandiseBoard;
import com.skywalkerstudios.spacedroid.merchandise.MerchandiseItem;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo1;
import com.skywalkerstudios.spacedroid.merchandise.guns.Gun;
import com.skywalkerstudios.spacedroid.merchandise.guns.Gun1;
import com.skywalkerstudios.spacedroid.merchandise.guns.Gun2;
import com.skywalkerstudios.spacedroid.merchandise.guns.Gun3;
import com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship;
import com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship1;
import com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship2;
import com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship3;
import com.skywalkerstudios.spacedroid.screens.MenuScreen;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.Lasers.AmmoBase;
import com.skywalkerstudios.spacedroid.screens.standardobjects.DefaultBoard;
import com.skywalkerstudios.spacedroid.screens.standardobjects.DefaultSlideView;
import com.skywalkerstudios.spacedroid.screens.standardobjects.StandardScreen;
import com.webstorms.framework.Screen;
import com.webstorms.framework.graphics.animation.buttons.VirtualButton;

public class StoreScreenOriginal  { // extends StandardScreen
	/*
	private static final int STATE_GET_HE3_STATE_ENQUIRY = 6;
	
	// Error States that could occure when purchasing
	private static final int ERROR_NOT_ENOUGH_CREDITS = 0;
	private static final int ERROR_NOT_ENOUGH_HE3 = 1;
	private static final int ERROR_LEVEL_NOT_HIGH_ENOUGH = 2;
	private static final int ERROR_NOT_ENOUGH_SPACE_FOR_GUNS = 3;
	private static final int ERROR_NOT_ENOUGH_SPACE_FOR_AMMO = 4;
	private static final int ERROR_GENERAL = 5;
	
	// Warning States that could occure when purchasing
	private static final int WARNING_SPACESHIP_ALREADY_PURCHASED = 0;
	private static final int WARNING_GUNS_WILL_BE_LOST = 1;
	
	private final VirtualButton getHe3Button = new VirtualButton(this.getGame(), "GetHe3", 109, 713, new Rect(0, 0, 122, 69), Assets.storesScreen_GetHe3Button, Assets.storesScreen_GetHe3ButtonClicked, this);
	private final VirtualButton infoButton = new VirtualButton(this.getGame(), "Info", 256, 713, new Rect(0, 0, 93, 69), Assets.standardScreen_InfoButton, Assets.standardScreen_InfoButtonClicked, this);
	private final VirtualButton buyButton = new VirtualButton(this.getGame(), "Buy", 375, 713, new Rect(0, 0, 93, 69), Assets.storesScreen_BuyButton, Assets.storesScreen_BuyButtonClicked, this);
	
	
	// Buy Inquiery Dialog
	private final String enquieryDialogMessage = "Are you sure you would like to purchase this item.";
	private final StaticLayout enquieryDialogLayout = new StaticLayout(this.enquieryDialogMessage, this.getDialogTextConstants(), 265, Layout.Alignment.ALIGN_NORMAL, 1.4f, 1f, false);
	
	// Buy Confirmation Dialog
	private final String confirmationDialogMessage = "The item has succesfully been purchased.";
	private final StaticLayout confirmationDialogLayout = new StaticLayout(this.confirmationDialogMessage, this.getDialogTextConstants(), 265, Layout.Alignment.ALIGN_NORMAL, 1.4f, 1f, false);
	
	// Buy Error Dialog
	private final String erorDialogMessageGeneral = "Sorry, due to the requirements not being met the item cannot be purchased.";
	private final StaticLayout errorDialogLayoutGeneral = new StaticLayout(this.erorDialogMessageGeneral, this.getDialogTextConstants(), 265, Layout.Alignment.ALIGN_NORMAL, 1.4f, 1f, false);
	
	private final String errorDialogMessageNotEnoughCredits = "Sorry, you do not possess enough credits to purchase this item.";
	private final StaticLayout errorDialogLayoutNotEnoughCredits = new StaticLayout(this.errorDialogMessageNotEnoughCredits, this.getDialogTextConstants(), 265, Layout.Alignment.ALIGN_NORMAL, 1.4f, 1f, false);
	
	private final String errorDialogMessageNotEnoughHe3 = "Sorry, you do not possess enough He3 to purchase this item.";
	private final StaticLayout errorDialogLayoutNotEnoughHe3 = new StaticLayout(this.errorDialogMessageNotEnoughHe3, this.getDialogTextConstants(), 265, Layout.Alignment.ALIGN_NORMAL, 1.4f, 1f, false);
	
	private final String errorDialogMessageNotHighEnoughLevelHe3 = "Sorry, your level is not high enough to purchase this item.";
	private final StaticLayout errorDialogLayoutNotHighEnoughLevel = new StaticLayout(this.errorDialogMessageNotHighEnoughLevelHe3, this.getDialogTextConstants(), 265, Layout.Alignment.ALIGN_NORMAL, 1.4f, 1f, false);
	
	private final String errorDialogMessageNotEnoughSpaceInSpacehip = "Sorry, your gun slots in your Spaceship are all in use";
	private final StaticLayout errorDialogLayoutNotEnoughSpaceInSpacehip = new StaticLayout(this.errorDialogMessageNotEnoughSpaceInSpacehip, this.getDialogTextConstants(), 265, Layout.Alignment.ALIGN_NORMAL, 1.4f, 1f, false);
	
	private final String errorDialogMessageNotEnoughSpaceForAmmo = "Sorry, the ammo in the Spaship is already full.";
	private final StaticLayout errorDialogLayoutNotEnoughSpaceForAmmo = new StaticLayout(this.errorDialogMessageNotEnoughSpaceForAmmo, this.getDialogTextConstants(), 265, Layout.Alignment.ALIGN_NORMAL, 1.4f, 1f, false);
	
	// Buy Warning Dialog
	private final String warningDialogMessageNotEnoughSpaceForAllGunsInSpaceship = "Not enough slots for all guns. Continue.";
	private final StaticLayout warningDialogLayoutNotEnoughSpaceForAllGunsInSpaceship = new StaticLayout(this.warningDialogMessageNotEnoughSpaceForAllGunsInSpaceship, this.getDialogTextConstants(), 265, Layout.Alignment.ALIGN_NORMAL, 1.4f, 1f, false);
	
	private final String warningDialogMessageDuplicateSpaceship = "You already possess this Spaceship. Continue.";
	private final StaticLayout warningDialogLayoutDuplicateSpaceship = new StaticLayout(this.warningDialogMessageDuplicateSpaceship, this.getDialogTextConstants(), 265, Layout.Alignment.ALIGN_NORMAL, 1.4f, 1f, false);
	
	public StoreScreenOriginal(SpaceDroid game) {
		super(game);
		
		// Initialze all the Store items
		this.setSlideViewData((List<Object>)(List<?>) new ArrayList<MerchandiseItem>());
		
		// Add Ammo
		this.getSlideViewData().add(new Ammo1());
		
		// Add all guns to storeItems holder
		this.getSlideViewData().add(new Gun1());
		this.getSlideViewData().add(new Gun2());
		this.getSlideViewData().add(new Gun3());
		
		// Add all spacehips to storeItems holder
		this.getSlideViewData().add(new Spaceship1());
		this.getSlideViewData().add(new Spaceship2());
		this.getSlideViewData().add(new Spaceship3());
		
		this.setSlideViewBoards((List<DefaultBoard>)(List<?>) new ArrayList<MerchandiseBoard>());
		this.initializeBoards(this.getSlideViewBoards(), (List<MerchandiseItem>)(List<?>) this.getSlideViewData());
		
		this.getSlideView().setStartPosition(1);
		this.getSlideView().setBoards((List<Board>)(List<?>) this.getSlideViewBoards());
		
	}

	private void initializeBoards(List<DefaultBoard> boards, List<MerchandiseItem> storeItems) {
		for(int amountOfStoreIems = 0; amountOfStoreIems < storeItems.size(); amountOfStoreIems++) {
			boards.add(new MerchandiseBoard((SpaceDroid) this.getGame(), storeItems.get(amountOfStoreIems)));
			boards.get(amountOfStoreIems).setPosition(amountOfStoreIems);
			
		}
		
	}
	
	@Override
	public void introTransitionUpdate() {
		super.introTransitionUpdate();
		
	}
	
	@Override
	public void introTransitionCompletedUpdate() {
		super.introTransitionCompletedUpdate();
		
		if(!this.isScreenTouched()) {
			this.getSlideView().update(DefaultSlideView.TYPE_UNACTIVE, this.getTouchX());
			
		}
		
		if(this.getUpdateState() == StoreScreen.STATE_VIEWING) {
			// Check if left navigation button should be updated
			if(this.getSlideView().getPositionOfBoardClosestToRestPosition() != 1) {
				// Left button
				this.getLeftNavigationButton().update(this.getTouchX(), this.getTouchY());
				
			}
			
			// Check if right navigation button should be updated
			if(this.getSlideView().getPositionOfBoardClosestToRestPosition() != this.getSlideViewBoards().size()) {
				// Right button
				this.getRightNavigationButton().update(this.getTouchX(), this.getTouchY());
				
			}
				
			// GetHe3 button
			this.getHe3Button.update(this.getTouchX(), this.getTouchY());
				
			// Info button
			this.infoButton.update(this.getTouchX(), this.getTouchY());
				
			// Buy button
			this.buyButton.update(this.getTouchX(), this.getTouchY());
				
			// Virtual Back button
			this.getVirtualBackButton().update(this.getTouchX(), this.getTouchY());
				
			// Update Slideview
			if(this.isScreenTouched() && !this.getLeftNavigationButton().isPressed() && !this.getRightNavigationButton().isPressed() && !this.getVirtualBackButton().isPressed()
					&& !this.getHe3Button.isPressed() && !this.infoButton.isPressed() && !this.buyButton.isPressed()) {
				this.getSlideView().update(DefaultSlideView.TYPE_ACTIVE, this.getTouchX());
				
			}
				
		}
		else if(this.getUpdateState() == StoreScreen.STATE_GET_HE3_STATE_ENQUIRY) {
			
		}
		
	}
	
	@Override
	public void outTransitionUpdate() {
		super.outTransitionUpdate();
		
	}

	@Override
	public void outTransitionCompletedUpdate() {
		super.outTransitionCompletedUpdate();
		if(this.getUpdateState() == StoreScreen.STATE_VIEWING) {
			if(ButtonManager.isClickedButton(this.getLeftNavigationButton())) {
				// While the touch screen is not clicked move the boards to the right
				if(this.getGame().getInput().isScreenTouchedDown() != true) {
					if(this.getSlideView().moveBoardToPosition(this.getSlideView().getPositionOfBoardClosestToRestPosition() - 1) == true) {
						this.setTransitionUpdateType(Screen.INTRO_TRANSITION_UPDATE);
						
					}
					
				}
				else {
					this.setTransitionUpdateType(Screen.INTRO_TRANSITION_UPDATE);
					
				}
				
			}
			else if(ButtonManager.isClickedButton(this.getRightNavigationButton())) {
				// While the touch screen is not clicked move the boards to the left
				if(this.getGame().getInput().isScreenTouchedDown() != true) {
					if(this.getSlideView().moveBoardToPosition(this.getSlideView().getPositionOfBoardClosestToRestPosition() + 1) == true) {
						this.setTransitionUpdateType(Screen.INTRO_TRANSITION_UPDATE);
						
					}
					
				}
				else {
					this.setTransitionUpdateType(Screen.INTRO_TRANSITION_UPDATE);
					
				}
				
			}
			else if(ButtonManager.isClickedButton(this.getHe3Button)) {
				// this.setUpdateState(StoreScreen.STATE_GET_HE3_STATE_ENQUIRY);
				this.setTransitionUpdateType(Screen.INTRO_TRANSITION_UPDATE);
				this.setTransitionUpdateType(Screen.INTRO_TRANSITION_UPDATE);
				
			}
			else if(ButtonManager.isClickedButton(this.infoButton)) {
				// Refresh variables of ammo, price and amount of ammo
				if(this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition()) instanceof Ammo1) {
					((Ammo1) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).refresh(this.getGame());
					
				}
				this.setUpdateState(StoreScreen.STATE_INFO);
				this.setTransitionUpdateType(Screen.INTRO_TRANSITION_UPDATE);
				
			}
			else if(ButtonManager.isClickedButton(this.buyButton)) {
				// Check for Errors
				boolean hasEnoughCredits = ((SpaceDroid) this.getGame()).getUserProfile().getAmountOfCredits() >= ((MerchandiseItem) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getCostOfCredits();
				boolean hasEnoughHe3 = ((SpaceDroid) this.getGame()).getUserProfile().getAmountOfHe3() >= ((MerchandiseItem) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getCostOfHe3();
				boolean hasHighEnoughLevel = ((SpaceDroid) this.getGame()).getUserProfile().getUserLevel() >= ((MerchandiseItem) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getRequiredLevel();
				boolean hasEnoughGunSlotsInSpaceship = ((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().isSpaceForNewGun();
				boolean hasEnoughSpaceForAmmo = ((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().isSpaceForMoreAmmo(this.getGame());
				
				int amountOfErrors = 0;
				
				// Check if new Gun can be purchased
				if(this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition()) instanceof Gun && hasEnoughGunSlotsInSpaceship == false) {
					amountOfErrors++;
					this.setUpdateState(StoreScreen.STATE_ERROR);
					this.setErrorType(StoreScreen.ERROR_NOT_ENOUGH_SPACE_FOR_GUNS);
					this.getGame().getGameLog().d(this, "Not enough gun slots");
					
				}
				
				if(this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition()) instanceof Ammo1 && hasEnoughSpaceForAmmo == false) {
					amountOfErrors++;
					this.setUpdateState(StoreScreen.STATE_ERROR);
					this.setErrorType(StoreScreen.ERROR_NOT_ENOUGH_SPACE_FOR_AMMO);
					this.getGame().getGameLog().d(this, "Not enough space for ammo");
					
				}
				
				if(hasEnoughCredits == false) {
					amountOfErrors++;
					this.setUpdateState(StoreScreen.STATE_ERROR);
					this.setErrorType(StoreScreen.ERROR_NOT_ENOUGH_CREDITS);
					this.getGame().getGameLog().d(this, "Not enough credits");
					
				}
				
				if(hasEnoughHe3 == false) {
					amountOfErrors++;
					this.setUpdateState(StoreScreen.STATE_ERROR);
					this.setErrorType(StoreScreen.ERROR_NOT_ENOUGH_HE3);
					this.getGame().getGameLog().d(this, "Not enough He3");
					
				}
				
				if(hasHighEnoughLevel == false) {
					amountOfErrors++;
					this.setUpdateState(StoreScreen.STATE_ERROR);
					this.setErrorType(StoreScreen.ERROR_LEVEL_NOT_HIGH_ENOUGH);
					this.getGame().getGameLog().d(this, "Not high enough level");
					
				}
				
				if(amountOfErrors > 1) {
					this.setErrorType(StoreScreen.ERROR_GENERAL);
					this.getGame().getGameLog().d(this, "So many fuck ups..");
					
				}
				
				if(amountOfErrors == 0) {
					this.setUpdateState(StoreScreen.STATE_ENQUIRY);
					this.getGame().getGameLog().d(this, "All is fine");
					
				}
				
				this.setTransitionUpdateType(Screen.INTRO_TRANSITION_UPDATE);
				
			}
			else if(ButtonManager.isClickedButton(this.getVirtualBackButton()) || ButtonManager.isClickedButton(this.getPhysicalBackButton()) || ButtonManager.isClickedButton(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));
				
			}
			
		}
		else if(this.getUpdateState() == StoreScreen.STATE_GET_HE3_STATE_ENQUIRY) {
			
		}
		else if(this.getUpdateState() == StoreScreen.STATE_INFO) {
			if(ButtonManager.isClickedButton(this.getOkayButton()) || ButtonManager.isClickedButton(this.getPhysicalBackButton())) {
				this.setUpdateState(StoreScreen.STATE_VIEWING);
				this.setTransitionUpdateType(Screen.INTRO_TRANSITION_UPDATE);
				
			}
			else if(ButtonManager.isClickedButton(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));
				
			}
			
		}
		else if(this.getUpdateState() == StoreScreen.STATE_ENQUIRY) {
			if(ButtonManager.isClickedButton(this.getYesButton())) {
				// Display Warnings if any
				if(this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition()) instanceof Spaceship) {
					
					// Check if the current Spaceship is the same that the user would like to purchase
					boolean isTheSameSpaceship = ((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().classTAG.equals(((MerchandiseItem) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).classTAG);
					
					if(isTheSameSpaceship == true) {
						this.setUpdateState(StoreScreen.STATE_WARNING);
						this.setWarningType(StoreScreen.WARNING_SPACESHIP_ALREADY_PURCHASED);
						this.getGame().getGameLog().d(this, "Spaceship is already in use!");
						
					}
					
					// Check that there is enough free gun slots for all the guns
					int currentAmountOfGunSlots = ((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getAmountOfGunSlotsInUse();
					int thisSpaceshipAmoutOfGunSlots = ((Spaceship) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getAmountOfGunSlots();
					
					this.getGame().getGameLog().d(this, "currentAmountOfGunSlots: " + currentAmountOfGunSlots);
					this.getGame().getGameLog().d(this, "thisSpaceshipAmoutOfGunSlots: " + thisSpaceshipAmoutOfGunSlots);
					
					if(currentAmountOfGunSlots > thisSpaceshipAmoutOfGunSlots) {
						this.setUpdateState(StoreScreen.STATE_WARNING);
						this.setWarningType(StoreScreen.WARNING_GUNS_WILL_BE_LOST);
						this.getGame().getGameLog().d(this, "Guns will be lost!");
						
					}
					
				}
				
				// Else Process the purchase
				if(this.getUpdateState() != StoreScreen.STATE_WARNING) {
					this.getGame().getGameLog().d(this, "Processing purchase...");
					
					// Process purchase
					this.processPurchase();
					this.setUpdateState(StoreScreen.STATE_CONFIRMATION);
					
				}
				
				this.setTransitionUpdateType(Screen.INTRO_TRANSITION_UPDATE);
				
			}
			else if(ButtonManager.isClickedButton(this.getNotNowButton()) || ButtonManager.isClickedButton(this.getPhysicalBackButton())) {
				this.setUpdateState(StoreScreen.STATE_VIEWING);
				this.setTransitionUpdateType(Screen.INTRO_TRANSITION_UPDATE);
				
			}
			else if(ButtonManager.isClickedButton(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));
				
			}
			
		}
		else if(this.getUpdateState() == StoreScreen.STATE_CONFIRMATION) {
			if(ButtonManager.isClickedButton(this.getOkayButton()) || ButtonManager.isClickedButton(this.getPhysicalBackButton())) {
				this.setUpdateState(StoreScreen.STATE_VIEWING);
				this.setTransitionUpdateType(Screen.INTRO_TRANSITION_UPDATE);
				
			}
			else if(ButtonManager.isClickedButton(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));
				
			}
			
		}
		else if(this.getUpdateState() == StoreScreen.STATE_ERROR) {
			if(ButtonManager.isClickedButton(this.getOkayButton()) || ButtonManager.isClickedButton(this.getPhysicalBackButton())) {
				this.setUpdateState(StoreScreen.STATE_VIEWING);
				this.setTransitionUpdateType(Screen.INTRO_TRANSITION_UPDATE);
				
			}
			else if(ButtonManager.isClickedButton(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));
				
			}
			
		}
		else if(this.getUpdateState() == StoreScreen.STATE_WARNING) {
			if(ButtonManager.isClickedButton(this.getYesButton())) {
				this.processPurchase();
				this.setUpdateState(StoreScreen.STATE_CONFIRMATION);
				this.setTransitionUpdateType(Screen.INTRO_TRANSITION_UPDATE);
				
			}
			else if(ButtonManager.isClickedButton(this.getNoButton()) || ButtonManager.isClickedButton(this.getPhysicalBackButton())) {
				this.setUpdateState(StoreScreen.STATE_VIEWING);
				this.setTransitionUpdateType(Screen.INTRO_TRANSITION_UPDATE);
				
			}
			else if(ButtonManager.isClickedButton(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));
				
			}
			
		}
		
	}
	
	public void updatePlayerDroidAmmoIncrease(String type) {
		// Update the PlayerDroid if the world is alive
		if(((SpaceDroid) this.getGame()).getWorld() != null) {	
			if(type.equals(Gun1.classTAG)) {
				((SpaceDroid) this.getGame()).getWorld().getPlayerDroid().getGun().increaseAmmoBy(new Gun1().getAmountOfAmmo());
				
			}
			else if(type.equals(Gun2.classTAG)) {
				((SpaceDroid) this.getGame()).getWorld().getPlayerDroid().getGun().increaseAmmoBy(new Gun2().getAmountOfAmmo());
				
			}
			else if(type.equals(Gun3.classTAG)) {
				((SpaceDroid) this.getGame()).getWorld().getPlayerDroid().getGun().increaseAmmoBy(new Gun3().getAmountOfAmmo());
				
			}
			
			LaserTemplate[] newMagazine = new LaserTemplate[((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getShipAmountOfAmmo()];
			
			// Copy all active laser bullets to the newMagazine array
			for(int i = 0; i < ((SpaceDroid) this.getGame()).getWorld().getPlayerDroid().getGun().getMagazineSize(); i++) {
				if(((SpaceDroid) this.getGame()).getWorld().getPlayerDroid().getGun().getMagazine()[i].isDisposable() == false) {
					newMagazine[i] = ((SpaceDroid) this.getGame()).getWorld().getPlayerDroid().getGun().getMagazine()[i];
					
				}
				
			}
			
			// Fill the rest of the newMagazine array with laser bullets
			for(int i = 0; i < newMagazine.length; i++) {
				if(newMagazine[i] == null) {
					newMagazine[i] = new DynamicLaser(((SpaceDroid) this.getGame()).getWorld(), ((SpaceDroid) this.getGame()).getWorld().getPlayerDroid().getGun(), 10);
					newMagazine[i].dispose();
					
				}
				
			}
			
			((SpaceDroid) this.getGame()).getWorld().getPlayerDroid().getGun().attachNewMagazine(newMagazine);
			
		}
				
	}
	
	public void processPurchase() {
		// If the storeitem is ammo, then refresh its variables
		if(this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition()) instanceof Ammo1) {
			((Ammo1) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).refresh(this.getGame());
			
		}
		
		// Pay for the new store item
		int creditsCapital = ((SpaceDroid) this.getGame()).getUserProfile().getAmountOfCredits();
		int creditsCost = ((MerchandiseItem) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getCostOfCredits();
		int creditsBalance = creditsCapital - creditsCost;
		((SpaceDroid) this.getGame()).getUserProfile().setAmountOfCredits(creditsBalance);
					
		int he3Capital = ((SpaceDroid) this.getGame()).getUserProfile().getAmountOfCredits();
		int he3Cost = ((MerchandiseItem) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getCostOfHe3();
		int he3Balance = he3Capital - he3Cost;
		((SpaceDroid) this.getGame()).getUserProfile().setAmountOfHe3(he3Balance);
		
		if(this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition()) instanceof Ammo1) {
			this.getGame().getGameLog().d(this, "Type Ammo...");
			((SpaceDroid) this.getGame()).getWorld().getPlayerDroid().replenishAmmo();
			
		}
		else if(this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition()) instanceof Gun) {
			this.getGame().getGameLog().d(this, "Type Gun...");
			
			// Get the new Gun
			if(((MerchandiseItem) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).classTAG.equals(Gun1.classTAG)) {
				((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().placeGunIntoShip(new Gun1());
				this.updatePlayerDroidAmmoIncrease(Gun1.classTAG);
				this.getGame().getGameLog().d(this, "Just placed a Gun1 into the spaceship");
				
			}
			else if(((MerchandiseItem) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).classTAG.equals(Gun2.classTAG)) {
				((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().placeGunIntoShip(new Gun2());
				this.updatePlayerDroidAmmoIncrease(Gun2.classTAG);
				this.getGame().getGameLog().d(this, "Just placed a Gun2 into the spaceship");
				
			}
			else if(((MerchandiseItem) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).classTAG.equals(Gun3.classTAG)) {
				((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().placeGunIntoShip(new Gun3());
				this.updatePlayerDroidAmmoIncrease(Gun3.classTAG);
				this.getGame().getGameLog().d(this, "Just placed a Gun3 into the spaceship");
				
			}
			
		}
		else if(this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition()) instanceof Spaceship) {
			
			this.getGame().getGameLog().d(this, "Type Spaceship...");
			
			// Take all the guns out of the current Spaceship
			Gun[] copyOfGuns = ((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getAllGunsPlacedInShip();
			
			// Pay for the Spaceship
			
			// Get the new Spaceship
			if(((MerchandiseItem) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).classTAG.equals(Spaceship1.classTAG)) {
				((SpaceDroid) this.getGame()).getUserProfile().setSpaceship(new Spaceship1());
				this.getGame().getGameLog().d(this, "User now has a Spaceship1");
				
			}
			else if(((MerchandiseItem)this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).classTAG.equals(Spaceship2.classTAG)) {
				((SpaceDroid) this.getGame()).getUserProfile().setSpaceship(new Spaceship2());
				this.getGame().getGameLog().d(this, "User now has a Spaceship2");
				
			}
			else if(((MerchandiseItem) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).classTAG.equals(Spaceship3.classTAG)) {
				((SpaceDroid) this.getGame()).getUserProfile().setSpaceship(new Spaceship3());
				this.getGame().getGameLog().d(this, "User now has a Spaceship3");
				
			}
			
			// Put all the guns into the new Spacehip
			for(int i = 0; i < copyOfGuns.length; i++) {
				((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().placeGunIntoShip(copyOfGuns[i]);
				
			}
			
		}
	}
	
	@Override
	public void renderBackground() {
		super.renderBackground();
		this.getSlideView().render();
		
	}

	@Override
	public void renderViewing() {
		super.renderViewing();
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
	public void renderDialogBackground() {
		super.renderDialogBackground();
		// Check if left navigation button should be rendered
		if(this.getSlideView().isRestBoardFirst() == false) {
			this.getLeftNavigationButton().render();
			
		}
		// Check if right navigation button should be rendered
		if(this.getSlideView().isRestBoardLast() == false) {
			this.getRightNavigationButton().render();
			
		}
		this.getHe3Button.render();
		this.infoButton.render();
		this.buyButton.render();
		this.getVirtualBackButton().render();
		
	}
	
	@Override
	public void renderInfoDialog() {
		super.renderInfoDialog();
		
		// Render the requierments
		this.getGame().getGraphics().drawText("Credits: ", 108, 260, this.getDialogTextConstants());
		this.getGame().getGraphics().drawText("He3: ", 108, 298, this.getDialogTextConstants());
		this.getGame().getGraphics().drawText("Level: ", 108, 336, this.getDialogTextConstants());
				
		// Render the values of the store item (Credits, He3, Level)
		this.getGame().getGraphics().drawText(String.valueOf(((MerchandiseItem) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getCostOfCredits()), 373, 260, this.getDialogTextValues());
		this.getGame().getGraphics().drawText(String.valueOf(((MerchandiseItem) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getCostOfHe3()), 373, 298, this.getDialogTextValues());
		this.getGame().getGraphics().drawText(String.valueOf(((MerchandiseItem) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getRequiredLevel()), 373, 336, this.getDialogTextValues());
				
		// Render the information of this store item
		if(this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition()) instanceof Ammo1) {
			// Render the constants
			this.getGame().getGraphics().drawText("Ammo: ", 108, 413, this.getDialogTextConstants());
			
			this.getGame().getGraphics().drawText(((Ammo1) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getAmountOfAmmo() + " U", 373, 413, this.getDialogTextValues());
			
		}
		else if(this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition()) instanceof Spaceship) {
			// Render the constants
			this.getGame().getGraphics().drawText("Slots: ", 108, 413, this.getDialogTextConstants());
			this.getGame().getGraphics().drawText("Health: ", 108, 452, this.getDialogTextConstants());
			
			// Render the values of the specific gun
			this.getGame().getGraphics().drawText(((Spaceship) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getAmountOfGunSlots() + " U", 373, 413, this.getDialogTextValues());
			this.getGame().getGraphics().drawText(((Spaceship) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getAmountOfHealth() + " Hp", 373, 452, this.getDialogTextValues());
			
		}
		else if(this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition()) instanceof Gun) {
			// Render the constants
			this.getGame().getGraphics().drawText("Ammo: ", 108, 413, this.getDialogTextConstants());
			this.getGame().getGraphics().drawText("Freq: ", 108, 452, this.getDialogTextConstants());
			this.getGame().getGraphics().drawText("Damage: ", 108, 492, this.getDialogTextConstants());
			this.getGame().getGraphics().drawText("Velocity: ", 108, 531, this.getDialogTextConstants());
			
			// Render the values of the specific gun
			this.getGame().getGraphics().drawText(((Gun) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getAmountOfAmmoSS(), 373, 413, this.getDialogTextValues());
			this.getGame().getGraphics().drawText(((Gun) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getAmountOfFrequencySS(), 373, 452, this.getDialogTextValues());
			this.getGame().getGraphics().drawText(((Gun) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getAmountOfDamageSS(), 373, 492, this.getDialogTextValues());
			this.getGame().getGraphics().drawText(((Gun) this.getSlideViewData().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getAmountOfVelocitySS(), 373, 531, this.getDialogTextValues());
			
			
		}
		
	}
	
	@Override
	public void renderEnquiryDialog() {
		super.renderEnquiryDialog();
		this.getGame().getGraphics().save();
		this.getGame().getGraphics().translate(108, 260);
		this.enquieryDialogLayout.draw(this.getGame().getGraphics());
		this.getGame().getGraphics().restore();
		
	}
	
	@Override
	public void renderConfirmationDialog() {
		super.renderConfirmationDialog();
		this.getGame().getGraphics().save();
		this.getGame().getGraphics().translate(108, 260);
		this.confirmationDialogLayout.draw(this.getGame().getGraphics());
		this.getGame().getGraphics().restore();
		
	}
	
	@Override
	public void renderErrorDialog() {
		super.renderErrorDialog();
		this.getGame().getGraphics().save();
		this.getGame().getGraphics().translate(108, 260);
		if(this.getErrorType() == StoreScreen.ERROR_NOT_ENOUGH_CREDITS) {
			this.errorDialogLayoutNotEnoughCredits.draw(this.getGame().getGraphics());
			
		}
		else if(this.getErrorType() == StoreScreen.ERROR_NOT_ENOUGH_HE3) {
			this.errorDialogLayoutNotEnoughHe3.draw(this.getGame().getGraphics());
			
		}
		else if(this.getErrorType() == StoreScreen.ERROR_LEVEL_NOT_HIGH_ENOUGH) {
			this.errorDialogLayoutNotHighEnoughLevel.draw(this.getGame().getGraphics());
			
		}
		else if(this.getErrorType() == StoreScreen.ERROR_NOT_ENOUGH_SPACE_FOR_GUNS) {
			this.errorDialogLayoutNotEnoughSpaceInSpacehip.draw(this.getGame().getGraphics());
			
		}
		else if(this.getErrorType() == StoreScreen.ERROR_NOT_ENOUGH_SPACE_FOR_AMMO) {
			this.errorDialogLayoutNotEnoughSpaceForAmmo.draw(this.getGame().getGraphics());
			
		}
		else if(this.getErrorType() == StoreScreen.ERROR_GENERAL) {
			this.errorDialogLayoutGeneral.draw(this.getGame().getGraphics());
			
		}
		this.getGame().getGraphics().restore();
		
	}
	
	@Override
	public void renderWarningDialog() {
		super.renderWarningDialog();
		this.getGame().getGraphics().save();
		this.getGame().getGraphics().translate(108, 260);
		if(this.getWarningType() == StoreScreen.WARNING_SPACESHIP_ALREADY_PURCHASED) {
			this.warningDialogLayoutDuplicateSpaceship.draw(this.getGame().getGraphics());
			
		}
		else if(this.getWarningType() == StoreScreen.WARNING_GUNS_WILL_BE_LOST) {
			this.warningDialogLayoutNotEnoughSpaceForAllGunsInSpaceship.draw(this.getGame().getGraphics());
			
		}
		this.getGame().getGraphics().restore();
		
	}
	
	*/
}