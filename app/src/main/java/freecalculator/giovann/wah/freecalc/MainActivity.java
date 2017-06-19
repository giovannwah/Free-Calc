package freecalculator.giovann.wah.freecalc;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.session.MediaSession;
import android.os.Build;
import android.os.Bundle;

import android.os.Vibrator;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.concurrent.atomic.AtomicInteger;

import freecalc.execution.exception.*;
import freecalc.execution.*;
import freecalc.parameter.*;
import freecalc.parameter.function.*;

public class MainActivity extends FragmentActivity implements SlideButtonFragment.SlideButtonInterface {
    protected static final int API_LEVEL = Build.VERSION.SDK_INT;
    protected static enum STATE {BLANK, NUM, NUMSYM, DECIMAL, RPAREN, LPAREN, BINOP, FUNC, POW, SCINOT, ANSWER, ERROR, SUPERSCRIPTMODE, XSUPERMODE, NEG};
    protected static STATE currentState;
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    protected static int NUM_ITEMS;
    protected static OnClickListener clickListener;
    protected static OnTouchListener touchListener;
    protected static Context context;

    protected int SCREENWIDTH;
    protected int SCREENHEIGHT;
    protected int TOTAL_MARGINS;
    protected int ButtonHeight;
    protected int ButtonWidth;

    protected EditText bigEditText;
    protected EditText smallEditText;

    private View decorView;
    private AdView ad;

    private static ViewPager mPager;
    private static MyAdapter mAdapter;

    private static int m_plus;
    private static int mr;
    private static int mc;
    private static int left_paren;
    private static int right_paren;
    private static int var_plus;
    private static int varr;
    private static int varc;
    private static int ans;
    private static int left_arrow;
    private static int right_arrow;
    private static int percent;
    private static int sin;
    private static int cos;
    private static int tan;
    private static int reciprocal;
    private static int pi;
    private static int asin;
    private static int acos;
    private static int atan;
    private static int mod;
    private static int sci_not;
    private static int nrt;
    private static int sqrt;
    private static int cbrt;
    private static int log;
    private static int e;
    private static int power;
    private static int square;
    private static int cube;
    private static int ln;
    private static int clear;
    private static int seven;
    private static int eight;
    private static int nine;
    private static int divide;
    private static int back;
    private static int four;
    private static int five;
    private static int six;
    private static int multiply;
    private static int equals;
    private static int one;
    private static int two;
    private static int three;
    private static int minus;
    private static int decimal;
    private static int zero;
    private static int ex;
    private static int plus;

    protected static ArrayList<Button> pageOneButtons;
    protected static ArrayList<Button> pageTwoButtons;
    protected static ArrayList<Button> pageThreeButtons;
    private static ArrayList<Button> Numbers;
    private static ArrayList<Button> Basic;
    private static Button bigButton;
    private static CalculatorScreen screen;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("xxx onCreate() called");
        context = this;

        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_main, null);

        SCREENWIDTH = getResources().getDisplayMetrics().widthPixels;
        SCREENHEIGHT = getResources().getDisplayMetrics().heightPixels;
        log ("Main Activity says screen width is " + SCREENWIDTH);
        log("Main Activity says screen height is "+SCREENHEIGHT);
        NUM_ITEMS = 3;

        TOTAL_MARGINS = dpToPixel(Math.round(SCREENWIDTH/160));
        log("Margins: " + TOTAL_MARGINS);
        ButtonWidth = (int) ((SCREENWIDTH - ((5 * TOTAL_MARGINS)+(2 * dpToPixel(4))))/5);
        log("Button Width: " + ButtonWidth);
        Data.setMargin(this.TOTAL_MARGINS);
        Data.setBWidth(this.ButtonWidth);

        //hide status bar
        if (API_LEVEL >= 19) decorView = getWindow().getDecorView();
        hideUI();
        setContentView(layout);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

/*        bigEditText = (EditText) this.findViewById(R.id.bigText);
        bigEditText.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/ColabThi.otf"));
        bigEditText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return true;
            }
        });
        smallEditText = (EditText) this.findViewById(R.id.smallText);
        smallEditText.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/ColabThi.otf"));
*/
        bigEditText = null;
        smallEditText = null;

        //set the current state of the calculator
        currentState = STATE.BLANK;

