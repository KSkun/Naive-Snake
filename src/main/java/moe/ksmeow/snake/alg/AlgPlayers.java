package moe.ksmeow.snake.alg;

public class AlgPlayers {

    public static final AlgPlayer randomAlg = new RandomAlgPlayer();

    public static void registerPlayers() {
        AlgRegistry.INSTANCE.register("random", randomAlg);
    }

}
