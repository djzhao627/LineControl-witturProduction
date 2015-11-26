package com.wittur.screen;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ExportLogin extends JFrame {

	private JPanel contentPane;
	private JTextField user;
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExportLogin frame = new ExportLogin();
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
	public ExportLogin() {
		setResizable(false);
		this.setIconImage(new ImageIcon(getClass().getResource(
				"/img/Wittur_Logo.gif")).getImage());
		setTitle("\u8EAB\u4EFD\u9A8C\u8BC1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 391, 244);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(ConrolScreen.class
				.getResource("/img/Wittur_Logo.gif")));
		label.setBounds(20, 37, 100, 90);
		contentPane.add(label);

		JLabel label_1 = new JLabel("\u7528\u6237\uFF1A");
		label_1.setBounds(175, 37, 54, 15);
		contentPane.add(label_1);

		user = new JTextField();
		user.setBounds(175, 62, 127, 21);
		contentPane.add(user);
		user.setColumns(10);

		JLabel label_2 = new JLabel("\u5BC6\u7801\uFF1A");
		label_2.setBounds(175, 93, 54, 15);
		contentPane.add(label_2);

		password = new JPasswordField();
		password.setColumns(10);
		password.setBounds(175, 118, 127, 21);
		contentPane.add(password);

		JButton button = new JButton("\u767B\u5F55");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if ("comm".equals(user.getText())
						&& "123".equals(password.getText())) {
					new ExportEXL(); // 打开导出界面
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "用户名或者密码错误！", "错误",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		button.setBounds(272, 172, 93, 23);
		contentPane.add(button);
	}
}
