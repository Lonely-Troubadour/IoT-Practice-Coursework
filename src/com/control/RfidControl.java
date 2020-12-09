package com.control;

import com.GUI.Gui;
import com.uhf.demo.DatabaseController;
import com.uhf.demo.UhfDemo;

public class RfidControl implements Runnable {
	private Gui gui;
	private DatabaseController dbController = new DatabaseController("Data/identities.csv");
	private UhfDemo uhf = new UhfDemo("COM4", dbController);
	
	public RfidControl(Gui gui) {
		this.gui = gui;
	}
	
	public void run() {
		while(true) {
			try {
				String result = this.uhf.epcReadSync(2,6);
				gui.update_epcName(result);
				Thread.sleep(5000);
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println(e.getMessage());
			}
		}
	}
}
