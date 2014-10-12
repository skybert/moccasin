#! /usr/bin/env bash

db=moccasindb
db_user=moccasinuser
db_password=moccasinpassword

tribe_list="
Apache
Kiowa
Comanche
Sioux
"

for tribe in $tribe_list; do
  mysql --user=${db_user} --password=${db_password} ${db} <<EOF
insert into tribe (name) values ('${tribe}');
EOF

done
