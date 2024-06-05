package TellManager;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {

    private Image image;

    // 이미지 경로를 받아와 Image 객체를 초기화합니다
    public ImagePanel(String imagePath) {
        this.image = new ImageIcon(imagePath).getImage();
        this.setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
    }

    // 컴포넌트에 맞게 이미지를 그립니다
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }



    // GUI를 생성하고 이미지 패널을 프레임에 추가하여 표시합니다
    public static void main(String[] args) {

    }
}