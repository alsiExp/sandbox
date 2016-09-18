CREATE TABLE TOPICS (
    ID INT NOT NULL AUTO_INCREMENT
  , NAME VARCHAR(70) NOT NULL
  , CREATION_TIME TIMESTAMP
  , PRIMARY KEY (ID)
);

CREATE TABLE MESSAGES (
    ID INT NOT NULL AUTO_INCREMENT
  , TOPIC_ID INT NOT NULL
  , CONTENT VARCHAR(400) NOT NULL
  , CREATION_TIME TIMESTAMP
  , PRIMARY KEY (ID)
  , CONSTRAINT FK_CONTACT_TEL_DETAIL_1 FOREIGN KEY (TOPIC_ID)
REFERENCES TOPICS (ID)
);
