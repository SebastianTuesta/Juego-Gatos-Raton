/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego_ratongatos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import org.armedbear.lisp.LispObject;

/**
 *
 * @author Sebastian
 */
public final class Board extends javax.swing.JFrame{

    /*
    *   Declaring variables
    */
    private final LISP_Functions rg;
    private final Label[][] label = new Label[8][8];
    private JLabel[] cats_label;
    private JLabel mouse_label;
    private final int player;
    private int initial_x;
    private int initial_y;
    private long begin = System.currentTimeMillis();
    private long end;
    
    public Board(LISP_Functions rg) {
        initComponents();
        
        //CENTER THE JFRAME WITH RELATION TO THE WINDOW
        setLocationRelativeTo(null);
        
        //ASIGN COLOR TO THE FRAME
        this.getContentPane().setBackground(Color.cyan.brighter());
        
        //ALLOWS THE PIECE OF THE CATS AND THE MOUSE TO BE ON THE BOARD ITSELF
        this.getContentPane().setLayout(null);
        
        this.rg=rg;
        
        //PLAYERS: CAT (1), MOUSE(2)
        player=rg.getPlayer();

        /*
        * PAINT THE BOARD ITSELF, THE CATS, THE MOUSE AND A LABEL THAT INDICATE
        * WHO IS THE PLAYER (CAT OR MOUSE) IN THE FRAME
        */
        paintFrame();        
    }
    
    public void paintFrame(){
        //System.out.println("I PASS HERE");
        
        //ERASE ALL THE ELEMENT IN THE FRAME
        this.getContentPane().removeAll();
        
        //PAINT THE BOARD ITSELF AND THE PIECES OF THE CATS AND MOUSE
        displayPiecesBoard();
        
        //ALLOW SEE THE PAINTING OF THE BOARD ITSELF AND THE PIECES
        this.getContentPane().repaint();
        
        //PAINT A LABEL THATINDICATE WHO IS THE PLAYER (CAT OR MOUSE)
        displayPlayer();
        
        /*ASIGN LISTENER TO THE PLAYER(S')('S) PIECE  AND THE BOARD`S GRID 
        *WHERE THIS PIECES CAN MOVE
        */
        asignListener();
    }
    
    public void displayPiecesBoard(){
        paintCats(rg.positionBoardCats());
        paintMouse(rg.positionBoardMouse());
        paintBoard();
    }
    
    public void asignListener(){
        
        final MouseListener m;
    
        /*
        * LISTENER FOR THE BOARD'S GRIDS 
        * WHERE THE PIECE THAT CHOICE THE PLAYER CAN MOVE
        *
        */
        m = new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) 
            { 
                end = System.currentTimeMillis();
                
                //ALLOWS THAT THIS EVENT ONLY PASS ONE TIME DURING A SHORT TIME
                if(end-begin>1000){
                    
                    //COORDINATE OF THE GRIDS' BOARD IN THE BOARD
                    int x=e.getComponent().getX()/80;
                    int y=e.getComponent().getY()/80;
                    
                    
                    //DETERMINATE WHAT MOVEMENT CHOICE THE PLAYER
                    if(x==initial_x-1 && y==initial_y-1){
                        rg.up_left(8*initial_y+initial_x);
                        rg.choose_pc_strategie();
                    }
                    else if(x==initial_x+1 && y==initial_y-1){
                        rg.up_right(8*initial_y+initial_x);
                        rg.choose_pc_strategie();
                    }
                    else if(x==initial_x-1 && y==initial_y+1){
                        rg.down_left(8*initial_y+initial_x); 
                        rg.choose_pc_strategie();

                    }
                    else if(x==initial_x+1 && y==initial_y+1){
                        rg.down_right(8*initial_y+initial_x);
                        rg.choose_pc_strategie();
                    }

                    paintFrame();

                    if (rg.win_machine()){
                        JOptionPane jop= new JOptionPane("LO SIENTO, "
                                + "HA PERDIDO HUMANO",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.PLAIN_MESSAGE);
                        JDialog dialog = jop.createDialog(null,
                                "VICTORIA MÁQUINA");
                        dialog.setDefaultCloseOperation(
                                WindowConstants.DO_NOTHING_ON_CLOSE);
                        dialog.setVisible(true);
                        dialog.dispose();
                        System.exit(0);
                        dispose();
                    }
                    if (rg.win_player()){
                        JOptionPane jop= new JOptionPane(
                                "FELICITACIONES, ME HA GANADO!!!",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.PLAIN_MESSAGE);
                        JDialog dialog = jop.createDialog(null,
                                "VICTORIA DEL HUMANO");
                        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                        dialog.setVisible(true);
                        dialog.dispose();
                        System.exit(0);
                        dispose();
                    }
                }
                
                begin = end;
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
            
        };
        
