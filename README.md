# Zomboid Storm

[![build](https://github.com/mlem/storm/actions/workflows/java-ci.yaml/badge.svg)](https://github.com/mlem/storm/actions/workflows/java-ci.yaml)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.pzstorm/storm-api?color=blue&logo=Apache%20Maven)](https://search.maven.org/search?q=g:%22io.github.pzstorm%22%20AND%20a:%22storm-api%22?color=informational) 
[![License](https://img.shields.io/github/license/pzstorm/storm?logo=gnu)](https://www.gnu.org/licenses/) 
[![Discord](https://img.shields.io/discord/823907021178798150?color=7289DA&label=discord&logo=discord&logoColor=white)](https://discord.gg/ZCmg9VsvSW)

Zomboid Storm is a Java modding toolchain for [Project Zomboid](https://projectzomboid.com/blog/).

![banner](https://raw.githubusercontent.com/pzstorm/storm/gh-pages/images/storm-banner.png)

## Introduction

Since the early days of Project Zomboid there was only ever two ways of modding the game; with Lua using the official API or with Java by modifying and recompiling game classes. For an in-depth analysis of Lua and Java modding you should read the [Unofficial Guide to Modding Project Zomboid](https://github.com/cocolabs/pz-modding-guide#writing-code). For now let's take a quickly look at and compare both approaches:

- Lua modding is the officially supported way of modding. It is easy to learn and start with but has very limited scope of possible implementation and is much more difficult to debug when things eventually go wrong. It also has serious limitations due to simplistic language architecture. 

- Java modding is more difficult to learn and start with but has a nearly unlimited scope of possible implementation and is very easy to inspect and debug during runtime. In addition to this, Java modders never had a way to interact with the game code in a standardized way (leading to mod incompatibility) or a way to load the mods in game (leading to limitations on how the mod can be used and distributed).

Until now the preferred way of modding the game was with the use of Lua API, and looking at the brief comparison above it is clear why. It might seem that when choosing between these approaches you are choosing between usability and scope, but that is incorrect. From the community perspective you are choosing between developing mods for yourself or others, since Java mods were always incompatible with the whole concept of community modding.

This is all in the past, now that Storm is here. Mod developers can now use a community supported API to write Java mods without worrying about core compatibility issues, as well as an integrated mod loading system to load Java mod classes during game execution. For more information read the [Features](#features) section to see an overview of all features currently implemented by Storm.

## Features

- Comprehensive and easy to use API that allows modders to write fully functional Java mods.
- 186 custom game events available to subscribe to when creating mods.
- Integrated mod loader system that allows loading mod classes during game runtime.
- Log4j 2 logging system with custom layout that embeds the time and date to each logged message.

## Installation

- Download the [latest release](https://github.com/pzstorm/storm/releases/latest) from the project's Github repository.
- Extract the archive in your Project Zomboid root directory.

When updating an existing version remember to remove the contents of the old version before extracting the new version, as to avoid having redundant and possibly conflicting files in your installation directory. In future Storm versions this process will be automated for you.

## How to use

To launch Storm simply run one of the launch script available in `bin` directory.
Choose the launch script that corresponds to your operating system and system architecture:

- 32-bit Windows - `storm_win32.bat`
- 64-bit Windows - `storm_win64.bat`
- Any Linux distribution - `storm_linux.sh`

Place the mods you want Storm to load in `~/Zomboid/mods` directory as you would normally do when installing local mods. Note that mods have to be assembled in `jar` files, loose mod loading is currently not supported.

Here is an example of a correct Java mod directory structure:

```
├── default.txt
└── sample
    ├── mod.info
    └── sample.jar
```

If you are a mod developer interested in developing Storm mods see [For developers](#for-developers) section.

## For developers

This section is intended for those interested in developing Storm mods or helping develop the Storm framework. 

### Components

Storm is comprised of the following integrated components:

- `StormBootstrap` - bootstraps everything needed to launch the game with static initialization.
- `StormLauncher`-  servers as an application entry point and starts Project Zomboid.  
- `StormClassLoader` - used to define, transform and load Project Zomboid classes.
- `StormModLoader` - responsible for loading mod metadata files and classes.
- `StormModRegistry` - responsible for creating and registering mod main class instances.
- `StormEventDispatcher` - responsible for registering event handlers and dispatching event instances.

### Metadata

Mods are detected by the presence of metadata in mod directory. Metadata has to be written in a `mod.info` file and included in the distribution. If a metadata file is not present in the mod directory the mod will not be loaded.

Here is an example of a valid `mod.info` file:

 ```
 name=Sample mod
 poster=poster.png
 description=This is a sample mod.
 id=pz-sample-mod
 url=https://github.com/pzstorm/sample-mod
 modversion=0.1.0
 pzversion=41.50-IWBUMS
 ```

The minimum requirement for valid metadata is the `name` property which has to be defined. Currently other properties are ignored, but support for more metadata definitions will be implemented in future releases.

### Events

Events are object instances that were created by in-game hooks. Events can have class fields that contain additional information about event context, which is very helpful when handling events. All events are located in `io.pzstorm.storm.event.*` package.

*Example: an event can be used to preform an action after an item is consumed.* 

Storm uses a dispatcher system to receive event calls from game and dispatch them to event handlers. Before mods can start receiving events then need to register an event handler. The following section explains how to register an event handler.

#### Event handler

Event handlers need to be registered from `ZomboidMod#registerEventHandlers` method. Multiple event handlers can be registered by a single mod, although this should be rarely needed and should be done mostly to organize different events into categories.

To register an event handler when subscribed methods are static methods:   

```java
@Override
public void registerEventHandlers() {
    StormEventDispatcher.registerEventHandler(EventHandler.class);
}
```

To register an event handler when subscribed methods are instance methods:   

```java
@Override
public void registerEventHandlers() {
    StormEventDispatcher.registerEventHandler(new EventHandler());
}
```

There is no practical difference between registering event handlers in static or instance context. The only difference is that registering event handler instances allows you more flexibility as more then one event handler of the same class can be registered using the event dispatcher.

All methods in registered event handlers annotated with `SubscribeEvent` annotation will be called by the dispatcher when an appropriate event is created. Each subscribed method has to have **exactly one parameter** that matches the type of event it wants to subscribe to. For example if a method wanted to subscribe to `OnRenderEvent` it would define itself in one of two ways depending on the handler registration method used:

- Subscribe to `OnRenderEvent` from static context:

  ```java
  // handler must be registered as a class
  public static void handleRenderEvent(OnRenderEvent event) {
  	...
  }
  ```

- Subscribe to `OnRenderEvent` from instance context:

  ```java
  // handler must be registered as an instance
  public void handleRenderEvent(OnRenderEvent event) {
  	...
  }
  ```

**Do not mix static and instance subscribed methods**. Doing so will cause an exception and crash the game. Registered handler has to have all subscribed methods declared as either static or instance methods depending on the method use to register the handler.

## Contribute

Anyone can contribute to the Storm project, here are a few ways to start:

- **Help test Storm** - Simply launch and play the game using Storm. You can play with or without Lua and/or Java mods, but note that there is no need to install any mods, just running the game with Storm is enough to test the core framework. If you come across any issue please report them using the project [issue tracker](https://github.com/pzstorm/storm/issues/new). This helps developers know what issues they need to address in future releases.
- **Create mods** - Create and publish Java mods that use the Storm framework. 
- **Spread the word** - Tell all your friends about the Storm project and how it changed your life (for the better).
- **Join the community** - Join other Storm enthusiasts on [Discord](https://discord.gg/ZCmg9VsvSW) and discuss the future of this project.

## License

This project is licensed under [GNU General Public License v3.0](https://github.com/pzstorm/storm/blob/master/LICENSE).
