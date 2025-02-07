package com.scouting_app_2025;

import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

public class ParentFragment extends Fragment {
    ViewBinding parentBinding;

    public ParentFragment(ViewBinding childBinding) {
        this.parentBinding = childBinding;
    }
}
