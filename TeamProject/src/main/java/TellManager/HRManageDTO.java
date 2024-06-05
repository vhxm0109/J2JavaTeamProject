package TellManager;/*
테이블 스키마:
이름       널?       유형
-------- -------- ------------
NAME     NOT NULL VARCHAR(30) : 이름
PHONE    NOT NULL VARCHAR(20) : 전화번호 PRIMERY KEY
ADDRESS           VARCHAR(40) : 주소
STATE    NOT NULL VARCHAR(10) : 상태
MEMO              VARCHAR(80) : 메모
*/

/*
HRManageDTO 클래스:
목적:어떤 데이터를 가지고 해당 프로그램을 수행할건지에 대한 기준데이터를 정하고 이를 전달하는 역활을 한다.
1. 이름        : 지원자의 이름 정보
2  전화번호     : 지원자의 전화번호
3. 주소        : 지원자의 주소
4. 상태        : 지원자의 채용 진행상태 - 1차, 2차 ,3차, 합격
5. 메모        : 지원자의 특이사항 등 기록해야 되는 정보.
*/

public class HRManageDTO {
    private String name;     // 이름
    private String phone;    // 전화번호
    private String address;  // 주소
    private String status;   // 상태
    private String memo;     // 메모

    //HRManageDTO 기본 생성자 - 아무것도 input 값을 받지 않을때 사용
    public HRManageDTO() {
    }

    public HRManageDTO(String name, String phone, String address, String status, String memo) {
        //super(); 불필요 - 최상위 클래스가  HRManageDTO 이기 때문
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.memo = memo;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
