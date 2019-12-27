package com.laonsys.springmvc.extensions.storage.file;

import java.io.File;
import java.util.UUID;

import javax.inject.Named;

import com.laonsys.springmvc.extensions.storage.RenamePolicy;

@Named("fileRenamePolicy")
public class FileRenamePolicy implements RenamePolicy {

	private File rename(File file) {
		String fileName = file.getName().replace(" ", "_");
		String directory = file.getParent();
		
		int offset = fileName.lastIndexOf(".");
		String ext = "";
		if(offset != -1) {
			ext = (fileName.substring(offset)).toLowerCase();
		}
		
		File renamedFile = null;
		String rename = fileName.substring(0, offset);
		if(rename.matches("[_A-Za-z0-9-\\.]+")) {
			renamedFile = new File(directory, rename + ext);
			
			if(!renamedFile.exists()) {
				return renamedFile;
			}
		}
		
		do {
			rename = UUID.randomUUID().toString();
			renamedFile = new File(directory, rename + ext);
		} while(renamedFile.exists());
		
		return renamedFile;
	}

	@Override
	public File getRenameFile(String path) {
	    return rename(new File(path));
	}
	
	@Override
	public File getRenameFile(String parent, String child) {
	    return rename(new File(parent, child));
	}
	
	@Override
	public String getRename(String parent, String child) {
	    return rename(new File(parent, child)).getName();
	}
	
	@Override
	public String getRename(String path) {
	    return rename(new File(path)).getAbsolutePath();
	}
}
