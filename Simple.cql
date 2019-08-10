CREATE  KEYSPACE IF NOT EXISTS test WITH REPLICATION = {'class' : 'SimpleStrategy', 'replication_factor' : 1 } AND DURABLE_WRITES = true;

CREATE TABLE test.testdb ( id int , name text , city text, Primary Key (id));

INSERT INTO test.testdb (id , name , city) values (1, 'A','AGRA') USING TTL 5000;

INSERT INTO test.testdb (id , name , city) values (2, 'B','BANGALORE') USING TTL 5000;

INSERT INTO test.testdb (id , name , city) values (3, 'C','COCHIN') USING TTL 5000;

INSERT INTO test.testdb (id , name , city) values (4, 'D','DEHRADUN') USING TTL 5000;
