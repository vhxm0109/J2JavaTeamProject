package TellManager;//AWT(Abstract Window Toolkit) 라이브러리의 모든 클래스 -> GUI 요소(버튼, 레이블, 패널 등)를 생성하고 배치 -벡터와 그리드 때문에 사용했음. 실제는 아스타쓰는것이 좋은것이 아닐수 있음.
import java.awt.*;
// 버튼 클릭과 같은 액션 이벤트 -> 버튼 클릭 시 발생하는 이벤트를 처리하는 데 사용
import java.awt.event.ActionEvent;
// 사용자의 액션 이벤트를 처리하는 데 사용 -> 버튼 클릭과 같은 이벤트에 대한 리스너를 구현하는 데 사용
import java.awt.event.ActionListener;
//File 클래스는 파일과 디렉토리 -> 파일 및 디렉토리의 경로를 관리하고 파일 시스템 작업(생성, 삭제 등)을 수행
import java.io.File;
//입출력 작업 중 발생하는 예외 -> 파일 입출력 작업에서 발생하는 오류를 처리
import java.io.IOException;
//요소의 순서를 유지하고 중복을 허용하는 List 컬렉션
import java.util.List;
//동기화된 동적 배열을 제공하는 클래스로, List 인터페이스를 구현 -> 데이터 목록 저장 및 스레드 안정 보장
import java.util.Vector;
//정규 표현식 -> 정규 표현식을 컴파일하고 매칭
import java.util.regex.Pattern;
//스윙 라이브러리의 버튼 컴포넌트 ->  GUI에서 버튼을 생성하고 관리하는 데 사용
import javax.swing.JButton;
//스윙 라이브러리의 최상위 창 -> 애플리케이션의 메인 윈도우를 생성
import javax.swing.JFrame;
//스윙 라이브러리의 텍스트 레이블 컴포넌트 -> GUI에서 텍스트 레이블을 표시
import javax.swing.JLabel;
// 다이얼로그 박스를 생성하는 데 사용 -> 사용자에게 메시지를 표시하거나 입력을 받는 대화 상자를 생성
import javax.swing.JOptionPane;
// 스윙 라이브러리의 패널 컴포넌트 -> GUI에서 다른 컴포넌트를 그룹화하는 컨테이너
import javax.swing.JPanel;
// 스크롤 가능한 뷰포트를 제공하는 컴포넌트 -> 큰 컴포넌트에 스크롤 기능을 추가하는 데 사용
import javax.swing.JScrollPane;
// 스윙 라이브러리의 테이블 컴포넌트 -> 데이터의 행과 열을 표시하는 테이블을 생성
import javax.swing.JTable;
// 스윙 라이브러리의 단일 행 텍스트 입력 필드 -> 사용자로부터 텍스트 입력을 받는 필드를 생성
import javax.swing.JTextField;
//빈 테두리를 사용 -> 컴포넌트의 경계에 여백을 추가하는 데 사용
import javax.swing.border.EmptyBorder;
// JTable의 데이터를 관리하는 기본 테이블 모델을 제공 -> 테이블의 데이터를 관리하고 조작하는 데 사용
import javax.swing.table.DefaultTableModel;
//PDF 문서 -> PDF 문서를 생성, 로드, 저장하는 데 사용
import org.apache.pdfbox.pdmodel.PDDocument;
//PDF 문서의 페이지 -> PDF 문서에 페이지를 추가하거나 관리
import org.apache.pdfbox.pdmodel.PDPage;
// PDF 페이지에 컨텐츠(텍스트, 그래픽 등)를 추가하는 데 사용 -> PDF 페이지에 내용을 그리거나 작성하는 데 사용
import org.apache.pdfbox.pdmodel.PDPageContentStream;
//PDFBox에서 다국어 글꼴을 지원하는 폰트 -> PDF 문서에 텍스트를 추가할 때 다양한 언어의 글꼴을 사용
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class HRManageGUIApp extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTable table; //데이터를 행과 열의 2차원 그리드 형태로 표시

    private JPanel contentPane; // 콘텐츠 표시
    private JTextField nameTF; //이름을 입력받는 텍스트 필드.
    private JTextField phoneTF; //전화번호를 입력받는 텍스트 필드.
    private JTextField addressTF; //주소를 입력받는 텍스트 필드.
    private JTextField statusTF; //상태를 입력받는 텍스트 필드.
    private JTextField memoTF; //메모를 입력받는 텍스트 필드
    private JTextField searchNTF; //이름으로 검색할 때 사용하는 텍스트 필드
    private JTextField searchSTF; //상태 검색할 때 사용하는 텍스트 필드.
    private JButton pdfButton; // PDF 파일을 생성하거나 관련 기능을 수행하는 버튼

    JButton createButton, updateButton, deleteButton, SearchButton;

    /**
     * Launch the application.
     */
    /*
    HRManageGUIApp: 인사관리 CRUD , PDF 다운로드 화면 - (사용자 엑션 이벤트 구현)
    =Main실행 -> HRManageGUIApp=
    2.HRManageGUIApp 화면 구현 - 이름, 상태 검색... 등 화면
    3.화면 출력 -> displayAllHRManage 기본값 집어넣음
        - actionPerformed 이벤트 구현
            - actionPerformed 이벤트 정의 [오버라이딩] - 사용자 입력에 따른 분기
                입력,출력,삭제,검색
    4.displayAllHRManage 분기 값에 의해 엑션 수행
        -createHRManage(); 정보 생성
        -deleteHRManage()  정보 삭제
        -updateHRManage(); 정보 업데이트
        -String name = searchNTF.getText(); // 사용자가 입력한 이름 텍스트 얻어옴
        -String status = searchSTF.getText(); // 사용자가 입력한 상태 텍스트 얻어옴
        -createPDF(); // PDF 다운로드
            - PDF 주요 메소드
              //PDF 생성
                //PDF 문서에 테이블의 한 행을 그리는 역할

     */

    public static void Startmain(String[] args) {
        //Swing 컴포넌트는 EDT에서만 안전하게 수행할수 있는데 이를 invokeLater가 처리해준다.
        //1. Deadlocks[교착상태] 방지 - 무한히 기다리는 스레드 방지
        //2. 스레드 안전성 확보
        //3. invokeLater를 사용한 GUI 응답성 향상
        EventQueue.invokeLater(new Runnable() { // new runnerable()이 왜 이런형태로 들어가냐? {
            public void run() {
                try {
                    HRManageGUIApp frame = new HRManageGUIApp();
                    frame.setVisible(true);//GUI 프레임을 표시 -> setVisible 메소드를 호출하여 GUI를 사용자에게 보여줍
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public HRManageGUIApp() {
        //전체 화면
        setResizable(false); //창 크기를 조정할 수 없도록 설정
        setTitle("인사 관리 프로그램"); // 창의 제목을 "인사 관리 프로그램"으로 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//사용자가 창을 닫을 때의 동작을 정의 -> JFrame.EXIT_ON_CLOSE의 사용은 창을 닫으면 프로그램이 종료되도록 한다
        setBounds(100, 100, 850, 450);//창의 위치와 크기를 설정
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));//테두리를 설정
        setContentPane(contentPane);//프레임 내에서 다른 GUI 컴포넌트들을 포함하는 기본 컨테이너
        contentPane.setLayout(null);//위치 레이아웃을 고정 사용

        // GUI 테이블 이름 생성 및 테이블 초기화
        String[] columnNames = {"이름", "전화번호", "주소", "상태", "메모"};
        //테이블에 표시할 데이터를 저장하고, 데이터의 추가, 삭제, 수정 등의 작업 클래스 설정
        DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);
        table = new JTable(tableModel);// JTable을 생성
        table.setShowGrid(false); //  테이블 셀 간의 경계선을 표시하지 않도록 설정

        // GUI 테이블 크기 생성
        JScrollPane scrollPane = new JScrollPane(table);// 테이블을 포함하는 스크롤 패널을 생성
        scrollPane.setBounds(250, 10, 580, 350); // 스크롤 패널의 위치  설정
        contentPane.add(scrollPane); // 스크롤 패널을 내용 패널에 추가

        // 패널 라벨 객체 생성
        JPanel panel = new JPanel();
        // 패널의 위치 생성
        panel.setBounds(10, 10, 230, 350);
        // 패널을 내용 패널에 추가
        contentPane.add(panel);
        // 패널의 레이아웃을 균등하게 배치하기 위해  사용자 값 입력 내용 패널을 7행 2열의 그리드 레이아웃으로 설정 (간격 5픽셀)
        // ※그리드 레이아웃: 수평선과 수직선으로 이루어진 2차원 레이아웃 시스템
        panel.setLayout(new GridLayout(7, 2, 5, 5));

        // J 라벨 생성후 패널 객체에 추가 [이름]
        JLabel nameLabel = new JLabel("이름");
        panel.add(nameLabel);

        nameTF = new JTextField();
        panel.add(nameTF);

        // J 라벨 생성후 패널 객체에 추가 [전화번호]
        JLabel phoneLabel = new JLabel("전화번호");
        panel.add(phoneLabel);

        phoneTF = new JTextField();
        panel.add(phoneTF);

        // J 라벨 생성후 패널 객체에 추가 [주소]
        JLabel addressLabel = new JLabel("주소");
        panel.add(addressLabel);

        addressTF = new JTextField();
        panel.add(addressTF);

        // J 라벨 생성후 패널 객체에 추가 [상태]
        JLabel distanceLabel = new JLabel("상태"); // 변경
        panel.add(distanceLabel);

        statusTF = new JTextField();
        panel.add(statusTF);


        // J 라벨 생성후 패널 객체에 추가 [메모]
        JLabel memoLabel = new JLabel("메모");
        panel.add(memoLabel);

        memoTF = new JTextField();
       // memoTF.setPreferredSize(new Dimension(150, 80)); // Adjust memo field size
        panel.add(memoTF);

        JLabel searchNTFLabel = new JLabel("이름 검색");
        panel.add(searchNTFLabel);

        searchNTF = new JTextField();
        panel.add(searchNTF);

        JLabel searchTTFLabel = new JLabel("상태 검색"); // 변경
        panel.add(searchTTFLabel); // 변경

        searchSTF = new JTextField(); // 변경
        panel.add(searchSTF); // 변경

        createButton = new JButton("정보 저장");
        createButton.setBounds(250, 370, 100, 30);
        contentPane.add(createButton);

        updateButton = new JButton("정보 변경");
        updateButton.setBounds(360, 370, 100, 30);
        contentPane.add(updateButton);

        deleteButton = new JButton("정보 삭제");
        deleteButton.setBounds(470, 370, 100, 30);
        contentPane.add(deleteButton);

        SearchButton = new JButton("직원 검색"); // 변경
        SearchButton.setBounds(580, 370, 100, 30); // 변경
        contentPane.add(SearchButton);

        pdfButton = new JButton("PDF 다운");
        pdfButton.setBounds(690, 370, 140, 30);
        contentPane.add(pdfButton);

        createButton.addActionListener(this); // 정보저장 버튼에 이벤트 추가
        updateButton.addActionListener(this); // 정보변경 버튼에 이벤트 추가
        deleteButton.addActionListener(this); // 정보삭제 버튼에 이벤트 추가
        SearchButton.addActionListener(this); // 직원검색 버튼에 이벤트 추가
        pdfButton.addActionListener(this);

        displayAllHRManage(0, null); // 변경
    }

    // DAO로부터 모든 인사 정보를 가져와 JTable에 표시하는 메소드
    public void displayAllHRManage(int mode, HRManageDTO hrDTO) { // 변경
        List<HRManageDTO> hrInfoList = null; // 변경
        if (mode == 0) {
           hrInfoList = HRManageDAO.getDAO().selectAllHRInfoList(); // 변경 - 모든값 출력
        } else if (mode == 1) {
            hrInfoList = HRManageDAO.getDAO().selectNameHRInfo(hrDTO); // 변경 - 이름에 맞는 정보 출력.
        } else if (mode == 2) {
            hrInfoList = HRManageDAO.getDAO().selectStatusHRInfoList(hrDTO); // 변경 - 정보를 검색했을때 그에 맞는 조건 검색
        }

        if (hrInfoList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "저장된 인사 정보가 없습니다."); // 변경
            return;
        }

        //테이블 초기화
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (int i = model.getRowCount(); i > 0; i--) {
            model.removeRow(0);
        }
        // JTable에 반환된 인사 정보를 출력 -> 다중환경 스레드
        /*
        프로그램을 동시에 두컴퓨터가 사용한다고 했을때 데이터 삽입 이상현상을 방지하기 위해
        다중스레드 환경에서 서로 동기화를 동시에 하지 못하도록 하나씩 처리하게 로직을 처리
         */
        for (HRManageDTO hrInfo : hrInfoList) { // 변경
            Vector<Object> rowData = new Vector<Object>();
            rowData.add(hrInfo.getName()); // 변경
            rowData.add(hrInfo.getPhone()); // 변경
            rowData.add(hrInfo.getAddress()); // 변경
            rowData.add(hrInfo.getStatus()); // 변경
            rowData.add(hrInfo.getMemo()); // 변경
            model.addRow(rowData);
        }
    }

    // JTextField에서 입력된 인사 정보를 받아와 DAO를 통해 저장하는 메소드 - '정보 저장'
    public void createHRManage() { // 변경
        String name = nameTF.getText();
        String phone = phoneTF.getText();
        String address = addressTF.getText();
        String status = statusTF.getText(); // 변경
        String memo = memoTF.getText();

        // 유효성 검사
        // ...

        // DTO 인스턴스 생성 및 입력값으로 필드 설정
        HRManageDTO hrInfo = new HRManageDTO(name, phone, address,
                status, memo); // 변경

        // DAO를 통해 정보 전달 및 저장
         // 변경
        /*if (name.equals("") || phone.equals("") || status.equals("")){
            JOptionPane.showMessageDialog(this, "이름, 전화번호, 상태를 반드시 입력해 주세요");
        }
        else{
            int rows = HRManageDAO.getDAO().insertHRInfo(hrInfo);
            JOptionPane.showMessageDialog(this, rows + "개의 인사 정보를 저장하였습니다."); // 변경

            displayAllHRManage(0, null); // 변경
        }
        */

        if (name.equals("") || phone.equals("") || status.equals("")){
            if(name.equals("")){
                JOptionPane.showMessageDialog(this, "이름 반드시 입력해 주세요");
            }
            if(phone.equals("")){
                JOptionPane.showMessageDialog(this, "전화번호 반드시 입력해 주세요");
            }
            if(status.equals("")){
                JOptionPane.showMessageDialog(this, "상태를 입력해주세요");
            }
        }
        else {
            int rows = HRManageDAO.getDAO().insertHRInfo(hrInfo);
            if(rows == 0){
                JOptionPane.showMessageDialog(this, "전화번호가 중복되었습니다."); // 변경
            }
            else {
                JOptionPane.showMessageDialog(this, rows + "개의 인사 정보를 저장하였습니다."); // 변경
                displayAllHRManage(0, null); // 변경
            }

        }
        /*String nameReg="[가-힣]{2,7}";
        if(!Pattern.matches(nameReg, name)) {
            JOptionPane.showMessageDialog(this, "이름은 2~5 범위의 한글만 입력해 주세요.");
            nameTF.requestFocus();
        }*/
    }

    // 이름과 전화번호를 제공받아 해당 직원 정보를 삭제하는 메소드 - '정보 삭제'
    public void deleteHRManage() { // 변경
        String name = nameTF.getText();
        String phone = phoneTF.getText();


        // DTO 인스턴스 생성 및 입력값으로 필드 설정
        HRManageDTO hrInfo = new HRManageDTO(name, phone, "", "", ""); // 변경

        // 이름과 전화번호를 무조건 입력해야 하므로 입력 여부를 먼저 확인
        if (name.equals("") || phone.equals("")) { // 이름 또는 전화번호 입력값 없는 경우
            JOptionPane.showMessageDialog(this, "이름과 전화번호 를 반드시 입력해 주세요");
            if (name.equals("")) {
                nameTF.requestFocus();
            }
            else {
                phoneTF.requestFocus();
            }
            return;
        }

        int rows = HRManageDAO.getDAO().deleteHRInfo(hrInfo); // 변경

        // Row 개수가 존재시 인사정보 삭제 , 없을시 인사정보 없다고 출력.
        if (rows > 0) {
            JOptionPane.showMessageDialog(this, rows + "개의 인사 정보를 삭제하였습니다."); // 변경
            displayAllHRManage(0, null); // 변경
        } else {
            JOptionPane.showMessageDialog(this, "삭제하고자 하는 이름의 인사 정보가 없습니다."); // 변경
        }

    }

    // 직원 정보를 제공받아 해당 직원 정보를 변경하는 메소드 - '정보 변경'
    public void updateHRManage() { // 변경
        String name = nameTF.getText();
        String phone = phoneTF.getText();
        String address = addressTF.getText();
        String status = statusTF.getText(); // 변경
        String memo = memoTF.getText();

        // DTO 인스턴스 생성 및 입력값으로 필드 설정
        HRManageDTO hrManagedto = new HRManageDTO(); // 변경

        // 이름과 전화번호를 무조건 입력해야 하므로 입력 여부를 먼저 확인
        if (name.equals("") || phone.equals("")) { // 이름 또는 전화번호 입력값 없는 경우
            JOptionPane.showMessageDialog(this, "이름과 전화번호를 반드시 입력해 주세요");
            if (name.equals("")) {
                nameTF.requestFocus();
            } else {
                phoneTF.requestFocus();
            }
            return;
        }

        String nameReg="[가-힣]{2,7}";//정규표현식
        if(!Pattern.matches(nameReg, name)) {
            JOptionPane.showMessageDialog(this, "이름은 2~5 범위의 한글만 입력해 주세요.");
            nameTF.requestFocus();

        }

        String phoneReg="\\d{2,3}-\\d{3,4}-\\d{4}";
        if(!Pattern.matches(phoneReg, phone)) {
            JOptionPane.showMessageDialog(this, "전화번호를 형식에 맞게 입력해 주세요.");
            phoneTF.requestFocus();
            return;
        }

        // 상태 조건 확인
        if (!status.equals("1차 합격") && !status.equals("2차 합격") && !status.equals("3차 합격") && !status.equals("합격")) {
            JOptionPane.showMessageDialog(this, "상태는 '1차 합격', '2차 합격', '3차 합격', '합격' 중 하나를 입력해 주세요");
            statusTF.requestFocus();
            return;
        }

        //컴퍼넌트의 입력값으로 필드값 변경 - Update Set 적용
        hrManagedto.setName(name);
        hrManagedto.setPhone(phone);
        hrManagedto.setAddress(address);
        hrManagedto.setStatus(status);
        hrManagedto.setMemo(memo);

        // DAO를 통해 정보 전달 및 변경
        int rows = HRManageDAO.getDAO().updateHRInfo(hrManagedto); // 변경
        if(rows == 0){
            JOptionPane.showMessageDialog(this, "인사정보가 없습니다."); // 변경
        }
        else {
            JOptionPane.showMessageDialog(this, rows + "명의 직원 정보를 변경하였습니다."); // 변경
        }
        displayAllHRManage(0, null); // 변경
    }


    @Override
    // 이벤트 클릭에 대한 처리 - 사용자 입력 반응에 따른 분기 로직 설정.
    //ActionEvent ev - 버튼 클릭 등의 액션 이벤트가 발생했을 때 호출
    public void actionPerformed(ActionEvent ev) {
        // 이벤트 클래스 객체 인스턴스화
        //ev.getSource() 는 Object 자료형 이를 Component로 형변환 -( 업캐스팅)
        //※ object(클래스)보다 Componet(추상클래스)가  상위
        Component c = (Component) ev.getSource();
        if (c == createButton) {
            createHRManage(); // 정보 저장
        } else if (c == deleteButton) {
            deleteHRManage(); // 정보 삭제
        } else if (c == updateButton) {
            updateHRManage(); // 정보 업데이트
        } else if (c == SearchButton) { // 정보 검색
            HRManageDTO hrDTO = new HRManageDTO(searchNTF.getText(),"","",searchSTF.getText(),""); // 사용자가 입력한 이름 텍스트 얻어옴

            if (!hrDTO.getName().equals("")) {
                searchNTF.setText("");
                displayAllHRManage(1, hrDTO); // 모드 1과 이름 전달

            } else if (!hrDTO.getStatus().equals("")) {
                searchSTF.setText("");
                displayAllHRManage(2, hrDTO); // 모드 2와 상태 전달

            } else {
                displayAllHRManage(0, null); // 모드 0 전달
            }
        }
        if (ev.getSource() == pdfButton) { // PDF 버튼 클릭 시 PDF 생성
            createPDF();
        }
    }

    private void createPDF() {
        try {
            // PDF 생성
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // 폰트 파일 로드
            String fontPath = "C:\\Nanum_Gothic\\NanumGothic-Regular.ttf"; // 폰트 파일 경로
            PDType0Font font = PDType0Font.load(document, new File(fontPath));

            // 페이지에 내용 추가
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(font, 16); // 제목 글자 크기 16으로 설정

            float margin = 50; // 좌우 여백
            float yStart = page.getMediaBox().getHeight() - margin; // 페이지 높이 - 상단 여백
            float y = yStart;

            // 제목 출력
            String title = "전체 지원자 현황";
            float titleWidth = font.getStringWidth(title) / 1000 * 16; // 제목의 너비
            float titleX = (page.getMediaBox().getWidth() - titleWidth) / 2; // 가운데 정렬을 위한 X 좌표
            contentStream.beginText(); // 택스트 출력을 위한 택스트 생성 메소드 사용
            contentStream.setFont(font, 16); // 폰트 지정.
            contentStream.newLineAtOffset(titleX, y);//제목의 시작 위치
            contentStream.showText(title); //설정한 위치에 제목을 출력
            contentStream.endText();//텍스트 블록을 종료
            y -= 30; // 제목 아래 여백

            // 테이블 헤더 입력
            contentStream.setFont(font, 10); // 헤더 글자 크기 10으로 설정
            float[] colWidths = {120, 120, 200, 60};
            String[] headers = {"이름", "전화번호", "주소", "상태"};
            y = drawTableRow(contentStream, headers, colWidths, y, font);
            y -= 20;

            // 테이블 데이터 출력
            //DefaultTableModel의 메서드들을 사용하기 위해 캐스팅
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            //tableModel.getRowCount() 메서드를 호출하여 테이블의 총 행(row) 수를 가저옴
            //tableModel.getRowCount() - 1까지 순회
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                Object[] rowData = new Object[headers.length];
                //headers 배열의 길이를 사용하여 열의 수를 가저옴
                for (int col = 0; col < headers.length; col++) {
                    //특정 행(row)과 열(col)의 데이터를 가져와 - rowData 배열의 해당 열 인덱스에 저장
                    rowData[col] = tableModel.getValueAt(row, col);
                }
                // 현재 행을 출력하고 다음 페이지로 넘어가는지 확인
                float rowHeight = 20; // 행 높이
                float requiredHeight = rowHeight * (rowData.length + 1); // 행 높이 * (데이터 행 + 헤더 행) : 전체 높이 설정.
                //다읔페이지를 넘어가는 조건
                if (y - requiredHeight < margin) { // 다음 페이지로 넘어감
                    //이전 페이지의 작업이 끝났으므로, 현재 사용 중인 contentStream을 닫고
                    //그후 이전 페이지의 내용을 완전히 저장하고 더 이상 수정할 수 없게 초기화
                    contentStream.close();
                    //새로운 페이지를 생성합니다.
                    page = new PDPage();
                    //document에 새로운 페이지를 추가-> 새로운 페이지에 내용을 추가
                    document.addPage(page);
                    //새 페이지에 대한 새로운 PDPageContentStream을 생성
                    contentStream = new PDPageContentStream(document, page);
                    contentStream.setFont(font, 10); // 글자 크기 10으로 설정
                    y = yStart;//새로운 페이지의 시작 Y 좌표를 설정
                    contentStream.beginText();//텍스트 블록 시작
                    contentStream.newLineAtOffset(margin, y);//텍스트를 출력할 위치를 설정 - margin은 여백을 나타내고, y는 현재 출력 위치
                    contentStream.showText("전체 지원자 현황 (계속)");
                    contentStream.endText();//텍스트 블록을 종료
                    y -= 30; // 위쪽으로 여백 생성
                    //headers 배열에 저장된 열 제목을 헤더 행으로 출력
                    y = drawTableRow(contentStream, headers, colWidths, y, font);
                    y -= 20; //헤더 아래에 여백을 추가
                }
                // 현재 행 출력
                y = drawTableRow(contentStream, rowData, colWidths, y, font);
            }

            contentStream.close();

            // PDF 저장
            String desktopPath = System.getProperty("user.home") + "/Desktop";
            File file = new File(desktopPath, "전체_지원자_현황.pdf");
            document.save(file);
            document.close();

            JOptionPane.showMessageDialog(this, "<html><div style='text-align: center;'>" +
                    "PDF 파일이 성공적으로 생성되었습니다." +
                    "<br>※지원자의 상세정보는 콘솔에서 직접 확인해 주세요</div></html>");
            System.out.println("저장된 경로: " + file.getAbsolutePath());

            // 데이터 미리보기 출력 - 콘솔
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                StringBuilder preview = new StringBuilder();
                for (int col = 0; col < headers.length; col++) {
                    preview.append(headers[col]).append(": ").append(tableModel.getValueAt(row, col)).append(" ");
                }
                System.out.println(preview.toString());
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "PDF 파일 생성 중 오류가 발생하였습니다.");
        }
    }

    //PDF 문서에 테이블의 한 행을 그리는 역할
    private float drawTableRow(PDPageContentStream contentStream, Object[] rowData, float[] colWidths, float y, PDType0Font font) throws IOException {
        float margin = 50; // 좌우 여백
        float x = margin; //현재 출력 위치를 표시하는 변수를 초기화

        // 텍스트 시작 위치 설정
        contentStream.beginText();//새로운 텍스트 객체를 시작
        contentStream.newLineAtOffset(x, y);//시작 위치를 (x, y)로 이동합니다. 여기서 y는 현재 출력 위치를 나타내는 변수

        // 각 열의 데이터를 출력
        for (int i = 0; i < rowData.length; i++) {
            String text = rowData[i].toString();    // 데이터를 문자열로 변환
            contentStream.showText(text);           // 현재 위치에 텍스트 출력
            x += colWidths[i];                      // 다음 열로 이동하기 위해 x 좌표 업데이트
            contentStream.newLineAtOffset(colWidths[i], 0);// 다음 열로 이동
        }

        contentStream.endText();    // 텍스트 출력 종료
        return y - 20; // 행 높이만큼 줄어듦
    }


}

