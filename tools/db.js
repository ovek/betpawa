const program = require("commander");
const project = require("./util/project.js");
const sql = require("./util/sql-executor.js");
const log = require("./util/log.js");
const path = require("path");
const config = require("./util/project.js").config;
const dbConfig = require("./util/project.js").dbConfig;

const dbDir = project.dbDir;

const runScript = async (scriptPath, env, app) => {
  let conf = (dbConfig[app] ? dbConfig[app] : {})[env]; //application specific conf
  conf = conf || dbConfig[env];

  return sql.execute(
    conf.databaseUrl,
    conf.databasePort,
    conf.databaseAdminDb,
    conf.databaseAdminUsername,
    conf.databaseAdminPassword,
    scriptPath
  );
};

const drop = (app, env) => {
  return new Promise((resolve, reject) => {
    log.info(`Dropping ${app} in ${env}`);

    runScript(path.join(dbDir, app, 'terminate.sql'), env, app)
      .then(() => {
        runScript(path.join(dbDir, app, 'drop.sql'), env, app)
          .then(resolve);
      });
  });
};

const setup = (app, env) => {
  return new Promise((resolve, reject) => {
    log.info(`Initializing  ${app} in ${env}`);

    runScript(path.join(dbDir, app, 'setup.sql'), env, app).then(resolve);
  })
};

program
  .command('clean [app] [env]')
  .description("rebuild existing database")
  .action((app, env, cmd) => {
    app = app || 'all';

    if (app === 'all') {
      config.db.reduce((p, app) => {
        return p.then(() => drop(app, env || "local"));
      }, Promise.resolve()).then(() => {
        config.db.reduce((p, app) => {
          return p.then(() => setup(app, env || "local"));
        }, Promise.resolve())
      });
    } else {
      drop(app, env || "local")
        .then(() => {
          setup(app, env || "local")
        });
    }
  });

program.parse(process.argv);