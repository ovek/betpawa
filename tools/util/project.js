const path = require("path");

const getProjectRoot = function () {
    return path.join(__dirname, "..", "..");
};

const getDbScriptsDir = function () {
    return path.join(__dirname, "..", "db");
};

const getConfig = () => {
    return require(getProjectRoot() + "/tools/config.json");
};

const getDbConfig = () => {
    return require(getProjectRoot() + "/tools/db-config.json");
};

exports.rootDir = getProjectRoot();
exports.dbDir = getDbScriptsDir();
exports.config = getConfig();
exports.dbConfig = getDbConfig();
