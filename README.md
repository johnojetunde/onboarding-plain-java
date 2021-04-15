# Onboarding project

## Tasks

1. Find at least two API to use in the project.
They should return similar information so it could be merged together.

2. Implement users module

As a user I would like to be able to register, login and remove my account (GDPR) from the system.

Requirements:
- Use validation (JSR 380)
- JDBC for persistence
- Json parsing using JSR 374
- Use Lombok

3. Retrieve and store information from 3rd party API

Retrieve information from at least two sources, merge it and store it in the database.

Information should be cached for some time and updated using worker.

Requirements:
- Use Apache HttpClient
- Json parsing using JSR 374
- Lombok
- JDBC for persistence

4. Create API to expose stored information

Requirements:
- REST
- Documented with Swagger
- Endpoints are secured and only authenticated users have access

## Docker

Use `docker-compose up -d` to start local mysql database.
