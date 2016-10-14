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
package com.github.shareme.blueandroid.log;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.shareme.blueandroid.base.DebugModule;
import com.github.shareme.blueandroid.log.ui.LogDialog;

@SuppressWarnings("unused")
public class LogModule implements DebugModule {

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull final ViewGroup parent) {
        View view = inflater.inflate(R.layout.debug_drawer_module_log, parent, false);

        view.findViewById(R.id.button_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LogDialog(parent.getContext()).show();
            }
        });

        return view;
    }

    @Override
    public void onOpened() {

    }

    @Override
    public void onClosed() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
