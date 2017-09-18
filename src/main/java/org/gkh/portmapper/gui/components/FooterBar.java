package org.gkh.portmapper.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class FooterBar extends JPanel {

	private static final long serialVersionUID = -7362353656493635490L;
	
	protected final JPanel rightSide = new JPanel(new FlowLayout());

	public FooterBar() {
		super(new BorderLayout());
        this.setBorder(BorderFactory.createEtchedBorder());
        this.setPreferredSize(new Dimension(0, 50));
        this.add(this.rightSide, BorderLayout.EAST);
	}
}
