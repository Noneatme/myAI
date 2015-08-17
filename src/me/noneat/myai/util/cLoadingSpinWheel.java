package me.noneat.myai.util;

import me.noneat.myai.cAISettings;

/**
 * Created by Noneatme on 12.08.2015.
 */
// -- //
// -- || LoadingWheel Class
// -- \\
public class cLoadingSpinWheel extends Thread
{
	// -- //
	// -- || PVars
	// -- \\
	private int m_timeToSleep = 10;
	private boolean m_bDo   = true;

	// -- //
	// -- || run
	// -- \\
	public void run()
	{
		if(cAISettings.USE_ASCII_CHARS)
		{
			try
			{
				System.out.print("   ");
				while (this.m_bDo)
				{
					sleep(this.m_timeToSleep);
					System.out.print('/');
					sleep(this.m_timeToSleep);
					System.out.print('\b');
					sleep(this.m_timeToSleep);
					System.out.print('-');
					sleep(this.m_timeToSleep);
					System.out.print('\b');
					sleep(this.m_timeToSleep);
					System.out.print('\\');
					sleep(this.m_timeToSleep);
					System.out.print('\b');
					sleep(this.m_timeToSleep);
					System.out.print('|');
					sleep(this.m_timeToSleep);
					System.out.print('\b');
				}
			}
			catch (Exception ex)
			{
				this.m_bDo = false;
			}
		}
	}
}
