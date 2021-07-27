package codebreaker;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

/**
 */
public class CodeBreakerThirdPre extends JFrame
		implements ActionListener {
	private JPanel panel;

	private JPanel centorPanel;

	private JPanel southPanel;

	private JScrollPane sc;

	private DefaultTableModel dtm;

	private JTable resultTable;

	private JComboBox[] inputBox = new JComboBox[3];

	private String[] selector = { "1", "2", "3", "4", "5", "6" };

	private JButton judge;

	public CodeBreakerThirdPre() {
		//GUIの初期化
		//Panelの初期化
		panel = new JPanel(new BorderLayout());
		centorPanel = new JPanel();
		southPanel = new JPanel();
		//テーブル部。結果表示。今回はJTableを使用。
		dtm = new DefaultTableModel();
		dtm.addColumn("1");
		dtm.addColumn("2");
		dtm.addColumn("3");
		dtm.addColumn("HIT");
		dtm.addColumn("BLOW");

		resultTable = new JTable(dtm);
		resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		sc = new JScrollPane(resultTable,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		centorPanel.add(sc);

		//入力部分の製作
		for (int i = 0; i < 3; i++) {
			inputBox[i] = new JComboBox(selector);
			inputBox[i].setActionCommand("" + i);
			inputBox[i].addActionListener(this);
			southPanel.add(inputBox[i]);
		}
		judge = new JButton("JUDGE!!");
		judge.setActionCommand("JUDGE");
		judge.addActionListener(this);
		southPanel.add(judge);

		//テーブル部、入力部のセット。
		panel.add(centorPanel, BorderLayout.CENTER);
		panel.add(southPanel, BorderLayout.SOUTH);
		this.getContentPane().add(panel);

		//表示
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 400, 500);
		this.pack();
		this.setVisible(true);

	}

	/*
	 * @see java.awt.event.ActionListener#actionPerformed
	 */
	public void actionPerformed(ActionEvent arg0) {
	}

	/**
	 * 申し訳程度のメインメソッド。<br>
	 * GUIの起動を行っているだけ。
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		new CodeBreakerThirdPre();
	}
}