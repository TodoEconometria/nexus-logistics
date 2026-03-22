# (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
# Proyecto Nexus Logistics - Material companion del libro
# Curso IFCD0014: Spring Boot + Hibernate
#
# Script de analisis exploratorio de datos del sistema Nexus.
# Consume la API REST, carga los datos en pandas y genera
# estadisticas basicas + graficos.
#
# Prerequisito: Nexus debe estar corriendo en localhost:8080
# Ejecutar con: python nexus_analytics.py

import requests
import pandas as pd
import matplotlib.pyplot as plt
import sys

BASE_URL = "http://localhost:8080/api"
JWT_TOKEN = ""

def get_headers():
    headers = {"Accept": "application/json"}
    if JWT_TOKEN:
        headers["Authorization"] = f"Bearer {JWT_TOKEN}"
    return headers

def obtener_envios(page=0, size=200):
    params = {"page": page, "size": size, "sort": "fechaRegistro,desc"}
    try:
        response = requests.get(f"{BASE_URL}/envios", params=params,
                                headers=get_headers(), timeout=10)
        response.raise_for_status()
        return response.json()["content"]
    except requests.ConnectionError:
        print(f"ERROR: No se puede conectar a {BASE_URL}")
        print("Verificar que Nexus esta corriendo (docker-compose up -d)")
        sys.exit(1)
    except requests.HTTPError as e:
        print(f"ERROR HTTP {e.response.status_code}: {e.response.text}")
        sys.exit(1)

def obtener_todos_los_envios():
    todos = []
    page = 0
    while True:
        datos = obtener_envios(page=page, size=200)
        if not datos:
            break
        todos.extend(datos)
        page += 1
    return todos

def obtener_clientes():
    response = requests.get(f"{BASE_URL}/clientes",
                            headers=get_headers(), timeout=10)
    response.raise_for_status()
    return response.json()

def obtener_rutas():
    response = requests.get(f"{BASE_URL}/rutas",
                            headers=get_headers(), timeout=10)
    response.raise_for_status()
    return response.json()

# Carga de datos
print("Conectando a la API de Nexus...")
envios_raw = obtener_todos_los_envios()
clientes_raw = obtener_clientes()
rutas_raw = obtener_rutas()

df_envios = pd.DataFrame(envios_raw)
df_clientes = pd.DataFrame(clientes_raw)
df_rutas = pd.DataFrame(rutas_raw)

if "fechaRegistro" in df_envios.columns:
    df_envios["fechaRegistro"] = pd.to_datetime(df_envios["fechaRegistro"])
if "fechaEntrega" in df_envios.columns:
    df_envios["fechaEntrega"] = pd.to_datetime(df_envios["fechaEntrega"])

print(f"Datos cargados: {len(df_envios)} envios, "
      f"{len(df_clientes)} clientes, {len(df_rutas)} rutas")
print()

if df_envios.empty:
    print("No hay envios para analizar.")
    sys.exit(0)

# Analisis 1: Distribucion por estado
print("=" * 50)
print("DISTRIBUCION DE ENVIOS POR ESTADO")
print("=" * 50)
estado_counts = df_envios["estado"].value_counts()
estado_pct = df_envios["estado"].value_counts(normalize=True) * 100
tabla_estados = pd.DataFrame({"Cantidad": estado_counts, "Porcentaje": estado_pct.round(1)})
print(tabla_estados)
print()

# Analisis 2: Metricas por destino
print("=" * 50)
print("METRICAS POR DESTINO")
print("=" * 50)
metricas_destino = df_envios.groupby("destino").agg(
    total_envios=("id", "count"),
    peso_total_kg=("pesoKg", "sum"),
    costo_total=("costo", "sum"),
    costo_promedio=("costo", "mean")
).round(2).sort_values("total_envios", ascending=False)
print(metricas_destino)
print()

# Analisis 3: Indicadores operativos
print("=" * 50)
print("INDICADORES OPERATIVOS")
print("=" * 50)
total = len(df_envios)
entregados = len(df_envios[df_envios["estado"] == "ENTREGADO"])
print(f"Total de envios:       {total}")
print(f"Entregados:            {entregados} ({(entregados/total*100):.1f}%)")
print(f"Peso promedio:         {df_envios['pesoKg'].mean():.2f} kg")
print(f"Costo promedio:        {df_envios['costo'].mean():.2f} EUR")
print(f"Facturacion total:     {df_envios['costo'].sum():.2f} EUR")
print()

# Visualizacion
fig, axes = plt.subplots(2, 2, figsize=(14, 10))
fig.suptitle("Nexus Logistics - Dashboard Analitico", fontsize=14, fontweight="bold")

estado_counts.plot(kind="bar", ax=axes[0, 0],
    color=["#2ecc71", "#3498db", "#e74c3c", "#f39c12", "#9b59b6"])
axes[0, 0].set_title("Envios por Estado")
axes[0, 0].set_ylabel("Cantidad")
axes[0, 0].tick_params(axis="x", rotation=45)

if not metricas_destino.empty:
    metricas_destino["costo_total"].plot(kind="barh", ax=axes[0, 1], color="#3498db")
    axes[0, 1].set_title("Costo Total por Destino")
    axes[0, 1].set_xlabel("EUR")

df_envios["pesoKg"].plot(kind="hist", bins=20, ax=axes[1, 0],
    color="#2ecc71", edgecolor="white")
axes[1, 0].set_title("Distribucion de Peso de Envios")
axes[1, 0].set_xlabel("Peso (kg)")

df_envios.plot(kind="scatter", x="pesoKg", y="costo", ax=axes[1, 1],
    color="#e74c3c", alpha=0.6)
axes[1, 1].set_title("Relacion Peso vs Costo")

plt.tight_layout()
plt.savefig("nexus_dashboard.png", dpi=150, bbox_inches="tight")
print("Dashboard guardado en nexus_dashboard.png")
