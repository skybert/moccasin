#! /usr/bin/env bash

# by torstein.k.johansen@gmail.com

jboss_cli_connect="/opt/jboss-eap-6.2/bin/jboss-cli.sh -c"
security_domain_prefix=moccasin

${jboss_cli_connect} <<EOF
/subsystem=security/security-domain=${security_domain_prefix}-security-domain:add(cache-type=default)
/subsystem=security/security-domain=${security_domain_prefix}-security-domain/authentication=classic:add( \
  login-modules=[ \
    { \
      "code" => "Database", \
      "flag" => "required", \
      "module-options" => [ \
        ("unauthenticatedIdentity" => "guest"), \
        ("dsJndiName" => "java:/db/moccasindb"), \
        ("principalsQuery" => "select password from user where user_name = ?"), \
        ("rolesQuery" => "select r.name, 'Roles' from role r, user_role ur, user u where r.id = ur.id and ur.user_id = u.id and u.user_name = ?") \
      ] \
    } \
  ] \
)
EOF
