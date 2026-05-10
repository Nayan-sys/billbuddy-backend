FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

EXPOSE 9876

CMD ["java", "-jar", "target/split-with-room-mates-0.0.1-SNAPSHOT.jar"]