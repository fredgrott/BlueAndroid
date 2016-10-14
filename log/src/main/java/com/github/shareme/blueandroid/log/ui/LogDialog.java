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
package com.github.shareme.blueandroid.log.ui;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.widget.ListView;
import android.widget.Toast;

import com.github.shareme.blueandroid.log.data.LumberYard;
import com.github.shareme.blueandroid.log.model.LogEntry;
import com.github.shareme.blueandroid.log.util.Intents;

import java.io.File;

@SuppressWarnings("unused")
public class LogDialog extends AlertDialog {

    private final LogAdapter mAdapter;

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public LogDialog(Context context) {
        super(context);

        mAdapter = new LogAdapter();

        ListView listView = new ListView(context);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        listView.setAdapter(mAdapter);

        setTitle("Logs");
        setView(listView);
        setButton(BUTTON_NEGATIVE, "Close", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /* no-op */
            }
        });
        setButton(BUTTON_POSITIVE, "Share", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                share();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        LumberYard lumberYard = LumberYard.getInstance(getContext());

        mAdapter.setLogs(lumberYard.bufferedLogs());

        lumberYard.setOnLogListener(new LumberYard.OnLogListener() {
            @Override
            public void onLog(LogEntry logEntry) {

                addLogEntry(logEntry);
            }
        });
    }

    private void addLogEntry(final LogEntry logEntry) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.addLog(logEntry);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        LumberYard.getInstance(getContext()).setOnLogListener(null);
    }

    private void share() {
        LumberYard.getInstance(getContext())
                .save(new LumberYard.OnSaveLogListener() {
                    @Override
                    public void onSave(File file) {
                        Intent sendIntent = new Intent(Intent.ACTION_SEND);
                        sendIntent.setType("text/plain");
                        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                        Intents.maybeStartActivity(getContext(), sendIntent);
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
