package com.lewei.model;

/**
 * 产线模型
 * 
 * @author djzhao
 * @time 2015年10月30日
 */
public class Line {

	/** 产线表主键 */
	private int ID;

	/** 产线所对应的ID标识 */
	private int LineID;

	/** 产线名称 */
	private String LineName;

	/** 备注信息 */
	private String Remarks;

	/** 状态标识 */
	private int Status;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getLineID() {
		return LineID;
	}

	public void setLineID(int lineID) {
		LineID = lineID;
	}

	public String getLineName() {
		return LineName;
	}

	public void setLineName(String lineName) {
		LineName = lineName;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

}
