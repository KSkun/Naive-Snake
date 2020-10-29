package moe.ksmeow.snake.alg;

import java.util.HashMap;
import java.util.Map;

public class AlgRegistry {

    public static final AlgRegistry INSTANCE = new AlgRegistry();

    public Map<String, AlgPlayer> algs;

    public AlgRegistry() {
        algs = new HashMap<>();
    }

    public void register(String name, AlgPlayer alg) {
        algs.put(name, alg);
    }

    public AlgPlayer getAlg(String name) {
        return algs.get(name);
    }

}
