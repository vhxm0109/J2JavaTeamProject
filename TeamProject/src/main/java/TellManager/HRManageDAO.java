package TellManager;//데이터베이스와의 연결을 위해 사용하는 인터페이스 -> 데이터 베이스와 통신 및 SQL 문을 실행
import java.sql.Connection;
//JDBC 드라이버를 로드하고 데이터베이스와의 연결을 설정하는 데 사용
import java.sql.DriverManager;
// 매개변수를 받아 SQL을 실행할때 사용
import java.sql.PreparedStatement;
// SQL 쿼리의 결과를 처리할 때 사용 -> 결과 행을 반복 하며 데이터를 읽기 가능
import java.sql.ResultSet;
// SQLException 데이터베이스 접근 오류나 다른 SQL 관련 오류가 발생했을 때 사용
import java.sql.SQLException;
//List는 요소의 순서를 유지하며 중복을 허용하는 컬렉션
import java.util.List;
//동적 배열을 제공하는 클래스 -> ArrayList는 크기가 가변적인 리스트를 구현 (List의 하위 컬랙션)
// -> 요소를 추가, 삭제, 수정할 가능
import java.util.ArrayList;


/*
* HRManageDAO 클래스
* 1. 목적: DB를 통해 입력받은 데이터를 CRUD기능을 할수있도록 한다.
*
* 2. 아키텍처:
*    2-1: 하나의 트랜잭션을 구현하기 위한 싱글톤 패턴을 구조화.
*    2-2: CRUD 기능 구현
*
* 3. CURD 기능 메소드 : ※아래 메소드 코드 상세 정보 참고
*   3-1: 메소드 insertHRInfo           (Input: 인사정보 DTO객체):                CREATE 역활
*   3-2: 메소드 updateHRInfo           (Input: 인사정보 DTO객체):                UPDATE 역활
*   3-3: 메소드 deleteHRInfo           (Input: 인사정보 DTO객체):                Delete 역활
*   3-4: 메소드 selectNameHRInfo       (Input: 인사정보 name 변수):              역활: 이름에 맞는 값 Row 검색
*   3-5: 메소드 selectStatusHRInfoList (Input: 인사정보 status 변수값):           진행상태(status)에 값에 일치한 Select 역활
*   3-6: 메소드 selectAllHRInfoList    (Input:X(없음)):                         HR DB에 있는 모든 값 Select
*
* */
public class HRManageDAO {
    String id = "root";
    String password = "1234";
    String sql;
    private static Connection conn;
    private static PreparedStatement pstmt;

    private static HRManageDAO _dao;


    /*
    2-1 싱글톤 패턴 구현 : CRUD 기능은 하나의 객체만 있어도 된다.
     */

    //프라이빗 생성자: private HRManageDAO()는 외부에서 이 클래스의 인스턴스를 생성하지 못하도록 한다.
    private HRManageDAO() {
    }

    //정적 인스턴스 변수: private static HRManageDAO _dao는 HRManageDAO 클래스의 단일 인스턴스를 저장
    static {
        _dao = new HRManageDAO();
    }

