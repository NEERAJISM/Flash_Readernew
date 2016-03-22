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

public class CustomListViewAdapterCard extends ArrayAdapter<RowItemCard> {

    Context context;
    public CustomListViewAdapterCard(Context context, int resourceId, List<RowItemCard> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtContent;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItemCard rowItemCard = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_adapter_card, null);
            holder = new ViewHolder();
            holder.txtContent = (TextView) convertView.findViewById(R.id.tv_content_custom_adapter_card);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtContent.setText(rowItemCard.getContent());
        return convertView;
    }
}
