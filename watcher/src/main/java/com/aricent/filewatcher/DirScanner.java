package com.aricent.filewatcher;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.FileVisitor;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.FileVisitResult;
import java.nio.file.FileSystems;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import com.aricent.filewatcher.FileWriter;
import com.aricent.filewatcher.FileInfo;
public class DirScanner {


ArrayList<FileInfo> file_list = new ArrayList<FileInfo>();

FileWriter file_writer;
String file_name;

    private static String getFileExtension(String fileName) {
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "default";
    }

    DirScanner()
    {
        file_writer = new FileWriter();
        SimpleDateFormat sdfDate = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss.SSS");// dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        String file_name = "settings_"+strDate.replace(":","_").replace(".","_").replace(" ","_")+".xml";
        file_writer.setFile(file_name);
        this.file_name = file_name;

    }

    public String get_file_name()
    {
        return this.file_name;
    }




          

public void ScanDir(String filePath) {
    FileVisitor<Path> simpleFileVisitor = new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult preVisitDirectory(Path dir,BasicFileAttributes attrs)
          throws IOException {
        System.out.println("DIRECTORY NAME:"+ dir.getFileName()); 
        return FileVisitResult.CONTINUE;
      }
       
      @Override
      public FileVisitResult visitFile(Path visitedFile,BasicFileAttributes fileAttributes)
          throws IOException {
try{
            String name = visitedFile.getFileName().toString();
            String size = String.valueOf(fileAttributes.size());
            String mimeType = getFileExtension(visitedFile.getFileName().toString());
            
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String createTime = sdf.format(fileAttributes.creationTime().toMillis());
            String modifiedTime = sdf.format(visitedFile.toFile().lastModified());
            FileInfo file_info = new FileInfo(visitedFile.getFileName().toString(),
            visitedFile.toString(),mimeType,size,createTime,modifiedTime);
            System.out.println(file_info.toString());
            file_list.add(file_info);
        return FileVisitResult.CONTINUE;
}
catch(Exception ex){
    StringWriter sw = new StringWriter();
PrintWriter pw = new PrintWriter(sw);
ex.printStackTrace(pw);

    System.out.print(sw.toString());
    return FileVisitResult.CONTINUE;
}
      }
    };
    FileSystem fileSystem = FileSystems.getDefault();
    Path rootPath = fileSystem.getPath(filePath);
    try {
        Files.walkFileTree(rootPath, simpleFileVisitor);
        } catch (IOException ioe) {
  ioe.printStackTrace();
}

try{
    file_writer.saveConfig(file_list);
}
catch(Exception ex) 
{

}

    
  }
}