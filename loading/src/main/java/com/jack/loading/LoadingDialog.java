package com.jack.loading;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by company on 2018/3/20.
 */

public class LoadingDialog extends Dialog {

    private View mView;

    public LoadingDialog(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    public LoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    private void initView(Context context) {
        mView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        setContentView(mView);
    }
}
