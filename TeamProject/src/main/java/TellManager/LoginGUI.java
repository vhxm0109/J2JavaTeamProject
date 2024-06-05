package TellManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginGUI extends JFrame implements ActionListener {

    private JTable table;

    private JPanel contentPane; // 콘텐츠 표시
    private JTextField textId; // 아이디를 입력받는 텍스트필드
    private JTextField textPW; // 패스워드를 입력받는 텍스트필드
    private JTextField textName; // 이름을 입력받는 텍스트필드
    private JTextField textPhone; // 전화번호를 입력받는 텍스트필드
    private JTextField textKey; // 키값을 입력받는 텍스트필드
    private JButton loginButton; // 버튼 생성 - 로그인
    private JButton findIdButton; // 버튼 생성 - 아이디 찾기
    private JButton findPWButton; // 버튼 생성 - 패스워드 찾기
    private JButton idCheckButton; // 아이디찾기 창 확인버튼
    private JButton pwCheckButton; // 패스워드찾기 창 확인버튼
    private JFrame loginSystem; // 로그인시스템 전체프레임

    LoginGUI() {
        loginSystem = new JFrame("로그인");
        loginSystem.setBounds(750, 350, 400, 400); // 창의 위치와 크기 설정
        loginSystem.setLayout(null); // 레이아웃 매니저 설정

        JLabel LoginId = new JLabel("ID : ");
        LoginId.setBounds(100, 25, 80, 30);
        textId = new JTextField();
        textId.setBounds(180, 25, 100, 30);

        JLabel LoginPw = new JLabel("Password");
        LoginPw.setBounds(100, 75, 80, 30);
        textPW = new JTextField();
        textPW = new JPasswordField(20);
        textPW.setBounds(180, 75, 100, 30);

        JLabel LoginKey = new JLabel("Key");
        LoginKey.setBounds(100, 125, 80, 30);
        textKey = new JTextField();
        textKey.setBounds(180, 125, 100, 30);

        loginButton = new JButton("로그인");
        loginButton.setBounds(150, 160, 80, 30);
        findIdButton = new JButton("아이디 찾기");
        findIdButton.setBounds(80, 200, 110, 30);
        findPWButton = new JButton("비밀번호 찾기");
        findPWButton.setBounds(200, 200, 110, 30);

        JLabel findKey = new JLabel("※Key 분실 시 인포데스크에 문의해주세요.※");

        findKey.setBounds(80, 327, 300, 30 );
        findKey.setForeground(Color.red);

        loginSystem.add(LoginId);
        loginSystem.add(textId);
        loginSystem.add(LoginPw);
        loginSystem.add(textPW);
        loginSystem.add(LoginKey);
        loginSystem.add(textKey);
        loginSystem.add(loginButton);
        loginSystem.add(findIdButton);
        loginSystem.add(findPWButton);
        loginSystem.add(findKey);

        loginSystem.setVisible(true); // JFrame을 표시하도록 설정
        loginSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 버튼 동작 설정

        ImagePanel imagePanel = new ImagePanel("C:\\Java\\TeamProject\\src\\main\\java\\TellManager\\images\\MGimage1.png");
        imagePanel.setBounds(50, 150, 500, 500); // 이미지 패널의 위치와 크기 설정

        loginSystem.add(imagePanel);
        loginSystem.setVisible(true);

        loginButton.addActionListener(this);
        findIdButton.addActionListener(this);
        findPWButton.addActionListener(this);
    }

    public void loginAction() {
        String id = textId.getText(); // 사용자가 입력한 아이디 텍스트 얻어옴
        String pw = textPW.getText(); // 사용자가 입력한 패스워드 텍스트 얻어옴
        String key = textKey.getText(); // 사용자가 입력한 키 텍스트 얻어옴

        LoginDTO loginDTO = new LoginDTO(id, pw, key); //입력한 id pw key 담을 DTO객체 생성

        //HRManageDAO.getDAO().insertHRInfo(hrInfo);
        if (id.equals("") || pw.equals("") || key.equals("")) {
            if (id.equals("")) {
                JOptionPane.showMessageDialog(this, "아이디를 입력해 주세요");
            }
            if (pw.equals("")) {
                JOptionPane.showMessageDialog(this, "비밀번호를 입력해 주세요");
            }
            if (key.equals("")) {
                JOptionPane.showMessageDialog(this, "고유 키를 입력해주세요");
            }
        } else {
            int rows = LoginDAO.getDAO().login(loginDTO);
            if (rows == 1) {
                JOptionPane.showMessageDialog(this, "로그인 되었습니다."); // 변경
                HRManageGUIApp.Startmain(new String[]{}); // 메인패널 실행
                loginSystem.dispose(); // 로그인되면 프레임창 종료
            } else if (rows == 0) {
                JOptionPane.showMessageDialog(this, "로그인에 실패하였습니다."); // 변경
            } else if (rows == -1) {
                JOptionPane.showMessageDialog(this, "아이디 또는 키가 일치하지 않습니다.");
            } else {
                JOptionPane.showMessageDialog(this, "[Erorr] login() 메소드의 SQL 오류");
            }
        }
    }

    private Image image;

    public void findIdAction() {

        loginSystem = new JFrame("아이디");
        loginSystem.setBounds(700, 300, 420, 200); // 창의 위치와 크기 설정
        loginSystem.setLayout(null); // 레이아웃 매니저 설정
        JLabel LoginName = new JLabel("이름을 입력하세요 : ");
        LoginName.setBounds(70, 25, 300, 30);
        textName = new JTextField();
        textName.setBounds(220, 25, 100, 30);
        JLabel LoginPhone = new JLabel("전화번호를 입력하세요 : ");
        LoginPhone.setBounds(70, 65, 300, 30);
        textPhone = new JTextField();
        textPhone.setBounds(220, 65, 100, 30);
        idCheckButton = new JButton("확인");
        idCheckButton.setBounds(160, 120, 80, 30);

        loginSystem.add(idCheckButton);
        loginSystem.add(LoginName);
        loginSystem.add(textName);
        loginSystem.add(LoginPhone);
        loginSystem.add(textPhone);

        loginSystem.setVisible(true); // JFrame을 표시하도록 설정

        idCheckButton.addActionListener(this); // 아이디 찾기 확인 클릭


    }

    public void findIdCheck() {

    }

    public void checkIdPW(String strIdpw) {
        String name = textName.getText();
        String phone = textPhone.getText();
        String id = textId.getText();
        // 입력 값이 ID 면 ID 찾기 , PW 면 PW 수행

        // 입력 값이 ID 면 ID 찾기
        if (strIdpw.equals("id")) {
            if (id.equals("")) {
                LoginDTO logindto = new LoginDTO(name, phone, "", "", "");
                if (name.equals("") || phone.equals("")) {
                    if (name.equals("")) {
                        JOptionPane.showMessageDialog(this, "이름을 입력해 주세요");
                    }
                    if (phone.equals("")) {
                        JOptionPane.showMessageDialog(this, "전화번호를 입력해 주세요");
                    }
                } else {
                    List<LoginDTO> loginDTOList = null;
                    loginDTOList = LoginDAO.getDAO().findId(logindto);
                    if (loginDTOList.size() == 1) {
                        System.out.println("아이디 찾기 패널 생성");
                        JOptionPane.showMessageDialog(this, "아이디는: " + loginDTOList.get(0).getlId());
                        loginSystem.dispose();

                    } else if (!(loginDTOList.size() == 1)) {
                        JOptionPane.showMessageDialog(this, "이름 혹은 전화번호가 일치하지 않습니다.");
                    }

                }
            }

        }
        // 입력 값이  PW 면 PW 수행
        else if (strIdpw.equals("pw")) {
            // 아이디가 공백이 아닐시 -> 비밀번호 찾기 로직 수행
            LoginDTO logindto = new LoginDTO(name, phone, id, "", "");
            if (name.equals("") || phone.equals("")) {
                if (name.equals("")) {
                    JOptionPane.showMessageDialog(this, "이름을 입력해 주세요");
                }
                if (phone.equals("")) {
                    JOptionPane.showMessageDialog(this, "전화번호를 입력해 주세요");
                }
                if (id.equals("")) {
                    JOptionPane.showMessageDialog(this, "아이디를 입력해 주세요");
                }
            } else {
                List<LoginDTO> loginDTOList = null;
                loginDTOList = LoginDAO.getDAO().findId(logindto);
                if (loginDTOList.size() == 1) {
                    System.out.println("패스워드 찾기 패널 생성");
                    JOptionPane.showMessageDialog(this, "패스워드는: " + loginDTOList.get(0).getlPw());
                    loginSystem.dispose();
                } else if (!(loginDTOList.size() == 1)) {
                    JOptionPane.showMessageDialog(this, "이름 혹은 전화번호가 일치하지 않습니다.");
                }

            }
        }


    }

    public void findPWAction() { // 비밀번호 찾기 창 생성

        loginSystem = new JFrame("비밀번호");
        loginSystem.setBounds(700, 300, 420, 250); // 창의 위치와 크기 설정
        loginSystem.setLayout(null); // 레이아웃 매니저 설정
        JLabel LoginName = new JLabel("이름을 입력하세요 : ");
        LoginName.setBounds(70, 25, 300, 30);
        textName = new JTextField();
        textName.setBounds(220, 25, 100, 30);
        JLabel LoginPhone = new JLabel("전화번호를 입력하세요 : ");
        LoginPhone.setBounds(70, 65, 300, 30);
        textPhone = new JTextField();
        textPhone.setBounds(220, 65, 100, 30);
        JLabel LoginID = new JLabel("아이디을 입력하세요 : ");
        LoginID.setBounds(70, 105, 300, 30);
        textId = new JTextField();
        textId.setBounds(220, 105, 100, 30);
        pwCheckButton = new JButton("확인");
        pwCheckButton.setBounds(160, 150, 80, 30);

        loginSystem.add(pwCheckButton);
        loginSystem.add(LoginName);
        loginSystem.add(textName);
        loginSystem.add(LoginPhone);
        loginSystem.add(textPhone);
        loginSystem.add(LoginID);
        loginSystem.add(textId);

        loginSystem.setVisible(true); // JFrame을 표시하도록 설정
        pwCheckButton.addActionListener(this); // 패스워드 찾기 확인 클릭


    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginGUI();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        Component c = (Component) ev.getSource();
        if (c == loginButton) {
            loginAction();
        } else if (c == findIdButton) {
            findIdAction();

        } else if (c == findPWButton) {
            System.out.println("비밀번호 찾기");
            findPWAction();
        } else if (c == pwCheckButton) {
            System.out.println("비밀번호 찾기 확인 버튼 클릭");
            checkIdPW("pw");
        } else if (c == idCheckButton) {
            System.out.println("이름값:" + textName.getText());
            System.out.println("전화번호값:" + textPhone.getText());

            checkIdPW("id");


        }


    }
}