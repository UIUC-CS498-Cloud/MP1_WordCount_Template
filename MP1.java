import java.io.*;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MP1 {
    Random generator;
    String userName;
    String delimiters = " \t,;.?!-:@[](){}_*/";
    static String[] stopWordsArray = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};

    static HashMap<String, Integer> wordCount = new HashMap<>(); // store word and its frequency

    public MP1(String userName) {
        this.userName = userName;
    }


    public Integer[] getIndexes() throws NoSuchAlgorithmException {
        Integer n = 10000;
        Integer number_of_lines = 50000;
        Integer[] ret = new Integer[n];
        long longSeed = Long.parseLong(this.userName);
        this.generator = new Random(longSeed);
        for (int i = 0; i < n; i++) {
            ret[i] = generator.nextInt(number_of_lines);
        }
        return ret;
    }

    public String[] process() throws Exception{
    	String[] topItems = new String[20];
        Integer[] indexes = getIndexes();

        Map<Integer, Integer> indexesCount = new HashMap<>();
        for (int idx: indexes) {
            if (!indexesCount.containsKey(idx)) {
                indexesCount.put(idx, 0);
            }
            indexesCount.put(idx, indexesCount.get(idx) + 1);
        }

    	//TO DO
//        FileReader fr = new FileReader("input.txt");
//        BufferedReader br = new BufferedReader(fr);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String currentLine = br.readLine();

        List<String> ignoreWord = new ArrayList<>(Arrays.asList(stopWordsArray));
//        ignoreWord.add("");
//        for(int i = 0; i < ignoreWord.size(); i++) {
//            System.out.println(ignoreWord.get(i));
//        }
//        System.out.println("=============");
        int count = 0;
        while (currentLine != null) {
//            currentLine.toLowerCase();
//            System.out.println(currentLine);

            if (!indexesCount.containsKey(count)) {
                currentLine = br.readLine();
                count++;
                continue;
            }

            int k = indexesCount.get(count);

            for (int i = 0; i < k; i++) {
                String[] temp = currentLine.trim().toLowerCase().split("[ \"\t,;.?!\\-:@\\[\\](){}_*/]+");

                for (int n = 0; n < temp.length; n++) {
//                System.out.println("" + n + " " + temp[n]);
//                if (temp[n].equals("")) {
//                    System.out.println(currentLine.toLowerCase());
//                }
                    if (ignoreWord.contains(temp[n])) {
                        continue;
                    }
                    if (wordCount.containsKey(temp[n]) ) {
                        wordCount.put(temp[n], wordCount.get(temp[n]) + 1);
                        continue;
                    } else {
                        wordCount.put(temp[n], 1);
                    }
                }
            }

            currentLine = br.readLine();
            count++;
        }
        br.close();
//        fr.close();
//        for (String i : wordCount.keySet()) {
//            System.out.println(i + " " + wordCount.get(i));
//        }
        List<Map.Entry<String, Integer>> wordList = new ArrayList<Map.Entry<String, Integer>>(wordCount.entrySet());
        wordList.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if (o1.getValue() == o2.getValue()) {
                    String s1 = o1.getKey();
                    String s2 = o2.getKey();

                    for (int i = 0; i < s1.length() && i < s2.length(); i++) {
                        char c1 = s1.charAt(i);
                        char c2 = s2.charAt(i);
                        if (c1 == c2) {
                            continue;
                        }
                        return c1 - c2;
                    }
                    if (s1.length() < s2.length()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
                return o2.getValue().compareTo(o1.getValue());
            }
        });
//        for (int i = 0; i < wordList.size(); i++) {
//            System.out.println(wordList.get(i).getKey() + ": " + wordList.get(i).getValue());
//        }
        for (int i = 0; i < topItems.length; i++) {
            topItems[i] = wordList.get(i).getKey();
//            System.out.println(topItems[i] + " " + wordList.get(i).getValue());
        }

		return topItems;
    }

    /* Read File*/
//    public static List readFile() throws IOException {
//        FileReader fr = new FileReader("input.txt");
//        BufferedReader br = new BufferedReader(fr);
//        String currentLine = br.readLine();
//        int count = 0;
//        List<String> ignoreWord = new ArrayList<>(Arrays.asList(stopWordsArray));
//        ignoreWord.add("");
////        for(int i = 0; i < ignoreWord.size(); i++) {
////            System.out.println(ignoreWord.get(i));
////        }
////        System.out.println("=============");
//
//        while (currentLine != null) {
////            currentLine.toLowerCase();
////            System.out.println(currentLine);
//            String[] temp = currentLine.toLowerCase().split("[ \t,;.?!-:@(){}_*/\\[\\]]+");
//            for (int n = 0; n < temp.length; n++) {
////                System.out.println(temp[n]);
//                if (ignoreWord.contains(temp[n])) {
//                    continue;
//                }
//                if (wordCount.containsKey(temp[n]) ) {
//                    wordCount.put(temp[n], wordCount.get(temp[n]) + 1);
//                    continue;
//                } else {
//                    wordCount.put(temp[n], 1);
//                }
//            }
//            currentLine = br.readLine();
//            count++;
//        }
//        br.close();
//        fr.close();
////        for (String i : wordCount.keySet()) {
////            System.out.println(i + " " + wordCount.get(i));
////        }
//        List<Map.Entry<String, Integer>> wordList = new ArrayList<Map.Entry<String, Integer>>(wordCount.entrySet());
//        wordList.sort(new Comparator<Map.Entry<String, Integer>>() {
//            @Override
//            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
//                return o2.getValue().compareTo(o1.getValue());
//            }
//        });
//        for (int i = 0; i < wordList.size(); i++) {
//            System.out.println(wordList.get(i).getKey() + ": " + wordList.get(i).getValue());
//        }
//        return wordList;
//    }

    public static void main(String args[]) throws Exception {
//        readFile();

    	if (args.length < 1){
    		System.out.println("missing the argument");
    	}
    	else{
    		String userName = args[0];
	    	MP1 mp = new MP1(userName);
	    	String[] topItems = mp.process();

	        for (String item: topItems){
	            System.out.println(item);
	        }
	    }
	}
}
