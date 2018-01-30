/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego_ratongatos;

import org.armedbear.lisp.*;

/**
 *
 * @author Sebastian
 */
public class LISP_Functions{
    private Interpreter interpreter;
    private Symbol boardPositionChipsSym;
    private Function boardPositionChipsFunct;
    private Symbol assignPlayerSym;
    private Function assignPlayerFunct;
    private Symbol assignPlayerRandomlySym;
    private Function assignPlayerRandomlyFunct;
    private Symbol playerSym;
    private Function playerFunct;
    private Symbol upLeftSym;
    private Function upLeftFunct;
    private Symbol upRightSym;
    private Function upRightFunct;
    private Symbol downLeftSym;
    private Function downLeftFunct;        
    private Symbol downRightSym;
    private Function downRightFunct;
    private Symbol chooseStrategySym;
    private Function chooseStrategyFunct;
    private Symbol winMachine;
    private Function winMachineFunct;
    private Symbol winPlayerSym;
    private Function winPlayerFunct;
    
    public LISP_Functions(){
        load_from_LISP();
    }
    
    private void load_from_LISP(){
        //CREATE THE OBJECT THAT WILL DO THE CONEXION BETWEEN JAVA AND LISP
        if(Interpreter.getInstance()==null){
            interpreter = Interpreter.createInstance();
            interpreter.eval("(load \"proyecto-ia\")");
        }
        //USING THE PREDERTEMINATE PACKAGE IN LISP
        org.armedbear.lisp.Package defaultPackage = Packages.findPackage("CL-USER");

        boardPositionChipsSym = defaultPackage.findAccessibleSymbol("POSICIONFICHASTABLERO");
        boardPositionChipsFunct = (Function) boardPositionChipsSym.getSymbolFunction();
        
        assignPlayerSym = defaultPackage.findAccessibleSymbol("ASIGNARJUGADOR");
        assignPlayerFunct = (Function) assignPlayerSym.getSymbolFunction();
        
        assignPlayerRandomlySym = defaultPackage.findAccessibleSymbol("ASIGNARJUGADORAZAR");
        assignPlayerRandomlyFunct = (Function) assignPlayerRandomlySym.getSymbolFunction();
        
        playerSym = defaultPackage.findAccessibleSymbol("JUGADOR");
        playerFunct = (Function) playerSym.getSymbolFunction();
    
        upLeftSym = defaultPackage.findAccessibleSymbol("JUGARSUBIR-IZQ");
        upLeftFunct = (Function) upLeftSym.getSymbolFunction();
        
        upRightSym = defaultPackage.findAccessibleSymbol("JUGARSUBIR-DER");
        upRightFunct = (Function) upRightSym.getSymbolFunction();
        
        downLeftSym = defaultPackage.findAccessibleSymbol("JUGARBAJAR-IZQ");
        downLeftFunct = (Function) downLeftSym.getSymbolFunction();
        
        downRightSym = defaultPackage.findAccessibleSymbol("JUGARBAJAR-DER");
        downRightFunct = (Function) downRightSym.getSymbolFunction();
        
        chooseStrategySym = defaultPackage.findAccessibleSymbol("ESCOGER-ESTRATEGIA-PC");
        chooseStrategyFunct = (Function) chooseStrategySym.getSymbolFunction();
        
        winMachine = defaultPackage.findAccessibleSymbol("GANAMAQUINA");
        winMachineFunct = (Function) winMachine.getSymbolFunction();
        
        winPlayerSym = defaultPackage.findAccessibleSymbol("GANAPERSONA");
        winPlayerFunct = (Function) winPlayerSym.getSymbolFunction();
    }
    
    public LispObject[] positionBoardCats(){
        LispObject[] posCats = boardPositionChipsFunct.execute(Fixnum.getInstance(1)).copyToArray();
        return posCats;
    }
    
    public int positionBoardMouse(){
        LispObject[] posMouse = boardPositionChipsFunct.execute(Fixnum.getInstance(2)).copyToArray();
        return posMouse[0].intValue();
    }
    
    public void assignPlayer(int i){
        assignPlayerFunct.execute(Fixnum.getInstance(i));
    }
    
    public int assingPlayerRandomly(){
        return assignPlayerRandomlyFunct.execute().intValue();
    }
    
    public int getPlayer(){
        return playerFunct.execute().intValue();
    }
    
    public void down_left(int i){
        downLeftFunct.execute(Fixnum.getInstance(i));
    }
    
    public void down_right(int i){
        downRightFunct.execute(Fixnum.getInstance(i));
    }
    
    public void up_left(int i){
        upLeftFunct.execute(Fixnum.getInstance(i));
    }
    
    public void up_right(int i){
        upRightFunct.execute(Fixnum.getInstance(i));
    }
    
    public void choose_pc_strategie(){
        chooseStrategyFunct.execute();
    }
    
    public boolean win_machine(){
        return winMachineFunct.execute().getBooleanValue();
    }
    
    public boolean win_player(){
        return winPlayerFunct.execute().getBooleanValue();
    }
}
