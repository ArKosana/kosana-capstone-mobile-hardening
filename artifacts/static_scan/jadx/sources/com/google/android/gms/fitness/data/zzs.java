package com.google.android.gms.fitness.data;

import java.util.List;

/* loaded from: classes.dex */
public class zzs {
    public static <T> int zza(T t, List<T> list) {
        if (t == null) {
            return -1;
        }
        int iIndexOf = list.indexOf(t);
        if (iIndexOf >= 0) {
            return iIndexOf;
        }
        list.add(t);
        return list.size() - 1;
    }
}
