package com.lewei.model;

public class Email {
	/** ����ID */
	private int EmailID;

	/** �����ַ */
	private String Email;

	/** ��ע��Ϣ */
	private String EmailRemark;

	/** ��Ӧ���߱� -1������Ӧ������ */
	private int LineID;

	/** ״̬�У�Ĭ��1�� */
	private int Status;

	/**
	 * @return the emailID
	 */
	public int getEmailID() {
		return EmailID;
	}

	/**
	 * @param emailID
	 *            the emailID to set
	 */
	public void setEmailID(int emailID) {
		EmailID = emailID;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return Email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		Email = email;
	}

	/**
	 * @return the emailRemark
	 */
	public String getEmailRemark() {
		return EmailRemark;
	}

	/**
	 * @param emailRemark
	 *            the emailRemark to set
	 */
	public void setEmailRemark(String emailRemark) {
		EmailRemark = emailRemark;
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