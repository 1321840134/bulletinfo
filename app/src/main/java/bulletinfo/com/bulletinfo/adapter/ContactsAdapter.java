package bulletinfo.com.bulletinfo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import bulletinfo.com.bulletinfo.R;
import bulletinfo.com.bulletinfo.bean.Contacts;
import bulletinfo.com.bulletinfo.dao.User_MessageDao;
import bulletinfo.com.bulletinfo.util.ToastUtils;

public class ContactsAdapter extends BaseAdapter{
    private List<Contacts> mList;
    private LayoutInflater layoutInflater;
    private Context context;

    public ContactsAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setDate( List<Contacts> mList){
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
    public View getView(final int position, View view, ViewGroup viewGroup) {
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
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+mList.get(position).getPhone()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.callBullet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort(context,"给"+mList.get(position).getName()+"发信息");
                User_MessageDao userDao = User_MessageDao.getInstance();
            }
        });

        return view;
    }



    class ViewHolder{
        TextView textName;
        TextView textPhone;
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
