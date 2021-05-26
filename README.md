# Zomboid Storm

[![build](https://github.com/pzstorm/storm/actions/workflows/java-ci.yaml/badge.svg?branch=dev)](https://github.com/pzstorm/storm/actions/workflows/java-ci.yaml) [![License](https://img.shields.io/github/license/pzstorm/storm?logo=gnu)](https://www.gnu.org/licenses/) [![Discord](https://img.shields.io/discord/823907021178798150?color=7289DA&label=discord&logo=discord&logoColor=white)](https://discord.gg/ZCmg9VsvSW)

Zomboid Storm is a Java modding toolchain for [Project Zomboid](https://projectzomboid.com/blog/).

![banner](https://raw.githubusercontent.com/pzstorm/storm/gh-pages/images/storm-banner.png)

## Introduction

Since the early days of Project Zomboid there was only ever two ways of modding the game; with Lua using the official API or with Java by modifying and recompiling game classes. For an in-depth analysis of Lua and Java modding you should read the [Unofficial Guide to Modding Project Zomboid](https://github.com/cocolabs/pz-modding-guide#writing-code). For now let's take a quickly look at and compare both approaches:

- Lua modding is the officially supported way of modding. It is easy to learn and start with but has very limited scope of possible implementation and is much more difficult to debug when things eventually go wrong. It also has serious limitations due to simplistic language architecture. 

- Java modding is more difficult to learn and start with but has a nearly unlimited scope of possible implementation and is very easy to inspect and debug during runtime. In addition to this, Java modders never had a way to interact with the game code in a standardized way (leading to mod incompatibility) or a way to load the mods in game (leading to limitations on how the mod can be used and distributed).

Until now the preferred way of modding the game was with the use of Lua API, and looking at the brief comparison above it is clear why. It might seem that when choosing between these approaches you are choosing between usability and scope, but that is incorrect. From the community perspective you are choosing between developing mods for yourself or others, since Java mods were always incompatible with the whole concept of community modding.

This is all in the past, now that Storm is here. Java mods can now use a community supported API to write their code without worrying about core compatibility issues, as well as an integrated mod loading system to load their classes during game execution. For more information read the [Features](#features) section to see an overview of all features currently implemented by Storm.

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

