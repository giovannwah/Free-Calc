package freecalculator.giovann.wah.freecalc;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class SlideButtonFragment extends Fragment {
    private SlideButtonInterface callback;
    private int SCREENWIDTH;
    private int SCREENHEIGHT;
    private int bHeight;
    private int bWidth;
    private int margins;

    private int pageNum;
    private int basicButtonColor = Color.rgb(4, 191, 104);

    protected interface SlideButtonInterface {}

    public static SlideButtonFragment newInstance(int pos, int margin, int buttonHeight, int buttonWidth) {
        SlideButtonFragment fragment = new SlideButtonFragment();
        Bundle args = new Bundle();
        args.putInt("pos", pos);
        args.putInt("margin", margin);
        args.putInt("buttonHeight", buttonHeight);
        args.putInt("buttonWidth", buttonWidth);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Converts dp value to pixels
     * @param dp
     * @return
     */
    public int dpToPixel(int dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
      //  return (int) ((getActivity().getResources().getDisplayMetrics().density) * dp);
    }
    public int pixelsToDp(float px){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SCREENWIDTH = getActivity().getResources().getDisplayMetrics().widthPixels;
        SCREENHEIGHT = getActivity().getResources().getDisplayMetrics().heightPixels;
        this.pageNum = getArguments() != null ? getArguments().getInt("pos") : -1;
        this.margins = getArguments() != null ? getArguments().getInt("margin") : -1;
        this.bHeight = getArguments() != null ? getArguments().getInt("buttonHeight") : -1;
        this.bWidth = getArguments() != null ? getArguments().getInt("buttonWidth") : -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_slide_button, container, false);
        TableLayout table = (TableLayout) v.findViewById(R.id.slideTable);
        TableRow row1 = (TableRow) table.findViewById(R.id.slideTableRow1);
        TableRow row2 = (TableRow) table.findViewById(R.id.slideTableRow2);
        TableRow.LayoutParams params = new TableRow.LayoutParams(bWidth, bHeight);
        params.setMargins(margins / 2, margins / 2, margins / 2, margins / 2);

        switch(pageNum) {
            case 0: {
                MainActivity.log("page# 1 activated");
         //       if (!added[pageNum]) {
                    setBackgroundColors(MainActivity.pageOneButtons, basicButtonColor);
                    for (int i = 0; i < 5; i++) {
                        Button child = MainActivity.pageOneButtons.get(i);
                        ViewGroup parent = (ViewGroup) child.getParent();
                        if (parent != null) parent.removeView(child);
                        row1.removeView(child);
                        row1.addView(child, params);
                    }
                    for (int i = 5; i < MainActivity.pageOneButtons.size(); i++) {
                        Button child = MainActivity.pageOneButtons.get(i);
                        ViewGroup parent = (ViewGroup) child.getParent();
                        if (parent != null) parent.removeView(child);
                        row2.removeView(child);
                        row2.addView(child, params);
                    }
          //      }
                break;
            }
            case 1: {
                MainActivity.log("page# 2 activated");
            //    if (!added[pageNum]) {
                    setBackgroundColors(MainActivity.pageTwoButtons, basicButtonColor);
                    for (int i = 0; i < 5; i++) {
                        Button child = MainActivity.pageTwoButtons.get(i);                        if (child == null) MainActivity.log("Child is null!!!!!!");
                        ViewGroup parent = (ViewGroup) child.getParent();
                        if (parent != null) parent.removeView(child);
                        row1.removeView(child);
                        row1.addView(child, params);
                    }
                    for (int i = 5; i < MainActivity.pageTwoButtons.size(); i++) {
                        Button child = MainActivity.pageTwoButtons.get(i);
                        ViewGroup parent = (ViewGroup) child.getParent();
                        if (parent != null) parent.removeView(child);
                        row2.removeView(child);
                        row2.addView(child, params);
                    }
            //    }
                break;
            }
            case 2: {
                MainActivity.log("page# 3 activated");
            //    if (!added[pageNum]) {
                    setBackgroundColors(MainActivity.pageThreeButtons, basicButtonColor);
                    for (int i = 0; i < 5; i++) {
                        Button child = MainActivity.pageThreeButtons.get(i);
                        ViewGroup parent = (ViewGroup) child.getParent();
                        if (parent != null) parent.removeView(child);
                        row1.removeView(child);
                        row1.addView(child, params);
                    }
                    for (int i = 5; i < MainActivity.pageThreeButtons.size(); i++) {
                        Button child = MainActivity.pageThreeButtons.get(i);
                        ViewGroup parent = (ViewGroup) child.getParent();
                        if (parent != null) parent.removeView(child);
                        row2.removeView(child);
                        row2.addView(child, params);
                    }
            //    }
                break;
            }
        }
        return v;
    }

    /**
     * Set the background colors of all buttons in the array
     * @param b
     */
    public static void setBackgroundColors(ArrayList<Button> b, int color){
        for (int i = 0; i < b.size(); i++){
            b.get(i).setBackgroundColor(color);
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (SlideButtonInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

}
