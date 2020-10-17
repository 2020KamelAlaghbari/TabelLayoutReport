package com.ebdaa.tablelayoutreport;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MyView extends ScrollView {

    private LinearLayout buttonLayout;
    private int no_of_pages;
    private TableLayout stk;
    private ScrollView scrollView;
    private TextView txtPageNumber;
    public MyView(Context context) {
        super(context);
        initialize(context);

    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize(context);
    }
    private void initialize(Context context){
        inflate(context, R.layout.my_view, this);

//
        scrollView = findViewById(R.id.scrollViewLib);
        stk =  findViewById(R.id.table_mainLib);
        stk.setStretchAllColumns(true);
        buttonLayout = findViewById(R.id.btnLayLib);
        txtPageNumber = findViewById(R.id.txtPageNumber);


    }

    public void loadingDataTable(ArrayList<String> strListHeader, ArrayList<List<String>> strListContent , int methodNumber,
                                  int designNumber, int headerTextColor, int contentTextColor)
    {

        if ((designNumber>0 &&designNumber<27) && (methodNumber >0 && methodNumber<3)) {


            if (methodNumber == 1) {
//            buttonLayout.setBackgroundColor(Color.parseColor("#c1a57b"));
                tabLayout1(strListHeader, strListContent, 0);
            } else {
//                int resID = getResources().getIdentifier("txt_bg" + designNumber, "drawable", getPackageName());
//                buttonLayout.setBackgroundResource(resID);
                tabLayout2(strListHeader, strListContent, designNumber, headerTextColor, contentTextColor, 0);
            }
            paginate(buttonLayout, strListHeader, strListContent, designNumber, headerTextColor, contentTextColor, methodNumber);

        }
        else
        {
            Toast.makeText(getContext(), "خطأ في ادخال رقم الدالة او التصميم", Toast.LENGTH_SHORT).show();
        }


    }




    private void paginate(final LinearLayout buttonLayout,
                          final ArrayList<String> strListHeader, final ArrayList<List<String>> strListContent ,
                          final int designNumber, final int headerTextColor, final int contentTextColor, final int methodNumber) {
//        no_of_pages = (data_size + page_size - 1) / page_size;
        Log.d("no_of_pages", "strListContent.size(): "+strListContent.size());
        no_of_pages = (int) Math.ceil((double)strListContent.size() / 20);
        Log.d("no_of_pages", "paginate: "+no_of_pages);
        Button[] buttons = new Button[no_of_pages];
        showPageNo(0, no_of_pages);

//        int resID = getResources().getIdentifier("txt_bg"+designNumber, "drawable", getPackageName());

        for (int i = 0; i < no_of_pages; i++) {
            buttons[i] = new Button(getContext());
//            buttons[i].setBackgroundResource(designNumber);
            buttons[i].setTextColor(headerTextColor);
//            buttons[i].setBackgroundColor(getResources().getColor(android.R.color.white));
            buttons[i].setText(String.valueOf(i + 1));

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            buttonLayout.addView(buttons[i], lp);

            final int j = i;
            buttons[j].setOnClickListener(new View.OnClickListener() {


                public void onClick(View v) {
                    scrollView.fullScroll(ScrollView.FOCUS_UP);

                    if (methodNumber == 1)
                    {
                        tabLayout1(strListHeader,strListContent,j);
                    }
                    else
                    {
                        tabLayout2(strListHeader,strListContent,designNumber,headerTextColor,contentTextColor,j);
                    }
                    showPageNo(j, no_of_pages);
                }
            });
        }
    }
    private void showPageNo(int j, int no_of_pages) {
        String r = "صفحة " + (j + 1) + " من " + no_of_pages;
        txtPageNumber.setText(r);
//        Snackbar.make(scrollView.findViewById(R.id.btnLayLib), "صفحة " + (j + 1) + " من " + no_of_pages, Snackbar.LENGTH_LONG).setBackgroundTint(getResources().getColor(R.color.transparent)).show();
    }
    private  void  tabLayout1(final ArrayList<String> strListHeader, final ArrayList<List<String>> strListContent, int page  )
    {
        int leftRowMargin = 0;
        int topRowMargin = 0;
        int rightRowMargin = 0;
        int bottomRowMargin = 0;

        int textSize, smallTextSize;

        textSize = (int) getResources().getDimension(R.dimen.font_size_verysmall);
        smallTextSize = (int) getResources().getDimension(R.dimen.font_size_small);



//        TableRow tbrow0 = new TableRow(getContext());
        stk.removeAllViews();
        final TableRow tr = new TableRow(getContext());


        TableLayout.LayoutParams trParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);

        trParams.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin);
        tr.setPadding(0, 0, 0, 0);
        tr.setLayoutParams(trParams);

        for (int i = 0; i < strListHeader.size(); i++) {

            tr.setId(i + 1);
            if (i%2==0) {
                final TextView tv = new TextView(getContext());
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                tv.setGravity(Gravity.CENTER);

                tv.setPadding(5, 15, 0, 15);

                tv.setText(strListHeader.get(i));
                tv.setBackgroundColor(Color.parseColor("#f0f0f0"));
//                tv.setBackgroundResource(R.drawable.txt_bg1);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_by_alpha_black, 0);
                final int finalI1 = i;
                tv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        SortUtil.sortBy(strListContent, finalI1);
                        tabLayout1(strListHeader,strListContent,0);
                    }
                });
                tr.addView(tv);
            }
            else
            {
                final LinearLayout layoutHeader1 = new LinearLayout(getContext());
                layoutHeader1.setOrientation(LinearLayout.VERTICAL);
                layoutHeader1.setGravity(Gravity.CENTER);

                layoutHeader1.setPadding(0, 10, 0, 10);
                layoutHeader1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));


                final TextView tv = new TextView(getContext());

                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                tv.setPadding(5, 5, 1, 5);
                layoutHeader1.setBackgroundColor(Color.parseColor("#f7f7f7"));
