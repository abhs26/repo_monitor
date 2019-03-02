package com.aricent.filewatcher;


public class FileInfo {
    private String file_name;
    private String file_path;
    private String file_type;
    private String file_size;
    private String file_create_dateteime;
    private String file_modified_datetime;

    public FileInfo() {
    }

    public FileInfo(String file_name, String file_path, String file_type, String file_size, String file_create_date, String file_modified_date) {
        this.file_name = file_name;
        this.file_path = file_path;
        this.file_type = file_type;
        this.file_size = file_size;
        this.file_create_dateteime = file_create_date;
        this.file_modified_datetime = file_modified_date;
    }

    // Overriding equals() to compare two FileInfo objects 
    @Override
    public boolean equals(Object o) { 
  
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of FileInfo or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof FileInfo)) { 
            return false; 
        } 
        // typecast o to FileInfo so that we can compare data members  
        FileInfo c = (FileInfo) o; 
        // Compare the data members and return accordingly  
        return this.file_name.equals(c.file_name);
    } 


    
    public String getFile_create_date()
    {
        return this.file_create_dateteime;
    }

    public void setFile_create_date(String datetime)
    {
        this.file_create_dateteime = datetime;
    }


    public String getFile_modified_date()
    {
        return this.file_create_dateteime;
    }

    public void setFile_modified_date(String datetime)
    {
        this.file_modified_datetime = datetime;
    }

    
    public String getFile_name() {
        return this.file_name;
    }


    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_path() {
        return this.file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getFile_type() {
        return this.file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getFile_size() {
        return this.file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    


    @Override
    public String toString() {
        return "File [name=" + file_name + ", path=" + file_path + ", size="
                + file_size + ", file_type=" + file_type + "]";
    }
}