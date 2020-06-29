package modle.asses;

public class ApplicationHolder {


    int id;
    String refno;
    String date;
    String type;
    int typeid;
    String names;
    String descriptions;
    String user;
    int iduser;
    int status;
    int step;

    public ApplicationHolder(int id, String refno, String date, String type, int typeid, String names, String descriptions, String user, int iduser, int status, int step) {
        this.id = id;
        this.refno = refno;
        this.date = date;
        this.type = type;
        this.typeid = typeid;
        this.names = names;
        this.descriptions = descriptions;
        this.user = user;
        this.iduser = iduser;
        this.status = status;
        this.step = step;


    }

    public int getId() {
        return id;
    }

    public String getRefno() {
        return refno;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public int getTypeid() {
        return typeid;
    }

    public String getNames() {
        return names;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public String getUser() {
        return user;
    }

    public int getIduser() {
        return iduser;
    }

    public int getStatus() {
        return status;
    }

    public int getStep() {
        return step;
    }
}
