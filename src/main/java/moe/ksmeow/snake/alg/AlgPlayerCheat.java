package moe.ksmeow.snake.alg;

import moe.ksmeow.snake.client.SnakeClient;

public abstract class AlgPlayerCheat extends AlgPlayer {

    public abstract SnakeClient.Direction play(int[][] map, int x, int y);

}
