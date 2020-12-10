package com.control;

import com.GUI.Gui;
import com.serialport.control.SerialController;

public class WsnControl implements Runnable {
	private Gui gui;
	private SerialController serialController = new SerialController();
	
	public WsnControl(Gui gui) {
		this.gui = gui;
	}
	
	public void run() {
		this.serialController.setOnDataAvailableListener(new SerialController.OnDataAvailableListener() {
			@Override
			public void onDataAvailable(String addr, double temperature, double humiliation, double illumination) {
				gui.update_env(addr, temperature, humiliation, illumination);
			}
		});
	}
}
