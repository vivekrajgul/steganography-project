/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dataSource.connector;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
 



 public class triple_a
{  receiver res=new receiver();
      byte msg[];
    public triple_a()

    {
    }
     
    public void encode(File f, String key , String message,String algo,String sender,String receiver)
    {
        //String          file_name   = image_path(path,original,ext1);
        BufferedImage   image_orig  = getImage(f);
BufferedImage image = user_space(image_orig);
        image = add_text(image,key,0,key);
       // return(setImage(image,new File(image_path(path,stegan,"png")),"png"));
      System.out.println(res.getOffset());
        image = add_text(image,message,res.getOffset(),key);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
try{baos.flush();
    ImageIO.write(image, "png", baos );
    
    InputStream is = new ByteArrayInputStream(baos.toByteArray());
byte[] imageInByte = baos.toByteArray();
Connection con;PreparedStatement ps;
con=connector.getConnect();
 ps=con.prepareStatement("insert into table1 (receiver,image,msglen,algo,sender) values(?,?,?,?,?)");
ps.setString(1, receiver);
 ps.setBinaryStream(2,is,is.available());
ps.setInt(3, message.length());
ps.setString(4, algo);
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
    }
    public String decode(BufferedImage bf, String key, int length)
    { 
        byte[] decode;
        try
        {
            //user space is necessary for decrypting
            BufferedImage image  = user_space(bf);
            decode = decode_text(get_byte_data(image),key,key.length(),0);
            System.out.println(res.getOffset());
                  String s1=new String(decode);
System.out.println(s1);
           if(s1.equals(key))        
{        decode = decode_text(get_byte_data(image),key,length,res.getOffset());
  System.out.println(s1);

 return(new String(decode));    
    
    }
else return "your provided key is incorrect,try again";
    
  //          return(new String(decode));
       
        }
        catch(Exception e)

        
        {
           e.printStackTrace();
            return "error occured";
        }
    }
    
    private String image_path(String path, String name, String ext)
    {
        return path + "/" + name + "." + ext;
    }
    private BufferedImage getImage(File f)
    {
        BufferedImage   image   = null;
        //File        file    = new File(f);
        try
        {
            image = ImageIO.read(f);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,
                "Image could not be read!","Error",JOptionPane.ERROR_MESSAGE);
        }
        return image;
    }

    private boolean setImage(BufferedImage image, File file, String ext)

    {
        try
        {

            file.delete(); //delete resources used by the File

            ImageIO.write(image,ext,file);

            return true;

        }

        catch(Exception e)

        {

            JOptionPane.showMessageDialog(null,

                "File could not be saved!","Error",JOptionPane.ERROR_MESSAGE);

            return false;
        }

    }

    private BufferedImage add_text(BufferedImage image, String text,int offset,String key)

    {

        byte img[]  = get_byte_data(image);

         msg = text.getBytes();

      //  byte len[]   = bit_conversion(msg.length);

        try

        {
//encode_text(img, len, 0);
            encode_text(img, msg, offset,key); //4 bytes of space for length: 4bytes*8bit = 32 bits

        }
        catch(Exception e)
        {e.printStackTrace();
            }
        return image;
    }


    private BufferedImage user_space(BufferedImage image)

    {

        BufferedImage new_img  = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        Graphics2D  graphics = new_img.createGraphics();

        graphics.drawRenderedImage(image, null);

        graphics.dispose(); //release all allocated memory for this image

        return new_img;

    }

     

    private byte[] get_byte_data(BufferedImage image)

    {

        WritableRaster raster   = image.getRaster();

        DataBufferByte buffer = (DataBufferByte)raster.getDataBuffer();

        return buffer.getData();

    }

    private byte[] bit_conversion(int i)

    {
        byte byte3 = (byte)((i & 0xFF000000) >>> 24); //0
        byte byte2 = (byte)((i & 0x00FF0000) >>> 16); //0

        byte byte1 = (byte)((i & 0x0000FF00) >>> 8 ); //0

        byte byte0 = (byte)((i & 0x000000FF)       );

        return(new byte[]{byte3,byte2,byte1,byte0});

    }
 public  String random(String s)
    {
        int w=0;
    for(int i=0;i<s.length();i++)
    {
    int j=Integer.valueOf(s.substring(i,i+1));
    w=w+(j)*39*s.length();
        }
    String s1=Integer.toString(w);
    return s1;
    }
public  String randomz(String s)
    {
        int w=0;
    for(int i=0;i<s.length();i++)
    {
    int j=Integer.parseInt(s.substring(i, i+1));    
    w=w+(j)*39*s.length();
    }
    String s1=Integer.toString(w);
    return s1;
    }
    private byte[] encode_text(byte[] image, byte[] addition, int offset,String s)
    {       if(addition.length + offset > image.length)

        {

            throw new IllegalArgumentException("File not long enough!");
        }
   
    int indicator=offset,next=0,k=0, z=0,next1=0;
        for(int i=0; i<addition.length; ++i)
        {next1=next;
       // k=0;z=0;
        next=0;
            int add = addition[i];
      
  for(int count=next1,bit=8-next1;count<8;)
  {   
   String w=random(s);
 // System.out.println(w);    
   s=w;
     //  for(int j=0;j<w.length();j++)
//{
 k=Integer.parseInt(w);    
k=k%7;
 if( k==0 )
{
    k=6;
}

//}     
     //  System.out.println(k);
String zw=randomz(w);
//System.out.println(zw);    
      
//for(int j=0;j<zw.length();j++)
//{
 z=Integer.parseInt(zw);    
z=z%4;
 if( z==0)
{
    z=2;
}

     
    //  System.out.println(z);
      indicator=indicator+3;
      if(k==1 && z==1)  
  {//1 bit encryption
      offset=indicator;
      bit--;
      int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
   count++;
   
   
  }
  else if(k==1 && z==2)
  {   offset=indicator;
  if(count<=6)
  {//2 bit encrypt
      bit=bit-2;
       int b = (add >>> bit) & 3;
                image[offset] = (byte)((image[offset] & 0xFC) | b );
  count=count+2;
  }
  else if(count==7)  
  {
      //1 bit enc
      bit--;
      count++;
      int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
                if((i+1)<addition.length)
                {
  add=addition[i+1];
  //1 bit enc
  next++;
      int c = (add >>> 7) & 1;
c=c<<1;
                image[offset] = (byte)((image[offset] & 0xFD) | c );
                }
  }
  }
  else if(k==1 && z==3)
  {offset=indicator;

if(count<=5)  
{    //3 bit enc
    bit=bit-3;
    int b = (add >>> bit) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | b );
count=count+3;    
}
else if(count==6)
{
//2 bit enc
    bit=bit-2;
    int b = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
     count=count+2;
     if((i+1)<addition.length)
                {
add=addition[i+1];
//1 bit enc
next++;
   int c = (add >>> 7) & 1;
c=c<<2;
                image[offset] = (byte)((image[offset] & 0xFB) | c );
                }
}
else if(count==7)
{
//1 bit enc
    bit--;
       int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
  count++;
  if((i+1)<addition.length)
                {
add=addition[i+1];
//2 bit enc
   int c = (add >>> 6) & 3;
c=c<<1;
                image[offset] = (byte)((image[offset] & 0xF9) | c );
  
next=next+2;
}}
  }
      if(k==2 && z==1)  
  {//1 bit encryption
  offset=indicator+1;
      bit--;
   int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
  
      count++;
 
  }
  else if(k==2 && z==2)
  {   offset=indicator+1;

  if(count<=6)
  {//2 bit encrypt
      bit=bit-2;
         int b = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
  
  count=count+2;
  }
  else if(count==7)  
  {bit--;
      //1 bit enc
   int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
  count++;
  if((i+1)<addition.length)
                {
  add=addition[i+1];
  //1 bit enc
     int c = (add >>> 7) & 1;
c=c<<1;
                image[offset] = (byte)((image[offset] & 0xFD) | c );
  
  next++;
  }}
  }
  else if(k==2 && z==3)
  {offset=indicator+1;

if(count<=5)  
{    //3 bit enc
    bit=bit-3;
       int b = (add >>> bit) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | b );
  
count=count+3;    
}
else if(count==6)
{
//2 bit enc
    bit=bit-2;
       int b = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
 count=count+2;
 if((i+1)<addition.length)
                {
add=addition[i+1];
//1 bit enc
   int c = (add >>> 7) & 1;
c=c<<2;
                image[offset] = (byte)((image[offset] & 0xFB) | c );
  
next++;
}}
else if(count==7)
{
//1 bit enc
    bit--;
       int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
  count++;
  if((i+1)<addition.length)
                {
add=addition[i+1];
//2 bit enc
   int c = (add >>> 6) & 3;
c=c<<1;
                image[offset] = (byte)((image[offset] & 0xF9) | c );
  
next=next+2;
}}
  }
      if(k==3 && z==1)  
  {//1 bit encryption
   count++;
   offset=indicator+2;
      bit--;
         int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
  }
  else if(k==3 && z==2)
  {   offset=indicator+2;

  if(count<=6)
  {//2 bit encrypt
      bit=bit-2;
          
         int b = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
  
  
  count=count+2;
  }
  else if(count==7)  
  {
      //1 bit enc
  bit--;
         int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
  
  count++;
  if((i+1)<addition.length)
                {
      add=addition[i+1];
  //1 bit enc
         int c = (add >>> 7) & 1;
c=c<<1;
                image[offset] = (byte)((image[offset] & 0xFD) | c );
  
  next++;
  }}
  }
  else if(k==3 && z==3)
  {offset=indicator+2;

if(count<=5)  
{    //3 bit enc
    bit=bit-3;
             int b = (add >>> bit) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | b );

count=count+3;    
}
else if(count==6)
{
//2 bit enc
    bit=bit-2;
             int b = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
count=count+2;
if((i+1)<addition.length)
                {
add=addition[i+1];
//1 bit enc
         int c = (add >>> 7) & 1;
c=c<<2;
                image[offset] = (byte)((image[offset] & 0xFB) | c );

next++;
                }}
else if(count==7)
{
    bit--;
       int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
  count++;
  if((i+1)<addition.length)
                {
add=addition[i+1];
//2 bit enc
   int c = (add >>> 6) & 3;
c=c<<1;
                image[offset] = (byte)((image[offset] & 0xF9) | c );

    next=next+2;
}}
  }
  
