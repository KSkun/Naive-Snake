package moe.ksmeow.snake;

import moe.ksmeow.snake.client.SnakeClient;
import moe.ksmeow.snake.mem.MemDump;
import moe.ksmeow.snake.mem.ServerProcess;
import moe.ksmeow.snake.ui.SnakeFrame;
import org.apache.commons.cli.*;

import java.util.Random;
import java.util.logging.Logger;

/**
 * @author KSkun
 */
public class NaiveSnake {

    public static final String APP_NAME = "NaiveSnake", VERSION = "1.0";

    public static final boolean DEBUG = true;

    public static boolean MANUAL;

    public static final Logger log = Logger.getLogger(APP_NAME);

    public static final int MAP_SIZE = 199;

    public static ServerProcess server;

    public static SnakeClient client;

    public static MemDump dump;

    public static SnakeFrame window;

    private static Options getOptions() {
        Options options = new Options();

        Option optManual = new Option("m", "manual", false, "manual mode");
        optManual.setRequired(false);
        options.addOption(optManual);

        Option optAlg = new Option("a", "alg", true, "watch specified alg play snake");
        optManual.setRequired(false);
        options.addOption(optAlg);

        return options;
    }

    /*
    params:
        -m, --manual: manual mode
        -a <name>, --alg <name>: watch specified alg play snake
     */
    public static void main(String[] args) throws Exception {
        Options options = getOptions();
        CommandLineParser parser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();
        CommandLine cmd = parser.parse(options, args);

        if ((cmd.hasOption("manual") && cmd.hasOption("alg")) ||
                (!cmd.hasOption("manual") && !cmd.hasOption("alg"))) {
            helpFormatter.printHelp(APP_NAME, options);
            return;
        }
        MANUAL = cmd.hasOption("manual");

        String file = "subg.client";
        int port = 23334;
        String seed = "2020";

        log.info(APP_NAME + " " + VERSION + " starting");

        server = new ServerProcess(file, port, seed);
        log.info(file + " starting with pid " + server.getPid());

        Thread.sleep(2000); // wait for map generation

        log.info("dump preparing");
        dump = new MemDump(server.getPid());
        log.info("dump is ready");

        log.info("client starting");
        client = new SnakeClient("localhost", port);
        client.connect();
        log.info("client is ready");

        log.info("ui starting");
        window = new SnakeFrame(dump.getMap());
        window.setVisible(true);
        log.info("ui is ready");

        while (window.isVisible()) {}

        server.destroy();
    }

}
