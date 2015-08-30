package me.noneat.myai;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.application.Application;
import javafx.stage.Stage;
import me.noneat.myai.ai.cAI;
import me.noneat.myai.gui.cAIChatWindow;

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
	public static cAI ai;

	// -- //
	// -- || Main
	// -- \\
	public static void main(String[] args)
	{
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
		System.out.println("Loading finished! Starting Threads...");

		// Start the AI
		cMain.ai.start();
		cAISettings.inputManager.start();

		// Everything loaded
		cAISettings.consoleUtil.setLoadingState(false);
		//cMain.ai.setLearnMode(true);


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
		System.out.println("!! Got Abort Signal, Terminating Program");

		// Exit
		System.exit(0);
	}

	// JAVAFX CLASS //
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Application app = new cAIChatWindow();
		app.start(primaryStage);
	}
}
