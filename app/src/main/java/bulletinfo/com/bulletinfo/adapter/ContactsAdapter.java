package bulletinfo.com.bulletinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import bulletinfo.com.bulletinfo.R;
import bulletinfo.com.bulletinfo.bean.Contacts;

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
//        final Contacts mContact = mList.get(position);
//        if (position == 0){
//            holder.tvLetter.setVisibility(View.VISIBLE);
//            holder.tvLetter.setText(mContact.getLetters());
//        } else {
//            String lastCatalog = mList.get(position - 1).getLetters();
//            if(mContact.getLetters().equals(lastCatalog)){
//                holder.tvLetter.setVisibility(View.GONE);
//            }else{
//                holder.tvLetter.setVisibility(View.VISIBLE);
//                holder.tvLetter.setText(mContact.getLetters());
//            }
//        }

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

//    @Override
//    public Object[] getSections() {
//        return null;
//    }
//
//    @Override
//    public int getPositionForSection(int section) {
//        Contacts contacts;
//        String l;
//        if (section == '!') {
//            return 0;
//        } else {
//            for (int i = 0; i < getCount(); i++) {
//                contacts = (Contacts) mList.get(i);
//                l = contacts.getLetters();
//                char firstChar = l.toUpperCase().charAt(0);
//                if (firstChar == section) {
//                    return i + 1;
//                }
//
//            }
//        }
//        contacts = null;
//        l = null;
//        return -1;
//    }
//
//    @Override
//    public int getSectionForPosition(int i) {
//        return 0;
//    }


    class ViewHolder{
        TextView textName;
        TextView textPhone;
//        TextView  tvLetter;
        Button callPhone;
        Button callBullet;
        public void getId(View view){
            textName = view.findViewById(R.id.contacts_name);
            textPhone = view.findViewById(R.id.contacts_phone);
            callPhone = view.findViewById(R.id.call_phone);
            callBullet = view.findViewById(R.id.call_bullet);
//            tvLetter = view.findViewById(R.id.tvLetter);
        }
    }
}
