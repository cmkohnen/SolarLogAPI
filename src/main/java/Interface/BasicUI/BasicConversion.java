package Interface.BasicUI;

import FileInteraction.GetFile;
import FileInteraction.ReadFiles.GetFromTar;
import FileInteraction.Tools.FileObject;
import FileInteraction.WirteFiles.WriteFileObject;
import Interface.SimpleFrame;
import TransformUtilities.DataConversion.GetDataMap;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BasicConversion extends JPanel {
    
    static List<File> files = new ArrayList<>();
    static JPanel filepanel = new JPanel();

    public BasicConversion() {
        setLayout(new BorderLayout());

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));

        JButton addFile = new JButton("Add File");
        addFile.addActionListener(e -> addToList(GetFile.ValidChosenDataFile()));

        JButton addDirectory = new JButton("Add from Folder");
        addDirectory.addActionListener(e -> {
            try {
                for (File file : GetFile.ChosenValidFilesInDirectory()) {
                    addToList(file);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        JButton addTar = new JButton("Add from tar");
        addTar.addActionListener(e -> {
            try {
                GetFromTar.getValidFilesFromTarArchive(GetFile.ChosenTarArchive()).forEach(this::addToList);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        JButton addTars = new JButton("Add from tars");
        addTars.addActionListener(e -> {
            try {
                GetFromTar.getValidFilesFromTarArchives(GetFile.ChosenTarsInDirectory()).forEach(this::addToList);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        buttons.add(addFile);
        buttons.add(addDirectory);
        buttons.add(addTar);
        buttons.add(addTars);

        add(buttons, BorderLayout.PAGE_START);

        JButton convert = new JButton("Convert");
        add(convert, BorderLayout.PAGE_END);
        convert.addActionListener(e -> {
            try {
                Map<Date, List<Integer>> data = GetDataMap.DataFromFiles(files);
                FileObject fileObject = new FileObject(data);
                WriteFileObject.write(GetFile.ChosenSafeLocation(),fileObject);
            } catch (IOException | ParseException ioException) {
                ioException.printStackTrace();
            }
        });

        filepanel.setLayout(new BoxLayout(filepanel, BoxLayout.Y_AXIS));
        add(filepanel, BorderLayout.CENTER);


    }

    private void addToList(File fi) {
        if(!files.contains(fi)){
            files.add(fi);
            filepanel.removeAll();
            for (File file : files) {
                JLabel m = new JLabel();
                m.setText(file.getName());
                filepanel.add(m);
            }
            filepanel.setVisible(false);
            filepanel.setVisible(true);
        }
    }

    public static void run() {
        JFrame f = new SimpleFrame(new BasicConversion());
        f.setSize(300,500);
        f.setTitle("Conversion");
    }
}
