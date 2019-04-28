package com.route.chatapp.Base;

import android.content.Context;
import android.support.v4.app.Fragment;
import com.afollestad.materialdialogs.MaterialDialog;

public class BaseFragment extends Fragment {
//Context context; //3shan a5od refrence 3ala elactivity
   // or
   protected BaseActivity activity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity=(BaseActivity) context;
    }

    MaterialDialog dialog;


    public MaterialDialog ShowMessage(String titleResID, String msgResID, String PosResTxt){
      dialog= activity.ShowMessage(titleResID,msgResID,PosResTxt);
      return dialog;

    }

    public MaterialDialog ShowProgressBar(){
        dialog=activity.ShowProgressBar();
        return dialog;
    }

    public void hideprogressbar(){
        activity.hideprogressbar();
    }
}
