DROP TABLE IF EXISTS Kysymys;
DROP TABLE IF EXISTS Vastaus;

CREATE TABLE Kysymys (
    id             integer PRIMARY KEY,
    kurssi         varchar(128),
    aihe           varchar(128),
    kysymys_teksti varchar(1024)
);

CREATE TABLE Vastaus (
    id             integer PRIMARY KEY,
    kysymys_id     integer,
    vastaus_teksti varchar(1024),
    oikein         integer,
    FOREING KEY    kysymys_id REFERENCES Kysymys (id)
);

