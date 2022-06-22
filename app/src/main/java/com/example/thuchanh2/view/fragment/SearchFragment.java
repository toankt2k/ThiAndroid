package com.example.thuchanh2.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuchanh2.R;
import com.example.thuchanh2.db.TicketDao;
import com.example.thuchanh2.model.Ticket;
import com.example.thuchanh2.view.activity.UpdateActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {

    private TicketDao dao;
    private RecyclerView rv;
    private TicketAdapter adapter;
    private CheckBox cb1, cb2;
    private TextView tvTotal, tvTk;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(
                R.layout.fragment_search,
                container, false
        );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rv = view.findViewById(R.id.recycler_view);
        tvTotal = view.findViewById(R.id.tv_total);
        tvTk = view.findViewById(R.id.tv_tk);
        cb1 = view.findViewById(R.id.cb1);
        cb2 = view.findViewById(R.id.cb2);
        dao = TicketDao.getInstance(getContext());
        adapter = new TicketAdapter();
        adapter.setItemListener(new TicketAdapter.ItemListener() {
            @Override
            public void onDelete(Ticket ticket, int position) {
                adapter.remove(ticket, position);
                dao.delete(ticket);
            }

            @Override
            public void onEdit(Ticket ticket, int position) {
                Intent intent = new Intent(getContext(), UpdateActivity.class);
                intent.putExtra("ticket", ticket);
                getActivity().startActivity(intent);
            }
        });
        rv.setAdapter(adapter);
        cb1.setOnClickListener(v -> search());
        cb2.setOnClickListener(v -> search());
        count();
    }

    private void count() {
        List<Ticket> list = dao.getAll();
        Pair<String, Integer>[] pairs = new Pair[]{
                new Pair("Hà Nội", 0),
                new Pair("Đà nẵng", 0),
                new Pair("Huế", 0),
                new Pair("Tp HCM", 0),
                new Pair("Hải Phòng", 0)
        };
        for (Ticket e : list) {
            if (e.getDepart() == 0) {
                pairs[0] = new Pair<>(pairs[0].first, Math.max(pairs[0].second, e.getPrice()));
            }
            if (e.getDepart() == 1) {
                pairs[1] = new Pair<>(pairs[1].first, Math.max(pairs[1].second, e.getPrice()));
            }
            if (e.getDepart() == 2) {
                pairs[2] = new Pair<>(pairs[2].first, Math.max(pairs[2].second, e.getPrice()));
            }
            if (e.getDepart() == 3) {
                pairs[3] = new Pair<>(pairs[3].first, Math.max(pairs[3].second, e.getPrice()));
            }
            if (e.getDepart() == 4) {
                pairs[4] = new Pair<>(pairs[4].first, Math.max(pairs[4].second, e.getPrice()));
            }
        }
        Arrays.sort(pairs, (t1, t2) -> t2.second - t1.second);
        String result = pairs[0].first + " giá cao nhất: " + pairs[0].second + "<br>";
        result += pairs[1].first + " giá cao nhất: " + pairs[1].second + "<br>";
        result += pairs[2].first + " giá cao nhất: " + pairs[2].second + "<br>";
        result += pairs[3].first + " giá cao nhất: " + pairs[3].second + "<br>";
        result += pairs[4].first + " giá cao nhất: " + pairs[4].second + "<br>";
        tvTk.setText(Html.fromHtml(result));
    }

    private void search() {
        if (cb1.isChecked() && cb2.isChecked()) {
            List<Ticket> list = dao.getAll();
            adapter.setList(list);
            tvTotal.setText("Số lượng: " + list.size());
            return;
        }
        if (!cb1.isChecked() && !cb2.isChecked()) {
            adapter.setList(new ArrayList<>());
            tvTotal.setText("Số lượng: " + 0);
            return;
        }
        List<Ticket> list = dao.getAll();
        if (cb1.isChecked()) {
            ArrayList<Ticket> tmp = new ArrayList<>();
            for (Ticket e : list) {
                if (e.getHasPackage()) {
                    tmp.add(e);
                }
            }
            list = tmp;
            adapter.setList(list);
            tvTotal.setText("Số lượng: " + list.size());
            return;
        }
        if (cb2.isChecked()) {
            ArrayList<Ticket> tmp = new ArrayList<>();
            for (Ticket e : list) {
                if (!e.getHasPackage()) {
                    tmp.add(e);
                }
            }
            list = tmp;
            adapter.setList(list);
            tvTotal.setText("Số lượng: " + list.size());
            return;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        search();
        count();
    }
}
