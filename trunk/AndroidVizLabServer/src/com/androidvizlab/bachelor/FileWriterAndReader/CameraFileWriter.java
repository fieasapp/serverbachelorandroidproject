package com.androidvizlab.bachelor.FileWriterAndReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import static com.androidvizlab.bachelor.FileWriterAndReader.Helpmethods.formatSentence;

public class CameraFileWriter {
	
	ArrayList<String> tab;
	File file;
	
	public CameraFileWriter(File file) throws IOException{
		this.file = file;
		
		BufferedReader in = new BufferedReader(new FileReader(file));

		tab = new ArrayList<String>();
		String line;
		while ((line = in.readLine()) != null) {

			tab.add(line);

		}

		in.close();

	}
	
	public void setCameraGroups(String s){
		if(s != null){
			for (int i = 0; i < tab.size(); i++) {
				if(formatSentence(tab.get(i), 1, 0).equals("Groups:")){
					tab.set(i, "Groups: " + s);
					break;
				}
			}
		}
	}
	
	public void writeToFile() throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		for (int i = 0; i < tab.size(); i++) {
			out.write(tab.get(i));
			out.newLine();
		}

		out.close();
		System.out.println("ALT I ORDEN KAMERAT");
	}
	
	public static void main(String[] args) throws IOException{
		CameraFileWriter c = new CameraFileWriter(new File("src//com//androidvizlab//bachelor//calibrationandoptionsfile//calibration_summary_ny.dat"));
		c.setCameraGroups("1_2_3");
		c.writeToFile();
	}
}
