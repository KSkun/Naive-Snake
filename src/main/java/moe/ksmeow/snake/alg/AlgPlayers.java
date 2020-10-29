package moe.ksmeow.snake.alg;

public class AlgPlayers {

    public static final AlgPlayer randomAlg = new RandomAlgPlayer();

    public static final AlgPlayer wiseRandom = new WiseRandomPlayer();

    public static void registerPlayers() {
        AlgRegistry.INSTANCE.register("random", randomAlg);
        AlgRegistry.INSTANCE.register("wrandom", wiseRandom);
    }

}
