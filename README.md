# FilmsApp 
This app is still **in progress**.

Below, you can read about things ***already done*** and those ***to be done soon***.
## Aim
The main **aim** of this app is to **show a list of movies** and **make simple operations on** that **list**, like adding movies to the favourite movies list and searching them by title or filters.

My **main goal** is to **learn different concepts** by making this application.

The **attention** has been **focused** on the **code and architecture quality**, as well as on **the appearance of the user interface**.

## Application Guide
After launching the app, you can search movies on a **Find screen** or change the screen by clicking on a *Filter* button at the bottom of a screen to use filters.

## Things done
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

## Things to do
All things to do in the near future:
- adding repositories
- writing unit tests
- creating a new screen with the user's favourite movies
- adding an X button on an EditText to remove the written sentence
- adding more filters to find movies
