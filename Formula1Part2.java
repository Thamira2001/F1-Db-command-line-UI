import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Formula1Part2 {
    public static void main(String[]args){
        // Connect to your database.
        // Replace server name, username, and password with your credentials
        Properties prop = new Properties();
        String fileName = "auth.cfg";
        try {
            FileInputStream configFile = new FileInputStream(fileName);
            prop.load(configFile);
            configFile.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find config file.");
            System.exit(1);
        } catch (IOException ex) {
            System.out.println("Error reading config file.");
            System.exit(1);
        }
        String username = (prop.getProperty("username"));
        String password = (prop.getProperty("password"));

        if (username == null || password == null){
            System.out.println("Username or password not provided.");
            System.exit(1);
        }

        F1DataBase f1_db= new F1DataBase(username, password);
        runProgUI(f1_db);

    }

    //function that runs our program ui
    public static void runProgUI(F1DataBase db){
        Scanner console = new Scanner(System.in);
        System.out.println("\n=================================================================================");
        System.out.println("||                                                                             || ");
        System.out.println("||                    Welcome to the Formula1 Database!                        || ");
        System.out.println("||                                                                             || ");
        System.out.println("||                    For a list of Commands, type: help                       || ");
        System.out.println("||                                                                             || ");
        System.out.println("=================================================================================\n");
        System.out.print("F1_DB > ");
        String line = console.nextLine();
        String[] parts;
        String arg = "";
        String arg2 = "";

        while (line != null && !line.equals("end")) {
            parts = line.split("\\s+");
            if (line.indexOf(" ") > 0)
                arg = line.substring(line.indexOf(" ")).trim();
            arg2 = line.substring(line.indexOf(" ")+1).trim();
            if (parts[0].equals("help")){
                printOption();
            }

            else if (parts[0].equals("dri")) {
                if (parts.length >= 2)
                    db.getDriverInfo(arg);
                else
                    System.out.println("Require an argument (First and Last name) for this command");
            }

            else if (parts[0].equals("con")) {
                if (parts.length >= 2)
                    db.getConstructors(arg);
                else
                    System.out.println("Require an argument (First and Last name) for this command");

            }
            else if (parts[0].equals("sw")) {
                if (parts.length >= 2)
                    db.getSeasonWinner(Integer.parseInt(arg));
                else
                    System.out.println("Require an argument (year) for this command");
            }
            else if (parts[0].equals("sc")) {
                if (parts.length >= 2)
                    db.getAllCircuitsInSeason(Integer.parseInt(arg));
                else
                    System.out.println("Require an argument (year) for this command");
            }
            else if (parts[0].equals("all")) {
                db.getAllWinners();
            }
            else if (parts[0].equals("cr")) {
                if (parts.length >= 2)
                    db.getAllRacesForCircuit(arg);
                else
                    System.out.println("Require an argument (Circuit Name) for this command");
            }
            else if (parts[0].equals("wf")) {
                if (parts.length >= 2)
                    db.getDriverWinsStartFirst(arg);
                else
                    System.out.println("Require an argument (First and Last name) for this command");
            }
            else if (parts[0].equals("wnf")) {
                if (parts.length >= 2)
                    db.getDriverWinsNotStartFirst(arg);
                else
                    System.out.println("Require an argument (First and Last name) for this command");
            }
            else if (parts[0].equals("tp")) {
                if (parts.length >= 2)
                    db.totalPoints(arg);
                else
                    System.out.println("Require an argument (First and Last name) for this command");
            } else if (parts[0].equals("1cir")) {
                if (parts.length >= 2)
                    db.firstCircuits(arg);
                else
                    System.out.println("Require an argument (First and Last name) for this command");
            } else if (parts[0].equals("dnf")) {
                if (parts.length >= 2)
                    db.didNotFinish(arg);
                else
                    System.out.println("Require an argument (First and Last name) for this command");
            } else if (parts[0].equals("claps")) {
                if (parts.length >= 3)
                    db.totalLaps(arg, arg2);
                else
                    System.out.println("Require an argument (First and Last name and CircuitName) for this command");
            } else if (parts[0].equals("stat")) {
                if (parts.length >= 3)
                    db.statusCount(arg, arg2);
                else
                    System.out.println("Require an argument (First and Last name and status) for this command");
            }
            else if (parts[0].equals("top5")){
                db.top5CareerPoints();
            }
            else if (parts[0].equals("engF")){
                db.getTotalEngineFailsEra();
            }
            else if (parts[0].equals("top3DNF")){
                db.getTop3CausesOfNotFinishingEra();
            }
            else if (parts[0].equals("pbcY")) {
                if (parts.length >= 2)
                    db.probabNotFinish(Integer.parseInt(arg));
                else
                    System.out.println("Require an argument (year) for this command");
            }
            else {
                System.out.println("Invalid input. Please review the options menu again!");
            }

            System.out.print("F1_DB > ");
            line=console.nextLine();

        }
        console.close();
    }

    //function that gives the user the options of choosing what kind of queries are available to run
    private static void printOption(){
        System.out.println("\n==================================== COMMANDS ===================================\n");
        System.out.println("COMMAND  <USER INPUT>              - OUTCOME");
        System.out.println("help                                - Displays every command the user can run");
        System.out.println("dri      <First Last>               - Get all the information about a driver (eg: 's Lewis Hamilton')");
        System.out.println("con      <First Last>               - Get a list of all the constructors that a driver has competed for (eg: 'con Lewis Hamilton')");
        System.out.println("sw       <year>                     - Get the winner of a particular season (eg: 'sw 2016')");
        System.out.println("sc       <year>                     - Get a list of all the circuits used in a season (eg: 'sc 2018')");
        System.out.println("all                                 - Get a list of all winners in the history of F1 ordered by season");
        System.out.println("cr       <CircuitName>              - Get the total number of races held on a circuit (eg: 'cr Fuji Speedway')");
        System.out.println("wf       <First Last>               - Get the total number of races for a driver when he started in first and won the race (eg: 'wf Lewis Hamilton'");
        System.out.println("wnf      <First Last>               - Get the total number of races for a driver when he did NOT start in first and won the race (eg: 'wnf Lewis Hamilton')");
        //New Qurires
        System.out.println("tp       <First Last>               - Get the total number of points a driver has gotten");
        System.out.println("1cir     <First Last>               - Get all circuits that the driver has placed first on");
        System.out.println("dnf      <First Last>               - Get the number times a driver has not finished the race");
        System.out.println("claps    <First Last CircuitName>   - Get the number of times a driver has gone around the circuit");
        System.out.println("stat     <First Last Status>        - Get the number of times a driver has recieved the given status");

        //new Queries
        System.out.println("top5                                 - Get the top 5 drivers with highest career points ");
        System.out.println("engF                                 - get the total number of engine failures that caused a dnf during a race in each era(only shows for hybridEra , V8Era and V10Era) ");
        System.out.println("top3DNF                              - provides the top three causes of not finishing a race in each era(hybridEra, V8Era and V10Era only)");
        System.out.println("pbcY       <year>                    - provides the probability of each cause for not finishing all the laps in a race in a given year(example pbcY 2014) ");
        System.out.println("end                                  - Ends the program");

        System.out.println("\n================================ END OF COMMANDS ================================\n");
    }


}

