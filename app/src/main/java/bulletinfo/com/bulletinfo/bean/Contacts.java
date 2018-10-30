package bulletinfo.com.bulletinfo.bean;

public class Contacts {
    private String name;
    private String phone;
    private String Letters;
    public Contacts(){}
    public Contacts(String name,String phone){
        this.name = name;
        this.phone = phone;
    }

    public String getLetters() {
        return Letters;
    }

    public void setLetters(String letters) {
        Letters = letters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
