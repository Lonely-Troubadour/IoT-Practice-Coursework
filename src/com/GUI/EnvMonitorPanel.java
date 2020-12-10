package com.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

public class EnvMonitorPanel extends JPanel {
	private JTextPane dataView;
	private JTable table;
	private JScrollPane scrollDataView;
	Vector<Vector<Object>> data;
	Vector<String> columnNames;
	
	public EnvMonitorPanel() {
		// TODO Auto-generated constructor stub
		initTable();
		initDataView();
	}
	
	public void initTable() {
		columnNames = new Vector<String>();
		columnNames.add("Time");
		columnNames.add("addr");
//		columnNames.add("Location");
		columnNames.add("Temperature");
		columnNames.add("Humidity");
		columnNames.add("Illumination");
		data = new Vector<Vector<Object>>();
//		Object[][] rowData = {{"", "", "", "", ""}};
//		{{new SimpleDateFormat("HH:mm:ss").format(new Date()), "A1", "30", "10", "100"}};
//		Vector<Object> row = new Vector<Object>();
//		row.add(new SimpleDateFormat("HH:mm:ss").format(new Date()));
//		row.add("b123");
//		row.add(30);
//		row.add(10);
//		row.add(100);
//		data.add(row);
		
		this.table = new JTable(data, columnNames);
		this.table.setEnabled(false);
	}
	
	
	public void initDataView() {
		setLayout(null);
		
		dataView = new JTextPane();
		scrollDataView = new JScrollPane(table);
		
		dataView.setFocusable(false);
		dataView.setBounds(0, 0, 655, 400);
		
		scrollDataView.setBounds(0, 0, 655, 400);
		
		JButton btn = new JButton();
		btn.setBounds(100, 100, 30, 30);
		btn.addActionListener(e -> {
			this.update_env("b123", 1, 1, 1);
		});
		add(btn);
		
		JButton btn2 = new JButton();
		btn2.setBounds(140, 140, 30, 30);
		btn2.addActionListener(e -> {
			this.update_env("b321", 1, 1, 1);
		});
		add(btn2);
		add(scrollDataView);
	}
	
	public void update_env(String addr, double temp, double hum, double illum) {
		int new_flag = 1;
		Vector<Object> row;
		for (Vector v: data) {
			if (v.get(1).equals(addr)) {
				v.set(0, new SimpleDateFormat("HH:mm:ss").format(new Date()));
				v.set(2, temp);
				v.set(3, hum);
				v.set(4, illum);
				new_flag = 0;
			}
		}
		if (new_flag == 1) {
			row = new Vector<>();
			row.add(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			row.add(addr);
			row.add(temp);
			row.add(hum);
			row.add(illum);
			data.add(row);
			DefaultTableModel model = new DefaultTableModel(data, this.columnNames);
			table.setModel(model);
		}
		
		this.validate();
		this.updateUI();
	}
}
