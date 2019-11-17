const program = require("commander");
const project = require("./util/project.js");
const sql = require("./util/sql-executor.js");
const log = require("./util/log.js");
const path = require("path");
const csv = require('csv-parser');
const fs = require('fs');
const moment = require('moment');

String.prototype.replaceAll = function(search, replacement) {
  var target = this;
  return target.replace(new RegExp(search, 'g'), replacement);
};

const linesInInsert = 1000;
const cleanInt = (row, elem) => row[elem] === '' ? 'NULL' : `${row[elem]}`;
const cleanBoolean = (row, elem) => row[elem] === 'Y' ? 'TRUE' : 'FALSE';
const cleanString = (row, elem, converter) => {
  if (row[elem] === '') {
    return 'NULL';
  }

  let val = row[elem];
  val = val.replaceAll("'", "''");

  if (converter) {
    val = converter(row[elem])
  }

  return `'${val}'`
};

program
  .command('convert-exclusionList')
  .action(() => {
    const filePath = "./data/EXCLUSIONLIST_V1_FULL_20190830.txt";

    const insert = "INSERT INTO account.swift_exclusionlist (record_key,country_code,iban_national_id,bic,valid_from,field_a,field_b,active) VALUES\n";

    let res = insert;
    let rows = 0;

    fs.createReadStream(filePath)
      .pipe(csv({separator: '\t'}))
      .on('data', (row) => {

        const rowKey = row["RECORD KEY"] === '' ? 'NULL' : `'${row["RECORD KEY"]}'`;
        const countryCode = row["COUNTRY CODE"] === '' ? 'NULL' : `'${row["COUNTRY CODE"]}'`;
        const ibanNationalId = row["IBAN NATIONAL ID"] === '' ? 'NULL' : `'${row["IBAN NATIONAL ID"]}'`;
        const bic = row["BIC"] === '' ? 'NULL' : `'${row["BIC"]}'`;
        const valid_from = row["VALID FROM"] === '' ? 'NULL' : `'${row["VALID FROM"]}'`;
        const field_a = row["FIELD A"] === '' ? 'NULL' : `'${row["FIELD A"]}'`;
        const field_b = row["FIELD B"] === '' ? 'NULL' : `'${row["FIELD B"]}'`;
        const active = 'TRUE';

        res += `(${rowKey}, ${countryCode}, ${ibanNationalId}, ${bic}, ${valid_from}, ${field_a}, ${field_b}, ${active}),\n`;
        rows += 1;

        if (rows % linesInInsert === 0) {
          res = res.slice(0, -2) + ";";
          res += "\n\n";
          res += insert;
        }

      })
      .on('end', () => {

        fs.writeFile(`${filePath}.sql`, res.slice(0, -2) + ";", function (err) {
          if (err) {
            return console.log(err);
          }

          console.log("The file was saved!");
        });

      });


  });


