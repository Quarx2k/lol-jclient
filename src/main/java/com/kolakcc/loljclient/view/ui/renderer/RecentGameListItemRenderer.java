package com.kolakcc.loljclient.view.ui.renderer;

import com.kolakcc.loljclient.model.Champion;
import com.kolakcc.loljclient.model.RecentGame;
import com.kolakcc.loljclient.util.LocaleMessages;
import com.kolakcc.loljclient.view.ui.ChampionBox;

import javax.swing.*;
import java.awt.*;

public class RecentGameListItemRenderer implements ListCellRenderer<RecentGame> {
	public Component getListCellRendererComponent(
			JList<? extends RecentGame> list, RecentGame value, int index,
			boolean isSelected, boolean cellHasFocus) {
		JPanel ret = new JPanel(new BorderLayout());
		ret.setBackground(value.getStatistics().containsKey("WIN") ? Color.green : Color.red);
		Champion champion = Champion.getChampionFromID(value.getChampionID());
		try {
			ret.add(new ChampionBox(champion), BorderLayout.WEST);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ret.add(new JLabel(String.format("<html>%s %d/%d/%d<br>%s", champion.getDisplayName(), value.getStatistic("CHAMPIONS_KILLED"), value.getStatistic("NUM_DEATHS"), value.getStatistic("ASSISTS"), LocaleMessages.leagueTermsMessages.getString(value.getMap().toString()))),BorderLayout.CENTER);
		return ret;
	}
}
