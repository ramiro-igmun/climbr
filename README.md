# climbr
Climbr is a social network project made using Spring Boot.

* **Backend:**
 
  Maven, Spring MVC, Spring data JPA and Spring Security
  H2 embedded database used for development

* **Frontend:**

  The frontend is rendered server-side using Thymeleaf and Bootstrap.
  
  There are some mock users and posts added to the database for testing purposes:
  
  * User: _Jon_ Password:_jonny_
  * User: _Eva_ Password:_evita_
  * User: _Ana_ Password:_anita_
  * User: _Joaquin_ Password:_joako_

  
1) **Running the app**
  
    You can run the app from the command line after installing Maven on your computer with the following command
    from the project folder:
    
    ```
    mvn spring-boot:run
    ```
       
    Press `Ctrl+c` to stop it.
    
1) **Heroku:**

    The app is deployed in Heroku, you can test it at [appclimbr.herokuapp.com](https://appclimbr.herokuapp.com)
    
    The database volatile and resets every session.
  
    ![alt text](https://github.com/ramiro-igmun/climbr/blob/master/screenshot.png "Climr")
    
    TODO: Post pagination and commit unit tests.
