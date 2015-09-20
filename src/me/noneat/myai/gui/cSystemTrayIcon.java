package me.noneat.myai.gui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import me.noneat.myai.cAISettings;

import javax.swing.*;

/**
 * Package: me.noneat.myai.gui
 * Author: Noneatme
 * Date: 01.09.2015-12:58-2015.
 * Version: 1.0.0
 * License: See LICENSE.md in the top folder.
 */
public class cSystemTrayIcon
{
	private Stage stage;
	private SystemTray tray;
	private TrayIcon icon;

	public void setTrayIcon(Stage stage)
	{
		this.stage                          = stage;

		this.tray                           = SystemTray.getSystemTray();
		Image image                         = Toolkit.getDefaultToolkit().getImage("");

		PopupMenu popup                     = new PopupMenu();
		MenuItem showItem                   = new MenuItem("Offnen");
		MenuItem exitItem                   = new MenuItem("Beenden");
		this.icon                           = new TrayIcon(image, cAISettings.APPLICATION_NAME + " " + cAISettings.VERSION, popup);
		this.icon.addActionListener(
				e -> Platform.runLater(() -> this.doOpen())
		);

		ActionListener listenerShow         = e -> Platform.runLater(() -> this.doOpen());
		ActionListener listenerClose        = e -> System.exit(0);

		stage.setOnCloseRequest(arg0 -> this.doHide());

		showItem.addActionListener(listenerShow);
		exitItem.addActionListener(listenerClose);

		popup.add(showItem);
		popup.add(exitItem);

	}

	private void doOpen()
	{
		this.stage.show();

		try
		{
			this.tray.remove(this.icon);

		}
		catch (Exception e)
		{
			System.err.println(e);
		}
	}

	private void doHide()
	{
		this.stage.hide();

		try
		{
			this.tray.add(this.icon);

		}
		catch (AWTException e)
		{
			System.err.println(e);
		}
	}
}
