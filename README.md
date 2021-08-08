# InterviewN
1. Create Rest service that will put request on jms queue.
2. Listener on that queue that will trigger spring batch job
3. Job should read database state and put it to cache
4. Test coverage that solution is valid, and we can run tests for that

Database table could be created in hsql db with such sql query:
create table PERSON (ID bigint not null, EMAIL varchar(255), FIRST_NAME varchar(255), JOINED_DATE date, LAST_NAME varchar(255), primary key (id))
Values to be generated in big volumes by candidate
