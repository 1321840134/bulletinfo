package bulletinfo.com.bulletinfo.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import bulletinfo.com.bulletinfo.R;
import bulletinfo.com.bulletinfo.adapter.FragmentAdapter;
import bulletinfo.com.bulletinfo.fragment.ContactsFragment;
import bulletinfo.com.bulletinfo.fragment.EnshrineFragment;
import bulletinfo.com.bulletinfo.fragment.MessageFragment;
import bulletinfo.com.bulletinfo.fragment.NewsFragment;
import bulletinfo.com.bulletinfo.fragment.PersonalFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Fragment控件
     */
    private MessageFragment msgFrt;
    private ContactsFragment conFrt;
    private NewsFragment newFrt;
    private EnshrineFragment ensFrt;
    private PersonalFragment perFrt;
    /**
     * 滑动设置
     */
    private FragmentAdapter mFragmentAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private ViewPager vp;
    /**
     * 菜单控件
     */
    private RelativeLayout message_layout,contacts_layout,news_layout,enshrine_layout,personal_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initViews();

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(),mFragmentList);
        vp.setOffscreenPageLimit(4);
        vp.setAdapter(mFragmentAdapter);
        vp.setCurrentItem(0);
        message_layout.setBackgroundColor(Color.parseColor("#eeeeee"));
        //滑动侦听
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /*此方法在页面被选中时调用*/
                changeTable(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /*此方法是在状态改变的时候调用，其中state这个参数有三种状态（0，1，2）。
                  state ==1的时辰默示正在滑动，
                  state==2的时辰默示滑动完毕了，
                  state==0的时辰默示什么都没0做。*/

            }
        });

    }


    private void initViews(){
        message_layout = (RelativeLayout) findViewById(R.id.message_layout);
        contacts_layout = (RelativeLayout) findViewById(R.id.contacts_layout);
        news_layout = (RelativeLayout) findViewById(R.id.news_layout);
        enshrine_layout = (RelativeLayout) findViewById(R.id.enshrine_layout);
        personal_layout = (RelativeLayout) findViewById(R.id.personal_layout);


        message_layout.setOnClickListener(this);
        contacts_layout.setOnClickListener(this);
        news_layout.setOnClickListener(this);
        enshrine_layout.setOnClickListener(this);
        personal_layout.setOnClickListener(this);;

        vp =(ViewPager)findViewById(R.id.mainViewPager);
        msgFrt = new MessageFragment();
        conFrt = new ContactsFragment();
        newFrt = new NewsFragment();
        ensFrt = new EnshrineFragment();
        perFrt = new PersonalFragment();

        mFragmentList.add(msgFrt);
        mFragmentList.add(conFrt);
        mFragmentList.add(newFrt);
        mFragmentList.add(ensFrt);
        mFragmentList.add(perFrt);

    }

    public void changeTable(int position){
        if(position == 0){
            message_layout.setBackgroundColor(Color.parseColor("#eeeeee"));
            contacts_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            news_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            enshrine_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            personal_layout.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        else if (position == 1){
            message_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            contacts_layout.setBackgroundColor(Color.parseColor("#eeeeee"));
            news_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            enshrine_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            personal_layout.setBackgroundColor(Color.parseColor("#ffffff"));
        }else if(position == 2){
            message_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            contacts_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            news_layout.setBackgroundColor(Color.parseColor("#eeeeee"));
            enshrine_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            personal_layout.setBackgroundColor(Color.parseColor("#ffffff"));
        }else if(position == 3){
            message_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            contacts_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            news_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            enshrine_layout.setBackgroundColor(Color.parseColor("#eeeeee"));
            personal_layout.setBackgroundColor(Color.parseColor("#ffffff"));
        }else{
            message_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            contacts_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            news_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            enshrine_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            personal_layout.setBackgroundColor(Color.parseColor("#eeeeee"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.message_layout:
                vp.setCurrentItem(0,true);
                break;
            case R.id.contacts_layout:
                vp.setCurrentItem(1,true);
                break;
            case R.id.news_layout:
                vp.setCurrentItem(2,true);
                break;
            case R.id.enshrine_layout:
                vp.setCurrentItem(3,true);
                break;
            case R.id.personal_layout:
                vp.setCurrentItem(4,true);
                break;
            default:
                break;
        }
    }
}