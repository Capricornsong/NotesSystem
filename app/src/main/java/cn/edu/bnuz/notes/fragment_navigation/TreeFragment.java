package cn.edu.bnuz.notes.fragment_navigation;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.edu.bnuz.notes.R;
import cn.edu.bnuz.notes.ntwpojo.TreeNode;
import cn.edu.bnuz.notes.ntwpojo.TreeRelationRD;
import cn.edu.bnuz.notes.tree.DrawGeometryView;
import cn.edu.bnuz.notes.tree.HVScrollView;

import static cn.edu.bnuz.notes.MyApplication.mTreeController;
import static cn.edu.bnuz.notes.MyApplication.threadExecutor;

public class TreeFragment  extends Fragment{

    private static final String ARG_SHOW_TEXT = "text";
    private String mContentText;
    private Unbinder unbinder;
    private int[] tree_xnum = new int[100];
    private DrawGeometryView view;
//    private String[] tree="[{startNodeName='人工智能', startNode_id=1, sim=0.6784384769251408, endNodeName='知识', endNode_id=3}, {startNodeName='人工智能', startNode_id=1, sim=0.1, endNodeName='软件', endNode_id=2}, {startNodeName='人工智能', startNode_id=1, sim=0.5, endNodeName='人工智能', endNode_id=1}, {startNodeName='软件', startNode_id=2, sim=0.1, endNodeName='人工智能', endNode_id=1}, {startNodeName='软件', startNode_id=2, sim=0.1, endNodeName='人工智能', endNode_id=1}, {startNodeName='知识', startNode_id=3, sim=0.1, endNodeName='软件', endNode_id=2}, {startNodeName='知识', startNode_id=3, sim=0.6784384769251408, endNodeName='人工智能', endNode_id=1}, {startNodeName='人工智能', startNode_id=4, sim=0.5, endNodeName='人工智能', endNode_id=1}{startNodeName='人工智能', startNode_id=4, sim=0.5, endNodeName='知识', endNode_id=3}, {startNodeName='人工智能', startNode_id=4, sim=0.5, endNodeName='知识', endNode_id=3}, {startNodeName='人工智能', startNode_id=4, sim=0.5, endNodeName='知识', endNode_id=3}, {startNodeName='苦难', startNode_id=5, sim=0.22524722217121346, endNodeName='人工智能', endNode_id=4}, {startNodeName='苦难', startNode_id=5, sim=0.22524722217121346, endNodeName='知识', endNode_id=3}, {startNodeName='苦难', startNode_id=5, sim=0.22524722217121346, endNodeName='人工智能', endNode_id=1}, {startNodeName='茶', startNode_id=6, sim=0.4121244903945665, endNodeName='软件', endNode_id=2}]"
    private Button[] bt = new Button[30];
    private RelativeLayout.LayoutParams[] layoutParams = new RelativeLayout.LayoutParams[30];
    private RelativeLayout.LayoutParams[] layoutParams1 = new RelativeLayout.LayoutParams[30];
    private Mystack mstack = new Mystack();
    private boolean model = true;
    private int bt_width = 300;
    @BindView(R.id.murp_nodemodel_title)
    TextView murp_nodemodel_title;
    @BindView(R.id.hvscrollview)
    HVScrollView hv;
    @BindView(R.id.layout_zone)
    RelativeLayout insertLayout;
    private String TAG = "TreeFragment";
    public List<TreeRelationRD> TreeRelationList;
    public List<TreeNode> NodesList;
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static TreeFragment newInstance(String param1) {
        TreeFragment fragment = new TreeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOW_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_tree,null);
        unbinder = ButterKnife.bind(this, rootView);
        murp_nodemodel_title.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                model = !model;
            }
        });


        WindowManager wm = getActivity().getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        TreeNode[] nd =new TreeNode[1];
        TreeRelationRD[] nc = new TreeRelationRD[1];
        nc[0] = new TreeRelationRD("知识树", 1L,0,"知识树",3L);
        drawbutton(width - 150, 50, 50, 1, nc, 1,0);
        return rootView;
    }


    public void drawbutton(int button_y, int button_x, int line_x, final int tree_current, final TreeRelationRD[] nc, int length,int l) {
        int line_y = button_y;
        button_x = tree_current % 2 == 1 ? button_x : button_x - 100;
        int num = 1;
        if (tree_current != 1) num = length;// 下一层个数
        button_y = button_y - (num - 1) * bt_width / 2;
        if (button_y < tree_xnum[tree_current]) {
            button_y = tree_xnum[tree_current] + 100;
        }
        if (tree_current > 2) hv.scrollTo(button_x - 400, button_y - 100);
        if (tree_xnum[tree_current] < button_y + 200 + (num - 1) * bt_width)
            tree_xnum[tree_current] = button_y + 200 + (num - 1) * bt_width;
        final int button_y_f = button_y;
        final int button_x_f = button_x;
        for (int i = 0; i < num; i++) {
            final int bt_paly_y = bt_width;
            int bt_w = tree_current % 2 == 0 ? bt_width : 200;
            int bt_h = 200;
            bt[i] = new Button(getContext());
            if (tree_current % 2 != 0) {
                bt[i].setBackgroundResource(R.drawable.allokbutton);
            } else {
                bt[i].setBackgroundResource(R.drawable.button33);
            }
            bt[i].setTextColor(Color.WHITE);
            if(nc[i].getStartNodeName()=="知识树")
            {
                bt[i].setText(nc[i].getEndNodeName());
            }
            else{
                DecimalFormat    df   = new DecimalFormat("######0.00");
                bt[i].setText(nc[i].getEndNodeName()+" "+df.format(nc[i].getSim()));
            }
            String tree_content=nc[i].getEndNodeName();
            final Long nc_id = nc[i].getStartNode_id();
            ScaleAnimation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setInterpolator(new BounceInterpolator());
            animation.setStartOffset(tree_current == 1 ? 1050 : 50);// 动画秒数。
            animation.setFillAfter(true);
            animation.setDuration(700);
            bt[i].startAnimation(animation);
            final int i1 = i;
            bt[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (model) mstack.pop(tree_current);
                    if (((Button)v).getHint() != null) {
                        Toast.makeText(getContext(), ((Button)v).getText(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    ((Button)v).setHint("1");
                    insertLayout.setEnabled(false);
                    int w = button_y_f + i1 * bt_paly_y;
                    int h = button_x_f + bt_paly_y / 2 * 3;
                    if(l==0){
                        getRemoteInfo(w, h, button_y_f + i1 * bt_paly_y, button_x_f, tree_current + 1, nc_id,
                                nc[i1].getEndNode_id());
                    }
                    else
                    {
                        Intent intent=new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri link=Uri.parse("https://www.baidu.com/s?wd="+tree_content);
                        intent.setData(link);
                        startActivity(intent);
                    }
                }
            });
            layoutParams[i] = new RelativeLayout.LayoutParams(bt_w, bt_h);
            layoutParams[i].topMargin = button_y + i * bt_paly_y;
            layoutParams[i].leftMargin = button_x;
            insertLayout.addView(bt[i], layoutParams[i]);
            if (tree_current != 1) {
                if (button_y + 100 + i * 300 - (line_y + 100) >= 0) {//为了优化内存，也是醉了
                    view = new DrawGeometryView(getContext(), 50, 50, button_x + 100 - (line_x + bt_paly_y) + 50 + (tree_current % 2 == 0 ? 100 : 0), button_y + 100 + i * 300
                            - (line_y + 100) + 50);
                    layoutParams1[i] = new RelativeLayout.LayoutParams(Math.abs(line_x - button_x) + 500, 100 + button_y + i * 300 - line_y);
                    view.invalidate();
                    layoutParams1[i].topMargin = (line_y + 100) - 50;// line_y-600;//Math.min(line_y+100,button_y+100
                    layoutParams1[i].leftMargin = (line_x + bt_paly_y) - 50;// line_x+300;
                    if (tree_current % 2 == 0) layoutParams1[i].leftMargin -= 100;
                    insertLayout.addView(view, layoutParams1[i]);
                } else {
                    view = new DrawGeometryView(getContext(), 50, -(button_y + 100 + i * 300 - (line_y + 100)) + 50, button_x - line_x - 150 + (tree_current % 2 == 0 ? 100 : 0), 50);
                    layoutParams1[i] = new RelativeLayout.LayoutParams(Math.abs(line_x - button_x) + 500, 100 + Math.abs(button_y + i * 300
                            - line_y));
                    view.invalidate();
                    layoutParams1[i].topMargin = (button_y + 100 + i * 300) - 50;// line_y-600;//Math.min(line_y+100,button_y+100
                    layoutParams1[i].leftMargin = (line_x + bt_paly_y) - 50;// line_x+300;
                    if (tree_current % 2 == 0) layoutParams1[i].leftMargin -= 100;
                    insertLayout.addView(view, layoutParams1[i]);
                }
//                line入栈
                mstack.push(view, tree_current);
            }
//            button入栈
            mstack.push(bt[i], tree_current);
        }
    }
    public void drawbutton1(int button_y, int button_x, int line_x, final int tree_current, final TreeNode[] nd, Long starid) {
        int line_y = button_y;
        button_x = tree_current % 2 == 1 ? button_x : button_x - 100;
        int num = 1;
        if (tree_current != 1) num = nd.length;// 下一层个数
        button_y = button_y - (num - 1) * bt_width / 2;
        if (button_y < tree_xnum[tree_current]) {
            button_y = tree_xnum[tree_current] + 100;
        }
        if (tree_current > 2) hv.scrollTo(button_x - 400, button_y - 100);
        if (tree_xnum[tree_current] < button_y + 200 + (num - 1) * bt_width)
            tree_xnum[tree_current] = button_y + 200 + (num - 1) * bt_width;
        final int button_y_f = button_y;
        final int button_x_f = button_x;
        for (int i = 0; i < num; i++) {
            final int bt_paly_y = bt_width;
            int bt_w = tree_current % 2 == 0 ? bt_width : 200;
            int bt_h = 200;
            bt[i] = new Button(getContext());
            if (tree_current % 2 != 0) {
                bt[i].setBackgroundResource(R.drawable.allokbutton);
            } else {
                bt[i].setBackgroundResource(R.drawable.button33);
            }
            bt[i].setTextColor(Color.WHITE);
            bt[i].setText(nd[i].getName());
            final Long nc_id = nd[i].getNodeId();
            ScaleAnimation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setInterpolator(new BounceInterpolator());
            animation.setStartOffset(tree_current == 1 ? 1050 : 50);// 动画秒数。
            animation.setFillAfter(true);
            animation.setDuration(700);
            bt[i].startAnimation(animation);
            final int i1 = i;
            bt[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (model) mstack.pop(tree_current);
                    if (((Button)v).getHint() != null) {
                        Toast.makeText(getContext(), ((Button)v).getText(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    ((Button)v).setHint("1");
                    insertLayout.setEnabled(false);
                    int w = button_y_f + i1 * bt_paly_y;
                    int h = button_x_f + bt_paly_y / 2 * 3;
                    getRemoteInfo1(w, h, button_y_f + i1 * bt_paly_y, button_x_f, tree_current + 1, nd[i1].getName(),
                            nd[i1].getNodeId());
                }
            });
            layoutParams[i] = new RelativeLayout.LayoutParams(bt_w, bt_h);
            layoutParams[i].topMargin = button_y + i * bt_paly_y;
            layoutParams[i].leftMargin = button_x;
            insertLayout.addView(bt[i], layoutParams[i]);
            if (tree_current != 1) {
                if (button_y + 100 + i * 300 - (line_y + 100) >= 0) {//为了优化内存，也是醉了
                    view = new DrawGeometryView(getContext(), 50, 50, button_x + 100 - (line_x + bt_paly_y) + 50 + (tree_current % 2 == 0 ? 100 : 0), button_y + 100 + i * 300
                            - (line_y + 100) + 50);
                    layoutParams1[i] = new RelativeLayout.LayoutParams(Math.abs(line_x - button_x) + 500, 100 + button_y + i * 300 - line_y);
                    view.invalidate();
                    layoutParams1[i].topMargin = (line_y + 100) - 50;// line_y-600;//Math.min(line_y+100,button_y+100
                    layoutParams1[i].leftMargin = (line_x + bt_paly_y) - 50;// line_x+300;
                    if (tree_current % 2 == 0) layoutParams1[i].leftMargin -= 100;
                    insertLayout.addView(view, layoutParams1[i]);
                } else {
                    view = new DrawGeometryView(getContext(), 50, -(button_y + 100 + i * 300 - (line_y + 100)) + 50, button_x - line_x - 150 + (tree_current % 2 == 0 ? 100 : 0), 50);
                    layoutParams1[i] = new RelativeLayout.LayoutParams(Math.abs(line_x - button_x) + 500, 100 + Math.abs(button_y + i * 300
                            - line_y));
                    view.invalidate();
                    layoutParams1[i].topMargin = (button_y + 100 + i * 300) - 50;// line_y-600;//Math.min(line_y+100,button_y+100
                    layoutParams1[i].leftMargin = (line_x + bt_paly_y) - 50;// line_x+300;
                    if (tree_current % 2 == 0) layoutParams1[i].leftMargin -= 100;
                    insertLayout.addView(view, layoutParams1[i]);
                }
//                line入栈
                mstack.push(view, tree_current);
            }
//            button入栈
            mstack.push(bt[i], tree_current);
        }
    }
    public synchronized void getRemoteInfo(int paly_y, int paly_x, int ppaly_y, int ppaly_x, int tree_h,
                                           Long starid, Long endid) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: tree..............");
                TreeRelationList = mTreeController.getRelation();
                NodesList = mTreeController.GetNodes();
//                Log.d(TAG, "run: " + TreeRelationList);
                countDownLatch.countDown();
            }
        });
        //等待上方线程执行完
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onClick: size" + TreeRelationList.size());
        TreeRelationRD[] nc = new TreeRelationRD[TreeRelationList.size()];
        TreeNode[] nd=new TreeNode[NodesList.size()];
        for (int i = 0; i < NodesList.size(); i++) {
            nd[i] = new TreeNode(NodesList.get(i).getNodeId(),NodesList.get(i).getName());
        }
        for (int i = 0; i < TreeRelationList.size(); i++) {
            nc[i] = new TreeRelationRD(TreeRelationList.get(i).getStartNodeName(), TreeRelationList.get(i).getStartNode_id(), TreeRelationList.get(i).getSim(), TreeRelationList.get(i).getEndNodeName(), TreeRelationList.get(i).getEndNode_id());
        }
        drawbutton1(paly_y, paly_x, ppaly_x, tree_h, nd, starid);
    }

    public synchronized void getRemoteInfo1(int paly_y, int paly_x, int ppaly_y, int ppaly_x, int tree_h,
                                           String NodeName, Long Nodeid) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: tree..............");
                TreeRelationList = mTreeController.getRelation();
                NodesList = mTreeController.GetNodes();
