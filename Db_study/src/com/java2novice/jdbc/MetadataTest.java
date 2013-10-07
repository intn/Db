package com.java2novice.jdbc;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;


public class MetadataTest {
    
    public static void main(String args[]) {
        try {
            Connection conn = getMySQLConnection();
            DatabaseMetaData md = conn.getMetaData();
           
            ResultSet rsCatalogs = md.getCatalogs();
            System.out.println("====================================================================");
            System.out.println("Databases: ");
            while(rsCatalogs.next()) {
                System.out.println(" * " + rsCatalogs.getString("TABLE_CAT"));
            }


            // getTables(catalog, schemaPattern, tableNamePattern, types)
            ResultSet rsTables = md.getTables("metadata", null, null, null);
            System.out.println("====================================================================");
            System.out.println("Tables: ");
            System.out.print("\n");
            while(rsTables.next()) {
                String tableName = rsTables.getString("TABLE_NAME");
                System.out.print(" ** " + tableName);
                System.out.print(" || Type: " + rsTables.getString("TABLE_TYPE"));
                System.out.print(" || Catalog: " + rsTables.getString("TABLE_CAT"));
                System.out.println(" || Scheme: " + rsTables.getString("TABLE_SCHEM"));
                //getTables(catalog, schemaPattern, tableNamePattern, columnNamePattern)
                ResultSet rsColumns = md.getColumns("metadata",null,tableName,null);
                while(rsColumns.next()) {
                    System.out.print(" ====> " + rsColumns.getString("COLUMN_NAME"));
                    System.out.print(", " + rsColumns.getString("TYPE_NAME"));
                    System.out.print(" (" + rsColumns.getString("COLUMN_SIZE"));
                    System.out.println("," + rsColumns.getInt("DECIMAL_DIGITS") + ")");
                }
                System.out.println("--------------------------------------------------------------------");

                String   catalog   = null;
                String   schema    = null;
               // String   tableName = "my_table";

                ResultSet result = md.getPrimaryKeys(
                    catalog, schema, tableName);

                while(result.next()){
                    String columnName = result.getString(4);
                    
                    System.out.println ("pm"+ columnName);
                }

            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Connection getMySQLConnection() throws Exception {
        Connection conn;
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection("jdbc:mysql://localhost/metadata", "root",
                "root");
        return conn;
    }
}

