# (c) 2026 Juan Marcelo Gutierrez Miranda (@TodoEconometria)
# Proyecto Nexus Logistics - Material companion del libro
# Curso IFCD0014: Spring Boot + Hibernate
#
# Dashboard interactivo para Nexus Logistics.
# Ejecutar con: streamlit run nexus_dashboard_streamlit.py

import streamlit as st
import requests
import pandas as pd
import matplotlib.pyplot as plt

st.set_page_config(page_title="Nexus Logistics - Dashboard", layout="wide")
st.title("Nexus Logistics - Panel de Analitica")
st.markdown("Dashboard en tiempo real conectado a la API REST de Nexus")

@st.cache_data(ttl=60)
def cargar_envios():
    todos = []
    page = 0
    while True:
        try:
            r = requests.get("http://localhost:8080/api/envios",
                             params={"page": page, "size": 200}, timeout=10)
            r.raise_for_status()
            datos = r.json()["content"]
            if not datos:
                break
            todos.extend(datos)
            page += 1
        except requests.ConnectionError:
            st.error("No se puede conectar a la API.")
            return pd.DataFrame()
    df = pd.DataFrame(todos)
    if "fechaRegistro" in df.columns:
        df["fechaRegistro"] = pd.to_datetime(df["fechaRegistro"])
    if "fechaEntrega" in df.columns:
        df["fechaEntrega"] = pd.to_datetime(df["fechaEntrega"], errors="coerce")
    return df

df_envios = cargar_envios()
if df_envios.empty:
    st.warning("No hay datos disponibles.")
    st.stop()

# Filtros
st.sidebar.header("Filtros")
estados_disponibles = df_envios["estado"].unique().tolist()
estados_seleccionados = st.sidebar.multiselect(
    "Estado:", options=estados_disponibles, default=estados_disponibles)

df_filtrado = df_envios[df_envios["estado"].isin(estados_seleccionados)]

# KPIs
col1, col2, col3, col4 = st.columns(4)
with col1:
    st.metric("Total Envios", len(df_filtrado))
with col2:
    st.metric("Facturacion", f"{df_filtrado['costo'].sum():,.2f} EUR")
with col3:
    entregados = len(df_filtrado[df_filtrado["estado"] == "ENTREGADO"])
    tasa = (entregados / len(df_filtrado) * 100) if len(df_filtrado) > 0 else 0
    st.metric("Tasa Entrega", f"{tasa:.1f}%")
with col4:
    st.metric("Peso Total", f"{df_filtrado['pesoKg'].sum():,.1f} kg")

st.divider()

# Graficos
col_izq, col_der = st.columns(2)
with col_izq:
    st.subheader("Distribucion por Estado")
    fig1, ax1 = plt.subplots(figsize=(6, 4))
    df_filtrado["estado"].value_counts().plot(kind="bar", ax=ax1,
        color=["#2ecc71", "#3498db", "#e74c3c", "#f39c12", "#9b59b6"])
    ax1.set_ylabel("Cantidad")
    ax1.tick_params(axis="x", rotation=45)
    plt.tight_layout()
    st.pyplot(fig1)

with col_der:
    st.subheader("Relacion Peso vs Costo")
    fig2, ax2 = plt.subplots(figsize=(6, 4))
    df_filtrado.plot(kind="scatter", x="pesoKg", y="costo",
                     ax=ax2, color="#e74c3c", alpha=0.6)
    plt.tight_layout()
    st.pyplot(fig2)

# Tabla
st.subheader("Detalle de Envios")
columnas = ["id", "trackingNumber", "origen", "destino", "pesoKg", "estado", "costo"]
cols_disponibles = [c for c in columnas if c in df_filtrado.columns]
st.dataframe(df_filtrado[cols_disponibles], use_container_width=True, height=400)

# Exportar
st.subheader("Exportar Datos")
col_csv, col_excel = st.columns(2)
with col_csv:
    csv_data = df_filtrado.to_csv(index=False).encode("utf-8")
    st.download_button("Descargar CSV", csv_data, "nexus_envios.csv", "text/csv")
with col_excel:
    from io import BytesIO
    buffer = BytesIO()
    df_filtrado.to_excel(buffer, index=False, engine="openpyxl")
    st.download_button("Descargar Excel", buffer.getvalue(), "nexus_envios.xlsx")
