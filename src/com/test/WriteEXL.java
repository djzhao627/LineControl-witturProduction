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
			// 创建一个工作簿
			wwb = Workbook.createWorkbook(new File("test.xls"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(wwb != null){
			// 创建一个工作表
			ws = wwb.createSheet("预警信息", 0);
			// 设置cell格式
			CellView cv = new CellView();
			cv.setAutosize(true);
			// 开始添加单元格
			for(int i = 0; i < 10; i ++){
				for(int j = 0; j < 5; j++){
					// 第一个参数表示列，第二个表示行
					Label labelC = new Label(j, i, "这是第 "+ (i+1)+"行，第 "+(j+1)+"列");
					
					// 将单元格添加到工作表中
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
			
			// 从内存中写入文件中
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
