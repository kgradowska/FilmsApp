# FilmsApp 
## Aim
The main **aim** of this app is to **show a list of movies** and **make simple operations on** that **list**, like adding movies to the favourite movies list and searching them by title or filters.

My **main goal** is to **learn different concepts** by making this application.

The **attention** has been **focused** on the **code and architecture quality**, as well as on **the appearance of the user interface**.

[Watch the video](https://youtube.com/shorts/3ZX-zb0Cwqw?feature=share)

## Application Guide
After launching the app, you can search movies on a **Search screen** or change the screen by clicking on a *Filter* button at the bottom of a screen to use filters.

## Migration & Modernization
The 2.0 version of this project represents a complete overhaul of the app's foundation. Key improvements include:
- UI Framework: Full migration from XML Layouts & ViewBinding to Jetpack Compose (Material 3).
- Navigation: Replaced Fragments and SafeArgs with Type-safe Compose Navigation (powered by kotlinx-serialization).
- Storage: Transitioned from SharedPreferences to the modern Jetpack DataStore (Preferences) for better performance and thread safety.
- Dependency Injection: Expanded Koin modules with full Compose integration (koin-androidx-compose).
- Images: Switched from Glide to Coil, which is natively optimized for Jetpack Compose.
- Data Handling: Introduced Immutable Collections to optimize Compose recomposition and ensure UI stability.

## Things previously done (before the migration)
All things which are done up to now:
- usage of data from TMDB API: https://developer.themoviedb.org/docs/getting-started
- implementing MVVM and single activity architecture
- usage of View Binding
- splitting code into packages: data, domain and presentation (Clean Architecture)
- Koin - dependency injection (Singleton and Factory design patterns used)
- Moshi - parsing elements from Json to ClassDTO (Data Transfer Object)
- Kotlin Flow & Coroutines
- Retrofit + OkHttp (REST API)
- Bottom Navigation View
- Splash screen
- searching movies by a title or keywords on a Find screen
- searching movies by filters on a Filter screen
- using Recycler Views to show movies 
- making a layout for movie details