//                layoutHeader1.setBackgroundResource(R.drawable.txt_bg1);

                tv.setGravity(Gravity.CENTER);

                tv.setText(strListHeader.get(i));
                tv.setBackgroundColor(Color.parseColor("#f7f7f7"));
                tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_by_alpha_black, 0);
                final int finalI1 = i;
                tv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        SortUtil.sortBy(strListContent, finalI1);
                        tabLayout1(strListHeader,strListContent,0);
                    }
                });
//                tv.setBackgroundResource(R.drawable.txt_bg1);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                layoutHeader1.addView(tv);
                tr.addView(layoutHeader1);


            }

        }
        stk.addView(tr,trParams);


        final TableRow trSep1 = new TableRow(getContext());
        TableLayout.LayoutParams trParamsSep1 = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        trParamsSep1.setMargins(0, 0, 0, 0);

        trSep1.setLayoutParams(trParamsSep1);
        TextView tvSep1 = new TextView(getContext());
        TableRow.LayoutParams tvSepLay1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        tvSepLay1.span = strListHeader.size();
        tvSep1.setLayoutParams(tvSepLay1);
        tvSep1.setBackgroundColor(Color.DKGRAY);
        tvSep1.setHeight(1);

        trSep1.addView(tvSep1);
        stk.addView(trSep1, trParamsSep1);


        if (strListContent.size()%20==0)
        {
            int maxSize = 20*(page+1);
            for (int i = page*20; i < maxSize; i++)
            {

                final TableRow tbrow = new TableRow(getContext());
                tbrow.setId(i + 1);
                TableLayout.LayoutParams trParams1= new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);


                tbrow.setPadding(0, 0,0 , 0);
                tbrow.setLayoutParams(trParams1);

                ArrayList<String> strContent = (ArrayList<String>) strListContent.get(i);
                for (int j = 0; j <strContent.size() ; j++) {

                    if (j%2 == 0)
                    {


                        final LinearLayout layAmounts = new LinearLayout(getContext());
                        layAmounts.setOrientation(LinearLayout.VERTICAL);
                        layAmounts.setGravity(Gravity.CENTER);
                        layAmounts.setPadding(0, 10, 0, 10);
                        layAmounts.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT));

                        final TextView t1v = new TextView(getContext());
                        t1v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        t1v.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                        t1v.setGravity(Gravity.LEFT);

                        t1v.setPadding(5, 15, 0, 15);

//                    t1v.setPadding(5, 5, 1, 5);
                        t1v.setBackgroundColor(Color.parseColor("#f8f8f8"));
                        t1v.setText(strContent.get(j));
//                    t1v.setTextColor(Color.BLACK);
                        t1v.setGravity(Gravity.CENTER);
                        layAmounts.addView(t1v);
//                    layout1.addView(t1v);
                        tbrow.addView(layAmounts);
                    }
                    else
                    {
//


                        final LinearLayout layAmounts = new LinearLayout(getContext());
                        layAmounts.setOrientation(LinearLayout.VERTICAL);
                        layAmounts.setGravity(Gravity.CENTER);
                        layAmounts.setPadding(0, 10, 0, 10);
                        layAmounts.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT));


                        final TextView tv4 = new TextView(getContext());

                        tv4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv4.setPadding(5, 0, 1, 5);
                        layAmounts.setBackgroundColor(Color.parseColor("#ffffff"));


                        tv4.setGravity(Gravity.CENTER);


                        tv4.setBackgroundColor(Color.parseColor("#ffffff"));
                        tv4.setTextColor(Color.parseColor("#000000"));
                        tv4.setText(strContent.get(j));
                        tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);


                        layAmounts.addView(tv4);


