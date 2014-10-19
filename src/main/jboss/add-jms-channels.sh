#! /usr/bin/env bash

# by torstein.k.johansen@conduct.no

jboss_cli_connect="/opt/jboss-eap-6.2/bin/jboss-cli.sh -c"
name_prefix=buffalo

${jboss_cli_connect} <<EOF
jms-queue add \
  --queue-address=${name_prefix}Queue \
  --entries=queue/${name_prefix},java:jboss/jms/queue/${name_prefix}

jms-topic add \
  --topic-address=${name_prefix}Topic \
  --entries=topic/${name_prefix},java:jboss/jms/topic/${name_prefix}
EOF