  else if(k==4 && z==1)
  {  offset=indicator;

  if(count<=6)
  {  
      for(int q=0;q<=1;q++)
  {
  //1bit enc
      bit--;
       int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
  
  offset++;
  count++;
  }}
  else if(count==7)
  {//1 bit enc
  count++;
 bit--;
  int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
  
  if((i+1)<addition.length)
                {
  add=addition[i+1];
  offset++;
  //1bit enc
   int c = (add >>> 7) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | c );
  
  next++;
                } }
  
  }
  else if(k==4 && z==2)
  {offset=indicator;
      if(count<=4)
  {
  for(int q=0;q<=1;q++)
  {//2 bit enc
 bit=bit-2;
  int b = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
  
      offset++;
  count=count+2;
  }}
  else if(count==5)
  {
  //2bit enc;
  bit=bit-2;
  int b = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
  
      count=count+3;
  offset++;
  //1 bit enc
  bit--;
  int c = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | c );
  if((i+1)<addition.length)
                {
  add=addition[i+1];
  //1 bit enc
  int d = (add >>> 7) & 1;
d=d<<1;
                image[offset] = (byte)((image[offset] & 0xFD) | d );
  
  next++;
  }}
  else if(count==6)
  {
  //2 bit enc
      bit=bit-2;
      int b = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
  
  count=count+2;
  
if((i+1)<addition.length)
                {offset++;
  add=addition[i+1];
  //2 bit enc
int c = (add >>> 6) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | c );
  
  next=next+2;
                }
  }
  else if(count==7)
  {
  //1 bit enc
      bit--;
      int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
  
  count++;
  if((i+1)<addition.length)
                {
  add=addition[i+1];
  //1 bit enc
  int c = (add >>> 7) & 1;
c=c<<1;
                image[offset] = (byte)((image[offset] & 0xFD) | c );
  
  offset++;
  //2 bit enc
  int d = (add >>> 5) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | d );
  
  next=next+3;
  }
  }  
  }
  else if(k==4 && z==3)
  {offset=indicator;
 if(count<=2) 
 {for(int q=0;q<=1;q++)
 {
  //3 bit enc
     bit=bit-3;
     int b = (add >>> bit) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | b );
  
  offset++;
  count=count+3;
 }}
 else if(count==3)
 {//3 bit enc
     bit=bit-3;
     int b = (add >>> bit) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | b );
  
  offset++;
  count=count+5;
  //2 bit enc
  bit=bit-2;
  int c = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | c );
  if((i+1)<addition.length)
                {
  add=addition[i+1];
  //1 bit enc
  int d = (add >>> 7) & 1;
  d=d<<2;
                image[offset] = (byte)((image[offset] & 0xFB) | d );
  
  next++;
 }}
 else if(count==4)
 {
  //3 bit enc
     bit=bit-3;
  int c = (add >>> bit) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | c );

  count=count+4;
  offset++;
  //1 bit enc
  bit=bit-1;
    int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
