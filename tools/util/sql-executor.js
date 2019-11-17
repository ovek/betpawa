const pg = require("pg").Client;
var fs = require('fs');

const readFile = (file) => {
  try {
    const data = fs.readFileSync(file, 'utf8');
    return data.toString();
  } catch (e) {
    console.log('Error:', e.stack);
  }
};

const executeSql = (client, sql) => {
  return new Promise((resolve, reject) => {
    client.query(
      sql,
      (err, res) => {
        // console.log(sql, err);
        resolve();
      });
  })
};

exports.execute = (host, port, db, username, password, file) => {
  return new Promise((resolve, reject) => {
      console.log(`Executing ${file}`);

      const client = new pg(
        {
          host: host,
          database: db,
          user: username,
          password: password,
          port: port,
        });

      client.connect();

      const sql = readFile(file);

      const commands = sql.split(";");

      commands.reduce((p, cmd) => {
        return p.then(() => executeSql(client, cmd));
      }, Promise.resolve())
        .then(() => {
          client.end();
          resolve();
        });
    }
  )
};