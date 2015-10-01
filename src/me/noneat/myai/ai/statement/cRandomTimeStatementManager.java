package me.noneat.myai.ai.statement;

import me.noneat.myai.ai.cSentenceUtils;
import me.noneat.myai.cAISettings;
import me.noneat.myai.cMain;
import me.noneat.myai.sql.cDatabase;

import java.lang.management.ThreadInfo;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Package: me.noneat.myai.ai.statement
 * Author: Noneatme
 * Date: 16.09.2015-12:15-2015.
 * Version: 1.0.0
 * License: See LICENSE.md in the top folder.
 */
public class cRandomTimeStatementManager extends Thread
{
	// PVars
	private long currentTimeMillis  = System.currentTimeMillis();
	private boolean outputEnabled   = true;
	private long timeToSleep        = 120000;
	private boolean dontSleep       = true;

	// -- //
	// -- || RandomTimeStatement
	// -- || Objekt welches ein zufaelliges Zeitstatement bei Inaktivitaet erhaelt
	// -- \\
	public void run()
	{
		Thread.currentThread().setName("Thread_Random Time Statement Sentence Thread");

		try {this.setToSleep();}
		catch(Exception ex){ dontSleep = false; }

		while(true)
		{
			try
			{
				if(this.outputEnabled && dontSleep && !this.isInterrupted())
					talkRandomTimeStatement();

				this.setToSleep();
			}

			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void talkRandomTimeStatement()
	{

		if(this.dontSleep)
			return;

		String query = "SELECT * FROM " + cDatabase.TABLE_STATEMENT_RANDOMS + " ORDER BY iTimestamp ASC;";
		ResultSet result = cAISettings.getDatabase().executeQuery(query);

		try
		{
			int id = result.getInt(1);
			String question = result.getString(2);

			cAISettings.getDatabase().executeUpdate("UPDATE " + cDatabase.TABLE_STATEMENT_RANDOMS + " SET 'iTimestamp' = " + System.currentTimeMillis() + " WHERE iID = '" + id + "';");

			question = cSentenceUtils.putAINameIntoString(question);
			System.out.println();
			cMain.ai.setNextAnswer(question);
			cMain.ai.speak();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	// -- //
	// -- || setToSleep
	// -- \\
	private void setToSleep() throws java.lang.InterruptedException
	{
		int randomTime = (int) (Math.random()*50000);
		this.timeToSleep = randomTime;

		sleep(this.timeToSleep);
	}

	// -- //
	// -- || setOutputEnabled
	// -- \\
	public void setOutputEnabled(boolean bBool)
	{
		this.outputEnabled = bBool;
	}

	// -- //
	// -- || refreshTimer
	// -- \\
	public void refreshTimer()
	{
		try
		{
			dontSleep = false;
			this.setToSleep();
		}
		catch(java.lang.InterruptedException ex) { dontSleep = false; }
	}
}
