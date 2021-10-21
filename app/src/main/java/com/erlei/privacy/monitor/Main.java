package com.erlei.privacy.monitor;

import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.Application;
import android.app.NotificationManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.erlei.privacy.monitor.model.HookMethod;
import com.erlei.privacy.monitor.util.FileLogger;
import com.erlei.privacy.monitor.util.FileRecorder;
import com.erlei.privacy.monitor.util.LifecycleChangeListener;
import com.erlei.privacy.monitor.util.Logger;
import com.erlei.privacy.monitor.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Main implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        hookApplication();
    }


    private void hookApplication() {
        try {
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Application application = (Application) param.thisObject;
                    Context context = (Context) param.args[0];
                    if ((context.getApplicationInfo().flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                        return;
                    }
                    String packageResourcePath = context.getPackageResourcePath();
                    boolean isDataApp = packageResourcePath.startsWith("/data/app/");
                    if (!isDataApp) {
                        return;
                    }
                    final String packageName = context.getPackageName();
                    if (BuildConfig.APPLICATION_ID.equals(packageName)
                            || "com.oasisfeng.greenify".equals(packageName)//绿色守护
                            || packageName.contains("xposed")
                            || packageName.contains("magisk")
                            || packageName.startsWith("com.google")
                            || packageName.startsWith("com.android")
                    ) {
                        return;
                    }
                    initLogger(context);
                    Logger.i("Application启动... %s", application.getClass().getCanonicalName());
                    LifecycleChangeListener.init(application);
                    LifecycleChangeListener.get(application).addListener(new LifecycleChangeListener.Listener() {
                        @Override
                        public void onBecameForeground() {
                            Logger.i("进入前台");
                        }

                        @Override
                        public void onBecameBackground() {
                            FileRecorder.save();
                            Logger.i("进入后台");
                        }
                    });
                    initHookMethodSet(context);
                    hookApi(context);
                    hookFile(context);
                }
            });
        } catch (Exception e) {
            Logger.e("hookApplication %s", Log.getStackTraceString(e));
        }
    }

    private void hookFile(Context context) {
        try {
            String processName = Util.getProcessName();
            File baseDir = Util.getBaseDir(context);
            File logFile = new File(baseDir, "文件列表" + "-" + processName);
            FileRecorder.init(processName, logFile.getAbsolutePath());
            String path = new File(Util.getBaseDir(context), Util.format.format(new Date()) + "-Access-" + processName).getAbsolutePath();
            FileLogger fileLogger = new FileLogger(path);
            XposedBridge.hookAllConstructors(File.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    File file = (File) param.thisObject;
                    FileRecorder.record(file.getAbsolutePath());
                    String parent = file.getParent();
                    if (parent != null && !parent.startsWith(baseDir.getAbsolutePath())) {
                        fileLogger.log(Log.INFO, String.format("%s\n%s", file.getAbsolutePath(), Util.getStackTraces()));
                    }
                }
            });
        } catch (Exception e) {
            Logger.e("%s", Log.getStackTraceString(e));
        }
    }

    private void initLogger(Context context) {
        String processName = Util.getProcessName();
        String path = new File(Util.getBaseDir(context), Util.format.format(new Date()) + "-" + processName).getAbsolutePath();
        Logger.addProcessLoggerListener(processName, new FileLogger(path));
    }

    private final Set<HookMethod> mHookMethodSet = new LinkedHashSet<>();

    private void initHookMethodSet(Context context) {
        mHookMethodSet.addAll(HookMethod.of(TelephonyManager.class,
                "getDeviceId",
                "getImei",
                "getCellLocation",
                "getAllCellInfo",
                "getSubscriberId",
                "getSimSerialNumber",
                "listen",
                "getNetworkOperator",
                "getSimOperator",
                "getSimCountryIso"));
        mHookMethodSet.addAll(HookMethod.of(WifiInfo.class, "getMacAddress", "getSSID", "getBSSID"));
        mHookMethodSet.addAll(HookMethod.of(Application.class, "attachBaseContext"));
        mHookMethodSet.addAll(HookMethod.of(WifiManager.class, "getScanResults"));
        mHookMethodSet.addAll(HookMethod.of(NetworkInterface.class, "getHardwareAddress"));
        mHookMethodSet.addAll(HookMethod.of(Settings.Secure.class, "getString", "getInt", "getLong"));
        mHookMethodSet.addAll(HookMethod.of(LocationManager.class, "getLastKnownLocation", "requestLocationUpdates", "getGpsStatus", "getLastLocation"));
        mHookMethodSet.addAll(HookMethod.of(ContentResolver.class, "query", "insert", "bulkInsert", "delete", "update", "applyBatch"));
        mHookMethodSet.addAll(HookMethod.of(Runtime.class, "exec"));
        mHookMethodSet.addAll(HookMethod.of(BluetoothLeScanner.class, "startScan", "startScanFromSource", "startTruncatedScan"));
        mHookMethodSet.addAll(HookMethod.of(SensorManager.class, "getSensorList", "getDynamicSensorList", "registerListener", "getDefaultSensor"));
        mHookMethodSet.addAll(HookMethod.of(AccountManager.class, "getAccounts", "getAccountsByType", "addAccountExplicitly"));
        mHookMethodSet.addAll(HookMethod.of(context.getClassLoader(), "android.app.ApplicationPackageManager", "getInstalledPackages", "getInstalledApplications", "deletePackage", "installPackage", "getLaunchIntentForPackage"));
        mHookMethodSet.addAll(HookMethod.of(android.hardware.Camera.class, "open", "getNumberOfCameras", "takePicture"));
        mHookMethodSet.addAll(HookMethod.of(AccountManager.class, "getRunningAppProcesses", "getRecentTasks", "getRunningServices", "getRunningExternalApplications"));
        mHookMethodSet.addAll(HookMethod.of(CameraManager.class, "openCamera", "getCameraIdList", "getCameraCharacteristics", "openCameraForUid", "openCameraDeviceUserAsync"));
        mHookMethodSet.addAll(HookMethod.of(SmsManager.class, "sendTextMessage", "getAllMessagesFromIcc", "sendDataMessage", "sendMultipartTextMessage"));
        mHookMethodSet.addAll(HookMethod.of(ActivityManager.class, "killBackgroundProcesses", "forceStopPackage"));
        mHookMethodSet.addAll(HookMethod.of(AlarmManager.class, "setImpl"));
        mHookMethodSet.addAll(HookMethod.of(ConnectivityManager.class, "setMobileDataEnabled", "getMobileDataEnabled", "getActiveNetworkInfo"));
        mHookMethodSet.addAll(HookMethod.of(MediaRecorder.class, "start"));
        mHookMethodSet.addAll(HookMethod.of(SystemClock.class, "elapsedRealtime", "elapsedRealtime", "elapsedRealtimeNanos"));
        mHookMethodSet.addAll(HookMethod.of(StatFs.class));
        mHookMethodSet.addAll(HookMethod.of(NotificationManager.class, "areNotificationsEnabled"));
        mHookMethodSet.addAll(HookMethod.of(context.getClassLoader(), "android.app.NotificationChannel", "getImportance"));
        mHookMethodSet.addAll(HookMethod.of(AppOpsManager.class));

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(Util.getBaseDir(context), "hookMethods.json")));
            String line = null;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            JSONArray array = new JSONArray(builder.toString());
            for (int i = 0; i < array.length(); i++) {
                HookMethod method = HookMethod.parse(context.getClassLoader(), array.getJSONObject(i));
                if (method != null) {
                    mHookMethodSet.remove(method);//有些参数没有算到equals里面,但是需要用文件里面的覆盖代码里面的
                    mHookMethodSet.add(method);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void hookApi(Context context) {
        //TelephonyManager
        Logger.i("HookMethodCount %s", mHookMethodSet.size());
        JSONArray jsonArray = new JSONArray();
        for (HookMethod hookMethod : mHookMethodSet) {
            jsonArray.put(hookMethod.toJSONObject());
            Logger.i("%s", hookMethod);
            try {
                XposedBridge.hookAllMethods(hookMethod.clazz, hookMethod.method, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        if (hookMethod.printArgs) {
                            String args = "";
                            try {
                                args = Arrays.deepToString(param.args);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            log(param.method.getDeclaringClass().getSimpleName() + "#" + param.method.getName() + "(" + args + ")");
                        } else {
                            log(param.method.getDeclaringClass().getSimpleName() + "#" + param.method.getName());
                        }
                    }
                });
            } catch (Exception e) {
                Logger.e("%s", Log.getStackTraceString(e));
            }
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(Util.getBaseDir(context), "hookMethods.json"))) {
            byte[] bytes = jsonArray.toString(4).getBytes();
            fileOutputStream.write(bytes, 0, bytes.length);
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void log(String method) {
        try {
            StringBuilder stackInfo = Util.getStackTraces();
            boolean foreground = LifecycleChangeListener.get().isForeground();
            Logger.i("%s; %s; %s; Method=%s;\n%s", Util.format.format(new Date()),
                    Util.getProcessName(), foreground ? "前台" : "后台", method, stackInfo.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("sendLog=%s", Log.getStackTraceString(e));
        }
    }
}
