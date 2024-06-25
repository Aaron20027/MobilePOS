package com.example.mobilepose;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ReportsAdapter extends FragmentStateAdapter {
    public ReportsAdapter(@NonNull ReportsManagement fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new SalesReport();
            case 1:
                return new ProductReport();
            case 2:
                return new EmployeeReport();
            default:
                return new SalesReport();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
