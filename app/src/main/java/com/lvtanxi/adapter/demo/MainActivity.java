package com.lvtanxi.adapter.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lvtanxi.adapter.SimplicityAdapter;
import com.lvtanxi.adapter.SimplicityConvert;
import com.lvtanxi.adapter.convert.IViewConvert;
import com.lvtanxi.adapter.decoration.SectionDecoration;
import com.lvtanxi.adapter.listener.OnItemClickListener;
import com.lvtanxi.adapter.manager.CustomLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private static final List<Object> data = new ArrayList<>();

    private List<Object> currentData = null;

    static {
        data.add(new SectionHeader("My Friends"));
        data.add(new User("Jack", 21, R.drawable.icon1, "123456789XX"));
        data.add(new User("Marry", 17, R.drawable.icon2, "123456789XX"));

        data.add(new SectionHeader("My Images"));
        data.add(new Image(R.drawable.cover1));
        data.add(new Image(R.drawable.cover2));
        data.add(new Image(R.drawable.cover3));
        data.add(new Image(R.drawable.cover4));
        data.add(new Image(R.drawable.cover5));
        data.add(new Image(R.drawable.cover6));
        data.add(new Image(R.drawable.cover7));
        data.add(new Image(R.drawable.cover8));
        data.add(new Image(R.drawable.cover9));
        data.add(new Image(R.drawable.cover10));

        data.add(new Body("贾静雯"));
        data.add(new Body("安以轩"));
        data.add(new Body("张柏芝"));
        data.add(new Body("刘亦菲"));
        data.add(new Body("林志玲"));
        data.add(new Body("巩俐"));

        data.add(new SectionHeader("My Musics"));
        data.add(new Music("Love story", R.drawable.icon3));
        data.add(new Music("Nothing's gonna change my love for u", R.drawable.icon4));
        data.add(new Music("Just one last dance", R.drawable.icon5));

    }

    private SimplicityAdapter mSimplicityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentData = new ArrayList<>(data);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        CustomLayoutManager customLayoutManager = new CustomLayoutManager();
        customLayoutManager.setSpanSizeLookup(new CustomLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if( mSimplicityAdapter.getItem(position) instanceof SectionHeader){
                    return 1;
                }else if(mSimplicityAdapter.getItem(position) instanceof User){
                    return 2;
                }else if(mSimplicityAdapter.getItem(position) instanceof Image){
                    return 3;
                }else if(mSimplicityAdapter.getItem(position) instanceof Body){
                    return 4;
                }else if(mSimplicityAdapter.getItem(position) instanceof Music){
                    return 2;
                }
                return 1;
            }});
        recyclerView.setLayoutManager(customLayoutManager);
        recyclerView.addItemDecoration(new SectionDecoration(0));
        mSimplicityAdapter = SimplicityAdapter.create()
                .registerDefault(R.layout.item_user, new SimplicityConvert<User>() {
                    @Override
                    public void convert(IViewConvert convert, User user, int position) {
                        Log.d("MainActivity", "item_user");
                        convert.setText(R.id.name, user.getName())
                                .setImage(R.id.avatar, user.getAvatarRes())
                                .setText(R.id.phone, user.getPhone())
                                .setTextColor(R.id.phone, Color.RED)
                                .setTextSize(R.id.phone, 12);
                    }
                })
                .register(R.layout.item_setion_header, new SimplicityConvert<SectionHeader>() {
                    @Override
                    public void convert(IViewConvert convert, SectionHeader sectionHeader, int position) {
                        Log.d("MainActivity", "item_setion_header");
                        convert.setText(R.id.section_title, sectionHeader.getTitle());
                    }
                })
                .register(R.layout.item_body, new SimplicityConvert<Body>() {
                    @Override
                    public void convert(IViewConvert convert, Body body, int position) {
                        Log.d("MainActivity", "item_body");
                        convert.setText(R.id.iem_body, body.getName())
                        .setOnItemChildClickListener(R.id.iem_body);
                    }
                })
                .register(R.layout.item_image, new SimplicityConvert<Image>() {
                    @Override
                    public void convert(IViewConvert convert, final Image image, int position) {
                        Log.d("MainActivity", "item_image");
                        convert.with(R.id.imageView, new IViewConvert.Action<ImageView>() {
                            @Override
                            public void action(ImageView imageView) {
                                Glide.with(MainActivity.this).load(image.getRes()).into(imageView);
                            }
                        });
                    }
                })
                .register(R.layout.item_music, new SimplicityConvert<Music>() {
                    @Override
                    public void convert(IViewConvert convert, Music music, int position) {
                        Log.d("MainActivity", "item_music");
                        convert.setText(R.id.name, music.getName())
                                .setImage(R.id.cover, music.getCoverRes());
                    }
                })
                .registerOnItemClickListener(new OnItemClickListener<Music>() {
                    @Override
                    public void onItemClick(View view, Music music, int position) {
                        Toast.makeText(MainActivity.this, music.getName(), Toast.LENGTH_SHORT).show();
                    }
                })
                .attachTo(recyclerView);

        mSimplicityAdapter.addItems(currentData,true);
    }

}

