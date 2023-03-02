clean:
	mvn clean

pipeline:
	mvn clean
	mvn test
	mvn checkstyle:check
	mvn compile

build:
	mvn compile

run: build
	mvn exec:java

run-prod: build
	mvn exec:java -Dexec.args="80"
