package com.androidvizlab.bachelor.datamodels;

import java.io.Serializable;
import java.util.ArrayList;

public class CameraGroup implements Serializable, Comparable<CameraGroup> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String groupName = "";
	private double mean = 0.0;
	private double max = 0.0;
	private double s0 = 0.0;
	private int noOfFramesUsed = 0;
	private ArrayList<Camera> cameraList;

	public CameraGroup(String groupName, double mean, double max, double s0, int noOfFramesUsed,
			ArrayList<Camera> cameraList) {
		this.groupName = groupName;
		this.mean = mean;
		this.max = max;
		this.s0 = s0;
		this.noOfFramesUsed = noOfFramesUsed;
		this.cameraList = cameraList;
	}

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

        public int getNoOfFramesUsed() {
            return noOfFramesUsed;
        }

        public void setNoOfFramesUsed(int noOfFramesUsed) {
            this.noOfFramesUsed = noOfFramesUsed;
        }

	public ArrayList<Camera> getCameraList() {
		return cameraList;
	}

	public void setCameraList(ArrayList<Camera> cameraList) {
		this.cameraList = cameraList;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
        
        public String toString() {
		String ret = groupName;
		ret += "\n";
		ret += s0 + "\n";
		ret += mean + "\n";
		ret += max + "\n";
		for (Camera c : cameraList) {
			ret += c.toString();
			ret += "\n";
		}
		return ret;
	}

	@Override
	public int compareTo(CameraGroup o) {

		if (this.s0 > o.getS0()) {
			return 1;
		} else if (this.s0 < o.getS0()) {
			return -1;
		} else {
			return 0;
		}
	}

	public boolean hasSameCamera(CameraGroup cg) {
		boolean bool = false;
		for (int i = 0; i < cg.getCameraList().size(); i++) {
			for (int j = 0; j < cameraList.size(); j++) {
				if (cg.getCameraList().get(i).getCamNo() == cameraList.get(j)
						.getCamNo()) {
					bool = true;
					break;
				}
			}
		}
		return bool;
	}

}
