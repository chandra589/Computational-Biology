import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class kmp {
    String m_text;
    String m_pattern;
    kmp(String text, String pattern){
        m_text = text;
        m_pattern = pattern;
    }

    public List<Integer> naivealgo(){
        List<Integer> ret = new ArrayList<>();
        int patt_len = m_pattern.length();
        int diff = m_text.length() - patt_len;
        int l = 0;
        for (int i = 1; i <= diff + 1; i++){
            l = 0;
            while (l < patt_len && (m_text.charAt(i-1+l) == m_pattern.charAt(l)))
                l++;
            if (l == patt_len)
                ret.add(i-1);
        }
        return ret;
    }

    public List<Integer> kmpalgo(){
        List<Integer> ret = new ArrayList<>();
        String compstring = m_pattern.concat("#").concat(m_text);
        int patt_len = m_pattern.length();
        int text_len = m_text.length();
        int[] border = new int[text_len + patt_len + 2];
        border[0] = 0; border[1] = 0;
        int b = 0;
        for (int i = 2; i <= text_len + patt_len + 1; i++){
            b = border[i-1];
            while (b > 0 && (compstring.charAt(b) != compstring.charAt(i-1)))
                b = border[b];
            if (compstring.charAt(b) == compstring.charAt(i-1))
                b = b + 1;
            border[i] = b;
            if (b == patt_len)
                ret.add(i - 2*patt_len - 1);
        }
        return ret;
    }


    public static String[] GenerateString(){
        int leftLimit = 48; // numeral '0'
        int rightLimit_alphabet = 90; // letter 'z'
        //changing the rightlimit randomly, this will generate strings from different alphabet sizes.
        int rightLimit = (int) (Math.random() * (rightLimit_alphabet - leftLimit)) + leftLimit + 1;
        int upperlimit_text = 300;
        int lowerlimit_text = 5;
        int text_StringLength = (int) (Math.random() * (upperlimit_text - lowerlimit_text)) + lowerlimit_text;
        int pattern_StringLength = (int) (Math.random() * (text_StringLength - 2)) + 3; //taking minimum length of pattern is 2
        Random random = new Random();

        String generatedString_text = random.ints(leftLimit, rightLimit + 1)
        .filter(i -> (i <= 57 || i >= 65))
        .limit(text_StringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();

        String generatedString_pattern = random.ints(leftLimit, rightLimit + 1)
        .filter(i -> (i <= 57 || i >= 65))
        .limit(pattern_StringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();

        String[] ret = new String[2];
        ret[0] = generatedString_text;
        ret[1] = generatedString_pattern;
        return ret;
    }


    public static void main(String[] args) {
        int testcases = (int) Math.pow(10, 6);
        for (int k = 0; k < testcases; k++){
            String[] texts = GenerateString();
            String text = texts[0];
            String pattern = texts[1];
            kmp test = new kmp(text, pattern);
            List<Integer> output_naive = test.naivealgo();
            List<Integer> output_kmp = test.kmpalgo();

            if (output_kmp.size() != output_naive.size()){
                System.out.println("The outputs from Naive algo and kmp algo differs\n");
                System.out.println(texts);
            }
            else{
                for (int i = 0; i < output_kmp.size(); i++){
                    int idx1 = output_kmp.get(i);
                    int idx2 = output_naive.get(i);
                    if (idx1 != idx2){
                        System.out.println("The outputs from Naive algo and kmp algo differs\n");
                        System.out.println(texts);
                        break;
                    }
                }
            }
        }
    }
}
