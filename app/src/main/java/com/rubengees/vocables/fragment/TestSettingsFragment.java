package com.rubengees.vocables.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rubengees.vocables.R;
import com.rubengees.vocables.activity.ExtendedToolbarActivity;
import com.rubengees.vocables.core.Core;
import com.rubengees.vocables.core.mode.Mode;
import com.rubengees.vocables.core.testsettings.TestSettings;
import com.rubengees.vocables.core.testsettings.layout.TestSettingsLayout;
import com.rubengees.vocables.data.VocableManager;
import com.rubengees.vocables.utils.SnackbarManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestSettingsFragment extends MainFragment implements TestSettingsLayout.OnTestSettingsListener {

    private static final String KEY_MODE = "mode";
    private static final String KEY_TEST_SETTINGS = "test_settings";
    private static final String STATE_VOCABLE_AMOUNT = "vocable_amount";

    private Mode mode;
    private TestSettings settings;
    private TestSettingsLayout layout;
    private VocableManager manager;

    private TextView status;
    private int vocableAmount;

    public TestSettingsFragment() {
        // Required empty public constructor
    }

    public static TestSettingsFragment newInstance(Mode mode) {
        TestSettingsFragment fragment = new TestSettingsFragment();
        Bundle args = new Bundle();

        args.putParcelable(KEY_MODE, mode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mode = getArguments().getParcelable(KEY_MODE);
            layout = mode.getTestSettingsLayout(getActivity(), this);
        }

        if (savedInstanceState != null) {
            settings = savedInstanceState.getParcelable(KEY_TEST_SETTINGS);
        }

        manager = Core.getInstance(getActivity()).getVocableManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View header = inflater.inflate(R.layout.fragment_test_settings_header, container, false);
        status = (TextView) header.findViewById(R.id.fragment_test_settings_header_status);

        final ViewGroup root = (ViewGroup) layout.inflateLayout(inflater, container, savedInstanceState);

        if (savedInstanceState == null) {
            settings = layout.generateTestSettings();
            getToolbarActivity().expandToolbar();

            updateStatus(calculateAmount(settings));
        } else {
            this.vocableAmount = savedInstanceState.getInt(STATE_VOCABLE_AMOUNT);
            updateStatus(vocableAmount);
        }

        getToolbarActivity().setToolbarView(header);
        getToolbarActivity().enableFab(R.drawable.ic_next, new ExtendedToolbarActivity.OnFabClickListener() {
            @Override
            public void onFabClick() {
                if (vocableAmount >= mode.getMinAmount()) {
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.content, TestFragment.newInstance(mode, settings)).commit();
                } else {
                    SnackbarManager.show(Snackbar.make(root, getString(R.string.fragment_test_settings_error_not_enough_vocables)
                            + " " + mode.getMinAmount(), Snackbar.LENGTH_LONG), null, null);
                }
            }
        });
        getToolbarActivity().styleApplication(mode.getColor(getActivity()), mode.getDarkColor(getActivity()));

        return root;
    }

    private void updateStatus(Integer amount) {
        if (amount == null) {
            amount = calculateAmount(settings);
        }

        this.status.setText(amount + " " + (amount == 1 ? getString(R.string.fragment_test_settings_vocable_selected) :
                getString(R.string.fragment_test_settings_vocables_selected)));
    }

    @Override
    public void onStop() {
        SnackbarManager.dismiss();
        super.onStop();
    }

    private int calculateAmount(TestSettings settings) {
        int result = 0;
        for (Integer integer : settings.getUnitIds()) {
            result += manager.getUnit(integer).getVocables(settings.getMaxRate()).size();
        }

        this.vocableAmount = result;

        return result;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_TEST_SETTINGS, settings);
        outState.putInt(STATE_VOCABLE_AMOUNT, vocableAmount);
        layout.saveInstanceState(outState);
    }

    @Override
    public void onChange(TestSettings settings) {
        this.settings = settings;
        updateStatus(calculateAmount(settings));
    }
}
