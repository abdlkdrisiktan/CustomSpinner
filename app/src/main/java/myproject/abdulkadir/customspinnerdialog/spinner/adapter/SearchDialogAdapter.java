package myproject.abdulkadir.customspinnerdialog.spinner.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import myproject.abdulkadir.customspinnerdialog.R;
import myproject.abdulkadir.customspinnerdialog.spinner.BaseSearchDialogCompat;
import myproject.abdulkadir.customspinnerdialog.spinner.listeners.SearchResultListener;
import myproject.abdulkadir.customspinnerdialog.spinner.listeners.Searchable;
import myproject.abdulkadir.customspinnerdialog.spinner.search.StringsHelper;

public class SearchDialogAdapter<T extends Searchable>
    extends RecyclerView.Adapter<SearchDialogAdapter.ViewHolder> {

    protected Context                mContext;
    private   List<T>                mItems                  = new ArrayList<>();
    private   LayoutInflater         mLayoutInflater;
    private   int                    mLayout;
    private   SearchResultListener   mSearchResultListener;
    private   AdapterViewBinder<T>   mViewBinder;
    private   String                 mSearchTag;
    private   boolean                mHighlightPartsInCommon = true;
    private   int                    mHighlightColor;
    private   BaseSearchDialogCompat mSearchDialog;

    public SearchDialogAdapter(Context context,
                               @LayoutRes int layout,
                               List<T> items,int mHighlightColor) {
        this(context, layout, null, items, mHighlightColor);
    }

    public SearchDialogAdapter(Context context,
                               AdapterViewBinder<T> viewBinder,
                               @LayoutRes int layout,
                               List<T> items,int mHighlightColor) {
        this(context, layout, viewBinder, items,mHighlightColor);
    }

    public SearchDialogAdapter(Context context,
                               @LayoutRes int layout,
                               @Nullable AdapterViewBinder<T> viewBinder,
                               List<T> items,
                               int mHighlightColor) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mItems = items;
        this.mLayout = layout;
        this.mViewBinder = viewBinder;
        this.mHighlightColor = mHighlightColor;
    }

    public List<T> getItems() {
        return mItems;
    }

    public void setItems(List<T> objects) {
        this.mItems = objects;
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public SearchDialogAdapter<T> setViewBinder(AdapterViewBinder<T> viewBinder) {
        this.mViewBinder = viewBinder;
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View convertView = mLayoutInflater.inflate(mLayout, parent, false);
        convertView.setTag(new ViewHolder(convertView));
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchDialogAdapter.ViewHolder holder,
                                 int position) {
        initializeViews(getItem(position), holder, position);
    }

    private void initializeViews(final T object,
                                 final SearchDialogAdapter.ViewHolder holder,
                                 final int position) {
        if (mViewBinder != null) {
            mViewBinder.bind(holder, object, position);
        }
        LinearLayout root = holder.getViewById(R.id.root);
        TextView text = holder.getViewById(R.id.text);
        if (mSearchTag != null && mHighlightPartsInCommon) {
            text.setText(StringsHelper.highlightLCS(object.getString(), getSearchTag(),
                                                    mHighlightColor));
        } else {
            text.setText(object.getString());
        }

        if (mSearchResultListener != null) {
            holder.getBaseView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSearchResultListener.onSelected(mSearchDialog, object, position);
                }
            });
        }
    }

    public SearchResultListener getSearchResultListener() {
        return mSearchResultListener;
    }

    public void setSearchResultListener(SearchResultListener searchResultListener) {
        this.mSearchResultListener = searchResultListener;
    }

    public String getSearchTag() {
        return mSearchTag;
    }

    public SearchDialogAdapter setSearchTag(String searchTag) {
        mSearchTag = searchTag;
        return this;
    }

    public boolean isHighlightPartsInCommon() {
        return mHighlightPartsInCommon;
    }

    public SearchDialogAdapter setHighlightPartsInCommon(boolean highlightPartsInCommon) {
        mHighlightPartsInCommon = highlightPartsInCommon;
        return this;
    }

    public SearchDialogAdapter setHighlightColor(int highlightColor) {
        mHighlightColor = highlightColor;
        return this;
    }

    public SearchDialogAdapter setSearchDialog(BaseSearchDialogCompat searchDialog) {
        mSearchDialog = searchDialog;
        return this;
    }

    public interface AdapterViewBinder<T> {

        void bind(ViewHolder holder,
                  T item,
                  int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private View mBaseView;

        public ViewHolder(View view) {
            super(view);
            mBaseView = view;
        }

        public View getBaseView() {
            return mBaseView;
        }

        public <T> T getViewById(@IdRes int id) {
            return (T) mBaseView.findViewById(id);
        }

        public void clearAnimation(@IdRes int id) {
            mBaseView.findViewById(id).clearAnimation();
        }
    }
}