package me.meloni.SolarLogAPI.FileInteraction.ReadFiles;

import me.meloni.SolarLogAPI.Handling.Logger;
import me.meloni.SolarLogAPI.MailInteraction.GetTarFromMessage;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GetFromEML {
    public static File getTarFromEML(File emlFile) throws IOException, MessagingException, URISyntaxException {
        Properties props = System.getProperties();
        Session mailSession = Session.getDefaultInstance(props, null);
        InputStream source = new FileInputStream(emlFile);
        MimeMessage message = new MimeMessage(mailSession, source);

        return GetTarFromMessage.getFromMessage(message);
    }

    public static List<File> getTarsFromEMLS(List<File> emlFiles) throws IOException, MessagingException, URISyntaxException {
        List<File> tars = new ArrayList<>();
        int i = 0;
        for (File emlFile : emlFiles) {
            i++;
            Logger.log("Extracting tars from " + emlFile + "(" + i + " from " + emlFiles.size() + ").");
            tars.add(getTarFromEML(emlFile));
        }
        return tars;
    }
}
