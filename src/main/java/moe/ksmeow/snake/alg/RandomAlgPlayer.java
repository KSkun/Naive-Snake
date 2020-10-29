package moe.ksmeow.snake.alg;

import moe.ksmeow.snake.NaiveSnake;
import moe.ksmeow.snake.client.SnakeClient;

public class RandomAlgPlayer extends AlgPlayerCheat {

    @Override
    public SnakeClient.Direction play(int[][] map) {
        return switch (Math.abs(NaiveSnake.rand.nextInt()) % 4) {
            case 0 -> SnakeClient.Direction.UP;
            case 1 -> SnakeClient.Direction.DOWN;
            case 2 -> SnakeClient.Direction.LEFT;
            case 3 -> SnakeClient.Direction.RIGHT;
            default -> null;
        };
    }

}
