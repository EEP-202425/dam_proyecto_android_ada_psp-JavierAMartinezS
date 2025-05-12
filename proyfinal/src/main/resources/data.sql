-- Insertar paradas
INSERT INTO parada (id, nombre, es_origen, es_destino, es_intermedio) VALUES
  (1, 'MADRID', true, false, false),
  (2, 'BARCELONA', false, false, true),
  (3, 'MARSELLA', false, true, false);

-- Insertar ruta MADRID -> BARCELONA -> MARSELLA
INSERT INTO ruta (id, origenId, destino_id, transbordo) VALUES
  (1, 1, 3, false);

-- Asociar paradas a la ruta (recorrido)
INSERT INTO ruta_paradas (ruta_id, parada_id, trayecto) VALUES
  (1, 1, 0),
  (1, 2, 1),
  (1, 3, 2);
