package com.wittur.screen;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.lewei.dao.LineControlDao;
import com.lewei.model.TPLine;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 
 * @author djzhao
 * @time 2015年11月10日
 */
public class ExportEXL implements ActionListener {

	DateChooser dateChooser1 = DateChooser.getInstance("yyyy-MM-dd");

	DateChooser dateChooser2 = DateChooser.getInstance("yyyy-MM-dd");

	JFrame frame = new JFrame("导出报表");// 框架布局
	JTabbedPane tabPane = new JTabbedPane();// 选项卡布局
	Container con = new Container();// 面板
	JLabel label1 = new JLabel("文件目录");
	JLabel label2 = new JLabel("\u4FDD\u5B58\u6587\u4EF6");
	JTextField text1 = new JTextField();// TextField 目录的路径
	JTextField text2 = new JTextField("demo");// 文件的路径
	JButton button1 = new JButton("...");// 选择
	JButton button2 = new JButton("...");// 选择
	JFileChooser jfc = new JFileChooser();// 文件选择器
	JButton button3 = new JButton("导出");//
	JLabel date2 = new JLabel("单击选择时间");
	JLabel date1 = new JLabel("单击选择时间");
	private JComboBox lineName;

	private static boolean time1 = false;
	private static boolean time2 = false;

	/** 目录路径 */
	private File directory = null;
	/** 文件路径 */
	private File file = null;

