package com.lewei.model;

/**
 * ����ģ��
 * 
 * @author djzhao
 * @time 2015��10��30��
 */
public class Line {

	/** ���߱����� */
	private int ID;

	/** ��������Ӧ��ID��ʶ */
	private int LineID;

	/** �������� */
	private String LineName;

	/** ��ע��Ϣ */
	private String Remarks;

	/** ״̬��ʶ */
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
