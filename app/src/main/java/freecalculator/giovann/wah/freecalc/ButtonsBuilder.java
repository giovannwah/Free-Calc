package freecalculator.giovann.wah.freecalc;

import android.content.Context;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by giovadmin on 7/11/15.
 * Manages the creation and orientation of calculator buttons in the app
 */
public class ButtonsBuilder {
  /*  public static final String[] buttons30Names = new String [] {
            "m+", "mr", "mc", "left_paren", "right_paren", "var+", "varr",
            "varc", "left_arrow", "right_arrow", "percent", "sin", "cos",
            "tan", "reciprocal", "pi", "arcsin", "arccos", "arctan", "mod",
            "sci_notation", "n_root", "square_root", "cube_root", "log", "e",
            "power", "square", "cube", "ln", "C", "7", "8", "9", "div", "back",
            "4", "5", "6", "x", "equals", "1", "2", "3", "minus", "decimal", "0", "neg", "plus"
    };
    public static final String[] buttons40Names = new String [] {
            "percent", "sin", "cos", "tan", "reciprocal", "pi", "arcsin",
            "arccos", "arctan", "mod", "sci_notation", "n_root", "square_root",
            "cube_root", "log", "e", "power", "square", "cube", "ln", "m+", "mr",
            "mc", "left_paren", "right_paren", "var+", "varr", "varc", "left_arrow",
            "right_arrow", "C", "7", "8", "9", "div", "back", "4", "5", "6", "x",
            "equals", "1", "2", "3", "minus", "decimal", "0", "neg", "plus"
    };
    private static ArrayList<ButtonSlot> buttons;

    public static void initiateButtons(int buttonsLayout){
        buttons = new ArrayList<>();
        Context context = MainActivity.mainContext;
        if (buttonsLayout == MainActivity.BUTTONS_30_LAYOUT) {
            for (int i = 0; i < 49; i++){
                buttons.add(new ButtonSlot( new Button(context), buttons30Names[i], i));
            }
        }
        else if (buttonsLayout == MainActivity.BUTTONS_40_LAYOUT) {
            for (int i = 0; i < 49; i++){
                buttons.add(new ButtonSlot( new Button(context), buttons40Names[i], i));
            }
        }
    }
    private static class ButtonSlot {
        private Button button;
        private String name;
        private int position;

        public ButtonSlot(){
            this.button = null;
            this.name = "";
            this.position = -1;
        }
        public ButtonSlot (Button b, String n, int p){
            this.button = b;
            this.name = n;
            this.position = p;
        }

        public Button getButton (){
            return this.button;
        }
        public String getName() {
            return this.name;
        }
        public int getPosition(){
            return this.position;
        }
        public void setButton (Button b){
            this.button = b;
        }
        public void setName(String n){
            this.name = n;
        }
        public void setPosition(int p){
            this.position = p;
        }
    }
    */
}
