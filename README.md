# Vehicle Care Coach

An Android/Jetpack Compose case study for a vehicle maintenance companion. It is deliberately designed as a recruiter-friendly learning project: the UI is polished enough to demonstrate a product direction, while the source makes state, navigation and Clean Architecture boundaries easy to inspect.

## Case scope

The product helps a driver:

- keep a small garage of vehicles and choose a default vehicle;
- view time- and mileage-based maintenance work;
- update the odometer and mark a maintenance task as completed;
- track insurance, inspection and registration expiry dates;
- browse nearby mock service points, including opening status, ratings and specialties.

The first launch seeds Room with deterministic mock data. This means the app is useful without network access and every interaction writes back to the local source of truth.

## Run

1. Install Android Studio with JDK 17 and Android SDK Platform 37.
2. Open this folder and let Gradle sync.
3. Run the `app` configuration on an emulator or device.

The build uses AGP 9.4.0 / Gradle 9.6, Kotlin 2.2.21, `compileSdk = 37`, and Compose BOM `2026.08.00`. All versions live in `gradle/libs.versions.toml` so upgrades are explicit and reviewable.

## Architecture

```
presentation (Route → Screen) → domain (use case → repository interface) ← data (Room / mock seed)
                 ↑                         ↑
             UiEvent                  domain models
                 ↓
             ViewModel → immutable StateFlow<UiState>
```

The project stays intentionally in one `:app` module to keep the case readable, while retaining production-ready package boundaries:

```
app/             Application entry point
core/            UiText, UiLoadState, error mapping, theme
data/            Room entities/DAO, mock seed, repository implementation
domain/          models, repository contract, focused use cases
di/              Hilt bindings
presentation/    feature-oriented Contract, Route, Screen and ViewModel files
```

### Feature contract

Every feature follows the same convention:

- `*Contract.kt` contains an immutable `UiState` and sealed `UiEvent`.
- `*ViewModel.kt` receives user events, invokes use cases, and exposes a `StateFlow`.
- `*Route.kt` is the stateful adapter: it obtains the Hilt ViewModel, uses `collectAsStateWithLifecycle()`, then forwards callbacks.
- `*Screen.kt` is render-focused. It does not know about a repository, ViewModel or Navigation 3 back stack.

This makes the main data path easy to trace:

`Screen → UiEvent → ViewModel → UseCase → Repository → Room → StateFlow → Route → Screen`

`UiText` avoids leaking `Context` into a ViewModel. `ErrorMapper` centralizes `Throwable → AppError → UiText` and rethrows coroutine cancellation. The sample keeps a `BaseViewModel` with a shared message flow for transient messages; persistent loading/error state remains in `UiState`.

## Navigation: what is current here?

This case uses **Navigation 3** rather than a `NavController` graph. Destinations are `@Serializable` `NavKey` values (`GarageDestination`, `VehicleDetailDestination`, etc.). `VehicleCareApp` owns a `rememberNavBackStack`, adds typed keys for forward navigation, and hands the stack to `NavDisplay` through an `entryProvider`.

Why this is useful:

- the application owns the back stack as ordinary state;
- route arguments are typed (`VehicleDetailDestination(vehicleId)`), not string routes;
- the navigation key is serializable and therefore saveable;
- Navigation 3’s `NavDisplay`/Scene model can later support multi-pane, list-detail layouts without redesigning destination keys.

`docs/NAVIGATION.md` explains the main differences from Navigation Compose 2 and the next adaptive step.

## Offline-first and mock data

Room is the local source of truth. `MockData` is only a seed source—replace it with a Retrofit/Firebase sync worker without changing any feature contract. A remote refresh must write to Room; screens continue to observe Room. This preserves cached content when a refresh fails.

## Localization

`src/main/res/values/strings.xml` contains English text and `values-tr/strings.xml` contains Turkish text. `UiText.Resource` carries resource identifiers across non-UI layers, while Composables use `stringResource(...)`. Android’s system/per-app language selection chooses the active resources; no locale string is stored in a ViewModel.

## Testing targets

The structure is ready for:

- pure unit tests for due-date/mileage rules and error mapping;
- ViewModel tests with a fake `VehicleRepository`;
- DAO migration tests for Room;
- Navigation 3 back-stack tests for typed keys;
- Compose semantics tests for loading, empty, error and loaded UI states.

## Intentional next production steps

This is a case study, not a claims-ready vehicle record system. Add these before production:

1. remote synchronization plus WorkManager reminder scheduling and notification permission handling;
2. real location/map provider and authenticated service reviews;
3. encrypted document file storage, URI permissions, privacy policy and account deletion;
4. Room migrations, analytics consent, accessibility audit and CI (lint/unit/UI tests);
5. Navigation 3 adaptive `SceneStrategy` list-detail treatment for tablets and foldables.

## Official references

- [Android architecture recommendations](https://developer.android.com/topic/architecture/recommendations)
- [Navigation 3 getting started](https://developer.android.com/guide/navigation/navigation-3/get-started)
- [Navigation 3 releases](https://developer.android.com/jetpack/androidx/releases/navigation3)
- [Compose BOM guidance](https://developer.android.com/develop/ui/compose/bom)
- [Offline-first data layer](https://developer.android.com/topic/architecture/data-layer/offline-first)
