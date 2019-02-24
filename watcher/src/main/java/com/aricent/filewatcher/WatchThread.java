
package com.aricent.filewatcher;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

public class WatchThread extends Thread {

  Path myDir;
  WatchService watcher;

  WatchThread(String path) {
    try {
      myDir = Paths.get(path);
      watcher = myDir.getFileSystem().newWatchService();
      myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
      StandardWatchEventKinds.ENTRY_MODIFY,
      StandardWatchEventKinds.ENTRY_DELETE );
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }


  public void run() {
    while (true) {
      try {
        WatchKey watchKey = watcher.take();
        List<WatchEvent<?>> events = watchKey.pollEvents();
        System.out.println("Created: " );
        for (WatchEvent<?> event : events) {

          if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
            System.out.println("Created: " + event.context().toString());
          }
          else if(event.kind() == StandardWatchEventKinds.ENTRY_MODIFY)
          {
            System.out.println("Modified: " + event.context().toString());
          }
          else if(event.kind() == StandardWatchEventKinds.ENTRY_DELETE)
          {
            System.out.println("Deleted: " + event.context().toString());

          }
        }
        watchKey.reset();
      }
      catch (Exception e) {
        System.out.println("Error: " + e.toString());
      }
    }
    
  }
}