package dkpathak.android.database;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Dharmendra on 6/3/16.
 */
public class RegistrationFragment extends Fragment {
    private String TAG = getClass().getName();
    private EditText nameEditView;
    private EditText mobileEditView;
    private EditText emailEditView;
    private ContentValues contentValues = new ContentValues();
    View.OnClickListener onRegistrationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (validateName() && validateMobile() && validateEmail()) {
                long result = DBManager.getInstance(getActivity()).insertDataToDB(TableConstants.TABLE_USER, contentValues);
                contentValues.clear();
                if (result != -1) {
                    RegistrationFragment.this.getFragmentManager().popBackStack();
                    Toast.makeText(getActivity(), "Data Inserted in Database", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "There is some issiue while inserting data", Toast.LENGTH_SHORT).show();
                }
            }
        }

    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration_layout, container, false);
        nameEditView = (EditText) view.findViewById(R.id.input_name);
        mobileEditView = (EditText) view.findViewById(R.id.input_mobile);
        emailEditView = (EditText) view.findViewById(R.id.input_email);
        view.findViewById(R.id.registration_button).setOnClickListener(onRegistrationClickListener);
        return view;
    }

    private boolean validateName() {
        contentValues.clear();
        String inputNameText = nameEditView.getText().toString();
        if (inputNameText.trim().isEmpty()) {
            nameEditView.setError("This field can't be empty");
            return false;
        }
        contentValues.put(TableConstants.COLUMN_USER_NAME, inputNameText);
        return true;
    }

    private boolean validateMobile() {
        String inputMobileText = mobileEditView.getText().toString();
        if (inputMobileText.trim().isEmpty()) {
            mobileEditView.setError("This field can't be empty");
            return false;
        }
        contentValues.put(TableConstants.COLUMN_USER_MOBILE, inputMobileText);
        return true;
    }

    private boolean validateEmail() {
        String inputText = emailEditView.getText().toString();
        if (inputText.trim().isEmpty()) {
            mobileEditView.setError("This field can't be empty");
            return false;
        }
        contentValues.put(TableConstants.COLUMN_USER_EMAIL, inputText);
        return true;
    }
}
