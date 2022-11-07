package com.exhibit.dao.connection;

import java.io.*;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.exhibit.dao.constants.UtilConstants.INFO_LOGGER;

public class TestDB {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);
    private TestDB(){}
    public static void restartDBScript(){
        try {
            //Registering the Driver
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            //Getting the connection
            String mysqlUrl = "jdbc:mysql://localhost/exhibition_db_test";
            Connection con = DriverManager.getConnection(mysqlUrl, "root", "root");
            //Initialize the script runner
            ScriptRunner sr = new ScriptRunner(con);
            //Creating a reader object

            ClassLoader classLoader = TestDB.class.getClassLoader();
            File file = new File(classLoader.getResource("db/testDBInit.sql").getFile());

            try (InputStream inputStream2 = Files.newInputStream(file.toPath())){
                Reader reader =  new InputStreamReader(inputStream2);
                //Running the script
                sr.runScript(reader);
            }
        } catch (Exception e){
            logger.error(e);
        }
    }
}
