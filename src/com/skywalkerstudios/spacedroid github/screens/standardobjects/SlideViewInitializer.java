package com.skywalkerstudios.spacedroid.screens.standardobjects;

import com.webstorms.framework.graphics.animation.slideview.SlideViewOptions;

public interface SlideViewInitializer {

	// Initialize SlideView Info and SlideView boards in this method aswell as the SlideView options
	
	public void initializeSlideView(SlideViewOptions options);
	
	public void initializeBoard(int index);
	
	
}
