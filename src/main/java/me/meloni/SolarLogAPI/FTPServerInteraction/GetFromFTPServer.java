package me.meloni.SolarLogAPI.FTPServerInteraction;

import me.meloni.SolarLogAPI.FileInteraction.WorkingDirectory;
import me.meloni.SolarLogAPI.Handling.Logger;
import me.meloni.SolarLogAPI.Handling.Translation;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetFromFTPServer {
    public static List<File> getJSFilesFromFTPServer(String host, String user, String password) throws IOException {
        FTPClient ftp = new FTPClient();
        Logger.log(Logger.INFO_LEVEL_2 + Translation.get("ftp_connect") + host);
        ftp.connect(host);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }
        Logger.log(Logger.INFO_LEVEL_2 + Translation.get("ftp_login") + user + "...");
        ftp.login(user, password);
        Logger.log(Logger.INFO_LEVEL_2 + Translation.get("ftp_list"));
        FTPFile[] files = ftp.listFiles();
        List<FTPFile> minuteFiles = new ArrayList<>();
        for (FTPFile file : files) {
            if(file.getName().startsWith("min") && !(file.getName().contains("day") || file.getName().contains("cur"))) {
                minuteFiles.add(file);
            }
        }

        List<File> jsFiles = new ArrayList<>();
        Logger.log(Logger.INFO_LEVEL_2 + Translation.get("ftp_downloading_1") + minuteFiles.size() + Translation.get("ftp_downloading_2"));
        for (int i = 0; i < minuteFiles.size(); i++) {
            FTPFile minuteFile = minuteFiles.get(i);
            Logger.log(Logger.INFO_LEVEL_3 + Translation.get("ftp_downloading_1") + minuteFile.getName() + " (" + i + " of " + minuteFiles.size() + ")");
            File download = new File(WorkingDirectory.getDirectory(), minuteFile.getName());
            FileOutputStream out = new FileOutputStream(download);
            ftp.retrieveFile(minuteFile.getName(),out);
            jsFiles.add(download);
        }
        return jsFiles;
    }
}
