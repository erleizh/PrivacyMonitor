package com.erlei.privacy.monitor.util;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * 作者:  lll
 * 日期： 2016/12/17
 * 应用前后台变化监听器
 */
public class LifecycleChangeListener implements Application.ActivityLifecycleCallbacks {

    public static final long CHECK_DELAY = 500;

    public interface Listener {

        void onBecameForeground();

        void onBecameBackground();

    }

    private static LifecycleChangeListener instance;

    private boolean foreground = false, paused = true;
    private Handler handler = new Handler(Looper.getMainLooper());
    private List<Listener> listeners = new CopyOnWriteArrayList<Listener>();
    private Runnable check;

    /**
     * Its not strictly necessary to use this method - _usually_ invoking
     * getDefault with a Context gives us a path to retrieve the Application and
     * initialise, but sometimes (e.g. in test harness) the ApplicationContext
     * is != the Application, and the docs make no guarantees.
     *
     * @param application
     * @return an initialised Foreground instance
     */
    public static LifecycleChangeListener init(Application application) {
        if (instance == null) {
            instance = new LifecycleChangeListener();
            application.registerActivityLifecycleCallbacks(instance);
        }
        return instance;
    }

    public static LifecycleChangeListener get(Application application) {
        if (instance == null) {
            init(application);
        }
        return instance;
    }

    public static LifecycleChangeListener get(Context ctx) {
        if (instance == null) {
            Context appCtx = ctx.getApplicationContext();
            if (appCtx instanceof Application) {
                init((Application) appCtx);
            } else {
                throw new IllegalStateException(
                        "Foreground is not initialised and " +
                                "cannot obtain the Application obj");
            }
        }
        return instance;
    }

    public static LifecycleChangeListener get() {
        if (instance == null) {
            throw new IllegalStateException(
                    "Foreground is not initialised - invoke " +
                            "at least once with parameterised init/getDefault");
        }
        return instance;
    }

    public boolean isForeground() {
        return foreground;
    }

    public boolean isBackground() {
        return !foreground;
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        paused = false;
        boolean wasBackground = !foreground;
        foreground = true;
        if (check != null)
            handler.removeCallbacks(check);

        if (wasBackground) {
            for (Listener l : listeners) {
                try {
                    l.onBecameForeground();
                } catch (Exception exc) {
                }
            }
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        paused = true;

        if (check != null)
            handler.removeCallbacks(check);

        handler.postDelayed(check = () -> {
            if (foreground && paused) {
                foreground = false;
                for (Listener l : listeners) {
                    try {
                        l.onBecameBackground();
                    } catch (Exception exc) {
                    }
                }
            } else {
            }
        }, CHECK_DELAY);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(new MyFragmentLifecycleCallbacks(activity), true);
        }
        Logger.i("%s\tonCreate\t%s", activity.getPackageName(), activity.getClass().getName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    private static class MyFragmentLifecycleCallbacks extends FragmentManager.FragmentLifecycleCallbacks {

        private final Activity activity;

        public MyFragmentLifecycleCallbacks(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
            super.onFragmentCreated(fm, f, savedInstanceState);
            Logger.i("%s\tonCreate\t%s", activity.getPackageName(), f.getClass().getName());
        }
    }
}

