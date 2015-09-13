package com.kolakcc.loljclient.view;

import com.camick.TableColumnAdjuster;
import com.kolakcc.loljclient.model.*;
import com.kolakcc.loljclient.model.swing.RecentGamesListModel;
import com.kolakcc.loljclient.model.swing.RunesInventoryTableModel;
import com.kolakcc.loljclient.util.FontUtils;
import com.kolakcc.loljclient.util.LocaleMessages;
import com.kolakcc.loljclient.view.ui.*;
import com.kolakcc.loljclient.view.ui.renderer.RecentGameListItemRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Map;

public class ProfileView extends KolaView {
	JTextField searchField;
	LocalizedJButton searchButton;
	
	JList<RecentGame> recentMatchesList;

	LocalizedJLabel summonerNameLabel, championNameLabel, killsLabel, deathsLabel, assistsLabel, summonerSpell1Label, summonerSpell2Label, goldEarnedLabel,
		   gamesNumber, currentGameChampionName;

	ItemImageLabel item1Label, item2Label, item3Label, item4Label, item5Label, item6Label;

	JPanel gameInfo, itemsPanel, statsPanel;
	
	JTable runesTable;
	JTabbedPane runePagesTabPane, leaguesTabPane;
	
	ChampionBox currentGameChampion;
	static LocaleMessages profileViewMessages = new LocaleMessages("profileViewBundle"),
						  statsMessages = new LocaleMessages("statsBundle");
	
	public ProfileView() {
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setTitle("Profile");
		
		JPanel topPanel = new JPanel(new BorderLayout());
		summonerNameLabel = new LocalizedJLabel();
		summonerNameLabel.setFont(FontUtils.emSize(summonerNameLabel.getFont(), 2.0));
		topPanel.add(summonerNameLabel,BorderLayout.WEST);
		
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		searchField = new JTextField(15);
		searchPanel.add(searchField);
		searchButton = new LocalizedJButton(profileViewMessages, "search");
		searchPanel.add(searchButton);
		topPanel.add(searchPanel,BorderLayout.EAST);
		
		add(topPanel,BorderLayout.NORTH);
		
		JTabbedPane tabPane = new JTabbedPane();
		JPanel recentGamesTabPanel = new JPanel(new BorderLayout());

		JPanel westPanel = new JPanel(new BorderLayout());
		JPanel recentMatchesPanel = new JPanel(new BorderLayout());
		recentMatchesList = new JList<RecentGame>();
		recentMatchesList.setCellRenderer(new RecentGameListItemRenderer());
		DefaultListModel<RecentGame> temp = new DefaultListModel<RecentGame>();
		recentMatchesList.setModel(temp);
		
		gamesNumber = new LocalizedJLabel();
		westPanel.add(gamesNumber, BorderLayout.NORTH);
		westPanel.add(new JScrollPane(recentMatchesList), BorderLayout.CENTER);
		recentMatchesPanel.add(westPanel, BorderLayout.WEST);
		
		gameInfo = new JPanel(new BorderLayout());
		JPanel topGamePanel = new JPanel(new BorderLayout());
		
		JPanel championAndName = new JPanel(new FlowLayout());
		currentGameChampion = new ChampionBox();
		championAndName.add(currentGameChampion);
		currentGameChampionName = new LocalizedJLabel();
		championAndName.add(currentGameChampionName);
		topGamePanel.add(championAndName, BorderLayout.NORTH);
		
		itemsPanel = new JPanel(new FlowLayout());
		item1Label = new ItemImageLabel(); itemsPanel.add(item1Label);
		item2Label = new ItemImageLabel(); itemsPanel.add(item2Label);
		item3Label = new ItemImageLabel(); itemsPanel.add(item3Label);
		item4Label = new ItemImageLabel(); itemsPanel.add(item4Label);
		item5Label = new ItemImageLabel(); itemsPanel.add(item5Label);
		item6Label = new ItemImageLabel(); itemsPanel.add(item6Label);
		topGamePanel.add(itemsPanel,BorderLayout.CENTER);
		
		gameInfo.add(topGamePanel,BorderLayout.NORTH);
		
		statsPanel = new JPanel(new GridLayout(0, 2));
		gameInfo.add(new VerticalJScrollPane(statsPanel),BorderLayout.CENTER);

		recentMatchesPanel.add(gameInfo, BorderLayout.CENTER);

		recentGamesTabPanel.add(recentMatchesPanel);
		tabPane.addTab(profileViewMessages.getString("recent games"), recentGamesTabPanel);

		leaguesTabPane = new JTabbedPane();
		tabPane.addTab(profileViewMessages.getString("leagues"), leaguesTabPane);
		
		JPanel runesPanel = new JPanel(new BorderLayout());
		runesTable = new JTable();
		runesPanel.add(new VerticalJScrollPane(runesTable),BorderLayout.WEST);
		
		runePagesTabPane = new JTabbedPane();
		runesPanel.add(runePagesTabPane,BorderLayout.CENTER);
		tabPane.add(profileViewMessages.getString("runes"),runesPanel);
		
		add(tabPane, BorderLayout.CENTER);
		setSize(600, 500);
		setVisible(true);
	}

