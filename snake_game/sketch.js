// キャンパスを用意
// 蛇を用意
// 蛇を動かす
// 果物を用意
// 蛇が果物を食べる
// 果物が動く
// 蛇が大きくなる
// ゲーム終了

let snake; // snakeオブジェクトを格納する。
let fruit;
const size = 30; // snakeのサイズ

function setup() { // 初期設定。初回時のみ。キャンバスと蛇オブジェクトを生成する。1秒で5回描画する。
  createCanvas(600, 600);
  frameRate(5);
  snake = new Snake();
  fruit = new Fruit();
}

function draw() { // フレーム1毎が自動更新。キャンパスの背景色と蛇の表示させる関数を呼ぶ。蛇を動かす。
  background('gray');
  snake.show();
  snake.move();
  fruit.show();

  if (snake.eat(fruit.body)) {
    fruit.move();
  }

  if (snake.end()) {
    background('black');
    noLoop();
  }
}

function keyPressed() { // 矢印キーを押した時の関数。
  if (keyCode === UP_ARROW) {
    snake.changeDirection(0, -1);
  } else if (keyCode === DOWN_ARROW) {
    snake.changeDirection(0, 1);
  } else if (keyCode === LEFT_ARROW) {
    snake.changeDirection(-1, 0);
  } else if (keyCode === RIGHT_ARROW) {
    snake.changeDirection(1, 0);
  }
}

class Fruit {
  constructor() {
    let x = size * floor(random(0, width / size));
    let y = size * floor(random(0, height / size));
    this.body = createVector(x, y);
  };

  move() {
    let x = size * floor(random(0, width / size));
    let y = size * floor(random(0, height / size));
    this.body.x = x;
    this.body.y = y;
  }

  show() {
    fill('red');
    rect(this.body.x, this.body.y, size, size);
  }
}

class Snake { // 蛇クラス
  constructor() { // 蛇の初期値。画面の左上。
    this.body = [];
    this.body.push(createVector(0, 0));
    this.xDirection = 1;
    this.yDirection = 0;
  }

  add() {
    let head = this.body[this.body.length - 1].copy();
    head.x += this.xDirection * size; // 横方向に、自分のサイズ × xDirection単位 位置を足していく。
    head.y += this.yDirection * size;
    this.body.push(head);
  }

  eat(vec) {
    let head = this.body[this.body.length - 1];
    if (head.x === vec.x && head.y === vec.y) {
      this.add();
      return true;
    } else {
      return false;
    }
  }

  changeDirection(x, y) {
    this.xDirection = x;
    this.yDirection = y;
  }

  show() { // 蛇クラスの中のshow()関数。蛇を表示させる為の関数を定義。色を黒とし、■とする。
    fill('black');
    this.body.forEach(body => {
      rect(body.x, body.y, size, size);
    }
    )

  }

  end() {
    let head = this.body[this.body.length - 1];
    if (head.x < 0 || head.y < 0 || head.x > width || head.y > height) {
      return true;
    }

    for (let i = 0; i < this.body.length - 1; i++) {
      if (this.body[i].x === head.x && this.body[i].y === head.y) {
        return true;
      }
    }

    return false;
  }

  move() { // 蛇を動かす定義
    let head = this.body[this.body.length - 1].copy();
    head.x += this.xDirection * size; // 横方向に、自分のサイズ × xDirection単位 位置を足していく。
    head.y += this.yDirection * size;
    this.body.shift();
    this.body.push(head);
  }

}