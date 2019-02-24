package com.aricent.filewatcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Working Directory = " );
        String file_cname = System.getProperty("user.dir");
        DirScanner dir = new DirScanner();
        
        System.out.println(getXMLFiles(new File("/Users/abhishek/Projects/ACP_Project/temp")));
        dir.ScanDir(file_cname);


        new WatchThread("temp").start();
        System.out.println("WatchThread is running!");
        try{
        TimeUnit.SECONDS.sleep(30);
        }
        catch(Exception ex){
            //
        }
        System.out.println("WatchThread is exiting!");
    }


    public static File[] getXMLFiles(File folder) {
        List<File> aList = new ArrayList<File>();
    
        File[] files = folder.listFiles();
        for (File pf : files) {
    
          if (pf.isFile() && getFileExtensionName(pf).indexOf("xml") != -1) {
            aList.add(pf);
          }
        }
        return aList.toArray(new File[aList.size()]);
      }
    
      public static String getFileExtensionName(File f) {
        if (f.getName().indexOf(".") == -1) {
          return "";
        } else {
          return f.getName().substring(f.getName().length() - 3, f.getName().length());
        }
      }
}
