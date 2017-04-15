package home.gio.calorieplanner.calculator;


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class CustomTextWatcher implements TextWatcher {
    private EditText mEditText;
    private int lastCount = 0;

    public CustomTextWatcher(EditText e) {
        mEditText = e;
    }

    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    public void afterTextChanged(Editable s) {
        int count = s.length();
        String str = s.toString();
        if (count == 1) {
            if (lastCount != 3 && lastCount != 2 && lastCount != 4 && lastCount != 1) {
                str = str + "'";
                lastCount = 1;
            } else {
                str = "";
                lastCount = 5;
            }
        } else if (count == 3) {
            if (lastCount != 4 && lastCount != 3) {
                str = str + "\"";
                lastCount = 3;
            } else {
                str = str.substring(0, str.length() - 1);
                lastCount = 2;
            }
        } else if ((count > 4) && (str.charAt(str.length() - 1) != '\"')) {
            str = str.substring(0, str.length() - 2) + str.charAt(str.length() - 1)
                    + "\"";
            lastCount = 4;
        } else {
            return;
        }

        if (count > 1 && Integer.parseInt(str.substring(0, str.indexOf('\''))) == 9) {
            str = str.replace(str.substring(0, str.indexOf('\'')), "8");
        }

        if (count > 3 && Integer.parseInt(str.substring(str.indexOf('\'') + 1, str.indexOf('\"'))) > 11) {
            str = str.replace(str.substring(str.indexOf('\'') + 1, str.indexOf('\"')), "11");
        }
        mEditText.setText(str);
        mEditText.setSelection(mEditText.getText().length());
    }


}