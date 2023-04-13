#!/bin/bash

#inicia el servidor de payara
asadmin --user=admin --passwordfile=/opt/payara/passwordFile start-domain

#crea el pool de conexiones
asadmin --user=admin --passwordfile=/opt/payara/passwordFile create-jdbc-connection-pool --restype=javax.sql.DataSource --datasourceclassname=org.postgresql.ds.PGSimpleDataSource --property=user=postgres:password=abc123:URL=jdbc\\:postgresql\\://db\\:5432/delivery deliveryPool

#hace ping al pool de conexiones que se acaba de crear
asadmin --user=admin --passwordfile=/opt/payara/passwordFile ping-connection-pool deliveryPool

#crea el recurso JDBC
asadmin --user=admin --passwordfile=/opt/payara/passwordFile create-jdbc-resource --connectionpoolid=deliveryPool jdbc/delivery

asadmin --user=admin --passwordfile=/opt/payara/passwordFile deploy /delivery-app/target/delivery.war

# mantiene el script en ejecucion para que el contenedor no se dentega
tail -f /dev/null