//
                        tbrow.addView(layAmounts);
                    }

                }




                stk.addView(tbrow,trParams1);

                final TableRow trSep = new TableRow(getContext());
                TableLayout.LayoutParams trParamsSep = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);
                trParamsSep.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin);

                trSep.setLayoutParams(trParamsSep);
                TextView tvSep = new TextView(getContext());
                TableRow.LayoutParams tvSepLay = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                tvSepLay.span = strListHeader.size();
                tvSep.setLayoutParams(tvSepLay);
                tvSep.setBackgroundColor(Color.parseColor("#d9d9d9"));
                tvSep.setHeight(1);


                trSep.addView(tvSep);
                stk.addView(trSep, trParamsSep);

            }
        }

        else {
            int pageSize = strListContent.size() / 20;


            Log.d("lastPage", "init3: " + pageSize + " : Page :" + page);
            if (pageSize == page) {

                int startIndex = strListContent.size() - (strListContent.size() % 20);
//               for (int i = startIndex; i < strListContent.size(); i++)
                for (int i = startIndex; i < strListContent.size(); i++) {

                    final TableRow tbrow = new TableRow(getContext());
                    tbrow.setId(i + 1);
                    TableLayout.LayoutParams trParams1 = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT);


                    tbrow.setPadding(0, 0, 0, 0);
                    tbrow.setLayoutParams(trParams1);

                    ArrayList<String> strContent = (ArrayList<String>) strListContent.get(i);
                    for (int j = 0; j < strContent.size(); j++) {

                        if (j % 2 == 0) {


                            final LinearLayout layAmounts = new LinearLayout(getContext());
                            layAmounts.setOrientation(LinearLayout.VERTICAL);
                            layAmounts.setGravity(Gravity.CENTER);
                            layAmounts.setPadding(0, 10, 0, 10);
                            layAmounts.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.MATCH_PARENT));

                            final TextView t1v = new TextView(getContext());
                            t1v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                    TableRow.LayoutParams.WRAP_CONTENT));
                            t1v.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                            t1v.setGravity(Gravity.LEFT);

                            t1v.setPadding(5, 15, 0, 15);

//                    t1v.setPadding(5, 5, 1, 5);
                            t1v.setBackgroundColor(Color.parseColor("#f8f8f8"));
                            t1v.setText(strContent.get(j));
//                    t1v.setTextColor(Color.BLACK);
                            t1v.setGravity(Gravity.CENTER);
                            layAmounts.addView(t1v);
