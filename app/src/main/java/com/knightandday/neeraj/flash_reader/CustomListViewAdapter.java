package com.knightandday.neeraj.flash_reader;

/**
 * Created by Neeraj on 04-Mar-16.
 */
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListViewAdapter extends ArrayAdapter<RowItem> {

    Context context;

    public CustomListViewAdapter(Context context, int resourceId, List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtTitle;
        TextView txtSize;
        TextView txtDate;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_adapter, null);
            holder = new ViewHolder();
            holder.txtDate = (TextView) convertView.findViewById(R.id.tv_date_custom_adapter);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.tv_title_custom_adapter);
            holder.txtSize = (TextView) convertView.findViewById(R.id.tv_size_custom_adapter);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtDate.setText(rowItem.getDate());
        holder.txtTitle.setText(rowItem.getTitle());
        holder.txtSize.setText(rowItem.getSize());
        return convertView;
    }
}
