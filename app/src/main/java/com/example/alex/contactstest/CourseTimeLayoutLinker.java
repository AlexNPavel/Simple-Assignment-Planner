package com.example.alex.contactstest;

import android.widget.ImageView;

/**
 * Created by alex on 9/18/15.
 */
public class CourseTimeLayoutLinker {
    CourseTimeLayout layout;
    CourseTimeStore store;

    public CourseTimeLayoutLinker(CourseTimeLayout lay, CourseTimeStore sto) {
        layout = lay;
        store = sto;
    }

    public ImageView getImageView() {
        return layout.getImageView();
    }

    public void createLayout() {
        layout.createLayout();
    }

    public void removeLayout() {
        layout.removeLayout();
    }

    public CourseTimeStore getStore() {
        return store;
    }

}
