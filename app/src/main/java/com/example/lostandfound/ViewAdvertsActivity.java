package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewAdvertsActivity extends AppCompatActivity {

    RecyclerView recyclerViewMain;
    VerticalAdapter vAdapter;
    AdvertDatabase advertDataBase;
    List<Advert> advertList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_advert);
        recyclerViewMain = findViewById(R.id.recyclerView);
        advertDataBase = new AdvertDatabase(this);
        createAdvertList();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        createAdvertList();
    }

    private void createAdvertList()
    {
        advertList = advertDataBase.getAllAdvert();
        vAdapter = new VerticalAdapter(advertList, new onItemClickListener()
        {
            @Override
            public void itemClick(Advert advert) {
                Intent intent = new Intent(ViewAdvertsActivity.this, ShowAdvertActivity.class);
                intent.putExtra("id", advert.getId());
                startActivity(intent);
            }
        });
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMain.setAdapter(vAdapter);
    }

    public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.ViewHolder> {

        private List<Advert> advertList;
        private final onItemClickListener listener;

        public VerticalAdapter(List<Advert> advertList, onItemClickListener listener) {
            this.advertList = advertList;
            this.listener = listener;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.titleText);
            }

            public void bind(Advert advert, final onItemClickListener listener) {
                textView.setText(advert.getType() + " " + advert.getName());
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.itemClick(advert);
                    }
                });
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advert_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Advert advert = advertList.get(position);
            holder.bind(advert, listener);
        }

        @Override
        public int getItemCount() {
            if (this.advertList == null) {
                return 0;
            }
            return this.advertList.size();
        }
    }

    public interface onItemClickListener
    {
        void itemClick(Advert advert);
    }
}