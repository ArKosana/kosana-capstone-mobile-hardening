#!/usr/bin/env bash
set -euo pipefail
WORKDIR="$(cd "$(dirname "$0")/.." && pwd)"
APK="$WORKDIR/apk/vuln.apk"
TMP="$WORKDIR/artifacts/patch_working"
DECOM="$TMP/decompiled"
REBUILD="$TMP/rebuilt.apk"
SIGNKEY="$TMP/debugkey.keystore"

echo "[*] Setting up clean workspace..."
rm -rf "$TMP" && mkdir -p "$TMP" "$DECOM"

echo "[*] Step 1: Decompiling APK..."
apktool d -f -o "$DECOM" "$APK" 2>&1 | grep -E "(I:|W:|E:)" || true

MAN="$DECOM/AndroidManifest.xml"
echo "[*] Step 2: Backing up original manifest..."
cp "$MAN" "$MAN.original"

echo "[*] Step 3: Applying security fixes with robust Python script..."
python3 <<PYSCRIPT
import re

print("Reading AndroidManifest.xml...")
with open('$MAN', 'r', encoding='utf-8', errors='ignore') as f:
    content = f.read()

print("Original file size:", len(content))

# Fix 1: Set allowBackup to false
changes = 0
if 'android:allowBackup="true"' in content:
    content = content.replace('android:allowBackup="true"', 'android:allowBackup="false"')
    changes += 1
    print("✓ Fixed allowBackup")

# Fix 2: Set exported to false for vulnerable components
# Use more specific patterns to avoid breaking XML structure
exported_patterns = [
    (r'(<activity [^>]*?android:exported=")true("[^>]*?>)', 'activity'),
    (r'(<receiver [^>]*?android:exported=")true("[^>]*?>)', 'receiver'), 
    (r'(<provider [^>]*?android:exported=")true("[^>]*?>)', 'provider')
]

for pattern, component in exported_patterns:
    matches = re.findall(pattern, content)
    if matches:
        content = re.sub(pattern, r'\1false\2', content)
        changes += len(matches)
        print(f"✓ Fixed {len(matches)} {component}(s)")

print(f"Total changes made: {changes}")

# Write back carefully
with open('$MAN', 'w', encoding='utf-8') as f:
    f.write(content)
print("Security fixes applied successfully")
PYSCRIPT

echo "[*] Step 4: Rebuilding APK carefully..."
if apktool b "$DECOM" -o "$REBUILD" 2>&1 | grep -q "Built apk into"; then
    echo "✓ APK rebuilt successfully"
else
    echo "✗ APK rebuild failed"
    exit 1
fi

echo "[*] Step 5: Creating debug keystore..."
keytool -genkeypair -v -keystore "$SIGNKEY" -storepass android -keypass android \
    -dname "CN=Kosana, OU=Capstone, O=Kosana" -alias capstonekey \
    -keyalg RSA -keysize 2048 -validity 10000 2>/dev/null && echo "✓ Keystore created"

echo "[*] Step 6: Signing APK..."
if jarsigner -verbose -keystore "$SIGNKEY" -storepass android -keypass android \
    "$REBUILD" capstonekey 2>/dev/null; then
    echo "✓ APK signed successfully"
else
    echo "✗ APK signing failed"
    exit 1
fi

echo "[*] Step 7: Final verification..."
if [ -f "$REBUILD" ] && unzip -t "$REBUILD" >/dev/null 2>&1; then
    echo "✅ PATCH SUCCESSFUL - APK is valid and ready"
    echo "Patched APK: $REBUILD"
    echo "File size: $(du -h "$REBUILD" | cut -f1)"
else
    echo "❌ PATCH FAILED - APK is corrupted"
    exit 1
fi
