### 隐私API调用检测

#### 使用方法
1. 安装Xposed框架
2. 运行并启用PrivacyMonitor
3. 打开待检测 apk
4. 查看控制台日志,或者到 `/sdcard/Android/data/[packageName]/files/合规检测/`路径下查看日志

#### 目前已经监控的API列表
```
INFO:	HookMethodCount 120
INFO:	HookMethod{clazz=class android.telephony.TelephonyManager, method='getImei', printArgs='false'}
INFO:	HookMethod{clazz=class android.telephony.TelephonyManager, method='getNetworkOperator', printArgs='false'}
INFO:	HookMethod{clazz=class android.telephony.TelephonyManager, method='listen', printArgs='false'}
INFO:	HookMethod{clazz=class android.telephony.TelephonyManager, method='getSimOperator', printArgs='false'}
INFO:	HookMethod{clazz=class android.telephony.TelephonyManager, method='getSubscriberId', printArgs='false'}
INFO:	HookMethod{clazz=class android.telephony.TelephonyManager, method='getDeviceId', printArgs='false'}
INFO:	HookMethod{clazz=class android.telephony.TelephonyManager, method='getSimSerialNumber', printArgs='false'}
INFO:	HookMethod{clazz=class android.telephony.TelephonyManager, method='getSimCountryIso', printArgs='false'}
INFO:	HookMethod{clazz=class android.telephony.TelephonyManager, method='getCellLocation', printArgs='false'}
INFO:	HookMethod{clazz=class android.telephony.TelephonyManager, method='getAllCellInfo', printArgs='false'}
INFO:	HookMethod{clazz=class android.net.wifi.WifiInfo, method='getBSSID', printArgs='false'}
INFO:	HookMethod{clazz=class android.net.wifi.WifiInfo, method='getMacAddress', printArgs='false'}
INFO:	HookMethod{clazz=class android.net.wifi.WifiInfo, method='getSSID', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.Application, method='attachBaseContext', printArgs='false'}
INFO:	HookMethod{clazz=class android.net.wifi.WifiManager, method='getScanResults', printArgs='false'}
INFO:	HookMethod{clazz=class java.net.NetworkInterface, method='getHardwareAddress', printArgs='false'}
INFO:	HookMethod{clazz=class android.provider.Settings$Secure, method='getString', printArgs='false'}
INFO:	HookMethod{clazz=class android.provider.Settings$Secure, method='getInt', printArgs='false'}
INFO:	HookMethod{clazz=class android.provider.Settings$Secure, method='getLong', printArgs='false'}
INFO:	HookMethod{clazz=class android.location.LocationManager, method='getLastLocation', printArgs='false'}
INFO:	HookMethod{clazz=class android.location.LocationManager, method='getLastKnownLocation', printArgs='false'}
INFO:	HookMethod{clazz=class android.location.LocationManager, method='getGpsStatus', printArgs='false'}
INFO:	HookMethod{clazz=class android.location.LocationManager, method='requestLocationUpdates', printArgs='false'}
INFO:	HookMethod{clazz=class android.content.ContentResolver, method='delete', printArgs='false'}
INFO:	HookMethod{clazz=class android.content.ContentResolver, method='insert', printArgs='false'}
INFO:	HookMethod{clazz=class android.content.ContentResolver, method='applyBatch', printArgs='false'}
INFO:	HookMethod{clazz=class android.content.ContentResolver, method='update', printArgs='false'}
INFO:	HookMethod{clazz=class android.content.ContentResolver, method='bulkInsert', printArgs='false'}
INFO:	HookMethod{clazz=class android.content.ContentResolver, method='query', printArgs='false'}
INFO:	HookMethod{clazz=class java.lang.Runtime, method='exec', printArgs='false'}
INFO:	HookMethod{clazz=class android.bluetooth.le.BluetoothLeScanner, method='startTruncatedScan', printArgs='false'}
INFO:	HookMethod{clazz=class android.bluetooth.le.BluetoothLeScanner, method='startScanFromSource', printArgs='false'}
INFO:	HookMethod{clazz=class android.bluetooth.le.BluetoothLeScanner, method='startScan', printArgs='false'}
INFO:	HookMethod{clazz=class android.hardware.SensorManager, method='getDefaultSensor', printArgs='false'}
INFO:	HookMethod{clazz=class android.hardware.SensorManager, method='getSensorList', printArgs='false'}
INFO:	HookMethod{clazz=class android.hardware.SensorManager, method='registerListener', printArgs='false'}
INFO:	HookMethod{clazz=class android.hardware.SensorManager, method='getDynamicSensorList', printArgs='false'}
INFO:	HookMethod{clazz=class android.accounts.AccountManager, method='addAccountExplicitly', printArgs='false'}
INFO:	HookMethod{clazz=class android.accounts.AccountManager, method='getAccountsByType', printArgs='false'}
INFO:	HookMethod{clazz=class android.accounts.AccountManager, method='getAccounts', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.ApplicationPackageManager, method='getLaunchIntentForPackage', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.ApplicationPackageManager, method='getInstalledApplications', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.ApplicationPackageManager, method='deletePackage', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.ApplicationPackageManager, method='installPackage', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.ApplicationPackageManager, method='getInstalledPackages', printArgs='false'}
INFO:	HookMethod{clazz=class android.hardware.Camera, method='takePicture', printArgs='false'}
INFO:	HookMethod{clazz=class android.hardware.Camera, method='getNumberOfCameras', printArgs='false'}
INFO:	HookMethod{clazz=class android.hardware.Camera, method='open', printArgs='false'}
INFO:	HookMethod{clazz=class android.accounts.AccountManager, method='getRunningServices', printArgs='false'}
INFO:	HookMethod{clazz=class android.accounts.AccountManager, method='getRecentTasks', printArgs='false'}
INFO:	HookMethod{clazz=class android.accounts.AccountManager, method='getRunningAppProcesses', printArgs='false'}
INFO:	HookMethod{clazz=class android.accounts.AccountManager, method='getRunningExternalApplications', printArgs='false'}
INFO:	HookMethod{clazz=class android.hardware.camera2.CameraManager, method='openCamera', printArgs='false'}
INFO:	HookMethod{clazz=class android.hardware.camera2.CameraManager, method='getCameraCharacteristics', printArgs='false'}
INFO:	HookMethod{clazz=class android.hardware.camera2.CameraManager, method='openCameraDeviceUserAsync', printArgs='false'}
INFO:	HookMethod{clazz=class android.hardware.camera2.CameraManager, method='getCameraIdList', printArgs='false'}
INFO:	HookMethod{clazz=class android.hardware.camera2.CameraManager, method='openCameraForUid', printArgs='false'}
INFO:	HookMethod{clazz=class android.telephony.SmsManager, method='sendMultipartTextMessage', printArgs='false'}
INFO:	HookMethod{clazz=class android.telephony.SmsManager, method='sendTextMessage', printArgs='false'}
INFO:	HookMethod{clazz=class android.telephony.SmsManager, method='sendDataMessage', printArgs='false'}
INFO:	HookMethod{clazz=class android.telephony.SmsManager, method='getAllMessagesFromIcc', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.ActivityManager, method='forceStopPackage', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.ActivityManager, method='killBackgroundProcesses', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AlarmManager, method='setImpl', printArgs='false'}
INFO:	HookMethod{clazz=class android.net.ConnectivityManager, method='getActiveNetworkInfo', printArgs='false'}
INFO:	HookMethod{clazz=class android.net.ConnectivityManager, method='getMobileDataEnabled', printArgs='false'}
INFO:	HookMethod{clazz=class android.net.ConnectivityManager, method='setMobileDataEnabled', printArgs='false'}
INFO:	HookMethod{clazz=class android.media.MediaRecorder, method='start', printArgs='false'}
INFO:	HookMethod{clazz=class android.os.SystemClock, method='elapsedRealtime', printArgs='false'}
INFO:	HookMethod{clazz=class android.os.SystemClock, method='elapsedRealtimeNanos', printArgs='false'}
INFO:	HookMethod{clazz=class android.os.StatFs, method='getFreeBytes', printArgs='false'}
INFO:	HookMethod{clazz=class android.os.StatFs, method='doStat', printArgs='false'}
INFO:	HookMethod{clazz=class android.os.StatFs, method='getTotalBytes', printArgs='false'}
INFO:	HookMethod{clazz=class android.os.StatFs, method='getFreeBlocksLong', printArgs='false'}
INFO:	HookMethod{clazz=class android.os.StatFs, method='getBlockSize', printArgs='false'}
INFO:	HookMethod{clazz=class android.os.StatFs, method='getBlockCountLong', printArgs='false'}
INFO:	HookMethod{clazz=class android.os.StatFs, method='restat', printArgs='false'}
INFO:	HookMethod{clazz=class android.os.StatFs, method='getFreeBlocks', printArgs='false'}
INFO:	HookMethod{clazz=class android.os.StatFs, method='getAvailableBytes', printArgs='false'}
INFO:	HookMethod{clazz=class android.os.StatFs, method='getBlockCount', printArgs='false'}
INFO:	HookMethod{clazz=class android.os.StatFs, method='getAvailableBlocksLong', printArgs='false'}
INFO:	HookMethod{clazz=class android.os.StatFs, method='getBlockSizeLong', printArgs='false'}
INFO:	HookMethod{clazz=class android.os.StatFs, method='getAvailableBlocks', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.NotificationManager, method='areNotificationsEnabled', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='setUidMode', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='opToDefaultMode', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='resetAllModes', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='startOp', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='checkOp', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='opToPermission', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='checkAudioOpNoThrow', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='checkAudioOp', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='-get0', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='setUserRestrictionForUser', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='getPackagesForOps', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='opAllowSystemBypassRestriction', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='opToRestriction', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='opAllowsReset', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='getToken', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='setRestriction', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='checkPackage', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='noteProxyOpNoThrow', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='strDebugOpToOp', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='buildSecurityExceptionMsg', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='permissionToOpCode', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='startOpNoThrow', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='stopWatchingMode', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='permissionToOp', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='opToName', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='noteOp', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='setMode', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='noteOpNoThrow', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='getOpsForPackage', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='finishOp', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='opToSwitch', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='strOpToOp', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='startWatchingMode', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='checkOpNoThrow', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='noteProxyOp', printArgs='false'}
INFO:	HookMethod{clazz=class android.app.AppOpsManager, method='setUserRestriction', printArgs='false'}
```


