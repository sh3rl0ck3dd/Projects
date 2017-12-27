
package jdbc_ttt;



import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.*;
import java.io.FileWriter;
import java.io.IOException;


class Board  implements WindowListener
{
    String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    String DB_URL = "jdbc:mysql://localhost/ttt";
    String USER = "rajat";
    String PASS = "";
    Connection conn = null;
    Statement stmt = null;

    public void win(String n1,String n2)
    {
        
	   String message = "Congratulations !!!!! "+n1+" \n    \nYou win \n";
        
        JOptionPane pane = new JOptionPane(message);
        JDialog dialog = pane.createDialog(new JFrame(), "Dilaog");
        dialog.show();
          
          
          
           try{
      
          Class.forName("com.mysql.jdbc.Driver");      
          conn = DriverManager.getConnection(DB_URL, USER, PASS);
          stmt = conn.createStatement();
          String sql = "INSERT INTO TTT VALUES (now(),'"+n1+"','"+n2+"','"+n1+"')";
          stmt.executeUpdate(sql);
           }catch(SQLException se){
      se.printStackTrace();
   }catch(Exception e){
      e.printStackTrace();
   }finally{
      try{
         if(stmt!=null)
            conn.close();
      }catch(SQLException se){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }
   }      
	}
		
    
    
    public void draw(String n1,String n2)
    {   String message = "Players the result is  !!!!! \n    \nSTALEMATE \n";
        
        JOptionPane pane = new JOptionPane(message);
        JDialog dialog = pane.createDialog(new JFrame(), "Dilaog");
        dialog.show();
          
          try{
      
          Class.forName("com.mysql.jdbc.Driver");      
          conn = DriverManager.getConnection(DB_URL, USER, PASS);
          stmt = conn.createStatement();
          String sql = "INSERT INTO TTT VALUES (now(),'"+n1+"','"+n2+"','DRAW')";
          stmt.executeUpdate(sql);
           }catch(SQLException se){
      se.printStackTrace();
   }catch(Exception e){
      e.printStackTrace();
   }finally{
      try{
         if(stmt!=null)
            conn.close();
      }catch(SQLException se){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }
   }      
    }
    
    public void windowClosing(WindowEvent we)
    {
        System.exit(0);
    }
    
    public void windowOpened(WindowEvent we)
    {
    }
    
    public void windowClosed(WindowEvent we)
    {
    }
    
    public void windowActivated(WindowEvent we)
    {
    }
    
    public void windowDeactivated(WindowEvent we)
    {
    }
    
    public void windowIconified(WindowEvent we)
    {
    }
    
    public void windowDeiconified(WindowEvent we)
    {
    }
}