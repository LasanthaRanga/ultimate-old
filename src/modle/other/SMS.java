package modle.other;

import modle.mix.GenarateRecipt;

import java.util.ArrayList;

public class SMS {

    public static void main(String[] args) {
        ArrayList<String> st = new ArrayList<>();// Wenas wenne nethi text
        st.add("Kurunegala Maha Nagara Sabawa. ");
        st.add("Oba Wisin Ayadumkara Ehti ");
        st.add("Sampurna Kara Etha ");
        st.add("Ankaya Idiripath kara Geweem Sidukaranna ");
        st.add("Mudalak Geviya yuthuwa etha ");
        st.add("Meyata Obe Nagaradhipathi ");
        st.add("Lasntha Ranga Kumara ");
        st.add("Sthuthi");
        //Me uda thiyana array eka  Table ekakin  load karanna hadanna
        //Tabal colom me widiyatha thiyenna
        // idSms , idApplication Category, step_number, type, message;


        ArrayList<String> dy= new ArrayList<>();// Mewa nitharama wenas wan ewa
        dy.add("");
        dy.add("BOP Application ");
        dy.add("NO. 5151 ");
        dy.add("Rs. 5000.00 ");
        // dynamic Array ekath meka system eken denna  mekata ona ona ewa system eken add karaganna puluwan wenna one
        // stap ekak thiyanawanam ewa
        // kage lagada den thiyenne kiyana ewa nam ehema ona ekak
        // me array ekata gelapenna thamai uda array ekata ona data tabal ekata danne


        String s = generateSmsText(dy, st);
        System.out.println(s);




    }

    public static String generateSmsText(ArrayList<String> dynamic, ArrayList<String> static_text) {
        String text = "";

        int dsize = dynamic.size();
        int size = static_text.size();

        int max = 0;

        if (dsize > size) {
            max = dsize;
        } else if (size > dsize) {
            max = size;
        } else {
            max = size;
        }

        for (int i = 0; i <= max; i++) {
            text += getDataFromArray(static_text, i);
            text += getDataFromArray(dynamic, i);
        }


        return text;
    }

    public static String getDataFromArray(ArrayList<String> array, int i) {
        if (array.size() > i) {
            return array.get(i);
        } else {
            return "";
        }
    }

}
