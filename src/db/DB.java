package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {

    //Objeto tipo conexão
    private static Connection conect = null;

    //Método para conectar com o banco de dados
    public static Connection getConnection(){
        if (conect == null){
            try {
                Properties props = loadProperties();//Objeto do tipo Properties recebendo o método loadProperties
                String url = props.getProperty("dburl");//Variável String revebendo getProperty("dburl")
                conect = DriverManager.getConnection(url, props);
            }
            catch (SQLException ex){
                throw new DbExceptions(ex.getMessage());
            }
        }
        return conect;
    }

    //Método para fechar a conexão com o banco de dados
    public static void closeConnection (){
        if (conect != null){
            try {
                conect.close();
            }
            catch (SQLException ex){
                throw new DbExceptions(ex.getMessage());
            }
        }
    }

    //Método auxiliar para carregar as propriedades que estão no documento db.properties
    private static Properties loadProperties(){
        try(FileInputStream fs = new FileInputStream("db.properties")){
            Properties props = new Properties();
            props.load(fs);
            return props;
        }
        catch (IOException ex){
            throw new DbExceptions(ex.getMessage());
        }
    }
}
