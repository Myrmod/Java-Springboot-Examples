# Gradle Tutorial

## About

`gradle` to Java basically is like `npm` to JavaScript. It's a package manager, a build tool and provides ways to manage the repository. 

## settings.gradle

This is the configuration of this "monorepository", it tells gradle, where the applications are

## FAQ

### How do  I build the application?

`gradlew` will call the included `gradle` jar, this way we have the right build tool on hand, without need to install anything.
You build using 

```sh
./gradlew build
```

### I get a permissions error

```sh
chmod +x gradlew
```

### How can I clean all the build artifacts?

```sh
./gradlew clean
```

### How can I run tests?

```sh
./gradlew test
```

The code will be hashed so that nothing runs unnecessarily.

### I don't want to run all the apps, but a specific one, how?

To run a command like `build` on a specific application like `my-webapp` you can simply add the parameter `-p` followed by the directory like:

```sh
./gradlew build -p my-webapp
```