package com.lewei.model;

public class Message {
	/** 信息ID */
	private int MesID;

	/** 接收信息的号码 */
	private String MesNumber;

	/** 备注字段 */
	private String MesRemark;

	/** 对应的线别 -1代表对应所有线 */
	private int LineID;

	/** 记录状态（默认1） */
	private int Status;

	/**
	 * @return the mesID
	 */
	public int getMesID() {
		return MesID;
	}

	/**
	 * @param mesID
	 *            the mesID to set
	 */
	public void setMesID(int mesID) {
		MesID = mesID;
	}

	/**
	 * @return the mesNumber
	 */
	public String getMesNumber() {
		return MesNumber;
	}

	/**
	 * @param mesNumber
	 *            the mesNumber to set
	 */
	public void setMesNumber(String mesNumber) {
		MesNumber = mesNumber;
	}

	/**
	 * @return the mesRemark
	 */
	public String getMesRemark() {
		return MesRemark;
	}

	/**
	 * @param mesRemark
	 *            the mesRemark to set
	 */
	public void setMesRemark(String mesRemark) {
		MesRemark = mesRemark;
	}

	/**
	 * @return the lineID
	 */
	public int getLineID() {
		return LineID;
	}

	/**
	 * @param lineID
	 *            the lineID to set
	 */
	public void setLineID(int lineID) {
		LineID = lineID;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return Status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		Status = status;
	}

}
