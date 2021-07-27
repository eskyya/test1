package codebreaker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * CodeBreakerZero<br>
 * シンプルにmainメソッドだけでコードブレイカーを作成します。
 *
 */
public class CodeBreakerFirst {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//変数の初期化。
		/*必要な変数
		 * タイトル、ルール文、答えの格納される配列、
		 * 入力された数字の格納される配列。
		 * ヒット数、ブロー数、チャレンジの回数
		 */
		String title = "*** CodeBreaker ***";
		String rule = "隠された3つの数字をあてます。\n"
				+ "1つの数字は1から6の間です。\n"
				+ "3つの答えの中に同じ数字はありません。\n"
				+ "入力した数字の、"
				+ "位置と数字が当たってたらヒット、\n"
				+ "数字だけあってたらブローとカウントします。\n"
				+ "全部当てたら(3つともヒットになったら)"
				+ "終了です\n\n";
		int[] answer = new int[3];
		int[] input = new int[3];

		int hit = 0;
		int blow = 0;
		int count = 0;

		//タイトルとルールの表示
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(title);
		System.out.println(rule);

		//ランダムな答えを作成。
		//ただし、仕様通り、同じ数字がないようにする。
		for (int i = 0; i < answer.length; i++) { // iは答えの数（配列の要素数）012の3個。
			//自分より前の要素にかぶるやつがないか確かめる。
			//あったらもう1回random
			boolean flag = false;
			answer[i] = (int) (Math.random() * 6 + 1); // 答えiにランダムな数字を入れていく。

			// 答えの配列[0][1][2]について、iとjを使って、同じ数字ではないように値をセットする。
			// i → 0 1 2, j → 1, 2と1
			do {
				flag = false; // flagはfalseに戻して、中を通らせる。
				for (int j = i - 1; j >= 0; j--) { // 1つ目の先行するループiと、2つめの1つ遅れるjの中身が同じか比較。
													// j--の意味は、jが1の時は次に0とし、MAXでj=2～0の2回ループという意味。
													// jは-1, 0, 1 で、-1の場合は条件からループ無し。0なら1回、1なら2回ループ。
													// 2回ループとは、jが1　→　1減らしてjが0の回。[j0][j1] [i0][i1][i2]
					if (answer[i] == answer[j]) { // 答えiと答えjの中身が一致したら、true。重複してるから再度Random。
						flag = true;
						answer[i] = (int) (Math.random() * 6 + 1); // 答えiにランダム数を入力する。
					}
				}
			} while (flag == true);
		}

		// ゲーム部
		// 常にtrueでループする。答え配列と同じ数、中でループして入力をinput[i]に保存する。
		while (true) {
			count++;
			System.out.println("*** " + count + "回目 ***");
			// インプット
			for (int i = 0; i < answer.length; i++) {
				System.out.println((i + 1) + "個目：");

				try {
					input[i] = Integer.parseInt(br.readLine());
				} catch (NumberFormatException e) {
					System.out.println("数値を入力してください");
					i--;
				} catch (IOException e) {
					System.out.println("もう一度入力して下さい");
					i--;
				}
			}
			// 答え判断
			// 二重ループで、iとjの値、回数と、配列の中身が同じなら、hit、中身だけ同じで順番が違うならブロー。
			hit = 0;
			blow = 0;
			for (int i = 0; i < answer.length; i++) {
				for (int j = 0; j < answer.length; j++) {
					if (i == j && input[i] == answer[j]) { // iとj順番（配列インデックス）が同じでと、入力数字と答えjが正しいか。
															// iはinputの入力数字が入っている箱。jは答えの配列。
						hit++;
					} else if (input[i] == answer[j]) { // 入力数字が、answerjのどこか1つと一致するか。
						blow++;
					}
				}
			}
			// 終了判断
			System.out.println("ヒット" + hit + " ブロー" + blow);

			if (hit == 3) {
				System.out.println("おめでとー");
				break;
			} else {
				System.out.println();
				System.out.println("答えは：" + answer[0] + answer[1] + answer[2]);
			}

		}
	}

}