//                    layout1.addView(t1v);
                            tbrow.addView(layAmounts);
                        } else {
//                    final TextView tv2 = new TextView(getContext());
//                    tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                            TableRow.LayoutParams.MATCH_PARENT));
//                    tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
//
//
//                    tv2.setGravity(Gravity.LEFT);
//                    tv2.setText(strContent.get(j));
//                    tv2.setPadding(5, 15, 0, 15);
//                    tv2.setTextColor(Color.BLACK);
//                    tv2.setBackgroundColor(Color.parseColor("#ffffff"));


                            final LinearLayout layAmounts = new LinearLayout(getContext());
                            layAmounts.setOrientation(LinearLayout.VERTICAL);
                            layAmounts.setGravity(Gravity.CENTER);
                            layAmounts.setPadding(0, 10, 0, 10);
                            layAmounts.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.MATCH_PARENT));


                            final TextView tv4 = new TextView(getContext());

                            tv4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.WRAP_CONTENT));
                            tv4.setPadding(5, 0, 1, 5);
                            layAmounts.setBackgroundColor(Color.parseColor("#ffffff"));


                            tv4.setGravity(Gravity.CENTER);


                            tv4.setBackgroundColor(Color.parseColor("#ffffff"));
                            tv4.setTextColor(Color.parseColor("#000000"));
                            tv4.setText(strContent.get(j));
                            tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);


                            layAmounts.addView(tv4);


//                    final LinearLayout layout1 = new LinearLayout(getContext());
//                    layout1.setOrientation(LinearLayout.VERTICAL);
//                    layout1.setGravity(Gravity.RIGHT);
//                    layout1.setPadding(0, 5, 0, 5);
//                    layout1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
//                            TableRow.LayoutParams.MATCH_PARENT));
////                    layout1.setBackgroundResource(R.drawable.txt_bg1);
////                    layout1.setBackgroundColor(Color.LTGRAY);
//
//                    TextView t2v = new TextView(getContext());
//                    t2v.setPadding(5, 5, 1, 5);
//                    t2v.setText(strContent.get(j));
//                    t2v.setTextColor(Color.BLACK);
//                    t2v.setBackgroundColor(Color.LTGRAY);
//                    t2v.setGravity(Gravity.CENTER);
//                    layout1.addView(t2v);
                            tbrow.addView(layAmounts);
                        }

                    }


                    stk.addView(tbrow, trParams1);

                    final TableRow trSep = new TableRow(getContext());
                    TableLayout.LayoutParams trParamsSep = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT);
                    trParamsSep.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin);

                    trSep.setLayoutParams(trParamsSep);
                    TextView tvSep = new TextView(getContext());
                    TableRow.LayoutParams tvSepLay = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT);
                    tvSepLay.span = strListHeader.size();
                    tvSep.setLayoutParams(tvSepLay);
                    tvSep.setBackgroundColor(Color.parseColor("#d9d9d9"));
                    tvSep.setHeight(1);


                    trSep.addView(tvSep);
                    stk.addView(trSep, trParamsSep);

                }
            }
            else
            {
                int maxSize = 20*(page+1);
                for (int i = page*20; i < maxSize; i++)
                {

                    final TableRow tbrow = new TableRow(getContext());
                    tbrow.setId(i + 1);
                    TableLayout.LayoutParams trParams1= new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT);


                    tbrow.setPadding(0, 0,0 , 0);
                    tbrow.setLayoutParams(trParams1);

                    ArrayList<String> strContent = (ArrayList<String>) strListContent.get(i);
                    for (int j = 0; j <strContent.size() ; j++) {

                        if (j%2 == 0)
                        {


                            final LinearLayout layAmounts = new LinearLayout(getContext());
                            layAmounts.setOrientation(LinearLayout.VERTICAL);
                            layAmounts.setGravity(Gravity.CENTER);
                            layAmounts.setPadding(0, 10, 0, 10);
                            layAmounts.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.MATCH_PARENT));

                            final TextView t1v = new TextView(getContext());
                            t1v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                    TableRow.LayoutParams.WRAP_CONTENT));
                            t1v.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                            t1v.setGravity(Gravity.LEFT);

                            t1v.setPadding(5, 15, 0, 15);

//                    t1v.setPadding(5, 5, 1, 5);
                            t1v.setBackgroundColor(Color.parseColor("#f8f8f8"));
                            t1v.setText(strContent.get(j));
