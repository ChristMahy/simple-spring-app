# SSL files generation
### Command to execute:

keytool -keystore mykeys.jks -genkey -alias tomcat -keyalg RSA

### Have to create different hosts for client, resource and authorization servers

It is important to create all these hosts, avoid to override cookies between hosts.

#### /etc/hosts - linux
```127.0.0.1       authserver clientserver resourceserver```