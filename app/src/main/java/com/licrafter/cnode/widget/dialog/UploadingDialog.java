package com.licrafter.cnode.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.licrafter.cnode.R;

/**
 * Created by lijx on 2017/4/10.
 */

public class UploadingDialog extends AlertDialog {

    private TextView mProgressVeiw;

    public UploadingDialog(@NonNull Context context) {
        super(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.layout_uploading_dialog, null);
        mProgressVeiw = (TextView) dialogView.findViewById(R.id.tv_message);
        mProgressVeiw.setText(String.format(getContext().getString(R.string.uploading_progress), 0));
        setView(dialogView);
    }

    public void updateProgress(int progress) {
        mProgressVeiw.setText(String.format(getContext().getString(R.string.uploading_progress), progress));
    }
}
