package com.licrafter.cnode.widget.richEditView.engine;

import android.view.View;
import android.widget.EditText;

import com.licrafter.cnode.R;

/**
 * Created by lijx on 2017/4/5.
 */

public class PerformEditable {

    private EditText mEditText;

    public PerformEditable(EditText editText) {
        this.mEditText = editText;
    }

    public void perform(int id, Object... params) {
        switch (id) {
            case R.id.font_blod:
                performBold();
                break;
            case R.id.font_italic:
                performItalic();
                break;
            case R.id.quote:
                performQuote();
                break;
            case R.id.list_ol:
                performList("1. ");
                break;
            case R.id.list_ul:
                performList("* ");
                break;
            case R.id.op_code:
                performConsole();
                break;
            case R.id.op_image:
                performInsertPhoto(params);
                break;
            case R.id.op_link:
                performInsertLink(params);
                break;
        }
    }

    public void onClick(View v) {
        if (mEditText == null) {
            return;
        }
        perform(v.getId());
    }

    private void performItalic() {
        String source = mEditText.getText().toString();
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        android.util.Log.d("ljx", "start = " + selectionStart + " end = " + selectionEnd);
        String substring = source.substring(selectionStart, selectionEnd);
        String result = " *" + substring + "* ";
        mEditText.getText().replace(selectionStart, selectionEnd, result);
        mEditText.setSelection(result.length() + selectionStart - 2);
    }

    private void performBold() {
        String source = mEditText.getText().toString();
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        String substring = source.substring(selectionStart, selectionEnd);
        String result = " **" + substring + "** ";
        mEditText.getText().replace(selectionStart, selectionEnd, result);
        mEditText.setSelection(result.length() + selectionStart - 3);
    }

    public void performQuote() {
        String source = mEditText.getText().toString();
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        String substring = source.substring(selectionStart, selectionEnd);

        String result;
        if (hasNewLine(source, selectionStart)) {
            result = "> " + substring;
        } else {
            result = "\n> " + substring;

        }
        mEditText.getText().replace(selectionStart, selectionEnd, result);
        mEditText.setSelection(result.length() + selectionStart);
    }

    private void performConsole() {
        String source = mEditText.getText().toString();
        int selectionStart = mEditText.getSelectionStart();

        int selectionEnd = mEditText.getSelectionEnd();
        String substring = source.substring(selectionStart, selectionEnd);

        String result;
        if (hasNewLine(source, selectionStart))
            result = "```\n" + substring + "\n```\n";
        else
            result = "\n```\n" + substring + "\n```\n";

        mEditText.getText().replace(selectionStart, selectionEnd, result);
        mEditText.setSelection(result.length() + selectionStart - 5);
    }

    private void performInsertLink(Object[] param) {
        int selectionStart = mEditText.getSelectionStart();
        String result;
        if (param == null || param.length == 0) {
            result = "[]()\n";
            mEditText.getText().insert(selectionStart, result);
            mEditText.setSelection(selectionStart + 1);
        } else if (param.length == 1) {
            result = "[image](" + param[0] + ")\n";
            mEditText.getText().insert(selectionStart, result);
            mEditText.setSelection(selectionStart + result.length());
        } else {
            result = "[" + param[0] + "](" + param[1] + ")\n";
            mEditText.getText().insert(selectionStart, result);
            mEditText.setSelection(selectionStart + result.length());
        }
    }

    public void performInsertTail() {
        String tail = "\n\n" + "[来自CNode-Android](https://github.com/shellljx/CNode-android)";
        mEditText.setText(mEditText.getText().toString() + tail);
    }

    private void performInsertPhoto(Object[] param) {
        Object[] temp = param;
        if (param == null) param = new Object[]{""};
        String source = mEditText.getText().toString();
        int selectionStart = mEditText.getSelectionStart();

        String result;
        if (hasNewLine(source, selectionStart)) {
            result = "![image](" + param[0] + ")";
        } else {
            result = "\n" + "![image](" + param[0] + ")";
        }
        mEditText.getText().insert(selectionStart, result);
        if (temp == null || temp[0].toString().length() == 0)
            mEditText.setSelection(result.length() + selectionStart - 1);
        else
            mEditText.setSelection(result.length() + selectionStart);
    }

    private void performList(String tag) {
        String source = mEditText.getText().toString();
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        String substring = source.substring(0, selectionStart);
        int line = substring.lastIndexOf(10);


        if (line != -1) {
            selectionStart = line + 1;
        } else {
            selectionStart = 0;
        }
        substring = source.substring(selectionStart, selectionEnd);

        String[] split = substring.split("\n");
        StringBuffer stringBuffer = new StringBuffer();

        if (split != null && split.length > 0)
            for (String s : split) {
                if (s.length() == 0 && stringBuffer.length() != 0) {
                    stringBuffer.append("\n");
                    continue;
                }
                if (!s.trim().startsWith(tag)) {
                    //不是 空行或者已经是序号开头
                    if (stringBuffer.length() > 0) stringBuffer.append("\n");
                    stringBuffer.append(tag).append(s);
                } else {
                    if (stringBuffer.length() > 0) stringBuffer.append("\n");
                    stringBuffer.append(s);
                }
            }

        if (stringBuffer.length() == 0) {
            stringBuffer.append(tag);
        }
        mEditText.getText().replace(selectionStart, selectionEnd, stringBuffer.toString());
        mEditText.setSelection(stringBuffer.length() + selectionStart);
    }

    private boolean hasNewLine(String source, int selectionStart) {
        if (source.isEmpty()) return true;
        source = source.substring(0, selectionStart);
        return source.charAt(source.length() - 1) == 10;
    }
}
