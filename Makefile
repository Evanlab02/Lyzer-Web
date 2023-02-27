build:
	@mvn clean
	@mvn test
	@mvn checkstyle:check
	@mvn compile

run:
	@mvn exec:java
