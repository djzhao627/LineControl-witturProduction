package com.wittur.screen;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.lewei.dao.LineControlDao;
import com.lewei.model.Product;
import com.lewei.model.TPLProd;
import com.lewei.model.TPLine;
import com.lewei.model.TPPlan;

/**
 * 计划输入界面。
 * 
 * @author djzhao
 * @time 2015年10月28日
 */
public class ConrolScreen extends JFrame {

	private static ConrolScreen frame = null;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField operator;
	private SimpleDateFormat df_Hm = new SimpleDateFormat("HH:mm");

	private static boolean rangerChange = false;
	private static boolean isRefreshProList = true;

	private DefaultTableModel tableModel = null;
	private TableSorter sorter = null;

	private String[] head = { "产品名称", "节拍", "数量", "生产优先级" };
	private static Object[][] data = { { "AA01C挂件 PL=1000 SF50", 94, 3, "n" },
			{ "S3300 01C挂件 PL=800 短扳机", 94, 9, "n" },
			{ "AA01C挂件 PL=800 SF50", 94, 15, "n" },
			{ "S3300 01C挂件 PL=900 短扳机", 94, null, "n" },
			{ "A-Prime挂件 PL900", 94, null, "n" }, { null, null, null, "n" },
			{ null, null, null, "n" } };

