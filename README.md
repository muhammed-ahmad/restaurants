# Restaurants
Restaurants app displays a list of resturants and meals
- it uses a backend api to get data and cache it locally using room.
- it allow filtering the current meals and resturants list.
- each restart it connect to the backend and gets what was updated recently.

<br />

# Screenshots:

<br />
<p float="left">
  <img src="screenshots/restaurants.png" width="45%" />
  <img src="screenshots/meals.png" width="45%" /> 
</p>
<br />
<p float="left">
  <img src="screenshots/meals-search.png" width="45%" />
  <img src="screenshots/meal-details.png" width="45%" /> 
</p>
<br />

# Tech Stack

- Kotlin (main branch), Java (java branch)

- Reactive Programming
    - RXjava
    - LiveData

- Other
    - View Binding
    - Gson
    - Glide
    
- Room

- Dependency Injection
    - dagger 2
    
- Testing
    - Unit Tests
    - Instrumentation Tests
    - Mockito
    
- Networking
    - Retrofit
    - REST APIs / JSON
    
- Architecture
    - MVVM Architecture (ViewModel - UseCases - Model)
    - No Repository pattern
    
- SOLID

<br />

# TODO
- hilt
- fragments instead of activities
- may be add login and register screens

<br />

# License & Copyright
Copyright (c) 2021 **Muhammad-2021**

 [MIT License](LICENSE)
