package downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Downloader {
	public static void main(String[] args) throws IOException{
        System.out.println("opening connection");
        Scanner sc = new Scanner(System.in);
       
        System.out.println("Enter Url : ");
        String url = sc.nextLine();
        
        URLConnection urlConnection = new URL(url).openConnection();
        urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0");
        
        InputStream in = urlConnection.getInputStream();
        
        System.out.println("Enter file name to save : ");
        String output = sc.nextLine();
        sc.close();
        FileOutputStream fos = new FileOutputStream(new File(output));

        System.out.println("reading from resource and writing to file...");
        int length = -1;
        byte[] buffer = new byte[1024];// buffer for portion of data from connection
        while ((length = in.read(buffer)) > -1) {
            fos.write(buffer, 0, length);
        }
        fos.close();
        in.close();
        System.out.println("File downloaded");
   }
}
