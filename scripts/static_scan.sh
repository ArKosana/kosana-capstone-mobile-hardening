#!/usr/bin/env bash
set -euo pipefail
WORKDIR="$(cd "$(dirname "$0")/.." && pwd)"
APK="$WORKDIR/apk/vuln.apk"
OUT="$WORKDIR/artifacts/static_scan"

echo "[*] Creating output directory: $OUT"
mkdir -p "$OUT"

echo "[*] Unpacking APK with apktool..."
apktool d -f -o "$OUT/decompiled" "$APK" > "$OUT/apktool.log" 2>&1 || true

echo "[*] Extracting AndroidManifest.xml..."
xml="$OUT/decompiled/AndroidManifest.xml"
cp "$xml" "$OUT/AndroidManifest.xml"

echo "[*] Checking for exported activities/services/receivers/providers..."
grep -n "android:exported=\"true\"" -R "$OUT/decompiled" || true
grep -n "<activity" -R "$OUT/decompiled" || true
grep -n "android:allowBackup" -R "$OUT/decompiled" || true

echo "[*] Searching for common hard-coded keys or secrets..."
jadx -d "$OUT/jadx" "$APK" > /dev/null 2>&1 || true
grep -RInE "API_KEY|APIKEY|PASSWORD|SECRET|aws_secret|aws_key|hardcoded" "$OUT/jadx" || true

echo "[*] Listing files in assets and res/raw (possible insecure storage)..."
ls -la "$OUT/decompiled/assets" 2>/dev/null || true
ls -la "$OUT/decompiled/res/raw" 2>/dev/null || true

echo "[*] Static scan completed. Results in $OUT"
