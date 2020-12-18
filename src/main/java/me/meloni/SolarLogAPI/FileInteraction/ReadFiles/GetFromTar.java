package me.meloni.SolarLogAPI.FileInteraction.ReadFiles;

import me.meloni.SolarLogAPI.FileInteraction.WorkingDirectory;
import me.meloni.SolarLogAPI.Handling.Logger;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * This Class includes functions to get the content of a tar archive.
 * @author ChaosMelone9
 * @since 1.0.0
 * @version 2
 */
public class GetFromTar {

    private static List<File> unTar(final File inputFile, final File outputDir) throws Exception {
        Logger.log(String.format("UnTaring %s to dir %s.", inputFile.getAbsolutePath(), outputDir.getAbsolutePath()));

        final List<File> unTaredFiles = new LinkedList<>();
        final InputStream is = new FileInputStream(inputFile);
        final TarArchiveInputStream debInputStream = (TarArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream("tar", is);
        TarArchiveEntry entry;
        while ((entry = (TarArchiveEntry)debInputStream.getNextEntry()) != null) {
            final File outputFile = new File(outputDir, entry.getName());
            if (entry.isDirectory()) {
                Logger.log(String.format("Attempting to write output directory %s.", outputFile.getAbsolutePath()));
                if (!outputFile.exists()) {
                    Logger.log(String.format("Attempting to create output directory %s.", outputFile.getAbsolutePath()));
                    if (!outputFile.mkdirs()) {
                        throw new IllegalStateException(String.format("Couldn't create directory %s.", outputFile.getAbsolutePath()));
                    }
                }
            } else {
                if(!outputFile.toPath().normalize().startsWith(outputDir.toPath())) {
                    throw new Exception("Bad Zip Entry!");
                }
                if(!outputFile.exists()) {
                    final OutputStream outputFileStream = new FileOutputStream(outputFile);
                    IOUtils.copy(debInputStream, outputFileStream);
                    outputFileStream.close();
                }
            }
            unTaredFiles.add(outputFile);
        }
        debInputStream.close();

        return unTaredFiles;
    }

    private static File unGzip(final File inputFile, final File outputDir) throws Exception {
        Logger.log(String.format("UnGZipping %s to dir %s.", inputFile.getAbsolutePath(), outputDir.getAbsolutePath()));

        final File outputFile = new File(outputDir, inputFile.getName().substring(0, inputFile.getName().length() - 3));
        if(!outputFile.toPath().normalize().startsWith(outputDir.toPath())) {
            throw new Exception("Bad Zip Entry!");
        }

        if(!outputFile.exists()) {
            final GZIPInputStream in = new GZIPInputStream(new FileInputStream(inputFile));
            final FileOutputStream out = new FileOutputStream(outputFile);

            IOUtils.copy(in, out);

            in.close();
            out.close();
        }
        return outputFile;
    }

    public static List<File> getValidFilesFromTarArchive(File tar) throws Exception {
        String tarDirectory = FilenameUtils.removeExtension(FilenameUtils.removeExtension(tar.getName()));
        File outputDirectory = new File(WorkingDirectory.getDirectory(), tarDirectory);
        if(!outputDirectory.exists()){
            boolean b = outputDirectory.mkdir();
            if(!b) {
                Logger.log("Cant create the directory " + outputDirectory.getAbsolutePath());
                throw new ArchiveException("cant create directory");
            }
        }
        return Validate.validFiles(unTar(unGzip(tar,outputDirectory),outputDirectory));
    }

    public static List<File> getValidFilesFromTarArchives(List<File> tars) throws Exception {
        List<File> validFiles = new ArrayList<>();
        for (File tar : tars) {
            Logger.log("Extracting data from " + tar.getAbsolutePath());
            validFiles.addAll(getValidFilesFromTarArchive(tar));
        }
        return validFiles;
    }
}
