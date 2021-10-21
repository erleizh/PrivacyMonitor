package com.erlei.privacy.monitor.util;

import android.util.Log;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileLogger implements Logger.LoggerListener {

    public final static ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final String file;

    public FileLogger(String file) {
        this.file = file;
    }

    @Override
    public void log(int priority, String msg) {
        executorService.execute(() -> {
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(file, true);
                fileWriter.append(getPriority(priority));
                fileWriter.append(":\t");
                fileWriter.append(msg);
                fileWriter.append("\n");
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileWriter != null) {
                        fileWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String getPriority(int priority) {
        switch (priority) {
            case Log.ERROR:
                return "ERROR";
            case Log.INFO:
                return "INFO";
            case Log.WARN:
                return "WARN";
            case Log.DEBUG:
                return "DEBUG";
            default:
                return "VERBOSE";
        }
    }
}
