# Spring Boot Bars

A Kotlin Multiplatform application with:

- a shared `Bar` model and typed Kilua RPC contract in `shared`
- a Kilua Compose frontend compiled to Kotlin/Wasm in `web`
- a Spring Boot WebFlux and R2DBC PostgreSQL backend in `server`

The Spring server packages and serves the generated Wasm application. JTE and handwritten browser JavaScript are no longer used.

## Requirements

- JDK 25 (the Gradle toolchain resolver can provision it automatically)
- Docker for the Testcontainers integration tests
- PostgreSQL when running the application locally

## Development

Configure PostgreSQL:

```bash
export SPRING_R2DBC_URL=r2dbc:postgresql://localhost/postgres
export SPRING_R2DBC_USERNAME=postgres
export SPRING_R2DBC_PASSWORD=password
```

Run a continuous JVM/Wasm build in one terminal:

```bash
./gradlew -t :server:classes
```

Run Spring Boot in another terminal:

```bash
./gradlew :server:bootRun
```

Open <http://localhost:8080>. The server serves `index.html`, `web.js`, and the generated `.wasm` file and exposes generated Kilua RPC endpoints under `/rpc`.

## Tests

```bash
./gradlew :shared:jvmTest :server:test
./gradlew :web:wasmJsTest
```

The server tests start PostgreSQL with Testcontainers.

## Production build

Build the optimized Wasm frontend and executable Spring Boot JAR:

```bash
./gradlew :server:bootJar
java -jar server/build/libs/server-1.0.0-SNAPSHOT.jar
```

Build an OCI image:

```bash
./gradlew :server:bootBuildImage
```

Run it with a host PostgreSQL instance:

```bash
docker run --rm --network host \
  -e SPRING_R2DBC_URL=r2dbc:postgresql://localhost/postgres \
  -e SPRING_R2DBC_USERNAME=postgres \
  -e SPRING_R2DBC_PASSWORD=password \
  springboot_bars
```

## Useful frontend tasks

```bash
./gradlew :web:wasmJsBrowserDevelopmentWebpack
./gradlew :web:wasmJsBrowserProductionWebpack
```
