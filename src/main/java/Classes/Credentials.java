package Classes;

public class Credentials {

    public Credentials(int userId, String passwd) {
        this.userId = userId;
        this.passwd = passwd;
    }

    public Credentials() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    private int userId;
    private String passwd;

}
