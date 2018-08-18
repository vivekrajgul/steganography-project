/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dataSource.connector;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author shiva
 */
public class Bridge {
Connection con;
PreparedStatement ps;
ResultSet rs;
       public List<receiver> Select(String receiver) throws Exception
    {
        
     con=connector.getConnect();
 ps=con.prepareStatement("select Sender,index_id from table1 where Receiver=?");
ps.setString(1,receiver);   
  rs=ps.executeQuery();
    List<receiver> lst=new ArrayList<receiver>();
    while(rs.next())
    { 
    receiver s=new receiver();
    s.setImageid(rs.getInt(2));
    s.setSender(rs.getString(1));
    //s.setImage(rs.getString(2));
    lst.add(s);
    }
   return lst;
    }
    public boolean uservalidate(String pass,String username,String email)throws Exception
{
    con=connector.getConnect();
     ps=con.prepareStatement("select * from users where password=? and user=? and email_id=?");
    ps.setString(1,pass);
     ps.setString(2,username);
    ps.setString(3,email);

    rs=ps.executeQuery();
    if(rs.next())
return true;
    else
        return false;
        }

public boolean register(String username,String password,String emailid)    
{int a=0;
    con=connector.getConnect();
    System.out.println("hello11"); 

    try{
    ps=con.prepareStatement("insert into users values(?,?,?)");
ps.setString(3, emailid);
ps.setString(2, password);
ps.setString(1, username);
   System.out.println("hello11");
 
a=ps.executeUpdate();
   System.out.println("hello11"); System.out.println(a);
  }
  catch(Exception e){
  System.out.println(e);}
if(a>0)
return true;
else return false;
}
    
    public boolean receiverpresent(String receiver)throws SQLException
    {
con=connector.getConnect();    System.out.println("hello11"); 

    ps=con.prepareStatement("select * from users where user=?");
    ps.setString(1,receiver);    System.out.println("hello11"); 

 rs=ps.executeQuery();    System.out.println("hello11"); 

    if(rs.next())     

return true;
    else
        return false;
    }
public static int[][] compute(File file)
{
try 
{
    BufferedImage img= ImageIO.read(file);
      BufferedImage new_img  = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_BGR);

        Graphics2D  graphics = new_img.createGraphics();

        graphics.drawRenderedImage(img, null);

        graphics.dispose(); //release all allocated memory for this image

        
    //Raster raster=img.getData();
    int w=img.getWidth(),h=img.getHeight();
    int pixels[][]=new int[w][h];
  //  sampleModel = raster.getSampleModel();
    
    for (int x=0;x<w;x++)
    {
        for(int y=0;y<h;y++)
        {
            pixels[x][y]=new_img.getRGB(x,y);
        }
    }

    return pixels;

}
catch (Exception e)
{
    e.printStackTrace();
}
return null;
}
public static int[][] compute(BufferedImage img)
{
try 
{
   // BufferedImage img= ImageIO.read(file);
      BufferedImage new_img  = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_BGR);

        Graphics2D  graphics = new_img.createGraphics();

        graphics.drawRenderedImage(img, null);

        graphics.dispose(); //release all allocated memory for this image

        
    //Raster raster=img.getData();
    int w=img.getWidth(),h=img.getHeight();
    int pixels[][]=new int[w][h];
  //  sampleModel = raster.getSampleModel();
    
    for (int x=0;x<w;x++)
    {
        for(int y=0;y<h;y++)
        {
            pixels[x][y]=new_img.getRGB(x,y);
        }
    }

    return pixels;

}
catch (Exception e)
{
    e.printStackTrace();
}
return null;
}

//Then I do some operations on the pixels array
//Next I convert the array back to the image.
public static BufferedImage getImage(int pixels[][])
{File f1=new File("E:/jpic/check.jpg");
     int w=pixels.length;
     int h=pixels[0].length;
     BufferedImage image=new BufferedImage(w,h,4);
    // WritableRaster raster=(WritableRaster)image.getData();
    // WritableRaster raster= Raster.createWritableRaster(sampleModel, new Point(0,0));
     for(int i=0;i<w;i++)
     {
         for(int j=0;j<h;j++)
         {
       image.setRGB(i, j, pixels[i][j]);
         }
     }
System.out.println("hello12");
//File output=new File("check.jpg");
try {
     //BufferedImage image1=new BufferedImage(w,h,4);
    //image1.setData(raster);
    System.out.println("hello1");
   // ImageIO.write(image,"jpg",f1);
}
catch (Exception e)
{
    e.printStackTrace();
}
return image;
}
}
