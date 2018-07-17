import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {


        Map<String, String> map = new HashMap<String,String>();
        List<String> arr = new ArrayList<String>();

        arr.add("小米");
        arr.add("华为");
        arr.add("中兴");
        arr.add("中兴");
        arr.add("iphone");
        arr.add("华为");
        arr.add("华为");

        System.out.println(arr.size());
        System.out.println(arr.toString());
    }


}
