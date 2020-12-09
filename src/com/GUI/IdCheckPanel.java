package com.GUI;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;

public class IdCheckPanel extends JPanel {
	private JLabel welcomeLabel;
	private JPanel idPanel;
	private JLabel epcLable;
	private JLabel nameLabel;
	
	public IdCheckPanel() {
		setLayout(new BorderLayout(0, 0));
		
		// Welcome panel
		welcomeLabel = new JLabel("Welcome,");
		welcomeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(welcomeLabel, BorderLayout.NORTH);
		
		// Id display panel
		idPanel = new JPanel();
		epcLable = new JLabel("Epc: ");
		nameLabel = new JLabel("Name: ");
		
		epcLable.setFont(new Font("American Typewriter", Font.PLAIN, 24));
		nameLabel.setFont(new Font("American Typewriter", Font.PLAIN, 24));
		
		idPanel.setLayout(null);
		epcLable.setBounds(200, 0, 220, 100);
		nameLabel.setBounds(200, 50, 220, 100);
		idPanel.add(epcLable);
		idPanel.add(nameLabel);
		
		add(idPanel, BorderLayout.CENTER);
		
		// update_id("007b", "huyongjian");
	}
	
	public void update_id(String epc, String name) {
		this.epcLable.setText("Epc: " + epc);
		this.nameLabel.setText("Name: " + name);
	}
}
