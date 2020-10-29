package moe.ksmeow.snake.alg;

import moe.ksmeow.snake.NaiveSnake;
import moe.ksmeow.snake.client.SnakeClient;

public class WiseRandomPlayer extends AlgPlayerCheat {

    private static final int[][] DIR_TO_POS_DIFF = {{-1, 1, 0, 0}, {0, 0, -1, 1}};

    private boolean checkDir(int[][] map, int x, int y, SnakeClient.Direction dir) {
        int nextX = x + DIR_TO_POS_DIFF[0][dir.ordinal()], nextY = y + DIR_TO_POS_DIFF[1][dir.ordinal()];
        return map[nextX][nextY] != 1 && map[nextX][nextY] != 2 && map[nextX][nextY] < 8;
    }

    @Override
    public SnakeClient.Direction play(int[][] map, int x, int y) {
        int counter = 0;
        SnakeClient.Direction dir = SnakeClient.Direction.values()[Math.abs(NaiveSnake.rand.nextInt()) % 4];
        while (!checkDir(map, x, y, dir) && counter < 20) {
            dir = SnakeClient.Direction.values()[Math.abs(NaiveSnake.rand.nextInt()) % 4];
            counter++;
        }
        return dir;
    }

}
