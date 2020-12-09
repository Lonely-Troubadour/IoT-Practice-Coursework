package com.uhf.demo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class DatabaseController {
	private String filePath;
	private File file;
	private LinkedHashMap<String, String> database;

	public DatabaseController(String filePath) {
		this.filePath = filePath;
		checkFilePath();
		loadDatabase();
	}

	private void checkFilePath() {
		this.file = new File(this.filePath);

		if (!this.file.exists()) {
			try {
				this.file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Error: file not exist.");
		}
	}

	private void loadDatabase() {
		this.database = new LinkedHashMap<String, String>();
		
		try {
			FileInputStream fileInputStream = new FileInputStream(this.filePath);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));

			String nextLine = bufferedReader.readLine();

			while((nextLine = bufferedReader.readLine()) != null) {
				String[] attributes = nextLine.split(",");
				this.database.put(attributes[0], attributes[1]);
			}
			
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String checkIdentity(String id) {
		for (Entry<String, String> entry : this.database.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if(key.equals(id)) {
				return key + ":" + value;
			}
		}

		return null;
	}
	
}