	public void addRunePage(RunePage runePage) {
		runePagesTabPane.addTab(runePage.getName(), new RunePagePanel(runePage));
	}
	public void resetRunePages() {
		while (runePagesTabPane.getTabCount() > 0) runePagesTabPane.remove(0);
	}
	
	public void setRuneInventoryModel(RunesInventoryTableModel model) {
		runesTable.setModel(model);
	}
	
	public void setColumnSizes() {
		TableColumnAdjuster columnAdjuster = new TableColumnAdjuster(runesTable);
		int amountSize = Math.max(columnAdjuster.getColumnDataWidth(0), columnAdjuster.getColumnHeaderWidth(0));
		runesTable.getColumnModel().getColumn(0).setMaxWidth(amountSize);
		pack();
	}
	public void addSearchActionListener(ActionListener l) {
		searchButton.addActionListener(l);
		searchField.addActionListener(l);
	}
	public String getSearchString() {
		return searchField.getText();
	}
	public void setName(String name) {
		summonerNameLabel.setText(name);
	}
	public void addLeague(Division division, MouseListener clickListener, ActionListener actionListener) {
		leaguesTabPane.addTab(LocaleMessages.leagueTermsMessages.getString(division.getQueue()), new DivisionPanel(division, clickListener, actionListener));
	}
	public void clearLeagues() {
		while (leaguesTabPane.getTabCount() > 0) leaguesTabPane.remove(0);
	}
	public void filterCurrentDivisionTable() {
		DivisionPanel divisionPanel = ((DivisionPanel) leaguesTabPane.getSelectedComponent());
		divisionPanel.getTableModel().filterByTier((String) divisionPanel.getTierList().getSelectedItem());
	}
	public void setSelectedGame(RecentGame game) {
		item1Label.setItem(Item.getItemFromID(game.getStatistic("ITEM0")));
		item2Label.setItem(Item.getItemFromID(game.getStatistic("ITEM1")));
		item3Label.setItem(Item.getItemFromID(game.getStatistic("ITEM2")));
		item4Label.setItem(Item.getItemFromID(game.getStatistic("ITEM3")));
		item5Label.setItem(Item.getItemFromID(game.getStatistic("ITEM4")));
		item6Label.setItem(Item.getItemFromID(game.getStatistic("ITEM5")));
		
		statsPanel.removeAll();
		for (Map.Entry<String, Integer> entry : game.getStatistics().entrySet()) {
			String key = entry.getKey();
			if (key.startsWith("ITEM") || key.startsWith("WIN") || key.startsWith("LOSE")) continue;
			statsPanel.add(new LocalizedJLabel(statsMessages, entry.getKey()));
			statsPanel.add(new JLabel(entry.getValue().toString()));
		}
		try { currentGameChampion.setChampion(Champion.getChampionFromID(game.getChampionID())); }
		catch (Exception e) { e.printStackTrace(); }
		currentGameChampionName.setText(Champion.getChampionFromID(game.getChampionID()).getDisplayName());
		currentGameChampion.setSize(60, 60);
		gameInfo.revalidate();
	}
	public void setRecentGamesModel(RecentGamesListModel model) {
		recentMatchesList.setModel(model);
		recentMatchesList.getSelectionModel().addSelectionInterval(0, 0);
	}
	public int getSelectedGameIndex() {
		return recentMatchesList.getSelectedIndex();
	}
	public void addRecentGameMouseListener(MouseListener l) {
		recentMatchesList.addMouseListener(l);
	}
	
	public void setAmountObject(Object[] messageArguments) {
		gamesNumber.setText(profileViewMessages.getComplexString("games_amount", messageArguments));
	}
}