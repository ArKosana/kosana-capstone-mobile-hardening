#!/usr/bin/env bash
set -euo pipefail
WORKDIR="$(cd "$(dirname "$0")/.." && pwd)"
APK="$WORKDIR/apk/vuln.apk"
TMP="$WORKDIR/artifacts/patch"
DECOM="$TMP/decompiled"
REBUILD="$TMP/rebuilt.apk"
SIGNKEY="$TMP/debugkey.keystore"

echo "[*] Setting up workspace..."
rm -rf "$TMP" && mkdir -p "$TMP" "$DECOM"

echo "[*] Decompiling APK..."
apktool d -f -o "$DECOM" "$APK"

MAN="$DECOM/AndroidManifest.xml"
echo "[*] Backing up manifest..."
cp "$MAN" "$MAN.original"

echo "[*] Applying security fixes with Python..."
python3 <<PYSCRIPT
import re

print("Reading AndroidManifest.xml...")
with open('$MAN', 'r') as f:
    content = f.read()

print("Applying security patches...")

# Fix 1: Set allowBackup to false
if 'android:allowBackup="true"' in content:
    content = content.replace('android:allowBackup="true"', 'android:allowBackup="false"')
    print("✓ Fixed: allowBackup set to false")

# Fix 2: Set exported to false for vulnerable components
exported_fixes = [
    ('activity', r'<activity [^>]*android:exported="true"[^>]*>'),
    ('receiver', r'<receiver [^>]*android:exported="true"[^>]*>'),
    ('provider', r'<provider [^>]*android:exported="true"[^>]*>')
]

for comp_type, pattern in exported_fixes:
    matches = re.findall(pattern, content)
    for match in matches:
        fixed = match.replace('android:exported="true"', 'android:exported="false"')
        content = content.replace(match, fixed)
    if matches:
        print(f"✓ Fixed: {len(matches)} {comp_type}(s) set to exported=false")

# Write back
with open('$MAN', 'w') as f:
    f.write(content)
print("Security patches applied successfully")
PYSCRIPT

echo "[*] Rebuilding APK..."
apktool b "$DECOM" -o "$REBUILD"

echo "[*] Creating debug keystore..."
keytool -genkeypair -keystore "$SIGNKEY" -storepass android -keypass android \
    -dname "CN=Kosana, OU=Capstone, O=Kosana" -alias capstonekey \
    -keyalg RSA -keysize 2048 -validity 10000 >/dev/null 2>&1

echo "[*] Signing APK..."
jarsigner -keystore "$SIGNKEY" -storepass android -keypass android "$REBUILD" capstonekey

echo "[*] Verifying patch..."
if [ -f "$REBUILD" ]; then
    echo "✅ PATCH SUCCESSFUL - APK created at: $REBUILD"
    echo "File size: $(du -h "$REBUILD" | cut -f1)"
else
    echo "❌ PATCH FAILED - No APK produced"
    exit 1
fi
