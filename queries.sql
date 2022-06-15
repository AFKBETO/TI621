drop database if exists TP4;
CREATE DATABASE TP4;
use TP4;

-- Creation des tables --

CREATE TABLE Category(
                         CategoryID INT auto_increment,
                         Name VARCHAR(50),
                         PRIMARY KEY(CategoryID)
);

CREATE TABLE Topic(
                      TopicID INT auto_increment,
                      Topic VARCHAR(50),
                      PRIMARY KEY(TopicID)
);

CREATE TABLE Tag(
                    TagID INT auto_increment,
                    Tag VARCHAR(50),
                    PRIMARY KEY(TagID)
);

CREATE TABLE Document(
                         DocumentID INT auto_increment,
                         Documentname VARCHAR(50),
                         DocumentDate DATE,
                         StorageAddress VARCHAR(50),
                         TopicID INT NOT NULL,
                         CategoryID INT NOT NULL,
                         PRIMARY KEY(DocumentID),
                         FOREIGN KEY(TopicID) REFERENCES Topic(TopicID),
                         FOREIGN KEY(CategoryID) REFERENCES Category(CategoryID)
);

CREATE TABLE possède(
                        DocumentID INT,
                        TagID INT,
                        PRIMARY KEY(DocumentID, TagID),
                        FOREIGN KEY(DocumentID) REFERENCES Document(DocumentID),
                        FOREIGN KEY(TagID) REFERENCES Tag(TagID)
);
