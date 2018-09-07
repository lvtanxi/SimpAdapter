package com.lvtanxi.adapter.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lvtanxi.adapter.SimpAdapter;
import com.lvtanxi.adapter.SimpSectionedAdapter;
import com.lvtanxi.adapter.convert.LayoutConvert;
import com.lvtanxi.adapter.decoration.SectionDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private static final List<SectionData> data = new ArrayList<>();

    private List<SectionData> currentData = null;
    private SimpAdapter mSimpSectionedAdapter;

    static {
        data.add(new SectionData("条目1", 1));
        data.add(new SectionData("条目2", 2));
        data.add(new SectionData("条目3", 3));
        data.add(new SectionData("条目4", 4));
        data.add(new SectionData("条目5", 5));
        data.add(new SectionData("条目6", 6));
        data.add(new SectionData("条目7", 7));
        data.add(new SectionData("条目8", 8));
        data.add(new SectionData("条目9", 9));
       /* data.add(new User("Jack", 21, R.drawable.icon1, "123456789XX"));
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
        data.add(new Music("Just one last dance", R.drawable.icon5));*/

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentData = new ArrayList<>(data);
        recyclerView = findViewById(R.id.recyler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new SectionDecoration(0));
        mSimpSectionedAdapter = SimpAdapter.create(SimpSectionedAdapter.class)
                .map(R.layout.item_setion_header, SectionData.class, (convert, sectionData, position) -> {
                    System.out.println(convert);
                    convert.setText(R.id.section_title, sectionData.getTitle());
                }).map(R.layout.item_body, new LayoutConvert(String.class))
                .attachTo(recyclerView).convert();
        mSimpSectionedAdapter.addItems(currentData);
    }

}

