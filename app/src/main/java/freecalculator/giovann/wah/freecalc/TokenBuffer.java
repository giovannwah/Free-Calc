package freecalculator.giovann.wah.freecalc;

import android.text.Html;
import android.widget.EditText;

import java.util.ArrayList;

import freecalc.parameter.Function;

/**
 * Created by giovadmin on 8/4/15.
 */
public class TokenBuffer {

    public static final String superOne = Character.toString((char)0x00B9);
    public static final String superTwo = Character.toString((char)0x00B2);
    public static final String superThree = Character.toString((char)0x00B3);
    public static final String superFour = Character.toString((char)0x2074);
    public static final String superFive = Character.toString((char)0x2075);
    public static final String superSix = Character.toString((char)0x2076);
    public static final String superSeven = Character.toString((char)0x2077);
    public static final String superEight = Character.toString((char)0x2078);
    public static final String superNine = Character.toString((char)0x2079);
    public static final String superZero = Character.toString((char)0x2070);
    //public static final String superNeg = Character.toString((char)0x207B);
    //public static final String superDecimal = Character.toString((char)0x02D9);
    public static final String superNeg = "<sup><small>-</small></sup>";
    public static final String superDecimal = "<sup><small>.</small></sup>";


    public static final String root = Character.toString((char)0x221A);
    public static final String divide = Character.toString((char)0x00F7);
    public static final String multiply = Character.toString((char)0x00D7);
    public static final String pi = Character.toString((char)0x03C0);
    //public static final String italicE = Character.toString((char)0x1D452);
    public static final String italicE = "<i>e</i>";

    public static final String superPi = "<sup><small>"+pi+"</small></sup>";
    public static final String superItalicE = "<sup><small>"+italicE+"</small></sup>";
    public static final String superE = "<sup><small>e</small></sup>";

    private ArrayList<String> functionalBuffer;
    private ArrayList<String> translationBuffer;

    private ArrayList<String> previousBuffer;
    private String currentDisplayedString;
    private double currentAnswer;

    private int addIndex;
    private int translationIndex;
    private int actualIndex;

    private EditText bigET;
    private EditText smallET;

    public TokenBuffer (EditText b, EditText s){
        this.functionalBuffer = new ArrayList<String>();
        this.translationBuffer = new ArrayList<String>();
        this.previousBuffer = new ArrayList<String>();
        this.currentDisplayedString = new String();
        this.bigET = b;
        this.smallET = s;
        this.translationIndex = 0;
        this.addIndex = 0;
        this.actualIndex = 0;
        this.currentAnswer = 0;
    }

    public TokenBuffer (ArrayList<String> s, EditText b, EditText e){
        this.functionalBuffer = new ArrayList<String>(s);
        this.translationBuffer = functionalToTranslation(this.functionalBuffer); //generate tokenTranslationBuffer
        this.previousBuffer = new ArrayList<String>();
        this.currentDisplayedString = new String();
        this.bigET = b;
        this.smallET = e;
        this.translationIndex = 0;
        this.addIndex = 0;
        this.actualIndex = 0;
        this.currentAnswer = 0;
    }

    /**
     * Establishes the translationBuffer based on the functional buffer.
     */
    public ArrayList<String> functionalToTranslation (ArrayList<String> tokens){
        ArrayList<String> ret = new ArrayList<String>();
        for (int i = 0; i < tokens.size(); i++){
            if (tokens.get(i).equals("neg")){
                ret.add("-");
                ret.add("~");
            }
            else if (tokens.get(i).equals("scinot")){
                ret.add("e");
            }
            //reciprocal is distinguished by a tilde after the division symbol.
            else if (tokens.get(i).equals("rec")){
                ret.add("1");
                ret.add(divide);
                ret.add("~");
            }
            else if (tokens.get(i).equals("/")){
                ret.add(divide);
            }
            else if (tokens.get(i).equals("*")){
                ret.add(multiply);
            }
            else if (tokens.get(i).equals("sqrt")){
                ret.add(root);
            }
            else if (tokens.get(i).equals("cbrt")){
                ret.add(superThree+root);
            }
            else if (tokens.get(i).equals("pi")){
                ret.add(pi);
            }
            else if (tokens.get(i).equals("arcsin")){
                ret.add("sin"+superNeg+superOne);
            }
            else if (tokens.get(i).equals("arccos")){
                ret.add("cos"+superNeg+superOne);
            }
            else if (tokens.get(i).equals("arctan")){
                ret.add("tan"+superNeg+superOne);
            }
            else if (tokens.get(i).equals("e")){
                ret.add(italicE);
            }
            else if (i < tokens.size()-1 && tokens.get(i).equals("^") && tokens.get(i+1).equals("2")){
                ret.add(superTwo);
                ++i;
            }
            else if (i < tokens.size()-1 && tokens.get(i).equals("^") && tokens.get(i+1).equals("3")){
                ret.add(superThree);
                ++i;
            }
            else if (isNumeric(tokens.get(i))){
                for (int j = 0; j < tokens.get(i).length(); j++){
                    ret.add(String.valueOf(tokens.get(i).charAt(j)));
                }
            }
            else {
                ret.add(tokens.get(i));
            }
        }
        return ret;
    }

