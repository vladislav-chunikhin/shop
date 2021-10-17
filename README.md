# Test task

## Author
Vladislav Chunikhin

## Shop
Test task that requires create a Spring-boot service application with REST CRUD methods for the following entities:
Category, Product, Order and OrderItem.

- Category contains: name and a list of products.
- Product contains: price, sku and name.
- Order Item contains: “quantity” value and has connection to Product.
- Order contains: a list of order items and a total amount of the order.

In addition, there is report controller, which returns date and sum of income for each day in JSON format
(2016-08-22: 250.65, 2016-08.23: 571.12 ... etc).

Finally, Integrate ElasticSearch and add “search” method, which must return the list of orders by part of the product name.

## Technology stack
- Spring-boot
- Hibernate
- Liquibase
- PostgreSQL
- Swagger
- Test Containers
- Elasticsearch
- Docker

## Deployment description

1. [You should configure docker && docker compose](https://docs.docker.com/engine/install/)
2. [Manage Docker as a non-root user](https://docs.docker.com/engine/install/linux-postinstall/)
3. cd /deploy
4. run `sh shop-local-env-start.sh`


## Links

1. [Local swagger](http://localhost:8080/swagger-ui.html)

## Gradle tasks

1. Run integration tests `gradle test` (It should take about 1 min as the application uses test docker containers)

