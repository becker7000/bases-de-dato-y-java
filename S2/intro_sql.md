### Introducción a SQL

*SQL: Structured Query Language*
Es el lenguaje que usan las base de datos relacionales.

---
Se ha convertido en un estandar internacional
de manejo de SGBD: MySQL, PostgreSQL, Oracle, SQLserver,etc...
---

SQL nos permite realizar diversas operaciones y consultas
a bases de datos, tales como:
* Crear (registros,tablas,etc)
* Modificar
* Leer
* Insertar
* Eliminar

Nota: CRUD (Create,Read,Update,Delete)

> SQL se subdivide en varios sublenguajes:
1. DDL (Data Definition Language): Define las entidades modeladas.
2. DML (Data Manipulation Language): Consultar y modificar datos.
3. DQL (Data Query Language): Su excelencia es consultar datos.
4. DCL (Data Control Language): Nos permite administrar permisos o acceso.

> DDL: Data Definition Language

Los comandos básicos son:
* CREATE: permite crear bases de datos, tablas y vistas.
* ALTER: Modificar tablas.
* DROP: Eliminar las bases de datos, tablas o columnas. (Tener mucho cuidado)
---

¿Como crear una BD?
```
   CREATE SCHEMA `nombre_bd`;  
```

¿Como usar una base de datos?
```
    USE `nombre_bd`;
```

Diagrama de una entidad:
![entidad_persona](entidad_persona.png)
¿Como creamos esta entidad?
```
    CREATE TABLE persona(
        id INT AUTO_INCREMENT,
        nombre VARCHAR(45),
        apellido VARCHAR(45),
        dirección VARCHAR(45),
        ciudad VARCHAR(45),
        PRIMARY KEY(`id`)
    );
```

¿Como agregar una columna a una tabla existente?
```
    ALTER TABLE persona
    ADD COLUMN fecha_nacimiento
    DATETIME NULL AFTER ciudad;
```

¿Como eliminar una columna de una tabla?
```
    ALTER TABLE persona
    DROP COLUMN fecha_nacimiento;
```

¿Como podemos cambiar el nombre y tipo de dato a una tabla?
```
    ALTER TABLE persona
    CHANGE apellido apellido_pat VARCHAR(45);
```

¿Como cancelar la ejecución de un comando?
Atajo: ctrl + c

*Vistas: es una consulta personalizada que se 
crea para no tener que escribir el query cada
vez que se desea ejecutar.*

Nota: para poder crear una vista lo ideal es que
existan algunos registros en la tabla a consultar.

> DML: Data Manipulation Language

Sus comandos básicos son: 
* SELECT: Selecciona los campos a consultar.
* INSERT: Inserta registros en los campos respectivos.
* UPDATE: Modifica algunos valores de datos de campos ya existentes.
* DELETE: Eliminar registros de una tabla. (Tener cuidado)

¿Como insertar datos de una tabla?
Generalmente a esto se llama "registro".

Usamos el comando INSERT:
```
    INSERT INTO persona (nombre,apellido_pat,direccion,ciudad)
    VALUES ('Ana','Gómez','Condesa','CDMX');
```
Nota: el 'id' no es necesario porque se generar en automatico.

¿Como podemos insertar multiples registros en una tabla?
```
    INSERT INTO persona (nombre,apellido_pat,direccion,ciudad) VALUES 
    ('Raul','Pérez','Lindavista','CDMX'),
    ('Laura','López','Narvarte','CDMX'),
    ('Carlos','Martinez','Tabacalera','CDMX');
```

¿Como podemos modificar un valor de un campo en registro?
Usando el comando UPDATE
```
    UPDATE persona 
    SET direccion = 'Polanco' 
    WHERE id=3;
```

¿Como eliminamos un registro de una tabla?
Nota: tener cuidado de como aplicar este comando.
```
    DELETE FROM persona
    WHERE id=2;
```

> DQL: Data Query Language
Se usa para realizar consultas y recuperar información
de la base de datos (registros). 

¿Como consultar todos los registros de una tabla?
Usando el comando SELECT
```
    SELECT * FROM persona;
```

¿Como consultar solamente algunos campos de una tabla?
```
    SELECT nombre,apellido_pat FROM persona;
```

¿Como consultar solamente algunos registros de una tabla?
```
    SELECT * FROM persona
    WHERE id<4;
```

¿Como consultar solamente algunos campos y algunos registros de una tabla?
```
    SELECT id,nombre
    FROM persona
    WHERE id>1;
```

Nota final:
SELECT delimina las columnas
FROM selecciona la tabla
WHERE delimina los registros bajo una condición

*Extra: * SQL nos permite usar un conjunto de funciones que sirven para 
obtener datos sobre los datos. Por ejemplo: COUNT(*)

¿Como podemos contar todos los registro de una tabla?
```
    SELECT COUNT(*) AS total_personas
    FROM persona;
```
Existen otras funciones como: SUM,AVG,etc...

> DCL: Data Control Language
Se usa para gestionar los permisos y privilegios
en una base de datos.

Sus comandos básicos son los siguientes:
* GRANT: conceder permisos a usarios o roles sobre objetos de BD.
* REVOKE: quita los permisos previamente concedidos.