# FilmsApp 
This app is still **in progress**.

Below you can read about things ***already done*** and those ***to be done soon***.
## Aim
The main **aim** of this app is to **show a list of movies** and **make simple operations on** that **list**, like adding movies to the favourite movies list and searching films by title.

My **main goal** is to **learn different concepts** by making this application.

The **attention** has been **focused mainly** on the **code and architecture quality**, **not the appearance of the user interface**.

## Application guide
After launching the app, you can click on an *OPEN LIST* button to see a list of movies.

## Things done
All things which are done up to now:
- implementing MVVM architecture (creating an activity, fragments, view models and layouts)
- usage of View Binding
- splitting code into packages: data, domain and presentation
- using recycler view to show movies 
  - making a layout for one element
  - making movie (element) clickable
- Koin - dependency injection (Singleton and Factory design patterns used)
- Moshi - parsing elements from Json to ClassDTO (Data Transfer Object)
- StateFlow - observing movieList
- retrofit + okhttp (REST API)
- searching movies by a title or keywords


## Things to do
All things to do in the nearest future:
- light mode (dark mode is almost done)
- adding new filters to search movies (for example, searching by the genres)
- changing the way of searching movies (this will probably be on a different layout)
