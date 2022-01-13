INSERT INTO reporte VALUES (15, 'hay mucha basura en la plaza', 2020-12-21 21:00:00, 0,
                            'basura',1,3);
INSERT INTO reporte VALUES (2, 'Alguien hizo unos rayados en la pared de mi casa', 2020-07-13 20:00:00, 0,
                            'Rayados en pared',1,3);
INSERT INTO reporte VALUES (3, 'Alguien rompio las bancas de la plaza', 2020-07-15 20:00:00, 1,
                            'Bancas rotas',1,3);
INSERT INTO reporte VALUES (4, 'vi cables electricos por la vereda izquierda de la calle z', 2020-07-19 20:00:00, 1,
                            'Tendido electrico caido',1,3);


INSERT INTO usuario (`id_usuario`, `activo`, `apellido_materno`, `apellido_paterno`, `correo`, `direccion`, `fono`,
                       `nombre`, `password`, `rol`, `token`) VALUES
                    (1, 1, 'Cea', 'Ortiz', 'ysalejandra@gmail.com', 'Cerro negro 66', 2456789, 'Alejandra', '$2a$10$f42ZT2wF2pZdhNswXca8pOhbqh0LGTnHt6TtOmWrnkPJDWwoN7Xma', 0, NULL),
                    (2, 1, 'Soto', 'Parra', 'patriciorobertoparrasoto@gmail.com', 'Cerro negro 66', 2456789, 'Patricio', '$2a$10$GcsVQhkd0KhEpBT4CBn/gONazEEWYmavFKD1dR/bcGQUuatX4WZqi', 0, NULL),
                    (3, 1, 'Ramos', 'Orellana', 'igorella@alumnos.ubiobio.cl', 'Cerro negro 66', 2456789, 'Juanito', '$2a$10$yWdoEK3qD.1gvGnJEwQ67e1zujEvRWxUucU.UjaxURZ/i/sF.ZCei', 0, NULL),
                    (4, 1, 'Ramos', 'Orellana', 'mateo@gmail.cl', 'Cerro negro 66', 2456789, 'Mateo', '$2a$10$UoFG9aFSSpLAEy3D2fnG1.PiTYGFQW8OSVnsFCj87VatYsE.gc2Ea', 0, NULL);

