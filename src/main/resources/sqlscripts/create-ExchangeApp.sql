CREATE DATABASE IF NOT EXISTS ExchangeApp;

CREATE TABLE ExchangeApp.exchanges (
    `exchange_id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    `code` VARCHAR(255),
    `country` VARCHAR(255),
    `timezone` VARCHAR(255),
    `status` VARCHAR(255),
PRIMARY KEY (exchange_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE ExchangeApp.trading_sessions (
    `trading_session_id` INT AUTO_INCREMENT,
    `exchange_id` INT,
    `session_date` DATETIME,
    `open_time` VARCHAR(255),
    `close_time` VARCHAR(255),
    `status` VARCHAR(255),
PRIMARY KEY (trading_session_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE ExchangeApp.exchange_instruments (
    `exchange_instrument_id` INT AUTO_INCREMENT,
    `exchange_id` INT,
    `instrument_id` INT,
    `exchange_symbol` VARCHAR(255),
    `active` BOOLEAN,
PRIMARY KEY (exchange_instrument_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

USE drcvelocity;
