var colors = require("colors/safe");

exports.debug = function (text) {
    console.log(colors.yellow(text));
};

exports.info = function (text) {
    console.log(colors.cyan(text));
};

exports.error = function (text) {
    console.error(colors.red(text));
};