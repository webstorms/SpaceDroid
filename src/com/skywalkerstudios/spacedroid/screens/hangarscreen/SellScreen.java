package com.skywalkerstudios.spacedroid.screens.hangarscreen;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;

import com.flurry.android.FlurryAgent;
import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.FileNames;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.skywalkerstudios.spacedroid.merchandise.MerchandiseBoard;
import com.skywalkerstudios.spacedroid.merchandise.MerchandiseItem;
import com.skywalkerstudios.spacedroid.merchandise.MerchandiseViewable;
import com.skywalkerstudios.spacedroid.merchandise.ammo.Ammo;
import com.skywalkerstudios.spacedroid.merchandise.guns.Gun;
import com.skywalkerstudios.spacedroid.merchandise.guns.Gun1;
import com.skywalkerstudios.spacedroid.merchandise.guns.Gun2;
import com.skywalkerstudios.spacedroid.merchandise.guns.Gun3;
import com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship1;
import com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship2;
import com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship3;
import com.skywalkerstudios.spacedroid.screens.MenuScreen;
import com.skywalkerstudios.spacedroid.screens.playscreen.World;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.PlayerDroid;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.Lasers.AmmoBase;
import com.skywalkerstudios.spacedroid.screens.playscreen.gameobjects.Lasers.LaserBullet;
import com.skywalkerstudios.spacedroid.screens.standardobjects.DefaultBoard;
import com.skywalkerstudios.spacedroid.screens.standardobjects.DefaultSlideView;
import com.skywalkerstudios.spacedroid.screens.standardobjects.SlideViewInitializer;
import com.skywalkerstudios.spacedroid.screens.standardobjects.StandardScreen;
import com.webstorms.framework.graphics.animation.buttons.Button;
import com.webstorms.framework.graphics.animation.buttons.VirtualButton;
import com.webstorms.framework.graphics.animation.slideview.SlideViewOptions;

public class SellScreen extends StandardScreen implements SlideViewInitializer {
	
	public final int WARNING_NO_GUNS_WILL_BE_LEFT = this.getValueOfLastWarning() + 1;
	private final int WARNING_GUNS_WILL_BE_LOST = this.getValueOfLastWarning() + 2;
	
	private final VirtualButton infoButton = new VirtualButton(this.getGame(), "Info", this, 242, 713, new Rect(0, 0, 93, 69), Assets.standardScreen_InfoButton, Assets.standardScreen_InfoButtonClicked);
	private final VirtualButton sellButton = new VirtualButton(this.getGame(), "Sell", this, 361, 713, new Rect(0, 0, 106, 69), Assets.hangarScreen_SellButton, Assets.hangarScreen_SellButtonClicked);
	
	private final String enquieryDialogMessage = "Are you sure you would like to sell this item.";
	private final String confirmationDialogMessage = "The item has succesfully been sold.";
	private final String warningDialogMessageNoGunsWillBeLeftToSell = "No guns will be left in the Spaceship. Continue.";
	private final String warningDialogMessageNotEnoughSpaceForAllGunsInSpaceship = "Not enough slots for all guns. Best guns will save. Continue.";
	private final String welcomeDialogMessage = "In the Sell screen you can sell items that you no longer want or need to get credits and He3.";
	
	public SellScreen(SpaceDroid game) {
		super(game);
		
	}
	
	@Override
	public void load() {
		super.load();
		FlurryAgent.onPageView();
		FlurryAgent.logEvent("SellScreen has been launched");
		this.setWelcomeScreenMessage(this.welcomeDialogMessage);
		this.initializeSlideView(this);
		
		if(!this.getGame().getIO().readPrimitiveInternalMemoryBoolean(FileNames.Welcome.HAS_SHOWN_SELL_SCREEN_WELCOME)) {
			this.setUpdateState(this.STATE_WELCOME);
			
		}
		
	}
	
