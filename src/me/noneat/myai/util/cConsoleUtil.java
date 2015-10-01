package me.noneat.myai.util;

/**
 * Package: me.noneat.myai.util
 * Author: Noneatme
 * Version: 1.0.0
 * License: See LICENSE.md in the top folder.
 */

// -- //
// -- || Class cConsoleUtil
// -- \\
public class cConsoleUtil
{
	// -- //
	// -- || PVars
	// -- \\
	private boolean m_bLoadingSpinEnabled = false;
	private cLoadingSpinWheel wheel;
	static cConsoleUtil instance;

	// -- //
	// -- || ConsoleUtils Constructor //
	// -- \\
	public cConsoleUtil()
	{
		this.wheel              = new cLoadingSpinWheel();
		cConsoleUtil.instance   = this;
	}

	// -- //
	// -- || SetLoadingState
	// -- \\
	public boolean setLoadingState(boolean bBool)
	{
		if(((bBool == true) && (this.m_bLoadingSpinEnabled)) || ((bBool == false) && (!this.m_bLoadingSpinEnabled)))
			return false;

		this.m_bLoadingSpinEnabled = bBool;

		try
		{
			if (this.m_bLoadingSpinEnabled)
			{
				this.wheel = new cLoadingSpinWheel();
				this.wheel.start();
			}
			else
			{
				this.wheel.interrupt();
				//System.out.println();

			}
		}
		catch(java.lang.IllegalThreadStateException ex)
		{
			// Unsauber!
		}

		return this.m_bLoadingSpinEnabled;
	}
	// -- //
	// -- || getInstance
	// -- \\
	public static cConsoleUtil getInstance()
	{
		return cConsoleUtil.instance;
	}
}
