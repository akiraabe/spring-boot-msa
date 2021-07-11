# udemy-msa

## how to start the axon-server on docker

```
docker run --name axonserver -p 8024:8024 -p 8124:8124 -v "/home/akira/practice/spring-boot/udemy-msa/docker-data/data":/data -v "/home/akira/practice/spring-boot/udemy-msa/docker-data/eventdata":/eventdata -v "/home/akira/practice/spring-boot/udemy-msa/docker-data/config":/config axoniq/axonserver

```

## how to use the H2-console

http://localhost:8082/products-service/h2-console
jdbc:h2:file:~/products

http://localhost:8082/orders-service/h2-console
jdbc:h2:file:~/orders-dev

http://localhost:8082/payments-service/h2-console
jdbc:h2:file:~/payments
