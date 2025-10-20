package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.google.android.gms.R;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.internal.zzgd;

@zzgd
/* loaded from: classes.dex */
public final class zzj {
    private final String zzoL;
    private final AdSize[] zzsr;

    public zzj(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainAttributes = context.getResources().obtainAttributes(attributeSet, R.styleable.AdsAttrs);
        String string = typedArrayObtainAttributes.getString(R.styleable.AdsAttrs_adSize);
        String string2 = typedArrayObtainAttributes.getString(R.styleable.AdsAttrs_adSizes);
        boolean z = !TextUtils.isEmpty(string);
        boolean z2 = TextUtils.isEmpty(string2) ? false : true;
        if (z && !z2) {
            this.zzsr = zzD(string);
        } else {
            if (z || !z2) {
                if (!z || !z2) {
                    throw new IllegalArgumentException("Required XML attribute \"adSize\" was missing.");
                }
                throw new IllegalArgumentException("Either XML attribute \"adSize\" or XML attribute \"supportedAdSizes\" should be specified, but not both.");
            }
            this.zzsr = zzD(string2);
        }
        this.zzoL = typedArrayObtainAttributes.getString(R.styleable.AdsAttrs_adUnitId);
        if (TextUtils.isEmpty(this.zzoL)) {
            throw new IllegalArgumentException("Required XML attribute \"adUnitId\" was missing.");
        }
    }

    private static AdSize[] zzD(String str) {
        String[] strArrSplit = str.split("\\s*,\\s*");
        AdSize[] adSizeArr = new AdSize[strArrSplit.length];
        for (int i = 0; i < strArrSplit.length; i++) {
            String strTrim = strArrSplit[i].trim();
            if (strTrim.matches("^(\\d+|FULL_WIDTH)\\s*[xX]\\s*(\\d+|AUTO_HEIGHT)$")) {
                String[] strArrSplit2 = strTrim.split("[xX]");
                strArrSplit2[0] = strArrSplit2[0].trim();
                strArrSplit2[1] = strArrSplit2[1].trim();
                try {
                    adSizeArr[i] = new AdSize("FULL_WIDTH".equals(strArrSplit2[0]) ? -1 : Integer.parseInt(strArrSplit2[0]), "AUTO_HEIGHT".equals(strArrSplit2[1]) ? -2 : Integer.parseInt(strArrSplit2[1]));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Could not parse XML attribute \"adSize\": " + strTrim);
                }
            } else if ("BANNER".equals(strTrim)) {
                adSizeArr[i] = AdSize.BANNER;
            } else if ("LARGE_BANNER".equals(strTrim)) {
                adSizeArr[i] = AdSize.LARGE_BANNER;
            } else if ("FULL_BANNER".equals(strTrim)) {
                adSizeArr[i] = AdSize.FULL_BANNER;
            } else if ("LEADERBOARD".equals(strTrim)) {
                adSizeArr[i] = AdSize.LEADERBOARD;
            } else if ("MEDIUM_RECTANGLE".equals(strTrim)) {
                adSizeArr[i] = AdSize.MEDIUM_RECTANGLE;
            } else if ("SMART_BANNER".equals(strTrim)) {
                adSizeArr[i] = AdSize.SMART_BANNER;
            } else {
                if (!"WIDE_SKYSCRAPER".equals(strTrim)) {
                    throw new IllegalArgumentException("Could not parse XML attribute \"adSize\": " + strTrim);
                }
                adSizeArr[i] = AdSize.WIDE_SKYSCRAPER;
            }
        }
        if (adSizeArr.length == 0) {
            throw new IllegalArgumentException("Could not parse XML attribute \"adSize\": " + str);
        }
        return adSizeArr;
    }

    public String getAdUnitId() {
        return this.zzoL;
    }

    public AdSize[] zzi(boolean z) {
        if (z || this.zzsr.length == 1) {
            return this.zzsr;
        }
        throw new IllegalArgumentException("The adSizes XML attribute is only allowed on PublisherAdViews.");
    }
}
