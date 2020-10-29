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
            NaiveSnake.window.updateMap(NaiveSnake.dump.getMap());
            Thread.sleep(2000); // wait for map refresh
        }
    }

    private void runCheat(AlgPlayerCheat player) throws Exception {
        int[][] lastMap = NaiveSnake.dump.getMap();
        while (NaiveSnake.gameRunning) {
            SnakeClient.Direction dir = player.play(lastMap);
            NaiveSnake.client.move(dir);
            lastMap = NaiveSnake.dump.getMap();
            NaiveSnake.window.updateMap(lastMap);
            Thread.sleep(2000); // wait for map refresh
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
