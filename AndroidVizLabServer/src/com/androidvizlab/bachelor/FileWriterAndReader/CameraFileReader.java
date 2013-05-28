package com.androidvizlab.bachelor.FileWriterAndReader;

import com.androidvizlab.bachelor.datamodels.Camera;
import com.androidvizlab.bachelor.datamodels.CameraGroup;
import com.androidvizlab.bachelor.datamodels.UniqueCamGroups;

import static com.androidvizlab.bachelor.FileWriterAndReader.Helpmethods.formatSentence;
import static com.androidvizlab.bachelor.FileWriterAndReader.Helpmethods.removeChars;
import static com.androidvizlab.bachelor.FileWriterAndReader.Helpmethods.findIndices;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Utility class used to read the contents of the calibration summary file.
 * 
 * @author Jakob
 */
public class CameraFileReader {

	/**
	 * 
	 */

	private ArrayList<String> allLinesCalibSummary;
	private ArrayList<String> allLinesCameraCombinationsFile;
	private ArrayList<Camera> cameras;
	private ArrayList<String> sortedTableFromSummary;
	private ArrayList<String> camGrpNames;
	private ArrayList<Double> s0;
	private ArrayList<Double> mean;
	private ArrayList<Double> max;
	private ArrayList<Integer> noOfFramesUsed;
	private ArrayList<CameraGroup> cameraGroups;

	BufferedReader in;

	public CameraFileReader() {
		allLinesCameraCombinationsFile = new ArrayList<String>();
		cameras = new ArrayList<Camera>();
		allLinesCalibSummary = new ArrayList<String>();
		camGrpNames = new ArrayList<String>();
		s0 = new ArrayList<Double>();
		mean = new ArrayList<Double>();
		max = new ArrayList<Double>();
		noOfFramesUsed = new ArrayList<Integer>();
		cameraGroups = new ArrayList<CameraGroup>();
	}

	public void readCalibrationCombFile(File file) {

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
		// cameraGroups.add(addCameraGroup());

	}

