package data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.util.Random;

public class DataGenerator {

    static Random rnd = new Random(42);

    private static void genOne(String fn, int n) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("n", n);
        obj.put("source", 0);

        JSONArray arr = new JSONArray();
        int edges = n + rnd.nextInt(n * 2);
        for (int i = 0; i < edges; i++) {
            int u = rnd.nextInt(n);
            int v = rnd.nextInt(n);
            if (u == v) continue;
            int w = 1 + rnd.nextInt(9);
            JSONObject e = new JSONObject();
            e.put("from", u);
            e.put("to", v);
            e.put("weight", w);
            arr.put(e);
        }
        obj.put("edges", arr);

        new java.io.File("data/generated").mkdirs();
        FileWriter fw = new FileWriter("data/generated/" + fn);
        fw.write(obj.toString());
        fw.close();
    }

    public static void generateAll() throws Exception {
        // Small: 6,8,10
        int[] small = {6, 8, 10};
        for (int i = 0; i < small.length; i++) {
            genOne("input_small" + (i + 1) + ".json", small[i]);
        }

        // Medium: 12,15,18
        int[] medium = {12, 15, 18};
        for (int i = 0; i < medium.length; i++) {
            genOne("input_medium" + (i + 1) + ".json", medium[i]);
        }

        // Large: 20,30,40
        int[] large = {20, 30, 40};
        for (int i = 0; i < large.length; i++) {
            genOne("input_large" + (i + 1) + ".json", large[i]);
        }
    }
}
