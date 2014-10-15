#! /usr/bin/env bash

# by torstein.k.johansen@conduct.no

jboss_cli_connect="/opt/jboss-eap-6.2/bin/jboss-cli.sh -c"
datasource_name=moccasin-ds

${jboss_cli_connect} <<EOF
jms-queue add \
  --queue-address=buffaloQueue \
  --entries=queue/buffalo,java:jboss/jms/queue/buffalo
EOF

