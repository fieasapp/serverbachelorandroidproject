package com.androidvizlab.bachelor.FileWriterAndReader;

import static com.androidvizlab.bachelor.FileWriterAndReader.Helpmethods.formatSentence;
import static com.androidvizlab.bachelor.FileWriterAndReader.Helpmethods.removeChars;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.androidvizlab.bachelor.datamodels.VizlabInputData;

/**
 * Utility class to read the contents of the options-file
 * @author Jakob
 */
public class OptionsFileReader {

	private ArrayList<String> optionsFile;
	private BufferedReader in;
	private VizlabInputData data;

	public OptionsFileReader(File file) {
		optionsFile = new ArrayList<String>();
		data = new VizlabInputData();
		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String line;
		try {
			while ((line = in.readLine()) != null) {

				optionsFile.add(line);

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
		makeData();
	}

	private void makeData() {
		data.setCalibrationFilePath(readCalibDir());
		data.setRunType(readRunType());
		data.setCalibrationFileName(readPathAndNameForCalibFile());
		data.setTriggingInterval(readTriggingInterval());
		data.setNumTripletCamGrp(readNoOfTripletGroups());
		data.setNumTimePts(readNoOfTimePoints());
		data.setNumMarkers(readNoOfMarkers());
		data.setOffsetPix(readOffsetPixel());
		data.setMinpix(readMinNrOfPixels());
		data.setMaxpix(readMaxNrOfPixels());
		data.setMinsep(readMinDistBetweenPixels());
		data.setMaxerr(readMaxError());
		data.setProgramOutputSocketConnection(readOutPutOpt());
		data.setImgFileInputTriplets(readTripletsFromFile());
		data.setImgFileInputTripletsTurnedRight(readTripletsFromFileTR());
		data.setImgFileOutputOriginalImg(readOriginalImages());
		data.setImgFileOutputGeneratedTriplets(readGeneratedTriplets());
		data.setImgFileOutputGeneratedTripletsTurnedRight(readGeneraterTripletsTR());
		data.setHelpFileOutputImageDetectPoints(readHelpFileInputToDuplicatePoints());
		data.setHelpFileOutputDuplicatePoints(readHelpFileInputToPointCoord());
		data.setHelpFileOutputMatch3(readHelpFileInputToDobbelMatch());
		data.setHelpFileOutputdobbelMatch(readHelpFileInputToMakeTimeSeries());
		data.setHelpFileOutputConnectPoints(readHelpFileOutoutFromConnectPoints());
		data.setHelpFileOutputTimeseries(readHelpFileOutPutfromTimeseries());
	}

	public VizlabInputData getData() {
		return data;
	}

	// read "!path for calibration files"
	private String readCalibDir() {
		String s = "Not found";
		for (int i = 0; i < optionsFile.size(); i++) {
			if (optionsFile.get(i).equals("!path for calibration files")) {
				s = optionsFile.get(i + 1);
				break;
			}
		}
		return s;
	}

	// read "run type"
	private String readRunType() {
		String s = "Not found";
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 2, 1).equals("Run type.")) {
				s = optionsFile.get(i + 1);
				break;
			}
		}
		return s;
	}

	// read "path and name of calibration file"
	private String readPathAndNameForCalibFile() {
		String s = "Not found";
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 6, 1).equals(
					"path and name of calibration file,")) {
				s = optionsFile.get(i + 1);
				break;
			}
		}
		return s;
	}

	// read "trigging interval"
	private int readTriggingInterval() {
		int s = -1;
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 2, 1).equals(
					"trigging interval,")) {
				s = Integer.parseInt(optionsFile.get(i + 1));
				break;
			}
		}
		return s;
	}

	// read "no. of triplet groups"
	private int readNoOfTripletGroups() {
		int s = -1;
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 4, 1).equals(
					"no. of triplet groups")) {
				s = Integer.parseInt(optionsFile.get(i + 1));
				break;
			}
		}
		return s;
	}

	// read "no of time pts, this run"
	private int readNoOfTimePoints() {
		int s = -1;
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 4, 1).equals(
					"no of time pts,")) {
				s = Integer.parseInt(optionsFile.get(i + 1));
				break;
			}
		}
		return s;
	}

	// read "no of markers"
	private int readNoOfMarkers() {
		int s = -1;
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 3, 1)
					.equals("no of markers")) {
				s = Integer.parseInt(optionsFile.get(i + 1));
				break;
			}
		}
		return s;
	}

	// read "output from program: Socket connection"
	private String readOutPutOpt() {
		String s = "Not found";
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 5, 1).equals(
					"output from program: Socket Connection")) {
				s = optionsFile.get(i + 1);
				break;
			}
		}
		return s;
	}

	// readOffsetPixel
	private int readOffsetPixel() {
		int s = -1;
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 1, 15).equals(
					"InputDef::imlimit")) {
				s = Integer.parseInt(optionsFile.get(i + 1));
				break;
			}
		}
		return s;
	}

	// readMinNoOfPixels
	private int readMinNrOfPixels() {
		int s = -1;
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 1, 18).equals(
					"InputDef::minpix")) {
				s = Integer.parseInt(optionsFile.get(i + 1));
				break;
			}
		}
		return s;
	}

	// readMaxNoOfPixels
	private int readMaxNrOfPixels() {
		int s = -1;
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 1, 18).equals(
					"InputDef::maxpix")) {
				s = Integer.parseInt(optionsFile.get(i + 1));
				break;
			}
		}
		return s;
	}

	// readMinDistBetweenPixels
	private int readMinDistBetweenPixels() {
		int s = -1;
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 1, 6).equals(
					"InputDef::minsep")) {
				s = Integer.parseInt(optionsFile.get(i + 1));
				break;
			}
		}
		return s;
	}

	// readMaxError
	private int readMaxError() {
		int s = -1;
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 1, 6).equals(
					"InputDef::maxerr")) {
				s = Integer.parseInt(optionsFile.get(i + 1));
				break;
			}
		}
		return s;
	}

	// readTripletsFromFile
	private String readTripletsFromFile() {
		String s = "";
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 3, 3).equals(
					"triplets from file")) {
				s = optionsFile.get(i + 1);
				break;
			}
		}
		return s;
	}

	// readTripletsFromFileTR
	private String readTripletsFromFileTR() {
		String s = "";
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 5, 5).equals(
					"triplets from file, turned right")) {
				s = optionsFile.get(i + 1);
				break;
			}
		}
		return s;
	}

	// readOriginalImages
	private String readOriginalImages() {
		String s = "";
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 2, 4).equals(
					"original images")) {
				s = optionsFile.get(i + 1);
				break;
			}
		}
		return s;
	}

	// readGeneratedTriplets
	private String readGeneratedTriplets() {
		String s = "";
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 2, 4).equals(
					"generated triplets")) {
				s = optionsFile.get(i + 1);
				break;
			}
		}
		return s;
	}

	// readGeneratedTripletsTR
	private String readGeneraterTripletsTR() {
		String s = "";
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 4, 4).equals(
					"generated triplets, turned right")) {
				s = optionsFile.get(i + 1);
				break;
			}
		}
		return s;
	}

	// read "output from ImageDetectPoints(), input to DuplicatePoints()
	private String readHelpFileInputToDuplicatePoints() {
		String s = "";
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 3, 7).equals(
					"input to DuplicatePoints()")) {
				s = optionsFile.get(i + 1);
				break;
			}
		}
		return s;
	}

	// read output from DuplicatePoints(), input to PointCoordinates()
	private String readHelpFileInputToPointCoord() {
		String s = "";
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 3, 7).equals(
					"input to PointCoordinates()")) {
				s = optionsFile.get(i + 1);
				break;
			}
		}
		return s;
	}

	// read helpfileinputToDobbelMatch
	private String readHelpFileInputToDobbelMatch() {
		String s = "";
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 3, 7).equals(
					"input to dobbelmatch()")) {
				s = optionsFile.get(i + 1);
				break;
			}
		}
		return s;
	}

	// read helpfile osvosvosvosvosvo
	private String readHelpFileInputToMakeTimeSeries() {
		String s = "";
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 5, 7).equals(
					"input to MakeTimeSeries() and WriteTimeSeries()")) {
				s = optionsFile.get(i + 1);
				break;
			}
		}
		return s;
	}

	private String readHelpFileOutoutFromConnectPoints() {
		String s = "";
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 3, 4).equals(
					"output from Connectpoints()")) {
				s = optionsFile.get(i + 1);
				break;
			}
		}
		return s;
	}

	private String readHelpFileOutPutfromTimeseries() {
		String s = "";
		for (int i = 0; i < optionsFile.size(); i++) {
			if (formatSentence(optionsFile.get(i), 3, 6).equals(
					"output from Timeseries()")) {
				s = optionsFile.get(i + 1);
				break;
			}
		}
		return s;
	}

	public static void main(String[] args) throws IOException {
		int antalltester = 24;
				
		VizlabInputData a = new VizlabInputData("FILEPATH", "RUNTYPE",
				"CALIBFILENAME", 999, 998, 997, 996, "SOCKKETOUTP", 995, 994,
				993, 992, 991, "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K");
		OptionsFileWriter skriver = new OptionsFileWriter(
				new File(
						"src//com//androidvizlab//bachelor//calibrationandoptionsfile//options.txt"),
				a);
		OptionsFileReader leser = new OptionsFileReader(
				new File(
						"src//com//androidvizlab//bachelor//calibrationandoptionsfile//options.txt"));
		VizlabInputData b = leser.getData();
		if	(!b.getCalibrationFileName().equals(a.getCalibrationFileName())) System.out.println("FEIL i calibfilename"); 
		if	(!b.getCalibrationFilePath().equals(a.getCalibrationFilePath()))System.out.println("FEIL i calibfilepath");
		if	(!b.getHelpFileOutputConnectPoints().equals(a.getHelpFileOutputConnectPoints()))System.out.println("FEIL i outputconnectpoints");
		if	(!b.getHelpFileOutputdobbelMatch().equals(a.getHelpFileOutputdobbelMatch()))System.out.println("FEIL i dobbelmatch");
		if	(!b.getHelpFileOutputDuplicatePoints().equals(a.getHelpFileOutputDuplicatePoints()))System.out.println("FEIL i duplicatepoints");
		if	(!b.getHelpFileOutputImageDetectPoints().equals(a.getHelpFileOutputImageDetectPoints()))System.out.println("FEIL i outputimgdetectpoints");
		if	(!b.getHelpFileOutputMatch3().equals(a.getHelpFileOutputMatch3()))System.out.println("FEIL i outoutmatch3");
		if	(!b.getHelpFileOutputTimeseries().equals(a.getHelpFileOutputTimeseries()))System.out.println("FEIL i outputtimeseries");
		if	(!b.getImgFileInputTriplets().equals(a.getImgFileInputTriplets()))System.out.println("FEIL i inputtriplets");
		if	(!b.getImgFileInputTripletsTurnedRight().equals(a.getImgFileInputTripletsTurnedRight()))System.out.println("FEIL i inputtripletsTR");
		if	(!b.getImgFileOutputGeneratedTriplets().equals(a.getImgFileOutputGeneratedTriplets()))System.out.println("FEIL i generatedtriplets");
		if	(!b.getImgFileOutputGeneratedTripletsTurnedRight().equals(a.getImgFileOutputGeneratedTripletsTurnedRight()))System.out.println("FEIL i generatedtripletstr");
		if	(!b.getImgFileOutputOriginalImg().equals(a.getImgFileOutputOriginalImg()))System.out.println("FEIL i outputorgimg");
		if	(b.getMaxerr()!=a.getMaxerr())System.out.println("FEIL i maxerr");
		if	(b.getMaxpix()!=a.getMaxpix())System.out.println("FEIL i maxpix");
		if	(b.getMinpix()!=a.getMinpix())System.out.println("FEIL i minpix");
		if	(b.getMinsep()!=a.getMinsep())System.out.println("FEIL i minsep");
		if	(b.getNumMarkers()!=a.getNumMarkers())System.out.println("FEIL i nummarkers");
		if	(b.getNumTimePts()!=a.getNumTimePts())System.out.println("FEIL i numtimepts");
		if	(b.getNumTripletCamGrp()!=a.getNumTripletCamGrp())System.out.println("FEIL i numtripletcamgrp");
		if	(b.getOffsetPix()!=a.getOffsetPix())System.out.println("FEIL i offsetpix");
		if	(!b.getProgramOutputSocketConnection().equals(a.getProgramOutputSocketConnection()))System.out.println("FEIL i outputsocketconn");
		if	(!b.getRunType().equals(a.getRunType()))System.out.println("FEIL i runtype");
		if	(b.getTriggingInterval()!=a.getTriggingInterval()) System.out.println("FEIL i trigginginterval");
			
		

	}

}
