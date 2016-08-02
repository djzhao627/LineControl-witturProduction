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

public class MesManager extends JFrame {

	private JPanel contentPane;
	private JTextField phone;
	private JComboBox lineName;
	private JTextField remark;
	private static MesManager frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					frame = new MesManager();
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
	public MesManager() {
		setTitle("\u77ED\u4FE1\u7BA1\u7406");
		setResizable(false);
		this.setIconImage(new ImageIcon(getClass().getResource(
				"/img/Wittur_Logo.gif")).getImage());
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
				"\u8BF7\u8F93\u5165\u624B\u673A\u53F7\u7801\uFF1A");
		label_1.setFont(new Font("����", Font.PLAIN, 15));
		label_1.setBounds(127, 34, 141, 21);
		contentPane.add(label_1);

		phone = new JTextField();
		phone.setBounds(127, 65, 141, 21);
		contentPane.add(phone);
		phone.setColumns(10);

		JButton button = new JButton("��Ӵ˺���");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String phoneNumber = phone.getText();
				String index = lineName.getSelectedItem().toString();
				String remarks = remark.getText();
				int lineID = -1;
				if (!"ȫ��".equals(index)) {
					lineID = lineName.getSelectedIndex() - 1;
				}
				if (!phoneNumber.matches("[0-9]{11}")) {
					// if(!s.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")){
					JOptionPane.showMessageDialog(null, "���벻�淶�����������룡", "����",
							JOptionPane.ERROR_MESSAGE);
				} else {
					LineControlDao lcd = new LineControlDao();
					try {
						// �ж��Ƿ��Ѵ���
						boolean has = lcd.hasNumber(phoneNumber);
						if (!has) {// �����ڵ����
							int r = lcd.addPhoneNumber(phoneNumber, lineID, remarks);// ����������
							if (r > 0) {
								JOptionPane.showMessageDialog(null, "�����º���ɹ���",
										"��ʾ", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(null, "����º���ʧ�ܣ�",
										"����", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "�˺����Ѵ��ڣ�",
									"����", JOptionPane.ERROR_MESSAGE);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		button.setFont(new Font("����", Font.PLAIN, 14));
		button.setBounds(155, 162, 107, 23);
		contentPane.add(button);

		JButton button_1 = new JButton("ɾ���˺���");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String s = phone.getText();
				if (!s.matches("[0-9]{11}")) {
					// if(!s.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")){
					JOptionPane.showMessageDialog(null, "���벻�淶�����������룡", "����",
							JOptionPane.ERROR_MESSAGE);
				} else {
					LineControlDao lcd = new LineControlDao();
					try {
						boolean has = lcd.hasNumber(s);
						if (has) {
							int r = lcd.deletePhoneNumber(s);
							if (r > 0) {
								JOptionPane.showMessageDialog(null, "ɾ����ɹ���",
										"��ʾ", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(null, "ɾ������ʧ�ܣ�",
										"����", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "�˺��벻���ڣ�",
									"����", JOptionPane.ERROR_MESSAGE);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		button_1.setFont(new Font("����", Font.PLAIN, 14));
		button_1.setBounds(298, 162, 107, 23);
		contentPane.add(button_1);

		JButton btnNewButton = new JButton("�˳�");
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
		btnNewButton.setBounds(323, 195, 93, 30);
		contentPane.add(btnNewButton);

		lineName = new JComboBox();
		lineName.setModel(new DefaultComboBoxModel(
				new String[] { "\u65E0\u6570\u636E" }));
		lineName.setFont(new Font("����", Font.PLAIN, 14));
		lineName.setBounds(305, 65, 100, 21);
		contentPane.add(lineName);

		JLabel label_2 = new JLabel("\u7EBF\u522B\uFF1A");
		label_2.setFont(new Font("����", Font.PLAIN, 15));
		label_2.setBounds(305, 34, 100, 21);
		contentPane.add(label_2);
		
		remark = new JTextField();
		remark.setColumns(10);
		remark.setBounds(127, 131, 141, 21);
		contentPane.add(remark);
		
		JLabel label_3 = new JLabel("\u5907\u6CE8\u4FE1\u606F\uFF1A");
		label_3.setFont(new Font("����", Font.PLAIN, 15));
		label_3.setBounds(127, 100, 100, 21);
		contentPane.add(label_3);
		
		JButton button_2 = new JButton("\u67E5\u770B\u8BB0\u5F55");
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				ShowMesRecord smr = new ShowMesRecord();
				smr.setVisible(true);
				
			}
		});
		button_2.setFont(new Font("����", Font.PLAIN, 14));
		button_2.setBounds(24, 162, 93, 24);
		contentPane.add(button_2);

		// �����б����
		SwingWorker updateLine = updateLine();
		updateLine.execute();
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