//                    t1v.setTextColor(Color.BLACK);
                            t1v.setGravity(Gravity.CENTER);
                            layAmounts.addView(t1v);
//                    layout1.addView(t1v);
                            tbrow.addView(layAmounts);
                        }
                        else
                        {
//
                            final LinearLayout layAmounts = new LinearLayout(getContext());
                            layAmounts.setOrientation(LinearLayout.VERTICAL);
                            layAmounts.setGravity(Gravity.CENTER);
                            layAmounts.setPadding(0, 10, 0, 10);
                            layAmounts.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.MATCH_PARENT));


                            final TextView tv4 = new TextView(getContext());

                            tv4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.WRAP_CONTENT));
                            tv4.setPadding(5, 0, 1, 5);
                            layAmounts.setBackgroundColor(Color.parseColor("#ffffff"));


                            tv4.setGravity(Gravity.CENTER);


                            tv4.setBackgroundColor(Color.parseColor("#ffffff"));
                            tv4.setTextColor(Color.parseColor("#000000"));
                            tv4.setText(strContent.get(j));
                            tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);


                            layAmounts.addView(tv4);


//
                            tbrow.addView(layAmounts);
                        }

                    }




                    stk.addView(tbrow,trParams1);

                    final TableRow trSep = new TableRow(getContext());
                    TableLayout.LayoutParams trParamsSep = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT);
                    trParamsSep.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin);

                    trSep.setLayoutParams(trParamsSep);
                    TextView tvSep = new TextView(getContext());
                    TableRow.LayoutParams tvSepLay = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT);
                    tvSepLay.span = strListHeader.size();
                    tvSep.setLayoutParams(tvSepLay);
                    tvSep.setBackgroundColor(Color.parseColor("#d9d9d9"));
                    tvSep.setHeight(1);


                    trSep.addView(tvSep);
                    stk.addView(trSep, trParamsSep);

                }
            }
        }



    }
    private  void  tabLayout2(final ArrayList<String> strListHeader, final ArrayList<List<String>> strListContent ,
                              final int designNumber, final int headerTextColor, final int contentTextColor, int page )
    {
        int leftRowMargin = 0;
        int topRowMargin = 0;
        int rightRowMargin = 0;
        int bottomRowMargin = 0;

        int textSize, smallTextSize;

        textSize = (int) getResources().getDimension(R.dimen.font_size_verysmall);
        smallTextSize = (int) getResources().getDimension(R.dimen.font_size_small);

        int resID = getResources().getIdentifier("txt_bg"+designNumber, "drawable", getContext().getPackageName());


//        TableRow tbrow0 = new TableRow(getContext());
        stk.removeAllViews();
        final TableRow tr = new TableRow(getContext());


        TableLayout.LayoutParams trParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);

        trParams.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin);
        tr.setPadding(0, 0, 0, 0);
        tr.setLayoutParams(trParams);

        for (int i = 0; i < strListHeader.size(); i++) {

            tr.setId(i + 1);
            if (i%2==0) {
                final TextView tv = new TextView(getContext());
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                if(headerTextColor == Color.WHITE)
                {
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_by_alpha_white, 0);
                }
                else
                {
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_by_alpha_black, 0);
                }
                tv.setGravity(Gravity.CENTER);

                tv.setPadding(5, 15, 0, 15);

                tv.setText(strListHeader.get(i));




//                tv.setBackground(new Border(Color.DKGRAY,1));
                tv.setBackgroundColor(Color.parseColor("#f0f0f0"));
                tv.setBackgroundResource(resID);
                tv.setTextColor(headerTextColor);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                final int finalI = i;
                tv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        SortUtil.sortBy(strListContent, finalI);
                        tabLayout2(strListHeader,strListContent,designNumber,headerTextColor,contentTextColor,0);
                    }
                });
                tr.addView(tv);

            }
            else
            {
                final LinearLayout layoutHeader1 = new LinearLayout(getContext());
                layoutHeader1.setOrientation(LinearLayout.VERTICAL);
                layoutHeader1.setGravity(Gravity.CENTER);

                layoutHeader1.setPadding(0, 10, 0, 10);
                layoutHeader1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));


                final TextView tv = new TextView(getContext());

                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                tv.setPadding(5, 15, 5, 15);
                if(headerTextColor == Color.WHITE)
                {
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_by_alpha_white, 0);
                }
                else
                {
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_by_alpha_black, 0);
                }

                final int finalI1 = i;
                tv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        SortUtil.sortBy(strListContent, finalI1);
                        tabLayout2(strListHeader,strListContent,designNumber,headerTextColor,contentTextColor,0);
                    }
                });
