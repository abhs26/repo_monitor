package com.aricent.filewatcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;


class LogFiles{
File latest_file;

    public LogFiles(File latest_file, File oldest_file) {
        this.latest_file = latest_file;
        this.oldest_file = oldest_file;
    }

    public File getLatest_file() {
        return this.latest_file;
    }

    public void setLatest_file(File latest_file) {
        this.latest_file = latest_file;
    }

    public File getOldest_file() {
        return this.oldest_file;
    }

    public void setOldest_file(File oldest_file) {
        this.oldest_file = oldest_file;
    }

    public LogFiles latest_file(File latest_file) {
        this.latest_file = latest_file;
        return this;
    }

    public LogFiles oldest_file(File oldest_file) {
        this.oldest_file = oldest_file;
        return this;
    }



    @Override
    public String toString() {
        return "{" +
            " latest_file='" + getLatest_file() + "'" +
            ", oldest_file='" + getOldest_file() + "'" +
            "}";
    }
File oldest_file;

}


public class App 
{

    private static List<FileInfo> del_FileInfos(List<FileInfo> nListFile, List<FileInfo> oldFileList)
    {
        List<FileInfo> del_file = new ArrayList<>(oldFileList);
        del_file.removeAll(nListFile);

        return del_file;
    }
    public static void main( String[] args )
    {
        
        String file_cname = System.getProperty("user.dir");
        System.out.println("Working Directory = "+file_cname );
        DirScanner dir = new DirScanner();
        File[] list_File = getXMLFiles(new File(file_cname));
        LogFiles latest_file = get_latest_file(Arrays.asList(list_File));
        if(list_File.length>=3)
        {
            latest_file.getOldest_file().delete();
        }

        FileParser fp = new FileParser();
        List<FileInfo> old_file= fp.parseXML(latest_file.getLatest_file().getPath());
        
        dir.ScanDir(file_cname);
        String file_name = dir.get_file_name();
        List<FileInfo> new_file = fp.parseXML(file_name);

        List<FileInfo> del_file = del_FileInfos(new_file, old_file);

        System.out.println("Deleted Files");
        for(FileInfo fr : del_file)
        {
            System.out.println(fr.toString());
        }
        
    }

    public static Date least(Date a, Date b) {
        return a == null ? b : (b == null ? a : (a.before(b) ? a : b));
    }
    public static LogFiles get_latest_file(List<File> file_list)
    {
        Collections.sort(file_list, Collections.reverseOrder());

        return new LogFiles(file_list.get(0),file_list.get(file_list.size()-1)) ;


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
