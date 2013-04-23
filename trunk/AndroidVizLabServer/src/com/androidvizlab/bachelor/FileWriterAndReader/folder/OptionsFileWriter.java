package com.androidvizlab.bachelor.FileWriterAndReader.folder;

import java.io.*;
import java.util.ArrayList;

import com.androidvizlab.bachelor.datamodels.VizlabInputData;
import com.androidvizlab.bachelor.datamodels.VizlabInputData;

/**
 * This class modifies the optionsfile based on the information taken from a
 * VizlabInputData object.
 * 
 * 
 * @author Jakob
 * */

public class OptionsFileWriter {

	File file;
	VizlabInputData inputData;
	ArrayList<String> tab;

	public OptionsFileWriter(File file, VizlabInputData inputData) throws IOException {
		this.inputData = inputData;
		this.file = file;
		BufferedReader in = new BufferedReader(new FileReader(file));

		tab = new ArrayList<String>();
		String line;
		while ((line = in.readLine()) != null) {

			tab.add(line);

		}

		in.close();

		setAllData();
		writeToFile();

	}

	public void setAllData() throws IOException {
		setCalibDir(inputData.getCalibrationFileName());
		setCalibFilePath(inputData.getCalibrationFilePath());
		setTriggingInterval(inputData.getTriggingInterval());
		setRunType(inputData.getRunType());
		setNrOfTripletGroups(inputData.getNumTripletCamGrp());
		setNoOfTimePoints(inputData.getNumTimePts());
		setNumOfMarkers(inputData.getNumMarkers());
		setOutputOpt(inputData.getProgramOutputSocketConnection());
		setMinNrOfPixels(inputData.getMixpix());
		setMaxNrOfPixels(inputData.getMaxpix());
		setMinDistanceBetweenPx(inputData.getMinsep());
		setMaxError(inputData.getMaxerr());
		setTripletsFromFile(inputData.getImgFileInputTriplets());
		setTripletsFromFileTR(inputData.getImgFileInputTripletsTurnedRight());
		setOrginalImages(inputData.getImgFileOutputOriginalImg());
		setGeneratedTriplets(inputData.getImgFileOutputGeneratedTriplets());
		setGeneratedTripletsTR(inputData.getImgFileOutputGeneratedTripletsTurnedRight());
		setHelpFileInputToDuplicatePoints(inputData
				.getHelpFileOutputImageDetectPoints());
		setHelpFileInputToPointCoord(inputData.getHelpFileOutputDuplicatePoints());
		setHelpFileInputToDobbelMatch(inputData.getHelpFileOutputMatch3());
		setHelpFileInputToMakeTimeSeries(inputData.getHelpFileOutputdobbelMatch());
		setHelpFileOutputFromConnectPoints(inputData
				.getHelpFileOutputConnectPoints());
		setHelpFileOutputFromTimeseries(inputData.getHelpFileOutputTimeseries());
		setApproxLimit(inputData.getApproxFrameMarkerLimit());
	}

