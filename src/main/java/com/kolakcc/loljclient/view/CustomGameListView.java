package com.kolakcc.loljclient.view;

import javax.swing.*;
import java.awt.*;

public class CustomGameListView extends KolaView {
	public JTable gamesListTable;
	public JButton refreshButton;
	public JButton createButton;
	public JButton filterButton;
	
	public CustomGameListView() {
		super();
		this.setTitle("Custom Games List");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		refreshButton = new JButton("Loading...");
		this.add(refreshButton,BorderLayout.NORTH);
		
		gamesListTable = new JTable();
		JScrollPane scroller = new JScrollPane(this.gamesListTable);
		this.add(scroller, BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout());

		filterButton = new JButton("Filters");
        panel.add(filterButton, BorderLayout.WEST);

        createButton = new JButton("Create Game");
        panel.add(createButton, BorderLayout.EAST);

        this.add(panel, BorderLayout.SOUTH);
		
		this.pack();
		this.setVisible(true);
	}
}
