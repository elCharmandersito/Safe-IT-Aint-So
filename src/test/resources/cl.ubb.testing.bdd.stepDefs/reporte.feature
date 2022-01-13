Feature: Servicios de reporte

  Scenario: Buscar un reporte por su nombre
    Given reporte con id 1, nombre "basura",descripcion "hay mucha basura en la plaza", nivelGravedad 0
    When solicito reporte con nombre "basura"
    Then obtengo status "ok" y un reporte con nombre "basura"

  Scenario: Buscar un reporte por su descripcion
    Given reporte con idReporte 1, nombre "basura", fecha "2020-12-21 21:00:00",descripcion "hay mucha basura en la plaza", nivelGravedad 0
    When solicito reporte con descripcion "hay mucha basura en la plaza"
    Then obtengo status "ok" y un reporte con descripcion "hay mucha basura en la plaza"
    And nombre <nombre>

  Scenario:  Buscar un reporte por su nombre pero no existe un reporte con ese nombre
    Given No existe un reporte con nombre "basura"
    When solicito el reporte con nombre "basura"
    Then obtengo status "not_found"

  Scenario: Buscar un reporte por su descripcion pero no existe un reporte con esa descripcion
    Given No existe un reporte con descripcion "hay mucha basura en la plaza"
    When solicito el reporte con descripcion "hay mucha basura en la plaza"
    Then obtengo status "not_found"

  Scenario Outline: Buscar reportes por orden de gravedad
    Given necesito los reportes por orden de gravedad
    When solicito los reportes por orden de gravedad
    Then obtengo status <status>
    And id del reporte <idReporte>
    And nombre reporte <nombre>
    And fecha reporte <fecha>
    And descripcion reporte <descripcion>
    And nivelGravedad reporte <nivelGravedad>

    Examples:
      | idReporte | nombre                  | fecha                    | descripcion                                                    | nivelGravedad |
      |1          |"basura"                 | "2020-12-21 21:00:00"    |"hay mucha basura en la plaza"                                  |0              |
      |4          |"personas deambulando"   | "2020-12-21 21:00:00"    |"se han avistado personas deambulando por la calle x"           |1              |
      |7          |"tendido electrico caido"| "2020-12-21 21:00:00"    |"vi cables electricos por la vereda izquierda de la calle z"    |1              |