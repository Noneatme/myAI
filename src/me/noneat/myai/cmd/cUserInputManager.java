package me.noneat.myai.cmd;

import me.noneat.myai.cAISettings;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by Noneatme on 12.08.2015.
 */
// -- //
// -- || class UserInputManager
// -- \\
public class cUserInputManager extends Thread
{
	private BufferedReader m_uBR = new BufferedReader(new InputStreamReader(System.in));

	// -- //
	// -- || Constructor
	// -- \\
	public cUserInputManager()
	{

	}

	// -- //
	// -- || waitForInput
	// -- \\
	public synchronized void run()
	{
		String strLine;

		try
		{
			while ((strLine = this.m_uBR.readLine()) != null)
			{
				cAISettings.consoleUtil.setLoadingState(true);
				cUserInput currentInput = new cUserInput(strLine);
				cAISettings.consoleUtil.setLoadingState(false);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
