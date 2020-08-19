package me.peace.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.peace.ui.utils.AnimationUtils;
import me.peace.widget.config.ImageConfig;
import me.peace.widget.image.Sh2ImageView;
import me.peace.widget.image.ShadowImageView;
import me.peace.widget.image.ShimmerImageView;
import me.peace.widget.image.UriImageView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private ImageConfig config;
    private String[] images = {
        "https://wx4.sinaimg.cn/mw690/5ba8d1cbly3ghv1ynzcaoj20go0gon4y.jpg",
        "https://wx2.sinaimg.cn/mw690/5ba8d1cbly3ghv1yo5ippj20u00u0tds.jpg",
        "https://wx4.sinaimg.cn/mw690/5ba8d1cbly3ghv1yni2tgj20u00u0h0c.jpg",
        "https://wx4.sinaimg.cn/mw690/acff3e61ly1ghvvq3lfrmj21o02804qr.jpg",
        "https://wx4.sinaimg.cn/mw690/acff3e61ly1ghvvq3lfrmj21o02804qr.jpg",
        "https://wx2.sinaimg.cn/mw690/daade500ly1ghv6p13yegj20zk0qo7wh.jpg",
    };
    private ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ImageAdapter();
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        config =
            new ImageConfig.Builder().offset(R.dimen.shadow_offset).shadow(R.drawable.shadow).build();
    }


    class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder>{

        @NonNull
        @Override
        public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
            return new ImageHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
            holder.image.config(config);
            holder.image.shimmer();
//            holder.image.getHierarchy().setPlaceholderImage(R.drawable.draw);
            holder.image.setImage(imageUrl(),R.mipmap.ic_launcher_round,R.mipmap.ic_launcher);
            holder.image.setOnFocusChangeListener((v, hasFocus) -> {
                scale(v,hasFocus);
                if (hasFocus){
                    holder.image.startShimmer();
                }else{
                    holder.image.stopShimmer();
                }
            });
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class ImageHolder extends RecyclerView.ViewHolder{
//            private ShimmerImageView image;
//            private ShadowImageView image;
            private Sh2ImageView image;
            public ImageHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
            }
        }

        private void scale(View v,boolean hasFocus){
            AnimationUtils.scaleCenter(v,hasFocus? 1.0f : 1.5f,hasFocus?1.5f : 1.0f,500);
        }

        private String imageUrl(){
            int index = new Random().nextInt(6);
            return images[index];
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_0){
            adapter.notifyDataSetChanged();
        }
        return super.onKeyDown(keyCode, event);
    }
}
