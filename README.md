## Realpad Realtime weather api

This is an api that returns the current weather for a given location. The backend is built with Java and Spring Boot, and the frontend is built with Vue.js.

<div style="text-align: center;">
  <img src="frontend/public/screenshot.png" alt="Screenshot of the frontend" style="max-width: 100%; height: auto;">
  <p>Screenshot of the frontend</p>
</div>

## How to run the project

- Using docker compose:
  - Clone the repository
  - build the `war` file by running `./mvnw clean package` or `make package` if you have `make` installed.
  - Run `docker-compose up` in the root directory
  - The frontend will be available at `http://localhost:5173`
  - The backend will be available at `http://localhost:8080`