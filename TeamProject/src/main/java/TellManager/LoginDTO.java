package TellManager;

/*

    로그인
    비밀번호

    관리자아이디찾기
        name과 phone 입력시 해당하는 id 출력
    관리자비밀번호찾기
        id와 name 입력 시 해당하는 비밀번호 출력 (비밀번호변경)

*/
public class LoginDTO {
    private String lName;     // 이름
    private String lPhone;    // 전화번호
    private String lId;  // 아이디
    private String lPw;   // 상태
    private String lKey;


    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getlPw() {
        return lPw;
    }

    public void setlPw(String lPw) {
        this.lPw = lPw;
    }

    public String getlId() {
        return lId;
    }

    public void setlId(String lId) {
        this.lId = lId;
    }

    public String getlPhone() {
        return lPhone;
    }

    public void setlPhone(String lPhone) {
        this.lPhone = lPhone;
    }

    public String getlKey() {
        return lKey;
    }

    public void setlKey(String lKey) {
        this.lKey = lKey;
    }

    public LoginDTO() {
    }

    public LoginDTO(String lId, String lPw, String lKey) {
        this.lId = lId;
        this.lPw = lPw;
        this.lKey = lKey;
    }

    public LoginDTO(String lName, String lPhone, String lId, String lPw, String lKey) {
        this.lName = lName;
        this.lPhone = lPhone;
        this.lId = lId;
        this.lPw = lPw;
        this.lKey = lKey;
    }
}


