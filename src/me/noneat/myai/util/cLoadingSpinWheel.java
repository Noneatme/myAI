package me.noneat.myai.util;

import me.noneat.myai.cAISettings;

/**
 * Package: me.noneat.myai.util
 * Author: Noneatme
 * Version: 1.0.0
 * License: See LICENSE.md in the top folder.
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
		Thread.currentThread().setName("Thread_Loading Spin Wheel Thread");

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

				System.out.print('\b');
			}
			catch (Exception ex)
			{
				this.m_bDo = false;
			}
		}
	}
}
