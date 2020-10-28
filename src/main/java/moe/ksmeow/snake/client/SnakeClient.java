package moe.ksmeow.snake.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;

public class SnakeClient {

    private final String host;
    private final int port;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public SnakeClient(String _host, int _port) {
        host = _host;
        port = _port;
    }

    public void connect() throws Exception {
        socket = new Socket(host, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    private void send(String msg) throws Exception {
        writer.write(msg);
        writer.flush();
    }

    private String read() throws Exception {
        char[] buf = new char[1000];
        reader.read(buf);
        return String.copyValueOf(buf).trim();
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public void move(Direction dir) throws Exception {
        String msg = null;
        switch (dir) {
            case UP -> msg = "w";
            case DOWN -> msg = "s";
            case LEFT -> msg = "a";
            case RIGHT -> msg = "d";
        }
        send(msg);
    }

    public static class View {
        public int x, y;
        public int[][] view;

        public String toString() {
            return "[x: " + x + ", y: " + y + ", view: " + Arrays.deepToString(view) + "]";
        }
    }

    public View getView() throws Exception {
        send("g");
        String line = read();

        JsonObject json = JsonParser.parseString(line).getAsJsonObject();
        View view = new View();
        view.x = json.get("x").getAsInt();
        view.y = json.get("y").getAsInt();
        JsonArray viewArr = json.get("view").getAsJsonArray();
        int viewSize = viewArr.size();
        view.view = new int[viewSize][viewSize];
        for (int i = 0; i < viewSize; i++) {
            JsonArray arr = viewArr.get(i).getAsJsonArray();
            for (int j = 0; j < viewSize; j++) {
                view.view[i][j] = arr.get(j).getAsInt();
            }
        }
        return view;
    }

}
