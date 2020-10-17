# TabelLayoutReport
How to use this library

It's free to any one to use it.

In your activity will create list of headers and from your custom adapter will create list of String content of this adapter.




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
    
    MyView myView = new MyView(this);
        myView.loadingDataTable(listHeaders(),setContentList(),1,5,Color.WHITE,Color.WHITE);
        setContentView(myView);


 
