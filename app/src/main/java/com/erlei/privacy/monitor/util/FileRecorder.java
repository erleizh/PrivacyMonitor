package com.erlei.privacy.monitor.util;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * Create by erlei on 7/28/21
 * <p>
 * Email : erleizh@gmail.com
 * <p>
 * Describe : 记录所有访问过的文件
 */
public class FileRecorder {

    private static final HashMap<String, LinkedHashSet<String>> sMap = new HashMap<>();
    private static final HashMap<String, String> sLogFile = new HashMap<>();

    public static void record(String path) {
        String processName = Util.getProcessName();
        LinkedHashSet<String> paths = sMap.get(processName);
        if (paths == null) {
            paths = new LinkedHashSet<>();
            sMap.put(processName, paths);
        }
        paths.add(path);
    }

    public static void save() {
        String processName = Util.getProcessName();
        HashSet<String> paths = sMap.get(processName);
        String logFile = sLogFile.get(processName);
        if (paths == null || paths.isEmpty() || logFile == null) return;
        Writer writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(logFile), 1024 * 1024);
            for (String path : paths) {
                writer.write(path);
                writer.write("\n");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void init(String processName, String path) {
        sLogFile.put(processName, path);
    }
}
