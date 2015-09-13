package com.kolakcc.loljclient;

import com.gvaneyck.rtmp.LoLRTMPSClient;
import com.kolakcc.loljclient.controller.*;
import com.kolakcc.loljclient.model.Champion;
import com.kolakcc.loljclient.model.CustomGameListItem;
import com.kolakcc.loljclient.model.LoLNewsItem;
import com.kolakcc.loljclient.util.Configuration;
import com.kolakcc.loljclient.util.FileSystem;
import com.kolakcc.loljclient.view.SettingsView;
import com.kolakcc.loljclient.view.WebView;
import com.kolakcc.swf.SWFExtractor;

import javax.swing.*;
import java.io.File;

public class StartupClass {
	public static LoLRTMPSClient Client;
	static LoginController loginController;
	static MainController mainController;
	static CustomGameListController customGameListController;
	static FriendsListController friendsListController;
	static ChampionsController championsController;
	static AboutController aboutController;
	static StoreController storeController;
	static DebugController consoleController;
	public static CustomGameLobbyController customGameLobbyController;

	public static void LoggedIn() {
		loginController.dispose();
		mainController = new MainController();
		Client.setReceiveHandler(new ReceivePacketController());
	}

	public static void main(String[] args) {
		String lafConfiguration = Configuration.getDefaultLAF();
		if (lafConfiguration.equals("")) Configuration.setDefaultLAF(UIManager.getSystemLookAndFeelClassName());
		
		try {
			UIManager.setLookAndFeel(lafConfiguration);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.printf("L&F by the name of %s could not found %n", lafConfiguration);
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println("No System Look and Feel found, continuing with default...");
			} 
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//Do some basic checks first - can League be found? are the SWF files extracted?
				consoleController = new DebugController();
				if (!Configuration.checkLeagueDirectory()) { 
					JFrame dummy = new JFrame();
					JOptionPane.showMessageDialog(dummy, "Your League of Legends directory could not be found.", "Error", JOptionPane.ERROR_MESSAGE);
					JFileChooser leagueFileChooser = new JFileChooser();
					leagueFileChooser.setDialogTitle("Select your League of Legends Directory.");
					leagueFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					leagueFileChooser.showOpenDialog(dummy);
					
					File selectedLeagueDirectory = leagueFileChooser.getSelectedFile();
					boolean found = false;
					for (File checkFile : selectedLeagueDirectory.listFiles()) {
						if (checkFile.getName().equals("lol.launcher.exe")) {
							System.out.println("Found lol.launcher.exe.");
							found = true;
							break;
						}
                        if (checkFile.getName().equals("Play League Of Legends.app")) {
                            System.out.println("Found Play League Of Legends.app.");
                            found = true;
                            break;
                        }
					}
					if (found) {
						System.out.println("This seems to be a valid league directory.");
						Configuration.setLeagueDirectory(selectedLeagueDirectory);
						loginController = new LoginController();
					} else {
						JOptionPane.showMessageDialog(dummy, "This directory doesn't seem to be a valid League of Legends Directory. Does it have lol.launcher.exe in it?.", "Error", JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					}
				}
				File itemsFolder = FileSystem.getFile("app://img/items/");
				if (itemsFolder.list().length < 10) {
					new SWFExtractor();
				}
				loginController = new LoginController();
			}
		});
	}

	public static void openAbout() {
		aboutController = new AboutController();
	}

	public static void openChampionDetails(Champion c) {
		new ChampionDetailedController(c);
	}

	public static void openChampions() {
		new ChampionsController();
	}

	public static void openCustomGameLobbyController(CustomGameListItem game) {
		customGameLobbyController = new CustomGameLobbyController(game);
	}

	public static void openCustomGames() {
		customGameListController = new CustomGameListController();
	}

	public static void openFriends(String username, String password) {
		friendsListController = new FriendsListController(username, password);
	}

	public static void openNewsItem(LoLNewsItem item) {
		new WebView(item);
	}

	public static void openProfile() {
		new ProfileController();
	}

	public static void openSettings() {
		new SettingsView();
	}
	public static void openStore() {
		storeController = new StoreController();
	}
}
