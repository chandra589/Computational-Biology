import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class edit_distance{

    static int ED(String s1, String s2){

        int[] dp = new int[s1.length() + 1];
        for (int i = 0; i <= s1.length(); i++){
            dp[i] = i;
        }

        int temp, pre_d;
        for (int i = 0; i < s2.length(); i++){
            pre_d = dp[0];
            dp[0] = dp[0]+1;
            for (int j = 0; j < s1.length(); j++){
                temp = dp[j+1];
                if (s1.charAt(i) == s2.charAt(j)){
                    dp[j+1] = pre_d;
                }
                else{
                    dp[j+1] = Math.min(dp[j], Math.min(dp[j+1], pre_d)) + 1;
                }
                pre_d = temp;
            }
            
        }

        return dp[dp.length - 1];
    }


    public static void main(String[] args) throws FileNotFoundException, IOException {
        String file = "in.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line, k;
            line = br.readLine();
            k = br.readLine();
            int i = 0;
            int[] ret = new int[Integer.parseInt(k)];
            String pattern;
            while((pattern = br.readLine()) != null && pattern.length() != 0){
                String[] splited = pattern.split("\\s+");
                int start1 = Integer.parseInt(splited[0]);
                int start2 = Integer.parseInt(splited[1]);
                int len =  Integer.parseInt(splited[2]);

                String s1 = line.substring(start1 - 1, start1 + len - 1);
                String s2 = line.substring(start2 - 1, start2 + len - 1);
                ret[i] = ED(s1, s2);
                i++;
            }
            br.close();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("out.txt"))) {
                for (i = 0; i < ret.length; i++){
                    bw.write(Integer.toString(ret[i]));
                    bw.newLine();
                }

                bw.close();
            }
        }
    }
}