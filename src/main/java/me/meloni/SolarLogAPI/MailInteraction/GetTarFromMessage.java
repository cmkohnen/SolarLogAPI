package me.meloni.SolarLogAPI.MailInteraction;

import me.meloni.SolarLogAPI.FileInteraction.WorkingDirectory;
import me.meloni.SolarLogAPI.FileInteraction.WriteFiles.WriteAttachment;
import me.meloni.SolarLogAPI.Handling.Logger;
import me.meloni.SolarLogAPI.Handling.Translation;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
/**
 * This Class provides a way to extract an tar archive from a {@link Message}
 * @author ChaosMelone9
 * @since 3.0.5
 */
public class GetTarFromMessage {
    public static File getTarArchiveFromMessage(Message message) throws MessagingException, IOException {
        Multipart multiPart = (Multipart) message.getContent();

        for (int i = 0; i < multiPart.getCount(); i++) {
            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                Logger.log(Logger.INFO_LEVEL_3 + Translation.get("message_foundtar") + "\"" + part.getFileName() + "\"");

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
