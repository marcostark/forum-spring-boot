# API REST para Fórum utilizando Spring Boot

### Tecnologias utilizadas no projeto

- Spring Boot
- Spring Web Security
- Spring Boot Cache
    - Obs.: Utilizar cache apenas onde fizer sentido, uma vez que ao estar realizando 
    a invalidação do cache requer custo computacional.
    - Preferível utilizar em informações que demoram muito ou raramente são atualizadas no banco de dados
