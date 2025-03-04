package com.scouting_app_2025.Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.scouting_app_2025.UIElements.GUIManager;
import com.scouting_app_2025.databinding.PostMatchFragmentBinding;

public class PostMatchFragment extends Fragment {
    PostMatchFragmentBinding binding;
    GUIManager guiManager = new GUIManager();

    public PostMatchFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = PostMatchFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

        @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        guiManager.createCheckbox(, binding.parkCheckbox, false);
    }

    @NonNull
    @Override
    public String toString() {
        return "PreAutonFragment";
    }
}
