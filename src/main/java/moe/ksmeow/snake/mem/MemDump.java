package moe.ksmeow.snake.mem;

import com.google.common.primitives.Bytes;
import moe.ksmeow.snake.NaiveSnake;
import moe.ksmeow.snake.mem.MemUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author KSkun
 */
public class MemDump {

    private final long pid;
    private long heapBegin, heapEnd, heapOffset;
    private int[][] map = new int[NaiveSnake.MAP_SIZE][NaiveSnake.MAP_SIZE];
    private Date updateTime;

    /**
     * The object of managing the memory operations.
     * @param _pid The PID of the server process.
     * @throws Exception
     */
    public MemDump(long _pid) throws Exception {
        pid = _pid;

        getOffset();
    }

    /**
     * Find the offset of the memory region storing the map data.
     * @throws Exception
     */
    private void getOffset() throws Exception {
        // get heap position
        BufferedReader readerMaps = new BufferedReader(new FileReader("/proc/" + pid + "/maps"));
        String line;
        String strBeg = null, strEnd = null;
        while ((line = readerMaps.readLine()) != null) {
            if (!line.contains("[heap]")) continue;
            Matcher matcher = Pattern.compile("([0-9A-Fa-f]+)-([0-9A-Fa-f]+)").matcher(line);
            matcher.find();
            strBeg = matcher.group(1);
            strEnd = matcher.group(2);
        }
        heapBegin = Long.valueOf(strBeg, 16);
        heapEnd = Long.valueOf(strEnd, 16);

        // dump mem
        byte[] bytesHeap = MemUtil.dumpMemory(pid, heapBegin, heapEnd);

        // find the map
        byte[] bytesToFind = ("\1\0\0\0\1\0\0\0\1\0\0\0\1\0\0\0\1\0\0\0" +
                "\1\0\0\0\1\0\0\0\1\0\0\0\1\0\0\0\1\0\0\0").getBytes(); // [\x01\x00\x00\x00] * 10
        heapOffset = Bytes.indexOf(bytesHeap, bytesToFind);
    }

    /**
     * Update the map data by dumping memory.
     * @throws Exception
     */
    private void updateMap() throws Exception {
        updateTime = new Date(); // record update time

        int mapSize = NaiveSnake.MAP_SIZE * NaiveSnake.MAP_SIZE * 4;
        byte[] bytesMap = MemUtil.dumpMemory(pid, heapBegin + heapOffset, heapBegin + heapOffset + mapSize);
        IntBuffer buf = ByteBuffer.wrap(bytesMap).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer();
        for (int i = 0; i < NaiveSnake.MAP_SIZE; i++) {
            for (int j = 0; j < NaiveSnake.MAP_SIZE; j++) {
                map[j][i] = buf.get(i * NaiveSnake.MAP_SIZE + j);
            }
        }
    }

    /**
     * Update and get the map data.
     * @return
     */
    public int[][] getMap() throws Exception {
        updateMap();
        return map;
    }

}
