package com.ebdaa.reportapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Bundle;
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


import com.ebdaa.tablelayoutreport.MyView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {


    private LinearLayout buttonLayout;
    private int no_of_pages;
    TableLayout stk;
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//        scrollView = findViewById(R.id.scrollView);
//        stk =  findViewById(R.id.table_main);
//        stk.setStretchAllColumns(true);
//        buttonLayout = findViewById(R.id.btnLay);
//
//        loadingDataTable(listHeaders(),setContentList(),2,22,Color.WHITE,Color.WHITE);
        MyView myView = new MyView(this);
        myView.loadingDataTable(listHeaders(),setContentList(),1,5,Color.WHITE,Color.WHITE);
        setContentView(myView);

    }


    private void loadingDataTable(ArrayList<String> strListHeader, ArrayList<List<String>> strListContent ,int methodNumber,
                             int designNumber,int headerTextColor, int contentTextColor)
    {

        if ((designNumber>0 &&designNumber<27) && (methodNumber >0 && methodNumber<3)) {


            if (methodNumber == 1) {
//            buttonLayout.setBackgroundColor(Color.parseColor("#c1a57b"));
                tabLayout1(listHeaders(), setContentList(), 0);
            } else {
//                int resID = getResources().getIdentifier("txt_bg" + designNumber, "drawable", getPackageName());
//                buttonLayout.setBackgroundResource(resID);
                tabLayout2(listHeaders(), strListContent, designNumber, headerTextColor, contentTextColor, 0);
            }
            paginate(buttonLayout, strListHeader, strListContent, designNumber, headerTextColor, contentTextColor, methodNumber);

        }
        else
        {
            Toast.makeText(this, "خطأ في ادخال رقم الدالة او التصميم", Toast.LENGTH_SHORT).show();
        }


    }




    private void paginate(final LinearLayout buttonLayout,
                          ArrayList<String> strListHeader, ArrayList<List<String>> strListContent ,
                          int designNumber,int headerTextColor, int contentTextColor,int methodNumber) {
//        no_of_pages = (data_size + page_size - 1) / page_size;
        Log.d("no_of_pages", "strListContent.size(): "+strListContent.size());
        no_of_pages = (int) Math.ceil((double)strListContent.size() / 20);
        Log.d("no_of_pages", "paginate: "+no_of_pages);
        Button[] buttons = new Button[no_of_pages];
        showPageNo(0, no_of_pages);

//        int resID = getResources().getIdentifier("txt_bg"+designNumber, "drawable", getPackageName());

        for (int i = 0; i < no_of_pages; i++) {
            buttons[i] = new Button(this);
//            buttons[i].setBackgroundResource(designNumber);
            buttons[i].setTextColor(headerTextColor);
//            buttons[i].setBackgroundColor(getResources().getColor(android.R.color.white));
            buttons[i].setText(String.valueOf(i + 1));

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            buttonLayout.addView(buttons[i], lp);

            final int j = i;
            buttons[j].setOnClickListener(new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                public void onClick(View v) {
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
//                    createTable(airports, j);
//                    checkBtnBackGroud(j);
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
        Snackbar.make(buttonLayout, "صفحة " + (j + 1) + " من " + no_of_pages, Snackbar.LENGTH_LONG).setBackgroundTint(getResources().getColor(R.color.transparent)).show();
    }










    private ArrayList<String> listHeaders() {
        ArrayList<String> list = new ArrayList<>();
        list.add("C1111C1111");
        list.add("C2222C2222");
        list.add("C3333C3333");
        list.add("C4444C4444");
        list.add("C5555C5555");
        list.add("C6666C6666");

        return list;
    }


    private ArrayList<List<String>> setContentList() {
        ArrayList<List<String>> tempListView = new ArrayList<>();


        Invoices invoices = new Invoices();
        for (int i = 0; i < invoices.getInvoices().size(); i++) {
            InvoiceData list = invoices.getInvoices().get(i);
            ArrayList<String> nestedList = new ArrayList<>();
            nestedList.add(String.valueOf(list.id));
            nestedList.add(String.valueOf(list.customerName+" "+(i+1)));
            nestedList.add(String.valueOf(list.invoiceDate));
            nestedList.add(String.valueOf(list.customerAddress));
            nestedList.add(String.valueOf(list.invoiceAmount));
            nestedList.add(String.valueOf(list.amountDue));
            tempListView.add(nestedList);
        }

        return tempListView;
    }

    private  void  tabLayout1( ArrayList<String> strListHeader, ArrayList<List<String>> strListContent,int page  )
    {
        int leftRowMargin = 0;
        int topRowMargin = 0;
        int rightRowMargin = 0;
        int bottomRowMargin = 0;

        int textSize, smallTextSize;

        textSize = (int) getResources().getDimension(R.dimen.font_size_verysmall);
        smallTextSize = (int) getResources().getDimension(R.dimen.font_size_small);

        TableLayout stk =  findViewById(R.id.table_main);
        stk.setStretchAllColumns(true);
//        TableRow tbrow0 = new TableRow(this);
        stk.removeAllViews();
        final TableRow tr = new TableRow(this);


        TableLayout.LayoutParams trParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);

        trParams.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin);
        tr.setPadding(0, 0, 0, 0);
        tr.setLayoutParams(trParams);

        for (int i = 0; i < strListHeader.size(); i++) {

            tr.setId(i + 1);
            if (i%2==0) {
                final TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                tv.setGravity(Gravity.CENTER);

                tv.setPadding(5, 15, 0, 15);

                tv.setText(strListHeader.get(i));
                tv.setBackgroundColor(Color.parseColor("#f0f0f0"));
//                tv.setBackgroundResource(R.drawable.txt_bg1);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_by_alpha_black, 0);
                int finalI1 = i;
                tv.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
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
                final LinearLayout layoutHeader1 = new LinearLayout(this);
                layoutHeader1.setOrientation(LinearLayout.VERTICAL);
                layoutHeader1.setGravity(Gravity.CENTER);

                layoutHeader1.setPadding(0, 10, 0, 10);
                layoutHeader1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));


                final TextView tv = new TextView(this);

                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                tv.setPadding(5, 5, 1, 5);
                layoutHeader1.setBackgroundColor(Color.parseColor("#f7f7f7"));
//                layoutHeader1.setBackgroundResource(R.drawable.txt_bg1);

                tv.setGravity(Gravity.CENTER);

                tv.setText(strListHeader.get(i));
                tv.setBackgroundColor(Color.parseColor("#f7f7f7"));
                tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_by_alpha_black, 0);
                int finalI1 = i;
                tv.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
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


        final TableRow trSep1 = new TableRow(this);
        TableLayout.LayoutParams trParamsSep1 = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        trParamsSep1.setMargins(0, 0, 0, 0);

        trSep1.setLayoutParams(trParamsSep1);
        TextView tvSep1 = new TextView(this);
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

                    final TableRow tbrow = new TableRow(this);
                    tbrow.setId(i + 1);
                    TableLayout.LayoutParams trParams1= new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT);


                    tbrow.setPadding(0, 0,0 , 0);
                    tbrow.setLayoutParams(trParams1);

                    ArrayList<String> strContent = (ArrayList<String>) strListContent.get(i);
                    for (int j = 0; j <strContent.size() ; j++) {

                        if (j%2 == 0)
                        {


                            final LinearLayout layAmounts = new LinearLayout(this);
                            layAmounts.setOrientation(LinearLayout.VERTICAL);
                            layAmounts.setGravity(Gravity.CENTER);
                            layAmounts.setPadding(0, 10, 0, 10);
                            layAmounts.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.MATCH_PARENT));

                            final TextView t1v = new TextView(this);
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
//                    final TextView tv2 = new TextView(this);
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


                            final LinearLayout layAmounts = new LinearLayout(this);
                            layAmounts.setOrientation(LinearLayout.VERTICAL);
                            layAmounts.setGravity(Gravity.CENTER);
                            layAmounts.setPadding(0, 10, 0, 10);
                            layAmounts.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.MATCH_PARENT));


                            final TextView tv4 = new TextView(this);

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


