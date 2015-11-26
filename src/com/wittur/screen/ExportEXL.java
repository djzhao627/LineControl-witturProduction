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
 * @time 2015��11��10��
 */
public class ExportEXL implements ActionListener {

	DateChooser dateChooser1 = DateChooser.getInstance("yyyy-MM-dd");

	DateChooser dateChooser2 = DateChooser.getInstance("yyyy-MM-dd");

	JFrame frame = new JFrame("��������");// ��ܲ���
	JTabbedPane tabPane = new JTabbedPane();// ѡ�����
	Container con = new Container();// ���
	JLabel label1 = new JLabel("�ļ�Ŀ¼");
	JLabel label2 = new JLabel("\u4FDD\u5B58\u6587\u4EF6");
	JTextField text1 = new JTextField();// TextField Ŀ¼��·��
	JTextField text2 = new JTextField("demo");// �ļ���·��
	JButton button1 = new JButton("...");// ѡ��
	JButton button2 = new JButton("...");// ѡ��
	JFileChooser jfc = new JFileChooser();// �ļ�ѡ����
	JButton button3 = new JButton("����");//
	JLabel date2 = new JLabel("����ѡ��ʱ��");
	JLabel date1 = new JLabel("����ѡ��ʱ��");
	private JComboBox lineName;

	private static boolean time1 = false;
	private static boolean time2 = false;

	/** Ŀ¼·�� */
	private File directory = null;
	/** �ļ�·�� */
	private File file = null;

