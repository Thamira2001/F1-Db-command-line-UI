


drop table if exists circuits;
drop table if exists drivers;
drop table if exists constructors;
drop table if exists race;
drop table if exists raceInformation;
drop table if exists results;
drop table if exists constructorStandings;
drop table if exists driverStandings;
drop table if exists status;
drop table if exists lapTimes;

create table circuits (
    circuitID integer primary key,
    circuitRef text not null,
    circuitName text,
    location text,
    country text not null,    
    latitude float,
    longitude float,
    altitude float
);
create table drivers (
    driverID integer primary key,
    driverRef text,
    number text,
    code text,
    forename text not null,
    surname text not null,
    dateOfBirth text,
    driverNationality text not null
);
create table constructors (
    constructorID integer primary key,
    constructorName text not null,
    constructorNationality text not null,
);
create table race (
    raceID integer primary key,
    year integer not null,
    circuitID integer,
);
create table raceInformation (
    raceID integer primary key,
    round integer not null,
    name text not null,
    date text not null,
    startTime text not null
);
create table results (
    raceID integer,
    driverID integer,
    constructorID integer,
    number integer, 
    grid integer,
    positionOrder integer,
    driverRacePoints integer,
    laps integer,
    lapTime text,
    milliseconds integer,
    fastestLap integer,
    rank integer,
    fastestLapTime text,
    fastestLapSpeed text,
    statusID integer
);
create table constructorStandings (
    raceID integer,
    constructorID integer,
    constructorPoints integer,
    constructorPosition integer    
);
create table driverStandings ( 
    raceID integer,
    driverID integer,
    driverPoints integer,
    driverPosition integer,
    wins integer
);
create table status (
    statusID integer primary key,
    status text not null
);
create table lapTimes (
    raceID integer,
    driverID integer,
    lap integer,
    position integer,
    time integer,
    milliseconds integer,
);
