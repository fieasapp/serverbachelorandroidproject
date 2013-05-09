package com.androidvizlab.bachelor.FileWriterAndReader;
import com.androidvizlab.bachelor.datamodels.Camera;
import com.androidvizlab.bachelor.datamodels.CameraGroup;
import static com.androidvizlab.bachelor.FileWriterAndReader.Helpmethods.formatSentence;
import static com.androidvizlab.bachelor.FileWriterAndReader.Helpmethods.removeChars;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * @author Jakob
 */
public class CameraFileReader{

	/**
	 * 
	 */
	
	private ArrayList<String> allLinesCameraGroupFile;
	private ArrayList<String> allLinesCameraCombinationsFile;
	private ArrayList<Camera> cameras;
	private ArrayList<String> cameraGroupInfoLines;
	private ArrayList<String> cameraCombinations;
	private ArrayList<Double> s0;
	private ArrayList<Double> mean;
	private ArrayList<Double> max;
	private ArrayList<Integer> noOfFramesUsed;
	private ArrayList<CameraGroup> cameraGroups;


	BufferedReader in;
	
	public CameraFileReader(){
		allLinesCameraCombinationsFile = new ArrayList<String>();
		cameras = new ArrayList<Camera>();
		allLinesCameraGroupFile = new ArrayList<String>();
		cameraCombinations = new ArrayList<String>();
		s0 = new ArrayList<Double>();
		mean = new ArrayList<Double>();
		max = new ArrayList<Double>();
		noOfFramesUsed = new ArrayList<Integer>();
		cameraGroups = new ArrayList<CameraGroup>();
	}
	
	public void readCalibrationCombFile(File file){

		
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
		cameraGroups.add(addCameraGroup());
		
	}
	
