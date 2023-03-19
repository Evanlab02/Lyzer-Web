clean:
	@mvn clean
	@echo "[INFO] Removing dependency reduced pom.xml"
	@rm -rf dependency-reduced-pom.xml

pipeline:
	@mvn clean
	@mvn test
	@mvn checkstyle:check
	@mvn compile package

test:
	@mvn test

lint:
	@mvn checkstyle:check

build:
	@mvn compile package

run: build
	@clear
	@java -jar target/Lyzer-Web-1.1.1.jar

run-local: build
	@clear
	@java -jar target/Lyzer-Web-1.1.1.jar 7000

run-dev: build
	@clear
	@mvn exec:java -Dexec.args="7000"
