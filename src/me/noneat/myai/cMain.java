package me.noneat.myai;

import javafx.application.Application;
import javafx.stage.Stage;
import me.noneat.myai.ai.cAI;
import me.noneat.myai.gui.popupdisplay.cPopupDisplayGUI;

import java.sql.SQLException;

/**
 * Created by Noneatme on 12.08.2015.
 * Copyright (c) 2015 Noneatme - All Rights Reserved.
 */
// -- //
// -- || cMain
// -- \\
public class cMain extends Application
{
	// -- //
	// -- || PVars
	// -- \\
	// PVArs
	public static cPopupDisplayGUI app;
	public static cAI ai;

	static long startupTime;


	// -- //
	// -- || Main
	// -- \\
	public static void main(String[] args)
	{
		startupTime = System.currentTimeMillis();

		// Main Outputs
		System.out.println("////// //////");
		System.out.println("Loading MyAI " + cAISettings.VERSION + ", Debug: " + cAISettings.DEBUG);
		System.out.println("Idle Terminate Time: " + cAISettings.TERMINATE_IDLE_TIME / 1000 + " Seconds");

		// SetLoading
		cAISettings.consoleUtil.setLoadingState(true);

		// New AI
		cMain.ai = new cAI();
		cAISettings.createInstances();

		// Finish Loadings
		System.out.println();
		System.out.println("Loading finished! (" + (System.currentTimeMillis() - startupTime) + "ms) Starting Threads...");

		// Start the AI
		cMain.ai.start();
		cAISettings.inputManager.start();

		// Everything loaded
		cAISettings.consoleUtil.setLoadingState(false);
		//cMain.ai.setLearnMode(true);

		if(cAISettings.USE_GUI)
			launch();
	}

	// -- //
	// -- || Abort
	// -- || Savely quits the program
	// -- \\
	public static void abort()
	{
		try
		{
			// Close Database
			cAISettings.database.getConnection().close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		// Outputs
		System.out.println("- Got abort signal, terminating program");

		// Exit
		System.exit(0);
	}

	// -- //
	// -- || Startt
	// -- || Launches the application if the gui mode has been activated.
	// -- \\
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		this.app = new cPopupDisplayGUI();
		this.app.start(primaryStage);
	}
}
