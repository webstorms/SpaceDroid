package com.skywalkerstudios.spacedroid.screens;

import java.io.IOException;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.skywalkerstudios.spacedroid.Assets;
import com.skywalkerstudios.spacedroid.SpaceDroid;
import com.webstorms.framework.Screen;
import com.webstorms.framework.graphics.animation.SpriteSheetAnimation;
import com.webstorms.framework.util.Timer;
import com.webstorms.physics.Shape;

public class SplashScreen extends Screen {

	private SpriteSheetAnimation splashAnimation;
	private Paint loadingTextPaint = new Paint();
	private String loadingText = "Loading";

	private boolean hasLoadedSplashScreenAssets;
	private Shape appLogoDimensions = new Shape();

	Timer loadTextDelay = new Timer(300);
	Timer loadDelay = new Timer(3000); // 5000
	
	public SplashScreen(final SpaceDroid game) {
		super(game);

	}

	@Override
	public void resume() {
		this.getGame().getInput().useKeyboard(true);
		
	}

	// this.getGame(), Assets.playScreen_Explosion, 5, 5, 2

	@Override
	public void load() {
		this.loadSplashScreenScreen(); // Requiered, since this gets rendered to the screen while everything loads
		hasLoadedSplashScreenAssets = true;
		
		this.appLogoDimensions.addRectangle(new Rect(0, 0, 389, 195));
		this.appLogoDimensions.setLocation(43, 303);

		this.splashAnimation = new SpriteSheetAnimation(this.getGame(), Assets.splashScreen_Animation, null, this.appLogoDimensions.getRect(), 5, 6, 0, true); // 43, 303, 389 + 43, 195 + 303
		this.splashAnimation.setLocation(43, 303);

		loadingTextPaint.setColor(Color.rgb(215, 215, 205));
		loadingTextPaint.setTypeface(Assets.standardScreen_Birdman);
		loadingTextPaint.setTextSize(23);
		// this.splashAnimation.update();
		
	}

	@Override
	public void onDispose() {
		this.disposeSplashScreenScreen();
	}

	private void disposeSplashScreenScreen() {
		this.getGame().getIO().disposeBitmap(Assets.splashScreen_Background);
		this.getGame().getIO().disposeBitmap(Assets.splashScreen_Animation);
		this.getGame().getIO().disposeBitmap(Assets.splashScreen_AppLogo);
		this.getGame().getAudio().disposeSound(Assets.splashScreen_GlintEffect);

	}

	@Override
	public void update() {
		// Wait until FPS is over 25 and all assets have been loaded
		if(loadDelay.delay() && (hasLoadedSplashScreenAssets && this.getGame().getAssetsLoaded())) {
			if(!this.splashAnimation.hasBeenStarted()) {
				Assets.splashScreen_GlintEffect.play();
			}
			if(!this.splashAnimation.isAnimationFinished()) {
				this.splashAnimation.update();

			}
			else {				
				this.getGame().setScreen(new MenuScreen((SpaceDroid) this.getGame()));
				
			}

		}
		else {
			if(loadTextDelay.delay()) {
				loadTextDelay.reset();

				if(!this.loadingText.equals("Loading...")) {
					this.loadingText += ".";

				}
				else {
					this.loadingText = "Loading";

				}

			}

		}

	}

	@Override
	public void render() {
		this.getGame().getGraphics().drawBitmap(Assets.splashScreen_Background, null, this.getGame().getWSScreen().getGameScreenDimensions(), null);		
		if(loadDelay.delay() && (hasLoadedSplashScreenAssets && this.getGame().getAssetsLoaded())) {
			this.splashAnimation.render();

		}
		else {
			this.getGame().getGraphics().drawText(this.loadingText, 13, 782, loadingTextPaint);
			this.getGame().getGraphics().drawBitmap(Assets.splashScreen_AppLogo, null, appLogoDimensions.getRect(), null);

		}

	}

	private void loadSplashScreenScreen() {
		try {
			Assets.splashScreen_Background = this.getGame().getIO().readAssetsBitmap("SplashScreen/Background.png");
			Assets.splashScreen_Animation = this.getGame().getIO().readAssetsBitmap("SplashScreen/Animation.png");
			Assets.splashScreen_AppLogo = this.getGame().getIO().readAssetsBitmap("SplashScreen/AppLogo.png");
			Assets.splashScreen_GlintEffect = this.getGame().getAudio().newSound("SplashScreen/GlintEffect.ogg");
			this.getGame().getGameLog().d(this, "Successfully read: SplashScreen");

		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}


}