	ExportEXL() {

		dateChooser1.register(date1);

		dateChooser2.register(date2);

		jfc.setCurrentDirectory(new File("D://"));// 文件选择器的初始目录定为d盘

		double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

		double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		frame.setResizable(false);

		frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));// 设定窗口出现位置
		frame.setIconImage(new ImageIcon(getClass().getResource(
				"/img/Wittur_Logo.gif")).getImage());
		frame.setSize(382, 273);// 设定窗口大小
		frame.setContentPane(tabPane);// 设置布局
		label1.setBounds(25, 118, 70, 20);
		text1.setEditable(false);
		text1.setBounds(90, 118, 120, 20);
		button1.setBounds(225, 118, 50, 20);
		label2.setBounds(25, 143, 70, 20);
		text2.setBounds(90, 143, 120, 20);
		button2.setEnabled(false);
		button2.setBounds(225, 143, 50, 20);
		button3.setBounds(291, 175, 60, 20);
		button1.addActionListener(this); // 添加事件处理
		button2.addActionListener(this); // 添加事件处理
		button3.addActionListener(this); // 添加事件处理
		con.setBackground(new Color(255, 255, 255));
		con.add(label1);
		con.add(text1);
		con.add(button1);
		con.add(label2);
		con.add(text2);
		con.add(button2);
		con.add(button3);
		con.add(date1);
		con.add(date2);
		frame.setVisible(true);// 窗口可见
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 使能关闭窗口，结束程序
		tabPane.add("选择", con);// 添加布局1

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(ExportEXL.class
				.getResource("/img/Wittur_Logo.gif")));
		label.setBounds(10, 10, 100, 90);
		con.add(label);
		
		JLabel label_1 = new JLabel("线别：");
		label_1.setFont(new Font("宋体", Font.PLAIN, 14));
		label_1.setBounds(130, 10, 54, 21);
		con.add(label_1);
		
		JLabel label_2 = new JLabel("开始：");
		label_2.setFont(new Font("宋体", Font.PLAIN, 13));
		label_2.setBounds(130, 53, 50, 15);
		con.add(label_2);
		
		JLabel label_3 = new JLabel("结束：");
		label_3.setFont(new Font("宋体", Font.PLAIN, 13));
		label_3.setBounds(130, 78, 50, 15);
		con.add(label_3);

		lineName = new JComboBox();
		lineName.setModel(new DefaultComboBoxModel(
				new String[] { "无数据" }));
		lineName.setFont(new Font("宋体", Font.PLAIN, 14));
		lineName.setBounds(175, 10, 100, 21);
		con.add(lineName);

		date2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				time2 = true;
			}
		});
		date2.setForeground(new Color(0, 0, 51));
		date2.setFont(new Font("宋体", Font.BOLD, 13));
		date2.setBounds(190, 78, 100, 15);

		date1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				time1 = true;
			}
		});
		date1.setForeground(new Color(0, 0, 51));
		date1.setFont(new Font("宋体", Font.BOLD, 13));
		date1.setBounds(190, 53, 100, 15);

		// 产线列表更新
		SwingWorker updateLine = updateLine();
		updateLine.execute();

	}

	/**
	 * 事件监听的方法。
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(button1)) {// 判断触发方法的按钮是哪个
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// 设定只能选择到文件夹
			int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
			if (state == 1) {
				return;
			} else {
				directory = jfc.getSelectedFile();// directory为选择到的目录
				text1.setText(directory.getAbsolutePath());
			}
		}
		// 绑定到选择文件，选择文件事件
		if (e.getSource().equals(button2)) {
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);// 设定只能选择到文件
			int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
			if (state == 1) {
				return;// 撤销则返回
			} else {
				file = jfc.getSelectedFile();// file为选择到的文件
				text2.setText(file.getAbsolutePath());
			}
		}
		if (e.getSource().equals(button3)) {// 从数据读取数据，写入Excel

			if (text1.getText().length() < 2) {
				JOptionPane.showMessageDialog(null, "请选择目的路径！", "错误", 0);
			} else if (time1 && time2) {

				boolean flag = true;

				/** 1.获取数据 */
				// 实例化dao
				LineControlDao lcd = new LineControlDao();
				// 实例化产线模型
				List<TPLine> lineList = new ArrayList<>();
				// 关键字
				String key = lineName.getSelectedItem().toString();

				// 更换为通配符
				if ("全部".equals(key)) {
					key = "%";
				}
				try {
					// 获取数据库的数据
					lineList = lcd.getLineList(key, date1.getText().toString(),
							date2.getText().toString());
				} catch (SQLException e1) {
					flag = false;
					e1.printStackTrace();
				}

				/** 2.写入Excel */
				try {
					// 创建一个工作簿
					WritableWorkbook wwb = Workbook.createWorkbook(new File(
							directory.getAbsolutePath() + "\\"
									+ text2.getText() + ".xls"));
					if (wwb != null) {
						// 创建一个工作表
						WritableSheet ws = wwb.createSheet("情况查询", 0);
						// 设置cell格式
						CellView cv = new CellView();
						cv.setAutosize(true);
						// 设置标题
						String[] head = new String[] { "线别", "工作时间", "计划产量",
								"实际产量", "损失时间", "造成损失的工位","创建时间" };

						// 插入数据库数据
						int row = 1;// 从第二行开始插入
						for (TPLine l : lineList) {
							// 第一个参数表示列，第二个表示行
							Label labelC1 = new Label(0, row, l.getTPLineName());
							ws.addCell(labelC1);
							Label labelC2 = new Label(1, row, l.getStartTime()
									+ "-" + l.getEndTime());
							ws.addCell(labelC2);
							Label labelC3 = new Label(2, row, l.getPlanNum()
									+ "");
							ws.addCell(labelC3);
							Label labelC4 = new Label(3, row, l.getRealNum()
									+ "");
							ws.addCell(labelC4);
							Label labelC5 = new Label(4, row, l.getLostTime());
							ws.addCell(labelC5);
							Label labelC6 = new Label(5, row,
									l.getWorkStations());
							ws.addCell(labelC6);
							Label labelC7 = new Label(6, row,
									l.getChangeTime());
							ws.addCell(labelC7);
							// 增加一行
							row++;
						}

						// 开始添加第一行标题单元格
						for (int j = 0; j < head.length; j++) {
							// 第一个参数表示列，第二个表示行
							Label labelC = new Label(j, 0, head[j]);
							// 将单元格添加到工作表中
							ws.addCell(labelC);
							ws.setColumnView(j, 15);
						}

						// 从内存中写入到文件中
						wwb.write();
						wwb.close();

					}
				} catch (IOException e1) {
					flag = false;
					e1.printStackTrace();
				} catch (RowsExceededException e1) {
					flag = false;
					e1.printStackTrace();
				} catch (WriteException e1) {
					flag = false;
					e1.printStackTrace();
				}
				if (flag) {
					JOptionPane.showMessageDialog(null, "生成报表成功！\n请到相关目录查看。",
							"提示", 1);
				} else {
					JOptionPane
							.showMessageDialog(null, "导出时发生错误，请重试！", "错误", 0);
				}
			} else {
				JOptionPane.showMessageDialog(null, "请选择时间！", "错误", 0);
			}
		}
	}

	public static void main(String[] args) {
		new ExportEXL();
	}

	/**
	 * 更新产线列表。
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	SwingWorker updateLine() {
		SwingWorker getLine = new SwingWorker<String[], String[]>() {

			@SuppressWarnings("unchecked")
			@Override
			protected String[] doInBackground() throws Exception {

				LineControlDao lcd = new LineControlDao();
				// 产线列表更新
				String[] lineList = lcd.getLineList2();
				return lineList;
			}

			@Override
			protected void done() {
				try {
					lineName.setModel(new DefaultComboBoxModel(get()));
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				super.done();
			}
		};
		return getLine;

	}
}