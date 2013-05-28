package com.androidvizlab.bachelor.datamodels;

import java.io.Serializable;

/**
 * Holds the values in the options-file. This class is sent through the stream between client and server
 * @author Jakob
 */
public class VizlabInputData implements Serializable {

    private static final long serialVersionUID = 1L;

    //*** General Option ***//
    private String calibrationFilePath = "";
    private String runType = "";
    private String calibrationFileName = "";
    private int triggingInterval = 0;
    private int numTripletCamGrp = 0;
    private int numTimePts = 0;
    private int numMarkers = 0;
    private String programOutputSocketConnection = "F";
    
    //*** Advanced Option ***//

    private int offsetPix;
    private int minpix;
    private int maxpix;
    private int minsep;
    private int maxerr;
    
    //Image file Input/Output
    private String imgFileInputTriplets = "T";
    private String imgFileInputTripletsTurnedRight = "T";
    private String imgFileOutputOriginalImg = "T";
    private String imgFileOutputGeneratedTriplets = "T";
    private String imgFileOutputGeneratedTripletsTurnedRight = "T";

    //Help file
    private String helpFileOutputImageDetectPoints = "T";
    private String helpFileOutputDuplicatePoints = "T";
    private String helpFileOutputMatch3 = "T";
    private String helpFileOutputdobbelMatch = "T";
    private String helpFileOutputConnectPoints = "T";
    private String helpFileOutputTimeseries = "T";
    
    
    
    //Production only
    private String selectedTripCamGrps = "";

    //GETTERS AND SETTERS
    public VizlabInputData(){}
    public VizlabInputData(String calibrationFilePath, String runType,
			String calibrationFileName, int triggingInterval,
			int numTripletCamGrp, int numTimePts, int numMarkers,
			String programOutputSocketConnection, int offsetPix, int minpix,
			int maxpix, int minsep, int maxerr, String imgFileInputTriplets,
			String imgFileInputTripletsTurnedRight,
			String imgFileOutputOriginalImg,
			String imgFileOutputGeneratedTriplets,
			String imgFileOutputGeneratedTripletsTurnedRight,
			String helpFileOutputImageDetectPoints,
			String helpFileOutputDuplicatePoints, String helpFileOutputMatch3,
			String helpFileOutputdobbelMatch,
			String helpFileOutputConnectPoints, String helpFileOutputTimeseries) {
		this.calibrationFilePath = calibrationFilePath;
		this.runType = runType;
		this.calibrationFileName = calibrationFileName;
		this.triggingInterval = triggingInterval;
		this.numTripletCamGrp = numTripletCamGrp;
		this.numTimePts = numTimePts;
		this.numMarkers = numMarkers;
		this.programOutputSocketConnection = programOutputSocketConnection;
		this.offsetPix = offsetPix;
		this.minpix = minpix;
		this.maxpix = maxpix;
		this.minsep = minsep;
		this.maxerr = maxerr;
		this.imgFileInputTriplets = imgFileInputTriplets;
		this.imgFileInputTripletsTurnedRight = imgFileInputTripletsTurnedRight;
		this.imgFileOutputOriginalImg = imgFileOutputOriginalImg;
		this.imgFileOutputGeneratedTriplets = imgFileOutputGeneratedTriplets;
		this.imgFileOutputGeneratedTripletsTurnedRight = imgFileOutputGeneratedTripletsTurnedRight;
		this.helpFileOutputImageDetectPoints = helpFileOutputImageDetectPoints;
		this.helpFileOutputDuplicatePoints = helpFileOutputDuplicatePoints;
		this.helpFileOutputMatch3 = helpFileOutputMatch3;
		this.helpFileOutputdobbelMatch = helpFileOutputdobbelMatch;
		this.helpFileOutputConnectPoints = helpFileOutputConnectPoints;
		this.helpFileOutputTimeseries = helpFileOutputTimeseries;
	}

	public String getCalibrationFilePath() {
        return calibrationFilePath;
    }

    public void setCalibrationFilePath(String calibrationFilePath) {
        this.calibrationFilePath = calibrationFilePath;
    }

    public String getRunType() {
        return runType;
    }

    public void setRunType(String runType) {
        this.runType = runType;
    }

    public String getCalibrationFileName() {
        return calibrationFileName;
    }

    public void setCalibrationFileName(String calibrationFileName) {
        this.calibrationFileName = calibrationFileName;
    }

    public int getTriggingInterval() {
        return triggingInterval;
    }

    public void setTriggingInterval(int triggingInterval) {
        this.triggingInterval = triggingInterval;
    }

    public int getNumTripletCamGrp() {
        return numTripletCamGrp;
    }

    public void setNumTripletCamGrp(int numTripletCamGrp) {
        this.numTripletCamGrp = numTripletCamGrp;
    }

    public int getNumTimePts() {
        return numTimePts;
    }

    public void setNumTimePts(int numTimePts) {
        this.numTimePts = numTimePts;
    }

    public int getNumMarkers() {
        return numMarkers;
    }

    public void setNumMarkers(int numMarkers) {
        this.numMarkers = numMarkers;
    }

    public String getProgramOutputSocketConnection() {
        return programOutputSocketConnection;
    }

    public void setProgramOutputSocketConnection(
                    String programOutputSocketConnection) {
        this.programOutputSocketConnection = programOutputSocketConnection;
    }

    public String getImgFileInputTriplets() {
        return imgFileInputTriplets;
    }

    public void setImgFileInputTriplets(String imgFileInputTriplets) {
        this.imgFileInputTriplets = imgFileInputTriplets;
    }

    public String getImgFileInputTripletsTurnedRight() {
        return imgFileInputTripletsTurnedRight;
    }

