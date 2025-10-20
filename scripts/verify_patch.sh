#!/usr/bin/env bash
echo "Testing if patched components are now blocked..."

components=("DoTransfer" "ViewStatement" "PostLogin" "ChangePassword")

for component in "${components[@]}"; do
    echo -n "Testing $component: "
    result=$(adb shell am start -n "com.android.insecurebankv2/.$component" 2>&1)
    if echo "$result" | grep -q "Permission Denial"; then
        echo "✅ BLOCKED (Security working)"
    elif echo "$result" | grep -q "Error type"; then
        echo "✅ BLOCKED (Cannot launch)"
    else
        echo "❌ STILL ACCESSIBLE - Patch may have failed"
    fi
done
