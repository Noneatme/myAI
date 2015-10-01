package me.noneat.myai.cmd;

/**
 * Package: me.noneat.myai.cmd
 * Author: Noneatme
 * Date: 01.09.2015-12:02-2015.
 * Version: 1.0.0
 * License: See LICENSE.md in the top folder.
 */
public class cGuiInputManager extends Thread
{
	private String sCurrentInput;

	public cGuiInputManager(String aasddf)
	{
		this.sCurrentInput = aasddf;
		System.out.println(this.sCurrentInput);
		this.start();
	}

	public synchronized void run()
	{
		Thread.currentThread().setName("Thread_GUI Input Thread");

		cUserInput currentInput = new cUserInput(this.sCurrentInput);
		this.sCurrentInput = null;
	}

	public void setCurrentInput(String sInput)
	{
		this.sCurrentInput = sInput;
	}
}
