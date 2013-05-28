package com.androidvizlab.bachelor.datamodels;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Holds a list of camera groups that are uniquely combine
 * @author Jakob
 */
public class UniqueCamGroups implements Comparable<UniqueCamGroups>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8805183700783394319L;
	private ArrayList<CameraGroup> groups;
	private double avgS0;
	
	public UniqueCamGroups(ArrayList<CameraGroup> groups) {
		this.groups = groups;
		for(CameraGroup cg : this.groups){
			avgS0 += cg.getS0();
		}
		avgS0 /= this.groups.size();
	}

        /**
         * Compares camera groups by the average s0
         * @param o an object to compare with
         * @return Returns a positive integer if the average s0 is greater than the compared object
         */
	@Override
	public int compareTo(UniqueCamGroups o) {
		// TODO Auto-generated method stub
		if(avgS0>o.getAvgS0()){
			return 1;
		}else if(avgS0<o.getAvgS0()){
			return -1;
		}else if(Double.compare(avgS0, o.getAvgS0()) == 0){
			return 0;
		}else{
			return 0;
		}
	}
	
	public String toString(){
		String s = "";
		for(CameraGroup cg : groups){
			s += cg.getGroupName() + cg.getS0();
			s += "\n";
		}
		s += "Avg s0: " + avgS0;
		return s;
	}
        
        //GETTERS AND SETTERS

    public double getAvgS0()
    {
	return avgS0;
    }
        
    public ArrayList<CameraGroup> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<CameraGroup> groups) {
        this.groups = groups;
    }
}
