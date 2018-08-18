package model;


import dataSource.connector;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.imageio.ImageIO;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vrg
 */
public class HaarTestWithSampleValues {
 //int [][]k;
   // byte[][] img;
    //byte[] msg;
    public void encode(File f, String key , String message,String algo,String receiver,String sender)
    {
        Bridge agn=new Bridge();
    
 int[][] pixel =   agn.compute(f);
        //String          file_name   = path;
   int[][]k=  add(pixel,message,key);
BufferedImage im=    agn.getImage(k);

ByteArrayOutputStream baos = new ByteArrayOutputStream();
try{baos.flush();
    ImageIO.write(im, "png", baos );
    
    InputStream is = new ByteArrayInputStream(baos.toByteArray());
byte[] imageInByte = baos.toByteArray();
Connection con;PreparedStatement ps;
con=connector.getConnect();
 ps=con.prepareStatement("insert into table1 (receiver,image,msglen,algo,sender) values(?,?,?,?,?)");
ps.setString(1, receiver);
 ps.setBinaryStream(2,is,is.available());
ps.setInt(3, message.length());
ps.setString(4,algo);
ps.setString(5, sender);
int x=ps.executeUpdate();
if(x>0)
  System.out.println("done");
else
 System.out.println(" not done");


}    
 catch(Exception e)       
 {System.out.println(e);
 } 
   //return(setImage(image,new File("G:/Newfolder/decode.jpg"),"jpg")); }
    }  
        private int[][] add(int[][] img,String msg,String key)
        {   for (int i = 0; i < img.length; i++) {
            for (int j = 0; j < img[0].length; j++) {
              //  System.out.print(img[i][j] + " ");
            }
            //System.out.println();
            
        //System.out.println();
        }
        int[][] haar2DFWTransform = HaarWaveletTransform.doHaar2DFWTransform(img, 2);
        for (int i = 0; i < haar2DFWTransform.length; i++) {
            for (int j = 0; j < haar2DFWTransform[0].length; j++) {
          //      System.out.print(haar2DFWTransform[i][j] + " ");
            }    }
            //System.out.println();
        
       // System.out.println();
        
          char k[]=(key+" "+msg).toCharArray();

        for(int i=0,j=haar2DFWTransform[0].length-1,w=0;;)
        {try
        {if(i>haar2DFWTransform.length-1)
        {
        if(w!=k.length)
        {  
         i=0;
         j--;
            }
        else break;
        }
        if(w<k.length)
        {int m=haar2DFWTransform[i][j];
           System.out.println(m+"this is first");
        haar2DFWTransform[i][j]=((int)haar2DFWTransform[i][j] & 0xffffff00);
                      System.out.println(haar2DFWTransform[i][j]+"this is sec");
                      System.out.println(haar2DFWTransform[i][j]+"this is sec11");

             haar2DFWTransform[i][j]=((int)(haar2DFWTransform[i][j] & 0xffffff00) | ( k[w] & 0xff));
    System.out.println(haar2DFWTransform[i][j]+"this is third");
        
        w++;
        i++;
    /*       // int m=k[w]/17;
        //int m2=k[w]%17;
           haar2DFWTransform[i][j]=((int)haar2DFWTransform[i][j] & 0xffff00) | ((m & 0x00)| k[w]);
                    //haar2DFWTransform[i][j]=((haar2DFWTransform[i][j] & 0xffff00) | k[w]);
                //    haar2DFWTransform[i][j]=((haar2DFWTransform[i][j] & 0xffff00) | m);
        //i++;    
        
          //          haar2DFWTransform[i][j]=((haar2DFWTransform[i][j] & 0xffff00) | m2);

            w++;
        i++;
*/
        }
        else break;
        }
        
    
        catch(Exception e)
        {e.printStackTrace();
        }
        
        }    
        
        
        int[][] haar2DInvTransform = HaarWaveletTransform
                .doHaar2DInvTransform(haar2DFWTransform, 2); {
            for (int j = 0; j < haar2DInvTransform[0].length; j++) {
              //  System.out.print(haar2DInvTransform[i][j] + " ");
            }
            //System.out.println();
        }
   return    haar2DInvTransform;
        }   

        
         public String decode(BufferedImage bf,int mslen,String key)
        { 
        char ch[]=new char[mslen +key.length() +1];
        Bridge agn=new Bridge();

    int array2[][];
array2=agn.compute(bf); 
    int[][] haar2FWTransform = HaarWaveletTransform
                .doHaar2DFWTransform(array2, 2);
    
    
        for(int i=0,j=haar2FWTransform[0].length-1,w=0;;)
        {
        if(i>haar2FWTransform.length-1)
        {
        if(w!=mslen+key.length() +1)
        {  
         i=0;
         j--;
            }
        else break;
        }
        if(w<mslen + key.length()+1)
        {//int jk=(int)(haar2FWTransform[i][j] & 0x000000FF);
    //System.out.println(jk);

        ch[w]=(char)((int)(haar2FWTransform[i][j] & 0x000000FF)); 
System.out.println(ch[w]);
         w++;
        i++;


        /*   ch[w]=(char)((int)haar2FWTransform[i][j] & 0x000000FF); 
                   ch[w]=(char)(((int)haar2FWTransform[i][j] & 0x000000FF)*17 + (int)haar2FWTransform[++i][j]); 

            
            w++;
        i++;*/
        }
        else break;
        }
String ss=new String(ch);        
 String a=  ss.substring(0,7);
System.out.println(ss);
if(a.equals(key))
{
    return ss.substring(7);
}
else return "sorry the key is incorrect";

        }
}


    
    
    


