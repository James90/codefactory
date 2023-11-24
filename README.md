# URL Shortener service

### Running the app

Packaging can be omitted using the zip from greenhouse. It already contains the jar file.

   ``` 
   mvn clean package
   docker-compose up -d
   ```

This will create two containers in a network: the url shortener service and a mongoDB database.

### Running the app locally

If you'd like to run the app locally, you can start a container running docker for it to connect to:

   ``` 
    docker run -d -p 27017:27017 --name mongodb mongo:latest
   ```

### Testing the app

   ``` 
   mvn clean test
   ```

### Endpoints

I've written 3 endpoints.

1. Post `fullURL` and return `shortUrl`

```
curl -X POST -H "Content-Type: application/json" -d '{"fullUrl": "https://google.com"}' http://localhost:8080/api/v1/url-shortener
```

2. Get `fullURL` string for `shortUrl`

```
curl "http://localhost:8080/api/v1/url-shortener?shortUrl=99999eb"
```

3. Get `fullURL` for `shortUrl` and redirect to `fullURL`. Will only work in a browser.

```
http://localhost:8080/api/v1/url-shortener/redirect?shortUrl=99999eb
```

### Design comments

#### Persistence

Since all I want to store is key-value pairs that have no relationships, and thinking about scalability, I have chosen
MongoDB as a database. I've written integration tests for MongoDB using TestContainers. Using postgres, I would use an
in-memory database for integration tests.

I would parameterize the database credentials and inject them at deployment from a parameter store.

#### Other considerations

- Tracing and monitoring:
  I would include tracing for observability to identify bottlenecks and study performance.
  I would also include logs for monitoring to be notified of errors or warnings and alarms based on SLOs.

- User authorization and ownership: after adding OAuth2, I would add a user id to the URL document to provide more
  functionality to the owner.

- Different environments: I would use the application.properties files to inherit and override/add properties for
  different environments

- Caching: resolving a short URL would be the most frequent operation, so I would cache like Caffeine to cache
  frequently accessed urls, in particular, for `getFullUrlFromShortUrl` in the `UrlService`.

- I've chosen to use 7 characters (like bit.ly): `2^28` unique strings. This first version generates a shortUrl, and if
  it already exists it creates another until it finds one that has not been used. In a subsequent version I'd implement
  a more robust solution.

- In a subsequent version I'd document the controller using a tool like Swagger.

- I am using Spring's actuator to provide an `/actuator/health` endpoint to be monitored by a load balancer.

- The service starts slowly the first time it's run because all the dependencies have to be downloaded. To mitigate
  this,
  I would create an image to create instances that have all the resources they need. An AWS AMI, for example.
- Dependency updates: before going to production I'd use renovate to automate dependency updates.