-- data.sql: Datos iniciales para el sistema Nexus
-- Se ejecuta automaticamente al iniciar la aplicacion

-- Clientes de ejemplo
INSERT INTO clientes (nombre, email, telefono, direccion, fecha_registro)
VALUES ('Logistica Rapida S.L.', 'contacto@logisticarapida.com',
        '+34 911 234 567', 'Calle Mayor 10, Madrid', NOW())
ON CONFLICT (email) DO NOTHING;

INSERT INTO clientes (nombre, email, telefono, direccion, fecha_registro)
VALUES ('Distribuciones Norte', 'info@distnorte.com',
        '+34 944 567 890', 'Gran Via 45, Bilbao', NOW())
ON CONFLICT (email) DO NOTHING;

INSERT INTO clientes (nombre, email, telefono, direccion, fecha_registro)
VALUES ('Envios Express BCN', 'ventas@expressbcn.com',
        '+34 933 456 789', 'Passeig de Gracia 78, Barcelona', NOW())
ON CONFLICT (email) DO NOTHING;

-- Almacenes
INSERT INTO almacenes (nombre, ciudad, direccion, capacidad_maxima)
VALUES ('Almacen Central Madrid', 'Madrid',
        'Poligono Industrial Norte, Nave 12', 500)
ON CONFLICT DO NOTHING;

INSERT INTO almacenes (nombre, ciudad, direccion, capacidad_maxima)
VALUES ('Almacen Barcelona', 'Barcelona',
        'Zona Franca, Edificio B3', 300)
ON CONFLICT DO NOTHING;

-- Vehiculos
INSERT INTO vehiculos (matricula, tipo, capacidad_kg, disponible)
VALUES ('1234-ABC', 'CAMION', 5000.0, true)
ON CONFLICT (matricula) DO NOTHING;

INSERT INTO vehiculos (matricula, tipo, capacidad_kg, disponible)
VALUES ('5678-DEF', 'FURGONETA', 1500.0, true)
ON CONFLICT (matricula) DO NOTHING;

INSERT INTO vehiculos (matricula, tipo, capacidad_kg, disponible)
VALUES ('9012-GHI', 'MOTO', 50.0, true)
ON CONFLICT (matricula) DO NOTHING;
