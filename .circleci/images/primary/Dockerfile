FROM golang:1.8.0

RUN apt-get update && apt-get install -y netcat
RUN go get github.com/jstemmer/go-junit-report


FROM circleci/openjdk:8-jdk

USER root

# AWS CLI needs the PYTHONIOENCODING environment variable to handle UTF-8 correctly
ENV PYTHONIOENCODING=UTF-8

RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y \
    less \
    man \
    ssh \
    git \
    gzip \
    tar \
    ca-certificates \
    python \
    python-pip \
    python-virtualenv \
    vim && \
    curl -sL https://deb.nodesource.com/setup_10.x | bash && \
    apt-get install -y nodejs && \
    npm install -g npm@latest && \
    npm install -g @angular/cli && \
    curl -sS https://dl.yarnpkg.com/debian/pubkey.gpg | apt-key add - && \
    echo "deb https://dl.yarnpkg.com/debian/ stable main" | tee /etc/apt/sources.list.d/yarn.list && \
    apt-get update && \
    apt-get install -y yarn && \
    npm install -g generator-jhipster

#RUN curl -s "https://get.sdkman.io" | bash
#RUN exit
#RUN source "/root/.sdkman/bin/sdkman-init.sh"
#RUN /bin/sh -l -c 'sdk install gradle 5.1.1'
#FROM gradle:jdk8

#COPY --chown=gradle:gradle . /home/gradle/src
#WORKDIR /home/gradle/src
#RUN gradle build
RUN wget https://services.gradle.org/distributions/gradle-5.1.1-bin.zip
#RUN mkdir /opt/gradle
RUN unzip -d /opt/gradle gradle-5.1.1-bin.zip
RUN ls /opt/gradle/gradle-5.1.1
RUN java -version
RUN gradle -v
