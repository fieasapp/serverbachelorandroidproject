package com.androidvizlab.bachelor.datamodels;

import java.io.Serializable;
import java.util.ArrayList;

public class VizlabOutputData implements Serializable{
	
    private static final long serialVersionUID = 2L;

    //private ArrayList<CamCombination> resultData = new ArrayList<CamCombination>();
    private ArrayList<CameraGroup> listCamGrp = new ArrayList<CameraGroup>();
    private ArrayList<CameraGroup> recomListGrp = new ArrayList<CameraGroup>();

    public VizlabOutputData()
    {
        /*ArrayList<CameraInfo> test = new ArrayList<CameraInfo>();
        test.add(new CameraInfo(1,550.823,43.896,-2.366,0.003494,1.0f,1.0f,1.0f,1.0f));
        test.add(new CameraInfo(2,549.653,43.896,-2.366,0.003494,1.0f,1.0f,1.0f,1.0f));
        test.add(new CameraInfo(3,536.283,43.896,-2.366,0.003494,1.0f,1.0f,1.0f,1.0f));

        resultData.add(new CamCombination("1_2_3",0.0674,0.38,0.48,55,test));
        resultData.add(new CamCombination("4_5_6",0.0674,0.38,0.48,55,test));
        resultData.add(new CamCombination("2_3_4",0.0674,0.38,0.48,55,test));
        resultData.add(new CamCombination("3_4_5",0.0674,0.38,0.48,55,test));
        resultData.add(new CamCombination("5_6_1",0.0674,0.38,0.48,55,test));
        resultData.add(new CamCombination("6_1_2",0.0674,0.38,0.48,55,test));
        resultData.add(new CamCombination("2_5_6",0.0674,0.38,0.48,55,test));*/
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
}
