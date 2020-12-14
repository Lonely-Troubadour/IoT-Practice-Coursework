package com.GUI;

import com.control.RfidControl;
import com.control.WsnControl;
import com.serialport.control.SerialController;
import com.uhf.demo.DatabaseController;
import com.uhf.demo.UhfDemo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Gui extends JFrame {
	
	public static final int WIDTH = 680;
	public static final int HEIGHT = 480;
	
	// Id check and env monitor
	IdCheckPanel ID;
	EnvMonitorPanel monitor;
	
	// Panels
	private JPanel WelcomePanel;
	private JTabbedPane tabbedPane;
	
	
	// Labels
	private JLabel timeLabel;
	private JLabel titleLabel;
	private JLabel logoLabel;
	
	// timer
	private Timer time;

	// rfid
	private Thread rfid;

	// wsn
	private int temperature;
	private int humidity;
	private int illumination;
	private Thread wsn;
	
	
	public Gui() {
		init();
	}
	
	/**
	 * Initialize frame
	 */
	private void init() {
		// Initializations
		setResizable(false);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setBounds(p.x - WIDTH/2, p.y - HEIGHT/2, WIDTH, HEIGHT);
		setTitle("Umbrella Corp. Admin Terminal");
		getContentPane().setLayout(new BorderLayout());
		
		
		// title Label
		WelcomePanel = new JPanel();
		WelcomePanel.setBounds(0, 0, 680, 50);
		
		// logo
		try {
			BufferedImage logo = ImageIO.read(new File("img/logo.jpeg"));
			logoLabel = new JLabel(new ImageIcon(logo));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		
		titleLabel = new JLabel("BUPT Laboratory, Group 13");
		titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		WelcomePanel.add(logoLabel);
		WelcomePanel.add(titleLabel);
		getContentPane().add(WelcomePanel, BorderLayout.NORTH);
		
		
		// Tab panel
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 60, 680, 300);
		
		ID = new IdCheckPanel();
		tabbedPane.addTab("ID", null, ID, null);
		
		monitor = new EnvMonitorPanel();
		tabbedPane.addTab("Environment Monitor", null, monitor, null);
		
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		// Time label
		timeLabel = new JLabel();
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		time = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				timeLabel.setText(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z").format(new Date()));
			}
		});
		time.start();
		getContentPane().add(timeLabel, BorderLayout.SOUTH);
		
//		update_epcName("007A0000000000003D225A6D:Alice");
//		update_env("B623", 28, 10, 100);
		
		
		
//		start_rfid();
//		start_wsn();
	}
	
	public void update_epcName(String result) {
		String[] strArr = result.split(":");
		
		ID.update_id(strArr[0], strArr[1]);
	}
	
	public void update_env(String addr, double temp, double hum, double illum) {
		monitor.update_env(addr, temp, hum, illum);
	}
	
	public void start_rfid() {
		rfid = new Thread(new RfidControl(this));
		rfid.start();
	}

	public void start_wsn() {
		wsn = new Thread(new WsnControl(this));
		wsn.start();
	}
	
	public static void main(String args[]) {
		new Gui().setVisible(true);
	}

}
