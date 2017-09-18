package org.gkh.portmapper;

import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;

public class SimpleRegistryListener implements RegistryListener {

	public void afterShutdown() {
		System.out.println("Shutdown of registry complete!");
	}

	public void beforeShutdown(Registry registry) {
		System.out.println(
				"Before shutdown, the registry has devices: "
						+ registry.getDevices().size());
	}

	public void localDeviceAdded(Registry registry, LocalDevice device) {
		System.out.println("Local device added: "
				+ device.getDisplayString());
	}

	public void localDeviceRemoved(Registry registry, LocalDevice device) {
		System.out.println("Local device removed: "
				+ device.getDisplayString());
	}

	public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
		System.out.println("Remote device added: "
				+ device.getDisplayString());
	}

	public void remoteDeviceDiscoveryFailed(Registry registry, RemoteDevice device,
			Exception ex) {
		System.out.println("Remote discovery failed: "
				+ device.getDisplayString() + " =>" + ex);
	}

	public void remoteDeviceDiscoveryStarted(Registry registry, RemoteDevice device) {
		System.out.println("Discovery started: "
				+ device.getDisplayString());
	}

	public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
		System.out.println("Remote device removed: "
				+ device.getDisplayString());
	}

	public void remoteDeviceUpdated(Registry registry, RemoteDevice device) {
		System.out.println("Remote device updates: "
				+ device.getDisplayString());
	}

}
