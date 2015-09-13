package com.kolakcc.loljclient.model.swing;

import com.kolakcc.loljclient.model.LoggedInSummoner;
import com.kolakcc.loljclient.model.RunePage;

import javax.swing.*;
import javax.swing.event.ListDataListener;

public class RunePageComboBoxModel implements ComboBoxModel<RunePage> {
	private RunePage selected;
	public RunePageComboBoxModel(RunePage defaultRunePage) {
		selected = defaultRunePage;
	}
	
	@Override
	public int getSize() {
		return LoggedInSummoner.summonerData.getRunePages().size();
	}

	@Override
	public RunePage getElementAt(int index) {
		return LoggedInSummoner.summonerData.getRunePages().get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
	}

	@Override
	public void setSelectedItem(Object anItem) {
		//TODO: send server set
		selected = (RunePage) anItem;
	}

	@Override
	public RunePage getSelectedItem() {
		return selected;
	}
}
