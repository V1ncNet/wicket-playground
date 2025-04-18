#!/bin/bash

exec 3<>/dev/tcp/localhost/${KC_HTTP_MANAGEMENT_PORT:-9000}

echo -e "GET ${KC_HTTP_MANAGEMENT_RELATIVE_PATH}/health/ready HTTP/1.1\nhost: localhost:${KC_HTTP_MANAGEMENT_PORT:-9000}\n" >&3

timeout --preserve-status 1 cat <&3 | grep -m 1 status | grep -m 1 UP
error=$?

exec 3<&-
exec 3>&-

exit $error
