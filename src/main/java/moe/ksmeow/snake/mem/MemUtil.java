package moe.ksmeow.snake.mem;

import moe.ksmeow.snake.NaiveSnake;

import java.io.*;

/**
 * @author KSkun
 */
public class MemUtil {

    /**
     * Read all bytes of the file.
     * @param path File path.
     * @return The bytes of the file.
     * @throws Exception
     */
    private static byte[] readAllBytesFromFile(String path) throws Exception {
        File file = new File(path);
        FileInputStream isFile = new FileInputStream(file);
        byte[] bytes = isFile.readAllBytes();
        isFile.close();
        return bytes;
    }

    /**
     * Write content to the specified file. This will rewrite the file.
     * @param path File path.
     * @param content Content to write.
     * @return The file.
     * @throws Exception
     */
    private static File writeStringToFile(String path, String content) throws Exception {
        File file = new File(path);
        if (file.exists()) file.delete();
        file.createNewFile();
        FileWriter writerFile = new FileWriter(file);
        writerFile.write(content);
        writerFile.close();
        return file;
    }

    /**
     * Dump the memory of the specified process from begin addr to end addr.
     * @param pid The PID of the process.
     * @param beg Begin address.
     * @param end End address.
     * @return The dumped memory, stored in a byte array.
     * @throws Exception
     */
    public static byte[] dumpMemory(long pid, long beg, long end) throws Exception {
        String filenameDump = pid + ".dump", filenameGdbCmd = pid + "_gdb.cmd";

        String strBeg = Long.toHexString(beg), strEnd = Long.toHexString(end);
        // gdb cmd: dump memory pid.dump 0xbeg 0xend
        String strGdbCmd = "dump memory " + filenameDump + " 0x" + strBeg + " 0x" + strEnd + "\nquit\ny\n";
        File fileGdbCmd = writeStringToFile(filenameGdbCmd, strGdbCmd);

        // cmd: gdb attach pid < pid_gdb.cmd
        String[] cmds = {"bash", "-c", "gdb attach " + pid + " < " + filenameGdbCmd};
        Process procGdb = Runtime.getRuntime().exec(cmds);
        // ###### DEBUG ######
        BufferedReader readerProcGdb = new BufferedReader(new InputStreamReader(procGdb.getInputStream()));
        if (NaiveSnake.DEBUG) {
            String _line;
            while (procGdb.isAlive()) {
                _line = readerProcGdb.readLine();
                if (_line != null) System.out.println(_line);
            }
        }
        // ###### DEBUG ######
        procGdb.waitFor();
        fileGdbCmd.delete();

        byte[] bytes = readAllBytesFromFile(filenameDump);
        (new File(filenameDump)).delete();
        return bytes;
    }

}
