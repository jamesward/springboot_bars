Spring Boot Bars
----------------

Dev:
```
./gradlew -t classes
./gradlew bootTestRun
```

External Postgres:
```
export SPRING_R2DBC_URL=r2dbc:postgresql://localhost/postgres
export SPRING_R2DBC_USERNAME=postgres
export SPRING_R2DBC_PASSWORD=password
./gradlew bootRun
```

JDK Container Build:
```
./gradlew bootBuildImage
```

Run Container:
```
docker run -it --network host \
  -e SPRING_R2DBC_URL=r2dbc:postgresql://localhost/postgres -eSPRING_R2DBC_USERNAME=postgres -eSPRING_R2DBC_PASSWORD=password \
  springboot_bars
```
