package com.aunico.os.commitment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommitmentHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    final CharSequence[] values = {"High", " Medium ", " Low"};
    Sqlitedatabaseclass mydatabase;
    public EditText input;
   private RecyclerAdapter adapter;
    public String Task, date, time,time1,Priority;
    public ArrayList<Datareterive> arrayList;
    public CardView cardView;
    CompletedTaskSqlite completedTaskSqlite;
    String outputPattern = "h:mm";
    SimpleDateFormat outputFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commitment_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         outputFormat= new SimpleDateFormat(outputPattern);




        arrayList = new ArrayList<>();
        completedTaskSqlite=new CompletedTaskSqlite(this);
        mydatabase = new Sqlitedatabaseclass(this);




        getdatafromsqlite();

        alertdialogforaddtask();

        Recyclerbuid();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void Recyclerbuid() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

         adapter= new RecyclerAdapter(arrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    public void alertdialogforaddtask()
    {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(CommitmentHome.this);

                builder.setMessage("Add Your Commitment");
                builder.setCancelable(false);
                LinearLayout layout = new LinearLayout(CommitmentHome.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                input = new EditText(CommitmentHome.this);

                input.setHint("Enter Task...");

                layout.addView(input);

                final Button btndate = new Button(CommitmentHome.this);
                btndate.setText("Select Date");
                layout.addView(btndate);
                btndate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CommitmentHome.this);

                        LinearLayout layout = new LinearLayout(CommitmentHome.this);
                        layout.setOrientation(LinearLayout.VERTICAL);
                        final DatePicker picker = new DatePicker(CommitmentHome.this);
                        layout.addView(picker);
                        builder.setView(layout);
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                date = picker.getDayOfMonth() + "-" + (picker.getMonth() + 1) + "-" + picker.getYear();
                                Toast.makeText(CommitmentHome.this, "" + date, Toast.LENGTH_SHORT).show();


                            }
                        });
                        AlertDialog dialog = builder.create();
                        builder.show();


                    }
                });


                final Button btntime = new Button(CommitmentHome.this);
                btntime.setText("Select Time");
                layout.addView(btntime);
                btntime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CommitmentHome.this);

                        LinearLayout layout = new LinearLayout(CommitmentHome.this);
                        layout.setOrientation(LinearLayout.VERTICAL);
                        final TimePicker picker = new TimePicker(CommitmentHome.this);
                        layout.addView(picker);
                        builder.setView(layout);
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(CommitmentHome.this, "" + picker.getCurrentHour() + ":" + picker.getCurrentMinute(), Toast.LENGTH_SHORT).show();
                                time1 = picker.getCurrentHour() + ":" + picker.getCurrentMinute();
                                try {
                                    final Date dateObj;
                                    dateObj = outputFormat.parse(time1);
                                    time=new SimpleDateFormat("K:mm a").format(dateObj)+"";
                                } catch (final ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        AlertDialog dialog = builder.create();
                        builder.show();


                    }
                });

                final Button btn = new Button(CommitmentHome.this);
                btn.setText("Select Priority");
                layout.addView(btn);


                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(CommitmentHome.this);

                        builder1.setTitle("Choose priortiy...");

                        builder1.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(CommitmentHome.this, "" + values[i], Toast.LENGTH_SHORT).show();

                                Priority = values[i] + "";
                                dialogInterface.dismiss();
                            }
                        });
                        builder1.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        AlertDialog alertDialog = builder1.create();

                        builder1.show();

                    }
                });


                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Task = input.getText().toString();
                        Boolean result = mydatabase.insertdata(Task, date, time, Priority);
                        if (result == true) {
                            getLastdatafromsqlite();
                            Toast.makeText(CommitmentHome.this, "saved successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CommitmentHome.this, "Failed..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        mydatabase.deleteAll();
                        dialogInterface.cancel();
                    }
                });

                builder.setView(layout);

                AlertDialog alert = builder.create();
                builder.show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.commitment_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            Intent intent=new Intent(this,CompletedTask.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void getdatafromsqlite() {
        Cursor res = mydatabase.getalldata();
        Datareterive datareterive = null;
        StringBuffer stringBuffer = new StringBuffer();
        while (res.moveToNext()) {
            datareterive = new Datareterive();
            String id=res.getString(0);
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


    private void getLastdatafromsqlite() {
        Cursor res = mydatabase.getalldata();
        Datareterive datareterive = null;
        StringBuffer stringBuffer = new StringBuffer();
        while (res.moveToNext()) {
            if (res.moveToLast()) {
                datareterive = new Datareterive();
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

    }


    public void delete(final View v)
    {


        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Is Task Completed..");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                View parent=(View) v.getParent();
                TextView taskTextView = (TextView) parent.findViewById(R.id.task);
                TextView taskTextView1 = (TextView) parent.findViewById(R.id.date);
                TextView taskTextView2 = (TextView) parent.findViewById(R.id.time);
                TextView taskTextView3 = (TextView) parent.findViewById(R.id.priority);
                String task1 = String.valueOf(taskTextView.getText());
                String date1 = String.valueOf(taskTextView1.getText());
                String time1 = String.valueOf(taskTextView2.getText());
                String priority1 = String.valueOf(taskTextView3.getText());
                boolean resultinsert=completedTaskSqlite.insertdatacompletetask(task1,date1,time1,priority1);

                if (resultinsert==true)
                {
                    Toast.makeText(CommitmentHome.this, "saved In History", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(CommitmentHome.this, "failed..", Toast.LENGTH_SHORT).show();
                }
                int result=mydatabase.deletedata(task1);
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


}