run:
	./mvnw spring-boot:run

test:
	./mvnw test

package:
	./mvnw package && java -jar target/realtime-api-0.1.0.jar

docker-build:
	@docker build -t realpad/realtime:latest .

docker-run:
	@docker run -p 8080:8080 realpad/realtime:latest