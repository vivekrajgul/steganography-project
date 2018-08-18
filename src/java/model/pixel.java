package model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package stegno;

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
 

 public class pixel
{    receiver s=new receiver();
public int getOffset1()
{
return s.getOffset();
}
      byte msg[];
    public pixel()

    {
    }
     
    public void encode(File f, String key , String message,String algo,String receiver,String sender)
    {
        //String          file_name   = path;
        BufferedImage   image_orig  = getImage(f);
BufferedImage image = user_space(image_orig);
        image = add_text(image,key,0);
        System.out.println(s.getOffset());
image = add_text(image,message,s.getOffset());
      //  return(setImage(image,new File(image_path(path,stegan,"png")),"png"));
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
ps.setString(4,algo);
ps.setString(5,sender);
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
    public String decode(BufferedImage bf, int length,int offset,String key,int mslen)
    {
        byte[] decode;
        try
        {
            //user space is necessary for decrypting
            BufferedImage image  = user_space(bf);
            
          decode = decode_text(get_byte_data(image),7,0);
           String s1=new String(decode);
System.out.println(s1);
           if(s1.equals(key))        
{        decode = decode_text(get_byte_data(image),mslen,s.getOffset());
  System.out.println(s1);

 return(new String(decode));    
    
    }
else return "your provided key is incorrect,try again";
    }
        catch(Exception e)
        {System.out.println(e);
                     return "error occured";
        }

        
    }

    private BufferedImage getImage(File f)
    {
        BufferedImage   image   = null;
        try
        {
            image = ImageIO.read(f);
        }
        catch(Exception ex)
        {
            //JOptionPane.showMessageDialog(null,
              //  "Image could not be read!","Error",JOptionPane.ERROR_MESSAGE);
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

    private BufferedImage add_text(BufferedImage image, String text,int a)

    {

        byte img[]  = get_byte_data(image);

         msg = text.getBytes();

      //  byte len[]   = bit_conversion(msg.length);

        try

        {
//encode_text(img, msg, 0);
    
            encode_text(img, msg, a); //4 bytes of space for length: 4bytes*8bit = 32 bits
              

        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,
"Target File cannot hold message!", "Error",JOptionPane.ERROR_MESSAGE);
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

  /*  private byte[] bit_conversion(int i)

    {
        byte byte3 = (byte)((i & 0xFF000000) >>> 24); //0
        byte byte2 = (byte)((i & 0x00FF0000) >>> 16); //0

        byte byte1 = (byte)((i & 0x0000FF00) >>> 8 ); //0

        byte byte0 = (byte)((i & 0x000000FF)       );

        return(new byte[]{byte3,byte2,byte1,byte0});

    }*/


    private byte[] encode_text(byte[] image, byte[] addition, int offset)
    {        if(addition.length + offset > image.length)

        {

            throw new IllegalArgumentException("File not long enough!");
        }
    int indicator=offset;

        for(int i=0; i<addition.length; ++i)
        { 
            int add = addition[i];
            int k=0;
for(int bit=6;bit>=0;)
{
    int w=image[indicator];
int c= w & 3;
if(k==0)
{
    if(c==0)
    {indicator=indicator + 4;
    k=k+1;
    }
    else if(c==1)    
    {
        offset=indicator+2;
    indicator=indicator + 4;
k=k+1;
    int b = (add >>> bit) & 3;
     
                image[offset] = (byte)((image[offset] & 0xFC) | b );
bit=bit-2;
    }
    else if(c==2)
    {
        offset=indicator+1;
        indicator=indicator + 4;
    k=k+1;
    int b = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
bit=bit-2;
    }
    else if(c==3)
    {
        int m=0;
    offset=indicator+1;
    indicator=indicator + 4;
    k=k+1;
    while(m<=1)
    {
    int b = (add >>> bit) & 3;
                image[offset] = (byte)((image[offset] & 0xFC) | b );
    offset++;
bit=bit-2;
m++;

if(bit>=0);
else break;
}
 }


}
else if(k==1)
{
    if(c==0)
    {indicator=indicator + 4;
    k=k+1;
    }
    else if(c==1)    
    {
        offset=indicator-1;
    indicator=indicator + 4;
k=k+1;
    int b = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
bit=bit-2;
    }
    else if(c==2)
    {
        offset=indicator+1;
        indicator=indicator + 4;
    k=k+1;
    int b = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
bit=bit-2;
    }
    else if(c==3)
    {
        int m=0;
    offset=indicator-1;
    indicator=indicator + 4;
    k=k+1;
    while(m<=1)
    {
    int b = (add >>> bit) & 3;
                image[offset] = (byte)((image[offset] & 0xFC) | b );
        offset=offset+2;
bit=bit-2;
m++;

if(bit>=0);
else break;
}
 }


}
else if(k==2)
{
    if(c==0)
    {indicator=indicator + 1;
    k=0;
    }
    else if(c==1)    
    {
        offset=indicator-2;
    indicator=indicator + 1;
k=0;
    int b = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
bit=bit-2;
    }
    else if(c==2)
    {
        offset=indicator-1;
        indicator=indicator + 1;
    k=0;
    int b = (add >>> bit) & 3;
                image[offset] = (byte)((image[offset] & 0xFC) | b );
bit=bit-2;
    }
    else if(c==3)
    {
        int m=0;
    offset=indicator-2;
    indicator=indicator + 1;
    k=0;
    while(m<=1)
    {
    int b = (add >>> bit) & 3;

                image[offset] = (byte)((image[offset] & 0xFC) | b );
                offset++;
bit=bit-2;
m++;
if(bit>=0);
else break;

}
 }
}
}
        }
    s.setOffset(indicator);
        return image;

    }

    private byte[] decode_text(byte[] image,int length,int offset)

    {
        //int offset  = 0;
   byte[] result=new byte[length];
        //loop through each byte of text
int indicator=offset,k=0;
    
    
for(int b=0; b<length; ++b )
    
        {
k=0;
         
       for(int bit=6;bit>=0;)
{
    int w=image[indicator];
int c=w  & 3;
if(k==0)
{
    if(c==0)
    {indicator=indicator + 4;
    k=k+1;
    }
    else if(c==1)    
    {
        offset=indicator+2;
    indicator=indicator + 4;
k=k+1;
   result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
bit=bit-2;
    }
    else if(c==2)
    {
        offset=indicator+1;
        indicator=indicator + 4;
    k=k+1;
    result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
bit=bit-2;
    }
    else if(c==3)
    {
        int m=0;
    offset=indicator+1;
    indicator=indicator + 4;
    k=k+1;
    while(m<=1)
    {
   result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
offset++;
   m++;
bit=bit-2;

if(bit>=0);
else break;//esc++;
}
 }


}
else if(k==1)
{
    if(c==0)
    {indicator=indicator + 4;
    k=k+1;
    }
    else if(c==1)    
    {
        offset=indicator-1;
    indicator=indicator + 4;
k=k+1;
   result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
bit=bit-2;
    }
    else if(c==2)
    {
        offset=indicator+1;
        indicator=indicator + 4;
    k=k+1;
    result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
bit=bit-2;
    }
    else if(c==3)
    {
        int m=0;
    offset=indicator-1;
    indicator=indicator + 4;
    k=k+1;
    while(m<=1)
    {
    result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
m++;
bit=bit-2;
offset=offset+2;

if(bit>=0);
else break;
    }
 }


}
else if(k==2)
{
    if(c==0)
    {indicator=indicator + 1;
    k=0;
    }
    else if(c==1)    
    {
        offset=indicator-2;
    indicator=indicator + 1;
k=0;
  result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
bit=bit-2;
    }
    else if(c==2)
    {
        offset=indicator-1;
        indicator=indicator + 1;
    k=0;
   result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
bit=bit-2;
    }
    else if(c==3)
    {
        int m=0;
    offset=indicator-2;
    indicator=indicator + 1;
    k=0;
    while(m<=1)
    {
    result[b] = (byte)((result[b] << 2) | (image[offset] & 3));
bit=bit-2;
m++;
offset++;
if(bit>=0);
else break;
    }
 }


}
}
        }
s.setOffset(indicator);

      return result;

    }
 
 }
// public class pixel
//{
  //  public static void main(String args[])
    //{
      //  Stegno s=new Stegno();
    //s.encode("C:/Users/vrg/Downloads","stegnodemo","jpg","stegnodemo2", "how in the hell are u my boy");
    //String w=s.decode("C:/Users/vrg/Downloads", "stegnodemo2");
    //System.out.printf(w);
      //      }
//}