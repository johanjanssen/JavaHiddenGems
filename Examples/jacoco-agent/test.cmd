REM  Extract jacocoagent.jar and jacococli.jar from the latest zip release build on https://www.eclemma.org/jacoco/
call mvn clean package

REM Start the application with the Java agent as a background process:
START /MIN java -javaagent:jacocoagent.jar=destfile=target\jacoco.exec,append=false,includes=com.example.*  -jar target\jacoco-agent-0.0.1-SNAPSHOT.jar

call curl http://localhost:8080/car
call curl http://localhost:8080/car
call curl http://localhost:8080/exit

REM Create the report:
call java -jar jacococli.jar report target\jacoco.exec --classfiles target\classes\com\example\ --sourcefiles src\main\java\ --html target\