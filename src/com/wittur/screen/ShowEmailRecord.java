package com.wittur.screen;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.lewei.dao.LineControlDao;
import com.lewei.model.Email;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShowEmailRecord extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private static ShowEmailRecord frame;
	private DefaultTableModel tableModel = null;
	private String[] head = { "邮箱地址", "备注信息", "接收线别" };
	private static Object[][] data = { { null, null, null },
			{ null, null, null }, { null, null, null } };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					frame = new ShowEmailRecord();
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
	public ShowEmailRecord() {
		this.setIconImage(new ImageIcon(getClass().getResource(
				"/img/Wittur_Logo.gif")).getImage());
		setTitle("\u90AE\u7BB1\u8BB0\u5F55");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(120, 120, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JLabel label = new JLabel(
				"\u5DF2\u5B58\u5728\u7684\u90AE\u7BB1\u8BB0\u5F55");
		label.setFont(new Font("宋体", Font.PLAIN, 16));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(label, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		tableModel = new DefaultTableModel(data, head);
		table = new JTable();
		table.setModel(tableModel);
		scrollPane.setViewportView(table);
		
		JButton button = new JButton("\u8FD4\u56DE");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		contentPane.add(button, BorderLayout.SOUTH);

		// 后台更新数据
		SwingWorker update = updateRecod();
		update.execute();
	}

	/**
	 * 后台更新记录数据。
	 * 
	 * @return
	 */
	SwingWorker updateRecod() {
		SwingWorker update = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				// 实例化DAO
				LineControlDao lcd = new LineControlDao();
				// 获取线别名称列表存入一个数组
				String[] lines = lcd.getLineList();
				// 实例化一个List
				List<Email> emailList = new ArrayList<>();
				emailList = lcd.getEmailRecord();
				// 新建一个数据模型
				data = new Object[emailList.size()][3];
				int i = 0;// 标志位
				for (Email email : emailList) {
					data[i][0] = email.getEmail();
					data[i][1] = email.getEmailRemark();
					int index = email.getLineID();
					if (index == -1) {// 负一表示所有线别
						data[i][2] = "所有线别";
					} else {
						// 根据产线ID获取相应的线别名称
						data[i][2] = lines[index];
					}
					i++;
				}
				if (i > 0) {// i>0 说明存在数据
					// 新数据存入table模型
					tableModel = new DefaultTableModel(data, head);
					// table设置新模型
					table.setModel(tableModel);
					// tableUI刷新
					table.updateUI();
				}

				return null;
			}
		};
		return update;
	}

}
