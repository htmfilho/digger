use serde::Deserialize;
use sqlx::postgres::PgPoolOptions;
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
    let file = File::open("geekswimmers.json")?;
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
