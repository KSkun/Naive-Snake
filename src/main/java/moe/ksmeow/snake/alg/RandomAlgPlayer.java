package moe.ksmeow.snake.alg;

import moe.ksmeow.snake.NaiveSnake;
import moe.ksmeow.snake.client.SnakeClient;

public class RandomAlgPlayer extends AlgPlayerCheat {

    @Override
    public SnakeClient.Direction play(int[][] map) {
        switch (Math.abs(NaiveSnake.rand.nextInt()) % 4) {
            case 0: return SnakeClient.Direction.UP;
            case 1: return SnakeClient.Direction.DOWN;
            case 2: return SnakeClient.Direction.LEFT;
            case 3: return SnakeClient.Direction.RIGHT;
            default: return null;
        }
    }

}