//F1DataBase class
class F1DataBase{
    private String connectionUrl;
    //constructor : creates the connection url
    public F1DataBase (String username, String password){
        this.connectionUrl =
                "jdbc:sqlserver://uranium.cs.umanitoba.ca:1433;"
                        + "database=cs3380;"
                        + "user=" + username + ";"
                        + "password="+ password +";"
                        + "encrypt=false;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";
    }

    //returns the driver's info given the driver's first and last name
    public void getDriverInfo(String driverName){
        String selectSql = "select driverID,forename, surname,number,code, driverNationality from drivers " +
                "where CONVERT(NVARCHAR(20),forename)= ? and CONVERT(NVARCHAR(20),surname)= ?;" ;

        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(selectSql);) {
            // Create and execute a SELECT SQL statement
            String [] name= driverName.split(" ");

            statement.setString(1,name[0]);
            statement.setString(2,name[1]);
            resultSet = statement.executeQuery();


            // Print results from select statement
            while (resultSet.next()) {
                String output=String.format("Driver ID: %d\nDriver Name: %s %s" +
                                "\nDriver Number: %s\nDriver Code: %s\nDriver Nationality: %s",resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5),resultSet.getString(6));
                System.out.println(output);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //helper function
    //returns the driverId given the driverName
    public int getDriverID(String driverName){
        int id=-1;
        String selectSql = "select driverID from drivers " +
                "where CONVERT(NVARCHAR(20),forename)= ? and CONVERT(NVARCHAR(20),surname)= ?;" ;

        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(selectSql);) {
            // Create and execute a SELECT SQL statement
            String [] name= driverName.split(" ");

            statement.setString(1,name[0]);
            statement.setString(2,name[1]);
            resultSet = statement.executeQuery();


            // Print results from select statement
            while (resultSet.next()) {
                id=resultSet.getInt(1);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    //returns all the constructors a driver has driven for
    public void getConstructors(String driverName){
        String selectSql = "with list as (select drivers.forename, drivers.surname, constructors.constructorName\n" +
                "from drivers\n" +
                "join results on drivers.driverID = results.driverID\n" +
                "join constructors on constructors.constructorID = results.constructorID\n" +
                "where drivers.driverID = ?)" +
                "select distinct CONVERT(NVARCHAR(20),forename), CONVERT(NVARCHAR(20),surname), CONVERT(NVARCHAR(20),constructorName) from list;";
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(selectSql);) {

            statement.setInt(1,getDriverID(driverName));

            resultSet = statement.executeQuery();

            System.out.print(driverName+ " has driven for: ");
            // Print results from select statement
            while (resultSet.next()) {

                String output=String.format("%s, ", resultSet.getString(3));
                System.out.print(output);

            }
            System.out.println();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //function that returns all the circuits where the races were held during a season
    public void getAllCircuitsInSeason(int year){
        String selectSql = "select circuitName from circuits " +
                "join race on circuits.circuitID = race.circuitID " +
                "where year = ?;";
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(selectSql);) {

            statement.setInt(1,year);

            resultSet = statement.executeQuery();
            //resultSet = statement.executeQuery(selectSql);
            System.out.println(year +" season had races in the following circuits: ");

            // Print results from select statement
            while (resultSet.next()) {
                String output=String.format("%s, ", resultSet.getString(1));
                System.out.print(output);
            }
            System.out.println();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //function that returns the winner of the season in a certain year
    public void getSeasonWinner(int year){
        String selectSql = "select top 1 forename, surname from drivers " +
                "join driverStandings on drivers.driverID = driverStandings.driverID " +
                "join race on driverStandings.raceID = race.raceID where race.year =? " +
                "order by driverPoints desc;";
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(selectSql);) {

            statement.setInt(1,year);

            resultSet = statement.executeQuery();

            System.out.print("The winner of the " + year +" season is:");
            // Print results from select statement
            while (resultSet.next()) {
                String output=String.format(" %s %s ", resultSet.getString(1), resultSet.getString(2));
                System.out.println(output);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // function that prints the winner for every season in F1 history
    public void getAllWinners() {
        String selectSql = "select distinct year, forename, surname " +
                "from drivers " +
                "join results on drivers.driverID = results.driverID " +
                "join race outerRace on results.raceID = outerRace.raceID " +
                "where drivers.driverID in (" +
                "select top 1 drivers.driverID " +
                "from drivers " +
                "join driverStandings on drivers.driverID = driverStandings.driverID " +
                "join race innerRace on driverStandings.raceID = innerRace.raceID " +
                "where innerRace.year = outerRace.year " +
                "order by driverPoints desc" +
                ")" +
                "order by year desc;";

        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(selectSql);) {

            resultSet = statement.executeQuery();

            System.out.println("The list of all winners in the history of F1 (season, first name, last name):");
            // Print results from select statement
            while (resultSet.next()) {
                String output=String.format(" %s %s %s ", resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3));
                System.out.println(output);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // function that prints the total number of races held at a circuit
    public void getAllRacesForCircuit(String circuitName) {
        String selectSql = "select circuitName, count(raceID) as numberOfRaces " +
                "from circuits join race on circuits.circuitID = race.circuitID " +
                "where circuitName = ? " +
                "group by circuits.circuitID, circuitName;";

        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(selectSql);) {

            statement.setString(1, circuitName);
            resultSet = statement.executeQuery();

            System.out.print("The total number of races held at " + circuitName + " is: ");
            // Print results from select statement
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("numberOfRaces"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // function that prints the number of races won by a driver when starting the race in first (grid 1)
    public void getDriverWinsStartFirst(String driverName) {
        String selectSql = "select count(results.raceID) as numberOfRaces " +
                "from drivers join results on drivers.driverID = results.driverID " +
                "join race on results.raceID = race.raceID " +
                "where drivers.driverID = ? and grid = 1 and positionOrder = 1;";

        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(selectSql);) {

            statement.setInt(1, getDriverID(driverName));
            resultSet = statement.executeQuery();

            System.out.print("The total number of races won when starting in first (grid 1) for " + driverName +
                    " is: ");
            // Print results from select statement
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // function that prints the number of races won by a driver when NOT starting the race in first (grid 1)
    public void getDriverWinsNotStartFirst(String driverName) {
        String selectSql = "select count(results.raceID) as numberOfRaces " +
                "from drivers join results on drivers.driverID = results.driverID " +
                "join race on results.raceID = race.raceID " +
                "where drivers.driverID = ? and grid <> 1 and positionOrder = 1;";

        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(selectSql);) {

            statement.setInt(1, getDriverID(driverName));
            resultSet = statement.executeQuery();

            System.out.print("The total number of races won when NOT starting in first (grid 1) for " + driverName +
                    " is: ");
            // Print results from select statement
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // function that prints the total number of points scored by a driver
    public void totalPoints(String driverName){
        String selectSql = "select Sum(results.driverRacePoints) "+
                "from drivers "+
                "join results on drivers.driverID = results.driverID "+
                "where drivers.driverID = ?";

        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(selectSql);) {

            statement.setInt(1, getDriverID(driverName));
            resultSet = statement.executeQuery();

            System.out.print("The total number of points scored by " + driverName +
                    " is: ");
            // Print results from select statement
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // function that prints all the circuits a driver has finished first on
    public void firstCircuits(String driverName){
        String selectSql = "select distinct circuits.circuitName "+
                "from drivers "+
                "join results on drivers.driverID = results.driverID "+
                "join race on results.raceID = race.raceID "+
                "join circuits on circuits.circuitID = race.circuitID "+
                "where results.positionOrder = 1 and drivers.driverID = ?";

        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(selectSql);) {
            statement.setInt(1, getDriverID(driverName));
            resultSet = statement.executeQuery();

            System.out.println("The list of all circuits that the "+driverName+"has placed first on");
            // Print results from select statement
            while (resultSet.next()) {
                String output=String.format("%s ", resultSet.getString(1));
                System.out.println(output);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // function that prints all the number of times a driver did not finish
    public void didNotFinish(String driverName){
        String selectSql = "select Count(results.raceID) "+
                "from drivers "+
                "join results on drivers.driverID = results.driverID "+
                "where drivers.driverID = ? and results.statusID <> 1";

        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(selectSql);) {

            statement.setInt(1, getDriverID(driverName));
            resultSet = statement.executeQuery();

            System.out.print("The total number of times that " + driverName +
                    " did not finish the race is: ");
            // Print results from select statement
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // function that prints the total number of laps a driver has done on the circuit
    public void totalLaps(String driverName, String circuitName){
        String selectSql = "select Sum(results.laps) "+
                "from drivers "+
                "join results on drivers.driverID = results.driverID "+
                "join race on results.raceID = race.raceID "+
                "join circuits on circuits.circuitID = race.circuitID "+
                "where circuits.circuitName = ? and drivers.driverID = ?";

        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(selectSql);) {

            statement.setInt(2, getDriverID(driverName));
            statement.setString(1, circuitName);
            resultSet = statement.executeQuery();

            System.out.print("The total number of laps done by " + driverName +
                    " on the circuit "+circuitName+" is: ");
            // Print results from select statement
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // function that prints the total number of times a driver has recieved a condition code
    public void statusCount(String driverName, String status ){
        String selectSql = "select Count(results.raceID) "+
                "from drivers "+
                "join results on drivers.driverID = results.driverID "+
                "where drivers.driverID = ? and results.status = ?";

        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(selectSql);) {

            statement.setInt(1, getDriverID(driverName));
            statement.setString(2, status);
            resultSet = statement.executeQuery();

            System.out.print("The total number of times that " + driverName +
                    " recieved the status "+status+" is: ");
            // Print results from select statement
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //function that gives a list of the top 5 drivers with most career points
    public void top5CareerPoints(){
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            String selectSql="select top 5 forename, surname, Sum(results.driverRacePoints) as careerPoints from drivers " +
                    "join results on results.driverID=drivers.driverID " +
                    "group by forename, surname  " +
                    "order by careerPoints desc;";


            resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                String output=String.format("DriverName: %s %s, careerPoints: %d  \n", resultSet.getString(1),
                        resultSet.getString(2), resultSet.getInt(3));
                System.out.println(output);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //function that provides the total number of engine failures in hybridEra, V8Era and V10Era
    public void getTotalEngineFailsEra(){
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            String selectSql="with hybridEra as (select  constructorName,count(results.raceID)as noOFEngineFailures from  results " +
                    "join condition on condition.statusID=results.statusID " +
                    "join race on results.raceID=race.raceID " +
                    "join constructors on results.constructorID=constructors.constructorID " +
                    "where condition.statusID=5 and race.year BETWEEN 2014 and 2021 " +
                    "group by constructorName)," +
                    "hybridEraList as(Select * FROM (Values('HybridEra'))as t(era) ,hybridEra)," +
                    "V8Era as (select constructorName, count(results.raceID) as noOFEngineFailures from  results " +
                    "join condition on condition.statusID=results.statusID " +
                    "join race on results.raceID=race.raceID " +
                    "join constructors on results.constructorID=constructors.constructorID " +
                    "where condition.statusID=5 and race.year BETWEEN 2006 and 2013 " +
                    "group by constructorName)," +
                    "V8EraList as(Select * FROM (Values('V8Era'))as t(era) ,V8Era)," +
                    "V10Era as (select constructorName, count(results.raceID) as noOFEngineFailures from  results " +
                    "join condition on condition.statusID=results.statusID " +
                    "join race on results.raceID=race.raceID " +
                    "join constructors on results.constructorID=constructors.constructorID " +
                    "where condition.statusID=5 and race.year BETWEEN 1989 and 2005 " +
                    "group by constructorName)," +
                    "V10EraList as(Select * FROM (Values('V10Era'))as t(era) ,V10Era)," +
                    "engineFailList as (select  * from hybridEraList " +
                    "union " +
                    "select  * from V8EraList " +
                    "union " +
                    "select * from V10EraList)" +
                    "select era, Sum(noOFEngineFailures) as totalEngineFails from engineFailList group by era order by totalEngineFails;";


            resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                String output=String.format("In the  %s, there were %d EngineFailures that caused a DNF during a race \n", resultSet.getString(1),
                         resultSet.getInt(2));
                System.out.println(output);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //function provides the top three causes of not finishing a race in each era(hybridEra, V8Era and V10Era only)
    public void getTop3CausesOfNotFinishingEra(){
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            String selectSql="with HybridEra as(select  condition.status as causeOfDNF,count(results.statusID)as frequencyOfStatus from  results " +
                    "join condition on condition.statusID=results.statusID " +
                    "join race on results.raceID=race.raceID " +
                    "join constructors on results.constructorID=constructors.constructorID " +
                    "where condition.statusID!=1 and race.year BETWEEN 2014 and 2021 " +
                    "group by condition.status)," +
                    "top3Hybrid as(select top 3 * from HybridEra order by frequencyOfStatus DESC)," +
                    "hybridEraList as(Select * FROM (Values('HybridEra'))as t(era) ,top3Hybrid)," +
                    "V8Era as(select  condition.status as causeOfDNF,count(results.statusID)as frequencyOfStatus from  results  " +
                    "join condition on condition.statusID=results.statusID " +
                    "join race on results.raceID=race.raceID " +
                    "join constructors on results.constructorID=constructors.constructorID " +
                    "where condition.statusID!=1 and race.year BETWEEN 2006 and 2013 " +
                    "group by condition.status)," +
                    "top3V8 as(select top 3 * from V8Era order by frequencyOfStatus DESC)," +
                    "V8EraList as(Select * FROM (Values('V8Era'))as t(era) ,top3V8)," +
                    "V10Era as(select  condition.status as causeOfDNF,count(results.statusID)as frequencyOfStatus from  results " +
                    "join condition on condition.statusID=results.statusID " +
                    "join race on results.raceID=race.raceID " +
                    "join constructors on results.constructorID=constructors.constructorID " +
                    "where condition.statusID!=1 and race.year BETWEEN 1989 and 2005 " +
                    "group by condition.status), " +
                    "top3V10 as(select top 3 * from V10Era order by frequencyOfStatus DESC), " +
                    "V10EraList as(Select * FROM (Values('V10Era'))as t(era) ,top3V10) " +
                    "select  * from V8EraList " +
                    "union " +
                    "select  * from hybridEraList " +
                    "union " +
                    "select * from V10EraList;";


            resultSet = statement.executeQuery(selectSql);
            String outputHybrid="";
            String outputV8="";
            String outputV10="";
            // Print results from select statement
            while (resultSet.next()) {

                if(resultSet.getString(1).equals("HybridEra")){
                    outputHybrid+=String.format("Cause of not finishing: %s which happened %d times \n", resultSet.getString(2),
                            resultSet.getInt(3));
                }
                else if(resultSet.getString(1).equals("V8Era")){
                    outputV8+=String.format("Cause of not finishing: %s which happened %d times \n", resultSet.getString(2),
                            resultSet.getInt(3));
                }
                else if(resultSet.getString(1).equals("V10Era")){
                    outputV10+=String.format("Cause of not finishing: %s which happened %d times \n", resultSet.getString(2),
                            resultSet.getInt(3));
                }

            }
            System.out.println(" In Hybrid Era :\n"+outputHybrid);
            System.out.println(" In V8 Era :\n"+outputV8);
            System.out.println(" In V10 Era :\n"+outputV10);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //function that provides the probability of each cause for not finishing all the laps in a race in a given year
    public void probabNotFinish(int year){
        String selectSql="with statusList as(select  condition.status as causeOfDNF,count(results.statusID)as frequencyOfStatus from  results " +
                "join condition on condition.statusID=results.statusID " +
                "join race on results.raceID=race.raceID " +
                "join constructors on results.constructorID=constructors.constructorID " +
                "where condition.statusID!=1 and race.year=? " +
                "group by condition.status)," +
                "totalOutcomes as (select  race.year,count(results.statusID)as totalOutcomes from  results " +
                "join condition on condition.statusID=results.statusID " +
                "join race on results.raceID=race.raceID " +
                "join constructors on results.constructorID=constructors.constructorID " +
                "where condition.statusID!=1 and race.year=? " +
                "group by race.year)," +
                "list1 as(select * from statusList, totalOutcomes) select causeOfDNF, CAST(frequencyOfStatus as decimal )/CAST(totalOutcomes as decimal)*100 as probabilityOfCause from list1 order by probabilityOfCause DESC;";
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(selectSql);) {
            statement.setInt(1,year);
            statement.setInt(2,year);



            resultSet = statement.executeQuery();
            System.out.println("In "+year+" :\n");
            // Print results from select statement
            while (resultSet.next()) {
                String output=String.format("There was a %f %% chance of a %s causing a driver to not finish all the laps of a race \n ",
                        resultSet.getFloat(2), resultSet.getString(1));
                System.out.println(output);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
