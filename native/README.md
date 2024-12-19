to build the docker image for cloud usage with eg kubernetes:
```sh
./gradlew bootBuildImage 
```
at the end of the output you'll have the name of the docker image which you can run using

```sh
docker run --rm -p 8080:8080 docker.io/library/native:0.0.1-SNAPSHOT
```

This should start up the application very quickly.

## Including static files and non Bean classes

We would need a class like

```java
package example.cashcard;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.lang.Nullable;

@SpringBootApplication
// the following includes non Bean classes
@RegisterReflectionForBinding({ CashCardDto.class, UserDto.class })
// the following includes RuntimeHints, which allow us to include static files
@ImportRuntimeHints(CashCardApplication.Hints.class)
public class CashCardApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashCardApplication.class, args);
	}

	static class Hints implements RuntimeHintsRegistrar {
		@Override
		public void registerHints(RuntimeHints hints, @Nullable ClassLoader classLoader) {
			hints.resources().registerPattern("cashcard-banner.txt");
		}
	}

}
```
