package myproject.abdulkadir.customspinnerdialog.spinner.listeners;

import myproject.abdulkadir.customspinnerdialog.spinner.BaseSearchDialogCompat;

public interface SearchResultListener<T> {
    void onSelected(BaseSearchDialogCompat dialog, T item, int position);
}
