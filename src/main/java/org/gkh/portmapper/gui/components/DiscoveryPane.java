package org.gkh.portmapper.gui.components;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class DiscoveryPane extends JTextPane {

	private static final long serialVersionUID = 4274692117781847357L;
	private final SimpleAttributeSet attrs = new SimpleAttributeSet();
	
	public DiscoveryPane() {
		this.setEditable(false);
	}
	
	public DiscoveryPane setColor(Color c) {
        StyleConstants.setForeground(this.attrs, c);
        return this;
    }

    public DiscoveryPane setBold(boolean b) {
        StyleConstants.setBold(this.attrs, b);
        return this;
    }
    
    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true; // Word wrapping.
    }

	public void write(String line) {
		try {
			this.getDocument().insertString(this.getDocument().getLength(),
					line, this.attrs);
			this.setCaretPosition(this.getDocument().getLength());
		} catch (BadLocationException ex) {
			ex.printStackTrace(System.err);
		}
	}
	
}
