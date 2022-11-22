#here openjdk is docker image for Java 11, as in pom.xml Java version is 11
FROM openjdk
ADD ./target/watchlist-service-0.0.1-SNAPSHOT.jar watchlist-service-0.0.1-SNAPSHOT.jar
#Run the jar file
ENTRYPOINT ["java","-jar","watchlist-service-0.0.1-SNAPSHOT.jar"]
