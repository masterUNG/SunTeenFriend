package masterung.androidthai.in.th.sunteenfriend.utility;

public class MyConstant {

    private String hostFTP = "ftp.androidthai.in.th";
    private String userFTP = "sun@androidthai.in.th";
    private String passwordFTP = "Abc12345";
    private int potrFTP = 21;
    private String urlImage = "http://androidthai.in.th/sun/MasterUNG/";
    private String urlAddUser = "http://androidthai.in.th/sun/addDataMaster.php";
    private String urlGetAllUser = "http://androidthai.in.th/sun/getAllUser.php";

    public String getUrlGetAllUser() {
        return urlGetAllUser;
    }

    public String getUrlAddUser() {
        return urlAddUser;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getHostFTP() {
        return hostFTP;
    }

    public String getUserFTP() {
        return userFTP;
    }

    public String getPasswordFTP() {
        return passwordFTP;
    }

    public int getPotrFTP() {
        return potrFTP;
    }
}
