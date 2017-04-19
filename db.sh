psql -U postgres -c "CREATE DATABASE geostore;"
psql -U postgres -d geostore -c "CREATE EXTENSION postgis;"
