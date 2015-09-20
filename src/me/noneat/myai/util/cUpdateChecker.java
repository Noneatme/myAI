package me.noneat.myai.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Package: me.noneat.myai.util
 * Author: Noneatme
 * Date: 11.09.2015-09:13-2015.
 * Version: 1.0.0
 * License: See LICENSE.md in the top folder.
 */

// -- //
// -- || Class cUpdateChecker
// -- \\
public class cUpdateChecker
{
	// -- //
	// -- || PVars
	// -- \\
	private boolean updatesChecked      = false;
	private boolean checkUpdates        = false; // Set to true if you want to check for updates upon launch

	private String sUpdateURL           = "http://noneat.me/myai/api/checkupdate.json";

	// -- //
	// -- || Constructor
	// -- \\
	public cUpdateChecker()
	{

	}

	// -- //
	// -- || checkForUpdates
	// -- \\
	public void checkForUpdates()
	{
		if(!this.updatesChecked)
		{
			try
			{
				URL updateURL = new URL(this.sUpdateURL);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
