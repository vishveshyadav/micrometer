FROM openjdk:8-jdk-alpine
WORKDIR /tmp
COPY ./startup.sh ./startup.sh
COPY ./metric-0.0.1-SNAPSHOT.jar ./metric-0.0.1-SNAPSHOT.jar
RUN /bin/sh -c 'chmod 777 ./startup.sh'
RUN /bin/sh -c 'chmod 777 ./metric-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["/bin/sh","-c","./${SCRIPT_FILE}"]