	/**
	 * Reads a file line by line and stores all the lines in an ArrayList inside
	 * the CameraFileReader object.
	 * 
	 * @param file
	 *            The calibration summary .dat file
	 */
	public void readCalibrationSummaryFile(File file) {

		// Reads all lines from the file to the allLines array
		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String line;
		try {
			while ((line = in.readLine()) != null) {

				allLinesCalibSummary.add(line);

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

		sortedTableFromSummary = separateSection('=', allLinesCalibSummary, 0);
		addAllCameraComps();
		addAllS0();
		addAllMeans();
		addAllMax();
		addAllNoOfFramesUsed();
		makeCameraObjects();

	}

	private void makeCameraObjects() {
		for (int i = 0; i < camGrpNames.size(); i++) {
			cameraGroups.add(new CameraGroup(camGrpNames.get(i), mean.get(i),
					max.get(i), s0.get(i), noOfFramesUsed.get(i), getCams(i)));
		}
	}

	private ArrayList<Camera> getCams(int i) {
		ArrayList<String> temp = new ArrayList<>();
		temp = separateSection('=', allLinesCalibSummary, i + 1);
		return addCamera(findIndices("Camno", temp), temp);
	}

	/**
	 * Reads all cameragroup names from the first column in the
	 * "SORTED - Camera combinations with S0" table, and adds them in the
	 * camGrpNames ArrayList.
	 */
	private void addAllCameraComps() {
		String line = "";
		for (int i = 0; i < sortedTableFromSummary.size(); i++) {
			line = sortedTableFromSummary.get(i);
			line.replaceAll("_", " ");
			line.replaceAll(":", "");
			camGrpNames.add(formatSentence(line, 1, 0));
		}
	}

	/**
	 * Reads all data from the s0 column (second column) in the
	 * "SORTED - Camera combinations with S0" table, and adds them in the s0
	 * ArrayList
	 * 
	 */
	private void addAllS0() {
		double value = 0.0;
		String t = "";
		for (int i = 0; i < sortedTableFromSummary.size(); i++) {
			t = Helpmethods.formatSentence(sortedTableFromSummary.get(i), 1, 1)
					.trim();
			if (t.length() > 0) {
				value = Double.parseDouble(t.substring(0, t.length() - 1));
				s0.add(new Double(value));
			}
		}
	}

	/**
	 * Reads all data from the mean column (third column) in the
	 * "SORTED - Camera combinations with S0" table, and adds them in the mean
	 * ArrayList
	 */
	private void addAllMeans() {
		double value = 0.0;
		for (int i = 0; i < sortedTableFromSummary.size(); i++) {
			value = Double.parseDouble(Helpmethods.formatSentence(
					sortedTableFromSummary.get(i), 1, 3).replaceAll(",", ""));
			mean.add(new Double(value));
		}
	}

	/**
	 * Reads all data from the max column (fourth column) in the
	 * "SORTED - Camera combinations with S0" table, and adds them in the max
	 * ArrayList
	 */
	private void addAllMax() {
		double value = 0.0;
		String t = "";
		for (int i = 0; i < sortedTableFromSummary.size(); i++) {
			t = Helpmethods.formatSentence(sortedTableFromSummary.get(i), 1, 5)
					.trim();
			if (t.length() > 0) {
				value = Double.parseDouble(t.substring(0, t.length() - 1));
				max.add(new Double(value));
			}
		}
	}

	/**
	 * Reads all data from the No of frames used column (fifth column) in the
	 * "SORTED - Camera combinations with S0" table, and adds them in the
	 * noOfFramesUsed ArrayList
	 */
	private void addAllNoOfFramesUsed() {
		int value = 0;
		for (int i = 0; i < sortedTableFromSummary.size(); i++) {
			value = Integer.parseInt(Helpmethods.formatSentence(
					sortedTableFromSummary.get(i), 1, 10));
			noOfFramesUsed.add(new Integer(value));
		}
	}

	/**
	 * This method is used to separate the different sections in the calibration
	 * summary file, witch is separated by one line of "============" It checks if the first char at the line equals ch, if so it
	 * removes every line before the first and after the second occurrence of ch.
	 * 
	 * @param ch - the character that separates the sections, presumably '='
	 * @param inputArray - ArrayList containing the calibration summary, line by line
	 * @param offset - used to skip sections, i.e with offset = 1, it removes every line before the second and after the third occurrence of ch. etc.
	 * 
	 * @return ArrayList containing the desired section
	 */
	private ArrayList<String> separateSection(char ch,
			ArrayList<String> inputArray, int offset) {
		ArrayList<String> array = new ArrayList<String>();
		for (String s : inputArray) {
			array.add(s);
		}
		int occurances = 0; //counts the occurrences of ch
		int indexStart = -1; //Is set to -1 in case c==ch in the first round of the for-loop below
		int indexEnd = 0;
		char c = 0;
		String line;
		for (int i = 0; i < array.size(); i++) {
			line = array.get(i);
			if (!line.isEmpty()) {
				c = line.charAt(0); //checks the first char at the current line (current index in array)
				if (c == ch) {
					occurances++; 
					if (indexStart > -1 && occurances > offset) { //Conditions true only if indexStart already has been set in the else if below
						indexEnd = i;
						break;
					} else if (occurances > offset) { //Will always be true before the if-statement above
						indexStart = i;
					}
				}
			}
		}
		//Removes elements after indexEnd
		int temp = array.size();
		for (int i = indexEnd; i < temp; i++) {
			array.remove(indexEnd);
		}
		//Removes elements before indexStart
		for (int i = 0; i <= indexStart; i++) {
			array.remove(0);
		}

		return array;
	}


	// Fra calib_cobFiler

	/*
	 * public CameraGroup addCameraGroup() { double s0 = 0.0; double mean = 0.0;
	 * double max = 0.0; for (int i = 0; i <
	 * allLinesCameraCombinationsFile.size(); i++) { String temp =
	 * allLinesCameraCombinationsFile.get(i); if (formatSentence(temp, 1,
	 * 0).equals("S0")) { s0 = Double
	 * .parseDouble(removeChars(formatSentence(temp, 1, 2))); mean = Double
	 * .parseDouble(removeChars(formatSentence(temp, 1, 4))); max = Double
	 * .parseDouble(removeChars(formatSentence(temp, 1, 6))); break; } }
	 * CameraGroup c = new CameraGroup(mean, max, s0, addAllCameras());
	 * allLinesCameraCombinationsFile.clear(); return c; }
	 */

	/*
	 * public ArrayList<Camera> addAllCameras() { ArrayList<Camera> cams = new
	 * ArrayList<Camera>(); ArrayList<Integer> indexes = findIndices("Camno");
	 * for (int i = 0; i < indexes.size(); i++) {
	 * cams.add(addCamera(indexes.get(i))); } // Collections.sort(cameras);
	 * Collections.sort(cams); return cams; }
	 */

	/**
	 * Makes Camera objects from a String ArrayList
	 * 
	 * @param indices - list of indices, so the method knows how many Camera-objects to add and at witch index to start reading 
	 * @param text - containing the raw String from the calibration summary with the data needed to make one or more Camera-objects
	 */
	private ArrayList<Camera> addCamera(ArrayList<Integer> indices,
			ArrayList<String> text) {
		
		/*Camera attributes*/
		int camNo = 0;
		double c = 0.0;
		double xh = 0.0;
		double yh = 0.0;
		double ort = 0.0;
		float f1 = 0;
		float f2 = 0;
		float p1 = 0;
		float p2 = 0;
		
		
		ArrayList<Camera> camList = new ArrayList<>();
		String line = "";
		for (int j = 0; j < indices.size(); j++) {
			for (int i = indices.get(j); i < indices.get(j) + 10; i++) {
				line = text.get(i);
				if (formatSentence(line, 1, 0).equals("Camno")) {
					String temp = formatSentence(line, 1, 1);
					if (temp.length() > 0) {
						camNo = Integer.parseInt(temp.substring(0,
								temp.length() - 1));
					}
				}
				if (formatSentence(line, 1, 0).equals("C:")) {
					c = Double.parseDouble(formatSentence(line, 1, 1));
					xh = Double.parseDouble(formatSentence(line, 1, 6));
				}

				if (formatSentence(line, 1, 0).equals("YH:")) {
					yh = Double.parseDouble(formatSentence(line, 1, 1));
				}

				if (formatSentence(line, 1, 0).equals("ORT:")) {
					ort = Double.parseDouble(formatSentence(line, 1, 1));
				}

				if (formatSentence(line, 1, 0).equals("F1:")) {
					f1 = Float.parseFloat(formatSentence(line, 1, 1));
					f2 = Float.parseFloat(formatSentence(line, 1, 6));
				}

				if (formatSentence(line, 1, 0).equals("F3:")) {
					p1 = Float.parseFloat(formatSentence(line, 1, 6));
				}

				if (formatSentence(line, 1, 0).equals("P2:")) {
					p2 = Float.parseFloat(formatSentence(line, 1, 1));
				}

			}
			camList.add(new Camera(camNo, c, xh, yh, ort, f1, f2, p1, p2));
		}
		return camList;
	}

	public ArrayList<Camera> getCameras() {
		return cameras;
	}

	public ArrayList<CameraGroup> getCameraGroups() {
		return cameraGroups;
	}

	/*
	 * public ArrayList<CameraGroup> getCameraCombInfo() {
	 * ArrayList<CameraGroup> list = new ArrayList<CameraGroup>(); for (int i =
	 * 0; i < camGrpNames.size(); i++) { list.add(new CameraGroup(mean.get(i),
	 * max.get(i), s0.get(i), noOfFramesUsed.get(i),
	 * addCameraToCameraGroup(camGrpNames .get(i)))); } return list; }
	 */

	private ArrayList<Camera> addCameraToCameraGroup(String s) {
		ArrayList<Camera> list = new ArrayList<Camera>();
		String[] split = s.split("_");
		int[] numbers = new int[split.length];
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = Integer.parseInt(split[i]);
		}

		for (int i = 0; i < cameras.size(); i++) {
			for (int j = 0; j < numbers.length; j++) {
				if (numbers[j] == cameras.get(i).getCamNo()) {
					list.add(cameras.get(i));
				}
			}
		}
		return list;
	}

	private boolean isCameraAdded(int camNo) {
		boolean b = false;
		for (int i = 0; i < cameras.size(); i++) {
			if (camNo == cameras.get(i).getCamNo()) {
				b = true;
			}
		}
		return b;
	}
	
	public ArrayList<CameraGroup> getRecommendedCameraGroups() {
		ArrayList<CameraGroup> output = new ArrayList<CameraGroup>();
		ArrayList<CameraGroup> input = getCameraGroups();
		Collections.sort(input);
		output.add(input.get(0));
		boolean b = false;
		for (int i = 1; i < input.size(); i++) {
			for (int j = 0; j < output.size(); j++) {
				b = output.get(j).hasSameCamera(input.get(i));
				if (b) {
					break;
				}
			}
			if (!b) {
				output.add(input.get(i));
			}
		}
		return output;
	}

	public ArrayList<CameraGroup> getRecommendedCameraGroups(int start) {
		ArrayList<CameraGroup> input = getCameraGroups();
		ArrayList<CameraGroup> output = new ArrayList<CameraGroup>();
		Collections.sort(input);
		output.add(input.get(start));
		boolean b = false;
		for (int i = 0; i < input.size(); i++) {
			for (int j = 0; j < output.size(); j++) {
				b = output.get(j).hasSameCamera(input.get(i));
				if (b) {
					break;
				}
			}
			if (!b) {
				output.add(input.get(i));
			}
		}
		Collections.sort(output);
		return output;
	}

	public ArrayList<UniqueCamGroups> getAllUniqueCamGrpSorted() {
		ArrayList<UniqueCamGroups> list = new ArrayList<UniqueCamGroups>();
		boolean isDuplicate = false;
		for (int i = 0; i < getCameraGroups().size(); i++) {
			isDuplicate = false;
			UniqueCamGroups obj = new UniqueCamGroups(
					getRecommendedCameraGroups(i));
			for (int j = 0; j < list.size(); j++) {
				if (obj.compareTo(list.get(j)) == 0) {
					isDuplicate = true;
				}
			}
			if (!isDuplicate) {
				list.add(obj);
			}
		}

		Collections.sort(list);
		return list;
	}

	/*public static void main(String[] args) {
		CameraFileReader cfr = new CameraFileReader();
		cfr.readCalibrationSummaryFile(new File(
				"src//com//androidvizlab//bachelor//calibrationandoptionsfile//calibration_summary_ny.dat"));
		ArrayList<CameraGroup> ccc = cfr.getCameraGroups();
		Collections.sort(ccc);
		System.out.println("tabsize: " + ccc.size());
		for (CameraGroup c : ccc) {
			System.out.println(c.toString());
		}
		System.out.println();
		System.out.println();
		System.out.println("Anbefalte kameragrupper:");
		for (CameraGroup c : cfr.getRecommendedCameraGroups()) {
			System.out.println(c.getGroupName() + " " + c.getS0());
		}
		System.out.println();
		System.out.println();
		System.out.println("Snitt:");

		ArrayList<UniqueCamGroups> ucg = cfr.getAllUniqueCamGrpSorted();
		for (UniqueCamGroups cc : ucg) {
			System.out.println(cc.toString());
			System.out.println();
		}

	}*/

}
