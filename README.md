# CRUD Killer Framework

A comprehensive Java 21 Spring Boot framework that eliminates boilerplate CRUD code.

[![](https://jitpack.io/v/kasunmadhumal/crud-killer.svg)](https://jitpack.io/#kasunmadhumal/crud-killer)
[![Java 21](https://img.shields.io/badge/Java-21-orange)](https://openjdk.java.net/projects/jdk/21/)
[![Spring Boot 3.2](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen)](https://spring.io/projects/spring-boot)

## Quick Start

### Installation
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.kasunmadhumal:crud-killer:1.0.0'
}
```

### Usage
1. Create entity implementing `BaseEntity<ID>`
2. Create repository extending `BaseRepository<Entity, ID>`
3. Create service extending `BaseService<Req, Res, Entity, ID>`
4. Create controller extending `BaseController<Req, Res, Entity, ID>`

Zero boilerplate CRUD operations with automatic REST endpoints!

## Features
- Generic CRUD operations
- Automatic validation
- Search and filtering
- Partial updates
- Java 21 ready
- Spring Boot 3.x compatible

## Requirements
- Java 21+
- Spring Boot 3.2+
- Gradle 8.5+ or Maven 3.6+
