# Contribute

If you are interested in contributing to this repo
feel free to open PRs which should cover following criteria:
* (list of criteria here)

## Development setup

You will need 
* at least JDK 1.8
* zomboid installed

First you need to setup your project

Run
```bash
### if you haven't installed it yet, run
# sdk install java 17.0.3-tem

sdk use java 17.0.3-tem

###  set your game directory
./gradlew setGameDirectory

### creates run configurations for starting the mod loader
./gradlew createRunConfigurations

### imports original game classes to build folder
./gradlew zomboidClasses

### packs those classes into a jar file, which we use in build.gradle
./gradlew zomboidJar

### 
```