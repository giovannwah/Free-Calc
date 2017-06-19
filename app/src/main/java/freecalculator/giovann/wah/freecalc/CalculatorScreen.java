package freecalculator.giovann.wah.freecalc;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.renderscript.Sampler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import freecalc.parameter.Function;

/**
 * Created by giovadmin on 12/31/15.
 */
public class CalculatorScreen{
    private int bigTextSize,
            smallTextSize,
            bigTextColor,
            smallTextColor,
            answerColor;

    private LinearLayout bigText;
    private LinearLayout smallText;
    private HorizontalScrollView bTSView;
    private HorizontalScrollView sTSView;
    private Context context;
    private TokenManager tm;
    private double currentAnswer;
    private boolean currentAnswerExists;
    //initialize the calculator screen
    public CalculatorScreen(int bts,
                            int sts,
                            int btc,
                            int stc,
                            int ac,
                            Context c,
                            LinearLayout bt,
                            LinearLayout st,
                            HorizontalScrollView b,
                            HorizontalScrollView s){
        this.context = c;
        this.bigTextSize = bts;
        this.smallTextSize = sts;
        this.bigTextColor = btc;
        this.smallTextColor = stc;
        this.answerColor = ac;
        this.bigText = bt;
        this.smallText = st;
        this.bTSView = b;
        this.sTSView = s;
        this.currentAnswerExists = false;
        this.tm = new TokenManager();
    }
    public ArrayList<String> getTokens(){
        return tm.getFunctionalBuffer();
    }
    public void setCurrentAnswer(double ans){
        this.currentAnswer = ans;
    }
    public double getCurrentAnswer(){
        return this.currentAnswer;
    }
    public void setPreviousBuffer(){
        tm.archive();
    }

    public void delete(){
        tm.delete();
    }
    public boolean getCurrentAnswerExists(){
        return this.currentAnswerExists;
    }
    public void setCurrentAnswerExists(boolean b){
        this.currentAnswerExists = b;
    }
    public void reset(){
        tm.reset();
    }

    public void shiftLeft(){
        tm.shiftLeft();
    }