    /**
     * Transforms a transition buffer into an equivalent functional buffer
     * @param tokens
     * @return
     */
    public ArrayList<String> translationToFunctional (ArrayList<String> tokens){
        ArrayList<String> ret = new ArrayList<String>();
        for (int i = 0; i < tokens.size(); i++){
            if (i < tokens.size()-2 && tokens.get(i).equals("1") && tokens.get(i+1).equals(divide) && tokens.get(i+2).equals("~")){
                ret.add("rec");
                i += 2;
            }
            else if (i < tokens.size()-1 && tokens.get(i).equals("-") && tokens.get(i+1).equals("~")){
                ret.add("neg");
                ++i;
            }
            else if (tokens.get(i).equals("e")){
                ret.add("scinot");
            }
            else if (tokens.get(i).equals(divide)){
                ret.add("/");
            }
            else if (tokens.get(i).equals(multiply)){
                ret.add("*");
            }
            else if (tokens.get(i).equals(superThree+root)){
                ret.add("cbrt");
            }
            else if (tokens.get(i).equals(root)){
                ret.add("sqrt");
            }
            else if (tokens.get(i).equals(pi)){
                ret.add("pi");
            }
            else if (tokens.get(i).equals("sin"+superNeg+superOne)){
                ret.add("arcsin");
            }
            else if (tokens.get(i).equals("cos"+superNeg+superOne)){
                ret.add("arccos");
            }
            else if (tokens.get(i).equals("tan"+superNeg+superOne)){
                ret.add("arctan");
            }
            else if (tokens.get(i).equals(italicE)){
                ret.add("e");
            }
            else if (tokens.get(i).equals(superTwo)){
                ret.add("^");
                ret.add("2");
            }
            else if (tokens.get(i).equals(superThree)){
                ret.add("^");
                ret.add("3");
            }
            else if (isNumeric(tokens.get(i)) || tokens.get(i).equals(".")){
                String add = tokens.get(i);
                for (int j = i+1; j < tokens.size(); j++){
                    if (isNumeric(tokens.get(j)) || tokens.get(j).equals(".")){
                        add += tokens.get(j);
                        i++;
                    }
                    else {
                        break;
                    }
                }
                ret.add(add);
            }
            else {
                ret.add(tokens.get(i));
            }
        }
        return ret;
    }

