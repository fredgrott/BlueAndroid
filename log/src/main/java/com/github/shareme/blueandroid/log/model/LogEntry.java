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
package com.github.shareme.blueandroid.log.model;

import android.util.Log;

@SuppressWarnings("unused")
public class LogEntry {
    private final int mLevel;
    private final String mTag;
    private final String mMessage;
    private final String mTimeStamp;

    public LogEntry(int level, String tag, String message, String timeStamp) {
        mLevel = level;
        mTag = tag;
        mMessage = message;
        mTimeStamp = timeStamp;
    }

    public int getLevel() {
        return mLevel;
    }

    public String getTag() {
        return mTag;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getTimeStamp() {
        return mTimeStamp;
    }

    public String prettyPrint() {
        return String.format("%18s %18s %s %s", mTimeStamp, mTag, displayLevel(), mMessage);
    }

    public String displayLevel() {
        switch (mLevel) {
            case Log.VERBOSE:
                return "V";
            case Log.DEBUG:
                return "D";
            case Log.INFO:
                return "I";
            case Log.WARN:
                return "W";
            case Log.ERROR:
                return "E";
            case Log.ASSERT:
                return "A";
            default:
                return "?";
        }
    }

}
