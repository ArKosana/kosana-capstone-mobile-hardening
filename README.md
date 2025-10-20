# Kosana Ankamma Rao — Capstone: Mobile App Hardening Pipeline

**Project:** Automated Mobile App Hardening + Evidence-driven Pentest  
**Author:** Kosana Ankamma Rao — kosana.ar@gmail.com

## Overview
A reproducible pipeline that finds mobile app vulnerabilities (static & dynamic), demonstrates a proof-of-concept exploit, applies targeted hardening, and verifies remediation — accompanied by a presentation and a short demo video.

## Contents
- `scripts/` — static_scan.sh, frida_poc.js, patch_and_rebuild.sh, record_demo.sh
- `apk/` — `vuln.apk` (original InsecureBankv2) and `patched.apk`
- `artifacts/` — before/after scan outputs, PoC logs, rebuild logs
- `slides/` — Kosana_Capstone_Presentation.pptx + exported PDF
- `report/` — EXECUTIVE_SUMMARY.pdf, TECH_APPENDIX.md

## Quick start (reproduce)
1. Enable USB debugging on your Android device and connect via USB.
2. Place the test app as `apk/vuln.apk`.
3. `./scripts/static_scan.sh` — static checks (artifacts/before_static).
4. `frida -U -f com.android.insecurebankv2 -l scripts/frida_poc.js --no-pause` (optional PoC).
5. `./scripts/patch_and_rebuild.sh` — builds `artifacts/patch/rebuilt.apk`.
6. Install rebuilt APK: `adb install -r artifacts/patch/rebuilt.apk`.
7. Re-run `./scripts/static_scan.sh` and compare `artifacts/before_static` and `artifacts/after_static`.

## Deliverables
- Slide deck + speaker notes (slides/)
- Demo video (capstone-demo-Kosana.mp4)
- Executive summary (report/EXECUTIVE_SUMMARY.pdf)
- Full artifacts & technical appendix (report/TECH_APPENDIX.md, artifacts/)

## Contact
Kosana Ankamma Rao — kosana.ar@gmail.com
