package moe.ksmeow.snake.alg;

import moe.ksmeow.snake.NaiveSnake;
import moe.ksmeow.snake.client.SnakeClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LivingPlayer extends AlgPlayerCheat {

    private static final int[][] DIR_TO_POS_DIFF = {{-1, 1, 0, 0}, {0, 0, -1, 1}};

    private int len;
    private double choiceX = 0, choiceY = 0;
    private int followCount = 100, hrCount = 0;
    private int[][] nextDir;

    private static boolean isPositionInvalid(int x, int y) {
        return x < 0 || x >= NaiveSnake.MAP_SIZE || y < 0 || y >= NaiveSnake.MAP_SIZE;
    }

    private static int cannotStep(int[][] map, int x, int y) {
        if (isPositionInvalid(x, y)) return 1;
        if (map[x][y] == 1 || map[x][y] == 2 || (map[x][y] >= 8 && map[x][y] != 12)) return 1;
        return 0;
    }

    private double dfs(int dep, int[][] map, double[][] maxValue, int[][] nextDir,
                       boolean[][] vis, boolean[][] bodyInd, int x, int y, int len, double disConst) {
        if (vis[x][y]) return -1e9;
        vis[x][y] = true;
        double thisValue = 0;
        if (map[x][y] == 4) thisValue = -2;
        if (map[x][y] == 5) thisValue = 1;
        if (map[x][y] == 6) thisValue = 5;
        int notStepCount = cannotStep(map, x - 1, y) + cannotStep(map, x + 1, y) +
                cannotStep(map, x, y - 1) + cannotStep(map, x, y + 1);
        if (map[x][y] == 1 || map[x][y] == 2 || map[x][y] >= 8) {//|| notStepCount >= 3) {
            thisValue = -1e9;
            return thisValue;
        }
        //if (bodyInd[x][y]) {
        //    thisValue -= 5;
        //} else {
            thisValue *= Math.max(0.5, disConst);
        //}
        maxValue[x][y] = thisValue;
        if (dep > Math.max(100, len * 1.5)) {
            return thisValue;
        }
        for (int i = 0; i < 4; i++) {
            int nextX = x + DIR_TO_POS_DIFF[0][i], nextY = y + DIR_TO_POS_DIFF[1][i];
            if (isPositionInvalid(nextX, nextY)) continue;
            double res = thisValue +
                    dfs(dep + 1, map, maxValue, nextDir, vis, bodyInd, nextX, nextY, len, disConst * 0.98);
            if (res > maxValue[x][y]) {
                maxValue[x][y] = res;
                nextDir[x][y] = i;
            }
        }
        return maxValue[x][y];
    }

    private void setBodyBlock(int x, int y, int headX, int headY, boolean[][] vis) {
        int dis = Math.abs(x - headX) + Math.abs(y - headY), R = 1;// + Math.max(0, (int) Math.log(dis));
        for (int i = x - R; i <= x + R; i++) {
            for (int j = y - R; j <= y + R; j++) {
                if (!isPositionInvalid(i, j)) {
                    vis[i][j] = true;
                }
            }
        }
    }

    private void doBodyBlock(int[][] map, int x, int y, boolean[][] vis) {
        for (int i = 0; i < NaiveSnake.MAP_SIZE; i++) {
            for (int j = 0; j < NaiveSnake.MAP_SIZE; j++) {
                if (map[i][j] == 8 && Math.abs(i - x) + Math.abs(j - y) > 5) {
                    setBodyBlock(i, j, x, y, vis);
                }
            }
        }
    }

    @Override
    public SnakeClient.Direction play(int[][] map, int x, int y) {
        hrCount++;
        if (hrCount == 24) {
            hrCount = 0;
        }

        len = 0;
        for (int i = 0; i < NaiveSnake.MAP_SIZE; i++) {
            for (int j = 0; j < NaiveSnake.MAP_SIZE; j++) {
                if (map[i][j] == 8) len++;
            }
        }

        /*if (followCount < 1 && nextDir[x][y] != -1 && hrCount != 0) {
            int nnextX = x + DIR_TO_POS_DIFF[0][nextDir[x][y]], nnextY = y + DIR_TO_POS_DIFF[1][nextDir[x][y]];
            if (map[nnextX][nnextY] != 1 && map[nnextX][nnextY] != 2 && map[nnextX][nnextY] < 8) {
                followCount++;
                return SnakeClient.Direction.values()[nextDir[x][y]];
            }
        }*/

        int dirNum = 0;
        double dirMaxValue = -1e9;
        double[][] maxValue = new double[NaiveSnake.MAP_SIZE][NaiveSnake.MAP_SIZE];
        boolean[][] bodyInd = new boolean[NaiveSnake.MAP_SIZE][NaiveSnake.MAP_SIZE];
        nextDir = new int[NaiveSnake.MAP_SIZE][NaiveSnake.MAP_SIZE];
        for (int i = 0; i < NaiveSnake.MAP_SIZE; i++) {
            for (int j = 0; j < NaiveSnake.MAP_SIZE; j++) {
                maxValue[i][j] = -1e9;
                nextDir[i][j] = -1;
            }
        }
        maxValue[x][y] = -1e9;
        if (len > 20) doBodyBlock(map, x, y, bodyInd);
        List<Integer> dirs = Arrays.asList(0, 1, 2, 3);
        Collections.shuffle(dirs);
        for (int i = 0; i < 4; i++) {
            int iDir = dirs.get(i);
            int nextX = x + DIR_TO_POS_DIFF[0][iDir], nextY = y + DIR_TO_POS_DIFF[1][iDir];
            if (isPositionInvalid(nextX, nextY)) continue;
            boolean[][] vis = new boolean[NaiveSnake.MAP_SIZE][NaiveSnake.MAP_SIZE];
            vis[x][y] = true;
            double nowMaxValue = dfs(1, map, maxValue, nextDir, vis, bodyInd, nextX, nextY, len, 1);
            if (nowMaxValue > dirMaxValue && map[nextX][nextY] != 1 && map[nextX][nextY] != 2 && map[nextX][nextY] < 8) {
                dirNum = iDir; dirMaxValue = nowMaxValue;
            }
        }
        int nextX = x + DIR_TO_POS_DIFF[0][dirNum], nextY = y + DIR_TO_POS_DIFF[1][dirNum];
        followCount = 0;

        if (len % 10 == 0) {
            NaiveSnake.log.info("snake length " + len);
        }

        return SnakeClient.Direction.values()[dirNum];
    }

}
