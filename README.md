# recruitment-task-02

## Prerequisites
You must have installed on your system:
* Java JDK8
## How to run this app
1. After download, run command in the root folder:
* `./mvnw clean install` for Unix systems
* `mvnw.cmd clean install` for Windows systems
2. After complete build, run 
* `./mvnw spring-boot:run` for Unix systems
* `mvnw spring-boot:run` for Windows systems

If you get message `Please set the JAVA_HOME variable in your environment to match the location of your Java installation.`
you need to set the JAVA_HOME variable with your jdk folder directory.
Example:
* `set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_181`
 
3. App available at `http://localhost:8080/` address.

### Technologie/Technologies:
* [spotify-web-api-java](https://github.com/thelinmichael/spotify-web-api-java) 
* [nitrite-database](https://github.com/dizitart/nitrite-database)
* Spring Boot
* log4j
* Tomcat
* JSP/JSTL