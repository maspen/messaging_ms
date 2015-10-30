FROM java:8
VOLUME /tmp
ADD auto-sender-0.0.1-SNAPSHOT.jar auto-sender.jar
RUN bash -c 'touch /auto-sender.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/auto-sender.jar"]