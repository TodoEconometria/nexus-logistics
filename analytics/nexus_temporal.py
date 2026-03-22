# (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
# Proyecto Nexus Logistics - Material companion del libro
# Curso IFCD0014: Spring Boot + Hibernate
#
# Analisis temporal de envios y eficiencia de rutas.
# Ejecutar con: python nexus_temporal.py

import requests
import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.dates as mdates

BASE_URL = "http://localhost:8080/api"

def obtener_todos_los_envios():
    todos = []
    page = 0
    while True:
        response = requests.get(f"{BASE_URL}/envios",
                                params={"page": page, "size": 200}, timeout=10)
        response.raise_for_status()
        datos = response.json()["content"]
        if not datos:
            break
        todos.extend(datos)
        page += 1
    return todos

df = pd.DataFrame(obtener_todos_los_envios())
if df.empty:
    print("No hay datos para analizar.")
    exit()

df["fechaRegistro"] = pd.to_datetime(df["fechaRegistro"])
df["fechaEntrega"] = pd.to_datetime(df["fechaEntrega"], errors="coerce")

# Envios por dia de la semana
df["dia_semana"] = df["fechaRegistro"].dt.day_name()
orden_dias = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
envios_por_dia = df["dia_semana"].value_counts().reindex(orden_dias).fillna(0)
print("=== Envios por Dia de la Semana ===")
print(envios_por_dia)
print()

# Envios por semana
df_temporal = df.set_index("fechaRegistro")
envios_semanales = df_temporal.resample("W")["id"].count()
print("=== Envios por Semana ===")
print(envios_semanales)
print()

# Tiempo de entrega
entregados = df[df["fechaEntrega"].notna()].copy()
p95 = 0
if len(entregados) > 0:
    entregados["horas_entrega"] = (
        entregados["fechaEntrega"] - entregados["fechaRegistro"]
    ).dt.total_seconds() / 3600
    p95 = entregados["horas_entrega"].quantile(0.95)
    print("=== Tiempo de Entrega ===")
    print(f"Promedio: {entregados['horas_entrega'].mean():.1f} horas")
    print(f"Mediana:  {entregados['horas_entrega'].median():.1f} horas")
    print(f"P95:      {p95:.1f} horas")
    print()

# Eficiencia por ruta
if "ruta" in df.columns:
    df["ruta_nombre"] = df["ruta"].apply(
        lambda r: r.get("nombre", "Sin ruta") if isinstance(r, dict) else "Sin ruta")
    metricas_rutas = df[df["ruta_nombre"] != "Sin ruta"].groupby("ruta_nombre").agg(
        envios=("id", "count"), peso_total=("pesoKg", "sum"), costo_total=("costo", "sum")
    ).round(2)
    metricas_rutas["costo_por_kg"] = (metricas_rutas["costo_total"] / metricas_rutas["peso_total"]).round(2)
    print("=== Eficiencia por Ruta ===")
    print(metricas_rutas.sort_values("costo_por_kg"))

# Visualizacion
fig, axes = plt.subplots(2, 2, figsize=(14, 10))
fig.suptitle("Nexus - Analisis Temporal", fontsize=14, fontweight="bold")

envios_por_dia.plot(kind="bar", ax=axes[0, 0], color="#3498db")
axes[0, 0].set_title("Envios por Dia de la Semana")
axes[0, 0].tick_params(axis="x", rotation=45)

if len(envios_semanales) > 1:
    envios_semanales.plot(ax=axes[0, 1], color="#2ecc71", marker="o")
    axes[0, 1].set_title("Tendencia Semanal")

if len(entregados) > 0:
    entregados["horas_entrega"].plot(kind="hist", bins=15, ax=axes[1, 0],
                                     color="#e74c3c", edgecolor="white")
    axes[1, 0].set_title("Distribucion Tiempo de Entrega")
    axes[1, 0].axvline(x=p95, color="black", linestyle="--", label=f"P95: {p95:.0f}h")
    axes[1, 0].legend()

if len(df_temporal) > 0:
    costo_acumulado = df_temporal["costo"].resample("D").sum().cumsum()
    costo_acumulado.plot(ax=axes[1, 1], color="#9b59b6")
    axes[1, 1].set_title("Facturacion Acumulada")
    axes[1, 1].set_ylabel("EUR")

plt.tight_layout()
plt.savefig("nexus_temporal.png", dpi=150, bbox_inches="tight")
print("\nGraficos guardados en nexus_temporal.png")
