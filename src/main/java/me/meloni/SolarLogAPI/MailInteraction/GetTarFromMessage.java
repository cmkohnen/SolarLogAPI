package me.meloni.SolarLogAPI.MailInteraction;

import me.meloni.SolarLogAPI.FileInteraction.WorkingDirectory;
import me.meloni.SolarLogAPI.FileInteraction.WriteFiles.WriteAttachment;
import me.meloni.SolarLogAPI.Handling.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileAlreadyExistsException;

public class GetTarFromMessage {
    public static File getFromMessage(Message message) throws MessagingException, IOException, URISyntaxException {
        Multipart multiPart = (Multipart) message.getContent();

        for (int i = 0; i < multiPart.getCount(); i++) {
            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                Logger.log("Found attachment: " + part.getFileName());

                File output = new File(WorkingDirectory.getDirectory(), part.getFileName());

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
}
