package moe.ksmeow.snake.alg;

import moe.ksmeow.snake.NaiveSnake;
import moe.ksmeow.snake.client.SnakeClient;

public class PlayerThread extends Thread {

    private final AlgPlayer player;

    public PlayerThread(AlgPlayer _player) {
        player = _player;
    }

    private void runView(AlgPlayerView player) throws Exception {
        while (NaiveSnake.gameRunning) {
            SnakeClient.View view = NaiveSnake.client.getView();
            SnakeClient.Direction dir = player.play(view);
            NaiveSnake.client.move(dir);
            Thread.sleep(10); // wait for map refresh
            NaiveSnake.window.updateMap(NaiveSnake.dump.getMap());
        }
    }

    private void runCheat(AlgPlayerCheat player) throws Exception {
        int[][] lastMap = NaiveSnake.dump.getMap();
        while (NaiveSnake.gameRunning) {
            SnakeClient.View view = NaiveSnake.client.getView();
            SnakeClient.Direction dir = player.play(lastMap, view.x, view.y);
            NaiveSnake.client.move(dir);
            Thread.sleep(10); // wait for map refresh
            lastMap = NaiveSnake.dump.getMap();
            NaiveSnake.window.updateMap(lastMap);
        }
    }

    @Override
    public void run() {
        try {
            if (player instanceof AlgPlayerView) {
                runView((AlgPlayerView) player);
            }
            if (player instanceof AlgPlayerCheat) {
                runCheat((AlgPlayerCheat) player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
