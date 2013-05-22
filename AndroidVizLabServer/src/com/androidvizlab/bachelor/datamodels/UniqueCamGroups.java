package com.androidvizlab.bachelor.datamodels;

import java.io.Serializable;
import java.util.ArrayList;

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
	
	public double getAvgS0(){
		return avgS0;
	}
	

	public ArrayList<CameraGroup> getGroups() {
		return groups;
	}

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
	
}
