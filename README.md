# Restaurants
Restaurants app written in kotlin, displays a list of restaurants and meals

# Features:
- connects to restaurants API to fetch data using retrofit and cache it locally using room.
- each restart it connect to the API and gets what was updated recently.
- displays a list of restaurants, when click on specific restaurant it display its meals 

<br />

# Tech Stack

- Kotlin

- Architecture
    - Clean Architecture
    - MVVM Architecture
    - Repository Pattern

- Dependency Injection
    - Dagger 2

- Reactive Programming
    - RxJava
    - LiveData
 
- Room
    
- Testing
    - Unit Tests (local and Instrumentation Tests)
    - Mockito
    - MockWebServer
    - Ui Testing (Espresso)
    
- Networking
    - Retrofit
    - REST APIs / JSON

- SOLID

- Other
    - View Binding
    - Gson
    - Glide

<br />

# Screenshots:

<p float="left">
  <img src="screenshots/restaurants.png" width="30%" />
  <img src="screenshots/meals.png" width="30%" />
  <img src="screenshots/meal-details.png" width="30%" /> 
</p>
<br />

# License & Copyright
Copyright (c) 2021 **Muhammad-2021**

 [MIT License](LICENSE)
