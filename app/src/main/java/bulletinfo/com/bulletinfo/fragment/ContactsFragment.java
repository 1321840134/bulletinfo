package bulletinfo.com.bulletinfo.fragment;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import bulletinfo.com.bulletinfo.R;
import bulletinfo.com.bulletinfo.adapter.ContactsAdapter;
import bulletinfo.com.bulletinfo.bean.Contacts;

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
        init();
        setInfo();
        adapter = new ContactsAdapter(context);
        adapter.setDate(list);
        listView.setAdapter(adapter);

    }
    public void init(){
        listView = getActivity().findViewById(R.id.contacts_listview);
    }
    public void setInfo(){
        list = new ArrayList<>();
        for (int i=0 ; i<10 ;i++){
            list.add(new Contacts("张"+String.valueOf(i),"1355:"+String.valueOf(i)));
        }
    }

    public List<Contacts> getContacts(){
        //联系人URI
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };

        //根据Uri查询相应的ContentProvider，cursor为获取到的数据集
        

        return  null;
    }





}