//                    final LinearLayout layout1 = new LinearLayout(this);
//                    layout1.setOrientation(LinearLayout.VERTICAL);
//                    layout1.setGravity(Gravity.RIGHT);
//                    layout1.setPadding(0, 5, 0, 5);
//                    layout1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
//                            TableRow.LayoutParams.MATCH_PARENT));
////                    layout1.setBackgroundResource(R.drawable.txt_bg1);
////                    layout1.setBackgroundColor(Color.LTGRAY);
//
//                    TextView t2v = new TextView(this);
//                    t2v.setPadding(5, 5, 1, 5);
//                    t2v.setText(strContent.get(j));
//                    t2v.setTextColor(Color.BLACK);
//                    t2v.setBackgroundColor(Color.LTGRAY);
//                    t2v.setGravity(Gravity.CENTER);
//                    layout1.addView(t2v);
                            tbrow.addView(layAmounts);
                        }

                    }




                    stk.addView(tbrow,trParams1);

                    final TableRow trSep = new TableRow(this);
                    TableLayout.LayoutParams trParamsSep = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT);
                    trParamsSep.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin);

                    trSep.setLayoutParams(trParamsSep);
                    TextView tvSep = new TextView(this);
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

                    final TableRow tbrow = new TableRow(this);
                    tbrow.setId(i + 1);
                    TableLayout.LayoutParams trParams1 = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT);


                    tbrow.setPadding(0, 0, 0, 0);
                    tbrow.setLayoutParams(trParams1);

                    ArrayList<String> strContent = (ArrayList<String>) strListContent.get(i);
                    for (int j = 0; j < strContent.size(); j++) {

                        if (j % 2 == 0) {


                            final LinearLayout layAmounts = new LinearLayout(this);
                            layAmounts.setOrientation(LinearLayout.VERTICAL);
                            layAmounts.setGravity(Gravity.CENTER);
                            layAmounts.setPadding(0, 10, 0, 10);
                            layAmounts.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.MATCH_PARENT));

                            final TextView t1v = new TextView(this);
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
//                    final TextView tv2 = new TextView(this);
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


                            final LinearLayout layAmounts = new LinearLayout(this);
                            layAmounts.setOrientation(LinearLayout.VERTICAL);
                            layAmounts.setGravity(Gravity.CENTER);
                            layAmounts.setPadding(0, 10, 0, 10);
                            layAmounts.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.MATCH_PARENT));


                            final TextView tv4 = new TextView(this);

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


