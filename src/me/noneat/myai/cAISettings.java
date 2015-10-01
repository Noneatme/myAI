package me.noneat.myai;

import me.noneat.myai.ai.cAIManager;
import me.noneat.myai.cmd.cUserInputManager;
import me.noneat.myai.sql.cDatabase;
import me.noneat.myai.util.cCloseHandler;
import me.noneat.myai.util.cConsoleUtil;

/**
 * Created by Noneatme on 12.08.2015.
 * Version: 1.0.0
 * Purpose: AI Settings
 * License: See top folder / document root
 */
// -- //
// -- || Class AI Settings
// -- \\
public class cAISettings
{
	// -- //
	// -- || PVArs
	// -- \\
	public static final String APPLICATION_NAME     = "myAI / Soupe";   // Program name
	public static String VERSION                    = "0.0.2";          // Program version
	public static String DATABASE_PATH              = "main.db";        // The database path
	public static boolean DEBUG                     = true;             // Debug the application
	public static boolean USE_ASCII_CHARS           = false;            // Enable the usage of ASCII chars
	protected static boolean instanced              = false;            // Dont change
	public static long TERMINATE_IDLE_TIME          = 20000*60*1000;         // Time in MS when the app will be killed
	public static boolean USE_GUI                   = true;            // Change this to false if you want the console output instead
																		// Useful for debugging

	// -- //
	// -- || Singleton Instances
	// -- \\
	// I dont think I should use public static all the way, nvm.
	// TODO: fix fucked up code
	public static cConsoleUtil consoleUtil  = new cConsoleUtil();
	public static cDatabase database;
	public static cUserInputManager inputManager;
	public static cCloseHandler closeHandler;
	public static cAIManager aiManager;

	// -- //
	// -- || createInstances
	// -- \\
	public static void createInstances()
	{
		if(!instanced)
		{
			instanced       = true;

			// Dafuq is this
			// Todo: srsly
			database        = new cDatabase(DATABASE_PATH);
			inputManager    = new cUserInputManager();
			closeHandler    = new cCloseHandler();
			aiManager       = new cAIManager();


			// Database
			database.checkTables();
			closeHandler.start();

			System.out.println(closeHandler.getState());
		}
	}

	// -- //
	// -- || getDatabase
	// -- \\
	public static cDatabase getDatabase()
	{
		return database;
	}

	/* GETTER AND SETTER */
	// -- //
	// -- || getAIManager
	// -- \\
	public static cAIManager getAiManager()
	{
		return aiManager;
	}

	// -- //
	// -- || getCloseHandler
	// -- \\
	public static cCloseHandler getCloseHandler()
	{
		return closeHandler;
	}

	// -- //
	// -- || getInputManager
	// -- \\
	public static cUserInputManager getInputManager()
	{
		return inputManager;
	}

	// -- //
	// -- || getConsoleUtil
	// -- \\
	public static cConsoleUtil getConsoleUtil()
	{
		return consoleUtil;
	}
}