//                layoutHeader1.setBackground(new Border(Color.DKGRAY,1));
                layoutHeader1.setBackgroundColor(Color.parseColor("#f7f7f7"));
                layoutHeader1.setBackgroundResource(resID);


                tv.setGravity(Gravity.CENTER);

                tv.setText(strListHeader.get(i));


//                tv.setBackgroundResource(R.drawable.txt_bg1);
                tv.setTextColor(headerTextColor);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                layoutHeader1.addView(tv);
                tr.addView(layoutHeader1);


            }

        }
        stk.addView(tr,trParams);

//        int startIndex = page*20;
//        int lastList = strListContent.size()%20;
//        int

        if (strListContent.size()%20==0)
        {
            int maxSize = 20*(page+1);
            for (int i = page*20; i < maxSize; i++)
            {

                final TableRow tbrow = new TableRow(getContext());
                tbrow.setId(i + 1);
                TableLayout.LayoutParams trParams1= new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);


                tbrow.setPadding(0, 0,0 , 0);
                tbrow.setLayoutParams(trParams1);

                ArrayList<String> strContent = (ArrayList<String>) strListContent.get(i);
                for (int j = 0; j <strContent.size() ; j++) {

                    if (j%2 == 0)
                    {


                        final LinearLayout layout = new LinearLayout(getContext());
                        layout.setOrientation(LinearLayout.VERTICAL);
                        layout.setGravity(Gravity.CENTER);
                        layout.setPadding(0, 10, 0, 10);
                        layout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT));
                        layout.setBackgroundResource(resID);






                        final TextView t1v = new TextView(getContext());
                        t1v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        t1v.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                        t1v.setGravity(Gravity.CENTER);

                        t1v.setPadding(5, 15, 5, 15);


                        t1v.setText(strContent.get(j));
                        t1v.setTextColor(contentTextColor);
                        t1v.setGravity(Gravity.CENTER);

                        layout.addView(t1v);

                        tbrow.addView(layout);
                    }
                    else
                    {


                        final LinearLayout layout = new LinearLayout(getContext());
                        layout.setOrientation(LinearLayout.VERTICAL);
                        layout.setGravity(Gravity.CENTER);
                        layout.setPadding(0, 10, 0, 10);
                        layout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT));






                        final TextView tv4 = new TextView(getContext());

                        tv4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv4.setPadding(5, 15, 5, 15);
//                    layAmounts.setBackgroundColor(Color.parseColor("#ffffff"));

                        layout.setBackgroundResource(resID);
                        tv4.setGravity(Gravity.CENTER);



                        tv4.setTextColor(contentTextColor);
                        tv4.setText(strContent.get(j));
                        tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);


                        layout.addView(tv4);


