#! /usr/bin/env bash

# by torstein.k.johansen@conduct.no

jboss_cli_connect="/opt/jboss-eap-6.2/bin/jboss-cli.sh -c"
datasource_name=moccasin-ds

${jboss_cli_connect} <<EOF
data-source add \
     --name=moccasin-ds \
     --driver-name=mysql \
     --connection-url=jdbc:mysql://localhost/moccasindb \
     --jndi-name=java:/db/moccasindb \
     --user-name=moccasinuser \
     --password=moccasinpassword

data-source enable --name=moccasin-ds
EOF