//                    final LinearLayout layout1 = new LinearLayout(this);
//                    layout1.setOrientation(LinearLayout.VERTICAL);
//                    layout1.setGravity(Gravity.RIGHT);
//                    layout1.setPadding(0, 5, 0, 5);
//                    layout1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
//                            TableRow.LayoutParams.MATCH_PARENT));
////                    layout1.setBackgroundResource(R.drawable.txt_bg1);
////                    layout1.setBackgroundColor(Color.LTGRAY);
//
//                    TextView t2v = new TextView(this);
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

                    final TableRow trSep = new TableRow(this);
                    TableLayout.LayoutParams trParamsSep = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT);
                    trParamsSep.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin);

                    trSep.setLayoutParams(trParamsSep);
                    TextView tvSep = new TextView(this);
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

                    final TableRow tbrow = new TableRow(this);
                    tbrow.setId(i + 1);
                    TableLayout.LayoutParams trParams1= new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT);


                    tbrow.setPadding(0, 0,0 , 0);
                    tbrow.setLayoutParams(trParams1);

                    ArrayList<String> strContent = (ArrayList<String>) strListContent.get(i);
                    for (int j = 0; j <strContent.size() ; j++) {

                        if (j%2 == 0)
                        {


                            final LinearLayout layAmounts = new LinearLayout(this);
                            layAmounts.setOrientation(LinearLayout.VERTICAL);
                            layAmounts.setGravity(Gravity.CENTER);
                            layAmounts.setPadding(0, 10, 0, 10);
                            layAmounts.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.MATCH_PARENT));

                            final TextView t1v = new TextView(this);
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
                            final LinearLayout layAmounts = new LinearLayout(this);
                            layAmounts.setOrientation(LinearLayout.VERTICAL);
                            layAmounts.setGravity(Gravity.CENTER);
                            layAmounts.setPadding(0, 10, 0, 10);
                            layAmounts.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                    TableRow.LayoutParams.MATCH_PARENT));


                            final TextView tv4 = new TextView(this);

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

                    final TableRow trSep = new TableRow(this);
                    TableLayout.LayoutParams trParamsSep = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT);
                    trParamsSep.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin);

                    trSep.setLayoutParams(trParamsSep);
                    TextView tvSep = new TextView(this);
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

    private  void  init2( ArrayList<String> strListHeader, ArrayList<List<String>> strListContent )
    {
        int leftRowMargin = 0;
        int topRowMargin = 0;
        int rightRowMargin = 0;
        int bottomRowMargin = 0;

        int textSize, smallTextSize;

        textSize = (int) getResources().getDimension(R.dimen.font_size_verysmall);
        smallTextSize = (int) getResources().getDimension(R.dimen.font_size_small);

        TableLayout stk =  findViewById(R.id.table_main);
        stk.setStretchAllColumns(true);
//        TableRow tbrow0 = new TableRow(this);
        stk.removeAllViews();
        final TableRow tr = new TableRow(this);


        TableLayout.LayoutParams trParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);

        trParams.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin);
        tr.setPadding(0, 0, 0, 0);
        tr.setLayoutParams(trParams);

        for (int i = 0; i < strListHeader.size(); i++) {

            tr.setId(i + 1);
            if (i%2==0) {
                final TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                tv.setGravity(Gravity.CENTER);

                tv.setPadding(5, 15, 0, 15);

                tv.setText(strListHeader.get(i));

                int[] colors = {Color.parseColor("#cbaf87"),Color.parseColor("#7e8a97"),Color.parseColor("#e7dec8")};

                //create a new gradient color
                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM, colors);

                gd.setCornerRadius(0f);

                tv.setBackground(new Border(Color.DKGRAY,1));



                tv.setBackgroundColor(Color.parseColor("#f0f0f0"));
//                tv.setBackgroundResource(R.drawable.txt_bg1);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

                tr.addView(tv);
            }
            else
            {
                final LinearLayout layoutHeader1 = new LinearLayout(this);
                layoutHeader1.setOrientation(LinearLayout.VERTICAL);
                layoutHeader1.setGravity(Gravity.CENTER);

                layoutHeader1.setPadding(0, 10, 0, 10);
                layoutHeader1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));


                final TextView tv = new TextView(this);

                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                tv.setPadding(5, 15, 5, 15);
                layoutHeader1.setBackground(new Border(Color.DKGRAY,1));
                layoutHeader1.setBackgroundColor(Color.parseColor("#f7f7f7"));
