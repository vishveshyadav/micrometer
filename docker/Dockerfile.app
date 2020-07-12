FROM openjdk:8-jdk-alpine
WORKDIR /tmp
#RUN ["/bin/sh","-c","chmod 777 ./${SCRIPT_FILE}"]
ENTRYPOINT ["/bin/sh","-c","./${SCRIPT_FILE}"]