if((i+1)<addition.length)
                {
  add=addition[i+1];
  //2 it enc
    int d = (add >>> 6) & 3;
d=d<<1;
                image[offset] = (byte)((image[offset] & 0xF9) | d );

  next=next+2;
 }}
 else if(count==5)
 {//3 bit enc
     bit=bit-3;
       int c = (add >>> bit) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | c );

  
  count=count+3;
  if((i+1)<addition.length)
                {offset++;
  add=addition[i+1];
  //3 bit enc
    int b= (add >>> 5) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | b );

  next=next+3;  
                }}
 else if(count==6)
 {
 //2 bit enc
     bit=bit-2;
       int c = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | c );

 count=count+2;
 if((i+1)<addition.length)
                {
 add=addition[i+1];
 //1 bit enc
   int b = (add >>> 7) & 1;
b=b<<2;
                image[offset] = (byte)((image[offset] & 0xFB) | b );

 offset++;
 //3 bit enc
   int d = (add >>> 4) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | d );

 next=next+4;
 }}
 else if(count==7) 
 {
 //1 bit enc  
     bit--;
     int c = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | c );

 count++;
 if((i+1)<addition.length)
                {
 add=addition[i+1];
 //2 bit nec
   int b = (add >>> 6) & 3;
b=b<<1;
                image[offset] = (byte)((image[offset] & 0xF9) | b );

 offset++;
 //3 bit nec
   int d = (add >>> 3) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | d );

 next=next+5;
 }}}
  else if(k==5 && z==1)
  {  offset=indicator+1;

  if(count<=6)
  {  
      for(int q=0;q<=1;q++)
  {
  //1bit enc
      bit--;
       int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
  
  offset++;
  count++;
  }}
  else if(count==7)
  {
  count++;
 bit--;
  int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
  if((i+1)<addition.length)
                {
  //1 bit enc
  add=addition[i+1];
  offset++;
  //1bit enc
   int c = (add >>> 7) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | c );
  
  next++;
   }
  }
  }
  else if(k==5 && z==2)
  {offset=indicator+1;
//  System.out.println(bit); 
  if(count<=4)
  {
  for(int q=0;q<=1;q++)
  {//2 bit enc
 bit=bit-2;
  int b = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
  
      offset++;
  count=count+2;
  }}
  else if(count==5)
  {
  //2bit enc;
  bit=bit-2;
  int b = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
  
      count=count+3;
  offset++;
  //1 bit enc
  bit--;
  int c = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | c );
  if((i+1)<addition.length)
                {
  add=addition[i+1];
  //1 bit enc
  int d = (add >>> 7) & 1;
d=d<<1;
                image[offset] = (byte)((image[offset] & 0xFD) | d );
  
  next++;
  }}
  else if(count==6)
  {
  //2 bit enc
      bit=bit-2;
      int b = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
  
  count=count+2;
  
  if((i+1)<addition.length)
                {offset++;
  add=addition[i+1];
  //2 bit enc
int c = (add >>> 6) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | c );
  
  next=next+2;
                }
  }
  else if(count==7)
  {
  //1 bit enc
      bit--;
      int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
  
  count++;
  if((i+1)<addition.length)
                {
  add=addition[i+1];
  //1 bit enc
  int c = (add >>> 7) & 1;
c=c<<1;
                image[offset] = (byte)((image[offset] & 0xFD) | c );
  
  offset++;
  //2 bit enc
  int d = (add >>> 5) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | d );
  
  next=next+3;
                }}
  }  
  
  else if(k==5 && z==3)
  {offset=indicator+1;
 if(count<=2) 
 {for(int q=0;q<=1;q++)
 {
  //3 bit enc
     bit=bit-3;
     int b = (add >>> bit) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | b );
  
  offset++;
  count=count+3;
 }}
 else if(count==3)
 {//3 bit enc
     bit=bit-3;
     int b = (add >>> bit) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | b );
  
  offset++;
  count=count+5;
  //2 bit enc
  bit=bit-2;
  int c = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | c );
  if((i+1)<addition.length)
                {
  add=addition[i+1];
  //1 bit enc
  int d = (add >>> 7) & 1;
  d=d<<2;
                image[offset] = (byte)((image[offset] & 0xFB) | d );
  
  next++;
 }}
 else if(count==4)
 {
  //3 bit enc
     bit=bit-3;
  int c = (add >>> bit) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | c );

  count=count+4;
  offset++;
  //1 bit enc
  bit=bit-1;
    int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
if((i+1)<addition.length)
                {
  add=addition[i+1];
  //2 it enc
    int d = (add >>> 6) & 3;
d=d<<1;
                image[offset] = (byte)((image[offset] & 0xF9) | d );

  next=next+2;
 }}
 else if(count==5)
 {//3 bit enc
     bit=bit-3;
       int c = (add >>> bit) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | c );

  
  count=count+3;
  if((i+1)<addition.length)
                {offset++;
  add=addition[i+1];
  //3 bit enc
    int b= (add >>> 5) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | b );

  next=next+3;  
  }}
 else if(count==6)
 {
 //2 bit enc
     bit=bit-2;
       int c = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | c );

 count=count+2;
 if((i+1)<addition.length)
                {
 add=addition[i+1];
 //1 bit enc
   int b = (add >>> 7) & 1;
b=b<<2;
                image[offset] = (byte)((image[offset] & 0xFB) | b );

 offset++;
 //3 bit enc
   int d = (add >>> 4) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | d );

 next=next+4;
                }}
 else if(count==7) 
 {
 //1 bit enc  
     bit--;
     int c = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | c );

 count++;
 if((i+1)<addition.length)
                {
 add=addition[i+1];
 //2 bit nec
   int b = (add >>> 6) & 3;
b=b<<1;
                image[offset] = (byte)((image[offset] & 0xF9) | b );

 offset++;
 //3 bit nec
   int d = (add >>> 3) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | d );

 next=next+5;
 }}}
  else if(k==6 && z==1)
  {  offset=indicator-1;

  if(count<=6)
  {  
      for(int q=0;q<=1;q++)
  {
  //1bit enc
      bit--;
       int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
  
  offset=offset+2;
  count++;
  }}
  else if(count==7)
  {
  count++;
 bit--;
  int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
  if((i+1)<addition.length)
                {
  //1 bit enc
  add=addition[i+1];
  offset=offset+2;
  //1bit enc
   int c = (add >>> 7) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | c );
  
  next++;
   }
  }
  }
  else if(k==6 && z==2)
  {offset=indicator-1;
      if(count<=4)
  {
  for(int q=0;q<=1;q++)
  {//2 bit enc
 bit=bit-2;
  int b = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
  
       offset=offset+2;
count=count+2;
  }}
  else if(count==5)
  {
  //2bit enc;
  bit=bit-2;
  int b = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
  
      count=count+3;
  offset=offset+2;
  //1 bit enc
  bit--;
  int c = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | c );
  if((i+1)<addition.length)
                {
  add=addition[i+1];
  //1 bit enc
  int d = (add >>> 7) & 1;
d=d<<1;
                image[offset] = (byte)((image[offset] & 0xFD) | d );
  
  next++;
  }}
  else if(count==6)
  {
  //2 bit enc
      bit=bit-2;
      int b = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
  
  count=count+2;
  offset=offset+2;
  if((i+1)<addition.length)
                {
  add=addition[i+1];
  //2 bit enc
int c = (add >>> 6) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | c );
  
  next=next+2;
                }
  }
  else if(count==7)
  {
  //1 bit enc
      bit--;
      int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
  
  count++;
  if((i+1)<addition.length)
                {
  add=addition[i+1];
  //1 bit enc
  int c = (add >>> 7) & 1;
c=c<<1;
                image[offset] = (byte)((image[offset] & 0xFD) | c );
  
  offset=offset+2;
  //2 bit enc
  int d = (add >>> 5) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | d );
  
  next=next+3;
  }
  }  
  }
  else if(k==6 && z==3)
  {offset=indicator-1;
 if(count<=2) 
 {for(int q=0;q<=1;q++)
 {
  //3 bit enc
     bit=bit-3;
     int b = (add >>> bit) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | b );
  
    offset=offset+2;
count=count+3;
 }}
 else if(count==3)
 {//3 bit enc
     bit=bit-3;
     int b = (add >>> bit) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | b );
  
    offset=offset+2;
