# Architecture decisions

## Why a repository interface lives in domain

The presentation layer depends on a small domain contract, not Room. `OfflineFirstVehicleRepository` is bound through Hilt and can later be replaced by a fake in tests or a synchronized implementation in production.

## Why Room receives mock data

Mock data is inserted exactly once and then UI observes DAO flows. This demonstrates the same source-of-truth behavior as a real remote sync: use cases do not expose DTOs or entities, and a failed network refresh does not erase a previously loaded screen.

## State versus one-off effects

Information the user must be able to recover after recreation belongs in `UiState` (loaded list, dialog visibility, loading/error condition). Ephemeral message delivery is isolated in `BaseViewModel.messages`. Do not make navigation, completed action or a Snackbar the only durable representation of business state.

## Why `Route` and `Screen` are split

The Route is allowed to know about Hilt, lifecycle collection and navigation callbacks. The Screen has plain state and lambdas, so it can be previewed and tested without an Android data source. This separation is especially valuable as a feature becomes adaptive or gains different entry points (deep link, notification, widget).
