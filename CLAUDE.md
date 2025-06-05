# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

AutoX is an Android automation framework based on Auto.js. It's a multi-module Android project that allows users to create and run JavaScript-based automation scripts on Android devices.

## Build Commands

```bash
# Build debug APK (PhoneFarm)
./gradlew :phonefarm:assembleDebug

# Build release APK (PhoneFarm)
./gradlew :phonefarm:assembleRelease

# Build specific flavor (PhoneFarm)
./gradlew :phonefarm:assembleCommonDebug
./gradlew :phonefarm:assembleV6Debug

# Build template APK (required before main build)
./gradlew :phonefarm:buildTemplateApp

# Clean build
./gradlew clean

# Build all modules
./gradlew build
```

## Project Architecture

### Module Structure

- **phonefarm/**: Main application module containing the IDE, script editor, and UI
- **autojs/**: Core automation JavaScript engine and runtime
- **automator/**: Android UI automation implementation
- **common/**: Shared utilities and common code
- **inrt/**: Template APK builder for packaging scripts into standalone apps
- **apkbuilder/**: APK building and signing functionality
- **paddleocr/**: OCR (Optical Character Recognition) functionality using PaddlePaddle

### Key Technologies

- **Language**: Kotlin and Java
- **UI Framework**: Jetpack Compose (for newer UI) + traditional Android Views
- **JavaScript Engine**: Rhino (Mozilla's JavaScript implementation for Java)
- **Build System**: Gradle with Kotlin DSL
- **Min SDK**: 21 (Android 5.0)
- **Target SDK**: 28 (Android 9)

### Important Files

- `project-versions.json`: Centralized version management
- `buildSrc/src/main/kotlin/Version.kt`: Version configuration loader
- `phonefarm/src/main/assets/modules/`: JavaScript module implementations
- `autojs/src/main/assets/init.js`: JavaScript runtime initialization

### JavaScript Bridge Architecture

The app provides JavaScript APIs through modules located in `autojs/src/main/assets/modules/`:
- `__app__.js`: App management APIs
- `__automator__.js`: UI automation APIs
- `__ui__.js`: UI creation APIs
- `__files__.js`: File system operations
- `__http__.js`: HTTP networking
- `__images__.js`: Image processing
- `__threads__.js`: Multi-threading support

### Known Issues (from README)

- v6.5.9 introduced "system incompatibility" bug preventing APK installation
- v6.6.1 has crash on startup bug
- v6.6.1 has packaging bug where .so files are missing from built APKs (need to change `map` to `forEach` in Zip.kt)

## Development Notes

- Android Studio version: 2023.3.1 Patch 2
- JDK version: 17
- The project uses AndroidX and Jetifier
- Template APK is automatically built when building the main app
- For Chinese developers, Aliyun Maven mirrors are configured for faster dependency downloads