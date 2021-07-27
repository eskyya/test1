package codebreaker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 */
public class CodeBreakerSecond {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameEngine ge = new GameEngine();
		/*
		 *  countは何回目のチャレンジかを数えている。
		 */
		int count = 0;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(ge.getTitle());
		System.out.println(ge.getRule());

		//ランダムな答えを作成する
		ge.makeAnswers();

		while (true) {
			count++;
			System.out.println("*** " + count + "回目 ***");
			//インプット
			for (int i = 0; i < ge.getNumberOfAnswers(); i++) {
				System.out.print((i + 1) + "個目 : ");
				try {
					ge.inputAnswer(i, br.readLine());
				} catch (InputException e) {
					System.err.println(e.getMessage());
					i--;
				} catch (IOException e) {
					System.err.println("もう一度入力してください");
					i--;
				}
			}
			//答え判断
			boolean end = ge.judge();
			//終了判断
			System.out.println("ヒット" + ge.getHit()
					+ " ブロー" + ge.getBlow());
			if (end) {
				System.out.println("おめでとー");
				break;
			} else {
				System.out.println();
			}
		}
	}
}