# Technical Appendix

## Environment
- Kali Linux (list versions), tools: apktool, jadx, frida, adb, ffmpeg

## Key scripts
- `scripts/static_scan.sh` — decompile APK, search exports, strings, allowBackup flag
- `scripts/frida_poc.js` — frida script used for dynamic checks (optional)
- `scripts/patch_and_rebuild.sh` — automated manifest changes, rebuild + sign

## Important logs & artifacts
- artifacts/before_static/scan.log
- artifacts/component_hijack_poc.log
- artifacts/patch/patch_run.log
- artifacts/after_static/scan.log
- artifacts/patch/rebuilt.apk (signed)
- slides/Kosana_Capstone_Presentation.pptx and PDF

## Repro steps (full commands)
1. Enable USB debugging on your Android device and connect via USB.
2. Place the test app as `apk/vuln.apk`.
3. `./scripts/static_scan.sh` — static checks (artifacts/before_static).
4. `frida -U -f com.android.insecurebankv2 -l scripts/frida_poc.js --no-pause` (optional PoC).
5. `./scripts/patch_and_rebuild.sh` — builds `artifacts/patch/rebuilt.apk`.
6. Install rebuilt APK: `adb install -r artifacts/patch/rebuilt.apk`.
7. Re-run `./scripts/static_scan.sh` and compare `artifacts/before_static` and `artifacts/after_static`.
