package cn.edu.bnuz.notes.fragment_navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.edu.bnuz.notes.R;

public class TreeFragment  extends Fragment{

    private static final String ARG_SHOW_TEXT = "text";
    private String mContentText;
    private Unbinder unbinder;
    public TreeFragment(){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static TreeFragment newInstance(String param1) {
        TreeFragment fragment = new TreeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOW_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_tree,null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


}