	@Override
	public void initializeSlideView(SlideViewOptions options) {
		options.setStartPosition(1);
		
		// Slideview Info 
		this.initializeSlideViewInfo((List<Object>)(List<?>) new ArrayList<MerchandiseViewable>());
		initializeHangarGuns();
		initializeHangarSpaceship();
		
		// Slideview Boards
		this.initializeSlideViewBoards((List<DefaultBoard>)(List<?>) new ArrayList<MerchandiseBoard>());
		
	}
	
	@Override
	public void initializeBoard(int index) {
		this.getSlideViewBoards().add(new MerchandiseBoard(this.getGame(), (MerchandiseViewable) this.getSlideViewInfo().get(index)));
		
	}
	
	private void initializeHangarGuns() {
		for(int i = 0; i < ((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getAmountOfGunSlotsInUse(); i++) {
			if(((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getAllGunsPlacedInShip()[i].getClassTag().equals(Gun1.CLASS_TAG)) {
				this.getGame().getGameLog().d(this, "Added Gun1");
				this.getSlideViewInfo().add(new Gun1(this.getGame()));
				
			}
			if(((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getAllGunsPlacedInShip()[i].getClassTag().equals(Gun2.CLASS_TAG)) {
				this.getGame().getGameLog().d(this, "Added Gun2");
				this.getSlideViewInfo().add(new Gun2(this.getGame()));
				
			}
			else if(((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getAllGunsPlacedInShip()[i].getClassTag().equals(Gun3.CLASS_TAG)) {
				this.getGame().getGameLog().d(this, "Added Gun3");
				this.getSlideViewInfo().add(new Gun3(this.getGame()));
				
			}
			
		}
		
	}
	
	private void initializeHangarSpaceship() {
	//	if(((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getClassTag().equals(Spaceship1.CLASS_TAG)) {
	//		this.getSlideViewInfo().add(new Spaceship1(this.getGame()));
			
	//	}
		if(((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getClassTag().equals(Spaceship2.CLASS_TAG)) {
			this.getSlideViewInfo().add(new Spaceship2(this.getGame()));
			
		}
		else if(((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getClassTag().equals(Spaceship3.CLASS_TAG)) {
			this.getSlideViewInfo().add(new Spaceship3(this.getGame()));
			
		}
		
	}
		
	@Override
	public void onTouch(Integer x, Integer y) {
		super.onTouch(x, y);
		if(this.getUpdateState() == this.STATE_VIEWING) {
			// Check if left navigation button should be updated
			if(this.getSlideView().getPositionOfBoardClosestToRestPosition() != Integer.valueOf(1)) {
				// Left button
				this.getLeftNavigationButton().update(x, y);
				
			}
			
			// Check if right navigation button should be updated
			if(this.getSlideView().getPositionOfBoardClosestToRestPosition() != Integer.valueOf(this.getSlideViewBoards().size())) {
				// Right button
				this.getRightNavigationButton().update(x, y);
				
			}
			
			// Info button
			this.infoButton.update(x, y);
			
			// Sell button
			this.sellButton.update(x, y);
			
			// Virtual Back button
			this.getVirtualBackButton().update(x, y);
			
			// Update Slideview
		//	if(this.getGame().getInput().isScreenTouchedDown() && !this.getLeftNavigationButton().isPressed() && !this.getRightNavigationButton().isPressed() && !this.getVirtualBackButton().isPressed()
		//			&& !this.infoButton.isPressed() && !this.sellButton.isPressed()) {
		//		this.getSlideView().update(DefaultSlideView.TYPE_ACTIVE, x);
				
		//	}
			
		}	
		else if(this.getUpdateState() == this.STATE_ENQUIRY) {
			this.getDialog().getYesButton().update(x, y);
			this.getDialog().getNotNowButton().update(x, y);
				
		}
		else if(this.getUpdateState() == this.STATE_CONFIRMATION) {
			this.getDialog().getOkayButton().update(x, y);
				
		}
		else if(this.getUpdateState() == this.STATE_WARNING) {
			this.getDialog().getYesButton().update(x, y);
			this.getDialog().getNoButton().update(x, y);
			
		}
		
	}
	
	@Override
	public void onButtonClicked(Button button) {
		if(this.getUpdateState() == this.STATE_WELCOME) {
			this.getGame().getIO().writePrimitiveInternalMemory(FileNames.Welcome.HAS_SHOWN_SELL_SCREEN_WELCOME, true);
			
		}
		super.onButtonClicked(button);
		if(this.getUpdateState() == this.STATE_VIEWING) {
			if(button.equals(this.getLeftNavigationButton())) {
				this.getSlideView().moveBoardToPosition(this.getSlideView().getPositionOfBoardClosestToRestPosition() - 1);

			}
			else if(button.equals(this.getRightNavigationButton())) {
				this.getSlideView().moveBoardToPosition(this.getSlideView().getPositionOfBoardClosestToRestPosition() + 1);

			}
			else if(button.equals(this.infoButton)) {
				this.setUpdateState(this.STATE_INFO);

			}
			else if(button.equals(this.sellButton)) {
				this.setUpdateState(this.STATE_ENQUIRY);

			}
			else if(button.equals(this.getVirtualBackButton()) || button.equals(this.getPhysicalBackButton())) {
				this.getGame().setScreen(new HangarScreen((SpaceDroid) this.getGame()));

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
				// Display Warnings if any
				if(this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition()) instanceof com.skywalkerstudios.spacedroid.merchandise.guns.Gun) {
					if((((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getAmountOfGunSlotsInUse() - 1) == 0) {
						this.setUpdateState(this.STATE_WARNING);
						this.setWarningType(this.WARNING_NO_GUNS_WILL_BE_LEFT);
						this.getGame().getGameLog().d(this, "No guns will be left in the Spaceship!");

					}

				}
				else if(this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition()) instanceof com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship) {
					this.setUpdateState(this.STATE_WARNING);
					this.setWarningType(this.WARNING_GUNS_WILL_BE_LOST);
					this.getGame().getGameLog().d(this, "All guns will be lost!");					

				}

				// Else Process the purchase
				if(this.getUpdateState() != this.STATE_WARNING) {
					this.getGame().getGameLog().d(this, "Processing transaction...");

					// Process purchase
					this.processTransaction();
					if(this.getGame().getWorld() != null) {
						this.updatePlayerDroid(this.getGame().getWorld().getPlayerDroid());
						
					}
					this.refreshSlideViewData(this);

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
				if(this.getGame().getUserProfile().getSpaceship().hasSellingItems() != true) {
					this.getGame().setScreen(new HangarScreen(this.getGame()));

				}

			}
			else if(button.equals(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));

			}

		}
		else if(this.getUpdateState() == this.STATE_WARNING) {
			if(button.equals(this.getDialog().getYesButton())) {
				this.processTransaction();
				if(this.getGame().getWorld() != null) {
					this.updatePlayerDroid(this.getGame().getWorld().getPlayerDroid());
				}
				this.refreshSlideViewData(this);
				this.setUpdateState(this.STATE_CONFIRMATION);

			}
			else if(button.equals(this.getDialog().getNoButton()) || button.equals(this.getPhysicalBackButton())) {
				this.setUpdateState(this.STATE_VIEWING);

			}
			else if(button.equals(this.getMenuButton())) {
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));

			}

		}
	}

	@Override
	public void update() {
		super.update();
		if(!this.getGame().getInput().isTouchDown() || this.getUpdateState() != this.STATE_VIEWING || this.getLeftNavigationButton().isPressed() || this.getRightNavigationButton().isPressed() 
				|| this.getVirtualBackButton().isPressed() || this.infoButton.isPressed() || this.sellButton.isPressed()) {
			this.getSlideView().update(DefaultSlideView.TYPE_UNACTIVE, null);
			
		} 
		else {
			this.getSlideView().update(DefaultSlideView.TYPE_ACTIVE, this.getGame().getInput().getTouchX());
			
		}
		
	}
		
	public void processTransaction() {
		// 	Get the money for the new Gun
		int creditsCapital = ((SpaceDroid) this.getGame()).getUserProfile().getAmountOfCredits();
		int creditsCost = (int) (0.75 * ((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getCostOfCredits()); // 75 % of initial purchase
		int creditsBalance = creditsCapital + creditsCost;
		((SpaceDroid) this.getGame()).getUserProfile().setAmountOfCredits(creditsBalance);
		this.getGame().getGameLog().d(this, creditsCost + " + " + creditsCapital + " = " + creditsBalance);
		
		int he3Capital = ((SpaceDroid) this.getGame()).getUserProfile().getAmountOfHe3();
		int he3Cost = (int) (0.5 * ((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getCostOfHe3()); // 50 % of initial purchase
		int he3Balance = he3Capital + he3Cost;
		((SpaceDroid) this.getGame()).getUserProfile().setAmountOfHe3(he3Balance);
		this.getGame().getGameLog().d(this, he3Cost + " + " + he3Capital + " = " + he3Balance);
		
		if(this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition()) instanceof com.skywalkerstudios.spacedroid.merchandise.guns.Gun) {
			this.getGame().getGameLog().d(this, "Type Gun...");
			((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().removeGunFromShip(((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getClassTag());
			this.getGame().getGameLog().d(this, "Just removed a Gun from the Spaceship");
			this.getGame().getUserProfile().getSpaceship().sortGunsAccordingToDamage();
			
		}
		else if(this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition()) instanceof com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship) {
			this.getGame().getGameLog().d(this, "Type Spaceship...");
			// Take all the guns out of the current Spaceship
			Gun[] copyOfGuns = this.getGame().getUserProfile().getSpaceship().getAllGunsPlacedInShip();
			Ammo copyOfAmmo = this.getGame().getUserProfile().getSpaceship().getAmmo();
			
			((SpaceDroid) this.getGame()).getUserProfile().setSpaceship(new Spaceship1(this.getGame()));
			
			// Put all the guns into the new Spaceship
			for(int i = 0; i < this.getGame().getUserProfile().getSpaceship().getAmountOfGunSlots(); i++) {
				this.getGame().getUserProfile().getSpaceship().placeGunIntoShip(copyOfGuns[i]);

			}
			
			// Put the ammo in the Spaceship
			this.getGame().getUserProfile().getSpaceship().setAmmo(copyOfAmmo);
			
			
		}
		
	}
	
	private void updatePlayerDroid(PlayerDroid playerDroid) {
		if(this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition()) instanceof com.skywalkerstudios.spacedroid.merchandise.guns.Gun) {
			playerDroid.getGun().setShootFrequency(this.getGame().getUserProfile().getSpaceship().getShipShootingFrequency());
			playerDroid.getGun().setShootDamage(this.getGame().getUserProfile().getSpaceship().getShipShootingDamage());
			playerDroid.getGun().setShootVelocity(this.getGame().getUserProfile().getSpaceship().getShipShootingVelocity());
			playerDroid.getGun().setAmmo(this.getNewAmountOfAmmo());
			playerDroid.getGun().attachNewMagazine(this.getNewMagazine());
			
		}
		else if(this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition()) instanceof com.skywalkerstudios.spacedroid.merchandise.spaceships.Spaceship) {
			playerDroid.setHealth(this.getGame().getUserProfile().getSpaceship().getAmountOfHealth());
			playerDroid.setBitmap(Assets.playScreen_Spaceship1);
			playerDroid.getShape().getHolderPolygons().clear();
			playerDroid.initializeShape(playerDroid.getShape());
			
		}
		
	}
	
	private int getNewAmountOfAmmo() {
		int newAmountOfAmmo = ((World) this.getGame().getWorld()).getPlayerDroid().getGun().getCurrentAmmo();
		
		int deficit = ((SpaceDroid) this.getGame()).getWorld().getPlayerDroid().getGun().getCurrentAmmo() - ((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getShipMaxAmountOfAmmo();
		
		if(deficit > 0) {
			newAmountOfAmmo -= deficit;
			
		}
	
		return newAmountOfAmmo;
		
	}
	
	private LaserBullet[] getNewMagazine() {
		// 1. Add all active bullets to the tempMag of the PlayerDroid
		// 2. Return the new mag
		
		// Copy all the active bullets to the tempMagazine magazine
		List<AmmoBase> tempMagazineList = new ArrayList<AmmoBase>();

		// Add existing TempMag to the new TempMag that we are creating
		if(((World) this.getGame().getWorld()).getPlayerDroid().getTempMagazine() != null) {
			for(int i = 0; i < ((World) this.getGame().getWorld()).getPlayerDroid().getTempMagazine().length; i++) {
				tempMagazineList.add(((World) this.getGame().getWorld()).getPlayerDroid().getTempMagazine()[i]);

			}

		}

		for(int i = 0; i < this.getGame().getWorld().getPlayerDroid().getGun().getMagazineSize(); i++) {
			if(!this.getGame().getWorld().getPlayerDroid().getGun().getMagazine()[i].isDisposable()) {
				tempMagazineList.add(this.getGame().getWorld().getPlayerDroid().getGun().getMagazine()[i]);
				this.getGame().getGameLog().d(this, "Adding new Bullet to TempMagazine");

			}

		}
		LaserBullet[] tempMagazineArray = tempMagazineList.toArray(new LaserBullet[tempMagazineList.size()]);
		this.getGame().getWorld().getPlayerDroid().setTempMagazine(tempMagazineArray);

		// Initialize the new Magazine and apply it to the Spaceship
		LaserBullet[] newMagazine = new LaserBullet[((SpaceDroid) this.getGame()).getUserProfile().getSpaceship().getShipMaxAmountOfAmmo()];
		for(int i = 0; i < newMagazine.length; i++) {
			newMagazine[i] = new LaserBullet(this.getGame(), this.getGame().getWorld(), this.getGame().getWorld().getPlayerDroid()); // , this.getGame().getUserProfile().getSpaceship().getShipShootingDamage(), this.getGame().getUserProfile().getSpaceship().getAmmo().getClassTag()
			newMagazine[i].dispose();

		}
		
		return newMagazine;
		
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
		this.infoButton.render();
		this.sellButton.render();
		this.getVirtualBackButton().render();

	}

	@Override
	public void renderInfoDialog() {
		this.getDialog().renderInfoDialog("");
		
		this.getGame().getGraphics().drawText("Credits: ", 108, 260, this.getDialog().getDialogTextConstants());
		this.getGame().getGraphics().drawText("He3: ", 108, 298, this.getDialog().getDialogTextConstants());
		
		// Render the values of the store item (Credits, He3)
		int sellingPriceCredits = (int) (0.75 * ((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getCostOfCredits());
		int sellingPriceHe3 = (int) (0.5 * ((MerchandiseItem) this.getSlideViewInfo().get(this.getSlideView().getIndexOfBoardClosestToRestPosition())).getCostOfHe3());
		
		this.getGame().getGraphics().drawText(String.valueOf(sellingPriceCredits), 373, 260, this.getDialog().getDialogTextValues());
		this.getGame().getGraphics().drawText(String.valueOf(sellingPriceHe3), 373, 298, this.getDialog().getDialogTextValues());
		
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
	public void renderWarningDialog() {
		if(this.getWarningType() == this.WARNING_NO_GUNS_WILL_BE_LEFT) {
			this.getDialog().renderWarningDialog(this.warningDialogMessageNoGunsWillBeLeftToSell);
			
		}
		else if(this.getWarningType() == this.WARNING_GUNS_WILL_BE_LOST) {
			this.getDialog().renderWarningDialog(this.warningDialogMessageNotEnoughSpaceForAllGunsInSpaceship);
			
		}
		
	}
	

}
	