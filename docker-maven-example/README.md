# Docker and Maven example

## Description

This application uses maven and has an example Dockerfile which shows hwo to containerize the application.

## FAQ

### How do I build the application?

```sh
./mvnw package
```

### How do I start the application?

After having built the application you can use:

```sh
docker-compose up
```