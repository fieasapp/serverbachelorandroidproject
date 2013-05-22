package com.androidvizlab.bachelor.datamodels;

import java.io.Serializable;
import java.util.ArrayList;

public class VizlabOutputData implements Serializable{
	
    private static final long serialVersionUID = 2L;

    private ArrayList<CameraGroup> listCamGrp = new ArrayList<CameraGroup>();
    private ArrayList<CameraGroup> recomListGrp = new ArrayList<CameraGroup>();
    private ArrayList<UniqueCamGroups> uniqueCamGrp = new ArrayList<UniqueCamGroups>();

    public VizlabOutputData()
    {
 
    }
    
    public ArrayList<CameraGroup> getListCamGrp() {
        return listCamGrp;
    }

    public void setListCamGrp(ArrayList<CameraGroup> listCamGrp) {
        this.listCamGrp = listCamGrp;
    }

    public ArrayList<CameraGroup> getRecomListGrp() {
        return recomListGrp;
    }

    public void setRecomListGrp(ArrayList<CameraGroup> recomListGrp) {
        this.recomListGrp = recomListGrp;
    }

    public ArrayList<UniqueCamGroups> getUniqueCamGrp() {
        return uniqueCamGrp;
    }

    public void setUniqueCamGrp(ArrayList<UniqueCamGroups> uniqueCamGrp) {
        this.uniqueCamGrp = uniqueCamGrp;
    }
}
