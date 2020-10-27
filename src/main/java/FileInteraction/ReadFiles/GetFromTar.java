package FileInteraction.ReadFiles;

import FileInteraction.GetFile;
import Handling.Logger;
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

public class GetFromTar {

    public static List<File> unTar(final File inputFile, final File outputDir) throws IOException, ArchiveException {

        Logger.log(String.format("Untaring %s to dir %s.", inputFile.getAbsolutePath(), outputDir.getAbsolutePath()));

        final List<File> untaredFiles = new LinkedList<>();
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
                //Logger.log(String.format("Creating output file %s.", outputFile.getAbsolutePath()));
                if(!outputFile.exists()) {
                    final OutputStream outputFileStream = new FileOutputStream(outputFile);
                    IOUtils.copy(debInputStream, outputFileStream);
                    outputFileStream.close();
                }
            }
            untaredFiles.add(outputFile);
        }
        debInputStream.close();

        return untaredFiles;
    }

    public static File unGzip(final File inputFile, final File outputDir) throws IOException {

        Logger.log(String.format("Ungzipping %s to dir %s.", inputFile.getAbsolutePath(), outputDir.getAbsolutePath()));

        final File outputFile = new File(outputDir, inputFile.getName().substring(0, inputFile.getName().length() - 3));

        if(!outputFile.exists()) {
            final GZIPInputStream in = new GZIPInputStream(new FileInputStream(inputFile));
            final FileOutputStream out = new FileOutputStream(outputFile);

            IOUtils.copy(in, out);

            in.close();
            out.close();
        }

        return outputFile;
    }

    public static List<File> getValidFilesFromTarArchive(File tar) throws IOException, ArchiveException {
        String tardir = FilenameUtils.removeExtension(FilenameUtils.removeExtension(String.valueOf(tar)));
        File outputdir = GetFile.File(tardir);
        if(!outputdir.exists()){
            boolean b = outputdir.mkdir();
            if(!b) {
                Logger.log("Cant create the directory " + outputdir.getAbsolutePath());
                throw new ArchiveException("cant create directory");
            }
        }
        return Validate.validfiles(unTar(unGzip(tar,outputdir),outputdir));
    }

    public static List<File> getValidFilesFromTarArchives(List<File> tars) throws IOException, ArchiveException {
        List<File> validfiles = new ArrayList<>();
        for (File tar : tars) {
            Logger.log("Extracting data from " + tar.getAbsolutePath());
            validfiles.addAll(getValidFilesFromTarArchive(tar));
        }
        return validfiles;
    }
}
