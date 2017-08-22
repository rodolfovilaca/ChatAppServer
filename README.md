# ChatApp Server
## Summary
A back-end server side message dispatcher that works with a client(https://github.com/rodolfovilaca/ChatAppClient). It's a multithreaded program that broadcasts all messages to clients and saves all its timeline in a MySQL database.

## Design Patterns
- Singleton: One object created to access MySQL and responsible to do save and request operations as we don't want several objects doing the same operations that could cause inconsistency data.
- DAO(Data Access Objects): model used to create objects in the program and save them as data in MySQL.

# Instructions

## MySQL user setup on production:

```bash
> mysql -u root -p
mysql> GRANT ALL PRIVILEGES ON *.* TO ‘ChatAppClient’@‘localhost' WITH GRANT OPTION;
mysql> FLUSH PRIVILEGES;
mysql> exit
```

## How to open ports in Digital Ocean

### DB Port
```bash
> sudo ufw allow 3306/tcp
> sudo service ufw restart
```
### Socket Port
```bash
> sudo ufw allow 5000/tcp 
> sudo service ufw restart
```

## How to generate and run the JAR on production:

```bash
> git clone https://github.com/rodolfovilaca/ChatAppServer
> cd ChatAppServer
> mv ./MANIFEST.MF ./bin
> mv ./libs/* ./bin
> cd bin
> jar cfm ChatAppServer.jar MANIFEST.MF * org.rodolfo.bancodedadosmysql.jar mysql-connector-java-5.1.42-bin.jar
```

### Run jar on the foreground:
```bash
> java -jar ChatAppServer.jar
```

### Run jar on the background
```bash
> nohup java -jar ChatAppServer.jar &
> tail -f nohup.out
```
 #### Kill background process

```bash
> ps -ef
> kill -9 $PID_NUMBER
```

