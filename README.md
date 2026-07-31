# Modern Multi-Module Crypto Tracker Showcase

A production-grade, offline-first Android application designed with modern software engineering standards. This repository demonstrates clean architecture boundaries, multi-module setups, Orbit MVI, type-safe Jetpack Compose navigation, and robust unit-testing suites.

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
