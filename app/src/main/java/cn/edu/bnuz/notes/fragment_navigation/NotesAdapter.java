package cn.edu.bnuz.notes.fragment_navigation;

import cn.edu.bnuz.notes.R;
import cn.edu.bnuz.notes.login_register.Register;
import cn.edu.bnuz.notes.ntwpojo.NotesbyPageorTagIdRD;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class NotesAdapter extends ArrayAdapter<NotesbyPageorTagIdRD.NotesPkg.Notes> {

    private List<NotesbyPageorTagIdRD.NotesPkg.Notes> Lnotes;
    private Context ctx;
    private LayoutInflater mInflater;
    private int resourceId;
    public NotesAdapter(Context context,int resourceId, List<NotesbyPageorTagIdRD.NotesPkg.Notes> Lnotes) {
        super(context, resourceId,Lnotes);
        this.ctx = context;
        this.Lnotes = Lnotes;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return Lnotes.size();
    }

    @Override
    public NotesbyPageorTagIdRD.NotesPkg.Notes getItem(int position) {
        return Lnotes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        NotesbyPageorTagIdRD.NotesPkg.Notes notesContent=Lnotes.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.simple_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.main_body = (TextView) convertView.findViewById(R.id.main_body);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(notesContent.getTitle());
        viewHolder.main_body.setText(notesContent.getContent());
        viewHolder.time.setText(notesContent.getGmtModified());
        AbsListView.LayoutParams param = new AbsListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,200);
        convertView.setLayoutParams(param);
        return convertView;
    }
    static class ViewHolder {
        public TextView title;
        public TextView main_body;
        public TextView time;
    }
}