//                layoutHeader1.setBackgroundResource(R.drawable.txt_bg1);


                tv.setGravity(Gravity.CENTER);

                tv.setText(strListHeader.get(i));


//                tv.setBackgroundResource(R.drawable.txt_bg1);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                layoutHeader1.addView(tv);
                tr.addView(layoutHeader1);


            }

        }
        stk.addView(tr,trParams);


//        final TableRow trSep1 = new TableRow(this);
//        TableLayout.LayoutParams trParamsSep1 = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT);
//        trParamsSep1.setMargins(0, 0, 0, 0);
//
//        trSep1.setLayoutParams(trParamsSep1);
//        TextView tvSep1 = new TextView(this);
//        TableRow.LayoutParams tvSepLay1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
//                TableRow.LayoutParams.WRAP_CONTENT);
//        tvSepLay1.span = strListHeader.size();
//        tvSep1.setLayoutParams(tvSepLay1);
//        tvSep1.setBackgroundColor(Color.DKGRAY);
//        tvSep1.setHeight(1);
//
//        trSep1.addView(tvSep1);
//        stk.addView(trSep1, trParamsSep1);
        for (int i = 0; i < strListContent.size(); i++)
        {

            final TableRow tbrow = new TableRow(this);
            tbrow.setId(i + 1);
            TableLayout.LayoutParams trParams1= new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);


            tbrow.setPadding(0, 0,0 , 0);
            tbrow.setLayoutParams(trParams1);

            ArrayList<String> strContent = (ArrayList<String>) strListContent.get(i);
            for (int j = 0; j <strContent.size() ; j++) {

                if (j%2 == 0)
                {
//                    final LinearLayout layout1 = new LinearLayout(this);
//                    layout1.setOrientation(LinearLayout.VERTICAL);
//                    layout1.setGravity(Gravity.RIGHT);
//                    layout1.setPadding(0, 10, 0, 10);
//                    layout1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
//                            TableRow.LayoutParams.MATCH_PARENT));
//                    layout1.setBackgroundColor(Color.parseColor("#f7f7f7"));

                    final LinearLayout layout = new LinearLayout(this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setGravity(Gravity.CENTER);
                    layout.setPadding(0, 10, 0, 10);
                    layout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
//                    layAmounts.setBackgroundResource(R.drawable.txt_bg1);


                    int[] colors = {Color.parseColor("#cbaf87"),Color.parseColor("#7e8a97"),Color.parseColor("#e7dec8")};

                    //create a new gradient color
                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM, colors);

                    gd.setCornerRadius(0f);




                    final LinearLayout layout2 = new LinearLayout(this);
                    layout2.setOrientation(LinearLayout.VERTICAL);
                    layout2.setGravity(Gravity.CENTER);
                    layout2.setPadding(0, 0, 0, 0);
                    layout2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));



                    layout.setBackground(gd);
                    layout2.setBackground(new Border(Color.DKGRAY,1));

                    final TextView t1v = new TextView(this);
                    t1v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    t1v.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                    t1v.setGravity(Gravity.CENTER);

                    t1v.setPadding(5, 15, 5, 15);