#### 添加需要监控的API
第一次启动app时会在`/sdcard/Android/data/[packageName]/files/合规检测/`路径下生成`hookMethods.json`
里面配置了所有需要Hook的方法.也可以手动往里面添加方法,例如:
```
{
    "clazz": "android.telephony.TelephonyManager",
    "method": "getImei",
    "describe": "",
    "printArgs": false // 打印方法参数
}
```

- 检查隐私方法调用堆栈日志
```java
INFO:	2021-08-12 15:37:05:412; com.tencent.qqlive:xg_vip_service; 后台; Method=WifiInfo#getBSSID;
com.tencent.qqlive.utils.AppNetworkUtils.getNetInfo(AppNetworkUtils.java:16)
com.tencent.qqlive.utils.AppNetworkUtils.<clinit>(AppNetworkUtils.java:1)
com.tencent.qqlive.utils.AppNetworkUtils.setAppContext(AppNetworkUtils.java:1)
com.tencent.qqlive.route.RouteConfig.setContext(RouteConfig.java:3)
com.tencent.qqlive.network.NetworkConfig.setupRoute(NetworkConfig.java:1)
com.tencent.qqlive.network.NetworkConfig.setup(NetworkConfig.java:12)
com.tencent.qqlive.network.NetworkConfig.setup(NetworkConfig.java:2)
com.tencent.qqlive.component.network.NetworkModuleConfig.init(NetworkModuleConfig.java:20)
com.tencent.qqlive.ona.base.OtherProcApplication.attachBaseContext(OtherProcApplication.java:10)
com.tencent.qqlive.ona.base.initializer.jobs.OtherProcInitJob.run(OtherProcInitJob.java:1)
com.tencent.qqlive.ona.base.initializer.AppInitializer.executeAppAttachContextJobs(AppInitializer.java:8)
com.tencent.qqlive.ona.base.QQLiveApplicationWrapperImpl.doInit(QQLiveApplicationWrapperImpl.java:5)
com.tencent.qqlive.ona.base.QQLiveApplicationWrapper.attachInit(QQLiveApplicationWrapper.java:3)
com.tencent.qqlive.ona.base.QQLiveApplicationWrapper.a(QQLiveApplicationWrapper.java:2)
d.w.b.u.e.g.run(lambda)
com.tencent.qqlive.ona.base.launchmonitor.AppLaunchMonitor.simpleMainProcessTick(AppLaunchMonitor.java:3)
com.tencent.qqlive.ona.base.QQLiveApplicationWrapper.doAttachBaseContext(QQLiveApplicationWrapper.java:7)
com.tencent.qqlive.ona.base.QQLiveApplicationWrapper.attachBaseContext(QQLiveApplicationWrapper.java:27)
com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:999)
com.android.internal.os.ZygoteInit.main(ZygoteInit.java:889)
```
- 监控所有进程的文件访问记录
```java
INFO:	/data/user/0/com.tencent.qqlive/shared_prefs
com.tencent.qapmsdk.impl.appstate.b.n(AppStateTimeInfo.java:2)
com.tencent.qapmsdk.impl.appstate.b.a(AppStateTimeInfo.java:9)
com.tencent.qapmsdk.impl.instrumentation.QAPMAppInstrumentation.attachBaseContextBeginIns(QAPMAppInstrumentation.java:1)
com.tencent.qqlive.ona.base.QQLiveApplicationWrapper.attachBaseContext(QQLiveApplicationWrapper.java)
com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:999)
com.android.internal.os.ZygoteInit.main(ZygoteInit.java:889)
```
