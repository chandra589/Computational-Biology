import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

public class KarpRabin{
    private String m_text;
    private BigInteger m_q;
    private BigInteger m_r;
    BigInteger[] prefix_fingerprints;
    KarpRabin(String text){
        m_text = text;
        //Integer dum = m_text.length();
        Integer prime = 23677;
        m_q = BigInteger.valueOf(prime);
        //int r = (int)Math.floor(Math.random()*(8807)); //q value
        int r = 257;
        m_r = BigInteger.valueOf(r);

        prefix_fingerprints = new BigInteger[m_text.length()+1];
        prefix_fingerprints[0] = BigInteger.ZERO;
    }
    public void fill_prefix_fingerprints(){
        BigInteger hashvalue = BigInteger.ZERO;

        for (int i = 1; i <= m_text.length(); i++){
            
            BigInteger dum2 = BigInteger.valueOf(m_text.charAt(i-1));
            hashvalue = hashvalue.multiply(m_r);
            hashvalue = hashvalue.add(dum2);
            hashvalue = hashvalue.mod(m_q);
            prefix_fingerprints[i] = hashvalue;

            //valid if you want to find for complete string, not for incremental strings. Only last element matches for both.
            // BigInteger dum1 = two.pow(len-i);
            // BigInteger temp = dum1.multiply(dum2);
            // t1 = t1.add(temp);
            // t1 = t1.mod(m_q);
            // test[i] = t1;
        }
    }

    public boolean Arestringssame(int st1, int st2, int len){
        int end1 = st1 + len - 1;
        int end2 = st2 + len - 1;

        BigInteger khash1 = prefix_fingerprints[end1];
        BigInteger jhash1 = prefix_fingerprints[st1-1];
        BigInteger temp1 = m_r.pow(len);
        BigInteger temp2 = temp1.multiply(jhash1);
        temp2 = temp2.mod(m_q);
        BigInteger hash1 = khash1.subtract(temp2).mod(m_q);

        BigInteger khash2 = prefix_fingerprints[end2];
        BigInteger jhash2 = prefix_fingerprints[st2-1];
        BigInteger temp = temp1.multiply(jhash2);
        temp = temp.mod(m_q);
        BigInteger hash2 = khash2.subtract(temp).mod(m_q);

        if (hash1.equals(hash2))
            return true;
        else
            return false;
    }


    public static void main(String[] args) throws FileNotFoundException, IOException {
        String file = "E:/SBU courses/Comp Biology/in.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line, k;
            line = br.readLine();
            k = br.readLine();
            int i = 0;
            boolean[] ret = new boolean[Integer.parseInt(k)];
            KarpRabin kr = new KarpRabin(line);
            String pattern;
            kr.fill_prefix_fingerprints();
            while((pattern = br.readLine()) != null && pattern.length() != 0){
                String[] splited = pattern.split("\\s+");
                boolean Issamestring = kr.Arestringssame(Integer.parseInt(splited[0]), Integer.parseInt(splited[1]), Integer.parseInt(splited[2]));
                ret[i++] = Issamestring;
            }
            br.close();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("E:/SBU courses/Comp Biology/out_test.txt"))) {
                String s1 = "YES";
                String s2 = "NO";
                for (i = 0; i < ret.length; i++){
                    if (ret[i])
                        bw.write(s1);
                    else
                        bw.write(s2);
                    
                    bw.newLine();
                }

                bw.close();
            }
        }
    }
}