//                    t1v.setPadding(5, 5, 1, 5);
//                    t1v.setBackgroundColor(Color.parseColor("#f8f8f8"));
                    t1v.setText(strContent.get(j));
                    t1v.setTextColor(Color.WHITE);
                    t1v.setGravity(Gravity.CENTER);
                    layout2.addView(t1v);
                    layout.addView(layout2);
//                    layout1.addView(t1v);
                    tbrow.addView(layout);
                }
                else
                {
//                    final TextView tv2 = new TextView(this);
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


                    final LinearLayout layout = new LinearLayout(this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setGravity(Gravity.CENTER);
                    layout.setPadding(0, 10, 0, 10);
                    layout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));



                    final LinearLayout layout2 = new LinearLayout(this);
                    layout2.setOrientation(LinearLayout.VERTICAL);
                    layout2.setGravity(Gravity.CENTER);
                    layout2.setPadding(0, 1, 0, 10);
                    layout2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));




                    final TextView tv4 = new TextView(this);

                    tv4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv4.setPadding(5, 15, 5, 15);
//                    layAmounts.setBackgroundColor(Color.parseColor("#ffffff"));

//                    layAmounts.setBackgroundResource(R.drawable.txt_bg1);
                    tv4.setGravity(Gravity.CENTER);
//                    tv4.setBackgroundResource(R.drawable.txt_bg1);

