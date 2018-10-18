package bulletinfo.com.bulletinfo.fragment;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bulletinfo.com.bulletinfo.R;

/**
 * 收藏
 */
public class EnshrineFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_enshrine_fragment,container,false);
        return view;
    }
}
