package org.gkh.portmapper.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.gkh.portmapper.App;
import org.gkh.portmapper.gui.components.FooterBar;

public class DiscoveryFooterBar extends FooterBar {

	private static final long serialVersionUID = -533068144334389499L;
	
	private final JPanel leftSide = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
	
	private final JButton pauseButton = new JButton("Pause");
	private final JButton stopButton = new JButton("Stop Polling");

	public DiscoveryFooterBar() {
		this.addActionListeners();
		this.leftSide.add(this.pauseButton);
		this.leftSide.add(this.stopButton);
		this.add(this.leftSide, BorderLayout.WEST);
	}

	private void addActionListeners() {
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App.pause();
			}
		});
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App.stopPolling();
			}
		});
	}
	
}
