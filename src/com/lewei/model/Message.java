package com.lewei.model;

public class Message {
	/** ��ϢID */
	private int MesID;

	/** ������Ϣ�ĺ��� */
	private String MesNumber;

	/** ��ע�ֶ� */
	private String MesRemark;

	/** ��Ӧ���߱� -1�����Ӧ������ */
	private int LineID;

	/** ��¼״̬��Ĭ��1�� */
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
