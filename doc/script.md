# why functional

- worked years with JavaEE and Spring
- never satisfied with code, bugs and tstability
- tried scala and WOW!

## principles
- minimize magic: reflection, thread local, proxies,
- favor compile check over runtime
- immutability
- use types


# ratpack

Netty based http server.
It is more library than framework!

## but why
- non  blocking
- stable, proven
- you need to learn principle (all alternatives are simillar (akka-http, spring 5 web flux))

## basic
webpage ratpack.io


## minimal project
- gradle
- main method

## starting ratpack
https://ratpack.io/manual/current/launching.html

## test
curl localhost:5050/webinar

# routing
prefix
gets 
post

# config

- threads

ab -m POST  -n 1000 -c 50 http://localhost:8080/counter/inc

# non blocking

ab  -n 100 -c 5 http://localhost:8080/fibb/10

#testing

Junit5

#JSON & DB

## DB
h2
```
CREATE TABLE lifelog (
    id BIGINT NOT NULL,
    points BIGINT NOT NULL,
    info VARCHAR NOT NULL,
    aTime TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

create sequence lifelog_id;
```
curl -H "Content-type: application/json" -d '{"info":"ok", "points":2 }' http://localhost:8080/life
curl  http://localhost:8080/life

## gradle JOOQ Mapping

#Packaging









 


