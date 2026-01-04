from sqlalchemy import create_engine, text
from dotenv import load_dotenv
import pandas as pd
import os

# Carica le variabili d'ambiente
load_dotenv()


def get_db_engine():
    """
    Crea la connessione al database PostgreSQL usando le variabili d'ambiente.
    """
    db_username = os.getenv("DB_USER", "postgres")
    db_password = os.getenv("DB_PASSWORD", "riccardo")
    db_host = os.getenv("DB_HOST", "localhost")
    db_port = os.getenv("DB_PORT", "5432")
    db_name = os.getenv("DB_NAME", "IUM_TWEB")

    conn_string = f"postgresql://{db_username}:{db_password}@{db_host}:{db_port}/{db_name}?client_encoding=utf8"
    return create_engine(conn_string)


def populate_database():
    engine = get_db_engine()
    json_folder = 'output'

    files_to_load = {
        #'movies_clean.json': 'movies',
        #'genres_clean.json': 'genres',
        'languages_clean.json': 'languages',
        #'actors_clean.json': 'actors',
        #'posters_clean.json': 'posters',
        #'releases_clean.json': 'releases',
        #'studios_clean.json': 'studios',
        #'theme_clean.json': 'themes',
        #'oscars_clean.json': 'oscars',
        #'countries_clean.json': 'countries',
        #'crew_clean.json': 'crew'
    }

    print("--- Inizio popolamento DB (Adattato per ID Autoincrementali) ---")

    for filename, table_name in files_to_load.items():
        file_path = os.path.join(json_folder, filename)

        if os.path.exists(file_path):
            try:
                # 1. Leggi il JSON
                df = pd.read_json(file_path, orient='records')
                print(f"Elaborazione {table_name}...")

                # --- GESTIONE DATE ---
                if 'date' in df.columns:
                    if table_name == 'movies':
                        # CORREZIONE CRITICA: Convertiamo il timestamp/stringa in datetime, estraiamo SOLO l'anno,
                        # e poi castiamo in Int64 (che in Postgres diventa INT o BIGINT).
                        df['date'] = pd.to_datetime(df['date'], unit='ms', errors='coerce')
                        df['date'] = df['date'].dt.year.astype('Int64')  # <--- QUESTA È LA RIGA CHIAVE
                    else:
                        # Per le altre tabelle, convertiamo in timestamp (java.util.Date)
                        df['date'] = pd.to_datetime(df['date'], errors='coerce')

                # --- GESTIONE ID e CHIAVI ESTERNE ---

                if table_name == 'movies':
                    # Movies è l'unica tabella che mantiene il suo 'id' originale come Primary Key
                    pass
                else:
                    # PER TUTTE LE ALTRE TABELLE (Actors, Countries, Crew, ecc...)

                    # 1. Rinomina l'id del film (presente nel JSON) in 'movie_id'
                    # Questo matcha il campo 'private Integer movieId' in Java
                    if 'id' in df.columns:
                        df.rename(columns={'id': 'movie_id'}, inplace=True)

                    # 2. Genera una NUOVA Primary Key univoca (1, 2, 3...)
                    # Questo matcha il campo 'private Long id' (@Id @GeneratedValue) in Java
                    df['id'] = range(1, len(df) + 1)

                # --- RINOMINE SPECIFICHE PER LE ENTITY JAVA ---

                # Countries: nel JSON è 'name', in Java Entity è 'country'
                if table_name == 'countries' and 'name' in df.columns:
                    df.rename(columns={'name': 'country'}, inplace=True)

                # Crew: nel JSON è 'name', in Java Entity spesso è 'name', ma controlla se serve 'crew_name'
                # Se nel tuo Crew.java hai 'private String name', questo blocco sotto puoi rimuoverlo o commentarlo.
                # Se invece hai 'crew_name', lascialo. Di solito 'name' va bene se non crea conflitti.
                if table_name == 'crew' and 'name' in df.columns:
                    # df.rename(columns={'name': 'crew_name'}, inplace=True)
                    pass

                    # Studios: nel JSON è 'studio', in Java Entity spesso è 'studio_name' o 'studio'
                if table_name == 'studios' and 'studio' in df.columns:
                    df.rename(columns={'studio': 'studio_name'}, inplace=True)

                # Oscars: Tagliamo a 254 caratteri per sicurezza
                if table_name == 'oscars' and 'name' in df.columns:
                    df['name'] = df['name'].astype(str).str.slice(0, 254)

                # -----------------------------------------------

                # Carica su Postgres
                df.to_sql(table_name, engine, if_exists='replace', index=False)

                print(f"✅ Tabella '{table_name}' caricata ({len(df)} righe).")

            except Exception as e:
                print(f"❌ ERRORE su {filename}: {e}")
                print(f"   Colonne nel file: {df.columns.tolist()}")
        else:
            print(f"⚠️ File non trovato: {filename}")


# --- MAIN ---
if __name__ == "__main__":
    populate_database()