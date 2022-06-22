package com.example.thuchanh2.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuchanh2.R;
import com.example.thuchanh2.db.TicketDao;
import com.example.thuchanh2.model.Ticket;
import com.example.thuchanh2.view.activity.UpdateActivity;

import java.util.List;

public class HomeFragment extends Fragment {

    private TicketDao dao;
    private RecyclerView rv;
    private TicketAdapter adapter;
    private List<Ticket> tickets;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(
                R.layout.fragment_home,
                container, false
        );
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.recycler_view);
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
        tickets = dao.getAll();
        adapter.setList(tickets);
    }

    @Override
    public void onResume() {
        super.onResume();
        tickets = dao.getAll();
        adapter.setList(tickets);
    }
}