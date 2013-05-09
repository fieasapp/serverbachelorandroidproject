package com.androidvizlab.bachelor.FileWriterAndReader;

import static com.androidvizlab.bachelor.FileWriterAndReader.Helpmethods.formatSentence;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.androidvizlab.bachelor.datamodels.VizlabInputData;

public class OptionsFileReader {
	
	private ArrayList<String> optionsFile;
	private BufferedReader in;
	private VizlabInputData data;
	
	public OptionsFileReader(File file){
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
	}
	
	private String readCalibDir(){
		String s = "Not found";
		for(int i = 0; i<optionsFile.size(); i++){
			if(optionsFile.get(i).equals("!path for calibration files")){
				s = optionsFile.get(i+1);
				break;
			}
		}
		return s;
	}
	
	// set "path for calibration files"
	// set "run type"
	// set "path and name of calibration file"
	// set "trigging interval"
	// set "no. of triplet groups"
	// set "no of time pts, this run"
	// set "no of markers"
	// set "output from program: Socket connection"
	//readMinNoOfPixels
	//readMaxNoOfPixels
	//readMinDistBetweenPixels
	//readMaxError
	//readTripletsFromFile
	//readTripletsFromFileTR
	//readOriginalImages
	//readGeneratedTriplets
	//readGeneratedTripletsTR
	//read "output from ImageDetectPoints(), input to DuplicatePoints()
	//read output from DuplicatePoints(), input to PointCoordinates()
	//read helpfileinputToDobbelMatch
	//read helpfile osvosvosvosvosvo
	
}
