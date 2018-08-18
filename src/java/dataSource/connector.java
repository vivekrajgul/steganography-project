/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSource;


import java.sql.Connection;
import java.sql.DriverManager;

public class connector
{
static Connection con;

public static Connection getConnect()
{
 try
{
Class.forName("com.mysql.jdbc.Driver");
con=DriverManager.getConnection("jdbc:mysql://localhost:3306/stegano","root","root");

}
catch(Exception e)
{System.out.println(e);
}
return con;
}
}
