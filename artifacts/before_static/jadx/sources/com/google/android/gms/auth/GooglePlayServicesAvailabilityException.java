package com.google.android.gms.auth;

import android.content.Intent;

/* loaded from: classes.dex */
public class GooglePlayServicesAvailabilityException extends UserRecoverableAuthException {
    private final int zzOD;

    GooglePlayServicesAvailabilityException(int connectionStatusCode, String msg, Intent intent) {
        super(msg, intent);
        this.zzOD = connectionStatusCode;
    }

    public int getConnectionStatusCode() {
        return this.zzOD;
    }
}
