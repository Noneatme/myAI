package me.noneat.myai;

import me.noneat.myai.ai.cAI;

/**
 * Created by Noneatme on 12.08.2015.
 * Copyright (c) 2015 Noneatme - All Rights Reserved.
 */
// -- //
// -- || cMain
// -- \\
public class cMain
{
	// -- //
	// -- || PVars
	// -- \\
	// PVArs
	public static cAI ai;

	// -- //
	// -- || Main
	// -- \\
	public static void main(String[] args)
	{
		System.out.println("////// //////");
		System.out.println("Loading MyAI " + cAISettings.VERSION + ", Debug: " + cAISettings.DEBUG);
		cAISettings.consoleUtil.setLoadingState(true);

		cMain.ai = new cAI();
		cAISettings.createInstances();

		System.out.println();
		System.out.println("Loading finished! Starting Threads...");

		cMain.ai.start();
		cAISettings.inputManager.start();

		cAISettings.consoleUtil.setLoadingState(false);

	}


	public static void abort()
	{
		System.out.println("!! Got abort signal, terminating porgramm");
		System.exit(0);
	}
}
