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
    python \
    python-pip \
    python-virtualenv \
    vim && \
    curl -sL https://deb.nodesource.com/setup_10.x | bash && \
    apt-get install -y nodejs && \
    npm install -g npm@latest && \
    curl -sS https://dl.yarnpkg.com/debian/pubkey.gpg | apt-key add - && \
    echo "deb https://dl.yarnpkg.com/debian/ stable main" | tee /etc/apt/sources.list.d/yarn.list && \
    apt-get update && \
    apt-get install -y yarn && \
    npm install -g generator-jhipster
