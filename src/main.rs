use serde::Deserialize;
use sqlx::postgres::PgPoolOptions;
use std::{env, process};
use std::fs::File;
use std::io::BufReader;

#[derive(Debug, Deserialize)]
struct Database {
    connection: Connection,
    tables: Vec<String>,
}

#[derive(Debug, Deserialize)]
struct Connection {
    url: String,
}

#[tokio::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
    let mut args = env::args();

    let program = args.next().unwrap_or_else(|| "geekswimmers".to_string());
    let json_path = match args.next() {
        Some(path) => path,
        None => {
            eprintln!("Usage: {} <path-to-database-json>", program);
            process::exit(1);
        }
    };

    let file = File::open(json_path)?;
    let reader = BufReader::new(file);

    let database: Database = serde_json::from_reader(reader)?;

    let pool = PgPoolOptions::new()
        .max_connections(10)
        .connect(&database.connection.url)
        .await?;

    for table in database.tables {
        let query = format!("select * from {table}");
        let rows = sqlx::query(&query).fetch_all(&pool).await?;
        println!("{:?}", rows);
    }

    Ok(())
}
