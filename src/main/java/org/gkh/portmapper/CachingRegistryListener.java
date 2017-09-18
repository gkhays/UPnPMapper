package org.gkh.portmapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fourthline.cling.model.meta.Icon;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.meta.RemoteDeviceIdentity;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;

public class CachingRegistryListener implements RegistryListener {
	
	private static List<String> deviceList = new ArrayList<String>();

	public void afterShutdown() {
	}

	public void beforeShutdown(Registry registry) {
	}

	// TODO: This isn't thread safe.
	// https://possiblelossofprecision.net/?p=813
	// https://stackoverflow.com/a/24362411/6146580
	public Set<String> getDeviceSet() {
		return new HashSet<String>(deviceList);
	}
	
	public void localDeviceAdded(Registry registry, LocalDevice device) {
		System.out.println("Local device added: " + device.getIdentity().toString());
		deviceList.add(device.getDisplayString());
	}

	public void localDeviceRemoved(Registry registry, LocalDevice device) {
		if (deviceList.contains(device)) {
			deviceList.remove(device);
		}
	}

	public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
		RemoteDeviceIdentity id = device.getIdentity();
		Icon[] icons = device.getIcons();
		System.out.println("Remote device added");
		System.out.println("  Identity:");
		System.out.format("    Descriptor (URL): %s\n", id.getDescriptorURL());
		System.out.format("    Max age (s): %s\n", id.getMaxAgeSeconds());
		System.out.format("    UDN: %s\n", id.getUdn());
		System.out.format("  Display name: %s\n", device.getDisplayString());
		System.out.format("  Type: %s\n", device.getType());
		System.out.format("  Found %d icons\n", icons.length);
		deviceList.add(device.getDisplayString());
	}

	public void remoteDeviceDiscoveryFailed(Registry registry, RemoteDevice device,
			Exception ex) {
		System.out.format("Remote discovery failed for %s\n", device.getDisplayString());
		System.out.println(ex.getMessage());
	}

	public void remoteDeviceDiscoveryStarted(Registry registry,
			RemoteDevice device) {
		deviceList.add(device.getDisplayString());
		System.out.format("Remote discovery started for %s\n",
				device.getDisplayString());
	}

	public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
		if (deviceList.contains(device)) {
			deviceList.remove(device);
		}
	}

	public void remoteDeviceUpdated(Registry registry, RemoteDevice device) {
		deviceList.add(device.getDisplayString());
		System.out.format("Remote device %s updated\n", device.getDisplayString());
	}

}
