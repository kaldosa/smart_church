package com.laonsys.springmvc.extensions.storage;

import java.io.File;

public interface RenamePolicy {
    public File getRenameFile(String path);

    public File getRenameFile(String parent, String child);

    public String getRename(String path);

    public String getRename(String parent, String child);
}