//                Log.d(TAG, "run: " + TreeRelationList);
                countDownLatch.countDown();
            }
        });
        //等待上方线程执行完
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TreeRelationRD[] nc = new TreeRelationRD[TreeRelationList.size()];
        int j=0;
        for (int i = 0; i < TreeRelationList.size(); i++) {
            if(NodeName.equals(TreeRelationList.get(i).getStartNodeName())) {
                nc[j] = new TreeRelationRD(TreeRelationList.get(i).getStartNodeName(), TreeRelationList.get(i).getStartNode_id(), TreeRelationList.get(i).getSim(), TreeRelationList.get(i).getEndNodeName(), TreeRelationList.get(i).getEndNode_id());
                j++;
            }
            if(NodeName.equals(TreeRelationList.get(i).getEndNodeName())) {
                nc[j] = new TreeRelationRD(TreeRelationList.get(i).getStartNodeName(), TreeRelationList.get(i).getStartNode_id(), TreeRelationList.get(i).getSim(), TreeRelationList.get(i).getStartNodeName(), TreeRelationList.get(i).getEndNode_id());
                j++;
            }
        }
        drawbutton(paly_y, paly_x, ppaly_x, tree_h, nc, j,1);
    }
    public class Mystack {
        View[] v = new View[1500];
        int[] treehigh = new int[1500];
        int size = 0;

        public void push(View view, int treecurrent) {
            size++;
            v[size] = view;
            treehigh[size] = treecurrent;
        }

        public void pop(int treecurrent) {
            while (treehigh[size] > treecurrent && size > 0) {
                if (size > 0) insertLayout.removeView(v[size]);
                size--;
            }
            for (int j = 49; j > treecurrent; j--) {
                tree_xnum[j] = 0;
            }
            for (int x = size; x > 0; x--) {
                if (treehigh[x] > treecurrent) {
                    insertLayout.removeView(v[x]);
                }
                if (treehigh[x] == treecurrent) {
                    try {
                        ((Button) v[x]).setHint(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
