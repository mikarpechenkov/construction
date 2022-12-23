package com.mkenit.timemanager.models.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesDB {
    private static final Properties PROPERTIES=new Properties();
    static {
        loadProperties();
    }
    private PropertiesDB(){}
    private static void loadProperties()  {
        try {
            InputStream inputStream=PropertiesDB.class.getClassLoader().
                    getResourceAsStream("com/mkenit/timemanager/properties/application.properties");
            PROPERTIES.load(inputStream);

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key){
        return PROPERTIES.getProperty(key);
    }
}
