package modle.tradelicens;

/**
 * Created by Ranga on 2019-01-13.
 */
public class StaticBadu {

    private static int appId;
    private static int approvalcatid;

    public static int getAppId() {
        return appId;
    }

    public static void setAppId(int appId) {
        StaticBadu.appId = appId;
    }

    public static int getApprovalcatid() {
        return approvalcatid;
    }

    public static void setApprovalcatid(int approvalcatid) {
        StaticBadu.approvalcatid = approvalcatid;
    }
}
