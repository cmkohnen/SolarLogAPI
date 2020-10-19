package FileInteraction;

import java.util.*;

public class FileObject {
    public FileObject() {

    }

    public static Map<String, Object> object(Map<Date, List<Integer>> data, Map<String,Object> information){
        Map<String, Object> FileObject = new HashMap<>();

        FileObject.put("data",data);

        information.forEach(FileObject::put);

        return FileObject;
    }


}
