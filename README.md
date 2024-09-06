# Oracle

Siga este tutorial:
https://www.oracle.com/br/technical-resources/articles/database-performance/oracle-db19c-com-docker.html

Troque o caminho /Users/feliperochaoliveira/Documents/Fiap/oracle/oradata por uma pasta em sua máquina

```sh
docker  run --name oracle19c -p 1521:1521 -p 5500:5500 -v /Users/feliperochaoliveira/Documents/Fiap/oracle/oradata:/opt/oracle/oradata oracle/database:19.3.0-ee
```

Helper
Recovery password command

```
docker exec oracle19c ./setPassword.sh Minha_Nova_Senha
```

Password: 1234
DB: ORCLPDB1