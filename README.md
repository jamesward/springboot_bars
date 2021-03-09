Spring Boot Bars
----------------

Dev:
```
./gradlew -t classes
./gradlew bootRun
```

External Postgres:
```
export SPRING_R2DBC_URL=r2dbc:postgresql://localhost/postgres
export SPRING_R2DBC_USERNAME=postgres
export SPRING_R2DBC_PASSWORD=password
./gradlew run
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

GraalVM Config Gen (With the native-image-agent installed)
```
export SPRING_R2DBC_URL=r2dbc:postgresql://localhost/postgres
export SPRING_R2DBC_USERNAME=postgres
export SPRING_R2DBC_PASSWORD=password
export JAVA_HOME=~/bin/graalvm-ce-java8-21.0.0.2
export PATH=$JAVA_HOME/bin:$PATH
./gradlew bootJar
java -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image \
  -jar build/libs/springboot_bars.jar
```

GraalVM Native Image Container Build:
* Currently Broken *
```
./gradlew bootBuildImage -Pnative
```