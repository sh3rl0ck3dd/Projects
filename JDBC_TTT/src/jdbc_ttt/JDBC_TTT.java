/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc_ttt;


import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class JDBC_TTT extends Applet
implements MouseListener, ActionListener, WindowListener
{
    JFrame f;
    JTextField tf1,tf2;
    int flag = 2;
    int n,m,i = 0;
    static int bug = 0;
    char[] ch = new char[9];
    JButton button1,button2;
    
    String s1 = "";
    String s2="player1",s3="player2";
      
    JButton b1;  
          
     
    public JDBC_TTT()
    {  
        f = new JFrame("Tic Tac Toe");
        button1 = new JButton("CLEAR");
        button2 = new JButton("EXIT");
        tf1=new JTextField("player1");  
        tf1.setBounds(600,50,160,20);  
        tf2=new JTextField("player2");  
        tf2.setBounds(600,100,160,20);  
        b1=new JButton("change ");  
        b1.setBounds(640,160,90,35);
        f.add(tf1);
        f.add(tf2);
        f.add(b1);
        f.add(button1);
        f.add(button2);
        f.addWindowListener(this);
        f.getContentPane().setBackground(Color.RED);
        f.setLayout(null);
        f.setVisible(true);
        f.setSize(800, 600);
        button1.setBounds(650, 350, 90, 60);
        button2.setBounds(650, 450, 90, 60);
        
        f.addMouseListener(this);
        for (i = 0; i < 9; i += 1){
        ch[i] = 'B';
        }
        button1.addActionListener(this);
        button2.addActionListener(this);
        b1.addActionListener(this);
        String message = "Please click on the frame   !!!!! \n    \nto start the game \n";
        
        JOptionPane pane = new JOptionPane(message);
        JDialog dialog = pane.createDialog(new JFrame(), "Dilaog");
        dialog.setSize(200,200);
        dialog.show();
        Graphics g = f.getGraphics();
        g.drawLine(200, 0, 200, 600);
        g.drawLine(400, 0, 400, 600);
        g.drawLine(0, 200, 600, 200);
        g.drawLine(0, 400, 600, 400);
        g.drawLine(600, 0, 600, 600);
    }
    
    public void keyPressed(KeyEvent k)
    {
        System.out.print("");
    }
    
    public void keyTyped(KeyEvent k) {
        s1 += k.getKeyChar();
    }
    
    public void keyReleased(KeyEvent k) {
        System.out.print("");
    }
    
    public void actionPerformed(ActionEvent ae)
    { if(ae.getSource()==b1){
       s2=tf1.getText();  
       s3=tf2.getText();  
    }
         if (ae.getSource() == button1)
        {
            f.setVisible(false);
            bug = 0;
            new JDBC_TTT();
        }
        if (ae.getSource() == button2)
        {
            System.exit(0);
        }
    }
    
    public void windowClosing(WindowEvent de)
    {
    System.exit(0); }
    
    public void windowOpened(WindowEvent de) { }
    
    public void windowClosed(WindowEvent de) { }
    
    public void windowActivated(WindowEvent de) { }
    
    public void windowDeactivated(WindowEvent de) { }
    
    public void windowIconified(WindowEvent de) { }
    
    public void windowDeiconified(WindowEvent de) {  }
    
    public void mouseClicked(MouseEvent e) { Graphics2D g2;
        Graphics g = f.getGraphics();
        g.drawLine(200, 0, 200, 600);
        g.drawLine(400, 0, 400, 600);
        g.drawLine(0, 200, 600, 200);
        g.drawLine(0, 400, 600, 400);
        g.drawLine(600, 0, 600, 600);
        flag -= 1;
        int x = e.getX();
        int y = e.getY();
        if (flag == 1)
        {
            if ((x < 200) && (y < 200)) { m = 0; n = 0; ch[0] = 'R'; }
            if ((x > 200) && (x < 400) && (y < 200)) { m = 200; n = 0; ch[1] = 'R'; }
            if ((x > 400) && (x < 600) && (y < 200)) { m = 400; n = 0; ch[2] = 'R'; }
            if ((x < 200) && (y > 200) && (y < 400)) { m = 0; n = 200; ch[3] = 'R'; }
            if ((x > 200) && (x < 400) && (y > 200) && (y < 400)) { m = 200; n = 200; ch[4] = 'R'; }
            if ((x > 400) && (x < 600) && (y > 200) && (y < 400)) { m = 400; n = 200; ch[5] = 'R'; }
            if ((x < 200) && (y > 400) && (y < 600)) { m = 0; n = 400; ch[6] = 'R'; }
            if ((x > 200) && (x < 400) && (y > 400) && (y < 600)) { m = 200; n = 400; ch[7] = 'R'; }
            if ((x > 400) && (x < 600) && (y > 400) && (y < 600)) { m = 400; n = 400; ch[8] = 'R'; }
            g.setColor(new Color(177, 176, 230));
            g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(10.0F));
            g.drawOval(m + 10, n + 10, 159, 159);
        }
        
        if (flag == 0)
        {
            if ((x < 200) && (y < 200)) { m = 0; n = 20; ch[0] = 'P'; }
            if ((x > 200) && (x < 400) && (y < 200)) { m = 200; n = 20; ch[1] = 'P'; }
            if ((x > 400) && (x < 600) && (y < 200)) { m = 400; n = 20; ch[2] = 'P'; }
            if ((x < 200) && (y > 200) && (y < 400)) { m = 0; n = 200; ch[3] = 'P'; }
            if ((x > 200) && (x < 400) && (y > 200) && (y < 400)) { m = 200; n = 200; ch[4] = 'P'; }
            if ((x > 400) && (x < 600) && (y > 200) && (y < 400)) { m = 400; n = 200; ch[5] = 'P'; }
            if ((x < 200) && (y > 400) && (y < 600)) { m = 0; n = 400; ch[6] = 'P'; }
            if ((x > 200) && (x < 400) && (y > 400) && (y < 600)) { m = 200; n = 400; ch[7] = 'P'; }
            if ((x > 400) && (x < 600) && (y > 400) && (y < 600)) { m = 400; n = 400; ch[8] = 'P'; }
            g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(10.0F));
            g.setColor(new Color(77, 176, 230));
            g.drawLine(m + 10, n + 13, m + 169, n + 164);
            g.drawLine(m + 169, n + 10, m + 10, n + 169);
            flag += 2;
        }
        
        
        for (i = 0; i < 3; i += 1)
        {
            if ((ch[i] != 'B') &&
            (ch[(i + 3)] == ch[i]) && (ch[(i + 6)] == ch[i]))
            {if(flag==0){
            String temp =s2;
            s2=s3;
            s3=temp;
        }
                new Board().win(s2,s3);
                bug = 1;
                         
            }
        }
        
        for (i = 0; i < 7; i += 1)
        {
            if (ch[i] != 'B')
            {
                if ((ch[i] == ch[(i + 1)]) && (ch[i] == ch[(i + 2)]))
                {if(flag==0){
            String temp =s2;
            s2=s3;
            s3=temp;
        }
                    new Board().win(s2,s3);
                    bug = 1;
                 
                }
                i += 2;
            }
            else {
                i += 2;
            }
        }
        if ((ch[4] != 'B') && ((
        ((ch[0] == ch[4]) && (ch[4] == ch[8])) || ((ch[2] == ch[4]) && (ch[4] == ch[6])))))
        {if(flag==0){
            String temp =s2;
            s2=s3;
            s3=temp;
        }
            new Board().win(s2,s3);
            bug = 1;
        }
        
        for (i = 0; (i < 9) &&
        (ch[i] != 'B'); i += 1)
        {
            if (i == 8)
            {
                if (bug == 0)
                new Board().draw(s2,s3);
                bug = 0;
            }
        }
    }
    
    public void mouseReleased(MouseEvent e)
    {
        System.out.print("");
    }
    
    public void mouseEntered(MouseEvent e)
    {
        System.out.print("");
    }
    
    public void mouseExited(MouseEvent e) {
        System.out.print("");
    }
    
    public void mousePressed(MouseEvent e) {
        System.out.print("");
    }
    
    public static void main(String[] args)
    {
         JDBC_TTT t1=new JDBC_TTT();
        

         
    }
}