        //LISTENER FOR THE CATS' PIECES
        MouseListener catsListener = new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {               
            }
            @Override
            public void mouseExited(MouseEvent e){
                //ELIMINATES HIGHLIGHTED GRIDS' BOARD
                try{
                    label[initial_x-1][initial_y+1].setBackground(Color.BLACK);
                }catch(Exception ex){}
                try{
                    label[initial_x+1][initial_y+1].setBackground(Color.BLACK);
                }catch(Exception ex){}
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //POSITION OF THE PIECE THAT THE PLAYER WANT MOVE
                initial_x=e.getComponent().getX()/80;
                initial_y=e.getComponent().getY()/80;
                
                /*
                * HIGHLIGHT THE BOARD'S GRID WHERE THE CHOICE PIECE CAN MOVE
                * AND ALLOW SELECT IT
                */
                try{
                    label[initial_x-1][initial_y+1].setBackground(Color.RED);
                    label[initial_x-1][initial_y+1].addMouseListener(m);
                }catch(Exception ex){}
                try{
                    label[initial_x+1][initial_y+1].setBackground(Color.RED);
                    label[initial_x+1][initial_y+1].addMouseListener(m);
                }catch(Exception ex){}
            }
        };
        
        //LISTENER FOR THE MOUSE'S PIECES
        MouseListener mouseListener = new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
            }
            @Override
            public void mouseExited(MouseEvent e){
                //ELIMINATES HIGHLIGHTED GRIDS' BOARD
                try{
                    label[initial_x-1][initial_y-1].setBackground(Color.BLACK);
                }catch(Exception ex){}
                try{
                    label[initial_x+1][initial_y-1].setBackground(Color.BLACK);
                }catch(Exception ex){}
                try{
                    label[initial_x-1][initial_y+1].setBackground(Color.BLACK);
                }catch(Exception ex){}
                try{
                    label[initial_x+1][initial_y+1].setBackground(Color.BLACK);
                }catch(Exception ex){}
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //POSITION OF THE PIECE THAT THE PLAYER WANT MOVE
                initial_x=e.getComponent().getX()/80;
                initial_y=e.getComponent().getY()/80;
                
                /* 
                *  HIGHLIGHT THE BOARD'S GRID WHERE THE CHOICE PIECE CAN MOVE
                *  AND ALLOW SELECT IT
                */
                try{
                    label[initial_x-1][initial_y-1].setBackground(Color.RED);
                    label[initial_x-1][initial_y-1].addMouseListener(m);
                }catch(Exception ex){}
                try{
                    label[initial_x+1][initial_y-1].setBackground(Color.RED);
                    label[initial_x+1][initial_y-1].addMouseListener(m);
                    
                }catch(Exception ex){}
                try{
                    label[initial_x-1][initial_y+1].setBackground(Color.RED);
                    label[initial_x-1][initial_y+1].addMouseListener(m);
                }catch(Exception ex){}
                try{
                    label[initial_x+1][initial_y+1].setBackground(Color.RED);
                    label[initial_x+1][initial_y+1].addMouseListener(m);
                }catch(Exception ex){}
            }
        };
        
        //ONE OF THE TWO LISTENER ABOVE CAN BE USED,DEPENDING OF THE PLAYER
        if(player==1)
            for(JLabel cat_label: cats_label){
                cat_label.addMouseListener(catsListener);
            }
        else
            mouse_label.addMouseListener(mouseListener);
        
    }
    
    public void displayPlayer(){
        Font f = new Font("Arial", Font.BOLD,16);
                
        JLabel playerLabel= new JLabel();
        playerLabel.setBounds(700,30,200,100);
        playerLabel.setFont(f);
        
        if(player==1)
            playerLabel.setText("JUGADOR: GATO");
        else if(player==2)
            playerLabel.setText("JUGADOR: RATÓN");
        this.getContentPane().add(playerLabel);
    }
    
    public void paintCats(LispObject [] pos){
        int len=pos.length;
        cats_label= new JLabel[len];
       
        String path = "../imagenes/gato.jpg";  
        URL url = this.getClass().getResource(path);  
        ImageIcon icon = new ImageIcon(url);    
        
        
        for(int i=0;i<len;i++){
            
            cats_label[i]= new JLabel();
            cats_label[i].setBounds(80*(pos[i].intValue()%8),80*(pos[i].intValue()/8),80,80);
            cats_label[i].setIcon(icon);
            this.getContentPane().add(cats_label[i]);
        }
        
    }
    
    public void paintMouse(int pos){
        
        String path = "../imagenes/raton.jpg";  
        URL url = this.getClass().getResource(path);  
        ImageIcon icon = new ImageIcon(url);    
        
        mouse_label = new JLabel();
        
        mouse_label.setBounds(80*(pos%8),80*(pos/8),80,80);
        mouse_label.setIcon(icon);
        this.getContentPane().add(mouse_label);
    }
    public void paintBoard(){
        Color color;
        for(int i=0; i<8; i++){
            if(i%2!=0) color=Color.black;
            else color=Color.white;
            for (int j = 0; j < 8; j++) {
                if(color==Color.black)
                    color=Color.white;
                else
                    color=Color.black;                        
                
                label[i][j]= new Label();
                label[i][j].setBounds(80*i,80*j,80,80);
                label[i][j].setBackground(color);
                this.getContentPane().add(label[i][j]);
                
            }
             
        }
        
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 963, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents



    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
