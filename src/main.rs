use serde::Deserialize;
use std::{env, process};
use std::fs::File;
use std::io::BufReader;
use bigdecimal::BigDecimal;
use sqlx::postgres::{PgPoolOptions, PgValueRef};
use sqlx::{Column, Row, TypeInfo};
use sqlx::ValueRef;
use sqlx::{Decode, Postgres};
use sqlx::types::chrono;

#[derive(Debug, Deserialize)]
struct Database {
    connection: Connection,
    tables: Vec<String>,
}

#[derive(Debug, Deserialize)]
struct Connection {
    source: String,
    target: String,
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

    let source_pool = PgPoolOptions::new()
        .max_connections(10)
        .connect(&database.connection.source)
        .await?;

    let target_pool = PgPoolOptions::new()
        .max_connections(10)
        .connect(&database.connection.target)
        .await?;

    print!("Deleting records from target database...");
    for table in database.tables.iter().rev() {
        sqlx::query(&format!("delete from {};", table)).execute(&target_pool).await?;
    }
    println!(" done.");

    for table in database.tables {
        print!("Copying table {}...", table);
        let query = format!("select * from {table}");
        let rows = sqlx::query(&query).fetch_all(&source_pool).await?;

        for row  in &rows {
            let mut values: Vec<String> = Vec::new();
            for (i, col) in row.columns().iter().enumerate() {
                let raw = row.try_get_raw(i).expect("failed to read raw column value");
                values.push(pg_literal(raw, col.type_info().name()));
            }

            sqlx::query(&format!("insert into {} ({}) values ({});",
                     table,
                     row.columns().iter().map(|c| c.name()).collect::<Vec<_>>().join(", "),
                     values.join(", ")
            )).execute(&target_pool).await?;
        };
        println!(" done.");
    }

    Ok(())
}

fn pg_literal(raw: PgValueRef<'_>, ty_name: &str) -> String {
    if raw.is_null() {
        return "NULL".to_string();
    }

    match ty_name {
        "BOOL" => <bool as Decode<Postgres>>::decode(raw)
            .map(|v| v.to_string())
            .unwrap_or_else(|_| "<unprintable BOOL>".to_string()),
        "DATE" => <chrono::NaiveDate as Decode<Postgres>>::decode(raw)
            .map(|v| format!("'{}'", v.format("%Y-%m-%d")))
            .unwrap_or_else(|_| "<unprintable DATE>".to_string()),
        "FLOAT4" => <f32 as Decode<Postgres>>::decode(raw)
            .map(|v| v.to_string())
            .unwrap_or_else(|_| "<unprintable FLOAT4>".to_string()),
        "FLOAT8" => <f64 as Decode<Postgres>>::decode(raw)
            .map(|v| v.to_string())
            .unwrap_or_else(|_| "<unprintable FLOAT8>".to_string()),
        "INT2" => <i16 as Decode<Postgres>>::decode(raw)
            .map(|v| v.to_string())
            .unwrap_or_else(|_| "<unprintable INT2>".to_string()),
        "INT4" => <i32 as Decode<Postgres>>::decode(raw)
            .map(|v| v.to_string())
            .unwrap_or_else(|_| "<unprintable INT4>".to_string()),
        "INT8" => <i64 as Decode<Postgres>>::decode(raw)
            .map(|v| v.to_string())
            .unwrap_or_else(|_| "<unprintable INT8>".to_string()),
        "JSON" | "JSONB" => <serde_json::Value as Decode<Postgres>>::decode(raw)
            .map(|v| quote_string_literal(&v.to_string()))
            .unwrap_or_else(|_| "<unprintable JSON>".to_string()),
        "NUMERIC" => <BigDecimal as Decode<Postgres>>::decode(raw)
            .map(|v| format!("'{}'", v.to_string()))
            .unwrap_or_else(|_| "<unprintable NUMERIC>".to_string()),
        "TIMESTAMP" => <chrono::NaiveDateTime as Decode<Postgres>>::decode(raw)
            .map(|v| format!("'{}'", v.format("%Y-%m-%d %H:%M:%S")))
            .unwrap_or_else(|_| "<unprintable TIMESTAMP>".to_string()),

        _ => <String as Decode<Postgres>>::decode(raw)
            .map(|s| quote_string_literal(&s))
            .unwrap_or_else(|_| format!("<unprintable {}>", ty_name)),
    }
}

fn quote_string_literal(s: &str) -> String {
    format!("'{}'", s.replace('\'', "''"))
}