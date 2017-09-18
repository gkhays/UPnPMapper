package org.gkh.portmapper;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ToolTipManager;
import javax.swing.UIManager;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.model.message.header.STAllHeader;
import org.fourthline.cling.registry.RegistryListener;
import org.gkh.portmapper.gui.DiscoveryFrame;
import org.gkh.portmapper.gui.components.DiscoveryPane;

public class App {
	
	private static final SimpleDateFormat format = new SimpleDateFormat("dd/M/yyy HH:mm:ss a");
	
	private static Timer upnpDiscoveryTask;
	private static UpnpService upnpService;
	private static CachingRegistryListener listener;
	
	private static DiscoveryFrame frame;

	public static void main(String[] args) {
		try {
			setLaF();
			modifyLaF();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		frame = new DiscoveryFrame();
		frame.setVisible(true);
		frame.discoveryPane.setColor(new Color(0, 0, 0)).setText("Looking for UPnP devices...");
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		listener = new CachingRegistryListener();		
		
		upnpDiscoveryTask = new Timer(true);
		upnpDiscoveryTask.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				upnpService = new UpnpServiceImpl(listener);
				upnpService.getControlPoint().search(new STAllHeader());
				updateStatus();
				frame.setCursor(Cursor.getDefaultCursor());
			}
		}, 1000, 10 * 1000);
	}
	
	protected static void updateStatus() {
		Set<String> deviceSet = listener.getDeviceSet();
		System.out.format("Found %d devices\n", deviceSet.size());
		frame.discoveryPane.setText(null);
		
		Iterator<String> iter = deviceSet.iterator();
		while (iter.hasNext()) {
			String line = iter.next();
			line = (!line.endsWith("\n") ? line + "\n" : line);
			frame.discoveryPane.setColor(new Color(137, 194, 54)).setBold(true)
					.write("[" + format.format(new Date()) + "] ");
			frame.discoveryPane.setColor(new Color(0, 0, 0)).setBold(false)
					.write(line);
		}
	}

	/**
	 * Set the look and feel to Nimbus.
	 * 
	 * @throws Exception
	 */
	private static void setLaF() throws Exception {
		for (UIManager.LookAndFeelInfo info : UIManager
				.getInstalledLookAndFeels()) {
			if (info.getName().equalsIgnoreCase("nimbus")) {
				UIManager.setLookAndFeel(info.getClassName());
			}
		}
	}
	
	private static void modifyLaF() throws Exception {
		ToolTipManager.sharedInstance().setDismissDelay(15000);
		ToolTipManager.sharedInstance().setInitialDelay(50);
		UIManager.put("FileChooser.readOnly", Boolean.TRUE);
		UIManager.put("ScrollBar.minimumThumbSize", new Dimension(50, 50));
	}

	public static void pause() {
		try {
			upnpDiscoveryTask.wait(5 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void stopPolling() {
		System.out.println("Stopping...");
		upnpDiscoveryTask.cancel();
		upnpDiscoveryTask.purge();
		upnpService.shutdown();
		System.out.println("UPnP service shut down");
	}

}
