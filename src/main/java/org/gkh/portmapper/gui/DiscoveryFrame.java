package org.gkh.portmapper.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import org.gkh.portmapper.Constants;
import org.gkh.portmapper.gui.components.DiscoveryPane;

public class DiscoveryFrame extends JFrame {

	private static final long serialVersionUID = 5471691600666607573L;
	
	public DiscoveryPane discoveryPane;
	private JScrollPane scrollPane;
	private DiscoveryFooterBar footerBar;
	
	public DiscoveryFrame() {
		this.setTitle("UPnP Discovery Console");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(600, 400));
		this.setLayout(new BorderLayout());
		
		discoveryPane = new DiscoveryPane();
		discoveryPane.setFont(new Font("SansSerif", Font.TRUETYPE_FONT, 12));
		// console.setForeground(new Color(255, 255, 255));
		discoveryPane.setSelectionColor(Constants.HIGHLIGHT_COLOR);
		
		footerBar = new DiscoveryFooterBar();
		
		scrollPane = new JScrollPane(discoveryPane,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.CENTER);
		add(footerBar, BorderLayout.SOUTH);
	}

}
