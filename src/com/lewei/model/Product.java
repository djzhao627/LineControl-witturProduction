package com.lewei.model;

/**
 * ��Ʒ��ģ��
 * 
 * @author djzhao
 * @time 2015��10��30��
 */
public class Product {

	/** ��Ʒ������ */
	private int ProID;

	/** ��Ʒ���� */
	private String ProName;

	/** ���������� */
	private int LineID;

	/** ��Ʒ���� */
	private int Takt;

	/** ��Ʒ���� */
	private int Num;

	/** ��Ʒ״̬ */
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
