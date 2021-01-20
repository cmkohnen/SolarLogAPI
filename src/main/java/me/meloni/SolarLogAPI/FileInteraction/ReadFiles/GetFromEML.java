package me.meloni.SolarLogAPI.FileInteraction.ReadFiles;

import me.meloni.SolarLogAPI.Handling.Logger;
import me.meloni.SolarLogAPI.Handling.Translation;
import me.meloni.SolarLogAPI.MailInteraction.GetTarFromMessage;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
/**
 * This Class provides functions to get tar archives from EML files.
 * @author ChaosMelone9
 * @since 3.0.5
 */
public class GetFromEML {
    public static File getTarFromEML(File emlFile) throws IOException, MessagingException {
        Properties props = System.getProperties();
        Session mailSession = Session.getDefaultInstance(props, null);
        InputStream source = new FileInputStream(emlFile);
        MimeMessage message = new MimeMessage(mailSession, source);
        return GetTarFromMessage.getTarArchiveFromMessage(message);
    }

    public static List<File> getTarsFromEMLS(List<File> emlFiles) throws IOException, MessagingException {
        List<File> tars = new ArrayList<>();
        int i = 0;
        for (File emlFile : emlFiles) {
            i++;
            Logger.log(Logger.INFO_LEVEL_3 + String.format(Translation.get("eml_export_tars"),emlFile, i, emlFiles.size()));
            tars.add(getTarFromEML(emlFile));
        }
        return tars;
    }
}
