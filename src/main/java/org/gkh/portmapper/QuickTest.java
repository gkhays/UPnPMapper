package org.gkh.portmapper;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.model.message.header.STAllHeader;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;
import org.fourthline.cling.support.igd.PortMappingListener;
import org.fourthline.cling.support.model.PortMapping;

public class QuickTest {

	public static void getInformation(RemoteDevice device) {
		device.getDetails();
	}
	
	public static void quickRun() {
		PortMapping desiredMapping = new PortMapping(25565, "192.168.0.11",
				PortMapping.Protocol.TCP, "My Port Mapping");

		UpnpService upnpService = new UpnpServiceImpl(new PortMappingListener(
				desiredMapping));
		
		upnpService.getControlPoint().search();
		
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		upnpService.shutdown();
	}
	
	public static void runForNSeconds(int s) {
		RegistryListener listener = new SimpleRegistryListener();

		UpnpService upnpService = new UpnpServiceImpl(listener);
		upnpService.getControlPoint().search(new STAllHeader());

		try {
			Thread.sleep(s * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		upnpService.shutdown();
	}
	
	/**
	 * @see <a
	 *      href="http://4thline.org/projects/cling/core/manual/cling-core-manual.xhtml#chapter.GettingStarted">Cling
	 *      Getting Started</a>
	 *      
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Run for 10 seconds.
		// runForNSeconds(10);
		
		String line;
		Scanner in = new Scanner(System.in);		

		RegistryListener listener = new CachingRegistryListener();
		UpnpService upnpService = new UpnpServiceImpl(listener);
		upnpService.getControlPoint().search(new STAllHeader());
		
		while (true) {
			line = in.next();			
			if (line.toLowerCase().equals("q")) {
				break;
			}
			
			try {				
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		in.close();		
		
		Set<String> deviceSet = ((CachingRegistryListener)listener).getDeviceSet();
		System.out.format("Found %d devices\n", deviceSet.size());
		Iterator<String> iter = deviceSet.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
		
		upnpService.shutdown();
		System.out.println("Exiting...");
		
	}

}
