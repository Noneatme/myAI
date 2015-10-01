package me.noneat.myai.util;

import me.noneat.myai.cAISettings;
import me.noneat.myai.cMain;

/**
 * Package: me.noneat.myai.util
 * Author: Noneatme
 * Version: 1.0.0
 * License: See LICENSE.md in the top folder.
 */
// -- //
// -- || closeHandler
// -- \\
public class cCloseHandler extends Thread
{
	private long m_iLastMS      = System.currentTimeMillis();

	// -- //
	// -- || constructor
	// -- \\
	public cCloseHandler()
	{

	}

	// -- //
	// -- || resetCloseHandlerRequest
	// -- \\
	public void resetCloseHandlerRequest()
	{
		this.m_iLastMS = System.currentTimeMillis();
	}

	// -- //
	// -- || run
	// -- \\
	public void run()
	{
		Thread.currentThread().setName("Thread_Close Handler Thread");

		while(true)
		{
			try
			{
				if(cMain.ai.canBeTerminated())
				{
					if ((System.currentTimeMillis() - this.m_iLastMS > cAISettings.TERMINATE_IDLE_TIME) && cAISettings.TERMINATE_IDLE_TIME > 0)
					{
						cMain.abort();
					}
				}

				sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
