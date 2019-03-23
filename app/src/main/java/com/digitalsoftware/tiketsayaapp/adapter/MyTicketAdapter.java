package com.digitalsoftware.tiketsayaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitalsoftware.tiketsayaapp.MyTicketDetailAct;
import com.digitalsoftware.tiketsayaapp.R;
import com.digitalsoftware.tiketsayaapp.item.MyTicket;

import java.util.ArrayList;

public class MyTicketAdapter extends RecyclerView.Adapter<MyTicketAdapter.MyTicketViewHolder> {
    Context context;
    ArrayList<MyTicket> myTickets;

    public MyTicketAdapter(Context context, ArrayList<MyTicket> myTickets) {
        this.context = context;
        this.myTickets = myTickets;
    }

    @NonNull
    @Override
    public MyTicketViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyTicketViewHolder(LayoutInflater.from(context).inflate(R.layout.item_myticket, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyTicketViewHolder myTicketViewHolder, int i) {
        myTicketViewHolder.bindToMyTicketsProfile(myTickets.get(i));

        final String getNamaWisataDetail = myTickets.get(i).getNama_wisata();

        myTicketViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomyTicketDetail = new Intent(context, MyTicketDetailAct.class);
                gotomyTicketDetail.putExtra("nama_wisata", getNamaWisataDetail);
                context.startActivity(gotomyTicketDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTickets.size();
    }

    class MyTicketViewHolder extends RecyclerView.ViewHolder {
        TextView nama_wisata_mytickets, lokasi_mytickets, jumlah_myTickets;

        public MyTicketViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_wisata_mytickets = itemView.findViewById(R.id.nama_wisata_myticket);
            lokasi_mytickets = itemView.findViewById(R.id.lokasi_mytickets);
            jumlah_myTickets = itemView.findViewById(R.id.jumlah_ticket_mytickets);
        }
        public void bindToMyTicketsProfile(MyTicket myTicket){
            nama_wisata_mytickets.setText(myTicket.getNama_wisata());
            lokasi_mytickets.setText(myTicket.getLokasi());
            jumlah_myTickets.setText(myTicket.getJumlah_ticket() + " Tickets");
        }
    }
}
