package com.route.chatapp.Base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;


public class BaseActivity extends AppCompatActivity {

    protected AppCompatActivity activity;
    MaterialDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
    }
    public MaterialDialog ShowMessage(String titleResID, String msgResID, String PosResTxt){
        dialog=new MaterialDialog.Builder(this)
                .title(titleResID)
                .content(msgResID)
                .positiveText(PosResTxt)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();


                    }
                })
                .show();
        return  dialog;

    }

    public MaterialDialog ShowMessage(int titleResID,int msgResID,int PosResTxt){
        dialog=new MaterialDialog.Builder(this)
                .title(titleResID)
                .content(msgResID)
                .positiveText(PosResTxt)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        finish();


                    }
                })
                .show();
        return  dialog;

    }

    public MaterialDialog ShowConfirmationMessage(int titleResID, int msgResID, int PosResTxt, MaterialDialog.SingleButtonCallback materialcallback){
        dialog=new MaterialDialog.Builder(this)
                .title(titleResID)
                .content(msgResID)
                .positiveText(PosResTxt)
                .onPositive(materialcallback)
                .show();
        return  dialog;

    }

    public MaterialDialog ShowConfirmationMessage(int titleResID, int msgResID, int PosResTxt, MaterialDialog.SingleButtonCallback materialcallback, int NegResTxt, MaterialDialog.SingleButtonCallback nomaterialcallback){
        dialog=new MaterialDialog.Builder(this)
                .title(titleResID)
                .content(msgResID)
                .positiveText(PosResTxt)
                .onPositive(materialcallback)
                .negativeText(NegResTxt)
                .onNegative(nomaterialcallback)
                .show();
        return  dialog;

    }


    public MaterialDialog ShowConfirmationMessage(String titleResID,String msgResID,String PosResTxt,MaterialDialog.SingleButtonCallback materialcallback){
        dialog=new MaterialDialog.Builder(this)
                .title(titleResID)
                .content(msgResID)
                .positiveText(PosResTxt)
                .onPositive(materialcallback)
                .show();
        return  dialog;

    }

    public MaterialDialog ShowConfirmationMessage(String titleResID,String msgResID,String PosResTxt,MaterialDialog.SingleButtonCallback materialcallback,String NegResTxt, MaterialDialog.SingleButtonCallback nomaterialcallback){
        dialog=new MaterialDialog.Builder(this)
                .title(titleResID)
                .content(msgResID)
                .positiveText(PosResTxt)
                .onPositive(materialcallback)
                .negativeText(NegResTxt)
                .onNegative(nomaterialcallback)
                .show();
        return  dialog;

    }

    public MaterialDialog ShowProgressBar(){
        dialog=new MaterialDialog.Builder(this)
                .progress(true,0)
                .cancelable(false)
                .show();
       return dialog;
    }

    public void hideprogressbar(){

        if(dialog!=null&&dialog.isShowing())
            dialog.dismiss();
    }
}
