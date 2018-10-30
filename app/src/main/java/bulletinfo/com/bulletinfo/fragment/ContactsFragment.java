package bulletinfo.com.bulletinfo.fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import bulletinfo.com.bulletinfo.R;
import bulletinfo.com.bulletinfo.adapter.ContactsAdapter;
import bulletinfo.com.bulletinfo.bean.Contacts;
import bulletinfo.com.bulletinfo.util.PhoneUtil;
import bulletinfo.com.bulletinfo.util.ToastUtils;


/**
 * 联系人
 */
public class ContactsFragment extends Fragment {
    private ListView listView;
    private ContactsAdapter adapter;
    private Context context;
    private List<Contacts> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_contacts_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getContext();
        check();
    }
    public void init(){
        listView = getActivity().findViewById(R.id.contacts_listview);
    }
    /**
     * 检查权限
     */
    private void check() {
        ToastUtils.showShort(context,"检查权限，获取联系人信息");
        //判断是否有权限
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CONTACTS},201);
        }else{
            initViews();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==201){
            initViews();
        }else{
            return;
        }
    }
    private void initViews() {
        PhoneUtil phoneUtil = new PhoneUtil(context);
        list = phoneUtil.getPhone();
        init();
        adapter = new ContactsAdapter(context);
        adapter.setDate(list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}
