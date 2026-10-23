# travel bucket list

A web application that helps users save their dream travel destinations and track the places they have visited. Users can register, build a personal bucket list, add pictures and ratings, mark destinations as visited or pending, and see their overall travel progress on a dashboard.

Built as part of Industry Practice (FA I and FA II) by Stuti Mishra (Roll 65) and Sumedh Mali (Roll 64), TT AI&DS - C.

## stack

java 17, spring boot, spring web, spring data jpa, hibernate, mysql, maven, html, css, javascript

## deployment

runs locally:

- frontend: http://localhost:8080 (served by spring boot)
- backend/database:
  - spring boot rest api
  - mysql (`travel_db`, created automatically)

## how to run

1. install jdk 17+, maven and mysql
2. set your mysql username and password in `src/main/resources/application.properties`
3. run `mvn spring-boot:run`
4. open http://localhost:8080
5. (optional) run `dummy-data.sql` in mysql to load demo users and destinations. login: `stuti` / `sumedh`, password `1234`

## features

- secure user registration and login with bcrypt password hashing
- personal bucket list for every user
- add, view, edit and delete destinations
- visited and pending status tracking
- search, category filter and status filter
- destination pictures from your own link or auto-fetched from wikipedia
- 1-5 star ratings
- google maps link for every destination
- travel statistics dashboard with progress bar
- dark mode and responsive UI
- input validation and json error messages

## visit tracking logic

Each destination has a visited flag:

- new destinations start as **pending**
- one click marks a destination **visited**, and one click moves it back to **pending**
- statistics are calculated from these flags:
  - total destinations
  - visited count
  - pending count
  - progress percentage

## project structure

### backend

- **model**: `User` and `Destination` JPA entities.
- **repository**: Spring Data JPA interfaces for database operations.
- **service**: `AuthService` handles register, login and tokens. `BucketListService` handles CRUD, filtering, visit status, stats and ownership checks.
- **controller**: `AuthController` and `BucketListController` expose the REST APIs. `ApiExceptionHandler` returns errors as JSON.

### frontend

- **index.html**: single page with login, dashboard, add/edit form, filters and destination cards. CSS and JavaScript are included in the same file and talk to the backend using fetch.

### database

- **users**: id, username, password (hashed)
- **destination**: id, name, city, country, description, category, image_url, rating, visited, user_id

Each destination belongs to one user, so lists stay private.

## api endpoints

send `Authorization: Bearer <token>` after login.

| method | endpoint | purpose |
|---|---|---|
| POST | /api/auth/register | register a new user |
| POST | /api/auth/login | login and get a token |
| GET | /api/bucket-list | list destinations (`?search=&category=&status=`) |
| POST | /api/bucket-list | add a destination |
| PUT | /api/bucket-list/{id} | update a destination |
| DELETE | /api/bucket-list/{id} | delete a destination |
| GET | /api/bucket-list/visited | visited destinations |
| GET | /api/bucket-list/pending | pending destinations |
| PUT | /api/bucket-list/{id}/visited | mark as visited |
| PUT | /api/bucket-list/{id}/pending | mark as pending |
| GET | /api/categories | beach, mountain, city, historical, other |
| GET | /api/stats | total, visited, pending, progress |

## security

- passwords are hashed with bcrypt and never returned by the api
- every bucket list endpoint needs a valid login token
- users can only view, edit or delete their own destinations
- user text is escaped on the frontend to prevent html injection

## future scope

- spring security with jwt authentication
- swagger/openapi documentation
- travel dates, notes and reviews
- budget planning and trip reminders
- statistics charts and export of saved lists
- cloud deployment

## o/p demo ss

<img src="op-1.png">
<img src="op-2.png">
<img src="op-3.png">
<img src="op-4.png">
