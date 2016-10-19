# TagSelectorView
TagSelectorView v2.0


[![download](https://api.bintray.com/packages/rock610/maven/tagselector/images/download.svg)](https://bintray.com/rock610/maven/tagselector/_latestVersion)
[![blog](https://img.shields.io/badge/blog-%E7%AE%80%E4%B9%A6-blue.svg)](http://www.jianshu.com/users/55ddc6ee39e8/latest_articles)



be free to contact me by the blog above or email _wang1ran@163.com_


![gif](https://github.com/Rock610/TagSelectorView/blob/master/gif/gif2.gif)

#### 2.0目前暂时未提交到jcenter,先上源码,稍后会提交.



# Setup

```
repositories {
   jcenter()
}

dependencies {
    compile 'com.rock.android:tagselector:xxx'//version code
}
```

#Usage

- in xml
```
<com.rock.android.tagselector.views.TagSelectView
        android:id="@+id/tagSelectView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

- Activity

- 2.0版本使用了adapter模式，使用配置起来更加优雅流畅，自定义一个实现```com.rock.android.tagselector.interfaces.BaseAdapter```的类之后调用```tagSelectView.setAdapter(tagSelectorAdapter);```即可.

```
TagSelectView tagSelectView = (TagSelectView) findViewById(R.id.tagSelectView);

dataList = new ArrayList<>();

        int count = 2;
        List<DataBean> dataBeanList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            dataBeanList.add(new MyDataBean("item A" + i));
        }

        dataList.add(new Tags()
                .setTags(dataBeanList));



        List<DataBean> dataBeanList1 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            dataBeanList1.add(new MyDataBean("item B" + i));
        }

        dataList.add(new Tags()
                .setTags(dataBeanList1));


        int countc = 10;
        List<DataBean> dataBeanList2 = new ArrayList<>();
        for (int i = 0; i < countc; i++) {
            dataBeanList2.add(new MyDataBean("item C" + i));
        }

        dataList.add(new Tags()
                .setTags(dataBeanList2));

        tagSelectorAdapter = new SimpleAdapter(dataList, this);
        tagSelectView.setAdapter(tagSelectorAdapter);
        tagSelectView.setWrapperHeight(FrameLayout.LayoutParams.MATCH_PARENT);
```
~~data source must extend the DataBean~~
现在不需要强行继承DataBean,把宝贵的单继承留给使用者

- 为某一个tab下的view添加一条数据
```
tagSelectView.getTabView(1).getTagSelectorView().insert(0, new MyDataBean("im new"));
tagSelectView.getTabView(1).getTagSelectorView().refresh();
```

- get the selected tag
```
tagSelectView.setOnTagSelectedListener(new TagSelectView.OnTagSelectedListener() {
            @Override
            public void onTagSelected(int selectorListPosition, int tabPosition) {
                LogUtil.e("onTagSelected", "selected==" + selectorListPosition + "===tab===" + tabPosition);

                DataBean dataBean = dataList.get(tabPosition).tags.get(selectorListPosition);
                MyDataBean d = (MyDataBean) dataBean;
                LogUtil.e("onTagSelected", "data name===" + d.name);

                DataBean dataBean1 = (DataBean) tagSelectorAdapter.getItem(tabPosition,selectorListPosition);
                        //(DataBean) tagSelectView.getTabView(tabPosition).getTagSelectorView().getCurrentItem(selectorListPosition);
                LogUtil.e("onTagSelected", "data1 name===" + dataBean1.name);
                //do something like request the network

            }
        });

```

- listening the tab
```
tagSelectView.setOnTagViewStatusChangedListener(new TagSelectView.OnTagViewStatusChangedListener() {
            @Override
            public void onOpened(ITagSelectorTabView openedView) {
              
            }

            @Override
            public void onClosed(ITagSelectorTabView closedView) {
              
            }
        });

```

- 更多用法请看源码咯

#License
[https://www.gnu.org/licenses/licenses.html](https://www.gnu.org/licenses/licenses.html)


