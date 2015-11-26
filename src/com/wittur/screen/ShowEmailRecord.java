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
	private String[] head = { "�����ַ", "��ע��Ϣ", "�����߱�" };
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
		label.setFont(new Font("����", Font.PLAIN, 16));
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

		// ��̨��������
		SwingWorker update = updateRecod();
		update.execute();
	}

	/**
	 * ��̨���¼�¼���ݡ�
	 * 
	 * @return
	 */
	SwingWorker updateRecod() {
		SwingWorker update = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				// ʵ����DAO
				LineControlDao lcd = new LineControlDao();
				// ��ȡ�߱������б����һ������
				String[] lines = lcd.getLineList();
				// ʵ����һ��List
				List<Email> emailList = new ArrayList<>();
				emailList = lcd.getEmailRecord();
				// �½�һ������ģ��
				data = new Object[emailList.size()][3];
				int i = 0;// ��־λ
				for (Email email : emailList) {
					data[i][0] = email.getEmail();
					data[i][1] = email.getEmailRemark();
					int index = email.getLineID();
					if (index == -1) {// ��һ��ʾ�����߱�
						data[i][2] = "�����߱�";
					} else {
						// ���ݲ���ID��ȡ��Ӧ���߱�����
						data[i][2] = lines[index];
					}
					i++;
				}
				if (i > 0) {// i>0 ˵����������
					// �����ݴ���tableģ��
					tableModel = new DefaultTableModel(data, head);
					// table������ģ��
					table.setModel(tableModel);
					// tableUIˢ��
					table.updateUI();
				}

				return null;
			}
		};
		return update;
	}

}
