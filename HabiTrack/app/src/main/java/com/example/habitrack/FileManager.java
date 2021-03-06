package com.example.habitrack;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by sshussai on 11/29/17.
 */

/**
 * Steps to add a new object:
 * 1. Declare a base object
 * 2. Declare a public final MODE value
 * 2. Declare a private final FILENAME value
 * 3. Add the if case to the save function. Modify as needed
 * 4. Add the if case to the load function. Modify as needed
 */

public class FileManager {

    private Context ctx;

    // 1. Base objects
    private ArrayList<HabitTypeMetadata> habitTypeMetadata;
    private ArrayList<HabitEvent> recentHabitEvents;
    private ArrayList<HabitEvent> todayHabitEvents;
    private ArrayList<HabitEvent> editedOfflineHEs;
    private ArrayList<HabitEvent> newOfflineHEs;
    private Calendar date;

    // 2. MODES
    public final Integer HT_METADATA_MODE = 100;
    public final Integer RECENT_HE_MODE = 200;
    public final Integer TODAY_HE_MODE = 300;
    public final Integer EDITED_OFFLINE_HE_MODE = 400;
    public final Integer NEW_OFFLINE_HE_MODE = 500;
    public final Integer DATE_MODE = 600;


    // 3. FILENAMES
    private String filename;
    private final String HT_METADATA_FILE = "htmetadata.sav";
    private final String RECENT_HE_FILE = "recenthabitevents.sav";
    private final String TODAY_HE_FILE = "todayhabitevents.sav";
    private final String EDITED_OFFLINE_HE_FILE = "editedOfflinehabitevents.sav";
    private final String NEW_OFFLINE_HE_FILE = "newOfflinehabitevents.sav";
    private final String DATE_FILE = "htdate.sav";

    // Constructor
    public FileManager(Context context) {
        this.ctx = context;
    }

    public void save(Integer mode){
        // 4. If cases for the save function
        if(mode == HT_METADATA_MODE){
            habitTypeMetadata = HabitTypeStateManager.getHTStateManager().getHtMetadataAll();
            filename = HT_METADATA_FILE;
        } else if(mode == RECENT_HE_MODE){
            recentHabitEvents = HabitEventStateManager.getHEStateManager().getRecentHabitevents();
            filename = RECENT_HE_FILE;
        } else if(mode == TODAY_HE_MODE){
            todayHabitEvents = HabitEventStateManager.getHEStateManager().getTodayHabitevents();
            filename = TODAY_HE_FILE;
        } else if(mode == EDITED_OFFLINE_HE_MODE){
            editedOfflineHEs = HabitEventStateManager.getHEStateManager().getEditedOfflineHE();
            filename = EDITED_OFFLINE_HE_FILE;
        } else if(mode == NEW_OFFLINE_HE_MODE){
            newOfflineHEs = HabitEventStateManager.getHEStateManager().getNewOfflineHE();
            filename = NEW_OFFLINE_HE_FILE;
        } else if(mode == DATE_MODE){
            date = HabitTypeStateManager.getHTStateManager().getHabitTypeDate();
            filename = DATE_FILE;
        }
        try {
            FileOutputStream fos = ctx.openFileOutput(filename,0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            if(mode == HT_METADATA_MODE) {
                gson.toJson(habitTypeMetadata, writer);
            } else if (mode == RECENT_HE_MODE){
                gson.toJson(recentHabitEvents, writer);
            } else if(mode == TODAY_HE_MODE){
                gson.toJson(todayHabitEvents, writer);
            } else if(mode == EDITED_OFFLINE_HE_MODE){
                gson.toJson(editedOfflineHEs, writer);
            } else if(mode == NEW_OFFLINE_HE_MODE){
                gson.toJson(newOfflineHEs, writer);
            } else if(mode == DATE_MODE) {
                gson.toJson(date, writer);
            }
            writer.flush();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    public void load(Integer mode) {
        // 5. If cases for load function
        if(mode == HT_METADATA_MODE){
            filename = HT_METADATA_FILE;
        } else if(mode == RECENT_HE_MODE){
            filename = RECENT_HE_FILE;
        } else if(mode == TODAY_HE_MODE){
            filename = TODAY_HE_FILE;
        } else if(mode == EDITED_OFFLINE_HE_MODE){
            filename = EDITED_OFFLINE_HE_FILE;
        } else if(mode == NEW_OFFLINE_HE_MODE){
            filename = NEW_OFFLINE_HE_FILE;
        } else if(mode == DATE_MODE){
            filename = DATE_FILE;
        }
        try {
            FileInputStream fis = ctx.openFileInput(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            //Code taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt Sept.22,2016
            if(mode == HT_METADATA_MODE) {
                Type calType = new TypeToken<ArrayList<HabitTypeMetadata>>() {}.getType();
                habitTypeMetadata = gson.fromJson(in, calType);
            } else if (mode == RECENT_HE_MODE){
                Type calType = new TypeToken<ArrayList<HabitEvent>>() {}.getType();
                recentHabitEvents = gson.fromJson(in, calType);
            } else if (mode == TODAY_HE_MODE){
                Type calType = new TypeToken<ArrayList<HabitEvent>>() {}.getType();
                todayHabitEvents = gson.fromJson(in, calType);
            } else if (mode == EDITED_OFFLINE_HE_MODE){
                Type calType = new TypeToken<ArrayList<HabitEvent>>() {}.getType();
                editedOfflineHEs = gson.fromJson(in, calType);
            } else if (mode == NEW_OFFLINE_HE_MODE){
                Type calType = new TypeToken<ArrayList<HabitEvent>>() {}.getType();
                newOfflineHEs = gson.fromJson(in, calType);
            } else if (mode == DATE_MODE){
                Type calType = new TypeToken<Calendar>() {}.getType();
                date = gson.fromJson(in, calType);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            if(mode == HT_METADATA_MODE){
                habitTypeMetadata = new ArrayList<HabitTypeMetadata>();
            } else if(mode == RECENT_HE_MODE){
                recentHabitEvents = new ArrayList<HabitEvent>();
            } else if(mode == TODAY_HE_MODE){
                todayHabitEvents = new ArrayList<HabitEvent>();
            } else if(mode == EDITED_OFFLINE_HE_MODE){
                editedOfflineHEs = new ArrayList<HabitEvent>();
            } else if(mode == NEW_OFFLINE_HE_MODE){
                newOfflineHEs = new ArrayList<HabitEvent>();
            } else if(mode == DATE_MODE){
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.DATE, 1);
                newDate.set(Calendar.MONTH, 1);
                newDate.set(Calendar.YEAR, 1);
                date = newDate;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
        if(mode == HT_METADATA_MODE){
            HabitTypeStateManager.getHTStateManager().setHtMetadataAll(habitTypeMetadata);
        } else if(mode == RECENT_HE_MODE){
            HabitEventStateManager.getHEStateManager().setRecentHabitEvents(recentHabitEvents);
        } else if(mode == TODAY_HE_MODE){
            HabitEventStateManager.getHEStateManager().setTodayHabitEvents(todayHabitEvents);
        } else if(mode == EDITED_OFFLINE_HE_MODE){
            HabitEventStateManager.getHEStateManager().setEditedOfflineHE(editedOfflineHEs);
        } else if(mode == NEW_OFFLINE_HE_MODE){
            HabitEventStateManager.getHEStateManager().setNewOfflineHE(newOfflineHEs);
        } else if(mode == DATE_MODE){
            HabitTypeStateManager.getHTStateManager().setHabitTypeDate(date);
        }
    }

}


