package kr.re.amc.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringUtil extends StringUtils{
    public static String random(int len){
        int random = 0;
        String randomStr = "";

        for(int i=0; i<len; i++){
            random = (int) (Math.random() * 10);
            randomStr += String.valueOf(random);
        }

        return randomStr;
    }
    
    @SuppressWarnings("unchecked")
	private static void lsFolderGet(String sourcePath, String destPath, ChannelSftp sftpChannel) throws SftpException {
	    Vector<ChannelSftp.LsEntry> list = sftpChannel.ls(sourcePath);
	    
	    for (ChannelSftp.LsEntry oListItem : list) {
	        if (!oListItem.getAttrs().isDir()) {
//	            if (!(new File(destPath + "/" + oListItem.getFilename())).exists() || (oListItem.getAttrs().getMTime() > Long.valueOf(new File(destPath + "/" + oListItem.getFilename()).lastModified() / (long) 1000).intValue())) { // Download only if changed later.
	                new File(destPath + "/" + oListItem.getFilename());
	                
	                sftpChannel.get(sourcePath + "/" + oListItem.getFilename(), destPath + "/" + oListItem.getFilename());
//	            }
	        } else if (!(".".equals(oListItem.getFilename()) || "..".equals(oListItem.getFilename()))) {
	            new File(destPath + "/" + oListItem.getFilename()).mkdirs(); // Empty folder copy.
	            lsFolderGet(sourcePath + "/" + oListItem.getFilename(), destPath + "/" + oListItem.getFilename(), sftpChannel);
	        }
	    }
	}
    
    public static void downloadDir(String sourcePath, String destPath,  ChannelSftp sftpChannel) throws SftpException, FileNotFoundException { // With subfolders and all files.

		File file = new File(destPath);
		
		if (file.exists()) {
			file.delete();
		}
		
		
		file.mkdirs();
	    
	    sftpChannel.lcd(file.getAbsolutePath());
	    lsFolderGet(sourcePath, file.getAbsolutePath(),sftpChannel);
	}
    

	public static void zip( String sourcDirPath,  String zipPath) throws IOException {
		
		  File file1 = new File(zipPath);
		  if (file1.exists()) {
			  file1.delete();
		  }
		  Path zipFile = Files.createFile(Paths.get(zipPath));
		  Path sourceDirPath = Paths.get(sourcDirPath);
		  
		  try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile));
		       Stream<Path> paths = Files.walk(sourceDirPath)) { 
		      paths
		              .filter(path -> !Files.isDirectory(path))
		              .forEach(path -> { 
		                  ZipEntry zipEntry = new ZipEntry(sourceDirPath.relativize(path).toString());
		                  try { 
		                      zipOutputStream.putNextEntry(zipEntry);
		                      Files.copy(path, zipOutputStream);
		                      zipOutputStream.closeEntry();
		                  } catch (IOException e) {
		                      System.err.println(e);
		                  }
		              });
		  }
		}	
	
}

