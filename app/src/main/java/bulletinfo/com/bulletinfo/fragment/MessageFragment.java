package bulletinfo.com.bulletinfo.fragment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import bulletinfo.com.bulletinfo.R;


public class MessageFragment extends Fragment implements View.OnClickListener {
    private EditText search_input;
    private Button edit_cancel;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_message_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getContext();
        init();

    }

    public void init(){
        search_input = getActivity().findViewById(R.id.search_input);
        edit_cancel = getActivity().findViewById(R.id.edit_cancel);

        search_input.setOnClickListener(this);
        edit_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_input:
                edit_cancel.setVisibility(View.VISIBLE);
                break;
            case R.id.edit_cancel:
                edit_cancel.setVisibility(View.GONE);
                break;
        }
    }
}
