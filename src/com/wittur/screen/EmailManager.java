package com.wittur.screen;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import com.lewei.dao.LineControlDao;

public class EmailManager extends JFrame {

	private JPanel contentPane;
	private JTextField phone;
	private JComboBox lineName;
	private JTextField remark;
	private static EmailManager frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					frame = new EmailManager();
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
	public EmailManager() {
		setResizable(false);
		this.setIconImage(new ImageIcon(getClass().getResource(
				"/img/Wittur_Logo.gif")).getImage());
		setTitle("\u901A\u77E5\u90AE\u7BB1\u7BA1\u7406");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 442, 274);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(ControlScreen.class
				.getResource("/img/Wittur_Logo.gif")));
		label.setBounds(10, 10, 107, 100);
		contentPane.add(label);

		JLabel label_1 = new JLabel(
				"\u8BF7\u8F93\u5165\u90AE\u7BB1\u5730\u5740\uFF1A");
		label_1.setFont(new Font("宋体", Font.PLAIN, 15));
		label_1.setBounds(127, 34, 141, 21);
		contentPane.add(label_1);

		phone = new JTextField();
		phone.setBounds(127, 65, 141, 21);
		contentPane.add(phone);
		phone.setColumns(10);

		JButton button = new JButton("\u6DFB\u52A0\u6B64\u5730\u5740");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String email = phone.getText();
				String index = lineName.getSelectedItem().toString();
				int lineID = -1;
				if (!"全部".equals(index)) {
					lineID = lineName.getSelectedIndex() - 1;
				}
				String remarks = remark.getText();
				if (!email
						.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")) {
					JOptionPane.showMessageDialog(null, "输入不规范，请重新输入！", "错误",
							JOptionPane.ERROR_MESSAGE);
				} else {
					LineControlDao lcd = new LineControlDao();
					try {
						// 判断是否存在
						boolean has = lcd.hasEmail(email);
						if (!has) {// 不存在情况
							int r = lcd.addEmail(email, lineID,remarks);// 插入新数据
							if (r > 0) {
								JOptionPane.showMessageDialog(null, "插入新邮箱成功！",
										"提示", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(null, "添加新邮箱失败！",
										"错误", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "此邮箱已存在！",
									"错误", JOptionPane.ERROR_MESSAGE);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		button.setFont(new Font("宋体", Font.PLAIN, 14));
		button.setBounds(154, 162, 107, 23);
		contentPane.add(button);

		JButton button_1 = new JButton("\u5220\u9664\u6B64\u5730\u5740");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String s = phone.getText();
				if (!s.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")) {
					JOptionPane.showMessageDialog(null, "输入不规范，请重新输入！", "错误",
							JOptionPane.ERROR_MESSAGE);
				} else {
					LineControlDao lcd = new LineControlDao();
					try {
						boolean has = lcd.hasEmail(s);
						if (has) {
							int r = lcd.deleteEmail(s);
							if (r > 0) {
								JOptionPane.showMessageDialog(null, "删除邮箱成功！",
										"提示", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(null, "删除邮箱失败！",
										"错误", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "此邮箱不存在！",
									"错误", JOptionPane.ERROR_MESSAGE);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		button_1.setFont(new Font("宋体", Font.PLAIN, 14));
		button_1.setBounds(295, 162, 107, 23);
		contentPane.add(button_1);

		JButton btnNewButton = new JButton("退出");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(330, 195, 93, 30);
		contentPane.add(btnNewButton);

		lineName = new JComboBox();
		lineName.setModel(new DefaultComboBoxModel(
				new String[] { "\u65E0\u6570\u636E" }));
		lineName.setFont(new Font("宋体", Font.PLAIN, 14));
		lineName.setBounds(295, 65, 107, 21);
		contentPane.add(lineName);

		JLabel label_2 = new JLabel("\u7EBF\u522B\uFF1A");
		label_2.setFont(new Font("宋体", Font.PLAIN, 15));
		label_2.setBounds(295, 34, 100, 21);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("\u5907\u6CE8\u4FE1\u606F\uFF1A");
		label_3.setFont(new Font("宋体", Font.PLAIN, 15));
		label_3.setBounds(127, 96, 100, 21);
		contentPane.add(label_3);
		
		remark = new JTextField();
		remark.setColumns(10);
		remark.setBounds(127, 127, 141, 21);
		contentPane.add(remark);
		
		JButton record = new JButton("\u67E5\u770B\u8BB0\u5F55");
		record.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ShowEmailRecord ser = new ShowEmailRecord();
				ser.setVisible(true);
			}
		});
		record.setFont(new Font("宋体", Font.PLAIN, 14));
		record.setBounds(21, 162, 93, 24);
		contentPane.add(record);
		// 产线列表更新
		SwingWorker updateLine = updateLine();
		updateLine.execute();
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
