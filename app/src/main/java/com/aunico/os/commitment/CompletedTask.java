package com.aunico.os.commitment;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CompletedTask extends AppCompatActivity {

    private RecyclerAdapter adapter;
    public ArrayList<Datareterive> arrayList;
    CompletedTaskSqlite mydatabasecomp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_task);


        mydatabasecomp=new CompletedTaskSqlite(this);
        arrayList = new ArrayList<>();

        Button button=(Button)findViewById(R.id.clearhistorybtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(CompletedTask.this);
                builder.setTitle("Are You Sure...");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mydatabasecomp.deleteAllcomptaskdata();
                        updatedata();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog=builder.create();
                builder.show();

            }
        });
        getdatafromsqlite();
        Recyclerbuid();

        }




public void Recyclerbuid()
{
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.comprecyclerview);
    adapter = new RecyclerAdapter(arrayList);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
    adapter.notifyDataSetChanged();

}

    public void updatedata()
    {
        arrayList=new ArrayList<>();
        getdatafromsqlite();

        if (adapter==null)
        {
            Recyclerbuid();
        }
        else {

            Recyclerbuid();
            adapter.notifyDataSetChanged();
        }


    }

public void getdatafromsqlite()
{
    Cursor res = mydatabasecomp.getcompletetaskdata();
    Datareterive datareterive = null;
    StringBuffer stringBuffer = new StringBuffer();
    while (res.moveToNext()) {
        datareterive = new Datareterive();
        String id = res.getString(0);
        String task = res.getString(1);
        String date = res.getString(res.getColumnIndexOrThrow("date"));
        String time = res.getString(res.getColumnIndexOrThrow("time"));
        String priority = res.getString(res.getColumnIndexOrThrow("Priority"));
        datareterive.setTask(task);
        datareterive.setDate(date);
        datareterive.setTime(time);

        datareterive.setPriority(priority);
        stringBuffer.append(datareterive);
        arrayList.add(datareterive);
    }

}
public void delete(View view)
{
    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
}
}