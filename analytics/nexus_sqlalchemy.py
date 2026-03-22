# (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
# Proyecto Nexus Logistics - Material companion del libro
# Curso IFCD0014: Spring Boot + Hibernate
#
# Acceso directo a PostgreSQL desde Python con SQLAlchemy.
# Usar SOLO para lectura y analisis.
# Ejecutar con: python nexus_sqlalchemy.py

from sqlalchemy import create_engine
import pandas as pd
import os

DB_URL = os.environ.get(
    "DATABASE_URL",
    "postgresql://nexus:nexus_password_2026@localhost:5432/nexusdb"
)

engine = create_engine(DB_URL, echo=False, pool_size=5)

# Consulta 1: Envios con datos del cliente (JOIN)
df_envios = pd.read_sql("""
    SELECT e.id, e.tracking_number, e.origen, e.destino,
           e.peso_kg, e.estado, e.costo, e.fecha_registro, e.fecha_entrega,
           c.nombre AS cliente_nombre, c.email AS cliente_email
    FROM envios e
    JOIN clientes c ON e.cliente_id = c.id
    ORDER BY e.fecha_registro DESC
""", engine)
print(f"Envios cargados: {len(df_envios)}")
print(df_envios.head())
print()

# Consulta 2: Resumen con window functions
df_resumen = pd.read_sql("""
    SELECT destino, COUNT(*) AS total_envios,
           SUM(costo) AS facturacion, AVG(peso_kg) AS peso_promedio,
           COUNT(*) FILTER (WHERE estado = 'ENTREGADO')::float
               / NULLIF(COUNT(*), 0) * 100 AS tasa_entrega_pct,
           RANK() OVER (ORDER BY SUM(costo) DESC) AS ranking
    FROM envios
    GROUP BY destino
    ORDER BY facturacion DESC
""", engine)
print("=== Resumen por Destino ===")
print(df_resumen.round(2))
print()

# Consulta 3: Cohortes
df_cohortes = pd.read_sql("""
    SELECT DATE_TRUNC('month', c.fecha_registro) AS cohorte_mes,
           DATE_TRUNC('month', e.fecha_registro) AS mes_envio,
           COUNT(DISTINCT c.id) AS clientes_activos,
           COUNT(e.id) AS total_envios, SUM(e.costo) AS facturacion
    FROM clientes c
    JOIN envios e ON c.id = e.cliente_id
    GROUP BY cohorte_mes, mes_envio
    ORDER BY cohorte_mes, mes_envio
""", engine)
if not df_cohortes.empty:
    print("=== Analisis de Cohortes ===")
    print(df_cohortes.round(2))

# Exportar
df_envios.to_csv("nexus_export_envios.csv", index=False)
df_resumen.to_csv("nexus_export_resumen.csv", index=False)
with pd.ExcelWriter("nexus_reporte.xlsx", engine="openpyxl") as writer:
    df_envios.to_excel(writer, sheet_name="Envios", index=False)
    df_resumen.to_excel(writer, sheet_name="Resumen", index=False)
    if not df_cohortes.empty:
        df_cohortes.to_excel(writer, sheet_name="Cohortes", index=False)
print("\nDatos exportados a CSV y Excel")
