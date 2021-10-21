package com.erlei.privacy.monitor.model;

import android.text.TextUtils;

import com.erlei.privacy.monitor.util.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import de.robv.android.xposed.XposedHelpers;

public class HookMethod {

    public Class<?> clazz;
    public String method;
    public String describe;
    public boolean printArgs = false;

    public HookMethod(Class<?> clazz, String method, String describe) {
        this.clazz = clazz;
        this.method = method;
        this.describe = describe;
    }

    public HookMethod(Class<?> clazz, String method, String describe, boolean printArgs) {
        this.clazz = clazz;
        this.method = method;
        this.describe = describe;
        this.printArgs = printArgs;
    }

    public static Set<HookMethod> of(ClassLoader loader, String clazz, String... methods) {
        try {
            Class<?> aClass = XposedHelpers.findClass(clazz, loader);
            return of(aClass, methods);
        } catch (Error e) {
            Logger.e("findClass error %s", clazz);
        }
        return new HashSet<>();
    }

    public static Set<HookMethod> of(Class<?> clazz, String... methods) {
        Set<HookMethod> set = new HashSet<>();
        if (methods == null || methods.length == 0) {
            for (Method declaredMethod : clazz.getDeclaredMethods()) {
                set.add(new HookMethod(clazz, declaredMethod.getName(), ""));
            }
        }
        for (String name : methods != null ? methods : new String[0]) {
            set.add(new HookMethod(clazz, name, ""));
        }
        return set;
    }

    public static HookMethod parse(ClassLoader classLoader, String line) {
        try {
            return parse(classLoader, new JSONObject(line));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HookMethod parse(ClassLoader classLoader, JSONObject jsonObject) {
        try {
            String clazz = jsonObject.getString("clazz");
            String method = jsonObject.optString("method");
            String describe = jsonObject.optString("describe");
            boolean printArgs = jsonObject.optBoolean("printArgs");
            Class<?> aClass = XposedHelpers.findClass(clazz, classLoader);
            return new HookMethod(aClass, method, describe, printArgs);
        } catch (Error | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HookMethod)) return false;
        HookMethod that = (HookMethod) o;
        return Objects.equals(clazz, that.clazz) &&
                Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz, method);
    }

    public JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        try {
            object.put("clazz", clazz.getCanonicalName());
            object.put("method", method);
            object.put("describe", describe);
            object.put("printArgs", printArgs);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HookMethod{");
        sb.append("clazz=").append(clazz);
        sb.append(", method='").append(method).append('\'');
        sb.append(", printArgs='").append(printArgs).append('\'');
        if (!TextUtils.isEmpty(describe)) sb.append(", describe='").append(describe).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