	ExportEXL() {

		dateChooser1.register(date1);

		dateChooser2.register(date2);

		jfc.setCurrentDirectory(new File("D://"));// �ļ�ѡ�����ĳ�ʼĿ¼��Ϊd��

		double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

		double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		frame.setResizable(false);

		frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));// �趨���ڳ���λ��
		frame.setIconImage(new ImageIcon(getClass().getResource(
				"/img/Wittur_Logo.gif")).getImage());
		frame.setSize(382, 273);// �趨���ڴ�С
		frame.setContentPane(tabPane);// ���ò���
		label1.setBounds(25, 118, 70, 20);
		text1.setEditable(false);
		text1.setBounds(90, 118, 120, 20);
		button1.setBounds(225, 118, 50, 20);
		label2.setBounds(25, 143, 70, 20);
		text2.setBounds(90, 143, 120, 20);
		button2.setEnabled(false);
		button2.setBounds(225, 143, 50, 20);
		button3.setBounds(291, 175, 60, 20);
		button1.addActionListener(this); // ����¼�����
		button2.addActionListener(this); // ����¼�����
		button3.addActionListener(this); // ����¼�����
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
		frame.setVisible(true);// ���ڿɼ�
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ʹ�ܹرմ��ڣ���������
		tabPane.add("ѡ��", con);// ��Ӳ���1

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(ExportEXL.class
				.getResource("/img/Wittur_Logo.gif")));
		label.setBounds(10, 10, 100, 90);
		con.add(label);
		
		JLabel label_1 = new JLabel("�߱�");
		label_1.setFont(new Font("����", Font.PLAIN, 14));
		label_1.setBounds(130, 10, 54, 21);
		con.add(label_1);
		
		JLabel label_2 = new JLabel("��ʼ��");
		label_2.setFont(new Font("����", Font.PLAIN, 13));
		label_2.setBounds(130, 53, 50, 15);
		con.add(label_2);
		
		JLabel label_3 = new JLabel("������");
		label_3.setFont(new Font("����", Font.PLAIN, 13));
		label_3.setBounds(130, 78, 50, 15);
		con.add(label_3);

		lineName = new JComboBox();
		lineName.setModel(new DefaultComboBoxModel(
				new String[] { "������" }));
		lineName.setFont(new Font("����", Font.PLAIN, 14));
		lineName.setBounds(175, 10, 100, 21);
		con.add(lineName);

		date2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				time2 = true;
			}
		});
		date2.setForeground(new Color(0, 0, 51));
		date2.setFont(new Font("����", Font.BOLD, 13));
		date2.setBounds(190, 78, 100, 15);

		date1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				time1 = true;
			}
		});
		date1.setForeground(new Color(0, 0, 51));
		date1.setFont(new Font("����", Font.BOLD, 13));
		date1.setBounds(190, 53, 100, 15);

		// �����б����
		SwingWorker updateLine = updateLine();
		updateLine.execute();

	}

	/**
	 * �¼������ķ�����
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(button1)) {// �жϴ��������İ�ť���ĸ�
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// �趨ֻ��ѡ���ļ���
			int state = jfc.showOpenDialog(null);// �˾��Ǵ��ļ�ѡ��������Ĵ������
			if (state == 1) {
				return;
			} else {
				directory = jfc.getSelectedFile();// directoryΪѡ�񵽵�Ŀ¼
				text1.setText(directory.getAbsolutePath());
			}
		}
		// �󶨵�ѡ���ļ���ѡ���ļ��¼�
		if (e.getSource().equals(button2)) {
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);// �趨ֻ��ѡ���ļ�
			int state = jfc.showOpenDialog(null);// �˾��Ǵ��ļ�ѡ��������Ĵ������
			if (state == 1) {
				return;// �����򷵻�
			} else {
				file = jfc.getSelectedFile();// fileΪѡ�񵽵��ļ�
				text2.setText(file.getAbsolutePath());
			}
		}
		if (e.getSource().equals(button3)) {// �����ݶ�ȡ���ݣ�д��Excel

			if (text1.getText().length() < 2) {
				JOptionPane.showMessageDialog(null, "��ѡ��Ŀ��·����", "����", 0);
			} else if (time1 && time2) {

				boolean flag = true;

				/** 1.��ȡ���� */
				// ʵ����dao
				LineControlDao lcd = new LineControlDao();
				// ʵ��������ģ��
				List<TPLine> lineList = new ArrayList<>();
				// �ؼ���
				String key = lineName.getSelectedItem().toString();

				// ����Ϊͨ���
				if ("ȫ��".equals(key)) {
					key = "%";
				}
				try {
					// ��ȡ���ݿ������
					lineList = lcd.getLineList(key, date1.getText().toString(),
							date2.getText().toString());
				} catch (SQLException e1) {
					flag = false;
					e1.printStackTrace();
				}

				/** 2.д��Excel */
				try {
					// ����һ��������
					WritableWorkbook wwb = Workbook.createWorkbook(new File(
							directory.getAbsolutePath() + "\\"
									+ text2.getText() + ".xls"));
					if (wwb != null) {
						// ����һ��������
						WritableSheet ws = wwb.createSheet("�����ѯ", 0);
						// ����cell��ʽ
						CellView cv = new CellView();
						cv.setAutosize(true);
						// ���ñ���
						String[] head = new String[] { "�߱�", "����ʱ��", "�ƻ�����",
								"ʵ�ʲ���", "��ʧʱ��", "�����ʧ�Ĺ�λ","����ʱ��" };

						// �������ݿ�����
						int row = 1;// �ӵڶ��п�ʼ����
						for (TPLine l : lineList) {
							// ��һ��������ʾ�У��ڶ�����ʾ��
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
							// ����һ��
							row++;
						}

						// ��ʼ��ӵ�һ�б��ⵥԪ��
						for (int j = 0; j < head.length; j++) {
							// ��һ��������ʾ�У��ڶ�����ʾ��
							Label labelC = new Label(j, 0, head[j]);
							// ����Ԫ����ӵ���������
							ws.addCell(labelC);
							ws.setColumnView(j, 15);
						}

						// ���ڴ���д�뵽�ļ���
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
					JOptionPane.showMessageDialog(null, "���ɱ���ɹ���\n�뵽���Ŀ¼�鿴��",
							"��ʾ", 1);
				} else {
					JOptionPane
							.showMessageDialog(null, "����ʱ�������������ԣ�", "����", 0);
				}
			} else {
				JOptionPane.showMessageDialog(null, "��ѡ��ʱ�䣡", "����", 0);
			}
		}
	}

	public static void main(String[] args) {
		new ExportEXL();
	}

	/**
	 * ���²����б�
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
				// �����б����
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