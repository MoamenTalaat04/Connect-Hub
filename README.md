# ConnectHub

ConnectHub is a Java Swing social platform with local JSON persistence.

This project includes both:

- The original core social app features (auth, friends, profile, posts, stories, feed)
- A full architectural refactor that keeps legacy behavior while making the codebase more modular
- New major capabilities: groups, direct chat, and event-driven notifications

## Full Project Scope

### Original Baseline Features

- User registration and login
- Password hashing for credential storage
- Friend request lifecycle:
	sent, received, accept, reject, cancel, remove, block, unblock
- News feed that shows friends' posts and stories
- Story expiration behavior
- Profile management:
	profile photo, cover photo, bio, personal posts
- Desktop GUI flows built with Swing forms

### New Features Added

- Group system:
	create groups, join/leave, view members, post in groups
- Chat system:
	direct messaging and conversation history
- Notification system:
	notifications for messages, friend requests, posts, and group activity

## Architecture (Current)

I refactored the project into layered modules so responsibilities are clearer and future changes are safer.

- UI layer:
	Swing windows and form bindings
- Service layer:
	business use-cases and orchestration
- Repository layer:
	persistence abstraction and JSON implementations
- Domain model layer:
	entities and core data structures
- Event layer:
	publish/subscribe flow used by notifications
- Composition layer:
	centralized dependency wiring

Main modular packages:

```text
src/com/connecthub/
├── model/
├── repository/
├── repository/json/
├── service/
├── service/impl/
├── events/
├── factory/
└── util/
```

## Legacy + Modern Coexistence

To avoid breaking the existing windows and workflows, I kept legacy entry classes in `src/` and routed them to the new service modules through adapters.

Examples:

- Compatibility bridge:
	`ConnectHubContext`
- Mapping between legacy and modular models:
	`LegacyMapper`
- Legacy facades still usable by UI:
	`AccountManagement`, `FriendManagement`, `NewsFeed`, `ProfileManager`, `MainContentCreation`, `UserDatabase`

## Engineering Improvements

- Reduced duplicated logic (hashing, lookup, repeated persistence flows)
- Split large responsibilities into focused services/repositories
- Lowered coupling between UI classes and data access details
- Introduced cleaner dependency composition via `ConnectHubFactory`
- Kept behavior stable while migrating internals incrementally

## SOLID and Patterns Used

Applied SOLID principles across the modular layer:

- SRP:
	each service/repository focuses on one concern
- OCP:
	interfaces allow extensions with minimal caller changes
- LSP:
	high-level logic targets abstractions
- ISP:
	feature-based, focused service interfaces
- DIP:
	dependency wiring handled at composition boundaries

Design patterns used:

- Repository pattern
- Factory pattern
- Observer / publish-subscribe pattern
- Facade pattern (for legacy compatibility)

## Data Files

Project data is persisted in local JSON files:

- `users.json`
- `posts.json`
- `stories.json`
- `groups.json`
- `conversations.json`
- `notifications.json`

## Run

1. Open the project in IntelliJ IDEA.
2. Ensure dependencies are configured.
3. Run `Main.java`.

