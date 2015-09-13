package com.kolakcc.loljclient.view.ui;

import com.kolakcc.loljclient.util.LocaleMessages;

import javax.swing.*;

public class LocalizedJCheckBox extends JCheckBox {
	String key;
	
	public LocalizedJCheckBox() {
		super(LocaleMessages.generalMessages.getString("loading"));
		this.key = "loading";
	}
	
	public LocalizedJCheckBox(String key) {
		super(LocaleMessages.generalMessages.getString(key));
		this.key = key;
	}
	
	public LocalizedJCheckBox(LocaleMessages messages, String key) {
		super(messages.getString(key));
		this.key = key;
	}
	
	public void setKey(String key) {
		super.setText(LocaleMessages.generalMessages.getString(key));
		this.key = key;
	}
	
	public void setKey(LocaleMessages messages, String key) {
		super.setText(messages.getString(key));
		this.key = key;
	}
	
	public String getKey() { return key; }
}
