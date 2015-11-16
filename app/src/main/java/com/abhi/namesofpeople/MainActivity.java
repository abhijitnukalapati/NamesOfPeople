package com.abhi.namesofpeople;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.firstName) EditText vFirstName;
    @Bind(R.id.lastName) EditText vLastName;
    @Bind(R.id.fullName) TextView vFullName;
    @Bind(R.id.orderNormal) RadioButton vOrderNormal;
    @Bind(R.id.orderReversed) RadioButton vOrderReversed;
    @BindString(R.string.hint_full_name) String mFullNameHint;

    private final String NAME_KEY = "name_key"; // key for retaining vFullName's state
    private boolean mShouldReverseName; // flag to display the full name in reverse

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(savedInstanceState != null) {
            vFullName.setText(savedInstanceState.getString(NAME_KEY, mFullNameHint));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
         // TextView's current text isn't stored by the system, in spite
         // of having an id defined in xml. So, we have to do it manually
        outState.putString(NAME_KEY, vFullName.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.displayName)
    void displayName() {
        final String first_name = vFirstName.getText().toString();
        final String last_name = vLastName.getText().toString();

        if(mShouldReverseName) {
            vFullName.setText(String.format("%s %s", first_name, last_name));
        } else {
            vFullName.setText(String.format("%s %s", last_name, first_name));
        }
    }

    // no support for RadioGroup's onCheckChangeListener - womp womp :(
    @OnClick({R.id.orderNormal, R.id.orderReversed})
    void styleOptionChanged(){
        mShouldReverseName = vOrderReversed.isChecked();
    }

}
