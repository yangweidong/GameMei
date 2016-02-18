package gamemei.qiyun.com.gamemei.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.activity.MissionDetailActivity;
import gamemei.qiyun.com.gamemei.fragment.common.BaseFragment;
import gamemei.qiyun.com.gamemei.widget.xlistview.XListView;

/**
 * 游戏详情-详情界面
 * Created by hfcui on 2016/2/18
 */
public class GameDetailFragment extends BaseFragment implements XListView.IXListViewListener {

    /**
     * 日志标记
     */
    private String TAG = "GameDetailFragment";
    /**
     * 下拉刷新的ListView
     */
    private XListView xListView;
    /**
     * 界面适配器
     */
    private MyAdapter madapter;
    /**
     * 任务类型
     */
    private ImageView iv_mission_type;

    private Handler mHandler;

    private View view;

    private int start = 0;

    private int items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mission, null);
        initView();
        initData();// 获取数据
        //设置ListView
        setXListView();
        return view;
    }

    @Override
    public void initData() {
        getItems();
    }

    private void getItems() {
        for (int i = 0; i != 10; ++i) {
            items = (++start);
        }
    }

    /**
     * 设置XListView
     */
    private void setXListView() {
        xListView = (XListView) view.findViewById(R.id.mission_listview);
        xListView.setAdapter(madapter);
        xListView.setPullLoadEnable(false);
        xListView.setPullRefreshEnable(true); // 设置可以下拉刷新和上拉加载
        xListView.setXListViewListener(this); // 设置监听事件
        // 设置条目可以被点击
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO 点击跳转到任务明细页面
                Intent detailIntent = new Intent(getActivity(),
                        MissionDetailActivity.class);
                startActivity(detailIntent);
            }
        });
    }

    @Override
    public View initView() {
        mHandler = new Handler();
        madapter = new MyAdapter();
        return null;
    }

    /**
     * 获取数据
     */
    private void getDate() {

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getDate();
                madapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getItems();
                madapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }

    /**
     * 加载
     */
    private void onLoad() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime("刚刚");
    }

    /**
     * ListView 适配器
     *
     * @author hfcui
     */
    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;

        private MyAdapter() {
            this.mInflater = LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount() {
            return items;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // 获取一个在数据集中指定索引的视图来显示数据
        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            convertView = mInflater.inflate(R.layout.item_top_mission_list, null);
            iv_mission_type = (ImageView) convertView.findViewById(R.id.iv_mission_type);

            for (int i = 0; i < 10; i++) {
                if (i % 2 == 1) {
                    iv_mission_type.setImageResource(R.mipmap.mission_underway);
                } else {
                    iv_mission_type.setImageResource(R.mipmap.mission_finish);
                }
            }
            return convertView;
        }
    }
}

