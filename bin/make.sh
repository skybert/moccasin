#! /usr/bin/env bash

# by torstein.k.johansen@conduct.no

jboss_home=/opt/jboss-eap-6.2
mvn_opts="-q -o -Dmaven.test.skip=true"
mvn ${mvn_opts} clean package -f $(dirname $0)/../pom.xml  && \
  ${jboss_home}/bin/standalone.sh -c standalone-full.xml