	private String[] mins = new String[] { "00", "01", "02", "03", "04", "05",
			"06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16",
			"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
			"28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38",
			"39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
			"50", "51", "52", "53", "54", "55", "56", "57", "58", "59" };
	private String[] hours = new String[] { "00", "01", "02", "03", "04", "05",
			"06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16",
			"17", "18", "19", "20", "21", "22", "23" };
	private JCheckBox updatePro;
	private JComboBox lineName;
	private JComboBox ranger;
	private JPanel extPanel;
	private JComboBox startWorkH;
	private JComboBox startWorkM;
	private JComboBox endWorkH;
	private JComboBox endWorkM;
	private JComboBox startRestH2;
	private JComboBox startRestM2;
	private JComboBox endRestH2;
	private JComboBox endRestM2;
	private JComboBox startRestH1;
	private JComboBox startRestM1;
	private JComboBox endRestH1;
	private JComboBox endRestM1;
	private JComboBox startRestH3;
	private JComboBox startRestM3;
	private JComboBox endRestH3;
	private JComboBox endRestM3;
	private JComboBox startRestH4;
	private JComboBox startRestM4;
	private JComboBox endRestH4;
	private JComboBox endRestM4;
	private JComboBox startRestH5;
	private JComboBox startRestM5;
	private JComboBox endRestH5;
	private JComboBox endRestM5;
	private JButton submit;
	private JTextField total;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new ConrolScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("unchecked")
	public ConrolScreen() {
		// 不可改变窗口大小
		setResizable(false);
		// 设置左上角的标题图标
		this.setIconImage(new ImageIcon(getClass().getResource(
				"/img/Wittur_Logo.gif")).getImage());
		// 设置标题
		setTitle("\u751F\u4EA7\u7535\u5B50\u770B\u677F");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置位置 和 大小
		setBounds(100, 20, 900, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("生产电子看板");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 25));
		lblNewLabel.setBounds(323, 10, 242, 35);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 55, 874, 2);
		contentPane.add(separator);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(ConrolScreen.class
				.getResource("/img/Wittur_Logo.gif")));
		label.setBounds(768, 68, 100, 90);
		contentPane.add(label);

		JLabel label_1 = new JLabel("\u5F53\u524D\u65F6\u95F4\uFF1A");
		label_1.setFont(new Font("宋体", Font.PLAIN, 15));
		label_1.setForeground(new Color(47, 79, 79));
		label_1.setBounds(622, 23, 82, 22);
		contentPane.add(label_1);

		JLabel time = new JLabel("");
		time.setFont(new Font("宋体", Font.PLAIN, 15));
		time.setForeground(new Color(47, 79, 79));
		time.setBounds(706, 23, 178, 22);
		this.setTimer(time);

		contentPane.add(time);

		JLabel label_2 = new JLabel("\u4EA7\u7EBF\uFF1A");
		label_2.setFont(new Font("宋体", Font.PLAIN, 17));
		label_2.setBounds(40, 92, 68, 30);
		contentPane.add(label_2);

		lineName = new JComboBox();
		lineName.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				resetTimeChange();
				isRefreshProList = true;
			}

		});
		lineName.setFont(new Font("宋体", Font.PLAIN, 17));
		lineName.setModel(new DefaultComboBoxModel(new String[] { "AA", "AP",
				"AMD", "DOP1", "DOP2" }));
		lineName.setBounds(118, 92, 90, 30);
		contentPane.add(lineName);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(239, 92, 2, 30);
		contentPane.add(separator_1);

		JLabel label_3 = new JLabel("\u73ED\u6B21\uFF1A");
		label_3.setFont(new Font("宋体", Font.PLAIN, 17));
		label_3.setBounds(267, 92, 68, 30);
		contentPane.add(label_3);

		ranger = new JComboBox();
		ranger.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// 默认上班时间设定
				if (ranger.getSelectedIndex() == 0) {
					startWorkH.setSelectedIndex(7);
					startWorkM.setSelectedIndex(0);
					endWorkH.setSelectedIndex(14);
					endWorkM.setSelectedIndex(59);
				} else if (ranger.getSelectedIndex() == 1) {
					startWorkH.setSelectedIndex(15);
					startWorkM.setSelectedIndex(0);
					endWorkH.setSelectedIndex(22);
					endWorkM.setSelectedIndex(59);
				} else if (ranger.getSelectedIndex() == 2) {
					startWorkH.setSelectedIndex(23);
					startWorkM.setSelectedIndex(0);
					endWorkH.setSelectedIndex(6);
					endWorkM.setSelectedIndex(59);
				}
				// 默认休息时间设定
				resetTimeChange();
			}
		});

		ranger.setModel(new DefaultComboBoxModel(new String[] { "\u65E9\u73ED",
				"\u4E2D\u73ED", "\u665A\u73ED" }));
		ranger.setFont(new Font("宋体", Font.PLAIN, 17));
		ranger.setBounds(345, 92, 90, 30);
		contentPane.add(ranger);

		JLabel label_4 = new JLabel("\u4E0A\u73ED\u65F6\u95F4\uFF1A");
		label_4.setFont(new Font("宋体", Font.PLAIN, 15));
		label_4.setBounds(267, 153, 90, 22);
		contentPane.add(label_4);

		startWorkH = new JComboBox();
		startWorkH.setModel(new DefaultComboBoxModel(hours));
		startWorkH.setSelectedIndex(7);
		startWorkH.setBounds(345, 153, 40, 21);
		contentPane.add(startWorkH);

		JLabel label_5 = new JLabel(":");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(391, 153, 12, 21);
		contentPane.add(label_5);

		startWorkM = new JComboBox();
		startWorkM.setModel(new DefaultComboBoxModel(mins));
		startWorkM.setSelectedIndex(0);
		startWorkM.setBounds(407, 153, 40, 21);
		contentPane.add(startWorkM);

		JLabel label_6 = new JLabel("-");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setFont(new Font("宋体", Font.BOLD, 12));
		label_6.setBounds(456, 153, 12, 21);
		contentPane.add(label_6);

		endWorkH = new JComboBox();
		endWorkH.setModel(new DefaultComboBoxModel(hours));
		endWorkH.setSelectedIndex(14);
		endWorkH.setBounds(476, 153, 40, 21);
		contentPane.add(endWorkH);

		endWorkM = new JComboBox();
		endWorkM.setModel(new DefaultComboBoxModel(mins));
		endWorkM.setSelectedIndex(59);
		endWorkM.setBounds(538, 153, 40, 21);
		contentPane.add(endWorkM);

		JLabel label_7 = new JLabel(":");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setBounds(522, 153, 12, 21);
		contentPane.add(label_7);

		JLabel label_8 = new JLabel("\u9009\u62E9\u4EA7\u54C1\uFF1A");
		label_8.setFont(new Font("宋体", Font.ITALIC, 13));
		label_8.setBounds(28, 160, 68, 15);
		contentPane.add(label_8);

		MyAbstractTableModel myModel = new MyAbstractTableModel();

		tableModel = new DefaultTableModel(data, head);
		sorter = new TableSorter(tableModel);

		table = new JTable(sorter);
		table.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				updateTotalNum();
			}

		});
		sorter.setTableHeader(table.getTableHeader());
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					Object[] d = { null, null, null, "n" };
					tableModel.addRow(d);
				}
			}
		});
		// table.setModel(tableModel);

		// table = new JTable(myModel); // 勾选功能开启
		// 设置可以拖动排序
		// table.setUI(new DragDropRowTableUI());
		// 设置表头不可以拖动
		table.getTableHeader().setReorderingAllowed(false);
		// 设置table内容居中
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		// 表格内容居中
		table.setDefaultRenderer(Object.class, dtcr);
		/** 勾选功能开启 */
		// 获得表格的表格列类
		// TableColumn tc = table.getColumnModel().getColumn(3);
		// 插入CheckBox
		// JCheckBox cb = new JCheckBox();
		// tc.setCellEditor(new DefaultCellEditor(cb));
		/** end勾选功能开启 */

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(28, 185, 840, 280);
		contentPane.add(scrollPane);

		submit = new JButton("提交");
		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 获取总量
				int sum = Integer.parseInt(total.getText());
				// 如果总量是0，则说明用户没有进行排序操作
				if (sum <= 0 && !updatePro.isSelected()) {// 未选择产品，也未勾选更新产品
					JOptionPane.showMessageDialog(null, "请选择需要生产的产品\n并排序",
							"警告！", JOptionPane.ERROR_MESSAGE);
				} else if (sum <= 0 && updatePro.isSelected()) {// 单独更新产品记录
					updateProRecord();
				} else {// 已选择产品
					try {
						// 实例化LineControlDao
						LineControlDao lcd = new LineControlDao();
						// 实例化计划表
						TPPlan tpplan = new TPPlan();
						// 设置总量
						tpplan.setTotalNum(sum);
						// 设置产线名
						tpplan.setTPLineName(lineName.getSelectedItem()
								.toString());
						// 设置班次
						tpplan.setRanger(ranger.getSelectedIndex());
						// 设置操作人
						tpplan.setCreatePeople(operator.getText().equals("") ? " "
								: operator.getText());
						/** 插入数据库 */
						int autoInckey = 0;
						// 自动返回新插入数据的主键
						autoInckey = lcd.insertToTpplan(tpplan);
						if (autoInckey > 0) {
							// 实例化产线
							TPLine tpline = new TPLine();
							// 产线计划ID
							tpline.setTPPlanID(autoInckey);
							// 设置产线名
							tpline.setTPLineName(lineName.getSelectedItem()
									.toString());
							// 设置班次
							tpline.setRanger(ranger.getSelectedIndex());
							// 休息时间1、吃饭时间、休息时间2
							String restTime = startRestH1.getSelectedItem()
									.toString()
									+ ":"
									+ startRestM1.getSelectedItem().toString()
									+ "-"
									+ endRestH1.getSelectedItem().toString()
									+ ":"
									+ endRestM1.getSelectedItem().toString()
									+ ";"
									+ startRestH2.getSelectedItem().toString()
									+ ":"
									+ startRestM2.getSelectedItem().toString()
									+ "-"
									+ endRestH2.getSelectedItem().toString()
									+ ":"
									+ endRestM2.getSelectedItem().toString()
									+ ";"
									+ startRestH3.getSelectedItem().toString()
									+ ":"
									+ startRestM3.getSelectedItem().toString()
									+ "-"
									+ endRestH3.getSelectedItem().toString()
									+ ":"
									+ endRestM3.getSelectedItem().toString();
							// 加班吃饭时间、加班休息时间
							String overtime1 = startRestH4.getSelectedItem()
									.toString()
									+ ":"
									+ startRestM4.getSelectedItem().toString();
							String overtime2 = endRestH4.getSelectedItem()
									.toString()
									+ ":"
									+ endRestM4.getSelectedItem().toString();
							String overtime3 = startRestH5.getSelectedItem()
									.toString()
									+ ":"
									+ startRestM5.getSelectedItem().toString();
							String overtime4 = endRestH5.getSelectedItem()
									.toString()
									+ ":"
									+ endRestM5.getSelectedItem().toString();
							// 比较第一次加班时间是否存在时间长
							long diff1 = df_Hm.parse(overtime2).getTime()
									- df_Hm.parse(overtime1).getTime();

							// 比较第二次加班时间是否存在时间长
							long diff2 = df_Hm.parse(overtime4).getTime()
									- df_Hm.parse(overtime3).getTime();
							System.out.println(diff1 + "---" + diff2);
							// 设置休息时间
							if (diff1 > 0 && diff2 > 0) {
								tpline.setRestTime(restTime + ";" + overtime1
										+ "-" + overtime2 + ";" + overtime3
										+ "-" + overtime4);
							} else if (diff1 <= 0 && diff2 > 0) {
								tpline.setRestTime(restTime + ";" + overtime3
										+ "-" + overtime4);
							} else if (diff1 > 0 && diff2 <= 0) {
								tpline.setRestTime(restTime + ";" + overtime1
										+ "-" + overtime2);
							} else {
								tpline.setRestTime(restTime);
							}
							// 上班时间
							String startTime = startWorkH.getSelectedItem()
									.toString()
									+ ":"
									+ startWorkM.getSelectedItem().toString();
							// 设置上班时间
							tpline.setStartTime(startTime);
							// 下班时间
							String endTime = endWorkH.getSelectedItem()
									.toString()
									+ ":"
									+ endWorkM.getSelectedItem().toString();
							// 设置下班时间
							tpline.setEndTime(endTime);
							/** 插入数据库 */
							autoInckey = lcd.insertToTpline(tpline);
							// 循环取出
							for (int i = 0; i < table.getRowCount(); i++) {
								if (!"n".equals(table.getValueAt(i, 3))) {
									TPLProd p = new TPLProd();
									p.setTPLineID(autoInckey);
									p.setProdName(table.getValueAt(i, 0)
											.toString());
									int takt = Integer.parseInt(table
											.getValueAt(i, 1).toString());
									p.setTakt(takt);
									int num = Integer.parseInt(table
											.getValueAt(i, 2).toString());
									p.setNum(num);
									/** 插入数据库 */
									int state = lcd.insertToTplprod(p);
									if (state < 0) {
										System.out.println("falied!");
									}
								} else {
									break;
								}
							}
						}

					} catch (Exception e1) {
						e1.printStackTrace();
					}

					if (updatePro.isSelected()) { // 勾选更新按钮的情况
						updateProRecord();
						JOptionPane.showMessageDialog(null, "计划提交成功！", "完成",
								JOptionPane.INFORMATION_MESSAGE);
					} else {// 不需要更新产品记录
						JOptionPane.showMessageDialog(null, "计划提交成功！", "完成",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
				System.exit(0);
			}

			/**
			 * 更新产品记录
			 */
			private void updateProRecord() {
				// 先清空当前产线的数据库内的数据
				LineControlDao lcd = new LineControlDao();
				// 删除标识 0为失败
				int flag = 0;
				try {
					flag = lcd.deleteProWithLineIndex(lineName
							.getSelectedIndex());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				// 删除成功则进行插入
				if (flag > 0) {
					// 获取产线的ID索引
					int lineID = lineName.getSelectedIndex();
					// 遍历table
					for (int i = 0; i < table.getRowCount(); i++) {
						String pName = table.getValueAt(i, 0).toString();
						if (pName != null && !"".equals(pName)) {
							Product p = new Product();
							p.setLineID(lineID);
							p.setProName(pName);
							int takt = Integer.parseInt(table.getValueAt(i, 1)
									.toString());
							p.setTakt(takt);
							int num = Integer.parseInt(table.getValueAt(i, 2)
									.toString());
							p.setNum(num);
							try {
								/** 插入数据库 */
								int state = lcd.insertToProduct(p);
								if (state <= 0) {
									System.out.println("更新产品记录失败");
								}
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						} else {
							break;
						}
					}
					JOptionPane.showMessageDialog(null, "产品记录更新成功！", "完成",
							JOptionPane.INFORMATION_MESSAGE);
				} else {// 删除失败
					JOptionPane.showMessageDialog(null, "更新产品记录失败！", "出错",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		submit.setFont(new Font("宋体", Font.ITALIC, 15));
		submit.setBounds(775, 572, 93, 23);
		contentPane.add(submit);

		operator = new JTextField();
		operator.setBounds(674, 573, 100, 21);
		contentPane.add(operator);
		operator.setColumns(10);

		JLabel label_10 = new JLabel("\u64CD\u4F5C\u4EBA\uFF1A");
		label_10.setBounds(622, 572, 78, 22);
		contentPane.add(label_10);

		updatePro = new JCheckBox("更新产品记录");
		updatePro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (updatePro.isSelected()) {
					updatePro.setForeground(Color.red);
				} else {
					updatePro.setForeground(new Color(192, 192, 192));
				}
			}
		});
		updatePro.setForeground(new Color(192, 192, 192));
		updatePro.setBounds(105, 156, 103, 23);
		contentPane.add(updatePro);

		startRestH2 = new JComboBox();
		startRestH2.setModel(new DefaultComboBoxModel(hours));
		startRestH2.setFont(new Font("宋体", Font.PLAIN, 12));
		startRestH2.setBounds(29, 498, 40, 21);
		contentPane.add(startRestH2);

		JLabel label_9 = new JLabel(":");
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setBounds(75, 498, 12, 21);
		contentPane.add(label_9);

		startRestM2 = new JComboBox();
		startRestM2.setModel(new DefaultComboBoxModel(mins));
		startRestM2.setFont(new Font("宋体", Font.PLAIN, 12));
		startRestM2.setBounds(91, 498, 40, 21);
		contentPane.add(startRestM2);

		JLabel label_11 = new JLabel("-");
		label_11.setHorizontalAlignment(SwingConstants.CENTER);
		label_11.setFont(new Font("宋体", Font.BOLD, 12));
		label_11.setBounds(140, 498, 12, 21);
		contentPane.add(label_11);

		endRestH2 = new JComboBox();
		endRestH2.setModel(new DefaultComboBoxModel(hours));
		endRestH2.setFont(new Font("宋体", Font.PLAIN, 12));
		endRestH2.setBounds(160, 498, 40, 21);
		contentPane.add(endRestH2);

		JLabel label_12 = new JLabel(":");
		label_12.setHorizontalAlignment(SwingConstants.CENTER);
		label_12.setBounds(206, 498, 12, 21);
		contentPane.add(label_12);

		endRestM2 = new JComboBox();
		endRestM2.setModel(new DefaultComboBoxModel(mins));
		endRestM2.setFont(new Font("宋体", Font.PLAIN, 12));
		endRestM2.setBounds(222, 498, 40, 21);
		contentPane.add(endRestM2);

		startRestH1 = new JComboBox();
		startRestH1.setModel(new DefaultComboBoxModel(hours));
		startRestH1.setFont(new Font("宋体", Font.PLAIN, 12));
		startRestH1.setBounds(333, 498, 40, 21);
		contentPane.add(startRestH1);

		JLabel label_13 = new JLabel(":");
		label_13.setHorizontalAlignment(SwingConstants.CENTER);
		label_13.setBounds(379, 498, 12, 21);
		contentPane.add(label_13);

		startRestM1 = new JComboBox();
		startRestM1.setModel(new DefaultComboBoxModel(mins));
		startRestM1.setFont(new Font("宋体", Font.PLAIN, 12));
		startRestM1.setBounds(395, 498, 40, 21);
		contentPane.add(startRestM1);

		JLabel label_14 = new JLabel("-");
		label_14.setHorizontalAlignment(SwingConstants.CENTER);
		label_14.setFont(new Font("宋体", Font.BOLD, 12));
		label_14.setBounds(444, 498, 12, 21);
		contentPane.add(label_14);

		endRestH1 = new JComboBox();
		endRestH1.setModel(new DefaultComboBoxModel(hours));
		endRestH1.setFont(new Font("宋体", Font.PLAIN, 12));
		endRestH1.setBounds(464, 498, 40, 21);
		contentPane.add(endRestH1);

		JLabel label_15 = new JLabel(":");
		label_15.setHorizontalAlignment(SwingConstants.CENTER);
		label_15.setBounds(510, 498, 12, 21);
		contentPane.add(label_15);

		endRestM1 = new JComboBox();
		endRestM1.setModel(new DefaultComboBoxModel(mins));
		endRestM1.setFont(new Font("宋体", Font.PLAIN, 12));
		endRestM1.setBounds(526, 498, 40, 21);
		contentPane.add(endRestM1);

		startRestH3 = new JComboBox();
		startRestH3.setModel(new DefaultComboBoxModel(hours));
		startRestH3.setFont(new Font("宋体", Font.PLAIN, 12));
		startRestH3.setBounds(626, 498, 40, 21);
		contentPane.add(startRestH3);

		JLabel label_16 = new JLabel(":");
		label_16.setHorizontalAlignment(SwingConstants.CENTER);
		label_16.setBounds(672, 498, 12, 21);
		contentPane.add(label_16);

		startRestM3 = new JComboBox();
		startRestM3.setModel(new DefaultComboBoxModel(mins));
		startRestM3.setFont(new Font("宋体", Font.PLAIN, 12));
		startRestM3.setBounds(688, 498, 40, 21);
		contentPane.add(startRestM3);

		JLabel label_17 = new JLabel("-");
		label_17.setHorizontalAlignment(SwingConstants.CENTER);
		label_17.setFont(new Font("宋体", Font.BOLD, 12));
		label_17.setBounds(737, 498, 12, 21);
		contentPane.add(label_17);

		endRestH3 = new JComboBox();
		endRestH3.setModel(new DefaultComboBoxModel(hours));
		endRestH3.setFont(new Font("宋体", Font.PLAIN, 12));
		endRestH3.setBounds(757, 498, 40, 21);
		contentPane.add(endRestH3);

		JLabel label_18 = new JLabel(":");
		label_18.setHorizontalAlignment(SwingConstants.CENTER);
		label_18.setBounds(803, 498, 12, 21);
		contentPane.add(label_18);

		endRestM3 = new JComboBox();
		endRestM3.setModel(new DefaultComboBoxModel(mins));
		endRestM3.setFont(new Font("宋体", Font.PLAIN, 12));
		endRestM3.setBounds(819, 498, 40, 21);
		contentPane.add(endRestM3);

		JLabel label_19 = new JLabel("\u5403\u996D\u65F6\u95F4\uFF1A");
		label_19.setFont(new Font("宋体", Font.PLAIN, 12));
		label_19.setBounds(29, 474, 77, 15);
		contentPane.add(label_19);

		JLabel label_20 = new JLabel("\u4F11\u606F\u65F6\u95F41\uFF1A");
		label_20.setFont(new Font("宋体", Font.PLAIN, 12));
		label_20.setBounds(333, 473, 82, 15);
		contentPane.add(label_20);

		JLabel label_21 = new JLabel("\u4F11\u606F\u65F6\u95F42\uFF1A");
		label_21.setFont(new Font("宋体", Font.PLAIN, 12));
		label_21.setBounds(627, 474, 82, 15);
		contentPane.add(label_21);

		extPanel = new JPanel();
		extPanel.setBounds(10, 539, 555, 60);
		contentPane.add(extPanel);
		extPanel.setLayout(null);

		JLabel label_28 = new JLabel(
				"\u52A0\u73ED\u5403\u996D\u65F6\u95F4\uFF1A");
		label_28.setFont(new Font("宋体", Font.PLAIN, 12));
		label_28.setBounds(20, 6, 106, 15);
		extPanel.add(label_28);

		startRestH4 = new JComboBox();
		startRestH4.setModel(new DefaultComboBoxModel(hours));
		startRestH4.setFont(new Font("宋体", Font.PLAIN, 12));
		startRestH4.setBounds(20, 31, 40, 21);
		extPanel.add(startRestH4);

		JLabel label_22 = new JLabel(":");
		label_22.setHorizontalAlignment(SwingConstants.CENTER);
		label_22.setBounds(66, 31, 12, 21);
		extPanel.add(label_22);

		startRestM4 = new JComboBox();
		startRestM4.setModel(new DefaultComboBoxModel(mins));
		startRestM4.setFont(new Font("宋体", Font.PLAIN, 12));
		startRestM4.setBounds(82, 31, 40, 21);
		extPanel.add(startRestM4);

		JLabel label_23 = new JLabel("-");
		label_23.setHorizontalAlignment(SwingConstants.CENTER);
		label_23.setFont(new Font("宋体", Font.BOLD, 12));
		label_23.setBounds(131, 31, 12, 21);
		extPanel.add(label_23);

		endRestH4 = new JComboBox();
		endRestH4.setModel(new DefaultComboBoxModel(hours));
		endRestH4.setFont(new Font("宋体", Font.PLAIN, 12));
		endRestH4.setBounds(151, 31, 40, 21);
		extPanel.add(endRestH4);

		JLabel label_24 = new JLabel(":");
		label_24.setHorizontalAlignment(SwingConstants.CENTER);
		label_24.setBounds(197, 31, 12, 21);
		extPanel.add(label_24);

		endRestM4 = new JComboBox();
		endRestM4.setModel(new DefaultComboBoxModel(mins));
		endRestM4.setFont(new Font("宋体", Font.PLAIN, 12));
		endRestM4.setBounds(213, 31, 40, 21);
		extPanel.add(endRestM4);

		JLabel label_29 = new JLabel(
				"\u52A0\u73ED\u4F11\u606F\u65F6\u95F4\uFF1A");
		label_29.setFont(new Font("宋体", Font.PLAIN, 12));
		label_29.setBounds(322, 6, 112, 15);
		extPanel.add(label_29);

		startRestH5 = new JComboBox();
		startRestH5.setModel(new DefaultComboBoxModel(hours));
		startRestH5.setFont(new Font("宋体", Font.PLAIN, 12));
		startRestH5.setBounds(322, 31, 40, 21);
		extPanel.add(startRestH5);

		JLabel label_25 = new JLabel(":");
		label_25.setHorizontalAlignment(SwingConstants.CENTER);
		label_25.setBounds(368, 31, 12, 21);
		extPanel.add(label_25);

		startRestM5 = new JComboBox();
		startRestM5.setModel(new DefaultComboBoxModel(mins));
		startRestM5.setFont(new Font("宋体", Font.PLAIN, 12));
		startRestM5.setBounds(384, 31, 40, 21);
		extPanel.add(startRestM5);

		JLabel label_26 = new JLabel("-");
		label_26.setHorizontalAlignment(SwingConstants.CENTER);
		label_26.setFont(new Font("宋体", Font.BOLD, 12));
		label_26.setBounds(433, 31, 12, 21);
		extPanel.add(label_26);

		endRestH5 = new JComboBox();
		endRestH5.setModel(new DefaultComboBoxModel(hours));
		endRestH5.setFont(new Font("宋体", Font.PLAIN, 12));
		endRestH5.setBounds(453, 31, 40, 21);
		extPanel.add(endRestH5);

		JLabel label_27 = new JLabel(":");
		label_27.setHorizontalAlignment(SwingConstants.CENTER);
		label_27.setBounds(499, 31, 12, 21);
		extPanel.add(label_27);

		endRestM5 = new JComboBox();
		endRestM5.setModel(new DefaultComboBoxModel(mins));
		endRestM5.setFont(new Font("宋体", Font.PLAIN, 12));
		endRestM5.setBounds(515, 31, 40, 21);
		extPanel.add(endRestM5);

		JLabel label_30 = new JLabel("\u603B\u91CF\uFF1A");
		label_30.setFont(new Font("宋体", Font.PLAIN, 17));
		label_30.setBounds(484, 92, 54, 30);
		contentPane.add(label_30);

		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(466, 92, 2, 30);
		contentPane.add(separator_2);

		total = new JTextField();
		total.setHorizontalAlignment(SwingConstants.CENTER);
		total.setText("0");
		total.setEditable(false);
		total.setFont(new Font("宋体", Font.PLAIN, 17));
		total.setBounds(563, 92, 78, 29);
		contentPane.add(total);
		total.setColumns(10);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(28, 609, 840, 8);
		contentPane.add(separator_3);

		JButton button = new JButton("\u77ED\u4FE1\u6A21\u5757");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MesLogin mes = new MesLogin();
				mes.setVisible(true);
				dispose();
			}
		});
		button.setBounds(124, 627, 93, 23);
		contentPane.add(button);

		JButton button_1 = new JButton("\u90AE\u4EF6\u6A21\u5757");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				EmailLogin email = new EmailLogin();
				email.setVisible(true);
				dispose();
			}
		});
		button_1.setBounds(385, 627, 93, 23);
		contentPane.add(button_1);

		JButton button_2 = new JButton("\u5BFC\u51FA\u62A5\u8868");
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ExportLogin ex = new ExportLogin();
				ex.setVisible(true);
				dispose();
			}
		});
		button_2.setBounds(666, 627, 93, 23);
		contentPane.add(button_2);

		JLabel label_31 = new JLabel(
				"\u6CE8\uFF1A\u5148\u6392\u5E8F\uFF0C\u518D\u63D0\u4EA4");
		label_31.setFont(new Font("宋体", Font.PLAIN, 12));
		label_31.setForeground(new Color(220, 20, 60));
		label_31.setHorizontalAlignment(SwingConstants.TRAILING);
		label_31.setBounds(718, 547, 150, 15);
		contentPane.add(label_31);

		resetTimeChange();

		updateTotalNum();

		SwingWorker s = updatePrioduct();
		s.execute();

	}

	/**
	 * 更新总量的显示
	 */
	private void updateTotalNum() {
		int sum = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			String s = table.getValueAt(i, 2) == null ? "" : table.getValueAt(
					i, 2).toString();
			String s2 = table.getValueAt(i, 3).toString();
			if (!s2.equals("n") && !"".equals(s)) {
				try {
					sum += Integer.parseInt(s);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "节拍必须输入数字",
							"ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		total.setText(sum + "");
	}

	/**
	 * 改变休息时间默认值
	 */
	private void resetTimeChange() {
		// 获取产线名当前选中索引
		int lineIndex = lineName.getSelectedIndex();
		// 获取当前班次索引
		int rangerIndex = ranger.getSelectedIndex();
		switch (lineIndex) {
		case 0: // case 0 - 2 AA、AP、AMD 时间相同
		case 1:
		case 2:
			if (rangerIndex == 0) {// 早班
				// 休息时间1
				startRestH1.setSelectedIndex(9);
				startRestM1.setSelectedIndex(0);
				endRestH1.setSelectedIndex(9);
				endRestM1.setSelectedIndex(10);

				// 吃饭时间
				startRestH2.setSelectedIndex(11);
				startRestM2.setSelectedIndex(20);
				endRestH2.setSelectedIndex(11);
				endRestM2.setSelectedIndex(50);

				// 休息时间2
				startRestH3.setSelectedIndex(13);
				startRestM3.setSelectedIndex(0);
				endRestH3.setSelectedIndex(13);
				endRestM3.setSelectedIndex(10);
			} else if (rangerIndex == 1) {// 中班
				// 休息时间1
				startRestH1.setSelectedIndex(20);
				startRestM1.setSelectedIndex(0);
				endRestH1.setSelectedIndex(20);
				endRestM1.setSelectedIndex(10);

				// 吃饭时间
				startRestH2.setSelectedIndex(17);
				startRestM2.setSelectedIndex(20);
				endRestH2.setSelectedIndex(17);
				endRestM2.setSelectedIndex(50);

				// 休息时间2
				startRestH3.setSelectedIndex(22);
				startRestM3.setSelectedIndex(0);
				endRestH3.setSelectedIndex(22);
				endRestM3.setSelectedIndex(10);
			} else if (rangerIndex == 2) {// 晚班
				// 休息时间1
				startRestH1.setSelectedIndex(1);
				startRestM1.setSelectedIndex(0);
				endRestH1.setSelectedIndex(1);
				endRestM1.setSelectedIndex(10);

				// 吃饭时间
				startRestH2.setSelectedIndex(2);
				startRestM2.setSelectedIndex(20);
				endRestH2.setSelectedIndex(2);
				endRestM2.setSelectedIndex(50);

				// 休息时间2
				startRestH3.setSelectedIndex(5);
				startRestM3.setSelectedIndex(0);
				endRestH3.setSelectedIndex(5);
				endRestM3.setSelectedIndex(10);
			}
			break;
		case 3: // case 3 - 4 是DOP1、DOP2 时间相同
		case 4:
			if (rangerIndex == 0) {// 早班
				// 休息时间1
				startRestH1.setSelectedIndex(9);
				startRestM1.setSelectedIndex(0);
				endRestH1.setSelectedIndex(9);
				endRestM1.setSelectedIndex(10);

				// 吃饭时间
				startRestH2.setSelectedIndex(11);
				startRestM2.setSelectedIndex(10);
				endRestH2.setSelectedIndex(11);
				endRestM2.setSelectedIndex(40);

				// 休息时间2
				startRestH3.setSelectedIndex(13);
				startRestM3.setSelectedIndex(0);
				endRestH3.setSelectedIndex(13);
				endRestM3.setSelectedIndex(10);
			} else if (rangerIndex == 1) {// 中班
				// 休息时间1
				startRestH1.setSelectedIndex(20);
				startRestM1.setSelectedIndex(0);
				endRestH1.setSelectedIndex(20);
				endRestM1.setSelectedIndex(10);

				// 吃饭时间
				startRestH2.setSelectedIndex(17);
				startRestM2.setSelectedIndex(10);
				endRestH2.setSelectedIndex(17);
				endRestM2.setSelectedIndex(40);

				// 休息时间2
				startRestH3.setSelectedIndex(22);
				startRestM3.setSelectedIndex(0);
				endRestH3.setSelectedIndex(22);
				endRestM3.setSelectedIndex(10);
			} else if (rangerIndex == 2) {// 晚班
				// 休息时间1
				startRestH1.setSelectedIndex(1);
				startRestM1.setSelectedIndex(0);
				endRestH1.setSelectedIndex(1);
				endRestM1.setSelectedIndex(10);

				// 吃饭时间
				startRestH2.setSelectedIndex(2);
				startRestM2.setSelectedIndex(10);
				endRestH2.setSelectedIndex(2);
				endRestM2.setSelectedIndex(40);

				// 休息时间2
				startRestH3.setSelectedIndex(5);
				startRestM3.setSelectedIndex(0);
				endRestH3.setSelectedIndex(5);
				endRestM3.setSelectedIndex(10);
			}
			break;
		default:
			break;
		}
	}

	// 设置Timer 1000ms实现一次动作 实际是一个线程
	private void setTimer(JLabel time) {
		final JLabel varTime = time;
		Timer timeAction = new Timer(1000, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				long timemillis = System.currentTimeMillis();
				// 转换日期显示格式
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				varTime.setText(df.format(new Date(timemillis)));
			}
		});
		timeAction.start();
	}

	@SuppressWarnings("serial")
	class MyAbstractTableModel extends AbstractTableModel {

		// 定义表头数据
		String[] head = { "产品名称", "Takt", "数量", "选择" };

		// 定义表格每一列的数据类型
		Class[] typeArray = { String.class, Integer.class, Integer.class,
				Boolean.class };

		Object[][] data = {
				{ "AA01C挂件 PL=1000 SF50", 94, 3, new Boolean(false) },
				{ "S3300 01C挂件 PL=800 短扳机", 94, 9, new Boolean(false) },
				{ "AA01C挂件 PL=800 SF50", 94, 15, new Boolean(false) },
				{ "S3300 01C挂件 PL=900 短扳机", 94, null, new Boolean(false) },
				{ "A-Prime挂件 PL900", 94, null, new Boolean(false) },
				{ null, null, null, new Boolean(false) },
				{ null, null, null, new Boolean(false) } };

		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public int getColumnCount() {
			return head.length;
		}

		// 获得表格的列名称
		@Override
		public String getColumnName(int column) {
			return head[column];
		}

		// 获得表格的单元格的数据
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data[rowIndex][columnIndex];
		}

		// 使表格具有可编辑性
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return true;
		}

		// 替换单元格的值
		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			data[rowIndex][columnIndex] = aValue;
			fireTableCellUpdated(rowIndex, columnIndex);
		}

		// 实现了如果是boolean自动转成JCheckbox
		/*
		 * 需要自己的celleditor这么麻烦吧。jtable自动支持Jcheckbox，
		 * 只要覆盖tablemodel的getColumnClass返回一个boolean的class，
		 * jtable会自动画一个Jcheckbox给你， 你的value是true还是false直接读table里那个cell的值就可以
		 */
		public Class getColumnClass(int columnIndex) {
			return typeArray[columnIndex];// 返回每一列的数据类型
		}
	}

	/**
	 * 根据产线变化来更新产品列表<br>
	 * 先执行一次产线更新
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private SwingWorker updatePrioduct() {
		
		SwingWorker getPro = new SwingWorker<Void, Integer>() {

			@SuppressWarnings("unchecked")
			@Override
			protected Void doInBackground() throws Exception {

				LineControlDao lcd = new LineControlDao();
				// 产线列表更新
				String[] lineList = lcd.getLineList();
				lineName.setModel(new DefaultComboBoxModel(lineList));

				// 产品列表更新
				while (true) {
					if (isRefreshProList) {
						isRefreshProList = false;
						List<Product> proList = new ArrayList<>();
						proList = lcd.getProWithLineIndex(lineName
								.getSelectedIndex());
						data = new Object[proList.size()][4];
						int i = 0;
						for (Product p : proList) {
							data[i][0] = p.getProName();
							data[i][1] = p.getTakt();
							data[i][2] = p.getNum();
							data[i][3] = "n";
							i++;
						}
						if (i > 0) {
							tableModel = new DefaultTableModel(data, head);
							sorter.setTableModel(tableModel);
							table.updateUI();
						}
					}
					Thread.sleep(500);
				}
			}
		};
		return getPro;
	}
}
