# Extract jacocoagent.jar and jacococli.jar from the latest zip release build on https://www.eclemma.org/jacoco/

mvn package
java -javaagent:jacocoagent.jar=destfile=target\jacoco.exec,append=false,includes=com.example.*  -jar target\jacoco-agent-0.0.1-SNAPSHOT.jar
java -jar jacococli.jar report target\jacoco.exec --classfiles target\classes\com\example\ --sourcefiles src\main\java\ --html target\
