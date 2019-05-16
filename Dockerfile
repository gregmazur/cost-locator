FROM openjdk:8
ADD target/cost-locator.jar cost-locator.jar
ADD houses.csv houses.csv
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "cost-locator.jar"]