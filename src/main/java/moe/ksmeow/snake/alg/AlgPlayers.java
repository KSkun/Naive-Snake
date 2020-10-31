package moe.ksmeow.snake.alg;

public class AlgPlayers {

    public static final AlgPlayer
            randomAlg = new RandomAlgPlayer(),
            wiseRandom = new WiseRandomPlayer(),
            living = new LivingPlayer();

    public static void registerPlayers() {
        AlgRegistry.INSTANCE.register("random", randomAlg);
        AlgRegistry.INSTANCE.register("wrandom", wiseRandom);
        AlgRegistry.INSTANCE.register("living", living);
    }

}
