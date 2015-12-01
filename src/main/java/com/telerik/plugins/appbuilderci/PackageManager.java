package com.telerik.plugins.appbuilderci;

import java.nio.file.*;
import java.io.PrintStream;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Predicate;
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
    
    private void pack(final Path sourceDirPath, Path zipFilePath) throws IOException {
        final PathFilteringService pathFilteringService = new PathFilteringService(Paths.get(sourceDirPath.toString(), ".abignore"));
        final ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(Files.createFile(zipFilePath)));
        try {
            Files.walk(sourceDirPath)
            .filter(new Predicate<Path>() {
				@Override
				public boolean test(Path path) {
				    boolean isExcluded = pathFilteringService.isFileExcluded(path);
				    if(isExcluded){
				    	PackageManager.this.logger.println(String.format("Excluding File : %s", path));
				    }
				    return !isExcluded;
				}
			})
            .filter(new Predicate<Path>() {
				@Override
				public boolean test(Path path) {
					return !Files.isDirectory(path);
				}
			})
            .forEach(new Consumer<Path>() {
				@Override
				public void accept(Path path) {
					Path relativePath = sourceDirPath.relativize(path); 
				    ZipEntry zipEntry = new ZipEntry(relativePath.toString());
				    try {
				        zs.putNextEntry(zipEntry);
				        zs.write(Files.readAllBytes(path));
				        zs.closeEntry();
				    } catch (Exception e) {
				        System.err.println(e);
				    }
					PackageManager.this.logger.println(String.format("Packaging File : %s", relativePath.toString()));
				}
			});
        } finally {
            zs.close();
        }
    }
}