package me.noneat.myai.util;

import me.noneat.myai.cAISettings;
import me.noneat.myai.cMain;

/**
 * Created by Noneatme on 13.08.2015.
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
	public synchronized void run()
	{
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
