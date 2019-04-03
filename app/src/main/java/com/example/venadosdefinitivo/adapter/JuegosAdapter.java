package com.example.venadosdefinitivo.adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.venadosdefinitivo.R;
import com.example.venadosdefinitivo.models.juegos.Juegos;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class JuegosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    List<Juegos> adapterList = new ArrayList<>();

    String meses = "";
    String evento = "";
    int pos1 = 0;

    public JuegosAdapter(Context context) {
        this.context = context;
    }

    public class juegosA extends RecyclerView.ViewHolder{

        ImageButton calendario;
        TextView mes;
        TextView fecha;
        TextView score;
        TextView Tvlocal;
        TextView TvVisitante;
        TextView dia;

        ImageView local;
        ImageView visitante;

        public juegosA(@NonNull View itemView) {
            super(itemView);

            calendario = itemView.findViewById(R.id.imageButton);
            calendario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.Events.TITLE, adapterList.get(getLayoutPosition()).getOpponent() +" VS Venados F.C");

                    String [] fech = adapterList.get(getLayoutPosition()).getDatetime().split("T");
                    String [] fechaCompleta = fech[0].split("-");
                    int day = Integer.parseInt(fechaCompleta[2]);
                    int month = Integer.parseInt(fechaCompleta[1])-1;
                    int year = Integer.parseInt(fechaCompleta[0]);

                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date strDate = null;

                    try {
                        strDate = sdf.parse(fech[0]);
                        if (new Date().after(strDate)) {
                            Toast.makeText(context, "Este partido ya paso", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(context, "Agregando!!!", Toast.LENGTH_SHORT).show();

                            GregorianCalendar calDate = new GregorianCalendar(year,month,day);
                            intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calDate.getTimeInMillis());
                            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calDate.getTimeInMillis());

                            context.startActivity(intent);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });

            mes = itemView.findViewById(R.id.mes);
            dia = itemView.findViewById(R.id.dia);
            fecha = itemView.findViewById(R.id.tvfecha);
            score = itemView.findViewById(R.id.tvscore);
            Tvlocal = itemView.findViewById(R.id.tvequipoLocal);
            TvVisitante = itemView.findViewById(R.id.tvEquipoVisitante);
            local = itemView.findViewById(R.id.ivlocal);
            visitante = itemView.findViewById(R.id.ivvisitante);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.view_juegos, viewGroup,false);
        return new juegosA(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        juegosA j = (juegosA)viewHolder;
        listaJuegos(j,i);
    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public void addjuegos(List <Juegos> lista){

        if(adapterList != null && adapterList.size() > 0)
        {
            lista.clear();
        }else{
            adapterList.addAll(lista);
            notifyDataSetChanged();
        }
    }

    public void listaJuegos(juegosA game, int p) {

        int pos = 0;

        evento = (adapterList.get(p).getOpponent() +" VS "+ "Venados F.C");
        String mescomparacion = asignarFecha(adapterList.get(p).getDatetime(),4);
        game.mes.setText(asignarFecha(adapterList.get(p).getDatetime(),4));

        //Scroll hacia abajo
        if(pos1 <= p){

            if(meses.equals(mescomparacion)){
                game.mes.setVisibility(View.GONE);
            }else{

                game.mes.setVisibility(View.VISIBLE);
                meses = mescomparacion;

                if(p != 0){

                    if(mescomparacion.equals(asignarFecha(adapterList.get(p-1).getDatetime(),4))){
                        game.mes.setVisibility(View.GONE);
                    }
                }
            }
            pos1 = p;
        }

        //Scroll hacia arriba
        if(pos1 > p){

            if(p != 0){
                meses = asignarFecha(adapterList.get(p-1).getDatetime(),4);
            }else{
                game.mes.setVisibility(View.VISIBLE);
                pos = 1;
            }

            if(meses.equals(mescomparacion) && pos == 0){
                game.mes.setVisibility(View.GONE);
                meses = mescomparacion;
            }else{
                game.mes.setVisibility(View.VISIBLE);
                meses = mescomparacion;
            }
            pos1 = p;
        }

        game.fecha.setText(asignarFecha(adapterList.get(p).getDatetime(), 0));
        game.dia.setText(asignarFecha(adapterList.get(p).getDatetime(),3));
        game.score.setText(adapterList.get(p).getHome_score()+" - "+ adapterList.get(p).getAway_score());
        if(adapterList.get(p).isLocal()){
            game.Tvlocal.setText(adapterList.get(p).getOpponent());
            Glide.with(context).load(adapterList.get(p).getOpponent_image()).into(game.local);
            Glide.with(context).load("https://tmssl.akamaized.net//images/wappen/big/32586.png?lm=1436681083.png").into(game.visitante);
            game.TvVisitante.setText("Venados F.C");
        }
        else {
            game.TvVisitante.setText(adapterList.get(p).getOpponent());
            Glide.with(context).load(adapterList.get(p).getOpponent_image()).into(game.visitante);
            Glide.with(context).load("https://tmssl.akamaized.net//images/wappen/big/32586.png?lm=1436681083.png").into(game.local);
            game.Tvlocal.setText("Venados F.C");
        }
    }

    public String asignarFecha(String fetch, int caso){

        String fech[] = fetch.split("T");
        String dato = fetch;
        String fecha = "yyyy-MM-dd";
        String respuesta ="";

        SimpleDateFormat date = new SimpleDateFormat(fecha);
        SimpleDateFormat result;
        ParsePosition dateparse;
        Date dates;

        switch(caso)
        {
            //Muestra el dia de la semana en numero
            case 0:
                //dia

                result = new SimpleDateFormat("d");
                dateparse = new ParsePosition(0);
                dates = date.parse(dato, dateparse);
                respuesta = result.format(dates);
                return respuesta;

            //Muestra el mes en numero
            case 1:
                //Mes del año
                result = new SimpleDateFormat("MM");
                dateparse = new ParsePosition(0);
                dates = date.parse(dato, dateparse);
                respuesta = result.format(dates);
                return respuesta;

            //Muestra el anio en numeros
            case 2:
                //año
                result = new SimpleDateFormat("yyyy");
                dateparse = new ParsePosition(0);
                dates = date.parse(dato, dateparse);
                respuesta = result.format(dates);
                return respuesta;

            //Muestra en letras el dia de la semana
            case 3:
                result = new SimpleDateFormat("EEE");
                dateparse = new ParsePosition(0);
                dates = date.parse(dato, dateparse);
                respuesta = result.format(dates);
                return respuesta;

            //Muestra en letras el mes del anio
            case 4:
                result = new SimpleDateFormat("MMMM");
                dateparse = new ParsePosition(0);
                dates = date.parse(dato, dateparse);
                respuesta = result.format(dates);
                return respuesta;

            default:
                return respuesta;
        }
    }
}
