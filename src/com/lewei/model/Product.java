package com.lewei.model;

/**
 * 产品表模型
 * 
 * @author djzhao
 * @time 2015年10月30日
 */
public class Product {

	/** 产品表主键 */
	private int ProID;

	/** 产品名称 */
	private String ProName;

	/** 所属产线名 */
	private int LineID;

	/** 产品节拍 */
	private int Takt;

	/** 产品数量 */
	private int Num;

	/** 产品状态 */
	private int Status;

	public int getProID() {
		return ProID;
	}

	public void setProID(int proID) {
		ProID = proID;
	}

	public String getProName() {
		return ProName;
	}

	public void setProName(String proName) {
		ProName = proName;
	}

	public int getLineID() {
		return LineID;
	}

	public void setLineID(int lineID) {
		LineID = lineID;
	}

	public int getTakt() {
		return Takt;
	}

	public void setTakt(int takt) {
		Takt = takt;
	}

	public int getNum() {
		return Num;
	}

	public void setNum(int num) {
		Num = num;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

}
