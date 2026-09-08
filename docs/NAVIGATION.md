# Navigation 3 notes

## This project

`presentation/navigation/AppDestination.kt` defines serializable `NavKey` values. `VehicleCareApp` owns the back stack:

```kotlin
val backStack = rememberNavBackStack(GarageDestination)
backStack.add(VehicleDetailDestination(vehicleId))
NavDisplay(backStack = backStack, entryProvider = entries)
```

The state is deliberately hoisted to the app shell. Feature `Route` files receive only navigation lambdas; they never construct routes from strings.

## Navigation Compose 2 vs Navigation 3

| Concern | Navigation Compose 2 | Navigation 3 in this case |
|---|---|---|
| Graph owner | `NavController` | App-owned `rememberNavBackStack` |
| Destination | typed `composable<T>()` or route string | serializable `NavKey` |
| Rendering | `NavHost` | `NavDisplay` + `entryProvider` |
| Large-screen evolution | custom graph/layout coordination | `Scene` / `SceneStrategy` can render more than one entry |
| Route role | VM/state/effects/nav callbacks | unchanged |

## Adaptive next step

For a tablet, retain the same keys and replace the single-pane `NavDisplay` arrangement with the Material 3 Adaptive Navigation 3 artifact (`adaptive-navigation3`). A list-detail scene can display `GarageDestination` and a selected `VehicleDetailDestination` together. This is why a typed key contains only the stable identifier, never a full `Vehicle` object.

## Back and deep links

The example uses `BackHandler` to pop from the app-owned stack. Navigation 3 also provides type-safe deep-link matchers. In a production app, map an external vehicle URI to `VehicleDetailDestination(vehicleId)` only after validating that the signed-in account owns the vehicle.
