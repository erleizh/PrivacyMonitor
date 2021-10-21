package com.erlei.privacy.monitor.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.erlei.privacy.monitor.BuildConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class Util {
    public final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.CHINA);

    @NonNull
    public static String getProcessName() {
        String processName = "";
        if (Build.VERSION.SDK_INT >= 28) {
            processName = Application.getProcessName();
        }
        if (TextUtils.isEmpty(processName)) {
            try {
                @SuppressLint("PrivateApi") Class<?> activityThread = Class.forName("android.app.ActivityThread");
                // Before API 18, the method was incorrectly named "currentPackageName", but it still returned the process name
                // See https://github.com/aosp-mirror/platform_frameworks_base/commit/b57a50bd16ce25db441da5c1b63d48721bb90687
                @SuppressLint("ObsoleteSdkInt") String methodName =
                        Build.VERSION.SDK_INT >= 18 ? "currentProcessName" : "currentPackageName";
                Method getProcessName = activityThread.getDeclaredMethod(methodName);
                processName = (String) getProcessName.invoke(null);
            } catch (Exception ignore) {

            }
        }

        if (TextUtils.isEmpty(processName)) {
            byte[] cmdlineBuffer = new byte[64];

            try (FileInputStream stream = new FileInputStream("/proc/self/cmdline")) {
                int n = stream.read(cmdlineBuffer);
                int endIndex = indexOf(cmdlineBuffer, (byte) 0);
                processName = new String(cmdlineBuffer, 0, endIndex > 0 ? endIndex : n);
            } catch (IOException e) {
                // ignore
            }
            // ignore
        }
        if (TextUtils.isEmpty(processName)) {
            ActivityManager activityManager = (ActivityManager) getApplicationByReflect().getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager == null) return "";
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            if (runningAppProcesses == null || runningAppProcesses.isEmpty()) return "";
            for (ActivityManager.RunningAppProcessInfo process : runningAppProcesses) {
                if (process.pid == android.os.Process.myPid()) {
                    processName = process.processName;
                }
            }
        }
        return processName;
    }

    public static StringBuilder getStackTraces() {
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
        StringBuilder stackInfo = new StringBuilder();
        for (StackTraceElement element : stackTraces) {
            if (element.toString().startsWith("dalvik.")
                    || element.toString().startsWith("java.")
                    || element.toString().startsWith("android.")
                    || element.toString().startsWith(BuildConfig.APPLICATION_ID)
                    || element.toString().startsWith("de.robv.android.xposed")
                    || element.toString().startsWith("EdHooker")
            ) {
                continue;
            }
            stackInfo
                    .append(element.toString())
                    .append("\n");
        }
        return stackInfo;
    }

    private static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return (Application) app;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }

    /**
     * @param haystack
     * @param needle
     * @return
     */
    private static int indexOf(byte[] haystack, byte needle) {
        for (int i = 0; i < haystack.length; ++i) {
            if (haystack[i] == needle) {
                return i;
            }
        }
        return -1;
    }

    public static File getBaseDir(Context context) {
        return context.getExternalFilesDir(Logger.TAG);
    }
}
