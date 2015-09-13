package com.kolakcc.loljclient.view.ui;

import javax.swing.*;
import java.awt.*;

public class VerticalJScrollPane extends JScrollPane {
	public VerticalJScrollPane(Component c) {
		super(c, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}
}
