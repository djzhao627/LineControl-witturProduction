package com.test;

import java.io.File;
import java.io.IOException;

import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class WriteEXL {

	private static WritableWorkbook wwb;

	private static WritableSheet ws;

	public static void main(String[] args) {
		
		try {
			// ����һ��������
			wwb = Workbook.createWorkbook(new File("test.xls"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(wwb != null){
			// ����һ��������
			ws = wwb.createSheet("Ԥ����Ϣ", 0);
			// ����cell��ʽ
			CellView cv = new CellView();
			cv.setAutosize(true);
			// ��ʼ��ӵ�Ԫ��
			for(int i = 0; i < 10; i ++){
				for(int j = 0; j < 5; j++){
					// ��һ��������ʾ�У��ڶ�����ʾ��
					Label labelC = new Label(j, i, "���ǵ� "+ (i+1)+"�У��� "+(j+1)+"��");
					
					// ����Ԫ����ӵ���������
					try {
						ws.addCell(labelC);
					} catch (RowsExceededException e) {
						e.printStackTrace();
					} catch (WriteException e) {
						e.printStackTrace();
					}
					ws.setColumnView(j, 20);
				}
			}
			
			// ���ڴ���д���ļ���
			try {
				wwb.write();
				wwb.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("SUCCESS!");
	}
}
