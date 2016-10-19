# TagSelectorView
TagSelectorView

A tag selector with animation

# [已更新v2.0](https://github.com/Rock610/TagSelectorView/blob/v2.0/README.md)

[![download](https://api.bintray.com/packages/rock610/maven/tagselector/images/download.svg)](https://bintray.com/rock610/maven/tagselector/_latestVersion)
[![blog](https://img.shields.io/badge/blog-%E7%AE%80%E4%B9%A6-blue.svg)](http://www.jianshu.com/users/55ddc6ee39e8/latest_articles)


be free to contact me by the blog above or email _wang1ran@163.com_


![gif](https://github.com/Rock610/TagSelectorView/blob/master/gif/gif2.gif)


#Setup

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

- in your activity
```
TagSelectView tagSelectView = (TagSelectView) findViewById(R.id.tagSelectView);

List<Tags> dataList = new ArrayList<>();

int count = 2;
List<DataBean> dataBeanList = new ArrayList<>();
for (int i = 0; i < count; i++) {
    dataBeanList.add(new MyDataBean("item A" + i));
}

dataList.add(new Tags().
                setDefaultTag(dataBeanList.get(0).name)
                .setTags(dataBeanList)
                .setChangeAfterClicked(true)
                .setSelectFirst(false);
tagSelectView.attach(dataList);
```
data source must extend the DataBean
- to insert a data to any tab
```
ITagSelectorTabView tagSelectorView = tagSelectView.getTabView(0).getTagSelectorView();
tagSelectorView.getData().add(0, new MyDataBean("im new"));
tagSelectorView.refresh();
```

- get the selected tag
```
tagSelectView.setOnTagSelectedListener(new TagSelectView.OnTagSelectedListener() {
            @Override
            public void onTagSelected(int selectorListPosition, int tabPosition) {
                DataBean dataBean = dataList.get(tabPosition).tags.get(selectorListPosition);
                MyDataBean d = (MyDataBean) dataBean;
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

#License
[https://www.gnu.org/licenses/licenses.html](https://www.gnu.org/licenses/licenses.html)