program
  .command('convert-structure')
  .action(() => {
    const filePath = "./data/IBANSTRUCTURE_V2_MONTHLY_FULL_20190830.txt";

    const insert = "INSERT INTO account.swift_ibanstructure (iban_country_code,iban_country_code_position,iban_country_code_length,iban_check_digit_position, iban_check_digit_length,bank_identifier_position,bank_identifier_length,branch_identifier_position, branch_identifier_length,iban_national_id_length,account_number_position,account_number_length,iban_total_length,sepa,optional_commence_date,mandatory_commence_date,active) VALUES\n";

    let res = insert;
    let rows = 0;

    fs.createReadStream(filePath)
      .pipe(csv({separator: '\t'}))
      .on('data', (row) => {

        const r1 = cleanString(row, "IBAN COUNTRY CODE");//iban_country_code
        const r2 = cleanInt(row, "IBAN COUNTRY CODE POSITION");//iban_country_code_position
        const r3 = cleanInt(row, "IBAN COUNTRY CODE LENGTH"); //iban_country_code_length
        const r4 = cleanInt(row, "IBAN CHECK DIGITS POSITION");//iban_check_digit_position
        const r5 = cleanInt(row, "IBAN CHECK DIGITS LENGTH");//iban_check_digit_length
        const r6 = cleanInt(row, "BANK IDENTIFIER POSITION");//bank_identifier_position
        const r7 = cleanInt(row, "BANK IDENTIFIER LENGTH");//bank_identifier_length
        const r8 = cleanInt(row, "BRANCH IDENTIFIER POSITION");//branch_identifier_position
        const r9 = cleanInt(row, "BRANCH IDENTIFIER LENGTH");//branch_identifier_length

        const r10 = cleanInt(row, "IBAN NATIONAL ID LENGTH");//iban_national_id_length
        const r11 = cleanInt(row, "ACCOUNT NUMBER POSITION");//account_number_position
        const r12 = cleanInt(row, "ACCOUNT NUMBER LENGTH");//account_number_length
        const r13 = cleanInt(row, "IBAN TOTAL LENGTH");//iban_total_length
        const r14 = cleanBoolean(row, "SEPA");//sepa
        const r15 = cleanString(row, "OPTIONAL COMMENCE DATE", (val) => moment(val, "YYYYMMDD").format("YYYY-MM-DD"));
        const r16 = cleanString(row, "MANDATORY COMMENCE DATE", (val) => moment(val, "YYYYMMDD").format("YYYY-MM-DD"));
        const r17 = "TRUE";


        res += `(${r1}, ${r2}, ${r3}, ${r4}, ${r5}, ${r6}, ${r7}, ${r8}, ${r9}, ${r10}, ${r11}, ${r12}, ${r13}, ${r14}, ${r15}, ${r16}, ${r17}),\n`;
        rows += 1;

        if (rows % linesInInsert === 0) {
          res = res.slice(0, -2) + ";";
          res += "\n\n";
          res += insert;
        }

      })
      .on('end', () => {

        fs.writeFile(`${filePath}.sql`, res.slice(0, -2) + ";", function (err) {
          if (err) {
            return console.log(err);
          }

          console.log("The file was saved!");
        });

      });


  });


program
  .command('convert-ibanplus')
  .action(() => {
    const filePath = "./data/IBANPLUS_V3_FULL_20190830.txt";

    const insert = "INSERT INTO account.swift_ibanplus (record_key,institution_name,country_name,iso_country_code,iban_iso_country_code,iban_bic,routing_bic,iban_national_id,service_context,active) VALUES\n";

    let res = insert;
    let rows = 0;

    fs.createReadStream(filePath)
      .pipe(csv({separator: '\t'}))
      .on('data', (row) => {

        const r1 = cleanString(row, "RECORD KEY");//record_key
        const r2 = cleanString(row, "INSTITUTION NAME");//institution_name
        const r3 = cleanString(row, "COUNTRY NAME"); //country_name
        const r4 = cleanString(row, "ISO COUNTRY CODE");//iso_country_code
        const r5 = cleanString(row, "IBAN ISO COUNTRY CODE");//iban_iso_country_code
        const r6 = cleanString(row, "IBAN BIC");//iban_bic
        const r7 = cleanString(row, "ROUTING BIC");//routing_bic
        const r8 = cleanString(row, "IBAN NATIONAL ID");//iban_national_id
        const r9 = cleanString(row, "SERVICE CONTEXT");//service_context
        const r10 = "TRUE";//active


        res += `(${r1}, ${r2}, ${r3}, ${r4}, ${r5}, ${r6}, ${r7}, ${r8}, ${r9}, ${r10}),\n`;
        rows += 1;

        if (rows % linesInInsert === 0) {
          res = res.slice(0, -2) + ";";
          res += "\n\n";
          res += insert;
        }

      })
      .on('end', () => {

        fs.writeFile(`${filePath}.sql`, res.slice(0, -2) + ";", function (err) {
          if (err) {
            return console.log(err);
          }

          console.log("The file was saved!");
        });

      });


  });

program.parse(process.argv);