//        LinearLayout textContainer = (LinearLayout) this.findViewById(R.id.allTextContainer);
//        FrameLayout.LayoutParams p = (FrameLayout.LayoutParams) textContainer.getLayoutParams();
//        p.setMargins(TOTAL_MARGINS, 0, TOTAL_MARGINS, 0);

        final FrameLayout bContainer = (FrameLayout) this.findViewById(R.id.buttonContainer);
        final TableRow row1 = (TableRow) this.findViewById(R.id.tableRow1);
        final TableRow row2 = (TableRow) this.findViewById(R.id.tableRow2);
        final TableRow row3 = (TableRow) this.findViewById(R.id.tableRow3);
        final TableRow row4 = (TableRow) this.findViewById(R.id.tableRow4);
        final LinearLayout lowerButtons = (LinearLayout) this.findViewById(R.id.lowerButtons);

        screen = new CalculatorScreen(
                72,
                36,
                R.color.lightgray,
                R.color.lightgray,
                R.color.green,
                this,
                (LinearLayout) this.findViewById(R.id.bigText),
                (LinearLayout) this.findViewById(R.id.smallText),
                (HorizontalScrollView) this.findViewById(R.id.bigTextScroll),
                (HorizontalScrollView) this.findViewById(R.id.smallTextScroll));

        if (bContainer.getViewTreeObserver().isAlive()){
            log("Tree is ALIVE!!!");
            bContainer.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

                @SuppressWarnings("deprecation")
                @Override
                public void onGlobalLayout() {
                    Data.setContainerHeight(bContainer.getHeight());
                    log("buttonContainer Height: " + Data.getContainerHeight());
                    int buttonHeight = (Data.getContainerHeight() / 6) - TOTAL_MARGINS;
                    int bigButtonHeight = (buttonHeight * 2) + TOTAL_MARGINS;
                    Data.setBHeight(buttonHeight);

                    TableRow.LayoutParams params = new TableRow.LayoutParams(
                            Data.getBWidth(),
                            Data.getBHeight()
                    );
                    LinearLayout.LayoutParams bigButtonParams = new LinearLayout.LayoutParams(
                            Data.getBWidth(),
                            bigButtonHeight
                    );
                    params.setMargins(TOTAL_MARGINS / 2, TOTAL_MARGINS / 2, TOTAL_MARGINS / 2, TOTAL_MARGINS / 2);
                    bigButtonParams.setMargins(TOTAL_MARGINS / 2, TOTAL_MARGINS / 2, TOTAL_MARGINS / 2, TOTAL_MARGINS / 2);
                    clickListener = new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            performClickAction(view);
                        }
                    };
                    touchListener = new OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            performTouchAction(view, motionEvent);
                            return false;
                        }
                    };
                    //initiate buttons on the slider
                    //initiateSliderButtons();

                    Button b1 = new Button(MainActivity.this);
                    b1.setText(R.string.divide);
                    b1.setId(getUniqueId(b1));
                    divide = b1.getId();

                    Button b2 = new Button(MainActivity.this);
                    b2.setText(R.string.seven);
                    b2.setId(getUniqueId(b2));
                    seven = b2.getId();

                    Button b3 = new Button(MainActivity.this);
                    b3.setText(R.string.eight);
                    b3.setId(getUniqueId(b3));
                    eight = b3.getId();

                    Button b4 = new Button(MainActivity.this);
                    b4.setText(R.string.nine);
                    b4.setId(getUniqueId(b4));
                    nine = b4.getId();

                    Button b5 = new Button(MainActivity.this);
                    b5.setText(R.string.clear);
                    b5.setId(getUniqueId(b5));
                    clear = b5.getId();

                    Button b6 = new Button(MainActivity.this);
                    b6.setText(R.string.multiply);
                    b6.setId(getUniqueId(b6));
                    multiply = b6.getId();

                    Button b7 = new Button(MainActivity.this);
                    b7.setText(R.string.four);
                    b7.setId(getUniqueId(b7));
                    four = b7.getId();

                    Button b8 = new Button(MainActivity.this);
                    b8.setText(R.string.five);
                    b8.setId(getUniqueId(b8));
                    five = b8.getId();

                    Button b9 = new Button(MainActivity.this);
                    b9.setText(R.string.six);
                    b9.setId(getUniqueId(b9));
                    six = b9.getId();

                    Button b0 = new Button(MainActivity.this);
                    b0.setText(R.string.back);
                    b0.setId(getUniqueId(b0));
                    back = b0.getId();

                    bigButton = new Button(MainActivity.this);
                    bigButton.setText(R.string.equals);
                    bigButton.setId(getUniqueId(bigButton));
                    equals = bigButton.getId();

                    Button b01 = new Button(MainActivity.this);
                    b01.setText(R.string.minus);
                    b01.setId(getUniqueId(b01));
                    minus = b01.getId();

                    Button b02 = new Button(MainActivity.this);
                    b02.setText(R.string.one);
                    b02.setId(getUniqueId(b02));
                    one = b02.getId();

                    Button b03 = new Button(MainActivity.this);
                    b03.setText(R.string.two);
                    b03.setId(getUniqueId(b03));
                    two = b03.getId();

                    Button b04 = new Button(MainActivity.this);
                    b04.setText(R.string.three);
                    b04.setId(getUniqueId(b04));
                    three = b04.getId();

                    Button b05 = new Button(MainActivity.this);
                    b05.setText(R.string.plus);
                    b05.setId(getUniqueId(b05));
                    plus = b05.getId();

                    Button b06 = new Button(MainActivity.this);
                    b06.setText(R.string.ans);
                    b06.setId(getUniqueId(b06));
                    ans = b06.getId();

                    Button b07 = new Button(MainActivity.this);
                    b07.setText(R.string.zero);
                    b07.setId(getUniqueId(b07));
                    zero = b07.getId();

                    Button b08 = new Button(MainActivity.this);
                    b08.setText(R.string.decimal);
                    b08.setId(getUniqueId(b08));
                    decimal = b08.getId();

                    Numbers = new ArrayList<Button>();
                    Basic = new ArrayList<Button>();
                    Numbers.add(b07);
                    Numbers.add(b02);
                    Numbers.add(b03);
                    Numbers.add(b04);
                    Numbers.add(b7);
                    Numbers.add(b8);
                    Numbers.add(b9);
                    Numbers.add(b2);
                    Numbers.add(b3);
                    Numbers.add(b4);

                    Basic.add(bigButton);
                    Basic.add(b1);
                    Basic.add(b5);
                    Basic.add(b6);
                    Basic.add(b0);
                    Basic.add(b01);
                    Basic.add(b05);
                    Basic.add(b06);
                    Basic.add(b08);

                    //initiate slider buttons and change other settings.
                    initiateSliderButtons();
                    editAllButtons();

                    row1.addView(b1, params);
                    row1.addView(b2, params);
                    row1.addView(b3, params);
                    row1.addView(b4, params);
                    row1.addView(b5, params);

                    row2.addView(b6, params);
                    row2.addView(b7, params);
                    row2.addView(b8, params);
                    row2.addView(b9, params);
                    row2.addView(b0, params);

                    lowerButtons.addView(bigButton, bigButtonParams);

                    row3.addView(b01, params);
                    row3.addView(b02, params);
                    row3.addView(b03, params);
                    row3.addView(b04, params);

                    row4.addView(b05, params);
                    row4.addView(b06, params);
                    row4.addView(b07, params);
                    row4.addView(b08, params);


                    mAdapter = new MyAdapter(getSupportFragmentManager());
                    mPager = (ViewPager) findViewById(R.id.buttonPager);
                    mPager.setAdapter(mAdapter);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        bContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    else
                        bContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

            });

        }

        //add ad
        ad = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("C44A1D858D3A33376F89D449A3699854")
                .build();
        ad.loadAd(adRequest);
        log("xxx End of OnCreate ");
    }
    //END OF ONCREATE

    /**
     * Adds touch and click listeners to all buttons
     */
    public void editAllButtons() {
        styleStaticButtons();
        for (Button b : Basic){
            b.setOnClickListener(clickListener);
            b.setOnTouchListener(touchListener);
            b.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/ColabThi.otf"));
            b.setAllCaps(false);
            b.setTextSize(22);
        }
        for (Button b : Numbers) {
            b.setOnClickListener(clickListener);
            b.setOnTouchListener(touchListener);
            b.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/ColabThi.otf"));
            b.setAllCaps(false);
            b.setTextSize(22);
        }
        for (Button b : pageOneButtons) {
            b.setOnClickListener(clickListener);
            b.setOnTouchListener(touchListener);
            b.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/ColabThi.otf"));
            b.setAllCaps(false);
            b.setTextSize(22);
        }
        for (Button b : pageTwoButtons) {
            b.setOnClickListener(clickListener);
            b.setOnTouchListener(touchListener);
            b.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/ColabThi.otf"));
            b.setAllCaps(false);
            b.setTextSize(22);
        }
        for (Button b : pageThreeButtons) {
            b.setOnClickListener(clickListener);
            b.setOnTouchListener(touchListener);
            b.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/ColabThi.otf"));
            b.setAllCaps(false);
            b.setTextSize(22);
        }
    }
    /**
     * Instantiates all slider buttons and puts them in arraylists for each page.
     */
    public static void initiateSliderButtons(){
        pageOneButtons = new ArrayList<Button>();
        pageTwoButtons = new ArrayList<Button>();
        pageThreeButtons = new ArrayList<Button>();

        Button b1 = new Button(context);
        b1.setText(R.string.m_plus);
        b1.setId(getUniqueId(b1));
        m_plus = b1.getId();

        Button b2 = new Button(context);
        b2.setText(R.string.mr);
        b2.setId(getUniqueId(b2));
        mr = b2.getId();

        Button b3 = new Button(context);
        b3.setText(R.string.mc);
        b3.setId(getUniqueId(b3));
        mc = b3.getId();

        Button b4 = new Button(context);
        b4.setText(R.string.left_paren);
        b4.setId(getUniqueId(b4));
        left_paren = b4.getId();

        Button b5 = new Button(context);
        b5.setText(R.string.right_paren);
        b5.setId(getUniqueId(b5));
        right_paren = b5.getId();

        Button b6 = new Button(context);
        b6.setText(R.string.var_plus);
        b6.setId(getUniqueId(b6));
        var_plus = b6.getId();

        Button b7 = new Button(context);
        b7.setText(R.string.varr);
        b7.setId(getUniqueId(b7));
        varr = b7.getId();

        Button b8 = new Button(context);
        b8.setText(R.string.varc);
        b8.setId(getUniqueId(b8));
        varc = b8.getId();

        Button b9 = new Button(context);
        b9.setText(R.string.left_arrow);
        b9.setId(getUniqueId(b9));
        left_arrow = b9.getId();

        Button b0 = new Button(context);
        b0.setText(R.string.right_arrow);
        b0.setId(getUniqueId(b0));
        right_arrow = b0.getId();

        pageOneButtons.add(b1);
        pageOneButtons.add(b2);
        pageOneButtons.add(b3);
        pageOneButtons.add(b4);
        pageOneButtons.add(b5);
        pageOneButtons.add(b6);
        pageOneButtons.add(b7);
        pageOneButtons.add(b8);
        pageOneButtons.add(b9);
        pageOneButtons.add(b0);

        Button b01 = new Button(context);
        b01.setText(R.string.ln);
        b01.setId(getUniqueId(b01));
        ln = b01.getId();

        Button b02 = new Button(context);
        b02.setText(Html.fromHtml("&radic;<span style=\"text-decoration: overline;\">x</span>"));
        b02.setId(getUniqueId(b02));
        sqrt = b02.getId();

        Button b03 = new Button(context);
        b03.setText(Html.fromHtml("<sup><small>3</small></sup>&radic;<span style=\"text-decoration: overline;\">x</span>"));
        b03.setId(getUniqueId(b03));
        cbrt = b03.getId();

        Button b04 = new Button(context);
        b04.setText(Html.fromHtml("<sup><small>n</small></sup>&radic;<span style=\"text-decoration: overline;\">x</span>"));
        b04.setId(getUniqueId(b04));
        nrt = b04.getId();

        Button b05 = new Button(context);
        b05.setText(Html.fromHtml("<i>e</i>"));
        b05.setId(getUniqueId(b05));
        e = b05.getId();

        Button b06 = new Button(context);
        b06.setText(R.string.log);
        b06.setId(getUniqueId(b06));
        log = b06.getId();

        Button b07 = new Button(context);
        b07.setText(Html.fromHtml("x<sup><small>2</small></sup>"));
        b07.setId(getUniqueId(b07));
        square = b07.getId();

        Button b08 = new Button(context);
        b08.setText(Html.fromHtml("x<sup><small>3</small></sup>"));
        b08.setId(getUniqueId(b08));
        cube = b08.getId();

        Button b09 = new Button(context);
        b09.setText(Html.fromHtml("x<sup><small>n</small></sup>"));
        b09.setId(getUniqueId(b09));
        power = b09.getId();

        Button b00 = new Button(context);
        b00.setText(R.string.sci_notation);
        b00.setId(getUniqueId(b00));
        sci_not = b00.getId();

        pageTwoButtons.add(b01);
        pageTwoButtons.add(b02);
        pageTwoButtons.add(b03);
        pageTwoButtons.add(b04);
        pageTwoButtons.add(b05);
        pageTwoButtons.add(b06);
        pageTwoButtons.add(b07);
        pageTwoButtons.add(b08);
        pageTwoButtons.add(b09);
        pageTwoButtons.add(b00);

        Button b10 = new Button(context);
        b10.setText(R.string.percent);
        b10.setId(getUniqueId(b10));
        percent = b10.getId();

        Button b20 = new Button(context);
        b20.setText(Html.fromHtml("sin<sup><small>-1</small></sup>"));
        b20.setId(getUniqueId(b20));
        asin = b20.getId();

        Button b30 = new Button(context);
        b30.setText(Html.fromHtml("cos<sup><small>-1</small></sup>"));
        b30.setId(getUniqueId(b30));
        acos = b30.getId();

        Button b40 = new Button(context);
        b40.setText(Html.fromHtml("tan<sup><small>-1</small></sup>"));
        b40.setId(getUniqueId(b40));
        atan = b40.getId();

        Button b50 = new Button(context);
        b50.setText(R.string.reciprocal);
        b50.setId(getUniqueId(b50));
        reciprocal = b50.getId();

        Button b60 = new Button(context);
        b60.setText(R.string.pi);
        b60.setId(getUniqueId(b60));
        pi = b60.getId();

        Button b70 = new Button(context);
        b70.setText(R.string.sin);
        b70.setId(getUniqueId(b70));
        sin = b70.getId();

        Button b80 = new Button(context);
        b80.setText(R.string.cos);
        b80.setId(getUniqueId(b80));
        cos = b80.getId();

        Button b90 = new Button(context);
        b90.setText(R.string.tan);
        b90.setId(getUniqueId(b90));
        tan = b90.getId();

        Button b000 = new Button(context);
        b000.setText(R.string.mod);
        b000.setId(getUniqueId(b000));
        mod = b000.getId();

        pageThreeButtons.add(b10);
        pageThreeButtons.add(b20);
        pageThreeButtons.add(b30);
        pageThreeButtons.add(b40);
        pageThreeButtons.add(b50);
        pageThreeButtons.add(b60);
        pageThreeButtons.add(b70);
        pageThreeButtons.add(b80);
        pageThreeButtons.add(b90);
        pageThreeButtons.add(b000);

        for (Button b : pageOneButtons){
        //    b.setLines(1);
            b.setCompoundDrawablePadding(0);
        }
        for (Button b : pageTwoButtons){
        //    b.setLines(1);
            b.setCompoundDrawablePadding(0);
        }
        for (Button b : pageThreeButtons){
        //    b.setLines(1);
            b.setCompoundDrawablePadding(0);
        }
    }

    /**
     * Handles touch actions in the calculator
     * @param v
     */
    public void performTouchAction(View v, MotionEvent e){
        if (e.getAction() == MotionEvent.ACTION_UP) {
            Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vb.vibrate(25);
        }
    }

    /**
     * Handles click actions in the calculator
     * @param v
     */
    public void performClickAction(View v){
        int j = v.getId();
        if(j == m_plus) {
            log("m+ button has been pressed.");
        }
        else if(j == mr) {
            log("mr button has been pressed.");
        }
        else if(j == mc) {
            log("mc button has been pressed.");
        }
        else if(j == left_paren) {
            log("left paren button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            screen.add(16);
        }
        else if (j == right_paren) {
            log("right paren button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            screen.add(17);
        }
        else if (j == var_plus) {
            log("var+ button has been pressed.");
        }
        else if (j == varr){
            log("varr button has been pressed.");
        }
        else if (j == varc) {
            log("varc button has been pressed.");
        }
        else if (j == left_arrow) {
            log("left arrow button has been pressed.");
            screen.shiftLeft();
        }
        else if (j == right_arrow){
            log("right arrow button has been pressed.");
            screen.shiftRight();
        }
        else if (j == percent) {
            log("percent button has been pressed.");
            screen.add(39);
        }
        else if (j == sin) {
            log("sin button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            screen.add(42);
        }
        else if (j == cos) {
            log("cos button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            screen.add(44);
        }
        else if (j == tan) {
            log("tan button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            screen.add(46);
        }
        else if (j == reciprocal) {
            log("reciprocal button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            screen.add(47); //need to change the second string to divide symbol
        }
        else if (j == pi) {
            log("pi button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            screen.add(40);
        }
        else if (j == asin) {
            log("asin button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            screen.add(41);
        }
        else if (j == acos) {
            log("acos button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            screen.add(43);
        }
        else if (j == atan) {
            log("atan button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            screen.add(45);
        }
        else if (j == mod) {
            log("mod button has been pressed.");
            screen.add(38);
        }
        else if (j == sci_not) {
            log("scientific notation button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            screen.add(15);
        }
        else if (j == nrt) {
            log("nth root button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            screen.add(48);
        }
        else if (j == sqrt) {
            log("square root button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            screen.add(33);
        }
        else if (j == cbrt) {
            log("cube root button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            screen.add(34);
        }
        else if (j == log) {
            log("log button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            screen.add(32);
        }
        else if (j == e) {
            log("e button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            screen.add(37);
        }
        else if (j == power) {
            log("power button has been pressed.");
            if (currentState == STATE.SUPERSCRIPTMODE) currentState = STATE.NUM;
            else currentState = STATE.SUPERSCRIPTMODE;
        }
        else if (j == square) {
            log("square button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            screen.add(35);
        }
        else if (j == cube) {
            log("cube button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            screen.add(36);
        }
        else if (j == ln) {
            log("ln button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            screen.add(31);
        }
        else if (j == clear) {
            log("clear button has been pressed.");
            screen.reset();
            currentState = STATE.BLANK;
        }
        else if (j == seven) {
            log("seven button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            if (currentState == STATE.SUPERSCRIPTMODE) screen.add(26);
            else screen.add(7);
        }
        else if (j == eight) {
            log("eight button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            if (currentState == STATE.SUPERSCRIPTMODE) screen.add(27);
            else screen.add(8);
        }
        else if (j == nine) {
            log("nine button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            if (currentState == STATE.SUPERSCRIPTMODE) screen.add(28);
            else screen.add(9);
        }
        else if (j == divide) {
            log("divide button has been pressed.");
            screen.add(13);
        }
        else if (j == back) {
            log("back button has been pressed.");
            screen.delete();
            /*
            if (answerVisible) {
            //    clearScreen();
                answerVisible = false;
                error = false;
                String s = TokenBuffer.tokensToString(previousBuffer);
                bigEditText.setText(s);
                smallEditText.setText(s);
                int cursorPosition = (bigEditText.getText().length());
                bigEditText.setSelection(cursorPosition);
                tokenBuffer = new ArrayList<String>(previousBuffer);
                if (API_LEVEL >= 16) bigEditText.setCursorVisible(true);
            }
            else if (!tokenBuffer.isEmpty()) {
                //go back alter the tokenBuffer and the cursor position
                goBack();

                String nBuf = "";
                for (int i = 0; i < tokenBuffer.size(); i++){
                    nBuf += tokenBuffer.get(i);
                    if (i != tokenBuffer.size()-1) nBuf += " ";
                }
                bigEditText.setText(nBuf);
                smallEditText.setText(nBuf);
                int cursorPosition = (bigEditText.getText().length()) - cursorOffset;
                bigEditText.setSelection(cursorPosition);
                //answerVisible = false;
                //error = false;
            }
            */
        }
        else if (j == four) {
            log("four button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            if (currentState == STATE.SUPERSCRIPTMODE) screen.add(23);
            else screen.add(4);
        }
        else if (j == five) {
            log("five button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            if (currentState == STATE.SUPERSCRIPTMODE) screen.add(24);
            else screen.add(5);
        }
        else if (j == six) {
            log("six button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            if (currentState == STATE.SUPERSCRIPTMODE) screen.add(25);
            else screen.add(6);
        }
        else if (j == multiply) {
            log("multiply button has been pressed.");
            screen.add(12);
        }
        else if (j == equals) {
            log("equals button has been pressed.");
            if (currentState != STATE.ANSWER && currentState != STATE.ERROR && !screen.isBlank()) {
                double ans = 0;
                ArrayList ppBuffer = new ArrayList();
                try {
                    //set the "last buffer" to the current
                    screen.setPreviousBuffer();
                    ArrayList<String> stt = screen.getTokens();
                    for (String ss : stt){
                        log("ola:"+ss);
                    }
                    ppBuffer = ExpressionParser.preprocess(stt);
                    ArrayList s = ExpressionParser.infixToPrefix(new ArrayList(ppBuffer));
                    ExecutionTree tree = ExecutionTreeBuilder.buildTree(s);
                    ans = tree.evaluateTree();
                }
                catch (OverflowException e) {
                    screen.setError("Overflow Error");
                    currentState = STATE.ERROR;
                    screen.setCurrentAnswerExists(false);
                    MainActivity.err(e.getMessage());
                }
                catch (UnderflowException e) {
                    screen.setError("Underflow Error");
                    currentState = STATE.ERROR;
                    screen.setCurrentAnswerExists(false);
                    MainActivity.err(e.getMessage());
                }
                catch (ExecutionException | EmptyStackException e) {
                    screen.setError("Invalid Operation");
                    currentState = STATE.ERROR;
                    screen.setCurrentAnswerExists(false);
                    MainActivity.err(e.getMessage());
                }
                catch (FormatException | NumberFormatException e) {
                    screen.setError("Invalid Formatting");
                    currentState = STATE.ERROR;
                    screen.setCurrentAnswerExists(false);
                    MainActivity.err(e.getMessage());
                }
                finally {
                    if (currentState != STATE.ERROR){
                        screen.setCurrentAnswer(ans);
                        screen.setCurrentAnswerExists(true);
                        //clear the current buffer
                        if (Function.closeToInteger(screen.getCurrentAnswer())) {
                            long a = Math.round(screen.getCurrentAnswer());
                            screen.setAnswer("" + a);
                            log("Rounded Answer: " + a);
                        } else screen.setAnswer("" + screen.getCurrentAnswer());
                        log(ExpressionParser.tokensToString(ppBuffer));
                    //  smallEditText.setText(TokenBuffer.tokensToString(ppBuffer));
                        log("Current Answer: " + screen.getCurrentAnswer());
                    }
                    currentState = STATE.ANSWER;
                }
            }
        }
        else if (j == one) {
            log("one button has been pressed.");
          //  buffer.addToBuffer(1);
            if (currentState == STATE.ANSWER) screen.reset();
            if (currentState == STATE.SUPERSCRIPTMODE) screen.add(20);
            else screen.add(1);
        }
        else if (j == two) {
            log("two button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            if (currentState == STATE.SUPERSCRIPTMODE) screen.add(21);
            else screen.add(2);
        }
        else if (j == three) {
            log("three button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            if (currentState == STATE.SUPERSCRIPTMODE) screen.add(22);
            else screen.add(3);
        } else if (j == minus) {
            log("minus button has been pressed.");
            if (currentState == STATE.SUPERSCRIPTMODE) screen.add(29);
            else screen.add(11);
        } else if (j == decimal) {
            log("decimal button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            if (currentState == STATE.SUPERSCRIPTMODE) screen.add(30);
            else screen.add(14);
        }
        else if (j == zero) {
            log("zero button has been pressed.");
            if (currentState == STATE.ANSWER) screen.reset();
            if (currentState == STATE.SUPERSCRIPTMODE) screen.add(19);
            else screen.add(0);
        }
        else if (j == ex) {
            log("exponent button has been pressed.");
        }
        else if (j == plus) {
            log("plus button has been pressed.");
            screen.add(10);
        }
        else if (j == ans){
            log("ans button has been pressed.");
            screen.insertAnswer();
        }
    }

    /**
     * Return a unique view ID for buttons on any
     * @param v
     * @return
     */
    public static int getUniqueId(View v){
        if (Build.VERSION.SDK_INT < 17) return generateViewId();
        else return v.generateViewId();
    }
    /**
     * generate unique view ID
     * @return
     */
    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    public static void styleStaticButtons(){
        for (Button b : Numbers){
            b.setBackgroundColor(Color.rgb(46, 92, 183));
        }
        for (Button b : Basic){
            b.setBackgroundColor(Color.rgb(0, 179, 134));
        }
    }

    /**
     * Fragment pager adapter
     */
    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
        @Override
        public Fragment getItem(int position) {
            return SlideButtonFragment.newInstance(position, Data.getMargin(), Data.getBHeight(), Data.getBWidth());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        log("xxx onResume() called.");
        //hide status bar
        hideUI();
        ad.resume();
        log("xxx End of onResume().");
    }

    @Override
    protected void onPause(){
        super.onPause();
        log("xxx onPause() called.");
        ad.pause();
        log("xxx end of onPause().");
    }

    @Override
    protected void onStop() {
        super.onStop();
        log("xxx onStop() called.");

        log("xxx end of onStop().");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log("xxx onDestroy() called.");
        // Destroy the AdView.
        ad.destroy();
        log("xxx end of onDestroy().");
    }

    /**
     * Converts dp value to pixels
     * @param dp
     * @return
     */
    public static int dpToPixel(int dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
        //  return (int) ((getActivity().getResources().getDisplayMetrics().density) * dp);
    }
    public static int pixelsToDp(float px){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

    private void hideUI() {
        if (API_LEVEL >= 19) {
            log("Hide UI SDK version >= 19");
            hideSystemUIImmersive();
            //  hideSystemUI16();
            //  hideSystemUI();
        }
        else if (API_LEVEL >= 16){
            log("Hide UI SDK version >= 16");
            hideSystemUI();
        }
        else {
            log("Hide UI SDK version < 16");
            hideSystemUI();
        }
    }

    private void hideSystemUIImmersive (){
        log("Called 'hideSystemUIImmersive()'");
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void hideSystemUI () {
        log("Called 'hideSystemUI()'");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * Writes a debug line to LogCat with the tag "Free Calc"
     * @param s
     */
    public static void log(String s){
        if (s != null) Log.d("Free Calc", s);
    }

    /**
     * Writes an error line to LogCat with the tag "Free Calc"
     * @param s
     */
    public static void err(String s){
        if (s != null) Log.e("Free Calc", s);
    }
    /*
    private void hideSystemUI16 (){
        log("Called 'hideSystemUI16'");
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }
    */
    private static class Data{
        static int marg = 0;
        static int bHeight = 0;
        static int bWidth = 0;
        static int containerHeight = 0;

        public static void setMargin(int b){
            marg = b;
        }
        public static int getMargin(){
            return marg;
        }
        public static void setBHeight(int b){
            bHeight = b;
        }
        public static int getBHeight(){
            return bHeight;
        }
        public static void setBWidth(int b){
            bWidth = b;
        }
        public static int getBWidth(){
            return bWidth;
        }
        public static void setContainerHeight(int b){
            containerHeight = b;
        }
        public static int getContainerHeight(){
            return containerHeight;
        }
    }
}
