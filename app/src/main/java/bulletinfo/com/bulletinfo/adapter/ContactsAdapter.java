package bulletinfo.com.bulletinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import bulletinfo.com.bulletinfo.R;
import bulletinfo.com.bulletinfo.bean.ContactsBean;

public class ContactsAdapter extends BaseAdapter {
    private List<ContactsBean> mList;
    private LayoutInflater layoutInflater;
    private Context context;

    public ContactsAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setDate( List<ContactsBean> mList){
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (holder == null){
            view = layoutInflater.inflate(R.layout.activity_contacts_item,null);
            holder = new ViewHolder();
            holder.getId(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }
        holder.textName.setText(mList.get(position).getName());
        holder.textPhone.setText(mList.get(position).getPhone());
        holder.callPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"点击打电话",Toast.LENGTH_LONG).show();
            }
        });
        holder.callBullet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"点击子弹短信",Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
    class ViewHolder{
        TextView textName;
        TextView  textPhone;
        Button callPhone;
        Button callBullet;
        public void getId(View view){
            textName = view.findViewById(R.id.contacts_name);
            textPhone = view.findViewById(R.id.contacts_phone);
            callPhone = view.findViewById(R.id.call_phone);
            callBullet = view.findViewById(R.id.call_bullet);
        }
    }
}
