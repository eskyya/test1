package codebreaker;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

/**
 * @author ono
 */
public class CodeBreakerThird extends JFrame
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

	private GameEngine ge = new GameEngine();

	public CodeBreakerThird() {
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
		this.setTitle(ge.getTitle());
		this.setVisible(true);

		//ルールの表示
		JOptionPane.showMessageDialog(this,
				ge.getRule(),
				ge.getTitle(),
				JOptionPane.INFORMATION_MESSAGE);

		//GameEngineの初期化
		try {
			ge.setInput(new int[] { 1, 1, 1 });
		} catch (InputException e) {
			JOptionPane.showMessageDialog(this,
					e.getMessage(),
					ge.getTitle(),
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/*
	 * @see java.awt.event.ActionListener#actionPerformed
	 */
	public void actionPerformed(ActionEvent arg0) {
		System.out.println(arg0.getActionCommand());
		if (arg0.getActionCommand().equalsIgnoreCase("judge")) {
			//Judge
			boolean judge = ge.judge();

			//コンソールに表示させる。確認用
			int[] ans = ge.getAnswer();
			System.out.println("答え");
			for (int i = 0; i < ans.length; i++) {
				System.out.print(ans[i]);
			}
			System.out.println();
			System.out.println("入力");
			int[] input = ge.getInput();
			for (int i = 0; i < input.length; i++) {
				System.out.print(input[i]);
			}
			System.out.println();
			System.out.println("hit:" + ge.getHit()
					+ " blow:" + ge.getBlow());

			//結果をresultTableに表示させる
			Object[] row = new Object[5];
			for (int i = 0; i < 3; i++) {
				row[i] = Integer.valueOf(input[i]);
			}
			row[3] = Integer.valueOf(ge.getHit());
			row[4] = Integer.valueOf(ge.getBlow());
			dtm.addRow(row);
			if (judge) {
				//全問正解のときの処理
				int res = JOptionPane.showConfirmDialog(this,
						"まだ続けますか？",
						"おめでとう",
						JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					//resultTableの初期化
					int count = dtm.getRowCount();
					for (int i = 0; i < count; i++) {
						dtm.removeRow(0);
					}
					//GameEngineの初期化
					ge.makeAnswers();
					try {
						ge.setInput(new int[] { 1, 1, 1 });
					} catch (InputException e) {
						JOptionPane.showMessageDialog(this,
								e.getMessage(),
								ge.getTitle(),
								JOptionPane.ERROR_MESSAGE);
					}
					ge.judge();
					//GUIの初期化
					for (int i = 0; i < inputBox.length; i++) {
						inputBox[i].setSelectedIndex(0);
					}
				} else {
					System.exit(0);
				}

			}

		} else {//Judge以外なので入力部分の処理となる
			//入力部の特定
			int input = Integer.parseInt(arg0.getActionCommand());
			try {
				ge.inputAnswer(input, (String) inputBox[input]
						.getSelectedItem());
			} catch (InputException e) {
				JOptionPane.showMessageDialog(this,
						e.getMessage(),
						ge.getTitle(),
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * 申し訳程度のメインメソッド。<br>
	 * GUIの起動を行っているだけ。
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		new CodeBreakerThird();
	}

}
