#here openjdk is docker image for Java 11, as in pom.xml Java version is 11
FROM openjdk
#Copy executable jar file getting created inside target and add it in usr/lib working director
ADD ./target/eureka-discovery-service-0.0.1-SNAPSHOT.jar eureka-discovery-service-0.0.1-SNAPSHOT.jar
#Run the jar file
ENTRYPOINT ["java","-jar","eureka-discovery-service-0.0.1-SNAPSHOT.jar"]
