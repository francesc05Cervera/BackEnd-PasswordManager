# 🔐 BackEnd-PasswordManager

Backend REST per un **Password Manager** sicuro, sviluppato in **Java Spring Boot**. Permette la gestione di utenti e credenziali con autenticazione **JWT** e **cifratura** delle password salvate, esposto tramite API REST e containerizzato con **Docker**.

## ✨ Funzionalità

- **Autenticazione JWT**: registrazione e login utenti con token firmati e filtro di autenticazione dedicato (`JwtAuthenticationFilter`).
- **Gestione utenti**: CRUD su `Utente` tramite `UtenteCtrl`.
- **Gestione credenziali**: CRUD su `Credenziale` (le password salvate dall'utente) tramite `CredenzialeCtrl`.
- **Cifratura delle credenziali**: le password memorizzate vengono protette tramite `CredentialEncryptionService`, evitando di salvarle in chiaro nel database.
- **Health check**: endpoint `Ping` per verificare rapidamente lo stato del servizio.
- **Gestione centralizzata degli errori** tramite package `Exception`.
- **Containerizzazione** pronta per il deploy con `Dockerfile`.

## 🏗️ Architettura del progetto

Il progetto segue una classica architettura **a layer** di Spring Boot:

```
src/main/java/com/francesco/passwordmanager/
├── Controller/        # Endpoint REST (UtenteCtrl, CredenzialeCtrl, Ping)
├── DTO/                # Data Transfer Object per le richieste/risposte
├── Exception/          # Gestione centralizzata delle eccezioni
├── Repository/         # Repository JPA per l'accesso ai dati
├── Security/           # JWT, filtro di autenticazione e configurazione security
├── Service/            # Logica di business
├── entity/              # Entità JPA (Utente, Credenziale)
└── PasswordmanagerApplication.java   # Entry point Spring Boot
```

### Componenti di sicurezza

| Classe | Ruolo |
|---|---|
| `SecurityConfig` | Configura le regole di Spring Security (endpoint pubblici/protetti, filtri) |
| `JWTservice` | Generazione e validazione dei token JWT |
| `JwtAuthenticationFilter` | Intercetta le richieste ed estrae/valida il token JWT |
| `CredentialEncryptionService` | Cifra/decifra le credenziali salvate dagli utenti |

## 🛠️ Tech Stack

- **Java** + **Spring Boot** (Web, Security, Data JPA)
- **Maven** (con wrapper `mvnw` / `mvnw.cmd`)
- **JWT** per l'autenticazione stateless
- **Docker** per la containerizzazione
- Database relazionale via Spring Data JPA (configurabile in `application.properties`)

## 🚀 Avvio del progetto

### Requisiti
- Java 17+ (o versione indicata nel `pom.xml`)
- Maven (oppure usare il wrapper incluso)
- Docker (opzionale, per l'esecuzione containerizzata)

### Esecuzione in locale

```bash
# Clona il repository
git clone https://github.com/francesc05Cervera/BackEnd-PasswordManager.git
cd BackEnd-PasswordManager

# Avvia con Maven wrapper
./mvnw spring-boot:run       # Linux/Mac
mvnw.cmd spring-boot:run     # Windows
```

Configura le variabili in `src/main/resources/application.properties` (es. connessione al database, secret JWT) prima dell'avvio.

### Esecuzione con Docker

```bash
docker build -t password-manager-backend .
docker run -p 8080:8080 password-manager-backend
```

## 📡 Endpoint principali

| Metodo | Endpoint (indicativo) | Descrizione |
|---|---|---|
| `GET` | `/ping` | Health check del servizio |
| `POST` | `/utenti/register` | Registrazione nuovo utente |
| `POST` | `/utenti/login` | Login e rilascio token JWT |
| `GET/POST/PUT/DELETE` | `/credenziali/**` | Gestione delle credenziali salvate dall'utente autenticato |

> Gli endpoint esatti dipendono dalle annotazioni presenti in `UtenteCtrl` e `CredenzialeCtrl`; aggiorna questa tabella con i path reali una volta consultato il codice dei controller.

## 🔒 Sicurezza

Tutte le richieste (eccetto login/registrazione e ping) richiedono un header:

```
Authorization: Bearer <token_jwt>
```

Le password delle credenziali salvate non vengono mai memorizzate in chiaro: passano attraverso `CredentialEncryptionService` prima della persistenza su database.

## 📄 Licenza

Progetto didattico/personale — nessuna licenza specifica definita al momento.

## 👤 Autore

**Francesco Cervera** — Studente di Informatica, Università di Napoli
[GitHub](https://github.com/francesc05Cervera)