	// set "path for calibration files
	public void setCalibDir(String dir) throws IOException {
		if (dir != null) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 4, 0).equals(
						"!path for calibration files")) {
					tab.set(i + 1, dir);
				}
			}
		}
	}

	// set "run type"
	public void setRunType(String mode) throws IOException {
		if (mode != null) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 2, 1).equals(
						"Run type.")) {
					tab.set(i + 1, mode);
				}
			}
		}

	}

	// set "path and name of calibration file"
	public void setCalibFilePath(String path) throws IOException {
		if (path != null) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 6, 1).equals(
						"path and name of calibration file,")) {
					tab.set(i + 1, path);
				}
			}
		}
	}

	// set "trigging interval"
	public void setTriggingInterval(int value) {
		if (value >= 0) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 2, 1).equals(
						"trigging interval,")) {
					tab.set(i + 1, "" + value);
				}
			}
		}
	}

	// set "no. of triplet groups"
	public void setNrOfTripletGroups(int num) throws IOException {
		if (num >= 0) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 4, 1).equals(
						"no. of triplet groups")) {
					tab.set(i + 1, num + "");
				}
			}
		}
	}

	// set "no of time pts, this run"
	public void setNoOfTimePoints(int fps) throws IOException {
		if (fps >= 0) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 4, 1).equals(
						"no of time pts,")) {
					tab.set(i + 1, fps + "");
				}
			}
		}
	}

	// set "no of markers"
	public void setNumOfMarkers(int num) throws IOException {
		if (num >= 0) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 3, 1).equals(
						"no of markers")) {
					tab.set(i + 1, num + "");
				}
			}
		}
	}

	/**
	 * Searches through the ArrayList containing the lines of the optionsfile,
	 * and replaces the output option line with the input parameter.
	 * 
	 * @param opt
	 * @throws IOException
	 */
	// set "output from program: Socket connection"
	public void setOutputOpt(String opt) throws IOException {
		if (opt != null) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 5, 1).equals(
						"output from program: Socket Connection")) {
					tab.set(i + 1, opt);
				}
			}
		}
	}

	public void setMinNrOfPixels(int px) {
		if (px >= 0) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 1, 18).equals(
						"InputDef::minpix")) {
					tab.set(i + 1, px + "");
				}
			}
		}
	}

	public void setMaxNrOfPixels(int px) {
		if (px >= 0) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 1, 18).equals(
						"InputDef::maxpix")) {
					tab.set(i + 1, px + "");
				}
			}
		}
	}

	public void setMinDistanceBetweenPx(int px) {
		if (px >= 0) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 1, 6).equals(
						"InputDef::minsep")) {
					tab.set(i + 1, px + "");
				}
			}
		}
	}

	public void setMaxError(int err) {
		if (err >= 0) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 1, 6).equals(
						"InputDef::maxerr")) {
					tab.set(i + 1, err + "");
				}
			}
		}
	}

	public void setTripletsFromFile(String f) {
		if (f != null) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 3, 3).equals(
						"triplets from file")) {
					tab.set(i + 1, f);
				}
			}
		}
	}

	public void setTripletsFromFileTR(String f) {
		if (f != null) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 5, 5).equals(
						"triplets from file, turned right")) {
					tab.set(i + 1, f);
				}
			}
		}
	}

	public void setOrginalImages(String f) {
		if (f != null) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 2, 4).equals(
						"original images")) {
					tab.set(i + 1, f);
				}
			}
		}
	}

	public void setGeneratedTriplets(String f) {
		if (f != null) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 2, 4).equals(
						"generated triplets")) {
					tab.set(i + 1, f);
				}
			}
		}
	}

	public void setGeneratedTripletsTR(String f) {
		if (f != null) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 4, 4).equals(
						"generated triplets, turned right")) {
					tab.set(i + 1, f);
				}
			}
		}
	}

	// set "output from ImageDetectPoints(), input to DuplicatePoints()
	public void setHelpFileInputToDuplicatePoints(String f) {
		if (f != null) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 3, 7).equals(
						"input to DuplicatePoints()")) {
					tab.set(i + 1, f);
				}
			}
		}
	}

	// set output from DuplicatePoints(), input to PointCoordinates()
	public void setHelpFileInputToPointCoord(String f) {
		if (f != null) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 3, 7).equals(
						"input to PointCoordinates()")) {
					tab.set(i + 1, f);
				}
			}
		}
	}

	public void setHelpFileInputToDobbelMatch(String f) {
		if (f != null) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 3, 7).equals(
						"input to dobbelmatch()")) {
					tab.set(i + 1, f);
				}
			}
		}
	}

	public void setHelpFileInputToMakeTimeSeries(String f) {
		if (f != null) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 5, 7).equals(
						"input to MakeTimeSeries() and WriteTimeSeries()")) {
					tab.set(i + 1, f);
				}
			}
		}
	}

	public void setHelpFileOutputFromConnectPoints(String f) {
		if (f != null) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 3, 4).equals(
						"output from Connectpoints()")) {
					tab.set(i + 1, f);
				}
			}
		}
	}

	public void setHelpFileOutputFromTimeseries(String f) {
		if (f != null) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 3, 6).equals(
						"output from Timeseries()")) {
					tab.set(i + 1, f);
				}
			}
		}
	}

	public void setApproxLimit(double d) {
		if (d >= 0) {
			for (int i = 0; i < tab.size(); i++) {
				if (Helpmethods.formatSentence(tab.get(i), 5, 1).equals(
						"Approximation limit for frame markers")) {
					tab.set(i + 1, d + "");
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

	public static void main(String[] args) throws IOException {
		/*VizlabInputData vid = new VizlabInputData("Calibdir sucsess!",
				"runtype sucsess!", "calib file path sucsess!", 1, 2, 3, 4,
				"SOCKETCON sucsess!", 5, 6, 7, 8, "tripletsfromfile",
				"tripletsfromfileTR", "ORGINALIMAGES", "GENERATEdtriplets",
				"generatedTripsTR", "output_from_ImageDetectPointssucsess!",
				"output_from_DuplicatePointssucsess!",
				"output_from_match3sucsess!",
				"output_from_dobbelmatchsucsess!",
				"output_from_connectpointssucsess!",
				"output_from_timeseriessucsess!", 666.666);
		OptionsFileWriter opt = new OptionsFileWriter(new File("src//options.txt"), vid);*/
	}

}
