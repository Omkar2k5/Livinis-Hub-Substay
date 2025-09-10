---
description: Repository Information Overview
alwaysApply: true
---

# Livinns Hub Stay Information

## Summary
Livinns Hub Stay is an Android mobile application built with Kotlin and Jetpack Compose. The app appears to be focused on accommodation or stay management, with authentication features and a navigation-based UI architecture.

## Structure
- **app/**: Main application module containing all source code
  - **src/main/**: Primary source code directory
    - **java/com/example/livinnshubstay/**: Application code organized by feature
    - **res/**: Android resources (layouts, strings, drawables)
  - **src/test/**: Unit test directory
  - **src/androidTest/**: Instrumentation test directory

## Language & Runtime
**Language**: Kotlin
**Version**: Kotlin 2.0.21
**Build System**: Gradle (Kotlin DSL) with AGP 8.8.1
**Package Manager**: Gradle

## Dependencies
**Main Dependencies**:
- Jetpack Compose (2024.09.00) - UI framework
- AndroidX Core KTX (1.10.1) - Kotlin extensions
- AndroidX Lifecycle (2.6.1, 2.7.0) - Lifecycle management
- Navigation Compose (2.7.7) - Navigation component
- Lottie Compose (6.4.0) - Animation library
- Material Icons Extended (1.6.3) - Icon library
- Firebase Auth (22.3.1) - Authentication (future implementation)

**Development Dependencies**:
- JUnit (4.13.2) - Testing framework
- AndroidX Test (1.1.5) - Android testing
- Espresso (3.5.1) - UI testing

## Build & Installation
```bash
# Build debug APK
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug

# Run the application
./gradlew :app:run
```

## Testing
**Frameworks**: 
- JUnit (unit tests)
- AndroidX Test and Espresso (instrumentation tests)

**Test Locations**:
- Unit tests: `app/src/test/java/com/example/livinnshubstay/`
- Instrumentation tests: `app/src/androidTest/java/com/example/livinnshubstay/`

**Run Commands**:
```bash
# Run unit tests
./gradlew test

# Run instrumentation tests
./gradlew connectedAndroidTest
```

## Application Architecture
**Pattern**: MVVM (Model-View-ViewModel)
**Key Components**:
- **Navigation**: Uses Jetpack Navigation Compose for screen navigation
- **UI**: Built with Jetpack Compose
- **State Management**: Uses ViewModel and Compose state
- **Authentication**: Prepared for Firebase authentication integration