package modle.account;

import conn.DB;
import javassist.bytecode.stackmap.BasicBlock;

public class Program {
    public boolean saveProgram(String english, String sinhala, int oder, int status) {
        boolean b = false;
        try {
            int i = DB.setData("INSERT INTO `program` ( `program_name_einglish`, `program_name_sinhala`, `program_oder`, `program_status`) VALUES ('" + english + "','" + sinhala + "', '" + oder + "', '" + status + "')");
            if(i>0){
                b = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }



}
