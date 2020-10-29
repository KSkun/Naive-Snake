package moe.ksmeow.snake.alg;

import moe.ksmeow.snake.client.SnakeClient;

public abstract class AlgPlayerView extends AlgPlayer {

    public abstract SnakeClient.Direction play(SnakeClient.View view);

}
