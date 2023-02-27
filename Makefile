build:
	@mvn clean
	@mvn test
	@mvn checkstyle:check
	@mvn compile

run:
	@mvn exec:java

run-scraper:
	cd Scraper0-9-0/ && ./Lyzer-Scraper &