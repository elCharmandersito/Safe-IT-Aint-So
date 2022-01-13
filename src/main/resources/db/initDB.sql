--
-- Estructura de tabla para la tabla `correccion`
--

CREATE TABLE `correccion` (
                              `id_correccion` int(11) NOT NULL,
                              `descripcion` varchar(255) DEFAULT NULL,
                              `reporte_id_reporte` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `emisor`
--

CREATE TABLE `emisor` (
                          `id_emisor` int(11) NOT NULL,
                          `servicio_emergencia` varchar(255) DEFAULT NULL,
                          `reporte_id_reporte` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notificacion`
--

CREATE TABLE `notificacion` (
                                `id_notificacion` int(11) NOT NULL,
                                `descripcion` varchar(255) DEFAULT NULL,
                                `nombre` varchar(255) DEFAULT NULL,
                                `tipo` varchar(255) DEFAULT NULL,
                                `emisor_id_emisor` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reporte`
--

CREATE TABLE `reporte` (
                           `id_reporte` int(11) NOT NULL,
                           `descripcion` varchar(255) DEFAULT NULL,
                           `fecha` datetime DEFAULT NULL,
                           `nivel_gravedad` int(11) DEFAULT NULL,
                           `nombre` varchar(255) DEFAULT NULL,
                           `usuario_id_usuario` int(11) DEFAULT NULL,
                           `tipo_reporte` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
                           `id_usuario` int(11) NOT NULL,
                           `activo` bit(1) NOT NULL,
                           `apellido_materno` varchar(255) DEFAULT NULL,
                           `apellido_paterno` varchar(255) DEFAULT NULL,
                           `correo` varchar(255) DEFAULT NULL,
                           `direccion` varchar(255) DEFAULT NULL,
                           `fono` int(11) NOT NULL,
                           `nombre` varchar(255) DEFAULT NULL,
                           `password` varchar(255) DEFAULT NULL,
                           `rol` int(11) DEFAULT NULL,
                           `token` varchar(1023) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `correccion`
--
ALTER TABLE `correccion`
    ADD PRIMARY KEY (`id_correccion`),
  ADD KEY `FK4mgeulxf0wljppccpq9jsuhfe` (`reporte_id_reporte`);

--
-- Indices de la tabla `emisor`
--
ALTER TABLE `emisor`
    ADD PRIMARY KEY (`id_emisor`),
  ADD KEY `FK4nl4d8d6g2mdx8yu4rbs9sgu0` (`reporte_id_reporte`);

--
-- Indices de la tabla `notificacion`
--
ALTER TABLE `notificacion`
    ADD PRIMARY KEY (`id_notificacion`),
  ADD KEY `FK8urynkluft5sixbwp97qaxlxf` (`emisor_id_emisor`);

--
-- Indices de la tabla `reporte`
--
ALTER TABLE `reporte`
    ADD PRIMARY KEY (`id_reporte`),
  ADD KEY `FK5e0jasa013dhfyj5liswv7t03` (`usuario_id_usuario`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
    ADD PRIMARY KEY (`id_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `correccion`
--
ALTER TABLE `correccion`
    MODIFY `id_correccion` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `emisor`
--
ALTER TABLE `emisor`
    MODIFY `id_emisor` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `notificacion`
--
ALTER TABLE `notificacion`
    MODIFY `id_notificacion` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `reporte`
--
ALTER TABLE `reporte`
    MODIFY `id_reporte` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
    MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `correccion`
--
ALTER TABLE `correccion`
    ADD CONSTRAINT `FK4mgeulxf0wljppccpq9jsuhfe` FOREIGN KEY (`reporte_id_reporte`) REFERENCES `reporte` (`id_reporte`);

--
-- Filtros para la tabla `emisor`
--
ALTER TABLE `emisor`
    ADD CONSTRAINT `FK4nl4d8d6g2mdx8yu4rbs9sgu0` FOREIGN KEY (`reporte_id_reporte`) REFERENCES `reporte` (`id_reporte`);

--
-- Filtros para la tabla `notificacion`
--
ALTER TABLE `notificacion`
    ADD CONSTRAINT `FK8urynkluft5sixbwp97qaxlxf` FOREIGN KEY (`emisor_id_emisor`) REFERENCES `emisor` (`id_emisor`);

--
-- Filtros para la tabla `reporte`
--
ALTER TABLE `reporte`
    ADD CONSTRAINT `FK5e0jasa013dhfyj5liswv7t03` FOREIGN KEY (`usuario_id_usuario`) REFERENCES `usuario` (`id_usuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