//                    tv4.setBackgroundColor(Color.parseColor("#ffffff"));

                    int[] colors = {Color.parseColor("#cbaf87"),Color.parseColor("#7e8a97"),Color.parseColor("#e7dec8")};

                    //create a new gradient color
                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM, colors);

                    gd.setCornerRadius(0f);

//                    ShapeDrawable myDrawable = new ShapeDrawable(new RectShape());
//                    myDrawable.getPaint().setShader(new LinearGradient(0, 0, 0, layout.getHeight(), colors[0], colors[1], Shader.TileMode.CLAMP));
//                    layout.setBackground(myDrawable);


                    layout.setBackground(gd);
//
                        layout2.setBackground(new Border(Color.DKGRAY, 1));

                    tv4.setTextColor(Color.WHITE);
                    tv4.setText(strContent.get(j));
                    tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);


                    layout2.addView(tv4);

                    layout.addView(layout2);

//                    final LinearLayout layout1 = new LinearLayout(this);
//                    layout1.setOrientation(LinearLayout.VERTICAL);
//                    layout1.setGravity(Gravity.RIGHT);
//                    layout1.setPadding(0, 5, 0, 5);
//                    layout1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
//                            TableRow.LayoutParams.MATCH_PARENT));
////                    layout1.setBackgroundResource(R.drawable.txt_bg1);
////                    layout1.setBackgroundColor(Color.LTGRAY);
//
//                    TextView t2v = new TextView(this);
//                    t2v.setPadding(5, 5, 1, 5);
//                    t2v.setText(strContent.get(j));
//                    t2v.setTextColor(Color.BLACK);
//                    t2v.setBackgroundColor(Color.LTGRAY);
//                    t2v.setGravity(Gravity.CENTER);
//                    layout1.addView(t2v);
                    tbrow.addView(layout);
                }

            }




            stk.addView(tbrow,trParams1);