    /**
     * Add new character to the buffer
     * @param id
     */
    public void addToBuffer(int id){
        MainActivity.log("*** addToBuffer called.");
        if (MainActivity.API_LEVEL >= 16 && !bigET.isCursorVisible())
            bigET.setCursorVisible(true);
        switch (id) {
            case 0: { //0 code
                translationBuffer.add(addIndex++, "0");
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NEG) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.SCINOT)){
                    currentDisplayedString += "0";
                    actualIndex += 1;
                }
                else {
                    currentDisplayedString += " 0";
                    actualIndex += 2;
                }
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 1: { //1 code
                translationBuffer.add(addIndex++, "1");
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NEG) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.SCINOT)){
                    currentDisplayedString += "1";
                    actualIndex += 1;
                }
                else {
                    currentDisplayedString += " 1";
                    actualIndex += 2;
                }
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 2: { //2 code
                translationBuffer.add(addIndex++, "2");
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NEG) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.SCINOT)){
                    currentDisplayedString += "2";
                    actualIndex += 1;
                }
                else {
                    currentDisplayedString += " 2";
                    actualIndex += 2;
                }
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 3: { //3 code
                translationBuffer.add(addIndex++, "3");
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NEG) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.SCINOT)){
                    currentDisplayedString += "3";
                    actualIndex += 1;
                }
                else {
                    currentDisplayedString += " 3";
                    actualIndex += 2;
                }
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 4: { //4 code
                translationBuffer.add(addIndex++, "4");
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NEG) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.SCINOT)){
                    currentDisplayedString += "4";
                    actualIndex += 1;
                }
                else {
                    currentDisplayedString += " 4";
                    actualIndex += 2;
                }
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 5: { //5 code
                translationBuffer.add(addIndex++, "5");
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NEG) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.SCINOT)){
                    currentDisplayedString += "5";
                    actualIndex += 1;
                }
                else {
                    currentDisplayedString += " 5";
                    actualIndex += 2;
                }
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 6: { //6 code
                translationBuffer.add(addIndex++, "6");
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NEG) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.SCINOT)){
                    currentDisplayedString += "6";
                    actualIndex += 1;
                }
                else {
                    currentDisplayedString += " 6";
                    actualIndex += 2;
                }
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 7: { //7 code
                translationBuffer.add(addIndex++, "7");
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NEG) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.SCINOT)){
                    currentDisplayedString += "7";
                    actualIndex += 1;
                }
                else {
                    currentDisplayedString += " 7";
                    actualIndex += 2;
                }
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 8: { //8 code
                translationBuffer.add(addIndex++, "8");
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NEG) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.SCINOT)){
                    currentDisplayedString += "8";
                    actualIndex += 1;
                }
                else {
                    currentDisplayedString += " 8";
                    actualIndex += 2;
                }
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 9: { //9 code
                translationBuffer.add(addIndex++, "9");
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NEG) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.SCINOT)){
                    currentDisplayedString += "9";
                    actualIndex += 1;
                }
                else {
                    currentDisplayedString += " 9";
                    actualIndex += 2;
                }
                MainActivity.currentState = MainActivity.STATE.NUM;
                break;
            }
            case 10: { //addition code
                if (state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.RPAREN)) {
                    translationBuffer.add(addIndex++, "+");
                    translationIndex++;
                    currentDisplayedString += " +";
                    actualIndex += 2;
                    MainActivity.currentState = MainActivity.STATE.BINOP;
                }
                else if (state(MainActivity.STATE.ANSWER)){
                    MainActivity.log("**** Current state is 'Answer'! Using ans...");
                    String temp = "";
                    if (Function.closeToInteger(this.currentAnswer)) temp = Long.toString(Math.round(this.currentAnswer));
                    else temp = Double.toString(Math.round(this.currentAnswer));
                    for (int i = 0; i < temp.length(); i++){
                        String s = new String(new char[]{temp.charAt(i)});
                        translationBuffer.add(addIndex++, s);
                    }
                    translationBuffer.add(addIndex++, "+");
                    translationIndex = temp.length() + 1;
                    actualIndex = temp.length() + 2;
                    currentDisplayedString = temp + " +";
                    MainActivity.currentState = MainActivity.STATE.BINOP;
                }
                else if (state(MainActivity.STATE.ERROR)){

                }
                break;
            }
            case 11: { //subtraction/neg code
                if (state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.RPAREN)) {
                    translationBuffer.add(addIndex++, "-");
                    translationIndex++;
                    currentDisplayedString += " -";
                    actualIndex += 2;
                    MainActivity.currentState = MainActivity.STATE.BINOP;
                }
                else if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.SCINOT) || state(MainActivity.STATE.LPAREN)){ //case for negative...
                    translationBuffer.add(addIndex++, "-");
                    translationIndex++;
                    currentDisplayedString += "-";
                    actualIndex += 1;
                    MainActivity.currentState = MainActivity.STATE.NEG;
                }
                else if (state(MainActivity.STATE.BINOP) || state(MainActivity.STATE.FUNC)){
                    translationBuffer.add(addIndex++, "-");
                    translationIndex++;
                    currentDisplayedString += " -";
                    actualIndex += 2;
                    MainActivity.currentState = MainActivity.STATE.NEG;
                }
                else if (state(MainActivity.STATE.ANSWER)){
                    MainActivity.log("**** Current state is 'Answer'! Using ans...");
                    String temp = "";
                    if (Function.closeToInteger(this.currentAnswer)) temp = Long.toString(Math.round(this.currentAnswer));
                    else temp = Double.toString(Math.round(this.currentAnswer));
                    for (int i = 0; i < temp.length(); i++){
                        String s = new String(new char[]{temp.charAt(i)});
                        translationBuffer.add(addIndex++, s);
                    }
                    translationBuffer.add(addIndex++, "-");
                    translationIndex = temp.length() + 1;
                    actualIndex = temp.length() + 2;
                    currentDisplayedString = temp + " -";
                    MainActivity.currentState = MainActivity.STATE.BINOP;
                }
                else if (state(MainActivity.STATE.ERROR)){

                }
                break;
            }
            case 12: { //multiply code
                if (state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.RPAREN)) {
                    translationBuffer.add(addIndex++, multiply);
                    translationIndex++;
                    currentDisplayedString += " " + multiply;
                    actualIndex += 2;
                    MainActivity.currentState = MainActivity.STATE.BINOP;
                }
                else if (state(MainActivity.STATE.ANSWER)){
                    MainActivity.log("**** Current state is 'Answer'! Using ans...");
                    String temp = "";
                    if (Function.closeToInteger(this.currentAnswer)) temp = Long.toString(Math.round(this.currentAnswer));
                    else temp = Double.toString(Math.round(this.currentAnswer));
                    for (int i = 0; i < temp.length(); i++){
                        String s = new String(new char[]{temp.charAt(i)});
                        translationBuffer.add(addIndex++, s);
                    }
                    translationBuffer.add(addIndex++, multiply);
                    translationIndex = temp.length() + 1;
                    actualIndex = temp.length() + 2;
                    currentDisplayedString = temp + " "+multiply;
                    MainActivity.currentState = MainActivity.STATE.BINOP;
                }
                else if (state(MainActivity.STATE.ERROR)){

                }
                break;
            }
            case 13: { //divide code
                if (state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.RPAREN)) {
                    translationBuffer.add(addIndex++, divide);
                    translationIndex++;
                    currentDisplayedString += " " + divide;
                    actualIndex += 2;
                    MainActivity.currentState = MainActivity.STATE.BINOP;
                }
                else if (state(MainActivity.STATE.ANSWER)){
                    MainActivity.log("**** Current state is 'Answer'! Using ans...");
                    String temp = "";
                    if (Function.closeToInteger(this.currentAnswer)) temp = Long.toString(Math.round(this.currentAnswer));
                    else temp = Double.toString(Math.round(this.currentAnswer));
                    for (int i = 0; i < temp.length(); i++){
                        String s = new String(new char[]{temp.charAt(i)});
                        translationBuffer.add(addIndex++, s);
                    }
                    translationBuffer.add(addIndex++, divide);
                    translationIndex = temp.length() + 1;
                    actualIndex = temp.length() + 2;
                    currentDisplayedString = temp + " "+divide;
                    MainActivity.currentState = MainActivity.STATE.BINOP;
                }
                else if (state(MainActivity.STATE.ERROR)){

                }
                break;
            }
            case 14: { //decimal code
                if (state(MainActivity.STATE.BLANK)  || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR) || state(MainActivity.STATE.BINOP) || state(MainActivity.STATE.FUNC) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.NUM)) {
                    translationBuffer.add(addIndex++, ".");
                    translationIndex++;
                    currentDisplayedString += ".";
                    actualIndex += 1;
                    MainActivity.currentState = MainActivity.STATE.DECIMAL;
                }
                break;
            }
            case 15: { //scientific notation code
                if (state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.RPAREN)){
                    translationBuffer.add(addIndex++, "e");
                    translationIndex++;
                    currentDisplayedString += "e";
                    actualIndex += 1;
                    MainActivity.currentState = MainActivity.STATE.SCINOT;
                }
                break;
            }
            case 16: { //left param code
                translationBuffer.add(addIndex++, "(");
                translationIndex++;
                if (translationBuffer.size() == 1 ||
                        (state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.POW) || state(MainActivity.STATE.FUNC) || state(MainActivity.STATE.RPAREN) || state(MainActivity.STATE.SUPERSCRIPTMODE))) {
                    currentDisplayedString += "(";
                    actualIndex += 1;
                }
                else {
                    currentDisplayedString += " (";
                    actualIndex += 2;
                }
                MainActivity.currentState = MainActivity.STATE.LPAREN;
                break;
            }
            case 17: { //right param code
                translationBuffer.add(addIndex++, ")");
                translationIndex++;
                if (translationBuffer.size() == 1 ||
                        (state(MainActivity.STATE.RPAREN) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM ))) {
                    currentDisplayedString += ")";
                    actualIndex += 1;
                }
                else {
                    currentDisplayedString += " )";
                    actualIndex += 2;
                }
                MainActivity.currentState = MainActivity.STATE.RPAREN;
                break;
            }
            case 18: { //neg code //probably won't be used

                break;
            }
            case 19: { // super zero code
                translationBuffer.add(addIndex++, superZero);
                translationIndex++;
                currentDisplayedString += superZero;
                actualIndex += 1;
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 20: { //super one code
                translationBuffer.add(addIndex++, superOne);
                translationIndex++;
                currentDisplayedString += superOne;
                actualIndex += 1;
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 21: { //super two code
                translationBuffer.add(addIndex++, superTwo);
                translationIndex++;
                currentDisplayedString += superTwo;
                actualIndex += 1;
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 22: { //super three code
                translationBuffer.add(addIndex++, superThree);
                translationIndex++;
                currentDisplayedString += superThree;
                actualIndex += 1;
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 23: { //super four code
                translationBuffer.add(addIndex++, superFour);
                translationIndex++;
                currentDisplayedString += superFour;
                actualIndex += 1;
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 24: { //super five code
                translationBuffer.add(addIndex++, superFive);
                translationIndex++;
                currentDisplayedString += superFive;
                actualIndex += 1;
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 25: { //super six code
                translationBuffer.add(addIndex++, superSix);
                translationIndex++;
                currentDisplayedString += superSix;
                actualIndex += 1;
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 26: { //super seven code
                translationBuffer.add(addIndex++, superSeven);
                translationIndex++;
                currentDisplayedString += superSeven;
                actualIndex += 1;
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 27: { //super eight code
                translationBuffer.add(addIndex++, superEight);
                translationIndex++;
                currentDisplayedString += superEight;
                actualIndex += 1;
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 28: { //super nine code
                translationBuffer.add(addIndex++, superNine);
                translationIndex++;
                currentDisplayedString += superNine;
                actualIndex += 1;
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 29: { //super negative code
                translationBuffer.add(addIndex++, superNeg);
                translationIndex++;
                currentDisplayedString += superNeg;
                actualIndex += 1;
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 30: { //super decimal code
                translationBuffer.add(addIndex++, superDecimal);
                translationIndex++;
                currentDisplayedString += superDecimal;
                actualIndex += 1;
                MainActivity.currentState = MainActivity.STATE.SUPERSCRIPTMODE;
                break;
            }
            case 31: { //ln code
                translationBuffer.add(addIndex++, "ln");
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR)|| state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.RPAREN)) {
                    currentDisplayedString += "ln";
                    actualIndex += 2;
                }
                else {
                    currentDisplayedString += " ln";
                    actualIndex += 3;
                }
                MainActivity.currentState = MainActivity.STATE.FUNC;
                break;
            }
            case 32: { //log code
                translationBuffer.add(addIndex++, "log");
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.RPAREN)) {
                    currentDisplayedString += "log";
                    actualIndex += 3;
                }
                else {
                    currentDisplayedString += " log";
                    actualIndex += 4;
                }
                MainActivity.currentState = MainActivity.STATE.FUNC;
                break;
            }
            case 33: { //square root code
                translationBuffer.add(addIndex++, root);
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR)|| state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.RPAREN)) {
                    currentDisplayedString += root;
                    actualIndex += 1;
                }
                else {
                    currentDisplayedString += " "+root;
                    actualIndex += 2;
                }
                MainActivity.currentState = MainActivity.STATE.FUNC;
                break;
            }
            case 34: { //cube root code
                translationBuffer.add(addIndex++, superThree+root);
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.RPAREN)) {
                    currentDisplayedString += superThree+root;
                    actualIndex += 2;
                }
                else {
                    currentDisplayedString += " "+superThree+root;
                    actualIndex += 3;
                }
                MainActivity.currentState = MainActivity.STATE.FUNC;
                break;
            }
            case 35: { //square code
                if (state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.RPAREN)) {
                    translationBuffer.add(addIndex++, superTwo);
                    translationIndex++;
                    currentDisplayedString += superTwo;
                    actualIndex += 1;
                    MainActivity.currentState = MainActivity.STATE.NUM;
                }
                break;
            }
            case 36: { //cube code
                if (state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.RPAREN)) {
                    translationBuffer.add(addIndex++, superThree);
                    translationIndex++;
                    currentDisplayedString += superThree;
                    actualIndex += 1;
                    MainActivity.currentState = MainActivity.STATE.NUM;
                }
                break;
            }
            case 37: { //e code
                translationBuffer.add(addIndex++, italicE);
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR)|| state(MainActivity.STATE.NEG) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.RPAREN) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM)) {
                    currentDisplayedString += italicE;
                    actualIndex += 1;
                }
                else {
                    currentDisplayedString += " "+italicE;
                    actualIndex += 2;
                }
                MainActivity.currentState = MainActivity.STATE.NUMSYM;
                break;
            }
            case 38: { //mod code
                if (state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.RPAREN)) {
                    translationBuffer.add(addIndex++, "mod");
                    translationIndex++;
                    currentDisplayedString += " mod";
                    actualIndex += 4;
                    MainActivity.currentState = MainActivity.STATE.BINOP;
                }
                else if (state(MainActivity.STATE.ANSWER)){
                    MainActivity.log("**** Current state is 'Answer'! Using ans...");
                    String temp = "";
                    if (Function.closeToInteger(this.currentAnswer)) temp = Long.toString(Math.round(this.currentAnswer));
                    else temp = Double.toString(Math.round(this.currentAnswer));
                    for (int i = 0; i < temp.length(); i++){
                        String s = new String(new char[]{temp.charAt(i)});
                        translationBuffer.add(addIndex++, s);
                    }
                    translationBuffer.add(addIndex++, "mod");
                    translationIndex = temp.length() + 1;
                    actualIndex = temp.length() + 4;
                    currentDisplayedString = temp + " mod";
                    MainActivity.currentState = MainActivity.STATE.BINOP;
                }
                else if (state(MainActivity.STATE.ERROR)){

                }
                break;
            }
            case 39: { //percent code
                if (state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.RPAREN)) {
                    translationBuffer.add(addIndex++, "%");
                    translationIndex++;
                    currentDisplayedString += " %";
                    actualIndex += 2;
                    MainActivity.currentState = MainActivity.STATE.BINOP;
                }
                else if (state(MainActivity.STATE.ANSWER)){
                    MainActivity.log("**** Current state is 'Answer'! Using ans...");
                    String temp = "";
                    if (Function.closeToInteger(this.currentAnswer)) temp = Long.toString(Math.round(this.currentAnswer));
                    else temp = Double.toString(Math.round(this.currentAnswer));
                    for (int i = 0; i < temp.length(); i++){
                        String s = new String(new char[]{temp.charAt(i)});
                        translationBuffer.add(addIndex++, s);
                    }
                    translationBuffer.add(addIndex++, "%");
                    translationIndex = temp.length() + 1;
                    actualIndex = temp.length() + 2;
                    currentDisplayedString = temp + " %";
                    MainActivity.currentState = MainActivity.STATE.BINOP;
                }
                else if (state(MainActivity.STATE.ERROR)){

                }
                break;
            }
            case 40: { //pi code
                translationBuffer.add(addIndex++, pi);
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR) || state(MainActivity.STATE.NEG) || state(MainActivity.STATE.RPAREN) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM)) {
                    currentDisplayedString += pi;
                    actualIndex += 1;
                }
                else {
                    currentDisplayedString += " "+pi;
                    actualIndex += 2;
                }
                MainActivity.currentState = MainActivity.STATE.NUMSYM;
                break;
            }
            case 41: { //arcsin code
                translationBuffer.add(addIndex++, "sin"+superNeg+superOne);
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.RPAREN)) {
                    currentDisplayedString += "sin"+superNeg+superOne;
                    actualIndex += 5;
                }
                else {
                    currentDisplayedString += " sin"+superNeg+superOne;
                    actualIndex += 6;
                }
                MainActivity.currentState = MainActivity.STATE.FUNC;
                break;
            }
            case 42: { //sin code
                translationBuffer.add(addIndex++, "sin");
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.RPAREN)) {
                    currentDisplayedString += "sin";
                    actualIndex += 3;
                }
                else {
                    currentDisplayedString += " sin";
                    actualIndex += 4;
                }
                MainActivity.currentState = MainActivity.STATE.FUNC;
                break;
            }
            case 43: { //arccos code
                translationBuffer.add(addIndex++, "cos"+superNeg+superOne);
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.RPAREN)) {
                    currentDisplayedString += "cos"+superNeg+superOne;
                    actualIndex += 5;
                }
                else {
                    currentDisplayedString += " cos"+superNeg+superOne;
                    actualIndex += 6;
                }
                MainActivity.currentState = MainActivity.STATE.FUNC;
                break;
            }
            case 44: { //cos code
                translationBuffer.add(addIndex++, "cos");
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.RPAREN)) {
                    currentDisplayedString += "cos";
                    actualIndex += 3;
                }
                else {
                    currentDisplayedString += " cos";
                    actualIndex += 4;
                }
                MainActivity.currentState = MainActivity.STATE.FUNC;
                break;
            }
            case 45: { //arctan code
                translationBuffer.add(addIndex++, "tan"+superNeg+superOne);
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.RPAREN)) {
                    currentDisplayedString += "tan"+superNeg+superOne;
                    actualIndex += 5;
                }
                else {
                    currentDisplayedString += " tan"+superNeg+superOne;
                    actualIndex += 6;
                }
                MainActivity.currentState = MainActivity.STATE.FUNC;
                break;
            }
            case 46: {  //tan code
                translationBuffer.add(addIndex++, "tan");
                translationIndex++;
                if (state(MainActivity.STATE.BLANK) || state(MainActivity.STATE.ANSWER) || state(MainActivity.STATE.ERROR) || state(MainActivity.STATE.NUM) || state(MainActivity.STATE.NUMSYM) || state(MainActivity.STATE.LPAREN) || state(MainActivity.STATE.RPAREN)) {
                    currentDisplayedString += "tan";
                    actualIndex += 3;
                }
                else {
                    currentDisplayedString += " tan";
                    actualIndex += 4;
                }
                MainActivity.currentState = MainActivity.STATE.FUNC;
                break;
            }
            case 47: { //rec code
                translationBuffer.add(addIndex++, "1");
                translationBuffer.add(addIndex++, divide);
                translationBuffer.add(addIndex++, "~");
                translationIndex++;
                if (translationBuffer.size() == 3 ||
                        state(MainActivity.STATE.RPAREN) || state(MainActivity.STATE.LPAREN)) {
                    currentDisplayedString += "1 "+divide;
                    actualIndex += 3;
                }
                else {
                    currentDisplayedString += " 1 "+divide;
                    actualIndex += 4;
                }
                MainActivity.currentState = MainActivity.STATE.BINOP;
                break;
            }
        }
        MainActivity.log("New STATE: "+MainActivity.currentState);
        MainActivity.log("Translation Index: " + translationIndex);
        MainActivity.log("Add Index: "+addIndex);
        MainActivity.log("Actual Index: "+actualIndex);
        MainActivity.log("translationBuffer size: "+translationBuffer.size());


        functionalBuffer = translationToFunctional(translationBuffer);
        bigET.setText(Html.fromHtml(currentDisplayedString));
        smallET.setText(Html.fromHtml(currentDisplayedString));
        MainActivity.log("Big Text: '" + bigET.getText().toString() + "'");
        MainActivity.log("Big Text length (Editable): " + bigET.getText().length());
        bigET.setSelection(actualIndex);
        MainActivity.log("*** End of addToBuffer.");
    }
    public void delete(){

    }
    public void reset(){
        MainActivity.log("*** reset called.");
        this.translationIndex = 0;
        this.actualIndex = 0;
        this.addIndex = 0;
        this.translationBuffer = new ArrayList();
        this.functionalBuffer = new ArrayList();
        this.bigET.setSelection(0);
        this.smallET.setSelection(0);
        this.bigET.setText("");
        this.smallET.setText("");
        this.currentDisplayedString = new String();
        MainActivity.currentState = MainActivity.STATE.BLANK;
        MainActivity.log("*** End of reset.");
    }
    public void set(ArrayList<String> nFunctionalBuffer){

    }
    public void shiftLeft(){

    }
    public void shiftRight(){

    }
    /**
     * translates an arraylist of tokens into a String to represent to the user
     * @param tokens
     * @return
     */
    public static String tokensToString(ArrayList<String> tokens){
        /*
        String nBuf = "";
        for (int i = 0; i < tokens.size(); i++){
            nBuf += tokens.get(i);
        }
        return nBuf;
        */
        String ret = new String();

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
    public boolean isNumber(String s){
        try {
            double d = Double.parseDouble(s.trim());
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }
    public boolean isNumericSymbol(String s){
        String cp = s.trim();
        if (cp.equals(pi) || cp.equals(italicE) ||
                cp.equals("pi") || cp.equals("scinot")) return true;
        else return false;
    }
    public boolean isBinaryOperator(String s){
        String cp = s.trim();
        if (cp.equals(divide) || cp.equals(multiply) || cp.equals("+") || cp.equals("-") || cp.equals("%") || cp.equals("mod") ||
                cp.equals("/") || cp.equals("*")) return true;
        else return false;
    }
    public boolean isFunction(String s){
        String cp = s.trim();
        if (cp.equals("ln") || cp.equals("log") || cp.equals("sin") || cp.equals("cos") || cp.equals("tan") || cp.equals("sin"+superNeg+superOne) || cp.equals("cos"+superNeg+superOne) || cp.equals("tan"+superNeg+superOne) || cp.equals(root) || cp.equals(superThree+root)
                ) return true;
        else return false;
    }
    public boolean isNumericSup(String s){
        String cp = s.trim();
        if (cp.equals(superZero) || cp.equals(superOne) || cp.equals(superTwo) || cp.equals(superThree) || cp.equals(superFour) || cp.equals(superFive) || cp.equals(superSix) || cp.equals(superSeven) || cp.equals(superEight) || cp.equals(superNine)) return true;
        else return false;
    }
    public boolean state(MainActivity.STATE s){
        return MainActivity.currentState == s;
    }

    public ArrayList getFunctionalBuffer(){
        return this.functionalBuffer;
    }
    public void setFunctionalBuffer(ArrayList<String> l){
        this.functionalBuffer = l;
    }
    public void setTranslationBuffer (ArrayList s){
        this.translationBuffer = s;
    }
    public ArrayList getTranslationBuffer(){
        return this.translationBuffer;
    }
    public void setPreviousBuffer (ArrayList s){
        this.previousBuffer = s;
    }
    public void setCurrentDisplayedString (String s){
        this.currentDisplayedString = s;
    }
    public String getCurrentDisplayedString () {
        return this.currentDisplayedString;
    }
    public ArrayList getPreviousBuffer(){
        return this.previousBuffer;
    }
    public void setActualIndex(int v){
        if (v >= 0)
        this.actualIndex = v;
    }
    public double getCurrentAnswer(){
        return this.currentAnswer;
    }
    public void setCurrentAnswer(double d){
        this.currentAnswer = d;
    }
    public int getActualIndex(){
        return this.actualIndex;
    }
    public void setTranslationIndex (int s){
        if (s >= 0) this.translationIndex = s;
    }
    public int getTranslationIndex (){
        return this.translationIndex;
    }
}