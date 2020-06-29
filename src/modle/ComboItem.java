package modle;

public class ComboItem {
    public int getId() {
        return id;
    }

    public String getNamee() {
        return namee;
    }

    int id;
    String namee;

    public ComboItem(int id, String namee) {
        this.id = id;
        this.namee = namee;
    }

    @Override
    public String toString() {
        return namee;
    }
}
