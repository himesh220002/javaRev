#!/bin/bash
echo "Starting MiniBank Server..."
java -cp "lib/mariadb-java-client-3.5.9.jar:bin" banking.Bank
