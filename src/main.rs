use serde::Deserialize;
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

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("geekswimmers.json")?;
    let reader = BufReader::new(file);

    let database: Database = serde_json::from_reader(reader)?;

    for table in database.tables {
        println!("select * from {} order by id", table);
    }

    Ok(())
}
