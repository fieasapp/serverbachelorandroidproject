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
		//cameraGroups.add(addCameraGroup());

	}

	public void realCalibrationSummaryFile(File file) {

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

		sortedTableFromSummary = formatLineArray('=', allLinesCalibSummary, 0);
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
		temp = formatLineArray('=', allLinesCalibSummary, i+1);
		return addCamera(findIndices("Camno", temp), temp);
	}

	/**
	 * Adds the first word of every line in the cameraGroupInfoLines array to
	 * the cameraCombinations array.
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
	 * Adds the second word of every line in the cameraGroupInfoLines array to
	 * the s0 array.
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
	 * Adds the 4th word of every line in the cameraGroupInfoLines array to the
	 * mean array.
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
	 * Adds the 6th word of every line in the cameraGroupInfoLines array to the
	 * max array.
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
	 * Adds the 11th word of every line in the cameraGroupInfoLines array to the
	 * noOfFramesUsed array
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
	 * This method removes all lines before the first and after the second
	 * occurrence of the specified character in the inputArray and returns it as
	 * a new array.
	 * 
	 * 
	 */
	private ArrayList<String> formatLineArray(char ch,
			ArrayList<String> inputArray, int offset) {
		ArrayList<String> array = new ArrayList<String>();
		for (String s : inputArray) {
			array.add(s);
		}
		int occurances = 0;
		int indexStart = -1;
		int indexEnd = 0;
		char c = 0;
		String line;
		for (int i = 0; i < array.size(); i++) {
			line = array.get(i);
			if (!line.isEmpty()){
				c = line.charAt(0);
				if (c == ch) {
					occurances++;
					if (indexStart > -1 && occurances > offset) {
						indexEnd = i;
						break;
					} else if (occurances > offset) {
						indexStart = i;
					}
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
		for (String s : allLinesCalibSummary) {
			System.out.println(s);
		}
	}

	// Fra calib_cobFiler

/*	public CameraGroup addCameraGroup() {
		double s0 = 0.0;
		double mean = 0.0;
		double max = 0.0;
		for (int i = 0; i < allLinesCameraCombinationsFile.size(); i++) {
			String temp = allLinesCameraCombinationsFile.get(i);
			if (formatSentence(temp, 1, 0).equals("S0")) {
				s0 = Double
						.parseDouble(removeChars(formatSentence(temp, 1, 2)));
				mean = Double
						.parseDouble(removeChars(formatSentence(temp, 1, 4)));
				max = Double
						.parseDouble(removeChars(formatSentence(temp, 1, 6)));
				break;
			}
		}
		CameraGroup c = new CameraGroup(mean, max, s0, addAllCameras());
		allLinesCameraCombinationsFile.clear();
		return c;
	}*/

/*	public ArrayList<Camera> addAllCameras() {
		ArrayList<Camera> cams = new ArrayList<Camera>();
		ArrayList<Integer> indexes = findIndices("Camno");
		for (int i = 0; i < indexes.size(); i++) {
			cams.add(addCamera(indexes.get(i)));
		}
		// Collections.sort(cameras);
		Collections.sort(cams);
		return cams;
	}*/

	/**
	 * This method iterates through the allLinesCameraCombinationsFile list,
	 * picks up the parameters needed to make a cameraInfo object and adds the
	 * object to the cameras list. Only adds one object to the list based on the
	 * start parameter.
	 * 
	 * @param start
	 *            The index of the list it will start the iteration.
	 */
	private ArrayList<Camera> addCamera(ArrayList<Integer> indices, ArrayList<String> text) {
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

/*	public ArrayList<CameraGroup> getCameraCombInfo() {
		ArrayList<CameraGroup> list = new ArrayList<CameraGroup>();
		for (int i = 0; i < camGrpNames.size(); i++) {
			list.add(new CameraGroup(mean.get(i), max.get(i), s0.get(i),
					noOfFramesUsed.get(i), addCameraToCameraGroup(camGrpNames
							.get(i))));
		}
		return list;
	}*/

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

	public static void main(String[] args) {
		CameraFileReader cfr = new CameraFileReader();
		cfr.realCalibrationSummaryFile(new File("src//com//androidvizlab//bachelor//calibrationandoptionsfile//calibration_summary_ny.dat"));
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

	}

}