count=count+5;
  //2 bit enc
  bit=bit-2;
  int c = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | c );
  if((i+1)<addition.length)
                {
  add=addition[i+1];
  //1 bit enc
  int d = (add >>> 7) & 1;
  d=d<<2;
                image[offset] = (byte)((image[offset] & 0xFB) | d );
  
  next++;
 }}
 else if(count==4)
 {
  //3 bit enc
     bit=bit-3;
  int c = (add >>> bit) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | c );

  count=count+4;
    offset=offset+2;
//1 bit enc
  bit=bit-1;
    int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
if((i+1)<addition.length)
                {
  add=addition[i+1];
  //2 it enc
    int d = (add >>> 6) & 3;
d=d<<1;
                image[offset] = (byte)((image[offset] & 0xF9) | d );

  next=next+2;
 }}
 else if(count==5)
 {//3 bit enc
     bit=bit-3;
       int c = (add >>> bit) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | c );

    offset=offset+2;
count=count+3;
if((i+1)<addition.length)
                {  
add=addition[i+1];
  //3 bit enc
    int b= (add >>> 5) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | b );

  next=next+3;  
                }  }
 else if(count==6)
 {
 //2 bit enc
     bit=bit-2;
       int c = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | c );

 count=count+2;
 if((i+1)<addition.length)
                {
 add=addition[i+1];
 //1 bit enc
   int b = (add >>> 7) & 1;
b=b<<2;
                image[offset] = (byte)((image[offset] & 0xFB) | b );

   offset=offset+2;
//3 bit enc
   int d = (add >>> 4) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | d );

 next=next+4;
 }}
 else if(count==7) 
 {
 //1 bit enc  
     bit--;
     int c = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | c );

 count++;
 if((i+1)<addition.length)
                {
 add=addition[i+1];
 //2 bit nec
   int b = (add >>> 6) & 3;
b=b<<1;
                image[offset] = (byte)((image[offset] & 0xF9) | b );

   offset=offset+2;
//3 bit nec
   int d = (add >>> 3) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | d );

 next=next+5;
 }}
  }
          
  else if(k==7 && z==1)
  {offset=indicator;  
  if(count<=5)
  {
  for(int q=0;q<=2;q++)
  {
  bit--;
   int c = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | c );
  offset++;
  count++;
  }
  }
  else if(count==6)
  {for(int q=0;q<=1;q++)
  {
  bit--;
  int c = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | c );
  offset++;
  count++;
  }
 if((i+1)<addition.length)
                {
 add=addition[i+1];
 //1 bit nec
   int b = (add >>> 7) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
 next++;
                }
  }
  else if(count==7)
  {bit--;
  int c = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | c );
  offset++;
  count++;
  
      if((i+1)<addition.length)
                {
 add=addition[i+1];
 //1 bit nec
   int b = (add >>> 7) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
                offset++;
 
 //1 bit nec
   int d = (add >>> 6) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | d );
  next=next+2;
                }
  }
  
  }
  else if(k==7 && z==2)
  {offset=indicator;
  if(count<=2)
  {
      for(int q=0;q<=2;q++)
      {
          bit=bit-2;
  int c = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | c );
  offset++;
  count=count+2;
      }     
  }
