# Git Repository Viewer

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)]()

A modern Android application to browse and explore GitHub public repositories, featuring offline caching and built for scalability using Clean Architecture and modern development practices.

---

## 📱 App Demo

<table>
  <tr>
    <td><img src="https://github.com/AElkhami/ABN-RepoViewer/blob/develop/paging.gif" width="250"/></td>
    <td><img src="https://github.com/AElkhami/ABN-RepoViewer/blob/develop/details.gif" width="250"/></td>
  </tr>
</table>

---

## 🧠 Overview

This project showcases a scalable and modular GitHub repository viewer app, focusing on production-grade architecture, offline-first design, and modern Android development with Jetpack Compose.

While the app may appear simple at first glance, it has been intentionally designed to demonstrate advanced patterns that support real-world enterprise scalability and maintainability.

---

## 🧱 Architecture Decisions

The app follows **Clean Architecture** principles with distinct **presentation**, **domain**, and **data** layers. This ensures high modularity, testability, and long-term maintainability.

### 🔁 State and Interaction Management

The app uses a **hybrid MVVM/MVI** pattern:

- **State Management:**  
  Each screen holds a single immutable UI state object representing the entire screen state, ensuring predictability and consistency.

- **User Interactions:**  
  ViewModels expose clear methods to handle actions. The combination of state + event makes testing and UI updates deterministic.

This model supports full **configuration change safety** and clean unidirectional data flow.

---

## 🧩 Modularity

The project is structured using a hybrid **feature-based** and **component-based** modularization strategy, achieving:

- **Faster build times**
- **Clear separation of responsibilities**
- **Improved test isolation**
- **Ease of future scaling**

More about the approach [here](https://medium.com/@ahmedeelkhami/multi-module-architecture-in-android-5f76373a84a7).

---

## 🛠️ Design Decisions

Several additional enhancements were made to reflect modern development best practices:

- **Jetpack Compose UI**  
  The UI is built entirely using declarative Compose, with reusable and consistent Composables.

- **Image Loading with Coil**  
  Fast, lightweight image loading with lifecycle awareness.

- **Networking with Ktor**  
  Designed with multiplatform compatibility in mind, making future migration to Kotlin Multiplatform (KMM) seamless.

- **DI with Koin**  
  Lightweight dependency injection, ideal for modular projects and potential KMM adoption.

- **Offline Caching**  
  Data is stored locally using Room and Paging3, allowing seamless offline access.

- **Network Awareness**  
  The app automatically detects connection loss and serves cached data, refreshing automatically upon reconnection.

---

## 🚀 Features

- 🔍 **Browse GitHub Repositories**  
  Smooth, paginated repository list.

- 📂 **Detailed Repository View**  
  View full repo name, visibility, description, and owner avatar.

- 🌐 **Open in Browser**  
  Directly navigate to the GitHub page.

- 📡 **Offline Support**  
  Uses Room with RemoteMediator to cache data.

- ⚡ **Network-Aware Updates**  
  Automatically refreshes when network returns.

- 🎨 **Modern UI**  
  Jetpack Compose, Material 3, and support for dark mode and Material You.

---

## 🔧 Technologies

| Layer         | Libraries |
|---------------|-----------|
| UI            | Jetpack Compose, Material3, Coil |
| DI            | Koin |
| Networking    | Ktor |
| Persistence   | Room, Paging 3 |
| Asynchronous  | Kotlin Coroutines, Flow |
| Architecture  | MVVM + MVI, Clean Architecture |
| Modularization| Multi-module hybrid |

---

## ✅ Testing

This project primarily includes **UI tests**, as most of the core logic is managed by the Paging 3 library, which internally handles data loading, caching, and pagination state management.

- **UI Tests:** Key Composables are tested using `createComposeRule` to ensure correct UI rendering and user interaction behavior across different states (loading, error, success).

Since the Paging 3 library abstracts away much of the data and state handling, additional unit or integration tests were not essential for the current scope.

---

## 🧭 UI & Theming

- **Responsive layout** with consistent padding, scalable text, and clean animations.
- Fully **localization-ready** — new languages can be added without code changes.

---

## 🧱 Things I'd Do to Take This to Production

If this app were to be deployed at scale, here are the next steps:

- ✅ Setup navigation with `Navigation-Compose` and destination arguments.
- ✅ Add Firebase Crashlytics and Analytics for observability.
- ✅ Add certificate pinning and enforce HTTPS.
- ✅ Abstract logging layer to swap out Timber or integrate structured logging.
- ✅ Add CI/CD using GitHub Actions or Bitrise with linting, tests, and artifact upload.
- ✅ Integrate Play Integrity API or SafetyNet for secure distribution.

