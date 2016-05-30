CREATE TABLE movie(
    id INTEGER PRIMARY KEY,
    title TEXT,
    overview TEXT,
    releasedate TEXT,
    posterpatch TEXT
);

CREATE TABLE usuario(
    id INTEGER PRIMARY KEY,
    nombre TEXT,
    foto TEXT,
    correo TEXT
);

CREATE TABLE cinema(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT,
    latitud FLOAT,
    longitud FLOAT
);

INSERT INTO cinema IN('nombre', 'latitud', 'longitud' ) VALUES('Cine Colombia Los Molinos', 6.232321400000001, -75.60410279999996);
INSERT INTO cinema IN('nombre', 'latitud', 'longitud' ) VALUES('Procinal Puerta del norte', 6.339409599999999, -75.54321419999997);
INSERT INTO cinema IN('nombre', 'latitud', 'longitud' ) VALUES('Procinal Florida', 6.270901899999999, -75.57674639999999);
INSERT INTO cinema IN('nombre', 'latitud', 'longitud' ) VALUES('Royal Films Bosque Plaza', 6.268674619223301, -75.56475877761841);