//
                        tbrow.addView(layout);
                    }

                }




                stk.addView(tbrow,trParams1);



            }

        }
        else
        {
            int pageSize =   strListContent.size()/20;




            Log.d("lastPage", "init3: "+pageSize +" : Page :"+page);
            if(pageSize == page)
            {

                int startIndex = strListContent.size() - (strListContent.size()%20);
//               for (int i = startIndex; i < strListContent.size(); i++)
                for (int i = startIndex; i < strListContent.size(); i++)
                {

                    final TableRow tbrow = new TableRow(getContext());
                    tbrow.setId(i + 1);
                    TableLayout.LayoutParams trParams1= new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT);


                    tbrow.setPadding(0, 0,0 , 0);
                    tbrow.setLayoutParams(trParams1);

                    ArrayList<String> strContent = (ArrayList<String>) strListContent.get(i);
                    for (int j = 0; j <strContent.size() ; j++) {

                        if (j%2 == 0)
                        {


                            final LinearLayout layout = new LinearLayout(getContext());
                            layout.setOrientation(LinearLayout.VERTICAL);
                            layout.setGravity(Gravity.CENTER);
                            layout.setPadding(0, 10, 0, 10);
                            layout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.MATCH_PARENT));
                            layout.setBackgroundResource(resID);






                            final TextView t1v = new TextView(getContext());
                            t1v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                    TableRow.LayoutParams.WRAP_CONTENT));
                            t1v.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                            t1v.setGravity(Gravity.CENTER);

                            t1v.setPadding(5, 15, 5, 15);


                            t1v.setText(strContent.get(j));
                            t1v.setTextColor(contentTextColor);
                            t1v.setGravity(Gravity.CENTER);

                            layout.addView(t1v);

                            tbrow.addView(layout);
                        }
                        else
                        {


                            final LinearLayout layout = new LinearLayout(getContext());
                            layout.setOrientation(LinearLayout.VERTICAL);
                            layout.setGravity(Gravity.CENTER);
                            layout.setPadding(0, 10, 0, 10);
                            layout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.MATCH_PARENT));






                            final TextView tv4 = new TextView(getContext());

                            tv4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.WRAP_CONTENT));
                            tv4.setPadding(5, 15, 5, 15);
//                    layAmounts.setBackgroundColor(Color.parseColor("#ffffff"));

                            layout.setBackgroundResource(resID);
                            tv4.setGravity(Gravity.CENTER);



                            tv4.setTextColor(contentTextColor);
                            tv4.setText(strContent.get(j));
                            tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);


                            layout.addView(tv4);


//
                            tbrow.addView(layout);
                        }

                    }




                    stk.addView(tbrow,trParams1);



                }

            }
            else
            {
                int maxSize = 20*(page+1);
                for (int i = page*20; i < maxSize; i++)
                {

                    final TableRow tbrow = new TableRow(getContext());
                    tbrow.setId(i + 1);
                    TableLayout.LayoutParams trParams1= new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT);


                    tbrow.setPadding(0, 0,0 , 0);
                    tbrow.setLayoutParams(trParams1);

                    ArrayList<String> strContent = (ArrayList<String>) strListContent.get(i);
                    for (int j = 0; j <strContent.size() ; j++) {

                        if (j%2 == 0)
                        {


                            final LinearLayout layout = new LinearLayout(getContext());
                            layout.setOrientation(LinearLayout.VERTICAL);
                            layout.setGravity(Gravity.CENTER);
                            layout.setPadding(0, 10, 0, 10);
                            layout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.MATCH_PARENT));
                            layout.setBackgroundResource(resID);






                            final TextView t1v = new TextView(getContext());
                            t1v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                    TableRow.LayoutParams.WRAP_CONTENT));
                            t1v.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                            t1v.setGravity(Gravity.CENTER);

                            t1v.setPadding(5, 15, 5, 15);


                            t1v.setText(strContent.get(j));
                            t1v.setTextColor(contentTextColor);
                            t1v.setGravity(Gravity.CENTER);

                            layout.addView(t1v);

                            tbrow.addView(layout);
                        }
                        else
                        {


                            final LinearLayout layout = new LinearLayout(getContext());
                            layout.setOrientation(LinearLayout.VERTICAL);
                            layout.setGravity(Gravity.CENTER);
                            layout.setPadding(0, 10, 0, 10);
                            layout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.MATCH_PARENT));






                            final TextView tv4 = new TextView(getContext());

                            tv4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.WRAP_CONTENT));
                            tv4.setPadding(5, 15, 5, 15);
//                    layAmounts.setBackgroundColor(Color.parseColor("#ffffff"));

                            layout.setBackgroundResource(resID);
                            tv4.setGravity(Gravity.CENTER);



                            tv4.setTextColor(contentTextColor);
                            tv4.setText(strContent.get(j));
                            tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);


                            layout.addView(tv4);


//
                            tbrow.addView(layout);
                        }

                    }




                    stk.addView(tbrow,trParams1);



                }
            }
        }





    }

}
