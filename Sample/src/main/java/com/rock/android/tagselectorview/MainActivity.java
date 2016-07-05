package com.rock.android.tagselectorview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.rock.android.tagselector.interfaces.ITagSelector;
import com.rock.android.tagselector.model.DataBean;
import com.rock.android.tagselector.model.Tags;
import com.rock.android.tagselector.interfaces.ITagSelectorTabView;
import com.rock.android.tagselector.views.TagSelectView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private TagSelectView tagSelectView;
    private List<Tags<DataBean>> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tagSelectView = (TagSelectView) findViewById(R.id.tagSelectView);

        tagSelectView.setOnTagViewStatusChangedListener(new TagSelectView.OnTagViewStatusChangedListener() {
            @Override
            public void onOpened(ITagSelectorTabView openedView) {
                openedView.getTextView().setTextColor(Color.BLUE);
            }

            @Override
            public void onClosed(ITagSelectorTabView closedView) {
                closedView.getTextView().setTextColor(Color.BLACK);
            }
        });

        tagSelectView.setOnTagSelectedListener(new TagSelectView.OnTagSelectedListener() {
            @Override
            public void onTagSelected(int selectorListPosition, int tabPosition) {
                Log.e("onTagSelected","selected=="+selectorListPosition+"===tab==="+tabPosition);

                DataBean dataBean = dataList.get(tabPosition).tags.get(selectorListPosition);
                MyDataBean d = (MyDataBean) dataBean;
                Log.e("onTagSelected","data name==="+ d.name);

                //do something like request the network

            }
        });
        initData();

    }

    private void initData(){
        dataList = new ArrayList<>();

        int count = 2;
        List<DataBean> dataBeanList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            dataBeanList.add(new MyDataBean("item A"+i));
        }

        dataList.add(new Tags<>(dataBeanList,dataBeanList.get(0).name,false,true));

        List<DataBean> dataBeanList1 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            dataBeanList1.add(new MyDataBean("item B"+i));
        }

        dataList.add(new Tags<>(dataBeanList1,dataBeanList1.get(0).name,false,true));


        int countc = 10;
        List<DataBean> dataBeanList2 = new ArrayList<>();
        for (int i = 0; i < countc; i++) {
            dataBeanList2.add(new MyDataBean("item C"+i));
        }

        dataList.add(new Tags<>(dataBeanList2,dataBeanList2.get(0).name,false,true));

        tagSelectView.attach(dataList);

        ITagSelector tagSelectorView = tagSelectView.getTabView(0).getTagSelectorView();
        tagSelectorView.setListAdapter(new CustomAdapter(dataBeanList,this));

        tagSelectorView.getData().add(0,new MyDataBean("im new"));
        tagSelectorView.refresh();
    }
}
