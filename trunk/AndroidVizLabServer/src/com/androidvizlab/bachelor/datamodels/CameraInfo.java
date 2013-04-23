package com.androidvizlab.bachelor.datamodels;

import java.io.Serializable;

public class CameraInfo implements Serializable{
	
    private static final long serialVersionUID = -1081138647066642371L;

    private int camNo;
    private double c;
    private double xh;
    private double yh;
    private double ort;
    private float f1;
    private float f2;
    private float p1;
    private float p2;


    public CameraInfo(int camNo, double c, double xh, double yh, double ort,
                    float f1, float f2, float p1, float p2) {
            this.camNo = camNo;
            this.c = c;
            this.xh = xh;
            this.yh = yh;
            this.ort = ort;
            this.f1 = f1;
            this.f2 = f2;
            this.p1 = p1;
            this.p2 = p2;
    }

    //GETTERS AND SETTERS

    public int getCamNo() {
            return camNo;
    }

    public void setCamNo(int camNo) {
            this.camNo = camNo;
    }

    public double getC() {
            return c;
    }

    public void setC(double c) {
            this.c = c;
    }

    public double getXh() {
            return xh;
    }

    public void setXh(double xh) {
            this.xh = xh;
    }

    public double getYh() {
            return yh;
    }

    public void setYh(double yh) {
            this.yh = yh;
    }

    public double getOrt() {
            return ort;
    }

    public void setOrt(double ort) {
            this.ort = ort;
    }

    public float getF1() {
            return f1;
    }

    public void setF1(float f1) {
            this.f1 = f1;
    }

    public float getF2() {
            return f2;
    }

    public void setF2(float f2) {
            this.f2 = f2;
    }

    public float getP1() {
            return p1;
    }

    public void setP1(float p1) {
            this.p1 = p1;
    }

    public float getP2() {
            return p2;
    }

    public void setP2(float p2) {
            this.p2 = p2;
    }

    //TO STRING
    public String toString(){
            return String.format("Camnr: %s, C: %s, XH: %s, YH: %s, ORT: %s, F1: %s, F2: %s, P1: %s, P2: %s", camNo, c, xh, yh, ort, f1, f2, p1, p2);
    }
}