//
//            final TableRow trSep = new TableRow(this);
//            TableLayout.LayoutParams trParamsSep = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
//                    TableLayout.LayoutParams.WRAP_CONTENT);
//            trParamsSep.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin);
//
//            trSep.setLayoutParams(trParamsSep);
//            TextView tvSep = new TextView(this);
//            TableRow.LayoutParams tvSepLay = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
//                    TableRow.LayoutParams.WRAP_CONTENT);
//            tvSepLay.span = strListHeader.size();
//            tvSep.setLayoutParams(tvSepLay);
//            tvSep.setBackgroundColor(Color.parseColor("#d9d9d9"));
//            tvSep.setHeight(1);
//
//
//            trSep.addView(tvSep);
//            stk.addView(trSep, trParamsSep);

        }


    }



    private  void  tabLayout2( ArrayList<String> strListHeader, ArrayList<List<String>> strListContent ,
                          int designNumber,int headerTextColor, int contentTextColor,int page )
    {
        int leftRowMargin = 0;
        int topRowMargin = 0;
        int rightRowMargin = 0;
        int bottomRowMargin = 0;

        int textSize, smallTextSize;

        textSize = (int) getResources().getDimension(R.dimen.font_size_verysmall);
        smallTextSize = (int) getResources().getDimension(R.dimen.font_size_small);

        int resID = getResources().getIdentifier("txt_bg"+designNumber, "drawable", getPackageName());


//        TableRow tbrow0 = new TableRow(this);
        stk.removeAllViews();
        final TableRow tr = new TableRow(this);


        TableLayout.LayoutParams trParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);

        trParams.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin);
        tr.setPadding(0, 0, 0, 0);
        tr.setLayoutParams(trParams);

        for (int i = 0; i < strListHeader.size(); i++) {

            tr.setId(i + 1);
            if (i%2==0) {
                final TextView tv = new TextView(this);
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
                int finalI = i;
                tv.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
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
                final LinearLayout layoutHeader1 = new LinearLayout(this);
                layoutHeader1.setOrientation(LinearLayout.VERTICAL);
                layoutHeader1.setGravity(Gravity.CENTER);

                layoutHeader1.setPadding(0, 10, 0, 10);
                layoutHeader1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));


                final TextView tv = new TextView(this);

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

                int finalI1 = i;
                tv.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
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

                final TableRow tbrow = new TableRow(this);
                tbrow.setId(i + 1);
                TableLayout.LayoutParams trParams1= new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);


                tbrow.setPadding(0, 0,0 , 0);
                tbrow.setLayoutParams(trParams1);

                ArrayList<String> strContent = (ArrayList<String>) strListContent.get(i);
                for (int j = 0; j <strContent.size() ; j++) {

                    if (j%2 == 0)
                    {


                        final LinearLayout layout = new LinearLayout(this);
                        layout.setOrientation(LinearLayout.VERTICAL);
                        layout.setGravity(Gravity.CENTER);
                        layout.setPadding(0, 10, 0, 10);
                        layout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT));
                        layout.setBackgroundResource(resID);






                        final TextView t1v = new TextView(this);
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


                        final LinearLayout layout = new LinearLayout(this);
                        layout.setOrientation(LinearLayout.VERTICAL);
                        layout.setGravity(Gravity.CENTER);
                        layout.setPadding(0, 10, 0, 10);
                        layout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT));






                        final TextView tv4 = new TextView(this);

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

                   final TableRow tbrow = new TableRow(this);
                   tbrow.setId(i + 1);
                   TableLayout.LayoutParams trParams1= new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                           TableLayout.LayoutParams.WRAP_CONTENT);


                   tbrow.setPadding(0, 0,0 , 0);
                   tbrow.setLayoutParams(trParams1);

                   ArrayList<String> strContent = (ArrayList<String>) strListContent.get(i);
                   for (int j = 0; j <strContent.size() ; j++) {

                       if (j%2 == 0)
                       {


                           final LinearLayout layout = new LinearLayout(this);
                           layout.setOrientation(LinearLayout.VERTICAL);
                           layout.setGravity(Gravity.CENTER);
                           layout.setPadding(0, 10, 0, 10);
                           layout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                   TableRow.LayoutParams.MATCH_PARENT));
                           layout.setBackgroundResource(resID);






                           final TextView t1v = new TextView(this);
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


                           final LinearLayout layout = new LinearLayout(this);
                           layout.setOrientation(LinearLayout.VERTICAL);
                           layout.setGravity(Gravity.CENTER);
                           layout.setPadding(0, 10, 0, 10);
                           layout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                   TableRow.LayoutParams.MATCH_PARENT));






                           final TextView tv4 = new TextView(this);

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

                   final TableRow tbrow = new TableRow(this);
                   tbrow.setId(i + 1);
                   TableLayout.LayoutParams trParams1= new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                           TableLayout.LayoutParams.WRAP_CONTENT);


                   tbrow.setPadding(0, 0,0 , 0);
                   tbrow.setLayoutParams(trParams1);

                   ArrayList<String> strContent = (ArrayList<String>) strListContent.get(i);
                   for (int j = 0; j <strContent.size() ; j++) {

                       if (j%2 == 0)
                       {


                           final LinearLayout layout = new LinearLayout(this);
                           layout.setOrientation(LinearLayout.VERTICAL);
                           layout.setGravity(Gravity.CENTER);
                           layout.setPadding(0, 10, 0, 10);
                           layout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                   TableRow.LayoutParams.MATCH_PARENT));
                           layout.setBackgroundResource(resID);






                           final TextView t1v = new TextView(this);
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


                           final LinearLayout layout = new LinearLayout(this);
                           layout.setOrientation(LinearLayout.VERTICAL);
                           layout.setGravity(Gravity.CENTER);
                           layout.setPadding(0, 10, 0, 10);
                           layout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                   TableRow.LayoutParams.MATCH_PARENT));






                           final TextView tv4 = new TextView(this);

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