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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.shareme.blueandroid.log.R;
import com.github.shareme.blueandroid.log.model.LogEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class LogAdapter extends BaseAdapter {
    private List<LogEntry> mLogEntries = Collections.emptyList();

    @Override
    public int getCount() {
        return mLogEntries.size();
    }

    @Override
    public Object getItem(int position) {
        return mLogEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log_entry, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LogEntry logEntry = mLogEntries.get(position);
        viewHolder.fillData(logEntry);

        return convertView;
    }

    public void setLogs(List<LogEntry> logs) {
        mLogEntries = new ArrayList<>(logs);
    }

    public void addLog(LogEntry logEntry) {
        mLogEntries.add(logEntry);

        notifyDataSetChanged();
    }

    static class ViewHolder {

        View mRootView;
        TextView mLogLevelTextView;
        TextView mLogTagTextView;
        TextView mLogMessageTextView;

        ViewHolder(View view) {
            mRootView = view;
            mLogLevelTextView = (TextView) view.findViewById(R.id.text_log_level);
            mLogTagTextView = (TextView) view.findViewById(R.id.text_log_tag);
            mLogMessageTextView = (TextView) view.findViewById(R.id.text_log_message);
        }

        void fillData(LogEntry entry) {
            mRootView.setBackgroundResource(backgroundForLevel(entry.getLevel()));
            mLogLevelTextView.setText(entry.displayLevel());
            mLogTagTextView.setText(String.format("%s %s", entry.getTimeStamp(), entry.getTag()));
            mLogMessageTextView.setText(entry.getMessage());
        }
    }

    public static int backgroundForLevel(int level) {
        switch (level) {
            case Log.VERBOSE:
            case Log.DEBUG:
                return R.color.debug_log_accent_debug;
            case Log.INFO:
                return R.color.debug_log_accent_info;
            case Log.WARN:
                return R.color.debug_log_accent_warn;
            case Log.ERROR:
            case Log.ASSERT:
                return R.color.debug_log_accent_error;
            default:
                return R.color.debug_log_accent_unknown;
        }
    }
}
