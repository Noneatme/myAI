package me.noneat.myai.output;

import me.noneat.myai.cAISettings;
import me.noneat.myai.cMain;

/**
 * Package: me.noneat.myai.output
 * Author: Noneatme
 * Date: 22.09.2015-11:12-2015.
 * Version: 1.0.0
 * License: See LICENSE.md in the top folder.
 *
 *
 * This class outputs a sting in a cycle of bytes within 25 milliseconds for each char.
 * This is usefull for displaying a pseudo talked sentence.
 */


public class cConsoleSentenceWriter extends Thread
{
	private String sentenceToWrite;
	private byte currentCharPos;
	private int msToSleep = 25;

	public cConsoleSentenceWriter(String sentence)
	{
		this.sentenceToWrite    = sentence;
		this.currentCharPos     = 0;

		this.start();
	}

	public void run()
	{
		Thread.currentThread().setName("Console Sentence Writer Thread");

		if(cAISettings.USE_GUI)
			cMain.app.beginWriteAIMessage();

		while(this.currentCharPos < this.sentenceToWrite.length())
		{
			char currentChar = this.sentenceToWrite.charAt(this.currentCharPos);
			this.currentCharPos++;


			System.out.print(currentChar);

			if(cAISettings.USE_GUI)
				cMain.app.writeAIChar(currentChar);

			try
			{
				sleep(this.msToSleep);
			}
			catch(InterruptedException ex) {}
		}

		System.out.println();
		System.out.println();

		if(cAISettings.USE_GUI)
		{
			cMain.app.endWriteAIMessage();
		}
		this.interrupt(); // Interrupt here because we don't want this thread to be existing anymore
	}
}
