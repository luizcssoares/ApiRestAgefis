# ApiRestAgefis
AGEFIS - Teste de Desenvolvimento de Software

## Importante:
### Ajuste no arquivo application.properties com informacoes do POSTGRES:	
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

#datasource
spring.dataSource.url = jdbc:postgresql://localhost:5432/postgres
spring.dataSource.username = postgres
spring.dataSource.password = admin
spring.jpa.hibernate.ddl-auto=update
	
### Endereco do Swagger (Documentacao e Testes) da APIRest:
http://localhost:8080/swagger-ui.html

### Views a serem criadas no POSTGRES:	
CREATE VIEW ViewMovimento AS	
SELECT row_number() OVER () AS id,
	   o.idvaga,
	   o.numplaca,
	   o.entrada,
	   o.hora,
	   d.saida,	   
	   d.valor
FROM   ocupacao o,
	   desocupacao d,
	   movimento m
WHERE  m.idocupacao = o.id AND m.iddesocupacao = d.id
ORDER  BY o.entrada, o.hora;
	   
CREATE VIEW ViewFinanceiro AS	
SELECT o.entrada,
       sum(d.valor) AS valor
FROM   ocupacao o,
       desocupacao d,
       movimento m
WHERE  m.idocupacao = o.id AND m.iddesocupacao = d.id
GROUP  BY o.entrada
ORDER  BY o.entrada;	   
	   
CREATE VIEW ViewTempoMedio AS	
SELECT o.entrada,
       avg(o.hora - d.hora) AS tempomedio
FROM   ocupacao o,
       desocupacao d,
       movimento m
WHERE  m.idocupacao = o.id AND m.iddesocupacao = d.id
GROUP  BY o.entrada
ORDER  BY o.entrada;
