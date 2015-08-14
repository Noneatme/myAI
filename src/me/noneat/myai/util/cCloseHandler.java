package me.noneat.myai.util;

import me.noneat.myai.cAISettings;
import me.noneat.myai.cMain;

/**
 * Created by Noneatme on 13.08.2015.
 */
public class cCloseHandler extends Thread
{
	private long m_iLastMS      = System.currentTimeMillis();

	public cCloseHandler()
	{

	}


	public void resetCloseHandlerRequest()
	{
		this.m_iLastMS = System.currentTimeMillis();
	}

	public synchronized void run()
	{
		while(true)
		{
			try
			{
				if(System.currentTimeMillis()-this.m_iLastMS > cAISettings.TERMINATE_IDLE_TIME)
				{
					cMain.abort();
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