    public void setImgFileInputTripletsTurnedRight(
                    String imgFileInputTripletsTurnedRight) {
        this.imgFileInputTripletsTurnedRight = imgFileInputTripletsTurnedRight;
    }

    public String getImgFileOutputOriginalImg() {
        return imgFileOutputOriginalImg;
    }

    public void setImgFileOutputOriginalImg(String imgFileOutputOriginalImg) {
        this.imgFileOutputOriginalImg = imgFileOutputOriginalImg;
    }

    public String getImgFileOutputGeneratedTriplets() {
        return imgFileOutputGeneratedTriplets;
    }

    public void setImgFileOutputGeneratedTriplets(
                    String imgFileOutputGeneratedTriplets) {
        this.imgFileOutputGeneratedTriplets = imgFileOutputGeneratedTriplets;
    }

    public String getImgFileOutputGeneratedTripletsTurnedRight() {
        return imgFileOutputGeneratedTripletsTurnedRight;
    }

    public void setImgFileOutputGeneratedTripletsTurnedRight(
                    String imgFileOutputGeneratedTripletsTurnedRight) {
        this.imgFileOutputGeneratedTripletsTurnedRight = imgFileOutputGeneratedTripletsTurnedRight;
    }

    public String getHelpFileOutputImageDetectPoints() {
        return helpFileOutputImageDetectPoints;
    }

    public void setHelpFileOutputImageDetectPoints(
                    String helpFileOutputImageDetectPoints) {
        this.helpFileOutputImageDetectPoints = helpFileOutputImageDetectPoints;
    }

    public String getHelpFileOutputDuplicatePoints() {
        return helpFileOutputDuplicatePoints;
    }

    public void setHelpFileOutputDuplicatePoints(
                    String helpFileOutputDuplicatePoints) {
        this.helpFileOutputDuplicatePoints = helpFileOutputDuplicatePoints;
    }

    public String getHelpFileOutputMatch3() {
        return helpFileOutputMatch3;
    }

    public void setHelpFileOutputMatch3(String helpFileOutputMatch3) {
        this.helpFileOutputMatch3 = helpFileOutputMatch3;
    }

    public String getHelpFileOutputdobbelMatch() {
        return helpFileOutputdobbelMatch;
    }

    public void setHelpFileOutputdobbelMatch(String helpFileOutputdobbelMatch) {
        this.helpFileOutputdobbelMatch = helpFileOutputdobbelMatch;
    }

    public String getHelpFileOutputConnectPoints() {
        return helpFileOutputConnectPoints;
    }

    public void setHelpFileOutputConnectPoints(String helpFileOutputConnectPoints) {
        this.helpFileOutputConnectPoints = helpFileOutputConnectPoints;
    }

    public String getHelpFileOutputTimeseries() {
        return helpFileOutputTimeseries;
    }

    public void setHelpFileOutputTimeseries(String helpFileOutputTimeseries) {
        this.helpFileOutputTimeseries = helpFileOutputTimeseries;
    }

    public int getMinpix() {
        return minpix;
    }

    public void setMinpix(int minpix) {
        this.minpix = minpix;
    }

    public int getMaxpix() {
        return maxpix;
    }

    public void setMaxpix(int maxpix) {
        this.maxpix = maxpix;
    }

    public int getMinsep() {
        return minsep;
    }

    public void setMinsep(int minsep) {
        this.minsep = minsep;
    }

    public int getMaxerr() {
        return maxerr;
    }

    public void setMaxerr(int maxerr) {
        this.maxerr = maxerr;
    }
    

    public int getOffsetPix() {
        return offsetPix;
    }

    public void setOffsetPix(int offsetPix) {
        this.offsetPix = offsetPix;
    }
    
    public String getSelectedTripCamGrps() {
        return selectedTripCamGrps;
    }

    public void setSelectedTripCamGrps(String selectedTripCamGrps) {
        this.selectedTripCamGrps = selectedTripCamGrps;
    }

    @Override
    public String toString() {
            return "VizlabInputData [calibrationFilePath=" + calibrationFilePath
                            + ", runType=" + runType + ", calibrationFileName="
                            + calibrationFileName + ", triggingInterval="
                            + triggingInterval + ", numTripletCamGrp=" + numTripletCamGrp
                            + ", numTimePts=" + numTimePts + ", numMarkers=" + numMarkers
                            + ", programOutputSocketConnection="
                            + programOutputSocketConnection + ", mixpix=" + minpix
                            + ", maxpix=" + maxpix + ", minsep=" + minsep + ", maxerr="
                            + maxerr + ", imgFileInputTriplets=" + imgFileInputTriplets
                            + ", imgFileInputTripletsTurnedRight="
                            + imgFileInputTripletsTurnedRight
                            + ", imgFileOutputOriginalImg=" + imgFileOutputOriginalImg
                            + ", imgFileOutputGeneratedTriplets="
                            + imgFileOutputGeneratedTriplets
                            + ", imgFileOutputGeneratedTripletsTurnedRight="
                            + imgFileOutputGeneratedTripletsTurnedRight
                            + ", helpFileOutputImageDetectPoints="
                            + helpFileOutputImageDetectPoints
                            + ", helpFileOutputDuplicatePoints="
                            + helpFileOutputDuplicatePoints + ", helpFileOutputMatch3="
                            + helpFileOutputMatch3 + ", helpFileOutputdobbelMatch="
                            + helpFileOutputdobbelMatch + ", helpFileOutputConnectPoints="
                            + helpFileOutputConnectPoints + ", helpFileOutputTimeseries="
                            + helpFileOutputTimeseries + ", approxFrameMarkerLimit="
                            +  "]";
    }
}