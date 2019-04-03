package com.example.venadosdefinitivo.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.venadosdefinitivo.R;
import com.example.venadosdefinitivo.models.jugadores.Jugadores;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JugadoresAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    List<Jugadores> adapterList;
    Dialog jugador;

    public JugadoresAdapter(Context context) {
        this.context = context;
        this.adapterList = new ArrayList<>();
    }

    public class jugadoresA extends RecyclerView.ViewHolder{

        TextView nombre;
        TextView posicion;
        ImageView foto;

        public jugadoresA(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre_jugador);
            posicion = itemView.findViewById(R.id.posicion_jugador);
            foto = itemView.findViewById(R.id.fotoJugador);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.view_jugadores,viewGroup,false);

        final jugadoresA dialog = new jugadoresA(view);
        jugador = new Dialog(viewGroup.getContext());
        jugador.setContentView(R.layout.dialog);

        dialog.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView nombreDialog = (TextView)jugador.findViewById(R.id.nombre_popup);
                TextView posicionDialog = (TextView)jugador.findViewById(R.id.posicion_popup);
                TextView fechaNaDialog = (TextView)jugador.findViewById(R.id.fecha_popup);
                TextView lugarNaDialog = (TextView)jugador.findViewById(R.id.lugarN_popup);
                TextView pesoDialog = (TextView)jugador.findViewById(R.id.peso_popup);
                TextView alturaDialog = (TextView)jugador.findViewById(R.id.altura_popup);
                TextView equipoDialog = (TextView)jugador.findViewById(R.id.equipoA_popup);
                ImageView fotoDialog = (ImageView) jugador.findViewById(R.id.fto_popup);

                Glide.with(context).load(adapterList.get(dialog.getAdapterPosition()).getImage()).apply(new RequestOptions().transform(new CircleCrop())).into(fotoDialog);
                nombreDialog.setText(adapterList.get(dialog.getAdapterPosition()).getName());
                posicionDialog.setText(adapterList.get(dialog.getAdapterPosition()).getPosition());
                fechaNaDialog.setText(Birthday(adapterList.get(dialog.getAdapterPosition()).getBirthday()));
                lugarNaDialog.setText(adapterList.get(dialog.getAdapterPosition()).getBirth_place());

                pesoDialog.setText(Integer.toString(adapterList.get(dialog.getAdapterPosition()).getWeight()).equals("0")?" ":String.valueOf(adapterList.get(dialog.getAdapterPosition()).getWeight()+"KG"));
                alturaDialog.setText(String.valueOf(adapterList.get(dialog.getAdapterPosition()).getHeight()).equals("null")?" ":String.valueOf(adapterList.get(dialog.getAdapterPosition()).getHeight()+"M"));

                equipoDialog.setText(adapterList.get(dialog.getAdapterPosition()).getLast_team());

                jugador.show();
            }
        });

        return dialog;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(!adapterList.isEmpty()){
            jugadoresA j = (jugadoresA)viewHolder;
            listaJugadores(j,i);
        }
    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public void addJugadores (List<Jugadores> lista) {
        if(!adapterList.isEmpty()){
            adapterList.clear();
        }
        if(adapterList.isEmpty()){
            adapterList.addAll(lista);
            notifyDataSetChanged();
        }
    }

    public void listaJugadores(jugadoresA players, int p){

        players.nombre.setText(adapterList.get(p).getName());
        players.posicion.setText(adapterList.get(p).getPosition());
        String url = adapterList.get(p).getImage();
        Glide.with(context).load(adapterList.get(p).getImage()).apply(new RequestOptions().transform(new CircleCrop())).into(players.foto);

    }

    public String Birthday(String fetch){

        String fech[] = fetch.split("T");
        String dato = fetch;
        String fecha = "yyyy-MM-dd";
        String respuesta ="";

        SimpleDateFormat date = new SimpleDateFormat(fecha);
        SimpleDateFormat result = new SimpleDateFormat("d MMMM yyyy");
        ParsePosition dateparse = new ParsePosition(0);
        Date dates = date.parse(dato, dateparse);
        return respuesta = result.format(dates);
    }
}
