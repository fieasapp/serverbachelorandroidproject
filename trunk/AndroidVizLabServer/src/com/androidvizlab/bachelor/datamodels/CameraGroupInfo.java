package com.androidvizlab.bachelor.datamodels;
import com.androidvizlab.bachelor.FileWriterAndReader.folder.Helpmethods;
import com.androidvizlab.bachelor.datamodels.CameraInfo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class CameraGroupInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<String> allLinesCameraGroupFile;
	private ArrayList<String> allLinesCameraCombinationsFile;
	private ArrayList<CameraInfo> cameras;
	ArrayList<String> cameraGroupInfoLines;
	ArrayList<String> cameraCombinations;
	ArrayList<Double> mean;
	ArrayList<Double> max;
	ArrayList<Integer> noOfFramesUsed;


	BufferedReader in;
	
	public void readCalibrationCombFile(File file){
		allLinesCameraCombinationsFile = new ArrayList<String>();
		cameras = new ArrayList<CameraInfo>();
		
		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String line;
		try {
			while ((line = in.readLine()) != null) {

				allLinesCameraCombinationsFile.add(line);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addAllCameras();
		
	}
	
	public void realCalibrationSummaryFile(File file) {

		allLinesCameraGroupFile = new ArrayList<String>();
		cameraCombinations = new ArrayList<String>();
		mean = new ArrayList<Double>();
		max = new ArrayList<Double>();
		noOfFramesUsed = new ArrayList<Integer>();
		
		
		//Reads all lines from the file to the allLines array
		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String line;
		try {
			while ((line = in.readLine()) != null) {

				allLinesCameraGroupFile.add(line);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cameraGroupInfoLines = formatLineArray('=', allLinesCameraGroupFile, 0);  //takes all relevant lines from the allLines array and puts them in the cameraGroupInfoLines array

		//Adds all the separate info from the cameraGroupInfoLines array into their own respective array
		addAllCameraComps();
		addAllMeans();
		addAllMax();
		addAllNoOfFramesUsed();
	}
	/**
	 * Adds the first word of every line in the cameraGroupInfoLines array to the cameraCombinations array.
	 */
	public void addAllCameraComps() {
		for (int i = 0; i < cameraGroupInfoLines.size(); i++) {
			cameraCombinations.add(Helpmethods.formatSentence(cameraGroupInfoLines.get(i),
					1, 0).replaceAll(":", ""));
		}
	}
	/**
	 * Adds the 4th word of every line in the cameraGroupInfoLines array to the mean array.
	 */
	public void addAllMeans() {
		double value = 0.0;
		for (int i = 0; i < cameraGroupInfoLines.size(); i++) {
			value = Double.parseDouble(Helpmethods.formatSentence(
					cameraGroupInfoLines.get(i), 1, 3).replaceAll(",", ""));
			mean.add(new Double(value));
		}
	}
	/**
	 * Adds the 6th word of every line in the cameraGroupInfoLines array to the max array.
	 */
	public void addAllMax() {
		double value = 0.0;
		String t = "";
		for (int i = 0; i < cameraGroupInfoLines.size(); i++) {
			t = Helpmethods.formatSentence(cameraGroupInfoLines.get(i), 1, 5).trim();
			if (t.length() > 0) {
				value = Double.parseDouble(t.substring(0, t.length() - 1));
				max.add(new Double(value));
			}
		}
	}
	/**
	 * Adds the 11th word of every line in the cameraGroupInfoLines array to the noOfFramesUsed array
	 */
	public void addAllNoOfFramesUsed() {
		int value = 0;
		for (int i = 0; i < cameraGroupInfoLines.size(); i++) {
			value = Integer.parseInt(Helpmethods.formatSentence(
					cameraGroupInfoLines.get(i), 1, 10));
			noOfFramesUsed.add(new Integer(value));
		}
	}
	/**This method removes all lines before the first and after the second occurrence of the specified character in the inputArray and returns it as a new array. (not deep copy)
	 * 
	 * 
	 */
	private ArrayList<String> formatLineArray(char ch, ArrayList<String> inputArray, int offset) {
		ArrayList<String> array = new ArrayList<String>();
		for(String s : inputArray){
			array.add(s);
		}
		int occurances = 0;
		int indexStart = 0;
		int indexEnd = 0;
		char c = 'd';
		String line;
		for (int i = 0; i < array.size(); i++) {
			line = array.get(i);
			if (!line.equals(""))
				c = line.charAt(0);
			if (c == ch) {
				occurances++;
				if (indexStart > 0 && occurances>offset) {
					indexEnd = i;
					break;
				} else if(occurances>offset){
					indexStart = i;
				}
			}
		}
		int temp = array.size();
		for (int i = indexEnd; i < temp; i++) {
			array.remove(indexEnd);
		}
		for (int i = 0; i <= indexStart; i++) {
			array.remove(0);
		}
		
		return array;
	}

	public void printFile() {
		for (String s : allLinesCameraGroupFile) {
			System.out.println(s);
		}
	}
	
	public void addAllCameras(){
		ArrayList<Integer> indexes = findIndices("Camno");
		for(int i = 0; i<indexes.size(); i++){
			addCamera(indexes.get(i));
		}
	}
	
	private void addCamera(int start){
		
		int camNo = 0;
		double c = 0.0;
		double xh = 0.0;
		double yh = 0.0;
		double ort = 0.0;
		float f1 = 0;
		float f2 = 0;
		float p1 = 0;
		float p2 = 0;
		String line = "";

		for(int i = start; i<start + 10; i++){
			line = allLinesCameraCombinationsFile.get(i);
			if(Helpmethods.formatSentence(line, 1, 0).equals("Camno")){
				String temp = Helpmethods.formatSentence(line, 1, 1);
				if (temp.length() > 0) {
					camNo = Integer.parseInt(temp.substring(0, temp.length() - 1));
				}
			}
			if(Helpmethods.formatSentence(line, 1, 0).equals("C:")){
				c = Double.parseDouble(Helpmethods.formatSentence(line, 1, 1));
				xh = Double.parseDouble(Helpmethods.formatSentence(line, 1, 6));
			}
			
			if(Helpmethods.formatSentence(line, 1, 0).equals("YH:")){
				yh = Double.parseDouble(Helpmethods.formatSentence(line, 1, 1));
			}
			
			if(Helpmethods.formatSentence(line, 1, 0).equals("ORT:")){
				ort = Double.parseDouble(Helpmethods.formatSentence(line, 1, 1));
			}
			
			if(Helpmethods.formatSentence(line, 1, 0).equals("F1:")){
				f1 = Float.parseFloat(Helpmethods.formatSentence(line, 1, 1));
				f2 = Float.parseFloat(Helpmethods.formatSentence(line, 1, 6));
			}
			
			if(Helpmethods.formatSentence(line, 1, 0).equals("F3:")){
				p1 = Float.parseFloat(Helpmethods.formatSentence(line, 1, 6));
			}
			
			if(Helpmethods.formatSentence(line, 1, 0).equals("P2:")){
				p2 = Float.parseFloat(Helpmethods.formatSentence(line, 1, 1));
			}
			
		}
		
		cameras.add(new CameraInfo(camNo, c, xh, yh, ort, f1, f2, p1, p2));
	}
	
	private ArrayList<Integer> findIndices(String word){
		ArrayList<Integer> al = new ArrayList<Integer>();
		for(int i = 0; i<allLinesCameraCombinationsFile.size(); i++){
			if(Helpmethods.formatSentence(allLinesCameraCombinationsFile.get(i), 1, 0).equals(word)){
				al.add(i);
			}
		}
		return al;
	}
	
	public ArrayList<CameraInfo> getCameras(){
		return cameras;
	}

}


