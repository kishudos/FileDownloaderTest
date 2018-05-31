package downloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class fileDownloader {
	static int count = 0;
	public static void Downloader(String url, String outDir){
		
		String[] parts = url.split("/");
		String fileName = parts[parts.length-1];
		String filePath = outDir+fileName;
		
		URLConnection urlConnection = null;
		try {
			urlConnection = new URL(url).openConnection();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0");
        
        InputStream in = null;
		try {
			in = urlConnection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        FileOutputStream fos = null;
		try {
			File f = new File(filePath);
			if(f.exists() && !f.isDirectory()) { 
			    filePath = outDir+count+"-"+fileName;
			    count++;
			}
			fos = new FileOutputStream(new File(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        System.out.println("Reading from "+filePath+" and writing to file...");
        int length = -1;
        byte[] buffer = new byte[1024];// buffer for portion of data from connection
        try {
			while ((length = in.read(buffer)) > -1) {
			    fos.write(buffer, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("File downloaded");
   }
	public static void main(String[] args){
		String linksFile = "/home/equester/Desktop/links.txt";//Links side as arguments
		String outputPath = "/home/equester/Desktop/DownloadedPic/";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(linksFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String link = "";
		try {
			while((link=br.readLine())!=null){
				link = link.split(" ")[1].trim();
				try{
					Downloader(link, outputPath);//Downloader method is being called 
				}catch(Exception e){
					System.out.println(link+" could't download");
				}
				System.out.println(link);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
