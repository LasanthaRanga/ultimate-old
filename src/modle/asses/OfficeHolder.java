package modle.asses;

public class OfficeHolder {
    int idOffice;
    String officeName;

    public OfficeHolder(int idOffice, String officeName) {
        this.idOffice = idOffice;
        this.officeName = officeName;
    }

    public int getIdOffice() {
        return idOffice;
    }

    public String getOfficeName() {
        return officeName;
    }

    @Override
    public String toString() {
        return officeName;
    }
}
