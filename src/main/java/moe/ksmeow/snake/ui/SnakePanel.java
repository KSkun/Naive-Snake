package moe.ksmeow.snake.ui;

import moe.ksmeow.snake.NaiveSnake;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SnakePanel extends JPanel {

    public static final int RECT_SIZE = 4;
    private int[][] map;

    private static final Color COL_SELF_HEAD = Color.WHITE, COL_SELF_BODY = Color.WHITE.darker(),
            COL_BOT_HEAD = Color.GREEN, COL_BOT_BODY = Color.GREEN.darker(),
            COL_EMPTY = Color.BLACK, COL_POISON = Color.RED, COL_SHOE = Color.BLUE, COL_SCOPE = Color.MAGENTA,
            COL_BAD_FRUIT = Color.YELLOW.darker().darker().darker(), COL_SINGLE_FRUIT = Color.YELLOW.darker(),
            COL_FIVE_FRUIT = Color.YELLOW;

    private static final Map<Integer, Color> COLOR_MAP = new HashMap<>() {{
        put(0, COL_EMPTY); put(1, COL_POISON); put(2, COL_SHOE); put(3, COL_SCOPE);
        put(4, COL_BAD_FRUIT); put(5, COL_SINGLE_FRUIT); put(6, COL_FIVE_FRUIT);
        put(8, COL_SELF_BODY); put(9, COL_BOT_BODY); put(10, COL_BOT_BODY); put(11, COL_BOT_BODY);
        put(12, COL_SELF_HEAD); put(13, COL_BOT_HEAD); put(14, COL_BOT_HEAD); put(15, COL_BOT_HEAD);
    }};

    public SnakePanel(int[][] _map) {
        map = _map;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < NaiveSnake.MAP_SIZE; i++) {
            for (int j = 0; j < NaiveSnake.MAP_SIZE; j++) {
                drawRect(g, i, j, COLOR_MAP.get(map[i][j]));
            }
        }
    }

    private void drawRect(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.fillRect(x * RECT_SIZE, y * RECT_SIZE, RECT_SIZE, RECT_SIZE);
    }

    public void setMap(int[][] _map) {
        map = _map;
    }

}
