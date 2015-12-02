package com.telerik.plugins.appbuilderci;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.PrintStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PackageManager {
	final private PrintStream logger;
    final private ProjectFilesManager projectFilesManager;
    
    public PackageManager(PrintStream logger) {
        this.logger = logger;
        this.projectFilesManager = new ProjectFilesManager();
    }
    
    public Path createPackage(String sourceDirPath) throws IOException {
        Path zipFilePath = Paths.get(sourceDirPath, "project_package.zip");
        Files.deleteIfExists(zipFilePath);
        
        Path projectDir = projectFilesManager.getProjectDir(sourceDirPath);
        this.logger.println(String.format("Project Folder : %s", projectDir));
        this.logger.println(String.format("Package File : %s", zipFilePath));
        
        pack(projectDir, zipFilePath);
        return zipFilePath;
    }
    
    private void pack(Path sourceDirPath, final Path zipFilePath) throws IOException {
        final PathFilteringService pathFilteringService = new PathFilteringService(Paths.get(sourceDirPath.toString(), ".abignore"));
        ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(Files.createFile(zipFilePath)));
        try {
        	final List<Path> packagingFiles = new ArrayList<Path>();
        	Files.walkFileTree(sourceDirPath, new SimpleFileVisitor<Path>()
        	{
        	   @Override
        	   public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs)
        	      throws IOException
        	   {
        		   boolean isExcluded = pathFilteringService.isFileExcluded(filePath) || 
				    		 zipFilePath.toString().equalsIgnoreCase(filePath.toString()) || 
				    		 filePath.toString().contains(Constants.OutputFolderName);
        		   
                   if(isExcluded){
                	   logger.println(String.format("Excluding File : %s", filePath));
                   }
                   else{
                	   packagingFiles.add(filePath);
                   }
        	      return FileVisitResult.CONTINUE;
        	   }
        	});
        	
        	for(Path filePath : packagingFiles){
        		Path relativePath = sourceDirPath.relativize(filePath); 
                ZipEntry zipEntry = new ZipEntry(relativePath.toString());
                try {
                    zs.putNextEntry(zipEntry);
                    zs.write(Files.readAllBytes(filePath));
                    zs.closeEntry();
                } catch (Exception e) {
                    System.err.println(e);
                }
            	logger.println(String.format("Packaging File : %s", relativePath.toString()));
        	}
        } finally {
            zs.close();
        }
    }
}