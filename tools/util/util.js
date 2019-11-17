var _ = require('underscore');

exports.toJavaArgs = function(args) {
    return _.chain(args)
            .map(function (value, key) { return "-D" + key + "=" + value; })
            .reduce(function (memo, arg) { return memo + " " + arg ; })
            .value();
};

exports.toDashArgs = function(args) {
    return _.chain(args)
            .map(function (value, key) { return "--" + key + "=" + value; })
            .reduce(function (memo, arg) { return memo + " " + arg ; })
            .value();
};