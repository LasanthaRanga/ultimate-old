package modle.asses;

import com.jfoenix.controls.JFXCheckBox;

public class NatureHolder {

    int id;
    String nature;
    JFXCheckBox checkBox;

    public int getId() {
        return id;
    }

    public String getNature() {
        return nature;
    }

    public JFXCheckBox getCheckBox() {
        return checkBox;
    }

    public NatureHolder(int id, String nature) {
        this.id = id;
        this.nature = nature;
        this.checkBox = new JFXCheckBox();
        this.checkBox.setSelected(true);
    }

}