    //공용 정적 메소드: public static HRManageDAO getDAO()는 이 인스턴스에 접근할 수 있는 유일한 방법을 제공
    public static HRManageDAO getDAO() {
        return _dao;
    }
    /*
    3-1: 메소드 insertHRInfo (Input: 인사정보 DTO객체): CREATE 역활
    기능설명: 지원자의 이름, 전화번호, 주소, 상태, 메모의 값이 입력을 받으면 해당값을 SQL 쿼리를 통하여 DB에 하나의 Row 생성
    상세설명:
        1.DB Connection
        2.SQL 쿼리 입력
            2-1: DAO의 클래스 get 메소드를 접근하여 입력
        3.쿼리를 DB에 업데이트: PreparedStatement -> psmt 객체의 executeUpdate() 메소드를 사용
        4.업데이트 후 Row 반환
     */
    public int insertHRInfo(HRManageDTO hrInfo) {
        int rows = 0;
        try {
            // DB 연결
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HR?" +
                            "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    id, password);
            // SQL 쿼리문 생성 -> ※ ?의 의미는 파라미터를 의미한다. SQL 값을 대입할때 인덱스를 사용한다.
            sql = "INSERT INTO ApplyManage VALUES(?,?,?,?,?)";
            // SQL 쿼리 처리 객체에 Update SQl문이 들어있는 sql문을 입력한다.
            pstmt = conn.prepareStatement(sql);
            // SQL 쿼리 처리 객체에 값을 집어넣는다 -> (인덱스 1번에, DTO객체 Name[이름])
            pstmt.setString(1, hrInfo.getName());
            // SQL 쿼리 처리 객체에 값을 집어넣는다 -> (인덱스 2번에, DTO객체 Phone[핸드폰 번호])
            pstmt.setString(2, hrInfo.getPhone());
            // SQL 쿼리 처리 객체에 값을 집어넣는다 -> (인덱스 3번에, DTO객체 Adress[주소])
            pstmt.setString(3, hrInfo.getAddress());
            // SQL 쿼리 처리 객체에 값을 집어넣는다 -> (인덱스 4번에, DTO객체 Status[상태])
            pstmt.setString(4, hrInfo.getStatus());
            // SQL 쿼리 처리 객체에 값을 집어넣는다 -> (인덱스 5번에, DTO객체 Memo[상세 정보])
            pstmt.setString(5, hrInfo.getMemo());
            rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[에러] insertHRInfo() 메소드의 SQL 오류 = " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rows;
    }
    /*
        3-2: 메소드 updateHRInfo (Input: 인사정보 DTO객체): UPDATE 역활
        기능설명: 지원자의 이름, 전화번호, 주소, 상태, 메모의 값이 입력을 받으면
                해당값을 SQL 쿼리를 통하여 DB에 이름과, 핸드폰 정보와 일치한 값을 찾아 Row 업데이트 수행
        상세설명:
            1.DB Connection
            2.SQL 쿼리 입력
                2-1: DAO의 클래스 get 메소드를 접근하여 입력
            3.쿼리를 DB에 업데이트: PreparedStatement -> psmt 객체의 executeUpdate() 메소드를 사용
            4.업데이트 후 Row 반환
     */
    public int updateHRInfo(HRManageDTO hrInfo) {
        int rows = 0;
        try {
            // DB 연결
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HR?" +
                            "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    id, password);
            // SQL 쿼리문 생성 -> ※ ?의 의미는 파라미터를 의미한다. SQL 값을 대입할때 인덱스를 사용한다.
            sql = "UPDATE ApplyManage SET name=?, phone = ?, address=?, Status=?, memo=? WHERE name =?  and phone = ?";
            // SQL 쿼리 처리 객체에 Update SQl문이 들어있는 sql문을 입력한다.
            pstmt = conn.prepareStatement(sql);
            // SQL 쿼리 처리 객체에 값을 집어넣는다 -> (인덱스 1번에, DTO객체 Name[이름])
            pstmt.setString(1, hrInfo.getName());
            // SQL 쿼리 처리 객체에 값을 집어넣는다 -> (인덱스 2번에, DTO객체 Phone[핸드폰 번호])
            pstmt.setString(2, hrInfo.getPhone());
            // SQL 쿼리 처리 객체에 값을 집어넣는다 -> (인덱스 3번에, DTO객체 Address[주소])
            pstmt.setString(3, hrInfo.getAddress());
            // SQL 쿼리 처리 객체에 값을 집어넣는다 -> (인덱스 4번에, DTO객체 Status[진행상태])
            pstmt.setString(4, hrInfo.getStatus());
            // SQL 쿼리 처리 객체에 값을 집어넣는다 -> (인덱스 5번에, DTO객체 Memo[상세정보])
            pstmt.setString(5, hrInfo.getMemo());
            // SQL 쿼리 처리 객체에 값을 집어넣는다 -> (인덱스 6번에, DTO객체 Name[이름]: 검색 조건)
            pstmt.setString(6, hrInfo.getName());
            // SQL 쿼리 처리 객체에 값을 집어넣는다 -> (인덱스 7번에, DTO객체 Name[핸드폰 번호]: 검색 조건)
            pstmt.setString(7, hrInfo.getPhone());
            rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[에러] updateHRInfo() 메소드의 SQL 오류 = " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rows;
    }
    /*
        3-3: 메소드 deleteHRInfo (Input: 인사정보 DTO객체): Delete 역활
        기능설명: 지원자의 이름, 전화번호 입력을 받으면
                해당값을 SQL 쿼리를 통하여 DB에 이름과, 핸드폰 정보와 일치한 값을 찾아 Row 삭제 수행
        상세설명:
            1.DB Connection
            2.SQL 쿼리 입력
                2-1: DAO의 클래스 get 메소드를 접근하여 입력
            3.쿼리를 DB에 업데이트: PreparedStatement -> psmt 객체의 executeUpdate() 메소드를 사용
            4.업데이트 후 Row 반환
     */
    public int deleteHRInfo(HRManageDTO hrInfo) {
        int rows = 0;
        try {
            // DB 연결
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HR?" +
                            "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    id, password);
            // SQL 쿼리문 생성 -> ※ ?의 의미는 파라미터를 의미한다. SQL 값을 대입할때 인덱스를 사용한다.
            sql = "DELETE FROM ApplyManage WHERE name=? And phone=?";
            // SQL 쿼리 처리 객체에 Delete SQl문이 들어있는 sql문을 입력한다.
            pstmt = conn.prepareStatement(sql);
            // SQL 쿼리 처리 객체에 값을 집어넣는다 -> (인덱스 1번에, DTO객체 Name[이름])
            pstmt.setString(1, hrInfo.getName());
            // SQL 쿼리 처리 객체에 값을 집어넣는다 -> (인덱스 1번에, DTO객체 Phone[핸드폰])
            pstmt.setString(2, hrInfo.getPhone());
            rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[에러] deleteHRInfo() 메소드의 SQL 오류 = " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rows;
    }

    /*
        3-4: 메소드 selectNameHRInfo (Input: 인사정보 이름 변수): 역활: 이름에 맞는 값 Row 검색
        기능설명: 지원자의 이름을 입력 받으면
                해당값을 SQL 쿼리를 통하여 DB에 이름과 일치한 모든 Row값을 찾는 명령 수행
        상세설명:
            1.DB Connection
            2.SQL 쿼리 입력
                2-1: DAO의 클래스 get 메소드를 접근하여 입력
            3.쿼리를 DB에 업데이트: PreparedStatement -> psmt 객체의 executeUpdate() 메소드를 사용
            4.업데이트 후 List 반환
     */
    public List<HRManageDTO> selectNameHRInfo(HRManageDTO hrInfo) {
        // Select SQL 쿼리문을 담기위한 리스트 객체 생성
        List<HRManageDTO> hrInfoList = new ArrayList<>();
        try {
            // DB 연결
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HR?" +
                            "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    id, password);
            // SQL 쿼리문 생성 -> ※ ?의 의미는 파라미터를 의미한다. SQL 값을 대입할때 인덱스를 사용한다.
            sql = "SELECT * FROM ApplyManage WHERE name=?";
            // SQL 쿼리 처리 객체에 이름과 일치한 Select SQl문이 들어있는 sql문을 입력한다.
            pstmt = conn.prepareStatement(sql);
            // SQL 쿼리 처리 객체에 값을 집어넣는다 -> (인덱스 1번에, DTO객체 이름[이름])
            pstmt.setString(1, hrInfo.getName());
            // rs 객체 생성 -> SQL select 쿼리문을 처리한 값에 대한 객체
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Row 생성을 위한 DTO 객체 hrInfo 생성.
                HRManageDTO hrInfo1 = new HRManageDTO();
                // hrInfo에 DTO의 name 값 GET
                hrInfo1.setName(rs.getString("name"));
                // hrInfo에 DTO의 Phone 값 GET
                hrInfo1.setPhone(rs.getString("phone"));
                // hrInfo에 DTO의 address 값 GET
                hrInfo1.setAddress(rs.getString("address"));
                // hrInfo에 DTO의 Status 값 GET
                hrInfo1.setStatus(rs.getString("Status"));
                // hrInfo에 DTO의 Meno 값 GET
                hrInfo1.setMemo(rs.getString("memo"));
                // List에 현재 hrInfo 객체값 Insert
                hrInfoList.add(hrInfo1);
            }
        } catch (SQLException e) {
            System.out.println("[에러] selectNameHRInfo() 메소드의 SQL 오류 = " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return hrInfoList;
    }

    /*
        3-5: 메소드 selectStatusHRInfoList (Input: status 변수값): 진행상태(status)에 값에 일치한 Select 역활
        기능설명: 지원자의 진행상태 (Status 값) 입력을 받으면
                해당값을 SQL 쿼리를 통하여 DB에 Status 값에 일치한 모든 Row 값을 찾아 Select 수행
        상세설명:
            1.DB Connection
            2.SQL 쿼리 입력
                2-1: DAO의 클래스 get 메소드를 접근하여 입력
            3.쿼리를 DB에 업데이트: PreparedStatement -> psmt 객체의 executeUpdate() 메소드를 사용
            4.업데이트 후 List 반환
     */
    public List<HRManageDTO> selectStatusHRInfoList(HRManageDTO hrInfo) {
        // Select SQL 쿼리문을 담기위한 리스트 객체 생성
        List<HRManageDTO> hrInfoList = new ArrayList<>();
        try {
            // DB 연결
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HR?" +
                            "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    id, password);
            // SQL 쿼리문 생성 -> ※ ?의 의미는 파라미터를 의미한다. SQL 값을 대입할때 인덱스를 사용한다.
            sql = "SELECT * FROM ApplyManage WHERE Status=?";
            // SQL 쿼리 처리 객체에 이름과 일치한 Select SQl문이 들어있는 sql문을 입력한다.
            pstmt = conn.prepareStatement(sql);
            // SQL 쿼리 처리 객체에 이름과 일치한 Select SQl문이 들어있는 sql문을 입력한다.
            pstmt.setString(1, hrInfo.getStatus());
            // rs 객체 생성 -> SQL select 쿼리문을 처리한 값에 대한 객체
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Row 생성을 위한 DTO 객체 hrInfo 생성.
                HRManageDTO hrInfo2 = new HRManageDTO();
                // hrInfo에 DTO의 name 값 GET
                hrInfo2.setName(rs.getString("name"));
                // hrInfo에 DTO의 phone 값 GET
                hrInfo2.setPhone(rs.getString("phone"));
                // hrInfo에 DTO의 address 값 GET
                hrInfo2.setAddress(rs.getString("address"));
                // hrInfo에 DTO의 Status 값 GET
                hrInfo2.setStatus(rs.getString("Status"));
                // hrInfo에 DTO의 memo 값 GET
                hrInfo2.setMemo(rs.getString("memo"));
                // List에 현재 hrInfo 객체값 Insert
                hrInfoList.add(hrInfo2);
            }
        } catch (Exception e) {
            System.out.println("[에러] selectStatusHRInfoList() 메소드의 SQL 오류 = " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return hrInfoList;
    }
    /*
        3-6: 메소드 selectAllHRInfoList (Input:X(없음)): HR DB에 있는 모든 값 Select
        기능설명: 모든 지원자값 선택

        상세설명:
            1.DB Connection
            2.SQL 쿼리 입력
                2-1: DAO의 클래스 get 메소드를 접근하여 입력
            3.쿼리를 DB에 업데이트: PreparedStatement -> psmt 객체의 executeUpdate() 메소드를 사용
            4.업데이트 후 List 반환
     */
    public List<HRManageDTO> selectAllHRInfoList() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = null;
        // Select SQL 쿼리문을 담기위한 리스트 객체 생성
        List<HRManageDTO> HRManagelist = new ArrayList<>();
        try {
            // DB 연결
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HR?"
                    + "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode="
                    + "false&serverTimezone=UTC", id, password);
            // SQL 쿼리문 생성 -> ※ ?의 의미는 파라미터를 의미한다. SQL 값을 대입할때 인덱스를 사용한다.
            sql = "select * from ApplyManage order by name";
            // SQL 쿼리 처리 객체에 모든 값을 출력하는 Select sql문을 입력한다.
            pstmt = conn.prepareStatement(sql);
            // rs 객체 생성 -> SQL select 쿼리문을 처리한 값에 대한 객체
            rs = pstmt.executeQuery();

            while (rs.next()) {
                // Row 생성을 위한 DTO 객체 hrInfo 생성.
                HRManageDTO HRManage = new HRManageDTO();
                // hrInfo에 DTO의 name 값 GET
                HRManage.setName(rs.getString("name"));
                // hrInfo에 DTO의 phone 값 GET
                HRManage.setPhone(rs.getString("phone"));
                // hrInfo에 DTO의 address 값 GET
                HRManage.setAddress(rs.getString("address"));
                // hrInfo에 DTO의 Status 값 GET
                HRManage.setStatus(rs.getString("Status"));
                // hrInfo에 DTO의 memo 값 GET
                HRManage.setMemo(rs.getString("memo"));
                // List에 현재 hrInfo 객체값 Insert
                HRManagelist.add(HRManage);
            }
        } catch (SQLException e) {
            System.out.println("[에러] =selectAllHRManageList() 메소드의 SQL 오류 = " + e.getMessage());
        }  finally {
            try {
                // 리소스 해제
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return HRManagelist;
    }
}
