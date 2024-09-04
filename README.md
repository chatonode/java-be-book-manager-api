# Spring Books API

## Property File Examples
### `application.properties`
```properties
## General
spring.application.name=bookmanagerapi

# Activating Amazon RDS property file
spring.profiles.active=rds
```

### `application-rds.properties`
```properties
# Amazon RDS - PostgreSQL

spring.jpa.database=postgresql
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://<OUR-DOMAIN-NAME>:<OUR-DOMAIN-PORT>/bookshop
spring.datasource.username=<OUR-USERNAME>
spring.datasource.password=<OUR-PASSWORD>
spring.jpa.database-platform=org.hibernate.dialect.PostgresPlusDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

```