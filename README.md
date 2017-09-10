# video-streaming-content-app
Project deals with API to retrieve content metadata for Video Streaming platform clients.

STEPS TO COMPILE:
1. It is a Maven project. To build, mvn clean install will download the dependencies and build the jar, provided Maven is configured.

STEPS TO RUN THE APP
COMMAND LINE:
1. Download the project in your system
2. Go to the downloaded project path in command prompt
3. mvn clean install
4. java -Xms512m -Xmx1g -Dspring.profiles.active=dev -Dspring.config.location=config -Dlogging.config=config/logback.xml -jar ./target/streaming-content-1.0-SNAPSHOT.jar
   Have configured application.yml for dev and test profiles with an application.yml for common properties.

IDE:
1. Download the project and import in Eclipse/Springsource Tool Suite/IntelliJ as a Maven Project
2. Run->Edit Configurations -> Application -> Select the Main Class as ContentFilterApp and provide a name for Configuration
3. in VMOptions, add -Dspring.config.location=config -Dlogging.config=config/logback.xml -Dspring.profiles.active=dev
4. Run as Java Aplication by selecting the Configuration Name configured in Step 2.


ASSUMPTIONS:
1. Have configured the http://bit.ly/javaassignmentsrc to get the Google Drive File ID.
2. Calling the Google Drive URL with path /uc?id=<fileId> to download the Content Data JSON.
3. This may not be required if the actual external API is configured.
4. Correspondingly, Content Data Service need to be implemented to get Content Data JSON file.
5. If the Classification is Censored and Query param Level is not provided, the Entry is removed from the Content Data sent as response.
6. Content Data JSON is assumed to be fairly static and hence caching it to reduce processing time.


DOCUMENTATION:
Java : JDK 8

Libraries Used in the Assignment:
1. SPRING BOOT:
   Spring Boot is used to create REST APIs and packaging of the application as a fat jar. This is helpful as it is a self-contained application including an Embedded Jetty server which can be used to start the application without the need of an external server.
   Also, Spring Boot helps in reducing the conflict due to different spring dependencies.
2. JSOUP:
   Jsoup library is used to parse an HTML file to get the redirected URL for getting Content Data JSON
3. Commons Lang3and Collection4:
   Apache provided utility libraries providing utility functions for String and Collections.
4. Google Guava:
   Used to create an in-memory cache.
5. Lombok:
   Used to annotate getter, setter, constructor, logging and other features. Helps in keeping code concise and clean.
6. Wiremock:
   Used for Mocking the External APIs
7. RestAssured:
   Used for testing developed REST APIs.
8. Logback:
   Used for logging purpose.

