package com.erlei.privacy.monitor.util;

import android.util.Log;

import java.util.HashMap;

public class Logger {

    public static final String TAG = "合规检测";
    static LoggerListener sListener = null;
    static final HashMap<String, LoggerListener> sProcessMap = new HashMap<>();


    public static void addProcessLoggerListener(String processName, LoggerListener loggerListener) {
        sProcessMap.put(processName, loggerListener);
    }

    public static void setListener(LoggerListener sListener) {
        Logger.sListener = sListener;
    }

    static synchronized void d(String format, Object... args) {
        String msg = String.format(format, args);
        Log.d(TAG, msg);
        if (sListener != null) {
            sListener.log(Log.DEBUG, msg);
        }
        LoggerListener listener = sProcessMap.get(Util.getProcessName());
        if (listener != null) {
            listener.log(Log.DEBUG, msg);
        }
    }

    public static void e(String format, Object... args) {
        String msg = String.format(format, args);
        Log.e(TAG, msg);
        if (sListener != null) {
            sListener.log(Log.ERROR, msg);
        }
        LoggerListener listener = sProcessMap.get(Util.getProcessName());
        if (listener != null) {
            listener.log(Log.ERROR, msg);
        }
    }

    public static void i(String format, Object... args) {
        String msg = String.format(format, args);
        Log.i(TAG, msg);
        if (sListener != null) {
            sListener.log(Log.INFO, msg);
        }
        LoggerListener listener = sProcessMap.get(Util.getProcessName());
        if (listener != null) {
            listener.log(Log.INFO, msg);
        }
    }

    public interface LoggerListener {

        /**
         * @param priority 级别
         * @param msg      消息
         * @see Log#DEBUG
         * @see Log#ERROR
         */
        void log(int priority, String msg);
    }


}
