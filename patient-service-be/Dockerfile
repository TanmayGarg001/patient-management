#######################################################################
# 1) BUILD STAGE — Uses Maven + JDK21 to COMPILE & BUILD the project  #
#######################################################################

# Use an official Maven image that *already includes JDK 21*
# This stage compiles the Java code and packages it into a JAR.
FROM maven:3.9.9-eclipse-temurin-21 AS builder

# Set the working directory inside this container.
# Every following command will run inside `/app`.
WORKDIR /app

# Copy ONLY pom.xml first.
# This allows Docker to cache the dependencies layer.
COPY pom.xml .

# Download all dependencies required by the project.
# This runs BEFORE copying the source code.
# WHY?: So that dependency downloading is cached unless pom.xml changes.
RUN mvn dependency:go-offline -B

# Now copy the entire source code.
# If your source code changes, only this layer rebuilds (not deps).
COPY src ./src

# Build the application.
# This creates the final JAR file inside /app/target/.
RUN mvn clean package


########################################################################
# 2) RUNNER STAGE — Uses lightweight JDK to RUN the already built JAR  #
########################################################################

# A much smaller image with only the JDK (no Maven).
# WHY?: Your final Docker image becomes MUCH smaller & more secure.
FROM eclipse-temurin:21-jdk AS runner

# Set the working directory again for the runtime container.
WORKDIR /app

# Copy the JAR built in the "builder" stage into this stage.
# Only the final artifact is copied, keeping the final image small.
COPY --from=builder /app/target/patient-service-be-0.0.1-SNAPSHOT.jar ./app.jar

# Expose port 4000 so tools like docker run -p can map the port.
# This doesn't actually open any ports; it's documentation for users.
EXPOSE 4000

# How the container should start.
# Runs your Spring Boot app.
ENTRYPOINT ["java", "-jar", "app.jar"]

# Metadata — not functional, but useful for identifying maintainers.
LABEL authors="tanmay"
