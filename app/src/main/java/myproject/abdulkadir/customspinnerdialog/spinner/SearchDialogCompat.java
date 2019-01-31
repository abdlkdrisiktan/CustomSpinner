package myproject.abdulkadir.customspinnerdialog.spinner;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;
import java.util.ArrayList;
import myproject.abdulkadir.customspinnerdialog.R;
import myproject.abdulkadir.customspinnerdialog.spinner.adapter.SearchDialogAdapter;
import myproject.abdulkadir.customspinnerdialog.spinner.listeners.FilterResultListener;
import myproject.abdulkadir.customspinnerdialog.spinner.listeners.SearchResultListener;
import myproject.abdulkadir.customspinnerdialog.spinner.listeners.Searchable;

public class SearchDialogCompat<T extends Searchable> extends BaseSearchDialogCompat<T> {

    private String mTitle;
    private String mSearchHint;
    private SearchResultListener<T> mSearchResultListener;
    private   int                    mHighlightColor;

    public SearchDialogCompat(Context context,
                                     String title,
                                     String searchHint,
                                     @Nullable Filter filter,
                                     ArrayList<T> items,
                                     SearchResultListener<T> searchResultListener,int mHighlightColor) {
        super(context, items, filter, null, null);
        init(title, searchHint, searchResultListener,mHighlightColor);
    }
    private void init(String title,
                      String searchHint,
                      SearchResultListener<T> searchResultListener,
                      int mHighlightColor) {
        mTitle = title;
        mSearchHint = searchHint;
        mSearchResultListener = searchResultListener;
        this.mHighlightColor = mHighlightColor;
    }
    @Override
    protected void getView(View view) {
        setContentView(view);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCancelable(true);
        TextView txtTitle = (TextView) view.findViewById(R.id.txt_title);
        final EditText searchBox = (EditText) view.findViewById(getSearchBoxId());
        txtTitle.setText(mTitle);
        searchBox.setHint(mSearchHint);
        view.findViewById(R.id.dummy_background).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        final SearchDialogAdapter adapter = new SearchDialogAdapter<>(getContext(),
                                                                      R.layout.list_item_search_spinner,
                                                                      getItems(),mHighlightColor);
        adapter.setSearchResultListener(mSearchResultListener);
        adapter.setSearchDialog(this);
        setFilterResultListener(new FilterResultListener<T>() {
            @Override
            public void onFilter(ArrayList<T> items) {
                ((SearchDialogAdapter) getAdapter()).setSearchTag(searchBox.getText().toString())
                    .setItems(items);
            }
        });
        setAdapter(adapter);
    }

    public SearchDialogCompat setTitle(String title) {
        mTitle = title;
        return this;
    }

    public SearchDialogCompat setSearchHint(String searchHint) {
        mSearchHint = searchHint;
        return this;
    }

    public SearchDialogCompat setSearchResultListener(SearchResultListener<T> searchResultListener) {
        mSearchResultListener = searchResultListener;
        return this;
    }

    @LayoutRes
    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_search;
    }

    @IdRes
    @Override
    protected int getSearchBoxId() {
        return R.id.txt_search;
    }

    @IdRes
    @Override
    protected int getRecyclerViewId() {
        return R.id.rv_items;
    }
}
