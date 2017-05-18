## Tomcat Request Header Filter

A servlet filter, which allows to add or replace request headers.

### How to use

Add the filter in your `web.xml`, by specifying `header-name` and  `header-value` params.

```xml
<filter>
    <filter-name>RequestHeaderFilter</filter-name>
    <filter-class>com.lonelyplanet.filters.RequestHeaderFilter</filter-class>
    <init-param>
        <param-name>header-name</param-name>
        <param-value>Authorization</param-value>
    </init-param>
    <init-param>
           <param-name>header-value</param-name>
           <param-value>Bearer access-token</param-value>
    </init-param>
</filter>
```

Apply the filter to the required routes

```xml
<filter-mapping>
    <filter-name>RequestHeaderFilter</filter-name>
    <url-pattern>/monitor/v1/*</url-pattern>
</filter-mapping>
```

If the configured header is already present in the request, it will be replaced.


### Release

Move `settings.xml.example` to `~/.m2/settings.xml` and add your bintray username and api key.


```
mvn release:prepare
mvn release:perform
```
