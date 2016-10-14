/*
Copyright 2015 Mantas Palaima.
Modifications Copyright(C) 2016 Fred Grott(GrottWorkShop)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */
package com.github.shareme.blueandroid.log.data;

import android.content.Context;
import android.os.AsyncTask;

import com.github.shareme.blueandroid.log.model.LogEntry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Deque;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

@SuppressWarnings("unused")
public class LumberYard {
    private static final int BUFFER_SIZE = 200;


  private static final DateFormat FILENAME_DATE = new SimpleDateFormat("yyyy-MM-dd hhmm a", Locale.US);
    private static final DateFormat LOG_DATE_PATTERN = new SimpleDateFormat("MM-dd hh:mm:ss.S", Locale.US);

    private static final String LOG_FILE_END = ".log";

    private static LumberYard sInstance;

    private final Context mContext;

    private final Deque<LogEntry> entries = new ArrayDeque<>(BUFFER_SIZE + 1);

    private OnLogListener mOnLogListener;

    public LumberYard(Context context) {

      mContext = context.getApplicationContext();

    }

    public static LumberYard getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new LumberYard(context);
        }

        return sInstance;
    }

    public Timber.Tree tree() {
        return new Timber.DebugTree() {
            @Override
            protected void log(int priority, String tag, String message, Throwable t) {
                addEntry(new LogEntry(priority, tag, message, LOG_DATE_PATTERN.format(Calendar.getInstance().getTime())));
            }
        };
    }

    public void setOnLogListener(OnLogListener onLogListener) {
        mOnLogListener = onLogListener;
    }

    private synchronized void addEntry(LogEntry entry) {
        entries.addLast(entry);

        if (entries.size() > BUFFER_SIZE) {
            entries.removeFirst();
        }

        onLog(entry);
    }

    public List<LogEntry> bufferedLogs() {
        return new ArrayList<>(entries);
    }

    /**
     * Save the current logs to disk.
     */
    public void save(OnSaveLogListener listener) {
        File dir = getLogDir();

        if (dir == null) {
            listener.onError("Can't save logs. External storage is not mounted. " +
                    "Check android.permission.WRITE_EXTERNAL_STORAGE permission");
            return;
        }

        FileWriter fileWriter = null;

        try {
            File output = new File(dir, getLogFileName());
            fileWriter = new FileWriter(output, true);

            List<LogEntry> entries = bufferedLogs();
            for (LogEntry entry : entries) {
                fileWriter.write(entry.prettyPrint() + "\n");
            }

            listener.onSave(output);

        } catch (IOException e) {
            listener.onError(e.getMessage());
            e.printStackTrace();

        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    listener.onError(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    //a lot of people copy jake Wharton's tricks,
   // at least do it effing correctly, as deleting is always
   //on a nonUI thread..sheesh!
    public void cleanUp() {
      new AsyncTask<Void, Void, Void>() {
        @Override protected Void doInBackground(Void... folders) {
          File folder = mContext.getExternalFilesDir(null);
          if (folder != null) {
            for (File file : folder.listFiles()) {
              if (file.getName().endsWith(".log")) {
                file.delete();
              }
            }
          }

          return null;
        }
      }.execute();

    }

    private File getLogDir() {
        return mContext.getExternalFilesDir(null);
    }

    private void onLog(LogEntry entry) {
        if (mOnLogListener != null) {
            mOnLogListener.onLog(entry);
        }
    }

    private String getLogFileName() {
        String pattern = "%s%s";
        String currentDate = FILENAME_DATE.format(Calendar.getInstance().getTime());

        return String.format(pattern, currentDate, LOG_FILE_END);
    }

    public interface OnSaveLogListener {
        void onSave(File file);

        void onError(String message);
    }

    public interface OnLogListener {
        void onLog(LogEntry logEntry);
    }
}
