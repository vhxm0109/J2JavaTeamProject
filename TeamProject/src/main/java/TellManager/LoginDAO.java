package TellManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/*
    로그인

    아이디찾기

    비밀번호 찾기
 */

public class LoginDAO {
    String id = "root";
    String password = "1234";
    String sql;
    private static Connection conn;
    private static PreparedStatement pstmt;
    private ResultSet rs;
    private static LoginDAO _dao;
    //프라이빗 생성자: private HRManageDAO()는 외부에서 이 클래스의 인스턴스를 생성하지 못하도록 한다.
    private LoginDAO() {
    }

    //정적 인스턴스 변수: private static HRManageDAO _dao는 HRManageDAO 클래스의 단일 인스턴스를 저장
    static {
        _dao = new LoginDAO();
    }

    //공용 정적 메소드: public static HRManageDAO getDAO()는 이 인스턴스에 접근할 수 있는 유일한 방법을 제공
    public static LoginDAO getDAO() {
        return _dao;
    }


    public int login(LoginDTO loginDTO) {

        boolean result;
        try {
            // DB 연동
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HR?" +
                            "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    id, password);
            // SQL 쿼리문 생성 -> ※ ?의 의미는 파라미터를 의미한다. SQL 값을 대입할때 인덱스를 사용한다.
            sql = "SELECT MGPassword FROM MG WHERE MGId = ? and MGKey = ?";
            // SQL 쿼리 처리 객체에 Update SQl문이 들어있는 sql문을 입력한다.
            pstmt = conn.prepareStatement(sql);
            // SQL 쿼리 처리 객체에 값을 집어넣는다 -> (인덱스 1번에, DTO객체 lId(아이디)
            pstmt.setString(1, loginDTO.getlId());
            // SQL 쿼리 처리 객체에 값을 집어넣는다 -> (인덱스 3번에, DTO객체 Adress[주소])
            pstmt.setString(2, loginDTO.getlKey());

            rs = pstmt.executeQuery();

            if (rs.next()){
                if (rs.getString(1).equals(loginDTO.getlPw())){
                    return 1; //로그인 성공
                }
                else {
                    return 0; //로그인 실패
                }
            }
            return -1; //아이디 없음
        } catch (SQLException e) {
            System.out.println("[에러] login() 메소드의 SQL 오류 = " + e.getMessage());
            return -2; //db오류
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public List<LoginDTO> findId(LoginDTO nameDTO) {
        List<LoginDTO> nameDTOList = new ArrayList<>();
        try {
            // DB 연동
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HR?" +
                            "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    id, password);
            // SQL 쿼리문 생성 -> ※ ?의 의미는 파라미터를 의미한다. SQL 값을 대입할때 인덱스를 사용한다.
            if(nameDTO.getlId().equals("")){
                // 아이디 찾기 수행시 SQL
                sql = "SELECT * FROM MG where MGName = ? and MGPhone = ?";
                // SQL 쿼리 처리 객체에 Update SQl문이 들어있는 sql문을 입력한다.
                pstmt = conn.prepareStatement(sql);
                // SQL 쿼리 처리 객체에 값을 집어넣는다 -> (인덱스 1번에, DTO객체 lId(아이디)
                pstmt.setString(1, nameDTO.getlName()); // sql 1번 인덱스
                pstmt.setString(2, nameDTO.getlPhone()); //    2번 인덱스
            }else{
                // 패스워드 찾기 수행시 SQL
                sql = "SELECT * FROM MG where MGName = ? and MGPhone = ? AND MGId = ?";
                // SQL 쿼리 처리 객체에 Update SQl문이 들어있는 sql문을 입력한다.
                pstmt = conn.prepareStatement(sql);
                // SQL 쿼리 처리 객체에 값을 집어넣는다 -> (인덱스 1번에, DTO객체 lId(아이디)
                System.out.println("----패스워드 찾기 정보-----");
                System.out.println("SQL 이름"+nameDTO.getlName());
                System.out.println("SQL 핸드폰"+nameDTO.getlPhone());
                System.out.println("SQL 아이디"+nameDTO.getlId());
                pstmt.setString(1, nameDTO.getlName());  // sql 1번 인덱스
                pstmt.setString(2, nameDTO.getlPhone()); //     2번 인덱스
                pstmt.setString(3, nameDTO.getlId());    //       3번 인덱스
            }

            rs = pstmt.executeQuery();

            if (rs.next()) {
                LoginDTO loginDTO1 = new LoginDTO();

                loginDTO1.setlId(rs.getString("MGId"));
                loginDTO1.setlName(rs.getString("MGName"));
                loginDTO1.setlPhone(rs.getString("MGPhone"));
                loginDTO1.setlPw(rs.getString("MGPassword"));
                nameDTOList.add(loginDTO1);
            }
        } catch (SQLException e) {
            System.out.println("[에러] login() 메소드의 SQL 오류 = " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return nameDTOList;
    }
    public List<LoginDTO> findPW(LoginDTO nameDTO) {
        List<LoginDTO> nameDTOList = new ArrayList<>();
        try {
            // DB 연동
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HR?" +
                            "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    id, password);
            // SQL 쿼리문 생성 -> ※ ?의 의미는 파라미터를 의미한다. SQL 값을 대입할때 인덱스를 사용한다.
            sql = "SELECT * FROM MG where MGName = ? and MGPhone = ? and MGId = ?";
            // SQL 쿼리 처리 객체에 Update SQl문이 들어있는 sql문을 입력한다.
            pstmt = conn.prepareStatement(sql);
            // SQL 쿼리 처리 객체에 값을 집어넣는다 -> (인덱스 1번에, DTO객체 lId(아이디)
            pstmt.setString(1, nameDTO.getlName()); // sql 1번물음표
            pstmt.setString(2, nameDTO.getlPhone()); // 2번 물음표
            pstmt.setString(3, nameDTO.getlId());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                LoginDTO loginDTO2 = new LoginDTO();

                loginDTO2.setlId(rs.getString("MGId"));
                loginDTO2.setlName(rs.getString("MGName"));
                loginDTO2.setlPhone(rs.getString("MGPhone"));
                loginDTO2.setlPhone(rs.getString("MGPassword"));
                nameDTOList.add(loginDTO2);
            }
        } catch (SQLException e) {
            System.out.println("[에러] login() 메소드의 SQL 오류 = " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return nameDTOList;
    }

    /*
    public static void main(String[] args) {
        LoginDAO loginDAO = new LoginDAO();
        System.out.println(loginDAO.rs);
    }
     */
}
