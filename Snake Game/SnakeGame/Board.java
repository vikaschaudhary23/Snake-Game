package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    private Image apple;
    private Image dot;
    private Image head;
    private int dots;
    private final int dot_size = 10;
    private final int MAX_SIZE = 900;
    private final int RANDOM_POSITION = 29;
    private int apple_x;
    private int apple_y;
    private Timer timer;
    private boolean inGame = true;

    private final int x[] = new int[MAX_SIZE];
    private final int y[] = new int[MAX_SIZE];


    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;


    Board(){
        addKeyListener(new Adpapter());

        setBackground(Color.black);
        setPreferredSize(new Dimension(300, 300));
        setFocusable(true);

        loadImages();
        initGame();

        locateApple();
        timer = new Timer(140, this);
        timer.start();
    }

    public void locateApple(){
        int r = (int)(Math.random() * RANDOM_POSITION);
        apple_x = r*dot_size;

        r = (int)(Math.random() * RANDOM_POSITION);
        apple_y = r*dot_size;
    }

    public void loadImages(){
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/apple.png"));
        apple = i1.getImage();
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("icons/head.png"));
        head = i3.getImage();
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("icons/dot.png"));
        dot = i2.getImage();
    }

    public void initGame(){
        dots = 3;
        for(int i=0;i<dots;i++){
            y[i] = 50;
            x[i] = 50- i*dot_size;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple, apple_x, apple_y, this);

            for(int i=0;i<dots;i++){
                if(i==0){
                    g.drawImage(head, x[i], y[i], this);
                }
                else{
                    g.drawImage(dot, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);
        }
    }

    public void gameOver(Graphics g){
        String msg = "GAME OVER!";
        Font font = new Font("SAN SARIF", Font.BOLD, 14);
        FontMetrics metrices = getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (300 - metrices.stringWidth(msg))/2, 300/2);
    }

    public void move(){
        for(int i=dots;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        if(leftDirection){
            x[0] -= dot_size;
        }
        if(rightDirection){
            x[0] += dot_size;
        }
        if(upDirection){
            y[0] -= dot_size;
        }
        if(downDirection){
            y[0] += dot_size;
        }
    }

    public void checkApple(){
        if(apple_x == x[0] && apple_y == y[0]){
            dots++;
            locateApple();
        }
    }

    public void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if (i>4 && x[0] == x[i] && y[0] == y[i]) inGame = false;

            if (y[0] >= 300 || y[0] < 0 || x[0] < 0 || x[0] >= 300) inGame = false;

            if(!inGame){
                timer.stop();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    public class Adpapter extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !rightDirection) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            else if(key == KeyEvent.VK_RIGHT && !leftDirection) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            else if(key == KeyEvent.VK_UP && !downDirection) {
                leftDirection = false;
                rightDirection = false;
                upDirection = true;
            }
            else if(key == KeyEvent.VK_DOWN && !upDirection) {
                leftDirection = false;
                rightDirection = false;
                downDirection = true;
            }
        }
    }
}









