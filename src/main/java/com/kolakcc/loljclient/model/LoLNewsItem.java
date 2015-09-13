package com.kolakcc.loljclient.model;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LoLNewsItem {
	public String title;
	public URL link;
	public Date pubDate; 
	public String description;
	public String content;
	public String creator;
	public String category;
	public URL GUID;

	public LoLNewsItem(Element item) {
		this.title = this.getTagValue("title", item);
		try {
			this.link = new URL(this.getTagValue("link", item));
			this.pubDate = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss", Locale.ENGLISH)
					.parse(this.getTagValue("pubDate", item));
			this.link = new URL(this.getTagValue("guid", item));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.description = this.getTagValue("description", item);
		this.content = this.getTagValue("content:encoded", item);
		this.creator = this.getTagValue("dc:creator", item);
		this.category = this.getTagValue("category", item);
	}

	protected String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();
		Node nValue = nlList.item(0);

		return nValue.getNodeValue();
	}
}
