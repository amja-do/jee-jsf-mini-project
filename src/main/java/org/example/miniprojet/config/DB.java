package org.example.miniprojet.config;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.ServletContext;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
    private Connection c=null;
    ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    String envFilePath = context.getRealPath("/WEB-INF/env.properties");
    private Properties props= new Properties();
    public DB(){
        

    }

    public Connection getConnection(){
        try {
            if(c == null || c.isClosed()){
                try {
                    props.load(new FileInputStream(envFilePath));
                    Class.forName(props.getProperty("DB_DRIVER"));
                    c=DriverManager.getConnection(
                            props.getProperty("DB_URL"),
                            props.getProperty("DB_USERNAME"),
                            props.getProperty("DB_PASSWORD"));
                    c.setAutoCommit(true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public void closeConnection(){
        try {
            if(c != null && !c.isClosed()){
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
