DROP TABLE IF EXISTS Kysymys;

CREATE TABLE Kysymys (
    id             SERIAL PRIMARY KEY,
    kurssi         varchar(128),
    aihe           varchar(128),
    kysymys_teksti varchar(1024)
);

DROP TABLE IF EXISTS Vastaus;

CREATE TABLE Vastaus (
    id             SERIAL PRIMARY KEY,
    kysymys_id     integer,
    vastaus_teksti varchar(1024),
    oikein         boolean,
    FOREIGN KEY (kysymys_id) REFERENCES Kysymys (id)
);
