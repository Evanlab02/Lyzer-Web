# Lyzer-Web

## 1. Config

This project requires <a href="https://github.com/Evanlab02/Lyzer-Scraper">Lyzer-Scraper</a> to work, as it retrieves all data from this service.
By default the server is setup to have Lyzer-Scraper setup at localhost:8000. If you intend to use the Scraper Service differently you need to adjust the IP/Hostname/Domain for the Scraper accordingly in the <a href="https://github.com/Evanlab02/Lyzer-Web/blob/release/src/main/resources/localConfig.json">localConfig.json</a>.

## 2. Running the Web Server.

This project requires Java 17 to run.
<br>This project is built using maven (I use maven 3.9.0).

### 2.1 You have not changed the local config file and do not intend to.

Download the jar file from the latest release and run the Scraper Service and then run the Web Server using

```bash
$ java -jar Lyzer-Web-1.0.0.jar
```

### 2.2 You have cloned/downloaded the repo and changed the local config file.

```bash
$ cd into/directory/with/source/code/
$ mvn clean compile package
$ java -jar target/Lyzer-Web-1.0.0.jar
```

### 2.3 I would like to run the server on another port

```bash
$ java -jar Lyzer-Web-1.0.0.jar <port>
$ java -jar Lyzer-Web-1.0.0.jar 7000
```
