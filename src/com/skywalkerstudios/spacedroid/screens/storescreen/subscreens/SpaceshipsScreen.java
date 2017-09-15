package com.skywalkerstudios.spacedroid.screens.storescreen.subscreens;

import java.util.ArrayList;
import java.util.List;

import com.flurry.android.FlurryAgent;
import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.merchandise.MerchandiseItem;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo;
import com.skywalkerstudios.spacedroid.merchandise.guns.Gun;
import com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship;
import com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship1;
import com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship2;
import com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship3;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.PlayerDroid;
import com.skywalkerstudios.spacedroid.screens.storescreen.StoreScreen;
import com.webstorms.framework.graphics.animation.slideview.SlideViewOptions;

public class SpaceshipsScreen extends StandardStoreScreen {

	/*
	 * Note: For all classes implementing a SlideView, make sure when the logout button is pressed, that the slideview can't be slideview
	 * 
	 */
	
	private final int WARNING_SPACESHIP_ALREADY_PURCHASED = this.getValueOfLastWarning() + 1;
	private final int WARNING_GUNS_WILL_BE_LOST = this.getValueOfLastWarning() + 2;
	
	private final String warningDialogMessageNotEnoughSpaceForAllGunsInSpaceship = "Not enough slots for all guns. Best guns will save. Continue.";
	
	private final String warningDialogMessageDuplicateSpaceship = "You already possess this Spaceship. Continue.";
	
	public SpaceshipsScreen(SpaceDroid game) {
		super(game);
		
	}

	@Override
	public void load() {
		super.load();
		FlurryAgent.onPageView();
		FlurryAgent.logEvent("SpaceshipsScreen has been launched");

	}
	
	@Override
	public void initializeSlideView(SlideViewOptions options) {
		super.initializeSlideView(options);
		this.initializeSlideViewInfo((List<Object>)(List<?>) new ArrayList<Spaceship>());
		this.getSlideViewInfo().add(new Spaceship1(this.getGame()));
		this.getSlideViewInfo().add(new Spaceship2(this.getGame()));
		this.getSlideViewInfo().add(new Spaceship3(this.getGame()));
		
	}

	@Override
	protected void onPurchase() {
		// Take all the guns out of the current Spaceship
		Gun[] copyOfGuns = this.getGame().getUserProfile().getSpaceship().getAllGunsPlacedInShip();
		Ammo copyOfAmmo = this.getGame().getUserProfile().getSpaceship().getAmmo();
		
		// Get the new Spaceship
		if(((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getClassTag().equals(Spaceship1.CLASS_TAG)) {
			((SpaceDroid) this.getGame()).getUserProfile().setSpaceship(new Spaceship1(this.getGame()));
			this.getGame().getGameLog().d(this, "User now has a Spaceship1");

		}
		else if(((MerchandiseItem)this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getClassTag().equals(Spaceship2.CLASS_TAG)) {
			((SpaceDroid) this.getGame()).getUserProfile().setSpaceship(new Spaceship2(this.getGame()));
			this.getGame().getGameLog().d(this, "User now has a Spaceship2");

		}
		else if(((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getClassTag().equals(Spaceship3.CLASS_TAG)) {
			((SpaceDroid) this.getGame()).getUserProfile().setSpaceship(new Spaceship3(this.getGame()));
			this.getGame().getGameLog().d(this, "User now has a Spaceship3");

		}

		// Put all the guns into the new Spacehip
		for(int i = 0; i < copyOfGuns.length; i++) {
			this.getGame().getUserProfile().getSpaceship().placeGunIntoShip(copyOfGuns[i]);

		}
		
		// Put the ammo in the Spaceship
		this.getGame().getUserProfile().getSpaceship().setAmmo(copyOfAmmo);
		
	}
	
	@Override
	protected void updatePlayerDroid(PlayerDroid playerDroid) {
		playerDroid.setHealth(this.getGame().getUserProfile().getSpaceship().getAmountOfHealth());
		if(this.getGame().getUserProfile().getSpaceship() instanceof Spaceship1) {
			playerDroid.setBitmap(Assets.playScreen_Spaceship1);
			
		}
		else if(this.getGame().getUserProfile().getSpaceship() instanceof Spaceship2) {
			playerDroid.setBitmap(Assets.playScreen_Spaceship2);
			
		}
		if(this.getGame().getUserProfile().getSpaceship() instanceof Spaceship3) {
			playerDroid.setBitmap(Assets.playScreen_Spaceship3);
			
		}
		
		playerDroid.getShape().getHolderPolygons().clear();
		playerDroid.initializeShape(playerDroid.getShape());
		
	}
	
	@Override
	protected boolean onCheckForErrors() {
		return false;
		
	}

	@Override
	protected boolean onCheckForWarnings() {
		boolean state = false;
		// Check if the current Spaceship is the same that the user would like to purchase
		if(((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getClassTag().equals(((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getClassTag())) {
			this.setWarningType(this.WARNING_SPACESHIP_ALREADY_PURCHASED);
			this.getGame().getGameLog().d(this, "Spaceship is already in use!");
			state = true;
			
		}
		
		// Check that there is enough free gun slots for all the guns
		int currentSpaceshipAmountOfGunSlots = ((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getAmountOfGunSlotsInUse();
		int storeSpaceshipAmoutOfGunSlots = ((Spaceship) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getAmountOfGunSlots();
		
		if(currentSpaceshipAmountOfGunSlots > storeSpaceshipAmoutOfGunSlots) {
			this.setWarningType(this.WARNING_GUNS_WILL_BE_LOST);
			this.getGame().getGameLog().d(this, "Guns will be lost!");
			state = true;
			
		}
		
		return state;
	}
	
	@Override 
	protected int getStartPositionForStoreScreen() {
		return StoreScreen.START_POSITION_SPACESHIPS;
	}
	
	@Override
	public void renderInfoDialog() {
		super.renderInfoDialog();
		
		// Render the constants
		this.getGame().getGraphics().drawText("Slots: ", 108, 413, this.getDialog().getDialogTextConstants());
		this.getGame().getGraphics().drawText("Health: ", 108, 452, this.getDialog().getDialogTextConstants());

		// Render the values of the specific gun
		this.getGame().getGraphics().drawText(((Spaceship) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getAmountOfGunSlots() + " U", 373, 413, this.getDialog().getDialogTextValues());
		this.getGame().getGraphics().drawText(((Spaceship) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getAmountOfHealth() + " Hp", 373, 452, this.getDialog().getDialogTextValues());
		
	}

	@Override
	public void renderWarningDialog() {
		super.renderWarningDialog();
		if(this.getWarningType() == this.WARNING_GUNS_WILL_BE_LOST) {
			this.getDialog().renderWarningDialog(this.warningDialogMessageNotEnoughSpaceForAllGunsInSpaceship);
			
		}
		else if(this.getWarningType() == this.WARNING_SPACESHIP_ALREADY_PURCHASED) {
			this.getDialog().renderWarningDialog(this.warningDialogMessageDuplicateSpaceship);
			
		}
		
	}
	
	
}
