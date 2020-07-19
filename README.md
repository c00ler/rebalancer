# Financial Portfolio Rebalancing Service

![Build](https://github.com/c00ler/rebalancer/workflows/Build/badge.svg?branch=master)

This service is not suitable for any production usage. The purpose is to showcase the algorithm only.
Data, that is used by the service, is packaged inside the jar itself.

## Dependencies

- **Java 11**

## Running tests 

To run _unit_ and _integration_ tests execute the following command:

```bash
./mvnw clean verify
```

## Running service locally

Build an executable jar:

```bash
./mvnw clean package
```

Output jar will always have the same name `service`. To run it, the following command can be used:

```bash
java -jar target/service.jar --server.port=8080
```

There is only one endpoint available `POST /portfolios/rebalance`. It will run rebalancing process for all customers 
and return status code `200` if operation was successful. An example of calling it using [HTTPie](https://httpie.org/):

```bash
http --verbose POST :8080/portfolios/rebalance
```

The result of the rebalancing will be available in the server logs. Rebalancing starts with message 
`Starting rebalancing of all portfolios` and ends with message `Rebalancing finished`.

As soon as service is using the static dataset, multiple calls of the endpoint should produce the same result.
Over the time output will change because of the time, that is used for the `age` calculation.

Example output:
![Example output](images/output.png?raw=true)

## Input data

- Customers defined in the csv file [customers.csv](src/main/resources/customers.csv)
- Strategies defined in the csv file [strategy.csv](src/main/resources/strategy.csv)
- Portfolios defined in the code [InMemoryFpsGateway.kt](src/main/kotlin/com/bcgdv/rebalancer/portfolio/InMemoryFpsGateway.kt)
