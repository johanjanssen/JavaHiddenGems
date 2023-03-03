# Extract jacocoagent.jar and jacococli.jar from the latest zip release build on https://www.eclemma.org/jacoco/

mvn clean package

Start the application with the Java agent:
java -javaagent:jacocoagent.jar=destfile=target\jacoco.exec,append=false,includes=com.example.*  -jar target\jacoco-agent-0.0.1-SNAPSHOT.jar

GOTO http://localhost:8080/car

Stop the application

Create the report:
java -jar jacococli.jar report target\jacoco.exec --classfiles target\classes\com\example\ --sourcefiles src\main\java\ --html target\