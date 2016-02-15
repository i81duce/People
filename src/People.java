import jodd.json.JsonSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class People {
    public static void main(String[] args) throws IOException {
        HashMap<String, ArrayList<Person>> countryNameMap;

        String fileContents = readFile("people.csv");
        String[] lines = fileContents.split("\n");

        countryNameMap = populateHashMap(lines);

            saveCatalog(countryNameMap);



        System.out.println(formatHashMap(countryNameMap));
    }

    static String readFile(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner fileScanner = new Scanner(f);
        fileScanner.useDelimiter("\\Z");
        String fileContents;
        fileContents = fileScanner.next();
        return fileContents;
    }
    public static HashMap<String, ArrayList<Person>> populateHashMap(String [] lines){
        int i = 0;
        HashMap<String, ArrayList<Person>> countryNameMap = new HashMap<>();
        for (String line : lines) {
            if (i != 0) {

                String[] columns = line.split("\\,");
                String id = columns[0];
                String firstName = columns[1];
                String lastName = columns[2];
                String email = columns[3];
                String country = columns[4];
                String ipAddress = columns[5];
                Person p = new Person(id, firstName, lastName, email, country, ipAddress);
                if (!countryNameMap.containsKey(country)) {
                    countryNameMap.put(country, new ArrayList<Person>());
                }
                countryNameMap.get(country).add(p); //then we add our country object "c" to the arraylist in the hashmap
                Collections.sort(countryNameMap.get(country));
            }
            i++;
        }
        return countryNameMap;
    }

    public static String formatHashMap (HashMap<String, ArrayList<Person>> countryNameMap) {
        String s = new String();
        for (String country : countryNameMap.keySet()) {
            for (Person p : countryNameMap.get(country)) {
                s += String.format("%s %s from %s\n", p.firstName, p.lastName, p.country);
            }
        }
        return s;
    }

    public static void saveCatalog(HashMap hashMap) throws IOException {
        JsonSerializer s = new JsonSerializer();
        //for (Person p : personList) {
            String json = s.include("*").serialize(hashMap);

            File f = new File("people.json");
            FileWriter fw = new FileWriter(f);
            fw.write(json);
            fw.close();
        //}
        System.out.println("Saved");
    }
}