package moe.ksmeow.snake.mem;

import java.io.IOException;

/**
 * @author KSkun
 */
public class ServerProcess {

    private final String file, seed;
    private final int port;
    private final Process process;

    /**
     * The object of managing the process operations of the server process.
     * @param _file The file path of the server file.
     * @param _port The socket port to bind.
     * @param _seed The seed for map generation.
     * @throws IOException
     */
    public ServerProcess(String _file, int _port, String _seed) throws IOException {
        file = _file;
        port = _port;
        seed = _seed;

        String cmd = "./" + file + " -p " + port + " -s " + seed;
        process = Runtime.getRuntime().exec(cmd);
    }

    /**
     * The object of managing the process operations of the server process.
     * @param _port The socket port to bind.
     * @param _seed The seed for map generation.
     * @throws IOException
     */
    public ServerProcess(int _port, String _seed) throws IOException {
        this("subg.client", _port, _seed);
    }

    /**
     * The object of managing the process operations of the server process.
     * @param _seed The seed for map generation.
     * @throws IOException
     */
    public ServerProcess(String _seed) throws IOException {
        this(8080, _seed);
    }

    public Process getProcess() {
        return process;
    }

    public long getPid() {
        return process.pid();
    }

    public void destroy() {
        process.destroy();
    }

}
