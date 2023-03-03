clean:
	mvn clean

pipeline:
	mvn clean
	mvn test
	mvn checkstyle:check
	mvn compile

test:
	mvn test

lint:
	mvn checkstyle:check

build:
	mvn compile

run: build
	mvn exec:java

run-local: build
	mvn exec:java -Dexec.args="7000"
