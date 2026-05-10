# Architecture Menufit — Android

## Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Database**: Room (SQLite)
- **DI**: Hilt
- **Navigation**: Jetpack Compose Navigation
- **Coroutines**: Kotlin Coroutines + Flow

## Project Structure

```
app/
├── src/main/
│   ├── kotlin/com/menufit/app/
│   │   ├── ui/                  # Compose UI screens & components
│   │   ├── data/
│   │   │   ├── database/        # Room database setup
│   │   │   │   ├── entity/      # Entity classes
│   │   │   │   └── dao/         # Data Access Objects
│   │   │   └── repository/      # Data repositories
│   │   ├── domain/
│   │   │   ├── model/           # Domain models
│   │   │   └── usecase/         # Business logic
│   │   ├── viewmodel/           # ViewModels for UI state
│   │   └── di/                  # Dependency Injection (Hilt)
│   └── res/                     # Resources
└── build.gradle.kts
```

## Layer Pattern

```
UI (Compose)
    ↓
ViewModel (State Management)
    ↓
Repository (Data Source)
    ↓
Database (Room/SQLite)
```

## Local-First Data Flow

1. **All data lives in Room/SQLite locally**
2. **No network calls on startup** — app ready in < 300ms
3. **Search is fulltext indexed** (FTS5) for instant results
4. **History & collections are persisted locally**
5. **Premium features (future)**: optional cloud sync with user consent

---

More to come as development progresses.
