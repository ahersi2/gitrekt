package com.example.habitrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class HabitTypeDetailsActivity extends AppCompatActivity {
    TextView progressNum;
    EditText titleEdit;
    EditText reasonEdit;
    EditText dateEdit;
    ProgressBar progressBar;
    Integer progressPercent;
    //Button editButton;
    Button deleteButton;

    // checkboxes for days of the week
    CheckBox sundayBox;
    CheckBox mondayBox;
    CheckBox tuesdayBox;
    CheckBox wednesdayBox;
    CheckBox thursdayBox;
    CheckBox fridayBox;
    CheckBox saturdayBox;
    // Edit schedule toggle
    Button editSchedule;
    // Bool checker
    //Boolean canEditSchedule = Boolean.FALSE;
    // Date var
    Calendar date;
    // Request Code for date entry
    private final Integer DATE_ENTRY = 101;

    HabitTypeController htc = new HabitTypeController(this);

    TextView titleView;
    TextView reasonView;
    TextView dateView;

    private String titleString;
    private String reasonString;
    private String dateString;
    private Integer htID = -1;
    private String esID;
    private ArrayList<Integer> schedule;
    HabitType currHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_type_details);
        // Get intent
        Intent intent = getIntent();
        // Get esID
        esID = intent.getStringExtra("esID");
        currHT = htc.getHabitTypeFromES(esID);

        if(currHT.getID() != -1){
            titleString = currHT.getTitle();
            reasonString = currHT.getReason();
            final Calendar date = currHT.getStartDate();
            dateString = date.get(Calendar.MONTH) + "/" +
                    date.get(Calendar.DATE) + "/" +
                    date.get(Calendar.YEAR);

            // Get text views
            titleView = (TextView) findViewById(R.id.titleView);
            reasonView = (TextView) findViewById(R.id.reasonView);
            dateView = (TextView) findViewById(R.id.dateView);
            progressNum = (TextView) findViewById(R.id.statPercent);

            // Display the text
            titleView.setText(titleString); // title
            reasonView.setText(reasonString); // reason
            dateView.setText(dateString); // date

            // Set progress bar
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            // Set the max, and the progress
            //progressBar.setMax(htc.getMaxCounter(htID));
            progressBar.setMax(currHT.getCurrentMaxCounter());
            progressBar.setProgress(currHT.getCompletedCounter());
            progressNum.setText(currHT.getCompletedCounter().toString() + "/" + currHT.getCurrentMaxCounter().toString());

            // Get the edit text references
            titleEdit = (EditText) findViewById(R.id.titleBox);
            reasonEdit = (EditText) findViewById(R.id.commentBox);
            dateEdit = (EditText) findViewById(R.id.startDateBox);

            // Edit button
            //editButton = (Button) findViewById(R.id.button9);

            // Get the checkboxes for days of the week
            sundayBox = (CheckBox) findViewById(R.id.sunCheckBox);
            mondayBox = (CheckBox) findViewById(R.id.monCheckBox);
            tuesdayBox = (CheckBox) findViewById(R.id.tuesCheckBox);
            wednesdayBox = (CheckBox) findViewById(R.id.wedCheckBox);
            thursdayBox = (CheckBox) findViewById(R.id.thursCheckBox);
            fridayBox = (CheckBox) findViewById(R.id.friCheckBox);
            saturdayBox = (CheckBox) findViewById(R.id.satCheckBox);
            // Get the edit toggle
            editSchedule = (Button) findViewById(R.id.editSchedule);

            // delete button
            deleteButton = (Button) findViewById(R.id.deleteButton);

            titleView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    titleView.setVisibility(View.GONE);
                    titleEdit.setVisibility(View.VISIBLE);
                    titleEdit.setText(titleString);
                    titleEdit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            String newTitle = titleEdit.getText().toString();
                            htc.editHabitTypeTitle(currHT, newTitle);
                        }
                    });
                    return false;
                }
            });
            titleEdit.setVisibility(View.GONE);
            titleView.setVisibility(View.VISIBLE);

            reasonView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    reasonView.setVisibility(View.GONE);
                    reasonEdit.setVisibility(View.VISIBLE);
                    reasonEdit.setText(reasonString);
                    reasonEdit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            String newReason = reasonEdit.getText().toString();
                            htc.editHabitTypeReason(currHT, newReason);
                        }
                    });
                    return false;
                }
            });
            reasonEdit.setVisibility(View.GONE);
            reasonView.setVisibility(View.VISIBLE);

            dateView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent i = new Intent(getApplicationContext(), DateSelector.class);
                    startActivityForResult(i, DATE_ENTRY);

                    return false;
                }
            });
            dateEdit.setVisibility(View.GONE);
            dateView.setVisibility(View.VISIBLE);

            // delete button functionality
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    htc.deleteHabitType(esID);
                    finish();
                }
            });

            // check boxes for the schedules
            schedule = currHT.getSchedule();
            for (Integer count = 0; count < schedule.size(); count++) {
                Integer res = schedule.get(count);
                if (res == Calendar.SUNDAY) {
                    sundayBox.setChecked(Boolean.TRUE);
                } else if (res == Calendar.MONDAY) {
                    mondayBox.setChecked(Boolean.TRUE);
                } else if (res == Calendar.TUESDAY) {
                    tuesdayBox.setChecked(Boolean.TRUE);
                } else if (res == Calendar.WEDNESDAY) {
                    wednesdayBox.setChecked(Boolean.TRUE);
                } else if (res == Calendar.THURSDAY) {
                    thursdayBox.setChecked(Boolean.TRUE);
                } else if (res == Calendar.FRIDAY) {
                    fridayBox.setChecked(Boolean.TRUE);
                } else if (res == Calendar.SATURDAY) {
                    saturdayBox.setChecked(Boolean.TRUE);
                }
            }


            editSchedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<Integer> newSchedule;
                    newSchedule = new ArrayList<>();
                    if (sundayBox.isChecked()) {
                        newSchedule.add(Calendar.SUNDAY);
                    }

                    if (mondayBox.isChecked()) {
                        newSchedule.add(Calendar.MONDAY);
                    }
                    if (tuesdayBox.isChecked()) {
                        newSchedule.add(Calendar.TUESDAY);
                    }
                    if (wednesdayBox.isChecked()) {
                        newSchedule.add(Calendar.WEDNESDAY);
                    }
                    if (thursdayBox.isChecked()) {
                        newSchedule.add(Calendar.THURSDAY);
                    }
                    if (fridayBox.isChecked()) {
                        newSchedule.add(Calendar.FRIDAY);
                    }
                    if (saturdayBox.isChecked()) {
                        newSchedule.add(Calendar.SATURDAY);
                    }

                    Toast.makeText(getApplicationContext(), "Applying Edits",
                            Toast.LENGTH_SHORT).show();
                    for (Integer log : newSchedule) {
                        Log.v("tag", Integer.toString(log));
                    }
                    htc.editHabitTypeSchedule(currHT, newSchedule);
                }
            });
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == DATE_ENTRY) {
            if(resultCode == Activity.RESULT_OK){
                Integer year = data.getIntExtra("year", -1);
                Integer month = data.getIntExtra("month", -1);
                Integer day = data.getIntExtra("day", -1);
                if(!year.equals(-1) && !month.equals(-1) && !day.equals(-1)){
                    date = Calendar.getInstance();
                    date.set(Calendar.YEAR, year);
                    date.set(Calendar.MONTH, month);
                    date.set(Calendar.DATE, day);
                    dateView.setText(month.toString() + "/" + day.toString() + "/" + year.toString());
                    htc.editHabitTypeStartDate(currHT, date);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }





    @Override
    protected void onStart() {
        super.onStart();


    }

}
