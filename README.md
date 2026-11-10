# Brainiac

Brainiac is a native Android assistant. The first Phase 1 increment accepts typed requests for the time and battery percentage. Both commands run locally. The command policy requires an unlocked device for battery status.

## Build and test

Install JDK 17 and Android SDK Platform 36, Build Tools 35.0.0, and Platform Tools. Set `JAVA_HOME` and `ANDROID_HOME`, or open the project in Android Studio and let it configure the SDK location. The minimum supported device API is 26 and the target API is 36.

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:lintDebug :app:assembleDebug
```

On the Galaxy S22 Ultra, enable USB debugging, connect via USB, and approve the debugging prompt. Confirm that `adb devices` lists the device as authorized, then install the debug build.

```powershell
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

Open Brainiac and submit `what time is it?`, `battery percentage`, and an unsupported command. Check the displayed time and battery against the device and verify that unsupported input gets help text.

## Remaining Phase 1 work

Push-to-talk and speech output, OpenRouter configuration and chat, tool validation and more device commands, Room history, preferences and onboarding, and wake-word and accessibility foundations.
