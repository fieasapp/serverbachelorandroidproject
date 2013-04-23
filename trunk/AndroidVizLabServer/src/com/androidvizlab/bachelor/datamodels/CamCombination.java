package com.androidvizlab.bachelor.datamodels;

import java.io.Serializable;
import java.util.ArrayList;

public class CamCombination implements Serializable{
	
    private static final long serialVersionUID = -4474755981191734083L;

    private double mean = 0.0;
    private double max = 0.0;
    private double s0 = 0.0;
    private int nrOfFrames = 0;
    private String camGrp = "";
    private ArrayList<CameraInfo> cameraList = null;

    public CamCombination()
    {

    }

    public CamCombination(String camGrp,double s0, double mean, double max,
                    int nrOfFrames,ArrayList<CameraInfo> cameraList)
    {
            this.camGrp = camGrp;
            this.s0 = s0;
            this.mean = mean;
            this.max = max;
            this.nrOfFrames = nrOfFrames;
            this.cameraList = cameraList;
    }

    //Getter and Setters
    public double getMean() {
            return mean;
    }

    public void setMean(double mean) {
            this.mean = mean;
    }

    public double getMax() {
            return max;
    }

    public void setMax(double max) {
            this.max = max;
    }

    public double getS0() {
            return s0;
    }

    public void setS0(double s0) {
            this.s0 = s0;
    }

    public String getCamGrp() {
            return camGrp;
    }

    public void setCamGrp(String camGrp) {
            this.camGrp = camGrp;
    }

    public int getNrOfFrames() {
            return nrOfFrames;
    }

    public void setNrOfFrames(int nrOfFrames) {
            this.nrOfFrames = nrOfFrames;
    }

    public ArrayList<CameraInfo> getCameraList()
    {
            return cameraList;
    }

    public void setCameraList(ArrayList<CameraInfo> cameraList) {
            this.cameraList = cameraList;
    }
}