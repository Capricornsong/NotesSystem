package cn.edu.bnuz.notes.fragment_navigation;

import android.graphics.Color;
import android.os.Bundle;
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

import java.util.Random;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.edu.bnuz.notes.R;
import cn.edu.bnuz.notes.tree.DrawGeometryView;
import cn.edu.bnuz.notes.tree.HVScrollView;

public class TreeFragment  extends Fragment{

    private static final String ARG_SHOW_TEXT = "text";
    private String mContentText;
    private Unbinder unbinder;
    private int[] tree_xnum = new int[100];
    private DrawGeometryView view;
//    private String[] tree="[{startNodeName='人工智能', startNode_id=1, sim=0.6784384769251408, endNodeName='知识', endNode_id=3}, {startNodeName='人工智能', startNode_id=1, sim=0.1, endNodeName='软件', endNode_id=2}, {startNodeName='人工智能', startNode_id=1, sim=0.5, endNodeName='人工智能', endNode_id=1}, {startNodeName='软件', startNode_id=2, sim=0.1, endNodeName='人工智能', endNode_id=1}, {startNodeName='软件', startNode_id=2, sim=0.1, endNodeName='人工智能', endNode_id=1}, {startNodeName='知识', startNode_id=3, sim=0.1, endNodeName='软件', endNode_id=2}, {startNodeName='知识', startNode_id=3, sim=0.6784384769251408, endNodeName='人工智能', endNode_id=1}, {startNodeName='人工智能', startNode_id=4, sim=0.5, endNodeName='人工智能', endNode_id=1}{startNodeName='人工智能', startNode_id=4, sim=0.5, endNodeName='知识', endNode_id=3}, {startNodeName='人工智能', startNode_id=4, sim=0.5, endNodeName='知识', endNode_id=3}, {startNodeName='人工智能', startNode_id=4, sim=0.5, endNodeName='知识', endNode_id=3}, {startNodeName='苦难', startNode_id=5, sim=0.22524722217121346, endNodeName='人工智能', endNode_id=4}, {startNodeName='苦难', startNode_id=5, sim=0.22524722217121346, endNodeName='知识', endNode_id=3}, {startNodeName='苦难', startNode_id=5, sim=0.22524722217121346, endNodeName='人工智能', endNode_id=1}, {startNodeName='茶', startNode_id=6, sim=0.4121244903945665, endNodeName='软件', endNode_id=2}]"
    private Button[] bt = new Button[15];
    private RelativeLayout.LayoutParams[] layoutParams = new RelativeLayout.LayoutParams[15];
    private RelativeLayout.LayoutParams[] layoutParams1 = new RelativeLayout.LayoutParams[15];
    private Mystack mstack = new Mystack();
    private boolean model = true;
    private int bt_width = 300;
    @BindView(R.id.murp_nodemodel_title)
    TextView murp_nodemodel_title;
    @BindView(R.id.hvscrollview)
    HVScrollView hv;
    @BindView(R.id.layout_zone)
    RelativeLayout insertLayout;
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

        nodechild[] nc = new nodechild[1];
        nc[0] = new nodechild("知识树", 1,0.6784384769251408,"知识",3);
        drawbutton(width - 150, 50, 50, 1, nc, 1);
        return rootView;
    }

    public class nodechild {
        private String startNodeName;
        private int startNode_id;
        private double sim;
        private String endNodeName;
        private int endNode_id;

        public nodechild(String startNodeName, int startNode_id, double sim, String endNodeName, int endNode_id) {
            super();
            this.startNodeName = startNodeName;
            this.startNode_id = startNode_id;
            this.sim = sim;
            this.endNodeName = endNodeName;
            this.endNode_id = endNode_id;

        }
        public String getStartNodeName() {
            return startNodeName;
        }

        public void setStartNodeName(String startNodeName) {
            this.startNodeName = startNodeName;
        }

        public int getStartNode_id() {
            return startNode_id;
        }

        public void setStartNode_id(int startNode_id) {
            this.startNode_id = startNode_id;
        }

        public double getSim() {
            return sim;
        }

        public void setSim(double sim) {
            this.sim = sim;
        }

        public String getEndNodeName() {
            return endNodeName;
        }

        public void setEndNodeName(String endNodeName) {
            this.endNodeName = endNodeName;
        }

        public int getEndNode_id() {
            return endNode_id;
        }

        public void setEndNode_id(int endNode_id) {
            this.endNode_id = endNode_id;
        }
    }

    public void drawbutton(int button_y, int button_x, int line_x, final int tree_current, final nodechild[] nc, int starid) {
        int line_y = button_y;
        button_x = tree_current % 2 == 1 ? button_x : button_x - 100;
        int num = 1;
        if (tree_current != 1) num = nc.length;// 下一层个数
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
            bt[i].setTextSize(15 - (int) Math.sqrt(nc[i].getStartNodeName().length() - 1));
            bt[i].setText(nc[i].getStartNodeName());
            final int nc_id = nc[i].getStartNode_id();
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
                    getRemoteInfo(w, h, button_y_f + i1 * bt_paly_y, button_x_f, tree_current + 1, nc_id,
                            nc[i1].getEndNode_id());
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
                                           int starid, int endid) {
        int n = 5;
        nodechild[] nc = new nodechild[n];
//        for (int i = 0; i < n; i++) {
//            nc[i] = new nodechild("人工智能", 1,0.6784384769251408,"知识",3);
//        }
        nc[0] = new nodechild("人工智能", 1,0.6784384769251408,"知识",3);
        nc[1] = new nodechild("知识", 3,0.1,"知识",2);
        nc[2] = new nodechild("软件", 1,0.5,"人工智能",3);
        nc[3] = new nodechild("知识", 1,0.6784384769251408,"知识",3);
        nc[4] = new nodechild("知识", 1,0.6784384769251408,"人工智能",3);
        drawbutton(paly_y, paly_x, ppaly_x, tree_h, nc, starid);
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