if(count==3)
{
    for(int q=0;q<=1;q++)
      {
          bit=bit-2;
  int c = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | c );
  offset++;
  count=count+2;
  }
  bit--;
  int j = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | j );
    count++;
                if((i+1)<addition.length)
                {
 add=addition[i+1];
 //1 bit nec
   int b = (add >>> 7) & 1;
b=b<<1;
                image[offset] = (byte)((image[offset] & 0xFD) | b );
                next++; 
                }
    
    
  }
  if(count==4)
  {for(int q=0;q<=1;q++)
      {
          bit=bit-2;
  int c = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | c );
  offset++;
  count=count+2;
  }
    if((i+1)<addition.length)
                {
 add=addition[i+1];
 //2 bit nec
   int b = (add >>> 6) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
                next=next+2; 
                }
     
  }
  if(count==5)
  {
  bit=bit-2;
  int c = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | c );
  offset++;
  count=count+2;
  bit--;
  int b = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | b );
  
  count++;
   if((i+1)<addition.length)
                {
 add=addition[i+1];
 //1 bit nec
   int d = (add >>> 7) & 1;
d=d<<1;
                image[offset] = (byte)((image[offset] & 0xFD) | d );
                next=next+1; 
  offset++;              
  int e = (add >>> 5) & 3;
                image[offset] = (byte)((image[offset] & 0xFC) | e );
                next=next+2; 
                
                }}
   if(count==6)
   {
  bit=bit-2;
  int c = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | c );
  offset++;
  count=count+2;
   if((i+1)<addition.length)
                {
 add=addition[i+1];
 //1 bit nec
   int d = (add >>> 6) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | d );
                next=next+2; 
   offset++;
   int b = (add >>> 4) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
                next=next+2; 
   
   }
   }
   else if(count==7)
   {
   bit--;
  int c = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | c );
   
   count++;
   
   if((i+1)<addition.length)
                {
 add=addition[i+1];
 //1 bit nec
   int d = (add >>> 7) & 1;
d=d<<1;
                image[offset] = (byte)((image[offset] & 0xFD) | d );
           
   offset++;
   int b = (add >>> 5) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
           
   offset++;
   int e = (add >>> 3) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | e );
            next=next+5; 
   
                }
   
   }}
  else if(k==7 && z==3)
  {offset=indicator;
  if(count==0)
  {
  for(int q=0;q<=1;q++)
      {
          bit=bit-3;
  int c = (add >>> bit) & 7;
                image[offset] = (byte)((image[offset] & 0xF8) | c );
  offset++;
  count=count+3;
  }
  bit=bit-2;
  int c = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | c );
   count=count+2;
                if((i+1)<addition.length)
                {
 add=addition[i+1];
 //1 bit nec
   int d = (add >>> 7) & 1;
d=d<<2;
     image[offset] = (byte)((image[offset] & 0xFB) | d );
     next++; 
  
                }  
  
  }
  else if(count==1)
  {
  for(int q=0;q<=1;q++)
      {
          bit=bit-3;
  int c = (add >>> bit) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | c );
  offset++;
  count=count+3;
  }
  bit--;
  int c = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | c );
  count++;
                if((i+1)<addition.length)
                {
 add=addition[i+1];
 //2 bit nec
   int d = (add >>> 6) & 3;
d=d<<1;
                image[offset] = (byte)((image[offset] & 0xF9) | d );
                next=next+2; 
  
  }}
  else if(count==2)
  {
  for(int q=0;q<=1;q++)
      {
          bit=bit-3;
  int c = (add >>> bit) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | c );
  offset++;
  count=count+3;
  }
   if((i+1)<addition.length)
                {
 add=addition[i+1];
 //3 bit nec
   int d = (add >>> 5) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | d );
                next=next+3; 
  
  }   
  }    
  else if(count==3)   
  {
          bit=bit-3;
  int c = (add >>> bit) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | c );
  offset++;
  
  bit=bit-2;
  int b = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
  count=count+5;
                if((i+1)<addition.length)
                {
 add=addition[i+1];
 //1 bit nec
   int d = (add >>> 7) & 1;
d=d<<2;
                image[offset] = (byte)((image[offset] & 0xF9) | d );
                next++; 
  offset++;
   int e = (add >>> 4) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | e );
  
  }
  }
  else if(count==4)
  {
         bit=bit-3;
  int c = (add >>> bit) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | c );
  offset++;
  
  bit--;
 int d = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | d );
  count=count+4;
                if((i+1)<addition.length)
  {
 add=addition[i+1];
 //2 bit nec
   int b = (add >>> 6) & 3;
b=b<<1;
                image[offset] = (byte)((image[offset] & 0xF9) | b );
                next=next+2; 
offset++;  
  int e = (add >>> 3) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | e );
                next=next+3; 
  
  }
  }  
  else if(count==5)   
  {
        bit=bit-3;
  int c = (add >>> bit) & 7;

                image[offset] = (byte)((image[offset] & 0xF8) | c );
  offset++;
  count=count+3;
  if((i+1)<addition.length)
                {
 add=addition[i+1];
 //2 bit nec
   int b = (add >>> 5) & 7;
                image[offset] = (byte)((image[offset] & 0xF8) | b );
                next=next+3; 
offset++;  
int e = (add >>> 2) & 7;
                image[offset] = (byte)((image[offset] & 0xF8) | e );
                next=next+3; 
                                
  }}
  else if(count==6)
  {
        bit=bit-2;
  int c = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | c );
  count=count+2;
  if((i+1)<addition.length)
                {
 add=addition[i+1];
 //1 bit nec
   int b = (add >>> 7) & 1;
   b=b<<2;
                image[offset] = (byte)((image[offset] & 0xFB) | b );
offset++;  
int e = (add >>> 4) & 7;
                image[offset] = (byte)((image[offset] & 0xF8) | e );
offset++;  
int d = (add >>> 1) & 7;
                image[offset] = (byte)((image[offset] & 0xF8) | d );
                next=next+7; 
                }
  
  }
  else if(count==7)
  {
        bit--;
  int c = (add >>> bit) & 1;

                image[offset] = (byte)((image[offset] & 0xFE) | c );
  count++;
  if((i+1)<addition.length)
                {
 add=addition[i+1];
 //2 bit nec
   int b = (add >>> 6) & 3;
   b=b<<1;
                image[offset] = (byte)((image[offset] & 0xF9) | b );
offset++;  
int e = (add >>> 3) & 7;
                image[offset] = (byte)((image[offset] & 0xF8) | e );
offset++;  
int d = (add >>> 0) & 7;
                image[offset] = (byte)((image[offset] & 0xF8) | d );
                next=next+8; 
                }
  
  }
  }}
        }
 res.setOffset(indicator);
        return image;

    }

    private byte[] decode_text(byte[] image,String sz,int length,int offset)

    {
        

       /* for(int i=0; i<32; ++i) //i=24 will also work, as only the 4th byte contains real data

        {

            length = (length << 1) | (image[i] & 1);

        }*/

         int next=0,k=0,z=0,indicator=offset,next1;

        byte[] result = new byte[length];
        //loop through each byte of text
System.out.println("decode");
        for(int b=0; b<result.length;b++ )
        {
        next1=next;
        next=0;
         for(int count=next1,bit=8-next1;count<8;)
  {   
   String w=random(sz);
       sz=w;
      // System.out.println(w);    

//       for(int j=0;j<w.length();j++)
//{
 k=Integer.parseInt(w);    
k=k%7;
 if(k==0)
{
    k=6;
}  
     //  System.out.println(k);
String zw=randomz(w);
//System.out.println(zw);    

  //        for(int j=0;j<zw.length();j++)
//{
 z=Integer.parseInt(zw);    
z=z%4;
if(z==0)
    z=2;
     
      
   //   System.out.println(z);
      indicator=indicator+3;
      if(k==1 && z==1)  
  {//1 bit encryption
   offset=indicator;
      bit--;
     result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
   count++;
   
   
  }
  else if(k==1 && z==2)
  {   offset=indicator;
  if(count<=6)
  {//2 bit encrypt
       result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
      bit=bit-2;
     count=count+2;
  }
  else if(count==7)  
  {
      //1 bit enc
      bit--;
      count++;
     result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
  //add=addition[i+1];
  //1 bit enc
     if((b+1)<result.length)
     {b++;
         next++;
  
      result[b] = (byte)((result[b] << 1) | ((image[offset] & 2)>>>1));
  b--;
     }}
  }
  else if(k==1 && z==3)
  {offset=indicator;

if(count<=5)  
{    //3 bit enc
    bit=bit-3;
   result[b] = (byte)((result[b] << 3) | (image[offset] & 7));
count=count+3;    
}
else if(count==6)
{
//2 bit enc
    count=count+2;
    bit=bit-2;
   result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
//add=addition[i+1];
//1 bit enc
if((b+1)<result.length)
     {b++;
   next++;
  result[b] = (byte)((result[b] << 1) | ((image[offset] & 4)>>>2));
b--;}}
else if(count==7)
{
//1 bit enc
    bit--;count++;
       result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
//add=addition[i+1];
//2 bit enc
       if((b+1)<result.length)
     {b++;
result[b] = (byte)((result[b] << 2) | ((image[offset] & 6)>>>1));
       next=next+2;
b--;}}
  }
      if(k==2 && z==1)  
  {//1 bit encryption
 offset=indicator+1;
      bit--;
result[b] = (byte)((result[b] << 1) | (image[offset] & 1));    
count++;
  
  }
  else if(k==2 && z==2)
  {   offset=indicator+1;

  if(count<=6)
  {//2 bit encrypt
       result[b] = (byte)((result[b] << 2) | (image[offset] & 3));    
      bit=bit-2;
  count=count+2;
  }
  else if(count==7)  
  {bit--;
  //1 bit enc 
      count++;
     result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
  //add=addition[i+1];
  //1 bit enc
  if((b+1)<result.length)
     {b++;
     next++;
   result[b] = (byte)((result[b] << 1) | ((image[offset] & 2)>>>1));
  b--;   }}
  }
  else if(k==2 && z==3)
  {offset=indicator+1;

if(count<=5)  
{    //3 bit enc
   result[b] = (byte)((result[b] << 3) | (image[offset] & 7));
count=count+3;
}
else if(count==6)
{
//2 bit enc
    bit=bit-2;count=count+2;
   result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
//add=addition[i+1];
//1 bit enc
if((b+1)<result.length)
     {b++;
   next++;
  result[b] = (byte)((result[b] << 1) | ((image[offset] & 4)>>>2));
b--;}}
else if(count==7)
{
//1 bit enc
    bit--;count++;
       result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
//add=addition[i+1];
//2 bit enc
if((b+1)<result.length)
     {b++;
       result[b] = (byte)((result[b] << 2) | ((image[offset] & 6)>>>1));
       next=next+2;
b--;}}
  }

   if(k==3 && z==1)  
  {//1 bit encryption
 offset=indicator+2;
      bit--;
result[b] = (byte)((result[b] << 1) | (image[offset] & 1));    
count++;
  
  }
  else if(k==3 && z==2)
  {   offset=indicator+2;

  if(count<=6)
  {//2 bit encrypt
       result[b] = (byte)((result[b] << 2) | (image[offset] & 3));    
      bit=bit-2;
  count=count+2;
  }
  else if(count==7)  
  {bit--;
   
      count++;
     result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
  //add=addition[i+1];
  //1 bit enc
  if((b+1)<result.length)
     {++b;
     next++;
  
      result[b] = (byte)((result[b] << 1) | ((image[offset] & 2)>>>1));
 b--; }}
  }
  else if(k==3 && z==3)
  {offset=indicator+2;

if(count<=5)  
{    //3 bit enc
   result[b] = (byte)((result[b] << 3) | (image[offset] & 7));
count=count+3;    
}
else if(count==6)
{
//2 bit enc
    count=count+2;
   result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
//add=addition[i+1];
//1 bit enc
if((b+1)<result.length)
     {b++;
   next++;
  result[b] = (byte)((result[b] << 1) | ((image[offset] & 4)>>>2));
b--;}}
else if(count==7)
{
//1 bit enc
    count++;
       result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
//add=addition[i+1];
//2 bit enc
if((b+1)<result.length)
     {b++;
       result[b] = (byte)((result[b] << 2) | ((image[offset] & 6)>>>1));
       next=next+2;
b--;}}
  }
  
  else if(k==4 && z==1)
  {  offset=indicator;

  if(count<=6)
  {  
      for(int q=0;q<=1;q++)
  {
  //1bit enc
             result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
  offset++;
  count++;
  }}
  else if(count==7)
  {
  count++;
 bit--;
       result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
  
  //1 bit enc
  //add=addition[i+1];
  //1bit enc
  if((b+1)<result.length)
     {b++;offset++;
  result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
  next++;
 b--;    } }
  
  }
  else if(k==4 && z==2)
  {offset=indicator;
      if(count<=4)
  {
  for(int q=0;q<=1;q++)
  {//2 bit enc
 bit=bit-2;
         result[b] = (byte)((result[b] << 2) | (image[offset] & 3));

      offset++;
  count=count+2;
  }}
  else if(count==5)
  {
  //2bit enc;
  bit=bit-2;
       result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
      count=count+3;
  offset++;
  //1 bit enc
  bit--;
         result[b] = (byte)((result[b] << 1) | (image[offset] & 1));

//  add=addition[i+1];
  //1 bit enc
      if((b+1)<result.length)
     {b++;
         result[b] = (byte)((result[b] << 1) | ((image[offset] & 2)>>>1));
  next++;
 b--; }}
  else if(count==6)
  {
  //2 bit enc
      bit=bit-2;
           result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
  count=count+2;
  offset++;
 // add=addition[i+1];
  //2 bit enc
  if((b+1)<result.length)
     {  b++;
  result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
  next=next+2;
 b--;    }
  }
  else if(count==7)
  {
  //1 bit enc
      bit--;
             result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
count++;
//  add=addition[i+1];
  //1 bit enc
if((b+1)<result.length)
     { b++;     
result[b] = (byte)((result[b] << 1) | ((image[offset] & 2)>>>1));

  offset++;
  //2 bit enc
         result[b] = (byte)((result[b] << 2) | (image[offset] & 3));

  next=next+3;
 b--; }
  }  }  
  
  else if(k==4 && z==3)
  {offset=indicator;
 if(count<=2) 
 {for(int q=0;q<=1;q++)
 {
  //3 bit enc
     
           result[b] = (byte)((result[b] << 3) | (image[offset] & 7));

  offset++;
  count=count+3;
 }}
 else if(count==3)
 {//3 bit enc
    
            result[b] = (byte)((result[b] << 3) | (image[offset] & 7));

  offset++;
  count=count+5;
  //2 bit enc
 
         result[b] = (byte)((result[b] << 2) | (image[offset] & 3));

  //add=addition[i+1];
  //1 bit enc
   if((b+1)<result.length)
     {b++;
         result[b] = (byte)((result[b] << 1) | ((image[offset] & 4)>>>2));

  next++;
 b--;}}
 else if(count==4)
 {
  //3 bit enc
  
        result[b] = (byte)((result[b] << 3) | (image[offset] & 7));
     count=count+4;
  offset++;
  //1 bit enc
         result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
//add=addition[i+1];
  //2 it enc
   if((b+1)<result.length)
     {b++;
         result[b] = (byte)((result[b] << 2) | ((image[offset] & 6)>>>1));
next=next+2;
 b--;}}
 else if(count==5)
 {//3 bit enc
             result[b] = (byte)((result[b] << 3) | (image[offset] & 7));
 count=count+3;
  //3 bit enc
  if((b+1)<result.length)
     { b++;  offset++;
 
  result[b] = (byte)((result[b] << 3) | (image[offset] & 7));

  next=next+3;  
 b--; }}
 
 else if(count==6)
 {
 //2 bit enc
     bit=bit-2;
              result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
 count=count+2;
 //add=addition[i+1];
 //1 bit enc
 if((b+1)<result.length)
     {  b++; 
 result[b] = (byte)((result[b] << 1) | ((image[offset] & 4)>>>2));

 offset++;
 //3 bit enc
          result[b] = (byte)((result[b] << 3) | (image[offset] & 7));

 next=next+4;
     b--;}}
 else if(count==7) 
 {
 //1 bit enc  
     bit--;
          result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
 count++;
 //add=addition[i+1];
 //2 bit nec
 if((b+1)<result.length)
     {  b++;   
 result[b] = (byte)((result[b] << 2) | ((image[offset] & 6)>>>1));
 offset++;
 //3 bit nec
          result[b] = (byte)((result[b] << 3) | (image[offset] & 7));
 next=next+5;
   b--;  }}}
  else if(k==5 && z==1)
  {  offset=indicator+1;

  if(count<=6)
  {  
      for(int q=0;q<=1;q++)
  {
  //1bit enc
      bit--;
             result[b] = (byte)((result[b] << 1) | (image[offset] & 1));

  offset++;
  count++;
  }}
  else if(count==7)
  {
  count++;
 bit--;
       result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
  
  //1 bit enc
  //add=addition[i+1];
  offset++;
  //1bit enc
  if((b+1)<result.length)
     {b++;
       result[b] = (byte)((result[b] << 1) | (image[offset] & 1));

  next++;
  b--; }}
  
  }
  else if(k==5 && z==2)
  {offset=indicator+1;
      if(count<=4)
  {
  for(int q=0;q<=1;q++)
  {//2 bit enc
         result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
      offset++;
  count=count+2;
  }}
  else if(count==5)
  {
  //2bit enc;
  bit=bit-2;
       result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
      count=count+3;
  offset++;
  //1 bit enc

         result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
//  add=addition[i+1];
  //1 bit enc
     if((b+1)<result.length)
     {b++;
     result[b] = (byte)((result[b] << 1) | ((image[offset] & 2)>>>1));
  next++;
b--;
     }}
  else if(count==6)
  {
  //2 bit enc
      bit=bit-2;
           result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
  count=count+2;
  offset++;
 // add=addition[i+1];
  //2 bit enc
  if((b+1)<result.length)
     {  b++;
  result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
  next=next+2;
b--;     }
  }
  else if(count==7)
  {
  //1 bit enc
      bit--;
             result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
count++;
//  add=addition[i+1];
  //1 bit enc
if((b+1)<result.length)
     { b++;     
result[b] = (byte)((result[b] << 1) | ((image[offset] & 2)>>>1));

  offset++;
  //2 bit enc
         result[b] = (byte)((result[b] << 2) | (image[offset] & 3));

  next=next+3;
b--;  }
  }  
  }  
  else if(k==5 && z==3)
  {offset=indicator+1;
 if(count<=2) 
 {for(int q=0;q<=1;q++)
 {
  //3 bit enc
     bit=bit-3;
           result[b] = (byte)((result[b] << 3) | (image[offset] & 7));

  offset++;
  count=count+3;
 }}
 else if(count==3)
 {//3 bit enc
     bit=bit-3;
            result[b] = (byte)((result[b] << 3) | (image[offset] & 7));

  offset++;
  count=count+5;
  //2 bit enc
  bit=bit-2;
         result[b] = (byte)((result[b] << 2) | (image[offset] & 3));

  //add=addition[i+1];
  //1 bit enc
   if((b+1)<result.length)
     {b++;
         result[b] = (byte)((result[b] << 1) | ((image[offset] & 4)>>>2));

  next++;
b--; }}
 else if(count==4)
 {
  //3 bit enc
     bit=bit-3;
        result[b] = (byte)((result[b] << 3) | (image[offset] & 7));

     count=count+4;
  offset++;
  //1 bit enc
  bit=bit-1;
         result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
//add=addition[i+1];
  //2 it enc
   if((b+1)<result.length)
     {b++;
         result[b] = (byte)((result[b] << 2) | ((image[offset] & 6)>>>1));
next=next+2;
b--; }}
 else if(count==5)
 {//3 bit enc
             result[b] = (byte)((result[b] << 3) | (image[offset] & 7));
  offset++;
  count=count+3;
 // add=addition[i+1];
  //3 bit enc
  if((b+1)<result.length)
     { b++;
  result[b] = (byte)((result[b] << 3) | (image[offset] & 7));

  next=next+3;  
b--;     }}
 else if(count==6)
 {
 //2 bit enc
     bit=bit-2;
              result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
 count=count+2;
 //add=addition[i+1];
 //1 bit enc
 if((b+1)<result.length)
     {  b++;   
 result[b] = (byte)((result[b] << 1) | ((image[offset] & 4)>>>2));

 offset++;
 //3 bit enc
          result[b] = (byte)((result[b] << 3) | (image[offset] & 7));

 next=next+4;
b--;     }}
 else if(count==7) 
 {
 //1 bit enc  
     bit--;
          result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
 count++;
 //add=addition[i+1];
 //2 bit nec
 if((b+1)<result.length)
     {  b++;   
 result[b] = (byte)((result[b] << 2) | ((image[offset] & 6)>>>1));
 offset++;
 //3 bit nec
          result[b] = (byte)((result[b] << 3) | (image[offset] & 7));
 next=next+5;
b--;     }}}
  else if(k==6 && z==1)
  {  offset=indicator-1;

  if(count<=6)
  {  
      for(int q=0;q<=1;q++)
  {
  //1bit enc
             result[b] = (byte)((result[b] << 1) | (image[offset] & 1));

  offset=offset+2;
  count++;
  }}
  else if(count==7)
  {
  count++;
       result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
  //1 bit enc
  //add=addition[i+1];
   offset=offset+2;
if((b+1)<result.length)
     {b++;
  //1bit enc
      
         result[b] = (byte)((result[b] << 1) | (image[offset] & 1));

  next++;
b--;   }
  }
  }
  else if(k==6 && z==2)
  {offset=indicator-1;
      if(count<=4)
  {
  for(int q=0;q<=1;q++)
  {//2 bit enc
         result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
        offset=offset+2;
  count=count+2;
  }}
  else if(count==5)
  {
  //2bit enc;
       result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
      count=count+3;
  offset=offset+2;
  //1 bit enc
         result[b] = (byte)((result[b] << 1) | (image[offset] & 1));

//  add=addition[i+1];
  //1 bit enc
       if((b+1)<result.length)
     {b++;
         result[b] = (byte)((result[b] << 1) | ((image[offset] & 2)>>>1));
  next++;
b--;  }}
  else if(count==6)
  {
  //2 bit enc
           result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
  count=count+2;
  offset=offset+2;
 // add=addition[i+1];
  //2 bit enc
  if((b+1)<result.length)
     {  b++;
  result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
  next=next+2;
b--;     }
  }
  else if(count==7)
  {
  //1 bit enc
             result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
count++;
//  add=addition[i+1];
  //1 bit enc
if((b+1)<result.length)
     { b++;     
result[b] = (byte)((result[b] << 1) | ((image[offset] & 2)>>>1));

  offset=offset+2;
  //2 bit enc
         result[b] = (byte)((result[b] << 2) | (image[offset] & 3));

  next=next+3;
b--;  }
  }  }  
  
  else if(k==6 && z==3)
  {offset=indicator-1;
 if(count<=2) 
 {for(int q=0;q<=1;q++)
 {
  //3 bit enc
           result[b] = (byte)((result[b] << 3) | (image[offset] & 7));
   offset=offset+2;
  count=count+3;
 }}
 else if(count==3)
 {//3 bit enc
            result[b] = (byte)((result[b] << 3) | (image[offset] & 7));

   offset=offset+2;
 count=count+5;
  //2 bit enc
         result[b] = (byte)((result[b] << 2) | (image[offset] & 3));

  //add=addition[i+1];
  //1 bit enc
   if((b+1)<result.length)
     {b++;
         result[b] = (byte)((result[b] << 1) | ((image[offset] & 4)>>>2));

  next++;
b--; }}
 else if(count==4)
 {
  //3 bit enc
        result[b] = (byte)((result[b] << 3) | (image[offset] & 7));

     count=count+4;
   offset=offset+2;
 //1 bit enc
         result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
//add=addition[i+1];
  //2 it enc
   if((b+1)<result.length)
     {b++;
         result[b] = (byte)((result[b] << 2) | ((image[offset] & 6)>>>1));
next=next+2;
b--; }}
 else if(count==5)
 {//3 bit enc
             result[b] = (byte)((result[b] << 3) | (image[offset] & 7));
   offset=offset+2;
 count=count+3;
 // add=addition[i+1];
  //3 bit enc
 if((b+1)<result.length)
     {  b++;
 result[b] = (byte)((result[b] << 3) | (image[offset] & 7));

  next=next+3;  
b--;     } }
 else if(count==6)
 {
 //2 bit enc
              result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
 count=count+2;
 //add=addition[i+1];
 //1 bit enc
 if((b+1)<result.length)
     {  b++;   
 result[b] = (byte)((result[b] << 1) | ((image[offset] & 4)>>>2));

   offset=offset+2;
//3 bit enc
          result[b] = (byte)((result[b] << 3) | (image[offset] & 7));

 next=next+4;
b--;     }}
 else if(count==7) 
 {
 //1 bit enc  
          result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
 count++;
 //add=addition[i+1];
 //2 bit nec
  if((b+1)<result.length)
     {   b++;  
     
 result[b] = (byte)((result[b] << 2) | ((image[offset] & 6)>>>1));
   offset=offset+2;
//3 bit nec
          result[b] = (byte)((result[b] << 3) | (image[offset] & 7));
 next=next+5;
b--; }}
  }      
  else if(k==7 && z==1)
  {offset=indicator;  
  if(count<=5)
  {
  for(int q=0;q<=2;q++)
  {
    result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
  offset++;
  count++;
  }
  }
  else if(count==6)
  {for(int q=0;q<=1;q++)
  {
   result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
  offset++;
  count++;
  }
 if((b+1)<result.length)
     {  b++;   
 result[b] = (byte)((result[b] << 1) | (image[offset] & 1));

 next++;
b--;     }
  }
  else if(count==7)
  {
 result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
 
  count++;
 if((b+1)<result.length)
     {  b++; 
     for(int q=0;q<=1;q++)  
     {offset++;
         result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
     next++;}
b--;
     }
     }
  }
  else if(k==7 && z==2)
  {offset=indicator;
  if(count<=2)
  {
      for(int q=0;q<=2;q++)
      {
           result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
offset++;
  count=count+2;
      }     
  }
  else if(count==3)
{
    for(int q=0;q<=1;q++)
      {
          result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
 offset++;
  count=count+2;
  }
            result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
  count++;
  if((b+1)<result.length)
     {  b++; 
         result[b] = (byte)((result[b] << 1) | ((image[offset] & 2)>>>1));
     next++;
}
b--;    
  }
else if(count==4)
  {for(int q=0;q<=1;q++)
      {
             result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
 offset++;
  count=count+2;
  }
     if((b+1)<result.length)
     {  b++; 
         result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
     next=next+2;
b--; 
}
  }
 else if(count==5)
  {
               result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
offset++;
  count=count+2;
               result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
  count++;
    if((b+1)<result.length)
     {  b++; 
         result[b] = (byte)((result[b] << 1) | ((image[offset] & 2)>>>1));
     next=next+3;
     offset++;
         result[b] = (byte)((result[b] << 2) | (image[offset] & 3));

}
b--;             }
  else if(count==6)
   {
           result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
  offset++;
  count=count+2;
   if((b+1)<result.length)
     {  b++;
     for(int q=0;q<=1;q++)
     {
         result[b] = (byte)((result[b] << 2) | ((image[offset] & 3)));
     next=next+2;
     }
  b--;   } }
   else if(count==7)
   {
           result[b] = (byte)((result[b] << 1) | ((image[offset] & 1)));
   count++;
   
    if((b+1)<result.length)
     {  b++; 
         result[b] = (byte)((result[b] << 1) | ((image[offset] & 2)>>>1));
     next++;
     for(int q=0;q<=1;q++)
     {
         result[b] = (byte)((result[b] << 2) | ((image[offset] & 3)));
     next=next+2;
     }
   b--;
   }}}
  else if(k==7 && z==3)
  {offset=indicator;
  if(count==0)
  {
  for(int q=0;q<=1;q++)
      {
           result[b] = (byte)((result[b] << 3) | ((image[offset] & 7)));

  offset++; 
  count=count+3;
  }
  bit=bit-2;
          result[b] = (byte)((result[b] << 2) | ((image[offset] & 3)));
  count=count+2;
    if((b+1)<result.length)
     {  b++; 
         result[b] = (byte)((result[b] << 1) | ((image[offset] & 4)>>>2));
     next++;
     b--;           }  
  
  }
  else if(count==1)
  {
  for(int q=0;q<=1;q++)
      {
          bit=bit-3;
             result[b] = (byte)((result[b] << 3) | ((image[offset] & 7)));
offset++;
  count=count+3;
  }
             result[b] = (byte)((result[b] << 1) | ((image[offset] & 1)));
count++;
   if((b+1)<result.length)
     {  b++; 
         result[b] = (byte)((result[b] << 2) | ((image[offset] & 6)>>>1));
     next=next+2;
   
  b--;}}
  else if(count==2)
  {
  for(int q=0;q<=1;q++)
      {
               result[b] = (byte)((result[b] << 3) | ((image[offset] & 7)));
offset++;
  count=count+3;
  }
   if((b+1)<result.length)
     {  b++; 
         result[b] = (byte)((result[b] << 3) | (image[offset] & 7));
     next=next+3;
  b--;
  }   
  }    
  else if(count==3)   
  {
          result[b] = (byte)((result[b] << 3) | (image[offset] & 7));
          offset++;
         result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
  count=count+5;
   if((b+1)<result.length)
     {  b++; 
         result[b] = (byte)((result[b] << 1) | ((image[offset] & 4)>>2));
     next=next+3;
  
  offset++;
           result[b] = (byte)((result[b] << 3) | (image[offset] & 7));

  b--;}
  }
  else if(count==4)
  {
         bit=bit-3;
             result[b] = (byte)((result[b] << 3) | (image[offset] & 7));
offset++;
           result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
  count=count+4;
   if((b+1)<result.length)
     {  b++; 
         result[b] = (byte)((result[b] << 2) | ((image[offset] & 6)>>>1));
     next=next+5;
  offset++;
           result[b] = (byte)((result[b] << 3) | (image[offset] & 7));

  b--;}
  }  
  else if(count==5)   
  {
        bit=bit-3;
           result[b] = (byte)((result[b] << 3) | (image[offset] & 7));
  offset++;
  count=count+3; // System.out.println(result[b]);

   if((b+1)<result.length)
     {  b++; 
     for(int q=0;q<=1;q++)
     {
         result[b] = (byte)((result[b] << 3) | (image[offset] & 7));
  offset++;
     }next=next+6;                          
 b--; }}
  else if(count==6)
  {
        bit=bit-2;
         result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
  //offset++;
  count=count+2;
   if((b+1)<result.length)
     {  b++; 
         result[b] = (byte)((result[b] << 1) | ((image[offset] & 4)>>>2));

for(int q=0;q<=1;q++)
     {  offset++;                
         result[b] = (byte)((result[b] << 3) | (image[offset] & 7));
  
  }
   b--;  next=next+7;}}
  else if(count==7)
  {
        bit--;
         result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
  count++;
  if((b+1)<result.length)
     {  b++; 
         result[b] = (byte)((result[b] << 2) | ((image[offset] & 6)>>>1));
for(int q=0;q<=1;q++)
     {  offset++;                
         result[b] = (byte)((result[b] << 3) | (image[offset] & 7));
     }next=next+8;
  b--;}
  }
  }}}
        res.setOffset(indicator);
    
        return result;

    }
 }

/*class swkt
{
    public static void main(String args[])
    {
        triple_a s=new triple_a();
    s.encode("C:/Users/vrg/Downloads","stegnodemo","jpg","stegnodemo2", "god please helppp this is to say that i am very greatful taht u hrlped me throughout this task and was always beside me to give me the patience it took to complete this task");
   
    String w=s.decode("C:/Users/vrg/Downloads", "stegnodemo2");
    System.out.println(w);
            }
}*/