package FileInteraction.Tools;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileObject implements Serializable {

    private final Map<String, Object> object = new HashMap<>();

    public FileObject(Map<Date, List<Integer>> data) {
        this.object.put("data",data);
    }

    public Map<Date, List<Integer>> getData() {
        return (Map<Date, List<Integer>>) object.get("data");
    }

    public void putInformation(String key, Object info) {
        object.put(key,info);
    }

    public Object getInformation(String key) {
        return object.get(key);
    }

    public static Map<Date, List<Integer>> data(FileObject fileObject) {
        return fileObject.getData();
    }


}