    public void shiftRight(){
        tm.shiftRight();
    }
    /**
     * Invokes the TokenManager in order to add the encoded token to both the display
     * and the functional buffer.
     * @param id
     */
    public void add(int id){
        MainActivity.log("CalculatorScreen - add() called.");
        switch(id){
            case 0: { //0 code
                tm.addToken(0);
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 1: { //1 code
                tm.addToken(1);
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 2: { //2 code
                tm.addToken(2);
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 3: { //3 code
                tm.addToken(3);
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }case 4: { //4 code
                tm.addToken(4);
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 5: { //5 code
                tm.addToken(5);
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 6: { //6 code
                tm.addToken(6);
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 7: { //7 code
                tm.addToken(7);
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 8: { //8 code
                tm.addToken(8);
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 9: { //9 code
                tm.addToken(9);
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 10: { //addition code
                tm.addToken(10);
                MainActivity.currentState = MainActivity.STATE.BINOP;
                break;
            }
            case 11: { //subtraction/neg code
                tm.addToken(11);
                MainActivity.currentState = MainActivity.STATE.BINOP;
                break;
            }
            case 12: { //multiply code
                tm.addToken(12);
                MainActivity.currentState = MainActivity.STATE.BINOP;
                break;
            }
            case 13: { //divide code
                tm.addToken(13);
                MainActivity.currentState = MainActivity.STATE.BINOP;
                break;
            }
            case 14: { //decimal code
                tm.addToken(14);
                MainActivity.currentState = MainActivity.STATE.DECIMAL;
                break;
            }
            case 15: { //scinot code
                tm.addToken(15);
                MainActivity.currentState = MainActivity.STATE.SCINOT;
                break;
            }
            case 16: { //left param code
                tm.addToken(16);
                MainActivity.currentState = MainActivity.STATE.LPAREN;
                break;
            }
            case 17: { //right param code
                tm.addToken(17);
                MainActivity.currentState = MainActivity.STATE.RPAREN;
                break;
            }
            case 18: { //old neg code
                break;
            }
            case 19: { //super 0 code
                tm.addToken(19);
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 20: { //super 1 code
                tm.addToken(20);
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 21: { //super 2 code
                tm.addToken(21);
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 22: { //super 3 code
                tm.addToken(22);
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 23: { //super 4 code
                tm.addToken(23);
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 24: { //super 5 code
                tm.addToken(24);
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 25: { //super 6 code
                tm.addToken(25);
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 26: { //super 7 code
                tm.addToken(26);
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 27: { //super 8 code
                tm.addToken(27);
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 28: { //super 9 code
                tm.addToken(28);
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 29: { //super neg code
                tm.addToken(29);
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 30: { //super decimal code
                tm.addToken(30);
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 31: { //ln code
                tm.addToken(31);
                MainActivity.currentState = MainActivity.STATE.FUNC;
                break;
            }
            case 32: { //log code
                tm.addToken(32);
                MainActivity.currentState = MainActivity.STATE.FUNC;
                break;
            }
            case 33: { //square root code
                tm.addToken(33);
                MainActivity.currentState = MainActivity.STATE.FUNC;
                break;
            }
            case 34: { //cube root code
                tm.addToken(34);
                MainActivity.currentState = MainActivity.STATE.FUNC;
                break;
            }
            case 35: { //square code
                tm.addToken(35);
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 36: { //cube code
                tm.addToken(36);
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 37: { //e code
                tm.addToken(37);
                MainActivity.currentState = MainActivity.STATE.NUMSYM;
                break;
            }
            case 38: { //mod code
                tm.addToken(38);
                MainActivity.currentState = MainActivity.STATE.BINOP;
                break;
            }
            case 39: { //percent code
                tm.addToken(39);
                MainActivity.currentState = MainActivity.STATE.BINOP;
                break;
            }
            case 40: { //pi code
                tm.addToken(40);
                MainActivity.currentState = MainActivity.STATE.NUMSYM;
                break;
            }
            case 41: { //arcsin code
                tm.addToken(41);
                MainActivity.currentState = MainActivity.STATE.FUNC;
                break;
            }
            case 42: { //sin code
                tm.addToken(42);
                MainActivity.currentState = MainActivity.STATE.FUNC;
                break;
            }
            case 43: { //arccos code
                tm.addToken(43);
                MainActivity.currentState = MainActivity.STATE.FUNC;
                break;
            }
            case 44: { //cos code
                tm.addToken(44);
                MainActivity.currentState = MainActivity.STATE.FUNC;
                break;
            }
            case 45: { //arctan code
                tm.addToken(45);
                MainActivity.currentState = MainActivity.STATE.FUNC;
                break;
            }
            case 46: { //tan code
                tm.addToken(46);
                MainActivity.currentState = MainActivity.STATE.FUNC;
                break;
            }
            case 47: { //rec code
                tm.addToken(47);
                MainActivity.currentState = MainActivity.STATE.BINOP;
                break;
            }
            case 48: {
                tm.addToken(48);
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
        }
    }

    /**
     * Returns true only if the screen is currently "blank"
     * (no text, only cursor)
     * @return
     */
    public boolean isBlank(){
        return bigText.getChildCount() == 0 ||
                (bigText.getChildCount() == 1 && !(bigText.getChildAt(0) instanceof TextView));
    }

    /**
     * Sets the screen's current answer to the string s
     * @param s
     */
    public void setAnswer(String s){
        reset();
        TextView t = new TextView(context);
        t.setText(s);
        t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, bigTextSize);
        t.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/ColabThi.otf"));
        t.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        t.setGravity(Gravity.TOP);
        t.setTextColor(context.getResources().getColor(R.color.green));
        bigText.addView(t);
        bTSView.postDelayed(new Runnable() {
            public void run() {
                bTSView.fullScroll(HorizontalScrollView.FOCUS_LEFT);
            }
        }, 100L);
    }

    public void stopCursor(){
    //    tm.cursorOA.end();
    }
    /**
     * Sets the screen's current error to the string s
     * @param s
     */
    public void setError(String s){
        reset();
        TextView t = new TextView(context);
        t.setText(s);
        t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, bigTextSize);
        t.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/ColabThi.otf"));
        t.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        t.setGravity(Gravity.TOP);
        t.setTextColor(context.getResources().getColor(R.color.red));
        bigText.addView(t);
    }
    public void insertAnswer(){
        tm.insertAnswer();
    }
    private void superScript(TextView t, int originalSize){
        t.setTextSize(originalSize / 2);
    }

    private void regularScript(TextView t, int originalSize){
        t.setTextSize(originalSize);
    }

    ////////////////////////////////////////////////////////////////////////////////////
    /**
     * Class to manage the textview tokens
     */
    private class TokenManager {
        public final String root = Character.toString((char)0x221A);
        public final String divide = Character.toString((char)0x00F7);
        public final String multiply = Character.toString((char)0x00D7);
        public final String pi = Character.toString((char)0x03C0);
        public final String italicE = "e~";
        public final String superTwo = Character.toString((char)0x00B2);
        public final String superThree = Character.toString((char)0x00B3);
        // public final String italicE = "<i>e</i>";

        private ArrayList<TextView> previousBuffer;
        private ArrayList<TextView> smallTextList;
        private int index;
        private float standardTextSize;
        private View cursor;
        private ArrayList<View> cursorPositions;
        private ArrayList<TextView> tokenPositions;
        private ObjectAnimator cursorOA;

        public TokenManager(){
            this.standardTextSize = 0;
            this.index = 0;
            this.smallTextList = new ArrayList<TextView>();
            this.cursor = getCursorView(2);
            bigText.addView(this.cursor, 0);
            this.cursorPositions = new ArrayList<View>();
            this.cursorPositions.add(this.cursor);
            this.tokenPositions = new ArrayList<TextView>();

            this.cursorOA = ObjectAnimator.ofFloat(this.cursor, "alpha", 0f, 1f);
            this.cursorOA.setInterpolator(new DecelerateInterpolator());
            this.cursorOA.setRepeatCount(ObjectAnimator.INFINITE);
            this.cursorOA.setRepeatMode(ObjectAnimator.REVERSE);
            this.cursorOA.setDuration(500);
            this.cursorOA.start();
        }

        public void delete(){

        }

        /**
         * Clears the screen and other stored data
         */
        public void reset(){
            MainActivity.log("reset() called");
            this.index = 0;
            bigText.removeAllViews();
            smallText.removeAllViews();
            this.smallTextList = new ArrayList<>();
            this.cursor = getCursorView(2);
            bigText.addView(this.cursor, 0);
            this.cursorPositions = new ArrayList<>();
            this.cursorPositions.add(this.cursor);
            this.tokenPositions = new ArrayList<>();

            //reinitialize cursor
            this.cursorOA.end();
            this.cursorOA.setTarget(this.cursor);
            this.cursorOA.start();
        //    reanimateCursor(0);
        }

        public void reanimateCursor(int i){
            this.cursorOA.end();
            this.cursorOA.setTarget(this.cursorPositions.get(i));
            this.cursorOA.start();
        }

        /**
         * handles the adding of the encoded for token to the display and the buffer
         */
        public void addToken(int i){
            MainActivity.log("BigText children: " + bigText.getChildCount());
            switch(i){
                case 0: {
                    TextView t = getTextView("0", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount() - index);
                    break;
                }
                case 1: {
                    TextView t = getTextView("1", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount() - index);
                    break;
                }
                case 2: {
                    TextView t = getTextView("2", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount() - index);
                    break;
                }
                case 3: {
                    TextView t = getTextView("3", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount() - index);
                    break;
                }
                case 4: {
                    TextView t = getTextView("4", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount() - index);
                    break;
                }
                case 5: {
                    TextView t = getTextView("5", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount() - index);
                    break;
                }
                case 6: {
                    TextView t = getTextView("6", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount() - index);
                    break;
                }
                case 7: {
                    TextView t = getTextView("7", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount() - index);
                    break;
                }
                case 8: {
                    TextView t = getTextView("8", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount() - index);
                    break;
                }
                case 9: {
                    TextView t = getTextView("9", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount() - index);
                    break;
                }
                case 10: {
                    TextView t = getTextView("+", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    //binary op can be added to  an answer immediately
                    if (MainActivity.currentState == MainActivity.STATE.ANSWER){
                        reset();
                        insertAnswer();
                    }
                    pushToDisplay(t, bigText.getChildCount() - index);
                    break;
                }
                case 11: {
                    TextView t = getTextView("-", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    //binary op can be added to  an answer immediately
                    if (MainActivity.currentState == MainActivity.STATE.ANSWER){
                        reset();
                        insertAnswer();
                    }
                    pushToDisplay(t, bigText.getChildCount() - index);
                    break;
                }
                case 12: {
                    TextView t = getTextView(multiply, true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    //binary op can be added to  an answer immediately
                    if (MainActivity.currentState == MainActivity.STATE.ANSWER){
                        reset();
                        insertAnswer();
                    }
                    pushToDisplay(t, bigText.getChildCount() - index);
                    break;
                }
                case 13: {
                    TextView t = getTextView(divide, true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    //binary op can be added to  an answer immediately
                    if (MainActivity.currentState == MainActivity.STATE.ANSWER){
                        reset();
                        insertAnswer();
                    }
                    pushToDisplay(t, bigText.getChildCount() - index);
                    break;
                }
                case 14: {
                    TextView t = getTextView(".", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 15: {
                    TextView t = getTextView("e", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 16: {
                    TextView t = getTextView("(", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 17: {
                    TextView t = getTextView(")", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 18: { //old neg code
                    break;
                }
                case 19: {
                    TextView t = getTextView("0", true, true);
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 20: {
                    TextView t = getTextView("1", true, true);
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 21: {
                    TextView t = getTextView("2", true, true);
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 22: {
                    TextView t = getTextView("3", true, true);
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 23: {
                    TextView t = getTextView("4", true, true);
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 24: {
                    TextView t = getTextView("5", true, true);
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 25: {
                    TextView t = getTextView("6", true, true);
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 26: {
                    TextView t = getTextView("7", true, true);
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 27: {
                    TextView t = getTextView("8", true, true);
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 28: {
                    TextView t = getTextView("9", true, true);
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 29: {
                    TextView t = getTextView("-", true, true);
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 30: {
                    TextView t = getTextView(".", true, true);
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 31: {
                    TextView t = getTextView("ln", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 32: {
                    TextView t = getTextView("log", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 33: {
                    TextView t = getTextView(root, true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 34: {
                    TextView t = getTextView(superThree+root, true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 35: {
                    TextView t = getTextView("2", true, true);
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 36: {
                    TextView t = getTextView("3", true, true);
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 37: {
                    TextView t = getTextView(italicE, true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 38: {
                    TextView t = getTextView("mod", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    //binary op can be added to  an answer immediately
                    if (MainActivity.currentState == MainActivity.STATE.ANSWER){
                        reset();
                        insertAnswer();
                    }
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 39: {
                    TextView t = getTextView("%", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    //binary op can be added to  an answer immediately
                    if (MainActivity.currentState == MainActivity.STATE.ANSWER){
                        reset();
                        insertAnswer();
                    }
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 40: {
                    TextView t = getTextView(pi, true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 41: {
                    TextView t = getTextView("arcsin", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 42: {
                    TextView t = getTextView("sin", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 43: {
                    TextView t = getTextView("arccos", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 44: {
                    TextView t = getTextView("cos", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 45: {
                    TextView t = getTextView("arctan", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 46: {
                    TextView t = getTextView("tan", true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 47: {
                    TextView t = getTextView("1 "+divide, true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount()-index);
                    break;
                }
                case 48: {
                    TextView t = getTextView(root, true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount()-index);
                    shiftLeft();
                    break;

                }
            } //switch
        //    reanimateCursor(index);
        }//addToken

        /**
         * Adds the specified textView to the display at index i, in addition to the cursor spots
         * @param t
         * @param i
         */
        public void pushToDisplay(TextView t, int i) {
        //    bigText.addView(t, i);
            bigText.addView(t, bigText.getChildCount() - (index*2)); //add text in a way that accounts for cursor space
            tokenPositions.add(index, t);

            View c = getCursorView(2);
        //    bigText.addView(c, i + 1); //add cursor position to the display
            bigText.addView(c, bigText.getChildCount() - (index*2)); //add cursor in a way that accounts for tokens
            cursorPositions.add(index, c); //add cursor to list of cursors at the current index
            cursorOA.end();
            ((View)cursorOA.getTarget()).setAlpha(0.0f);
            cursorOA.setTarget(cursorPositions.get(index));
            cursorOA.start();
            bTSView.postDelayed(new Runnable() {
                public void run() {
                    bTSView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                }
            }, 100L);
        }

        /**
         * returns a template for the cursor view, with the width in dip
         * @return
         */
        public View getCursorView(int width){
            View v = new View(context);
            v.setBackgroundColor(context.getResources().getColor(R.color.red));
            v.setAlpha(0.0f);
            v.setLayoutParams(new ViewGroup.LayoutParams(MainActivity.dpToPixel(width), ViewGroup.LayoutParams.MATCH_PARENT));
            return v;
        }

        /**
         * Insert stored answer into display
         */
        public void insertAnswer(){
            MainActivity.log("insertAnswer()");
            if (currentAnswerExists && MainActivity.currentState == MainActivity.STATE.ANSWER){
                String s = "";
                //if the current answer is close to an integer, represent it as an integer.
                if (Function.closeToInteger(currentAnswer)) {
                    long a = Math.round(currentAnswer);
                    s += a;
                }
                else s += currentAnswer;
                if (MainActivity.currentState == MainActivity.STATE.NUM){
                    TextView t = getTextView(multiply, true, false);
                    if (this.standardTextSize == 0) this.standardTextSize = t.getTextSize();
                    pushToDisplay(t, bigText.getChildCount()-index); //add multiplication
                    //add answer
                    for (int i = 0; i < s.length(); i++){
                        TextView t2 = getTextView(s.substring(i,i+1), true, false);
                        pushToDisplay(t2, bigText.getChildCount()-index);
                    }
                    MainActivity.currentState = MainActivity.STATE.NUM;
                }
                else if (MainActivity.currentState == MainActivity.STATE.SUPERSCRIPTMODE
                         ){
                    // TODO: implement cursor first, then this.
                }
                //simply insert the number into the display
                else {
                    for (int i = 0; i < s.length(); i++){
                        TextView t2 = getTextView(s.substring(i,i+1), true, false);
                        pushToDisplay(t2, bigText.getChildCount()-index);
                    }
                    MainActivity.currentState = MainActivity.STATE.NUM;
                }
            }
        }

        /**
         * Transforms tokens to functional Arraylist for further processing
         * @return
         */
        public ArrayList<String> getFunctionalBuffer(){
            MainActivity.log("getFunctionalBuffer called...");
            ArrayList<TextView> tokens = new ArrayList();
            ArrayList<String> ret = new ArrayList();
            for (int i = 0; i < bigText.getChildCount(); i++){
                if (bigText.getChildAt(i) instanceof TextView) tokens.add((TextView)bigText.getChildAt(i));
            }
            for (int i = 0; i < tokens.size(); i++){
                MainActivity.log("i: "+i);
                if (i < tokens.size()-1 && tokens.get(i).getText().toString().equals("1 " + divide)){
                    ret.add("rec");
                }
                //resolve "e" vs scientific notation
                else if (tokens.get(i).getText().toString().equals("e")
                        && tokens.get(i).getCurrentTextColor() == context.getResources().getColor(R.color.blue)){
                    ret.add("e");
                }
                else if (tokens.get(i).getText().toString().equals("e")){
                    ret.add("scinot");
                }
                else if (tokens.get(i).getText().toString().equals(divide)){
                    ret.add("/");
                }
                else if (tokens.get(i).getText().toString().equals(multiply)){
                    ret.add("*");
                }
                else if (tokens.get(i).getText().toString().equals(superThree+root)){
                    ret.add("cbrt");
                }
                else if (tokens.get(i).getText().toString().equals(root)){
                    ret.add("sqrt");
                }
                else if (tokens.get(i).getText().toString().equals(pi)){
                    ret.add("pi");
                }
                //handle exponents

                else if (tokens.get(i).getTextSize() < this.standardTextSize){
                    MainActivity.log("EXPONENT FOUND");
                    String add = tokens.get(i).getText().toString();
                    for (int j = ++i; j < tokens.size(); j++, i++){
                        MainActivity.log("i: "+i);
                        MainActivity.log("j: "+j);
                        if (tokens.get(j).getTextSize() < this.standardTextSize){
                            add += tokens.get(j).getText().toString();
                        }
                        else {
                            break;
                        }
                    }
                    //n root vs nth power
                    if (tokens.get(i).getText().toString().equals(root)){
                        MainActivity.log("Its a ROOT");
                        MainActivity.log(tokens.get(i).getText().toString());
                        ret.add("nrt");
                        ret.add(add);
                    }
                    else {
                        MainActivity.log("Its a POWER");
                        MainActivity.log(tokens.get(i).getText().toString());
                        ret.add("^");
                        ret.add(add);
                    }
                }
                //handle multi-digit numbers
                else if (isNumeric(tokens.get(i).getText().toString()) || tokens.get(i).getText().toString().equals(".")){
                    String add = tokens.get(i).getText().toString();
                    for (int j = i+1; j < tokens.size(); j++, i++){
                        if ((isNumeric(tokens.get(j).getText().toString()) || tokens.get(j).getText().toString().equals("."))
                                && tokens.get(j).getTextSize() == this.standardTextSize)
                            add += tokens.get(j).getText().toString();
                        else break;
                    }
                    ret.add(add);
                }
                else {
                    ret.add(tokens.get(i).getText().toString());
                }
            }
            MainActivity.log("getFunctionalBuffer end...");
            return ret;
        }

        /**
         * Returns true if the string s is a number or a number ending in a decimal
         * @param s
         * @return
         */
        public boolean isNumeric(String s){
            MainActivity.log("isNumeric");
            try {
                double d = Double.parseDouble(s.trim());
            }
            catch (NumberFormatException e){
                if (s.charAt(s.length()-1) == '.') {
                    String s2 = s.substring(0, s.length()-1).trim();
                    try {
                        double d2 = Double.parseDouble(s2);
                    }
                    catch (NumberFormatException e2){
                        return false;
                    }
                    return true;
                }
                else{
                    return false;
                }
            }
            return true;
        }

        public void shiftLeft(){
            if (index < cursorPositions.size()-1){
                index++;
                cursorOA.end();
                ((View)cursorOA.getTarget()).setAlpha(0.0f);
                cursorOA.setTarget(cursorPositions.get(index));
                cursorOA.start();
            }
        }

        public void shiftRight(){
            if (index > 0){
                index--;
                cursorOA.end();
                ((View)cursorOA.getTarget()).setAlpha(0.0f);
                cursorOA.setTarget(cursorPositions.get(index));
                cursorOA.start();

            }
        }

        public void archive(){
            ArrayList<TextView> t = new ArrayList<TextView>();
            for (int i = 0; i < bigText.getChildCount(); i++){
                if (bigText.getChildAt(i) instanceof TextView)
                    t.add((TextView)bigText.getChildAt(i));
            }
            this.previousBuffer = t;
        }
        /**
         * Returns a TextView object with the specified text, size, and superscript status
         * @param text
         * @param big
         * @param sup
         * @return
         */
        public TextView getTextView(String text, boolean big, boolean sup){
            TextView t = new TextView(context);
            t.setText(text);
            t.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/ColabThi.otf"));
            t.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            t.setGravity(Gravity.TOP);
            if (big){
                if (sup){
                    t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, bigTextSize / 2);
                }
                else {
                    t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, bigTextSize);
                }
                if (text.equals(pi) || text.equals(italicE)) {
                    if (text.equals(italicE)) t.setText("e");
                    t.setTextColor(context.getResources().getColor(R.color.blue));
                }
                else t.setTextColor(context.getResources().getColor(bigTextColor));
            }
            else {
                if (sup){
                    t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, smallTextSize / 2);
                }
                else {
                    t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, smallTextSize);
                }
                if (text.equals(pi) || text.equals(italicE)) {
                    if (text.equals(italicE)) t.setText("e");
                    t.setTextColor(context.getResources().getColor(R.color.blue));
                }
                else t.setTextColor(context.getResources().getColor(smallTextColor));
            }
            return t;
        }

        /**
         * Copy the relevant properties of the textview ori (original) to the textview tar (target)
         * @param ori
         * @param tar
         */
        private void copyTextViewProperties(TextView ori, TextView tar){
            tar.setText(ori.getText());
            tar.setTextColor(ori.getCurrentTextColor());
            tar.setTextSize(ori.getTextSize());
        }
    } //End of TokenManager class
////////////////////////////////////////////////////////////////////////////////////////
}
