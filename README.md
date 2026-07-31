# CoinPulse — Modern Multi-Module Crypto Tracker

A production-grade, offline-first Android application designed with modern software engineering standards. This repository demonstrates clean architecture boundaries, multi-module setups, Orbit MVI, type-safe Jetpack Compose navigation, and robust unit-testing suites.

<p align="center">
  <img src="art/screenshot_tracker.png" width="45%" />
  <img src="art/screenshot_detail.png" width="45%" />
</p>

---

## 💼 Business & Engineering Value

When building software at scale, architecture directly impacts business performance. **CoinPulse** is engineered to address common scaling challenges in commercial environments:

* **Modular Scalability**: By decoupling features into independent Gradle modules, build times are optimized, merge conflicts are minimized in team settings, and code ownership boundaries are clean.
* **Offline Resilience (Zero-Latency UI)**: Offline-first architecture ensures that users have immediate access to data upon opening the app, regardless of network conditions (e.g., in subways or areas with low connectivity). Caching remote API payloads locally in Room serves as a single source of truth.
* **SDK Dependency Minimization**: By drawing custom historical trend graphs directly on a Compose `Canvas` using cubic Bezier splines, the app avoids importing heavy third-party plotting libraries. This reduces the final APK size and minimizes external security/licensing risks.
* **Robust Demo Reliability**: Public APIs frequently rate-limit requests. CoinPulse handles transient network errors and rate-limiting responses (`HTTP 429`) by gracefully falling back to a mock local database sync, ensuring the app remains fully functional for reviewers at all times.

---

## 🚀 Key Architectural Highlights

* **Clean Multi-Module Separation**: Divided into decoupled features and core modules to optimize compilation speed, support parallel development, and isolate layers of concern.
* **Single Source of Truth (SSOT)**: The presentation layer observes local database cache layers reactively. UI components never query the API endpoints directly, guaranteeing complete offline support and a smooth, zero-latency user experience.
* **Orbit MVI Framework**: Utilizes the Orbit MVI library to enforce unidirectional data flows (UDF) through structured state management, intents, and side-effects (e.g., navigation, toast notifications).
* **Type-Safe Compose Navigation**: Replaces old string-based route templates and bundle parsing with Kotlin Serialization, ensuring compile-time route verification.
* **Custom Canvas Drawing**: Implements a custom-drawn, smooth cubic Bezier Line Chart directly on a Compose `Canvas` with gridlines and gradients, showcasing low-overhead rendering without third-party chart libraries.
* **CI/CD Automation**: Integrated with a GitHub Actions workflow that executes compilation checks, Detekt lint reviews, and the unit-test suite on every pull request.

---

## 🛠️ Technology Stack

* **Language**: Kotlin
* **UI Framework**: Jetpack Compose (Material 3)
* **Dependency Injection**: Dagger-Hilt
* **Asynchronous Streams**: Kotlin Coroutines & Flow
* **State Management**: Orbit MVI
* **Navigation**: Type-Safe Navigation Compose (2.8+)
* **Local Persistence**: Room Database
* **Networking**: Retrofit & OkHttp
* **Image Loading**: Coil Compose
* **Testing Stack**: JUnit 4, Mockk (Kotlin-first mocking), Google Truth assertions, Turbine (flow verification), Orbit Test
* **Dependency Management**: Centralized Gradle Version Catalog (`libs.versions.toml`)
* **Static Analysis**: Detekt Linting

---

## 📂 Module Layout & Boundaries

The codebase complies with domain-driven modularization, preventing cross-boundary leakage:

```
├── app                     # Entry-point shell, sets up navigation graph and Hilt bindings
├── feature
│   ├── tracker             # Dashboard listing coins, search, and pull-to-refresh
│   └── detail              # Detailed coin metrics and historical pricing trend charts
└── core
    ├── model               # Pure Kotlin domain data structures and repository interfaces
    ├── network             # Retrofit definitions, DTO payloads, and network data sources
    ├── database            # Room database caching schema, DAOs, and database entities
    └── designsystem        # Material 3 custom styles, themes, and canvas chart composables
```

---

## 🧪 Testing Coverage

The application includes an extensive suite of local unit tests checking:
* **Orbit ViewModels**: Validating MVI state sequences (`reduce`) and side-effect dispatches using the official Orbit Test framework.
* **Data Repositories**: Verifying database cache swaps and network-to-local fallback operations using Mockk behavior declarations.
* **Data Mappers**: Assuring mapping operations correctly bridge database entities, API payloads, and domain objects.

### Run tests via command line:
```bash
./gradlew test
```

---

## ⚙️ Compilation & Setup

1. Open Android Studio (Koala 2024.1.1 or newer recommended).
2. Choose **Open Project** and import the `CryptoTracker` directory.
3. Allow Gradle to download dependencies and build the version catalog.
4. Run the `:app` module on a connected emulator or physical device running API Level 24 or newer.