	public void realCalibrationSummaryFile(File file) {


		
		
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
		addAllS0();
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
	 * Adds the second word of every line in the cameraGroupInfoLines array to the s0 array.
	 */
	public void addAllS0(){
		double value = 0.0;
		String t = "";
		for (int i = 0; i < cameraGroupInfoLines.size(); i++) {
			t = Helpmethods.formatSentence(cameraGroupInfoLines.get(i), 1, 1).trim();
			if (t.length() > 0) {
				value = Double.parseDouble(t.substring(0, t.length() - 1));
				s0.add(new Double(value));
			}
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
	//Fra calib_cobFiler
	public CameraGroup addCameraGroup(){
		double s0 = 0.0;
		double mean = 0.0;
		double max = 0.0;
		for (int i = 0; i < allLinesCameraCombinationsFile.size(); i++) {
			String temp = allLinesCameraCombinationsFile.get(i);
			if(formatSentence(temp, 1, 0).equals("S0")){
				s0 = Double.parseDouble(removeChars(formatSentence(temp, 1, 2)));
				mean = Double.parseDouble(removeChars(formatSentence(temp, 1, 4)));
				max = Double.parseDouble(removeChars(formatSentence(temp, 1, 6)));		
				break;
			}
		}
		CameraGroup c = new CameraGroup(mean, max, s0, addAllCameras());
		allLinesCameraCombinationsFile.clear();
		return c;
	}
	
	public ArrayList<Camera> addAllCameras(){
		ArrayList<Camera> cams = new ArrayList<Camera>();
		ArrayList<Integer> indexes = findIndices("Camno");
		for(int i = 0; i<indexes.size(); i++){
			cams.add(addCamera(indexes.get(i)));
		}
		//Collections.sort(cameras);
		Collections.sort(cams);
		return cams;
	}
	/**
	 * This method iterates through the allLinesCameraCombinationsFile list, picks up the parameters needed to make a cameraInfo object
	 * and adds the object to the cameras list. Only adds one object to the list based on the start parameter.
	 * @param start
	 * The index of the list it will start the iteration.
	 */
	private Camera addCamera(int start){
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
/*		if(!isCameraAdded(camNo)){
			cameras.add(new Camera(camNo, c, xh, yh, ort, f1, f2, p1, p2));
			
		}*/
		return new Camera(camNo, c, xh, yh, ort, f1, f2, p1, p2);
	}
	/**
	 * Iterates through the allLinesCameraCombinationsFile list and removes the everything but the first word at every index.
	 * @param word
	 * A string that gets compared to the string of every index of the list.
	 * @return
	 * An ArrayList containing all the indices of the input string.
	 */
	private ArrayList<Integer> findIndices(String word){
		ArrayList<Integer> al = new ArrayList<Integer>();
		for(int i = 0; i<allLinesCameraCombinationsFile.size(); i++){
			if(Helpmethods.formatSentence(allLinesCameraCombinationsFile.get(i), 1, 0).equals(word)){
				al.add(i);
			}
		}
		return al;
	}
	
	public ArrayList<Camera> getCameras(){
		return cameras;
	}
	
	public ArrayList<CameraGroup> getCameraGroups(){
		return cameraGroups;
	}
	
	public ArrayList<CameraGroup> getCameraCombInfo(){
		ArrayList<CameraGroup> list = new ArrayList<CameraGroup>();
		for(int i = 0; i<cameraCombinations.size(); i++){
			list.add(new CameraGroup(mean.get(i), max.get(i), s0.get(i), addCameraToCameraGroup(cameraCombinations.get(i))));
		}
		return list;
	}
	
	private ArrayList<Camera> addCameraToCameraGroup(String s){
		ArrayList<Camera> list = new ArrayList<Camera>();
		String[] split = s.split("_");
		int[] numbers = new int[split.length];
		for(int i = 0; i<numbers.length; i++){
			numbers[i] = Integer.parseInt(split[i]);
		}
		
		for(int i = 0; i<cameras.size(); i++){
			for(int j = 0; j<numbers.length; j++){
				if(numbers[j] == cameras.get(i).getCamNo()){
					list.add(cameras.get(i));
				}
			}
		}
		return list;
	}
	
	private boolean isCameraAdded(int camNo){
		boolean b = false;
		for(int i = 0; i<cameras.size(); i++){
			if(camNo == cameras.get(i).getCamNo()){
				b = true;
			}
		}
		return b;
	}
	
	
	public ArrayList<CameraGroup> getRecommendedCameraGroups(){
		ArrayList<CameraGroup> output = new ArrayList<CameraGroup>();
		ArrayList<CameraGroup> input = getCameraGroups();
		Collections.sort(input);
		output.add(input.get(0));
		boolean b = false;
		for(int i = 1; i<input.size(); i++){
			for(int j = 0; j<output.size(); j++){	
				b = output.get(j).hasSameCamera(input.get(i));
				if(b){
					break;
				}
			}
			if(!b){
				output.add(input.get(i));
			}
		}
		return output;
	}
	
	
	public static void main(String[] args){
		CameraFileReader cfr = new CameraFileReader();
		cfr.readCalibrationCombFile(new File("src//com//androidvizlab//bachelor//calibrationandoptionsfile//calibrationcob.dat"));	
		cfr.readCalibrationCombFile(new File("src//com//androidvizlab//bachelor//calibrationandoptionsfile//calibrationcob2.dat"));	
		cfr.readCalibrationCombFile(new File("src//com//androidvizlab//bachelor//calibrationandoptionsfile//calibrationcob3.dat"));	
		cfr.readCalibrationCombFile(new File("src//com//androidvizlab//bachelor//calibrationandoptionsfile//calibrationcob4.dat"));	
		cfr.readCalibrationCombFile(new File("src//com//androidvizlab//bachelor//calibrationandoptionsfile//calibrationcob5.dat"));	
		cfr.readCalibrationCombFile(new File("src//com//androidvizlab//bachelor//calibrationandoptionsfile//calibrationcob6.dat"));	
		ArrayList<CameraGroup> ccc = cfr.getCameraGroups();
		System.out.println("tabsize: " + ccc.size());
		for(CameraGroup c : ccc){
			System.out.println(c.getGroupName() + " " + c.getS0());
		}
		System.out.println();
		System.out.println();
		System.out.println("Anbefalte kameragrupper:");
		for(CameraGroup c : cfr.getRecommendedCameraGroups()){
			System.out.println(c.getGroupName() + " " + c.getS0());
		}
	}

}


