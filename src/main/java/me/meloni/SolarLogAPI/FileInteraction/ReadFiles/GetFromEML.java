package me.meloni.SolarLogAPI.FileInteraction.ReadFiles;

import me.meloni.SolarLogAPI.FileInteraction.WriteFiles.WriteAttachment;
import me.meloni.SolarLogAPI.Handling.Logger;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GetFromEML {
    public static File getTarFromEML(File emlFile) throws IOException, MessagingException {
        Properties props = System.getProperties();
        Session mailSession = Session.getDefaultInstance(props, null);
        InputStream source = new FileInputStream(emlFile);
        MimeMessage message = new MimeMessage(mailSession, source);

        Multipart multiPart = (Multipart) message.getContent();

        for (int i = 0; i < multiPart.getCount(); i++) {
            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                Logger.log("Found attachment: " + part.getFileName());

                String destFilePath = emlFile.getParent() + "/" + part.getFileName();
                File output = new File(destFilePath);

                try {
                    WriteAttachment.write(part.getInputStream(), output);
                    return output;
                } catch (FileAlreadyExistsException e) {
                    return output;
                }
            }
        }
        return null;
    }

    public static List<File> getTarsFromEMLS(List<File> emlFiles) throws IOException, MessagingException {
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
