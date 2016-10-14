/*
 * Copyright (C) 2015 Mantas Palaima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.shareme.blueandroid.debugdrawer;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.view.ViewGroup;

import com.github.shareme.blueandroid.base.DebugModule;

@SuppressWarnings("unused")
public class DebugDrawer {


    private DebugDrawer(Builder builder) {

    }

    /**
     * Open the drawer
     */
    public void openDrawer() {

    }

    /**
     * close the drawer
     */
    public void closeDrawer() {

    }

    /**
     * Get the current state of the drawer.
     * True if the drawer is currently open.
     *
     * @return
     */
    public boolean isDrawerOpen() {
        return false;
    }

    /**
     * Calls modules {@link DebugModule#onResume()} method
     */
    public void onResume() {

    }

    /**
     * Calls modules {@link DebugModule#onPause()} method
     */
    public void onPause() {

    }

    /**
     * Starts all modules and calls their {@link DebugModule#onStart()} method
     */
    public void onStart() {

    }

    /**
     * Removes all modules and calls their {@link DebugModule#onStop()} method
     */
    public void onStop() {

    }

    public static class Builder {

        /**
         * Pass the activity you use the drawer in ;)
         * This is required if you want to set any values by resource
         */
        public Builder(Activity activity) {

        }

        /**
         * Pass the rootView of the Drawer which will be used to inflate the DrawerLayout in
         */
        public Builder rootView(ViewGroup rootView) {
            return this;
        }

        /**
         * Pass the rootView as resource of the Drawer which will be used to inflate the
         * DrawerLayout in
         */
        public Builder rootView(int rootViewRes) {
            return this;
        }

        /**
         * Set the gravity for the drawer. START, LEFT | RIGHT, END
         */
        public Builder gravity(int gravity) {
            return this;
        }

        /**
         * Set the Drawer width with a pixel value
         */
        public Builder widthPx(int drawerWidthPx) {
            return this;
        }

        /**
         * Set the Drawer width with a dp value
         */
        public Builder widthDp(int drawerWidthDp) {
            return this;
        }

        /**
         * Set the Drawer width with a dimension resource
         */
        public Builder widthRes(int drawerWidthRes) {
            return this;
        }

        /**
         * Set the background color for the Slider.
         * This is the view containing the list.
         */
        public Builder backgroundColor(int sliderBackgroundColor) {
            return this;
        }

        /**
         * Set the background color for the Slider from a Resource.
         * This is the view containing the list.
         */
        public Builder backgroundColorRes(@IntegerRes int sliderBackgroundColorRes) {
            return this;
        }


        /**
         * Set the background drawable for the Slider.
         * This is the view containing the list.
         */
        public Builder backgroundDrawable(Drawable sliderBackgroundDrawable) {
            return this;
        }


        /**
         * Set the background drawable for the Slider from a Resource.
         * This is the view containing the list.
         */
        public Builder backgroundDrawableRes(@DrawableRes int sliderBackgroundDrawableRes) {
            return this;
        }

        /**
         * Add a initial DrawerItem or a DrawerItem Array  for the Drawer
         */
        public Builder modules(DebugModule... drawerItems) {
            return this;
        }

        /**
         * Build and add the Drawer to your activity
         *
         * @return
         */
        public DebugDrawer build() {
            return new DebugDrawer(this);
        }
    }
}
