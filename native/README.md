to build the docker image for cloud usage with eg kubernetes:
```sh
./gradlew bootBuildImage 
```
at the end of the output you'll have the name of the docker image which you can run using

```sh
docker run --rm -p 8080:8080 docker.io/library/native:0.0.1-SNAPSHOT
```

This should start up the application very quickly.
