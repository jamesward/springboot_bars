Spring Boot Bars
----------------

Dev:
```
./gradlew -t classes
./gradlew bootTestRun
```

External Postgres:
```
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost/postgres
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=password
./gradlew bootRun
```

JDK Container Build:
```
./gradlew bootBuildImage
```

Run Container:
```
docker run -it --network host \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://localhost/postgres -eSPRING_DATASOURCE_USERNAME=postgres -eSPRING_DATASOURCE_PASSWORD=password \
  springboot_bars
```
