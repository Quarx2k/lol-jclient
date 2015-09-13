package com.kolakcc.loljclient.model;

import com.gvaneyck.rtmp.encoding.TypedObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class MasteryBook extends ModelFromTO implements Iterable<MasteryPage> {
	ArrayList<MasteryPage> pages;
	Object bookPagesJson;
	int dataVersion;
	String dateString;
	Object futureData;
	double summonerID;
	
	public MasteryBook(TypedObject ito) {
		super(ito);
		bookPagesJson = getProbablyNull("bookPagesJson");
		dataVersion = getInt("dataVersion");
		dateString = getString("dateString");
		futureData = getProbablyNull("futureData");
		summonerID = getDouble("summonerId");
		pages = new ArrayList<MasteryPage>();
		for (TypedObject page : getArray("bookPages")) {
			pages.add(new MasteryPage(page));
		}
		Collections.sort(pages);
		checkFields();
	}
	public MasteryPage getPage(int index) { return pages.get(index); }
	public int size() { return pages.size(); }
	@Override
	public Iterator<MasteryPage> iterator() {
		return pages.iterator();
	}
}
