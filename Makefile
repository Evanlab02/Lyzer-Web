clean:
	mvn clean
	rm -rf dependency-reduced-pom.xml

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
	mvn compile package

run: build
	java -jar target/Lyzer-Web-1.0.0.jar

run-local: build
	java -jar target/Lyzer-Web-1.0.0.jar 7000

run-dev: build
	mvn exec:java -Dexec.args="7000"
